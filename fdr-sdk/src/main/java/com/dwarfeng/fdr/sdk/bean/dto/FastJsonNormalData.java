package com.dwarfeng.fdr.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.dto.NormalData;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.Objects;

/**
 * FastJson 一般数据。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FastJsonNormalData implements Dto {

    private static final long serialVersionUID = 4268721908843113764L;

    public static FastJsonNormalData of(NormalData normalData) {
        if (Objects.isNull(normalData)) {
            return null;
        } else {
            return new FastJsonNormalData(
                    FastJsonLongIdKey.of(normalData.getPointKey()),
                    normalData.getValue(),
                    normalData.getHappenedDate()
            );
        }
    }

    public static NormalData toStackBean(FastJsonNormalData fastNormalData) {
        if (Objects.isNull(fastNormalData)) {
            return null;
        } else {
            return new NormalData(
                    FastJsonLongIdKey.toStackBean(fastNormalData.getPointKey()),
                    fastNormalData.getValue(),
                    fastNormalData.getHappenedDate()
            );
        }
    }

    @JSONField(name = "point_key", ordinal = 1)
    private FastJsonLongIdKey pointKey;

    @JSONField(name = "value", ordinal = 2)
    private Object value;

    @JSONField(name = "happened_date", ordinal = 3)
    private Date happenedDate;

    public FastJsonNormalData() {
    }

    public FastJsonNormalData(FastJsonLongIdKey pointKey, Object value, Date happenedDate) {
        this.pointKey = pointKey;
        this.value = value;
        this.happenedDate = happenedDate;
    }

    public FastJsonLongIdKey getPointKey() {
        return pointKey;
    }

    public void setPointKey(FastJsonLongIdKey pointKey) {
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

    @Override
    public String toString() {
        return "FastJsonNormalData{" +
                "pointKey=" + pointKey +
                ", value=" + value +
                ", happenedDate=" + happenedDate +
                '}';
    }
}
