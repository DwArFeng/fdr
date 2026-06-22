package com.dwarfeng.fdr.impl.handler.fetcher.simulate.wave;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

/**
 * 函数波形模拟抓取器配置。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
public class SimulateWaveFetcherConfig implements Bean {

    private static final long serialVersionUID = 4998048592701514003L;

    @JSONField(name = "#point_key", ordinal = 1, deserialize = false)
    private String pointKeyRem = "输出记录所属点位主键。";

    @JSONField(name = "point_key", ordinal = 2)
    private FastJsonLongIdKey pointKey;

    @JSONField(name = "#tick_period", ordinal = 3, deserialize = false)
    private String tickPeriodRem = "抓取器 tick 调度周期，单位毫秒，必须大于 0。";

    @JSONField(name = "tick_period", ordinal = 4)
    private Long tickPeriod;

    @JSONField(name = "#sample_frequency", ordinal = 5, deserialize = false)
    private String sampleFrequencyRem = "输出采样频率，单位 Hz，必须大于 0。";

    @JSONField(name = "sample_frequency", ordinal = 6)
    private Long sampleFrequency;

    @JSONField(name = "#wave_type", ordinal = 7, deserialize = false)
    private String waveTypeRem = "函数波形类型，合法值为 sine、cosine、square、triangle、sawtooth、dc。";

    @JSONField(name = "wave_type", ordinal = 8)
    private String waveType;

    @JSONField(name = "#wave_period", ordinal = 9, deserialize = false)
    private String wavePeriodRem = "波形周期，单位毫秒。除 dc 外必须大于 0。";

    @JSONField(name = "wave_period", ordinal = 10)
    private Double wavePeriod;

    @JSONField(name = "#amplitude", ordinal = 11, deserialize = false)
    private String amplitudeRem = "波形幅值，必须大于等于 0。";

    @JSONField(name = "amplitude", ordinal = 12)
    private Double amplitude;

    @JSONField(name = "#offset", ordinal = 13, deserialize = false)
    private String offsetRem = "输出偏置；dc 类型直接输出该值。";

    @JSONField(name = "offset", ordinal = 14)
    private Double offset;

    @JSONField(name = "#duty_cycle", ordinal = 15, deserialize = false)
    private String dutyCycleRem = "方波占空比，仅 square 使用，必须在 0 到 1 之间且不含边界。";

    @JSONField(name = "duty_cycle", ordinal = 16)
    private Double dutyCycle;

    @JSONField(name = "#max_samples_per_tick", ordinal = 17, deserialize = false)
    private String maxSamplesPerTickRem = "单 tick 最大补样数量，必须大于 0。";

    @JSONField(name = "max_samples_per_tick", ordinal = 18)
    private Integer maxSamplesPerTick;

    @JSONField(name = "#misfire_threshold", ordinal = 19, deserialize = false)
    private String misfireThresholdRem = "调度延迟判定阈值，单位毫秒，必须大于等于 0。";

    @JSONField(name = "misfire_threshold", ordinal = 20)
    private Long misfireThreshold;

    @JSONField(name = "#misfire_postpone", ordinal = 21, deserialize = false)
    private String misfirePostponeRem = "发生误触发时推迟下次调度的毫秒数，必须大于等于 0。";

    @JSONField(name = "misfire_postpone", ordinal = 22)
    private Long misfirePostpone;

    public SimulateWaveFetcherConfig() {
    }

    public SimulateWaveFetcherConfig(
            FastJsonLongIdKey pointKey, Long tickPeriod, Long sampleFrequency, String waveType, Double wavePeriod,
            Double amplitude, Double offset, Double dutyCycle, Integer maxSamplesPerTick, Long misfireThreshold,
            Long misfirePostpone
    ) {
        this.pointKey = pointKey;
        this.tickPeriod = tickPeriod;
        this.sampleFrequency = sampleFrequency;
        this.waveType = waveType;
        this.wavePeriod = wavePeriod;
        this.amplitude = amplitude;
        this.offset = offset;
        this.dutyCycle = dutyCycle;
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

    public String getSampleFrequencyRem() {
        return sampleFrequencyRem;
    }

    public void setSampleFrequencyRem(String sampleFrequencyRem) {
        this.sampleFrequencyRem = sampleFrequencyRem;
    }

    public Long getSampleFrequency() {
        return sampleFrequency;
    }

    public void setSampleFrequency(Long sampleFrequency) {
        this.sampleFrequency = sampleFrequency;
    }

    public String getWaveTypeRem() {
        return waveTypeRem;
    }

    public void setWaveTypeRem(String waveTypeRem) {
        this.waveTypeRem = waveTypeRem;
    }

    public String getWaveType() {
        return waveType;
    }

    public void setWaveType(String waveType) {
        this.waveType = waveType;
    }

    public String getWavePeriodRem() {
        return wavePeriodRem;
    }

    public void setWavePeriodRem(String wavePeriodRem) {
        this.wavePeriodRem = wavePeriodRem;
    }

    public Double getWavePeriod() {
        return wavePeriod;
    }

    public void setWavePeriod(Double wavePeriod) {
        this.wavePeriod = wavePeriod;
    }

    public String getAmplitudeRem() {
        return amplitudeRem;
    }

    public void setAmplitudeRem(String amplitudeRem) {
        this.amplitudeRem = amplitudeRem;
    }

    public Double getAmplitude() {
        return amplitude;
    }

    public void setAmplitude(Double amplitude) {
        this.amplitude = amplitude;
    }

    public String getOffsetRem() {
        return offsetRem;
    }

    public void setOffsetRem(String offsetRem) {
        this.offsetRem = offsetRem;
    }

    public Double getOffset() {
        return offset;
    }

    public void setOffset(Double offset) {
        this.offset = offset;
    }

    public String getDutyCycleRem() {
        return dutyCycleRem;
    }

    public void setDutyCycleRem(String dutyCycleRem) {
        this.dutyCycleRem = dutyCycleRem;
    }

    public Double getDutyCycle() {
        return dutyCycle;
    }

    public void setDutyCycle(Double dutyCycle) {
        this.dutyCycle = dutyCycle;
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
        return "SimulateWaveFetcherConfig{" +
                "pointKeyRem='" + pointKeyRem + '\'' +
                ", pointKey=" + pointKey +
                ", tickPeriodRem='" + tickPeriodRem + '\'' +
                ", tickPeriod=" + tickPeriod +
                ", sampleFrequencyRem='" + sampleFrequencyRem + '\'' +
                ", sampleFrequency=" + sampleFrequency +
                ", waveTypeRem='" + waveTypeRem + '\'' +
                ", waveType='" + waveType + '\'' +
                ", wavePeriodRem='" + wavePeriodRem + '\'' +
                ", wavePeriod=" + wavePeriod +
                ", amplitudeRem='" + amplitudeRem + '\'' +
                ", amplitude=" + amplitude +
                ", offsetRem='" + offsetRem + '\'' +
                ", offset=" + offset +
                ", dutyCycleRem='" + dutyCycleRem + '\'' +
                ", dutyCycle=" + dutyCycle +
                ", maxSamplesPerTickRem='" + maxSamplesPerTickRem + '\'' +
                ", maxSamplesPerTick=" + maxSamplesPerTick +
                ", misfireThresholdRem='" + misfireThresholdRem + '\'' +
                ", misfireThreshold=" + misfireThreshold +
                ", misfirePostponeRem='" + misfirePostponeRem + '\'' +
                ", misfirePostpone=" + misfirePostpone +
                '}';
    }
}
