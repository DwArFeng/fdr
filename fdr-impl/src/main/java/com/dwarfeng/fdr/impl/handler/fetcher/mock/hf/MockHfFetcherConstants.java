package com.dwarfeng.fdr.impl.handler.fetcher.mock.hf;

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
 * 高频模拟抓取器常量类。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
public final class MockHfFetcherConstants {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockHfFetcherConstants.class);

    @MockHfFetcherGeneratorTypeItem
    public static final String GENERATOR_TYPE_INT = "int";
    @MockHfFetcherGeneratorTypeItem
    public static final String GENERATOR_TYPE_LONG = "long";
    @MockHfFetcherGeneratorTypeItem
    public static final String GENERATOR_TYPE_FLOAT = "float";
    @MockHfFetcherGeneratorTypeItem
    public static final String GENERATOR_TYPE_DOUBLE = "double";
    @MockHfFetcherGeneratorTypeItem
    public static final String GENERATOR_TYPE_GAUSSIAN = "gaussian";
    @MockHfFetcherGeneratorTypeItem
    public static final String GENERATOR_TYPE_BOOLEAN = "boolean";
    @MockHfFetcherGeneratorTypeItem
    public static final String GENERATOR_TYPE_STRING = "string";
    @MockHfFetcherGeneratorTypeItem
    public static final String GENERATOR_TYPE_INT_STRING = "int_string";
    @MockHfFetcherGeneratorTypeItem
    public static final String GENERATOR_TYPE_LONG_STRING = "long_string";
    @MockHfFetcherGeneratorTypeItem
    public static final String GENERATOR_TYPE_FLOAT_STRING = "float_string";
    @MockHfFetcherGeneratorTypeItem
    public static final String GENERATOR_TYPE_DOUBLE_STRING = "double_string";
    @MockHfFetcherGeneratorTypeItem
    public static final String GENERATOR_TYPE_GAUSSIAN_STRING = "gaussian_string";
    @MockHfFetcherGeneratorTypeItem
    public static final String GENERATOR_TYPE_BOOLEAN_STRING = "boolean_string";

    public static final char SAFE_TICK_SETTING_SEPARATOR_CHAR = ';';

    private static final Lock LOCK = new ReentrantLock();

    private static List<String> generatorTypeSpace = null;

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

        Field[] declaredFields = MockHfFetcherConstants.class.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (!declaredField.isAnnotationPresent(MockHfFetcherGeneratorTypeItem.class)) {
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

    private MockHfFetcherConstants() {
        throw new IllegalStateException("禁止实例化");
    }
}
