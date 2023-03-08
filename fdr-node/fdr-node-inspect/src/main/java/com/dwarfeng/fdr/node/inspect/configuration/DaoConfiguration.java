package com.dwarfeng.fdr.node.inspect.configuration;

import com.dwarfeng.fdr.impl.bean.HibernateMapper;
import com.dwarfeng.fdr.impl.bean.entity.*;
import com.dwarfeng.fdr.impl.dao.preset.*;
import com.dwarfeng.fdr.sdk.bean.FastJsonMapper;
import com.dwarfeng.fdr.sdk.bean.entity.FastJsonRealtimeValue;
import com.dwarfeng.fdr.stack.bean.entity.*;
import com.dwarfeng.subgrade.impl.bean.MapStructBeanTransformer;
import com.dwarfeng.subgrade.impl.dao.*;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateStringIdKey;
import com.dwarfeng.subgrade.sdk.hibernate.modification.DefaultDeletionMod;
import com.dwarfeng.subgrade.sdk.hibernate.nativelookup.DialectNativeLookup;
import com.dwarfeng.subgrade.sdk.redis.formatter.LongIdStringKeyFormatter;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.orm.hibernate5.HibernateTemplate;

import java.util.List;

@Configuration
public class DaoConfiguration {

    private final HibernateTemplate hibernateTemplate;
    private final RedisTemplate<String, ?> redisTemplate;

    private final FilteredValuePresetCriteriaMaker filteredValuePresetCriteriaMaker;
    private final List<DialectNativeLookup<FilteredValue>> filteredValueDialectNativeLookups;
    private final FilterInfoPresetCriteriaMaker filterInfoPresetCriteriaMaker;
    private final PersistenceValuePresetCriteriaMaker persistenceValuePresetCriteriaMaker;
    private final List<DialectNativeLookup<PersistenceValue>> persistenceValueDialectNativeLookups;
    private final PointPresetCriteriaMaker pointPresetCriteriaMaker;
    private final TriggeredValuePresetCriteriaMaker triggeredValuePresetCriteriaMaker;
    private final List<DialectNativeLookup<TriggeredValue>> triggeredValueDialectNativeLookups;
    private final TriggerInfoPresetCriteriaMaker triggerInfoPresetCriteriaMaker;
    private final FilterSupportPresetCriteriaMaker filterSupportPresetCriteriaMaker;
    private final TriggerSupportPresetCriteriaMaker triggerSupportPresetCriteriaMaker;
    private final MapperSupportPresetCriteriaMaker mapperSupportPresetCriteriaMaker;

    @Value("${redis.dbkey.realtime_value}")
    private String realtimeValueDbKey;

    @Value("${hibernate.jdbc.batch_size}")
    private int batchSize;

    @Value("${hibernate.dialect}")
    private String hibernateDialect;
    @Value("${hibernate.accelerate_enabled}")
    private boolean accelerateEnabled;

