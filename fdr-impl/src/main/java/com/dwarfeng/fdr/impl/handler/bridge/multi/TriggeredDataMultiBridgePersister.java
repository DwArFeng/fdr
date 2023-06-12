package com.dwarfeng.fdr.impl.handler.bridge.multi;

import com.dwarfeng.fdr.impl.handler.Bridge;
import com.dwarfeng.fdr.stack.bean.dto.TriggeredData;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * 多重桥接器被触发数据持久器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class TriggeredDataMultiBridgePersister extends MultiBridgePersister<TriggeredData> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TriggeredDataMultiBridgePersister.class);

    @Value("${bridge.multi.delegates.persist.triggered_data}")
    private String triggeredDataPersistDelegateConfig;

    protected TriggeredDataMultiBridgePersister(
            ApplicationContext ctx,
            ThreadPoolTaskExecutor executor
    ) {
        super(ctx, executor);
    }

    @Override
    protected String getDelegateConfig() {
        return triggeredDataPersistDelegateConfig;
    }

    @Override
    protected Bridge.Persister<TriggeredData> getPersisterFromBridge(Bridge bridge) throws HandlerException {
        return bridge.getTriggeredDataPersister();
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    @Override
    public String toString() {
        return "TriggeredDataMultiBridgePersister{" +
                "triggeredDataPersistDelegateConfig='" + triggeredDataPersistDelegateConfig + '\'' +
                ", ctx=" + ctx +
                ", executor=" + executor +
                ", primaryPersister=" + primaryPersister +
                ", delegatePersisters=" + delegatePersisters +
                '}';
    }
}
