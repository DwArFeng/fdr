package com.dwarfeng.fdr.node.record.configuration;

import com.dwarfeng.fdr.impl.bean.entity.*;
import com.dwarfeng.fdr.impl.dao.preset.*;
import com.dwarfeng.fdr.sdk.bean.entity.FastJsonRealtimeValue;
import com.dwarfeng.fdr.stack.bean.entity.*;
import com.dwarfeng.subgrade.impl.bean.DozerBeanTransformer;
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
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.orm.hibernate5.HibernateTemplate;

import java.util.List;

@Configuration
public class DaoConfiguration {

    @Autowired
    private HibernateTemplate hibernateTemplate;
    @Autowired
    private RedisTemplate<String, ?> redisTemplate;
    @Autowired
    private Mapper mapper;

    @Autowired
    private FilteredValuePresetCriteriaMaker filteredValuePresetCriteriaMaker;
    @Autowired
    private FilterInfoPresetCriteriaMaker filterInfoPresetCriteriaMaker;
    @Autowired
    private PersistenceValuePresetCriteriaMaker persistenceValuePresetCriteriaMaker;
    @Autowired
    private List<DialectNativeLookup<PersistenceValue>> persistenceValueDialectNativeLookups;
    @Autowired
    private PointPresetCriteriaMaker pointPresetCriteriaMaker;
    @Autowired
    private TriggeredValuePresetCriteriaMaker triggeredValuePresetCriteriaMaker;
    @Autowired
    private TriggerInfoPresetCriteriaMaker triggerInfoPresetCriteriaMaker;
    @Autowired
    private FilterSupportPresetCriteriaMaker filterSupportPresetCriteriaMaker;
    @Autowired
    private TriggerSupportPresetCriteriaMaker triggerSupportPresetCriteriaMaker;
    @Autowired
    private MapperSupportPresetCriteriaMaker mapperSupportPresetCriteriaMaker;

    @Value("${redis.dbkey.realtime_value}")
    private String realtimeValueDbKey;

    @Value("${hibernate.jdbc.batch_size}")
    private int batchSize;

    @Value("${hibernate.dialect}")
    private String hibernateDialect;
    @Value("${hibernate.accelerate.using_native_sql}")
    private boolean accelerateEnabled;

