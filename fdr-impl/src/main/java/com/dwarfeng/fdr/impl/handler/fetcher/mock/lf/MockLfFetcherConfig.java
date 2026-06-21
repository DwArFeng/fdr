package com.dwarfeng.fdr.impl.handler.fetcher.mock.lf;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

/**
 * 低频模拟抓取器配置。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
public class MockLfFetcherConfig implements Bean {

    private static final long serialVersionUID = 5780858502302835891L;

    @JSONField(name = "#point_key", ordinal = 1, deserialize = false)
    private String pointKeyRem = "抓取数据写入的点位主键。";

    @JSONField(name = "point_key", ordinal = 2)
    private FastJsonLongIdKey pointKey;

    @JSONField(name = "#poll_type", ordinal = 3, deserialize = false)
    private String pollTypeRem = "轮询类型，合法值为 " +
            String.join("、", MockLfFetcherConstants.pollTypeSpace()) + "。";

    @JSONField(name = "poll_type", ordinal = 4)
    private String pollType;

    @JSONField(name = "#poll_setting", ordinal = 5, deserialize = false)
    private String pollSettingRem = "轮询参数。fixed_rate/fixed_delay 使用毫秒数；cron 使用 cron 表达式；" +
            "safe_* 使用 原参数;misfire_threshold;misfire_postpone。";

    @JSONField(name = "poll_setting", ordinal = 6)
    private String pollSetting;

    @JSONField(name = "#generator_type", ordinal = 7, deserialize = false)
    private String generatorTypeRem = "数据生成类型，合法值为 " +
            String.join("、", MockLfFetcherConstants.generatorTypeSpace()) + "。";

    @JSONField(name = "generator_type", ordinal = 8)
    private String generatorType;

    @JSONField(name = "#random_seed", ordinal = 9, deserialize = false)
    private String randomSeedRem = "随机种子。为空字符串时使用随机种子；填写 long 值时生成可复现随机序列。";

    @JSONField(name = "random_seed", ordinal = 10)
    private Long randomSeed;

    @JSONField(name = "#fetch_before_delay", ordinal = 11, deserialize = false)
    private String fetchBeforeDelayRem = "每次生成记录前的延迟毫秒数，0 表示不延迟。";

    @JSONField(name = "fetch_before_delay", ordinal = 12)
    private Long fetchBeforeDelay;

    @JSONField(name = "#fetch_after_delay", ordinal = 13, deserialize = false)
    private String fetchAfterDelayRem = "每次生成记录后的延迟毫秒数，0 表示不延迟。";

    @JSONField(name = "fetch_after_delay", ordinal = 14)
    private Long fetchAfterDelay;

    public MockLfFetcherConfig() {
    }

    public MockLfFetcherConfig(
            FastJsonLongIdKey pointKey, String pollType, String pollSetting, String generatorType, Long randomSeed,
            Long fetchBeforeDelay, Long fetchAfterDelay
    ) {
        this.pointKey = pointKey;
        this.pollType = pollType;
        this.pollSetting = pollSetting;
        this.generatorType = generatorType;
        this.randomSeed = randomSeed;
        this.fetchBeforeDelay = fetchBeforeDelay;
        this.fetchAfterDelay = fetchAfterDelay;
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

    public String getPollTypeRem() {
        return pollTypeRem;
    }

    public void setPollTypeRem(String pollTypeRem) {
        this.pollTypeRem = pollTypeRem;
    }

    public String getPollType() {
        return pollType;
    }

    public void setPollType(String pollType) {
        this.pollType = pollType;
    }

    public String getPollSettingRem() {
        return pollSettingRem;
    }

    public void setPollSettingRem(String pollSettingRem) {
        this.pollSettingRem = pollSettingRem;
    }

    public String getPollSetting() {
        return pollSetting;
    }

    public void setPollSetting(String pollSetting) {
        this.pollSetting = pollSetting;
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

    public String getFetchBeforeDelayRem() {
        return fetchBeforeDelayRem;
    }

    public void setFetchBeforeDelayRem(String fetchBeforeDelayRem) {
        this.fetchBeforeDelayRem = fetchBeforeDelayRem;
    }

    public Long getFetchBeforeDelay() {
        return fetchBeforeDelay;
    }

    public void setFetchBeforeDelay(Long fetchBeforeDelay) {
        this.fetchBeforeDelay = fetchBeforeDelay;
    }

    public String getFetchAfterDelayRem() {
        return fetchAfterDelayRem;
    }

    public void setFetchAfterDelayRem(String fetchAfterDelayRem) {
        this.fetchAfterDelayRem = fetchAfterDelayRem;
    }

    public Long getFetchAfterDelay() {
        return fetchAfterDelay;
    }

    public void setFetchAfterDelay(Long fetchAfterDelay) {
        this.fetchAfterDelay = fetchAfterDelay;
    }

    @Override
    public String toString() {
        return "MockLfFetcherConfig{" +
                "pointKeyRem='" + pointKeyRem + '\'' +
                ", pointKey=" + pointKey +
                ", pollTypeRem='" + pollTypeRem + '\'' +
                ", pollType='" + pollType + '\'' +
                ", pollSettingRem='" + pollSettingRem + '\'' +
                ", pollSetting='" + pollSetting + '\'' +
                ", generatorTypeRem='" + generatorTypeRem + '\'' +
                ", generatorType='" + generatorType + '\'' +
                ", randomSeedRem='" + randomSeedRem + '\'' +
                ", randomSeed=" + randomSeed +
                ", fetchBeforeDelayRem='" + fetchBeforeDelayRem + '\'' +
                ", fetchBeforeDelay=" + fetchBeforeDelay +
                ", fetchAfterDelayRem='" + fetchAfterDelayRem + '\'' +
                ", fetchAfterDelay=" + fetchAfterDelay +
                '}';
    }
}
