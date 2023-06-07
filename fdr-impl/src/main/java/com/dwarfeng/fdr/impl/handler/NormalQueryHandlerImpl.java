package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.handler.MapLocalCacheHandler;
import com.dwarfeng.fdr.stack.handler.NormalPersistHandler;
import com.dwarfeng.fdr.stack.handler.NormalQueryHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NormalQueryHandlerImpl extends AbstractQueryHandler implements NormalQueryHandler {

    @Value("${query.normal.max_period_span}")
    private long maxPeriodSpan;
    @Value("${query.normal.max_page_size}")
    private int maxPageSize;

    public NormalQueryHandlerImpl(
            NormalPersistHandler normalPersistHandler,
            MapLocalCacheHandler mapLocalCacheHandler
    ) {
        super(normalPersistHandler, mapLocalCacheHandler);
    }

    @Override
    protected LookupConfig getLookupConfig() {
        return new LookupConfig(maxPeriodSpan, maxPageSize);
    }

    @Override
    public String toString() {
        return "NormalQueryHandlerImpl{" +
                "maxPeriodSpan=" + maxPeriodSpan +
                ", maxPageSize=" + maxPageSize +
                ", persistHandler=" + persistHandler +
                ", mapLocalCacheHandler=" + mapLocalCacheHandler +
                '}';
    }
}
