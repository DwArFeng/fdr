package com.dwarfeng.fdr.impl.handler.bridge.hibernate.bean;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;

/**
 * Hibernate 桥接被触发数据。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class HibernateBridgeTriggeredData implements Entity<LongIdKey> {

    private static final long serialVersionUID = -8116433460275886303L;

    private LongIdKey key;
    private LongIdKey pointKey;
    private LongIdKey triggerKey;
    private String value;
    private String message;
    private Date happenedDate;

    public HibernateBridgeTriggeredData() {
    }

    public HibernateBridgeTriggeredData(
            LongIdKey key, LongIdKey pointKey, LongIdKey triggerKey, String value, String message, Date happenedDate
    ) {
        this.key = key;
        this.pointKey = pointKey;
        this.triggerKey = triggerKey;
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

    public LongIdKey getTriggerKey() {
        return triggerKey;
    }

    public void setTriggerKey(LongIdKey triggerKey) {
        this.triggerKey = triggerKey;
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
        return "HibernateBridgeTriggeredData{" +
                "key=" + key +
                ", pointKey=" + pointKey +
                ", triggerKey=" + triggerKey +
                ", value='" + value + '\'' +
                ", message='" + message + '\'' +
                ", happenedDate=" + happenedDate +
                '}';
    }
}
