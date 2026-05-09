package com.dwarfeng.fdr.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.dto.RecordInfo;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.Objects;

/**
 * FastJson 记录信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FastJsonRecordInfo implements Dto {

    private static final long serialVersionUID = -1190893035020373731L;

    public static FastJsonRecordInfo of(RecordInfo recordInfo) {
        if (Objects.isNull(recordInfo)) {
            return null;
        } else {
            return new FastJsonRecordInfo(
                    FastJsonLongIdKey.of(recordInfo.getPointKey()),
                    recordInfo.getValue(),
                    recordInfo.getHappenedDate(),
                    recordInfo.getHappenedDateNanoOffset()
            );
        }
    }

    public static RecordInfo toStackBean(FastJsonRecordInfo fastRecordInfo) {
        if (Objects.isNull(fastRecordInfo)) {
            return null;
        } else {
            return new RecordInfo(
                    FastJsonLongIdKey.toStackBean(fastRecordInfo.getPointKey()),
                    fastRecordInfo.getValue(),
                    fastRecordInfo.getHappenedDate(),
                    fastRecordInfo.getHappenedDateNanoOffset()
            );
        }
    }

    @JSONField(name = "point_key", ordinal = 1)
    private FastJsonLongIdKey pointKey;

    @JSONField(name = "value", ordinal = 2)
    private Object value;

    @JSONField(name = "happened_date", ordinal = 3)
    private Date happenedDate;

    @JSONField(name = "happened_date_nano_offset", ordinal = 4)
    private int happenedDateNanoOffset;

    public FastJsonRecordInfo() {
    }

    public FastJsonRecordInfo(FastJsonLongIdKey pointKey, Object value, Date happenedDate) {
        this(pointKey, value, happenedDate, 0);
    }

    public FastJsonRecordInfo(FastJsonLongIdKey pointKey, Object value, Date happenedDate, int happenedDateNanoOffset) {
        this.pointKey = pointKey;
        this.value = value;
        this.happenedDate = happenedDate;
        this.happenedDateNanoOffset = happenedDateNanoOffset;
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

    public int getHappenedDateNanoOffset() {
        return happenedDateNanoOffset;
    }

    public void setHappenedDateNanoOffset(int happenedDateNanoOffset) {
        this.happenedDateNanoOffset = happenedDateNanoOffset;
    }

    @Override
    public String toString() {
        return "FastJsonRecordInfo{" +
                "pointKey=" + pointKey +
                ", value=" + value +
                ", happenedDate=" + happenedDate +
                ", happenedDateNanoOffset=" + happenedDateNanoOffset +
                '}';
    }
}
