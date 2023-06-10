package com.dwarfeng.fdr.impl.handler.bridge.influxdb.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.List;

/**
 * Hibernate 桥接器自定义查询信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class HibernateBridgeCustomQueryInfo implements Dto {

    private static final long serialVersionUID = 1556293926090308497L;

    private List<String> measurements;
    private Date rangeStart;
    private Date rangeStop;
    private String fluxFragment;

    public HibernateBridgeCustomQueryInfo() {
    }

    public HibernateBridgeCustomQueryInfo(
            List<String> measurements, Date rangeStart, Date rangeStop, String fluxFragment
    ) {
        this.measurements = measurements;
        this.rangeStart = rangeStart;
        this.rangeStop = rangeStop;
        this.fluxFragment = fluxFragment;
    }

    public List<String> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<String> measurements) {
        this.measurements = measurements;
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

    public String getFluxFragment() {
        return fluxFragment;
    }

    public void setFluxFragment(String fluxFragment) {
        this.fluxFragment = fluxFragment;
    }

    @Override
    public String toString() {
        return "HibernateBridgeCustomQueryInfo{" +
                "measurements=" + measurements +
                ", rangeStart=" + rangeStart +
                ", rangeStop=" + rangeStop +
                ", fluxFragment='" + fluxFragment + '\'' +
                '}';
    }
}
