package com.dwarfeng.fdr.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.dto.LookupResult;
import com.dwarfeng.fdr.stack.bean.dto.LookupResult.Sequence;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * FastJson 查看结果。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FastJsonLookupResult implements Dto {

    private static final long serialVersionUID = 2631293356830103063L;

    public static FastJsonLookupResult of(LookupResult lookupResult) {
        if (Objects.isNull(lookupResult)) {
            return null;
        } else {
            return new FastJsonLookupResult(
                    lookupResult.getSequences().stream().map(FastJsonSequence::of).collect(Collectors.toList())
            );
        }
    }

    @JSONField(name = "sequences", ordinal = 1)
    private List<FastJsonSequence> sequences;

    public FastJsonLookupResult() {
    }

    public FastJsonLookupResult(List<FastJsonSequence> sequences) {
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
        return "FastJsonLookupResult{" +
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

        private static final long serialVersionUID = 9114085075247785703L;

        public static FastJsonSequence of(Sequence sequence) {
            if (Objects.isNull(sequence)) {
                return null;
            } else {
                return new FastJsonSequence(
                        sequence.getItems().stream().map(FastJsonItem::of).collect(Collectors.toList()),
                        sequence.getStartDate(),
                        sequence.getEndDate()
                );
            }
        }

        @JSONField(name = "items", ordinal = 1)
        private List<FastJsonItem> items;

        @JSONField(name = "start_date", ordinal = 2)
        private Date startDate;

        @JSONField(name = "end_date", ordinal = 3)
        private Date endDate;

        public FastJsonSequence() {
        }

        public FastJsonSequence(List<FastJsonItem> items, Date startDate, Date endDate) {
            this.items = items;
            this.startDate = startDate;
            this.endDate = endDate;
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

        public Date getEndDate() {
            return endDate;
        }

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }

        @Override
        public String toString() {
            return "FastJsonSequence{" +
                    "items=" + items +
                    ", startDate=" + startDate +
                    ", endDate=" + endDate +
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

        private static final long serialVersionUID = -1871739579385428145L;

        public static FastJsonItem of(LookupResult.Item item) {
            if (Objects.isNull(item)) {
                return null;
            } else {
                return new FastJsonItem(
                        FastJsonLongIdKey.of(item.getPointKey()),
                        item.getValue(),
                        item.getHappenedDate()
                );
            }
        }

        @JSONField(name = "point_key", ordinal = 1)
        private FastJsonLongIdKey pointKey;

        @JSONField(name = "value", ordinal = 2)
        private Object value;

        @JSONField(name = "happened_date", ordinal = 3)
        private Date happenedDate;

        public FastJsonItem() {
        }

        public FastJsonItem(FastJsonLongIdKey pointKey, Object value, Date happenedDate) {
            this.pointKey = pointKey;
            this.value = value;
            this.happenedDate = happenedDate;
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

        @Override
        public String toString() {
            return "FastJsonItem{" +
                    "pointKey=" + pointKey +
                    ", value=" + value +
                    ", happenedDate=" + happenedDate +
                    '}';
        }
    }
}
