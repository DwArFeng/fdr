package com.dwarfeng.fdr.impl.handler.bridge.hibernate;

import com.dwarfeng.fdr.impl.handler.bridge.AbstractPersister;
import com.dwarfeng.fdr.impl.handler.bridge.hibernate.bean.entity.HibernateBridgeFilteredData;
import com.dwarfeng.fdr.impl.handler.bridge.hibernate.serialize.SerializerFactory;
import com.dwarfeng.fdr.impl.handler.bridge.hibernate.service.HibernateBridgeFilteredDataMaintainService;
import com.dwarfeng.fdr.sdk.util.WatchUtil;
import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.fdr.stack.bean.dto.QueryInfo;
import com.dwarfeng.fdr.stack.bean.dto.QueryResult;
import com.dwarfeng.fdr.stack.handler.PersistHandler;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Hibernate 桥接一般数据持久化器。
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

    private final SerializerFactory serializerFactory;

    protected HibernateBridgeFilteredDataPersister(
            HibernateBridgeFilteredDataMaintainService service,
            SerializerFactory serializerFactory
    ) {
        super(false, QUERY_MANUALS);
        this.service = service;
        this.serializerFactory = serializerFactory;
    }

    @Override
    protected void doRecord(FilteredData dataRecord) throws Exception {
        Object value = dataRecord.getValue();
        String serializedValue = serializerFactory.getSerializer(value.getClass()).serialize(value);
        HibernateBridgeFilteredData filteredData = transformData(dataRecord, serializedValue);
        service.write(filteredData);
    }

    @Override
    protected void doRecord(List<FilteredData> dataRecords) throws Exception {
        List<HibernateBridgeFilteredData> entities = new ArrayList<>();
        for (FilteredData dataRecord : dataRecords) {
            Object value = dataRecord.getValue();
            String serializedValue = serializerFactory.getSerializer(value.getClass()).serialize(value);
            HibernateBridgeFilteredData filteredData = transformData(dataRecord, serializedValue);
            entities.add(filteredData);
        }
        service.batchWrite(entities);
    }

    private HibernateBridgeFilteredData transformData(FilteredData dataRecord, String serializedValue) {
        return new HibernateBridgeFilteredData(
                null,
                dataRecord.getPointKey(),
                dataRecord.getFilterKey(),
                serializedValue,
                dataRecord.getMessage(),
                dataRecord.getHappenedDate()
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

        // 转换数据。
        List<FilteredData> datas = lookup.getData().stream().map(
                d -> new FilteredData(
                        d.getPointKey(), d.getFilterKey(), d.getValue(), d.getMessage(), d.getHappenedDate()
                )
        ).collect(Collectors.toList());
        boolean hasMore = lookup.getTotalPages() > page + 1;
        return new QueryResult<>(pointKey, datas, hasMore);
    }
}
