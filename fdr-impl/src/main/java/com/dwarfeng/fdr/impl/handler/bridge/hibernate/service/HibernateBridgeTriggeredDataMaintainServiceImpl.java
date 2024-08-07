package com.dwarfeng.fdr.impl.handler.bridge.hibernate.service;

import com.dwarfeng.fdr.impl.handler.bridge.hibernate.bean.HibernateBridgeTriggeredData;
import com.dwarfeng.subgrade.impl.service.DaoOnlyBatchCrudService;
import com.dwarfeng.subgrade.impl.service.DaoOnlyBatchWriteService;
import com.dwarfeng.subgrade.impl.service.DaoOnlyEntireLookupService;
import com.dwarfeng.subgrade.impl.service.DaoOnlyPresetLookupService;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class HibernateBridgeTriggeredDataMaintainServiceImpl implements HibernateBridgeTriggeredDataMaintainService {

    private final DaoOnlyBatchCrudService<LongIdKey, HibernateBridgeTriggeredData> batchCrudService;
    private final DaoOnlyEntireLookupService<HibernateBridgeTriggeredData> entireLookupService;
    private final DaoOnlyPresetLookupService<HibernateBridgeTriggeredData> presetLookupService;
    private final DaoOnlyBatchWriteService<LongIdKey, HibernateBridgeTriggeredData> batchWriteService;

    public HibernateBridgeTriggeredDataMaintainServiceImpl(
            DaoOnlyBatchCrudService<LongIdKey, HibernateBridgeTriggeredData> batchCrudService,
            DaoOnlyEntireLookupService<HibernateBridgeTriggeredData> entireLookupService,
            DaoOnlyPresetLookupService<HibernateBridgeTriggeredData> presetLookupService,
            DaoOnlyBatchWriteService<LongIdKey, HibernateBridgeTriggeredData> batchWriteService
    ) {
        this.batchCrudService = batchCrudService;
        this.entireLookupService = entireLookupService;
        this.presetLookupService = presetLookupService;
        this.batchWriteService = batchWriteService;
    }

    @Override
    @BehaviorAnalyse
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            readOnly = true,
            rollbackFor = Exception.class
    )
    public boolean exists(LongIdKey key) throws ServiceException {
        return batchCrudService.exists(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            readOnly = true,
            rollbackFor = Exception.class
    )
    public HibernateBridgeTriggeredData get(LongIdKey key) throws ServiceException {
        return batchCrudService.get(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            rollbackFor = Exception.class
    )
    public LongIdKey insert(HibernateBridgeTriggeredData element) throws ServiceException {
        return batchCrudService.insert(element);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            rollbackFor = Exception.class
    )
    public void update(HibernateBridgeTriggeredData element) throws ServiceException {
        batchCrudService.update(element);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            rollbackFor = Exception.class
    )
    public void delete(LongIdKey key) throws ServiceException {
        batchCrudService.delete(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            readOnly = true,
            rollbackFor = Exception.class
    )
    public HibernateBridgeTriggeredData getIfExists(LongIdKey key) throws ServiceException {
        return batchCrudService.getIfExists(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            rollbackFor = Exception.class
    )
    public LongIdKey insertIfNotExists(HibernateBridgeTriggeredData element) throws ServiceException {
        return batchCrudService.insertIfNotExists(element);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            rollbackFor = Exception.class
    )
    public void updateIfExists(HibernateBridgeTriggeredData element) throws ServiceException {
        batchCrudService.updateIfExists(element);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            rollbackFor = Exception.class
    )
    public void deleteIfExists(LongIdKey key) throws ServiceException {
        batchCrudService.deleteIfExists(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            rollbackFor = Exception.class
    )
    public LongIdKey insertOrUpdate(HibernateBridgeTriggeredData element) throws ServiceException {
        return batchCrudService.insertOrUpdate(element);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            readOnly = true,
            rollbackFor = Exception.class
    )
    public boolean allExists(@SkipRecord List<LongIdKey> keys) throws ServiceException {
        return batchCrudService.allExists(keys);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            readOnly = true,
            rollbackFor = Exception.class
    )
    public boolean nonExists(@SkipRecord List<LongIdKey> keys) throws ServiceException {
        return batchCrudService.nonExists(keys);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            readOnly = true,
            rollbackFor = Exception.class
    )
    public List<HibernateBridgeTriggeredData> batchGet(
            @SkipRecord List<LongIdKey> keys
    ) throws ServiceException {
        return batchCrudService.batchGet(keys);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            rollbackFor = Exception.class
    )
    public List<LongIdKey> batchInsert(
            @SkipRecord List<HibernateBridgeTriggeredData> elements
    ) throws ServiceException {
        return batchCrudService.batchInsert(elements);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            rollbackFor = Exception.class
    )
    public void batchUpdate(@SkipRecord List<HibernateBridgeTriggeredData> elements) throws ServiceException {
        batchCrudService.batchUpdate(elements);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            rollbackFor = Exception.class
    )
    public void batchDelete(@SkipRecord List<LongIdKey> keys) throws ServiceException {
        batchCrudService.batchDelete(keys);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            readOnly = true,
            rollbackFor = Exception.class
    )
    public List<HibernateBridgeTriggeredData> batchGetIfExists(
            @SkipRecord List<LongIdKey> keys
    ) throws ServiceException {
        return batchCrudService.batchGetIfExists(keys);
    }

    @Deprecated
    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            rollbackFor = Exception.class
    )
    public List<LongIdKey> batchInsertIfExists(
            @SkipRecord List<HibernateBridgeTriggeredData> elements
    ) throws ServiceException {
        return batchCrudService.batchInsertIfExists(elements);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            rollbackFor = Exception.class
    )
    public List<LongIdKey> batchInsertIfNotExists(
            @SkipRecord List<HibernateBridgeTriggeredData> elements
    ) throws ServiceException {
        return batchCrudService.batchInsertIfNotExists(elements);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            rollbackFor = Exception.class
    )
    public void batchUpdateIfExists(@SkipRecord List<HibernateBridgeTriggeredData> elements) throws ServiceException {
        batchCrudService.batchUpdateIfExists(elements);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            rollbackFor = Exception.class
    )
    public void batchDeleteIfExists(@SkipRecord List<LongIdKey> keys) throws ServiceException {
        batchCrudService.batchDeleteIfExists(keys);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            rollbackFor = Exception.class
    )
    public List<LongIdKey> batchInsertOrUpdate(
            @SkipRecord List<HibernateBridgeTriggeredData> elements
    ) throws ServiceException {
        return batchCrudService.batchInsertOrUpdate(elements);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            readOnly = true,
            rollbackFor = Exception.class
    )
    public PagedData<HibernateBridgeTriggeredData> lookup() throws ServiceException {
        return entireLookupService.lookup();
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            readOnly = true,
            rollbackFor = Exception.class
    )
    public PagedData<HibernateBridgeTriggeredData> lookup(PagingInfo pagingInfo) throws ServiceException {
        return entireLookupService.lookup(pagingInfo);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            readOnly = true,
            rollbackFor = Exception.class
    )
    public List<HibernateBridgeTriggeredData> lookupAsList() throws ServiceException {
        return entireLookupService.lookupAsList();
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            readOnly = true,
            rollbackFor = Exception.class
    )
    public List<HibernateBridgeTriggeredData> lookupAsList(PagingInfo pagingInfo) throws ServiceException {
        return entireLookupService.lookupAsList(pagingInfo);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            readOnly = true,
            rollbackFor = Exception.class
    )
    public HibernateBridgeTriggeredData lookupFirst() throws ServiceException {
        return entireLookupService.lookupFirst();
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            readOnly = true,
            rollbackFor = Exception.class
    )
    public PagedData<HibernateBridgeTriggeredData> lookup(String preset, Object[] objs) throws ServiceException {
        return presetLookupService.lookup(preset, objs);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            readOnly = true,
            rollbackFor = Exception.class
    )
    public PagedData<HibernateBridgeTriggeredData> lookup(
            String preset, Object[] objs, PagingInfo pagingInfo
    ) throws ServiceException {
        return presetLookupService.lookup(preset, objs, pagingInfo);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            readOnly = true,
            rollbackFor = Exception.class
    )
    public List<HibernateBridgeTriggeredData> lookupAsList(String preset, Object[] objs) throws ServiceException {
        return presetLookupService.lookupAsList(preset, objs);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            readOnly = true,
            rollbackFor = Exception.class
    )
    public List<HibernateBridgeTriggeredData> lookupAsList(
            String preset, Object[] objs, PagingInfo pagingInfo
    ) throws ServiceException {
        return presetLookupService.lookupAsList(preset, objs, pagingInfo);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            readOnly = true,
            rollbackFor = Exception.class
    )
    public HibernateBridgeTriggeredData lookupFirst(String preset, Object[] objs) throws ServiceException {
        return presetLookupService.lookupFirst(preset, objs);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            rollbackFor = Exception.class
    )
    public void write(HibernateBridgeTriggeredData element) throws ServiceException {
        batchWriteService.write(element);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            rollbackFor = Exception.class
    )
    public void batchWrite(@SkipRecord List<HibernateBridgeTriggeredData> elements) throws ServiceException {
        batchWriteService.batchWrite(elements);
    }

    /**
     * @since 2.2.0
     */
    @Override
    @BehaviorAnalyse
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            rollbackFor = Exception.class
    )
    public int lookupCount() throws ServiceException {
        return entireLookupService.lookupCount();
    }

    /**
     * @since 2.2.0
     */
    @Override
    @BehaviorAnalyse
    @Transactional(
            transactionManager = "hibernateBridge.hibernateTransactionManager",
            rollbackFor = Exception.class
    )
    public int lookupCount(String preset, Object[] objs) throws ServiceException {
        return presetLookupService.lookupCount(preset, objs);
    }
}
