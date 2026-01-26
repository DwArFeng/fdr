package com.dwarfeng.fdr.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;

/**
 * Point 组合查询信息。
 *
 * @author WFM
 * @since 2.3.2
 */
public class PointCompositeLookupInfo implements Dto {

    private static final long serialVersionUID = 3791358463389819704L;

    /**
     * 名称模糊匹配。
     */
    private String namePattern;

    /**
     * 备注模糊匹配。
     */
    private String remarkPattern;

    /**
     * 一般数据保持使能。
     */
    private Boolean normalKeepEnabled;

    /**
     * 一般数据持久使能。
     */
    private Boolean normalPersistEnabled;

    /**
     * 被过滤数据保持使能。
     */
    private Boolean filteredKeepEnabled;

    /**
     * 被过滤数据持久使能。
     */
    private Boolean filteredPersistEnabled;

    /**
     * 被触发数据保持使能。
     */
    private Boolean triggeredKeepEnabled;

    /**
     * 被触发数据持久使能。
     */
    private Boolean triggeredPersistEnabled;

    /**
     * 预留字段 String 类型 1# 模糊匹配。
     */
    private String reservedStringAlphaPattern;

    /**
     * 预留字段 String 类型 2# 模糊匹配。
     */
    private String reservedStringBravoPattern;

    /**
     * 预留字段 String 类型 3# 模糊匹配。
     */
    private String reservedStringCharliePattern;

    /**
     * 预留字段 String 类型 4# 模糊匹配。
     */
    private String reservedStringDeltaPattern;

    /**
     * 预留字段 Long 类型 1# 最小值。
     */
    private Long reservedLongAlphaMin;

    /**
     * 预留字段 Long 类型 1# 最大值。
     */
    private Long reservedLongAlphaMax;

    /**
     * 预留字段 Long 类型 2# 最小值。
     */
    private Long reservedLongBravoMin;

    /**
     * 预留字段 Long 类型 2# 最大值。
     */
    private Long reservedLongBravoMax;

    /**
     * 预留字段 Integer 类型 1# 最小值。
     */
    private Integer reservedIntegerAlphaMin;

    /**
     * 预留字段 Integer 类型 1# 最大值。
     */
    private Integer reservedIntegerAlphaMax;

    /**
     * 预留字段 Integer 类型 2# 最小值。
     */
    private Integer reservedIntegerBravoMin;

    /**
     * 预留字段 Integer 类型 2# 最大值。
     */
    private Integer reservedIntegerBravoMax;

    /**
     * 预留字段 Boolean 类型 1#。
     */
    private Boolean reservedBooleanAlpha;

    /**
     * 预留字段 Boolean 类型 2#。
     */
    private Boolean reservedBooleanBravo;

    /**
     * 预留字段 Date 类型 1# 最小值。
     */
    private Date reservedDateAlphaMin;

    /**
     * 预留字段 Date 类型 1# 最大值。
     */
    private Date reservedDateAlphaMax;

    /**
     * 预留字段 Date 类型 2# 最小值。
     */
    private Date reservedDateBravoMin;

    /**
     * 预留字段 Date 类型 2# 最大值。
     */
    private Date reservedDateBravoMax;

    public PointCompositeLookupInfo() {
    }

    public PointCompositeLookupInfo(
            String namePattern, String remarkPattern, Boolean normalKeepEnabled, Boolean normalPersistEnabled,
            Boolean filteredKeepEnabled, Boolean filteredPersistEnabled, Boolean triggeredKeepEnabled,
            Boolean triggeredPersistEnabled, String reservedStringAlphaPattern, String reservedStringBravoPattern,
            String reservedStringCharliePattern, String reservedStringDeltaPattern, Long reservedLongAlphaMin,
            Long reservedLongAlphaMax, Long reservedLongBravoMin, Long reservedLongBravoMax,
            Integer reservedIntegerAlphaMin, Integer reservedIntegerAlphaMax, Integer reservedIntegerBravoMin,
            Integer reservedIntegerBravoMax, Boolean reservedBooleanAlpha, Boolean reservedBooleanBravo,
            Date reservedDateAlphaMin, Date reservedDateAlphaMax, Date reservedDateBravoMin, Date reservedDateBravoMax
    ) {
        this.namePattern = namePattern;
        this.remarkPattern = remarkPattern;
        this.normalKeepEnabled = normalKeepEnabled;
        this.normalPersistEnabled = normalPersistEnabled;
        this.filteredKeepEnabled = filteredKeepEnabled;
        this.filteredPersistEnabled = filteredPersistEnabled;
        this.triggeredKeepEnabled = triggeredKeepEnabled;
        this.triggeredPersistEnabled = triggeredPersistEnabled;
        this.reservedStringAlphaPattern = reservedStringAlphaPattern;
        this.reservedStringBravoPattern = reservedStringBravoPattern;
        this.reservedStringCharliePattern = reservedStringCharliePattern;
        this.reservedStringDeltaPattern = reservedStringDeltaPattern;
        this.reservedLongAlphaMin = reservedLongAlphaMin;
        this.reservedLongAlphaMax = reservedLongAlphaMax;
        this.reservedLongBravoMin = reservedLongBravoMin;
        this.reservedLongBravoMax = reservedLongBravoMax;
        this.reservedIntegerAlphaMin = reservedIntegerAlphaMin;
        this.reservedIntegerAlphaMax = reservedIntegerAlphaMax;
        this.reservedIntegerBravoMin = reservedIntegerBravoMin;
        this.reservedIntegerBravoMax = reservedIntegerBravoMax;
        this.reservedBooleanAlpha = reservedBooleanAlpha;
        this.reservedBooleanBravo = reservedBooleanBravo;
        this.reservedDateAlphaMin = reservedDateAlphaMin;
        this.reservedDateAlphaMax = reservedDateAlphaMax;
        this.reservedDateBravoMin = reservedDateBravoMin;
        this.reservedDateBravoMax = reservedDateBravoMax;
    }

