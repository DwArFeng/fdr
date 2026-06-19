package com.dwarfeng.fdr.stack.dao;

import com.dwarfeng.fdr.stack.bean.entity.FetcherSupport;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 抓取器支持数据访问层。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
public interface FetcherSupportDao extends BatchBaseDao<StringIdKey, FetcherSupport>, EntireLookupDao<FetcherSupport>,
        PresetLookupDao<FetcherSupport> {
}
