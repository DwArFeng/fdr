package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.entity.FetcherInfo;
import com.dwarfeng.fdr.stack.handler.Fetcher;
import com.dwarfeng.fdr.stack.handler.FetcherLocalCacheHandler;
import com.dwarfeng.fdr.stack.service.FetcherInfoMaintainService;
import com.dwarfeng.fdr.stack.service.FetcherQosService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

@Service
public class FetcherQosServiceImpl implements FetcherQosService {

    private final FetcherInfoMaintainService fetcherInfoMaintainService;

    private final FetcherLocalCacheHandler fetcherLocalCacheHandler;

    private final ServiceExceptionMapper sem;

    public FetcherQosServiceImpl(
            FetcherInfoMaintainService fetcherInfoMaintainService,
            FetcherLocalCacheHandler fetcherLocalCacheHandler,
            ServiceExceptionMapper sem
    ) {
        this.fetcherInfoMaintainService = fetcherInfoMaintainService;
        this.fetcherLocalCacheHandler = fetcherLocalCacheHandler;
        this.sem = sem;
    }

    @Override
    public boolean exists(LongIdKey fetcherInfoKey) throws ServiceException {
        try {
            return fetcherLocalCacheHandler.exists(fetcherInfoKey);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("判断指定的抓取器是否存在时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public FetcherDescription get(LongIdKey fetcherInfoKey) throws ServiceException {
        try {
            if (!fetcherLocalCacheHandler.exists(fetcherInfoKey)) {
                return null;
            }
            FetcherInfo fetcherInfo = fetcherInfoMaintainService.get(fetcherInfoKey);
            Fetcher fetcher = fetcherLocalCacheHandler.get(fetcherInfoKey);
            return new FetcherDescription(fetcherInfo, fetcher);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("获取指定抓取器的抓取器描述时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void clearLocalCache() throws ServiceException {
        try {
            fetcherLocalCacheHandler.clear();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("清除抓取器本地缓存时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
