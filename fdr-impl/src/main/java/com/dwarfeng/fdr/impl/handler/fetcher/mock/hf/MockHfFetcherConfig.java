package com.dwarfeng.fdr.impl.handler.fetcher.mock.hf;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

/**
 * 高频模拟抓取器配置。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
public class MockHfFetcherConfig implements Bean {

    private static final long serialVersionUID = -7600930411773120179L;

    @JSONField(name = "#point_key", ordinal = 1, deserialize = false)
    private String pointKeyRem = "抓取数据写入的点位主键。";

    @JSONField(name = "point_key", ordinal = 2)
    private FastJsonLongIdKey pointKey;

    @JSONField(name = "#tick_period", ordinal = 3, deserialize = false)
    private String tickPeriodRem = "高频补样任务 tick 周期，单位毫秒，必须大于 0。";

    @JSONField(name = "tick_period", ordinal = 4)
    private Long tickPeriod;

    @JSONField(name = "#frequency", ordinal = 5, deserialize = false)
    private String frequencyRem = "目标采样频率，单位 Hz，必须大于 0。";

    @JSONField(name = "frequency", ordinal = 6)
    private Long frequency;

    @JSONField(name = "#generator_type", ordinal = 7, deserialize = false)
    private String generatorTypeRem = "数据生成类型，合法值为 " +
            String.join("、", MockHfFetcherConstants.generatorTypeSpace()) + "。";

    @JSONField(name = "generator_type", ordinal = 8)
    private String generatorType;

    @JSONField(name = "#random_seed", ordinal = 9, deserialize = false)
    private String randomSeedRem = "随机种子。为 null 时使用随机种子；填写 long 值时生成可复现随机序列。";

    @JSONField(name = "random_seed", ordinal = 10)
    private Long randomSeed;

    @JSONField(name = "#max_samples_per_tick", ordinal = 11, deserialize = false)
    private String maxSamplesPerTickRem = "单次 tick 允许补发的最大样本数，必须大于 0；超过时丢弃本 tick。";

    @JSONField(name = "max_samples_per_tick", ordinal = 12)
    private Integer maxSamplesPerTick;

    @JSONField(name = "#misfire_threshold", ordinal = 13, deserialize = false)
    private String misfireThresholdRem = "调度延迟自愈阈值，单位毫秒；实际延迟超过该值时重置调度基线。";

    @JSONField(name = "misfire_threshold", ordinal = 14)
    private Long misfireThreshold;

    @JSONField(name = "#misfire_postpone", ordinal = 15, deserialize = false)
    private String misfirePostponeRem = "触发 misfire 后基于当前时间向后推迟的毫秒数。";

    @JSONField(name = "misfire_postpone", ordinal = 16)
    private Long misfirePostpone;

    public MockHfFetcherConfig() {
    }

    public MockHfFetcherConfig(
            FastJsonLongIdKey pointKey, Long tickPeriod, Long frequency, String generatorType, Long randomSeed,
            Integer maxSamplesPerTick, Long misfireThreshold, Long misfirePostpone
    ) {
        this.pointKey = pointKey;
        this.tickPeriod = tickPeriod;
        this.frequency = frequency;
        this.generatorType = generatorType;
        this.randomSeed = randomSeed;
        this.maxSamplesPerTick = maxSamplesPerTick;
        this.misfireThreshold = misfireThreshold;
        this.misfirePostpone = misfirePostpone;
    }

    public String getPointKeyRem() {
        return pointKeyRem;
    }

    public void setPointKeyRem(String pointKeyRem) {
        this.pointKeyRem = pointKeyRem;
    }

    public FastJsonLongIdKey getPointKey() {
        return pointKey;
    }

    public void setPointKey(FastJsonLongIdKey pointKey) {
        this.pointKey = pointKey;
    }

    public String getTickPeriodRem() {
        return tickPeriodRem;
    }

    public void setTickPeriodRem(String tickPeriodRem) {
        this.tickPeriodRem = tickPeriodRem;
    }

    public Long getTickPeriod() {
        return tickPeriod;
    }

    public void setTickPeriod(Long tickPeriod) {
        this.tickPeriod = tickPeriod;
    }

    public String getFrequencyRem() {
        return frequencyRem;
    }

    public void setFrequencyRem(String frequencyRem) {
        this.frequencyRem = frequencyRem;
    }

    public Long getFrequency() {
        return frequency;
    }

    public void setFrequency(Long frequency) {
        this.frequency = frequency;
    }

    public String getGeneratorTypeRem() {
        return generatorTypeRem;
    }

    public void setGeneratorTypeRem(String generatorTypeRem) {
        this.generatorTypeRem = generatorTypeRem;
    }

    public String getGeneratorType() {
        return generatorType;
    }

    public void setGeneratorType(String generatorType) {
        this.generatorType = generatorType;
    }

    public String getRandomSeedRem() {
        return randomSeedRem;
    }

    public void setRandomSeedRem(String randomSeedRem) {
        this.randomSeedRem = randomSeedRem;
    }

    public Long getRandomSeed() {
        return randomSeed;
    }

    public void setRandomSeed(Long randomSeed) {
        this.randomSeed = randomSeed;
    }

    public String getMaxSamplesPerTickRem() {
        return maxSamplesPerTickRem;
    }

    public void setMaxSamplesPerTickRem(String maxSamplesPerTickRem) {
        this.maxSamplesPerTickRem = maxSamplesPerTickRem;
    }

    public Integer getMaxSamplesPerTick() {
        return maxSamplesPerTick;
    }

    public void setMaxSamplesPerTick(Integer maxSamplesPerTick) {
        this.maxSamplesPerTick = maxSamplesPerTick;
    }

    public String getMisfireThresholdRem() {
        return misfireThresholdRem;
    }

    public void setMisfireThresholdRem(String misfireThresholdRem) {
        this.misfireThresholdRem = misfireThresholdRem;
    }

    public Long getMisfireThreshold() {
        return misfireThreshold;
    }

    public void setMisfireThreshold(Long misfireThreshold) {
        this.misfireThreshold = misfireThreshold;
    }

    public String getMisfirePostponeRem() {
        return misfirePostponeRem;
    }

    public void setMisfirePostponeRem(String misfirePostponeRem) {
        this.misfirePostponeRem = misfirePostponeRem;
    }

    public Long getMisfirePostpone() {
        return misfirePostpone;
    }

    public void setMisfirePostpone(Long misfirePostpone) {
        this.misfirePostpone = misfirePostpone;
    }

    @Override
    public String toString() {
        return "MockHfFetcherConfig{" +
                "pointKeyRem='" + pointKeyRem + '\'' +
                ", pointKey=" + pointKey +
                ", tickPeriodRem='" + tickPeriodRem + '\'' +
                ", tickPeriod=" + tickPeriod +
                ", frequencyRem='" + frequencyRem + '\'' +
                ", frequency=" + frequency +
                ", generatorTypeRem='" + generatorTypeRem + '\'' +
                ", generatorType='" + generatorType + '\'' +
                ", randomSeedRem='" + randomSeedRem + '\'' +
                ", randomSeed=" + randomSeed +
                ", maxSamplesPerTickRem='" + maxSamplesPerTickRem + '\'' +
                ", maxSamplesPerTick=" + maxSamplesPerTick +
                ", misfireThresholdRem='" + misfireThresholdRem + '\'' +
                ", misfireThreshold=" + misfireThreshold +
                ", misfirePostponeRem='" + misfirePostponeRem + '\'' +
                ", misfirePostpone=" + misfirePostpone +
                '}';
    }
}
