package com.dwarfeng.fdr.stack.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;

/**
 * 数据点。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class Point implements Entity<LongIdKey> {

    private static final long serialVersionUID = -986941622654987835L;

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

    // region 预留字段

    /**
     * 预留字段 String 类型 1#。
     *
     * @since 2.3.2
     */
    private String reservedStringAlpha;

    /**
     * 预留字段 String 类型 2#。
     *
     * @since 2.3.2
     */
    private String reservedStringBravo;

    /**
     * 预留字段 String 类型 3#。
     *
     * @since 2.3.2
     */
    private String reservedStringCharlie;

    /**
     * 预留字段 String 类型 4#。
     *
     * @since 2.3.2
     */
    private String reservedStringDelta;

    /**
     * 预留字段 Long 类型 1#。
     *
     * @since 2.3.2
     */
    private Long reservedLongAlpha;

    /**
     * 预留字段 Long 类型 2#。
     *
     * @since 2.3.2
     */
    private Long reservedLongBravo;

    /**
     * 预留字段 Integer 类型 1#。
     *
     * @since 2.3.2
     */
    private Integer reservedIntegerAlpha;

    /**
     * 预留字段 Integer 类型 1#。
     *
     * @since 2.3.2
     */
    private Integer reservedIntegerBravo;

    /**
     * 预留字段 Boolean 类型 1#。
     *
     * @since 2.3.2
     */
    private Boolean reservedBooleanAlpha;

    /**
     * 预留字段 Boolean 类型 2#。
     *
     * @since 2.3.2
     */
    private Boolean reservedBooleanBravo;

    /**
     * 预留字段 Date 类型 1#。
     *
     * @since 2.3.2
     */
    private Date reservedDateAlpha;

    /**
     * 预留字段 Date 类型 2#。
     *
     * @since 2.3.2
     */
    private Date reservedDateBravo;

    // endregion

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

    public Point(
            LongIdKey key, String name, String remark, boolean normalKeepEnabled, boolean normalPersistEnabled,
            boolean filteredKeepEnabled, boolean filteredPersistEnabled, boolean triggeredKeepEnabled,
            boolean triggeredPersistEnabled, String reservedStringAlpha, String reservedStringBravo,
            String reservedStringCharlie, String reservedStringDelta, Long reservedLongAlpha, Long reservedLongBravo,
            Integer reservedIntegerAlpha, Integer reservedIntegerBravo, Boolean reservedBooleanAlpha,
            Boolean reservedBooleanBravo, Date reservedDateAlpha, Date reservedDateBravo
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
        this.reservedStringAlpha = reservedStringAlpha;
        this.reservedStringBravo = reservedStringBravo;
        this.reservedStringCharlie = reservedStringCharlie;
        this.reservedStringDelta = reservedStringDelta;
        this.reservedLongAlpha = reservedLongAlpha;
        this.reservedLongBravo = reservedLongBravo;
        this.reservedIntegerAlpha = reservedIntegerAlpha;
        this.reservedIntegerBravo = reservedIntegerBravo;
        this.reservedBooleanAlpha = reservedBooleanAlpha;
        this.reservedBooleanBravo = reservedBooleanBravo;
        this.reservedDateAlpha = reservedDateAlpha;
        this.reservedDateBravo = reservedDateBravo;
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

    public String getReservedStringAlpha() {
        return reservedStringAlpha;
    }

    public void setReservedStringAlpha(String reservedStringAlpha) {
        this.reservedStringAlpha = reservedStringAlpha;
    }

    public String getReservedStringBravo() {
        return reservedStringBravo;
    }

    public void setReservedStringBravo(String reservedStringBravo) {
        this.reservedStringBravo = reservedStringBravo;
    }

    public String getReservedStringCharlie() {
        return reservedStringCharlie;
    }

    public void setReservedStringCharlie(String reservedStringCharlie) {
        this.reservedStringCharlie = reservedStringCharlie;
    }

    public String getReservedStringDelta() {
        return reservedStringDelta;
    }

    public void setReservedStringDelta(String reservedStringDelta) {
        this.reservedStringDelta = reservedStringDelta;
    }

    public Long getReservedLongAlpha() {
        return reservedLongAlpha;
    }

    public void setReservedLongAlpha(Long reservedLongAlpha) {
        this.reservedLongAlpha = reservedLongAlpha;
    }

    public Long getReservedLongBravo() {
        return reservedLongBravo;
    }

    public void setReservedLongBravo(Long reservedLongBravo) {
        this.reservedLongBravo = reservedLongBravo;
    }

    public Integer getReservedIntegerAlpha() {
        return reservedIntegerAlpha;
    }

    public void setReservedIntegerAlpha(Integer reservedIntegerAlpha) {
        this.reservedIntegerAlpha = reservedIntegerAlpha;
    }

    public Integer getReservedIntegerBravo() {
        return reservedIntegerBravo;
    }

    public void setReservedIntegerBravo(Integer reservedIntegerBravo) {
        this.reservedIntegerBravo = reservedIntegerBravo;
    }

    public Boolean getReservedBooleanAlpha() {
        return reservedBooleanAlpha;
    }

    public void setReservedBooleanAlpha(Boolean reservedBooleanAlpha) {
        this.reservedBooleanAlpha = reservedBooleanAlpha;
    }

    public Boolean getReservedBooleanBravo() {
        return reservedBooleanBravo;
    }

    public void setReservedBooleanBravo(Boolean reservedBooleanBravo) {
        this.reservedBooleanBravo = reservedBooleanBravo;
    }

    public Date getReservedDateAlpha() {
        return reservedDateAlpha;
    }

    public void setReservedDateAlpha(Date reservedDateAlpha) {
        this.reservedDateAlpha = reservedDateAlpha;
    }

    public Date getReservedDateBravo() {
        return reservedDateBravo;
    }

    public void setReservedDateBravo(Date reservedDateBravo) {
        this.reservedDateBravo = reservedDateBravo;
    }

    @SuppressWarnings("DuplicatedCode")
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
                ", reservedStringAlpha='" + reservedStringAlpha + '\'' +
                ", reservedStringBravo='" + reservedStringBravo + '\'' +
                ", reservedStringCharlie='" + reservedStringCharlie + '\'' +
                ", reservedStringDelta='" + reservedStringDelta + '\'' +
                ", reservedLongAlpha=" + reservedLongAlpha +
                ", reservedLongBravo=" + reservedLongBravo +
                ", reservedIntegerAlpha=" + reservedIntegerAlpha +
                ", reservedIntegerBravo=" + reservedIntegerBravo +
                ", reservedBooleanAlpha=" + reservedBooleanAlpha +
                ", reservedBooleanBravo=" + reservedBooleanBravo +
                ", reservedDateAlpha=" + reservedDateAlpha +
                ", reservedDateBravo=" + reservedDateBravo +
                '}';
    }
}
