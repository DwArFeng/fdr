package com.dwarfeng.fdr.node.record.configuration;

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
        destination.put(TriggerException.class, ServiceExceptionCodes.TRIGGER_FAILED);
        destination.put(TriggerMakeException.class, ServiceExceptionCodes.TRIGGER_MAKE_FAILED);
        destination.put(UnsupportedTriggerTypeException.class, ServiceExceptionCodes.TRIGGER_TYPE_UNSUPPORTED);
        destination.put(PointNotExistsException.class, ServiceExceptionCodes.POINT_NOT_EXISTS);
        destination.put(RecordStoppedException.class, ServiceExceptionCodes.RECORD_HANDLER_STOPPED);
        destination.put(ConsumeStoppedException.class, ServiceExceptionCodes.CONSUME_HANDLER_STOPPED);
        destination.put(MapperException.class, ServiceExceptionCodes.MAPPER_FAILED);
        destination.put(MapperMakeException.class, ServiceExceptionCodes.MAPPER_MAKE_FAILED);
        destination.put(UnsupportedMapperTypeException.class, ServiceExceptionCodes.MAPPER_TYPE_UNSUPPORTED);
        destination.put(PersistenceDisabledException.class, ServiceExceptionCodes.PERSISTENCE_DISABLED);
        destination.put(RealtimeDisabledException.class, ServiceExceptionCodes.REALTIME_DISABLED);
        destination.put(MappingLookupException.class, ServiceExceptionCodes.MAPPING_LOOKUP_FAILED);
        destination.put(MappingLookupSessionCanceledException.class, ServiceExceptionCodes.MAPPING_LOOKUP_SESSION_CANCELED);
        destination.put(MappingLookupSessionNotExistsException.class, ServiceExceptionCodes.MAPPING_LOOKUP_SESSION_NOT_EXISTS);
        destination.put(MappingLookupTimeoutException.class, ServiceExceptionCodes.MAPPING_LOOKUP_TIMEOUT);
        return new MapServiceExceptionMapper(destination, com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes.UNDEFINE);
    }
}
