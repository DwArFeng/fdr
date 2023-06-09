package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.fdr.stack.handler.FilteredViewHandler;
import com.dwarfeng.fdr.stack.service.FilteredViewQosService;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.springframework.stereotype.Component;

@Component
public class FilteredViewQosServiceImpl extends AbstractViewQosService<FilteredData>
        implements FilteredViewQosService {

    public FilteredViewQosServiceImpl(
            FilteredViewHandler filteredWatchHandler,
            ServiceExceptionMapper sem
    ) {
        super(filteredWatchHandler, sem);
    }

    @Override
    public String toString() {
        return "FilteredViewQosServiceImpl{" +
                "viewHandler=" + viewHandler +
                ", sem=" + sem +
                '}';
    }
}
