package com.dwarfeng.fdr.sdk.util;

import com.dwarfeng.dutil.basic.time.TimeUtil;
import com.dwarfeng.fdr.stack.handler.Mapper;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 映射器时间工具类。
 *
 * <p>
 * 为 {@link Mapper.Sequence} 与 {@link Mapper.Item} 提供组合时间（毫秒 <code>Date</code> + 毫秒内纳秒偏移）相关的运算能力。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public final class MapperUtil {

    /**
     * 获取序列参照时间区间的开始瞬时时间。
     *
     * @param sequence 数据序列。
     * @return 开始瞬时时间。
     */
    public static Instant getStartInstant(Mapper.Sequence sequence) {
        Objects.requireNonNull(sequence, "sequence");
        return TimeUtil.toInstant(sequence.getStartDate(), sequence.getStartDateNanoOffset());
    }

    /**
     * 获取序列参照时间区间的结束瞬时时间。
     *
     * @param sequence 数据序列。
     * @return 结束瞬时时间。
     */
    public static Instant getEndInstant(Mapper.Sequence sequence) {
        Objects.requireNonNull(sequence, "sequence");
        return TimeUtil.toInstant(sequence.getEndDate(), sequence.getEndDateNanoOffset());
    }

    /**
     * 将自 epoch 起的纳秒数转换为 <code>Instant</code>。
     *
     * @param epochNanos 自 epoch 起的纳秒数。
     * @return 对应的瞬时时间。
     */
    public static Instant instantFromEpochNanos(long epochNanos) {
        return Instant.ofEpochSecond(
                Math.floorDiv(epochNanos, 1_000_000_000L),
                Math.floorMod(epochNanos, 1_000_000_000L)
        );
    }

    /**
     * 将（毫秒时间戳 + 毫秒内纳秒偏移）转为从 epoch 起的纳秒数，用于时长计算。
     *
     * @param happenedDate           毫秒时间。
     * @param happenedDateNanoOffset 毫秒内纳秒偏移。
     * @return 自 epoch 起的纳秒数。
     */
    public static long toEpochNanos(Date happenedDate, int happenedDateNanoOffset) {
        return Math.addExact(
                Math.multiplyExact(happenedDate.getTime(), 1_000_000L),
                happenedDateNanoOffset
        );
    }

    /**
     * 较晚条目相对较早条目的时间间隔（纳秒），结果非负时由调用方保证次序。
     *
     * @param earlier 较早条目。
     * @param later   较晚条目。
     * @return 间隔纳秒数。
     */
    public static long nanosAfter(Mapper.Item earlier, Mapper.Item later) {
        return toEpochNanos(later.getHappenedDate(), later.getHappenedDateNanoOffset())
                - toEpochNanos(earlier.getHappenedDate(), earlier.getHappenedDateNanoOffset());
    }

    /**
     * 对 items 按照时间进行升序排序，并且过滤在参照时间区间之外的数据。
     *
     * <p>
     * 如果 extendItem 为 true，则会保留至多一个发生时间在 start 之前的数据条目，
     * 以及至多一个发生时间在 end 之后的数据条目。
     *
     * @param items      指定的数据条目序列。
     * @param startDate  区间开始时间的毫秒部分。
     * @param startNano  区间开始时间在对应毫秒内的纳秒偏移。
     * @param endDate    区间结束时间的毫秒部分。
     * @param endNano    区间结束时间在对应毫秒内的纳秒偏移。
     * @param extendItem 指定是否扩展数据条目。
     * @return 排序并且过滤后的数据条目序列。
     */
    public static List<Mapper.Item> sortAndFilterItems(
            List<Mapper.Item> items, Date startDate, int startNano, Date endDate, int endNano, boolean extendItem
    ) {
        items.sort(CompareUtil.DATA_HAPPENED_INSTANT_ASC_COMPARATOR);

        int firstIndex = 0;
        for (int i = 0; i < items.size(); i++) {
            Mapper.Item item = items.get(i);
            if (extendItem) {
                if (TimeUtil.compare(
                        item.getHappenedDate(), item.getHappenedDateNanoOffset(), startDate, startNano
                ) <= 0) {
                    firstIndex = i;
                } else {
                    break;
                }
            } else {
                if (TimeUtil.compare(
                        item.getHappenedDate(), item.getHappenedDateNanoOffset(), startDate, startNano
                ) >= 0) {
                    firstIndex = i;
                    break;
                }
            }
        }

        int lastIndex = items.size() - 1;
        for (int i = items.size() - 1; i >= 0; i--) {
            Mapper.Item item = items.get(i);
            if (extendItem) {
                if (TimeUtil.compare(
                        item.getHappenedDate(), item.getHappenedDateNanoOffset(), endDate, endNano
                ) >= 0) {
                    lastIndex = i;
                } else {
                    break;
                }
            } else {
                if (TimeUtil.compare(
                        item.getHappenedDate(), item.getHappenedDateNanoOffset(), endDate, endNano
                ) <= 0) {
                    lastIndex = i;
                    break;
                }
            }
        }

        if (firstIndex > lastIndex) {
            return items.subList(0, 0);
        }

        return items.subList(firstIndex, lastIndex + 1);
    }

    private MapperUtil() {
        throw new IllegalStateException("禁止实例化");
    }
}
