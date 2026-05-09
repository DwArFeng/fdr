package com.dwarfeng.fdr.sdk.util;

import com.dwarfeng.dutil.basic.time.TimeUtil;
import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.time.Instant;
import java.util.Objects;

/**
 * 被过滤数据时间工具类。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public final class FilteredDataUtil {

    /**
     * 获取被过滤数据的发生瞬时时间。
     *
     * @param filteredData 被过滤数据。
     * @return 被过滤数据的发生瞬时时间。
     */
    public static Instant getHappenedInstant(FilteredData filteredData) {
        Objects.requireNonNull(filteredData, "filteredData");
        return DataUtil.getHappenedInstant(filteredData);
    }

    /**
     * 设置被过滤数据的发生瞬时时间。
     *
     * @param filteredData 被过滤数据。
     * @param instant      发生瞬时时间。
     */
    public static void setHappenedInstant(FilteredData filteredData, Instant instant) {
        Objects.requireNonNull(filteredData, "filteredData");
        filteredData.setHappenedDate(TimeUtil.toDate(instant));
        filteredData.setHappenedDateNanoOffset(TimeUtil.toNanoOffset(instant));
    }

    /**
     * 根据指定参数构造新的被过滤数据实例。
     *
     * <p>
     * 该方法会根据指定的瞬时时间同步设置发生时间与毫秒内纳秒偏移。
     *
     * @param pointKey        指定的点位主键。
     * @param filterKey       指定的过滤器主键。
     * @param value           指定的值。
     * @param message         指定的过滤信息。
     * @param happenedInstant 指定的发生瞬时时间。
     * @return 新构造的被过滤数据实例。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static FilteredData newInstance(
            LongIdKey pointKey, LongIdKey filterKey, Object value, String message, Instant happenedInstant
    ) {
        Objects.requireNonNull(pointKey, "pointKey 不能为空");
        Objects.requireNonNull(filterKey, "filterKey 不能为空");
        Objects.requireNonNull(happenedInstant, "happenedInstant 不能为空");

        return new FilteredData(
                pointKey, filterKey, value, message,
                TimeUtil.toDate(happenedInstant), TimeUtil.toNanoOffset(happenedInstant)
        );
    }

    private FilteredDataUtil() {
        throw new IllegalStateException("禁止外部实例化");
    }
}
