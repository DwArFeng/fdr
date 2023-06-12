package com.dwarfeng.fdr.impl.handler.bridge.multi;

import com.dwarfeng.fdr.impl.handler.Bridge;
import com.dwarfeng.fdr.stack.bean.dto.NormalData;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * 多重桥接器一般数据持久器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class NormalDataMultiBridgePersister extends MultiBridgePersister<NormalData> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NormalDataMultiBridgePersister.class);

    @Value("${bridge.multi.delegates.persist.normal_data}")
    private String normalDataPersistDelegateConfig;

    protected NormalDataMultiBridgePersister(
            ApplicationContext ctx,
            ThreadPoolTaskExecutor executor
    ) {
        super(ctx, executor);
    }

    @Override
    protected String getDelegateConfig() {
        return normalDataPersistDelegateConfig;
    }

    @Override
    protected Bridge.Persister<NormalData> getPersisterFromBridge(Bridge bridge) throws HandlerException {
        return bridge.getNormalDataPersister();
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    @Override
    public String toString() {
        return "NormalDataMultiBridgePersister{" +
                "normalDataPersistDelegateConfig='" + normalDataPersistDelegateConfig + '\'' +
                ", ctx=" + ctx +
                ", executor=" + executor +
                ", primaryPersister=" + primaryPersister +
                ", delegatePersisters=" + delegatePersisters +
                '}';
    }
}
