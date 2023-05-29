package com.dwarfeng.fdr.impl.handler.bridge.hibernate.dao;

import com.dwarfeng.fdr.impl.handler.bridge.hibernate.bean.entity.HibernateBridgeNormalData;
import com.dwarfeng.fdr.impl.handler.bridge.hibernate.bean.entity.HibernateHibernateBridgeNormalData;
import com.dwarfeng.subgrade.impl.dao.HibernateBatchBaseDao;
import com.dwarfeng.subgrade.impl.dao.HibernateBatchWriteDao;
import com.dwarfeng.subgrade.impl.dao.HibernateEntireLookupDao;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;
import com.dwarfeng.subgrade.stack.exception.DaoException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class HibernateBridgeNormalDataDaoImpl implements HibernateBridgeNormalDataDao {

    private final HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, HibernateBridgeNormalData,
            HibernateHibernateBridgeNormalData> batchBaseDao;
    private final HibernateEntireLookupDao<HibernateBridgeNormalData, HibernateHibernateBridgeNormalData>
            entireLookupDao;
    private final PresetLookupDao<HibernateBridgeNormalData> presetLookupDao;
    private final HibernateBatchWriteDao<HibernateBridgeNormalData, HibernateHibernateBridgeNormalData> batchWriteDao;

    public HibernateBridgeNormalDataDaoImpl(
            HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, HibernateBridgeNormalData,
                    HibernateHibernateBridgeNormalData> batchBaseDao,
            HibernateEntireLookupDao<HibernateBridgeNormalData, HibernateHibernateBridgeNormalData> entireLookupDao,
            PresetLookupDao<HibernateBridgeNormalData> presetLookupDao,
            HibernateBatchWriteDao<HibernateBridgeNormalData, HibernateHibernateBridgeNormalData> batchWriteDao
    ) {
        this.batchBaseDao = batchBaseDao;
        this.entireLookupDao = entireLookupDao;
        this.presetLookupDao = presetLookupDao;
        this.batchWriteDao = batchWriteDao;
    }

    @Override
    @BehaviorAnalyse
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            rollbackFor = Exception.class
    )
    public LongIdKey insert(HibernateBridgeNormalData element) throws DaoException {
        return batchBaseDao.insert(element);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            rollbackFor = Exception.class
    )
    public void update(HibernateBridgeNormalData element) throws DaoException {
        batchBaseDao.update(element);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            rollbackFor = Exception.class
    )
    public void delete(LongIdKey key) throws DaoException {
        batchBaseDao.delete(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            readOnly = true,
            rollbackFor = Exception.class
    )
    public boolean exists(LongIdKey key) throws DaoException {
        return batchBaseDao.exists(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            readOnly = true,
            rollbackFor = Exception.class
    )
    public HibernateBridgeNormalData get(LongIdKey key) throws DaoException {
        return batchBaseDao.get(key);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            rollbackFor = Exception.class
    )
    public List<LongIdKey> batchInsert(@SkipRecord List<HibernateBridgeNormalData> elements) throws DaoException {
        return batchBaseDao.batchInsert(elements);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            rollbackFor = Exception.class
    )
    public void batchUpdate(@SkipRecord List<HibernateBridgeNormalData> elements) throws DaoException {
        batchBaseDao.batchUpdate(elements);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            rollbackFor = Exception.class
    )
    public void batchDelete(@SkipRecord List<LongIdKey> keys) throws DaoException {
        batchBaseDao.batchDelete(keys);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            readOnly = true,
            rollbackFor = Exception.class
    )
    public boolean allExists(@SkipRecord List<LongIdKey> keys) throws DaoException {
        return batchBaseDao.allExists(keys);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            readOnly = true,
            rollbackFor = Exception.class
    )
    public boolean nonExists(@SkipRecord List<LongIdKey> keys) throws DaoException {
        return batchBaseDao.nonExists(keys);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            readOnly = true,
            rollbackFor = Exception.class
    )
    public List<HibernateBridgeNormalData> batchGet(@SkipRecord List<LongIdKey> keys) throws DaoException {
        return batchBaseDao.batchGet(keys);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            readOnly = true,
            rollbackFor = Exception.class
    )
    public List<HibernateBridgeNormalData> lookup() throws DaoException {
        return entireLookupDao.lookup();
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            readOnly = true,
            rollbackFor = Exception.class
    )
    public List<HibernateBridgeNormalData> lookup(PagingInfo pagingInfo) throws DaoException {
        return entireLookupDao.lookup(pagingInfo);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            readOnly = true,
            rollbackFor = Exception.class
    )
    public int lookupCount() throws DaoException {
        return entireLookupDao.lookupCount();
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            readOnly = true,
            rollbackFor = Exception.class
    )
    public List<HibernateBridgeNormalData> lookup(String preset, Object[] objs) throws DaoException {
        return presetLookupDao.lookup(preset, objs);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            readOnly = true,
            rollbackFor = Exception.class
    )
    public List<HibernateBridgeNormalData> lookup(
            String preset, Object[] objs, PagingInfo pagingInfo
    ) throws DaoException {
        return presetLookupDao.lookup(preset, objs, pagingInfo);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            readOnly = true,
            rollbackFor = Exception.class
    )
    public int lookupCount(String preset, Object[] objs) throws DaoException {
        return presetLookupDao.lookupCount(preset, objs);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            rollbackFor = Exception.class
    )
    public void write(HibernateBridgeNormalData element) throws DaoException {
        batchWriteDao.write(element);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            rollbackFor = Exception.class
    )
    public void batchWrite(List<HibernateBridgeNormalData> elements) throws DaoException {
        batchWriteDao.batchWrite(elements);
    }
}
