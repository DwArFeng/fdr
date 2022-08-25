package com.dwarfeng.fdr.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Arrays;
import java.util.Date;

/**
 * 映射查询信息。
 *
 * @author DwArFeng
 * @since 1.10.0
 */
public class MappingLookupInfo implements Dto {

    private static final long serialVersionUID = -8228178259884905875L;

    private String mapperType;
    private LongIdKey pointKey;
    private Date startDate;
    private Date endDate;
    private Object[] mapperArgs;

    public MappingLookupInfo() {
    }

    public MappingLookupInfo(
            String mapperType, LongIdKey pointKey, Date startDate, Date endDate, Object[] mapperArgs
    ) {
        this.mapperType = mapperType;
        this.pointKey = pointKey;
        this.startDate = startDate;
        this.endDate = endDate;
        this.mapperArgs = mapperArgs;
    }

    public String getMapperType() {
        return mapperType;
    }

    public void setMapperType(String mapperType) {
        this.mapperType = mapperType;
    }

    public LongIdKey getPointKey() {
        return pointKey;
    }

    public void setPointKey(LongIdKey pointKey) {
        this.pointKey = pointKey;
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

    public Object[] getMapperArgs() {
        return mapperArgs;
    }

    public void setMapperArgs(Object[] mapperArgs) {
        this.mapperArgs = mapperArgs;
    }

    @Override
    public String toString() {
        return "MappingLookupInfo{" +
                "mapperType='" + mapperType + '\'' +
                ", pointKey=" + pointKey +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", mapperArgs=" + Arrays.toString(mapperArgs) +
                '}';
    }
}
