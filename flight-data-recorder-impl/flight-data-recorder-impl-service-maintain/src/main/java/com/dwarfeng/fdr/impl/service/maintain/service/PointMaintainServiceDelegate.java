package com.dwarfeng.fdr.impl.service.maintain.service;

import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.sdk.util.ServiceExceptionCodes;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.dto.PagedData;
import com.dwarfeng.fdr.stack.bean.entity.FilterInfo;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.entity.TriggerInfo;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.cache.*;
import com.dwarfeng.fdr.stack.dao.*;
import com.dwarfeng.fdr.stack.exception.CacheException;
import com.dwarfeng.fdr.stack.exception.DaoException;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import com.dwarfeng.fdr.stack.handler.ValidationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Validated
public class PointMaintainServiceDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(PointMaintainServiceDelegate.class);

    @Autowired
    private PointDao pointDao;
    @Autowired
    private PointCache pointCache;
    @Autowired
    private CategoryHasPointCache categoryHasPointCache;
    @Autowired
    private PointHasFilterInfoCache pointHasFilterInfoCache;
    @Autowired
    private PointHasTriggerInfoCache pointHasTriggerInfoCache;
    @Autowired
    private ValidationHandler validationHandler;

    @Autowired
    private FilterInfoDao filterInfoDao;
    @Autowired
    private FilterInfoCache filterInfoCache;
    @Autowired
    private TriggerInfoDao triggerInfoDao;
    @Autowired
    private TriggerInfoCache triggerInfoCache;
    @Autowired
    private PersistenceValueDao persistenceValueDao;
    @Autowired
    private PersistenceValueCache persistenceValueCache;
    @Autowired
    private RealtimeValueDao realtimeValueDao;
    @Autowired
    private RealtimeValueCache realtimeValueCache;
    @Autowired
    private FilteredValueDao filteredValueDao;
    @Autowired
    private FilteredValueCache filteredValueCache;
    @Autowired
    private TriggeredValueDao triggeredValueDao;
    @Autowired
    private TriggeredValueCache triggeredValueCache;

    @Value("${cache.timeout.entity.point}")
    private long pointTimeout;
    @Value("${cache.timeout.one_to_many.category_has_point}")
    private long pointHasChildTimeout;
    @Value("${cache.batch_fetch_size.point}")
    private int pointFetchSize;

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public Point get(@NotNull UuidKey key) throws ServiceException {
        try {
            validationHandler.uuidKeyValidation(key);
            return internalGet(key);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    private Point internalGet(UuidKey key) throws CacheException, DaoException {
        if (pointCache.exists(key)) {
            LOGGER.debug("在缓存中发现了 " + key.toString() + " 对应的值，直接返回该值...");
            return pointCache.get(key);
        } else {
            LOGGER.debug("未能在缓存中发现 " + key.toString() + " 对应的值，读取数据访问层...");
            Point point = pointDao.get(key);
            LOGGER.debug("将读取到的值 " + point.toString() + " 回写到缓存中...");
            pointCache.push(key, point, pointTimeout);
            return point;
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public UuidKey insert(@NotNull Point point) throws ServiceException {
        try {
            validationHandler.pointValidation(point);

            if (pointCache.exists(point.getKey()) || pointDao.exists(point.getKey())) {
                LOGGER.debug("指定的实体 " + point.toString() + " 已经存在，无法插入...");
                throw new IllegalStateException("指定的实体 " + point.toString() + " 已经存在，无法插入...");
            } else {
                LOGGER.debug("将指定的实体 " + point.toString() + " 插入数据访问层中...");
                pointDao.insert(point);
                LOGGER.debug("将指定的实体 " + point.toString() + " 插入缓存中...");
                pointCache.push(point.getKey(), point, pointTimeout);
                if (Objects.nonNull(point.getCategoryKey())) {
                    LOGGER.debug("清除实体 " + point.toString() + " 对应的父项缓存...");
                    categoryHasPointCache.delete(point.getCategoryKey());
                }
                return point.getKey();
            }
        } catch (Exception e) {
            throw new ServiceException(ServiceExceptionCodes.UNDEFINE, e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public UuidKey update(@NotNull Point point) throws ServiceException {
        try {
            validationHandler.pointValidation(point);

            if (!pointCache.exists(point.getKey()) && !pointDao.exists(point.getKey())) {
                LOGGER.debug("指定的实体 " + point.toString() + " 已经存在，无法更新...");
                throw new IllegalStateException("指定的实体 " + point.toString() + " 已经存在，无法更新...");
            } else {
                Point oldPoint = internalGet(point.getKey());
                if (Objects.nonNull(oldPoint.getCategoryKey())) {
                    LOGGER.debug("清除旧实体 " + oldPoint.toString() + " 对应的父项缓存...");
                    categoryHasPointCache.delete(oldPoint.getCategoryKey());
                }
                LOGGER.debug("将指定的实体 " + point.toString() + " 插入数据访问层中...");
                pointDao.update(point);
                LOGGER.debug("将指定的实体 " + point.toString() + " 插入缓存中...");
                pointCache.push(point.getKey(), point, pointTimeout);
                if (Objects.nonNull(point.getCategoryKey())) {
                    LOGGER.debug("清除新实体 " + point.toString() + " 对应的父项缓存...");
                    categoryHasPointCache.delete(point.getCategoryKey());
                }
                return point.getKey();
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void delete(@NotNull UuidKey key) throws ServiceException {
        try {
            validationHandler.uuidKeyValidation(key);

            if (!pointCache.exists(key) && !pointDao.exists(key)) {
                LOGGER.debug("指定的键 " + key.toString() + " 不存在，无法删除...");
                throw new IllegalStateException("指定的键 " + key.toString() + " 不存在，无法删除...");
            } else {
                Point oldPoint = internalGet(key);
                if (Objects.nonNull(oldPoint.getCategoryKey())) {
                    LOGGER.debug("清除旧实体 " + oldPoint.toString() + " 对应的父项缓存...");
                    categoryHasPointCache.delete(oldPoint.getCategoryKey());
                }

                LOGGER.debug("查询数据点 " + key.toString() + " 对应的子项过滤器信息...");
                Set<UuidKey> filterInfos2Delete = filterInfoDao.getFilterInfos(key, LookupPagingInfo.LOOKUP_ALL)
                        .stream().map(FilterInfo::getKey).collect(Collectors.toSet());
                LOGGER.debug("查询数据点 " + key.toString() + " 对应的子项触发器信息...");
                Set<UuidKey> triggerInfos2Delete = triggerInfoDao.getTriggerInfos(key, LookupPagingInfo.LOOKUP_ALL)
                        .stream().map(TriggerInfo::getKey).collect(Collectors.toSet());

                LOGGER.debug("清除数据点 " + key.toString() + " 对应的子项缓存...");
                pointHasFilterInfoCache.delete(key);
                pointHasTriggerInfoCache.delete(key);

                LOGGER.debug("删除数据点 " + key.toString() + " 相关联的被过滤数据与相关缓存...");
                for (UuidKey filterInfoKey : filterInfos2Delete) {
                    filteredValueCache.deleteAllByFilterInfo(filterInfoKey);
                    filteredValueDao.deleteAllByFilterInfo(filterInfoKey);
                }
                filteredValueCache.deleteAllByPoint(key);
                filteredValueDao.deleteAllByPoint(key);
                LOGGER.debug("删除数据点 " + key.toString() + " 相关联的被触发数据与相关缓存...");
                for (UuidKey triggerInfoKey : triggerInfos2Delete) {
                    triggeredValueCache.deleteAllByTriggerInfo(triggerInfoKey);
                    triggeredValueDao.deleteAllByTriggerInfo(triggerInfoKey);
                }
                triggeredValueCache.deleteAllByPoint(key);
                triggeredValueDao.deleteAllByPoint(key);
                LOGGER.debug("删除数据点 " + key.toString() + " 相关联的持久化数据与相关缓存...");
                persistenceValueCache.deleteAll(key);
                persistenceValueDao.deleteAll(key);
                LOGGER.debug("删除数据点 " + key.toString() + " 相关联的实时数据与相关缓存...");
                if (realtimeValueDao.exists(key)) {
                    realtimeValueCache.delete(key);
                    realtimeValueDao.delete(key);
                }

                LOGGER.debug("删除数据点 " + key.toString() + " 相关联的过滤器信息与缓存...");
                for (UuidKey filterInfoKey : filterInfos2Delete) {
                    filterInfoCache.delete(filterInfoKey);
                    filterInfoDao.delete(filterInfoKey);
                }
                LOGGER.debug("删除数据点 " + key.toString() + " 相关联的触发器信息与缓存...");
                for (UuidKey triggerInfoKey : triggerInfos2Delete) {
                    triggerInfoCache.delete(triggerInfoKey);
                    triggerInfoDao.delete(triggerInfoKey);
                }

                LOGGER.debug("删除数据点 " + key.toString() + " 与缓存...");
                pointCache.delete(key);
                pointDao.delete(key);
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }


    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public PagedData<Point> getPoints(UuidKey categoryUuid, LookupPagingInfo lookupPagingInfo) throws ServiceException {
        try {
            validationHandler.uuidKeyValidation(categoryUuid);
            validationHandler.lookupPagingInfoValidation(lookupPagingInfo);

            //定义中间变量。
            List<Point> categories;
            long count;

            if (categoryHasPointCache.exists(categoryUuid)) {
                LOGGER.debug("在缓存中发现了 " + categoryUuid.toString() + " 对应的子项列表，直接返回缓存中的值...");
                categories = categoryHasPointCache.get(categoryUuid, lookupPagingInfo.getPage() * lookupPagingInfo.getRows(), lookupPagingInfo.getRows());
                count = categoryHasPointCache.size(categoryUuid);
            } else {
                LOGGER.debug("查询指定的Point对应的子项...");
                categories = pointDao.getPoints(categoryUuid, lookupPagingInfo);
                count = pointDao.getPointCount(categoryUuid);
                if (count > 0) {
                    for (Point point : categories) {
                        LOGGER.debug("将查询到的的实体 " + point.toString() + " 插入缓存中...");
                        pointCache.push(point.getKey(), point, pointTimeout);
                    }
                    LOGGER.debug("抓取实体 " + categoryUuid.toString() + " 对应的子项并插入缓存...");
                    fetchPoint2Cache(categoryUuid);
                }
            }
            return new PagedData<>(
                    lookupPagingInfo.getPage(),
                    Math.max((int) Math.ceil((double) count / lookupPagingInfo.getRows()), 1),
                    lookupPagingInfo.getRows(),
                    count,
                    categories
            );
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }


    @Transactional(transactionManager = "hibernateTransactionManager")
    @TimeAnalyse
    @Async
    public void fetchPoint2Cache(UuidKey uuidKey) {
        try {
            int totlePage;
            int currPage;
            long count = pointDao.getPointCount(uuidKey);
            totlePage = Math.max((int) Math.ceil((double) count / pointFetchSize), 1);
            currPage = 0;
            categoryHasPointCache.delete(uuidKey);
            do {
                LookupPagingInfo lookupPagingInfo = new LookupPagingInfo(true, currPage++, pointFetchSize);
                List<Point> childs = pointDao.getPoints(uuidKey, lookupPagingInfo);
                if (childs.size() > 0) {
                    categoryHasPointCache.push(uuidKey, childs, pointHasChildTimeout);
                }
            } while (currPage < totlePage);
        } catch (Exception e) {
            LOGGER.warn("将分类 " + uuidKey.toString() + " 的子项添加进入缓存时发生异常，异常信息如下", e);
        }
    }
}