package com.dwarfeng.fdr.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.dto.LookupResult;
import com.dwarfeng.fdr.stack.bean.dto.LookupResult.Sequence;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * JSFixed FastJson 查看结果。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class JSFixedFastJsonLookupResult implements Dto {

    private static final long serialVersionUID = -1193794197813482055L;

    public static JSFixedFastJsonLookupResult of(LookupResult lookupResult) {
        if (Objects.isNull(lookupResult)) {
            return null;
        } else {
            return new JSFixedFastJsonLookupResult(
                    lookupResult.getSequences().stream().map(JSFixedFastJsonSequence::of).collect(Collectors.toList())
            );
        }
    }

    @JSONField(name = "sequences", ordinal = 1)
    private List<JSFixedFastJsonSequence> sequences;

    public JSFixedFastJsonLookupResult() {
    }

    public JSFixedFastJsonLookupResult(List<JSFixedFastJsonSequence> sequences) {
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
        return "JSFixedFastJsonLookupResult{" +
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

        private static final long serialVersionUID = -549454070494303720L;

        public static JSFixedFastJsonSequence of(Sequence sequence) {
            if (Objects.isNull(sequence)) {
                return null;
            } else {
                return new JSFixedFastJsonSequence(
                        sequence.getItems().stream().map(JSFixedFastJsonItem::of).collect(Collectors.toList()),
                        sequence.getStartDate(),
                        sequence.getEndDate()
                );
            }
        }

        @JSONField(name = "items", ordinal = 1)
        private List<JSFixedFastJsonItem> items;

        @JSONField(name = "start_date", ordinal = 2)
        private Date startDate;

        @JSONField(name = "end_date", ordinal = 3)
        private Date endDate;

        public JSFixedFastJsonSequence() {
        }

        public JSFixedFastJsonSequence(List<JSFixedFastJsonItem> items, Date startDate, Date endDate) {
            this.items = items;
            this.startDate = startDate;
            this.endDate = endDate;
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

        public Date getEndDate() {
            return endDate;
        }

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }

        @Override
        public String toString() {
            return "JSFixedFastJsonSequence{" +
                    "items=" + items +
                    ", startDate=" + startDate +
                    ", endDate=" + endDate +
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

        private static final long serialVersionUID = -5315555985713154945L;

        public static JSFixedFastJsonItem of(LookupResult.Item item) {
            if (Objects.isNull(item)) {
                return null;
            } else {
                return new JSFixedFastJsonItem(
                        JSFixedFastJsonLongIdKey.of(item.getPointKey()),
                        item.getValue(),
                        item.getHappenedDate()
                );
            }
        }

        @JSONField(name = "point_key", ordinal = 1)
        private JSFixedFastJsonLongIdKey pointKey;

        @JSONField(name = "value", ordinal = 2)
        private Object value;

        @JSONField(name = "happened_date", ordinal = 3)
        private Date happenedDate;

        public JSFixedFastJsonItem() {
        }

        public JSFixedFastJsonItem(JSFixedFastJsonLongIdKey pointKey, Object value, Date happenedDate) {
            this.pointKey = pointKey;
            this.value = value;
            this.happenedDate = happenedDate;
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

        @Override
        public String toString() {
            return "JSFixedFastJsonItem{" +
                    "pointKey=" + pointKey +
                    ", value=" + value +
                    ", happenedDate=" + happenedDate +
                    '}';
        }
    }
}
