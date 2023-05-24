package com.dwarfeng.fdr.stack.bean.dto;

import com.dwarfeng.fdr.stack.structure.Data;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import javax.annotation.Nonnull;
import java.util.Date;

/**
 * 一般数据。
 *
 * <p>
 * 该实体表示正常记录的数据，包含了数据的点位主键、数据的值、数据的发生时间。
 *
 * <p>
 * 该实体继承了 {@link Data} 接口。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class NormalData implements Data, Dto {

    private static final long serialVersionUID = 8974709216835006306L;

    private LongIdKey pointKey;
    private Object value;
    private Date happenedDate;

    public NormalData() {
    }

    public NormalData(LongIdKey pointKey, Object value, Date happenedDate) {
        this.pointKey = pointKey;
        this.value = value;
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

    @Override
    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
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
        return "NormalData{" +
                "pointKey=" + pointKey +
                ", value=" + value +
                ", happenedDate=" + happenedDate +
                '}';
    }
}