    public String getNamePattern() {
        return namePattern;
    }

    public void setNamePattern(String namePattern) {
        this.namePattern = namePattern;
    }

    public String getRemarkPattern() {
        return remarkPattern;
    }

    public void setRemarkPattern(String remarkPattern) {
        this.remarkPattern = remarkPattern;
    }

    public Boolean getNormalKeepEnabled() {
        return normalKeepEnabled;
    }

    public void setNormalKeepEnabled(Boolean normalKeepEnabled) {
        this.normalKeepEnabled = normalKeepEnabled;
    }

    public Boolean getNormalPersistEnabled() {
        return normalPersistEnabled;
    }

    public void setNormalPersistEnabled(Boolean normalPersistEnabled) {
        this.normalPersistEnabled = normalPersistEnabled;
    }

    public Boolean getFilteredKeepEnabled() {
        return filteredKeepEnabled;
    }

    public void setFilteredKeepEnabled(Boolean filteredKeepEnabled) {
        this.filteredKeepEnabled = filteredKeepEnabled;
    }

    public Boolean getFilteredPersistEnabled() {
        return filteredPersistEnabled;
    }

    public void setFilteredPersistEnabled(Boolean filteredPersistEnabled) {
        this.filteredPersistEnabled = filteredPersistEnabled;
    }

    public Boolean getTriggeredKeepEnabled() {
        return triggeredKeepEnabled;
    }

    public void setTriggeredKeepEnabled(Boolean triggeredKeepEnabled) {
        this.triggeredKeepEnabled = triggeredKeepEnabled;
    }

    public Boolean getTriggeredPersistEnabled() {
        return triggeredPersistEnabled;
    }

    public void setTriggeredPersistEnabled(Boolean triggeredPersistEnabled) {
        this.triggeredPersistEnabled = triggeredPersistEnabled;
    }

    public String getReservedStringAlphaPattern() {
        return reservedStringAlphaPattern;
    }

    public void setReservedStringAlphaPattern(String reservedStringAlphaPattern) {
        this.reservedStringAlphaPattern = reservedStringAlphaPattern;
    }

    public String getReservedStringBravoPattern() {
        return reservedStringBravoPattern;
    }

    public void setReservedStringBravoPattern(String reservedStringBravoPattern) {
        this.reservedStringBravoPattern = reservedStringBravoPattern;
    }

    public String getReservedStringCharliePattern() {
        return reservedStringCharliePattern;
    }

    public void setReservedStringCharliePattern(String reservedStringCharliePattern) {
        this.reservedStringCharliePattern = reservedStringCharliePattern;
    }

    public String getReservedStringDeltaPattern() {
        return reservedStringDeltaPattern;
    }

    public void setReservedStringDeltaPattern(String reservedStringDeltaPattern) {
        this.reservedStringDeltaPattern = reservedStringDeltaPattern;
    }

    public Long getReservedLongAlphaMin() {
        return reservedLongAlphaMin;
    }

    public void setReservedLongAlphaMin(Long reservedLongAlphaMin) {
        this.reservedLongAlphaMin = reservedLongAlphaMin;
    }

    public Long getReservedLongAlphaMax() {
        return reservedLongAlphaMax;
    }

    public void setReservedLongAlphaMax(Long reservedLongAlphaMax) {
        this.reservedLongAlphaMax = reservedLongAlphaMax;
    }

