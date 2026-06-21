package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.entity.FetcherInfo;
import com.dwarfeng.fdr.stack.handler.Fetcher;
import com.dwarfeng.fdr.stack.handler.FetcherLocalCacheHandler;
import com.dwarfeng.fdr.stack.handler.FetcherSession;
import com.dwarfeng.fdr.stack.handler.FetcherSessionHoldHandler;
import com.dwarfeng.fdr.stack.service.FetcherInfoMaintainService;
import com.dwarfeng.fdr.stack.service.FetcherSessionQosService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

@Service
public class FetcherSessionQosServiceImpl implements FetcherSessionQosService {

    private final FetcherInfoMaintainService fetcherInfoMaintainService;

    private final FetcherLocalCacheHandler fetcherLocalCacheHandler;
    private final FetcherSessionHoldHandler fetcherSessionHoldHandler;

    private final ServiceExceptionMapper sem;

    public FetcherSessionQosServiceImpl(
            FetcherInfoMaintainService fetcherInfoMaintainService,
            FetcherLocalCacheHandler fetcherLocalCacheHandler,
            FetcherSessionHoldHandler fetcherSessionHoldHandler,
            ServiceExceptionMapper sem
    ) {
        this.fetcherInfoMaintainService = fetcherInfoMaintainService;
        this.fetcherLocalCacheHandler = fetcherLocalCacheHandler;
        this.fetcherSessionHoldHandler = fetcherSessionHoldHandler;
        this.sem = sem;
    }

    @Override
    public boolean exists(LongIdKey fetcherInfoKey) throws ServiceException {
        try {
            return fetcherLocalCacheHandler.exists(fetcherInfoKey);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("判断指定的抓取器会话是否存在时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public FetcherSessionDescription get(LongIdKey fetcherInfoKey) throws ServiceException {
        try {
            if (!fetcherLocalCacheHandler.exists(fetcherInfoKey)) {
                return null;
            }
            FetcherInfo fetcherInfo = fetcherInfoMaintainService.get(fetcherInfoKey);
            Fetcher fetcher = fetcherLocalCacheHandler.get(fetcherInfoKey);
            FetcherSession fetcherSession = fetcherSessionHoldHandler.get(fetcherInfoKey);
            return new FetcherSessionDescription(fetcherInfo, fetcher, fetcherSession);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("获取指定抓取器的抓取器会话描述时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void closeAndClearHolding() throws ServiceException {
        try {
            fetcherSessionHoldHandler.closeAndClear();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("关闭并清除所有的抓取器会话时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
