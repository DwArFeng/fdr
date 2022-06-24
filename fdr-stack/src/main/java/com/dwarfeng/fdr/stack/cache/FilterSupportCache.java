package com.dwarfeng.fdr.stack.cache;

import com.dwarfeng.fdr.stack.bean.entity.FilterSupport;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 过滤器支持缓存。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface FilterSupportCache extends BatchBaseCache<StringIdKey, FilterSupport> {
}
