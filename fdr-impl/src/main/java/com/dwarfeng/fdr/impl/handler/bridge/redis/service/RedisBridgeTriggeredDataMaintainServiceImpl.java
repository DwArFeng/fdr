package com.dwarfeng.fdr.impl.handler.bridge.redis.service;

import com.dwarfeng.fdr.impl.handler.bridge.redis.bean.RedisBridgeTriggeredData;
import com.dwarfeng.subgrade.impl.service.DaoOnlyBatchCrudService;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RedisBridgeTriggeredDataMaintainServiceImpl implements RedisBridgeTriggeredDataMaintainService {

    private final DaoOnlyBatchCrudService<LongIdKey, RedisBridgeTriggeredData> batchCrudService;

    public RedisBridgeTriggeredDataMaintainServiceImpl(
            DaoOnlyBatchCrudService<LongIdKey, RedisBridgeTriggeredData> batchCrudService
    ) {
        this.batchCrudService = batchCrudService;
    }

    @Override
    @BehaviorAnalyse
    public boolean exists(LongIdKey key) throws ServiceException {
        return batchCrudService.exists(key);
    }

    @Override
    @BehaviorAnalyse
    public RedisBridgeTriggeredData get(LongIdKey key) throws ServiceException {
        return batchCrudService.get(key);
    }

    @Override
    @BehaviorAnalyse
    public LongIdKey insert(RedisBridgeTriggeredData element) throws ServiceException {
        return batchCrudService.insert(element);
    }

    @Override
    @BehaviorAnalyse
    public void update(RedisBridgeTriggeredData element) throws ServiceException {
        batchCrudService.update(element);
    }

    @Override
    @BehaviorAnalyse
    public void delete(LongIdKey key) throws ServiceException {
        batchCrudService.delete(key);
    }

    @Override
    @BehaviorAnalyse
    public RedisBridgeTriggeredData getIfExists(LongIdKey key) throws ServiceException {
        return batchCrudService.getIfExists(key);
    }

    @Override
    @BehaviorAnalyse
    public LongIdKey insertIfNotExists(RedisBridgeTriggeredData element) throws ServiceException {
        return batchCrudService.insertIfNotExists(element);
    }

    @Override
    @BehaviorAnalyse
    public void updateIfExists(RedisBridgeTriggeredData element) throws ServiceException {
        batchCrudService.updateIfExists(element);
    }

    @Override
    @BehaviorAnalyse
    public void deleteIfExists(LongIdKey key) throws ServiceException {
        batchCrudService.deleteIfExists(key);
    }

    @Override
    @BehaviorAnalyse
    public LongIdKey insertOrUpdate(RedisBridgeTriggeredData element) throws ServiceException {
        return batchCrudService.insertOrUpdate(element);
    }

    @Override
    @BehaviorAnalyse
    public boolean allExists(@SkipRecord List<LongIdKey> keys) throws ServiceException {
        return batchCrudService.allExists(keys);
    }

    @Override
    @BehaviorAnalyse
    public boolean nonExists(@SkipRecord List<LongIdKey> keys) throws ServiceException {
        return batchCrudService.nonExists(keys);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    public List<RedisBridgeTriggeredData> batchGet(
            @SkipRecord List<LongIdKey> keys
    ) throws ServiceException {
        return batchCrudService.batchGet(keys);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    public List<LongIdKey> batchInsert(
            @SkipRecord List<RedisBridgeTriggeredData> elements
    ) throws ServiceException {
        return batchCrudService.batchInsert(elements);
    }

    @Override
    @BehaviorAnalyse
    public void batchUpdate(@SkipRecord List<RedisBridgeTriggeredData> elements) throws ServiceException {
        batchCrudService.batchUpdate(elements);
    }

    @Override
    @BehaviorAnalyse
    public void batchDelete(@SkipRecord List<LongIdKey> keys) throws ServiceException {
        batchCrudService.batchDelete(keys);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    public List<RedisBridgeTriggeredData> batchGetIfExists(
            @SkipRecord List<LongIdKey> keys
    ) throws ServiceException {
        return batchCrudService.batchGetIfExists(keys);
    }

    @Deprecated
    @Override
    @BehaviorAnalyse
    @SkipRecord
    public List<LongIdKey> batchInsertIfExists(
            @SkipRecord List<RedisBridgeTriggeredData> elements
    ) throws ServiceException {
        return batchCrudService.batchInsertIfExists(elements);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    public List<LongIdKey> batchInsertIfNotExists(
            @SkipRecord List<RedisBridgeTriggeredData> elements
    ) throws ServiceException {
        return batchCrudService.batchInsertIfNotExists(elements);
    }

    @Override
    @BehaviorAnalyse
    public void batchUpdateIfExists(@SkipRecord List<RedisBridgeTriggeredData> elements) throws ServiceException {
        batchCrudService.batchUpdateIfExists(elements);
    }

    @Override
    @BehaviorAnalyse
    public void batchDeleteIfExists(@SkipRecord List<LongIdKey> keys) throws ServiceException {
        batchCrudService.batchDeleteIfExists(keys);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    public List<LongIdKey> batchInsertOrUpdate(
            @SkipRecord List<RedisBridgeTriggeredData> elements
    ) throws ServiceException {
        return batchCrudService.batchInsertOrUpdate(elements);
    }
}
