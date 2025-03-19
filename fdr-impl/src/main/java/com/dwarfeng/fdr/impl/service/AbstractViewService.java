package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.dto.*;
import com.dwarfeng.fdr.stack.handler.ViewHandler;
import com.dwarfeng.fdr.stack.service.ViewService;
import com.dwarfeng.fdr.stack.struct.Data;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 观察服务的抽象实现。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public abstract class AbstractViewService<D extends Data> implements ViewService<D> {

    protected final ViewHandler<D> viewHandler;

    protected final ServiceExceptionMapper sem;

    protected AbstractViewService(ViewHandler<D> viewHandler, ServiceExceptionMapper sem) {
        this.viewHandler = viewHandler;
        this.sem = sem;
    }

    @BehaviorAnalyse
    @SkipRecord
    @Override
    public D latest(LongIdKey pointKey) throws ServiceException {
        try {
            return viewHandler.latest(pointKey);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查询数据点的最新数据时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @BehaviorAnalyse
    @SkipRecord
    @Override
    public List<D> latest(@SkipRecord List<LongIdKey> pointKeys) throws ServiceException {
        try {
            return viewHandler.latest(pointKeys);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查询数据点的最新数据时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @BehaviorAnalyse
    @SkipRecord
    @Override
    public LookupResult<D> lookup(LookupInfo lookupInfo) throws ServiceException {
        try {
            return viewHandler.lookup(lookupInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查看时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @BehaviorAnalyse
    @SkipRecord
    @Override
    public List<LookupResult<D>> lookup(@SkipRecord List<LookupInfo> lookupInfos) throws ServiceException {
        try {
            return viewHandler.lookup(lookupInfos);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查看时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @BehaviorAnalyse
    @SkipRecord
    @Override
    public QueryResult query(QueryInfo queryInfo) throws ServiceException {
        try {
            return viewHandler.query(queryInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查询时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @BehaviorAnalyse
    @SkipRecord
    @Override
    public List<QueryResult> query(@SkipRecord List<QueryInfo> queryInfos) throws ServiceException {
        try {
            return viewHandler.query(queryInfos);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查询时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @BehaviorAnalyse
    @SkipRecord
    @Override
    public QueryResult nativeQuery(NativeQueryInfo queryInfo) throws ServiceException {
        try {
            return viewHandler.nativeQuery(queryInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("原生查询时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @BehaviorAnalyse
    @SkipRecord
    @Override
    public List<QueryResult> nativeQuery(@SkipRecord List<NativeQueryInfo> queryInfos) throws ServiceException {
        try {
            return viewHandler.nativeQuery(queryInfos);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("原生查询时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @BehaviorAnalyse
    @SkipRecord
    @Override
    public CompletableFuture<D> latestAsync(LongIdKey pointKey) throws ServiceException {
        try {
            return viewHandler.latestAsync(pointKey);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查询数据点的最新数据时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @BehaviorAnalyse
    @SkipRecord
    @Override
    public CompletableFuture<List<D>> latestAsync(@SkipRecord List<LongIdKey> pointKeys) throws ServiceException {
        try {
            return viewHandler.latestAsync(pointKeys);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查询数据点的最新数据时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @BehaviorAnalyse
    @SkipRecord
    @Override
    public CompletableFuture<LookupResult<D>> lookupAsync(LookupInfo lookupInfo) throws ServiceException {
        try {
            return viewHandler.lookupAsync(lookupInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查看时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @BehaviorAnalyse
    @SkipRecord
    @Override
    public CompletableFuture<List<LookupResult<D>>> lookupAsync(@SkipRecord List<LookupInfo> lookupInfos) throws ServiceException {
        try {
            return viewHandler.lookupAsync(lookupInfos);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查看时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @BehaviorAnalyse
    @SkipRecord
    @Override
    public CompletableFuture<QueryResult> nativeQueryAsync(NativeQueryInfo queryInfo) throws ServiceException {
        try {
            return viewHandler.nativeQueryAsync(queryInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("原生查询时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @BehaviorAnalyse
    @SkipRecord
    @Override
    public CompletableFuture<List<QueryResult>> nativeQueryAsync(List<NativeQueryInfo> queryInfos)
            throws ServiceException {
        try {
            return viewHandler.nativeQueryAsync(queryInfos);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("原生查询时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @BehaviorAnalyse
    @SkipRecord
    @Override
    public CompletableFuture<QueryResult> queryAsync(QueryInfo queryInfo) throws ServiceException {
        try {
            return viewHandler.queryAsync(queryInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查询时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @BehaviorAnalyse
    @SkipRecord
    @Override
    public CompletableFuture<List<QueryResult>> queryAsync(@SkipRecord List<QueryInfo> queryInfos) throws ServiceException {
        try {
            return viewHandler.queryAsync(queryInfos);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查询时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public String toString() {
        return "AbstractViewService{" +
                "viewHandler=" + viewHandler +
                ", sem=" + sem +
                '}';
    }
}
