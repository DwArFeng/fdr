package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.handler.SupportHandler;
import com.dwarfeng.fdr.stack.service.SupportQosService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

@Service
public class SupportQosServiceImpl implements SupportQosService {

    private final SupportHandler supportHandler;

    private final ServiceExceptionMapper sem;

    public SupportQosServiceImpl(SupportHandler supportHandler, ServiceExceptionMapper sem) {
        this.supportHandler = supportHandler;
        this.sem = sem;
    }

    @Override
    public void resetFilter() throws ServiceException {
        try {
            supportHandler.resetFilter();
        } catch (HandlerException e) {
            throw ServiceExceptionHelper.logParse("重置过滤器时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void resetWasher() throws ServiceException {
        try {
            supportHandler.resetWasher();
        } catch (HandlerException e) {
            throw ServiceExceptionHelper.logParse("重置清洗器时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void resetTrigger() throws ServiceException {
        try {
            supportHandler.resetTrigger();
        } catch (HandlerException e) {
            throw ServiceExceptionHelper.logParse("重置触发器时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void resetMapper() throws ServiceException {
        try {
            supportHandler.resetMapper();
        } catch (HandlerException e) {
            throw ServiceExceptionHelper.logParse("重置映射器时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
