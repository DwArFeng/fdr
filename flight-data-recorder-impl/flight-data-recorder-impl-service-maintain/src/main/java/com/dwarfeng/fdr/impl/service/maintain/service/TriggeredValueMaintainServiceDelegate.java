package com.dwarfeng.fdr.impl.service.maintain.service;

import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.cache.TriggeredValueCache;
import com.dwarfeng.fdr.stack.dao.TriggeredValueDao;
import com.dwarfeng.fdr.stack.exception.CacheException;
import com.dwarfeng.fdr.stack.exception.DaoException;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import com.dwarfeng.fdr.stack.handler.ValidationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Component
@Validated
public class TriggeredValueMaintainServiceDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(TriggeredValueMaintainServiceDelegate.class);

    @Autowired
    private TriggeredValueDao triggeredValueDao;
    @Autowired
    private TriggeredValueCache triggeredValueCache;
    @Autowired
    private ValidationHandler validationHandler;

    @Value("${cache.timeout.entity.triggered_value}")
    private long triggeredValueTimeout;

    @TimeAnalyse
    @Transactional(transactionManager = "daoTransactionManager", readOnly = true)
    public TriggeredValue get(@NotNull UuidKey key) throws ServiceException {
        try {
            validationHandler.uuidKeyValidation(key);
            return internalGet(key);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    private TriggeredValue internalGet(UuidKey key) throws CacheException, DaoException {
        if (triggeredValueCache.exists(key)) {
            LOGGER.debug("在缓存中发现了 " + key.toString() + " 对应的值，直接返回该值...");
            return triggeredValueCache.get(key);
        } else {
            LOGGER.debug("未能在缓存中发现 " + key.toString() + " 对应的值，读取数据访问层...");
            TriggeredValue triggeredValue = triggeredValueDao.get(key);
            LOGGER.debug("将读取到的值 " + triggeredValue.toString() + " 回写到缓存中...");
            triggeredValueCache.push(key, triggeredValue, triggeredValueTimeout);
            return triggeredValue;
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "daoTransactionManager")
    public UuidKey insert(@NotNull TriggeredValue triggeredValue) throws ServiceException {
        try {
            validationHandler.triggeredValueValidation(triggeredValue);

            if (triggeredValueCache.exists(triggeredValue.getKey()) || triggeredValueDao.exists(triggeredValue.getKey())) {
                LOGGER.debug("指定的实体 " + triggeredValue.toString() + " 已经存在，无法插入...");
                throw new IllegalStateException("指定的实体 " + triggeredValue.toString() + " 已经存在，无法插入...");
            } else {
                LOGGER.debug("将指定的实体 " + triggeredValue.toString() + " 插入数据访问层中...");
                triggeredValueDao.insert(triggeredValue);
                LOGGER.debug("将指定的实体 " + triggeredValue.toString() + " 插入缓存中...");
                triggeredValueCache.push(triggeredValue.getKey(), triggeredValue, triggeredValueTimeout);
                return triggeredValue.getKey();
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "daoTransactionManager")
    public UuidKey update(@NotNull TriggeredValue triggeredValue) throws ServiceException {
        try {
            validationHandler.triggeredValueValidation(triggeredValue);

            if (!triggeredValueCache.exists(triggeredValue.getKey()) && !triggeredValueDao.exists(triggeredValue.getKey())) {
                LOGGER.debug("指定的实体 " + triggeredValue.toString() + " 已经存在，无法更新...");
                throw new IllegalStateException("指定的实体 " + triggeredValue.toString() + " 已经存在，无法更新...");
            } else {
                TriggeredValue oldTriggeredValue = internalGet(triggeredValue.getKey());
                LOGGER.debug("将指定的实体 " + triggeredValue.toString() + " 插入数据访问层中...");
                triggeredValueDao.update(triggeredValue);
                LOGGER.debug("将指定的实体 " + triggeredValue.toString() + " 插入缓存中...");
                triggeredValueCache.push(triggeredValue.getKey(), triggeredValue, triggeredValueTimeout);
                return triggeredValue.getKey();
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @TimeAnalyse
    @Transactional(transactionManager = "daoTransactionManager")
    public void delete(@NotNull UuidKey key) throws ServiceException {
        try {
            validationHandler.uuidKeyValidation(key);

            if (!triggeredValueCache.exists(key) && !triggeredValueDao.exists(key)) {
                LOGGER.debug("指定的键 " + key.toString() + " 不存在，无法删除...");
                throw new IllegalStateException("指定的键 " + key.toString() + " 不存在，无法删除...");
            } else {
                TriggeredValue oldTriggeredValue = internalGet(key);
                LOGGER.debug("清除实体 " + key.toString() + " 对应的子项缓存...");
                LOGGER.debug("将指定的TriggeredValue从缓存中删除...");
                triggeredValueCache.delete(key);
                LOGGER.debug("将指定的TriggeredValue从数据访问层中删除...");
                triggeredValueDao.delete(key);
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
}
