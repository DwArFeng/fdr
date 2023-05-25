package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.fdr.stack.bean.entity.QuerySupport;
import com.dwarfeng.fdr.stack.bean.key.QuerySupportKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 查询支持维护服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface QuerySupportMaintainService extends BatchCrudService<QuerySupportKey, QuerySupport>,
        EntireLookupService<QuerySupport>, PresetLookupService<QuerySupport> {

    String CATEGORY_EQUALS = "category_equals";
    String PRESET_LIKE = "preset_like";
    String CATEGORY_EQUALS_PRESET_LIKE = "category_equals_preset_like";

    /**
     * 重置触发器支持。
     *
     * @throws ServiceException 服务异常。
     */
    void reset() throws ServiceException;
}
