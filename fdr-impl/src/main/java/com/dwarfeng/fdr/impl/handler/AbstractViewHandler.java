package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.bean.dto.LookupInfo;
import com.dwarfeng.fdr.stack.bean.dto.LookupResult;
import com.dwarfeng.fdr.stack.bean.dto.QueryInfo;
import com.dwarfeng.fdr.stack.bean.dto.QueryResult;
import com.dwarfeng.fdr.stack.exception.LatestNotSupportedException;
import com.dwarfeng.fdr.stack.exception.LookupNotSupportedException;
import com.dwarfeng.fdr.stack.exception.QueryNotSupportedException;
import com.dwarfeng.fdr.stack.handler.KeepHandler;
import com.dwarfeng.fdr.stack.handler.PersistHandler;
import com.dwarfeng.fdr.stack.handler.QueryHandler;
import com.dwarfeng.fdr.stack.handler.ViewHandler;
import com.dwarfeng.fdr.stack.struct.Data;
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
public abstract class AbstractViewHandler<D extends Data> implements ViewHandler<D> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractViewHandler.class);

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
        if (keepHandler.writeOnly()) {
            throw new LatestNotSupportedException();
        }
        return keepHandler.latest(pointKey);
    }

    @Override
    public List<D> latest(List<LongIdKey> pointKeys) throws HandlerException {
        if (keepHandler.writeOnly()) {
            throw new LatestNotSupportedException();
        }
        return keepHandler.latest(pointKeys);
    }

    @Override
    public LookupResult<D> lookup(LookupInfo lookupInfo) throws HandlerException {
        if (persistHandler.writeOnly()) {
            throw new LookupNotSupportedException();
        }
        return persistHandler.lookup(lookupInfo);
    }

    @Override
    public List<LookupResult<D>> lookup(List<LookupInfo> lookupInfos) throws HandlerException {
        if (persistHandler.writeOnly()) {
            throw new LookupNotSupportedException();
        }
        return persistHandler.lookup(lookupInfos);
    }

    @Override
    public QueryResult query(QueryInfo queryInfo) throws HandlerException {
        if (persistHandler.writeOnly()) {
            throw new QueryNotSupportedException();
        }
        return queryHandler.query(queryInfo);
    }

    @Override
    public List<QueryResult> query(List<QueryInfo> queryInfos) throws HandlerException {
        if (persistHandler.writeOnly()) {
            throw new QueryNotSupportedException();
        }
        return queryHandler.query(queryInfos);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public CompletableFuture<D> latestAsync(LongIdKey pointKey) throws HandlerException {
        if (keepHandler.writeOnly()) {
            throw new LatestNotSupportedException();
        }
        return CompletableFuture.supplyAsync(
                () -> {
                    try {
                        return latest(pointKey);
                    } catch (HandlerException e) {
                        LOGGER.warn("发生异常, 异常信息如下", e);
                        return null;
                    }
                },
                executor
        );
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public CompletableFuture<List<D>> latestAsync(List<LongIdKey> pointKeys) throws HandlerException {
        if (keepHandler.writeOnly()) {
            throw new LatestNotSupportedException();
        }
        return CompletableFuture.supplyAsync(
                () -> {
                    try {
                        return latest(pointKeys);
                    } catch (HandlerException e) {
                        LOGGER.warn("发生异常, 异常信息如下", e);
                        return null;
                    }
                },
                executor
        );
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public CompletableFuture<LookupResult<D>> lookupAsync(LookupInfo lookupInfo) throws HandlerException {
        if (persistHandler.writeOnly()) {
            throw new LookupNotSupportedException();
        }
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

    @SuppressWarnings("DuplicatedCode")
    @Override
    public CompletableFuture<List<LookupResult<D>>> lookupAsync(List<LookupInfo> lookupInfos) throws HandlerException {
        if (persistHandler.writeOnly()) {
            throw new LookupNotSupportedException();
        }
        return CompletableFuture.supplyAsync(
                () -> {
                    try {
                        return lookup(lookupInfos);
                    } catch (HandlerException e) {
                        LOGGER.warn("发生异常, 异常信息如下", e);
                        return null;
                    }
                },
                executor
        );
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public CompletableFuture<QueryResult> queryAsync(QueryInfo queryInfo) throws HandlerException {
        if (persistHandler.writeOnly()) {
            throw new QueryNotSupportedException();
        }
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

    @SuppressWarnings("DuplicatedCode")
    @Override
    public CompletableFuture<List<QueryResult>> queryAsync(List<QueryInfo> queryInfos) throws HandlerException {
        if (persistHandler.writeOnly()) {
            throw new QueryNotSupportedException();
        }
        return CompletableFuture.supplyAsync(
                () -> {
                    try {
                        return query(queryInfos);
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
        return "AbstractViewHandler{" +
                "keepHandler=" + keepHandler +
                ", persistHandler=" + persistHandler +
                ", queryHandler=" + queryHandler +
                ", executor=" + executor +
                '}';
    }
}
