package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.dto.LookupInfo;
import com.dwarfeng.fdr.stack.bean.dto.LookupResult;
import com.dwarfeng.fdr.stack.bean.dto.QueryInfo;
import com.dwarfeng.fdr.stack.bean.dto.QueryResult;
import com.dwarfeng.fdr.stack.handler.WatchHandler;
import com.dwarfeng.fdr.stack.service.WatchService;
import com.dwarfeng.fdr.stack.struct.Data;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
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
public abstract class AbstractWatchService<D extends Data> implements WatchService<D> {

    protected final WatchHandler<D> watchHandler;

    protected final ServiceExceptionMapper sem;

    protected AbstractWatchService(WatchHandler<D> watchHandler, ServiceExceptionMapper sem) {
        this.watchHandler = watchHandler;
        this.sem = sem;
    }

    @Override
    public D inspect(LongIdKey pointKey) throws ServiceException {
        try {
            return watchHandler.inspect(pointKey);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询数据时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    public List<D> inspect(List<LongIdKey> pointKeys) throws ServiceException {
        try {
            return watchHandler.inspect(pointKeys);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询数据时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    public QueryResult<D> query(QueryInfo queryInfo) throws ServiceException {
        try {
            return watchHandler.query(queryInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询数据时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    public LookupResult lookup(LookupInfo lookupInfo) throws ServiceException {
        try {
            return watchHandler.lookup(lookupInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查看数据时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    public CompletableFuture<D> inspectAsync(LongIdKey pointKey) throws ServiceException {
        try {
            return watchHandler.inspectAsync(pointKey);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询数据时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    public CompletableFuture<List<D>> inspectAsync(List<LongIdKey> pointKeys) throws ServiceException {
        try {
            return watchHandler.inspectAsync(pointKeys);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询数据时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    public CompletableFuture<QueryResult<D>> queryAsync(QueryInfo queryInfo) throws ServiceException {
        try {
            return watchHandler.queryAsync(queryInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询数据时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    public CompletableFuture<LookupResult> lookupAsync(LookupInfo lookupInfo) throws ServiceException {
        try {
            return watchHandler.lookupAsync(lookupInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查看数据时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    public String toString() {
        return "AbstractWatchService{" +
                "watchHandler=" + watchHandler +
                ", sem=" + sem +
                '}';
    }
}
