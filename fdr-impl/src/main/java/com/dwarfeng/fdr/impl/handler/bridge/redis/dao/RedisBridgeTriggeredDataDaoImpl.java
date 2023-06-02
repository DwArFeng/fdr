package com.dwarfeng.fdr.impl.handler.bridge.redis.dao;

import com.dwarfeng.fdr.impl.handler.bridge.redis.bean.RedisBridgeFastJsonTriggeredData;
import com.dwarfeng.fdr.impl.handler.bridge.redis.bean.RedisBridgeTriggeredData;
import com.dwarfeng.subgrade.impl.dao.RedisBatchBaseDao;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.DaoException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RedisBridgeTriggeredDataDaoImpl implements RedisBridgeTriggeredDataDao {

    private final RedisBatchBaseDao<LongIdKey, RedisBridgeTriggeredData, RedisBridgeFastJsonTriggeredData> batchBaseDao;

    public RedisBridgeTriggeredDataDaoImpl(
            RedisBatchBaseDao<LongIdKey, RedisBridgeTriggeredData, RedisBridgeFastJsonTriggeredData> batchBaseDao
    ) {
        this.batchBaseDao = batchBaseDao;
    }

    @Override
    @BehaviorAnalyse
    public LongIdKey insert(RedisBridgeTriggeredData element) throws DaoException {
        return batchBaseDao.insert(element);
    }

    @Override
    @BehaviorAnalyse
    public void update(RedisBridgeTriggeredData element) throws DaoException {
        batchBaseDao.update(element);
    }

    @Override
    @BehaviorAnalyse
    public void delete(LongIdKey key) throws DaoException {
        batchBaseDao.delete(key);
    }

    @Override
    @BehaviorAnalyse
    public boolean exists(LongIdKey key) throws DaoException {
        return batchBaseDao.exists(key);
    }

    @Override
    @BehaviorAnalyse
    public RedisBridgeTriggeredData get(LongIdKey key) throws DaoException {
        return batchBaseDao.get(key);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    public List<LongIdKey> batchInsert(@SkipRecord List<RedisBridgeTriggeredData> elements) throws DaoException {
        return batchBaseDao.batchInsert(elements);
    }

    @Override
    @BehaviorAnalyse
    public void batchUpdate(@SkipRecord List<RedisBridgeTriggeredData> elements) throws DaoException {
        batchBaseDao.batchUpdate(elements);
    }

    @Override
    @BehaviorAnalyse
    public void batchDelete(@SkipRecord List<LongIdKey> keys) throws DaoException {
        batchBaseDao.batchDelete(keys);
    }

    @Override
    @BehaviorAnalyse
    public boolean allExists(@SkipRecord List<LongIdKey> keys) throws DaoException {
        return batchBaseDao.allExists(keys);
    }

    @Override
    @BehaviorAnalyse
    public boolean nonExists(@SkipRecord List<LongIdKey> keys) throws DaoException {
        return batchBaseDao.nonExists(keys);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    public List<RedisBridgeTriggeredData> batchGet(@SkipRecord List<LongIdKey> keys) throws DaoException {
        return batchBaseDao.batchGet(keys);
    }
}
