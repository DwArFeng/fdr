package com.dwarfeng.fdr.impl.handler.bridge.hibernate.bean;

import com.dwarfeng.fdr.sdk.util.Constraints;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.Date;
import java.util.Optional;

@Entity
@IdClass(HibernateLongIdKey.class)
@Table(name = "tbl_hibernate_bridge_triggered_data", indexes = {
        @Index(name = "idx_point_id_happened_date", columnList = "point_id, happened_date ASC"),
})
public class HibernateBridgeHibernateTriggeredData implements Bean {

    private static final long serialVersionUID = 4796516981093825524L;

    // -----------------------------------------------------------主键-----------------------------------------------------------
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long longId;

    // -----------------------------------------------------------主属性字段-----------------------------------------------------------
    @Column(name = "point_id", nullable = false)
    private Long pointLongId;

    @Column(name = "trigger_id", nullable = false)
    private Long triggerLongId;

    @Column(name = "value", columnDefinition = "TEXT", nullable = false)
    private String value;

    @Column(name = "message", length = Constraints.LENGTH_MESSAGE, nullable = false)
    private String message;

    @Column(name = "happened_date", nullable = false)
    private Date happenedDate;

    // -----------------------------------------------------------映射用属性区-----------------------------------------------------------
    public HibernateLongIdKey getKey() {
        return Optional.ofNullable(longId).map(HibernateLongIdKey::new).orElse(null);
    }

    public void setKey(HibernateLongIdKey idKey) {
        this.longId = Optional.ofNullable(idKey).map(HibernateLongIdKey::getLongId).orElse(null);
    }

    public HibernateLongIdKey getPointKey() {
        return Optional.ofNullable(pointLongId).map(HibernateLongIdKey::new).orElse(null);
    }

    public void setPointKey(HibernateLongIdKey idKey) {
        this.pointLongId = Optional.ofNullable(idKey).map(HibernateLongIdKey::getLongId).orElse(null);
    }

    public HibernateLongIdKey getTriggerKey() {
        return Optional.ofNullable(triggerLongId).map(HibernateLongIdKey::new).orElse(null);
    }

    public void setTriggerKey(HibernateLongIdKey idKey) {
        this.triggerLongId = Optional.ofNullable(idKey).map(HibernateLongIdKey::getLongId).orElse(null);
    }

    // -----------------------------------------------------------常规属性区-----------------------------------------------------------
    public Long getLongId() {
        return longId;
    }

    public void setLongId(Long longId) {
        this.longId = longId;
    }

    public Long getPointLongId() {
        return pointLongId;
    }

    public void setPointLongId(Long pointLongId) {
        this.pointLongId = pointLongId;
    }

    public Long getTriggerLongId() {
        return triggerLongId;
    }

    public void setTriggerLongId(Long triggerLongId) {
        this.triggerLongId = triggerLongId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getHappenedDate() {
        return happenedDate;
    }

    public void setHappenedDate(Date happenedDate) {
        this.happenedDate = happenedDate;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "longId = " + longId + ", " +
                "pointLongId = " + pointLongId + ", " +
                "triggerLongId = " + triggerLongId + ", " +
                "value = " + value + ", " +
                "message = " + message + ", " +
                "happenedDate = " + happenedDate + ")";
    }
}
