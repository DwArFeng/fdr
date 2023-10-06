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
    public MapServiceExceptionMapper serviceExceptionMapper() {
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
        destination.put(WasherException.class, ServiceExceptionCodes.WASHER_FAILED);
        destination.put(WasherMakeException.class, ServiceExceptionCodes.WASHER_MAKE_FAILED);
        destination.put(UnsupportedWasherTypeException.class, ServiceExceptionCodes.WASHER_TYPE_UNSUPPORTED);
        destination.put(WasherExecutionException.class, ServiceExceptionCodes.WASHER_EXECUTION_FAILED);
        destination.put(KeepException.class, ServiceExceptionCodes.KEEP_FAILED);
        destination.put(UpdateException.class, ServiceExceptionCodes.UPDATE_FAILED);
        destination.put(LatestException.class, ServiceExceptionCodes.LATEST_FAILED);
        destination.put(PersistException.class, ServiceExceptionCodes.PERSIST_FAILED);
        destination.put(RecordException.class, ServiceExceptionCodes.RECORD_FAILED);
        destination.put(LookupException.class, ServiceExceptionCodes.LOOKUP_FAILED);
        destination.put(NativeQueryException.class, ServiceExceptionCodes.NATIVE_QUERY_FAILED);
        destination.put(QueryException.class, ServiceExceptionCodes.QUERY_FAILED);
        destination.put(KeeperNotSupportedException.class, ServiceExceptionCodes.KEEPER_NOT_SUPPORTED);
        destination.put(PersisterNotSupportedException.class, ServiceExceptionCodes.PERSISTER_NOT_SUPPORTED);
        return new MapServiceExceptionMapper(destination, com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes.UNDEFINE);
    }
}
