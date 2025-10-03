package com.dwarfeng.fdr.impl.handler.bridge.hibernate;

import com.dwarfeng.dct.handler.ValueCodingHandler;
import com.dwarfeng.fdr.impl.handler.bridge.hibernate.service.HibernateBridgeMaintainService;
import com.dwarfeng.fdr.sdk.handler.bridge.FullPersister;
import com.dwarfeng.fdr.sdk.util.ViewUtil;
import com.dwarfeng.fdr.stack.bean.dto.LookupInfo;
import com.dwarfeng.fdr.stack.bean.dto.LookupResult;
import com.dwarfeng.fdr.stack.bean.dto.NativeQueryInfo;
import com.dwarfeng.fdr.stack.bean.dto.QueryResult;
import com.dwarfeng.fdr.stack.struct.Data;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

/**
 * Hibernate 桥接器持久器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public abstract class HibernateBridgePersister<D extends Data, T extends Entity<LongIdKey>>
        extends FullPersister<D> {

    public static final String LOOKUP_PRESET_DEFAULT = "default";

    protected final HibernateBridgeMaintainService<T> service;
    protected final ValueCodingHandler valueCodingHandler;
    protected final ThreadPoolTaskExecutor executor;

    public HibernateBridgePersister(
            HibernateBridgeMaintainService<T> service,
            ValueCodingHandler valueCodingHandler,
            ThreadPoolTaskExecutor executor
    ) {
        this.service = service;
        this.valueCodingHandler = valueCodingHandler;
        this.executor = executor;
    }

    @Override
    protected void doRecord(D data) throws Exception {
        T t = transform(data);
        service.write(t);
    }

    @Override
    protected void doRecord(List<D> datas) throws Exception {
        List<T> entities = new ArrayList<>();
        for (D data : datas) {
            T t = transform(data);
            entities.add(t);
        }
        service.batchWrite(entities);
    }

    protected abstract T transform(D data) throws Exception;

    @Override
    protected LookupResult<D> doLookup(LookupInfo lookupInfo) throws Exception {
        return lookupSingle(lookupInfo);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected List<LookupResult<D>> doLookup(List<LookupInfo> lookupInfos) throws Exception {
        // 构造查看结果，并初始化。
        List<LookupResult<D>> lookupResults = new ArrayList<>(lookupInfos.size());
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
                        LookupResult<D> lookupResult = wrappedLookupSingle(lookupInfo);
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

    private LookupResult<D> wrappedLookupSingle(LookupInfo lookupInfo) throws CompletionException {
        try {
            return lookupSingle(lookupInfo);
        } catch (Exception e) {
            throw new CompletionException(e);
        }
    }

    private LookupResult<D> lookupSingle(LookupInfo lookupInfo) throws Exception {
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
        if (!LOOKUP_PRESET_DEFAULT.equals(preset)) {
            throw new IllegalArgumentException("预设不合法: " + preset);
        }

        // 查询数据。
        String servicePreset = resolveServicePreset(includeStartDate, includeEndDate);
        PagedData<T> lookup = service.lookup(
                servicePreset, new Object[]{pointKey, startDate, endDate}, new PagingInfo(page, rows)
        );

        // 处理数据。
        List<T> ts = lookup.getData();
        List<D> datas = new ArrayList<>(ts.size());
        for (T t : ts) {
            D data = reverseTransform(t);
            datas.add(data);
        }
        boolean hasMore = lookup.getTotalPages() > page + 1;

        // 返回结果。
        return new LookupResult<>(pointKey, datas, hasMore);
    }

    protected abstract String resolveServicePreset(boolean includeStartDate, boolean includeEndDate);

    protected abstract D reverseTransform(T t) throws Exception;

    @Override
    protected QueryResult doNativeQuery(NativeQueryInfo queryInfo) throws IllegalArgumentException {
        String preset = queryInfo.getPreset();
        throw new IllegalArgumentException("预设不合法: " + preset);
    }

    @Override
    protected List<QueryResult> doNativeQuery(List<NativeQueryInfo> queryInfos) throws IllegalArgumentException {
        if (queryInfos.isEmpty()) {
            return new ArrayList<>(0);
        }
        String preset = queryInfos.get(0).getPreset();
        throw new IllegalArgumentException("预设不合法: " + preset);
    }
}
