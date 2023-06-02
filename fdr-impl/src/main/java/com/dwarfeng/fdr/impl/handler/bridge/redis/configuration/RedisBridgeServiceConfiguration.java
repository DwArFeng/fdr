package com.dwarfeng.fdr.impl.handler.bridge.redis.configuration;

import com.dwarfeng.fdr.impl.handler.bridge.redis.bean.RedisBridgeFilteredData;
import com.dwarfeng.fdr.impl.handler.bridge.redis.bean.RedisBridgeNormalData;
import com.dwarfeng.fdr.impl.handler.bridge.redis.bean.RedisBridgeTriggeredData;
import com.dwarfeng.fdr.impl.handler.bridge.redis.dao.RedisBridgeFilteredDataDao;
import com.dwarfeng.fdr.impl.handler.bridge.redis.dao.RedisBridgeNormalDataDao;
import com.dwarfeng.fdr.impl.handler.bridge.redis.dao.RedisBridgeTriggeredDataDao;
import com.dwarfeng.sfds.api.integration.subgrade.SnowFlakeLongIdKeyFetcher;
import com.dwarfeng.subgrade.impl.service.DaoOnlyBatchCrudService;
import com.dwarfeng.subgrade.stack.bean.key.KeyFetcher;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisBridgeServiceConfiguration {

    private final RedisBridgeServiceExceptionMapperConfiguration serviceExceptionMapperConfiguration;

    private final RedisBridgeNormalDataDao redisBridgeNormalDataDao;
    private final RedisBridgeFilteredDataDao redisBridgeFilteredDataDao;
    private final RedisBridgeTriggeredDataDao redisBridgeTriggeredDataDao;

    public RedisBridgeServiceConfiguration(
            RedisBridgeServiceExceptionMapperConfiguration serviceExceptionMapperConfiguration,
            RedisBridgeNormalDataDao redisBridgeNormalDataDao,
            RedisBridgeFilteredDataDao redisBridgeFilteredDataDao,
            RedisBridgeTriggeredDataDao redisBridgeTriggeredDataDao
    ) {
        this.serviceExceptionMapperConfiguration = serviceExceptionMapperConfiguration;
        this.redisBridgeNormalDataDao = redisBridgeNormalDataDao;
        this.redisBridgeFilteredDataDao = redisBridgeFilteredDataDao;
        this.redisBridgeTriggeredDataDao = redisBridgeTriggeredDataDao;
    }

    @Bean("redisBridge.longIdKeyKeyFetcher")
    public KeyFetcher<LongIdKey> longIdKeyKeyFetcher() {
        return new SnowFlakeLongIdKeyFetcher();
    }

    @Bean
    public DaoOnlyBatchCrudService<LongIdKey, RedisBridgeNormalData>
    redisBridgeNormalDataDaoOnlyBatchCrudService() {
        return new DaoOnlyBatchCrudService<>(
                redisBridgeNormalDataDao,
                longIdKeyKeyFetcher(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyBatchCrudService<LongIdKey, RedisBridgeFilteredData>
    redisBridgeFilteredDataDaoOnlyBatchCrudService() {
        return new DaoOnlyBatchCrudService<>(
                redisBridgeFilteredDataDao,
                longIdKeyKeyFetcher(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyBatchCrudService<LongIdKey, RedisBridgeTriggeredData>
    redisBridgeTriggeredDataDaoOnlyBatchCrudService() {
        return new DaoOnlyBatchCrudService<>(
                redisBridgeTriggeredDataDao,
                longIdKeyKeyFetcher(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }
}
