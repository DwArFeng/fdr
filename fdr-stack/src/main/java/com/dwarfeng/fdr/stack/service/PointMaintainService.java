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
}
