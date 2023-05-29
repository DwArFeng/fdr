package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.entity.TriggerInfo;
import com.dwarfeng.fdr.stack.cache.EnabledTriggerInfoCache;
import com.dwarfeng.fdr.stack.dao.TriggerInfoDao;
import com.dwarfeng.fdr.stack.service.EnabledTriggerInfoLookupService;
import com.dwarfeng.fdr.stack.service.TriggerInfoMaintainService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnabledTriggerInfoLookupServiceImpl implements EnabledTriggerInfoLookupService {

    private final TriggerInfoDao dao;
    private final EnabledTriggerInfoCache cache;
    private final ServiceExceptionMapper sem;

    @Value("${cache.timeout.key_list.enabled_trigger_info}")
    private long timeout;

    public EnabledTriggerInfoLookupServiceImpl(
            TriggerInfoDao dao,
            EnabledTriggerInfoCache cache,
            @Qualifier("mapServiceExceptionMapper") ServiceExceptionMapper sem
    ) {
        this.dao = dao;
        this.cache = cache;
        this.sem = sem;
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    public List<TriggerInfo> getEnabledTriggerInfos(LongIdKey pointKey) throws ServiceException {
        try {
            if (cache.exists(pointKey)) {
                return cache.get(pointKey);
            }
            List<TriggerInfo> lookup = dao.lookup(
                    TriggerInfoMaintainService.ENABLED_CHILD_FOR_POINT_INDEX_ASC, new Object[]{pointKey}
            );
            cache.set(pointKey, lookup, timeout);
            return lookup;
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询有效的过滤器信息时发生异常", LogLevel.WARN, sem, e);
        }
    }
}
