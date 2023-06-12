package com.dwarfeng.fdr.impl.handler.bridge.multi;

import com.dwarfeng.fdr.impl.handler.Bridge;
import com.dwarfeng.fdr.impl.handler.Bridge.Keeper;
import com.dwarfeng.fdr.impl.handler.bridge.AbstractKeeper;
import com.dwarfeng.fdr.stack.exception.LatestNotSupportedException;
import com.dwarfeng.fdr.stack.struct.Data;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
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
 * 多重桥接器保持器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public abstract class MultiBridgeKeeper<D extends Data> extends AbstractKeeper<D> implements InitializingBean {

    protected final ApplicationContext ctx;

    protected final ThreadPoolTaskExecutor executor;

    protected Keeper<D> primaryKeeper;
    protected List<Keeper<D>> delegateKeepers;

    protected MultiBridgeKeeper(ApplicationContext ctx, ThreadPoolTaskExecutor executor) {
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
            if (!bridge.supportKeeper()) {
                throw new IllegalStateException("桥接器不支持持久器, 请检查 bridge.properties 配置文件: " + bridgeType);
            }
            delegateBridges.add(bridge);
        }
        // 基于桥接器获取持久化器。
        List<Keeper<D>> delegateKeepers = new ArrayList<>(delegateBridges.size());
        for (int i = 0; i < delegateBridges.size(); i++) {
            Keeper<D> delegateKeeper = getKeeperFromBridge(delegateBridges.get(i));
            if (i == 0) {
                this.primaryKeeper = delegateKeeper;
            }
            delegateKeepers.add(delegateKeeper);
        }
        this.delegateKeepers = delegateKeepers;
    }

    @Override
    public boolean writeOnly() {
        return primaryKeeper.writeOnly();
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void update(D data) throws HandlerException {
        try {
            // 对代理的所有持久器执行异步更新操作。
            List<CompletableFuture<?>> futures = new ArrayList<>(delegateKeepers.size());
            for (final Keeper<D> delegateKeeper : delegateKeepers) {
                CompletableFuture<Void> future = CompletableFuture.runAsync(
                        () -> wrappedDoUpdate(delegateKeeper, data),
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

    private void wrappedDoUpdate(Keeper<D> delegateKeeper, D data) throws CompletionException {
        try {
            delegateKeeper.update(data);
        } catch (Exception e) {
            Logger logger = getLogger();
            String message = "持久器 " + delegateKeeper + " 更新数据时发生异常, 数据为 " + data + ", 异常信息如下";
            logger.warn(message, e);
            throw new CompletionException(e);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void update(List<D> datas) throws HandlerException {
        try {
            // 对代理的所有持久器执行异步更新操作。
            List<CompletableFuture<?>> futures = new ArrayList<>(delegateKeepers.size());
            for (final Keeper<D> delegateKeeper : delegateKeepers) {
                CompletableFuture<Void> future = CompletableFuture.runAsync(
                        () -> wrappedDoUpdate(delegateKeeper, datas),
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

    private void wrappedDoUpdate(Keeper<D> delegateKeeper, List<D> datas) throws CompletionException {
        try {
            delegateKeeper.update(datas);
        } catch (Exception e) {
            Logger logger = getLogger();
            String message = "持久器 " + delegateKeeper + " 更新数据时发生异常, 共 " +
                    datas.size() + " 条数据, 异常信息如下";
            logger.warn(message, e);
            throw new CompletionException(e);
        }
    }

    @Override
    public D latest(LongIdKey pointKey) throws HandlerException {
        if (primaryKeeper.writeOnly()) {
            throw new LatestNotSupportedException();
        }
        try {
            return primaryKeeper.latest(pointKey);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public List<D> latest(List<LongIdKey> pointKeys) throws HandlerException {
        if (primaryKeeper.writeOnly()) {
            throw new LatestNotSupportedException();
        }
        try {
            return primaryKeeper.latest(pointKeys);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    protected abstract String getDelegateConfig();

    protected abstract Keeper<D> getKeeperFromBridge(Bridge bridge) throws HandlerException;

    protected abstract Logger getLogger();

    @Override
    public String toString() {
        return "MultiBridgeKeeper{" +
                "ctx=" + ctx +
                ", executor=" + executor +
                ", primaryKeeper=" + primaryKeeper +
                ", delegateKeepers=" + delegateKeepers +
                '}';
    }
}
