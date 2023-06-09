package com.dwarfeng.fdr.impl.cache;

import com.dwarfeng.fdr.sdk.bean.entity.FastJsonWasherInfo;
import com.dwarfeng.fdr.stack.bean.entity.WasherInfo;
import com.dwarfeng.fdr.stack.cache.EnabledWasherInfoCache;
import com.dwarfeng.subgrade.impl.cache.RedisKeyListCache;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.CacheException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Repository
public class EnabledWasherInfoCacheImpl implements EnabledWasherInfoCache {

    private final RedisKeyListCache<LongIdKey, WasherInfo, FastJsonWasherInfo> delegate;

    public EnabledWasherInfoCacheImpl(RedisKeyListCache<LongIdKey, WasherInfo, FastJsonWasherInfo> delegate) {
        this.delegate = delegate;
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public boolean exists(LongIdKey key) throws CacheException {
        return delegate.exists(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public int size(LongIdKey key) throws CacheException {
        return delegate.size(key);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<WasherInfo> get(LongIdKey key) throws CacheException {
        return delegate.get(key);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<WasherInfo> get(LongIdKey key, int beginIndex, int maxEntity) throws CacheException {
        return delegate.get(key, beginIndex, maxEntity);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<WasherInfo> get(LongIdKey key, PagingInfo pagingInfo) throws CacheException {
        return delegate.get(key, pagingInfo);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void set(LongIdKey key, @SkipRecord Collection<WasherInfo> entities, long timeout) throws CacheException {
        delegate.set(key, entities, timeout);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void leftPush(LongIdKey key, @SkipRecord Collection<WasherInfo> entities, long timeout) throws CacheException {
        delegate.leftPush(key, entities, timeout);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void rightPush(LongIdKey key, @SkipRecord Collection<WasherInfo> entities, long timeout) throws CacheException {
        delegate.rightPush(key, entities, timeout);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void delete(LongIdKey key) throws CacheException {
        delegate.delete(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void clear() throws CacheException {
        delegate.clear();
    }
}
