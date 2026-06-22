package com.dwarfeng.fdr.impl.handler.fetcher.simulate.awg;

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
 * 任意波形模拟抓取器注册。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
@Component
public class SimulateAwgFetcherRegistry extends AbstractFetcherRegistry {

    public static final String FETCHER_TYPE = SimulateAwgFetcherConstants.FETCHER_TYPE;

    private final ApplicationContext ctx;

    private final ThreadPoolTaskScheduler scheduler;

    public SimulateAwgFetcherRegistry(ApplicationContext ctx, ThreadPoolTaskScheduler scheduler) {
        super(FETCHER_TYPE);
        this.ctx = ctx;
        this.scheduler = scheduler;
    }

    @Override
    public String provideLabel() {
        return "任意波形模拟抓取器";
    }

    @Override
    public String provideDescription() {
        return "根据配置中的波表、插值方式、幅值、偏置与初始相位，按采样频率生成记录。";
    }

    @Override
    public String provideExampleParam() {
        SimulateAwgFetcherConfig config = new SimulateAwgFetcherConfig(
                new FastJsonLongIdKey(1), 10L, 1000L, 1000.0, new double[]{0.0, 1.0, 0.0, -1.0}, "linear",
                1.0, 0.0, 20000, 200L, 100L
        );
        return JSON.toJSONString(config, true);
    }

    @Override
    public Fetcher makeFetcher(String type, String param) throws FetcherException {
        try {
            SimulateAwgFetcherConfig config = JSON.parseObject(param, SimulateAwgFetcherConfig.class);
            validateSimulateAwgFetcherConfig(config);
            return ctx.getBean(SimulateAwgFetcher.class, ctx, config, scheduler);
        } catch (FetcherException e) {
            throw e;
        } catch (Exception e) {
            throw new FetcherMakeException("构造任意波形模拟抓取器失败", e);
        }
    }

    private void validateSimulateAwgFetcherConfig(SimulateAwgFetcherConfig config) throws FetcherMakeException {
        if (Objects.isNull(config)) {
            throw new FetcherMakeException("配置不能为空");
        }
        if (Objects.isNull(config.getPointKey())) {
            throw new FetcherMakeException("字段 point_key 不能为空");
        }
        validatePositive("tick_period", config.getTickPeriod());
        validatePositive("sample_frequency", config.getSampleFrequency());
        validatePositive("wave_table_frequency", config.getWaveTableFrequency());
        if (Objects.isNull(config.getWaveTable()) || config.getWaveTable().length < 2) {
            throw new FetcherMakeException("字段 wave_table 至少需要 2 个数值");
        }
        if (Objects.isNull(config.getInterpolation()) || config.getInterpolation().trim().isEmpty()) {
            throw new FetcherMakeException("字段 interpolation 不能为空");
        }
        if (!SimulateAwgFetcherConstants.interpolationSpace().contains(config.getInterpolation())) {
            throw new FetcherMakeException("字段 interpolation 非法: " + config.getInterpolation());
        }
        if (Objects.isNull(config.getAmplitude()) || config.getAmplitude() < 0) {
            throw new FetcherMakeException("字段 amplitude 必须大于等于 0");
        }
        if (Objects.isNull(config.getOffset())) {
            throw new FetcherMakeException("字段 offset 不能为空");
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
        return "SimulateAwgFetcherRegistry{" +
                "ctx=" + ctx +
                ", scheduler=" + scheduler +
                ", fetcherType='" + fetcherType + '\'' +
                '}';
    }
}
