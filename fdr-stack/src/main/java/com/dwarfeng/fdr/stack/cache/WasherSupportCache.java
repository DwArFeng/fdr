package com.dwarfeng.fdr.stack.cache;

import com.dwarfeng.fdr.stack.bean.entity.WasherSupport;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 清洗器支持缓存。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface WasherSupportCache extends BatchBaseCache<StringIdKey, WasherSupport> {
}
