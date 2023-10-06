package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.sdk.util.ViewUtil;
import com.dwarfeng.fdr.stack.bean.dto.LookupInfo;
import com.dwarfeng.fdr.stack.bean.dto.LookupResult;
import com.dwarfeng.fdr.stack.bean.dto.QueryInfo;
import com.dwarfeng.fdr.stack.bean.dto.QueryResult;
import com.dwarfeng.fdr.stack.exception.QueryException;
import com.dwarfeng.fdr.stack.exception.UnsupportedMapperTypeException;
import com.dwarfeng.fdr.stack.handler.MapLocalCacheHandler;
import com.dwarfeng.fdr.stack.handler.Mapper;
import com.dwarfeng.fdr.stack.handler.QueryHandler;
import com.dwarfeng.fdr.stack.struct.Data;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

/**
 * 组织处理器的抽象实现。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public abstract class AbstractQueryHandler implements QueryHandler {

    protected final MapLocalCacheHandler mapLocalCacheHandler;

    protected final ThreadPoolTaskExecutor executor;

    protected final List<Bridge> bridges;

    protected Bridge.Persister<? extends Data> persister;

    public AbstractQueryHandler(
            MapLocalCacheHandler mapLocalCacheHandler,
            ThreadPoolTaskExecutor executor,
            List<Bridge> bridges
    ) {
        this.mapLocalCacheHandler = mapLocalCacheHandler;
        this.executor = executor;
        this.bridges = bridges;
    }

    /**
     * 初始化持久器。
     *
     * <p>
     * 该方法会从持久器列表中找到对应类型的桥接。
     *
     * <p>
     * 需要在子类构造完毕后调用该方法。
     *
     * @param bridgeType 指定的桥接类型。
     * @throws Exception 初始化过程中发生的任何异常。
     */
    protected void init(String bridgeType) throws Exception {
        // 从持久器列表中找到对应类型的持久器。
        Bridge bridge = bridges.stream().filter(b -> b.supportType(bridgeType)).findAny()
                .orElseThrow(() -> new HandlerException("未知的 bridge 类型: " + bridgeType));

        // 如果桥接器支持持久器，则获取持久器。
        persister = getPersisterFromBridge(bridge);
    }

    /**
     * 在指定的桥接器中获取持久器。
     *
     * @param bridge 指定的桥接器。
     * @return 指定的桥接器中的持久器。
     * @throws Exception 任何可能的异常。
     */
    protected abstract Bridge.Persister<? extends Data> getPersisterFromBridge(Bridge bridge) throws Exception;

    @Override
    public QueryResult query(QueryInfo queryInfo) throws HandlerException {
        try {
            return querySingle(queryInfo);
        } catch (QueryException e) {
            throw e;
        } catch (Exception e) {
            throw new QueryException(e, Collections.singletonList(queryInfo));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public List<QueryResult> query(List<QueryInfo> queryInfos) throws HandlerException {
        try {
            // 构造查询结果，并初始化。
            List<QueryResult> queryResults = new ArrayList<>(queryInfos.size());
            for (int i = 0; i < queryInfos.size(); i++) {
                queryResults.add(null);
            }

            // 遍历 queryInfos，异步查询。
            List<CompletableFuture<?>> futures = new ArrayList<>(queryInfos.size());
            for (int i = 0; i < queryInfos.size(); i++) {
                final int index = i;
                final QueryInfo queryInfo = queryInfos.get(index);
                CompletableFuture<Void> future = CompletableFuture.runAsync(
                        () -> {
                            QueryResult queryResult = wrappedQuerySingle(queryInfo);
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

            // 返回结果。
            return queryResults;
        } catch (QueryException e) {
            throw e;
        } catch (Exception e) {
            throw new QueryException(e, queryInfos);
        }
    }

    private QueryResult wrappedQuerySingle(QueryInfo queryInfo) throws CompletionException {
        try {
            return querySingle(queryInfo);
        } catch (Exception e) {
            throw new CompletionException(e);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private QueryResult querySingle(QueryInfo queryInfo) throws Exception {
        // 获取查询配置。
        LookupConfig config = getLookupConfig();

        // 展开查询配置。
        long maxPeriodSpan = config.getMaxPeriodSpan();
        int maxPageSize = config.getMaxPageSize();
        // 展开查询信息。
        String preset = queryInfo.getPreset();
        String[] params = queryInfo.getParams();
        List<LongIdKey> pointKeys = queryInfo.getPointKeys();
        Date startDate = ViewUtil.validStartDate(queryInfo.getStartDate());
        Date endDate = ViewUtil.validEndDate(queryInfo.getEndDate());
        boolean includeStartDate = queryInfo.isIncludeStartDate();
        boolean includeEndDate = queryInfo.isIncludeEndDate();
        List<QueryInfo.MapInfo> mapInfos = queryInfo.getMapInfos();

        // 进行查询，构造 List<Mapper.Sequence>。
        List<Mapper.Sequence> sequences = lookupSequences(
                maxPeriodSpan, maxPageSize, preset, params, pointKeys, startDate, endDate, includeStartDate,
                includeEndDate
        );

        // 进行映射。
        sequences = mapSequences(mapInfos, sequences);
        for (QueryInfo.MapInfo mapInfo : queryInfo.getMapInfos()) {
            sequences = mapSingleSequence(mapInfo, sequences);
        }

        // 根据 List<Mapper.Sequence> 构造 QueryResult。
        return buildLookupResult(sequences);
    }

    private List<Mapper.Sequence> lookupSequences(
            long maxPeriodSpan, int maxPageSize, String preset, String[] params, List<LongIdKey> pointKeys,
            Date startDate, Date endDate, boolean includeStartDate, boolean includeEndDate
    ) throws Exception {
        // 构造查询结果，并初始化。
        List<Mapper.Sequence> sequences = new ArrayList<>(pointKeys.size());
        for (int i = 0; i < pointKeys.size(); i++) {
            sequences.add(null);
        }

        // 遍历 pointKeys，异步查询。
        List<CompletableFuture<?>> futures = new ArrayList<>(pointKeys.size());
        for (int i = 0; i < pointKeys.size(); i++) {
            final int index = i;
            final LongIdKey pointKey = pointKeys.get(index);
            CompletableFuture<Void> future = CompletableFuture.runAsync(
                    () -> {
                        Mapper.Sequence sequence = wrappedLookupSingleSequence(
                                maxPeriodSpan, maxPageSize, preset, params, pointKey, startDate, endDate,
                                includeStartDate, includeEndDate
                        );
                        sequences.set(index, sequence);
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

        // 返回结果。
        return sequences;
    }

    private Mapper.Sequence wrappedLookupSingleSequence(
            long maxPeriodSpan, int maxPageSize, String preset, String[] params, LongIdKey pointKey,
            Date startDate, Date endDate, boolean includeStartDate, boolean includeEndDate
    ) throws CompletionException {
        try {
            return lookupSingleSequence(
                    maxPeriodSpan, maxPageSize, preset, params, pointKey, startDate, endDate, includeStartDate,
                    includeEndDate
            );
        } catch (Exception e) {
            throw new CompletionException(e);
        }
    }

    private Mapper.Sequence lookupSingleSequence(
            long maxPeriodSpan, int maxPageSize, String preset, String[] params, LongIdKey pointKey,
            Date startDate, Date endDate, boolean includeStartDate, boolean includeEndDate
    ) throws Exception {
        // 定义中间变量。
        List<Mapper.Item> items = new ArrayList<>();

        // 循环查询数据，每次查询的时间跨度最大为 maxPeriodSpan。
        Date anchorStartDate = startDate;
        boolean notLastPeriodFlag = anchorStartDate.getTime() + maxPeriodSpan < endDate.getTime();
        Date anchorEndDate = notLastPeriodFlag ? new Date(anchorStartDate.getTime() + maxPeriodSpan) : endDate;
        boolean anchorIncludeStartDate = includeStartDate;
        // 为了代码的可读性，此处不简化三目运算符。
        @SuppressWarnings("SimplifiableConditionalExpression")
        boolean anchorIncludeEndDate = notLastPeriodFlag ? false : includeEndDate;
        do {
            // 循环查询数据，每次查询的数据量最大为 maxPageSize。
            int anchorPage = 0;
            LookupResult<? extends Data> anchorLookupResult;
            do {
                // 构造查询信息，进行查询。
                LookupInfo lookupInfo = new LookupInfo(
                        preset, params, pointKey, anchorStartDate, anchorEndDate, anchorIncludeStartDate,
                        anchorIncludeEndDate, anchorPage, maxPageSize
                );
                anchorLookupResult = persister.lookup(lookupInfo);

                // 将查询结果转换为 Mapper.Item，添加到返回值中。
                for (Data data : anchorLookupResult.getDatas()) {
                    items.add(new Mapper.Item(data.getPointKey(), data.getValue(), data.getHappenedDate()));
                }

                // 如果 anchorLookupResult.isHasMore() 为 true，则更新查询数据。
                if (anchorLookupResult.isHasMore()) {
                    anchorPage++;
                }
            } while (anchorLookupResult.isHasMore());

            // 如果 notLastPeriodFlag 为 true，则更新查询数据。
            if (notLastPeriodFlag) {
                anchorStartDate = anchorEndDate;
                notLastPeriodFlag = anchorStartDate.getTime() + maxPeriodSpan < endDate.getTime();
                anchorEndDate = notLastPeriodFlag ? new Date(anchorStartDate.getTime() + maxPeriodSpan) : endDate;
                anchorIncludeStartDate = false;
                // 为了代码的可读性，此处不简化三目运算符。
                @SuppressWarnings("SimplifiableConditionalExpression")
                boolean anchorIncludeEndDateDejaVu = notLastPeriodFlag ? false : includeEndDate;
                anchorIncludeEndDate = anchorIncludeEndDateDejaVu;
            }
        } while (notLastPeriodFlag);

        // 将查询结果添加到返回值中。
        return new Mapper.Sequence(pointKey, items, startDate, endDate);
    }

    private List<Mapper.Sequence> mapSequences(List<QueryInfo.MapInfo> mapInfos, List<Mapper.Sequence> sequences)
            throws Exception {
        // 遍历 mapInfos，进行映射。
        for (QueryInfo.MapInfo mapInfo : mapInfos) {
            sequences = mapSingleSequence(mapInfo, sequences);
        }

        // 返回结果。
        return sequences;
    }

    private List<Mapper.Sequence> mapSingleSequence(QueryInfo.MapInfo mapInfo, List<Mapper.Sequence> sequences)
            throws Exception {
        // 展开映射信息。
        String type = mapInfo.getType();
        String param = mapInfo.getParam();

        // 根据 mapInfo 找到对应的 Mapper。
        Mapper mapper = mapLocalCacheHandler.get(type);
        // 如果找不到对应的 Mapper，则抛出异常。
        if (mapper == null) {
            throw new UnsupportedMapperTypeException(type);
        }

        // 调用 mapper 进行映射，返回结果。
        return mapper.map(new Mapper.MapParam(param), sequences);
    }

    private QueryResult buildLookupResult(List<Mapper.Sequence> sequences) {
        List<QueryResult.Sequence> resultSequences = new ArrayList<>();
        for (Mapper.Sequence sequence : sequences) {
            List<QueryResult.Item> items = new ArrayList<>();
            for (Mapper.Item item : sequence.getItems()) {
                items.add(new QueryResult.Item(item.getPointKey(), item.getValue(), item.getHappenedDate()));
            }
            resultSequences.add(new QueryResult.Sequence(
                    sequence.getPointKey(), items, sequence.getStartDate(), sequence.getEndDate()
            ));
        }
        return new QueryResult(resultSequences);
    }

    protected abstract LookupConfig getLookupConfig();

    @Override
    public String toString() {
        return "AbstractQueryHandler{" +
                "mapLocalCacheHandler=" + mapLocalCacheHandler +
                ", bridges=" + bridges +
                ", persister=" + persister +
                '}';
    }

    protected static final class LookupConfig {

        private final long maxPeriodSpan;
        private final int maxPageSize;

        public LookupConfig(long maxPeriodSpan, int maxPageSize) {
            this.maxPeriodSpan = maxPeriodSpan;
            this.maxPageSize = maxPageSize;
        }

        public long getMaxPeriodSpan() {
            return maxPeriodSpan;
        }

        public int getMaxPageSize() {
            return maxPageSize;
        }

        @Override
        public String toString() {
            return "LookupConfig{" +
                    "maxPeriodSpan=" + maxPeriodSpan +
                    ", maxPageSize=" + maxPageSize +
                    '}';
        }
    }
}
