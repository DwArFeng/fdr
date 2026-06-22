package com.dwarfeng.fdr.impl.handler.fetcher.simulate.awg;

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
 * 任意波形模拟抓取器 safe tick 触发器。
 *
 * <p>
 * 该触发器代理原始 Trigger 的下一次触发计算，并在每次调度时比较“当前时间 - 理论触发时间”。
 * 当延迟超过阈值时，改用“当前时间 + 推迟时长”作为新的调度基线，以避免任务在调度线程拥堵后发生堆积补偿。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
public final class SimulateAwgSafeTickTrigger implements Trigger {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimulateAwgSafeTickTrigger.class);

    private final Trigger delegateTrigger;
    private final long misfireThreshold;
    private final long misfirePostpone;

    public SimulateAwgSafeTickTrigger(Trigger delegateTrigger, long misfireThreshold, long misfirePostpone) {
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
                "任意波形模拟任务延迟超过阈值, 原计划触发时间戳为 {}, 当前时间戳为 {}, 延迟时长为 {}, 超过阈值 {}, " +
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
