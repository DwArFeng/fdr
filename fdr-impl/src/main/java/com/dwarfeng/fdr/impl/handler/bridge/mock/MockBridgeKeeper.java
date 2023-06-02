package com.dwarfeng.fdr.impl.handler.bridge.mock;

import com.dwarfeng.fdr.impl.handler.bridge.AbstractKeeper;
import com.dwarfeng.fdr.stack.struct.Data;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 模拟保持器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public abstract class MockBridgeKeeper<D extends Data> extends AbstractKeeper<D> {

    protected final MockBridgeConfig config;
    protected final MockBridgeDataValueGenerator dataValueGenerator;

    public MockBridgeKeeper(
            MockBridgeConfig config,
            MockBridgeDataValueGenerator dataValueGenerator
    ) {
        super(false);
        this.config = config;
        this.dataValueGenerator = dataValueGenerator;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected void doUpdate(D data) {
        long startTimestamp = System.currentTimeMillis();
        long anchorTimestamp = System.currentTimeMillis();

        long updateBeforeDelay = config.getUpdateBeforeDelay();
        long updateDelay = config.getUpdateDelay();
        long updateAfterDelay = config.getUpdateAfterDelay();

        if (updateBeforeDelay > 0) {
            anchorTimestamp += updateBeforeDelay;
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        if (updateDelay > 0) {
            anchorTimestamp += updateDelay;
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        if (updateAfterDelay > 0) {
            anchorTimestamp += updateAfterDelay;
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        long endTimestamp = System.currentTimeMillis();
        getLogger().info("模拟更新数据, 耗时 {} 毫秒", endTimestamp - startTimestamp);
        getLogger().debug("数据内容: {}", data);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected void doUpdate(List<D> datas) {
        long startTimestamp = System.currentTimeMillis();
        long anchorTimestamp = System.currentTimeMillis();

        long updateBeforeDelay = config.getUpdateBeforeDelay();
        long updateDelay = config.getUpdateDelay();
        long updateAfterDelay = config.getUpdateAfterDelay();

        if (updateBeforeDelay > 0) {
            anchorTimestamp += updateBeforeDelay;
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        if (updateDelay > 0) {
            anchorTimestamp += updateDelay * datas.size();
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        if (updateAfterDelay > 0) {
            anchorTimestamp += updateAfterDelay;
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        long endTimestamp = System.currentTimeMillis();
        getLogger().info("模拟更新数据, 耗时 {} 毫秒", endTimestamp - startTimestamp);
        getLogger().debug("数据内容: {}", datas);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected D doInspect(LongIdKey pointKey) throws Exception {
        long startTimestamp = System.currentTimeMillis();
        long anchorTimestamp = System.currentTimeMillis();

        long inspectBeforeDelay = config.getInspectBeforeDelay();
        long inspectDelay = config.getInspectDelay();
        long inspectAfterDelay = config.getInspectAfterDelay();

        if (inspectBeforeDelay > 0) {
            anchorTimestamp += inspectBeforeDelay;
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        Object value = dataValueGenerator.nextValue(pointKey);
        D result = generateData(pointKey, value, new Date());

        if (inspectDelay > 0) {
            anchorTimestamp += inspectDelay;
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        if (inspectAfterDelay > 0) {
            anchorTimestamp += inspectAfterDelay;
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        long endTimestamp = System.currentTimeMillis();
        getLogger().info("模拟检查数据, 耗时 {} 毫秒", endTimestamp - startTimestamp);
        getLogger().debug("数据内容: {}", result);

        return result;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected List<D> doInspect(List<LongIdKey> pointKeys) throws Exception {
        long startTimestamp = System.currentTimeMillis();
        long anchorTimestamp = System.currentTimeMillis();

        long inspectBeforeDelay = config.getInspectBeforeDelay();
        long inspectDelay = config.getInspectDelay();
        long inspectAfterDelay = config.getInspectAfterDelay();

        if (inspectBeforeDelay > 0) {
            anchorTimestamp += inspectBeforeDelay;
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        List<D> result = new ArrayList<>();
        for (LongIdKey pointKey : pointKeys) {
            Object value = dataValueGenerator.nextValue(pointKey);
            result.add(generateData(pointKey, value, new Date()));
        }
        if (inspectDelay > 0) {
            anchorTimestamp += inspectDelay * pointKeys.size();
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        if (inspectAfterDelay > 0) {
            anchorTimestamp += inspectAfterDelay;
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        long endTimestamp = System.currentTimeMillis();
        getLogger().info("模拟检查数据, 耗时 {} 毫秒", endTimestamp - startTimestamp);
        getLogger().debug("数据内容: {}", result);

        return result;
    }

    protected abstract Logger getLogger();

    protected abstract D generateData(LongIdKey pointKey, Object value, Date date);

    @Override
    public String toString() {
        return "MockBridgeKeeper{" +
                "config=" + config +
                ", dataValueGenerator=" + dataValueGenerator +
                ", writeOnly=" + writeOnly +
                '}';
    }
}
