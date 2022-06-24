package com.dwarfeng.fdr.stack.cache;

import com.dwarfeng.fdr.stack.bean.entity.MapperSupport;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 映射器支持缓存。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public interface MapperSupportCache extends BatchBaseCache<StringIdKey, MapperSupport> {
}
