package com.dwarfeng.fdr.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.dto.LookupInfo;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 * FastJson 查看信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FastJsonLookupInfo implements Dto {

    private static final long serialVersionUID = -5750731973917933484L;

    public static FastJsonLookupInfo of(LookupInfo lookupInfo) {
        if (Objects.isNull(lookupInfo)) {
            return null;
        } else {
            return new FastJsonLookupInfo(
                    lookupInfo.getPreset(),
                    lookupInfo.getParams(),
                    FastJsonLongIdKey.of(lookupInfo.getPointKey()),
                    lookupInfo.getStartDate(),
                    lookupInfo.getEndDate(),
                    lookupInfo.isIncludeStartDate(),
                    lookupInfo.isIncludeEndDate(),
                    lookupInfo.getPage(),
                    lookupInfo.getRows()
            );
        }
    }

    public static LookupInfo toStackBean(FastJsonLookupInfo fastJsonLookupInfo) {
        if (Objects.isNull(fastJsonLookupInfo)) {
            return null;
        } else {
            return new LookupInfo(
                    fastJsonLookupInfo.getPreset(),
                    fastJsonLookupInfo.getParams(),
                    FastJsonLongIdKey.toStackBean(fastJsonLookupInfo.getPointKey()),
                    fastJsonLookupInfo.getStartDate(),
                    fastJsonLookupInfo.getEndDate(),
                    fastJsonLookupInfo.isIncludeStartDate(),
                    fastJsonLookupInfo.isIncludeEndDate(),
                    fastJsonLookupInfo.getPage(),
                    fastJsonLookupInfo.getRows()
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

    @JSONField(name = "page", ordinal = 8)
    private Integer page;

    @JSONField(name = "rows", ordinal = 9)
    private Integer rows;

    public FastJsonLookupInfo() {
    }

    public FastJsonLookupInfo(
            String preset, String[] params, FastJsonLongIdKey pointKey, Date startDate, Date endDate,
            boolean includeStartDate, boolean includeEndDate, Integer page, Integer rows
    ) {
        this.preset = preset;
        this.params = params;
        this.pointKey = pointKey;
        this.startDate = startDate;
        this.endDate = endDate;
        this.includeStartDate = includeStartDate;
        this.includeEndDate = includeEndDate;
        this.page = page;
        this.rows = rows;
    }

    @Nonnull
    public String getPreset() {
        return preset;
    }

    public void setPreset(String preset) {
        this.preset = preset;
    }

    @Nonnull
    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }

    @Nonnull
    public FastJsonLongIdKey getPointKey() {
        return pointKey;
    }

    public void setPointKey(FastJsonLongIdKey pointKey) {
        this.pointKey = pointKey;
    }

    @Nullable
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Nullable
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

    @Nullable
    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    @Nullable
    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "FastJsonLookupInfo{" +
                "preset='" + preset + '\'' +
                ", params=" + Arrays.toString(params) +
                ", pointKey=" + pointKey +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", includeStartDate=" + includeStartDate +
                ", includeEndDate=" + includeEndDate +
                ", page=" + page +
                ", rows=" + rows +
                '}';
    }
}
