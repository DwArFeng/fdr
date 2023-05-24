package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.fdr.stack.handler.FilteredWatchHandler;
import com.dwarfeng.fdr.stack.service.FilteredWatchQosService;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.springframework.stereotype.Component;

@Component
public class FilteredWatchQosServiceImpl extends AbstractWatchQosService<FilteredData>
        implements FilteredWatchQosService {

    public FilteredWatchQosServiceImpl(
            FilteredWatchHandler filteredWatchHandler,
            ServiceExceptionMapper sem
    ) {
        super(filteredWatchHandler, sem);
    }

    @Override
    public String toString() {
        return "FilteredWatchQosServiceImpl{" +
                "watchHandler=" + watchHandler +
                ", sem=" + sem +
                '}';
    }
}
