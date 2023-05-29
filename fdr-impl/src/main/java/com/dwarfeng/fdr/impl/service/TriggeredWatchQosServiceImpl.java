package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.dto.TriggeredData;
import com.dwarfeng.fdr.stack.handler.TriggeredWatchHandler;
import com.dwarfeng.fdr.stack.service.TriggeredWatchQosService;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class TriggeredWatchQosServiceImpl extends AbstractWatchQosService<TriggeredData>
        implements TriggeredWatchQosService {

    public TriggeredWatchQosServiceImpl(
            TriggeredWatchHandler triggeredWatchHandler,
            @Qualifier("mapServiceExceptionMapper") ServiceExceptionMapper sem
    ) {
        super(triggeredWatchHandler, sem);
    }

    @Override
    public String toString() {
        return "TriggeredWatchQosServiceImpl{" +
                "watchHandler=" + watchHandler +
                ", sem=" + sem +
                '}';
    }
}
