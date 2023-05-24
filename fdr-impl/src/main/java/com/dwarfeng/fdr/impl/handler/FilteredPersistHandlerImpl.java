package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.fdr.stack.handler.FilteredPersistHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class FilteredPersistHandlerImpl extends AbstractPersistHandler<FilteredData>
        implements FilteredPersistHandler {

    @Value("${persist.filtered_data.type}")
    private String type;

    public FilteredPersistHandlerImpl(List<Bridge> bridges) {
        super(bridges, FilteredData.class);
    }

    @PostConstruct
    public void init() throws Exception {
        super.init(type);
    }

    @Override
    public String toString() {
        return "FilteredPersistHandlerImpl{" +
                "type='" + type + '\'' +
                ", bridges=" + bridges +
                ", dataClazz=" + dataClazz +
                ", persister=" + persister +
                '}';
    }
}
