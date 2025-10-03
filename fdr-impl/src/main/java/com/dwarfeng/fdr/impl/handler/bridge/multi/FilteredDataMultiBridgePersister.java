package com.dwarfeng.fdr.impl.handler.bridge.multi;

import com.dwarfeng.fdr.sdk.handler.Bridge;
import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * 多重桥接器被过滤数据持久器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class FilteredDataMultiBridgePersister extends MultiBridgePersister<FilteredData> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilteredDataMultiBridgePersister.class);

    @Value("${bridge.multi.delegates.persist.filtered_data}")
    private String filteredDataPersistDelegateConfig;

    protected FilteredDataMultiBridgePersister(
            ApplicationContext ctx,
            ThreadPoolTaskExecutor executor
    ) {
        super(ctx, executor);
    }

    @Override
    protected String getDelegateConfig() {
        return filteredDataPersistDelegateConfig;
    }

    @Override
    protected Bridge.Persister<FilteredData> getPersisterFromBridge(Bridge bridge) throws HandlerException {
        return bridge.getFilteredDataPersister();
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    @Override
    public String toString() {
        return "FilteredDataMultiBridgePersister{" +
                "filteredDataPersistDelegateConfig='" + filteredDataPersistDelegateConfig + '\'' +
                ", ctx=" + ctx +
                ", executor=" + executor +
                ", primaryPersister=" + primaryPersister +
                ", delegatePersisters=" + delegatePersisters +
                '}';
    }
}
