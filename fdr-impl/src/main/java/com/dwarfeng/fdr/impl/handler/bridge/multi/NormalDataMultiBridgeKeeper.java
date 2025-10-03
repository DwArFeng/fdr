package com.dwarfeng.fdr.impl.handler.bridge.multi;

import com.dwarfeng.fdr.sdk.handler.Bridge;
import com.dwarfeng.fdr.stack.bean.dto.NormalData;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * 多重桥接器一般数据保持器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class NormalDataMultiBridgeKeeper extends MultiBridgeKeeper<NormalData> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NormalDataMultiBridgeKeeper.class);

    @Value("${bridge.multi.delegates.keep.normal_data}")
    private String normalDataKeepDelegateConfig;

    protected NormalDataMultiBridgeKeeper(
            ApplicationContext ctx,
            ThreadPoolTaskExecutor executor
    ) {
        super(ctx, executor);
    }

    @Override
    protected String getDelegateConfig() {
        return normalDataKeepDelegateConfig;
    }

    @Override
    protected Bridge.Keeper<NormalData> getKeeperFromBridge(Bridge bridge) throws HandlerException {
        return bridge.getNormalDataKeeper();
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    @Override
    public String toString() {
        return "NormalDataMultiBridgeKeeper{" +
                "normalDataKeepDelegateConfig='" + normalDataKeepDelegateConfig + '\'' +
                ", ctx=" + ctx +
                ", executor=" + executor +
                ", primaryKeeper=" + primaryKeeper +
                ", delegateKeepers=" + delegateKeepers +
                '}';
    }
}
