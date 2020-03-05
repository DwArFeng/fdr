package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.fdr.stack.bean.entity.TriggerSupport;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.service.CrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 触发器支持维护服务。
 *
 * @author DwArFeng
 * @since 0.0.1.a-alpha
 */
public interface TriggerSupportMaintainService extends CrudService<StringIdKey, TriggerSupport>,
        EntireLookupService<TriggerSupport>, PresetLookupService<TriggerSupport> {

    String ID_LIKE = "id_like";
    String LABEL_LIKE = "label_like";
}
