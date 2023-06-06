package com.dwarfeng.fdr.sdk.util;

import java.util.Date;

/**
 * 常量类。
 *
 * @author DwArFeng
 * @since 1.8.2
 */
public final class Constants {

    /**
     * 检查任务的执行间隔。
     */
    public static final long SCHEDULER_CHECK_INTERVAL = 5000L;

    /**
     * 观察操作：缺省起始日期。
     */
    public static final Date WATCH_DEFAULT_START_DATE = new Date(0);

    /**
     * 观察操作：缺省结束日期。
     *
     * <p>
     * 该日期为 4423-01-01 00:00:00.000。
     */
    public static final Date WATCH_DEFAULT_END_DATE = new Date(77409216000000L);

    /**
     * 观察操作：缺省页码。
     */
    public static final int WATCH_DEFAULT_PAGE = 0;

    /**
     * 观察操作：缺省每页的最大行数。
     */
    public static final int WATCH_DEFAULT_ROWS = Integer.MAX_VALUE;

    private Constants() {
        throw new IllegalStateException("禁止实例化");
    }
}
