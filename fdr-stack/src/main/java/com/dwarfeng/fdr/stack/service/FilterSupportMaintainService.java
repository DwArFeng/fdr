package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.fdr.stack.bean.entity.FilterSupport;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 过滤器支持维护服务。
 *
 * @author DwArFeng
 * @since 0.0.1.a-alpha
 */
public interface FilterSupportMaintainService extends BatchCrudService<StringIdKey, FilterSupport>,
        EntireLookupService<FilterSupport>, PresetLookupService<FilterSupport> {

    String ID_LIKE = "id_like";
    String LABEL_LIKE = "label_like";
}
