package com.dwarfeng.fdr.impl.handler.bridge.hibernate.service;

import com.dwarfeng.fdr.impl.handler.bridge.hibernate.bean.HibernateBridgeNormalData;

/**
 * Hibernate 桥接器一般数据维护服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface HibernateBridgeNormalDataMaintainService extends
        HibernateBridgeMaintainService<HibernateBridgeNormalData> {

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
