package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.fdr.stack.bean.entity.FetcherSupport;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 抓取器支持维护服务。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
public interface FetcherSupportMaintainService extends BatchCrudService<StringIdKey, FetcherSupport>,
        EntireLookupService<FetcherSupport>, PresetLookupService<FetcherSupport> {

    String ID_LIKE = "id_like";
    String LABEL_LIKE = "label_like";
}
