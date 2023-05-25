package com.dwarfeng.fdr.stack.dao;

import com.dwarfeng.fdr.stack.bean.entity.QuerySupport;
import com.dwarfeng.fdr.stack.bean.key.QuerySupportKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 查询支持数据访问层。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface QuerySupportDao extends BatchBaseDao<QuerySupportKey, QuerySupport>, EntireLookupDao<QuerySupport>,
        PresetLookupDao<QuerySupport> {
}
