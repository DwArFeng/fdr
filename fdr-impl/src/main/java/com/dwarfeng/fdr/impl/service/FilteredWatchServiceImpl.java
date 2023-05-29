package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.fdr.stack.handler.FilteredWatchHandler;
import com.dwarfeng.fdr.stack.service.FilteredWatchService;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class FilteredWatchServiceImpl extends AbstractWatchService<FilteredData> implements FilteredWatchService {

    public FilteredWatchServiceImpl(
            FilteredWatchHandler filteredWatchHandler,
            @Qualifier("mapServiceExceptionMapper") ServiceExceptionMapper sem
    ) {
        super(filteredWatchHandler, sem);
    }

    @Override
    public String toString() {
        return "FilteredWatchServiceImpl{" +
                "watchHandler=" + watchHandler +
                ", sem=" + sem +
                '}';
    }
}
