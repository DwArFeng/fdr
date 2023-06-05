package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.fdr.stack.handler.FilteredViewHandler;
import com.dwarfeng.fdr.stack.service.FilteredViewService;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class FilteredViewServiceImpl extends AbstractViewService<FilteredData> implements FilteredViewService {

    public FilteredViewServiceImpl(
            FilteredViewHandler filteredWatchHandler,
            @Qualifier("mapServiceExceptionMapper") ServiceExceptionMapper sem
    ) {
        super(filteredWatchHandler, sem);
    }

    @Override
    public String toString() {
        return "FilteredViewServiceImpl{" +
                "viewHandler=" + viewHandler +
                ", sem=" + sem +
                '}';
    }
}
