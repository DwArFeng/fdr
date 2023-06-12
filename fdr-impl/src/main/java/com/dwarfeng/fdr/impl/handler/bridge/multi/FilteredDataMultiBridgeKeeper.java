package com.dwarfeng.fdr.impl.handler.bridge.multi;

import com.dwarfeng.fdr.impl.handler.Bridge;
import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * 多重桥接器被过滤数据保持器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class FilteredDataMultiBridgeKeeper extends MultiBridgeKeeper<FilteredData> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilteredDataMultiBridgeKeeper.class);

    @Value("${bridge.multi.delegates.keep.filtered_data}")
    private String filteredDataKeepDelegateConfig;

    protected FilteredDataMultiBridgeKeeper(
            ApplicationContext ctx,
            ThreadPoolTaskExecutor executor
    ) {
        super(ctx, executor);
    }

    @Override
    protected String getDelegateConfig() {
        return filteredDataKeepDelegateConfig;
    }

    @Override
    protected Bridge.Keeper<FilteredData> getKeeperFromBridge(Bridge bridge) throws HandlerException {
        return bridge.getFilteredDataKeeper();
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    @Override
    public String toString() {
        return "FilteredDataMultiBridgeKeeper{" +
                "filteredDataKeepDelegateConfig='" + filteredDataKeepDelegateConfig + '\'' +
                ", ctx=" + ctx +
                ", executor=" + executor +
                ", primaryKeeper=" + primaryKeeper +
                ", delegateKeepers=" + delegateKeepers +
                '}';
    }
}
