package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.bean.dto.NormalData;
import com.dwarfeng.fdr.stack.handler.NormalPersistHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class NormalPersistHandlerImpl extends AbstractPersistHandler<NormalData>
        implements NormalPersistHandler {

    @Value("${persist.normal_data.type}")
    private String type;

    public NormalPersistHandlerImpl(List<Bridge> bridges) {
        super(bridges, NormalData.class);
    }

    @PostConstruct
    public void init() throws Exception {
        super.init(type);
    }

    @Override
    public String toString() {
        return "NormalPersistHandlerImpl{" +
                "type='" + type + '\'' +
                ", bridges=" + bridges +
                ", dataClazz=" + dataClazz +
                ", persister=" + persister +
                '}';
    }
}
