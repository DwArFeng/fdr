package com.dwarfeng.fdr.impl.configuration;

import com.dwarfeng.fdr.impl.bean.BeanMapper;
import com.dwarfeng.fdr.impl.bean.entity.*;
import com.dwarfeng.fdr.impl.dao.preset.*;
import com.dwarfeng.fdr.stack.bean.entity.*;
import com.dwarfeng.subgrade.impl.bean.MapStructBeanTransformer;
import com.dwarfeng.subgrade.impl.dao.HibernateBatchBaseDao;
import com.dwarfeng.subgrade.impl.dao.HibernateEntireLookupDao;
import com.dwarfeng.subgrade.impl.dao.HibernatePresetLookupDao;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateStringIdKey;
import com.dwarfeng.subgrade.sdk.hibernate.modification.DefaultDeletionMod;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTemplate;

@Configuration
public class DaoConfiguration {

    private final HibernateTemplate template;

    private final FilterInfoPresetCriteriaMaker filterInfoPresetCriteriaMaker;
    private final PointPresetCriteriaMaker pointPresetCriteriaMaker;
    private final TriggerInfoPresetCriteriaMaker triggerInfoPresetCriteriaMaker;
    private final FilterSupportPresetCriteriaMaker filterSupportPresetCriteriaMaker;
    private final TriggerSupportPresetCriteriaMaker triggerSupportPresetCriteriaMaker;
    private final MapperSupportPresetCriteriaMaker mapperSupportPresetCriteriaMaker;
    private final WasherInfoPresetCriteriaMaker washerInfoPresetCriteriaMaker;
    private final WasherSupportPresetCriteriaMaker washerSupportPresetCriteriaMaker;

    @Value("${hibernate.jdbc.batch_size}")
    private int batchSize;

