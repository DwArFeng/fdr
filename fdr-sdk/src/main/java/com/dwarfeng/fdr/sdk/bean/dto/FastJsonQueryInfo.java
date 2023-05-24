package com.dwarfeng.fdr.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.dto.QueryInfo;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 * FastJson 原生查询信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FastJsonQueryInfo implements Dto {

    private static final long serialVersionUID = -4040096429908542693L;

    public static FastJsonQueryInfo of(QueryInfo queryInfo) {
        if (Objects.isNull(queryInfo)) {
            return null;
        } else {
            return new FastJsonQueryInfo(
                    queryInfo.getPreset(),
                    queryInfo.getParams(),
                    FastJsonLongIdKey.of(queryInfo.getPointKey()),
                    queryInfo.getStartDate(),
                    queryInfo.getEndDate(),
                    queryInfo.isIncludeStartDate(),
                    queryInfo.isIncludeEndDate(),
                    queryInfo.getLimit(),
                    queryInfo.getOffset()
            );
        }
    }

    public static QueryInfo toStackBean(FastJsonQueryInfo fastJsonQueryInfo) {
        if (Objects.isNull(fastJsonQueryInfo)) {
            return null;
        } else {
            return new QueryInfo(
                    fastJsonQueryInfo.getPreset(),
                    fastJsonQueryInfo.getParams(),
                    FastJsonLongIdKey.toStackBean(fastJsonQueryInfo.getPointKey()),
                    fastJsonQueryInfo.getStartDate(),
                    fastJsonQueryInfo.getEndDate(),
                    fastJsonQueryInfo.isIncludeStartDate(),
                    fastJsonQueryInfo.isIncludeEndDate(),
                    fastJsonQueryInfo.getLimit(),
                    fastJsonQueryInfo.getOffset()
            );
        }
    }

    @JSONField(name = "preset", ordinal = 1)
    private String preset;

    @JSONField(name = "params", ordinal = 2)
    private String[] params;

    @JSONField(name = "point_key", ordinal = 3)
    private FastJsonLongIdKey pointKey;

    @JSONField(name = "start_date", ordinal = 4)
    private Date startDate;

    @JSONField(name = "end_date", ordinal = 5)
    private Date endDate;

    @JSONField(name = "include_start_date", ordinal = 6)
    private boolean includeStartDate;

    @JSONField(name = "include_end_date", ordinal = 7)
    private boolean includeEndDate;

    @JSONField(name = "limit", ordinal = 8)
    private Integer limit;

    @JSONField(name = "offset", ordinal = 9)
    private int offset;

    public FastJsonQueryInfo() {
    }

    public FastJsonQueryInfo(
            String preset, String[] params, FastJsonLongIdKey pointKey, Date startDate, Date endDate,
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

    public FastJsonLongIdKey getPointKey() {
        return pointKey;
    }

    public void setPointKey(FastJsonLongIdKey pointKey) {
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
        return "FastJsonQueryInfo{" +
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
