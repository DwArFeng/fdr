package com.dwarfeng.fdr.stack.cache;

import com.dwarfeng.fdr.stack.exception.CacheException;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 基础缓存接口。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface BaseCache<K, V> extends Cache {

    /**
     * 获取缓存中指定的键是否存在。
     *
     * @param key 指定的键是否存在。
     * @return 指定的键是否存在。
     */
    public boolean exists(K key) throws CacheException;

    /**
     * 获取缓存中指定的所有键是否存在。
     *
     * @param c 指定的所有的键组成的集合。
     * @return 指定的所有键是否都存在。
     * @throws CacheException 缓存异常。
     */
    public boolean existsAll(Collection<K> c) throws CacheException;

    /**
     * 获取缓存中指定的所有键是否都不存在。
     *
     * @param c 指定的所有键组成的集合。
     * @return 指定的所有键是否都不存在。
     * @throws CacheException 缓存异常。
     */
    public boolean existsNon(Collection<K> c) throws CacheException;

    /**
     * 获取缓存中指定键对应的值。
     *
     * @param key 指定的键。
     * @return 指定的键对应的值。
     */
    public V get(K key) throws CacheException;

    /**
     * 向缓存中推送指定的键与值。
     * <p>
     * 如果指定的键不存在，则创建。
     *
     * @param key      指定的键。
     * @param value    指定的键对应的值。
     * @param timeout  超时时间。
     * @param timeUnit 时间单位。
     */
    public void push(K key, V value, long timeout, TimeUnit timeUnit) throws CacheException;

    /**
     * 向缓存中批量推送键与值。
     *
     * @param map      指定的键值对组成的映射。
     * @param timeout  超时时间。
     * @param timeUnit 时间单位。
     * @throws CacheException 缓存异常。
     */
    public void batchPush(Map<K, V> map, long timeout, TimeUnit timeUnit) throws CacheException;

    /**
     * 从缓存中删除指定的键。
     *
     * @param key 指定的键。
     */
    public void delete(K key) throws CacheException;

    /**
     * 从缓存中批量删除指定的键。
     *
     * @param c 指定的键组成的集合。
     * @throws CacheException 缓存异常。
     */
    public void batchDelete(Collection<K> c) throws CacheException;

}
