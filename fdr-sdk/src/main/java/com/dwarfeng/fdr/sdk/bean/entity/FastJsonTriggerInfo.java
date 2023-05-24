package com.dwarfeng.fdr.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.entity.TriggerInfo;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * FastJson 触发器信息。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class FastJsonTriggerInfo implements Bean {

    private static final long serialVersionUID = -6226895690204371122L;

    public static FastJsonTriggerInfo of(TriggerInfo triggerInfo) {
        if (Objects.isNull(triggerInfo)) {
            return null;
        } else {
            return new FastJsonTriggerInfo(
                    FastJsonLongIdKey.of(triggerInfo.getKey()),
                    FastJsonLongIdKey.of(triggerInfo.getPointKey()),
                    triggerInfo.getIndex(),
                    triggerInfo.isEnabled(),
                    triggerInfo.getType(),
                    triggerInfo.getParam(),
                    triggerInfo.getRemark()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonLongIdKey key;

    @JSONField(name = "point_key", ordinal = 2)
    private FastJsonLongIdKey pointKey;

    @JSONField(name = "index", ordinal = 3)
    private int index;

    @JSONField(name = "enabled", ordinal = 4)
    private boolean enabled;

    @JSONField(name = "type", ordinal = 5)
    private String type;

    @JSONField(name = "param", ordinal = 6)
    private String param;

    @JSONField(name = "remark", ordinal = 7)
    private String remark;

    public FastJsonTriggerInfo() {
    }

    public FastJsonTriggerInfo(
            FastJsonLongIdKey key, FastJsonLongIdKey pointKey, int index, boolean enabled, String type, String param,
            String remark
    ) {
        this.key = key;
        this.pointKey = pointKey;
        this.index = index;
        this.enabled = enabled;
        this.type = type;
        this.param = param;
        this.remark = remark;
    }

    public FastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonLongIdKey key) {
        this.key = key;
    }

    public FastJsonLongIdKey getPointKey() {
        return pointKey;
    }

    public void setPointKey(FastJsonLongIdKey pointKey) {
        this.pointKey = pointKey;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "FastJsonTriggerInfo{" +
                "key=" + key +
                ", pointKey=" + pointKey +
                ", index=" + index +
                ", enabled=" + enabled +
                ", type='" + type + '\'' +
                ", param='" + param + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
