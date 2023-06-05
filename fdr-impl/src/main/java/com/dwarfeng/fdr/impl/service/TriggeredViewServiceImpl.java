package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.dto.TriggeredData;
import com.dwarfeng.fdr.stack.handler.TriggeredViewHandler;
import com.dwarfeng.fdr.stack.service.TriggeredViewService;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class TriggeredViewServiceImpl extends AbstractViewService<TriggeredData> implements TriggeredViewService {

    public TriggeredViewServiceImpl(
            TriggeredViewHandler triggeredWatchHandler,
            @Qualifier("mapServiceExceptionMapper") ServiceExceptionMapper sem
    ) {
        super(triggeredWatchHandler, sem);
    }

    @Override
    public String toString() {
        return "TriggeredViewServiceImpl{" +
                "viewHandler=" + viewHandler +
                ", sem=" + sem +
                '}';
    }
}
