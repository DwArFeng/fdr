package com.dwarfeng.fdr.impl.cache.redis.cache;

import com.dwarfeng.fdr.impl.cache.redis.bean.entity.RedisTriggeredValue;
import com.dwarfeng.fdr.impl.cache.redis.bean.key.RedisUuidKey;
import com.dwarfeng.fdr.impl.cache.redis.formatter.Formatter;
import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.exception.CacheException;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@Validated
public class TriggeredValueCacheDelegate {

    @Autowired
    private RedisTemplate<String, RedisTriggeredValue> template;
    @Autowired
    private Mapper mapper;
    @Autowired
    @Qualifier("uuidKeyFormatter")
    private Formatter<UuidKey> formatter;

    @Value("${cache.prefix.entity.triggered_value}")
    private String keyPrefix;

    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    @TimeAnalyse
    public boolean exists(@NotNull UuidKey key) throws CacheException {
        try {
            return template.hasKey(formatter.format(keyPrefix, key));
        } catch (Exception e) {
            throw new CacheException("缓存异常", e);
        }
    }

    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    @TimeAnalyse
    public TriggeredValue get(@NotNull UuidKey key) throws CacheException {
        try {
            RedisTriggeredValue redisTriggeredValue = template.opsForValue().get(formatter.format(keyPrefix, key));
            return mapper.map(redisTriggeredValue, TriggeredValue.class);
        } catch (Exception e) {
            throw new CacheException("缓存异常", e);
        }
    }

    @Transactional(transactionManager = "hibernateTransactionManager")
    @TimeAnalyse
    public void push(@NotNull UuidKey key, @NotNull TriggeredValue triggeredValue, @Min(0) long timeout) throws CacheException {
        try {
            RedisTriggeredValue redisTriggeredValue = mapper.map(triggeredValue, RedisTriggeredValue.class);
            template.opsForValue().set(formatter.format(keyPrefix, key), redisTriggeredValue, timeout, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            throw new CacheException("缓存异常", e);
        }
    }

    @Transactional(transactionManager = "hibernateTransactionManager")
    @TimeAnalyse
    public void delete(@NotNull UuidKey key) throws CacheException {
        try {
            template.delete(formatter.format(keyPrefix, key));
        } catch (Exception e) {
            throw new CacheException("缓存异常", e);
        }
    }

    @Transactional(transactionManager = "hibernateTransactionManager")
    @TimeAnalyse
    public void deleteAllByPoint(UuidKey pointKey) throws CacheException {
        try {
            RedisUuidKey redisUuidKey = mapper.map(pointKey, RedisUuidKey.class);
            Set<String> keys = template.keys(keyPrefix + "*");
            for (String key : keys) {
                RedisTriggeredValue redisTriggeredValue = template.opsForValue().get(key);
                if (Objects.equals(redisUuidKey, redisTriggeredValue.getPointKey())) {
                    template.delete(key);
                }
            }
        } catch (Exception e) {
            throw new CacheException("缓存异常", e);
        }
    }

    @Transactional(transactionManager = "hibernateTransactionManager")
    @TimeAnalyse
    public void deleteAllByTriggerInfo(UuidKey triggerInfoKey) throws CacheException {
        try {
            RedisUuidKey redisUuidKey = mapper.map(triggerInfoKey, RedisUuidKey.class);
            Set<String> keys = template.keys(keyPrefix + "*");
            for (String key : keys) {
                RedisTriggeredValue redisTriggeredValue = template.opsForValue().get(key);
                if (Objects.equals(redisUuidKey, redisTriggeredValue.getTriggerKey())) {
                    template.delete(key);
                }
            }
        } catch (Exception e) {
            throw new CacheException("缓存异常", e);
        }
    }
}