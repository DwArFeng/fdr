package com.dwarfeng.fdr.impl.handler.source.mock.historical;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.support.SimpleTriggerContext;

import javax.annotation.Nonnull;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

/**
 * 历史模拟数据源安全触发器。
 *
 * <p>
 * 该触发器包装基础触发器，并在每次计算下一次触发时间时检测任务调度延迟。<br>
 * 当当前时间与理论触发时间的差值超过阈值时，会将调度基线重置为“当前时间 + 推迟时长”，
 * 从而避免调度线程在拥塞后持续进行追赶式触发。
 *
 * @author DwArFeng
 * @since 3.0.1
 */
public final class HistoricalMockSourceSafeTrigger implements Trigger {

    private static final Logger LOGGER = LoggerFactory.getLogger(HistoricalMockSourceSafeTrigger.class);

    private final Trigger delegateTrigger;
    private final long misfireThreshold;
    private final long misfirePostpone;

    public HistoricalMockSourceSafeTrigger(Trigger delegateTrigger, long misfireThreshold, long misfirePostpone) {
        if (Objects.isNull(delegateTrigger)) {
            throw new IllegalArgumentException("delegateTrigger 不能为 null");
        }
        if (misfireThreshold <= 0) {
            throw new IllegalArgumentException("misfireThreshold 必须大于 0");
        }
        if (misfirePostpone <= 0) {
            throw new IllegalArgumentException("misfirePostpone 必须大于 0");
        }
        this.delegateTrigger = delegateTrigger;
        this.misfireThreshold = misfireThreshold;
        this.misfirePostpone = misfirePostpone;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public Date nextExecutionTime(@Nonnull TriggerContext triggerContext) {
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
                "历史模拟任务延迟超过阈值，原计划触发时间戳为 {}，当前时间戳为 {}，延迟时长为 {}，超过阈值 {}，" +
                        "将基于 '当前时间戳 + 推迟时长' 重置调度，推迟时长为 {}，重置后的基线时间戳为 {}",
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
