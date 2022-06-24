package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.fdr.stack.bean.entity.TriggerSupport;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 触发器支持维护服务。
 *
 * @author DwArFeng
 * @since 0.0.1.a-alpha
 */
public interface TriggerSupportMaintainService extends BatchCrudService<StringIdKey, TriggerSupport>,
        EntireLookupService<TriggerSupport>, PresetLookupService<TriggerSupport> {

    String ID_LIKE = "id_like";
    String LABEL_LIKE = "label_like";

    /**
     * 重置触发器支持。
     *
     * @throws ServiceException 服务异常。
     */
    void reset() throws ServiceException;
}
