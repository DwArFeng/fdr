package com.dwarfeng.fdr.impl.handler.fetcher.simulate.awg;

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
 * 任意波形模拟抓取器常量类。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
public final class SimulateAwgFetcherConstants {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimulateAwgFetcherConstants.class);

    public static final String FETCHER_TYPE = "simulate.awg";

    @SimulateAwgFetcherInterpolationItem
    public static final String INTERPOLATION_STEP = "step";
    @SimulateAwgFetcherInterpolationItem
    public static final String INTERPOLATION_LINEAR = "linear";

    private static final Lock LOCK = new ReentrantLock();

    private static List<String> interpolationSpace = null;

    /**
     * 插值方式空间。
     *
     * @return 插值方式空间。
     */
    public static List<String> interpolationSpace() {
        if (Objects.nonNull(interpolationSpace)) {
            return interpolationSpace;
        }
        // 基于线程安全的懒加载初始化结果列表。
        LOCK.lock();
        try {
            if (Objects.nonNull(interpolationSpace)) {
                return interpolationSpace;
            }
            initInterpolationSpace();
            return interpolationSpace;
        } finally {
            LOCK.unlock();
        }
    }

    private static void initInterpolationSpace() {
        List<String> result = new ArrayList<>();

        Field[] declaredFields = SimulateAwgFetcherConstants.class.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (!declaredField.isAnnotationPresent(SimulateAwgFetcherInterpolationItem.class)) {
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

        interpolationSpace = Collections.unmodifiableList(result);
    }

    private SimulateAwgFetcherConstants() {
        throw new IllegalStateException("禁止实例化");
    }
}
