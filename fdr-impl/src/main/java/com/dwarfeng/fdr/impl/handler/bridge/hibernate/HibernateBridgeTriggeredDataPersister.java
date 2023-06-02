package com.dwarfeng.fdr.impl.handler.bridge.hibernate;

import com.dwarfeng.dct.handler.ValueCodingHandler;
import com.dwarfeng.fdr.impl.handler.bridge.AbstractPersister;
import com.dwarfeng.fdr.impl.handler.bridge.hibernate.bean.HibernateBridgeTriggeredData;
import com.dwarfeng.fdr.impl.handler.bridge.hibernate.service.HibernateBridgeTriggeredDataMaintainService;
import com.dwarfeng.fdr.sdk.util.WatchUtil;
import com.dwarfeng.fdr.stack.bean.dto.QueryInfo;
import com.dwarfeng.fdr.stack.bean.dto.QueryResult;
import com.dwarfeng.fdr.stack.bean.dto.TriggeredData;
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

    private final ValueCodingHandler valueCodingHandler;

    protected HibernateBridgeTriggeredDataPersister(
            HibernateBridgeTriggeredDataMaintainService service,
            @Qualifier("hibernateBridge.valueCodingHandler") ValueCodingHandler valueCodingHandler
    ) {
        super(false, QUERY_MANUALS);
        this.service = service;
        this.valueCodingHandler = valueCodingHandler;
    }

    @Override
    protected void doRecord(TriggeredData data) throws Exception {
        HibernateBridgeTriggeredData triggeredData = transform(data);
        service.write(triggeredData);
    }

    @Override
    protected void doRecord(List<TriggeredData> datas) throws Exception {
        List<HibernateBridgeTriggeredData> entities = new ArrayList<>();
        for (TriggeredData data : datas) {
            HibernateBridgeTriggeredData triggeredData = transform(data);
            entities.add(triggeredData);
        }
        service.batchWrite(entities);
    }

    private HibernateBridgeTriggeredData transform(TriggeredData data) throws Exception {
        String flatValue = valueCodingHandler.encode(data.getValue());
        return new HibernateBridgeTriggeredData(
                null,
                data.getPointKey(),
                data.getTriggerKey(),
                flatValue,
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

        // 处理数据。
        List<HibernateBridgeTriggeredData> hibernateBridgeDatas = lookup.getData();
        List<TriggeredData> datas = new ArrayList<>(hibernateBridgeDatas.size());
        for (HibernateBridgeTriggeredData hibernateBridgeData : hibernateBridgeDatas) {
            TriggeredData data = reverseTransform(hibernateBridgeData);
            datas.add(data);
        }
        boolean hasMore = lookup.getTotalPages() > page + 1;

        // 返回结果。
        return new QueryResult<>(pointKey, datas, hasMore);
    }

    private TriggeredData reverseTransform(HibernateBridgeTriggeredData data) throws Exception {
        Object value = valueCodingHandler.decode(data.getValue());
        return new TriggeredData(
                data.getPointKey(),
                data.getTriggerKey(),
                value,
                data.getMessage(),
                data.getHappenedDate()
        );
    }
}
