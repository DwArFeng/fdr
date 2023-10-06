package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.entity.FilterInfo;
import com.dwarfeng.fdr.stack.cache.EnabledFilterInfoCache;
import com.dwarfeng.fdr.stack.dao.FilterInfoDao;
import com.dwarfeng.fdr.stack.service.EnabledFilterInfoLookupService;
import com.dwarfeng.fdr.stack.service.FilterInfoMaintainService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnabledFilterInfoLookupServiceImpl implements EnabledFilterInfoLookupService {

    private final FilterInfoDao dao;
    private final EnabledFilterInfoCache cache;
    private final ServiceExceptionMapper sem;

    @Value("${cache.timeout.key_list.enabled_filter_info}")
    private long timeout;

    public EnabledFilterInfoLookupServiceImpl(
            FilterInfoDao dao,
            EnabledFilterInfoCache cache,
            ServiceExceptionMapper sem
    ) {
        this.dao = dao;
        this.cache = cache;
        this.sem = sem;
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    public List<FilterInfo> getEnabledFilterInfos(LongIdKey pointKey) throws ServiceException {
        try {
            if (cache.exists(pointKey)) {
                return cache.get(pointKey);
            }
            List<FilterInfo> lookup = dao.lookup(
                    FilterInfoMaintainService.ENABLED_CHILD_FOR_POINT_INDEX_ASC, new Object[]{pointKey}
            );
            cache.set(pointKey, lookup, timeout);
            return lookup;
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查询有效的过滤器信息时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
