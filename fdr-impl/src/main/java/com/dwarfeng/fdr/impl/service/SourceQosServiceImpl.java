package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.handler.Source;
import com.dwarfeng.fdr.stack.handler.SourceHandler;
import com.dwarfeng.fdr.stack.service.SourceQosService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SourceQosServiceImpl implements SourceQosService {

    private final SourceHandler sourceHandler;

    private final ServiceExceptionMapper sem;

    public SourceQosServiceImpl(SourceHandler sourceHandler, ServiceExceptionMapper sem) {
        this.sourceHandler = sourceHandler;
        this.sem = sem;
    }

    @Override
    public List<Source> all() throws ServiceException {
        try {
            return sourceHandler.all();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("列出在用的全部数据源时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
