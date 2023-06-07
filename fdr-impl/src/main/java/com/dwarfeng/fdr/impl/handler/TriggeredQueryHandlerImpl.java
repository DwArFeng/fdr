package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.bean.dto.TriggeredData;
import com.dwarfeng.fdr.stack.handler.MapLocalCacheHandler;
import com.dwarfeng.fdr.stack.handler.TriggeredQueryHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class TriggeredQueryHandlerImpl extends AbstractQueryHandler implements TriggeredQueryHandler {

    @Value("${persist.triggered_data.type}")
    private String type;

    @Value("${query.triggered.max_period_span}")
    private long maxPeriodSpan;
    @Value("${query.triggered.max_page_size}")
    private int maxPageSize;

    public TriggeredQueryHandlerImpl(
            MapLocalCacheHandler mapLocalCacheHandler,
            List<Bridge> bridges
    ) {
        super(mapLocalCacheHandler, bridges);
    }

    @PostConstruct
    public void init() throws Exception {
        super.init(type);
    }

    @Override
    protected Bridge.Persister<TriggeredData> getPersisterFromBridge(Bridge bridge) throws Exception {
        return bridge.getTriggeredDataPersister();
    }

    @Override
    protected LookupConfig getLookupConfig() {
        return new LookupConfig(maxPeriodSpan, maxPageSize);
    }

    @Override
    public String toString() {
        return "TriggeredQueryHandlerImpl{" +
                "type='" + type + '\'' +
                ", maxPeriodSpan=" + maxPeriodSpan +
                ", maxPageSize=" + maxPageSize +
                ", mapLocalCacheHandler=" + mapLocalCacheHandler +
                ", bridges=" + bridges +
                ", persister=" + persister +
                '}';
    }
}
