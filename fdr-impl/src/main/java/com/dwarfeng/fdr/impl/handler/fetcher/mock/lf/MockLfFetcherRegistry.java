package com.dwarfeng.fdr.impl.handler.fetcher.mock.lf;

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
 * 低频模拟抓取器注册。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
@Component
public class MockLfFetcherRegistry extends AbstractFetcherRegistry {

    public static final String FETCHER_TYPE = "mock.lf";

    private final ApplicationContext ctx;

    private final ThreadPoolTaskScheduler scheduler;

    public MockLfFetcherRegistry(ApplicationContext ctx, ThreadPoolTaskScheduler scheduler) {
        super(FETCHER_TYPE);
        this.ctx = ctx;
        this.scheduler = scheduler;
    }

    @Override
    public String provideLabel() {
        return "低频模拟抓取器";
    }

    @Override
    public String provideDescription() {
        return "生成随机的数据模拟抓取过程。";
    }

    @Override
    public String provideExampleParam() {
        MockLfFetcherConfig config = new MockLfFetcherConfig(
                new FastJsonLongIdKey(12450), "fixed_rate", "1000", "double", null, 0L, 0L
        );
        return JSON.toJSONString(config, true);
    }

    @Override
    public Fetcher makeFetcher(String type, String param) throws FetcherException {
        try {
            MockLfFetcherConfig config = JSON.parseObject(param, MockLfFetcherConfig.class);
            validateMockLfFetcherConfig(config);
            return ctx.getBean(MockLfFetcher.class, ctx, config, scheduler);
        } catch (FetcherException e) {
            throw e;
        } catch (Exception e) {
            throw new FetcherMakeException("构造低频模拟抓取器失败", e);
        }
    }

    private void validateMockLfFetcherConfig(MockLfFetcherConfig config) throws FetcherMakeException {
        if (Objects.isNull(config)) {
            throw new FetcherMakeException("配置不能为空");
        }
        if (Objects.isNull(config.getPointKey())) {
            throw new FetcherMakeException("字段 point_key 不能为空");
        }
        if (Objects.isNull(config.getPollType()) || config.getPollType().trim().isEmpty()) {
            throw new FetcherMakeException("字段 poll_type 不能为空");
        }
        if (Objects.isNull(config.getPollSetting()) || config.getPollSetting().trim().isEmpty()) {
            throw new FetcherMakeException("字段 poll_setting 不能为空");
        }
        if (!MockLfFetcherConstants.pollTypeSpace().contains(config.getPollType())) {
            throw new FetcherMakeException("字段 poll_type 非法: " + config.getPollType());
        }
        validatePollSetting(config.getPollType(), config.getPollSetting());
        if (!MockLfFetcherConstants.generatorTypeSpace().contains(config.getGeneratorType())) {
            throw new FetcherMakeException("字段 generator_type 非法: " + config.getGeneratorType());
        }
        if (Objects.isNull(config.getFetchBeforeDelay()) || config.getFetchBeforeDelay() < 0) {
            throw new FetcherMakeException("字段 fetch_before_delay 必须大于等于 0");
        }
        if (Objects.isNull(config.getFetchAfterDelay()) || config.getFetchAfterDelay() < 0) {
            throw new FetcherMakeException("字段 fetch_after_delay 必须大于等于 0");
        }
    }

    private void validatePollSetting(String pollType, String pollSetting) throws FetcherMakeException {
        try {
            switch (pollType) {
                case MockLfFetcherConstants.POLL_TYPE_FIXED_DELAY:
                case MockLfFetcherConstants.POLL_TYPE_FIXED_RATE:
                    validateFixedPollSetting(pollSetting);
                    break;
                case MockLfFetcherConstants.POLL_TYPE_CRON:
                    break;
                case MockLfFetcherConstants.POLL_TYPE_SAFE_FIXED_DELAY:
                case MockLfFetcherConstants.POLL_TYPE_SAFE_FIXED_RATE:
                    validateSafeFixedPollSetting(pollSetting);
                    break;
                case MockLfFetcherConstants.POLL_TYPE_SAFE_CRON:
                    validateSafeCronPollSetting(pollSetting);
                    break;
                default:
                    throw new FetcherMakeException("字段 poll_type 非法: " + pollType);
            }
        } catch (NumberFormatException e) {
            throw new FetcherMakeException("字段 poll_setting 非法: " + pollSetting, e);
        }
    }

    private void validateFixedPollSetting(String pollSetting) throws FetcherMakeException {
        long period = Long.parseLong(pollSetting);
        if (period <= 0) {
            throw new FetcherMakeException("fixed 轮询参数必须大于 0");
        }
    }

    private void validateSafeFixedPollSetting(String pollSetting) throws FetcherMakeException {
        String[] patterns = parseSafePollSettingPatterns(pollSetting);
        validateFixedPollSetting(patterns[0]);
        validateSafeMisfireSetting(patterns[1], patterns[2]);
    }

    private void validateSafeCronPollSetting(String pollSetting) throws FetcherMakeException {
        String[] patterns = parseSafePollSettingPatterns(pollSetting);
        if (patterns[0].trim().isEmpty()) {
            throw new FetcherMakeException("safe cron 基础轮询参数不能为空");
        }
        validateSafeMisfireSetting(patterns[1], patterns[2]);
    }

    private void validateSafeMisfireSetting(String misfireThresholdPattern, String misfirePostponePattern)
            throws FetcherMakeException {
        long misfireThreshold = Long.parseLong(misfireThresholdPattern);
        if (misfireThreshold < 0) {
            throw new FetcherMakeException("safe 轮询参数 misfire_threshold 必须大于等于 0");
        }
        long misfirePostpone = Long.parseLong(misfirePostponePattern);
        if (misfirePostpone < 0) {
            throw new FetcherMakeException("safe 轮询参数 misfire_postpone 必须大于等于 0");
        }
    }

    private String[] parseSafePollSettingPatterns(String pollSetting) throws FetcherMakeException {
        String[] patterns = pollSetting.split(
                String.valueOf(MockLfFetcherConstants.SAFE_POLL_SETTING_SEPARATOR_CHAR), -1
        );
        if (patterns.length != 3) {
            throw new FetcherMakeException("safe 轮询参数必须为三段式: 基础调度参数;misfireThreshold;misfirePostpone");
        }
        return patterns;
    }

    @Override
    public String toString() {
        return "MockLfFetcherRegistry{" +
                "ctx=" + ctx +
                ", scheduler=" + scheduler +
                ", fetcherType='" + fetcherType + '\'' +
                '}';
    }
}
