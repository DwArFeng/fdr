package com.dwarfeng.fdr.impl.handler.bridge.mock;

import com.dwarfeng.fdr.impl.handler.bridge.AbstractPersister;
import com.dwarfeng.fdr.sdk.util.WatchUtil;
import com.dwarfeng.fdr.stack.bean.dto.QueryInfo;
import com.dwarfeng.fdr.stack.bean.dto.QueryResult;
import com.dwarfeng.fdr.stack.handler.PersistHandler;
import com.dwarfeng.fdr.stack.structure.Data;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 模拟的持久化器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public abstract class MockBridgePersister<D extends Data> extends AbstractPersister<D> {

    public static final String DEFAULT = "default";

    private static final List<PersistHandler.QueryGuide> QUERY_MANUALS;

    static {
        QUERY_MANUALS = new ArrayList<>();
        QUERY_MANUALS.add(new PersistHandler.QueryGuide(
                DEFAULT, new String[0], "默认的查询方法"
        ));
    }

    protected final MockBridgeConfig config;
    protected final MockBridgeDataValueGenerator dataValueGenerator;

    public MockBridgePersister(MockBridgeConfig config, MockBridgeDataValueGenerator dataValueGenerator) {
        super(false, QUERY_MANUALS);
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
    protected QueryResult<D> doQuery(QueryInfo queryInfo) throws Exception {
        // 展开查询信息。
        long queryStartTimestamp = WatchUtil.validStartDate(queryInfo.getStartDate()).getTime();
        long queryEndTimestamp = WatchUtil.validEndDate(queryInfo.getEndDate()).getTime();
        int page = WatchUtil.validPage(queryInfo.getPage());
        int rows = WatchUtil.validRows(queryInfo.getRows());

        // 检查预设是否合法。
        String preset = queryInfo.getPreset();
        if (!DEFAULT.equals(preset)) {
            throw new IllegalArgumentException("预设不合法");
        }

        return mockQuery(
                queryInfo.getPointKey(),
                queryStartTimestamp, queryEndTimestamp,
                queryInfo.isIncludeStartDate(), queryInfo.isIncludeEndDate(),
                page, rows
        );
    }

    private QueryResult<D> mockQuery(
            LongIdKey pointKey, long queryStartTimestamp, long queryEndTimestamp,
            boolean includeStartDate, boolean includeEndDate, int page, int rows
    ) throws Exception {
        long startTimestamp = System.currentTimeMillis();
        long anchorTimestamp = System.currentTimeMillis();

        long queryBeforeDelay = config.getQueryBeforeDelay();
        long queryOffsetDelay = config.getQueryOffsetDelay();
        long queryDelay = config.getQueryDelay();
        long queryAfterDelay = config.getQueryAfterDelay();

        if (queryBeforeDelay > 0) {
            anchorTimestamp += queryBeforeDelay;
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        long queryDataInterval = config.getQueryDataInterval();
        // 计算数据的起始时间，对齐到 queryDataInterval 的整数倍。
        long dataStartTimestamp = queryStartTimestamp - queryStartTimestamp % queryDataInterval;
        if (dataStartTimestamp == queryStartTimestamp && !includeStartDate) {
            dataStartTimestamp += queryDataInterval;
        }
        // 计算数据的数量。
        int dataCount = (int) ((queryEndTimestamp - dataStartTimestamp) / queryDataInterval);
        if (dataStartTimestamp + dataCount * queryDataInterval == queryEndTimestamp && !includeEndDate) {
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
                    new Date(dataStartTimestamp + (actualOffset + i) * queryDataInterval)
            ));
        }
        QueryResult<D> queryResult = new QueryResult<>(pointKey, datas, hasMore);

        if (queryOffsetDelay > 0) {
            anchorTimestamp += queryOffsetDelay * actualOffset;
            ThreadUtil.sleepUntil(anchorTimestamp);
        }
        if (queryDelay > 0) {
            anchorTimestamp += queryDelay * actualLimit;
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        if (queryAfterDelay > 0) {
            anchorTimestamp += queryAfterDelay;
            ThreadUtil.sleepUntil(anchorTimestamp);
        }

        long endTimestamp = System.currentTimeMillis();
        getLogger().info("模拟查询数据, 耗时 {} 毫秒", endTimestamp - startTimestamp);
        getLogger().debug("查询结果: {}", queryResult);

        return queryResult;
    }

    protected abstract Logger getLogger();

    protected abstract D generateData(LongIdKey pointKey, Object value, Date date);

    @Override
    public String toString() {
        return "MockBridgePersister{" +
                "config=" + config +
                ", dataValueGenerator=" + dataValueGenerator +
                ", writeOnly=" + writeOnly +
                ", queryGuides=" + queryGuides +
                '}';
    }
}
