package com.dwarfeng.fdr.stack.dao;

import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 被过滤数据数据访问层。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface FilteredValueDao extends BatchBaseDao<LongIdKey, FilteredValue>, PresetLookupDao<FilteredValue> {
}