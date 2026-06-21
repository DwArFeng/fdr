package com.dwarfeng.fdr.impl.handler.fetcher.mock.lf;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.support.SimpleTriggerContext;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

/**
 * 低频模拟抓取器 safe 轮询触发器。
 *
 * <p>
 * 该触发器代理原始 Trigger 的下一次触发计算，并在每次调度时比较“当前时间 - 理论触发时间”：
 * 若偏差不超过阈值，按原始 Trigger 行为执行；若偏差超过阈值，则改用“当前时间 + 推迟时间”作为下一次调度基线。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
public final class MockLfSafePollTrigger implements Trigger {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockLfSafePollTrigger.class);

    private final Trigger delegateTrigger;
    private final long misfireThreshold;
    private final long misfirePostpone;

    public MockLfSafePollTrigger(Trigger delegateTrigger, long misfireThreshold, long misfirePostpone) {
        this.delegateTrigger = delegateTrigger;
        this.misfireThreshold = misfireThreshold;
        this.misfirePostpone = misfirePostpone;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public Date nextExecutionTime(@NonNull TriggerContext triggerContext) {
        Date nextExecutionDate = delegateTrigger.nextExecutionTime(triggerContext);
        if (Objects.isNull(nextExecutionDate)) {
            return null;
        }
        long nowTimestamp = System.currentTimeMillis();
        long nextExecutionTimestamp = nextExecutionDate.getTime();
        long lag = nowTimestamp - nextExecutionTimestamp;
        if (lag <= misfireThreshold) {
            return nextExecutionDate;
        }
        long rebasedTimestamp = nowTimestamp + misfirePostpone;
        LOGGER.warn(
                "轮询任务延迟超过阈值, 原计划触发时间戳为 {}, 当前时间戳为 {}, 延迟时长为 {}, 超过阈值 {}, " +
                        "将基于 '当前时间戳 + 推迟时长' 重置调度, 推迟时长为 {}, 重置后的基线时间戳为 {}",
                nextExecutionTimestamp, nowTimestamp, lag, misfireThreshold, misfirePostpone, rebasedTimestamp
        );
        Date rebasedDate = new Date(rebasedTimestamp);
        TriggerContext rebasedTriggerContext = new SimpleTriggerContext(
                Clock.fixed(Instant.ofEpochMilli(rebasedTimestamp), ZoneId.systemDefault())
        );
        Date rebasedNextExecutionDate = delegateTrigger.nextExecutionTime(rebasedTriggerContext);
        if (Objects.nonNull(rebasedNextExecutionDate)) {
            return rebasedNextExecutionDate;
        }
        return rebasedDate;
    }
}
