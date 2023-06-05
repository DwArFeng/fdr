package com.dwarfeng.fdr.stack.cache;

import com.dwarfeng.fdr.stack.bean.entity.LookupSupport;
import com.dwarfeng.fdr.stack.bean.key.LookupSupportKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 查看支持缓存。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface LookupSupportCache extends BatchBaseCache<LookupSupportKey, LookupSupport> {
}
