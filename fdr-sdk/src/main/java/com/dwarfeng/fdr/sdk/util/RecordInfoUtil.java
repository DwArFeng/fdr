package com.dwarfeng.fdr.sdk.util;

import com.dwarfeng.dutil.basic.time.TimeUtil;
import com.dwarfeng.fdr.stack.bean.dto.RecordInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.time.Instant;
import java.util.Objects;

/**
 * 记录信息时间工具类。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public final class RecordInfoUtil {

    /**
     * 获取记录信息的发生瞬时时间。
     *
     * @param recordInfo 记录信息。
     * @return 记录信息的发生瞬时时间。
     */
    public static Instant getHappenedInstant(RecordInfo recordInfo) {
        Objects.requireNonNull(recordInfo, "recordInfo");
        return TimeUtil.toInstant(recordInfo.getHappenedDate(), recordInfo.getHappenedDateNanoOffset());
    }

    /**
     * 设置记录信息的发生瞬时时间。
     *
     * @param recordInfo 记录信息。
     * @param instant    发生瞬时时间。
     */
    public static void setHappenedInstant(RecordInfo recordInfo, Instant instant) {
        Objects.requireNonNull(recordInfo, "recordInfo");
        recordInfo.setHappenedDate(TimeUtil.toDate(instant));
        recordInfo.setHappenedDateNanoOffset(TimeUtil.toNanoOffset(instant));
    }

    /**
     * 根据指定参数构造新的记录信息实例。
     *
     * <p>
     * 该方法会根据指定的瞬时时间同步设置发生时间与毫秒内纳秒偏移。
     *
     * @param pointKey        指定的点位主键。
     * @param value           指定的值。
     * @param happenedInstant 指定的发生瞬时时间。
     * @return 新构造的记录信息实例。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static RecordInfo newInstance(LongIdKey pointKey, Object value, Instant happenedInstant) {
        Objects.requireNonNull(pointKey, "pointKey 不能为空");
        Objects.requireNonNull(happenedInstant, "happenedInstant 不能为空");

        return new RecordInfo(
                pointKey, value, TimeUtil.toDate(happenedInstant), TimeUtil.toNanoOffset(happenedInstant)
        );
    }

    private RecordInfoUtil() {
        throw new IllegalStateException("禁止外部实例化");
    }
}
