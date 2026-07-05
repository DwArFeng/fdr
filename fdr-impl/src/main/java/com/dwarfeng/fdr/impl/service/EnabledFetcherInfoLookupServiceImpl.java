package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.entity.FetcherInfo;
import com.dwarfeng.fdr.stack.cache.EnabledFetcherInfoCache;
import com.dwarfeng.fdr.stack.dao.FetcherInfoDao;
import com.dwarfeng.fdr.stack.service.EnabledFetcherInfoLookupService;
import com.dwarfeng.fdr.stack.service.FetcherInfoMaintainService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <code>EnabledFetcherInfoLookupService</code> 的默认实现。
 *
 * <p>
 * 该实现通过缓存优先策略查询所有启用的抓取器信息：
 * 如果缓存中存在则直接返回，否则从数据库中查询并回填缓存。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
@Service
public class EnabledFetcherInfoLookupServiceImpl implements EnabledFetcherInfoLookupService {

    private final FetcherInfoDao dao;
    private final EnabledFetcherInfoCache cache;
    private final ServiceExceptionMapper sem;

    @Value("${com.dwarfeng.fdr.cache.timeout.key_list.enabled_fetcher_info}")
    private long timeout;

    public EnabledFetcherInfoLookupServiceImpl(
            FetcherInfoDao dao,
            EnabledFetcherInfoCache cache,
            ServiceExceptionMapper sem
    ) {
        this.dao = dao;
        this.cache = cache;
        this.sem = sem;
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    public List<FetcherInfo> getEnabledFetcherInfos() throws ServiceException {
        try {
            if (cache.exists()) {
                return cache.get();
            }
            List<FetcherInfo> lookup = dao.lookup(
                    FetcherInfoMaintainService.ENABLED, new Object[0]
            );
            cache.set(lookup, timeout);
            return lookup;
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查询有效的抓取器信息时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