    public DaoConfiguration(
            HibernateTemplate hibernateTemplate,
            RedisTemplate<String, ?> redisTemplate,
            FilteredValuePresetCriteriaMaker filteredValuePresetCriteriaMaker,
            List<DialectNativeLookup<FilteredValue>> filteredValueDialectNativeLookups,
            FilterInfoPresetCriteriaMaker filterInfoPresetCriteriaMaker,
            PersistenceValuePresetCriteriaMaker persistenceValuePresetCriteriaMaker,
            List<DialectNativeLookup<PersistenceValue>> persistenceValueDialectNativeLookups,
            PointPresetCriteriaMaker pointPresetCriteriaMaker,
            TriggeredValuePresetCriteriaMaker triggeredValuePresetCriteriaMaker,
            List<DialectNativeLookup<TriggeredValue>> triggeredValueDialectNativeLookups,
            TriggerInfoPresetCriteriaMaker triggerInfoPresetCriteriaMaker,
            FilterSupportPresetCriteriaMaker filterSupportPresetCriteriaMaker,
            TriggerSupportPresetCriteriaMaker triggerSupportPresetCriteriaMaker,
            MapperSupportPresetCriteriaMaker mapperSupportPresetCriteriaMaker
    ) {
        this.hibernateTemplate = hibernateTemplate;
        this.redisTemplate = redisTemplate;
        this.filteredValuePresetCriteriaMaker = filteredValuePresetCriteriaMaker;
        this.filteredValueDialectNativeLookups = filteredValueDialectNativeLookups;
        this.filterInfoPresetCriteriaMaker = filterInfoPresetCriteriaMaker;
        this.persistenceValuePresetCriteriaMaker = persistenceValuePresetCriteriaMaker;
        this.persistenceValueDialectNativeLookups = persistenceValueDialectNativeLookups;
        this.pointPresetCriteriaMaker = pointPresetCriteriaMaker;
        this.triggeredValuePresetCriteriaMaker = triggeredValuePresetCriteriaMaker;
        this.triggeredValueDialectNativeLookups = triggeredValueDialectNativeLookups;
        this.triggerInfoPresetCriteriaMaker = triggerInfoPresetCriteriaMaker;
        this.filterSupportPresetCriteriaMaker = filterSupportPresetCriteriaMaker;
        this.triggerSupportPresetCriteriaMaker = triggerSupportPresetCriteriaMaker;
        this.mapperSupportPresetCriteriaMaker = mapperSupportPresetCriteriaMaker;
    }

