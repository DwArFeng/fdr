package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.fdr.stack.bean.entity.WasherSupport;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 清洗器支持维护服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface WasherSupportMaintainService extends BatchCrudService<StringIdKey, WasherSupport>,
        EntireLookupService<WasherSupport>, PresetLookupService<WasherSupport> {

    String ID_LIKE = "id_like";
    String LABEL_LIKE = "label_like";
}
