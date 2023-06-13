package com.dwarfeng.fdr.impl.handler.bridge.mock;

import com.dwarfeng.fdr.impl.handler.bridge.FullPersister;
import com.dwarfeng.fdr.sdk.util.ViewUtil;
import com.dwarfeng.fdr.stack.bean.dto.LookupInfo;
import com.dwarfeng.fdr.stack.bean.dto.LookupResult;
import com.dwarfeng.fdr.stack.bean.dto.NativeQueryInfo;
import com.dwarfeng.fdr.stack.bean.dto.QueryResult;
import com.dwarfeng.fdr.stack.struct.Data;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 模拟的持久器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public abstract class MockBridgePersister<D extends Data> extends FullPersister<D> {

    public static final String LOOKUP_PRESET_DEFAULT = "default";
    public static final String NATIVE_QUERY_PRESET_DEFAULT = "default";

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

    private LookupResult<D> doSingleLookup(LookupInfo lookupInfo) throws Exception {
        // 展开查询信息。
        long lookupStartTimestamp = ViewUtil.validStartDate(lookupInfo.getStartDate()).getTime();
        long lookupEndTimestamp = ViewUtil.validEndDate(lookupInfo.getEndDate()).getTime();
        int page = ViewUtil.validPage(lookupInfo.getPage());
        int rows = ViewUtil.validRows(lookupInfo.getRows());

        // 检查预设是否合法。
        String preset = lookupInfo.getPreset();
        if (!LOOKUP_PRESET_DEFAULT.equals(preset)) {
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

    @Override
    protected QueryResult doNativeQuery(NativeQueryInfo queryInfo) throws Exception {
        return doSingleNativeQuery(queryInfo);
    }

    @Override
    protected List<QueryResult> doNativeQuery(List<NativeQueryInfo> queryInfos) throws Exception {
        List<QueryResult> result = new ArrayList<>();
        for (NativeQueryInfo queryInfo : queryInfos) {
            result.add(doSingleNativeQuery(queryInfo));
        }
        return result;
    }

    private QueryResult doSingleNativeQuery(NativeQueryInfo queryInfo) throws Exception {
        // 展开查询信息。
        long queryStartTimestamp = ViewUtil.validStartDate(queryInfo.getStartDate()).getTime();
        long queryEndTimestamp = ViewUtil.validEndDate(queryInfo.getEndDate()).getTime();

        // 检查预设是否合法。
        String preset = queryInfo.getPreset();
        if (!NATIVE_QUERY_PRESET_DEFAULT.equals(preset)) {
            throw new IllegalArgumentException("预设不合法: " + preset);
        }

        // 展开参数。
        String[] params = queryInfo.getParams();
        long period = Long.parseLong(params[0]);
        long offset = Long.parseLong(params[1]);

        return mockNativeQuery(
                queryInfo.getPointKeys(),
                queryStartTimestamp, queryEndTimestamp,
                queryInfo.isIncludeStartDate(), queryInfo.isIncludeEndDate(),
                period, offset
        );
    }

    private QueryResult mockNativeQuery(
            List<LongIdKey> pointKeys, long queryStartTimestamp, long queryEndTimestamp, boolean includeStartDate,
            boolean includeEndDate, long period, long offset
    ) throws Exception {
        // 获取当前时间戳，用于模拟延迟。
        long anchorTimestamp = System.currentTimeMillis();

        // 获取配置。
        long nativeQueryBeforeDelay = config.getNativeQueryBeforeDelay();
        long nativeQueryDelayPerSecond = config.getNativeQueryDelayPerSecond();
        long nativeQueryAfterDelay = config.getNativeQueryAfterDelay();

        // 根据查询区间的开闭情况调整查询区间。
        long actualQueryStartTimestamp = includeStartDate ? queryStartTimestamp : queryStartTimestamp + 1;
        long actualQueryEndTimestamp = includeEndDate ? queryEndTimestamp : queryEndTimestamp - 1;

        if (nativeQueryBeforeDelay > 0) {
            anchorTimestamp += nativeQueryBeforeDelay;
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        // 计算第一个点的数据起始时间。
        // 数据的第一个起始时间应该大于等于 actualQueryStartTimestamp，且减去 offset 后应该是 period 的整数倍。
        long cursor = actualQueryStartTimestamp + (actualQueryStartTimestamp - offset) % period;
        // 在 cursor 小于等于 actualQueryEndTimestamp 之前，生成数据，随后 cursor 自增 period。
        List<List<QueryResult.Item>> itemsList = new ArrayList<>(pointKeys.size());
        for (int i = 0; i < pointKeys.size(); i++) {
            itemsList.add(new ArrayList<>());
        }
        while (cursor <= actualQueryEndTimestamp) {
            for (int i = 0; i < pointKeys.size(); i++) {
                LongIdKey pointKey = pointKeys.get(i);
                Object value = dataValueGenerator.nextValue(pointKey);
                itemsList.get(i).add(new QueryResult.Item(pointKey, value, new Date(cursor)));
            }
            cursor += period;
        }
        List<QueryResult.Sequence> sequences = new ArrayList<>(pointKeys.size());
        for (int i = 0; i < pointKeys.size(); i++) {
            LongIdKey pointKey = pointKeys.get(i);
            List<QueryResult.Item> items = itemsList.get(i);
            Date startDate = new Date(queryStartTimestamp);
            Date endDate = new Date(queryEndTimestamp);
            sequences.add(new QueryResult.Sequence(pointKey, items, startDate, endDate));
        }

        // 模拟延迟，延迟时间为(查询的时间范围 / 1000 + 1) * nativeQueryDelayPerSecond。
        // 如果时间区间小于等于0，则不延迟。
        if (nativeQueryDelayPerSecond > 0) {
            long timeRange = actualQueryEndTimestamp - actualQueryStartTimestamp;
            long delay = timeRange <= 0 ? 0 : (timeRange / 1000 + 1) * nativeQueryDelayPerSecond;
            anchorTimestamp += delay;
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        if (nativeQueryAfterDelay > 0) {
            anchorTimestamp += nativeQueryAfterDelay;
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        // 返回结果。
        return new QueryResult(sequences);
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
