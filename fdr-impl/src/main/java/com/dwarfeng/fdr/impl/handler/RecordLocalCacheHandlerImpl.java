package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.bean.entity.FilterInfo;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.entity.TriggerInfo;
import com.dwarfeng.fdr.stack.handler.*;
import com.dwarfeng.fdr.stack.service.EnabledFilterInfoLookupService;
import com.dwarfeng.fdr.stack.service.EnabledTriggerInfoLookupService;
import com.dwarfeng.fdr.stack.service.PointMaintainService;
import com.dwarfeng.subgrade.impl.handler.Fetcher;
import com.dwarfeng.subgrade.impl.handler.GeneralLocalCacheHandler;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class RecordLocalCacheHandlerImpl implements RecordLocalCacheHandler {

    private final GeneralLocalCacheHandler<LongIdKey, RecordContext> handler;

    public RecordLocalCacheHandlerImpl(RecordContextFetcher recordContextFetcher) {
        handler = new GeneralLocalCacheHandler<>(recordContextFetcher);
    }

    @BehaviorAnalyse
    @Override
    public boolean exists(LongIdKey key) throws HandlerException {
        return handler.exists(key);
    }

    @BehaviorAnalyse
    @Override
    public RecordContext get(LongIdKey key) throws HandlerException {
        return handler.get(key);
    }

    @BehaviorAnalyse
    @Override
    public boolean remove(LongIdKey key) {
        return handler.remove(key);
    }

    @BehaviorAnalyse
    @Override
    public void clear() throws HandlerException {
        handler.clear();
    }

    @Component
    public static class RecordContextFetcher implements Fetcher<LongIdKey, RecordContext> {

        private final PointMaintainService pointMaintainService;
        private final EnabledFilterInfoLookupService enabledFilterInfoLookupService;
        private final EnabledTriggerInfoLookupService enabledTriggerInfoLookupService;

        private final FilterHandler filterHandler;
        private final TriggerHandler triggerHandler;

        public RecordContextFetcher(
                PointMaintainService pointMaintainService,
                EnabledFilterInfoLookupService enabledFilterInfoLookupService,
                EnabledTriggerInfoLookupService enabledTriggerInfoLookupService,
                FilterHandler filterHandler,
                TriggerHandler triggerHandler
        ) {
            this.pointMaintainService = pointMaintainService;
            this.enabledFilterInfoLookupService = enabledFilterInfoLookupService;
            this.enabledTriggerInfoLookupService = enabledTriggerInfoLookupService;
            this.filterHandler = filterHandler;
            this.triggerHandler = triggerHandler;
        }

        @Override
        @BehaviorAnalyse
        @Transactional(
                transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class
        )
        public boolean exists(LongIdKey key) throws Exception {
            return pointMaintainService.exists(key);
        }

        @Override
        @BehaviorAnalyse
        @Transactional(
                transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class
        )
        public RecordContext fetch(LongIdKey key) throws Exception {
            Point point = pointMaintainService.get(key);
            List<FilterInfo> filterInfos = enabledFilterInfoLookupService.getEnabledFilterInfos(key);
            List<TriggerInfo> triggerInfos = enabledTriggerInfoLookupService.getEnabledTriggerInfos(key);

            List<Filter> filters = new ArrayList<>();
            List<Trigger> triggers = new ArrayList<>();

            for (FilterInfo filterInfo : filterInfos) {
                filters.add(filterHandler.make(filterInfo));
            }
            for (TriggerInfo triggerInfo : triggerInfos) {
                triggers.add(triggerHandler.make(triggerInfo));
            }

            return new RecordLocalCacheHandler.RecordContext(point, filters, triggers);
        }
    }
}
