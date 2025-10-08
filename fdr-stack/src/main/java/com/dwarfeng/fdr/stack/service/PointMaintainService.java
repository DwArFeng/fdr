package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 数据点维护服务。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface PointMaintainService extends BatchCrudService<LongIdKey, Point>, EntireLookupService<Point>,
        PresetLookupService<Point> {

    String NAME_LIKE = "name_like";

    /**
     * @since 1.9.6
     */
    String REMARK_LIKE = "remark_like";

    /**
     * @since 2.3.1
     */
    String NORMAL_KEEP_ENABLED_EQ = "normal_keep_enabled_eq";

    /**
     * @since 2.3.1
     */
    String NORMAL_PERSIST_ENABLED_EQ = "normal_persist_enabled_eq";

    /**
     * @since 2.3.1
     */
    String FILTERED_KEEP_ENABLED_EQ = "filtered_keep_enabled_eq";

    /**
     * @since 2.3.1
     */
    String FILTERED_PERSIST_ENABLED_EQ = "filtered_persist_enabled_eq";

    /**
     * @since 2.3.1
     */
    String TRIGGERED_KEEP_ENABLED_EQ = "triggered_keep_enabled_eq";

    /**
     * @since 2.3.1
     */
    String TRIGGERED_PERSIST_ENABLED_EQ = "triggered_persist_enabled_eq";
}
