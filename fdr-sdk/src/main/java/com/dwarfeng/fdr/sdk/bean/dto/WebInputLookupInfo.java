package com.dwarfeng.fdr.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.dto.LookupInfo;
import com.dwarfeng.fdr.stack.bean.dto.LookupInfo.QueryInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * WebInput 原生查看信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class WebInputLookupInfo implements Dto {

    private static final long serialVersionUID = -5540686194694036119L;

    public static LookupInfo toStackBean(WebInputLookupInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new LookupInfo(
                    webInput.getQueryInfos().stream().map(WebInputQueryInfo::toStackBean).collect(Collectors.toList()),
                    webInput.getMapInfos().stream().map(WebInputMapInfo::toStackBean).collect(Collectors.toList())
            );
        }
    }

    @JSONField(name = "query_infos")
    private List<WebInputQueryInfo> queryInfos;

    @JSONField(name = "map_infos")
    private List<WebInputMapInfo> mapInfos;

    public WebInputLookupInfo() {
    }

    public List<WebInputQueryInfo> getQueryInfos() {
        return queryInfos;
    }

    public void setQueryInfos(List<WebInputQueryInfo> queryInfos) {
        this.queryInfos = queryInfos;
    }

    public List<WebInputMapInfo> getMapInfos() {
        return mapInfos;
    }

    public void setMapInfos(List<WebInputMapInfo> mapInfos) {
        this.mapInfos = mapInfos;
    }

    @Override
    public String toString() {
        return "WebInputLookupInfo{" +
                "queryInfos=" + queryInfos +
                ", mapInfos=" + mapInfos +
                '}';
    }

    /**
     * WebInput 查询信息。
     *
     * @author DwArFeng
     * @since 2.0.0
     */
    public static class WebInputQueryInfo implements Dto {

        private static final long serialVersionUID = 4453262371931623817L;

        public static QueryInfo toStackBean(WebInputQueryInfo webInputQueryInfo) {
            if (Objects.isNull(webInputQueryInfo)) {
                return null;
            } else {
                return new QueryInfo(
                        webInputQueryInfo.getPreset(),
                        webInputQueryInfo.getParams(),
                        WebInputLongIdKey.toStackBean(webInputQueryInfo.getPointKey()),
                        webInputQueryInfo.getStartDate(),
                        webInputQueryInfo.getEndDate(),
                        webInputQueryInfo.isIncludeStartDate(),
                        webInputQueryInfo.isIncludeEndDate()
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

        public WebInputLongIdKey getPointKey() {
            return pointKey;
        }

        public void setPointKey(WebInputLongIdKey pointKey) {
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

        @Override
        public String toString() {
            return "WebInputQueryInfo{" +
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
     * WebInput 映射信息。
     *
     * @author DwArFeng
     * @since 2.0.0
     */
    public static class WebInputMapInfo implements Dto {

        private static final long serialVersionUID = -7015286670133471491L;

        public static LookupInfo.MapInfo toStackBean(WebInputMapInfo webInputMapInfo) {
            if (Objects.isNull(webInputMapInfo)) {
                return null;
            } else {
                return new LookupInfo.MapInfo(
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

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
