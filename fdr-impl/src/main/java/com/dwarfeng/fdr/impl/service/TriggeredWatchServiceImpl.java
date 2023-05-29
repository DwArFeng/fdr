package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.dto.TriggeredData;
import com.dwarfeng.fdr.stack.handler.TriggeredWatchHandler;
import com.dwarfeng.fdr.stack.service.TriggeredWatchService;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class TriggeredWatchServiceImpl extends AbstractWatchService<TriggeredData> implements TriggeredWatchService {

    public TriggeredWatchServiceImpl(
            TriggeredWatchHandler triggeredWatchHandler,
            @Qualifier("mapServiceExceptionMapper") ServiceExceptionMapper sem
    ) {
        super(triggeredWatchHandler, sem);
    }

    @Override
    public String toString() {
        return "TriggeredWatchServiceImpl{" +
                "watchHandler=" + watchHandler +
                ", sem=" + sem +
                '}';
    }
}
