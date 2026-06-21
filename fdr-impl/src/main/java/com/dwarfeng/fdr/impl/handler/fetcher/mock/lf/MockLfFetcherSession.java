package com.dwarfeng.fdr.impl.handler.fetcher.mock.lf;

import com.dwarfeng.fdr.sdk.handler.fetcher.AbstractFetcherSession;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 低频模拟抓取器会话。
 *
 * <p>
 * 该会话负责按照配置创建低频调度任务，并在停止或关闭时取消调度资源。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MockLfFetcherSession extends AbstractFetcherSession {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockLfFetcherSession.class);

    private final ApplicationContext ctx;

    private final MockLfFetcherConfig config;

    private final ThreadPoolTaskScheduler scheduler;

    private final Lock lock = new ReentrantLock();

    private MockLfFetcherFetchInfo mockLfFetcherFetchInfo;
    private ScheduledFuture<?> scheduledFuture;

    public MockLfFetcherSession(ApplicationContext ctx, MockLfFetcherConfig config, ThreadPoolTaskScheduler scheduler) {
        this.ctx = ctx;
        this.config = config;
        this.scheduler = scheduler;
    }

    @Override
    protected void doOpenSession() {
        LOGGER.info("低频模拟点位 {} 会话打开...", config.getPointKey());
        lock.lock();
        try {
            mockLfFetcherFetchInfo = parseFetchInfo();
        } finally {
            lock.unlock();
        }
    }

    private MockLfFetcherFetchInfo parseFetchInfo() {
        LongIdKey pointKey = FastJsonLongIdKey.toStackBean(config.getPointKey());
        String pollType = config.getPollType();
        String pollSetting = config.getPollSetting();
        String generatorType = config.getGeneratorType();
        Long randomSeed = config.getRandomSeed();
        Long fetchBeforeDelay = config.getFetchBeforeDelay();
        Long fetchAfterDelay = config.getFetchAfterDelay();

        Random random = Objects.isNull(randomSeed) ? new Random() : new Random(randomSeed);
        MockLfFetcherValueGenerator valueGenerator = ctx.getBean(
                MockLfFetcherValueGenerator.class, random, generatorType
        );
        MockLfFetcherFetchTask fetchTask = ctx.getBean(
                MockLfFetcherFetchTask.class, context, valueGenerator, pointKey, fetchBeforeDelay, fetchAfterDelay
        );
        return new MockLfFetcherFetchInfo(fetchTask, pollType, pollSetting);
    }

    @Override
    protected void doStartFetch() {
        LOGGER.info("低频模拟点位 {} 开始抓取...", config.getPointKey());
        lock.lock();
        try {
            doStartFetch0();
        } finally {
            lock.unlock();
        }
    }

    private void doStartFetch0() {
        // 展开参数。
        MockLfFetcherFetchTask fetchTask = mockLfFetcherFetchInfo.getFetchTask();
        String pollType = mockLfFetcherFetchInfo.getPollType();
        String pollSetting = mockLfFetcherFetchInfo.getPollSetting();

        // 创建定时任务，并赋值到 scheduledFuture。
        long delay;
        long rate;
        String cron;
        String[] safePollSettingPatterns;
        long misfireThreshold;
        long misfirePostpone;
        Trigger delegateTrigger;
        switch (pollType) {
            case MockLfFetcherConstants.POLL_TYPE_FIXED_DELAY:
                delay = Long.parseLong(pollSetting);
                scheduledFuture = scheduler.scheduleWithFixedDelay(fetchTask, delay);
                break;
            case MockLfFetcherConstants.POLL_TYPE_FIXED_RATE:
                rate = Long.parseLong(pollSetting);
                scheduledFuture = scheduler.scheduleAtFixedRate(fetchTask, rate);
                break;
            case MockLfFetcherConstants.POLL_TYPE_CRON:
                cron = pollSetting;
                scheduledFuture = scheduler.schedule(fetchTask, new CronTrigger(cron));
                break;
            case MockLfFetcherConstants.POLL_TYPE_SAFE_FIXED_DELAY:
                safePollSettingPatterns = parseSafePollSettingPatterns(pollSetting);
                delay = Long.parseLong(safePollSettingPatterns[0]);
                misfireThreshold = Long.parseLong(safePollSettingPatterns[1]);
                misfirePostpone = Long.parseLong(safePollSettingPatterns[2]);
                delegateTrigger = new PeriodicTrigger(delay);
                ((PeriodicTrigger) delegateTrigger).setFixedRate(false);
                scheduledFuture = scheduler.schedule(
                        fetchTask, new MockLfSafePollTrigger(delegateTrigger, misfireThreshold, misfirePostpone)
                );
                break;
            case MockLfFetcherConstants.POLL_TYPE_SAFE_FIXED_RATE:
                safePollSettingPatterns = parseSafePollSettingPatterns(pollSetting);
                rate = Long.parseLong(safePollSettingPatterns[0]);
                misfireThreshold = Long.parseLong(safePollSettingPatterns[1]);
                misfirePostpone = Long.parseLong(safePollSettingPatterns[2]);
                delegateTrigger = new PeriodicTrigger(rate);
                ((PeriodicTrigger) delegateTrigger).setFixedRate(true);
                scheduledFuture = scheduler.schedule(
                        fetchTask, new MockLfSafePollTrigger(delegateTrigger, misfireThreshold, misfirePostpone)
                );
                break;
            case MockLfFetcherConstants.POLL_TYPE_SAFE_CRON:
                safePollSettingPatterns = parseSafePollSettingPatterns(pollSetting);
                cron = safePollSettingPatterns[0];
                misfireThreshold = Long.parseLong(safePollSettingPatterns[1]);
                misfirePostpone = Long.parseLong(safePollSettingPatterns[2]);
                delegateTrigger = new CronTrigger(cron);
                scheduledFuture = scheduler.schedule(
                        fetchTask, new MockLfSafePollTrigger(delegateTrigger, misfireThreshold, misfirePostpone)
                );
                break;
            default:
                throw new IllegalArgumentException("Unsupported poll type: " + pollType);
        }
    }

    /**
     * 解析 safe 轮询参数。
     *
     * @param pollSetting 轮询参数。
     * @return 参数三段式数组。
     */
    private String[] parseSafePollSettingPatterns(String pollSetting) {
        if (Objects.isNull(pollSetting)) {
            throw new IllegalArgumentException("safe 轮询参数为 null");
        }
        String[] patterns = pollSetting.split(
                String.valueOf(MockLfFetcherConstants.SAFE_POLL_SETTING_SEPARATOR_CHAR), -1
        );
        if (patterns.length != 3) {
            throw new IllegalArgumentException("safe 轮询参数必须为三段式: 基础调度参数;misfireThreshold;misfirePostpone");
        }
        return patterns;
    }

    @Override
    protected void doStopFetch() {
        LOGGER.info("低频模拟点位 {} 停止抓取...", config.getPointKey());
        lock.lock();
        try {
            cancelAndResetScheduledFuture();
        } finally {
            lock.unlock();
        }
    }

    @Override
    protected void doCloseSession() {
        LOGGER.info("低频模拟点位 {} 会话关闭...", config.getPointKey());
        lock.lock();
        try {
            cancelAndResetScheduledFuture();
            mockLfFetcherFetchInfo = null;
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
        return "MockLfFetcherSession{" +
                "ctx=" + ctx +
                ", config=" + config +
                ", scheduler=" + scheduler +
                ", lock=" + lock +
                ", mockLfFetcherFetchInfo=" + mockLfFetcherFetchInfo +
                ", scheduledFuture=" + scheduledFuture +
                ", context=" + context +
                '}';
    }

    private static final class MockLfFetcherFetchInfo {

        private final MockLfFetcherFetchTask fetchTask;
        private final String pollType;
        private final String pollSetting;

        public MockLfFetcherFetchInfo(
                MockLfFetcherFetchTask fetchTask, String pollType, String pollSetting
        ) {
            this.fetchTask = fetchTask;
            this.pollType = pollType;
            this.pollSetting = pollSetting;
        }

        public MockLfFetcherFetchTask getFetchTask() {
            return fetchTask;
        }

        public String getPollType() {
            return pollType;
        }

        public String getPollSetting() {
            return pollSetting;
        }

        @Override
        public String toString() {
            return "MockLfFetcherFetchInfo{" +
                    "fetchTask=" + fetchTask +
                    ", pollType='" + pollType + '\'' +
                    ", pollSetting='" + pollSetting + '\'' +
                    '}';
        }
    }
}
