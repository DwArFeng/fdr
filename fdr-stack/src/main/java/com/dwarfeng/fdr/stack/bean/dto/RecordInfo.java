package com.dwarfeng.fdr.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;

/**
 * 记录信息。
 *
 * <p>
 * 该实体表示记录信息，包含了记录的点位主键、记录的值、记录的发生时间。
 *
 * <p>
 * 有关记录的详细信息，请参阅术语。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class RecordInfo implements Dto {

    private static final long serialVersionUID = 1736742714193263141L;

    private LongIdKey pointKey;
    private Object value;
    private Date happenedDate;

    public RecordInfo() {
    }

    public RecordInfo(LongIdKey pointKey, Object value, Date happenedDate) {
        this.pointKey = pointKey;
        this.value = value;
        this.happenedDate = happenedDate;
    }

    public LongIdKey getPointKey() {
        return pointKey;
    }

    public void setPointKey(LongIdKey pointKey) {
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
        return "RecordInfo{" +
                "pointKey=" + pointKey +
                ", value=" + value +
                ", happenedDate=" + happenedDate +
                '}';
    }
}
