package com.dwarfeng.fdr.impl.handler.bridge.hibernate;

import com.dwarfeng.fdr.impl.handler.bridge.AbstractPersister;
import com.dwarfeng.fdr.impl.handler.bridge.hibernate.bean.entity.HibernateBridgeTriggeredData;
import com.dwarfeng.fdr.impl.handler.bridge.hibernate.serialize.SerializerFactory;
import com.dwarfeng.fdr.impl.handler.bridge.hibernate.service.HibernateBridgeTriggeredDataMaintainService;
import com.dwarfeng.fdr.sdk.util.WatchUtil;
import com.dwarfeng.fdr.stack.bean.dto.QueryInfo;
import com.dwarfeng.fdr.stack.bean.dto.QueryResult;
import com.dwarfeng.fdr.stack.bean.dto.TriggeredData;
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
 * Hibernate 桥接被触发数据持久化器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class HibernateBridgeTriggeredDataPersister extends AbstractPersister<TriggeredData> {

    public static final String DEFAULT = "default";

    private static final List<PersistHandler.QueryGuide> QUERY_MANUALS;

    static {
        QUERY_MANUALS = new ArrayList<>();
        QUERY_MANUALS.add(new PersistHandler.QueryGuide(
                DEFAULT, new String[0], "默认的查询方法"
        ));
    }

    private final HibernateBridgeTriggeredDataMaintainService service;

    private final SerializerFactory serializerFactory;

    protected HibernateBridgeTriggeredDataPersister(
            HibernateBridgeTriggeredDataMaintainService service,
            SerializerFactory serializerFactory
    ) {
        super(false, QUERY_MANUALS);
        this.service = service;
        this.serializerFactory = serializerFactory;
    }

    @Override
    protected void doRecord(TriggeredData data) throws Exception {
        Object value = data.getValue();
        String serializedValue = serializerFactory.getSerializer(value.getClass()).serialize(value);
        HibernateBridgeTriggeredData triggeredData = transformData(data, serializedValue);
        service.write(triggeredData);
    }

    @Override
    protected void doRecord(List<TriggeredData> datas) throws Exception {
        List<HibernateBridgeTriggeredData> entities = new ArrayList<>();
        for (TriggeredData data : datas) {
            Object value = data.getValue();
            String serializedValue = serializerFactory.getSerializer(value.getClass()).serialize(value);
            HibernateBridgeTriggeredData triggeredData = transformData(data, serializedValue);
            entities.add(triggeredData);
        }
        service.batchWrite(entities);
    }

    private HibernateBridgeTriggeredData transformData(TriggeredData data, String serializedValue) {
        return new HibernateBridgeTriggeredData(
                null,
                data.getPointKey(),
                data.getTriggerKey(),
                serializedValue,
                data.getMessage(),
                data.getHappenedDate()
        );
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected QueryResult<TriggeredData> doQuery(QueryInfo queryInfo) throws Exception {
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
            servicePreset = HibernateBridgeTriggeredDataMaintainService.CHILD_FOR_POINT_BETWEEN_CLOSE_CLOSE;
        } else if (includeStartDate) {
            servicePreset = HibernateBridgeTriggeredDataMaintainService.CHILD_FOR_POINT_BETWEEN_CLOSE_OPEN;
        } else if (includeEndDate) {
            servicePreset = HibernateBridgeTriggeredDataMaintainService.CHILD_FOR_POINT_BETWEEN_OPEN_CLOSE;
        } else {
            servicePreset = HibernateBridgeTriggeredDataMaintainService.CHILD_FOR_POINT_BETWEEN_OPEN_OPEN;
        }
        PagedData<HibernateBridgeTriggeredData> lookup = service.lookup(
                servicePreset, new Object[]{pointKey, startDate, endDate}, new PagingInfo(page, rows)
        );

        // 转换数据。
        List<TriggeredData> datas = lookup.getData().stream().map(
                d -> new TriggeredData(
                        d.getPointKey(), d.getTriggerKey(), d.getValue(), d.getMessage(), d.getHappenedDate()
                )
        ).collect(Collectors.toList());
        boolean hasMore = lookup.getTotalPages() > page + 1;
        return new QueryResult<>(pointKey, datas, hasMore);
    }
}
