package com.dwarfeng.fdr.node.inspect.configuration;

import com.dwarfeng.fdr.impl.service.operation.*;
import com.dwarfeng.fdr.stack.bean.entity.*;
import com.dwarfeng.fdr.stack.cache.FilterSupportCache;
import com.dwarfeng.fdr.stack.cache.MapperSupportCache;
import com.dwarfeng.fdr.stack.cache.TriggerSupportCache;
import com.dwarfeng.fdr.stack.dao.*;
import com.dwarfeng.sfds.api.integration.subgrade.SnowFlakeLongIdKeyFetcher;
import com.dwarfeng.subgrade.impl.bean.key.ExceptionKeyFetcher;
import com.dwarfeng.subgrade.impl.service.*;
import com.dwarfeng.subgrade.stack.bean.key.KeyFetcher;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    @Autowired
    private ServiceExceptionMapperConfiguration serviceExceptionMapperConfiguration;

    @Bean
    public KeyFetcher<LongIdKey> longIdKeyKeyFetcher() {
        return new SnowFlakeLongIdKeyFetcher();
    }

    @Autowired
    private FilteredValueCrudOperation filteredValueCrudOperation;
    @Autowired
    private FilteredValueDao filteredValueDao;
    @Autowired
    private FilterInfoCrudOperation filterInfoCrudOperation;
    @Autowired
    private FilterInfoDao filterInfoDao;
    @Autowired
    private PersistenceValueCrudOperation persistenceValueCrudOperation;
    @Autowired
    private PersistenceValueDao persistenceValueDao;
    @Autowired
    private PointCrudOperation pointCrudOperation;
    @Autowired
    private PointDao pointDao;
    @Autowired
    private RealtimeValueDao realtimeValueDao;
    @Autowired
    private RealtimeValueCrudOperation realtimeValueCrudOperation;
    @Autowired
    private TriggeredValueCrudOperation triggeredValueCrudOperation;
    @Autowired
    private TriggeredValueDao triggeredValueDao;
    @Autowired
    private TriggerInfoCrudOperation triggerInfoCrudOperation;
    @Autowired
    private TriggerInfoDao triggerInfoDao;
    @Autowired
    private FilterSupportCache filterSupportCache;
    @Autowired
    private FilterSupportDao filterSupportDao;
    @Autowired
    private TriggerSupportCache triggerSupportCache;
    @Autowired
    private TriggerSupportDao triggerSupportDao;
    @Autowired
    private MapperSupportCache mapperSupportCache;
    @Autowired
    private MapperSupportDao mapperSupportDao;

    @Value("${cache.timeout.entity.filter_support}")
    private long filterSupportTimeout;
    @Value("${cache.timeout.entity.trigger_support}")
    private long triggerSupportTimeout;
    @Value("${cache.timeout.entity.mapper_support}")
    private long mapperSupportTimeout;

    @Bean
    public CustomBatchCrudService<LongIdKey, FilteredValue> filteredValueCustomCrudService() {
        return new CustomBatchCrudService<>(
                filteredValueCrudOperation,
                longIdKeyKeyFetcher(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<FilteredValue> filteredValueDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                filteredValueDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<FilteredValue> filteredValueDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                filteredValueDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
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
    public CustomBatchCrudService<LongIdKey, PersistenceValue> persistenceValueCustomCrudService() {
        return new CustomBatchCrudService<>(
                persistenceValueCrudOperation,
                longIdKeyKeyFetcher(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<PersistenceValue> persistenceValueDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                persistenceValueDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<PersistenceValue> persistenceValueDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                persistenceValueDao,
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
    public CustomBatchCrudService<LongIdKey, RealtimeValue> realtimeValueCustomBatchCrudService() {
        return new CustomBatchCrudService<>(
                realtimeValueCrudOperation,
                longIdKeyKeyFetcher(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<RealtimeValue> realtimeValueDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                realtimeValueDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public CustomBatchCrudService<LongIdKey, TriggeredValue> triggeredValueCustomCrudService() {
        return new CustomBatchCrudService<>(
                triggeredValueCrudOperation,
                longIdKeyKeyFetcher(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<TriggeredValue> triggeredValueDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                triggeredValueDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<TriggeredValue> triggeredValueDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                triggeredValueDao,
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

    @Bean
    public DaoOnlyBatchWriteService<LongIdKey, FilteredValue> filteredValueDaoOnlyBatchWriteService() {
        return new DaoOnlyBatchWriteService<>(
                filteredValueDao,
                longIdKeyKeyFetcher(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyBatchWriteService<LongIdKey, TriggeredValue> triggeredValueDaoOnlyBatchWriteService() {
        return new DaoOnlyBatchWriteService<>(
                triggeredValueDao,
                longIdKeyKeyFetcher(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyBatchWriteService<LongIdKey, PersistenceValue> persistenceValueDaoOnlyBatchWriteService() {
        return new DaoOnlyBatchWriteService<>(
                persistenceValueDao,
                longIdKeyKeyFetcher(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }
}
