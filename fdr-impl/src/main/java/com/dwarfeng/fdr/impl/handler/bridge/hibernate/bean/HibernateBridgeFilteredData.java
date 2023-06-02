package com.dwarfeng.fdr.impl.handler.bridge.hibernate.bean;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;

/**
 * Hibernate 桥接被过滤数据。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class HibernateBridgeFilteredData implements Entity<LongIdKey> {

    private static final long serialVersionUID = 294042820094157311L;

    private LongIdKey key;
    private LongIdKey pointKey;
    private LongIdKey filterKey;
    private String value;
    private String message;
    private Date happenedDate;

    public HibernateBridgeFilteredData() {
    }

    public HibernateBridgeFilteredData(
            LongIdKey key, LongIdKey pointKey, LongIdKey filterKey, String value, String message, Date happenedDate
    ) {
        this.key = key;
        this.pointKey = pointKey;
        this.filterKey = filterKey;
        this.value = value;
        this.message = message;
        this.happenedDate = happenedDate;
    }

    @Override
    public LongIdKey getKey() {
        return key;
    }

    @Override
    public void setKey(LongIdKey key) {
        this.key = key;
    }

    public LongIdKey getPointKey() {
        return pointKey;
    }

    public void setPointKey(LongIdKey pointKey) {
        this.pointKey = pointKey;
    }

    public LongIdKey getFilterKey() {
        return filterKey;
    }

    public void setFilterKey(LongIdKey filterKey) {
        this.filterKey = filterKey;
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
        return "HibernateBridgeFilteredData{" +
                "key=" + key +
                ", pointKey=" + pointKey +
                ", filterKey=" + filterKey +
                ", value='" + value + '\'' +
                ", message='" + message + '\'' +
                ", happenedDate=" + happenedDate +
                '}';
    }
}
