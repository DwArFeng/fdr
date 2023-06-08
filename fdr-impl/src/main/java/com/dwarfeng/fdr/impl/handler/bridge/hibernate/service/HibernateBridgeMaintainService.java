package com.dwarfeng.fdr.impl.handler.bridge.hibernate.service;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.BatchWriteService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * Hibernate 桥接器维护服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface HibernateBridgeMaintainService<T extends Entity<LongIdKey>> extends BatchCrudService<LongIdKey, T>,
        EntireLookupService<T>, PresetLookupService<T>, BatchWriteService<T> {
}
