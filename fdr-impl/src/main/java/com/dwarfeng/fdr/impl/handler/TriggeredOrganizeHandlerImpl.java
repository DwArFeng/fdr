package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.handler.MapLocalCacheHandler;
import com.dwarfeng.fdr.stack.handler.TriggeredOrganizeHandler;
import com.dwarfeng.fdr.stack.handler.TriggeredPersistHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TriggeredOrganizeHandlerImpl extends AbstractOrganizeHandler implements TriggeredOrganizeHandler {

    @Value("${organize.triggered.max_period_span}")
    private long maxPeriodSpan;
    @Value("${organize.triggered.max_page_size}")
    private int maxPageSize;

    public TriggeredOrganizeHandlerImpl(
            TriggeredPersistHandler triggeredPersistHandler,
            MapLocalCacheHandler mapLocalCacheHandler
    ) {
        super(triggeredPersistHandler, mapLocalCacheHandler);
    }

    @Override
    protected LookupConfig getOrganizeConfig() {
        return new LookupConfig(maxPeriodSpan, maxPageSize);
    }

    @Override
    public String toString() {
        return "TriggeredOrganizeHandlerImpl{" +
                "maxPeriodSpan=" + maxPeriodSpan +
                ", maxPageSize=" + maxPageSize +
                ", persistHandler=" + persistHandler +
                ", mapLocalCacheHandler=" + mapLocalCacheHandler +
                '}';
    }
}
