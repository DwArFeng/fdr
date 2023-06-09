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
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateBridgeServiceConfiguration {

    private final ServiceExceptionMapper sem;

    private final HibernateBridgeNormalDataDao hibernateBridgeNormalDataDao;
    private final HibernateBridgeFilteredDataDao hibernateBridgeFilteredDataDao;
    private final HibernateBridgeTriggeredDataDao hibernateBridgeTriggeredDataDao;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public HibernateBridgeServiceConfiguration(
            ServiceExceptionMapper sem,
            HibernateBridgeNormalDataDao hibernateBridgeNormalDataDao,
            HibernateBridgeFilteredDataDao hibernateBridgeFilteredDataDao,
            HibernateBridgeTriggeredDataDao hibernateBridgeTriggeredDataDao
    ) {
        this.sem = sem;
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
                sem,
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<HibernateBridgeNormalData>
    hibernateBridgeNormalDataDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                hibernateBridgeNormalDataDao,
                sem,
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<HibernateBridgeNormalData>
    hibernateBridgeNormalDataDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                hibernateBridgeNormalDataDao,
                sem,
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyBatchWriteService<LongIdKey, HibernateBridgeNormalData>
    hibernateBridgeNormalDataDaoOnlyBatchWriteService() {
        return new DaoOnlyBatchWriteService<>(
                hibernateBridgeNormalDataDao,
                longIdKeyKeyFetcher(),
                sem,
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyBatchCrudService<LongIdKey, HibernateBridgeFilteredData>
    hibernateBridgeFilteredDataDaoOnlyBatchCrudService() {
        return new DaoOnlyBatchCrudService<>(
                hibernateBridgeFilteredDataDao,
                longIdKeyKeyFetcher(),
                sem,
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<HibernateBridgeFilteredData>
    hibernateBridgeFilteredDataDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                hibernateBridgeFilteredDataDao,
                sem,
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<HibernateBridgeFilteredData>
    hibernateBridgeFilteredDataDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                hibernateBridgeFilteredDataDao,
                sem,
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyBatchWriteService<LongIdKey, HibernateBridgeFilteredData>
    hibernateBridgeFilteredDataDaoOnlyBatchWriteService() {
        return new DaoOnlyBatchWriteService<>(
                hibernateBridgeFilteredDataDao,
                longIdKeyKeyFetcher(),
                sem,
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyBatchCrudService<LongIdKey, HibernateBridgeTriggeredData>
    hibernateBridgeTriggeredDataDaoOnlyBatchCrudService() {
        return new DaoOnlyBatchCrudService<>(
                hibernateBridgeTriggeredDataDao,
                longIdKeyKeyFetcher(),
                sem,
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<HibernateBridgeTriggeredData>
    hibernateBridgeTriggeredDataDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                hibernateBridgeTriggeredDataDao,
                sem,
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<HibernateBridgeTriggeredData>
    hibernateBridgeTriggeredDataDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                hibernateBridgeTriggeredDataDao,
                sem,
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyBatchWriteService<LongIdKey, HibernateBridgeTriggeredData>
    hibernateBridgeTriggeredDataDaoOnlyBatchWriteService() {
        return new DaoOnlyBatchWriteService<>(
                hibernateBridgeTriggeredDataDao,
                longIdKeyKeyFetcher(),
                sem,
                LogLevel.WARN
        );
    }
}