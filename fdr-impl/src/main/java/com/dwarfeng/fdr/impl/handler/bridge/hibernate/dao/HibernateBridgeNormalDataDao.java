package com.dwarfeng.fdr.impl.handler.bridge.hibernate.dao;

import com.dwarfeng.fdr.impl.handler.bridge.hibernate.bean.HibernateBridgeNormalData;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.BatchWriteDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * Hibernate 桥接器一般数据访问层。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface HibernateBridgeNormalDataDao extends BatchBaseDao<LongIdKey, HibernateBridgeNormalData>,
        EntireLookupDao<HibernateBridgeNormalData>, PresetLookupDao<HibernateBridgeNormalData>,
        BatchWriteDao<HibernateBridgeNormalData> {
}
