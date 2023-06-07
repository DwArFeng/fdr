package com.dwarfeng.fdr.impl.handler.bridge.mock;

import com.dwarfeng.fdr.impl.handler.bridge.FullPersister;
import com.dwarfeng.fdr.sdk.util.ViewUtil;
import com.dwarfeng.fdr.stack.bean.dto.LookupInfo;
import com.dwarfeng.fdr.stack.bean.dto.LookupResult;
import com.dwarfeng.fdr.stack.struct.Data;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 模拟的持久化器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public abstract class MockBridgePersister<D extends Data> extends FullPersister<D> {

    public static final String DEFAULT = "default";

    protected final MockBridgeConfig config;
    protected final MockBridgeDataValueGenerator dataValueGenerator;

    public MockBridgePersister(MockBridgeConfig config, MockBridgeDataValueGenerator dataValueGenerator) {
        this.config = config;
        this.dataValueGenerator = dataValueGenerator;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected void doRecord(D data) {
        long startTimestamp = System.currentTimeMillis();
        long anchorTimestamp = System.currentTimeMillis();

        long recordBeforeDelay = config.getRecordBeforeDelay();
        long recordDelay = config.getRecordDelay();
        long recordAfterDelay = config.getRecordAfterDelay();

        if (recordBeforeDelay > 0) {
            anchorTimestamp += recordBeforeDelay;
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        if (recordDelay > 0) {
            anchorTimestamp += recordDelay;
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        if (recordAfterDelay > 0) {
            anchorTimestamp += recordAfterDelay;
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        long endTimestamp = System.currentTimeMillis();
        getLogger().info("模拟记录数据, 耗时 {} 毫秒", endTimestamp - startTimestamp);
        getLogger().debug("数据内容: {}", data);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected void doRecord(List<D> datas) {
        long startTimestamp = System.currentTimeMillis();
        long anchorTimestamp = System.currentTimeMillis();

        long recordBeforeDelay = config.getRecordBeforeDelay();
        long recordDelay = config.getRecordDelay();
        long recordAfterDelay = config.getRecordAfterDelay();

        if (recordBeforeDelay > 0) {
            anchorTimestamp += recordBeforeDelay;
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        if (recordDelay > 0) {
            anchorTimestamp += recordDelay * datas.size();
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        if (recordAfterDelay > 0) {
            anchorTimestamp += recordAfterDelay;
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        long endTimestamp = System.currentTimeMillis();
        getLogger().info("模拟记录数据, 耗时 {} 毫秒", endTimestamp - startTimestamp);
        getLogger().debug("数据内容: {}", datas);
    }

    @Override
    protected LookupResult<D> doLookup(LookupInfo lookupInfo) throws Exception {
        return doSingleLookup(lookupInfo);
    }

    @Override
    protected List<LookupResult<D>> doLookup(List<LookupInfo> lookupInfos) throws Exception {
        List<LookupResult<D>> result = new ArrayList<>();
        for (LookupInfo lookupInfo : lookupInfos) {
            result.add(doSingleLookup(lookupInfo));
        }
        return result;
    }

    @Nonnull
    private LookupResult<D> doSingleLookup(LookupInfo lookupInfo) throws Exception {
        // 展开查询信息。
        long lookupStartTimestamp = ViewUtil.validStartDate(lookupInfo.getStartDate()).getTime();
        long lookupEndTimestamp = ViewUtil.validEndDate(lookupInfo.getEndDate()).getTime();
        int page = ViewUtil.validPage(lookupInfo.getPage());
        int rows = ViewUtil.validRows(lookupInfo.getRows());

        // 检查预设是否合法。
        String preset = lookupInfo.getPreset();
        if (!DEFAULT.equals(preset)) {
            throw new IllegalArgumentException("预设不合法");
        }

        return mockLookup(
                lookupInfo.getPointKey(),
                lookupStartTimestamp, lookupEndTimestamp,
                lookupInfo.isIncludeStartDate(), lookupInfo.isIncludeEndDate(),
                page, rows
        );
    }

    private LookupResult<D> mockLookup(
            LongIdKey pointKey, long lookupStartTimestamp, long lookupEndTimestamp,
            boolean includeStartDate, boolean includeEndDate, int page, int rows
    ) throws Exception {
        long startTimestamp = System.currentTimeMillis();
        long anchorTimestamp = System.currentTimeMillis();

        long lookupBeforeDelay = config.getLookupBeforeDelay();
        long lookupOffsetDelay = config.getLookupOffsetDelay();
        long lookupDelay = config.getLookupDelay();
        long lookupAfterDelay = config.getLookupAfterDelay();

        if (lookupBeforeDelay > 0) {
            anchorTimestamp += lookupBeforeDelay;
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        long lookupDataInterval = config.getLookupDataInterval();
        // 计算数据的起始时间，对齐到 lookupDataInterval 的整数倍。
        long dataStartTimestamp = lookupStartTimestamp - lookupStartTimestamp % lookupDataInterval;
        if (dataStartTimestamp == lookupStartTimestamp && !includeStartDate) {
            dataStartTimestamp += lookupDataInterval;
        }
        // 计算数据的数量。
        int dataCount = (int) ((lookupEndTimestamp - dataStartTimestamp) / lookupDataInterval);
        if (dataStartTimestamp + dataCount * lookupDataInterval == lookupEndTimestamp && !includeEndDate) {
            dataCount--;
        }
        // 计算实际偏移量。
        int actualOffset = Math.min(page * rows, dataCount);
        // 计算返回数据量。
        int actualLimit = Math.min(rows, dataCount - actualOffset);
        // 判断数据是否还有更多的数据。
        boolean hasMore = dataCount - actualOffset - actualLimit > 0;
        // 根据发生日期的顺序生成返回的数据。
        List<D> datas = new ArrayList<>(actualLimit);
        for (int i = 0; i < actualLimit; i++) {
            Object value = dataValueGenerator.nextValue(pointKey);
            datas.add(generateData(
                    pointKey,
                    value,
                    new Date(dataStartTimestamp + (actualOffset + i) * lookupDataInterval)
            ));
        }
        LookupResult<D> lookupResult = new LookupResult<>(pointKey, datas, hasMore);

        if (lookupOffsetDelay > 0) {
            anchorTimestamp += lookupOffsetDelay * actualOffset;
            ThreadUtil.sleepUntil(anchorTimestamp);
        }
        if (lookupDelay > 0) {
            anchorTimestamp += lookupDelay * actualLimit;
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        if (lookupAfterDelay > 0) {
            anchorTimestamp += lookupAfterDelay;
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        long endTimestamp = System.currentTimeMillis();
        getLogger().info("模拟查询数据, 耗时 {} 毫秒", endTimestamp - startTimestamp);
        getLogger().debug("查询结果: {}", lookupResult);

        return lookupResult;
    }

    protected abstract Logger getLogger();

    protected abstract D generateData(LongIdKey pointKey, Object value, Date date);

    @Override
    public String toString() {
        return "MockBridgePersister{" +
                "config=" + config +
                ", dataValueGenerator=" + dataValueGenerator +
                '}';
    }
}
