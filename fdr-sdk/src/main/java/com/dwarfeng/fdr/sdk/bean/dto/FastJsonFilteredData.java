package com.dwarfeng.fdr.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * FastJson 被过滤数据。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FastJsonFilteredData implements Dto {

    private static final long serialVersionUID = 698750099137226890L;

    public static FastJsonFilteredData of(FilteredData filteredData) {
        if (Objects.isNull(filteredData)) {
            return null;
        } else {
            return new FastJsonFilteredData(
                    FastJsonLongIdKey.of(filteredData.getPointKey()),
                    FastJsonLongIdKey.of(filteredData.getFilterKey()),
                    filteredData.getValue(),
                    filteredData.getMessage()
            );
        }
    }

    @JSONField(name = "point_key", ordinal = 1)
    private FastJsonLongIdKey pointKey;

    @JSONField(name = "filter_key", ordinal = 2)
    private FastJsonLongIdKey filterKey;

    @JSONField(name = "value", ordinal = 3)
    private Object value;

    @JSONField(name = "message", ordinal = 4)
    private String message;

    public FastJsonFilteredData() {
    }

    public FastJsonFilteredData(FastJsonLongIdKey pointKey, FastJsonLongIdKey filterKey, Object value, String message) {
        this.pointKey = pointKey;
        this.filterKey = filterKey;
        this.value = value;
        this.message = message;
    }

    public FastJsonLongIdKey getPointKey() {
        return pointKey;
    }

    public void setPointKey(FastJsonLongIdKey pointKey) {
        this.pointKey = pointKey;
    }

    public FastJsonLongIdKey getFilterKey() {
        return filterKey;
    }

    public void setFilterKey(FastJsonLongIdKey filterKey) {
        this.filterKey = filterKey;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "FastJsonFilteredData{" +
                "pointKey=" + pointKey +
                ", filterKey=" + filterKey +
                ", value=" + value +
                ", message='" + message + '\'' +
                '}';
    }
}
