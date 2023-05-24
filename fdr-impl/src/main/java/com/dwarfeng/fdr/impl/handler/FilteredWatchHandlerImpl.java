package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.fdr.stack.handler.FilteredKeepHandler;
import com.dwarfeng.fdr.stack.handler.FilteredOrganizeHandler;
import com.dwarfeng.fdr.stack.handler.FilteredPersistHandler;
import com.dwarfeng.fdr.stack.handler.FilteredWatchHandler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class FilteredWatchHandlerImpl extends AbstractWatchHandler<FilteredData> implements FilteredWatchHandler {

    public FilteredWatchHandlerImpl(
            FilteredKeepHandler filteredKeepHandler,
            FilteredPersistHandler filteredPersistHandler,
            FilteredOrganizeHandler filteredOrganizeHandler,
            ThreadPoolTaskExecutor executor
    ) {
        super(filteredKeepHandler, filteredPersistHandler, filteredOrganizeHandler, executor);
    }

    @Override
    public String toString() {
        return "FilteredWatchHandlerImpl{" +
                "keepHandler=" + keepHandler +
                ", persistHandler=" + persistHandler +
                ", organizeHandler=" + organizeHandler +
                ", executor=" + executor +
                '}';
    }
}
