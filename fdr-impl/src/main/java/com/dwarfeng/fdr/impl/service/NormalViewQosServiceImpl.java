package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.dto.NormalData;
import com.dwarfeng.fdr.stack.handler.NormalViewHandler;
import com.dwarfeng.fdr.stack.service.NormalViewQosService;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.springframework.stereotype.Component;

@Component
public class NormalViewQosServiceImpl extends AbstractViewQosService<NormalData>
        implements NormalViewQosService {

    public NormalViewQosServiceImpl(
            NormalViewHandler normalWatchHandler,
            ServiceExceptionMapper sem
    ) {
        super(normalWatchHandler, sem);
    }

    @Override
    public String toString() {
        return "NormalViewQosServiceImpl{" +
                "viewHandler=" + viewHandler +
                ", sem=" + sem +
                '}';
    }
}
