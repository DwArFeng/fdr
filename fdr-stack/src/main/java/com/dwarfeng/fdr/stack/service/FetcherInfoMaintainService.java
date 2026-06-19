package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.fdr.stack.bean.entity.FetcherInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 抓取器信息维护服务。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
public interface FetcherInfoMaintainService extends BatchCrudService<LongIdKey, FetcherInfo>,
        EntireLookupService<FetcherInfo>, PresetLookupService<FetcherInfo> {

    String ENABLED = "enabled";
}
