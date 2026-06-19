package com.dwarfeng.fdr.stack.cache;

import com.dwarfeng.fdr.stack.bean.entity.FetcherInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 抓取器信息缓存。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
public interface FetcherInfoCache extends BatchBaseCache<LongIdKey, FetcherInfo> {
}
