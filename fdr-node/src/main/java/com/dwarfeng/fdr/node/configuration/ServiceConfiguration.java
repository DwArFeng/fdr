package com.dwarfeng.fdr.node.configuration;

import com.dwarfeng.fdr.impl.service.operation.FilterInfoCrudOperation;
import com.dwarfeng.fdr.impl.service.operation.PointCrudOperation;
import com.dwarfeng.fdr.impl.service.operation.TriggerInfoCrudOperation;
import com.dwarfeng.fdr.impl.service.operation.WasherInfoCrudOperation;
import com.dwarfeng.fdr.stack.bean.entity.*;
import com.dwarfeng.fdr.stack.cache.FilterSupportCache;
import com.dwarfeng.fdr.stack.cache.MapperSupportCache;
import com.dwarfeng.fdr.stack.cache.TriggerSupportCache;
import com.dwarfeng.fdr.stack.cache.WasherSupportCache;
import com.dwarfeng.fdr.stack.dao.*;
import com.dwarfeng.subgrade.impl.generation.ExceptionKeyGenerator;
import com.dwarfeng.subgrade.impl.service.CustomBatchCrudService;
import com.dwarfeng.subgrade.impl.service.DaoOnlyEntireLookupService;
import com.dwarfeng.subgrade.impl.service.DaoOnlyPresetLookupService;
import com.dwarfeng.subgrade.impl.service.GeneralBatchCrudService;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    private final GenerateConfiguration generateConfiguration;
    private final ServiceExceptionMapper sem;

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
    private final WasherInfoCrudOperation washerInfoCrudOperation;
    private final WasherInfoDao washerInfoDao;
    private final WasherSupportCache washerSupportCache;
    private final WasherSupportDao washerSupportDao;

    @Value("${cache.timeout.entity.filter_support}")
    private long filterSupportTimeout;
    @Value("${cache.timeout.entity.trigger_support}")
    private long triggerSupportTimeout;
    @Value("${cache.timeout.entity.mapper_support}")
    private long mapperSupportTimeout;
    @Value("${cache.timeout.entity.washer_support}")
    private long washerSupportTimeout;

    public ServiceConfiguration(
            GenerateConfiguration generateConfiguration,
            ServiceExceptionMapper sem,
            FilterInfoCrudOperation filterInfoCrudOperation, FilterInfoDao filterInfoDao,
            PointCrudOperation pointCrudOperation, PointDao pointDao,
            TriggerInfoCrudOperation triggerInfoCrudOperation, TriggerInfoDao triggerInfoDao,
            FilterSupportCache filterSupportCache, FilterSupportDao filterSupportDao,
            TriggerSupportCache triggerSupportCache, TriggerSupportDao triggerSupportDao,
            MapperSupportCache mapperSupportCache, MapperSupportDao mapperSupportDao,
            WasherInfoCrudOperation washerInfoCrudOperation, WasherInfoDao washerInfoDao,
            WasherSupportCache washerSupportCache, WasherSupportDao washerSupportDao
    ) {
        this.generateConfiguration = generateConfiguration;
        this.sem = sem;
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
        this.washerInfoCrudOperation = washerInfoCrudOperation;
        this.washerInfoDao = washerInfoDao;
        this.washerSupportCache = washerSupportCache;
        this.washerSupportDao = washerSupportDao;
    }

    @Bean
    public CustomBatchCrudService<LongIdKey, FilterInfo> filterInfoBatchCustomCrudService() {
        return new CustomBatchCrudService<>(
                filterInfoCrudOperation,
                generateConfiguration.snowflakeLongIdKeyGenerator(),
                sem,
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<FilterInfo> filterInfoDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                filterInfoDao,
                sem,
                LogLevel.WARN
        );
    }

    @Bean
    public CustomBatchCrudService<LongIdKey, Point> pointBatchCustomCrudService() {
        return new CustomBatchCrudService<>(
                pointCrudOperation,
                generateConfiguration.snowflakeLongIdKeyGenerator(),
                sem,
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<Point> pointDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                pointDao,
                sem,
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<Point> pointDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                pointDao,
                sem,
                LogLevel.WARN
        );
    }

    @Bean
    public CustomBatchCrudService<LongIdKey, TriggerInfo> triggerInfoBatchCustomCrudService() {
        return new CustomBatchCrudService<>(
                triggerInfoCrudOperation,
                generateConfiguration.snowflakeLongIdKeyGenerator(),
                sem,
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<TriggerInfo> triggerInfoDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                triggerInfoDao,
                sem,
                LogLevel.WARN
        );
    }

    @Bean
    public GeneralBatchCrudService<StringIdKey, FilterSupport> filterSupportGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                filterSupportDao,
                filterSupportCache,
                new ExceptionKeyGenerator<>(),
                sem,
                LogLevel.WARN,
                filterSupportTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<FilterSupport> filterSupportDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                filterSupportDao,
                sem,
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<FilterSupport> filterSupportDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                filterSupportDao,
                sem,
                LogLevel.WARN
        );
    }

    @Bean
    public GeneralBatchCrudService<StringIdKey, TriggerSupport> triggerSupportGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                triggerSupportDao,
                triggerSupportCache,
                new ExceptionKeyGenerator<>(),
                sem,
                LogLevel.WARN,
                triggerSupportTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<TriggerSupport> triggerSupportDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                triggerSupportDao,
                sem,
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<TriggerSupport> triggerSupportDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                triggerSupportDao,
                sem,
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<FilterInfo> filterInfoDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                filterInfoDao,
                sem,
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<TriggerInfo> triggerInfoDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                triggerInfoDao,
                sem,
                LogLevel.WARN
        );
    }

    @Bean
    public GeneralBatchCrudService<StringIdKey, MapperSupport> mapperSupportGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                mapperSupportDao,
                mapperSupportCache,
                new ExceptionKeyGenerator<>(),
                sem,
                LogLevel.WARN,
                mapperSupportTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<MapperSupport> mapperSupportDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                mapperSupportDao,
                sem,
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<MapperSupport> mapperSupportDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                mapperSupportDao,
                sem,
                LogLevel.WARN
        );
    }

    @Bean
    public CustomBatchCrudService<LongIdKey, WasherInfo> washerInfoBatchCustomCrudService() {
        return new CustomBatchCrudService<>(
                washerInfoCrudOperation,
                generateConfiguration.snowflakeLongIdKeyGenerator(),
                sem,
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<WasherInfo> washerInfoDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                washerInfoDao,
                sem,
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<WasherInfo> washerInfoDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                washerInfoDao,
                sem,
                LogLevel.WARN
        );
    }

    @Bean
    public GeneralBatchCrudService<StringIdKey, WasherSupport> washerSupportGeneralBatchCrudService() {
        return new GeneralBatchCrudService<>(
                washerSupportDao,
                washerSupportCache,
                new ExceptionKeyGenerator<>(),
                sem,
                LogLevel.WARN,
                washerSupportTimeout
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<WasherSupport> washerSupportDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                washerSupportDao,
                sem,
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<WasherSupport> washerSupportDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                washerSupportDao,
                sem,
                LogLevel.WARN
        );
    }
}
