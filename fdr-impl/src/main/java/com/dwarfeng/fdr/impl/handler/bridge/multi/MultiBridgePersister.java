package com.dwarfeng.fdr.impl.handler.bridge.multi;

import com.dwarfeng.fdr.impl.handler.Bridge;
import com.dwarfeng.fdr.impl.handler.Bridge.Persister;
import com.dwarfeng.fdr.impl.handler.bridge.AbstractPersister;
import com.dwarfeng.fdr.stack.bean.dto.LookupInfo;
import com.dwarfeng.fdr.stack.bean.dto.LookupResult;
import com.dwarfeng.fdr.stack.bean.dto.NativeQueryInfo;
import com.dwarfeng.fdr.stack.bean.dto.QueryResult;
import com.dwarfeng.fdr.stack.struct.Data;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

/**
 * 多重桥接器持久器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public abstract class MultiBridgePersister<D extends Data> extends AbstractPersister<D> implements InitializingBean {

    protected final ApplicationContext ctx;

    protected final ThreadPoolTaskExecutor executor;

    protected Persister<D> primaryPersister;
    protected List<Persister<D>> delegatePersisters;

    protected MultiBridgePersister(ApplicationContext ctx, ThreadPoolTaskExecutor executor) {
        this.ctx = ctx;
        this.executor = executor;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void afterPropertiesSet() throws Exception {
        // 获取注册的所有桥接器。
        List<Bridge> bridges = new ArrayList<>(ctx.getBeansOfType(Bridge.class).values());
        // 获取并解析配置。
        String delegateConfig = getDelegateConfig();
        List<String> bridgeTypes = ConfigUtil.parseConfigToTypes(delegateConfig);
        // 基于配置获取桥接器的代理列表。
        List<Bridge> delegateBridges = new ArrayList<>();
        for (String bridgeType : bridgeTypes) {
            Bridge bridge = bridges.stream().filter(b -> b.supportType(bridgeType)).findAny()
                    .orElseThrow(() -> new HandlerException("未知的 bridge 类型: " + bridgeType));
            delegateBridges.add(bridge);
        }
        // 基于桥接器获取持久器。
        List<Persister<D>> delegatePersisters = new ArrayList<>(delegateBridges.size());
        for (int i = 0; i < delegateBridges.size(); i++) {
            Persister<D> delegatePersister = getPersisterFromBridge(delegateBridges.get(i));
            if (i == 0) {
                this.primaryPersister = delegatePersister;
            }
            delegatePersisters.add(delegatePersister);
        }
        this.delegatePersisters = delegatePersisters;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void record(D data) throws HandlerException {
        try {
            // 对代理的所有持久器执行异步更新操作。
            List<CompletableFuture<?>> futures = new ArrayList<>(delegatePersisters.size());
            for (final Persister<D> delegatePersister : delegatePersisters) {
                CompletableFuture<Void> future = CompletableFuture.runAsync(
                        () -> wrappedDoRecord(delegatePersister, data),
                        executor
                );
                futures.add(future);
            }
            try {
                CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
            } catch (CompletionException e) {
                throw (Exception) e.getCause();
            }
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    private void wrappedDoRecord(Persister<D> delegatePersister, D data) throws CompletionException {
        try {
            delegatePersister.record(data);
        } catch (Exception e) {
            Logger logger = getLogger();
            String message = "持久器 " + delegatePersister + " 记录数据时发生异常, 数据为 " + data + ", 异常信息如下";
            logger.warn(message, e);
            throw new CompletionException(e);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void record(List<D> datas) throws HandlerException {
        try {
            // 对代理的所有持久器执行异步更新操作。
            List<CompletableFuture<?>> futures = new ArrayList<>(delegatePersisters.size());
            for (final Persister<D> delegatePersister : delegatePersisters) {
                CompletableFuture<Void> future = CompletableFuture.runAsync(
                        () -> wrappedDoRecord(delegatePersister, datas),
                        executor
                );
                futures.add(future);
            }
            try {
                CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
            } catch (CompletionException e) {
                throw (Exception) e.getCause();
            }
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    private void wrappedDoRecord(Persister<D> delegatePersister, List<D> datas) throws CompletionException {
        try {
            delegatePersister.record(datas);
        } catch (Exception e) {
            Logger logger = getLogger();
            String message = "持久器 " + delegatePersister + " 记录数据时发生异常, 共 " +
                    datas.size() + " 条数据, 异常信息如下";
            logger.warn(message, e);
            throw new CompletionException(e);
        }
    }

    @Override
    public LookupResult<D> lookup(LookupInfo lookupInfo) throws HandlerException {
        try {
            return primaryPersister.lookup(lookupInfo);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public List<LookupResult<D>> lookup(List<LookupInfo> lookupInfos) throws HandlerException {
        try {
            return primaryPersister.lookup(lookupInfos);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public QueryResult nativeQuery(NativeQueryInfo queryInfo) throws HandlerException {
        try {
            return primaryPersister.nativeQuery(queryInfo);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public List<QueryResult> nativeQuery(List<NativeQueryInfo> queryInfos) throws HandlerException {
        try {
            return primaryPersister.nativeQuery(queryInfos);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    protected abstract String getDelegateConfig();

    protected abstract Persister<D> getPersisterFromBridge(Bridge bridge) throws HandlerException;

    protected abstract Logger getLogger();

    @Override
    public String toString() {
        return "MultiBridgePersister{" +
                "ctx=" + ctx +
                ", executor=" + executor +
                ", primaryPersister=" + primaryPersister +
                ", delegatePersisters=" + delegatePersisters +
                '}';
    }
}