    public Long getReservedLongBravoMin() {
        return reservedLongBravoMin;
    }

    public void setReservedLongBravoMin(Long reservedLongBravoMin) {
        this.reservedLongBravoMin = reservedLongBravoMin;
    }

    public Long getReservedLongBravoMax() {
        return reservedLongBravoMax;
    }

    public void setReservedLongBravoMax(Long reservedLongBravoMax) {
        this.reservedLongBravoMax = reservedLongBravoMax;
    }

    public Integer getReservedIntegerAlphaMin() {
        return reservedIntegerAlphaMin;
    }

    public void setReservedIntegerAlphaMin(Integer reservedIntegerAlphaMin) {
        this.reservedIntegerAlphaMin = reservedIntegerAlphaMin;
    }

    public Integer getReservedIntegerAlphaMax() {
        return reservedIntegerAlphaMax;
    }

    public void setReservedIntegerAlphaMax(Integer reservedIntegerAlphaMax) {
        this.reservedIntegerAlphaMax = reservedIntegerAlphaMax;
    }

    public Integer getReservedIntegerBravoMin() {
        return reservedIntegerBravoMin;
    }

    public void setReservedIntegerBravoMin(Integer reservedIntegerBravoMin) {
        this.reservedIntegerBravoMin = reservedIntegerBravoMin;
    }

    public Integer getReservedIntegerBravoMax() {
        return reservedIntegerBravoMax;
    }

    public void setReservedIntegerBravoMax(Integer reservedIntegerBravoMax) {
        this.reservedIntegerBravoMax = reservedIntegerBravoMax;
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

    public Date getReservedDateAlphaMin() {
        return reservedDateAlphaMin;
    }

    public void setReservedDateAlphaMin(Date reservedDateAlphaMin) {
        this.reservedDateAlphaMin = reservedDateAlphaMin;
    }

    public Date getReservedDateAlphaMax() {
        return reservedDateAlphaMax;
    }

    public void setReservedDateAlphaMax(Date reservedDateAlphaMax) {
        this.reservedDateAlphaMax = reservedDateAlphaMax;
    }

    public Date getReservedDateBravoMin() {
        return reservedDateBravoMin;
    }

    public void setReservedDateBravoMin(Date reservedDateBravoMin) {
        this.reservedDateBravoMin = reservedDateBravoMin;
    }

    public Date getReservedDateBravoMax() {
        return reservedDateBravoMax;
    }

    public void setReservedDateBravoMax(Date reservedDateBravoMax) {
        this.reservedDateBravoMax = reservedDateBravoMax;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public String toString() {
        return "PointCompositeLookupInfo{" +
                "namePattern='" + namePattern + '\'' +
                ", remarkPattern='" + remarkPattern + '\'' +
                ", normalKeepEnabled=" + normalKeepEnabled +
                ", normalPersistEnabled=" + normalPersistEnabled +
                ", filteredKeepEnabled=" + filteredKeepEnabled +
                ", filteredPersistEnabled=" + filteredPersistEnabled +
                ", triggeredKeepEnabled=" + triggeredKeepEnabled +
                ", triggeredPersistEnabled=" + triggeredPersistEnabled +
                ", reservedStringAlphaPattern='" + reservedStringAlphaPattern + '\'' +
                ", reservedStringBravoPattern='" + reservedStringBravoPattern + '\'' +
                ", reservedStringCharliePattern='" + reservedStringCharliePattern + '\'' +
                ", reservedStringDeltaPattern='" + reservedStringDeltaPattern + '\'' +
                ", reservedLongAlphaMin=" + reservedLongAlphaMin +
                ", reservedLongAlphaMax=" + reservedLongAlphaMax +
                ", reservedLongBravoMin=" + reservedLongBravoMin +
                ", reservedLongBravoMax=" + reservedLongBravoMax +
                ", reservedIntegerAlphaMin=" + reservedIntegerAlphaMin +
                ", reservedIntegerAlphaMax=" + reservedIntegerAlphaMax +
                ", reservedIntegerBravoMin=" + reservedIntegerBravoMin +
                ", reservedIntegerBravoMax=" + reservedIntegerBravoMax +
                ", reservedBooleanAlpha=" + reservedBooleanAlpha +
                ", reservedBooleanBravo=" + reservedBooleanBravo +
                ", reservedDateAlphaMin=" + reservedDateAlphaMin +
                ", reservedDateAlphaMax=" + reservedDateAlphaMax +
                ", reservedDateBravoMin=" + reservedDateBravoMin +
                ", reservedDateBravoMax=" + reservedDateBravoMax +
                '}';
    }
}
