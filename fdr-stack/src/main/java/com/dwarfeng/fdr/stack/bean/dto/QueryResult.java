package com.dwarfeng.fdr.stack.bean.dto;

import com.dwarfeng.fdr.stack.struct.Data;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import javax.annotation.Nonnull;
import java.util.Date;
import java.util.List;

/**
 * 查看结果。
 *
 * <p>
 * 该实体表示查看结果，包含了查看的序列。
 *
 * <p>
 * 有关查看的详细信息，请参阅术语。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class QueryResult implements Dto {

    private static final long serialVersionUID = 5419873655268858032L;

    private List<Sequence> sequences;

    public QueryResult() {
    }

    public QueryResult(List<Sequence> sequences) {
        this.sequences = sequences;
    }

    public List<Sequence> getSequences() {
        return sequences;
    }

    public void setSequences(List<Sequence> sequences) {
        this.sequences = sequences;
    }

    @Override
    public String toString() {
        return "QueryResult{" +
                "sequences=" + sequences +
                '}';
    }

    /**
     * 序列。
     *
     * <p>
     * 该实体表示序列，包含了序列的数据、序列的开始时间和序列的结束时间。
     *
     * <p>
     * 序列是查看结果的组成部分。
     *
     * <p>
     * 有关查看的详细信息，请参阅术语。
     *
     * @author DwArFeng
     * @since 2.0.0
     */
    public static class Sequence implements Dto {

        private static final long serialVersionUID = -70921132759022033L;

        private LongIdKey pointKey;
        private List<Item> items;
        private Date startDate;
        private Date endDate;

        public Sequence() {
        }

        public Sequence(LongIdKey pointKey, List<Item> items, Date startDate, Date endDate) {
            this.pointKey = pointKey;
            this.items = items;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public LongIdKey getPointKey() {
            return pointKey;
        }

        public void setPointKey(LongIdKey pointKey) {
            this.pointKey = pointKey;
        }

        public List<Item> getItems() {
            return items;
        }

        public void setItems(List<Item> items) {
            this.items = items;
        }

        public Date getStartDate() {
            return startDate;
        }

        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        public Date getEndDate() {
            return endDate;
        }

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
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
     * 该实体包含了数据的主键、数据的值和数据的发生时间。
     *
     * <p>
     * 条目是序列的组成部分。
     *
     * <p>
     * 有关查看的详细信息，请参阅术语。
     *
     * @author DwArFeng
     * @since 2.0.0
     */
    public static class Item implements Data, Dto {

        private static final long serialVersionUID = 4131477003995249721L;

        private LongIdKey pointKey;
        private Object value;
        private Date happenedDate;

        public Item() {
        }

        public Item(LongIdKey pointKey, Object value, Date happenedDate) {
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
            return "Item{" +
                    "pointKey=" + pointKey +
                    ", value=" + value +
                    ", happenedDate=" + happenedDate +
                    '}';
        }
    }
}
