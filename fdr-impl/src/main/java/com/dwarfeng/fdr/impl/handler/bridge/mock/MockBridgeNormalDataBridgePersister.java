package com.dwarfeng.fdr.impl.handler.bridge.mock;

import com.dwarfeng.fdr.stack.bean.dto.NormalData;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 模拟一般数据持久器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class MockBridgeNormalDataBridgePersister extends MockBridgePersister<NormalData> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockBridgeNormalDataBridgePersister.class);

    public MockBridgeNormalDataBridgePersister(
            MockBridgeConfig config,
            MockBridgeDataValueGenerator dataValueGenerator
    ) {
        super(config, dataValueGenerator);
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    @Override
    protected NormalData generateData(LongIdKey pointKey, Object value, Date date) {
        return new NormalData(pointKey, value, date);
    }

    @Override
    public String toString() {
        return "MockBridgeNormalDataBridgePersister{" +
                "config=" + config +
                ", dataValueGenerator=" + dataValueGenerator +
                ", lookupGuides=" + lookupGuides +
                '}';
    }
}
