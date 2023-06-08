package com.dwarfeng.fdr.impl.handler.bridge.influxdb.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.time.Instant;
import java.util.List;

/**
 * Hibernate 桥接器查询信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class HibernateBridgeQueryResult implements Dto {

    private static final long serialVersionUID = 3131397231002182171L;

    private List<HibernateBridgeSequence> sequences;

    public HibernateBridgeQueryResult() {
    }

    public HibernateBridgeQueryResult(List<HibernateBridgeSequence> sequences) {
        this.sequences = sequences;
    }

    public List<HibernateBridgeSequence> getSequences() {
        return sequences;
    }

    public void setSequences(List<HibernateBridgeSequence> sequences) {
        this.sequences = sequences;
    }

    @Override
    public String toString() {
        return "HibernateBridgeQueryResult{" +
                "sequences=" + sequences +
                '}';
    }

    public static class HibernateBridgeSequence implements Dto {

        private static final long serialVersionUID = 6825156575804538927L;

        private String measurement;
        private List<HibernateBridgeItem> items;
        private Instant startInstant;
        private Instant endInstant;

        public HibernateBridgeSequence() {
        }

        public HibernateBridgeSequence(
                String measurement, List<HibernateBridgeItem> items, Instant startInstant, Instant endInstant
        ) {
            this.measurement = measurement;
            this.items = items;
            this.startInstant = startInstant;
            this.endInstant = endInstant;
        }

        public String getMeasurement() {
            return measurement;
        }

        public void setMeasurement(String measurement) {
            this.measurement = measurement;
        }

        public List<HibernateBridgeItem> getItems() {
            return items;
        }

        public void setItems(List<HibernateBridgeItem> items) {
            this.items = items;
        }

        public Instant getStartInstant() {
            return startInstant;
        }

        public void setStartInstant(Instant startInstant) {
            this.startInstant = startInstant;
        }

        public Instant getEndInstant() {
            return endInstant;
        }

        public void setEndInstant(Instant endInstant) {
            this.endInstant = endInstant;
        }

        @Override
        public String toString() {
            return "HibernateBridgeSequence{" +
                    "measurement='" + measurement + '\'' +
                    ", items=" + items +
                    ", startInstant=" + startInstant +
                    ", endInstant=" + endInstant +
                    '}';
        }
    }

    public static class HibernateBridgeItem implements Dto {

        private static final long serialVersionUID = -4693545503211723887L;

        private String measurement;
        private Object value;
        private Instant happenedInstant;

        public HibernateBridgeItem() {
        }

        public HibernateBridgeItem(String measurement, Object value, Instant happenedInstant) {
            this.measurement = measurement;
            this.value = value;
            this.happenedInstant = happenedInstant;
        }

        public String getMeasurement() {
            return measurement;
        }

        public void setMeasurement(String measurement) {
            this.measurement = measurement;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public Instant getHappenedInstant() {
            return happenedInstant;
        }

        public void setHappenedInstant(Instant happenedInstant) {
            this.happenedInstant = happenedInstant;
        }

        @Override
        public String toString() {
            return "HibernateBridgeItem{" +
                    "measurement='" + measurement + '\'' +
                    ", value=" + value +
                    ", happenedInstant=" + happenedInstant +
                    '}';
        }
    }
}
