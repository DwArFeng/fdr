package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.sdk.handler.*;
import com.dwarfeng.fdr.sdk.handler.FilterSupporter;
import com.dwarfeng.fdr.sdk.handler.MapperSupporter;
import com.dwarfeng.fdr.sdk.handler.TriggerSupporter;
import com.dwarfeng.fdr.sdk.handler.WasherSupporter;
import com.dwarfeng.fdr.stack.bean.entity.*;
import com.dwarfeng.fdr.stack.handler.SupportHandler;
import com.dwarfeng.fdr.stack.service.*;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SupportHandlerImpl implements SupportHandler {

    private final FilterSupportMaintainService filterSupportMaintainService;
    private final WasherSupportMaintainService washerSupportMaintainService;
    private final TriggerSupportMaintainService triggerSupportMaintainService;
    private final MapperSupportMaintainService mapperSupportMaintainService;
    private final FetcherSupportMaintainService fetcherSupportMaintainService;

    private final List<FilterSupporter> filterSupporters;
    private final List<WasherSupporter> washerSupporters;
    private final List<TriggerSupporter> triggerSupporters;
    private final List<MapperSupporter> mapperSupporters;
    private final List<FetcherSupporter> fetcherSupporters;

    public SupportHandlerImpl(
            FilterSupportMaintainService filterSupportMaintainService,
            WasherSupportMaintainService washerSupportMaintainService,
            TriggerSupportMaintainService triggerSupportMaintainService,
            MapperSupportMaintainService mapperSupportMaintainService,
            FetcherSupportMaintainService fetcherSupportMaintainService,
            List<FilterSupporter> filterSupporters,
            List<WasherSupporter> washerSupporters,
            List<TriggerSupporter> triggerSupporters,
            List<MapperSupporter> mapperSupporters,
            List<FetcherSupporter> fetcherSupporters
    ) {
        this.filterSupportMaintainService = filterSupportMaintainService;
        this.washerSupportMaintainService = washerSupportMaintainService;
        this.triggerSupportMaintainService = triggerSupportMaintainService;
        this.mapperSupportMaintainService = mapperSupportMaintainService;
        this.fetcherSupportMaintainService = fetcherSupportMaintainService;
        this.filterSupporters = Optional.ofNullable(filterSupporters).orElse(Collections.emptyList());
        this.washerSupporters = Optional.ofNullable(washerSupporters).orElse(Collections.emptyList());
        this.triggerSupporters = Optional.ofNullable(triggerSupporters).orElse(Collections.emptyList());
        this.mapperSupporters = Optional.ofNullable(mapperSupporters).orElse(Collections.emptyList());
        this.fetcherSupporters = Optional.ofNullable(fetcherSupporters).orElse(Collections.emptyList());
    }

    @Override
    @BehaviorAnalyse
    public void resetFilter() throws HandlerException {
        try {
            doResetFilter();
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private void doResetFilter() throws Exception {
        List<StringIdKey> filterKeys = filterSupportMaintainService.lookupAsList().stream()
                .map(FilterSupport::getKey).collect(Collectors.toList());
        filterSupportMaintainService.batchDelete(filterKeys);
        List<FilterSupport> filterSupports = filterSupporters.stream().map(
                supporter -> new FilterSupport(
                        new StringIdKey(supporter.provideType()),
                        supporter.provideLabel(),
                        supporter.provideDescription(),
                        supporter.provideExampleParam()
                )
        ).collect(Collectors.toList());
        filterSupportMaintainService.batchInsert(filterSupports);
    }

    @Override
    @BehaviorAnalyse
    public void resetWasher() throws HandlerException {
        try {
            doResetWasher();
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private void doResetWasher() throws Exception {
        List<StringIdKey> washerKeys = washerSupportMaintainService.lookupAsList().stream()
                .map(WasherSupport::getKey).collect(Collectors.toList());
        washerSupportMaintainService.batchDelete(washerKeys);
        List<WasherSupport> washerSupports = washerSupporters.stream().map(
                supporter -> new WasherSupport(
                        new StringIdKey(supporter.provideType()),
                        supporter.provideLabel(),
                        supporter.provideDescription(),
                        supporter.provideExampleParam()
                )
        ).collect(Collectors.toList());
        washerSupportMaintainService.batchInsert(washerSupports);
    }

    @Override
    @BehaviorAnalyse
    public void resetTrigger() throws HandlerException {
        try {
            doResetTrigger();
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private void doResetTrigger() throws Exception {
        List<StringIdKey> triggerKeys = triggerSupportMaintainService.lookupAsList().stream()
                .map(TriggerSupport::getKey).collect(Collectors.toList());
        triggerSupportMaintainService.batchDelete(triggerKeys);
        List<TriggerSupport> triggerSupports = triggerSupporters.stream().map(
                supporter -> new TriggerSupport(
                        new StringIdKey(supporter.provideType()),
                        supporter.provideLabel(),
                        supporter.provideDescription(),
                        supporter.provideExampleParam()
                )
        ).collect(Collectors.toList());
        triggerSupportMaintainService.batchInsert(triggerSupports);
    }

    @Override
    @BehaviorAnalyse
    public void resetMapper() throws HandlerException {
        try {
            doResetMapper();
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private void doResetMapper() throws Exception {
        List<StringIdKey> mapperKeys = mapperSupportMaintainService.lookupAsList().stream()
                .map(MapperSupport::getKey).collect(Collectors.toList());
        mapperSupportMaintainService.batchDelete(mapperKeys);
        List<MapperSupport> mapperSupports = mapperSupporters.stream().map(
                supporter -> new MapperSupport(
                        new StringIdKey(supporter.provideType()),
                        supporter.provideLabel(),
                        supporter.provideDescription(),
                        supporter.provideExampleParam()
                )
        ).collect(Collectors.toList());
        mapperSupportMaintainService.batchInsert(mapperSupports);
    }

    @Override
    @BehaviorAnalyse
    public void resetFetcher() throws HandlerException {
        try {
            doResetFetcher();
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private void doResetFetcher() throws Exception {
        List<StringIdKey> fetcherKeys = fetcherSupportMaintainService.lookupAsList().stream()
                .map(FetcherSupport::getKey).collect(Collectors.toList());
        fetcherSupportMaintainService.batchDelete(fetcherKeys);
        List<FetcherSupport> fetcherSupports = fetcherSupporters.stream().map(
                supporter -> new FetcherSupport(
                        new StringIdKey(supporter.provideType()),
                        supporter.provideLabel(),
                        supporter.provideDescription(),
                        supporter.provideExampleParam()
                )
        ).collect(Collectors.toList());
        fetcherSupportMaintainService.batchInsert(fetcherSupports);
    }

}
