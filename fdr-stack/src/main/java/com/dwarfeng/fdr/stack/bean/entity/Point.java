package com.dwarfeng.fdr.stack.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 数据点。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class Point implements Entity<LongIdKey> {

    private static final long serialVersionUID = -1945106807046843385L;

    /**
     * 主键。
     */
    private LongIdKey key;

    /**
     * 数据点的名称。
     */
    private String name;

    /**
     * 备注。
     */
    private String remark;

    /**
     * 一般数据保持使能。
     *
     * @since 2.0.0
     */
    private boolean normalKeepEnabled;

    /**
     * 一般数据持久使能。
     *
     * @since 2.0.0
     */
    private boolean normalPersistEnabled;

    /**
     * 被过滤数据保持使能。
     *
     * @since 2.0.0
     */
    private boolean filteredKeepEnabled;

    /**
     * 被过滤数据持久使能。
     *
     * @since 2.0.0
     */
    private boolean filteredPersistEnabled;

    /**
     * 被触发数据保持使能。
     *
     * @since 2.0.0
     */
    private boolean triggeredKeepEnabled;

    /**
     * 被触发数据持久使能。
     *
     * @since 2.0.0
     */
    private boolean triggeredPersistEnabled;

    public Point() {
    }

    public Point(
            LongIdKey key, String name, String remark, boolean normalKeepEnabled, boolean normalPersistEnabled,
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

    @Override
    public LongIdKey getKey() {
        return key;
    }

    @Override
    public void setKey(LongIdKey key) {
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
        return "Point{" +
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
