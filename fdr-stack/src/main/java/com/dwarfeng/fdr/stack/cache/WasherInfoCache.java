package com.dwarfeng.fdr.stack.cache;

import com.dwarfeng.fdr.stack.bean.entity.WasherInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 清洗器信息缓存。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface WasherInfoCache extends BatchBaseCache<LongIdKey, WasherInfo> {
}
