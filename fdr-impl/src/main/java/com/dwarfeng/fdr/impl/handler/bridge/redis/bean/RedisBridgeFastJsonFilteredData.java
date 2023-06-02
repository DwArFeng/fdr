package com.dwarfeng.fdr.impl.handler.bridge.redis.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Date;

/**
 * Redis 桥接 FastJson 被过滤数据。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class RedisBridgeFastJsonFilteredData implements Bean {

    private static final long serialVersionUID = 2964315260708184414L;

    @JSONField(name = "key", ordinal = 1)
    private FastJsonLongIdKey key;

    @JSONField(name = "filter_key", ordinal = 2)
    private FastJsonLongIdKey filterKey;

    @JSONField(name = "value", ordinal = 3)
    private String value;

    @JSONField(name = "message", ordinal = 4)
    private String message;

    @JSONField(name = "happened_date", ordinal = 5)
    private Date happenedDate;

    public RedisBridgeFastJsonFilteredData() {
    }

    public RedisBridgeFastJsonFilteredData(
            FastJsonLongIdKey key, FastJsonLongIdKey filterKey, String value,
            String message, Date happenedDate
    ) {
        this.key = key;
        this.filterKey = filterKey;
        this.value = value;
        this.message = message;
        this.happenedDate = happenedDate;
    }

    public FastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonLongIdKey key) {
        this.key = key;
    }

    public FastJsonLongIdKey getFilterKey() {
        return filterKey;
    }

    public void setFilterKey(FastJsonLongIdKey filterKey) {
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
        return "RedisBridgeFastJsonFilteredData{" +
                "key=" + key +
                ", filterKey=" + filterKey +
                ", value='" + value + '\'' +
                ", message='" + message + '\'' +
                ", happenedDate=" + happenedDate +
                '}';
    }
}
