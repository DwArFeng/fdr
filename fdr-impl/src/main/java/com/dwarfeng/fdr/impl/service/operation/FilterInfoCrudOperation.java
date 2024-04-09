package com.dwarfeng.fdr.impl.service.operation;

import com.dwarfeng.fdr.stack.bean.entity.FilterInfo;
import com.dwarfeng.fdr.stack.cache.EnabledFilterInfoCache;
import com.dwarfeng.fdr.stack.cache.FilterInfoCache;
import com.dwarfeng.fdr.stack.dao.FilterInfoDao;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.BatchCrudOperation;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class FilterInfoCrudOperation implements BatchCrudOperation<LongIdKey, FilterInfo> {

    @Autowired
    private FilterInfoDao filterInfoDao;

    @Autowired
    private FilterInfoCache filterInfoCache;
    @Autowired
    private EnabledFilterInfoCache enabledFilterInfoCache;

    @Value("${cache.timeout.entity.filter_info}")
    private long filterInfoTimeout;

    @Override
    public boolean exists(LongIdKey key) throws Exception {
        return filterInfoCache.exists(key) || filterInfoDao.exists(key);
    }

    @Override
    public FilterInfo get(LongIdKey key) throws Exception {
        if (filterInfoCache.exists(key)) {
            return filterInfoCache.get(key);
        } else {
            if (!filterInfoDao.exists(key)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            FilterInfo filterInfo = filterInfoDao.get(key);
            filterInfoCache.push(filterInfo, filterInfoTimeout);
            return filterInfo;
        }
    }

    @Override
    public LongIdKey insert(FilterInfo filterInfo) throws Exception {
        if (Objects.nonNull(filterInfo.getPointKey())) {
            enabledFilterInfoCache.delete(filterInfo.getPointKey());
        }

        filterInfoCache.push(filterInfo, filterInfoTimeout);
        return filterInfoDao.insert(filterInfo);
    }

    @Override
    public void update(FilterInfo filterInfo) throws Exception {
        FilterInfo oldFilterInfo = get(filterInfo.getKey());
        if (Objects.nonNull(oldFilterInfo.getPointKey())) {
            enabledFilterInfoCache.delete(oldFilterInfo.getPointKey());
        }

        if (Objects.nonNull(filterInfo.getPointKey())) {
            enabledFilterInfoCache.delete(filterInfo.getPointKey());
        }

        filterInfoCache.push(filterInfo, filterInfoTimeout);
        filterInfoDao.update(filterInfo);
    }

    @Override
    public void delete(LongIdKey key) throws Exception {
        FilterInfo oldFilterInfo = get(key);
        if (Objects.nonNull(oldFilterInfo.getPointKey())) {
            enabledFilterInfoCache.delete(oldFilterInfo.getPointKey());
        }

        filterInfoDao.delete(key);
        filterInfoCache.delete(key);
    }

    @Override
    public boolean allExists(List<LongIdKey> keys) throws Exception {
        return filterInfoCache.allExists(keys) || filterInfoDao.allExists(keys);
    }

    @Override
    public boolean nonExists(List<LongIdKey> keys) throws Exception {
        return filterInfoCache.nonExists(keys) && filterInfoDao.nonExists(keys);
    }

    @Override
    public List<FilterInfo> batchGet(List<LongIdKey> keys) throws Exception {
        if (filterInfoCache.allExists(keys)) {
            return filterInfoCache.batchGet(keys);
        } else {
            if (!filterInfoDao.allExists(keys)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            List<FilterInfo> filterInfos = filterInfoDao.batchGet(keys);
            filterInfoCache.batchPush(filterInfos, filterInfoTimeout);
            return filterInfos;
        }
    }

    @Override
    public List<LongIdKey> batchInsert(List<FilterInfo> filterInfos) throws Exception {
        List<LongIdKey> keys = new ArrayList<>();
        for (FilterInfo filterInfo : filterInfos) {
            keys.add(insert(filterInfo));
        }
        return keys;
    }

    @Override
    public void batchUpdate(List<FilterInfo> filterInfos) throws Exception {
        for (FilterInfo filterInfo : filterInfos) {
            update(filterInfo);
        }
    }

    @Override
    public void batchDelete(List<LongIdKey> keys) throws Exception {
        for (LongIdKey key : keys) {
            delete(key);
        }
    }
}
