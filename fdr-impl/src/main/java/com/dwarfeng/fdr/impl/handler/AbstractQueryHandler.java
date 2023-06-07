package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.sdk.util.ViewUtil;
import com.dwarfeng.fdr.stack.bean.dto.LookupInfo;
import com.dwarfeng.fdr.stack.bean.dto.LookupResult;
import com.dwarfeng.fdr.stack.bean.dto.QueryInfo;
import com.dwarfeng.fdr.stack.bean.dto.QueryInfo.QueryLookupInfo;
import com.dwarfeng.fdr.stack.bean.dto.QueryResult;
import com.dwarfeng.fdr.stack.exception.QueryNotSupportedException;
import com.dwarfeng.fdr.stack.exception.UnsupportedMapperTypeException;
import com.dwarfeng.fdr.stack.handler.MapLocalCacheHandler;
import com.dwarfeng.fdr.stack.handler.Mapper;
import com.dwarfeng.fdr.stack.handler.PersistHandler;
import com.dwarfeng.fdr.stack.handler.QueryHandler;
import com.dwarfeng.fdr.stack.struct.Data;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 组织处理器的抽象实现。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public abstract class AbstractQueryHandler implements QueryHandler {

    protected final PersistHandler<? extends Data> persistHandler;
    protected final MapLocalCacheHandler mapLocalCacheHandler;

    public AbstractQueryHandler(
            PersistHandler<? extends Data> persistHandler,
            MapLocalCacheHandler mapLocalCacheHandler
    ) {
        this.persistHandler = persistHandler;
        this.mapLocalCacheHandler = mapLocalCacheHandler;
    }

    @Override
    public QueryResult query(QueryInfo queryInfo) throws HandlerException {
        try {
            if (persistHandler.writeOnly()) {
                throw new QueryNotSupportedException();
            }
            return querySingle(queryInfo);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public List<QueryResult> query(List<QueryInfo> queryInfos) throws HandlerException {
        try {
            if (persistHandler.writeOnly()) {
                throw new QueryNotSupportedException();
            }
            List<QueryResult> resultList = new ArrayList<>();
            for (QueryInfo queryInfo : queryInfos) {
                resultList.add(querySingle(queryInfo));
            }
            return resultList;
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    private QueryResult querySingle(QueryInfo queryInfo) throws Exception {
        // 获取查询配置。
        LookupConfig config = getLookupConfig();

        // 遍历查询信息，进行查询，构造 List<Mapper.Sequence>。
        List<Mapper.Sequence> sequences = new ArrayList<>();
        for (QueryLookupInfo queryLookupInfo : queryInfo.getQueryInfos()) {
            sequences.add(querySequence(queryLookupInfo, config));
        }

        // 遍历映射信息，进行映射。
        for (QueryInfo.QueryMapInfo queryMapInfo : queryInfo.getMapInfos()) {
            sequences = mapSequence(queryMapInfo, sequences);
        }

        // 根据 List<Mapper.Sequence> 构造 QueryResult。
        return buildLookupResult(sequences);
    }

    @SuppressWarnings("DuplicatedCode")
    private Mapper.Sequence querySequence(QueryLookupInfo queryLookupInfo, LookupConfig config) throws Exception {
        // 定义返回值。
        List<Mapper.Item> items = new ArrayList<>();

        // 展开查询配置。
        long maxPeriodSpan = config.getMaxPeriodSpan();
        int maxPageSize = config.getMaxPageSize();
        // 展开查询信息。
        String preset = queryLookupInfo.getPreset();
        String[] params = queryLookupInfo.getParams();
        LongIdKey pointKey = queryLookupInfo.getPointKey();
        Date startDate = ViewUtil.validStartDate(queryLookupInfo.getStartDate());
        Date endDate = ViewUtil.validEndDate(queryLookupInfo.getEndDate());
        boolean includeStartDate = queryLookupInfo.isIncludeStartDate();
        boolean includeEndDate = queryLookupInfo.isIncludeEndDate();

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
                anchorLookupResult = persistHandler.lookup(lookupInfo);

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

        // 返回结果。
        return new Mapper.Sequence(pointKey, items, startDate, endDate);
    }

    private List<Mapper.Sequence> mapSequence(QueryInfo.QueryMapInfo queryMapInfo, List<Mapper.Sequence> sequences)
            throws Exception {
        // 展开映射信息。
        String type = queryMapInfo.getType();
        String param = queryMapInfo.getParam();

        // 根据 queryMapInfo 找到对应的 Mapper。
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
                "persistHandler=" + persistHandler +
                ", mapLocalCacheHandler=" + mapLocalCacheHandler +
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
