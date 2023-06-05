package com.dwarfeng.fdr.impl.handler.bridge.mock;

import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 模拟被过滤数据保持器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class MockBridgeFilteredDataBridgeKeeper extends MockBridgeKeeper<FilteredData> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockBridgeFilteredDataBridgeKeeper.class);

    private final MockBridgeRandomGenerator randomGenerator;

    public MockBridgeFilteredDataBridgeKeeper(
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
    protected FilteredData generateData(LongIdKey pointKey, Object value, Date date) {
        LongIdKey filterKey = randomGenerator.nextLongIdKey();
        String message = randomGenerator.nextString();
        return new FilteredData(pointKey, filterKey, value, message, date);
    }

    @Override
    public String toString() {
        return "MockBridgeFilteredDataBridgeKeeper{" +
                "randomGenerator=" + randomGenerator +
                ", config=" + config +
                ", dataValueGenerator=" + dataValueGenerator +
                '}';
    }
}
