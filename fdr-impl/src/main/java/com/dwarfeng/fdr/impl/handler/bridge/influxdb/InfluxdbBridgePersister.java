package com.dwarfeng.fdr.impl.handler.bridge.influxdb;

import com.dwarfeng.fdr.impl.handler.bridge.FullPersister;
import com.dwarfeng.fdr.impl.handler.bridge.influxdb.bean.dto.*;
import com.dwarfeng.fdr.impl.handler.bridge.influxdb.handler.InfluxdbBridgeDataHandler;
import com.dwarfeng.fdr.impl.handler.bridge.influxdb.util.DateUtil;
import com.dwarfeng.fdr.sdk.util.ViewUtil;
import com.dwarfeng.fdr.stack.bean.dto.LookupInfo;
import com.dwarfeng.fdr.stack.bean.dto.LookupResult;
import com.dwarfeng.fdr.stack.bean.dto.NativeQueryInfo;
import com.dwarfeng.fdr.stack.bean.dto.QueryResult;
import com.dwarfeng.fdr.stack.struct.Data;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.influxdb.client.write.Point;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;

/**
 * Influxdb 桥接器一般数据持久器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public abstract class InfluxdbBridgePersister<D extends Data> extends FullPersister<D> {

    public static final String LOOKUP_PRESET_DEFAULT = "default";
    public static final String NATIVE_QUERY_PRESET_DEFAULT = "default";
    public static final String NATIVE_QUERY_PRESET_AGGREGATE_WINDOW = "aggregate_window";
    public static final String NATIVE_QUERY_PRESET_CUSTOM = "custom";

    protected final InfluxdbBridgeDataHandler handler;

    protected final ThreadPoolTaskExecutor executor;

    protected InfluxdbBridgePersister(
            InfluxdbBridgeDataHandler handler,
            ThreadPoolTaskExecutor executor
    ) {
        this.handler = handler;
        this.executor = executor;
    }

    @Override
    protected void doRecord(D data) throws Exception {
        Point point = dataToPoint(data);
        handler.write(point);
    }

    @Override
    protected void doRecord(List<D> datas) throws Exception {
        List<Point> points = datas.stream().map(this::dataToPoint).collect(Collectors.toList());
        handler.write(points);
    }

    protected abstract Point dataToPoint(D data);

    @Override
    protected LookupResult<D> doLookup(LookupInfo lookupInfo) throws Exception {
        return lookupSingle(lookupInfo);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected List<LookupResult<D>> doLookup(List<LookupInfo> lookupInfos) throws Exception {
        // 构造查看结果，并初始化。
        List<LookupResult<D>> lookupResults = new ArrayList<>(lookupInfos.size());
        for (int i = 0; i < lookupInfos.size(); i++) {
            lookupResults.add(null);
        }

        // 遍历 lookupInfos，异步查看。
        List<CompletableFuture<?>> futures = new ArrayList<>(lookupInfos.size());
        for (int i = 0; i < lookupInfos.size(); i++) {
            final int index = i;
            final LookupInfo lookupInfo = lookupInfos.get(index);
            CompletableFuture<Void> future = CompletableFuture.runAsync(
                    () -> {
                        LookupResult<D> lookupResult = wrappedLookupSingle(lookupInfo);
                        lookupResults.set(index, lookupResult);
                    },
                    executor
            );
            futures.add(future);
        }
        try {
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        } catch (CompletionException e) {
            throw (Exception) e.getCause();
        }

        // 返回查看结果。
        return lookupResults;
    }

    private LookupResult<D> wrappedLookupSingle(LookupInfo lookupInfo) throws CompletionException {
        try {
            return lookupSingle(lookupInfo);
        } catch (Exception e) {
            throw new CompletionException(e);
        }
    }

    private LookupResult<D> lookupSingle(LookupInfo lookupInfo) throws HandlerException {
        // 展开参数。
        String preset = lookupInfo.getPreset();
        String[] params = lookupInfo.getParams();
        LongIdKey pointKey = lookupInfo.getPointKey();
        Date startDate = ViewUtil.validStartDate(lookupInfo.getStartDate());
        Date endDate = ViewUtil.validEndDate(lookupInfo.getEndDate());
        boolean includeStartDate = lookupInfo.isIncludeStartDate();
        boolean includeEndDate = lookupInfo.isIncludeEndDate();
        int page = ViewUtil.validPage(lookupInfo.getPage());
        int rows = ViewUtil.validRows(lookupInfo.getRows());

        // 构造查看信息。
        String measurement = Long.toString(pointKey.getLongId());
        long startOffset = includeStartDate ? 0 : 1;
        long stopOffset = includeEndDate ? 1 : 0;
        Date rangeStart = DateUtil.offsetDate(startDate, startOffset);
        Date rangeStop = DateUtil.offsetDate(endDate, stopOffset);
        int limitNumber = rows + 1;
        int limitOffset = page * rows;
        HibernateBridgeLookupInfo queryInfo = new HibernateBridgeLookupInfo(
                measurement, rangeStart, rangeStop, limitNumber, limitOffset, params
        );

        // 根据 preset 分类处理。
        HibernateBridgeLookupResult queryResult;
        if (preset.equals(LOOKUP_PRESET_DEFAULT)) {
            queryResult = handler.lookup(queryInfo);
        } else {
            throw new IllegalArgumentException("非法的预设: " + preset);
        }

        // 展开参数。
        List<HibernateBridgeLookupResult.Item> items = queryResult.getItems();

        // 构造查看结果，并返回。
        boolean hasMore = items.size() > rows;
        List<D> datas = new ArrayList<>();
        for (int i = 0; i < rows && i < items.size(); i++) {
            datas.add(itemToData(items.get(i)));
        }
        return new LookupResult<>(pointKey, datas, hasMore);
    }

    protected abstract D itemToData(HibernateBridgeLookupResult.Item item);

    @Override
    protected QueryResult doNativeQuery(NativeQueryInfo queryInfo) throws Exception {
        return nativeQuerySingle(queryInfo);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected List<QueryResult> doNativeQuery(List<NativeQueryInfo> queryInfos) throws Exception {
        // 构造查看结果，并初始化。
        List<QueryResult> queryResults = new ArrayList<>(queryInfos.size());
        for (int i = 0; i < queryInfos.size(); i++) {
            queryResults.add(null);
        }

        // 遍历 queryInfos，异步查看。
        List<CompletableFuture<?>> futures = new ArrayList<>(queryInfos.size());
        for (int i = 0; i < queryInfos.size(); i++) {
            final int index = i;
            final NativeQueryInfo queryInfo = queryInfos.get(index);
            CompletableFuture<Void> future = CompletableFuture.runAsync(
                    () -> {
                        QueryResult queryResult = wrappedNativeQuerySingle(queryInfo);
                        queryResults.set(index, queryResult);
                    },
                    executor
            );
            futures.add(future);
        }
        try {
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        } catch (CompletionException e) {
            throw (Exception) e.getCause();
        }

        // 返回查看结果。
        return queryResults;
    }

    private QueryResult wrappedNativeQuerySingle(NativeQueryInfo queryInfo) throws CompletionException {
        try {
            return nativeQuerySingle(queryInfo);
        } catch (Exception e) {
            throw new CompletionException(e);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private QueryResult nativeQuerySingle(NativeQueryInfo queryInfo) throws HandlerException {
        // 展开参数。
        String preset = queryInfo.getPreset();
        String[] params = queryInfo.getParams();
        List<LongIdKey> pointKeys = queryInfo.getPointKeys();
        Date startDate = ViewUtil.validStartDate(queryInfo.getStartDate());
        Date endDate = ViewUtil.validEndDate(queryInfo.getEndDate());
        boolean includeStartDate = queryInfo.isIncludeStartDate();
        boolean includeEndDate = queryInfo.isIncludeEndDate();

        // 构造查看信息。
        List<String> measurements = new ArrayList<>(pointKeys.size());
        for (LongIdKey pointKey : pointKeys) {
            measurements.add(Long.toString(pointKey.getLongId()));
        }
        long startOffset = includeStartDate ? 0 : 1;
        long stopOffset = includeEndDate ? 1 : 0;
        Date rangeStart = DateUtil.offsetDate(startDate, startOffset);
        Date rangeStop = DateUtil.offsetDate(endDate, stopOffset);

        // 根据 preset 分类处理。
        HibernateBridgeQueryResult queryResult;
        switch (preset) {
            case NATIVE_QUERY_PRESET_DEFAULT:
            case NATIVE_QUERY_PRESET_AGGREGATE_WINDOW:
                queryResult = handler.defaultQuery(
                        resolveDefaultQueryInfo(measurements, rangeStart, rangeStop, params)
                );
                break;
            case NATIVE_QUERY_PRESET_CUSTOM:
                queryResult = handler.customQuery(
                        resolveCustomQueryInfo(measurements, rangeStart, rangeStop, params)
                );
                break;
            default:
                throw new IllegalArgumentException("非法的预设: " + preset);
        }

        // 构造查看结果，并返回。
        List<HibernateBridgeQueryResult.HibernateBridgeSequence> hibernateBridgeSequences = queryResult.getSequences();
        List<QueryResult.Sequence> sequences = new ArrayList<>(hibernateBridgeSequences.size());
        for (HibernateBridgeQueryResult.HibernateBridgeSequence hibernateBridgeSequence : hibernateBridgeSequences) {
            LongIdKey pointKey = new LongIdKey(Long.parseLong(hibernateBridgeSequence.getMeasurement()));
            startDate = DateUtil.instant2Date(hibernateBridgeSequence.getStartInstant());
            endDate = DateUtil.instant2Date(hibernateBridgeSequence.getEndInstant());

            List<HibernateBridgeQueryResult.HibernateBridgeItem> hibernateBridgeItems
                    = hibernateBridgeSequence.getItems();
            List<QueryResult.Item> items = new ArrayList<>(hibernateBridgeItems.size());
            for (HibernateBridgeQueryResult.HibernateBridgeItem hibernateBridgeItem : hibernateBridgeItems) {
                Date happenedDate = DateUtil.instant2Date(hibernateBridgeItem.getHappenedInstant());
                items.add(new QueryResult.Item(pointKey, hibernateBridgeItem.getValue(), happenedDate));
            }

            sequences.add(new QueryResult.Sequence(pointKey, items, startDate, endDate));
        }
        return new QueryResult(sequences);
    }

    private HibernateBridgeDefaultQueryInfo resolveDefaultQueryInfo(
            List<String> measurements, Date rangeStart, Date rangeStop, String[] params
    ) {
        // params 的第 0 个元素是 aggregateWindowEvery。
        long aggregateWindowEvery = Long.parseLong(params[0]);
        // params 的第 1 个元素是 aggregateWindowOffset。
        long aggregateWindowOffset = Long.parseLong(params[1]);
        // params 的第 2 个元素是 aggregateWindowFn。
        String aggregateWindowFn = params[2];

        // 返回结果。
        return new HibernateBridgeDefaultQueryInfo(
                measurements, rangeStart, rangeStop, aggregateWindowEvery, aggregateWindowOffset, aggregateWindowFn
        );
    }

    private HibernateBridgeCustomQueryInfo resolveCustomQueryInfo(
            List<String> measurements, Date rangeStart, Date rangeStop, String[] params
    ) {
        // params 的第 0 个元素是 fluxFragment。
        String fluxFragment = params[0];

        // 返回结果。
        return new HibernateBridgeCustomQueryInfo(measurements, rangeStart, rangeStop, fluxFragment);
    }

    @Override
    public String toString() {
        return "InfluxdbBridgePersister{" +
                "handler=" + handler +
                ", executor=" + executor +
                '}';
    }
}
