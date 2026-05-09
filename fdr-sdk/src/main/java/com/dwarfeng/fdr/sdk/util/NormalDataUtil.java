package com.dwarfeng.fdr.sdk.util;

import com.dwarfeng.dutil.basic.time.TimeUtil;
import com.dwarfeng.fdr.stack.bean.dto.NormalData;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.time.Instant;
import java.util.Objects;

/**
 * 一般数据时间工具类。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public final class NormalDataUtil {

    /**
     * 获取一般数据的发生瞬时时间。
     *
     * @param normalData 一般数据。
     * @return 一般数据的发生瞬时时间。
     */
    public static Instant getHappenedInstant(NormalData normalData) {
        Objects.requireNonNull(normalData, "normalData");
        return DataUtil.getHappenedInstant(normalData);
    }

    /**
     * 设置一般数据的发生瞬时时间。
     *
     * @param normalData 一般数据。
     * @param instant    发生瞬时时间。
     */
    public static void setHappenedInstant(NormalData normalData, Instant instant) {
        Objects.requireNonNull(normalData, "normalData");
        normalData.setHappenedDate(TimeUtil.toDate(instant));
        normalData.setHappenedDateNanoOffset(TimeUtil.toNanoOffset(instant));
    }

    /**
     * 根据指定参数构造新的一般数据实例。
     *
     * <p>
     * 该方法会根据指定的瞬时时间同步设置发生时间与毫秒内纳秒偏移。
     *
     * @param pointKey        指定的点位主键。
     * @param value           指定的值。
     * @param happenedInstant 指定的发生瞬时时间。
     * @return 新构造的一般数据实例。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static NormalData newInstance(LongIdKey pointKey, Object value, Instant happenedInstant) {
        Objects.requireNonNull(pointKey, "pointKey 不能为空");
        Objects.requireNonNull(happenedInstant, "happenedInstant 不能为空");

        return new NormalData(
                pointKey, value, TimeUtil.toDate(happenedInstant), TimeUtil.toNanoOffset(happenedInstant)
        );
    }

    private NormalDataUtil() {
        throw new IllegalStateException("禁止外部实例化");
    }
}
