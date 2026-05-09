package com.dwarfeng.fdr.impl.handler.bridge.influxdb.util;

import java.util.Date;

/**
 * InfluxDB 桥接查询区间工具。
 *
 * <p>
 * 仅保留基于毫秒的时间窗偏移，与 {@code LookupInfo} / {@code NativeQueryInfo} 的边界语义一致。
 * 数据点时间的 Instant 与 Date + 纳秒偏移互转请使用 dutil {@code TimeUtil} 与 fdr-sdk 的 {@code *DataUtil}。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public final class DateUtil {

    /**
     * 将指定的日期偏移指定的毫秒数。
     *
     * @param date   指定的日期。
     * @param offset 指定的毫秒数。
     * @return 偏移后的日期。
     */
    public static Date offsetDate(Date date, long offset) {
        return new Date(date.getTime() + offset);
    }

    private DateUtil() {
        throw new IllegalStateException("禁止外部实例化");
    }
}
