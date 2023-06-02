package com.dwarfeng.fdr.impl.handler.bridge.redis.dao;

import com.dwarfeng.fdr.impl.handler.bridge.redis.bean.RedisBridgeTriggeredData;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;

/**
 * Redis 桥接器被触发数据访问层。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface RedisBridgeTriggeredDataDao extends BatchBaseDao<LongIdKey, RedisBridgeTriggeredData> {
}
