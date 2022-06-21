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
     * 主键索引：point_id。
     */
    public static final String INDEX_NAME_POINT_ID = "idx_point_id";
    /**
     * 主键索引：point_id, happened_date。
     */
    public static final String INDEX_NAME_POINT_ID_HAPPENED_DATE = "idx_point_id_happened_date";
    /**
     * 主键索引：point_id, happened_date DESC。
     */
    public static final String INDEX_NAME_POINT_ID_HAPPENED_DATE_DESC = "idx_point_id_happened_date_desc";
    /**
     * 主键索引：trigger_id。
     */
    public static final String INDEX_NAME_TRIGGER_ID = "idx_trigger_id";
    /**
     * 主键索引：trigger_id, happened_date。
     */
    public static final String INDEX_NAME_TRIGGER_ID_HAPPENED_DATE = "idx_trigger_id_happened_date";
    /**
     * 主键索引：trigger_id, happened_date DESC。
     */
    public static final String INDEX_NAME_TRIGGER_ID_HAPPENED_DATE_DESC = "idx_trigger_id_happened_date_desc";
    /**
     * 主键索引：filter_id。
     */
    public static final String INDEX_NAME_FILTER_ID = "idx_filter_id";
    /**
     * 主键索引：filter_id, happened_date。
     */
    public static final String INDEX_NAME_FILTER_ID_HAPPENED_DATE = "idx_filter_id_happened_date";
    /**
     * 主键索引：filter_id, happened_date DESC。
     */
    public static final String INDEX_NAME_FILTER_ID_HAPPENED_DATE_DESC = "idx_filter_id_happened_date_desc";

    private Constants() {
        throw new IllegalStateException("禁止实例化");
    }
}
