package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.dto.NormalData;
import com.dwarfeng.fdr.stack.handler.NormalWatchHandler;
import com.dwarfeng.fdr.stack.service.NormalWatchQosService;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class NormalWatchQosServiceImpl extends AbstractWatchQosService<NormalData>
        implements NormalWatchQosService {

    public NormalWatchQosServiceImpl(
            NormalWatchHandler normalWatchHandler,
            @Qualifier("mapServiceExceptionMapper") ServiceExceptionMapper sem
    ) {
        super(normalWatchHandler, sem);
    }

    @Override
    public String toString() {
        return "NormalWatchQosServiceImpl{" +
                "watchHandler=" + watchHandler +
                ", sem=" + sem +
                '}';
    }
}
