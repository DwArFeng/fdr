package com.dwarfeng.fdr.stack.bean.dto;

import com.dwarfeng.fdr.stack.struct.Data;
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
 * <p>
 * 在 3.0.0 版本中，新增了数据发生时间在毫秒内的纳秒偏移。
 * 数据发生时间由 {@link #happenedDate} 和 {@link #happenedDateNanoOffset} 共同唯一确定。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class NormalData implements Data, Dto {

    private static final long serialVersionUID = 3227747393478021761L;

    private LongIdKey pointKey;
    private Object value;
    private Date happenedDate;

    /**
     * @since 3.0.0
     */
    private int happenedDateNanoOffset;

    public NormalData() {
    }

    public NormalData(LongIdKey pointKey, Object value, Date happenedDate) {
        this(pointKey, value, happenedDate, 0);
    }

    /**
     * @since 3.0.0
     */
    public NormalData(LongIdKey pointKey, Object value, Date happenedDate, int happenedDateNanoOffset) {
        this.pointKey = pointKey;
        this.value = value;
        this.happenedDate = happenedDate;
        this.happenedDateNanoOffset = happenedDateNanoOffset;
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
    public int getHappenedDateNanoOffset() {
        return happenedDateNanoOffset;
    }

    public void setHappenedDateNanoOffset(int happenedDateNanoOffset) {
        this.happenedDateNanoOffset = happenedDateNanoOffset;
    }

    @Override
    public String toString() {
        return "NormalData{" +
                "pointKey=" + pointKey +
                ", value=" + value +
                ", happenedDate=" + happenedDate +
                ", happenedDateNanoOffset=" + happenedDateNanoOffset +
                '}';
    }
}
