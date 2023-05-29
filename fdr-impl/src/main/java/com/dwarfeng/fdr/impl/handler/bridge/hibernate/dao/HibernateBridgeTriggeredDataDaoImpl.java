package com.dwarfeng.fdr.impl.handler.bridge.hibernate.dao;

import com.dwarfeng.fdr.impl.handler.bridge.hibernate.bean.entity.HibernateBridgeTriggeredData;
import com.dwarfeng.fdr.impl.handler.bridge.hibernate.bean.entity.HibernateHibernateBridgeTriggeredData;
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
public class HibernateBridgeTriggeredDataDaoImpl implements HibernateBridgeTriggeredDataDao {

    private final HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, HibernateBridgeTriggeredData,
            HibernateHibernateBridgeTriggeredData> batchBaseDao;
    private final HibernateEntireLookupDao<HibernateBridgeTriggeredData, HibernateHibernateBridgeTriggeredData>
            entireLookupDao;
    private final PresetLookupDao<HibernateBridgeTriggeredData> presetLookupDao;
    private final HibernateBatchWriteDao<HibernateBridgeTriggeredData, HibernateHibernateBridgeTriggeredData>
            batchWriteDao;

    public HibernateBridgeTriggeredDataDaoImpl(
            HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, HibernateBridgeTriggeredData,
                    HibernateHibernateBridgeTriggeredData> batchBaseDao,
            HibernateEntireLookupDao<HibernateBridgeTriggeredData, HibernateHibernateBridgeTriggeredData>
                    entireLookupDao,
            PresetLookupDao<HibernateBridgeTriggeredData> presetLookupDao,
            HibernateBatchWriteDao<HibernateBridgeTriggeredData, HibernateHibernateBridgeTriggeredData> batchWriteDao
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
    public LongIdKey insert(HibernateBridgeTriggeredData element) throws DaoException {
        return batchBaseDao.insert(element);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            rollbackFor = Exception.class
    )
    public void update(HibernateBridgeTriggeredData element) throws DaoException {
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
    public HibernateBridgeTriggeredData get(LongIdKey key) throws DaoException {
        return batchBaseDao.get(key);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            rollbackFor = Exception.class
    )
    public List<LongIdKey> batchInsert(@SkipRecord List<HibernateBridgeTriggeredData> elements) throws DaoException {
        return batchBaseDao.batchInsert(elements);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            rollbackFor = Exception.class
    )
    public void batchUpdate(@SkipRecord List<HibernateBridgeTriggeredData> elements) throws DaoException {
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
    public List<HibernateBridgeTriggeredData> batchGet(@SkipRecord List<LongIdKey> keys) throws DaoException {
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
    public List<HibernateBridgeTriggeredData> lookup() throws DaoException {
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
    public List<HibernateBridgeTriggeredData> lookup(PagingInfo pagingInfo) throws DaoException {
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
    public List<HibernateBridgeTriggeredData> lookup(String preset, Object[] objs) throws DaoException {
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
    public List<HibernateBridgeTriggeredData> lookup(
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
    public void write(HibernateBridgeTriggeredData element) throws DaoException {
        batchWriteDao.write(element);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            rollbackFor = Exception.class
    )
    public void batchWrite(List<HibernateBridgeTriggeredData> elements) throws DaoException {
        batchWriteDao.batchWrite(elements);
    }
}
