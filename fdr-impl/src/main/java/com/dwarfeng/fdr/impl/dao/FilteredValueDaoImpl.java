package com.dwarfeng.fdr.impl.dao;

import com.dwarfeng.fdr.impl.bean.entity.HibernateFilteredValue;
import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.dao.FilteredValueDao;
import com.dwarfeng.fdr.stack.service.FilteredValueMaintainService;
import com.dwarfeng.subgrade.impl.dao.HibernateBatchBaseDao;
import com.dwarfeng.subgrade.impl.dao.HibernateBatchWriteDao;
import com.dwarfeng.subgrade.impl.dao.HibernateEntireLookupDao;
import com.dwarfeng.subgrade.impl.dao.HibernatePresetLookupDao;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.DaoException;
import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Repository
public class FilteredValueDaoImpl implements FilteredValueDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceValueDaoImpl.class);

    @Autowired
    private HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, FilteredValue, HibernateFilteredValue> batchBaseDao;
    @Autowired
    private HibernateEntireLookupDao<FilteredValue, HibernateFilteredValue> entireLookupDao;
    @Autowired
    private HibernatePresetLookupDao<FilteredValue, HibernateFilteredValue> presetLookupDao;
    @Autowired
    private HibernateBatchWriteDao<FilteredValue, HibernateFilteredValue> batchWriteDao;
    @Autowired
    private HibernateTemplate hibernateTemplate;
    @Autowired
    private BeanTransformer<FilteredValue, HibernateFilteredValue> beanTransformer;
    @SuppressWarnings("FieldMayBeFinal")
    @Autowired(required = false)
    private List<FilteredValueNSQLQuery> nsqlQueries = Collections.emptyList();

    @Value("${hibernate.accelerate.using_native_sql}")
    private boolean usingNativeSQL;
    @Value("${hibernate.dialect}")
    private String hibernateDialect;

    private FilteredValueNSQLQuery nsqlQuery = null;

    @PostConstruct
    public void init() {
        nsqlQuery = nsqlQueries.stream()
                .filter(generator -> generator.supportType(hibernateDialect)).findAny().orElse(null);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public LongIdKey insert(FilteredValue element) throws DaoException {
        return batchBaseDao.insert(element);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void update(FilteredValue element) throws DaoException {
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
    public FilteredValue get(LongIdKey key) throws DaoException {
        return batchBaseDao.get(key);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public List<LongIdKey> batchInsert(@SkipRecord List<FilteredValue> elements) throws DaoException {
        return batchBaseDao.batchInsert(elements);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void batchUpdate(@SkipRecord List<FilteredValue> elements) throws DaoException {
        batchBaseDao.batchUpdate(elements);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void batchDelete(@SkipRecord List<LongIdKey> keys) throws DaoException {
        batchBaseDao.batchDelete(keys);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public boolean allExists(@SkipRecord List<LongIdKey> keys) throws DaoException {
        return batchBaseDao.allExists(keys);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public boolean nonExists(@SkipRecord List<LongIdKey> keys) throws DaoException {
        return batchBaseDao.nonExists(keys);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<FilteredValue> batchGet(@SkipRecord List<LongIdKey> keys) throws DaoException {
        return batchBaseDao.batchGet(keys);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<FilteredValue> lookup() throws DaoException {
        return entireLookupDao.lookup();
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<FilteredValue> lookup(PagingInfo pagingInfo) throws DaoException {
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
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<FilteredValue> lookup(String preset, Object[] objs) throws DaoException {
        try {
            if (Objects.equals(FilteredValueMaintainService.CHILD_FOR_POINT_BETWEEN, preset) && usingNativeSQL) {
                if (Objects.isNull(nsqlQuery)) {
                    LOGGER.warn("指定的 hibernateDialect: " + hibernateDialect + ", 不受支持, 将不会使用原生SQL进行查询");
                    return presetLookupDao.lookup(preset, objs);
                }
                LOGGER.debug("使用原生SQL进行查询...");
                List<FilteredValue> filteredValues = hibernateTemplate.executeWithNativeSession(
                        session -> session.doReturningWork(connection -> {
                            try {
                                return nsqlQuery.lookupFilteredForPoint(connection, objs);
                            } catch (Exception e) {
                                LOGGER.warn("原生SQL查询返回异常", e);
                                return null;
                            }
                        })
                );
                if (Objects.nonNull(filteredValues)) {
                    return filteredValues;
                } else {
                    LOGGER.warn("原生SQL查询返回值无效, 不使用原生SQL再次进行查询...");
                    return presetLookupDao.lookup(preset, objs);
                }
            } else if (Objects.equals(FilteredValueMaintainService.CHILD_FOR_FILTER_BETWEEN, preset) && usingNativeSQL) {
                if (Objects.isNull(nsqlQuery)) {
                    LOGGER.warn("指定的 hibernateDialect: " + hibernateDialect + ", 不受支持, 将不会使用原生SQL进行查询");
                    return presetLookupDao.lookup(preset, objs);
                }
                LOGGER.debug("使用原生SQL进行查询...");
                List<FilteredValue> filteredValues = hibernateTemplate.executeWithNativeSession(
                        session -> session.doReturningWork(connection -> {
                            try {
                                return nsqlQuery.lookupFilteredForFilter(connection, objs);
                            } catch (Exception e) {
                                LOGGER.warn("原生SQL查询返回异常", e);
                                return null;
                            }
                        })
                );
                if (Objects.nonNull(filteredValues)) {
                    return filteredValues;
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
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<FilteredValue> lookup(String preset, Object[] objs, PagingInfo pagingInfo) throws DaoException {
        try {
            if (Objects.equals(FilteredValueMaintainService.CHILD_FOR_POINT_BETWEEN, preset) && usingNativeSQL) {
                if (Objects.isNull(nsqlQuery)) {
                    LOGGER.warn("指定的 hibernateDialect: " + hibernateDialect + ", 不受支持, 将不会使用原生SQL进行查询");
                    return presetLookupDao.lookup(preset, objs, pagingInfo);
                }
                LOGGER.debug("使用原生SQL进行查询...");
                List<FilteredValue> filteredValues = hibernateTemplate.executeWithNativeSession(
                        session -> session.doReturningWork(connection -> {
                            try {
                                return nsqlQuery.lookupFilteredForPoint(connection, objs, pagingInfo);
                            } catch (Exception e) {
                                LOGGER.warn("原生SQL查询返回异常", e);
                                return null;
                            }
                        })
                );
                if (Objects.nonNull(filteredValues)) {
                    return filteredValues;
                } else {
                    LOGGER.warn("原生SQL查询返回值无效, 不使用原生SQL再次进行查询...");
                    return presetLookupDao.lookup(preset, objs, pagingInfo);
                }
            } else if (Objects.equals(FilteredValueMaintainService.CHILD_FOR_FILTER_BETWEEN, preset) && usingNativeSQL) {
                if (Objects.isNull(nsqlQuery)) {
                    LOGGER.warn("指定的 hibernateDialect: " + hibernateDialect + ", 不受支持, 将不会使用原生SQL进行查询");
                    return presetLookupDao.lookup(preset, objs, pagingInfo);
                }
                LOGGER.debug("使用原生SQL进行查询...");
                List<FilteredValue> filteredValues = hibernateTemplate.executeWithNativeSession(
                        session -> session.doReturningWork(connection -> {
                            try {
                                return nsqlQuery.lookupFilteredForFilter(connection, objs, pagingInfo);
                            } catch (Exception e) {
                                LOGGER.warn("原生SQL查询返回异常", e);
                                return null;
                            }
                        })
                );
                if (Objects.nonNull(filteredValues)) {
                    return filteredValues;
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
            if (Objects.equals(FilteredValueMaintainService.CHILD_FOR_POINT_BETWEEN, preset) && usingNativeSQL) {
                if (Objects.isNull(nsqlQuery)) {
                    LOGGER.warn("指定的 hibernateDialect: " + hibernateDialect + ", 不受支持, 将不会使用原生SQL进行查询");
                    return presetLookupDao.lookupCount(preset, objs);
                }
                LOGGER.debug("使用原生SQL进行查询...");
                Integer count = hibernateTemplate.executeWithNativeSession(
                        session -> session.doReturningWork(connection -> {
                            try {
                                return nsqlQuery.lookupFilteredCountForPoint(connection, objs);
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
            } else if (Objects.equals(FilteredValueMaintainService.CHILD_FOR_FILTER_BETWEEN, preset) && usingNativeSQL) {
                if (Objects.isNull(nsqlQuery)) {
                    LOGGER.warn("指定的 hibernateDialect: " + hibernateDialect + ", 不受支持, 将不会使用原生SQL进行查询");
                    return presetLookupDao.lookupCount(preset, objs);
                }
                LOGGER.debug("使用原生SQL进行查询...");
                Integer count = hibernateTemplate.executeWithNativeSession(
                        session -> session.doReturningWork(connection -> {
                            try {
                                return nsqlQuery.lookupFilteredCountForFilter(connection, objs);
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
    public void write(FilteredValue element) throws DaoException {
        batchWriteDao.write(element);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void batchWrite(List<FilteredValue> elements) throws DaoException {
        batchWriteDao.batchWrite(elements);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public FilteredValue previous(LongIdKey pointKey, Date date) throws DaoException {
        try {
            if (Objects.isNull(nsqlQuery)) {
                LOGGER.warn("指定的 hibernateDialect: " + hibernateDialect + ", 不受支持, 将不会使用原生SQL进行查询");
                return previousByCriteria(pointKey, date);
            }
            LOGGER.debug("使用原生SQL进行查询...");
            Pair<FilteredValue, Exception> queryInfo = hibernateTemplate.executeWithNativeSession(
                    session -> session.doReturningWork(connection -> {
                        FilteredValue filteredValue = null;
                        Exception exception = null;
                        try {
                            filteredValue = nsqlQuery.previous(connection, pointKey, date);
                        } catch (Exception e) {
                            LOGGER.warn("原生SQL查询返回异常", e);
                            exception = e;
                        }
                        return Pair.of(filteredValue, exception);
                    })
            );
            assert queryInfo != null;
            if (Objects.isNull(queryInfo.getRight())) {
                return queryInfo.getLeft();
            } else {
                LOGGER.warn("原生SQL查询返回值无效, 不使用原生SQL再次进行查询...");
                return previousByCriteria(pointKey, date);
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    private FilteredValue previousByCriteria(LongIdKey pointKey, Date date) {
        DetachedCriteria criteria = DetachedCriteria.forClass(HibernateFilteredValue.class);
        if (Objects.isNull(pointKey)) {
            criteria.add(Restrictions.isNull("pointLongId"));
        } else {
            criteria.add(Restrictions.eq("pointLongId", pointKey.getLongId()));
        }
        criteria.add(Restrictions.lt("happenedDate", date));
        criteria.addOrder(Order.desc("happenedDate"));
        return hibernateTemplate.findByCriteria(criteria, 0, 1).stream().findFirst()
                .map(value -> beanTransformer.reverseTransform((HibernateFilteredValue) value)).orElse(null);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public FilteredValue rear(LongIdKey pointKey, Date date) throws DaoException {
        try {
            if (Objects.isNull(nsqlQuery)) {
                LOGGER.warn("指定的 hibernateDialect: " + hibernateDialect + ", 不受支持, 将不会使用原生SQL进行查询");
                return rearByCriteria(pointKey, date);
            }
            LOGGER.debug("使用原生SQL进行查询...");
            Pair<FilteredValue, Exception> queryInfo = hibernateTemplate.executeWithNativeSession(
                    session -> session.doReturningWork(connection -> {
                        FilteredValue filteredValue = null;
                        Exception exception = null;
                        try {
                            filteredValue = nsqlQuery.rear(connection, pointKey, date);
                        } catch (Exception e) {
                            LOGGER.warn("原生SQL查询返回异常", e);
                            exception = e;
                        }
                        return Pair.of(filteredValue, exception);
                    })
            );
            assert queryInfo != null;
            if (Objects.isNull(queryInfo.getRight())) {
                return queryInfo.getLeft();
            } else {
                LOGGER.warn("原生SQL查询返回值无效, 不使用原生SQL再次进行查询...");
                return rearByCriteria(pointKey, date);
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    private FilteredValue rearByCriteria(LongIdKey pointKey, Date date) {
        DetachedCriteria criteria = DetachedCriteria.forClass(HibernateFilteredValue.class);
        if (Objects.isNull(pointKey)) {
            criteria.add(Restrictions.isNull("pointLongId"));
        } else {
            criteria.add(Restrictions.eq("pointLongId", pointKey.getLongId()));
        }
        criteria.add(Restrictions.gt("happenedDate", date));
        criteria.addOrder(Order.asc("happenedDate"));
        return hibernateTemplate.findByCriteria(criteria, 0, 1).stream().findFirst()
                .map(value -> beanTransformer.reverseTransform((HibernateFilteredValue) value)).orElse(null);
    }
}
