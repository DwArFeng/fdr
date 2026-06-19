package com.dwarfeng.fdr.stack.dao;

import com.dwarfeng.fdr.stack.bean.entity.FetcherInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 抓取器信息数据访问层。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
public interface FetcherInfoDao extends BatchBaseDao<LongIdKey, FetcherInfo>, EntireLookupDao<FetcherInfo>,
        PresetLookupDao<FetcherInfo> {
}
