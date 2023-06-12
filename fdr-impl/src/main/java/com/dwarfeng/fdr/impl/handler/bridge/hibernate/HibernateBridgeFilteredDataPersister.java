package com.dwarfeng.fdr.impl.handler.bridge.hibernate;

import com.dwarfeng.dct.handler.ValueCodingHandler;
import com.dwarfeng.fdr.impl.handler.bridge.hibernate.bean.HibernateBridgeFilteredData;
import com.dwarfeng.fdr.impl.handler.bridge.hibernate.service.HibernateBridgeFilteredDataMaintainService;
import com.dwarfeng.fdr.impl.handler.bridge.hibernate.service.HibernateBridgeMaintainService;
import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * Hibernate 桥接被过滤数据持久器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class HibernateBridgeFilteredDataPersister extends
        HibernateBridgePersister<FilteredData, HibernateBridgeFilteredData> {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    protected HibernateBridgeFilteredDataPersister(
            HibernateBridgeMaintainService<HibernateBridgeFilteredData> service,
            @Qualifier("hibernateBridge.valueCodingHandler") ValueCodingHandler valueCodingHandler,
            ThreadPoolTaskExecutor executor
    ) {
        super(service, valueCodingHandler, executor);
    }

    @Override
    protected HibernateBridgeFilteredData transform(FilteredData data) throws Exception {
        String flatValue = valueCodingHandler.encode(data.getValue());
        return new HibernateBridgeFilteredData(
                null,
                data.getPointKey(),
                data.getFilterKey(),
                flatValue,
                data.getMessage(),
                data.getHappenedDate()
        );
    }

    @Override
    protected String resolveServicePreset(boolean includeStartDate, boolean includeEndDate) {
        String servicePreset;
        if (includeStartDate && includeEndDate) {
            servicePreset = HibernateBridgeFilteredDataMaintainService.CHILD_FOR_POINT_BETWEEN_CLOSE_CLOSE;
        } else if (includeStartDate) {
            servicePreset = HibernateBridgeFilteredDataMaintainService.CHILD_FOR_POINT_BETWEEN_CLOSE_OPEN;
        } else if (includeEndDate) {
            servicePreset = HibernateBridgeFilteredDataMaintainService.CHILD_FOR_POINT_BETWEEN_OPEN_CLOSE;
        } else {
            servicePreset = HibernateBridgeFilteredDataMaintainService.CHILD_FOR_POINT_BETWEEN_OPEN_OPEN;
        }
        return servicePreset;
    }

    @Override
    protected FilteredData reverseTransform(HibernateBridgeFilteredData t) throws Exception {
        Object value = valueCodingHandler.decode(t.getValue());
        return new FilteredData(
                t.getPointKey(),
                t.getFilterKey(),
                value,
                t.getMessage(),
                t.getHappenedDate()
        );
    }

    @Override
    public String toString() {
        return "HibernateBridgeFilteredDataPersister{" +
                "service=" + service +
                ", valueCodingHandler=" + valueCodingHandler +
                ", executor=" + executor +
                '}';
    }
}
