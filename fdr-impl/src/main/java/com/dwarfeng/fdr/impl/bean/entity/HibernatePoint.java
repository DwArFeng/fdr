package com.dwarfeng.fdr.impl.bean.entity;

import com.dwarfeng.datamark.bean.jpa.DatamarkEntityListener;
import com.dwarfeng.datamark.bean.jpa.DatamarkField;
import com.dwarfeng.fdr.sdk.util.Constraints;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@IdClass(HibernateLongIdKey.class)
@Table(name = "tbl_point")
@EntityListeners(DatamarkEntityListener.class)
public class HibernatePoint implements Bean {

    private static final long serialVersionUID = 5332170999451244762L;

    // -----------------------------------------------------------主键-----------------------------------------------------------
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long longId;

    // -----------------------------------------------------------主属性字段-----------------------------------------------------------
    @Column(name = "name", length = Constraints.LENGTH_NAME, nullable = false)
    private String name;

    @Column(name = "remark", length = Constraints.LENGTH_REMARK)
    private String remark;

    @Column(name = "normal_keep_enabled")
    private boolean normalKeepEnabled;

    @Column(name = "normal_persist_enabled")
    private boolean normalPersistEnabled;

    @Column(name = "filtered_keep_enabled")
    private boolean filteredKeepEnabled;

    @Column(name = "filtered_persist_enabled")
    private boolean filteredPersistEnabled;

    @Column(name = "triggered_keep_enabled")
    private boolean triggeredKeepEnabled;

    @Column(name = "triggered_persist_enabled")
    private boolean triggeredPersistEnabled;

    // -----------------------------------------------------------预留字段-----------------------------------------------------------
    @Column(name = "reserved_string_alpha", columnDefinition = "TEXT")
    private String reservedStringAlpha;

    @Column(name = "reserved_string_bravo", columnDefinition = "TEXT")
    private String reservedStringBravo;

    @Column(name = "reserved_string_charlie", columnDefinition = "TEXT")
    private String reservedStringCharlie;

    @Column(name = "reserved_string_delta", columnDefinition = "TEXT")
    private String reservedStringDelta;

    @Column(name = "reserved_long_alpha")
    private Long reservedLongAlpha;

    @Column(name = "reserved_long_bravo")
    private Long reservedLongBravo;

    @Column(name = "reserved_integer_alpha")
    private Integer reservedIntegerAlpha;

    @Column(name = "reserved_integer_bravo")
    private Integer reservedIntegerBravo;

    @Column(name = "reserved_boolean_alpha")
    private Boolean reservedBooleanAlpha;

    @Column(name = "reserved_boolean_bravo")
    private Boolean reservedBooleanBravo;

    @Column(name = "reserved_date_alpha")
    private Date reservedDateAlpha;

    @Column(name = "reserved_date_bravo")
    private Date reservedDateBravo;

    // -----------------------------------------------------------一对多-----------------------------------------------------------
    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateFilterInfo.class, mappedBy = "point")
    private Set<HibernateFilterInfo> filterInfos = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateTriggerInfo.class, mappedBy = "point")
    private Set<HibernateTriggerInfo> triggerInfos = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateWasherInfo.class, mappedBy = "point")
    private Set<HibernateWasherInfo> washerInfos = new HashSet<>();

    // -----------------------------------------------------------审计-----------------------------------------------------------
    @DatamarkField(handlerName = "pointDatamarkHandler")
    @Column(
            name = "created_datamark",
            length = com.dwarfeng.datamark.util.Constraints.LENGTH_DATAMARK_VALUE,
            updatable = false
    )
    private String createdDatamark;

    @DatamarkField(handlerName = "pointDatamarkHandler")
    @Column(
            name = "modified_datamark",
            length = com.dwarfeng.datamark.util.Constraints.LENGTH_DATAMARK_VALUE
    )
    private String modifiedDatamark;

    public HibernatePoint() {
    }

    // -----------------------------------------------------------映射用属性区-----------------------------------------------------------
    public HibernateLongIdKey getKey() {
        return Optional.ofNullable(longId).map(HibernateLongIdKey::new).orElse(null);
    }

    public void setKey(HibernateLongIdKey idKey) {
        this.longId = Optional.ofNullable(idKey).map(HibernateLongIdKey::getLongId).orElse(null);
    }

    // -----------------------------------------------------------常规属性区-----------------------------------------------------------
    public Long getLongId() {
        return longId;
    }

    public void setLongId(Long longId) {
        this.longId = longId;
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

    public Set<HibernateFilterInfo> getFilterInfos() {
        return filterInfos;
    }

    public void setFilterInfos(Set<HibernateFilterInfo> filterInfos) {
        this.filterInfos = filterInfos;
    }

    public Set<HibernateTriggerInfo> getTriggerInfos() {
        return triggerInfos;
    }

    public void setTriggerInfos(Set<HibernateTriggerInfo> triggerInfos) {
        this.triggerInfos = triggerInfos;
    }

    public Set<HibernateWasherInfo> getWasherInfos() {
        return washerInfos;
    }

    public void setWasherInfos(Set<HibernateWasherInfo> washerInfos) {
        this.washerInfos = washerInfos;
    }

    public String getCreatedDatamark() {
        return createdDatamark;
    }

    public void setCreatedDatamark(String createdDatamark) {
        this.createdDatamark = createdDatamark;
    }

    public String getModifiedDatamark() {
        return modifiedDatamark;
    }

    public void setModifiedDatamark(String modifiedDatamark) {
        this.modifiedDatamark = modifiedDatamark;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "longId = " + longId + ", " +
                "name = " + name + ", " +
                "remark = " + remark + ", " +
                "normalKeepEnabled = " + normalKeepEnabled + ", " +
                "normalPersistEnabled = " + normalPersistEnabled + ", " +
                "filteredKeepEnabled = " + filteredKeepEnabled + ", " +
                "filteredPersistEnabled = " + filteredPersistEnabled + ", " +
                "triggeredKeepEnabled = " + triggeredKeepEnabled + ", " +
                "triggeredPersistEnabled = " + triggeredPersistEnabled + ", " +
                "reservedStringAlpha = " + reservedStringAlpha + ", " +
                "reservedStringBravo = " + reservedStringBravo + ", " +
                "reservedStringCharlie = " + reservedStringCharlie + ", " +
                "reservedStringDelta = " + reservedStringDelta + ", " +
                "reservedLongAlpha = " + reservedLongAlpha + ", " +
                "reservedLongBravo = " + reservedLongBravo + ", " +
                "reservedIntegerAlpha = " + reservedIntegerAlpha + ", " +
                "reservedIntegerBravo = " + reservedIntegerBravo + ", " +
                "reservedBooleanAlpha = " + reservedBooleanAlpha + ", " +
                "reservedBooleanBravo = " + reservedBooleanBravo + ", " +
                "reservedDateAlpha = " + reservedDateAlpha + ", " +
                "reservedDateBravo = " + reservedDateBravo + ", " +
                "createdDatamark = " + createdDatamark + ", " +
                "modifiedDatamark = " + modifiedDatamark + ")";
    }
}
