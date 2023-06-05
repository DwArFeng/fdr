package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.dto.NormalData;
import com.dwarfeng.fdr.stack.handler.NormalViewHandler;
import com.dwarfeng.fdr.stack.service.NormalViewService;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class NormalViewServiceImpl extends AbstractViewService<NormalData> implements NormalViewService {

    public NormalViewServiceImpl(
            NormalViewHandler normalWatchHandler,
            @Qualifier("mapServiceExceptionMapper") ServiceExceptionMapper sem
    ) {
        super(normalWatchHandler, sem);
    }

    @Override
    public String toString() {
        return "NormalViewServiceImpl{" +
                "viewHandler=" + viewHandler +
                ", sem=" + sem +
                '}';
    }
}
