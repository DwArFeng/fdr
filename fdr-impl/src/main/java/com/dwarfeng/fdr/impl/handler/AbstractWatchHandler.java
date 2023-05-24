package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.bean.dto.LookupInfo;
import com.dwarfeng.fdr.stack.bean.dto.LookupResult;
import com.dwarfeng.fdr.stack.bean.dto.QueryInfo;
import com.dwarfeng.fdr.stack.bean.dto.QueryResult;
import com.dwarfeng.fdr.stack.handler.KeepHandler;
import com.dwarfeng.fdr.stack.handler.OrganizeHandler;
import com.dwarfeng.fdr.stack.handler.PersistHandler;
import com.dwarfeng.fdr.stack.handler.WatchHandler;
import com.dwarfeng.fdr.stack.structure.Data;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 观察处理器的抽象实现。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public abstract class AbstractWatchHandler<D extends Data> implements WatchHandler<D> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractWatchHandler.class);

    protected final KeepHandler<D> keepHandler;
    protected final PersistHandler<D> persistHandler;
    protected final OrganizeHandler organizeHandler;

    protected final ThreadPoolTaskExecutor executor;

    protected AbstractWatchHandler(
            KeepHandler<D> keepHandler,
            PersistHandler<D> persistHandler,
            OrganizeHandler organizeHandler,
            ThreadPoolTaskExecutor executor
    ) {
        this.keepHandler = keepHandler;
        this.persistHandler = persistHandler;
        this.organizeHandler = organizeHandler;
        this.executor = executor;
    }

    @Override
    public D inspect(LongIdKey pointKey) throws HandlerException {
        return keepHandler.inspect(pointKey);
    }

    @Override
    public List<D> inspect(List<LongIdKey> pointKeys) throws HandlerException {
        return keepHandler.inspect(pointKeys);
    }

    @Override
    public QueryResult<D> query(QueryInfo queryInfo) throws HandlerException {
        return persistHandler.query(queryInfo);
    }

    @Override
    public LookupResult lookup(LookupInfo lookupInfo) throws HandlerException {
        return organizeHandler.lookup(lookupInfo);
    }

    @Override
    public CompletableFuture<D> inspectAsync(LongIdKey pointKey) {
        return CompletableFuture.supplyAsync(
                () -> {
                    try {
                        return inspect(pointKey);
                    } catch (HandlerException e) {
                        LOGGER.warn("发生异常, 异常信息如下", e);
                        return null;
                    }
                },
                executor
        );
    }

    @Override
    public CompletableFuture<List<D>> inspectAsync(List<LongIdKey> pointKeys) {
        return CompletableFuture.supplyAsync(
                () -> {
                    try {
                        return inspect(pointKeys);
                    } catch (HandlerException e) {
                        LOGGER.warn("发生异常, 异常信息如下", e);
                        return null;
                    }
                },
                executor
        );
    }

    @Override
    public CompletableFuture<QueryResult<D>> queryAsync(QueryInfo queryInfo) {
        return CompletableFuture.supplyAsync(
                () -> {
                    try {
                        return query(queryInfo);
                    } catch (HandlerException e) {
                        LOGGER.warn("发生异常, 异常信息如下", e);
                        return null;
                    }
                },
                executor
        );
    }

    @Override
    public CompletableFuture<LookupResult> lookupAsync(LookupInfo lookupInfo) {
        return CompletableFuture.supplyAsync(
                () -> {
                    try {
                        return lookup(lookupInfo);
                    } catch (HandlerException e) {
                        LOGGER.warn("发生异常, 异常信息如下", e);
                        return null;
                    }
                },
                executor
        );
    }

    @Override
    public String toString() {
        return "AbstractWatchHandler{" +
                "keepHandler=" + keepHandler +
                ", persistHandler=" + persistHandler +
                ", organizeHandler=" + organizeHandler +
                ", executor=" + executor +
                '}';
    }
}
