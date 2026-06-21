package com.dwarfeng.fdr.impl.handler.fetcher.mock.lf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 低频模拟抓取器常量类。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
public final class MockLfFetcherConstants {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockLfFetcherConstants.class);

    public static final String FETCHER_TYPE = "mock.lf";

    @MockLfFetcherPollTypeItem
    public static final String POLL_TYPE_FIXED_RATE = "fixed_rate";
    @MockLfFetcherPollTypeItem
    public static final String POLL_TYPE_FIXED_DELAY = "fixed_delay";
    @MockLfFetcherPollTypeItem
    public static final String POLL_TYPE_CRON = "cron";
    @MockLfFetcherPollTypeItem
    public static final String POLL_TYPE_SAFE_FIXED_RATE = "safe_fixed_rate";
    @MockLfFetcherPollTypeItem
    public static final String POLL_TYPE_SAFE_FIXED_DELAY = "safe_fixed_delay";
    @MockLfFetcherPollTypeItem
    public static final String POLL_TYPE_SAFE_CRON = "safe_cron";

    @MockLfFetcherGeneratorTypeItem
    public static final String GENERATOR_TYPE_INT = "int";
    @MockLfFetcherGeneratorTypeItem
    public static final String GENERATOR_TYPE_LONG = "long";
    @MockLfFetcherGeneratorTypeItem
    public static final String GENERATOR_TYPE_FLOAT = "float";
    @MockLfFetcherGeneratorTypeItem
    public static final String GENERATOR_TYPE_DOUBLE = "double";
    @MockLfFetcherGeneratorTypeItem
    public static final String GENERATOR_TYPE_GAUSSIAN = "gaussian";
    @MockLfFetcherGeneratorTypeItem
    public static final String GENERATOR_TYPE_BOOLEAN = "boolean";
    @MockLfFetcherGeneratorTypeItem
    public static final String GENERATOR_TYPE_STRING = "string";
    @MockLfFetcherGeneratorTypeItem
    public static final String GENERATOR_TYPE_INT_STRING = "int_string";
    @MockLfFetcherGeneratorTypeItem
    public static final String GENERATOR_TYPE_LONG_STRING = "long_string";
    @MockLfFetcherGeneratorTypeItem
    public static final String GENERATOR_TYPE_FLOAT_STRING = "float_string";
    @MockLfFetcherGeneratorTypeItem
    public static final String GENERATOR_TYPE_DOUBLE_STRING = "double_string";
    @MockLfFetcherGeneratorTypeItem
    public static final String GENERATOR_TYPE_GAUSSIAN_STRING = "gaussian_string";
    @MockLfFetcherGeneratorTypeItem
    public static final String GENERATOR_TYPE_BOOLEAN_STRING = "boolean_string";

    public static final char SAFE_POLL_SETTING_SEPARATOR_CHAR = ';';

    private static final Lock LOCK = new ReentrantLock();

    private static List<String> pollTypeSpace = null;
    private static List<String> generatorTypeSpace = null;

    /**
     * 轮询类型空间。
     *
     * @return 轮询类型空间。
     */
    public static List<String> pollTypeSpace() {
        if (Objects.nonNull(pollTypeSpace)) {
            return pollTypeSpace;
        }
        // 基于线程安全的懒加载初始化结果列表。
        LOCK.lock();
        try {
            if (Objects.nonNull(pollTypeSpace)) {
                return pollTypeSpace;
            }
            initPollTypeSpace();
            return pollTypeSpace;
        } finally {
            LOCK.unlock();
        }
    }

    private static void initPollTypeSpace() {
        List<String> result = new ArrayList<>();

        Field[] declaredFields = MockLfFetcherConstants.class.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (!declaredField.isAnnotationPresent(MockLfFetcherPollTypeItem.class)) {
                continue;
            }
            String value;
            try {
                value = (String) declaredField.get(null);
                result.add(value);
            } catch (Exception e) {
                LOGGER.error("初始化异常, 请检查代码, 信息如下: ", e);
            }
        }

        pollTypeSpace = Collections.unmodifiableList(result);
    }

    /**
     * 值生成器类型空间。
     *
     * @return 值生成器类型空间。
     */
    @SuppressWarnings("DuplicatedCode")
    public static List<String> generatorTypeSpace() {
        if (Objects.nonNull(generatorTypeSpace)) {
            return generatorTypeSpace;
        }
        // 基于线程安全的懒加载初始化结果列表。
        LOCK.lock();
        try {
            if (Objects.nonNull(generatorTypeSpace)) {
                return generatorTypeSpace;
            }
            initGeneratorTypeSpace();
            return generatorTypeSpace;
        } finally {
            LOCK.unlock();
        }
    }

    private static void initGeneratorTypeSpace() {
        List<String> result = new ArrayList<>();

        Field[] declaredFields = MockLfFetcherConstants.class.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (!declaredField.isAnnotationPresent(MockLfFetcherGeneratorTypeItem.class)) {
                continue;
            }
            String value;
            try {
                value = (String) declaredField.get(null);
                result.add(value);
            } catch (Exception e) {
                LOGGER.error("初始化异常, 请检查代码, 信息如下: ", e);
            }
        }

        generatorTypeSpace = Collections.unmodifiableList(result);
    }

    private MockLfFetcherConstants() {
        throw new IllegalStateException("禁止实例化");
    }
}
