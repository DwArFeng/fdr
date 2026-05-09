package com.dwarfeng.fdr.impl.handler.bridge.hibernate.bean;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;

/**
 * Hibernate 桥接被触发数据。
 *
 * <p>
 * 在 3.0.0 版本中，新增了数据发生时间在毫秒内的纳秒偏移。
 * 数据发生时间由 {@link #happenedDate} 和 {@link #happenedDateNanoOffset} 共同唯一确定。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class HibernateBridgeTriggeredData implements Entity<LongIdKey> {

    private static final long serialVersionUID = 5637690099862493687L;

    private LongIdKey key;
    private LongIdKey pointKey;
    private LongIdKey triggerKey;
    private String value;
    private String message;
    private Date happenedDate;

    /**
     * @since 3.0.0
     */
    private int happenedDateNanoOffset;

    public HibernateBridgeTriggeredData() {
    }

    public HibernateBridgeTriggeredData(
            LongIdKey key, LongIdKey pointKey, LongIdKey triggerKey, String value, String message, Date happenedDate
    ) {
        this(key, pointKey, triggerKey, value, message, happenedDate, 0);
    }

    /**
     * @since 3.0.0
     */
    public HibernateBridgeTriggeredData(
            LongIdKey key, LongIdKey pointKey, LongIdKey triggerKey, String value, String message,
            Date happenedDate, int happenedDateNanoOffset
    ) {
        this.key = key;
        this.pointKey = pointKey;
        this.triggerKey = triggerKey;
        this.value = value;
        this.message = message;
        this.happenedDate = happenedDate;
        this.happenedDateNanoOffset = happenedDateNanoOffset;
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

    public int getHappenedDateNanoOffset() {
        return happenedDateNanoOffset;
    }

    public void setHappenedDateNanoOffset(int happenedDateNanoOffset) {
        this.happenedDateNanoOffset = happenedDateNanoOffset;
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
                ", happenedDateNanoOffset=" + happenedDateNanoOffset +
                '}';
    }
}
