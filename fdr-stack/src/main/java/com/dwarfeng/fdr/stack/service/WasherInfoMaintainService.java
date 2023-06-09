package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.fdr.stack.bean.entity.WasherInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 清洗器信息维护服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface WasherInfoMaintainService extends BatchCrudService<LongIdKey, WasherInfo>,
        EntireLookupService<WasherInfo>, PresetLookupService<WasherInfo> {

    String CHILD_FOR_POINT = "child_for_point";

    /**
     * @since 2.0.0
     */
    String ENABLED_CHILD_FOR_POINT_INDEX_ASC = "enabled_child_for_point_index_asc";
}
