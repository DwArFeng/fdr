package com.dwarfeng.fdr.impl.handler.fetcher.simulate.wave;

import com.dwarfeng.fdr.sdk.handler.fetcher.AbstractFetcherSession;
import com.dwarfeng.fdr.sdk.util.RecordInfoUtil;
import com.dwarfeng.fdr.stack.bean.dto.RecordInfo;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Objects;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 函数波形模拟抓取器会话。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SimulateWaveFetcherSession extends AbstractFetcherSession {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimulateWaveFetcherSession.class);

    private static final long NANOS_PER_MILLI = 1_000_000L;

    private final ApplicationContext ctx;

    private final SimulateWaveFetcherConfig config;

    private final ThreadPoolTaskScheduler scheduler;

    private final Lock lock = new ReentrantLock();

    private WavePointRuntime wavePointRuntime;
    private ScheduledFuture<?> scheduledFuture;

    public SimulateWaveFetcherSession(
            ApplicationContext ctx, SimulateWaveFetcherConfig config, ThreadPoolTaskScheduler scheduler
    ) {
        this.ctx = ctx;
        this.config = config;
        this.scheduler = scheduler;
    }

    @Override
    protected void doOpenSession() {
        LOGGER.info("函数波形模拟点位 {} 会话打开...", config.getPointKey());
        lock.lock();
        try {
            wavePointRuntime = parseRuntime();
        } finally {
            lock.unlock();
        }
    }

    private WavePointRuntime parseRuntime() {
        LongIdKey pointKey = FastJsonLongIdKey.toStackBean(config.getPointKey());
        long tickPeriod = config.getTickPeriod();
        long sampleFrequency = config.getSampleFrequency();
        String waveType = config.getWaveType();
        double wavePeriod = config.getWavePeriod();
        double amplitude = config.getAmplitude();
        double offset = config.getOffset();
        double dutyCycle = config.getDutyCycle();
        long maxSamplesPerTick = config.getMaxSamplesPerTick();
        long misfireThreshold = config.getMisfireThreshold();
        long misfirePostpone = config.getMisfirePostpone();

        SimulateWaveValueGenerator generator = ctx.getBean(
                SimulateWaveValueGenerator.class, sampleFrequency, waveType, wavePeriod, amplitude, offset, dutyCycle
        );
        Instant baseInstant = Instant.now();
        return new WavePointRuntime(
                pointKey, tickPeriod, sampleFrequency, maxSamplesPerTick, misfireThreshold, misfirePostpone,
                baseInstant, generator
        );
    }

    @Override
    protected void doStartFetch() {
        LOGGER.info("函数波形模拟点位 {} 开始抓取...", config.getPointKey());
        lock.lock();
        try {
            PeriodicTrigger delegateTrigger = new PeriodicTrigger(config.getTickPeriod());
            delegateTrigger.setFixedRate(true);
            Trigger safeTickTrigger = new SimulateWaveSafeTickTrigger(
                    delegateTrigger, config.getMisfireThreshold(), config.getMisfirePostpone()
            );
            scheduledFuture = scheduler.schedule(this::tick, safeTickTrigger);
        } finally {
            lock.unlock();
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void tick() {
        long start = System.currentTimeMillis();
        try {
            long tickIndex = wavePointRuntime.getAndIncrementTickCount();
            long tickEndNanos = (tickIndex + 1L) * wavePointRuntime.getTickPeriod() * NANOS_PER_MILLI;
            long shouldProduced = tickEndNanos * wavePointRuntime.getSampleFrequency() / 1_000_000_000L;
            long toGenerate = shouldProduced - wavePointRuntime.getProducedSamples();

            if (toGenerate <= 0) {
                return;
            }
            if (toGenerate > wavePointRuntime.getMaxSamplesPerTick()) {
                LOGGER.warn(
                        "函数波形模拟点位 {} 单 tick 样本数 {} 超过阈值 {}, 丢弃本 tick 并推进样本游标",
                        wavePointRuntime.getPointKey(), toGenerate, wavePointRuntime.getMaxSamplesPerTick()
                );
                wavePointRuntime.setProducedSamples(shouldProduced);
                return;
            }

            for (long i = 0; i < toGenerate; i++) {
                long sampleIndex = wavePointRuntime.getProducedSamples() + i;
                Double value = wavePointRuntime.getGenerator().generateValue(sampleIndex);
                Instant happenedInstant = computeInstant(
                        wavePointRuntime.getBaseInstant(), sampleIndex, wavePointRuntime.getSampleFrequency()
                );
                RecordInfo recordInfo = RecordInfoUtil.newInstance(
                        wavePointRuntime.getPointKey(), value, happenedInstant
                );
                context.record(recordInfo);
            }
            wavePointRuntime.setProducedSamples(shouldProduced);

            long cost = System.currentTimeMillis() - start;
            if (cost > Math.max(1L, wavePointRuntime.getTickPeriod())) {
                LOGGER.warn(
                        "函数波形模拟点位 {} 补样耗时 {}ms 超过 tick 周期 {}ms",
                        wavePointRuntime.getPointKey(), cost, wavePointRuntime.getTickPeriod()
                );
            }
        } catch (Exception e) {
            LOGGER.warn("函数波形模拟点位 {} 补样失败", wavePointRuntime.getPointKey(), e);
        }
    }

    private Instant computeInstant(Instant baseInstant, long sampleIndex, long frequency) {
        long seconds = sampleIndex / frequency;
        long nanos = (sampleIndex % frequency) * 1_000_000_000L / frequency;
        return baseInstant.plusSeconds(seconds).plusNanos(nanos);
    }

    @Override
    protected void doStopFetch() {
        LOGGER.info("函数波形模拟点位 {} 停止抓取...", config.getPointKey());
        lock.lock();
        try {
            cancelAndResetScheduledFuture();
        } finally {
            lock.unlock();
        }
    }

    @Override
    protected void doCloseSession() {
        LOGGER.info("函数波形模拟点位 {} 会话关闭...", config.getPointKey());
        lock.lock();
        try {
            cancelAndResetScheduledFuture();
            wavePointRuntime = null;
        } finally {
            lock.unlock();
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void cancelAndResetScheduledFuture() {
        if (Objects.isNull(scheduledFuture)) {
            return;
        }
        if (!scheduledFuture.isCancelled()) {
            scheduledFuture.cancel(true);
        }
        try {
            scheduledFuture.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (CancellationException | ExecutionException ignored) {
            // 抛异常也要按照基本法。
        }
        scheduledFuture = null;
    }

    @Override
    public String toString() {
        return "SimulateWaveFetcherSession{" +
                "ctx=" + ctx +
                ", config=" + config +
                ", scheduler=" + scheduler +
                ", lock=" + lock +
                ", wavePointRuntime=" + wavePointRuntime +
                ", scheduledFuture=" + scheduledFuture +
                ", context=" + context +
                '}';
    }

    private static final class WavePointRuntime {

        private final LongIdKey pointKey;
        private final long tickPeriod;
        private final long sampleFrequency;
        private final long maxSamplesPerTick;
        private final long misfireThreshold;
        private final long misfirePostpone;
        private final Instant baseInstant;
        private final SimulateWaveValueGenerator generator;
        private final AtomicLong tickCount = new AtomicLong(0);
        private long producedSamples;

        @SuppressWarnings("DuplicatedCode")
        private WavePointRuntime(
                LongIdKey pointKey, long tickPeriod, long sampleFrequency, long maxSamplesPerTick,
                long misfireThreshold, long misfirePostpone, Instant baseInstant, SimulateWaveValueGenerator generator
        ) {
            this.pointKey = pointKey;
            this.tickPeriod = tickPeriod;
            this.sampleFrequency = sampleFrequency;
            this.maxSamplesPerTick = maxSamplesPerTick;
            this.misfireThreshold = misfireThreshold;
            this.misfirePostpone = misfirePostpone;
            this.baseInstant = baseInstant;
            this.generator = generator;
            this.producedSamples = 0L;
        }

        public LongIdKey getPointKey() {
            return pointKey;
        }

        public long getTickPeriod() {
            return tickPeriod;
        }

        public long getSampleFrequency() {
            return sampleFrequency;
        }

        public long getMaxSamplesPerTick() {
            return maxSamplesPerTick;
        }

        public long getMisfireThreshold() {
            return misfireThreshold;
        }

        public long getMisfirePostpone() {
            return misfirePostpone;
        }

        public Instant getBaseInstant() {
            return baseInstant;
        }

        public SimulateWaveValueGenerator getGenerator() {
            return generator;
        }

        public long getAndIncrementTickCount() {
            return tickCount.getAndIncrement();
        }

        public long getProducedSamples() {
            return producedSamples;
        }

        public void setProducedSamples(long producedSamples) {
            this.producedSamples = producedSamples;
        }

        @Override
        public String toString() {
            return "WavePointRuntime{" +
                    "pointKey=" + pointKey +
                    ", tickPeriod=" + tickPeriod +
                    ", sampleFrequency=" + sampleFrequency +
                    ", maxSamplesPerTick=" + maxSamplesPerTick +
                    ", misfireThreshold=" + misfireThreshold +
                    ", misfirePostpone=" + misfirePostpone +
                    ", baseInstant=" + baseInstant +
                    ", generator=" + generator +
                    ", tickCount=" + tickCount +
                    ", producedSamples=" + producedSamples +
                    '}';
        }
    }
}
