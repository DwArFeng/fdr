package com.dwarfeng.fdr.impl.handler.fetcher.simulate.awg;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Arrays;

/**
 * 任意波形模拟抓取器配置。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
public class SimulateAwgFetcherConfig implements Bean {

    private static final long serialVersionUID = 5704213956382267245L;

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

    @JSONField(name = "#wave_table_frequency", ordinal = 7, deserialize = false)
    private String waveTableFrequencyRem = "波表原始采样频率，单位 Hz，必须大于 0。";

    @JSONField(name = "wave_table_frequency", ordinal = 8)
    private Double waveTableFrequency;

    @JSONField(name = "#wave_table", ordinal = 9, deserialize = false)
    private String waveTableRem = "任意波形表，至少包含 2 个 Double 数值。";

    @JSONField(name = "wave_table", ordinal = 10)
    private double[] waveTable;

    @JSONField(name = "#interpolation", ordinal = 11, deserialize = false)
    private String interpolationRem = "插值方式，合法值为 step、linear。";

    @JSONField(name = "interpolation", ordinal = 12)
    private String interpolation;

    @JSONField(name = "#amplitude", ordinal = 13, deserialize = false)
    private String amplitudeRem = "输出缩放系数，必须大于等于 0。";

    @JSONField(name = "amplitude", ordinal = 14)
    private Double amplitude;

    @JSONField(name = "#offset", ordinal = 15, deserialize = false)
    private String offsetRem = "输出偏置。";

    @JSONField(name = "offset", ordinal = 16)
    private Double offset;

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

    public SimulateAwgFetcherConfig() {
    }

    public SimulateAwgFetcherConfig(
            FastJsonLongIdKey pointKey, Long tickPeriod, Long sampleFrequency, Double waveTableFrequency,
            double[] waveTable, String interpolation, Double amplitude, Double offset, Integer maxSamplesPerTick,
            Long misfireThreshold, Long misfirePostpone
    ) {
        this.pointKey = pointKey;
        this.tickPeriod = tickPeriod;
        this.sampleFrequency = sampleFrequency;
        this.waveTableFrequency = waveTableFrequency;
        this.waveTable = waveTable;
        this.interpolation = interpolation;
        this.amplitude = amplitude;
        this.offset = offset;
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

    public String getWaveTableFrequencyRem() {
        return waveTableFrequencyRem;
    }

    public void setWaveTableFrequencyRem(String waveTableFrequencyRem) {
        this.waveTableFrequencyRem = waveTableFrequencyRem;
    }

    public Double getWaveTableFrequency() {
        return waveTableFrequency;
    }

    public void setWaveTableFrequency(Double waveTableFrequency) {
        this.waveTableFrequency = waveTableFrequency;
    }

    public String getWaveTableRem() {
        return waveTableRem;
    }

    public void setWaveTableRem(String waveTableRem) {
        this.waveTableRem = waveTableRem;
    }

    public double[] getWaveTable() {
        return waveTable;
    }

    public void setWaveTable(double[] waveTable) {
        this.waveTable = waveTable;
    }

    public String getInterpolationRem() {
        return interpolationRem;
    }

    public void setInterpolationRem(String interpolationRem) {
        this.interpolationRem = interpolationRem;
    }

    public String getInterpolation() {
        return interpolation;
    }

    public void setInterpolation(String interpolation) {
        this.interpolation = interpolation;
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
        return "SimulateAwgFetcherConfig{" +
                "pointKeyRem='" + pointKeyRem + '\'' +
                ", pointKey=" + pointKey +
                ", tickPeriodRem='" + tickPeriodRem + '\'' +
                ", tickPeriod=" + tickPeriod +
                ", sampleFrequencyRem='" + sampleFrequencyRem + '\'' +
                ", sampleFrequency=" + sampleFrequency +
                ", waveTableFrequencyRem='" + waveTableFrequencyRem + '\'' +
                ", waveTableFrequency=" + waveTableFrequency +
                ", waveTableRem='" + waveTableRem + '\'' +
                ", waveTable=" + Arrays.toString(waveTable) +
                ", interpolationRem='" + interpolationRem + '\'' +
                ", interpolation='" + interpolation + '\'' +
                ", amplitudeRem='" + amplitudeRem + '\'' +
                ", amplitude=" + amplitude +
                ", offsetRem='" + offsetRem + '\'' +
                ", offset=" + offset +
                ", maxSamplesPerTickRem='" + maxSamplesPerTickRem + '\'' +
                ", maxSamplesPerTick=" + maxSamplesPerTick +
                ", misfireThresholdRem='" + misfireThresholdRem + '\'' +
                ", misfireThreshold=" + misfireThreshold +
                ", misfirePostponeRem='" + misfirePostponeRem + '\'' +
                ", misfirePostpone=" + misfirePostpone +
                '}';
    }
}
