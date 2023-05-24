package com.dwarfeng.fdr.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.dto.TriggeredData;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.Objects;

/**
 * FastJson 被触发数据。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FastJsonTriggeredData implements Dto {

    private static final long serialVersionUID = 7136563316840846416L;

    public static FastJsonTriggeredData of(TriggeredData triggeredData) {
        if (Objects.isNull(triggeredData)) {
            return null;
        } else {
            return new FastJsonTriggeredData(
                    FastJsonLongIdKey.of(triggeredData.getPointKey()),
                    FastJsonLongIdKey.of(triggeredData.getTriggerKey()),
                    triggeredData.getValue(),
                    triggeredData.getMessage(),
                    triggeredData.getHappenedDate()
            );
        }
    }

    @JSONField(name = "point_key", ordinal = 1)
    private FastJsonLongIdKey pointKey;

    @JSONField(name = "trigger_key", ordinal = 2)
    private FastJsonLongIdKey triggerKey;

    @JSONField(name = "value", ordinal = 3)
    private Object value;

    @JSONField(name = "message", ordinal = 4)
    private String message;

    @JSONField(name = "happened_date", ordinal = 5)
    private Date happenedDate;

    public FastJsonTriggeredData() {
    }

    public FastJsonTriggeredData(
            FastJsonLongIdKey pointKey, FastJsonLongIdKey triggerKey, Object value, String message, Date happenedDate
    ) {
        this.pointKey = pointKey;
        this.triggerKey = triggerKey;
        this.value = value;
        this.message = message;
        this.happenedDate = happenedDate;
    }

    public FastJsonLongIdKey getPointKey() {
        return pointKey;
    }

    public void setPointKey(FastJsonLongIdKey pointKey) {
        this.pointKey = pointKey;
    }

    public FastJsonLongIdKey getTriggerKey() {
        return triggerKey;
    }

    public void setTriggerKey(FastJsonLongIdKey triggerKey) {
        this.triggerKey = triggerKey;
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

    @Override
    public String toString() {
        return "FastJsonTriggeredData{" +
                "pointKey=" + pointKey +
                ", triggerKey=" + triggerKey +
                ", value=" + value +
                ", message='" + message + '\'' +
                ", happenedDate=" + happenedDate +
                '}';
    }
}
