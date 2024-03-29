package com.dwarfeng.fdr.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.dto.LookupInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import javax.annotation.Nullable;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 * WebInput 查看信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class WebInputLookupInfo implements Dto {

    private static final long serialVersionUID = -3550314003759789227L;

    public static LookupInfo toStackBean(WebInputLookupInfo webInputLookupInfo) {
        if (Objects.isNull(webInputLookupInfo)) {
            return null;
        } else {
            return new LookupInfo(
                    webInputLookupInfo.getPreset(),
                    webInputLookupInfo.getParams(),
                    WebInputLongIdKey.toStackBean(webInputLookupInfo.getPointKey()),
                    webInputLookupInfo.getStartDate(),
                    webInputLookupInfo.getEndDate(),
                    webInputLookupInfo.isIncludeStartDate(),
                    webInputLookupInfo.isIncludeEndDate(),
                    webInputLookupInfo.getPage(),
                    webInputLookupInfo.getRows()
            );
        }
    }

    @JSONField(name = "preset")
    @NotNull
    @NotEmpty
    private String preset;

    @JSONField(name = "params")
    @NotNull
    private String[] params;

    @JSONField(name = "point_key")
    @NotNull
    @Valid
    private WebInputLongIdKey pointKey;

    @JSONField(name = "start_date")
    @NotNull
    private Date startDate;

    @JSONField(name = "end_date")
    @NotNull
    private Date endDate;

    @JSONField(name = "include_start_date")
    private boolean includeStartDate;

    @JSONField(name = "include_end_date")
    private boolean includeEndDate;

    @JSONField(name = "page")
    @PositiveOrZero
    private Integer page;

    @JSONField(name = "rows")
    @PositiveOrZero
    private Integer rows;

    public WebInputLookupInfo() {
    }

    @NotNull
    public String getPreset() {
        return preset;
    }

    public void setPreset(String preset) {
        this.preset = preset;
    }

    @NotNull
    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }

    @NotNull
    public WebInputLongIdKey getPointKey() {
        return pointKey;
    }

    public void setPointKey(WebInputLongIdKey pointKey) {
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
        return "WebInputLookupInfo{" +
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
