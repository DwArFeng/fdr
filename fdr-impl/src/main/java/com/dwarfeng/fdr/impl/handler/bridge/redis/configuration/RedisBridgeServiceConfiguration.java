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
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisBridgeServiceConfiguration {

    private final ServiceExceptionMapper sem;

    private final RedisBridgeNormalDataDao redisBridgeNormalDataDao;
    private final RedisBridgeFilteredDataDao redisBridgeFilteredDataDao;
    private final RedisBridgeTriggeredDataDao redisBridgeTriggeredDataDao;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public RedisBridgeServiceConfiguration(
            ServiceExceptionMapper sem,
            RedisBridgeNormalDataDao redisBridgeNormalDataDao,
            RedisBridgeFilteredDataDao redisBridgeFilteredDataDao,
            RedisBridgeTriggeredDataDao redisBridgeTriggeredDataDao
    ) {
        this.sem = sem;
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
                sem,
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyBatchCrudService<LongIdKey, RedisBridgeFilteredData>
    redisBridgeFilteredDataDaoOnlyBatchCrudService() {
        return new DaoOnlyBatchCrudService<>(
                redisBridgeFilteredDataDao,
                longIdKeyKeyFetcher(),
                sem,
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyBatchCrudService<LongIdKey, RedisBridgeTriggeredData>
    redisBridgeTriggeredDataDaoOnlyBatchCrudService() {
        return new DaoOnlyBatchCrudService<>(
                redisBridgeTriggeredDataDao,
                longIdKeyKeyFetcher(),
                sem,
                LogLevel.WARN
        );
    }
}
