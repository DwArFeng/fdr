package com.dwarfeng.fdr.impl.handler.bridge.mock;

import com.dwarfeng.fdr.sdk.handler.bridge.FullPersister;
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
    public static final String LOOKUP_PRESET_HIGH_PRECISION = "high_precision";
    public static final String NATIVE_QUERY_PRESET_DEFAULT = "default";
    public static final String NATIVE_QUERY_PRESET_HIGH_PRECISION = "high_precision";

    private static final int NANOSECONDS_PER_MILLISECOND = 1000000;

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

        // 根据预设值，调用不同的模拟查询方法。
        String preset = lookupInfo.getPreset();
        if (LOOKUP_PRESET_DEFAULT.equals(preset)) {
            return mockLookup(
                    lookupInfo.getPointKey(),
                    lookupStartTimestamp, lookupEndTimestamp,
                    lookupInfo.isIncludeStartDate(), lookupInfo.isIncludeEndDate(),
                    page, rows
            );
        } else if (LOOKUP_PRESET_HIGH_PRECISION.equals(preset)) {
            return mockLookupWithNanoOffset(
                    lookupInfo.getPointKey(),
                    lookupStartTimestamp, lookupEndTimestamp,
                    lookupInfo.isIncludeStartDate(), lookupInfo.isIncludeEndDate(),
                    page, rows
            );
        } else {
            throw new IllegalArgumentException("预设不合法: " + preset);
        }
    }

    @SuppressWarnings("DuplicatedCode")
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
            long happenedTimestamp = dataStartTimestamp + (actualOffset + i) * lookupDataInterval;
            datas.add(generateData(pointKey, value, new Date(happenedTimestamp), 0));
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

    @SuppressWarnings("DuplicatedCode")
    private LookupResult<D> mockLookupWithNanoOffset(
            LongIdKey pointKey, long lookupStartTimestamp, long lookupEndTimestamp,
            boolean includeStartDate, boolean includeEndDate, int page, int rows
    ) throws Exception {
        // 获取当前时间戳，用于模拟延迟。
        long startTimestamp = System.currentTimeMillis();
        long anchorTimestamp = System.currentTimeMillis();

        // 获取配置。
        long lookupBeforeDelay = config.getLookupBeforeDelay();
        long lookupOffsetDelay = config.getLookupOffsetDelay();
        long lookupDelay = config.getLookupDelay();
        long lookupAfterDelay = config.getLookupAfterDelay();

        if (lookupBeforeDelay > 0) {
            anchorTimestamp += lookupBeforeDelay;
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        // 将查询区间转换为纳秒时间轴，查询边界仍以毫秒精度为准，纳秒偏移固定为 0。
        long lookupStartTime = lookupStartTimestamp * NANOSECONDS_PER_MILLISECOND;
        long lookupEndTime = lookupEndTimestamp * NANOSECONDS_PER_MILLISECOND;
        // 读取高精度查询周期，单位为（毫秒 + 毫秒内纳秒偏移）。
        long lookupDataInterval = config.getLookupDataInterval();
        int lookupDataIntervalNanoOffset = config.getLookupDataIntervalNanoOffset();
        long lookupDataIntervalWithNanoOffset =
                lookupDataInterval * NANOSECONDS_PER_MILLISECOND + lookupDataIntervalNanoOffset;
        if (lookupDataIntervalWithNanoOffset <= 0) {
            throw new IllegalArgumentException("lookupDataIntervalWithNanoOffset 必须大于 0");
        }

        // 计算数据的起始时间，对齐到 lookupDataIntervalWithNanoOffset 的整数倍。
        long dataStartTime = Math.floorDiv(lookupStartTime, lookupDataIntervalWithNanoOffset) *
                lookupDataIntervalWithNanoOffset;
        if (dataStartTime < lookupStartTime || (dataStartTime == lookupStartTime && !includeStartDate)) {
            dataStartTime += lookupDataIntervalWithNanoOffset;
        }

        // 计算有效结束时间：不包含结束边界时，将结束时间向前移动 1 毫秒。
        long effectiveLookupEndTime = includeEndDate ?
                lookupEndTime : lookupEndTime - NANOSECONDS_PER_MILLISECOND;
        // 计算数据总量。
        int dataCount;
        if (effectiveLookupEndTime < dataStartTime) {
            dataCount = 0;
        } else {
            dataCount = (int) (Math.floorDiv(
                    effectiveLookupEndTime - dataStartTime, lookupDataIntervalWithNanoOffset
            ) + 1);
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
            long happenedTime = dataStartTime + (actualOffset + i) * lookupDataIntervalWithNanoOffset;
            long happenedTimestamp = Math.floorDiv(happenedTime, NANOSECONDS_PER_MILLISECOND);
            int happenedDateNanoOffset = (int) Math.floorMod(happenedTime, NANOSECONDS_PER_MILLISECOND);
            datas.add(generateData(pointKey, value, new Date(happenedTimestamp), happenedDateNanoOffset));
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
        getLogger().info("模拟查询数据(高精度), 耗时 {} 毫秒", endTimestamp - startTimestamp);
        getLogger().debug("查询结果(高精度): {}", lookupResult);

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

        // 根据预设值，调用不同的模拟本地查询方法。
        String preset = queryInfo.getPreset();
        if (NATIVE_QUERY_PRESET_DEFAULT.equals(preset)) {
            // 展开参数。
            String[] params = queryInfo.getParams();
            if (params.length < 2) {
                throw new IllegalArgumentException("参数数量不足: " + params.length);
            }
            long period = Long.parseLong(params[0]);
            long offset = Long.parseLong(params[1]);

            return mockNativeQuery(
                    queryInfo.getPointKeys(),
                    queryStartTimestamp, queryEndTimestamp,
                    queryInfo.isIncludeStartDate(), queryInfo.isIncludeEndDate(),
                    period, offset
            );
        } else if (NATIVE_QUERY_PRESET_HIGH_PRECISION.equals(preset)) {
            // 展开参数。
            String[] params = queryInfo.getParams();
            if (params.length < 4) {
                throw new IllegalArgumentException("参数数量不足: " + params.length);
            }
            long period = Long.parseLong(params[0]);
            int periodNanoOffset = Integer.parseInt(params[1]);
            long offset = Long.parseLong(params[2]);
            int offsetNanoOffset = Integer.parseInt(params[3]);

            return mockNativeQueryWithNanoOffset(
                    queryInfo.getPointKeys(),
                    queryStartTimestamp, queryEndTimestamp,
                    queryInfo.isIncludeStartDate(), queryInfo.isIncludeEndDate(),
                    period, periodNanoOffset, offset, offsetNanoOffset
            );
        } else {
            throw new IllegalArgumentException("预设不合法: " + preset);
        }
    }

    @SuppressWarnings("DuplicatedCode")
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
                itemsList.get(i).add(new QueryResult.Item(pointKey, value, new Date(cursor), 0));
            }
            cursor += period;
        }
        List<QueryResult.Sequence> sequences = new ArrayList<>(pointKeys.size());
        for (int i = 0; i < pointKeys.size(); i++) {
            LongIdKey pointKey = pointKeys.get(i);
            List<QueryResult.Item> items = itemsList.get(i);
            Date startDate = new Date(queryStartTimestamp);
            Date endDate = new Date(queryEndTimestamp);
            sequences.add(new QueryResult.Sequence(pointKey, items, startDate, 0, endDate, 0));
        }

        // 模拟延迟，延迟时间为(查询的时间范围 / 1000 + 1) * nativeQueryDelayPerSecond。
        // 如果时间区间小于等于 0，则不延迟。
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

    @SuppressWarnings("DuplicatedCode")
    private QueryResult mockNativeQueryWithNanoOffset(
            List<LongIdKey> pointKeys, long queryStartTimestamp, long queryEndTimestamp, boolean includeStartDate,
            boolean includeEndDate, long period, int periodNanoOffset, long offset, int offsetNanoOffset
    ) throws Exception {
        // 获取当前时间戳，用于模拟延迟。
        long anchorTimestamp = System.currentTimeMillis();

        // 获取配置。
        long nativeQueryBeforeDelay = config.getNativeQueryBeforeDelay();
        long nativeQueryDelayPerSecond = config.getNativeQueryDelayPerSecond();
        long nativeQueryAfterDelay = config.getNativeQueryAfterDelay();

        // 根据查询区间的开闭情况调整查询区间，并映射到纳秒时间轴。
        long queryStartTime = queryStartTimestamp * NANOSECONDS_PER_MILLISECOND;
        long queryEndTime = queryEndTimestamp * NANOSECONDS_PER_MILLISECOND;
        long actualQueryStartTime = includeStartDate ? queryStartTime : queryStartTime + 1;
        long actualQueryEndTime = includeEndDate ? queryEndTime : queryEndTime - 1;

        if (nativeQueryBeforeDelay > 0) {
            anchorTimestamp += nativeQueryBeforeDelay;
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        // 组合高精度周期与偏移量。
        long periodWithNanoOffset = period * NANOSECONDS_PER_MILLISECOND + periodNanoOffset;
        if (periodWithNanoOffset <= 0) {
            throw new IllegalArgumentException("参数 period 与 periodNanoOffset 组合后必须大于 0");
        }
        long offsetWithNanoOffset = offset * NANOSECONDS_PER_MILLISECOND + offsetNanoOffset;
        // 计算第一个点的数据起始时间。
        // 数据的第一个起始时间应该大于等于 actualQueryStartTime，
        // 且减去 offsetWithNanoOffset 后应该是 periodWithNanoOffset 的整数倍。
        long cursor = actualQueryStartTime + Math.floorMod(
                offsetWithNanoOffset - actualQueryStartTime, periodWithNanoOffset
        );
        // 在 cursor 小于等于 actualQueryEndTime 之前，生成数据，随后 cursor 自增 periodWithNanoOffset。
        List<List<QueryResult.Item>> itemsList = new ArrayList<>(pointKeys.size());
        for (int i = 0; i < pointKeys.size(); i++) {
            itemsList.add(new ArrayList<>());
        }
        while (cursor <= actualQueryEndTime) {
            Date happenedDate = new Date(cursor / NANOSECONDS_PER_MILLISECOND);
            int happenedDateNanoOffset = (int) (cursor % NANOSECONDS_PER_MILLISECOND);
            for (int i = 0; i < pointKeys.size(); i++) {
                LongIdKey pointKey = pointKeys.get(i);
                Object value = dataValueGenerator.nextValue(pointKey);
                itemsList.get(i).add(new QueryResult.Item(pointKey, value, happenedDate, happenedDateNanoOffset));
            }
            cursor += periodWithNanoOffset;
        }
        // 将条目组装为查询序列。
        List<QueryResult.Sequence> sequences = new ArrayList<>(pointKeys.size());
        for (int i = 0; i < pointKeys.size(); i++) {
            LongIdKey pointKey = pointKeys.get(i);
            List<QueryResult.Item> items = itemsList.get(i);
            Date startDate = new Date(queryStartTimestamp);
            Date endDate = new Date(queryEndTimestamp);
            sequences.add(new QueryResult.Sequence(pointKey, items, startDate, 0, endDate, 0));
        }

        // 模拟延迟，延迟时间为(查询的时间范围 / 1000 + 1) * nativeQueryDelayPerSecond。
        // 如果时间区间小于等于 0，则不延迟。
        if (nativeQueryDelayPerSecond > 0) {
            long timeRange = actualQueryEndTime - actualQueryStartTime;
            long delay = timeRange <= 0 ?
                    0 : (timeRange / NANOSECONDS_PER_MILLISECOND / 1000 + 1) * nativeQueryDelayPerSecond;
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

    protected abstract D generateData(LongIdKey pointKey, Object value, Date date, int dateNanoOffset);

    @Override
    public String toString() {
        return "MockBridgePersister{" +
                "config=" + config +
                ", dataValueGenerator=" + dataValueGenerator +
                '}';
    }
}
