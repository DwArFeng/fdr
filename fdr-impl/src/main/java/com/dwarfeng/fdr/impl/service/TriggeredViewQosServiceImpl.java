package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.dto.TriggeredData;
import com.dwarfeng.fdr.stack.handler.TriggeredViewHandler;
import com.dwarfeng.fdr.stack.service.TriggeredViewQosService;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class TriggeredViewQosServiceImpl extends AbstractViewQosService<TriggeredData>
        implements TriggeredViewQosService {

    public TriggeredViewQosServiceImpl(
            TriggeredViewHandler triggeredWatchHandler,
            @Qualifier("mapServiceExceptionMapper") ServiceExceptionMapper sem
    ) {
        super(triggeredWatchHandler, sem);
    }

    @Override
    public String toString() {
        return "TriggeredViewQosServiceImpl{" +
                "viewHandler=" + viewHandler +
                ", sem=" + sem +
                '}';
    }
}
