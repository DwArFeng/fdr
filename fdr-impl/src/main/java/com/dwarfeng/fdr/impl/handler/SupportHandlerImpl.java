package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.sdk.handler.FilterSupporter;
import com.dwarfeng.fdr.sdk.handler.MapperSupporter;
import com.dwarfeng.fdr.sdk.handler.TriggerSupporter;
import com.dwarfeng.fdr.sdk.handler.WasherSupporter;
import com.dwarfeng.fdr.stack.bean.entity.FilterSupport;
import com.dwarfeng.fdr.stack.bean.entity.MapperSupport;
import com.dwarfeng.fdr.stack.bean.entity.TriggerSupport;
import com.dwarfeng.fdr.stack.bean.entity.WasherSupport;
import com.dwarfeng.fdr.stack.handler.SupportHandler;
import com.dwarfeng.fdr.stack.service.FilterSupportMaintainService;
import com.dwarfeng.fdr.stack.service.MapperSupportMaintainService;
import com.dwarfeng.fdr.stack.service.TriggerSupportMaintainService;
import com.dwarfeng.fdr.stack.service.WasherSupportMaintainService;
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

    private final List<FilterSupporter> filterSupporters;
    private final List<WasherSupporter> washerSupporters;
    private final List<TriggerSupporter> triggerSupporters;
    private final List<MapperSupporter> mapperSupporters;

    public SupportHandlerImpl(
            FilterSupportMaintainService filterSupportMaintainService,
            WasherSupportMaintainService washerSupportMaintainService,
            TriggerSupportMaintainService triggerSupportMaintainService,
            MapperSupportMaintainService mapperSupportMaintainService,
            List<FilterSupporter> filterSupporters,
            List<WasherSupporter> washerSupporters,
            List<TriggerSupporter> triggerSupporters,
            List<MapperSupporter> mapperSupporters
    ) {
        this.filterSupportMaintainService = filterSupportMaintainService;
        this.washerSupportMaintainService = washerSupportMaintainService;
        this.triggerSupportMaintainService = triggerSupportMaintainService;
        this.mapperSupportMaintainService = mapperSupportMaintainService;
        this.filterSupporters = Optional.ofNullable(filterSupporters).orElse(Collections.emptyList());
        this.washerSupporters = Optional.ofNullable(washerSupporters).orElse(Collections.emptyList());
        this.triggerSupporters = Optional.ofNullable(triggerSupporters).orElse(Collections.emptyList());
        this.mapperSupporters = Optional.ofNullable(mapperSupporters).orElse(Collections.emptyList());
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

    @SuppressWarnings("DuplicatedCode")
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

    @SuppressWarnings("DuplicatedCode")
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

    @SuppressWarnings("DuplicatedCode")
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

    @SuppressWarnings("DuplicatedCode")
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
}
