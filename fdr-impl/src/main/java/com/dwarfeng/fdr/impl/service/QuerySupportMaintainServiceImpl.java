package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.sdk.util.Constants;
import com.dwarfeng.fdr.stack.bean.entity.QuerySupport;
import com.dwarfeng.fdr.stack.bean.key.QuerySupportKey;
import com.dwarfeng.fdr.stack.handler.FilteredPersistHandler;
import com.dwarfeng.fdr.stack.handler.NormalPersistHandler;
import com.dwarfeng.fdr.stack.handler.TriggeredPersistHandler;
import com.dwarfeng.fdr.stack.service.QuerySupportMaintainService;
import com.dwarfeng.subgrade.impl.service.DaoOnlyEntireLookupService;
import com.dwarfeng.subgrade.impl.service.DaoOnlyPresetLookupService;
import com.dwarfeng.subgrade.impl.service.GeneralBatchCrudService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuerySupportMaintainServiceImpl implements QuerySupportMaintainService {

    private final GeneralBatchCrudService<QuerySupportKey, QuerySupport> crudService;
    private final DaoOnlyEntireLookupService<QuerySupport> entireLookupService;
    private final DaoOnlyPresetLookupService<QuerySupport> presetLookupService;

    private final NormalPersistHandler normalPersistHandler;
    private final FilteredPersistHandler filteredPersistHandler;
    private final TriggeredPersistHandler triggeredPersistHandler;

    private final ServiceExceptionMapper sem;

    public QuerySupportMaintainServiceImpl(
            GeneralBatchCrudService<QuerySupportKey, QuerySupport> crudService,
            DaoOnlyEntireLookupService<QuerySupport> entireLookupService,
            DaoOnlyPresetLookupService<QuerySupport> presetLookupService,
            NormalPersistHandler normalPersistHandler,
            FilteredPersistHandler filteredPersistHandler,
            TriggeredPersistHandler triggeredPersistHandler,
            ServiceExceptionMapper sem
    ) {
        this.crudService = crudService;
        this.entireLookupService = entireLookupService;
        this.presetLookupService = presetLookupService;
        this.normalPersistHandler = normalPersistHandler;
        this.filteredPersistHandler = filteredPersistHandler;
        this.triggeredPersistHandler = triggeredPersistHandler;
        this.sem = sem;
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public boolean exists(QuerySupportKey key) throws ServiceException {
        return crudService.exists(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public QuerySupport get(QuerySupportKey key) throws ServiceException {
        return crudService.get(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public QuerySupportKey insert(QuerySupport element) throws ServiceException {
        return crudService.insert(element);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void update(QuerySupport element) throws ServiceException {
        crudService.update(element);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void delete(QuerySupportKey key) throws ServiceException {
        crudService.delete(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public QuerySupport getIfExists(QuerySupportKey key) throws ServiceException {
        return crudService.getIfExists(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public QuerySupportKey insertIfNotExists(QuerySupport element) throws ServiceException {
        return crudService.insertIfNotExists(element);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void updateIfExists(QuerySupport element) throws ServiceException {
        crudService.updateIfExists(element);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void deleteIfExists(QuerySupportKey key) throws ServiceException {
        crudService.deleteIfExists(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public QuerySupportKey insertOrUpdate(QuerySupport element) throws ServiceException {
        return crudService.insertOrUpdate(element);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public boolean allExists(@SkipRecord List<QuerySupportKey> keys) throws ServiceException {
        return crudService.allExists(keys);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public boolean nonExists(@SkipRecord List<QuerySupportKey> keys) throws ServiceException {
        return crudService.nonExists(keys);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<QuerySupport> batchGet(@SkipRecord List<QuerySupportKey> keys) throws ServiceException {
        return crudService.batchGet(keys);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public List<QuerySupportKey> batchInsert(@SkipRecord List<QuerySupport> elements) throws ServiceException {
        return crudService.batchInsert(elements);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void batchUpdate(@SkipRecord List<QuerySupport> elements) throws ServiceException {
        crudService.batchUpdate(elements);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void batchDelete(@SkipRecord List<QuerySupportKey> keys) throws ServiceException {
        crudService.batchDelete(keys);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<QuerySupport> batchGetIfExists(@SkipRecord List<QuerySupportKey> keys) throws ServiceException {
        return crudService.batchGetIfExists(keys);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public List<QuerySupportKey> batchInsertIfExists(@SkipRecord List<QuerySupport> elements) throws ServiceException {
        return crudService.batchInsertIfExists(elements);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void batchUpdateIfExists(@SkipRecord List<QuerySupport> elements) throws ServiceException {
        crudService.batchUpdateIfExists(elements);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void batchDeleteIfExists(@SkipRecord List<QuerySupportKey> keys) throws ServiceException {
        crudService.batchDeleteIfExists(keys);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public List<QuerySupportKey> batchInsertOrUpdate(@SkipRecord List<QuerySupport> elements) throws ServiceException {
        return crudService.batchInsertOrUpdate(elements);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public PagedData<QuerySupport> lookup() throws ServiceException {
        return entireLookupService.lookup();
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public PagedData<QuerySupport> lookup(PagingInfo pagingInfo) throws ServiceException {
        return entireLookupService.lookup(pagingInfo);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<QuerySupport> lookupAsList() throws ServiceException {
        return entireLookupService.lookupAsList();
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<QuerySupport> lookupAsList(PagingInfo pagingInfo) throws ServiceException {
        return entireLookupService.lookupAsList(pagingInfo);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public PagedData<QuerySupport> lookup(String preset, Object[] objs) throws ServiceException {
        return presetLookupService.lookup(preset, objs);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public PagedData<QuerySupport> lookup(String preset, Object[] objs, PagingInfo pagingInfo) throws ServiceException {
        return presetLookupService.lookup(preset, objs, pagingInfo);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<QuerySupport> lookupAsList(String preset, Object[] objs) throws ServiceException {
        return presetLookupService.lookupAsList(preset, objs);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<QuerySupport> lookupAsList(String preset, Object[] objs, PagingInfo pagingInfo) throws ServiceException {
        return presetLookupService.lookupAsList(preset, objs, pagingInfo);
    }

    @Override
    @BehaviorAnalyse
    public void reset() throws ServiceException {
        try {
            List<QuerySupportKey> querySupportKeys = entireLookupService.lookup().getData().stream()
                    .map(QuerySupport::getKey).collect(Collectors.toList());
            crudService.batchDelete(querySupportKeys);

            List<QuerySupport> querySupports;
            // 一般。
            querySupports = normalPersistHandler.queryGuides().stream().map(guide -> new QuerySupport(
                    new QuerySupportKey(Constants.QUERY_SUPPORT_CATEGORY_NORMAL, guide.getPreset()),
                    guide.getExampleParams(),
                    guide.getDescription()
            )).collect(Collectors.toList());
            crudService.batchInsert(querySupports);
            // 被过滤。
            querySupports = filteredPersistHandler.queryGuides().stream().map(guide -> new QuerySupport(
                    new QuerySupportKey(Constants.QUERY_SUPPORT_CATEGORY_FILTERED, guide.getPreset()),
                    guide.getExampleParams(),
                    guide.getDescription()
            )).collect(Collectors.toList());
            crudService.batchInsert(querySupports);
            // 被触发。
            querySupports = triggeredPersistHandler.queryGuides().stream().map(guide -> new QuerySupport(
                    new QuerySupportKey(Constants.QUERY_SUPPORT_CATEGORY_TRIGGERED, guide.getPreset()),
                    guide.getExampleParams(),
                    guide.getDescription()
            )).collect(Collectors.toList());
            crudService.batchInsert(querySupports);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow(
                    "重置查询支持时发生异常", LogLevel.WARN, sem, e
            );
        }
    }
}
