package com.dwarfeng.fdr.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.dto.QueryResult;
import com.dwarfeng.fdr.stack.bean.dto.QueryResult.Sequence;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * JSFixed FastJson 查看结果。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class JSFixedFastJsonQueryResult implements Dto {

    private static final long serialVersionUID = -1820032032230454159L;

    public static JSFixedFastJsonQueryResult of(QueryResult queryResult) {
        if (Objects.isNull(queryResult)) {
            return null;
        } else {
            return new JSFixedFastJsonQueryResult(
                    Optional.ofNullable(queryResult.getSequences()).map(
                            f -> f.stream().map(JSFixedFastJsonSequence::of).collect(Collectors.toList())
                    ).orElse(null)
            );
        }
    }

    @JSONField(name = "sequences", ordinal = 1)
    private List<JSFixedFastJsonSequence> sequences;

    public JSFixedFastJsonQueryResult() {
    }

    public JSFixedFastJsonQueryResult(List<JSFixedFastJsonSequence> sequences) {
        this.sequences = sequences;
    }

    public List<JSFixedFastJsonSequence> getSequences() {
        return sequences;
    }

    public void setSequences(List<JSFixedFastJsonSequence> sequences) {
        this.sequences = sequences;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonQueryResult{" +
                "sequences=" + sequences +
                '}';
    }

    /**
     * JSFixed FastJson 序列。
     *
     * @author DwArFeng
     * @since 2.0.0
     */
    public static class JSFixedFastJsonSequence implements Dto {

        private static final long serialVersionUID = 5287456081591604732L;

        public static JSFixedFastJsonSequence of(Sequence sequence) {
            if (Objects.isNull(sequence)) {
                return null;
            } else {
                return new JSFixedFastJsonSequence(
                        Optional.ofNullable(sequence.getItems()).map(
                                f -> f.stream().map(JSFixedFastJsonItem::of).collect(Collectors.toList())
                        ).orElse(null),
                        sequence.getStartDate(),
                        sequence.getStartDateNanoOffset(),
                        sequence.getEndDate(),
                        sequence.getEndDateNanoOffset()
                );
            }
        }

        @JSONField(name = "items", ordinal = 1)
        private List<JSFixedFastJsonItem> items;

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

        public JSFixedFastJsonSequence() {
        }

        public JSFixedFastJsonSequence(List<JSFixedFastJsonItem> items, Date startDate, Date endDate) {
            this(items, startDate, 0, endDate, 0);
        }

        /**
         * @since 3.0.0
         */
        public JSFixedFastJsonSequence(
                List<JSFixedFastJsonItem> items, Date startDate, int startDateNanoOffset,
                Date endDate, int endDateNanoOffset
        ) {
            this.items = items;
            this.startDate = startDate;
            this.startDateNanoOffset = startDateNanoOffset;
            this.endDate = endDate;
            this.endDateNanoOffset = endDateNanoOffset;
        }

        public List<JSFixedFastJsonItem> getItems() {
            return items;
        }

        public void setItems(List<JSFixedFastJsonItem> items) {
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
            return "JSFixedFastJsonSequence{" +
                    "items=" + items +
                    ", startDate=" + startDate +
                    ", startDateNanoOffset=" + startDateNanoOffset +
                    ", endDate=" + endDate +
                    ", endDateNanoOffset=" + endDateNanoOffset +
                    '}';
        }
    }

    /**
     * JSFixed FastJson 条目。
     *
     * @author DwArFeng
     * @since 2.0.0
     */
    public static class JSFixedFastJsonItem implements Dto {

        private static final long serialVersionUID = 5561249243224524300L;

        public static JSFixedFastJsonItem of(QueryResult.Item item) {
            if (Objects.isNull(item)) {
                return null;
            } else {
                return new JSFixedFastJsonItem(
                        JSFixedFastJsonLongIdKey.of(item.getPointKey()),
                        item.getValue(),
                        item.getHappenedDate(),
                        item.getHappenedDateNanoOffset()
                );
            }
        }

        public static QueryResult.Item toStackBean(JSFixedFastJsonItem jsFixedFastItem) {
            if (Objects.isNull(jsFixedFastItem)) {
                return null;
            } else {
                return new QueryResult.Item(
                        JSFixedFastJsonLongIdKey.toStackBean(jsFixedFastItem.getPointKey()),
                        jsFixedFastItem.getValue(),
                        jsFixedFastItem.getHappenedDate(),
                        jsFixedFastItem.getHappenedDateNanoOffset()
                );
            }
        }

        @JSONField(name = "point_key", ordinal = 1)
        private JSFixedFastJsonLongIdKey pointKey;

        @JSONField(name = "value", ordinal = 2)
        private Object value;

        @JSONField(name = "happened_date", ordinal = 3)
        private Date happenedDate;

        @JSONField(name = "happened_date_nano_offset", ordinal = 4)
        private int happenedDateNanoOffset;

        public JSFixedFastJsonItem() {
        }

        public JSFixedFastJsonItem(JSFixedFastJsonLongIdKey pointKey, Object value, Date happenedDate) {
            this(pointKey, value, happenedDate, 0);
        }

        public JSFixedFastJsonItem(
                JSFixedFastJsonLongIdKey pointKey, Object value, Date happenedDate, int happenedDateNanoOffset
        ) {
            this.pointKey = pointKey;
            this.value = value;
            this.happenedDate = happenedDate;
            this.happenedDateNanoOffset = happenedDateNanoOffset;
        }

        public JSFixedFastJsonLongIdKey getPointKey() {
            return pointKey;
        }

        public void setPointKey(JSFixedFastJsonLongIdKey pointKey) {
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
            return "JSFixedFastJsonItem{" +
                    "pointKey=" + pointKey +
                    ", value=" + value +
                    ", happenedDate=" + happenedDate +
                    ", happenedDateNanoOffset=" + happenedDateNanoOffset +
                    '}';
        }
    }
}
