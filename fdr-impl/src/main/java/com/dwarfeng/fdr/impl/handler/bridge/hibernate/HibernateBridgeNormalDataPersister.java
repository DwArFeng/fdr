package com.dwarfeng.fdr.impl.handler.bridge.hibernate;

import com.dwarfeng.fdr.impl.handler.bridge.AbstractPersister;
import com.dwarfeng.fdr.impl.handler.bridge.hibernate.bean.entity.HibernateBridgeNormalData;
import com.dwarfeng.fdr.impl.handler.bridge.hibernate.serialize.SerializerFactory;
import com.dwarfeng.fdr.impl.handler.bridge.hibernate.service.HibernateBridgeNormalDataMaintainService;
import com.dwarfeng.fdr.sdk.util.WatchUtil;
import com.dwarfeng.fdr.stack.bean.dto.NormalData;
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
 * Hibernate 桥接被过滤数据持久化器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class HibernateBridgeNormalDataPersister extends AbstractPersister<NormalData> {

    public static final String DEFAULT = "default";

    private static final List<PersistHandler.QueryGuide> QUERY_MANUALS;

    static {
        QUERY_MANUALS = new ArrayList<>();
        QUERY_MANUALS.add(new PersistHandler.QueryGuide(
                DEFAULT, new String[0], "默认的查询方法"
        ));
    }

    private final HibernateBridgeNormalDataMaintainService service;

    private final SerializerFactory serializerFactory;

    protected HibernateBridgeNormalDataPersister(
            HibernateBridgeNormalDataMaintainService service,
            SerializerFactory serializerFactory
    ) {
        super(false, QUERY_MANUALS);
        this.service = service;
        this.serializerFactory = serializerFactory;
    }

    @Override
    protected void doRecord(NormalData data) throws Exception {
        Object value = data.getValue();
        String serializedValue = serializerFactory.getSerializer(value.getClass()).serialize(value);
        HibernateBridgeNormalData normalData = transformData(data, serializedValue);
        service.write(normalData);
    }

    @Override
    protected void doRecord(List<NormalData> datas) throws Exception {
        List<HibernateBridgeNormalData> entities = new ArrayList<>();
        for (NormalData data : datas) {
            Object value = data.getValue();
            String serializedValue = serializerFactory.getSerializer(value.getClass()).serialize(value);
            HibernateBridgeNormalData normalData = transformData(data, serializedValue);
            entities.add(normalData);
        }
        service.batchWrite(entities);
    }

    private HibernateBridgeNormalData transformData(NormalData data, String serializedValue) {
        return new HibernateBridgeNormalData(
                null,
                data.getPointKey(),
                serializedValue,
                data.getHappenedDate()
        );
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected QueryResult<NormalData> doQuery(QueryInfo queryInfo) throws Exception {
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
            servicePreset = HibernateBridgeNormalDataMaintainService.CHILD_FOR_POINT_BETWEEN_CLOSE_CLOSE;
        } else if (includeStartDate) {
            servicePreset = HibernateBridgeNormalDataMaintainService.CHILD_FOR_POINT_BETWEEN_CLOSE_OPEN;
        } else if (includeEndDate) {
            servicePreset = HibernateBridgeNormalDataMaintainService.CHILD_FOR_POINT_BETWEEN_OPEN_CLOSE;
        } else {
            servicePreset = HibernateBridgeNormalDataMaintainService.CHILD_FOR_POINT_BETWEEN_OPEN_OPEN;
        }
        PagedData<HibernateBridgeNormalData> lookup = service.lookup(
                servicePreset, new Object[]{pointKey, startDate, endDate}, new PagingInfo(page, rows)
        );

        // 转换数据。
        List<NormalData> datas = lookup.getData().stream().map(
                d -> new NormalData(d.getPointKey(), d.getValue(), d.getHappenedDate())
        ).collect(Collectors.toList());
        boolean hasMore = lookup.getTotalPages() > page + 1;
        return new QueryResult<>(pointKey, datas, hasMore);
    }
}
