package com.dwarfeng.fdr.sdk.util;

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
     * 查询支持的类别：一般。
     */
    public static final String QUERY_SUPPORT_CATEGORY_NORMAL = "normal";

    /**
     * 查询支持的类别：被过滤。
     */
    public static final String QUERY_SUPPORT_CATEGORY_FILTERED = "filtered";

    /**
     * 查询支持的类别：被触发。
     */
    public static final String QUERY_SUPPORT_CATEGORY_TRIGGERED = "triggered";

    private Constants() {
        throw new IllegalStateException("禁止实例化");
    }
}
