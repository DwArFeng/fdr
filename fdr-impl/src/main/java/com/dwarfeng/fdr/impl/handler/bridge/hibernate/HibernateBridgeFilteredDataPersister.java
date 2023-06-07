package com.dwarfeng.fdr.impl.handler.bridge.hibernate;

import com.dwarfeng.dct.handler.ValueCodingHandler;
import com.dwarfeng.fdr.impl.handler.bridge.FullPersister;
import com.dwarfeng.fdr.impl.handler.bridge.hibernate.bean.HibernateBridgeFilteredData;
import com.dwarfeng.fdr.impl.handler.bridge.hibernate.service.HibernateBridgeFilteredDataMaintainService;
import com.dwarfeng.fdr.sdk.util.ViewUtil;
import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.fdr.stack.bean.dto.LookupInfo;
import com.dwarfeng.fdr.stack.bean.dto.LookupResult;
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
public class HibernateBridgeFilteredDataPersister extends FullPersister<FilteredData> {

    public static final String DEFAULT = "default";

    private final HibernateBridgeFilteredDataMaintainService service;

    private final ValueCodingHandler valueCodingHandler;

    protected HibernateBridgeFilteredDataPersister(
            HibernateBridgeFilteredDataMaintainService service,
            @Qualifier("hibernateBridge.valueCodingHandler") ValueCodingHandler valueCodingHandler
    ) {
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

    @Override
    protected LookupResult<FilteredData> doQuery(LookupInfo lookupInfo) throws Exception {
        return doSingleQuery(lookupInfo);
    }

    @Override
    protected List<LookupResult<FilteredData>> doQuery(List<LookupInfo> lookupInfos) throws Exception {
        List<LookupResult<FilteredData>> resultList = new ArrayList<>();
        for (LookupInfo lookupInfo : lookupInfos) {
            resultList.add(doSingleQuery(lookupInfo));
        }
        return resultList;
    }

    @SuppressWarnings("DuplicatedCode")
    private LookupResult<FilteredData> doSingleQuery(LookupInfo lookupInfo) throws Exception {
        // 展开查询信息。
        LongIdKey pointKey = lookupInfo.getPointKey();
        Date startDate = ViewUtil.validStartDate(lookupInfo.getStartDate());
        Date endDate = ViewUtil.validEndDate(lookupInfo.getEndDate());
        int page = ViewUtil.validPage(lookupInfo.getPage());
        int rows = ViewUtil.validRows(lookupInfo.getRows());
        boolean includeStartDate = lookupInfo.isIncludeStartDate();
        boolean includeEndDate = lookupInfo.isIncludeEndDate();

        // 检查预设是否合法。
        String preset = lookupInfo.getPreset();
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
        return new LookupResult<>(pointKey, datas, hasMore);
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
