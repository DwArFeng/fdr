package com.dwarfeng.fdr.impl.handler.bridge.influxdb.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.time.Instant;
import java.util.List;

/**
 * Influxdb 桥接器查询信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class InfluxdbBridgeQueryResult implements Dto {

    private static final long serialVersionUID = 2336292356366533387L;

    private List<InfluxdbBridgeSequence> sequences;

    public InfluxdbBridgeQueryResult() {
    }

    public InfluxdbBridgeQueryResult(List<InfluxdbBridgeSequence> sequences) {
        this.sequences = sequences;
    }

    public List<InfluxdbBridgeSequence> getSequences() {
        return sequences;
    }

    public void setSequences(List<InfluxdbBridgeSequence> sequences) {
        this.sequences = sequences;
    }

    @Override
    public String toString() {
        return "InfluxdbBridgeQueryResult{" +
                "sequences=" + sequences +
                '}';
    }

    public static class InfluxdbBridgeSequence implements Dto {

        private static final long serialVersionUID = 6825156575804538927L;

        private String measurement;
        private List<InfluxdbBridgeItem> items;
        private Instant startInstant;
        private Instant endInstant;

        public InfluxdbBridgeSequence() {
        }

        public InfluxdbBridgeSequence(
                String measurement, List<InfluxdbBridgeItem> items, Instant startInstant, Instant endInstant
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

        public List<InfluxdbBridgeItem> getItems() {
            return items;
        }

        public void setItems(List<InfluxdbBridgeItem> items) {
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
            return "InfluxdbBridgeSequence{" +
                    "measurement='" + measurement + '\'' +
                    ", items=" + items +
                    ", startInstant=" + startInstant +
                    ", endInstant=" + endInstant +
                    '}';
        }
    }

    public static class InfluxdbBridgeItem implements Dto {

        private static final long serialVersionUID = -4693545503211723887L;

        private String measurement;
        private Object value;
        private Instant happenedInstant;

        public InfluxdbBridgeItem() {
        }

        public InfluxdbBridgeItem(String measurement, Object value, Instant happenedInstant) {
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
            return "InfluxdbBridgeItem{" +
                    "measurement='" + measurement + '\'' +
                    ", value=" + value +
                    ", happenedInstant=" + happenedInstant +
                    '}';
        }
    }
}
