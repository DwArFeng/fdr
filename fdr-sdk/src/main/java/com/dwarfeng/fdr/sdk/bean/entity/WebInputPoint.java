package com.dwarfeng.fdr.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.sdk.util.Constraints;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.util.Date;
import java.util.Objects;

/**
 * WebInput 数据点。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class WebInputPoint implements Bean {

    private static final long serialVersionUID = -4739858083232019144L;

    public static Point toStackBean(WebInputPoint webInputPoint) {
        if (Objects.isNull(webInputPoint)) {
            return null;
        } else {
            return new Point(
                    WebInputLongIdKey.toStackBean(webInputPoint.getKey()),
                    webInputPoint.getName(),
                    webInputPoint.getRemark(),
                    webInputPoint.isNormalKeepEnabled(),
                    webInputPoint.isNormalPersistEnabled(),
                    webInputPoint.isFilteredKeepEnabled(),
                    webInputPoint.isFilteredPersistEnabled(),
                    webInputPoint.isTriggeredKeepEnabled(),
                    webInputPoint.isTriggeredPersistEnabled(),
                    webInputPoint.getReservedStringAlpha(),
                    webInputPoint.getReservedStringBravo(),
                    webInputPoint.getReservedStringCharlie(),
                    webInputPoint.getReservedStringDelta(),
                    webInputPoint.getReservedLongAlpha(),
                    webInputPoint.getReservedLongBravo(),
                    webInputPoint.getReservedIntegerAlpha(),
                    webInputPoint.getReservedIntegerBravo(),
                    webInputPoint.getReservedBooleanAlpha(),
                    webInputPoint.getReservedBooleanBravo(),
                    webInputPoint.getReservedDateAlpha(),
                    webInputPoint.getReservedDateBravo()
            );
        }
    }

    @JSONField(name = "key")
    @Valid
    @NotNull(groups = Default.class)
    private WebInputLongIdKey key;

    @JSONField(name = "name")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_NAME)
    private String name;

    @JSONField(name = "remark")
    @Length(max = Constraints.LENGTH_REMARK)
    private String remark;

    @JSONField(name = "normal_keep_enabled")
    private boolean normalKeepEnabled;

    @JSONField(name = "normal_persist_enabled")
    private boolean normalPersistEnabled;

    @JSONField(name = "filtered_keep_enabled")
    private boolean filteredKeepEnabled;

    @JSONField(name = "filtered_persist_enabled")
    private boolean filteredPersistEnabled;

    @JSONField(name = "triggered_keep_enabled")
    private boolean triggeredKeepEnabled;

    @JSONField(name = "triggered_persist_enabled")
    private boolean triggeredPersistEnabled;

    // region 预留字段

    @JSONField(name = "reserved_string_alpha")
    private String reservedStringAlpha;

    @JSONField(name = "reserved_string_bravo")
    private String reservedStringBravo;

    @JSONField(name = "reserved_string_charlie")
    private String reservedStringCharlie;

    @JSONField(name = "reserved_string_delta")
    private String reservedStringDelta;

    @JSONField(name = "reserved_long_alpha")
    private Long reservedLongAlpha;

    @JSONField(name = "reserved_long_bravo")
    private Long reservedLongBravo;

    @JSONField(name = "reserved_integer_alpha")
    private Integer reservedIntegerAlpha;

    @JSONField(name = "reserved_integer_bravo")
    private Integer reservedIntegerBravo;

    @JSONField(name = "reserved_boolean_alpha")
    private Boolean reservedBooleanAlpha;

    @JSONField(name = "reserved_boolean_bravo")
    private Boolean reservedBooleanBravo;

    @JSONField(name = "reserved_date_alpha")
    private Date reservedDateAlpha;

    @JSONField(name = "reserved_date_bravo")
    private Date reservedDateBravo;

    // endregion

    public WebInputPoint() {
    }

    public WebInputLongIdKey getKey() {
        return key;
    }

    public void setKey(WebInputLongIdKey key) {
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
        return "WebInputPoint{" +
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
