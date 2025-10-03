package com.dwarfeng.fdr.impl.handler.bridge.multi;

import com.dwarfeng.fdr.sdk.handler.Bridge;
import com.dwarfeng.fdr.stack.bean.dto.TriggeredData;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * 多重桥接器被触发数据保持器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class TriggeredDataMultiBridgeKeeper extends MultiBridgeKeeper<TriggeredData> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TriggeredDataMultiBridgeKeeper.class);

    @Value("${bridge.multi.delegates.keep.triggered_data}")
    private String triggeredDataKeepDelegateConfig;

    protected TriggeredDataMultiBridgeKeeper(
            ApplicationContext ctx,
            ThreadPoolTaskExecutor executor
    ) {
        super(ctx, executor);
    }

    @Override
    protected String getDelegateConfig() {
        return triggeredDataKeepDelegateConfig;
    }

    @Override
    protected Bridge.Keeper<TriggeredData> getKeeperFromBridge(Bridge bridge) throws HandlerException {
        return bridge.getTriggeredDataKeeper();
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    @Override
    public String toString() {
        return "TriggeredDataMultiBridgeKeeper{" +
                "triggeredDataKeepDelegateConfig='" + triggeredDataKeepDelegateConfig + '\'' +
                ", ctx=" + ctx +
                ", executor=" + executor +
                ", primaryKeeper=" + primaryKeeper +
                ", delegateKeepers=" + delegateKeepers +
                '}';
    }
}
