package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.entity.WasherInfo;
import com.dwarfeng.fdr.stack.cache.EnabledWasherInfoCache;
import com.dwarfeng.fdr.stack.dao.WasherInfoDao;
import com.dwarfeng.fdr.stack.service.EnabledWasherInfoLookupService;
import com.dwarfeng.fdr.stack.service.WasherInfoMaintainService;
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
public class EnabledWasherInfoLookupServiceImpl implements EnabledWasherInfoLookupService {

    private final WasherInfoDao dao;
    private final EnabledWasherInfoCache cache;
    private final ServiceExceptionMapper sem;

    @Value("${cache.timeout.key_list.enabled_washer_info}")
    private long timeout;

    public EnabledWasherInfoLookupServiceImpl(
            WasherInfoDao dao,
            EnabledWasherInfoCache cache,
            ServiceExceptionMapper sem
    ) {
        this.dao = dao;
        this.cache = cache;
        this.sem = sem;
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    public List<WasherInfo> getEnabledWasherInfos(LongIdKey pointKey) throws ServiceException {
        try {
            if (cache.exists(pointKey)) {
                return cache.get(pointKey);
            }
            List<WasherInfo> lookup = dao.lookup(
                    WasherInfoMaintainService.ENABLED_CHILD_FOR_POINT_INDEX_ASC, new Object[]{pointKey}
            );
            cache.set(pointKey, lookup, timeout);
            return lookup;
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询有效的清洗器信息时发生异常", LogLevel.WARN, sem, e);
        }
    }
}
