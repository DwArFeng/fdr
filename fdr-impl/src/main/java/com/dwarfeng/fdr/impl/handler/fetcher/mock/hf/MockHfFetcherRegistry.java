package com.dwarfeng.fdr.impl.handler.fetcher.mock.hf;

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
 * 高频模拟抓取器注册。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
@Component
public class MockHfFetcherRegistry extends AbstractFetcherRegistry {

    public static final String FETCHER_TYPE = "mock.hf";

    private final ApplicationContext ctx;

    private final ThreadPoolTaskScheduler scheduler;

    public MockHfFetcherRegistry(ApplicationContext ctx, ThreadPoolTaskScheduler scheduler) {
        super(FETCHER_TYPE);
        this.ctx = ctx;
        this.scheduler = scheduler;
    }

    @Override
    public String provideLabel() {
        return "高频模拟抓取器";
    }

    @Override
    public String provideDescription() {
        return "使用粗粒度 tick + 批量补样生成高频随机数据。";
    }

    @Override
    public String provideExampleParam() {
        MockHfFetcherConfig config = new MockHfFetcherConfig(
                new FastJsonLongIdKey(12450), 1000L, 100L, "double", null, 200, 5000L, 1000L
        );
        return JSON.toJSONString(config, true);
    }

    @Override
    public Fetcher makeFetcher(String type, String param) throws FetcherException {
        try {
            MockHfFetcherConfig config = JSON.parseObject(param, MockHfFetcherConfig.class);
            validateMockHfFetcherConfig(config);
            return ctx.getBean(MockHfFetcher.class, ctx, config, scheduler);
        } catch (FetcherException e) {
            throw e;
        } catch (Exception e) {
            throw new FetcherMakeException("构造高频模拟抓取器失败", e);
        }
    }

    private void validateMockHfFetcherConfig(MockHfFetcherConfig config) throws FetcherMakeException {
        if (Objects.isNull(config)) {
            throw new FetcherMakeException("配置不能为空");
        }
        if (Objects.isNull(config.getPointKey())) {
            throw new FetcherMakeException("字段 point_key 不能为空");
        }
        validatePositive("tick_period", config.getTickPeriod());
        validatePositive("frequency", config.getFrequency());
        if (Objects.isNull(config.getGeneratorType()) || config.getGeneratorType().trim().isEmpty()) {
            throw new FetcherMakeException("字段 generator_type 不能为空");
        }
        if (!MockHfFetcherConstants.generatorTypeSpace().contains(config.getGeneratorType())) {
            throw new FetcherMakeException("字段 generator_type 非法: " + config.getGeneratorType());
        }
        validatePositive("max_samples_per_tick", config.getMaxSamplesPerTick());
        validatePositive("misfire_threshold", config.getMisfireThreshold());
        validatePositive("misfire_postpone", config.getMisfirePostpone());
    }

    private void validatePositive(String fieldName, Number value) throws FetcherMakeException {
        if (Objects.isNull(value)) {
            throw new FetcherMakeException("字段 " + fieldName + " 不能为空");
        }
        if (value.longValue() <= 0) {
            throw new FetcherMakeException("字段 " + fieldName + " 必须大于 0");
        }
    }

    @Override
    public String toString() {
        return "MockHfFetcherRegistry{" +
                "ctx=" + ctx +
                ", scheduler=" + scheduler +
                ", fetcherType='" + fetcherType + '\'' +
                '}';
    }
}
