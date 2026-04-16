package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.handler.RecordMemoryHandler;
import com.dwarfeng.fdr.stack.service.RecordMemoryQosService;
import com.dwarfeng.fdr.stack.struct.RecordMemory;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordMemoryQosServiceImpl implements RecordMemoryQosService {

    private final RecordMemoryHandler recordMemoryHandler;
    private final ServiceExceptionMapper sem;

    public RecordMemoryQosServiceImpl(RecordMemoryHandler recordMemoryHandler, ServiceExceptionMapper sem) {
        this.recordMemoryHandler = recordMemoryHandler;
        this.sem = sem;
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    public List<RecordMemory> lookup(LongIdKey pointKey) throws ServiceException {
        try {
            return recordMemoryHandler.lookup(pointKey);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查询指定点位的记录记忆时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    @BehaviorAnalyse
    public void remove(LongIdKey pointKey) throws ServiceException {
        try {
            recordMemoryHandler.remove(pointKey);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("移除指定的点位对应的记录记忆时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    @BehaviorAnalyse
    public void clear() throws ServiceException {
        try {
            recordMemoryHandler.clear();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("清除记录记忆时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
