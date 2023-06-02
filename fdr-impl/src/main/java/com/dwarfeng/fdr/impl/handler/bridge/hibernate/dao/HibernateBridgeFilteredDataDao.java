package com.dwarfeng.fdr.impl.handler.bridge.hibernate.dao;

import com.dwarfeng.fdr.impl.handler.bridge.hibernate.bean.HibernateBridgeFilteredData;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.BatchWriteDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * Hibernate 桥接器被过滤数据访问层。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface HibernateBridgeFilteredDataDao extends BatchBaseDao<LongIdKey, HibernateBridgeFilteredData>,
        EntireLookupDao<HibernateBridgeFilteredData>, PresetLookupDao<HibernateBridgeFilteredData>,
        BatchWriteDao<HibernateBridgeFilteredData> {
}
