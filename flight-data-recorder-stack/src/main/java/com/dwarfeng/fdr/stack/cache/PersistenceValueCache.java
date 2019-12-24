package com.dwarfeng.fdr.stack.cache;

import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.exception.CacheException;

/**
 * 持久化数据缓存。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface PersistenceValueCache extends BaseCache<UuidKey, PersistenceValue> {

    void deleteAll(UuidKey pointKey) throws CacheException;
}
