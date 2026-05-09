package com.dwarfeng.fdr.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dwarfeng.fdr.stack.bean.dto.QueryResult;
import com.dwarfeng.fdr.stack.bean.dto.QueryResult.Sequence;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * FastJson 查看结果。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FastJsonQueryResult implements Dto {

    private static final long serialVersionUID = 6239635946386830843L;

    public static FastJsonQueryResult of(QueryResult queryResult) {
        if (Objects.isNull(queryResult)) {
            return null;
        } else {
            return new FastJsonQueryResult(
                    Optional.ofNullable(queryResult.getSequences()).map(
                            f -> f.stream().map(FastJsonSequence::of).collect(Collectors.toList())
                    ).orElse(null)
            );
        }
    }

    @JSONField(name = "sequences", ordinal = 1)
    private List<FastJsonSequence> sequences;

    public FastJsonQueryResult() {
    }

    public FastJsonQueryResult(List<FastJsonSequence> sequences) {
        this.sequences = sequences;
    }

    public List<FastJsonSequence> getSequences() {
        return sequences;
    }

    public void setSequences(List<FastJsonSequence> sequences) {
        this.sequences = sequences;
    }

    @Override
    public String toString() {
        return "FastJsonQueryResult{" +
                "sequences=" + sequences +
                '}';
    }

    /**
     * FastJson 序列。
     *
     * @author DwArFeng
     * @since 2.0.0
     */
    public static class FastJsonSequence implements Dto {

        private static final long serialVersionUID = -6855536877517759192L;

        public static FastJsonSequence of(Sequence sequence) {
            if (Objects.isNull(sequence)) {
                return null;
            } else {
                return new FastJsonSequence(
                        Optional.ofNullable(sequence.getItems()).map(
                                f -> f.stream().map(FastJsonItem::of).collect(Collectors.toList())
                        ).orElse(null),
                        sequence.getStartDate(),
                        sequence.getStartDateNanoOffset(),
                        sequence.getEndDate(),
                        sequence.getEndDateNanoOffset()
                );
            }
        }

        @JSONField(name = "items", ordinal = 1)
        private List<FastJsonItem> items;

        @JSONField(name = "start_date", ordinal = 2)
        private Date startDate;

        /**
         * @since 3.0.0
         */
        @JSONField(name = "start_date_nano_offset", ordinal = 3)
        private int startDateNanoOffset;

        @JSONField(name = "end_date", ordinal = 4)
        private Date endDate;

        /**
         * @since 3.0.0
         */
        @JSONField(name = "end_date_nano_offset", ordinal = 5)
        private int endDateNanoOffset;

        public FastJsonSequence() {
        }

        public FastJsonSequence(List<FastJsonItem> items, Date startDate, Date endDate) {
            this(items, startDate, 0, endDate, 0);
        }

        /**
         * @since 3.0.0
         */
        public FastJsonSequence(
                List<FastJsonItem> items, Date startDate, int startDateNanoOffset, Date endDate, int endDateNanoOffset
        ) {
            this.items = items;
            this.startDate = startDate;
            this.startDateNanoOffset = startDateNanoOffset;
            this.endDate = endDate;
            this.endDateNanoOffset = endDateNanoOffset;
        }

        public List<FastJsonItem> getItems() {
            return items;
        }

        public void setItems(List<FastJsonItem> items) {
            this.items = items;
        }

        public Date getStartDate() {
            return startDate;
        }

        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        /**
         * @since 3.0.0
         */
        public int getStartDateNanoOffset() {
            return startDateNanoOffset;
        }

        /**
         * @since 3.0.0
         */
        public void setStartDateNanoOffset(int startDateNanoOffset) {
            this.startDateNanoOffset = startDateNanoOffset;
        }

        public Date getEndDate() {
            return endDate;
        }

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }

        /**
         * @since 3.0.0
         */
        public int getEndDateNanoOffset() {
            return endDateNanoOffset;
        }

        /**
         * @since 3.0.0
         */
        public void setEndDateNanoOffset(int endDateNanoOffset) {
            this.endDateNanoOffset = endDateNanoOffset;
        }

        @Override
        public String toString() {
            return "FastJsonSequence{" +
                    "items=" + items +
                    ", startDate=" + startDate +
                    ", startDateNanoOffset=" + startDateNanoOffset +
                    ", endDate=" + endDate +
                    ", endDateNanoOffset=" + endDateNanoOffset +
                    '}';
        }
    }

    /**
     * FastJson 条目。
     *
     * @author DwArFeng
     * @since 2.0.0
     */
    public static class FastJsonItem implements Dto {

        private static final long serialVersionUID = -684258327622899440L;

        public static FastJsonItem of(QueryResult.Item item) {
            if (Objects.isNull(item)) {
                return null;
            } else {
                return new FastJsonItem(
                        FastJsonLongIdKey.of(item.getPointKey()),
                        item.getValue(),
                        item.getHappenedDate(),
                        item.getHappenedDateNanoOffset()
                );
            }
        }

        public static QueryResult.Item toStackBean(FastJsonItem fastItem) {
            if (Objects.isNull(fastItem)) {
                return null;
            } else {
                return new QueryResult.Item(
                        FastJsonLongIdKey.toStackBean(fastItem.getPointKey()),
                        fastItem.getValue(),
                        fastItem.getHappenedDate(),
                        fastItem.getHappenedDateNanoOffset()
                );
            }
        }

        @JSONField(name = "point_key", ordinal = 1)
        private FastJsonLongIdKey pointKey;

        @JSONField(name = "value", ordinal = 2, serialzeFeatures = SerializerFeature.WriteClassName)
        private Object value;

        @JSONField(name = "happened_date", ordinal = 3)
        private Date happenedDate;

        @JSONField(name = "happened_date_nano_offset", ordinal = 4)
        private int happenedDateNanoOffset;

        public FastJsonItem() {
        }

        public FastJsonItem(FastJsonLongIdKey pointKey, Object value, Date happenedDate) {
            this(pointKey, value, happenedDate, 0);
        }

        public FastJsonItem(FastJsonLongIdKey pointKey, Object value, Date happenedDate, int happenedDateNanoOffset) {
            this.pointKey = pointKey;
            this.value = value;
            this.happenedDate = happenedDate;
            this.happenedDateNanoOffset = happenedDateNanoOffset;
        }

        public FastJsonLongIdKey getPointKey() {
            return pointKey;
        }

        public void setPointKey(FastJsonLongIdKey pointKey) {
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

        public int getHappenedDateNanoOffset() {
            return happenedDateNanoOffset;
        }

        public void setHappenedDateNanoOffset(int happenedDateNanoOffset) {
            this.happenedDateNanoOffset = happenedDateNanoOffset;
        }

        @Override
        public String toString() {
            return "FastJsonItem{" +
                    "pointKey=" + pointKey +
                    ", value=" + value +
                    ", happenedDate=" + happenedDate +
                    ", happenedDateNanoOffset=" + happenedDateNanoOffset +
                    '}';
        }
    }
}
