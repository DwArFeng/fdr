package com.dwarfeng.fdr.impl.handler.source.mock.historical;

import java.text.SimpleDateFormat;

/**
 * 时间工具类。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
final class TimeUtil {

    /**
     * 将日期字符串转换为时间戳。
     *
     * @param dateString 日期字符串，格式为 yyyy-MM-dd HH:mm:ss.SSS
     * @return 日期字符串对应的时间戳。
     */
    public static long dateString2Timestamp(String dateString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        try {
            return simpleDateFormat.parse(dateString).getTime();
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的日期字符串: " + dateString, e);
        }
    }

    private TimeUtil() {
        throw new IllegalStateException("禁止外部实例化");
    }
}
