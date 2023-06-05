package com.dwarfeng.fdr.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.sdk.cna.ValidateList;
import com.dwarfeng.fdr.stack.bean.dto.QueryInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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

    private static final long serialVersionUID = 6709223628381791018L;

    public static QueryInfo toStackBean(WebInputQueryInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new QueryInfo(
                    webInput.getLookupInfos().stream().map(WebInputQueryLookupInfo::toStackBean)
                            .collect(Collectors.toList()),
                    webInput.getMapInfos().stream().map(WebInputQueryMapInfo::toStackBean)
                            .collect(Collectors.toList())
            );
        }
    }

    @JSONField(name = "lookup_infos")
    @NotNull
    @Valid
    private ValidateList<WebInputQueryLookupInfo> lookupInfos;

    @JSONField(name = "map_infos")
    @NotNull
    @Valid
    private ValidateList<WebInputQueryMapInfo> mapInfos;

    public WebInputQueryInfo() {
    }

    @Nonnull
    public ValidateList<WebInputQueryLookupInfo> getLookupInfos() {
        return lookupInfos;
    }

    public void setLookupInfos(ValidateList<WebInputQueryLookupInfo> lookupInfos) {
        this.lookupInfos = lookupInfos;
    }

    @Nonnull
    public ValidateList<WebInputQueryMapInfo> getMapInfos() {
        return mapInfos;
    }

    public void setMapInfos(ValidateList<WebInputQueryMapInfo> mapInfos) {
        this.mapInfos = mapInfos;
    }

    @Override
    public String toString() {
        return "WebInputQueryInfo{" +
                "lookupInfos=" + lookupInfos +
                ", mapInfos=" + mapInfos +
                '}';
    }

    /**
     * WebInput 查询查看信息。
     *
     * @author DwArFeng
     * @since 2.0.0
     */
    public static class WebInputQueryLookupInfo implements Dto {

        private static final long serialVersionUID = 7013251424453558444L;

        public static QueryInfo.QueryLookupInfo toStackBean(WebInputQueryLookupInfo webInputQueryLookupInfo) {
            if (Objects.isNull(webInputQueryLookupInfo)) {
                return null;
            } else {
                return new QueryInfo.QueryLookupInfo(
                        webInputQueryLookupInfo.getPreset(),
                        webInputQueryLookupInfo.getParams(),
                        WebInputLongIdKey.toStackBean(webInputQueryLookupInfo.getPointKey()),
                        webInputQueryLookupInfo.getStartDate(),
                        webInputQueryLookupInfo.getEndDate(),
                        webInputQueryLookupInfo.isIncludeStartDate(),
                        webInputQueryLookupInfo.isIncludeEndDate()
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

        public WebInputQueryLookupInfo() {
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

        @Override
        public String toString() {
            return "WebInputQueryLookupInfo{" +
                    "preset='" + preset + '\'' +
                    ", params=" + Arrays.toString(params) +
                    ", pointKey=" + pointKey +
                    ", startDate=" + startDate +
                    ", endDate=" + endDate +
                    ", includeStartDate=" + includeStartDate +
                    ", includeEndDate=" + includeEndDate +
                    '}';
        }
    }

    /**
     * WebInput 查询映射信息。
     *
     * @author DwArFeng
     * @since 2.0.0
     */
    public static class WebInputQueryMapInfo implements Dto {

        private static final long serialVersionUID = 7807478667872775082L;

        public static QueryInfo.QueryMapInfo toStackBean(WebInputQueryMapInfo webInputQueryMapInfo) {
            if (Objects.isNull(webInputQueryMapInfo)) {
                return null;
            } else {
                return new QueryInfo.QueryMapInfo(
                        webInputQueryMapInfo.getType(),
                        webInputQueryMapInfo.getParam()
                );
            }
        }

        @JSONField(name = "type")
        @NotNull
        @NotEmpty
        private String type;

        @JSONField(name = "param")
        private String param;

        public WebInputQueryMapInfo() {
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
            return "WebInputQueryMapInfo{" +
                    "type='" + type + '\'' +
                    ", param='" + param + '\'' +
                    '}';
        }
    }
}
