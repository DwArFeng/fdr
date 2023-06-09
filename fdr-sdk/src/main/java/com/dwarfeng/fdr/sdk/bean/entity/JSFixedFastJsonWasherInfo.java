package com.dwarfeng.fdr.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.entity.WasherInfo;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * JSFixed FastJson 清洗器信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class JSFixedFastJsonWasherInfo implements Bean {

    private static final long serialVersionUID = -3121954236286912498L;

    public static JSFixedFastJsonWasherInfo of(WasherInfo washerInfo) {
        if (Objects.isNull(washerInfo)) {
            return null;
        } else {
            return new JSFixedFastJsonWasherInfo(
                    JSFixedFastJsonLongIdKey.of(washerInfo.getKey()),
                    JSFixedFastJsonLongIdKey.of(washerInfo.getPointKey()),
                    washerInfo.getIndex(),
                    washerInfo.isEnabled(),
                    washerInfo.isPreFilter(),
                    washerInfo.getType(),
                    washerInfo.getParam(),
                    washerInfo.getRemark()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "point_key", ordinal = 2)
    private JSFixedFastJsonLongIdKey pointKey;

    @JSONField(name = "index", ordinal = 3)
    private int index;

    @JSONField(name = "enabled", ordinal = 4)
    private boolean enabled;

    @JSONField(name = "pre_filter", ordinal = 5)
    private boolean preFilter;

    @JSONField(name = "type", ordinal = 6)
    private String type;

    @JSONField(name = "param", ordinal = 7)
    private String param;

    @JSONField(name = "remark", ordinal = 8)
    private String remark;

    public JSFixedFastJsonWasherInfo() {
    }

    public JSFixedFastJsonWasherInfo(
            JSFixedFastJsonLongIdKey key, JSFixedFastJsonLongIdKey pointKey, int index, boolean enabled,
            boolean preFilter, String type, String param, String remark
    ) {
        this.key = key;
        this.pointKey = pointKey;
        this.index = index;
        this.enabled = enabled;
        this.preFilter = preFilter;
        this.type = type;
        this.param = param;
        this.remark = remark;
    }

    public JSFixedFastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonLongIdKey key) {
        this.key = key;
    }

    public JSFixedFastJsonLongIdKey getPointKey() {
        return pointKey;
    }

    public void setPointKey(JSFixedFastJsonLongIdKey pointKey) {
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

    public boolean isPreFilter() {
        return preFilter;
    }

    public void setPreFilter(boolean preFilter) {
        this.preFilter = preFilter;
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
        return "JSFixedFastJsonWasherInfo{" +
                "key=" + key +
                ", pointKey=" + pointKey +
                ", index=" + index +
                ", enabled=" + enabled +
                ", preFilter=" + preFilter +
                ", type='" + type + '\'' +
                ", param='" + param + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
