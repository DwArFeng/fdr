package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.bean.dto.TriggeredData;
import com.dwarfeng.fdr.stack.handler.TriggeredKeepHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class TriggeredKeepHandlerImpl extends AbstractKeepHandler<TriggeredData> implements TriggeredKeepHandler {

    @Value("${keep.triggered_data.type}")
    private String type;

    public TriggeredKeepHandlerImpl(List<Bridge> bridges) {
        super(bridges);
    }

    @PostConstruct
    public void init() throws Exception {
        super.init(type);
    }

    @Override
    protected Bridge.Keeper<TriggeredData> getKeeperFromBridge(Bridge bridge) throws Exception {
        return bridge.getTriggeredDataKeeper();
    }

    @Override
    public String
    toString() {
        return "TriggeredKeepHandlerImpl{" +
                "type='" + type + '\'' +
                ", bridges=" + bridges +
                ", keeper=" + keeper +
                '}';
    }
}