    public DaoConfiguration(
            @Qualifier("hibernateTemplate") HibernateTemplate template,
            FilterInfoPresetCriteriaMaker filterInfoPresetCriteriaMaker,
            PointPresetCriteriaMaker pointPresetCriteriaMaker,
            TriggerInfoPresetCriteriaMaker triggerInfoPresetCriteriaMaker,
            FilterSupportPresetCriteriaMaker filterSupportPresetCriteriaMaker,
            TriggerSupportPresetCriteriaMaker triggerSupportPresetCriteriaMaker,
            MapperSupportPresetCriteriaMaker mapperSupportPresetCriteriaMaker,
            WasherInfoPresetCriteriaMaker washerInfoPresetCriteriaMaker,
            WasherSupportPresetCriteriaMaker washerSupportPresetCriteriaMaker
    ) {
        this.template = template;
        this.filterInfoPresetCriteriaMaker = filterInfoPresetCriteriaMaker;
        this.pointPresetCriteriaMaker = pointPresetCriteriaMaker;
        this.triggerInfoPresetCriteriaMaker = triggerInfoPresetCriteriaMaker;
        this.filterSupportPresetCriteriaMaker = filterSupportPresetCriteriaMaker;
        this.triggerSupportPresetCriteriaMaker = triggerSupportPresetCriteriaMaker;
        this.mapperSupportPresetCriteriaMaker = mapperSupportPresetCriteriaMaker;
        this.washerInfoPresetCriteriaMaker = washerInfoPresetCriteriaMaker;
        this.washerSupportPresetCriteriaMaker = washerSupportPresetCriteriaMaker;
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, FilterInfo, HibernateFilterInfo>
    filterInfoHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                template,
                new MapStructBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, BeanMapper.class),
                new MapStructBeanTransformer<>(FilterInfo.class, HibernateFilterInfo.class, BeanMapper.class),
                HibernateFilterInfo.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernatePresetLookupDao<FilterInfo, HibernateFilterInfo> filterInfoHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                new MapStructBeanTransformer<>(FilterInfo.class, HibernateFilterInfo.class, BeanMapper.class),
                HibernateFilterInfo.class,
                filterInfoPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, Point, HibernatePoint> pointHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                template,
                new MapStructBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, BeanMapper.class),
                new MapStructBeanTransformer<>(Point.class, HibernatePoint.class, BeanMapper.class),
                HibernatePoint.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<Point, HibernatePoint> pointHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                template,
                new MapStructBeanTransformer<>(Point.class, HibernatePoint.class, BeanMapper.class),
                HibernatePoint.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<Point, HibernatePoint> pointHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                new MapStructBeanTransformer<>(Point.class, HibernatePoint.class, BeanMapper.class),
                HibernatePoint.class,
                pointPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, TriggerInfo, HibernateTriggerInfo>
    triggerInfoHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                template,
                new MapStructBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, BeanMapper.class),
                new MapStructBeanTransformer<>(TriggerInfo.class, HibernateTriggerInfo.class, BeanMapper.class),
                HibernateTriggerInfo.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernatePresetLookupDao<TriggerInfo, HibernateTriggerInfo> triggerInfoHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                new MapStructBeanTransformer<>(TriggerInfo.class, HibernateTriggerInfo.class, BeanMapper.class),
                HibernateTriggerInfo.class,
                triggerInfoPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<StringIdKey, HibernateStringIdKey, FilterSupport, HibernateFilterSupport>
    filterSupportHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                template,
                new MapStructBeanTransformer<>(StringIdKey.class, HibernateStringIdKey.class, BeanMapper.class),
                new MapStructBeanTransformer<>(
                        FilterSupport.class, HibernateFilterSupport.class, BeanMapper.class
                ),
                HibernateFilterSupport.class
        );
    }

    @Bean
    public HibernateEntireLookupDao<FilterSupport, HibernateFilterSupport> filterSupportHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                template,
                new MapStructBeanTransformer<>(
                        FilterSupport.class, HibernateFilterSupport.class, BeanMapper.class
                ),
                HibernateFilterSupport.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<FilterSupport, HibernateFilterSupport> filterSupportHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                new MapStructBeanTransformer<>(
                        FilterSupport.class, HibernateFilterSupport.class, BeanMapper.class
                ),
                HibernateFilterSupport.class,
                filterSupportPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<StringIdKey, HibernateStringIdKey, TriggerSupport, HibernateTriggerSupport>
    triggerSupportHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                template,
                new MapStructBeanTransformer<>(StringIdKey.class, HibernateStringIdKey.class, BeanMapper.class),
                new MapStructBeanTransformer<>(
                        TriggerSupport.class, HibernateTriggerSupport.class, BeanMapper.class
                ),
                HibernateTriggerSupport.class
        );
    }

    @Bean
    public HibernateEntireLookupDao<TriggerSupport, HibernateTriggerSupport> triggerSupportHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                template,
                new MapStructBeanTransformer<>(
                        TriggerSupport.class, HibernateTriggerSupport.class, BeanMapper.class
                ),
                HibernateTriggerSupport.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<TriggerSupport, HibernateTriggerSupport> triggerSupportHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                new MapStructBeanTransformer<>(
                        TriggerSupport.class, HibernateTriggerSupport.class, BeanMapper.class
                ),
                HibernateTriggerSupport.class,
                triggerSupportPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateEntireLookupDao<FilterInfo, HibernateFilterInfo> filterInfoHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                template,
                new MapStructBeanTransformer<>(FilterInfo.class, HibernateFilterInfo.class, BeanMapper.class),
                HibernateFilterInfo.class
        );
    }

    @Bean
    public HibernateEntireLookupDao<TriggerInfo, HibernateTriggerInfo> triggerInfoHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                template,
                new MapStructBeanTransformer<>(TriggerInfo.class, HibernateTriggerInfo.class, BeanMapper.class),
                HibernateTriggerInfo.class
        );
    }

    @Bean
    public HibernateBatchBaseDao<StringIdKey, HibernateStringIdKey, MapperSupport, HibernateMapperSupport>
    mapperSupportHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                template,
                new MapStructBeanTransformer<>(StringIdKey.class, HibernateStringIdKey.class, BeanMapper.class),
                new MapStructBeanTransformer<>(
                        MapperSupport.class, HibernateMapperSupport.class, BeanMapper.class
                ),
                HibernateMapperSupport.class
        );
    }

    @Bean
    public HibernateEntireLookupDao<MapperSupport, HibernateMapperSupport> mapperSupportHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                template,
                new MapStructBeanTransformer<>(
                        MapperSupport.class, HibernateMapperSupport.class, BeanMapper.class
                ),
                HibernateMapperSupport.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<MapperSupport, HibernateMapperSupport> mapperSupportHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                new MapStructBeanTransformer<>(
                        MapperSupport.class, HibernateMapperSupport.class, BeanMapper.class
                ),
                HibernateMapperSupport.class,
                mapperSupportPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, WasherInfo, HibernateWasherInfo>
    washerInfoHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                template,
                new MapStructBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, BeanMapper.class),
                new MapStructBeanTransformer<>(WasherInfo.class, HibernateWasherInfo.class, BeanMapper.class),
                HibernateWasherInfo.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernatePresetLookupDao<WasherInfo, HibernateWasherInfo> washerInfoHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                new MapStructBeanTransformer<>(WasherInfo.class, HibernateWasherInfo.class, BeanMapper.class),
                HibernateWasherInfo.class,
                washerInfoPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateEntireLookupDao<WasherInfo, HibernateWasherInfo> washerInfoHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                template,
                new MapStructBeanTransformer<>(WasherInfo.class, HibernateWasherInfo.class, BeanMapper.class),
                HibernateWasherInfo.class
        );
    }

    @Bean
    public HibernateBatchBaseDao<StringIdKey, HibernateStringIdKey, WasherSupport, HibernateWasherSupport>
    washerSupportHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                template,
                new MapStructBeanTransformer<>(StringIdKey.class, HibernateStringIdKey.class, BeanMapper.class),
                new MapStructBeanTransformer<>(
                        WasherSupport.class, HibernateWasherSupport.class, BeanMapper.class
                ),
                HibernateWasherSupport.class
        );
    }

    @Bean
    public HibernateEntireLookupDao<WasherSupport, HibernateWasherSupport> washerSupportHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                template,
                new MapStructBeanTransformer<>(
                        WasherSupport.class, HibernateWasherSupport.class, BeanMapper.class
                ),
                HibernateWasherSupport.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<WasherSupport, HibernateWasherSupport> washerSupportHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                new MapStructBeanTransformer<>(
                        WasherSupport.class, HibernateWasherSupport.class, BeanMapper.class
                ),
                HibernateWasherSupport.class,
                washerSupportPresetCriteriaMaker
        );
    }
}
