package com.dwarfeng.fdr.stack.bean.dto;

import com.dwarfeng.fdr.stack.struct.Data;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import javax.annotation.Nonnull;
import java.util.Date;

/**
 * 被触发数据。
 *
 * <p>
 * 该实体表示被触发的数据，包含了数据的点位主键、触发器主键、数据的值、数据的发生时间。
 *
 * <p>
 * 该实体继承了 {@link Data} 接口。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class TriggeredData implements Data, Dto {

    private static final long serialVersionUID = -2535367965977677088L;

    private LongIdKey pointKey;
    private LongIdKey triggerKey;
    private Object value;
    private String message;
    private Date happenedDate;

    public TriggeredData() {
    }

    public TriggeredData(
            LongIdKey pointKey, LongIdKey triggerKey, Object value, String message, Date happenedDate
    ) {
        this.pointKey = pointKey;
        this.triggerKey = triggerKey;
        this.value = value;
        this.message = message;
        this.happenedDate = happenedDate;
    }

    @Nonnull
    @Override
    public LongIdKey getPointKey() {
        return pointKey;
    }

    public void setPointKey(LongIdKey pointKey) {
        this.pointKey = pointKey;
    }

    public LongIdKey getTriggerKey() {
        return triggerKey;
    }

    public void setTriggerKey(LongIdKey triggerKey) {
        this.triggerKey = triggerKey;
    }

    @Override
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

    @Nonnull
    @Override
    public Date getHappenedDate() {
        return happenedDate;
    }

    public void setHappenedDate(Date happenedDate) {
        this.happenedDate = happenedDate;
    }

    @Override
    public String toString() {
        return "TriggeredData{" +
                "pointKey=" + pointKey +
                ", triggerKey=" + triggerKey +
                ", value=" + value +
                ", message='" + message + '\'' +
                ", happenedDate=" + happenedDate +
                '}';
    }
}
