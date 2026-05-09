package com.dwarfeng.fdr.impl.handler.bridge.hibernate.bean;

import com.dwarfeng.fdr.sdk.util.Constraints;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.Date;
import java.util.Optional;

@Entity
@IdClass(HibernateLongIdKey.class)
@Table(name = "tbl_hibernate_bridge_filtered_data", indexes = {
        @Index(
                name = "idx_point_id_happened_date_nano_id",
                columnList = "point_id, happened_date ASC, happened_date_nano_offset ASC, id ASC"
        ),
})
public class HibernateBridgeHibernateFilteredData implements Bean {

    private static final long serialVersionUID = 7908428793378216072L;

    // region 主键

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long longId;

    // endregion

    // region 主属性字段

    @Column(name = "point_id", nullable = false)
    private Long pointLongId;

    @Column(name = "filter_id", nullable = false)
    private Long filterLongId;

    @Column(name = "value", columnDefinition = "TEXT", nullable = false)
    private String value;

    @Column(name = "message", length = Constraints.LENGTH_MESSAGE, nullable = false)
    private String message;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "happened_date", nullable = false)
    private Date happenedDate;

    @Column(name = "happened_date_nano_offset", nullable = false)
    private int happenedDateNanoOffset;

    // endregion

    // region 映射用属性区

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

    public HibernateLongIdKey getFilterKey() {
        return Optional.ofNullable(filterLongId).map(HibernateLongIdKey::new).orElse(null);
    }

    public void setFilterKey(HibernateLongIdKey idKey) {
        this.filterLongId = Optional.ofNullable(idKey).map(HibernateLongIdKey::getLongId).orElse(null);
    }

    // endregion

    // region 常规属性区

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

    public Long getFilterLongId() {
        return filterLongId;
    }

    public void setFilterLongId(Long filterLongId) {
        this.filterLongId = filterLongId;
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

    public int getHappenedDateNanoOffset() {
        return happenedDateNanoOffset;
    }

    public void setHappenedDateNanoOffset(int happenedDateNanoOffset) {
        this.happenedDateNanoOffset = happenedDateNanoOffset;
    }

    // endregion

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "longId = " + longId + ", " +
                "pointLongId = " + pointLongId + ", " +
                "filterLongId = " + filterLongId + ", " +
                "value = " + value + ", " +
                "message = " + message + ", " +
                "happenedDate = " + happenedDate + ", " +
                "happenedDateNanoOffset = " + happenedDateNanoOffset + ")";
    }
}
