package com.dwarfeng.fdr.impl.handler.bridge.redis.service;

import com.dwarfeng.fdr.impl.handler.bridge.redis.bean.RedisBridgeFilteredData;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;

/**
 * Redis 桥接器被过滤数据维护服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface RedisBridgeFilteredDataMaintainService extends BatchCrudService<LongIdKey, RedisBridgeFilteredData> {
}
