package com.dwarfeng.fdr.stack.cache;

import com.dwarfeng.fdr.stack.bean.entity.FetcherSupport;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 抓取器支持缓存。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
public interface FetcherSupportCache extends BatchBaseCache<StringIdKey, FetcherSupport> {
}
