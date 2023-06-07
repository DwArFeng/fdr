package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.fdr.stack.handler.FilteredKeepHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class FilteredKeepHandlerImpl extends AbstractKeepHandler<FilteredData> implements FilteredKeepHandler {

    @Value("${keep.filtered_data.type}")
    private String type;

    public FilteredKeepHandlerImpl(List<Bridge> bridges) {
        super(bridges);
    }

    @PostConstruct
    public void init() throws Exception {
        super.init(type);
    }

    @Override
    protected Bridge.Keeper<FilteredData> getKeeperFromBridge(Bridge bridge) throws Exception {
        return bridge.getFilteredDataKeeper();
    }

    @Override
    public String toString() {
        return "FilteredKeepHandlerImpl{" +
                "type='" + type + '\'' +
                ", bridges=" + bridges +
                ", keeper=" + keeper +
                '}';
    }
}
