package com.dwarfeng.fdr.impl.handler.bridge.hibernate.configuration;

import com.dwarfeng.fdr.impl.handler.bridge.hibernate.bean.HibernateBridgeFilteredData;
import com.dwarfeng.fdr.impl.handler.bridge.hibernate.bean.HibernateBridgeNormalData;
import com.dwarfeng.fdr.impl.handler.bridge.hibernate.bean.HibernateBridgeTriggeredData;
import com.dwarfeng.fdr.impl.handler.bridge.hibernate.dao.HibernateBridgeFilteredDataDao;
import com.dwarfeng.fdr.impl.handler.bridge.hibernate.dao.HibernateBridgeNormalDataDao;
import com.dwarfeng.fdr.impl.handler.bridge.hibernate.dao.HibernateBridgeTriggeredDataDao;
import com.dwarfeng.sfds.api.integration.subgrade.SnowflakeLongIdKeyGenerator;
import com.dwarfeng.sfds.stack.service.GenerateService;
import com.dwarfeng.subgrade.impl.service.DaoOnlyBatchCrudService;
import com.dwarfeng.subgrade.impl.service.DaoOnlyBatchWriteService;
import com.dwarfeng.subgrade.impl.service.DaoOnlyEntireLookupService;
import com.dwarfeng.subgrade.impl.service.DaoOnlyPresetLookupService;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.generation.KeyGenerator;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateBridgeServiceConfiguration {

    private final GenerateService snowflakeGenerateService;

    private final ServiceExceptionMapper sem;

    private final HibernateBridgeNormalDataDao hibernateBridgeNormalDataDao;
    private final HibernateBridgeFilteredDataDao hibernateBridgeFilteredDataDao;
    private final HibernateBridgeTriggeredDataDao hibernateBridgeTriggeredDataDao;

    public HibernateBridgeServiceConfiguration(
            GenerateService snowflakeGenerateService,
            ServiceExceptionMapper sem,
            HibernateBridgeNormalDataDao hibernateBridgeNormalDataDao,
            HibernateBridgeFilteredDataDao hibernateBridgeFilteredDataDao,
            HibernateBridgeTriggeredDataDao hibernateBridgeTriggeredDataDao
    ) {
        this.snowflakeGenerateService = snowflakeGenerateService;
        this.sem = sem;
        this.hibernateBridgeNormalDataDao = hibernateBridgeNormalDataDao;
        this.hibernateBridgeFilteredDataDao = hibernateBridgeFilteredDataDao;
        this.hibernateBridgeTriggeredDataDao = hibernateBridgeTriggeredDataDao;
    }

    @Bean("hibernateBridge.snowflakeLongIdKeyGenerator")
    public KeyGenerator<LongIdKey> snowflakeLongIdKeyGenerator() {
        return new SnowflakeLongIdKeyGenerator(snowflakeGenerateService);
    }

    @Bean
    public DaoOnlyBatchCrudService<LongIdKey, HibernateBridgeNormalData>
    hibernateBridgeNormalDataDaoOnlyBatchCrudService() {
        return new DaoOnlyBatchCrudService<>(
                sem,
                LogLevel.WARN,
                hibernateBridgeNormalDataDao,
                snowflakeLongIdKeyGenerator()
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<HibernateBridgeNormalData>
    hibernateBridgeNormalDataDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                sem,
                LogLevel.WARN,
                hibernateBridgeNormalDataDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<HibernateBridgeNormalData>
    hibernateBridgeNormalDataDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                sem,
                LogLevel.WARN,
                hibernateBridgeNormalDataDao
        );
    }

    @Bean
    public DaoOnlyBatchWriteService<LongIdKey, HibernateBridgeNormalData>
    hibernateBridgeNormalDataDaoOnlyBatchWriteService() {
        return new DaoOnlyBatchWriteService<>(
                sem,
                LogLevel.WARN,
                hibernateBridgeNormalDataDao,
                snowflakeLongIdKeyGenerator()
        );
    }

    @Bean
    public DaoOnlyBatchCrudService<LongIdKey, HibernateBridgeFilteredData>
    hibernateBridgeFilteredDataDaoOnlyBatchCrudService() {
        return new DaoOnlyBatchCrudService<>(
                sem,
                LogLevel.WARN,
                hibernateBridgeFilteredDataDao,
                snowflakeLongIdKeyGenerator()
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<HibernateBridgeFilteredData>
    hibernateBridgeFilteredDataDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                sem,
                LogLevel.WARN,
                hibernateBridgeFilteredDataDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<HibernateBridgeFilteredData>
    hibernateBridgeFilteredDataDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                sem,
                LogLevel.WARN,
                hibernateBridgeFilteredDataDao
        );
    }

    @Bean
    public DaoOnlyBatchWriteService<LongIdKey, HibernateBridgeFilteredData>
    hibernateBridgeFilteredDataDaoOnlyBatchWriteService() {
        return new DaoOnlyBatchWriteService<>(
                sem,
                LogLevel.WARN,
                hibernateBridgeFilteredDataDao,
                snowflakeLongIdKeyGenerator()
        );
    }

    @Bean
    public DaoOnlyBatchCrudService<LongIdKey, HibernateBridgeTriggeredData>
    hibernateBridgeTriggeredDataDaoOnlyBatchCrudService() {
        return new DaoOnlyBatchCrudService<>(
                sem,
                LogLevel.WARN,
                hibernateBridgeTriggeredDataDao,
                snowflakeLongIdKeyGenerator()
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<HibernateBridgeTriggeredData>
    hibernateBridgeTriggeredDataDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                sem,
                LogLevel.WARN,
                hibernateBridgeTriggeredDataDao
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<HibernateBridgeTriggeredData>
    hibernateBridgeTriggeredDataDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                sem,
                LogLevel.WARN,
                hibernateBridgeTriggeredDataDao
        );
    }

    @Bean
    public DaoOnlyBatchWriteService<LongIdKey, HibernateBridgeTriggeredData>
    hibernateBridgeTriggeredDataDaoOnlyBatchWriteService() {
        return new DaoOnlyBatchWriteService<>(
                sem,
                LogLevel.WARN,
                hibernateBridgeTriggeredDataDao,
                snowflakeLongIdKeyGenerator()
        );
    }
}
