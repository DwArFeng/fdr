package com.dwarfeng.fdr.impl.handler.bridge.hibernate.configuration;

import com.dwarfeng.fdr.impl.handler.bridge.hibernate.bean.HibernateMapper;
import com.dwarfeng.fdr.impl.handler.bridge.hibernate.bean.entity.*;
import com.dwarfeng.fdr.impl.handler.bridge.hibernate.dao.preset.HibernateBridgeFilteredDataPresetCriteriaMaker;
import com.dwarfeng.fdr.impl.handler.bridge.hibernate.dao.preset.HibernateBridgeNormalDataPresetCriteriaMaker;
import com.dwarfeng.fdr.impl.handler.bridge.hibernate.dao.preset.HibernateBridgeTriggeredDataPresetCriteriaMaker;
import com.dwarfeng.subgrade.impl.bean.MapStructBeanTransformer;
import com.dwarfeng.subgrade.impl.dao.HibernateBatchBaseDao;
import com.dwarfeng.subgrade.impl.dao.HibernateBatchWriteDao;
import com.dwarfeng.subgrade.impl.dao.HibernateDaoFactory;
import com.dwarfeng.subgrade.impl.dao.HibernateEntireLookupDao;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.sdk.hibernate.modification.DefaultDeletionMod;
import com.dwarfeng.subgrade.sdk.hibernate.nativelookup.DialectNativeLookup;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Configuration
public class HibernateBridgeDaoConfiguration {

    private static <T> List<T> validList(List<T> list) {
        return Optional.ofNullable(list).orElse(Collections.emptyList());
    }

    private final HibernateTemplate hibernateTemplate;

    private final HibernateBridgeNormalDataPresetCriteriaMaker hibernateBridgeNormalDataPresetCriteriaMaker;
    private final List<DialectNativeLookup<HibernateBridgeNormalData>> hibernateBridgeNormalDataDialectNativeLookups;
    private final HibernateBridgeFilteredDataPresetCriteriaMaker hibernateBridgeFilteredDataPresetCriteriaMaker;
    private final List<DialectNativeLookup<HibernateBridgeFilteredData>> hibernateBridgeFilteredDataDialectNativeLookups;
    private final HibernateBridgeTriggeredDataPresetCriteriaMaker hibernateBridgeTriggeredDataPresetCriteriaMaker;
    private final List<DialectNativeLookup<HibernateBridgeTriggeredData>> hibernateBridgeTriggeredDataDialectNativeLookups;

    @Value("${bridge.hibernate.use_project_config}")
    private boolean useProjectConfig;

    @Value("${hibernate.dialect}")
    private String projectHibernateDialect;
    @Value("${hibernate.jdbc.batch_size}")
    private int projectHibernateJdbcBatchSize;

    @Value("${bridge.hibernate.hibernate.dialect}")
    private String bridgeHibernateDialect;
    @Value("${bridge.hibernate.hibernate.jdbc.batch_size}")
    private int bridgeHibernateJdbcBatchSize;

    @Value("${bridge.hibernate.hibernate.accelerate_enabled}")
    private boolean hibernateAccelerateEnabled;

