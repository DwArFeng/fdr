package com.dwarfeng.fdr.impl.service.operation;

import com.dwarfeng.fdr.stack.bean.entity.FetcherInfo;
import com.dwarfeng.fdr.stack.cache.EnabledFetcherInfoCache;
import com.dwarfeng.fdr.stack.cache.FetcherInfoCache;
import com.dwarfeng.fdr.stack.dao.FetcherInfoDao;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.BatchCrudOperation;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FetcherInfoCrudOperation implements BatchCrudOperation<LongIdKey, FetcherInfo> {

    private final FetcherInfoDao fetcherInfoDao;
    private final FetcherInfoCache fetcherInfoCache;

    private final EnabledFetcherInfoCache enabledFetcherInfoCache;

    @Value("${cache.timeout.entity.fetcher_info}")
    private long fetcherInfoTimeout;

    public FetcherInfoCrudOperation(
            FetcherInfoDao fetcherInfoDao, FetcherInfoCache fetcherInfoCache,
            EnabledFetcherInfoCache enabledFetcherInfoCache
    ) {
        this.fetcherInfoDao = fetcherInfoDao;
        this.fetcherInfoCache = fetcherInfoCache;
        this.enabledFetcherInfoCache = enabledFetcherInfoCache;
    }

    @Override
    public boolean exists(LongIdKey key) throws Exception {
        return fetcherInfoCache.exists(key) || fetcherInfoDao.exists(key);
    }

    @Override
    public FetcherInfo get(LongIdKey key) throws Exception {
        if (fetcherInfoCache.exists(key)) {
            return fetcherInfoCache.get(key);
        } else {
            if (!fetcherInfoDao.exists(key)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            FetcherInfo fetcherInfo = fetcherInfoDao.get(key);
            fetcherInfoCache.push(fetcherInfo, fetcherInfoTimeout);
            return fetcherInfo;
        }
    }

    @Override
    public LongIdKey insert(FetcherInfo fetcherInfo) throws Exception {
        enabledFetcherInfoCache.clear();

        fetcherInfoCache.push(fetcherInfo, fetcherInfoTimeout);
        return fetcherInfoDao.insert(fetcherInfo);
    }

    @Override
    public void update(FetcherInfo fetcherInfo) throws Exception {
        enabledFetcherInfoCache.clear();

        fetcherInfoCache.push(fetcherInfo, fetcherInfoTimeout);
        fetcherInfoDao.update(fetcherInfo);
    }

    @Override
    public void delete(LongIdKey key) throws Exception {
        enabledFetcherInfoCache.clear();

        fetcherInfoDao.delete(key);
        fetcherInfoCache.delete(key);
    }

    @Override
    public boolean allExists(List<LongIdKey> keys) throws Exception {
        return fetcherInfoCache.allExists(keys) || fetcherInfoDao.allExists(keys);
    }

    @Override
    public boolean nonExists(List<LongIdKey> keys) throws Exception {
        return fetcherInfoCache.nonExists(keys) && fetcherInfoDao.nonExists(keys);
    }

    @Override
    public List<FetcherInfo> batchGet(List<LongIdKey> keys) throws Exception {
        if (fetcherInfoCache.allExists(keys)) {
            return fetcherInfoCache.batchGet(keys);
        } else {
            if (!fetcherInfoDao.allExists(keys)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            List<FetcherInfo> fetcherInfos = fetcherInfoDao.batchGet(keys);
            fetcherInfoCache.batchPush(fetcherInfos, fetcherInfoTimeout);
            return fetcherInfos;
        }
    }

    @Override
    public List<LongIdKey> batchInsert(List<FetcherInfo> fetcherInfos) throws Exception {
        List<LongIdKey> keys = new ArrayList<>();
        for (FetcherInfo fetcherInfo : fetcherInfos) {
            keys.add(insert(fetcherInfo));
        }
        return keys;
    }

    @Override
    public void batchUpdate(List<FetcherInfo> fetcherInfos) throws Exception {
        for (FetcherInfo fetcherInfo : fetcherInfos) {
            update(fetcherInfo);
        }
    }

    @Override
    public void batchDelete(List<LongIdKey> keys) throws Exception {
        for (LongIdKey key : keys) {
            delete(key);
        }
    }
}
