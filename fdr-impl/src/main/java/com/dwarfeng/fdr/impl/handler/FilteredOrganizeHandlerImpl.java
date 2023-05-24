package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.handler.FilteredOrganizeHandler;
import com.dwarfeng.fdr.stack.handler.FilteredPersistHandler;
import com.dwarfeng.fdr.stack.handler.MapLocalCacheHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FilteredOrganizeHandlerImpl extends AbstractOrganizeHandler implements FilteredOrganizeHandler {

    @Value("${organize.filtered.max_period_span}")
    private long maxPeriodSpan;
    @Value("${organize.filtered.max_page_size}")
    private int maxPageSize;

    public FilteredOrganizeHandlerImpl(
            FilteredPersistHandler filteredPersistHandler,
            MapLocalCacheHandler mapLocalCacheHandler
    ) {
        super(filteredPersistHandler, mapLocalCacheHandler);
    }

    @Override
    protected LookupConfig getOrganizeConfig() {
        return new LookupConfig(maxPeriodSpan, maxPageSize);
    }

    @Override
    public String toString() {
        return "FilteredOrganizeHandlerImpl{" +
                "maxPeriodSpan=" + maxPeriodSpan +
                ", maxPageSize=" + maxPageSize +
                ", persistHandler=" + persistHandler +
                ", mapLocalCacheHandler=" + mapLocalCacheHandler +
                '}';
    }
}
