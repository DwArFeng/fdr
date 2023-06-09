package com.dwarfeng.fdr.impl.service.operation;

import com.dwarfeng.fdr.stack.bean.entity.WasherInfo;
import com.dwarfeng.fdr.stack.cache.EnabledWasherInfoCache;
import com.dwarfeng.fdr.stack.cache.WasherInfoCache;
import com.dwarfeng.fdr.stack.dao.WasherInfoDao;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.BatchCrudOperation;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class WasherInfoCrudOperation implements BatchCrudOperation<LongIdKey, WasherInfo> {

    private final WasherInfoDao washerInfoDao;
    private final WasherInfoCache washerInfoCache;

    private final EnabledWasherInfoCache enabledWasherInfoCache;

    @Value("${cache.timeout.entity.washer_info}")
    private long washerInfoTimeout;

    public WasherInfoCrudOperation(
            WasherInfoDao washerInfoDao, WasherInfoCache washerInfoCache,
            EnabledWasherInfoCache enabledWasherInfoCache
    ) {
        this.washerInfoDao = washerInfoDao;
        this.washerInfoCache = washerInfoCache;
        this.enabledWasherInfoCache = enabledWasherInfoCache;
    }

    @Override
    public boolean exists(LongIdKey key) throws Exception {
        return washerInfoCache.exists(key) || washerInfoDao.exists(key);
    }

    @Override
    public WasherInfo get(LongIdKey key) throws Exception {
        if (washerInfoCache.exists(key)) {
            return washerInfoCache.get(key);
        } else {
            if (!washerInfoDao.exists(key)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            WasherInfo washerInfo = washerInfoDao.get(key);
            washerInfoCache.push(washerInfo, washerInfoTimeout);
            return washerInfo;
        }
    }

    @Override
    public LongIdKey insert(WasherInfo washerInfo) throws Exception {
        if (Objects.nonNull(washerInfo.getPointKey())) {
            enabledWasherInfoCache.delete(washerInfo.getPointKey());
        }

        washerInfoCache.push(washerInfo, washerInfoTimeout);
        return washerInfoDao.insert(washerInfo);
    }

    @Override
    public void update(WasherInfo washerInfo) throws Exception {
        WasherInfo oldWasherInfo = get(washerInfo.getKey());
        if (Objects.nonNull(oldWasherInfo.getPointKey())) {
            enabledWasherInfoCache.delete(oldWasherInfo.getPointKey());
        }

        if (Objects.nonNull(washerInfo.getPointKey())) {
            enabledWasherInfoCache.delete(washerInfo.getPointKey());
        }

        washerInfoCache.push(washerInfo, washerInfoTimeout);
        washerInfoDao.update(washerInfo);
    }

    @Override
    public void delete(LongIdKey key) throws Exception {
        WasherInfo oldWasherInfo = get(key);
        if (Objects.nonNull(oldWasherInfo.getPointKey())) {
            enabledWasherInfoCache.delete(oldWasherInfo.getPointKey());
        }

        washerInfoDao.delete(key);
        washerInfoCache.delete(key);
    }

    @Override
    public boolean allExists(List<LongIdKey> keys) throws Exception {
        return washerInfoCache.allExists(keys) || washerInfoDao.allExists(keys);
    }

    @Override
    public boolean nonExists(List<LongIdKey> keys) throws Exception {
        return washerInfoCache.nonExists(keys) && washerInfoCache.nonExists(keys);
    }

    @Override
    public List<WasherInfo> batchGet(List<LongIdKey> keys) throws Exception {
        if (washerInfoCache.allExists(keys)) {
            return washerInfoCache.batchGet(keys);
        } else {
            if (!washerInfoDao.allExists(keys)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            List<WasherInfo> washerInfos = washerInfoDao.batchGet(keys);
            washerInfoCache.batchPush(washerInfos, washerInfoTimeout);
            return washerInfos;
        }
    }

    @Override
    public List<LongIdKey> batchInsert(List<WasherInfo> washerInfos) throws Exception {
        List<LongIdKey> keys = new ArrayList<>();
        for (WasherInfo washerInfo : washerInfos) {
            keys.add(insert(washerInfo));
        }
        return keys;
    }

    @Override
    public void batchUpdate(List<WasherInfo> washerInfos) throws Exception {
        for (WasherInfo washerInfo : washerInfos) {
            update(washerInfo);
        }
    }

    @Override
    public void batchDelete(List<LongIdKey> keys) throws Exception {
        for (LongIdKey key : keys) {
            delete(key);
        }
    }
}
