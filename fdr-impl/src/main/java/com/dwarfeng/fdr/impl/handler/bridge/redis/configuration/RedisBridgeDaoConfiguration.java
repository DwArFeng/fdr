package com.dwarfeng.fdr.impl.handler.bridge.redis.configuration;

import com.dwarfeng.fdr.impl.handler.bridge.redis.bean.*;
import com.dwarfeng.subgrade.impl.bean.MapStructBeanTransformer;
import com.dwarfeng.subgrade.impl.dao.RedisBatchBaseDao;
import com.dwarfeng.subgrade.sdk.redis.formatter.LongIdStringKeyFormatter;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisBridgeDaoConfiguration {

    private final RedisTemplate<String, ?> redisTemplate;

    @Value("${bridge.redis.dbkey.normal_data}")
    private String normalDataDbKey;
    @Value("${bridge.redis.dbkey.filtered_data}")
    private String filteredDataDbKey;
    @Value("${bridge.redis.dbkey.triggered_data}")
    private String triggeredDataDbKey;

    public RedisBridgeDaoConfiguration(
            @Qualifier("redisBridge.redisTemplate") RedisTemplate<String, ?> redisTemplate
    ) {
        this.redisTemplate = redisTemplate;
    }

    @SuppressWarnings("unchecked")
    @Bean
    public RedisBatchBaseDao<LongIdKey, RedisBridgeNormalData, RedisBridgeFastJsonNormalData>
    redisBridgeNormalDataRedisBatchBaseDao() {
        return new RedisBatchBaseDao<>(
                (RedisTemplate<String, RedisBridgeFastJsonNormalData>) redisTemplate,
                new LongIdStringKeyFormatter("key."),
                new MapStructBeanTransformer<>(
                        RedisBridgeNormalData.class,
                        RedisBridgeFastJsonNormalData.class,
                        FastJsonMapper.class
                ),
                normalDataDbKey
        );
    }

    @SuppressWarnings("unchecked")
    @Bean
    public RedisBatchBaseDao<LongIdKey, RedisBridgeFilteredData, RedisBridgeFastJsonFilteredData>
    redisBridgeFilteredDataRedisBatchBaseDao() {
        return new RedisBatchBaseDao<>(
                (RedisTemplate<String, RedisBridgeFastJsonFilteredData>) redisTemplate,
                new LongIdStringKeyFormatter("key."),
                new MapStructBeanTransformer<>(
                        RedisBridgeFilteredData.class,
                        RedisBridgeFastJsonFilteredData.class,
                        FastJsonMapper.class
                ),
                filteredDataDbKey
        );
    }

    @SuppressWarnings("unchecked")
    @Bean
    public RedisBatchBaseDao<LongIdKey, RedisBridgeTriggeredData, RedisBridgeFastJsonTriggeredData>
    redisBridgeTriggeredDataRedisBatchBaseDao() {
        return new RedisBatchBaseDao<>(
                (RedisTemplate<String, RedisBridgeFastJsonTriggeredData>) redisTemplate,
                new LongIdStringKeyFormatter("key."),
                new MapStructBeanTransformer<>(
                        RedisBridgeTriggeredData.class,
                        RedisBridgeFastJsonTriggeredData.class,
                        FastJsonMapper.class
                ),
                triggeredDataDbKey
        );
    }
}
