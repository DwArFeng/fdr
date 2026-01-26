package com.dwarfeng.fdr.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.dto.PointCompositeLookupInfo;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.Objects;

/**
 * WebInput Point 组合查询信息。
 *
 * @author WFM
 * @since 2.3.2
 */
public class WebInputPointCompositeLookupInfo implements Dto {

    private static final long serialVersionUID = -185891482390225263L;

    public static PointCompositeLookupInfo toStackBean(WebInputPointCompositeLookupInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new PointCompositeLookupInfo(
                    webInput.getNamePattern(),
                    webInput.getRemarkPattern(),
                    webInput.getNormalKeepEnabled(),
                    webInput.getNormalPersistEnabled(),
                    webInput.getFilteredKeepEnabled(),
                    webInput.getFilteredPersistEnabled(),
                    webInput.getTriggeredKeepEnabled(),
                    webInput.getTriggeredPersistEnabled(),
                    webInput.getReservedStringAlphaPattern(),
                    webInput.getReservedStringBravoPattern(),
                    webInput.getReservedStringCharliePattern(),
                    webInput.getReservedStringDeltaPattern(),
                    webInput.getReservedLongAlphaMin(),
                    webInput.getReservedLongAlphaMax(),
                    webInput.getReservedLongBravoMin(),
                    webInput.getReservedLongBravoMax(),
                    webInput.getReservedIntegerAlphaMin(),
                    webInput.getReservedIntegerAlphaMax(),
                    webInput.getReservedIntegerBravoMin(),
                    webInput.getReservedIntegerBravoMax(),
                    webInput.getReservedBooleanAlpha(),
                    webInput.getReservedBooleanBravo(),
                    webInput.getReservedDateAlphaMin(),
                    webInput.getReservedDateAlphaMax(),
                    webInput.getReservedDateBravoMin(),
                    webInput.getReservedDateBravoMax()
            );
        }
    }

    @JSONField(name = "name_pattern", ordinal = 1)
    private String namePattern;

    @JSONField(name = "remark_pattern", ordinal = 2)
    private String remarkPattern;

    @JSONField(name = "normal_keep_enabled", ordinal = 3)
    private Boolean normalKeepEnabled;

    @JSONField(name = "normal_persist_enabled", ordinal = 4)
    private Boolean normalPersistEnabled;

    @JSONField(name = "filtered_keep_enabled", ordinal = 5)
    private Boolean filteredKeepEnabled;

    @JSONField(name = "filtered_persist_enabled", ordinal = 6)
    private Boolean filteredPersistEnabled;

    @JSONField(name = "triggered_keep_enabled", ordinal = 7)
    private Boolean triggeredKeepEnabled;

    @JSONField(name = "triggered_persist_enabled", ordinal = 8)
    private Boolean triggeredPersistEnabled;

    @JSONField(name = "reserved_string_alpha_pattern", ordinal = 9)
    private String reservedStringAlphaPattern;

    @JSONField(name = "reserved_string_bravo_pattern", ordinal = 10)
    private String reservedStringBravoPattern;

    @JSONField(name = "reserved_string_charlie_pattern", ordinal = 11)
    private String reservedStringCharliePattern;

    @JSONField(name = "reserved_string_delta_pattern", ordinal = 12)
    private String reservedStringDeltaPattern;

    @JSONField(name = "reserved_long_alpha_min", ordinal = 13)
    private Long reservedLongAlphaMin;

    @JSONField(name = "reserved_long_alpha_max", ordinal = 14)
    private Long reservedLongAlphaMax;

    @JSONField(name = "reserved_long_bravo_min", ordinal = 15)
    private Long reservedLongBravoMin;

    @JSONField(name = "reserved_long_bravo_max", ordinal = 16)
    private Long reservedLongBravoMax;

    @JSONField(name = "reserved_integer_alpha_min", ordinal = 17)
    private Integer reservedIntegerAlphaMin;

    @JSONField(name = "reserved_integer_alpha_max", ordinal = 18)
    private Integer reservedIntegerAlphaMax;

    @JSONField(name = "reserved_integer_bravo_min", ordinal = 19)
    private Integer reservedIntegerBravoMin;

    @JSONField(name = "reserved_integer_bravo_max", ordinal = 20)
    private Integer reservedIntegerBravoMax;

    @JSONField(name = "reserved_boolean_alpha", ordinal = 21)
    private Boolean reservedBooleanAlpha;

    @JSONField(name = "reserved_boolean_bravo", ordinal = 22)
    private Boolean reservedBooleanBravo;

    @JSONField(name = "reserved_date_alpha_min", ordinal = 23)
    private Date reservedDateAlphaMin;

    @JSONField(name = "reserved_date_alpha_max", ordinal = 24)
    private Date reservedDateAlphaMax;

    @JSONField(name = "reserved_date_bravo_min", ordinal = 25)
    private Date reservedDateBravoMin;

    @JSONField(name = "reserved_date_bravo_max", ordinal = 26)
    private Date reservedDateBravoMax;

    public WebInputPointCompositeLookupInfo() {
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
        return "WebInputPointCompositeLookupInfo{" +
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
