package com.dwarfeng.fdr.stack.bean.entity;

import com.dwarfeng.fdr.stack.bean.key.UuidKey;

/**
 * 数据点。
 */
public class Point implements Entity<UuidKey> {

    private static final long serialVersionUID = 9116129592160719291L;

    /**
     * 主键。
     */
    private UuidKey key;
    /**
     * 所属分类主键。
     */
    private UuidKey categoryKey;
    /**
     * 数据点的名称。
     */
    private String name;
    /**
     * 备注。
     */
    private String remark;
    /**
     * 是否启用持久化。
     */
    private boolean persistenceEnabled;
    /**
     * 是否启用实时化。
     */
    private boolean realtimeEnabled;

    public Point() {
    }

    public Point(UuidKey key, UuidKey categoryKey, String name, String remark, boolean persistenceEnabled, boolean realtimeEnabled) {
        this.key = key;
        this.categoryKey = categoryKey;
        this.name = name;
        this.remark = remark;
        this.persistenceEnabled = persistenceEnabled;
        this.realtimeEnabled = realtimeEnabled;
    }

    @Override
    public UuidKey getKey() {
        return key;
    }

    @Override
    public void setKey(UuidKey key) {
        this.key = key;
    }

    public UuidKey getCategoryKey() {
        return categoryKey;
    }

    public void setCategoryKey(UuidKey categoryKey) {
        this.categoryKey = categoryKey;
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

    public boolean isPersistenceEnabled() {
        return persistenceEnabled;
    }

    public void setPersistenceEnabled(boolean persistenceEnabled) {
        this.persistenceEnabled = persistenceEnabled;
    }

    public boolean isRealtimeEnabled() {
        return realtimeEnabled;
    }

    public void setRealtimeEnabled(boolean realtimeEnabled) {
        this.realtimeEnabled = realtimeEnabled;
    }

    @Override
    public String toString() {
        return "Point{" +
                "key=" + key +
                ", categoryKey=" + categoryKey +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", persistenceEnabled=" + persistenceEnabled +
                ", realtimeEnabled=" + realtimeEnabled +
                '}';
    }
}