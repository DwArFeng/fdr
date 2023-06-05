package com.dwarfeng.fdr.impl.handler.bridge.hibernate;

import com.dwarfeng.dct.handler.ValueCodingHandler;
import com.dwarfeng.fdr.impl.handler.bridge.FullPersister;
import com.dwarfeng.fdr.impl.handler.bridge.hibernate.bean.HibernateBridgeNormalData;
import com.dwarfeng.fdr.impl.handler.bridge.hibernate.service.HibernateBridgeNormalDataMaintainService;
import com.dwarfeng.fdr.sdk.util.WatchUtil;
import com.dwarfeng.fdr.stack.bean.dto.LookupInfo;
import com.dwarfeng.fdr.stack.bean.dto.LookupResult;
import com.dwarfeng.fdr.stack.bean.dto.NormalData;
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
public class HibernateBridgeNormalDataPersister extends FullPersister<NormalData> {

    public static final String DEFAULT = "default";

    private static final List<PersistHandler.LookupGuide> QUERY_MANUALS;

    static {
        QUERY_MANUALS = new ArrayList<>();
        QUERY_MANUALS.add(new PersistHandler.LookupGuide(
                DEFAULT, new String[0], "默认的查询方法"
        ));
    }

    private final HibernateBridgeNormalDataMaintainService service;

    private final ValueCodingHandler valueCodingHandler;

    protected HibernateBridgeNormalDataPersister(
            HibernateBridgeNormalDataMaintainService service,
            @Qualifier("hibernateBridge.valueCodingHandler") ValueCodingHandler valueCodingHandler
    ) {
        super(QUERY_MANUALS);
        this.service = service;
        this.valueCodingHandler = valueCodingHandler;
    }

    @Override
    protected void doRecord(NormalData data) throws Exception {
        HibernateBridgeNormalData normalData = transform(data);
        service.write(normalData);
    }

    @Override
    protected void doRecord(List<NormalData> datas) throws Exception {
        List<HibernateBridgeNormalData> entities = new ArrayList<>();
        for (NormalData data : datas) {
            HibernateBridgeNormalData normalData = transform(data);
            entities.add(normalData);
        }
        service.batchWrite(entities);
    }

    private HibernateBridgeNormalData transform(NormalData data) throws Exception {
        String flatValue = valueCodingHandler.encode(data.getValue());
        return new HibernateBridgeNormalData(
                null,
                data.getPointKey(),
                flatValue,
                data.getHappenedDate()
        );
    }

    @Override
    protected LookupResult<NormalData> doQuery(LookupInfo lookupInfo) throws Exception {
        return doSingleQuery(lookupInfo);
    }

    @Override
    protected List<LookupResult<NormalData>> doQuery(List<LookupInfo> lookupInfos) throws Exception {
        List<LookupResult<NormalData>> resultList = new ArrayList<>();
        for (LookupInfo lookupInfo : lookupInfos) {
            resultList.add(doSingleQuery(lookupInfo));
        }
        return resultList;
    }

    @SuppressWarnings("DuplicatedCode")
    private LookupResult<NormalData> doSingleQuery(LookupInfo lookupInfo) throws Exception {
        // 展开查询信息。
        LongIdKey pointKey = lookupInfo.getPointKey();
        Date startDate = WatchUtil.validStartDate(lookupInfo.getStartDate());
        Date endDate = WatchUtil.validEndDate(lookupInfo.getEndDate());
        int page = WatchUtil.validPage(lookupInfo.getPage());
        int rows = WatchUtil.validRows(lookupInfo.getRows());
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

        // 处理数据。
        List<HibernateBridgeNormalData> hibernateBridgeDatas = lookup.getData();
        List<NormalData> datas = new ArrayList<>(hibernateBridgeDatas.size());
        for (HibernateBridgeNormalData hibernateBridgeData : hibernateBridgeDatas) {
            NormalData data = reverseTransform(hibernateBridgeData);
            datas.add(data);
        }
        boolean hasMore = lookup.getTotalPages() > page + 1;

        // 返回结果。
        return new LookupResult<>(pointKey, datas, hasMore);
    }

    private NormalData reverseTransform(HibernateBridgeNormalData data) throws Exception {
        Object value = valueCodingHandler.decode(data.getValue());
        return new NormalData(
                data.getPointKey(),
                value,
                data.getHappenedDate()
        );
    }
}
