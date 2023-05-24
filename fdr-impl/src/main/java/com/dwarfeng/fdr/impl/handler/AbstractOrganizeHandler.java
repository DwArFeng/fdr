package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.bean.dto.LookupInfo;
import com.dwarfeng.fdr.stack.bean.dto.LookupInfo.MapInfo;
import com.dwarfeng.fdr.stack.bean.dto.LookupResult;
import com.dwarfeng.fdr.stack.bean.dto.QueryInfo;
import com.dwarfeng.fdr.stack.bean.dto.QueryResult;
import com.dwarfeng.fdr.stack.exception.UnsupportedMapperTypeException;
import com.dwarfeng.fdr.stack.handler.MapLocalCacheHandler;
import com.dwarfeng.fdr.stack.handler.Mapper;
import com.dwarfeng.fdr.stack.handler.OrganizeHandler;
import com.dwarfeng.fdr.stack.handler.PersistHandler;
import com.dwarfeng.fdr.stack.structure.Data;
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
public abstract class AbstractOrganizeHandler implements OrganizeHandler {

    protected final PersistHandler<? extends Data> persistHandler;
    protected final MapLocalCacheHandler mapLocalCacheHandler;

    public AbstractOrganizeHandler(
            PersistHandler<? extends Data> persistHandler,
            MapLocalCacheHandler mapLocalCacheHandler
    ) {
        this.persistHandler = persistHandler;
        this.mapLocalCacheHandler = mapLocalCacheHandler;
    }

    @Override
    public LookupResult lookup(LookupInfo lookupInfo) throws HandlerException {
        try {
            // 获取查询配置。
            LookupConfig config = getOrganizeConfig();

            // 遍历查询信息，进行查询，构造 List<Mapper.Sequence>。
            List<Mapper.Sequence> sequences = new ArrayList<>();
            for (LookupInfo.QueryInfo queryInfo : lookupInfo.getQueryInfos()) {
                sequences.add(querySequence(queryInfo, config));
            }

            // 遍历映射信息，进行映射。
            for (MapInfo mapInfo : lookupInfo.getMapInfos()) {
                sequences = mapSequence(mapInfo, sequences);
            }

            // 根据 List<Mapper.Sequence> 构造 LookupResult。
            return buildLookupResult(sequences);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    private Mapper.Sequence querySequence(LookupInfo.QueryInfo queryItem, LookupConfig config) throws Exception {
        // 定义返回值。
        List<Mapper.Item> items = new ArrayList<>();

        // 展开查询配置。
        long maxPeriodSpan = config.getMaxPeriodSpan();
        int maxPageSize = config.getMaxPageSize();
        // 展开查询信息。
        String preset = queryItem.getPreset();
        String[] params = queryItem.getParams();
        LongIdKey pointKey = queryItem.getPointKey();
        Date startDate = queryItem.getStartDate();
        Date endDate = queryItem.getEndDate();
        boolean includeStartDate = queryItem.isIncludeStartDate();
        boolean includeEndDate = queryItem.isIncludeEndDate();

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
            int anchorOffset = 0;
            QueryResult<? extends Data> anchorQueryResult;
            do {
                // 构造查询信息，进行查询。
                QueryInfo queryInfo = new QueryInfo(
                        preset, params, pointKey, anchorStartDate, anchorEndDate, anchorIncludeStartDate,
                        anchorIncludeEndDate, maxPageSize, anchorOffset
                );
                anchorQueryResult = persistHandler.query(queryInfo);

                // 将查询结果转换为 Mapper.Item，添加到返回值中。
                for (Data data : anchorQueryResult.getDatas()) {
                    items.add(new Mapper.Item(data.getPointKey(), data.getValue(), data.getHappenedDate()));
                }

                // 如果 anchorQueryResult.isHasMore() 为 true，则更新查询数据。
                if (anchorQueryResult.isHasMore()) {
                    anchorOffset += anchorQueryResult.getDatas().size();
                }
            } while (anchorQueryResult.isHasMore());

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

    private List<Mapper.Sequence> mapSequence(LookupInfo.MapInfo mapInfo, List<Mapper.Sequence> sequences)
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

    private LookupResult buildLookupResult(List<Mapper.Sequence> sequences) {
        List<LookupResult.Sequence> resultSequences = new ArrayList<>();
        for (Mapper.Sequence sequence : sequences) {
            List<LookupResult.Item> items = new ArrayList<>();
            for (Mapper.Item item : sequence.getItems()) {
                items.add(new LookupResult.Item(item.getPointKey(), item.getValue(), item.getHappenedDate()));
            }
            resultSequences.add(new LookupResult.Sequence(
                    sequence.getPointKey(), items, sequence.getStartDate(), sequence.getEndDate()
            ));
        }
        return new LookupResult(resultSequences);
    }

    protected abstract LookupConfig getOrganizeConfig();

    @Override
    public String toString() {
        return "AbstractOrganizeHandler{" +
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
