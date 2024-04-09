package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.dcti.stack.bean.dto.DataInfo;
import com.dwarfeng.fdr.stack.handler.RecordHandler;
import com.dwarfeng.fdr.stack.service.RecordService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordHandler recordHandler;
    @Autowired
    private ServiceExceptionMapper sem;

    @Override
    @BehaviorAnalyse
    public void record(String message) throws ServiceException {
        try {
            recordHandler.record(message);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("记录数据信息时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    @BehaviorAnalyse
    public void record(@NotNull DataInfo dataInfo) throws ServiceException {
        try {
            recordHandler.record(dataInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("记录数据信息时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
