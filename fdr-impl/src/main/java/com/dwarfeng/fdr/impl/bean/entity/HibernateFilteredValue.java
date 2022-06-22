package com.dwarfeng.fdr.impl.bean.entity;

import com.dwarfeng.fdr.sdk.util.Constraints;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.Date;
import java.util.Optional;

@Entity
@IdClass(HibernateLongIdKey.class)
@Table(name = "tbl_filtered_value", indexes = {
        @Index(name = "idx_point_id", columnList = "point_id"),
        @Index(name = "idx_point_id_happened_date", columnList = "point_id, happened_date ASC"),
        @Index(name = "idx_point_id_happened_date_desc", columnList = "point_id, happened_date DESC"),
        @Index(name = "idx_filter_id", columnList = "filter_id"),
        @Index(name = "idx_filter_id_happened_date", columnList = "filter_id, happened_date ASC"),
        @Index(name = "idx_filter_id_happened_date_desc", columnList = "filter_id, happened_date DESC")
})
public class HibernateFilteredValue implements Bean {

    private static final long serialVersionUID = 314967025301471016L;

    // -----------------------------------------------------------主键-----------------------------------------------------------
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long longId;

    // -----------------------------------------------------------主属性字段-----------------------------------------------------------
    @Column(name = "point_id")
    private Long pointLongId;

    @Column(name = "filter_id")
    private Long filterLongId;

    @Column(name = "happened_date", nullable = false)
    private Date happenedDate;

    @Column(name = "value", columnDefinition = "TEXT", nullable = false)
    private String value;

    @Column(name = "message", length = Constraints.LENGTH_MESSAGE)
    private String message;

    public HibernateFilteredValue() {
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

    public HibernateLongIdKey getFilterKey() {
        return Optional.ofNullable(filterLongId).map(HibernateLongIdKey::new).orElse(null);
    }

    public void setFilterKey(HibernateLongIdKey idKey) {
        this.filterLongId = Optional.ofNullable(idKey).map(HibernateLongIdKey::getLongId).orElse(null);
    }

    public Long getFilterLongId() {
        return filterLongId;
    }

    public void setFilterLongId(Long filterGuid) {
        this.filterLongId = filterGuid;
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
        return "HibernateFilteredValue{" +
                "longId=" + longId +
                ", pointLongId=" + pointLongId +
                ", filterLongId=" + filterLongId +
                ", happenedDate=" + happenedDate +
                ", value='" + value + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
