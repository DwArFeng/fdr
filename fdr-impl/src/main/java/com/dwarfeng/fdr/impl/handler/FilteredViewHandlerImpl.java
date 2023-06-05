package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.fdr.stack.handler.FilteredKeepHandler;
import com.dwarfeng.fdr.stack.handler.FilteredPersistHandler;
import com.dwarfeng.fdr.stack.handler.FilteredQueryHandler;
import com.dwarfeng.fdr.stack.handler.FilteredViewHandler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class FilteredViewHandlerImpl extends AbstractViewHandler<FilteredData> implements FilteredViewHandler {

    public FilteredViewHandlerImpl(
            FilteredKeepHandler filteredKeepHandler,
            FilteredPersistHandler filteredPersistHandler,
            FilteredQueryHandler filteredOrganizeHandler,
            ThreadPoolTaskExecutor executor
    ) {
        super(filteredKeepHandler, filteredPersistHandler, filteredOrganizeHandler, executor);
    }

    @Override
    public String toString() {
        return "FilteredViewHandlerImpl{" +
                "keepHandler=" + keepHandler +
                ", persistHandler=" + persistHandler +
                ", queryHandler=" + queryHandler +
                ", executor=" + executor +
                '}';
    }
}
