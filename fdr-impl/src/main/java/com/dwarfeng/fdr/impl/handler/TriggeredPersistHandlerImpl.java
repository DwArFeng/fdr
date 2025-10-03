package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.sdk.handler.Bridge;
import com.dwarfeng.fdr.stack.bean.dto.TriggeredData;
import com.dwarfeng.fdr.stack.handler.TriggeredPersistHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class TriggeredPersistHandlerImpl extends AbstractPersistHandler<TriggeredData>
        implements TriggeredPersistHandler {

    @Value("${persist.triggered_data.type}")
    private String type;

    public TriggeredPersistHandlerImpl(List<Bridge> bridges) {
        super(bridges);
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
    public String toString() {
        return "TriggeredPersistHandlerImpl{" +
                "type='" + type + '\'' +
                ", bridges=" + bridges +
                ", persister=" + persister +
                '}';
    }
}
