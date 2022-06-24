package com.dwarfeng.fdr.stack.cache;

import com.dwarfeng.fdr.stack.bean.entity.TriggerSupport;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 触发器支持缓存。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface TriggerSupportCache extends BatchBaseCache<StringIdKey, TriggerSupport> {
}
