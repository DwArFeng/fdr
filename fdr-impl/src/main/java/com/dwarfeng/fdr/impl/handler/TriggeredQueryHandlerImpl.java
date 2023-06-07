package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.handler.MapLocalCacheHandler;
import com.dwarfeng.fdr.stack.handler.TriggeredPersistHandler;
import com.dwarfeng.fdr.stack.handler.TriggeredQueryHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TriggeredQueryHandlerImpl extends AbstractQueryHandler implements TriggeredQueryHandler {

    @Value("${query.triggered.max_period_span}")
    private long maxPeriodSpan;
    @Value("${query.triggered.max_page_size}")
    private int maxPageSize;

    public TriggeredQueryHandlerImpl(
            TriggeredPersistHandler triggeredPersistHandler,
            MapLocalCacheHandler mapLocalCacheHandler
    ) {
        super(triggeredPersistHandler, mapLocalCacheHandler);
    }

    @Override
    protected LookupConfig getLookupConfig() {
        return new LookupConfig(maxPeriodSpan, maxPageSize);
    }

    @Override
    public String toString() {
        return "TriggeredQueryHandlerImpl{" +
                "maxPeriodSpan=" + maxPeriodSpan +
                ", maxPageSize=" + maxPageSize +
                ", persistHandler=" + persistHandler +
                ", mapLocalCacheHandler=" + mapLocalCacheHandler +
                '}';
    }
}
