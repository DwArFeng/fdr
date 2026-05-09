package com.dwarfeng.fdr.sdk.util;

import com.dwarfeng.dutil.basic.time.TimeUtil;
import com.dwarfeng.fdr.stack.bean.dto.TriggeredData;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.time.Instant;
import java.util.Objects;

/**
 * 被触发数据时间工具类。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public final class TriggeredDataUtil {

    /**
     * 获取被触发数据的发生瞬时时间。
     *
     * @param triggeredData 被触发数据。
     * @return 被触发数据的发生瞬时时间。
     */
    public static Instant getHappenedInstant(TriggeredData triggeredData) {
        Objects.requireNonNull(triggeredData, "triggeredData");
        return DataUtil.getHappenedInstant(triggeredData);
    }

    /**
     * 设置被触发数据的发生瞬时时间。
     *
     * @param triggeredData 被触发数据。
     * @param instant       发生瞬时时间。
     */
    public static void setHappenedInstant(TriggeredData triggeredData, Instant instant) {
        Objects.requireNonNull(triggeredData, "triggeredData");
        triggeredData.setHappenedDate(TimeUtil.toDate(instant));
        triggeredData.setHappenedDateNanoOffset(TimeUtil.toNanoOffset(instant));
    }

    /**
     * 根据指定参数构造新的被触发数据实例。
     *
     * <p>
     * 该方法会根据指定的瞬时时间同步设置发生时间与毫秒内纳秒偏移。
     *
     * @param pointKey        指定的点位主键。
     * @param triggerKey      指定的触发器主键。
     * @param value           指定的值。
     * @param message         指定的触发信息。
     * @param happenedInstant 指定的发生瞬时时间。
     * @return 新构造的被触发数据实例。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static TriggeredData newInstance(
            LongIdKey pointKey, LongIdKey triggerKey, Object value, String message, Instant happenedInstant
    ) {
        Objects.requireNonNull(pointKey, "pointKey 不能为空");
        Objects.requireNonNull(triggerKey, "triggerKey 不能为空");
        Objects.requireNonNull(happenedInstant, "happenedInstant 不能为空");

        return new TriggeredData(
                pointKey, triggerKey, value, message,
                TimeUtil.toDate(happenedInstant), TimeUtil.toNanoOffset(happenedInstant)
        );
    }

    private TriggeredDataUtil() {
        throw new IllegalStateException("禁止外部实例化");
    }
}
