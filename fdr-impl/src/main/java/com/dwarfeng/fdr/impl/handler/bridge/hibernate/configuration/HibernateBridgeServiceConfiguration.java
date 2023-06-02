package com.dwarfeng.fdr.impl.handler.bridge.hibernate.configuration;

import com.dwarfeng.fdr.impl.handler.bridge.hibernate.bean.HibernateBridgeFilteredData;
import com.dwarfeng.fdr.impl.handler.bridge.hibernate.bean.HibernateBridgeNormalData;
import com.dwarfeng.fdr.impl.handler.bridge.hibernate.bean.HibernateBridgeTriggeredData;
import com.dwarfeng.fdr.impl.handler.bridge.hibernate.dao.HibernateBridgeFilteredDataDao;
import com.dwarfeng.fdr.impl.handler.bridge.hibernate.dao.HibernateBridgeNormalDataDao;
import com.dwarfeng.fdr.impl.handler.bridge.hibernate.dao.HibernateBridgeTriggeredDataDao;
import com.dwarfeng.sfds.api.integration.subgrade.SnowFlakeLongIdKeyFetcher;
import com.dwarfeng.subgrade.impl.service.DaoOnlyBatchCrudService;
import com.dwarfeng.subgrade.impl.service.DaoOnlyBatchWriteService;
import com.dwarfeng.subgrade.impl.service.DaoOnlyEntireLookupService;
import com.dwarfeng.subgrade.impl.service.DaoOnlyPresetLookupService;
import com.dwarfeng.subgrade.stack.bean.key.KeyFetcher;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateBridgeServiceConfiguration {

    private final HibernateBridgeServiceExceptionMapperConfiguration serviceExceptionMapperConfiguration;

    private final HibernateBridgeNormalDataDao hibernateBridgeNormalDataDao;
    private final HibernateBridgeFilteredDataDao hibernateBridgeFilteredDataDao;
    private final HibernateBridgeTriggeredDataDao hibernateBridgeTriggeredDataDao;

    public HibernateBridgeServiceConfiguration(
            HibernateBridgeServiceExceptionMapperConfiguration serviceExceptionMapperConfiguration,
            HibernateBridgeNormalDataDao hibernateBridgeNormalDataDao,
            HibernateBridgeFilteredDataDao hibernateBridgeFilteredDataDao,
            HibernateBridgeTriggeredDataDao hibernateBridgeTriggeredDataDao
    ) {
        this.serviceExceptionMapperConfiguration = serviceExceptionMapperConfiguration;
        this.hibernateBridgeNormalDataDao = hibernateBridgeNormalDataDao;
        this.hibernateBridgeFilteredDataDao = hibernateBridgeFilteredDataDao;
        this.hibernateBridgeTriggeredDataDao = hibernateBridgeTriggeredDataDao;
    }

    @Bean("hibernateBridge.longIdKeyKeyFetcher")
    public KeyFetcher<LongIdKey> longIdKeyKeyFetcher() {
        return new SnowFlakeLongIdKeyFetcher();
    }

    @Bean
    public DaoOnlyBatchCrudService<LongIdKey, HibernateBridgeNormalData>
    hibernateBridgeNormalDataDaoOnlyBatchCrudService() {
        return new DaoOnlyBatchCrudService<>(
                hibernateBridgeNormalDataDao,
                longIdKeyKeyFetcher(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<HibernateBridgeNormalData>
    hibernateBridgeNormalDataDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                hibernateBridgeNormalDataDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<HibernateBridgeNormalData>
    hibernateBridgeNormalDataDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                hibernateBridgeNormalDataDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyBatchWriteService<LongIdKey, HibernateBridgeNormalData>
    hibernateBridgeNormalDataDaoOnlyBatchWriteService() {
        return new DaoOnlyBatchWriteService<>(
                hibernateBridgeNormalDataDao,
                longIdKeyKeyFetcher(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyBatchCrudService<LongIdKey, HibernateBridgeFilteredData>
    hibernateBridgeFilteredDataDaoOnlyBatchCrudService() {
        return new DaoOnlyBatchCrudService<>(
                hibernateBridgeFilteredDataDao,
                longIdKeyKeyFetcher(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<HibernateBridgeFilteredData>
    hibernateBridgeFilteredDataDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                hibernateBridgeFilteredDataDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<HibernateBridgeFilteredData>
    hibernateBridgeFilteredDataDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                hibernateBridgeFilteredDataDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyBatchWriteService<LongIdKey, HibernateBridgeFilteredData>
    hibernateBridgeFilteredDataDaoOnlyBatchWriteService() {
        return new DaoOnlyBatchWriteService<>(
                hibernateBridgeFilteredDataDao,
                longIdKeyKeyFetcher(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyBatchCrudService<LongIdKey, HibernateBridgeTriggeredData>
    hibernateBridgeTriggeredDataDaoOnlyBatchCrudService() {
        return new DaoOnlyBatchCrudService<>(
                hibernateBridgeTriggeredDataDao,
                longIdKeyKeyFetcher(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<HibernateBridgeTriggeredData>
    hibernateBridgeTriggeredDataDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                hibernateBridgeTriggeredDataDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<HibernateBridgeTriggeredData>
    hibernateBridgeTriggeredDataDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                hibernateBridgeTriggeredDataDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyBatchWriteService<LongIdKey, HibernateBridgeTriggeredData>
    hibernateBridgeTriggeredDataDaoOnlyBatchWriteService() {
        return new DaoOnlyBatchWriteService<>(
                hibernateBridgeTriggeredDataDao,
                longIdKeyKeyFetcher(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }
}
