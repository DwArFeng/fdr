package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.bean.dto.NormalData;
import com.dwarfeng.fdr.stack.handler.NormalKeepHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class NormalKeepHandlerImpl extends AbstractKeepHandler<NormalData> implements NormalKeepHandler {

    @Value("${keep.normal_data.type}")
    private String type;

    public NormalKeepHandlerImpl(List<Bridge> bridges) {
        super(bridges, NormalData.class);
    }

    @PostConstruct
    public void init() throws Exception {
        super.init(type);
    }

    @Override
    public String toString() {
        return "NormalKeepHandlerImpl{" +
                "type='" + type + '\'' +
                ", bridges=" + bridges +
                ", dataClazz=" + dataClazz +
                ", keeper=" + keeper +
                '}';
    }
}
