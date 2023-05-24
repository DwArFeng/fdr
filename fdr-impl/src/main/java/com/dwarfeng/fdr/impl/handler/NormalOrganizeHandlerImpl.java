package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.handler.MapLocalCacheHandler;
import com.dwarfeng.fdr.stack.handler.NormalOrganizeHandler;
import com.dwarfeng.fdr.stack.handler.NormalPersistHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NormalOrganizeHandlerImpl extends AbstractOrganizeHandler implements NormalOrganizeHandler {

    @Value("${organize.normal.max_period_span}")
    private long maxPeriodSpan;
    @Value("${organize.normal.max_page_size}")
    private int maxPageSize;

    public NormalOrganizeHandlerImpl(
            NormalPersistHandler normalPersistHandler,
            MapLocalCacheHandler mapLocalCacheHandler
    ) {
        super(normalPersistHandler, mapLocalCacheHandler);
    }

    @Override
    protected LookupConfig getOrganizeConfig() {
        return new LookupConfig(maxPeriodSpan, maxPageSize);
    }

    @Override
    public String toString() {
        return "NormalOrganizeHandlerImpl{" +
                "maxPeriodSpan=" + maxPeriodSpan +
                ", maxPageSize=" + maxPageSize +
                ", persistHandler=" + persistHandler +
                ", mapLocalCacheHandler=" + mapLocalCacheHandler +
                '}';
    }
}
