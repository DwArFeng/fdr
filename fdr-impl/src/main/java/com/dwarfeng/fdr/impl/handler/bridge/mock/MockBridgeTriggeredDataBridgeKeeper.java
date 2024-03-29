package com.dwarfeng.fdr.impl.handler.bridge.mock;

import com.dwarfeng.fdr.stack.bean.dto.TriggeredData;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 模拟被触发数据保持器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class MockBridgeTriggeredDataBridgeKeeper extends MockBridgeKeeper<TriggeredData> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockBridgeTriggeredDataBridgeKeeper.class);

    private final MockBridgeRandomGenerator randomGenerator;

    public MockBridgeTriggeredDataBridgeKeeper(
            MockBridgeConfig config,
            MockBridgeDataValueGenerator dataValueGenerator,
            MockBridgeRandomGenerator randomGenerator
    ) {
        super(config, dataValueGenerator);
        this.randomGenerator = randomGenerator;
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    @Override
    protected TriggeredData generateData(LongIdKey pointKey, Object value, Date date) {
        LongIdKey triggerKey = randomGenerator.nextLongIdKey();
        String message = randomGenerator.nextString();
        return new TriggeredData(pointKey, triggerKey, value, message, date);
    }

    @Override
    public String toString() {
        return "MockBridgeTriggeredDataBridgeKeeper{" +
                "randomGenerator=" + randomGenerator +
                ", config=" + config +
                ", dataValueGenerator=" + dataValueGenerator +
                '}';
    }
}
