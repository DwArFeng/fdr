package com.dwarfeng.fdr.impl.configuration;

import com.dwarfeng.fdr.sdk.util.ServiceExceptionCodes;
import com.dwarfeng.fdr.stack.exception.*;
import com.dwarfeng.subgrade.impl.exception.MapServiceExceptionMapper;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class ServiceExceptionMapperConfiguration {

    @Bean
    public MapServiceExceptionMapper mapServiceExceptionMapper() {
        Map<Class<? extends Exception>, ServiceException.Code> destination = ServiceExceptionHelper.putDefaultDestination(null);
        destination.put(FilterException.class, ServiceExceptionCodes.FILTER_FAILED);
        destination.put(FilterMakeException.class, ServiceExceptionCodes.FILTER_MAKE_FAILED);
        destination.put(UnsupportedFilterTypeException.class, ServiceExceptionCodes.FILTER_TYPE_UNSUPPORTED);
        destination.put(FilterExecutionException.class, ServiceExceptionCodes.FILTER_EXECUTION_FAILED);
        destination.put(TriggerException.class, ServiceExceptionCodes.TRIGGER_FAILED);
        destination.put(TriggerMakeException.class, ServiceExceptionCodes.TRIGGER_MAKE_FAILED);
        destination.put(UnsupportedTriggerTypeException.class, ServiceExceptionCodes.TRIGGER_TYPE_UNSUPPORTED);
        destination.put(TriggerExecutionException.class, ServiceExceptionCodes.TRIGGER_EXECUTION_FAILED);
        destination.put(PointNotExistsException.class, ServiceExceptionCodes.POINT_NOT_EXISTS);
        destination.put(RecordStoppedException.class, ServiceExceptionCodes.RECORD_HANDLER_STOPPED);
        destination.put(ConsumeStoppedException.class, ServiceExceptionCodes.CONSUME_HANDLER_STOPPED);
        destination.put(MapperException.class, ServiceExceptionCodes.MAPPER_FAILED);
        destination.put(MapperMakeException.class, ServiceExceptionCodes.MAPPER_MAKE_FAILED);
        destination.put(UnsupportedMapperTypeException.class, ServiceExceptionCodes.MAPPER_TYPE_UNSUPPORTED);
        destination.put(MapperExecutionException.class, ServiceExceptionCodes.MAPPER_EXECUTION_FAILED);
        destination.put(FunctionNotSupportedException.class, ServiceExceptionCodes.FUNCTION_NOT_SUPPORTED);
        destination.put(LatestNotSupportedException.class, ServiceExceptionCodes.LATEST_NOT_SUPPORTED);
        destination.put(LookupNotSupportedException.class, ServiceExceptionCodes.LOOKUP_NOT_SUPPORTED);
        destination.put(QueryNotSupportedException.class, ServiceExceptionCodes.QUERY_NOT_SUPPORTED);
        return new MapServiceExceptionMapper(destination, com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes.UNDEFINE);
    }
}
