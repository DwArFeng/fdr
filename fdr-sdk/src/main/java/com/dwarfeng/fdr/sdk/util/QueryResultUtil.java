package com.dwarfeng.fdr.sdk.util;

import com.dwarfeng.dutil.basic.time.TimeUtil;
import com.dwarfeng.fdr.stack.bean.dto.QueryResult;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

/**
 * 查看结果时间工具类。
 *
 * <p>
 * 为 {@link QueryResult.Sequence} 与 {@link QueryResult.Item} 提供组合时间
 * （毫秒 <code>Date</code> + 毫秒内纳秒偏移）相关的瞬时时间运算能力。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public final class QueryResultUtil {

    /**
     * 获取查看结果序列参照时间区间的开始瞬时时间。
     *
     * @param sequence 查看结果序列。
     * @return 开始瞬时时间。
     */
    public static Instant getStartInstant(QueryResult.Sequence sequence) {
        Objects.requireNonNull(sequence, "sequence");
        return TimeUtil.toInstant(sequence.getStartDate(), sequence.getStartDateNanoOffset());
    }

    /**
     * 设置查看结果序列参照时间区间的开始瞬时时间。
     *
     * @param sequence 查看结果序列。
     * @param instant  开始瞬时时间。
     */
    public static void setStartInstant(QueryResult.Sequence sequence, Instant instant) {
        Objects.requireNonNull(sequence, "sequence");
        sequence.setStartDate(TimeUtil.toDate(instant));
        sequence.setStartDateNanoOffset(TimeUtil.toNanoOffset(instant));
    }

    /**
     * 获取查看结果序列参照时间区间的结束瞬时时间。
     *
     * @param sequence 查看结果序列。
     * @return 结束瞬时时间。
     */
    public static Instant getEndInstant(QueryResult.Sequence sequence) {
        Objects.requireNonNull(sequence, "sequence");
        return TimeUtil.toInstant(sequence.getEndDate(), sequence.getEndDateNanoOffset());
    }

    /**
     * 设置查看结果序列参照时间区间的结束瞬时时间。
     *
     * @param sequence 查看结果序列。
     * @param instant  结束瞬时时间。
     */
    public static void setEndInstant(QueryResult.Sequence sequence, Instant instant) {
        Objects.requireNonNull(sequence, "sequence");
        sequence.setEndDate(TimeUtil.toDate(instant));
        sequence.setEndDateNanoOffset(TimeUtil.toNanoOffset(instant));
    }

    /**
     * 根据指定参数构造新的查看结果序列实例。
     *
     * <p>
     * 该方法会根据指定的瞬时时间同步设置参照时间区间起止时间与毫秒内纳秒偏移。
     *
     * @param pointKey     指定的点位主键。
     * @param items        指定的条目列表。
     * @param startInstant 指定的参照时间区间起始瞬时时间。
     * @param endInstant   指定的参照时间区间结束瞬时时间。
     * @return 新构造的查看结果序列实例。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static QueryResult.Sequence newSequenceInstance(
            LongIdKey pointKey, List<QueryResult.Item> items, Instant startInstant, Instant endInstant
    ) {
        Objects.requireNonNull(pointKey, "pointKey 不能为空");
        Objects.requireNonNull(items, "items 不能为空");
        Objects.requireNonNull(startInstant, "startInstant 不能为空");
        Objects.requireNonNull(endInstant, "endInstant 不能为空");

        return new QueryResult.Sequence(
                pointKey, items,
                TimeUtil.toDate(startInstant), TimeUtil.toNanoOffset(startInstant),
                TimeUtil.toDate(endInstant), TimeUtil.toNanoOffset(endInstant)
        );
    }

    /**
     * 获取查看结果条目的发生瞬时时间。
     *
     * @param item 查看结果条目。
     * @return 查看结果条目的发生瞬时时间。
     */
    public static Instant getHappenedInstant(QueryResult.Item item) {
        Objects.requireNonNull(item, "item");
        return DataUtil.getHappenedInstant(item);
    }

    /**
     * 设置查看结果条目的发生瞬时时间。
     *
     * @param item    查看结果条目。
     * @param instant 发生瞬时时间。
     */
    public static void setHappenedInstant(QueryResult.Item item, Instant instant) {
        Objects.requireNonNull(item, "item");
        item.setHappenedDate(TimeUtil.toDate(instant));
        item.setHappenedDateNanoOffset(TimeUtil.toNanoOffset(instant));
    }

    /**
     * 根据指定参数构造新的查看结果条目实例。
     *
     * <p>
     * 该方法会根据指定的瞬时时间同步设置发生时间与毫秒内纳秒偏移。
     *
     * @param pointKey        指定的点位主键。
     * @param value           指定的值。
     * @param happenedInstant 指定的发生瞬时时间。
     * @return 新构造的查看结果条目实例。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static QueryResult.Item newItemInstance(LongIdKey pointKey, Object value, Instant happenedInstant) {
        Objects.requireNonNull(pointKey, "pointKey 不能为空");
        Objects.requireNonNull(happenedInstant, "happenedInstant 不能为空");

        return new QueryResult.Item(
                pointKey, value, TimeUtil.toDate(happenedInstant), TimeUtil.toNanoOffset(happenedInstant)
        );
    }

    private QueryResultUtil() {
        throw new IllegalStateException("禁止外部实例化");
    }
}
