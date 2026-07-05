package com.dwarfeng.fdr.impl.configuration;

import com.dwarfeng.fdr.sdk.bean.BeanMapper;
import com.dwarfeng.fdr.sdk.bean.entity.*;
import com.dwarfeng.fdr.stack.bean.entity.*;
import com.dwarfeng.subgrade.impl.bean.MapStructBeanTransformer;
import com.dwarfeng.subgrade.impl.cache.RedisBatchBaseCache;
import com.dwarfeng.subgrade.impl.cache.RedisKeyListCache;
import com.dwarfeng.subgrade.impl.cache.RedisListCache;
import com.dwarfeng.subgrade.sdk.redis.formatter.LongIdStringKeyFormatter;
import com.dwarfeng.subgrade.sdk.redis.formatter.StringIdStringKeyFormatter;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class CacheConfiguration {

    private final RedisTemplate<String, ?> template;

    @Value("${com.dwarfeng.fdr.cache.prefix.entity.filter_info}")
    private String filterInfoPrefix;
    @Value("${com.dwarfeng.fdr.cache.prefix.entity.point}")
    private String pointPrefix;
    @Value("${com.dwarfeng.fdr.cache.prefix.entity.trigger_info}")
    private String triggerInfoPrefix;
    @Value("${com.dwarfeng.fdr.cache.prefix.entity.filter_support}")
    private String filterSupportPrefix;
    @Value("${com.dwarfeng.fdr.cache.prefix.entity.trigger_support}")
    private String triggerSupportPrefix;
    @Value("${com.dwarfeng.fdr.cache.prefix.list.enabled_filter_info}")
    private String enabledFilterInfoPrefix;
    @Value("${com.dwarfeng.fdr.cache.prefix.list.enabled_trigger_info}")
    private String enabledTriggerInfoPrefix;
    @Value("${com.dwarfeng.fdr.cache.prefix.entity.mapper_support}")
    private String mapperSupportPrefix;
    @Value("${com.dwarfeng.fdr.cache.prefix.entity.washer_info}")
    private String washerInfoPrefix;
    @Value("${com.dwarfeng.fdr.cache.prefix.entity.washer_support}")
    private String washerSupportPrefix;
    @Value("${com.dwarfeng.fdr.cache.prefix.list.enabled_washer_info}")
    private String enabledWasherInfoPrefix;
    @Value("${com.dwarfeng.fdr.cache.prefix.entity.fetcher_info}")
    private String fetcherInfoPrefix;
    @Value("${com.dwarfeng.fdr.cache.prefix.entity.fetcher_support}")
    private String fetcherSupportPrefix;
    @Value("${com.dwarfeng.fdr.cache.prefix.list.enabled_fetcher_info}")
    private String enabledFetcherInfoPrefix;

    public CacheConfiguration(
            @Qualifier("redisTemplate") RedisTemplate<String, ?> template
    ) {
        this.template = template;
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<LongIdKey, FilterInfo, FastJsonFilterInfo> filterInfoRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonFilterInfo>) template,
                new LongIdStringKeyFormatter(filterInfoPrefix),
                new MapStructBeanTransformer<>(FilterInfo.class, FastJsonFilterInfo.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<LongIdKey, Point, FastJsonPoint> pointRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonPoint>) template,
                new LongIdStringKeyFormatter(pointPrefix),
                new MapStructBeanTransformer<>(Point.class, FastJsonPoint.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<LongIdKey, TriggerInfo, FastJsonTriggerInfo> triggerInfoRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonTriggerInfo>) template,
                new LongIdStringKeyFormatter(triggerInfoPrefix),
                new MapStructBeanTransformer<>(TriggerInfo.class, FastJsonTriggerInfo.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisKeyListCache<LongIdKey, FilterInfo, FastJsonFilterInfo> filterInfoEnabledRedisKeyListCache() {
        return new RedisKeyListCache<>(
                (RedisTemplate<String, FastJsonFilterInfo>) template,
                new LongIdStringKeyFormatter(enabledFilterInfoPrefix),
                new MapStructBeanTransformer<>(FilterInfo.class, FastJsonFilterInfo.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisKeyListCache<LongIdKey, TriggerInfo, FastJsonTriggerInfo> triggerInfoEnabledRedisKeyListCache() {
        return new RedisKeyListCache<>(
                (RedisTemplate<String, FastJsonTriggerInfo>) template,
                new LongIdStringKeyFormatter(enabledTriggerInfoPrefix),
                new MapStructBeanTransformer<>(TriggerInfo.class, FastJsonTriggerInfo.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<StringIdKey, FilterSupport, FastJsonFilterSupport> filterSupportRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonFilterSupport>) template,
                new StringIdStringKeyFormatter(filterSupportPrefix),
                new MapStructBeanTransformer<>(FilterSupport.class, FastJsonFilterSupport.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<StringIdKey, TriggerSupport, FastJsonTriggerSupport>
    triggerSupportRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonTriggerSupport>) template,
                new StringIdStringKeyFormatter(triggerSupportPrefix),
                new MapStructBeanTransformer<>(TriggerSupport.class, FastJsonTriggerSupport.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<StringIdKey, MapperSupport, FastJsonMapperSupport> mapperSupportRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonMapperSupport>) template,
                new StringIdStringKeyFormatter(mapperSupportPrefix),
                new MapStructBeanTransformer<>(MapperSupport.class, FastJsonMapperSupport.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<LongIdKey, WasherInfo, FastJsonWasherInfo> washerInfoRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonWasherInfo>) template,
                new LongIdStringKeyFormatter(washerInfoPrefix),
                new MapStructBeanTransformer<>(WasherInfo.class, FastJsonWasherInfo.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<StringIdKey, WasherSupport, FastJsonWasherSupport> washerSupportRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonWasherSupport>) template,
                new StringIdStringKeyFormatter(washerSupportPrefix),
                new MapStructBeanTransformer<>(WasherSupport.class, FastJsonWasherSupport.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisKeyListCache<LongIdKey, WasherInfo, FastJsonWasherInfo> washerInfoEnabledRedisKeyListCache() {
        return new RedisKeyListCache<>(
                (RedisTemplate<String, FastJsonWasherInfo>) template,
                new LongIdStringKeyFormatter(enabledWasherInfoPrefix),
                new MapStructBeanTransformer<>(WasherInfo.class, FastJsonWasherInfo.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<LongIdKey, FetcherInfo, FastJsonFetcherInfo> fetcherInfoRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonFetcherInfo>) template,
                new LongIdStringKeyFormatter(fetcherInfoPrefix),
                new MapStructBeanTransformer<>(FetcherInfo.class, FastJsonFetcherInfo.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<StringIdKey, FetcherSupport, FastJsonFetcherSupport>
    fetcherSupportRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonFetcherSupport>) template,
                new StringIdStringKeyFormatter(fetcherSupportPrefix),
                new MapStructBeanTransformer<>(FetcherSupport.class, FastJsonFetcherSupport.class, BeanMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisListCache<FetcherInfo, FastJsonFetcherInfo> fetcherInfoEnabledRedisListCache() {
        return new RedisListCache<>(
                enabledFetcherInfoPrefix,
                (RedisTemplate<String, FastJsonFetcherInfo>) template,
                new MapStructBeanTransformer<>(FetcherInfo.class, FastJsonFetcherInfo.class, BeanMapper.class)
        );
    }
}
