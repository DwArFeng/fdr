package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.handler.FilteredPersistHandler;
import com.dwarfeng.fdr.stack.handler.FilteredQueryHandler;
import com.dwarfeng.fdr.stack.handler.MapLocalCacheHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FilteredQueryHandlerImpl extends AbstractQueryHandler implements FilteredQueryHandler {

    @Value("${query.filtered.max_period_span}")
    private long maxPeriodSpan;
    @Value("${query.filtered.max_page_size}")
    private int maxPageSize;

    public FilteredQueryHandlerImpl(
            FilteredPersistHandler filteredPersistHandler,
            MapLocalCacheHandler mapLocalCacheHandler
    ) {
        super(filteredPersistHandler, mapLocalCacheHandler);
    }

    @Override
    protected LookupConfig getLookupConfig() {
        return new LookupConfig(maxPeriodSpan, maxPageSize);
    }

    @Override
    public String toString() {
        return "FilteredQueryHandlerImpl{" +
                "maxPeriodSpan=" + maxPeriodSpan +
                ", maxPageSize=" + maxPageSize +
                ", persistHandler=" + persistHandler +
                ", mapLocalCacheHandler=" + mapLocalCacheHandler +
                '}';
    }
}
