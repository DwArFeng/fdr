package com.dwarfeng.fdr.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.Objects;

/**
 * JSFixed FastJson 被过滤数据。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class JSFixedFastJsonFilteredData implements Dto {

    private static final long serialVersionUID = -1799171160980420281L;

    public static JSFixedFastJsonFilteredData of(FilteredData filteredData) {
        if (Objects.isNull(filteredData)) {
            return null;
        } else {
            return new JSFixedFastJsonFilteredData(
                    JSFixedFastJsonLongIdKey.of(filteredData.getPointKey()),
                    JSFixedFastJsonLongIdKey.of(filteredData.getFilterKey()),
                    filteredData.getValue(),
                    filteredData.getMessage(),
                    filteredData.getHappenedDate(),
                    filteredData.getHappenedDateNanoOffset()
            );
        }
    }

    public static FilteredData toStackBean(JSFixedFastJsonFilteredData jsFixedFastFilteredData) {
        if (Objects.isNull(jsFixedFastFilteredData)) {
            return null;
        } else {
            return new FilteredData(
                    JSFixedFastJsonLongIdKey.toStackBean(jsFixedFastFilteredData.getPointKey()),
                    JSFixedFastJsonLongIdKey.toStackBean(jsFixedFastFilteredData.getFilterKey()),
                    jsFixedFastFilteredData.getValue(),
                    jsFixedFastFilteredData.getMessage(),
                    jsFixedFastFilteredData.getHappenedDate(),
                    jsFixedFastFilteredData.getHappenedDateNanoOffset()
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

    @JSONField(name = "happened_date", ordinal = 5)
    private Date happenedDate;

    @JSONField(name = "happened_date_nano_offset", ordinal = 6)
    private int happenedDateNanoOffset;

    public JSFixedFastJsonFilteredData() {
    }

    public JSFixedFastJsonFilteredData(
            JSFixedFastJsonLongIdKey pointKey, JSFixedFastJsonLongIdKey filterKey, Object value, String message
    ) {
        this(pointKey, filterKey, value, message, null, 0);
    }

    public JSFixedFastJsonFilteredData(
            JSFixedFastJsonLongIdKey pointKey, JSFixedFastJsonLongIdKey filterKey, Object value, String message,
            Date happenedDate, int happenedDateNanoOffset
    ) {
        this.pointKey = pointKey;
        this.filterKey = filterKey;
        this.value = value;
        this.message = message;
        this.happenedDate = happenedDate;
        this.happenedDateNanoOffset = happenedDateNanoOffset;
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
        return "JSFixedFastJsonFilteredData{" +
                "pointKey=" + pointKey +
                ", filterKey=" + filterKey +
                ", value=" + value +
                ", message='" + message + '\'' +
                ", happenedDate=" + happenedDate +
                ", happenedDateNanoOffset=" + happenedDateNanoOffset +
                '}';
    }
}
