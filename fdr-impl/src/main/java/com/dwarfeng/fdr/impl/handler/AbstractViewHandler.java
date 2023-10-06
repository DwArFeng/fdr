package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.bean.dto.*;
import com.dwarfeng.fdr.stack.handler.KeepHandler;
import com.dwarfeng.fdr.stack.handler.PersistHandler;
import com.dwarfeng.fdr.stack.handler.QueryHandler;
import com.dwarfeng.fdr.stack.handler.ViewHandler;
import com.dwarfeng.fdr.stack.struct.Data;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

/**
 * 观察处理器的抽象实现。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public abstract class AbstractViewHandler<D extends Data> implements ViewHandler<D> {

    protected final KeepHandler<D> keepHandler;
    protected final PersistHandler<D> persistHandler;
    protected final QueryHandler queryHandler;

    protected final ThreadPoolTaskExecutor executor;

    protected AbstractViewHandler(
            KeepHandler<D> keepHandler,
            PersistHandler<D> persistHandler,
            QueryHandler queryHandler,
            ThreadPoolTaskExecutor executor
    ) {
        this.keepHandler = keepHandler;
        this.persistHandler = persistHandler;
        this.queryHandler = queryHandler;
        this.executor = executor;
    }

    @Override
    public D latest(LongIdKey pointKey) throws HandlerException {
        return keepHandler.latest(pointKey);
    }

    @Override
    public List<D> latest(List<LongIdKey> pointKeys) throws HandlerException {
        return keepHandler.latest(pointKeys);
    }

    @Override
    public LookupResult<D> lookup(LookupInfo lookupInfo) throws HandlerException {
        return persistHandler.lookup(lookupInfo);
    }

    @Override
    public List<LookupResult<D>> lookup(List<LookupInfo> lookupInfos) throws HandlerException {
        return persistHandler.lookup(lookupInfos);
    }

    @Override
    public QueryResult nativeQuery(NativeQueryInfo queryInfo) throws HandlerException {
        return persistHandler.nativeQuery(queryInfo);
    }

    @Override
    public List<QueryResult> nativeQuery(List<NativeQueryInfo> queryInfos) throws HandlerException {
        return persistHandler.nativeQuery(queryInfos);
    }

    @Override
    public QueryResult query(QueryInfo queryInfo) throws HandlerException {
        return queryHandler.query(queryInfo);
    }

    @Override
    public List<QueryResult> query(List<QueryInfo> queryInfos) throws HandlerException {
        return queryHandler.query(queryInfos);
    }

    @Override
    public CompletableFuture<D> latestAsync(LongIdKey pointKey) {
        return CompletableFuture.supplyAsync(() -> wrappedLatest(pointKey), executor);
    }

    private D wrappedLatest(LongIdKey pointKey) throws CompletionException {
        try {
            return keepHandler.latest(pointKey);
        } catch (HandlerException e) {
            throw new CompletionException(e);
        }
    }

    @Override
    public CompletableFuture<List<D>> latestAsync(List<LongIdKey> pointKeys) {
        return CompletableFuture.supplyAsync(() -> wrappedLatest(pointKeys), executor);
    }

    private List<D> wrappedLatest(List<LongIdKey> pointKeys) throws CompletionException {
        try {
            return keepHandler.latest(pointKeys);
        } catch (HandlerException e) {
            throw new CompletionException(e);
        }
    }

    @Override
    public CompletableFuture<LookupResult<D>> lookupAsync(LookupInfo lookupInfo) {
        return CompletableFuture.supplyAsync(() -> wrappedLookup(lookupInfo), executor);
    }

    private LookupResult<D> wrappedLookup(LookupInfo lookupInfo) throws CompletionException {
        try {
            return persistHandler.lookup(lookupInfo);
        } catch (HandlerException e) {
            throw new CompletionException(e);
        }
    }

    @Override
    public CompletableFuture<List<LookupResult<D>>> lookupAsync(List<LookupInfo> lookupInfos) {
        return CompletableFuture.supplyAsync(() -> wrappedLookup(lookupInfos), executor);
    }

    private List<LookupResult<D>> wrappedLookup(List<LookupInfo> lookupInfos) throws CompletionException {
        try {
            return persistHandler.lookup(lookupInfos);
        } catch (HandlerException e) {
            throw new CompletionException(e);
        }
    }

    @Override
    public CompletableFuture<QueryResult> nativeQueryAsync(NativeQueryInfo queryInfo) {
        return CompletableFuture.supplyAsync(() -> wrappedNativeQuery(queryInfo), executor);
    }

    private QueryResult wrappedNativeQuery(NativeQueryInfo queryInfo) throws CompletionException {
        try {
            return persistHandler.nativeQuery(queryInfo);
        } catch (HandlerException e) {
            throw new CompletionException(e);
        }
    }

    @Override
    public CompletableFuture<List<QueryResult>> nativeQueryAsync(List<NativeQueryInfo> queryInfos) {
        return CompletableFuture.supplyAsync(() -> wrappedNativeQuery(queryInfos), executor);
    }

    private List<QueryResult> wrappedNativeQuery(List<NativeQueryInfo> queryInfos) throws CompletionException {
        try {
            return persistHandler.nativeQuery(queryInfos);
        } catch (HandlerException e) {
            throw new CompletionException(e);
        }
    }

    @Override
    public CompletableFuture<QueryResult> queryAsync(QueryInfo queryInfo) {
        return CompletableFuture.supplyAsync(() -> wrappedQuery(queryInfo), executor);
    }

    private QueryResult wrappedQuery(QueryInfo queryInfo) throws CompletionException {
        try {
            return queryHandler.query(queryInfo);
        } catch (HandlerException e) {
            throw new CompletionException(e);
        }
    }

    @Override
    public CompletableFuture<List<QueryResult>> queryAsync(List<QueryInfo> queryInfos) {
        return CompletableFuture.supplyAsync(() -> wrappedQuery(queryInfos), executor);
    }

    private List<QueryResult> wrappedQuery(List<QueryInfo> queryInfos) throws CompletionException {
        try {
            return queryHandler.query(queryInfos);
        } catch (HandlerException e) {
            throw new CompletionException(e);
        }
    }

    @Override
    public String toString() {
        return "AbstractViewHandler{" +
                "keepHandler=" + keepHandler +
                ", persistHandler=" + persistHandler +
                ", queryHandler=" + queryHandler +
                ", executor=" + executor +
                '}';
    }
}
