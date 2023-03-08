package com.dwarfeng.fdr.node.record.configuration;

import com.dwarfeng.fdr.sdk.bean.FastJsonMapper;
import com.dwarfeng.fdr.sdk.bean.entity.*;
import com.dwarfeng.fdr.stack.bean.entity.*;
import com.dwarfeng.subgrade.impl.bean.MapStructBeanTransformer;
import com.dwarfeng.subgrade.impl.cache.RedisBatchBaseCache;
import com.dwarfeng.subgrade.impl.cache.RedisKeyListCache;
import com.dwarfeng.subgrade.sdk.redis.formatter.LongIdStringKeyFormatter;
import com.dwarfeng.subgrade.sdk.redis.formatter.StringIdStringKeyFormatter;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class CacheConfiguration {

    private final RedisTemplate<String, ?> template;

    @Value("${cache.prefix.entity.filter_info}")
    private String filterInfoPrefix;
    @Value("${cache.prefix.entity.point}")
    private String pointPrefix;
    @Value("${cache.prefix.entity.trigger_info}")
    private String triggerInfoPrefix;
    @Value("${cache.prefix.entity.filter_support}")
    private String filterSupportPrefix;
    @Value("${cache.prefix.entity.trigger_support}")
    private String triggerSupportPrefix;
    @Value("${cache.prefix.list.enabled_filter_info}")
    private String enabledFilterInfoPrefix;
    @Value("${cache.prefix.list.enabled_trigger_info}")
    private String enabledTriggerInfoPrefix;
    @Value("${cache.prefix.entity.mapper_support}")
    private String mapperSupportPrefix;

    public CacheConfiguration(RedisTemplate<String, ?> template) {
        this.template = template;
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<LongIdKey, FilterInfo, FastJsonFilterInfo> filterInfoRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonFilterInfo>) template,
                new LongIdStringKeyFormatter(filterInfoPrefix),
                new MapStructBeanTransformer<>(FilterInfo.class, FastJsonFilterInfo.class, FastJsonMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<LongIdKey, Point, FastJsonPoint> pointRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonPoint>) template,
                new LongIdStringKeyFormatter(pointPrefix),
                new MapStructBeanTransformer<>(Point.class, FastJsonPoint.class, FastJsonMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<LongIdKey, TriggerInfo, FastJsonTriggerInfo> triggerInfoRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonTriggerInfo>) template,
                new LongIdStringKeyFormatter(triggerInfoPrefix),
                new MapStructBeanTransformer<>(TriggerInfo.class, FastJsonTriggerInfo.class, FastJsonMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisKeyListCache<LongIdKey, FilterInfo, FastJsonFilterInfo> filterInfoEnabledRedisKeyListCache() {
        return new RedisKeyListCache<>(
                (RedisTemplate<String, FastJsonFilterInfo>) template,
                new LongIdStringKeyFormatter(enabledFilterInfoPrefix),
                new MapStructBeanTransformer<>(FilterInfo.class, FastJsonFilterInfo.class, FastJsonMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisKeyListCache<LongIdKey, TriggerInfo, FastJsonTriggerInfo> triggerInfoEnabledRedisKeyListCache() {
        return new RedisKeyListCache<>(
                (RedisTemplate<String, FastJsonTriggerInfo>) template,
                new LongIdStringKeyFormatter(enabledTriggerInfoPrefix),
                new MapStructBeanTransformer<>(TriggerInfo.class, FastJsonTriggerInfo.class, FastJsonMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<StringIdKey, FilterSupport, FastJsonFilterSupport> filterSupportRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonFilterSupport>) template,
                new StringIdStringKeyFormatter(filterSupportPrefix),
                new MapStructBeanTransformer<>(FilterSupport.class, FastJsonFilterSupport.class, FastJsonMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<StringIdKey, TriggerSupport, FastJsonTriggerSupport>
    triggerSupportRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonTriggerSupport>) template,
                new StringIdStringKeyFormatter(triggerSupportPrefix),
                new MapStructBeanTransformer<>(TriggerSupport.class, FastJsonTriggerSupport.class, FastJsonMapper.class)
        );
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisBatchBaseCache<StringIdKey, MapperSupport, FastJsonMapperSupport> mapperSupportRedisBatchBaseCache() {
        return new RedisBatchBaseCache<>(
                (RedisTemplate<String, FastJsonMapperSupport>) template,
                new StringIdStringKeyFormatter(mapperSupportPrefix),
                new MapStructBeanTransformer<>(MapperSupport.class, FastJsonMapperSupport.class, FastJsonMapper.class)
        );
    }
}
