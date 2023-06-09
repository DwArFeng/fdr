package com.dwarfeng.fdr.stack.cache;

import com.dwarfeng.fdr.stack.bean.entity.WasherInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.cache.KeyListCache;

/**
 * 分类持有子项的缓存。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface EnabledWasherInfoCache extends KeyListCache<LongIdKey, WasherInfo> {
}
