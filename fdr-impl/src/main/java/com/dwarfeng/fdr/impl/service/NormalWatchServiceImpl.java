package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.dto.NormalData;
import com.dwarfeng.fdr.stack.handler.NormalWatchHandler;
import com.dwarfeng.fdr.stack.service.NormalWatchService;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.springframework.stereotype.Component;

@Component
public class NormalWatchServiceImpl extends AbstractWatchService<NormalData> implements NormalWatchService {

    public NormalWatchServiceImpl(
            NormalWatchHandler normalWatchHandler,
            ServiceExceptionMapper sem
    ) {
        super(normalWatchHandler, sem);
    }

    @Override
    public String toString() {
        return "NormalWatchServiceImpl{" +
                "watchHandler=" + watchHandler +
                ", sem=" + sem +
                '}';
    }
}
