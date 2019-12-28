package com.dwarfeng.fdr.impl.cache.redis.cache;

import com.dwarfeng.fdr.stack.bean.entity.Category;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.cache.CategoryHasChildCache;
import com.dwarfeng.fdr.stack.exception.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryHasChildCacheImpl implements CategoryHasChildCache {

    @Autowired
    private CategoryHasChildCacheDelegate delegate;

    @Override
    public boolean exists(UuidKey key) throws CacheException {
        return delegate.exists(key);
    }

    @Override
    public long size(UuidKey key) throws CacheException {
        return delegate.size(key);
    }

    @Override
    public List<Category> get(UuidKey key, int beginIndex, int maxSize) throws CacheException {
        return delegate.get(key, beginIndex, maxSize);
    }

    @Override
    public void set(UuidKey key, List<? extends Category> value, long timeout) throws CacheException {
        delegate.set(key, value, timeout);
    }

    @Override
    public void push(UuidKey key, List<? extends Category> value, long timeout) throws CacheException {
        delegate.push(key, value, timeout);
    }

    @Override
    public void delete(UuidKey key) throws CacheException {
        delegate.delete(key);
    }
}