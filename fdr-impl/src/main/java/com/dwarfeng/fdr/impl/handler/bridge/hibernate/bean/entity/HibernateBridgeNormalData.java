package com.dwarfeng.fdr.impl.handler.bridge.hibernate.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;

/**
 * Hibernate 桥接一般数据。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class HibernateBridgeNormalData implements Entity<LongIdKey> {

    private static final long serialVersionUID = 2974791092188181620L;

    private LongIdKey key;
    private LongIdKey pointKey;
    private String value;
    private Date happenedDate;

    public HibernateBridgeNormalData() {
    }

    public HibernateBridgeNormalData(LongIdKey key, LongIdKey pointKey, String value, Date happenedDate) {
        this.key = key;
        this.pointKey = pointKey;
        this.value = value;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getHappenedDate() {
        return happenedDate;
    }

    public void setHappenedDate(Date happenedDate) {
        this.happenedDate = happenedDate;
    }

    @Override
    public String toString() {
        return "HibernateBridgeNormalData{" +
                "key=" + key +
                ", pointKey=" + pointKey +
                ", value='" + value + '\'' +
                ", happenedDate=" + happenedDate +
                '}';
    }
}
