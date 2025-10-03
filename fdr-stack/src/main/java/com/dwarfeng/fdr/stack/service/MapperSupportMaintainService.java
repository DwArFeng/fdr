package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.fdr.stack.bean.entity.MapperSupport;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 映射器支持维护服务。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public interface MapperSupportMaintainService extends BatchCrudService<StringIdKey, MapperSupport>,
        EntireLookupService<MapperSupport>, PresetLookupService<MapperSupport> {

    String ID_LIKE = "id_like";
    String LABEL_LIKE = "label_like";
}
