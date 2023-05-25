package com.dwarfeng.fdr.stack.cache;

import com.dwarfeng.fdr.stack.bean.entity.QuerySupport;
import com.dwarfeng.fdr.stack.bean.key.QuerySupportKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 查询支持缓存。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface QuerySupportCache extends BatchBaseCache<QuerySupportKey, QuerySupport> {
}
