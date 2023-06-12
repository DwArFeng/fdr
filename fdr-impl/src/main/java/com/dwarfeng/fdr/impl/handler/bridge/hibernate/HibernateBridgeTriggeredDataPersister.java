package com.dwarfeng.fdr.impl.handler.bridge.hibernate;

import com.dwarfeng.dct.handler.ValueCodingHandler;
import com.dwarfeng.fdr.impl.handler.bridge.hibernate.bean.HibernateBridgeTriggeredData;
import com.dwarfeng.fdr.impl.handler.bridge.hibernate.service.HibernateBridgeMaintainService;
import com.dwarfeng.fdr.impl.handler.bridge.hibernate.service.HibernateBridgeTriggeredDataMaintainService;
import com.dwarfeng.fdr.stack.bean.dto.TriggeredData;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * Hibernate 桥接被触发数据持久器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class HibernateBridgeTriggeredDataPersister extends
        HibernateBridgePersister<TriggeredData, HibernateBridgeTriggeredData> {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    protected HibernateBridgeTriggeredDataPersister(
            HibernateBridgeMaintainService<HibernateBridgeTriggeredData> service,
            @Qualifier("hibernateBridge.valueCodingHandler") ValueCodingHandler valueCodingHandler,
            ThreadPoolTaskExecutor executor
    ) {
        super(service, valueCodingHandler, executor);
    }

    @Override
    protected HibernateBridgeTriggeredData transform(TriggeredData data) throws Exception {
        String flatValue = valueCodingHandler.encode(data.getValue());
        return new HibernateBridgeTriggeredData(
                null,
                data.getPointKey(),
                data.getTriggerKey(),
                flatValue,
                data.getMessage(),
                data.getHappenedDate()
        );
    }

    @Override
    protected String resolveServicePreset(boolean includeStartDate, boolean includeEndDate) {
        String servicePreset;
        if (includeStartDate && includeEndDate) {
            servicePreset = HibernateBridgeTriggeredDataMaintainService.CHILD_FOR_POINT_BETWEEN_CLOSE_CLOSE;
        } else if (includeStartDate) {
            servicePreset = HibernateBridgeTriggeredDataMaintainService.CHILD_FOR_POINT_BETWEEN_CLOSE_OPEN;
        } else if (includeEndDate) {
            servicePreset = HibernateBridgeTriggeredDataMaintainService.CHILD_FOR_POINT_BETWEEN_OPEN_CLOSE;
        } else {
            servicePreset = HibernateBridgeTriggeredDataMaintainService.CHILD_FOR_POINT_BETWEEN_OPEN_OPEN;
        }
        return servicePreset;
    }

    @Override
    protected TriggeredData reverseTransform(HibernateBridgeTriggeredData t) throws Exception {
        Object value = valueCodingHandler.decode(t.getValue());
        return new TriggeredData(
                t.getPointKey(),
                t.getTriggerKey(),
                value,
                t.getMessage(),
                t.getHappenedDate()
        );
    }

    @Override
    public String toString() {
        return "HibernateBridgeTriggeredDataPersister{" +
                "service=" + service +
                ", valueCodingHandler=" + valueCodingHandler +
                ", executor=" + executor +
                '}';
    }
}
