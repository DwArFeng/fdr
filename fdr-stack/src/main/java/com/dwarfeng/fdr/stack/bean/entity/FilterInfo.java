package com.dwarfeng.fdr.stack.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 过滤器信息。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class FilterInfo implements Entity<LongIdKey> {

    private static final long serialVersionUID = 1718556999742209362L;

    private LongIdKey key;
    private LongIdKey pointKey;
    private int index;
    private boolean enabled;
    private String type;
    private String param;
    private String remark;

    public FilterInfo() {
    }

    public FilterInfo(
            LongIdKey key, LongIdKey pointKey, int index, boolean enabled, String type, String param, String remark
    ) {
        this.key = key;
        this.pointKey = pointKey;
        this.index = index;
        this.enabled = enabled;
        this.type = type;
        this.param = param;
        this.remark = remark;
    }

    @Override
    public LongIdKey getKey() {
        return key;
    }

    @Override
    public void setKey(LongIdKey key) {
        this.key = key;
    }

    public LongIdKey getPointKey() {
        return pointKey;
    }

    public void setPointKey(LongIdKey pointKey) {
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
        return "FilterInfo{" +
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
