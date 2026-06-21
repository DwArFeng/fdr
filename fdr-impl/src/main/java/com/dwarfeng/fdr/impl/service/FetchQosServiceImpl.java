package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.handler.FetchHandler;
import com.dwarfeng.fdr.stack.service.FetchQosService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

/**
 * 抓取 QOS 服务实现。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
@Service
public class FetchQosServiceImpl implements FetchQosService {

    private final FetchHandler fetchHandler;

    private final ServiceExceptionMapper sem;

    public FetchQosServiceImpl(
            FetchHandler fetchHandler,
            ServiceExceptionMapper sem
    ) {
        this.fetchHandler = fetchHandler;
        this.sem = sem;
    }

    @Override
    @BehaviorAnalyse
    public boolean isStarted() throws ServiceException {
        try {
            return fetchHandler.isStarted();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("获取抓取服务是否已经开始时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    @BehaviorAnalyse
    public void start() throws ServiceException {
        try {
            fetchHandler.start();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("启动抓取服务时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    @BehaviorAnalyse
    public void stop() throws ServiceException {
        try {
            fetchHandler.stop();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("停止抓取服务时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
