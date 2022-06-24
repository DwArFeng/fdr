package com.dwarfeng.fdr.stack.dao;

import com.dwarfeng.fdr.stack.bean.entity.TriggerSupport;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 触发器支持数据访问层。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface TriggerSupportDao extends BatchBaseDao<StringIdKey, TriggerSupport>, EntireLookupDao<TriggerSupport>,
        PresetLookupDao<TriggerSupport> {
}
