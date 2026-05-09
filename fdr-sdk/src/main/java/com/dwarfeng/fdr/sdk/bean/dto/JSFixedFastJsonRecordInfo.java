package com.dwarfeng.fdr.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.dto.RecordInfo;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.Objects;

/**
 * JSFixed FastJson 记录信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class JSFixedFastJsonRecordInfo implements Dto {

    private static final long serialVersionUID = -1480082027390226466L;

    public static JSFixedFastJsonRecordInfo of(RecordInfo recordInfo) {
        if (Objects.isNull(recordInfo)) {
            return null;
        } else {
            return new JSFixedFastJsonRecordInfo(
                    JSFixedFastJsonLongIdKey.of(recordInfo.getPointKey()),
                    recordInfo.getValue(),
                    recordInfo.getHappenedDate(),
                    recordInfo.getHappenedDateNanoOffset()
            );
        }
    }

    public static RecordInfo toStackBean(JSFixedFastJsonRecordInfo jsFixedFastRecordInfo) {
        if (Objects.isNull(jsFixedFastRecordInfo)) {
            return null;
        } else {
            return new RecordInfo(
                    JSFixedFastJsonLongIdKey.toStackBean(jsFixedFastRecordInfo.getPointKey()),
                    jsFixedFastRecordInfo.getValue(),
                    jsFixedFastRecordInfo.getHappenedDate(),
                    jsFixedFastRecordInfo.getHappenedDateNanoOffset()
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

    public JSFixedFastJsonRecordInfo() {
    }

    public JSFixedFastJsonRecordInfo(JSFixedFastJsonLongIdKey pointKey, Object value, Date happenedDate) {
        this(pointKey, value, happenedDate, 0);
    }

    public JSFixedFastJsonRecordInfo(
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
        return "JSFixedFastJsonRecordInfo{" +
                "pointKey=" + pointKey +
                ", value=" + value +
                ", happenedDate=" + happenedDate +
                ", happenedDateNanoOffset=" + happenedDateNanoOffset +
                '}';
    }
}
