package com.dwarfeng.fdr.impl.handler.fetcher.simulate.wave;

import com.alibaba.fastjson.JSON;

import com.dwarfeng.fdr.sdk.handler.fetcher.AbstractFetcherRegistry;
import com.dwarfeng.fdr.stack.exception.FetcherException;
import com.dwarfeng.fdr.stack.exception.FetcherMakeException;
import com.dwarfeng.fdr.stack.handler.Fetcher;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 函数波形模拟抓取器注册。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
@Component
public class SimulateWaveFetcherRegistry extends AbstractFetcherRegistry {

    public static final String FETCHER_TYPE = SimulateWaveFetcherConstants.FETCHER_TYPE;

    private final ApplicationContext ctx;

    private final ThreadPoolTaskScheduler scheduler;

    public SimulateWaveFetcherRegistry(ApplicationContext ctx, ThreadPoolTaskScheduler scheduler) {
        super(FETCHER_TYPE);
        this.ctx = ctx;
        this.scheduler = scheduler;
    }

    @Override
    public String provideLabel() {
        return "函数波形模拟抓取器";
    }

    @Override
    public String provideDescription() {
        return "根据配置中的波形类型、周期、幅值、偏置与方波占空比，按采样频率生成记录。";
    }

    @Override
    public String provideExampleParam() {
        SimulateWaveFetcherConfig config = new SimulateWaveFetcherConfig(
                new FastJsonLongIdKey(1), 10L, 1000L, "sine", 1000.0, 1.0, 0.0, 0.5, 20000, 200L, 100L
        );
        return JSON.toJSONString(config, true);
    }

    @Override
    public Fetcher makeFetcher(String type, String param) throws FetcherException {
        try {
            SimulateWaveFetcherConfig config = JSON.parseObject(param, SimulateWaveFetcherConfig.class);
            validateSimulateWaveFetcherConfig(config);
            return ctx.getBean(SimulateWaveFetcher.class, ctx, config, scheduler);
        } catch (FetcherException e) {
            throw e;
        } catch (Exception e) {
            throw new FetcherMakeException("构造函数波形模拟抓取器失败", e);
        }
    }

    private void validateSimulateWaveFetcherConfig(SimulateWaveFetcherConfig config) throws FetcherMakeException {
        if (Objects.isNull(config)) {
            throw new FetcherMakeException("配置不能为空");
        }
        if (Objects.isNull(config.getPointKey())) {
            throw new FetcherMakeException("字段 point_key 不能为空");
        }
        validatePositive("tick_period", config.getTickPeriod());
        validatePositive("sample_frequency", config.getSampleFrequency());
        if (Objects.isNull(config.getWaveType()) || config.getWaveType().trim().isEmpty()) {
            throw new FetcherMakeException("字段 wave_type 不能为空");
        }
        if (!SimulateWaveFetcherConstants.waveTypeSpace().contains(config.getWaveType())) {
            throw new FetcherMakeException("字段 wave_type 非法: " + config.getWaveType());
        }
        // dc 类型不需要检查 wavePeriod
        if (!SimulateWaveFetcherConstants.WAVE_TYPE_DC.equals(config.getWaveType())) {
            if (Objects.isNull(config.getWavePeriod()) || config.getWavePeriod() <= 0) {
                throw new FetcherMakeException("字段 wave_period 必须大于 0");
            }
        }
        if (Objects.isNull(config.getAmplitude()) || config.getAmplitude() < 0) {
            throw new FetcherMakeException("字段 amplitude 必须大于等于 0");
        }
        if (Objects.isNull(config.getOffset())) {
            throw new FetcherMakeException("字段 offset 不能为空");
        }
        if (SimulateWaveFetcherConstants.WAVE_TYPE_SQUARE.equals(config.getWaveType())) {
            if (Objects.isNull(config.getDutyCycle()) || config.getDutyCycle() <= 0 || config.getDutyCycle() >= 1) {
                throw new FetcherMakeException("字段 duty_cycle 必须在 0 到 1 之间且不含边界");
            }
        }
        validatePositive("max_samples_per_tick", config.getMaxSamplesPerTick());
        validateNonNegative("misfire_threshold", config.getMisfireThreshold());
        validateNonNegative("misfire_postpone", config.getMisfirePostpone());
    }

    private void validatePositive(String fieldName, Number value) throws FetcherMakeException {
        if (Objects.isNull(value)) {
            throw new FetcherMakeException("字段 " + fieldName + " 不能为空");
        }
        if (value.longValue() <= 0) {
            throw new FetcherMakeException("字段 " + fieldName + " 必须大于 0");
        }
    }

    private void validateNonNegative(String fieldName, Number value) throws FetcherMakeException {
        if (Objects.isNull(value)) {
            throw new FetcherMakeException("字段 " + fieldName + " 不能为空");
        }
        if (value.longValue() < 0) {
            throw new FetcherMakeException("字段 " + fieldName + " 必须大于等于 0");
        }
    }

    @Override
    public String toString() {
        return "SimulateWaveFetcherRegistry{" +
                "ctx=" + ctx +
                ", scheduler=" + scheduler +
                ", fetcherType='" + fetcherType + '\'' +
                '}';
    }
}
