package com.dwarfeng.fdr.impl.cache;

import com.dwarfeng.fdr.sdk.bean.entity.FastJsonFetcherInfo;
import com.dwarfeng.fdr.stack.bean.entity.FetcherInfo;
import com.dwarfeng.fdr.stack.cache.EnabledFetcherInfoCache;
import com.dwarfeng.subgrade.impl.cache.RedisListCache;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.exception.CacheException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * <code>EnabledFetcherInfoCache</code> 的 Redis 实现。
 *
 * <p>
 * 该实现委托 {@link RedisListCache} 完成实际的 Redis 缓存操作。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
@Repository
public class EnabledFetcherInfoCacheImpl implements EnabledFetcherInfoCache {

    private final RedisListCache<FetcherInfo, FastJsonFetcherInfo> delegate;

    public EnabledFetcherInfoCacheImpl(RedisListCache<FetcherInfo, FastJsonFetcherInfo> delegate) {
        this.delegate = delegate;
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public boolean exists() throws CacheException {
        return delegate.exists();
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public int size() throws CacheException {
        return delegate.size();
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<FetcherInfo> get() throws CacheException {
        return delegate.get();
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<FetcherInfo> get(int beginIndex, int maxEntity) throws CacheException {
        return delegate.get(beginIndex, maxEntity);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<FetcherInfo> get(PagingInfo pagingInfo) throws CacheException {
        return delegate.get(pagingInfo);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void set(@SkipRecord Collection<FetcherInfo> entities, long timeout) throws CacheException {
        delegate.set(entities, timeout);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void leftPush(@SkipRecord Collection<FetcherInfo> entities, long timeout)
            throws CacheException {
        delegate.leftPush(entities, timeout);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void rightPush(@SkipRecord Collection<FetcherInfo> entities, long timeout)
            throws CacheException {
        delegate.rightPush(entities, timeout);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void clear() throws CacheException {
        delegate.clear();
    }
}
