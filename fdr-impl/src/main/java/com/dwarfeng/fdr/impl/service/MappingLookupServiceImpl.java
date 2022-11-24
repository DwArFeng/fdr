package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.dcti.stack.bean.dto.TimedValue;
import com.dwarfeng.fdr.stack.bean.dto.MappingLookupInfo;
import com.dwarfeng.fdr.stack.handler.FilteredValueMappingLookupHandler;
import com.dwarfeng.fdr.stack.handler.PersistenceValueMappingLookupHandler;
import com.dwarfeng.fdr.stack.handler.TriggeredValueMappingLookupHandler;
import com.dwarfeng.fdr.stack.service.MappingLookupService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Deprecated
public class MappingLookupServiceImpl implements MappingLookupService {

    private final PersistenceValueMappingLookupHandler persistenceValueMappingLookupHandler;
    private final FilteredValueMappingLookupHandler filteredValueMappingLookupHandler;
    private final TriggeredValueMappingLookupHandler triggeredValueMappingLookupHandler;

    private final ServiceExceptionMapper sem;

    public MappingLookupServiceImpl(
            PersistenceValueMappingLookupHandler persistenceValueMappingLookupHandler,
            FilteredValueMappingLookupHandler filteredValueMappingLookupHandler,
            TriggeredValueMappingLookupHandler triggeredValueMappingLookupHandler,
            ServiceExceptionMapper sem
    ) {
        this.persistenceValueMappingLookupHandler = persistenceValueMappingLookupHandler;
        this.filteredValueMappingLookupHandler = filteredValueMappingLookupHandler;
        this.triggeredValueMappingLookupHandler = triggeredValueMappingLookupHandler;
        this.sem = sem;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Deprecated
    public List<TimedValue> mappingPersistenceValue(
            String mapperType, LongIdKey pointKey, Date startDate, Date endDate, Object[] mapperArgs
    ) throws ServiceException {
        try {
            return persistenceValueMappingLookupHandler.mappingLookup(new MappingLookupInfo(
                    mapperType, pointKey, startDate, endDate, mapperArgs
            ));
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询并映射持久数据值时发生异常",
                    LogLevel.WARN, sem, e
            );
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Deprecated
    public List<TimedValue> mappingFilteredValue(
            String mapperType, LongIdKey pointKey, Date startDate, Date endDate, Object[] mapperArgs
    ) throws ServiceException {
        try {
            return filteredValueMappingLookupHandler.mappingLookup(new MappingLookupInfo(
                    mapperType, pointKey, startDate, endDate, mapperArgs
            ));
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询并映射被过滤数据值时发生异常",
                    LogLevel.WARN, sem, e
            );
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Deprecated
    public List<TimedValue> mappingTriggeredValue(
            String mapperType, LongIdKey pointKey, Date startDate, Date endDate, Object[] mapperArgs
    ) throws ServiceException {
        try {
            return triggeredValueMappingLookupHandler.mappingLookup(new MappingLookupInfo(
                    mapperType, pointKey, startDate, endDate, mapperArgs
            ));
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询并映射被触发数据值时发生异常",
                    LogLevel.WARN, sem, e
            );
        }
    }
}
