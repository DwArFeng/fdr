package com.dwarfeng.fdr.impl.handler.bridge.hibernate;

import com.dwarfeng.dct.handler.ValueCodingHandler;
import com.dwarfeng.fdr.impl.handler.bridge.FullPersister;
import com.dwarfeng.fdr.impl.handler.bridge.hibernate.bean.HibernateBridgeTriggeredData;
import com.dwarfeng.fdr.impl.handler.bridge.hibernate.service.HibernateBridgeTriggeredDataMaintainService;
import com.dwarfeng.fdr.sdk.util.ViewUtil;
import com.dwarfeng.fdr.stack.bean.dto.LookupInfo;
import com.dwarfeng.fdr.stack.bean.dto.LookupResult;
import com.dwarfeng.fdr.stack.bean.dto.TriggeredData;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

/**
 * Hibernate 桥接被触发数据持久化器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class HibernateBridgeTriggeredDataPersister extends FullPersister<TriggeredData> {

    public static final String DEFAULT = "default";

    private final HibernateBridgeTriggeredDataMaintainService service;

    private final ValueCodingHandler valueCodingHandler;

    private final ThreadPoolTaskExecutor executor;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    protected HibernateBridgeTriggeredDataPersister(
            HibernateBridgeTriggeredDataMaintainService service,
            @Qualifier("hibernateBridge.valueCodingHandler") ValueCodingHandler valueCodingHandler,
            ThreadPoolTaskExecutor executor
    ) {
        this.service = service;
        this.valueCodingHandler = valueCodingHandler;
        this.executor = executor;
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

    @Override
    protected LookupResult<TriggeredData> doLookup(LookupInfo lookupInfo) throws Exception {
        return lookupSingle(lookupInfo);
    }

    @Override
    protected List<LookupResult<TriggeredData>> doLookup(List<LookupInfo> lookupInfos) throws Exception {
        // 构造查看结果，并初始化。
        List<LookupResult<TriggeredData>> lookupResults = new ArrayList<>(lookupInfos.size());
        for (int i = 0; i < lookupInfos.size(); i++) {
            lookupResults.add(null);
        }

        // 遍历 lookupInfos，异步查看。
        List<CompletableFuture<?>> futures = new ArrayList<>(lookupInfos.size());
        for (int i = 0; i < lookupInfos.size(); i++) {
            final int index = i;
            final LookupInfo lookupInfo = lookupInfos.get(index);
            CompletableFuture<Void> future = CompletableFuture.runAsync(
                    () -> {
                        LookupResult<TriggeredData> lookupResult = wrappedLookupSingle(lookupInfo);
                        lookupResults.set(index, lookupResult);
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

        // 返回查看结果。
        return lookupResults;
    }

    private LookupResult<TriggeredData> wrappedLookupSingle(LookupInfo lookupInfo) throws CompletionException {
        try {
            return lookupSingle(lookupInfo);
        } catch (Exception e) {
            throw new CompletionException(e);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private LookupResult<TriggeredData> lookupSingle(LookupInfo lookupInfo) throws Exception {
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
        return new LookupResult<>(pointKey, datas, hasMore);
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
