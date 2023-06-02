package com.dwarfeng.fdr.impl.handler.bridge.redis.configuration;

import com.dwarfeng.subgrade.impl.exception.MapServiceExceptionMapper;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class RedisBridgeServiceExceptionMapperConfiguration {

    @Bean("redisBridge.mapServiceExceptionMapper")
    public MapServiceExceptionMapper mapServiceExceptionMapper() {
        Map<Class<? extends Exception>, ServiceException.Code> destination = ServiceExceptionHelper.putDefaultDestination(null);
        return new MapServiceExceptionMapper(destination, ServiceExceptionCodes.UNDEFINE);
    }
}
