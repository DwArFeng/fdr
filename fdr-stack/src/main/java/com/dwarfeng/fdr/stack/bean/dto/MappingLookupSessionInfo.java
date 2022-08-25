package com.dwarfeng.fdr.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Arrays;
import java.util.Date;

/**
 * 映射查询会话信息。
 *
 * @author DwArFeng
 * @since 1.10.0
 */
public class MappingLookupSessionInfo implements Dto {

    private static final long serialVersionUID = -8531280805925660709L;

    private LongIdKey key;
    private String mapperType;
    private LongIdKey pointKey;
    private Date startDate;
    private Date endDate;
    private Object[] mapperArgs;
    private Date createdDate;
    private boolean canceledFlag;
    private Date canceledDate;
    private boolean finishedFlag;
    private Date finishedDate;

    public MappingLookupSessionInfo() {
    }

    public MappingLookupSessionInfo(
            LongIdKey key, String mapperType, LongIdKey pointKey, Date startDate, Date endDate, Object[] mapperArgs,
            Date createdDate, boolean canceledFlag, Date canceledDate, boolean finishedFlag, Date finishedDate
    ) {
        this.key = key;
        this.mapperType = mapperType;
        this.pointKey = pointKey;
        this.startDate = startDate;
        this.endDate = endDate;
        this.mapperArgs = mapperArgs;
        this.createdDate = createdDate;
        this.canceledFlag = canceledFlag;
        this.canceledDate = canceledDate;
        this.finishedFlag = finishedFlag;
        this.finishedDate = finishedDate;
    }

    public LongIdKey getKey() {
        return key;
    }

    public void setKey(LongIdKey key) {
        this.key = key;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isCanceledFlag() {
        return canceledFlag;
    }

    public void setCanceledFlag(boolean canceledFlag) {
        this.canceledFlag = canceledFlag;
    }

    public Date getCanceledDate() {
        return canceledDate;
    }

    public void setCanceledDate(Date canceledDate) {
        this.canceledDate = canceledDate;
    }

    public boolean isFinishedFlag() {
        return finishedFlag;
    }

    public void setFinishedFlag(boolean finishedFlag) {
        this.finishedFlag = finishedFlag;
    }

    public Date getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(Date finishedDate) {
        this.finishedDate = finishedDate;
    }

    @Override
    public String toString() {
        return "MappingLookupSessionInfo{" +
                "key=" + key +
                ", mapperType='" + mapperType + '\'' +
                ", pointKey=" + pointKey +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", mapperArgs=" + Arrays.toString(mapperArgs) +
                ", createdDate=" + createdDate +
                ", canceledFlag=" + canceledFlag +
                ", canceledDate=" + canceledDate +
                ", finishedFlag=" + finishedFlag +
                ", finishedDate=" + finishedDate +
                '}';
    }
}
