package com.dwarfeng.fdr.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.sdk.cna.ValidateList;
import com.dwarfeng.fdr.stack.bean.dto.QueryInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import javax.annotation.Nonnull;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * WebInput 查询信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class WebInputQueryInfo implements Dto {

    private static final long serialVersionUID = 6271896807009156128L;

    @SuppressWarnings("DuplicatedCode")
    public static QueryInfo toStackBean(WebInputQueryInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new QueryInfo(
                    webInput.getPreset(),
                    webInput.getParams(),
                    webInput.getPointKeys().stream().map(WebInputLongIdKey::toStackBean).collect(Collectors.toList()),
                    webInput.getStartDate(),
                    webInput.getEndDate(),
                    webInput.isIncludeStartDate(),
                    webInput.isIncludeEndDate(),
                    webInput.getMapInfos().stream().map(WebInputMapInfo::toStackBean).collect(Collectors.toList())
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

    @JSONField(name = "point_keys")
    @NotNull
    @Valid
    private ValidateList<WebInputLongIdKey> pointKeys;

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

    @JSONField(name = "map_infos")
    @NotNull
    @Valid
    private ValidateList<WebInputMapInfo> mapInfos;

    public WebInputQueryInfo() {
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

    public ValidateList<WebInputLongIdKey> getPointKeys() {
        return pointKeys;
    }

    public void setPointKeys(ValidateList<WebInputLongIdKey> pointKeys) {
        this.pointKeys = pointKeys;
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

    public ValidateList<WebInputMapInfo> getMapInfos() {
        return mapInfos;
    }

    public void setMapInfos(ValidateList<WebInputMapInfo> mapInfos) {
        this.mapInfos = mapInfos;
    }

    @Override
    public String toString() {
        return "WebInputQueryInfo{" +
                "preset='" + preset + '\'' +
                ", params=" + Arrays.toString(params) +
                ", pointKeys=" + pointKeys +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", includeStartDate=" + includeStartDate +
                ", includeEndDate=" + includeEndDate +
                ", mapInfos=" + mapInfos +
                '}';
    }

    /**
     * WebInput 查询映射信息。
     *
     * @author DwArFeng
     * @since 2.0.0
     */
    public static class WebInputMapInfo implements Dto {

        private static final long serialVersionUID = 6871773652673371294L;

        public static QueryInfo.MapInfo toStackBean(WebInputMapInfo webInputMapInfo) {
            if (Objects.isNull(webInputMapInfo)) {
                return null;
            } else {
                return new QueryInfo.MapInfo(
                        webInputMapInfo.getType(),
                        webInputMapInfo.getParam()
                );
            }
        }

        @JSONField(name = "type")
        @NotNull
        @NotEmpty
        private String type;

        @JSONField(name = "param")
        private String param;

        public WebInputMapInfo() {
        }

        @Nonnull
        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Nonnull
        public String getParam() {
            return param;
        }

        public void setParam(String param) {
            this.param = param;
        }

        @Override
        public String toString() {
            return "WebInputMapInfo{" +
                    "type='" + type + '\'' +
                    ", param='" + param + '\'' +
                    '}';
        }
    }
}
