package com.dwarfeng.fdr.impl.handler.bridge.redis.service;

import com.dwarfeng.fdr.impl.handler.bridge.redis.bean.RedisBridgeNormalData;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;

/**
 * Redis 桥接器一般数据维护服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface RedisBridgeNormalDataMaintainService extends BatchCrudService<LongIdKey, RedisBridgeNormalData> {
}