    @Bean
    public BeanTransformer<FilteredValue, HibernateFilteredValue> filteredValueDozerBeanTransformer() {
        return new DozerBeanTransformer<>(FilteredValue.class, HibernateFilteredValue.class, mapper);
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, FilteredValue, HibernateFilteredValue>
    filteredValueHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, mapper),
                new DozerBeanTransformer<>(FilteredValue.class, HibernateFilteredValue.class, mapper),
                HibernateFilteredValue.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<FilteredValue, HibernateFilteredValue> filteredValueHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(FilteredValue.class, HibernateFilteredValue.class, mapper),
                HibernateFilteredValue.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<FilteredValue, HibernateFilteredValue> filteredValueHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(FilteredValue.class, HibernateFilteredValue.class, mapper),
                HibernateFilteredValue.class,
                filteredValuePresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, FilterInfo, HibernateFilterInfo> filterInfoHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, mapper),
                new DozerBeanTransformer<>(FilterInfo.class, HibernateFilterInfo.class, mapper),
                HibernateFilterInfo.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernatePresetLookupDao<FilterInfo, HibernateFilterInfo> filterInfoHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(FilterInfo.class, HibernateFilterInfo.class, mapper),
                HibernateFilterInfo.class,
                filterInfoPresetCriteriaMaker
        );
    }

    @Bean
    public BeanTransformer<PersistenceValue, HibernatePersistenceValue> persistenceValueBeanTransformer() {
        return new DozerBeanTransformer<>(PersistenceValue.class, HibernatePersistenceValue.class, mapper);
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, PersistenceValue, HibernatePersistenceValue> persistenceValueHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, mapper),
                new DozerBeanTransformer<>(PersistenceValue.class, HibernatePersistenceValue.class, mapper),
                HibernatePersistenceValue.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<PersistenceValue, HibernatePersistenceValue> persistenceValueHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(PersistenceValue.class, HibernatePersistenceValue.class, mapper),
                HibernatePersistenceValue.class
        );
    }

    @Bean
    public PresetLookupDao<PersistenceValue> persistenceValuePresetLookupDao() {
        return HibernateDaoFactory.newPresetLookupDaoWithChosenDialect(
                hibernateTemplate,
                new DozerBeanTransformer<>(PersistenceValue.class, HibernatePersistenceValue.class, mapper),
                HibernatePersistenceValue.class,
                persistenceValuePresetCriteriaMaker,
                persistenceValueDialectNativeLookups,
                hibernateDialect,
                true
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, Point, HibernatePoint> pointHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, mapper),
                new DozerBeanTransformer<>(Point.class, HibernatePoint.class, mapper),
                HibernatePoint.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<Point, HibernatePoint> pointHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(Point.class, HibernatePoint.class, mapper),
                HibernatePoint.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<Point, HibernatePoint> pointHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(Point.class, HibernatePoint.class, mapper),
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
                new DozerBeanTransformer<>(RealtimeValue.class, FastJsonRealtimeValue.class, mapper),
                realtimeValueDbKey
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisEntireLookupDao<LongIdKey, RealtimeValue, FastJsonRealtimeValue> realtimeValueRedisEntireLookupDao() {
        return new RedisEntireLookupDao<>(
                (RedisTemplate<String, FastJsonRealtimeValue>) redisTemplate,
                new LongIdStringKeyFormatter("key."),
                new DozerBeanTransformer<>(RealtimeValue.class, FastJsonRealtimeValue.class, mapper),
                realtimeValueDbKey
        );
    }

    @Bean
    public BeanTransformer<TriggeredValue, HibernateTriggeredValue> triggeredValueDozerBeanTransformer() {
        return new DozerBeanTransformer<>(TriggeredValue.class, HibernateTriggeredValue.class, mapper);
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, TriggeredValue, HibernateTriggeredValue> triggeredValueHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, mapper),
                new DozerBeanTransformer<>(TriggeredValue.class, HibernateTriggeredValue.class, mapper),
                HibernateTriggeredValue.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<TriggeredValue, HibernateTriggeredValue> triggeredValueHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(TriggeredValue.class, HibernateTriggeredValue.class, mapper),
                HibernateTriggeredValue.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<TriggeredValue, HibernateTriggeredValue> triggeredValueHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(TriggeredValue.class, HibernateTriggeredValue.class, mapper),
                HibernateTriggeredValue.class,
                triggeredValuePresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, TriggerInfo, HibernateTriggerInfo> triggerInfoHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, mapper),
                new DozerBeanTransformer<>(TriggerInfo.class, HibernateTriggerInfo.class, mapper),
                HibernateTriggerInfo.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernatePresetLookupDao<TriggerInfo, HibernateTriggerInfo> triggerInfoHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(TriggerInfo.class, HibernateTriggerInfo.class, mapper),
                HibernateTriggerInfo.class,
                triggerInfoPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<StringIdKey, HibernateStringIdKey, FilterSupport, HibernateFilterSupport>
    filterSupportHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(StringIdKey.class, HibernateStringIdKey.class, mapper),
                new DozerBeanTransformer<>(FilterSupport.class, HibernateFilterSupport.class, mapper),
                HibernateFilterSupport.class
        );
    }

    @Bean
    public HibernateEntireLookupDao<FilterSupport, HibernateFilterSupport> filterSupportHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(FilterSupport.class, HibernateFilterSupport.class, mapper),
                HibernateFilterSupport.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<FilterSupport, HibernateFilterSupport> filterSupportHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(FilterSupport.class, HibernateFilterSupport.class, mapper),
                HibernateFilterSupport.class,
                filterSupportPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<StringIdKey, HibernateStringIdKey, TriggerSupport, HibernateTriggerSupport>
    triggerSupportHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(StringIdKey.class, HibernateStringIdKey.class, mapper),
                new DozerBeanTransformer<>(TriggerSupport.class, HibernateTriggerSupport.class, mapper),
                HibernateTriggerSupport.class
        );
    }

    @Bean
    public HibernateEntireLookupDao<TriggerSupport, HibernateTriggerSupport> triggerSupportHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(TriggerSupport.class, HibernateTriggerSupport.class, mapper),
                HibernateTriggerSupport.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<TriggerSupport, HibernateTriggerSupport> triggerSupportHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(TriggerSupport.class, HibernateTriggerSupport.class, mapper),
                HibernateTriggerSupport.class,
                triggerSupportPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateEntireLookupDao<FilterInfo, HibernateFilterInfo> filterInfoHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(FilterInfo.class, HibernateFilterInfo.class, mapper),
                HibernateFilterInfo.class
        );
    }

    @Bean
    public HibernateEntireLookupDao<TriggerInfo, HibernateTriggerInfo> triggerInfoHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(TriggerInfo.class, HibernateTriggerInfo.class, mapper),
                HibernateTriggerInfo.class
        );
    }

    @Bean
    public HibernateBatchBaseDao<StringIdKey, HibernateStringIdKey, MapperSupport, HibernateMapperSupport>
    mapperSupportHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(StringIdKey.class, HibernateStringIdKey.class, mapper),
                new DozerBeanTransformer<>(MapperSupport.class, HibernateMapperSupport.class, mapper),
                HibernateMapperSupport.class
        );
    }

    @Bean
    public HibernateEntireLookupDao<MapperSupport, HibernateMapperSupport> mapperSupportHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(MapperSupport.class, HibernateMapperSupport.class, mapper),
                HibernateMapperSupport.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<MapperSupport, HibernateMapperSupport> mapperSupportHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(MapperSupport.class, HibernateMapperSupport.class, mapper),
                HibernateMapperSupport.class,
                mapperSupportPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchWriteDao<FilteredValue, HibernateFilteredValue> filteredValueHibernateBatchWriteDao() {
        return new HibernateBatchWriteDao<>(
                hibernateTemplate,
                filteredValueDozerBeanTransformer(),
                batchSize
        );
    }

    @Bean
    public HibernateBatchWriteDao<TriggeredValue, HibernateTriggeredValue> triggeredValueHibernateBatchWriteDao() {
        return new HibernateBatchWriteDao<>(
                hibernateTemplate,
                triggeredValueDozerBeanTransformer(),
                batchSize
        );
    }

    @Bean
    public HibernateBatchWriteDao<PersistenceValue, HibernatePersistenceValue> persistenceValueHibernateBatchWriteDao() {
        return new HibernateBatchWriteDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(PersistenceValue.class, HibernatePersistenceValue.class, mapper),
                batchSize
        );
    }
}
