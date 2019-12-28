package com.dwarfeng.fdr.impl.dao.fuh.bean.entity;

import com.dwarfeng.fdr.impl.dao.fuh.bean.key.HibernateUuidKey;
import com.dwarfeng.fdr.sdk.util.Constraints;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

@Entity
@IdClass(HibernateUuidKey.class)
@Table(name = "tbl_triggered_value")
public class HibernateTriggeredValue implements Serializable {

    private static final long serialVersionUID = 4923865131580198476L;

    // -----------------------------------------------------------主键-----------------------------------------------------------
    @Id
    @Column(name = "uuid", columnDefinition = "CHAR(22)", nullable = false, unique = true)
    private String uuid;

    // -----------------------------------------------------------外键-----------------------------------------------------------
    @Column(name = "point_uuid", columnDefinition = "CHAR(22)")
    private String pointUuid;

    @Column(name = "trigger_uuid", columnDefinition = "CHAR(22)")
    private String triggerUuid;

    // -----------------------------------------------------------主属性字段-----------------------------------------------------------
    @Column(name = "happened_date", nullable = false)
    private Date happenedDate;

    @Column(name = "value", length = Constraints.LENGTH_VALUE, nullable = false)
    private String value;

    @Column(name = "message", length = Constraints.LENGTH_MESSAGE, nullable = true)
    private String message;

    // -----------------------------------------------------------多对一-----------------------------------------------------------
    @ManyToOne(targetEntity = HibernatePoint.class)
    @JoinColumns({ //
            @JoinColumn(name = "point_uuid", referencedColumnName = "uuid", insertable = false, updatable = false), //
    })
    private HibernatePoint point;

    @ManyToOne(targetEntity = HibernateTriggerInfo.class)
    @JoinColumns({ //
            @JoinColumn(name = "trigger_uuid", referencedColumnName = "uuid", insertable = false, updatable = false), //
    })
    private HibernateTriggerInfo triggerInfo;

    public HibernateTriggeredValue() {
    }

    public HibernateUuidKey getKey() {
        return Optional.ofNullable(uuid).map(HibernateUuidKey::new).orElse(null);
    }

    public void setKey(HibernateUuidKey uuidKey) {
        this.uuid = Optional.ofNullable(uuidKey).map(HibernateUuidKey::getUuid).orElse(null);
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public HibernateUuidKey getPointKey() {
        return Optional.ofNullable(pointUuid).map(HibernateUuidKey::new).orElse(null);
    }

    public void setPointKey(HibernateUuidKey uuidKey) {
        this.pointUuid = Optional.ofNullable(uuidKey).map(HibernateUuidKey::getUuid).orElse(null);
    }

    public String getPointUuid() {
        return pointUuid;
    }

    public void setPointUuid(String pointUuid) {
        this.pointUuid = pointUuid;
    }

    public HibernateUuidKey getTriggerKey() {
        return Optional.ofNullable(triggerUuid).map(HibernateUuidKey::new).orElse(null);
    }

    public void setTriggerKey(HibernateUuidKey uuidKey) {
        this.triggerUuid = Optional.ofNullable(uuidKey).map(HibernateUuidKey::getUuid).orElse(null);
    }

    public String getTriggerUuid() {
        return triggerUuid;
    }

    public void setTriggerUuid(String triggerUuid) {
        this.triggerUuid = triggerUuid;
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

    public HibernatePoint getPoint() {
        return point;
    }

    public void setPoint(HibernatePoint point) {
        this.point = point;
    }

    public HibernateTriggerInfo getTriggerInfo() {
        return triggerInfo;
    }

    public void setTriggerInfo(HibernateTriggerInfo triggerInfo) {
        this.triggerInfo = triggerInfo;
    }

    @Override
    public String toString() {
        return "HibernateTriggeredValue{" +
                "uuid='" + uuid + '\'' +
                ", pointUuid='" + pointUuid + '\'' +
                ", triggerUuid='" + triggerUuid + '\'' +
                ", happenedDate=" + happenedDate +
                ", value='" + value + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}