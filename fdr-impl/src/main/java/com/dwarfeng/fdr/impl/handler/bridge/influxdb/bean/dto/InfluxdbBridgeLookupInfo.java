package com.dwarfeng.fdr.impl.handler.bridge.influxdb.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Arrays;
import java.util.Date;

/**
 * Influxdb 桥接器查询信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class InfluxdbBridgeLookupInfo implements Dto {

    private static final long serialVersionUID = 2909213045259407650L;

    private String measurement;
    private Date rangeStart;
    private Date rangeStop;
    private int limitNumber;
    private int limitOffset;
    private String[] params;

    public InfluxdbBridgeLookupInfo() {
    }

    public InfluxdbBridgeLookupInfo(
            String measurement, Date rangeStart, Date rangeStop, int limitNumber, int limitOffset,
            String[] params
    ) {
        this.measurement = measurement;
        this.rangeStart = rangeStart;
        this.rangeStop = rangeStop;
        this.limitNumber = limitNumber;
        this.limitOffset = limitOffset;
        this.params = params;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public Date getRangeStart() {
        return rangeStart;
    }

    public void setRangeStart(Date rangeStart) {
        this.rangeStart = rangeStart;
    }

    public Date getRangeStop() {
        return rangeStop;
    }

    public void setRangeStop(Date rangeStop) {
        this.rangeStop = rangeStop;
    }

    public int getLimitNumber() {
        return limitNumber;
    }

    public void setLimitNumber(int limitNumber) {
        this.limitNumber = limitNumber;
    }

    public int getLimitOffset() {
        return limitOffset;
    }

    public void setLimitOffset(int limitOffset) {
        this.limitOffset = limitOffset;
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "InfluxdbBridgeLookupInfo{" +
                "measurement='" + measurement + '\'' +
                ", rangeStart=" + rangeStart +
                ", rangeStop=" + rangeStop +
                ", limitNumber=" + limitNumber +
                ", limitOffset=" + limitOffset +
                ", params=" + Arrays.toString(params) +
                '}';
    }
}
