package com.dwarfeng.fdr.impl.handler.bridge.redis.bean;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;

/**
 * Redis 桥接一般数据。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class RedisBridgeNormalData implements Entity<LongIdKey> {

    private static final long serialVersionUID = -6401576547629299319L;
    
    private LongIdKey key;
    private String value;
    private Date happenedDate;

    public RedisBridgeNormalData() {
    }

    public RedisBridgeNormalData(LongIdKey key, String value, Date happenedDate) {
        this.key = key;
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
        return "RedisBridgeNormalData{" +
                "key=" + key +
                ", value='" + value + '\'' +
                ", happenedDate=" + happenedDate +
                '}';
    }
}
