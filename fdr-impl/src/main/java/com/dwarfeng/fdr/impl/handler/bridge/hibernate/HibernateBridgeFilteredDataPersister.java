package com.dwarfeng.fdr.impl.handler.bridge.hibernate;

import com.dwarfeng.dct.handler.ValueCodingHandler;
import com.dwarfeng.fdr.impl.handler.bridge.AbstractPersister;
import com.dwarfeng.fdr.impl.handler.bridge.hibernate.bean.HibernateBridgeFilteredData;
import com.dwarfeng.fdr.impl.handler.bridge.hibernate.service.HibernateBridgeFilteredDataMaintainService;
import com.dwarfeng.fdr.sdk.util.WatchUtil;
import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.fdr.stack.bean.dto.QueryInfo;
import com.dwarfeng.fdr.stack.bean.dto.QueryResult;
import com.dwarfeng.fdr.stack.handler.PersistHandler;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Hibernate 桥接被过滤数据持久化器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class HibernateBridgeFilteredDataPersister extends AbstractPersister<FilteredData> {

    public static final String DEFAULT = "default";

    private static final List<PersistHandler.QueryGuide> QUERY_MANUALS;

    static {
        QUERY_MANUALS = new ArrayList<>();
        QUERY_MANUALS.add(new PersistHandler.QueryGuide(
                DEFAULT, new String[0], "默认的查询方法"
        ));
    }

    private final HibernateBridgeFilteredDataMaintainService service;

    private final ValueCodingHandler valueCodingHandler;

    protected HibernateBridgeFilteredDataPersister(
            HibernateBridgeFilteredDataMaintainService service,
            @Qualifier("hibernateBridge.valueCodingHandler") ValueCodingHandler valueCodingHandler
    ) {
        super(false, QUERY_MANUALS);
        this.service = service;
        this.valueCodingHandler = valueCodingHandler;
    }

    @Override
    protected void doRecord(FilteredData data) throws Exception {
        HibernateBridgeFilteredData filteredData = transform(data);
        service.write(filteredData);
    }

    @Override
    protected void doRecord(List<FilteredData> datas) throws Exception {
        List<HibernateBridgeFilteredData> entities = new ArrayList<>();
        for (FilteredData data : datas) {
            HibernateBridgeFilteredData filteredData = transform(data);
            entities.add(filteredData);
        }
        service.batchWrite(entities);
    }

    private HibernateBridgeFilteredData transform(FilteredData data) throws Exception {
        String flatValue = valueCodingHandler.encode(data.getValue());
        return new HibernateBridgeFilteredData(
                null,
                data.getPointKey(),
                data.getFilterKey(),
                flatValue,
                data.getMessage(),
                data.getHappenedDate()
        );
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected QueryResult<FilteredData> doQuery(QueryInfo queryInfo) throws Exception {
        // 展开查询信息。
        LongIdKey pointKey = queryInfo.getPointKey();
        Date startDate = WatchUtil.validStartDate(queryInfo.getStartDate());
        Date endDate = WatchUtil.validEndDate(queryInfo.getEndDate());
        int page = WatchUtil.validPage(queryInfo.getPage());
        int rows = WatchUtil.validRows(queryInfo.getRows());
        boolean includeStartDate = queryInfo.isIncludeStartDate();
        boolean includeEndDate = queryInfo.isIncludeEndDate();

        // 检查预设是否合法。
        String preset = queryInfo.getPreset();
        if (!DEFAULT.equals(preset)) {
            throw new IllegalArgumentException("预设不合法");
        }

        // 查询数据。
        String servicePreset;
        if (includeStartDate && includeEndDate) {
            servicePreset = HibernateBridgeFilteredDataMaintainService.CHILD_FOR_POINT_BETWEEN_CLOSE_CLOSE;
        } else if (includeStartDate) {
            servicePreset = HibernateBridgeFilteredDataMaintainService.CHILD_FOR_POINT_BETWEEN_CLOSE_OPEN;
        } else if (includeEndDate) {
            servicePreset = HibernateBridgeFilteredDataMaintainService.CHILD_FOR_POINT_BETWEEN_OPEN_CLOSE;
        } else {
            servicePreset = HibernateBridgeFilteredDataMaintainService.CHILD_FOR_POINT_BETWEEN_OPEN_OPEN;
        }
        PagedData<HibernateBridgeFilteredData> lookup = service.lookup(
                servicePreset, new Object[]{pointKey, startDate, endDate}, new PagingInfo(page, rows)
        );

        // 处理数据。
        List<HibernateBridgeFilteredData> hibernateBridgeDatas = lookup.getData();
        List<FilteredData> datas = new ArrayList<>(hibernateBridgeDatas.size());
        for (HibernateBridgeFilteredData hibernateBridgeData : hibernateBridgeDatas) {
            FilteredData data = reverseTransform(hibernateBridgeData);
            datas.add(data);
        }
        boolean hasMore = lookup.getTotalPages() > page + 1;

        // 返回结果。
        return new QueryResult<>(pointKey, datas, hasMore);
    }

    private FilteredData reverseTransform(HibernateBridgeFilteredData data) throws Exception {
        Object value = valueCodingHandler.decode(data.getValue());
        return new FilteredData(
                data.getPointKey(),
                data.getFilterKey(),
                value,
                data.getMessage(),
                data.getHappenedDate()
        );
    }
}
