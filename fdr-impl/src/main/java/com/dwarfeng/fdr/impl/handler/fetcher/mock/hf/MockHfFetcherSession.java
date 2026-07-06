package com.dwarfeng.fdr.impl.handler.fetcher.mock.hf;

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
import java.util.Random;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 高频模拟抓取器会话。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MockHfFetcherSession extends AbstractFetcherSession {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockHfFetcherSession.class);

    private static final long NANOS_PER_MILLI = 1_000_000L;

    private final ApplicationContext ctx;

    private final MockHfFetcherConfig config;

    private final ThreadPoolTaskScheduler scheduler;

    private final Lock lock = new ReentrantLock();

    private HfPointRuntime hfPointRuntime;
    private ScheduledFuture<?> scheduledFuture;

    public MockHfFetcherSession(ApplicationContext ctx, MockHfFetcherConfig config, ThreadPoolTaskScheduler scheduler) {
        this.ctx = ctx;
        this.config = config;
        this.scheduler = scheduler;
    }

    @Override
    protected void doOpenSession() {
        LOGGER.info("高频模拟点位 {} 会话打开...", config.getPointKey());
        lock.lock();
        try {
            hfPointRuntime = parseRuntime();
        } finally {
            lock.unlock();
        }
    }

    private HfPointRuntime parseRuntime() {
        LongIdKey pointKey = FastJsonLongIdKey.toStackBean(config.getPointKey());
        long tickPeriod = config.getTickPeriod();
        long frequency = config.getFrequency();
        long maxSamplesPerTick = config.getMaxSamplesPerTick();
        long misfireThreshold = config.getMisfireThreshold();
        long misfirePostpone = config.getMisfirePostpone();
        String generatorType = config.getGeneratorType();
        Long randomSeed = config.getRandomSeed();

        Random random = Objects.isNull(randomSeed) ? new Random() : new Random(randomSeed);
        MockHfFetcherValueGenerator generator = ctx.getBean(MockHfFetcherValueGenerator.class, random, generatorType);
        Instant baseInstant = Instant.now();
        return new HfPointRuntime(
                pointKey, tickPeriod, frequency, maxSamplesPerTick, misfireThreshold, misfirePostpone, baseInstant,
                generator
        );
    }

    @Override
    protected void doStartFetch() {
        LOGGER.info("高频模拟点位 {} 开始抓取...", config.getPointKey());
        lock.lock();
        try {
            PeriodicTrigger delegateTrigger = new PeriodicTrigger(config.getTickPeriod());
            delegateTrigger.setFixedRate(true);
            Trigger safeTickTrigger = new MockHfSafeTickTrigger(
                    delegateTrigger, config.getMisfireThreshold(), config.getMisfirePostpone()
            );
            scheduledFuture = scheduler.schedule(this::tick, safeTickTrigger);
        } finally {
            lock.unlock();
        }
    }

    private void tick() {
        long start = System.currentTimeMillis();
        try {
            long tickIndex = hfPointRuntime.getAndIncrementTickCount();
            long tickEndNanos = (tickIndex + 1L) * hfPointRuntime.getTickPeriod() * NANOS_PER_MILLI;
            long shouldProduced = tickIndexToSampleIndex(hfPointRuntime.getFrequency(), tickEndNanos);
            long toGenerate = shouldProduced - hfPointRuntime.getProducedSamples();

            if (toGenerate <= 0) {
                return;
            }
            if (toGenerate > hfPointRuntime.getMaxSamplesPerTick()) {
                LOGGER.warn(
                        "高频模拟点位 {} 单 tick 样本数 {} 超过阈值 {}, 丢弃本 tick",
                        hfPointRuntime.getPointKey(), toGenerate, hfPointRuntime.getMaxSamplesPerTick()
                );
                return;
            }

            for (long i = 0; i < toGenerate; i++) {
                long sampleIndex = hfPointRuntime.getProducedSamples() + i;
                Instant happenedInstant = computeInstant(
                        hfPointRuntime.getBaseInstant(), sampleIndex, hfPointRuntime.getFrequency()
                );
                RecordInfo recordInfo = RecordInfoUtil.newInstance(
                        hfPointRuntime.getPointKey(), hfPointRuntime.getGenerator().generateValue(), happenedInstant
                );
                context.record(recordInfo);
            }
            hfPointRuntime.increaseProducedSamples(toGenerate);

            long cost = System.currentTimeMillis() - start;
            if (cost > Math.max(1L, hfPointRuntime.getTickPeriod())) {
                LOGGER.warn(
                        "高频模拟点位 {} 补样耗时 {}ms 超过 tick 周期 {}ms",
                        hfPointRuntime.getPointKey(), cost, hfPointRuntime.getTickPeriod()
                );
            }
        } catch (Exception e) {
            LOGGER.warn("高频模拟点位 {} 补样失败", hfPointRuntime.getPointKey(), e);
        }
    }

    private long tickIndexToSampleIndex(long frequency, long elapsedNanosSinceBase) {
        return elapsedNanosSinceBase * frequency / 1_000_000_000L;
    }

    private Instant computeInstant(Instant baseInstant, long sampleIndex, long frequency) {
        long seconds = sampleIndex / frequency;
        long nanos = (sampleIndex % frequency) * 1_000_000_000L / frequency;
        return baseInstant.plusSeconds(seconds).plusNanos(nanos);
    }

    @Override
    protected void doStopFetch() {
        LOGGER.info("高频模拟点位 {} 停止抓取...", config.getPointKey());
        lock.lock();
        try {
            cancelAndResetScheduledFuture();
        } finally {
            lock.unlock();
        }
    }

    @Override
    protected void doCloseSession() {
        LOGGER.info("高频模拟点位 {} 会话关闭...", config.getPointKey());
        lock.lock();
        try {
            cancelAndResetScheduledFuture();
            hfPointRuntime = null;
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
        return "MockHfFetcherSession{" +
                "ctx=" + ctx +
                ", config=" + config +
                ", scheduler=" + scheduler +
                ", lock=" + lock +
                ", hfPointRuntime=" + hfPointRuntime +
                ", scheduledFuture=" + scheduledFuture +
                ", context=" + context +
                '}';
    }

    private static final class HfPointRuntime {

        private final LongIdKey pointKey;
        private final long tickPeriod;
        private final long frequency;
        private final long maxSamplesPerTick;
        private final long misfireThreshold;
        private final long misfirePostpone;
        private final Instant baseInstant;
        private final MockHfFetcherValueGenerator generator;
        private final AtomicLong tickCount = new AtomicLong(0);
        private long producedSamples;

        private HfPointRuntime(
                LongIdKey pointKey, long tickPeriod, long frequency, long maxSamplesPerTick, long misfireThreshold,
                long misfirePostpone, Instant baseInstant, MockHfFetcherValueGenerator generator
        ) {
            this.pointKey = pointKey;
            this.tickPeriod = tickPeriod;
            this.frequency = frequency;
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

        public long getFrequency() {
            return frequency;
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

        public MockHfFetcherValueGenerator getGenerator() {
            return generator;
        }

        public long getAndIncrementTickCount() {
            return tickCount.getAndIncrement();
        }

        public long getProducedSamples() {
            return producedSamples;
        }

        public void increaseProducedSamples(long delta) {
            producedSamples += delta;
        }

        @Override
        public String toString() {
            return "HfPointRuntime{" +
                    "pointKey=" + pointKey +
                    ", tickPeriod=" + tickPeriod +
                    ", frequency=" + frequency +
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
