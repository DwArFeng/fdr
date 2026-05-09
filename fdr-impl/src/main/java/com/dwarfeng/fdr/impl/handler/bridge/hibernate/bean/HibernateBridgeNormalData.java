package com.dwarfeng.fdr.impl.handler.bridge.hibernate.bean;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;

/**
 * Hibernate 桥接一般数据。
 *
 * <p>
 * 在 3.0.0 版本中，新增了数据发生时间在毫秒内的纳秒偏移。
 * 数据发生时间由 {@link #happenedDate} 和 {@link #happenedDateNanoOffset} 共同唯一确定。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class HibernateBridgeNormalData implements Entity<LongIdKey> {

    private static final long serialVersionUID = 3664228402740520147L;

    private LongIdKey key;
    private LongIdKey pointKey;
    private String value;
    private Date happenedDate;

    /**
     * @since 3.0.0
     */
    private int happenedDateNanoOffset;

    public HibernateBridgeNormalData() {
    }

    public HibernateBridgeNormalData(LongIdKey key, LongIdKey pointKey, String value, Date happenedDate) {
        this(key, pointKey, value, happenedDate, 0);
    }

    /**
     * @since 3.0.0
     */
    public HibernateBridgeNormalData(
            LongIdKey key, LongIdKey pointKey, String value, Date happenedDate, int happenedDateNanoOffset
    ) {
        this.key = key;
        this.pointKey = pointKey;
        this.value = value;
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

    public int getHappenedDateNanoOffset() {
        return happenedDateNanoOffset;
    }

    public void setHappenedDateNanoOffset(int happenedDateNanoOffset) {
        this.happenedDateNanoOffset = happenedDateNanoOffset;
    }

    @Override
    public String toString() {
        return "HibernateBridgeNormalData{" +
                "key=" + key +
                ", pointKey=" + pointKey +
                ", value='" + value + '\'' +
                ", happenedDate=" + happenedDate +
                ", happenedDateNanoOffset=" + happenedDateNanoOffset +
                '}';
    }
}
