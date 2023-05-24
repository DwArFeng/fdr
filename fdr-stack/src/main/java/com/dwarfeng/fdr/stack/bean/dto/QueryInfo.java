package com.dwarfeng.fdr.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Arrays;
import java.util.Date;

/**
 * 查询信息。
 *
 * <p>
 * 该实体表示原生查询信息，包含了查询的预设、参数、开始时间、结束时间、是否包含开始时间、是否包含结束时间、发生时间的排序、
 * 限制、偏移。
 *
 * <p>
 * 有关查询的详细信息，请参阅术语。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class QueryInfo implements Dto {

    private static final long serialVersionUID = -7240908645935078002L;

    private String preset;
    private String[] params;
    private LongIdKey pointKey;
    private Date startDate;
    private Date endDate;
    private boolean includeStartDate;
    private boolean includeEndDate;
    private Integer limit;
    private int offset;

    public QueryInfo() {
    }

    public QueryInfo(
            String preset, String[] params, LongIdKey pointKey, Date startDate, Date endDate,
            boolean includeStartDate, boolean includeEndDate, Integer limit, int offset
    ) {
        this.preset = preset;
        this.params = params;
        this.pointKey = pointKey;
        this.startDate = startDate;
        this.endDate = endDate;
        this.includeStartDate = includeStartDate;
        this.includeEndDate = includeEndDate;
        this.limit = limit;
        this.offset = offset;
    }

    public String getPreset() {
        return preset;
    }

    public void setPreset(String preset) {
        this.preset = preset;
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
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

    public boolean isIncludeStartDate() {
        return includeStartDate;
    }

    public void setIncludeStartDate(boolean includeStartDate) {
        this.includeStartDate = includeStartDate;
    }

    public boolean isIncludeEndDate() {
        return includeEndDate;
    }

    public void setIncludeEndDate(boolean includeEndDate) {
        this.includeEndDate = includeEndDate;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public String toString() {
        return "QueryInfo{" +
                "preset='" + preset + '\'' +
                ", params=" + Arrays.toString(params) +
                ", pointKey=" + pointKey +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", includeStartDate=" + includeStartDate +
                ", includeEndDate=" + includeEndDate +
                ", limit=" + limit +
                ", offset=" + offset +
                '}';
    }
}
