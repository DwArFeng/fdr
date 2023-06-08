package com.dwarfeng.fdr.impl.handler.bridge.hibernate;

import com.dwarfeng.dct.handler.ValueCodingHandler;
import com.dwarfeng.fdr.impl.handler.bridge.hibernate.bean.HibernateBridgeNormalData;
import com.dwarfeng.fdr.impl.handler.bridge.hibernate.service.HibernateBridgeMaintainService;
import com.dwarfeng.fdr.impl.handler.bridge.hibernate.service.HibernateBridgeNormalDataMaintainService;
import com.dwarfeng.fdr.stack.bean.dto.NormalData;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * Hibernate 桥接被过滤数据持久化器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class HibernateBridgeNormalDataPersister extends
        HibernateBridgePersister<NormalData, HibernateBridgeNormalData> {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    protected HibernateBridgeNormalDataPersister(
            HibernateBridgeMaintainService<HibernateBridgeNormalData> service,
            @Qualifier("hibernateBridge.valueCodingHandler") ValueCodingHandler valueCodingHandler,
            ThreadPoolTaskExecutor executor
    ) {
        super(service, valueCodingHandler, executor);
    }

    @Override
    protected HibernateBridgeNormalData transform(NormalData data) throws Exception {
        String flatValue = valueCodingHandler.encode(data.getValue());
        return new HibernateBridgeNormalData(
                null,
                data.getPointKey(),
                flatValue,
                data.getHappenedDate()
        );
    }

    @Override
    protected String resolveServicePreset(boolean includeStartDate, boolean includeEndDate) {
        String servicePreset;
        if (includeStartDate && includeEndDate) {
            servicePreset = HibernateBridgeNormalDataMaintainService.CHILD_FOR_POINT_BETWEEN_CLOSE_CLOSE;
        } else if (includeStartDate) {
            servicePreset = HibernateBridgeNormalDataMaintainService.CHILD_FOR_POINT_BETWEEN_CLOSE_OPEN;
        } else if (includeEndDate) {
            servicePreset = HibernateBridgeNormalDataMaintainService.CHILD_FOR_POINT_BETWEEN_OPEN_CLOSE;
        } else {
            servicePreset = HibernateBridgeNormalDataMaintainService.CHILD_FOR_POINT_BETWEEN_OPEN_OPEN;
        }
        return servicePreset;
    }

    @Override
    protected NormalData reverseTransform(HibernateBridgeNormalData t) throws Exception {
        Object value = valueCodingHandler.decode(t.getValue());
        return new NormalData(
                t.getPointKey(),
                value,
                t.getHappenedDate()
        );
    }

    @Override
    public String toString() {
        return "HibernateBridgeNormalDataPersister{" +
                "service=" + service +
                ", valueCodingHandler=" + valueCodingHandler +
                ", executor=" + executor +
                '}';
    }
}
