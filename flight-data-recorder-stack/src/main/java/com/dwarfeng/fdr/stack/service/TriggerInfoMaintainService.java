package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.fdr.stack.bean.entity.TriggerInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.CrudService;
import com.dwarfeng.subgrade.stack.service.PresetDeleteService;

/**
 * 过滤器信息维护服务。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface TriggerInfoMaintainService extends CrudService<LongIdKey, TriggerInfo>, PresetDeleteService<TriggerInfo> {

    String CHILD_FOR_POINT = "child_for_point";
    String CHILD_FOR_POINT_SET = "child_for_point_set";
    String ENABLED_CHILD_FOR_POINT = "enabled_child_for_point";
}
