package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.fdr.stack.handler.FilteredQueryHandler;
import com.dwarfeng.fdr.stack.handler.MapLocalCacheHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class FilteredQueryHandlerImpl extends AbstractQueryHandler implements FilteredQueryHandler {

    @Value("${persist.filtered_data.type}")
    private String type;

    @Value("${query.filtered.max_period_span}")
    private long maxPeriodSpan;
    @Value("${query.filtered.max_page_size}")
    private int maxPageSize;

    public FilteredQueryHandlerImpl(
            MapLocalCacheHandler mapLocalCacheHandler,
            ThreadPoolTaskExecutor executor,
            List<Bridge> bridges
    ) {
        super(mapLocalCacheHandler, executor, bridges);
    }

    @PostConstruct
    public void init() throws Exception {
        super.init(type);
    }

    @Override
    protected Bridge.Persister<FilteredData> getPersisterFromBridge(Bridge bridge) throws Exception {
        return bridge.getFilteredDataPersister();
    }

    @Override
    protected LookupConfig getLookupConfig() {
        return new LookupConfig(maxPeriodSpan, maxPageSize);
    }

    @Override
    public String toString() {
        return "FilteredQueryHandlerImpl{" +
                "type='" + type + '\'' +
                ", maxPeriodSpan=" + maxPeriodSpan +
                ", maxPageSize=" + maxPageSize +
                ", mapLocalCacheHandler=" + mapLocalCacheHandler +
                ", bridges=" + bridges +
                ", persister=" + persister +
                '}';
    }
}
