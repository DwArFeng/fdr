package com.dwarfeng.fdr.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.dto.NormalData;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.Objects;

/**
 * JSFixed FastJson 一般数据。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class JSFixedFastJsonNormalData implements Dto {

    private static final long serialVersionUID = 75713181928884992L;

    public static JSFixedFastJsonNormalData of(NormalData normalData) {
        if (Objects.isNull(normalData)) {
            return null;
        } else {
            return new JSFixedFastJsonNormalData(
                    JSFixedFastJsonLongIdKey.of(normalData.getPointKey()),
                    normalData.getValue(),
                    normalData.getHappenedDate(),
                    normalData.getHappenedDateNanoOffset()
            );
        }
    }

    public static NormalData toStackBean(JSFixedFastJsonNormalData jsFixedFastNormalData) {
        if (Objects.isNull(jsFixedFastNormalData)) {
            return null;
        } else {
            return new NormalData(
                    JSFixedFastJsonLongIdKey.toStackBean(jsFixedFastNormalData.getPointKey()),
                    jsFixedFastNormalData.getValue(),
                    jsFixedFastNormalData.getHappenedDate(),
                    jsFixedFastNormalData.getHappenedDateNanoOffset()
            );
        }
    }

    @JSONField(name = "point_key", ordinal = 1)
    private JSFixedFastJsonLongIdKey pointKey;

    @JSONField(name = "value", ordinal = 2)
    private Object value;

    @JSONField(name = "happened_date", ordinal = 3)
    private Date happenedDate;

    @JSONField(name = "happened_date_nano_offset", ordinal = 4)
    private int happenedDateNanoOffset;

    public JSFixedFastJsonNormalData() {
    }

    public JSFixedFastJsonNormalData(JSFixedFastJsonLongIdKey pointKey, Object value, Date happenedDate) {
        this(pointKey, value, happenedDate, 0);
    }

    public JSFixedFastJsonNormalData(
            JSFixedFastJsonLongIdKey pointKey, Object value, Date happenedDate, int happenedDateNanoOffset
    ) {
        this.pointKey = pointKey;
        this.value = value;
        this.happenedDate = happenedDate;
        this.happenedDateNanoOffset = happenedDateNanoOffset;
    }

    public JSFixedFastJsonLongIdKey getPointKey() {
        return pointKey;
    }

    public void setPointKey(JSFixedFastJsonLongIdKey pointKey) {
        this.pointKey = pointKey;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
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
        return "JSFixedFastJsonNormalData{" +
                "pointKey=" + pointKey +
                ", value=" + value +
                ", happenedDate=" + happenedDate +
                ", happenedDateNanoOffset=" + happenedDateNanoOffset +
                '}';
    }
}
