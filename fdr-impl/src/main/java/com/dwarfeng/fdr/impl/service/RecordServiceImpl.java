package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.dto.RecordInfo;
import com.dwarfeng.fdr.stack.handler.RecordHandler;
import com.dwarfeng.fdr.stack.service.RecordService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

@Service
public class RecordServiceImpl implements RecordService {

    private final RecordHandler recordHandler;
    private final ServiceExceptionMapper sem;

    public RecordServiceImpl(
            RecordHandler recordHandler,
            ServiceExceptionMapper sem
    ) {
        this.recordHandler = recordHandler;
        this.sem = sem;
    }

    @BehaviorAnalyse
    @Override
    public void record(RecordInfo recordInfo) throws ServiceException {
        try {
            recordHandler.record(recordInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("记录数据时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
