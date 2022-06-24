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
     * @since 1.9.6
     */
    String NAME_LIKE_PERSISTENCE_ENABLED = "name_like_persistence_enabled";
    /**
     * @since 1.9.6
     */
    String NAME_LIKE_REALTIME_ENABLED = "name_like_realtime_enabled";
    /**
     * @since 1.9.6
     */
    String NAME_LIKE_PERSISTENCE_DISABLED = "name_like_persistence_disabled";
    /**
     * @since 1.9.6
     */
    String NAME_LIKE_REALTIME_DISABLED = "name_like_realtime_disabled";
}
