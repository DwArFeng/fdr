package com.dwarfeng.fdr.stack.dao;

import com.dwarfeng.fdr.stack.bean.entity.LookupSupport;
import com.dwarfeng.fdr.stack.bean.key.LookupSupportKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 查看支持数据访问层。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface LookupSupportDao extends BatchBaseDao<LookupSupportKey, LookupSupport>, EntireLookupDao<LookupSupport>,
        PresetLookupDao<LookupSupport> {
}
