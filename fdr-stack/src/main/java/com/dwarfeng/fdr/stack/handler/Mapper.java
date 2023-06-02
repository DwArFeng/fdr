package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.fdr.stack.exception.MapperException;
import com.dwarfeng.fdr.stack.struct.Data;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Date;
import java.util.List;

/**
 * 映射器。
 *
 * <p>
 * 映射器在数据查看过程中的第二步被调用，用于将传入的序列映射为新的序列。
 *
 * <p>
 * 每个映射器可以接受一个映射参数对象，该对象用于控制映射器的行为。
 *
 * <p>
 * 关于查看的详细信息，请参阅术语。
 *
 * @author DwArFeng
 * @since 1.4.1
 */
public interface Mapper {

    /**
     * 映射序列列表。
     *
     * @param mapParam  映射参数。
     * @param sequences 待映射的序列组成的列表。
     * @return 映射后的序列组成的列表。
     * @throws MapperException 映射器异常。
     */
    List<Sequence> map(MapParam mapParam, List<Sequence> sequences) throws MapperException;

    /**
     * 映射参数。
     *
     * <p>
     * 映射参数用于控制映射器的行为。
     *
     * @author DwArFeng
     * @since 2.0.0
     */
    final class MapParam {

        private final String param;

        public MapParam(String param) {
            this.param = param;
        }

        public String getParam() {
            return param;
        }

        @Override
        public String toString() {
            return "MapParam{" +
                    "param='" + param + '\'' +
                    '}';
        }
    }

    /**
     * 数据序列。
     *
     * <p>
     * 数据序列是一个由数据组成的列表，每个数据都包含了条目、开始时间、结束时间、前一条数据、后一条数据。
     *
     * <p>
     * 数据序列的开始时间和结束时间是指该序列参与映射的参照时间，序列中的条目列表中的所有数据都应该在该时间段内。<br>
     * 在部分情况下，数据序列中的条目不恰好发生在起始时间或结束时间点上。
     *
     * <p>
     * 需要注意的是：
     * <ul>
     *     <li>
     *         数据序列应保证其中所有的数据条目的数据点主键都与序列本身的数据点主键相同。
     *     </li>
     *     <li>
     *         数据序列中的条目列表中的数据不一定是按照时间顺序排列的。数据序列的顺序和查询结果以及之前的映射结果有关。<br>
     *         可以使用 sdk 模块中的数据工具类获取比较器，对数据进行排序。
     *     </li>
     * </ul>
     */
    final class Sequence {

        @Nonnull
        private final LongIdKey pointKey;

        @Nonnull
        private final List<Item> items;

        @Nonnull
        private final Date startDate;

        @Nonnull
        private final Date endDate;

        public Sequence(
                @Nonnull LongIdKey pointKey, @Nonnull List<Item> items, @Nonnull Date startDate, @Nonnull Date endDate
        ) {
            this.pointKey = pointKey;
            this.items = items;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        @Nonnull
        public LongIdKey getPointKey() {
            return pointKey;
        }

        @Nonnull
        public List<Item> getItems() {
            return items;
        }

        @Nonnull
        public Date getStartDate() {
            return startDate;
        }

        @Nonnull
        public Date getEndDate() {
            return endDate;
        }

        @Override
        public String toString() {
            return "Sequence{" +
                    "pointKey=" + pointKey +
                    ", items=" + items +
                    ", startDate=" + startDate +
                    ", endDate=" + endDate +
                    '}';
        }
    }

    /**
     * 条目。
     *
     * <p>
     * 条目是一个数据，它包含了数据点的键、数据值、发生时间。
     *
     * @author DwArFeng
     * @since 2.0.0
     */
    final class Item implements Data {

        @Nonnull
        private final LongIdKey pointKey;

        @Nullable
        private final Object value;

        @Nonnull
        private final Date happenedDate;

        public Item(@Nonnull LongIdKey pointKey, @Nullable Object value, @Nonnull Date happenedDate) {
            this.pointKey = pointKey;
            this.value = value;
            this.happenedDate = happenedDate;
        }

        @Nonnull
        @Override
        public LongIdKey getPointKey() {
            return pointKey;
        }

        @Nullable
        @Override
        public Object getValue() {
            return value;
        }

        @Nonnull
        @Override
        public Date getHappenedDate() {
            return happenedDate;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "pointKey=" + pointKey +
                    ", value=" + value +
                    ", happenedDate=" + happenedDate +
                    '}';
        }
    }
}
