package com.dwarfeng.fdr.impl.bean.entity;

import com.dwarfeng.fdr.sdk.util.Constants;
import com.dwarfeng.fdr.sdk.util.Constraints;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.Date;
import java.util.Optional;

@Entity
@IdClass(HibernateLongIdKey.class)
@Table(name = "tbl_triggered_value", indexes = {
        @Index(name = Constants.INDEX_NAME_POINT_ID, columnList = "point_id"),
        @Index(name = Constants.INDEX_NAME_POINT_ID_HAPPENED_DATE, columnList = "point_id, happened_date ASC"),
        @Index(name = Constants.INDEX_NAME_POINT_ID_HAPPENED_DATE_DESC, columnList = "point_id, happened_date DESC"),
        @Index(name = Constants.INDEX_NAME_TRIGGER_ID, columnList = "trigger_id"),
        @Index(name = Constants.INDEX_NAME_TRIGGER_ID_HAPPENED_DATE, columnList = "trigger_id, happened_date ASC"),
        @Index(name = Constants.INDEX_NAME_TRIGGER_ID_HAPPENED_DATE_DESC, columnList = "trigger_id, happened_date DESC")
})
public class HibernateTriggeredValue implements Bean {

    private static final long serialVersionUID = -4017760460462806084L;

    // -----------------------------------------------------------主键-----------------------------------------------------------
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long longId;

    // -----------------------------------------------------------主属性字段-----------------------------------------------------------
    @Column(name = "point_id")
    private Long pointLongId;

    @Column(name = "trigger_id")
    private Long triggerLongId;

    @Column(name = "happened_date", nullable = false)
    private Date happenedDate;

    @Column(name = "value", columnDefinition = "TEXT", nullable = false)
    private String value;

    @Column(name = "message", length = Constraints.LENGTH_MESSAGE)
    private String message;

    public HibernateTriggeredValue() {
    }

    public HibernateLongIdKey getKey() {
        return Optional.ofNullable(longId).map(HibernateLongIdKey::new).orElse(null);
    }

    public void setKey(HibernateLongIdKey idKey) {
        this.longId = Optional.ofNullable(idKey).map(HibernateLongIdKey::getLongId).orElse(null);
    }

    public Long getLongId() {
        return longId;
    }

    public void setLongId(Long id) {
        this.longId = id;
    }

    public HibernateLongIdKey getPointKey() {
        return Optional.ofNullable(pointLongId).map(HibernateLongIdKey::new).orElse(null);
    }

    public void setPointKey(HibernateLongIdKey idKey) {
        this.pointLongId = Optional.ofNullable(idKey).map(HibernateLongIdKey::getLongId).orElse(null);
    }

    public Long getPointLongId() {
        return pointLongId;
    }

    public void setPointLongId(Long pointGuid) {
        this.pointLongId = pointGuid;
    }

    public HibernateLongIdKey getTriggerKey() {
        return Optional.ofNullable(triggerLongId).map(HibernateLongIdKey::new).orElse(null);
    }

    public void setTriggerKey(HibernateLongIdKey idKey) {
        this.triggerLongId = Optional.ofNullable(idKey).map(HibernateLongIdKey::getLongId).orElse(null);
    }

    public Long getTriggerLongId() {
        return triggerLongId;
    }

    public void setTriggerLongId(Long triggerGuid) {
        this.triggerLongId = triggerGuid;
    }

    public Date getHappenedDate() {
        return happenedDate;
    }

    public void setHappenedDate(Date happenedDate) {
        this.happenedDate = happenedDate;
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

    @Override
    public String toString() {
        return "HibernateTriggeredValue{" +
                "longId=" + longId +
                ", pointLongId=" + pointLongId +
                ", triggerLongId=" + triggerLongId +
                ", happenedDate=" + happenedDate +
                ", value='" + value + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
