package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.fdr.stack.bean.entity.RealtimeValue;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;

/**
 * 实时数据维护服务。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface RealtimeValueMaintainService extends BatchCrudService<LongIdKey, RealtimeValue>,
        EntireLookupService<RealtimeValue> {
}
