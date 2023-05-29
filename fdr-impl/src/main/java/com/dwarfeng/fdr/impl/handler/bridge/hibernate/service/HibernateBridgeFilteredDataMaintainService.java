package com.dwarfeng.fdr.impl.handler.bridge.hibernate.service;

import com.dwarfeng.fdr.impl.handler.bridge.hibernate.bean.entity.HibernateBridgeFilteredData;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.BatchWriteService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * Hibernate 桥接器被过滤数据维护服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface HibernateBridgeFilteredDataMaintainService extends
        BatchCrudService<LongIdKey, HibernateBridgeFilteredData>, EntireLookupService<HibernateBridgeFilteredData>,
        PresetLookupService<HibernateBridgeFilteredData>, BatchWriteService<HibernateBridgeFilteredData> {

    /**
     * 数据点位为指定值，且发生时间在起始时间和结束时间之间，起始时间闭合，结束时间闭合。
     */
    String CHILD_FOR_POINT_BETWEEN_CLOSE_CLOSE = "child_for_point_between_close_close";

    /**
     * 数据点位为指定值，且发生时间在起始时间和结束时间之间，起始时间闭合，结束时间开放。
     */
    String CHILD_FOR_POINT_BETWEEN_CLOSE_OPEN = "child_for_point_between_close_open";

    /**
     * 数据点位为指定值，且发生时间在起始时间和结束时间之间，起始时间开放，结束时间闭合。
     */
    String CHILD_FOR_POINT_BETWEEN_OPEN_CLOSE = "child_for_point_between_open_close";

    /**
     * 数据点位为指定值，且发生时间在起始时间和结束时间之间，起始时间开放，结束时间开放。
     */
    String CHILD_FOR_POINT_BETWEEN_OPEN_OPEN = "child_for_point_between_open_open";
}