    public HibernateBridgeDaoConfiguration(
            @Qualifier("hibernateBridge.hibernateTemplate") HibernateTemplate hibernateTemplate,
            HibernateBridgeNormalDataPresetCriteriaMaker hibernateBridgeNormalDataPresetCriteriaMaker,
            List<DialectNativeLookup<HibernateBridgeNormalData>> hibernateBridgeNormalDataDialectNativeLookups,
            HibernateBridgeFilteredDataPresetCriteriaMaker hibernateBridgeFilteredDataPresetCriteriaMaker,
            List<DialectNativeLookup<HibernateBridgeFilteredData>> hibernateBridgeFilteredDataDialectNativeLookups,
            HibernateBridgeTriggeredDataPresetCriteriaMaker hibernateBridgeTriggeredDataPresetCriteriaMaker,
            List<DialectNativeLookup<HibernateBridgeTriggeredData>> hibernateBridgeTriggeredDataDialectNativeLookups
    ) {
        this.hibernateTemplate = hibernateTemplate;
        this.hibernateBridgeNormalDataPresetCriteriaMaker = hibernateBridgeNormalDataPresetCriteriaMaker;
        this.hibernateBridgeNormalDataDialectNativeLookups = validList(
                hibernateBridgeNormalDataDialectNativeLookups
        );
        this.hibernateBridgeFilteredDataPresetCriteriaMaker = hibernateBridgeFilteredDataPresetCriteriaMaker;
        this.hibernateBridgeFilteredDataDialectNativeLookups = validList(
                hibernateBridgeFilteredDataDialectNativeLookups
        );
        this.hibernateBridgeTriggeredDataPresetCriteriaMaker = hibernateBridgeTriggeredDataPresetCriteriaMaker;
        this.hibernateBridgeTriggeredDataDialectNativeLookups = hibernateBridgeTriggeredDataDialectNativeLookups;
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, HibernateBridgeNormalData,
            HibernateHibernateBridgeNormalData> hibernateBridgeNormalDataHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, HibernateMapper.class),
                new MapStructBeanTransformer<>(
                        HibernateBridgeNormalData.class,
                        HibernateHibernateBridgeNormalData.class,
                        HibernateMapper.class
                ),
                HibernateHibernateBridgeNormalData.class,
                new DefaultDeletionMod<>(),
                hibernateJdbcBatchSize()
        );
    }

    @Bean
    public HibernateEntireLookupDao<HibernateBridgeNormalData, HibernateHibernateBridgeNormalData>
    hibernateBridgeNormalDataHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        HibernateBridgeNormalData.class,
                        HibernateHibernateBridgeNormalData.class,
                        HibernateMapper.class
                ),
                HibernateHibernateBridgeNormalData.class
        );
    }

    @Bean
    public PresetLookupDao<HibernateBridgeNormalData> hibernateBridgeNormalDataPresetLookupDao() {
        return HibernateDaoFactory.newPresetLookupDaoWithChosenDialect(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        HibernateBridgeNormalData.class,
                        HibernateHibernateBridgeNormalData.class,
                        HibernateMapper.class
                ),
                HibernateHibernateBridgeNormalData.class,
                hibernateBridgeNormalDataPresetCriteriaMaker,
                hibernateBridgeNormalDataDialectNativeLookups,
                hibernateDialect(),
                hibernateAccelerateEnabled
        );
    }

    @Bean
    public HibernateBatchWriteDao<HibernateBridgeNormalData, HibernateHibernateBridgeNormalData>
    hibernateBridgeNormalDataHibernateBatchWriteDao() {
        return new HibernateBatchWriteDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        HibernateBridgeNormalData.class,
                        HibernateHibernateBridgeNormalData.class,
                        HibernateMapper.class
                ),
                hibernateJdbcBatchSize()
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, HibernateBridgeFilteredData,
            HibernateHibernateBridgeFilteredData> hibernateBridgeFilteredDataHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, HibernateMapper.class),
                new MapStructBeanTransformer<>(
                        HibernateBridgeFilteredData.class,
                        HibernateHibernateBridgeFilteredData.class,
                        HibernateMapper.class
                ),
                HibernateHibernateBridgeFilteredData.class,
                new DefaultDeletionMod<>(),
                hibernateJdbcBatchSize()
        );
    }

    @Bean
    public HibernateEntireLookupDao<HibernateBridgeFilteredData, HibernateHibernateBridgeFilteredData>
    hibernateBridgeFilteredDataHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        HibernateBridgeFilteredData.class,
                        HibernateHibernateBridgeFilteredData.class,
                        HibernateMapper.class
                ),
                HibernateHibernateBridgeFilteredData.class
        );
    }

    @Bean
    public PresetLookupDao<HibernateBridgeFilteredData> hibernateBridgeFilteredDataPresetLookupDao() {
        return HibernateDaoFactory.newPresetLookupDaoWithChosenDialect(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        HibernateBridgeFilteredData.class,
                        HibernateHibernateBridgeFilteredData.class,
                        HibernateMapper.class
                ),
                HibernateHibernateBridgeFilteredData.class,
                hibernateBridgeFilteredDataPresetCriteriaMaker,
                hibernateBridgeFilteredDataDialectNativeLookups,
                hibernateDialect(),
                hibernateAccelerateEnabled
        );
    }

    @Bean
    public HibernateBatchWriteDao<HibernateBridgeFilteredData, HibernateHibernateBridgeFilteredData>
    hibernateBridgeFilteredDataHibernateBatchWriteDao() {
        return new HibernateBatchWriteDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        HibernateBridgeFilteredData.class,
                        HibernateHibernateBridgeFilteredData.class,
                        HibernateMapper.class
                ),
                hibernateJdbcBatchSize()
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, HibernateBridgeTriggeredData,
            HibernateHibernateBridgeTriggeredData> hibernateBridgeTriggeredDataHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, HibernateMapper.class),
                new MapStructBeanTransformer<>(
                        HibernateBridgeTriggeredData.class,
                        HibernateHibernateBridgeTriggeredData.class,
                        HibernateMapper.class
                ),
                HibernateHibernateBridgeTriggeredData.class,
                new DefaultDeletionMod<>(),
                hibernateJdbcBatchSize()
        );
    }

    @Bean
    public HibernateEntireLookupDao<HibernateBridgeTriggeredData, HibernateHibernateBridgeTriggeredData>
    hibernateBridgeTriggeredDataHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        HibernateBridgeTriggeredData.class,
                        HibernateHibernateBridgeTriggeredData.class,
                        HibernateMapper.class
                ),
                HibernateHibernateBridgeTriggeredData.class
        );
    }

    @Bean
    public PresetLookupDao<HibernateBridgeTriggeredData> hibernateBridgeTriggeredDataPresetLookupDao() {
        return HibernateDaoFactory.newPresetLookupDaoWithChosenDialect(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        HibernateBridgeTriggeredData.class,
                        HibernateHibernateBridgeTriggeredData.class,
                        HibernateMapper.class
                ),
                HibernateHibernateBridgeTriggeredData.class,
                hibernateBridgeTriggeredDataPresetCriteriaMaker,
                hibernateBridgeTriggeredDataDialectNativeLookups,
                hibernateDialect(),
                hibernateAccelerateEnabled
        );
    }

    @Bean
    public HibernateBatchWriteDao<HibernateBridgeTriggeredData, HibernateHibernateBridgeTriggeredData>
    hibernateBridgeTriggeredDataHibernateBatchWriteDao() {
        return new HibernateBatchWriteDao<>(
                hibernateTemplate,
                new MapStructBeanTransformer<>(
                        HibernateBridgeTriggeredData.class,
                        HibernateHibernateBridgeTriggeredData.class,
                        HibernateMapper.class
                ),
                hibernateJdbcBatchSize()
        );
    }

    private String hibernateDialect() {
        return useProjectConfig ? projectHibernateDialect : bridgeHibernateDialect;
    }

    private int hibernateJdbcBatchSize() {
        return useProjectConfig ? projectHibernateJdbcBatchSize : bridgeHibernateJdbcBatchSize;
    }
}
