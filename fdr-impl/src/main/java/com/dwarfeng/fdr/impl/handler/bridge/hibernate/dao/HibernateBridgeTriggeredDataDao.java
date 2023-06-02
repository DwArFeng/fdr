package com.dwarfeng.fdr.impl.handler.bridge.hibernate.dao;

import com.dwarfeng.fdr.impl.handler.bridge.hibernate.bean.HibernateBridgeTriggeredData;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.BatchWriteDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * Hibernate 桥接器被触发数据访问层。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface HibernateBridgeTriggeredDataDao extends BatchBaseDao<LongIdKey, HibernateBridgeTriggeredData>,
        EntireLookupDao<HibernateBridgeTriggeredData>, PresetLookupDao<HibernateBridgeTriggeredData>,
        BatchWriteDao<HibernateBridgeTriggeredData> {
}
