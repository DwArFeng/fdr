package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.sdk.handler.Bridge;
import com.dwarfeng.fdr.stack.handler.MapLocalCacheHandler;
import com.dwarfeng.fdr.stack.handler.NormalQueryHandler;
import com.dwarfeng.fdr.stack.struct.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class NormalQueryHandlerImpl extends AbstractQueryHandler implements NormalQueryHandler {

    @Value("${persist.normal_data.type}")
    private String type;

    @Value("${query.normal.max_period_span}")
    private long maxPeriodSpan;
    @Value("${query.normal.max_page_size}")
    private int maxPageSize;

    public NormalQueryHandlerImpl(
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
    protected Bridge.Persister<? extends Data> getPersisterFromBridge(Bridge bridge) throws Exception {
        return bridge.getNormalDataPersister();
    }

    @Override
    protected LookupConfig getLookupConfig() {
        return new LookupConfig(maxPeriodSpan, maxPageSize);
    }

    @Override
    public String toString() {
        return "NormalQueryHandlerImpl{" +
                "type='" + type + '\'' +
                ", maxPeriodSpan=" + maxPeriodSpan +
                ", maxPageSize=" + maxPageSize +
                ", mapLocalCacheHandler=" + mapLocalCacheHandler +
                ", bridges=" + bridges +
                ", persister=" + persister +
                '}';
    }
}
