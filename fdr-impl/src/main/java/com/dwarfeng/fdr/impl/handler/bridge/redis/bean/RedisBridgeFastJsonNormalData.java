package com.dwarfeng.fdr.impl.handler.bridge.redis.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Date;

/**
 * Redis 桥接 FastJson 一般数据。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class RedisBridgeFastJsonNormalData implements Bean {

    private static final long serialVersionUID = 9136949940506617201L;

    @JSONField(name = "key", ordinal = 1)
    private FastJsonLongIdKey key;

    @JSONField(name = "value", ordinal = 2)
    private String value;

    @JSONField(name = "happened_date", ordinal = 3)
    private Date happenedDate;

    public RedisBridgeFastJsonNormalData() {
    }

    public RedisBridgeFastJsonNormalData(
            FastJsonLongIdKey key, String value, Date happenedDate
    ) {
        this.key = key;
        this.value = value;
        this.happenedDate = happenedDate;
    }

    public FastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonLongIdKey key) {
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
        return "RedisBridgeFastJsonNormalData{" +
                "key=" + key +
                ", value='" + value + '\'' +
                ", happenedDate=" + happenedDate +
                '}';
    }
}
