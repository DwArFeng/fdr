package com.dwarfeng.fdr.stack.struct;

import com.dwarfeng.fdr.stack.bean.dto.NormalData;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;

/**
 * 记录记忆。
 *
 * <p>
 * 该结构表示记录记忆中的单条记录，包含数据的点位主键、值、发生时间。
 *
 * @author DwArFeng
 * @since 2.5.0
 */
public final class RecordMemory {

    /**
     * 点位主键。
     */
    private final LongIdKey pointKey;

    /**
     * 数据发生时间。
     */
    private final Date happenedDate;

    /**
     * 原始数据。
     */
    private final Object rawValue;

    /**
     * 是否通过。
     *
     * <p>
     * 通过的定义是指数据通过了记录机制的数据处理，最终转换为了一般数据（{@link NormalData}）。
     *
     * <p>
     * 如果该字段的值为 <code>false</code>，那么可能的原因为：
     * <ul>
     *     <li>数据没通过过滤器的测试。</li>
     * </ul>
     *
     * @see NormalData
     */
    private final boolean passed;

    /**
     * 清洗后的数据，如被过滤则为 {@code null}。
     */
    private final Object value;

    public RecordMemory(LongIdKey pointKey, Date happenedDate, Object rawValue, boolean passed, Object value) {
        this.pointKey = pointKey;
        this.happenedDate = happenedDate;
        this.rawValue = rawValue;
        this.passed = passed;
        this.value = value;
    }

    public LongIdKey getPointKey() {
        return pointKey;
    }

    public Date getHappenedDate() {
        return happenedDate;
    }

    public Object getRawValue() {
        return rawValue;
    }

    public boolean isPassed() {
        return passed;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "RecordMemory{" +
                "pointKey=" + pointKey +
                ", happenedDate=" + happenedDate +
                ", rawValue=" + rawValue +
                ", passed=" + passed +
                ", value=" + value +
                '}';
    }
}
