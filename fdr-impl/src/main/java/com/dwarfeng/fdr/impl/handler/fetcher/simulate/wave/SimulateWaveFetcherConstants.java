package com.dwarfeng.fdr.impl.handler.fetcher.simulate.wave;

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
 * 函数波形模拟抓取器常量类。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
public final class SimulateWaveFetcherConstants {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimulateWaveFetcherConstants.class);

    public static final String FETCHER_TYPE = "simulate.wave";

    @SimulateWaveFetcherWaveTypeItem
    public static final String WAVE_TYPE_SINE = "sine";
    @SimulateWaveFetcherWaveTypeItem
    public static final String WAVE_TYPE_COSINE = "cosine";
    @SimulateWaveFetcherWaveTypeItem
    public static final String WAVE_TYPE_SQUARE = "square";
    @SimulateWaveFetcherWaveTypeItem
    public static final String WAVE_TYPE_TRIANGLE = "triangle";
    @SimulateWaveFetcherWaveTypeItem
    public static final String WAVE_TYPE_SAWTOOTH = "sawtooth";
    @SimulateWaveFetcherWaveTypeItem
    public static final String WAVE_TYPE_DC = "dc";

    private static final Lock LOCK = new ReentrantLock();

    private static List<String> waveTypeSpace = null;

    /**
     * 波形类型空间。
     *
     * @return 波形类型空间。
     */
    public static List<String> waveTypeSpace() {
        if (Objects.nonNull(waveTypeSpace)) {
            return waveTypeSpace;
        }
        // 基于线程安全的懒加载初始化结果列表。
        LOCK.lock();
        try {
            if (Objects.nonNull(waveTypeSpace)) {
                return waveTypeSpace;
            }
            initWaveTypeSpace();
            return waveTypeSpace;
        } finally {
            LOCK.unlock();
        }
    }

    private static void initWaveTypeSpace() {
        List<String> result = new ArrayList<>();

        Field[] declaredFields = SimulateWaveFetcherConstants.class.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (!declaredField.isAnnotationPresent(SimulateWaveFetcherWaveTypeItem.class)) {
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

        waveTypeSpace = Collections.unmodifiableList(result);
    }

    private SimulateWaveFetcherConstants() {
        throw new IllegalStateException("禁止实例化");
    }
}
