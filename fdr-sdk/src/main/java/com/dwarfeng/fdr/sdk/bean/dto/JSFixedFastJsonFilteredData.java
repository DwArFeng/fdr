package com.dwarfeng.fdr.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * JSFixed FastJson 被过滤数据。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class JSFixedFastJsonFilteredData implements Dto {

    private static final long serialVersionUID = -534630551254894014L;

    public static JSFixedFastJsonFilteredData of(FilteredData filteredData) {
        if (Objects.isNull(filteredData)) {
            return null;
        } else {
            return new JSFixedFastJsonFilteredData(
                    JSFixedFastJsonLongIdKey.of(filteredData.getPointKey()),
                    JSFixedFastJsonLongIdKey.of(filteredData.getFilterKey()),
                    filteredData.getValue(),
                    filteredData.getMessage()
            );
        }
    }

    @JSONField(name = "point_key", ordinal = 1)
    private JSFixedFastJsonLongIdKey pointKey;

    @JSONField(name = "filter_key", ordinal = 2)
    private JSFixedFastJsonLongIdKey filterKey;

    @JSONField(name = "value", ordinal = 3)
    private Object value;

    @JSONField(name = "message", ordinal = 4)
    private String message;

    public JSFixedFastJsonFilteredData() {
    }

    public JSFixedFastJsonFilteredData(JSFixedFastJsonLongIdKey pointKey, JSFixedFastJsonLongIdKey filterKey, Object value, String message) {
        this.pointKey = pointKey;
        this.filterKey = filterKey;
        this.value = value;
        this.message = message;
    }

    public JSFixedFastJsonLongIdKey getPointKey() {
        return pointKey;
    }

    public void setPointKey(JSFixedFastJsonLongIdKey pointKey) {
        this.pointKey = pointKey;
    }

    public JSFixedFastJsonLongIdKey getFilterKey() {
        return filterKey;
    }

    public void setFilterKey(JSFixedFastJsonLongIdKey filterKey) {
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
        return "JSFixedFastJsonFilteredData{" +
                "pointKey=" + pointKey +
                ", filterKey=" + filterKey +
                ", value=" + value +
                ", message='" + message + '\'' +
                '}';
    }
}
