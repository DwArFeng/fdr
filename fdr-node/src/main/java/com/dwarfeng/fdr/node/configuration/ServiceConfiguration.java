package com.dwarfeng.fdr.node.configuration;

import com.dwarfeng.fdr.impl.service.operation.FilterInfoCrudOperation;
import com.dwarfeng.fdr.impl.service.operation.PointCrudOperation;
import com.dwarfeng.fdr.impl.service.operation.TriggerInfoCrudOperation;
import com.dwarfeng.fdr.stack.bean.entity.*;
import com.dwarfeng.fdr.stack.cache.FilterSupportCache;
import com.dwarfeng.fdr.stack.cache.MapperSupportCache;
import com.dwarfeng.fdr.stack.cache.TriggerSupportCache;
import com.dwarfeng.fdr.stack.dao.*;
import com.dwarfeng.sfds.api.integration.subgrade.SnowFlakeLongIdKeyFetcher;
import com.dwarfeng.subgrade.impl.bean.key.ExceptionKeyFetcher;
import com.dwarfeng.subgrade.impl.service.CustomBatchCrudService;
import com.dwarfeng.subgrade.impl.service.DaoOnlyEntireLookupService;
import com.dwarfeng.subgrade.impl.service.DaoOnlyPresetLookupService;
import com.dwarfeng.subgrade.impl.service.GeneralBatchCrudService;
import com.dwarfeng.subgrade.stack.bean.key.KeyFetcher;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    private final ServiceExceptionMapperConfiguration serviceExceptionMapperConfiguration;

    private final FilterInfoCrudOperation filterInfoCrudOperation;
    private final FilterInfoDao filterInfoDao;
    private final PointCrudOperation pointCrudOperation;
    private final PointDao pointDao;
    private final TriggerInfoCrudOperation triggerInfoCrudOperation;
    private final TriggerInfoDao triggerInfoDao;
    private final FilterSupportCache filterSupportCache;
    private final FilterSupportDao filterSupportDao;
    private final TriggerSupportCache triggerSupportCache;
    private final TriggerSupportDao triggerSupportDao;
    private final MapperSupportCache mapperSupportCache;
    private final MapperSupportDao mapperSupportDao;

    @Value("${cache.timeout.entity.filter_support}")
    private long filterSupportTimeout;
    @Value("${cache.timeout.entity.trigger_support}")
    private long triggerSupportTimeout;
    @Value("${cache.timeout.entity.mapper_support}")
    private long mapperSupportTimeout;

    public ServiceConfiguration(
            ServiceExceptionMapperConfiguration serviceExceptionMapperConfiguration,
            FilterInfoCrudOperation filterInfoCrudOperation, FilterInfoDao filterInfoDao,
            PointCrudOperation pointCrudOperation, PointDao pointDao,
            TriggerInfoCrudOperation triggerInfoCrudOperation, TriggerInfoDao triggerInfoDao,
            FilterSupportCache filterSupportCache, FilterSupportDao filterSupportDao,
            TriggerSupportCache triggerSupportCache, TriggerSupportDao triggerSupportDao,
            MapperSupportCache mapperSupportCache, MapperSupportDao mapperSupportDao
    ) {
        this.serviceExceptionMapperConfiguration = serviceExceptionMapperConfiguration;
        this.filterInfoCrudOperation = filterInfoCrudOperation;
        this.filterInfoDao = filterInfoDao;
        this.pointCrudOperation = pointCrudOperation;
        this.pointDao = pointDao;
        this.triggerInfoCrudOperation = triggerInfoCrudOperation;
        this.triggerInfoDao = triggerInfoDao;
        this.filterSupportCache = filterSupportCache;
        this.filterSupportDao = filterSupportDao;
        this.triggerSupportCache = triggerSupportCache;
        this.triggerSupportDao = triggerSupportDao;
        this.mapperSupportCache = mapperSupportCache;
        this.mapperSupportDao = mapperSupportDao;
    }


    @Bean
    public KeyFetcher<LongIdKey> longIdKeyKeyFetcher() {
        return new SnowFlakeLongIdKeyFetcher();
    }

    @Bean
    public CustomBatchCrudService<LongIdKey, FilterInfo> filterInfoBatchCustomCrudService() {
        return new CustomBatchCrudService<>(
                filterInfoCrudOperation,
                longIdKeyKeyFetcher(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<FilterInfo> filterInfoDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                filterInfoDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public CustomBatchCrudService<LongIdKey, Point> pointBatchCustomCrudService() {
        return new CustomBatchCrudService<>(
                pointCrudOperation,
                longIdKeyKeyFetcher(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<Point> pointDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                pointDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<Point> pointDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                pointDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public CustomBatchCrudService<LongIdKey, TriggerInfo> triggerInfoBatchCustomCrudService() {
        return new CustomBatchCrudService<>(
                triggerInfoCrudOperation,
                longIdKeyKeyFetcher(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<TriggerInfo> triggerInfoDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                triggerInfoDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public GeneralBatchCrudService<StringIdKey, FilterSupport> filterSupportGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                filterSupportDao,
                filterSupportCache,
                new ExceptionKeyFetcher<>(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                filterSupportTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<FilterSupport> filterSupportDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                filterSupportDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<FilterSupport> filterSupportDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                filterSupportDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public GeneralBatchCrudService<StringIdKey, TriggerSupport> triggerSupportGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                triggerSupportDao,
                triggerSupportCache,
                new ExceptionKeyFetcher<>(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                triggerSupportTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<TriggerSupport> triggerSupportDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                triggerSupportDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<TriggerSupport> triggerSupportDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                triggerSupportDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<FilterInfo> filterInfoDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                filterInfoDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<TriggerInfo> triggerInfoDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                triggerInfoDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public GeneralBatchCrudService<StringIdKey, MapperSupport> mapperSupportGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                mapperSupportDao,
                mapperSupportCache,
                new ExceptionKeyFetcher<>(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN,
                mapperSupportTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<MapperSupport> mapperSupportDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                mapperSupportDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<MapperSupport> mapperSupportDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                mapperSupportDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }
}
