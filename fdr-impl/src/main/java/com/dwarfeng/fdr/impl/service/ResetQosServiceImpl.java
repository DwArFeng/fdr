package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.handler.ResetHandler;
import com.dwarfeng.fdr.stack.handler.Resetter;
import com.dwarfeng.fdr.stack.handler.ResetterHandler;
import com.dwarfeng.fdr.stack.service.ResetQosService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.List;

@Service
public class ResetQosServiceImpl implements ResetQosService {

    private final ResetterHandler resetterHandler;
    private final ResetHandler resetHandler;

    private final ServiceExceptionMapper sem;

    public ResetQosServiceImpl(
            ResetterHandler resetterHandler,
            ResetHandler resetHandler,
            @Qualifier("mapServiceExceptionMapper") ServiceExceptionMapper sem
    ) {
        this.resetterHandler = resetterHandler;
        this.resetHandler = resetHandler;
        this.sem = sem;
    }

    @PreDestroy
    public void dispose() throws Exception {
        resetHandler.stop();
    }

    @Override
    public List<Resetter> all() throws ServiceException {
        try {
            return resetterHandler.all();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("列出在用的全部重置器时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    public boolean isStarted() throws ServiceException {
        try {
            return resetHandler.isStarted();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("判断重置服务是否启动时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    public void start() throws ServiceException {
        try {
            resetHandler.start();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("重置服务启动时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    public void stop() throws ServiceException {
        try {
            resetHandler.stop();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("重置服务停止时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    public void resetRecord() throws ServiceException {
        try {
            resetHandler.resetRecord();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("重置记录功能时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    public void resetMap() throws ServiceException {
        try {
            resetHandler.resetMap();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("重置映射功能时发生异常", LogLevel.WARN, sem, e);
        }
    }
}
