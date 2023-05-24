package com.dwarfeng.fdr.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * FastJson 数据点对象。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class FastJsonPoint implements Bean {

    private static final long serialVersionUID = -4105403032098910366L;

    public static FastJsonPoint of(Point point) {
        if (Objects.isNull(point)) {
            return null;
        } else {
            return new FastJsonPoint(
                    FastJsonLongIdKey.of(point.getKey()),
                    point.getName(),
                    point.getRemark(),
                    point.isNormalKeepEnabled(),
                    point.isNormalPersistEnabled(),
                    point.isFilteredKeepEnabled(),
                    point.isFilteredPersistEnabled(),
                    point.isTriggeredKeepEnabled(),
                    point.isTriggeredPersistEnabled()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonLongIdKey key;

    @JSONField(name = "name", ordinal = 2)
    private String name;

    @JSONField(name = "remark", ordinal = 3)
    private String remark;

    @JSONField(name = "normal_keep_enabled", ordinal = 4)
    private boolean normalKeepEnabled;

    @JSONField(name = "normal_persist_enabled", ordinal = 5)
    private boolean normalPersistEnabled;

    @JSONField(name = "filtered_keep_enabled", ordinal = 6)
    private boolean filteredKeepEnabled;

    @JSONField(name = "filtered_persist_enabled", ordinal = 7)
    private boolean filteredPersistEnabled;

    @JSONField(name = "triggered_keep_enabled", ordinal = 8)
    private boolean triggeredKeepEnabled;

    @JSONField(name = "triggered_persist_enabled", ordinal = 9)
    private boolean triggeredPersistEnabled;

    public FastJsonPoint() {
    }

    public FastJsonPoint(
            FastJsonLongIdKey key, String name, String remark, boolean normalKeepEnabled, boolean normalPersistEnabled,
            boolean filteredKeepEnabled, boolean filteredPersistEnabled, boolean triggeredKeepEnabled,
            boolean triggeredPersistEnabled
    ) {
        this.key = key;
        this.name = name;
        this.remark = remark;
        this.normalKeepEnabled = normalKeepEnabled;
        this.normalPersistEnabled = normalPersistEnabled;
        this.filteredKeepEnabled = filteredKeepEnabled;
        this.filteredPersistEnabled = filteredPersistEnabled;
        this.triggeredKeepEnabled = triggeredKeepEnabled;
        this.triggeredPersistEnabled = triggeredPersistEnabled;
    }

    public FastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonLongIdKey key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isNormalKeepEnabled() {
        return normalKeepEnabled;
    }

    public void setNormalKeepEnabled(boolean normalKeepEnabled) {
        this.normalKeepEnabled = normalKeepEnabled;
    }

    public boolean isNormalPersistEnabled() {
        return normalPersistEnabled;
    }

    public void setNormalPersistEnabled(boolean normalPersistEnabled) {
        this.normalPersistEnabled = normalPersistEnabled;
    }

    public boolean isFilteredKeepEnabled() {
        return filteredKeepEnabled;
    }

    public void setFilteredKeepEnabled(boolean filteredKeepEnabled) {
        this.filteredKeepEnabled = filteredKeepEnabled;
    }

    public boolean isFilteredPersistEnabled() {
        return filteredPersistEnabled;
    }

    public void setFilteredPersistEnabled(boolean filteredPersistEnabled) {
        this.filteredPersistEnabled = filteredPersistEnabled;
    }

    public boolean isTriggeredKeepEnabled() {
        return triggeredKeepEnabled;
    }

    public void setTriggeredKeepEnabled(boolean triggeredKeepEnabled) {
        this.triggeredKeepEnabled = triggeredKeepEnabled;
    }

    public boolean isTriggeredPersistEnabled() {
        return triggeredPersistEnabled;
    }

    public void setTriggeredPersistEnabled(boolean triggeredPersistEnabled) {
        this.triggeredPersistEnabled = triggeredPersistEnabled;
    }

    @Override
    public String toString() {
        return "FastJsonPoint{" +
                "key=" + key +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", normalKeepEnabled=" + normalKeepEnabled +
                ", normalPersistEnabled=" + normalPersistEnabled +
                ", filteredKeepEnabled=" + filteredKeepEnabled +
                ", filteredPersistEnabled=" + filteredPersistEnabled +
                ", triggeredKeepEnabled=" + triggeredKeepEnabled +
                ", triggeredPersistEnabled=" + triggeredPersistEnabled +
                '}';
    }
}
