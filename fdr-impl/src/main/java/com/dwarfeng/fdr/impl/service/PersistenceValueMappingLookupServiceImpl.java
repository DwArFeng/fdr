package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.dcti.stack.bean.dto.TimedValue;
import com.dwarfeng.fdr.stack.bean.dto.MappingLookupInfo;
import com.dwarfeng.fdr.stack.bean.dto.MappingLookupSessionInfo;
import com.dwarfeng.fdr.stack.handler.PersistenceValueMappingLookupHandler;
import com.dwarfeng.fdr.stack.service.PersistenceValueMappingLookupService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersistenceValueMappingLookupServiceImpl implements PersistenceValueMappingLookupService {

    private final PersistenceValueMappingLookupHandler handler;

    private final ServiceExceptionMapper sem;

    public PersistenceValueMappingLookupServiceImpl(
            PersistenceValueMappingLookupHandler handler, ServiceExceptionMapper sem
    ) {
        this.handler = handler;
        this.sem = sem;
    }

    @Override
    @BehaviorAnalyse
    public List<TimedValue> mappingLookup(MappingLookupInfo mappingLookupInfo) throws ServiceException {
        try {
            return handler.mappingLookup(mappingLookupInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("映射查询时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    @BehaviorAnalyse
    public LongIdKey mappingLookupAsync(MappingLookupInfo mappingLookupInfo) throws ServiceException {
        try {
            return handler.mappingLookupAsync(mappingLookupInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("异步式执行映射查询时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    @BehaviorAnalyse
    public void cancel(LongIdKey sessionKey) throws ServiceException {
        try {
            handler.cancel(sessionKey);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("取消异步式会话时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    @BehaviorAnalyse
    public List<TimedValue> getResult(LongIdKey sessionKey) throws ServiceException {
        try {
            return handler.getResult(sessionKey);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("异步式获取查询结果时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    @BehaviorAnalyse
    public List<TimedValue> getResult(LongIdKey sessionKey, long timeout) throws ServiceException {
        try {
            return handler.getResult(sessionKey, timeout);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("异步式获取查询结果时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    @BehaviorAnalyse
    public MappingLookupSessionInfo getSessionInfo(LongIdKey sessionKey) throws ServiceException {
        try {
            return handler.getSessionInfo(sessionKey);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("获取指定会话信息时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
