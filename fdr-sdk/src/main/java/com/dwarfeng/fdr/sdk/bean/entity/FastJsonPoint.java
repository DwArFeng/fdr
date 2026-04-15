package com.dwarfeng.fdr.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Date;
import java.util.Objects;

/**
 * FastJson 数据点对象。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class FastJsonPoint implements Bean {

    private static final long serialVersionUID = 2505434950655520154L;

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
                    point.isTriggeredPersistEnabled(),
                    point.getRecordMemorySize(),
                    point.getReservedStringAlpha(),
                    point.getReservedStringBravo(),
                    point.getReservedStringCharlie(),
                    point.getReservedStringDelta(),
                    point.getReservedLongAlpha(),
                    point.getReservedLongBravo(),
                    point.getReservedIntegerAlpha(),
                    point.getReservedIntegerBravo(),
                    point.getReservedBooleanAlpha(),
                    point.getReservedBooleanBravo(),
                    point.getReservedDateAlpha(),
                    point.getReservedDateBravo()
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

    @JSONField(name = "record_memory_size", ordinal = 10)
    private int recordMemorySize;

    // region 预留字段

    @JSONField(name = "reserved_string_alpha", ordinal = 11)
    private String reservedStringAlpha;

    @JSONField(name = "reserved_string_bravo", ordinal = 12)
    private String reservedStringBravo;

    @JSONField(name = "reserved_string_charlie", ordinal = 13)
    private String reservedStringCharlie;

    @JSONField(name = "reserved_string_delta", ordinal = 14)
    private String reservedStringDelta;

    @JSONField(name = "reserved_long_alpha", ordinal = 15)
    private Long reservedLongAlpha;

    @JSONField(name = "reserved_long_bravo", ordinal = 16)
    private Long reservedLongBravo;

    @JSONField(name = "reserved_integer_alpha", ordinal = 17)
    private Integer reservedIntegerAlpha;

    @JSONField(name = "reserved_integer_bravo", ordinal = 18)
    private Integer reservedIntegerBravo;

    @JSONField(name = "reserved_boolean_alpha", ordinal = 19)
    private Boolean reservedBooleanAlpha;

    @JSONField(name = "reserved_boolean_bravo", ordinal = 20)
    private Boolean reservedBooleanBravo;

    @JSONField(name = "reserved_date_alpha", ordinal = 21)
    private Date reservedDateAlpha;

    @JSONField(name = "reserved_date_bravo", ordinal = 22)
    private Date reservedDateBravo;

    // endregion

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

    public FastJsonPoint(
            FastJsonLongIdKey key, String name, String remark, boolean normalKeepEnabled, boolean normalPersistEnabled,
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

    public FastJsonPoint(
            FastJsonLongIdKey key, String name, String remark, boolean normalKeepEnabled, boolean normalPersistEnabled,
            boolean filteredKeepEnabled, boolean filteredPersistEnabled, boolean triggeredKeepEnabled,
            boolean triggeredPersistEnabled, int recordMemorySize, String reservedStringAlpha,
            String reservedStringBravo, String reservedStringCharlie, String reservedStringDelta,
            Long reservedLongAlpha, Long reservedLongBravo, Integer reservedIntegerAlpha, Integer reservedIntegerBravo,
            Boolean reservedBooleanAlpha, Boolean reservedBooleanBravo, Date reservedDateAlpha, Date reservedDateBravo
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
        this.recordMemorySize = recordMemorySize;
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

    public int getRecordMemorySize() {
        return recordMemorySize;
    }

    public void setRecordMemorySize(int recordMemorySize) {
        this.recordMemorySize = recordMemorySize;
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
                ", recordMemorySize=" + recordMemorySize +
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
