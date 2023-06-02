package com.dwarfeng.fdr.impl.handler.bridge.redis.bean;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;

/**
 * Redis 桥接被触发数据。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class RedisBridgeTriggeredData implements Entity<LongIdKey> {

    private static final long serialVersionUID = 8724161679980954380L;
    
    private LongIdKey key;
    private LongIdKey triggerKey;
    private String value;
    private String message;
    private Date happenedDate;

    public RedisBridgeTriggeredData() {
    }

    public RedisBridgeTriggeredData(
            LongIdKey key, LongIdKey triggerKey, String value, String message, Date happenedDate
    ) {
        this.key = key;
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
        return "RedisBridgeTriggeredData{" +
                "key=" + key +
                ", triggerKey=" + triggerKey +
                ", value='" + value + '\'' +
                ", message='" + message + '\'' +
                ", happenedDate=" + happenedDate +
                '}';
    }
}