    @Bean
    public BeanTransformer<FilteredValue, HibernateFilteredValue> filteredValueMapStructBeanTransformer() {
        return new MapStructBeanTransformer<>(FilteredValue.class, HibernateFilteredValue.class, HibernateMapper.class);
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, FilteredValue, HibernateFilteredValue>
    filteredValueHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, HibernateMapper.class),
                new MapStructBeanTransformer<>(
                        FilteredValue.class, HibernateFilteredValue.class, HibernateMapper.class
                ),
                HibernateFilteredValue.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<FilteredValue, HibernateFilteredValue> filteredValueHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        FilteredValue.class, HibernateFilteredValue.class, HibernateMapper.class
                ),
                HibernateFilteredValue.class
        );
    }

    @Bean
    public PresetLookupDao<FilteredValue> filteredValuePresetLookupDao() {
        return HibernateDaoFactory.newPresetLookupDaoWithChosenDialect(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        FilteredValue.class, HibernateFilteredValue.class, HibernateMapper.class
                ),
                HibernateFilteredValue.class,
                filteredValuePresetCriteriaMaker,
                filteredValueDialectNativeLookups,
                hibernateDialect,
                accelerateEnabled
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, FilterInfo, HibernateFilterInfo>
    filterInfoHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, HibernateMapper.class),
                new MapStructBeanTransformer<>(FilterInfo.class, HibernateFilterInfo.class, HibernateMapper.class),
                HibernateFilterInfo.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernatePresetLookupDao<FilterInfo, HibernateFilterInfo> filterInfoHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(FilterInfo.class, HibernateFilterInfo.class, HibernateMapper.class),
                HibernateFilterInfo.class,
                filterInfoPresetCriteriaMaker
        );
    }

    @Bean
    public BeanTransformer<PersistenceValue, HibernatePersistenceValue> persistenceValueBeanTransformer() {
        return new MapStructBeanTransformer<>(
                PersistenceValue.class, HibernatePersistenceValue.class, HibernateMapper.class
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, PersistenceValue, HibernatePersistenceValue>
    persistenceValueHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, HibernateMapper.class),
                new MapStructBeanTransformer<>(
                        PersistenceValue.class, HibernatePersistenceValue.class, HibernateMapper.class
                ),
                HibernatePersistenceValue.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<PersistenceValue, HibernatePersistenceValue>
    persistenceValueHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        PersistenceValue.class, HibernatePersistenceValue.class, HibernateMapper.class
                ),
                HibernatePersistenceValue.class
        );
    }

    @Bean
    public PresetLookupDao<PersistenceValue> persistenceValuePresetLookupDao() {
        return HibernateDaoFactory.newPresetLookupDaoWithChosenDialect(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        PersistenceValue.class, HibernatePersistenceValue.class, HibernateMapper.class
                ),
                HibernatePersistenceValue.class,
                persistenceValuePresetCriteriaMaker,
                persistenceValueDialectNativeLookups,
                hibernateDialect,
                accelerateEnabled
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, Point, HibernatePoint> pointHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, HibernateMapper.class),
                new MapStructBeanTransformer<>(Point.class, HibernatePoint.class, HibernateMapper.class),
                HibernatePoint.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<Point, HibernatePoint> pointHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(Point.class, HibernatePoint.class, HibernateMapper.class),
                HibernatePoint.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<Point, HibernatePoint> pointHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(Point.class, HibernatePoint.class, HibernateMapper.class),
                HibernatePoint.class,
                pointPresetCriteriaMaker
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseDao<LongIdKey, RealtimeValue, FastJsonRealtimeValue> realtimeValueRedisBatchBaseDao() {
        return new RedisBatchBaseDao<>(
                (RedisTemplate<String, FastJsonRealtimeValue>) redisTemplate,
                new LongIdStringKeyFormatter("key."),
                new MapStructBeanTransformer<>(RealtimeValue.class, FastJsonRealtimeValue.class, FastJsonMapper.class),
                realtimeValueDbKey
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisEntireLookupDao<LongIdKey, RealtimeValue, FastJsonRealtimeValue> realtimeValueRedisEntireLookupDao() {
        return new RedisEntireLookupDao<>(
                (RedisTemplate<String, FastJsonRealtimeValue>) redisTemplate,
                new LongIdStringKeyFormatter("key."),
                new MapStructBeanTransformer<>(RealtimeValue.class, FastJsonRealtimeValue.class, FastJsonMapper.class),
                realtimeValueDbKey
        );
    }

    @Bean
    public BeanTransformer<TriggeredValue, HibernateTriggeredValue> triggeredValueMapStructBeanTransformer() {
        return new MapStructBeanTransformer<>(
                TriggeredValue.class, HibernateTriggeredValue.class, HibernateMapper.class
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, TriggeredValue, HibernateTriggeredValue>
    triggeredValueHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, HibernateMapper.class),
                new MapStructBeanTransformer<>(
                        TriggeredValue.class, HibernateTriggeredValue.class, HibernateMapper.class
                ),
                HibernateTriggeredValue.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<TriggeredValue, HibernateTriggeredValue> triggeredValueHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        TriggeredValue.class, HibernateTriggeredValue.class, HibernateMapper.class
                ),
                HibernateTriggeredValue.class
        );
    }

    @Bean
    public PresetLookupDao<TriggeredValue> triggeredValuePresetLookupDao() {
        return HibernateDaoFactory.newPresetLookupDaoWithChosenDialect(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        TriggeredValue.class, HibernateTriggeredValue.class, HibernateMapper.class
                ),
                HibernateTriggeredValue.class,
                triggeredValuePresetCriteriaMaker,
                triggeredValueDialectNativeLookups,
                hibernateDialect,
                accelerateEnabled
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, TriggerInfo, HibernateTriggerInfo>
    triggerInfoHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, HibernateMapper.class),
                new MapStructBeanTransformer<>(TriggerInfo.class, HibernateTriggerInfo.class, HibernateMapper.class),
                HibernateTriggerInfo.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernatePresetLookupDao<TriggerInfo, HibernateTriggerInfo> triggerInfoHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(TriggerInfo.class, HibernateTriggerInfo.class, HibernateMapper.class),
                HibernateTriggerInfo.class,
                triggerInfoPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<StringIdKey, HibernateStringIdKey, FilterSupport, HibernateFilterSupport>
    filterSupportHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(StringIdKey.class, HibernateStringIdKey.class, HibernateMapper.class),
                new MapStructBeanTransformer<>(
                        FilterSupport.class, HibernateFilterSupport.class, HibernateMapper.class
                ),
                HibernateFilterSupport.class
        );
    }

    @Bean
    public HibernateEntireLookupDao<FilterSupport, HibernateFilterSupport> filterSupportHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        FilterSupport.class, HibernateFilterSupport.class, HibernateMapper.class
                ),
                HibernateFilterSupport.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<FilterSupport, HibernateFilterSupport> filterSupportHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        FilterSupport.class, HibernateFilterSupport.class, HibernateMapper.class
                ),
                HibernateFilterSupport.class,
                filterSupportPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<StringIdKey, HibernateStringIdKey, TriggerSupport, HibernateTriggerSupport>
    triggerSupportHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(StringIdKey.class, HibernateStringIdKey.class, HibernateMapper.class),
                new MapStructBeanTransformer<>(
                        TriggerSupport.class, HibernateTriggerSupport.class, HibernateMapper.class
                ),
                HibernateTriggerSupport.class
        );
    }

    @Bean
    public HibernateEntireLookupDao<TriggerSupport, HibernateTriggerSupport> triggerSupportHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        TriggerSupport.class, HibernateTriggerSupport.class, HibernateMapper.class
                ),
                HibernateTriggerSupport.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<TriggerSupport, HibernateTriggerSupport> triggerSupportHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        TriggerSupport.class, HibernateTriggerSupport.class, HibernateMapper.class
                ),
                HibernateTriggerSupport.class,
                triggerSupportPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateEntireLookupDao<FilterInfo, HibernateFilterInfo> filterInfoHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(FilterInfo.class, HibernateFilterInfo.class, HibernateMapper.class),
                HibernateFilterInfo.class
        );
    }

    @Bean
    public HibernateEntireLookupDao<TriggerInfo, HibernateTriggerInfo> triggerInfoHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(TriggerInfo.class, HibernateTriggerInfo.class, HibernateMapper.class),
                HibernateTriggerInfo.class
        );
    }

    @Bean
    public HibernateBatchBaseDao<StringIdKey, HibernateStringIdKey, MapperSupport, HibernateMapperSupport>
    mapperSupportHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(StringIdKey.class, HibernateStringIdKey.class, HibernateMapper.class),
                new MapStructBeanTransformer<>(
                        MapperSupport.class, HibernateMapperSupport.class, HibernateMapper.class
                ),
                HibernateMapperSupport.class
        );
    }

    @Bean
    public HibernateEntireLookupDao<MapperSupport, HibernateMapperSupport> mapperSupportHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        MapperSupport.class, HibernateMapperSupport.class, HibernateMapper.class
                ),
                HibernateMapperSupport.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<MapperSupport, HibernateMapperSupport> mapperSupportHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        MapperSupport.class, HibernateMapperSupport.class, HibernateMapper.class
                ),
                HibernateMapperSupport.class,
                mapperSupportPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchWriteDao<FilteredValue, HibernateFilteredValue> filteredValueHibernateBatchWriteDao() {
        return new HibernateBatchWriteDao<>(
                hibernateTemplate,
                filteredValueMapStructBeanTransformer(),
                batchSize
        );
    }

    @Bean
    public HibernateBatchWriteDao<TriggeredValue, HibernateTriggeredValue> triggeredValueHibernateBatchWriteDao() {
        return new HibernateBatchWriteDao<>(
                hibernateTemplate,
                triggeredValueMapStructBeanTransformer(),
                batchSize
        );
    }

    @Bean
    public HibernateBatchWriteDao<PersistenceValue, HibernatePersistenceValue>
    persistenceValueHibernateBatchWriteDao() {
        return new HibernateBatchWriteDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        PersistenceValue.class, HibernatePersistenceValue.class, HibernateMapper.class
                ),
                batchSize
        );
    }
}
