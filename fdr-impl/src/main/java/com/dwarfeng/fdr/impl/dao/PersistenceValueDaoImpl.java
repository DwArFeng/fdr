package com.dwarfeng.fdr.impl.dao;

import com.dwarfeng.fdr.impl.bean.entity.HibernatePersistenceValue;
import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.dao.PersistenceValueDao;
import com.dwarfeng.fdr.stack.service.PersistenceValueMaintainService;
import com.dwarfeng.subgrade.impl.dao.HibernateBatchBaseDao;
import com.dwarfeng.subgrade.impl.dao.HibernateBatchWriteDao;
import com.dwarfeng.subgrade.impl.dao.HibernateEntireLookupDao;
import com.dwarfeng.subgrade.impl.dao.HibernatePresetLookupDao;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.DaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;

@Repository
public class PersistenceValueDaoImpl implements PersistenceValueDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceValueDaoImpl.class);

    @Autowired
    private HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, PersistenceValue, HibernatePersistenceValue> batchBaseDao;
    @Autowired
    private HibernateEntireLookupDao<PersistenceValue, HibernatePersistenceValue> entireLookupDao;
    @Autowired
    private HibernatePresetLookupDao<PersistenceValue, HibernatePersistenceValue> presetLookupDao;
    @Autowired
    private HibernateBatchWriteDao<PersistenceValue, HibernatePersistenceValue> batchWriteDao;
    @Autowired
    private HibernateTemplate hibernateTemplate;
    @Autowired
    private List<NSQLGenerator> nsqlGenerators;

    @Value("${hibernate.accelerate.using_native_sql}")
    private boolean usingNativeSQL;
    @Value("${hibernate.dialect}")
    private String hibernateDialect;

    private NSQLGenerator nsqlGenerator = null;

    @PostConstruct
    public void init() {
        nsqlGenerator = nsqlGenerators.stream()
                .filter(generator -> generator.supportType(hibernateDialect)).findAny().orElse(null);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public LongIdKey insert(PersistenceValue element) throws DaoException {
        return batchBaseDao.insert(element);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void update(PersistenceValue element) throws DaoException {
        batchBaseDao.update(element);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void delete(LongIdKey key) throws DaoException {
        batchBaseDao.delete(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public boolean exists(LongIdKey key) throws DaoException {
        return batchBaseDao.exists(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public PersistenceValue get(LongIdKey key) throws DaoException {
        return batchBaseDao.get(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public List<LongIdKey> batchInsert(List<PersistenceValue> elements) throws DaoException {
        return batchBaseDao.batchInsert(elements);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void batchUpdate(List<PersistenceValue> elements) throws DaoException {
        batchBaseDao.batchUpdate(elements);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void batchDelete(List<LongIdKey> keys) throws DaoException {
        batchBaseDao.batchDelete(keys);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public boolean allExists(List<LongIdKey> keys) throws DaoException {
        return batchBaseDao.allExists(keys);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public boolean nonExists(List<LongIdKey> keys) throws DaoException {
        return batchBaseDao.nonExists(keys);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<PersistenceValue> batchGet(List<LongIdKey> keys) throws DaoException {
        return batchBaseDao.batchGet(keys);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<PersistenceValue> lookup() throws DaoException {
        return entireLookupDao.lookup();
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<PersistenceValue> lookup(PagingInfo pagingInfo) throws DaoException {
        return entireLookupDao.lookup(pagingInfo);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public int lookupCount() throws DaoException {
        return entireLookupDao.lookupCount();
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<PersistenceValue> lookup(String preset, Object[] objs) throws DaoException {
        try {
            if (Objects.equals(PersistenceValueMaintainService.CHILD_FOR_POINT_BETWEEN, preset) && usingNativeSQL) {
                if (Objects.isNull(nsqlGenerator)) {
                    LOGGER.warn("指定的 hibernateDialect: " + hibernateDialect + ", 不受支持, 将不会使用原生SQL进行查询");
                    return presetLookupDao.lookup(preset, objs);
                }
                LOGGER.debug("使用原生SQL进行查询...");
                List<PersistenceValue> persistenceValues = hibernateTemplate.executeWithNativeSession(
                        session -> session.doReturningWork(connection -> {
                            try {
                                return nsqlGenerator.lookupPersistence(connection, objs);
                            } catch (Exception e) {
                                LOGGER.warn("原生SQL查询返回异常", e);
                                return null;
                            }
                        })
                );
                if (Objects.nonNull(persistenceValues)) {
                    return persistenceValues;
                } else {
                    LOGGER.warn("原生SQL查询返回值无效, 不使用原生SQL再次进行查询...");
                    return presetLookupDao.lookup(preset, objs);
                }
            } else {
                return presetLookupDao.lookup(preset, objs);
            }
        } catch (DaoException e) {
            throw e;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<PersistenceValue> lookup(String preset, Object[] objs, PagingInfo pagingInfo) throws DaoException {
        try {
            if (Objects.equals(PersistenceValueMaintainService.CHILD_FOR_POINT_BETWEEN, preset) && usingNativeSQL) {
                if (Objects.isNull(nsqlGenerator)) {
                    LOGGER.warn("指定的 hibernateDialect: " + hibernateDialect + ", 不受支持, 将不会使用原生SQL进行查询");
                    return presetLookupDao.lookup(preset, objs, pagingInfo);
                }
                LOGGER.debug("使用原生SQL进行查询...");
                List<PersistenceValue> persistenceValues = hibernateTemplate.executeWithNativeSession(
                        session -> session.doReturningWork(connection -> {
                            try {
                                return nsqlGenerator.lookupPersistence(connection, objs, pagingInfo);
                            } catch (Exception e) {
                                LOGGER.warn("原生SQL查询返回异常", e);
                                return null;
                            }
                        })
                );
                if (Objects.nonNull(persistenceValues)) {
                    return persistenceValues;
                } else {
                    LOGGER.warn("原生SQL查询返回值无效, 不使用原生SQL再次进行查询...");
                    return presetLookupDao.lookup(preset, objs, pagingInfo);
                }
            } else {
                return presetLookupDao.lookup(preset, objs, pagingInfo);
            }
        } catch (DaoException e) {
            throw e;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public int lookupCount(String preset, Object[] objs) throws DaoException {
        try {
            if (Objects.equals(PersistenceValueMaintainService.CHILD_FOR_POINT_BETWEEN, preset) && usingNativeSQL) {
                if (Objects.isNull(nsqlGenerator)) {
                    LOGGER.warn("指定的 hibernateDialect: " + hibernateDialect + ", 不受支持, 将不会使用原生SQL进行查询");
                    return presetLookupDao.lookupCount(preset, objs);
                }
                LOGGER.debug("使用原生SQL进行查询...");
                Integer count = hibernateTemplate.executeWithNativeSession(
                        session -> session.doReturningWork(connection -> {
                            try {
                                return nsqlGenerator.lookupPersistenceCount(connection, objs);
                            } catch (Exception e) {
                                LOGGER.warn("原生SQL查询返回异常", e);
                                return null;
                            }
                        })
                );
                if (Objects.nonNull(count)) {
                    return count;
                } else {
                    LOGGER.warn("原生SQL查询返回值无效, 不使用原生SQL再次进行查询...");
                    return presetLookupDao.lookupCount(preset, objs);
                }
            } else {
                return presetLookupDao.lookupCount(preset, objs);
            }
        } catch (DaoException e) {
            throw e;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void write(PersistenceValue element) throws DaoException {
        batchWriteDao.write(element);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void batchWrite(List<PersistenceValue> elements) throws DaoException {
        batchWriteDao.batchWrite(elements);
    }
}
