package com.dwarfeng.fdr.stack.dao;

import com.dwarfeng.fdr.stack.bean.entity.WasherSupport;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 清洗器支持数据访问层。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface WasherSupportDao extends BatchBaseDao<StringIdKey, WasherSupport>, EntireLookupDao<WasherSupport>,
        PresetLookupDao<WasherSupport> {
}
