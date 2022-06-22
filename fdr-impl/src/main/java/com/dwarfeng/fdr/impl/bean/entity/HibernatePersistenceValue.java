package com.dwarfeng.fdr.impl.bean.entity;

import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.Date;
import java.util.Optional;

@Entity
@IdClass(HibernateLongIdKey.class)
@Table(name = "tbl_persistence_value", indexes = {
        @Index(name = "idx_point_id", columnList = "point_id"),
        @Index(name = "idx_point_id_happened_date", columnList = "point_id, happened_date ASC"),
        @Index(name = "idx_point_id_happened_date_desc", columnList = "point_id, happened_date DESC")
})
public class HibernatePersistenceValue implements Bean {

    private static final long serialVersionUID = -4074117033394243833L;

    // -----------------------------------------------------------主键-----------------------------------------------------------
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long longId;

    // -----------------------------------------------------------主属性字段-----------------------------------------------------------
    @Column(name = "point_id")
    private Long pointLongId;

    @Column(name = "happened_date", nullable = false)
    private Date happenedDate;

    @Column(name = "value", columnDefinition = "TEXT", nullable = false)
    private String value;

    public HibernatePersistenceValue() {
    }

    public HibernateLongIdKey getKey() {
        return Optional.ofNullable(longId).map(HibernateLongIdKey::new).orElse(null);
    }

    public void setKey(HibernateLongIdKey key) {
        this.longId = Optional.ofNullable(key).map(HibernateLongIdKey::getLongId).orElse(null);
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

    public void setPointKey(HibernateLongIdKey pointKey) {
        this.pointLongId = Optional.ofNullable(pointKey).map(HibernateLongIdKey::getLongId).orElse(null);
    }

    public Long getPointLongId() {
        return pointLongId;
    }

    public void setPointLongId(Long pointGuid) {
        this.pointLongId = pointGuid;
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

    @Override
    public String toString() {
        return "HibernatePersistenceValue{" +
                "longId=" + longId +
                ", pointLongId=" + pointLongId +
                ", happenedDate=" + happenedDate +
                ", value='" + value + '\'' +
                '}';
    }
}
