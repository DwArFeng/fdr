package com.dwarfeng.fdr.impl.handler.bridge.redis.dao;

import com.dwarfeng.fdr.impl.handler.bridge.redis.bean.RedisBridgeFilteredData;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;

/**
 * Redis 桥接器被过滤数据访问层。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface RedisBridgeFilteredDataDao extends BatchBaseDao<LongIdKey, RedisBridgeFilteredData> {
}
