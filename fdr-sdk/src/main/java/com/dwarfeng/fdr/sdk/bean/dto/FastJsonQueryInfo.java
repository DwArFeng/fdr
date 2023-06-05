package com.dwarfeng.fdr.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.dto.QueryInfo;
import com.dwarfeng.fdr.stack.bean.dto.QueryInfo.QueryMapInfo;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * FastJson 查询信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FastJsonQueryInfo implements Dto {

    private static final long serialVersionUID = 4908673177081928706L;

    public static FastJsonQueryInfo of(QueryInfo queryInfo) {
        if (Objects.isNull(queryInfo)) {
            return null;
        } else {
            return new FastJsonQueryInfo(
                    queryInfo.getQueryInfos().stream().map(FastJsonQueryLookupInfo::of).collect(Collectors.toList()),
                    queryInfo.getMapInfos().stream().map(FastJsonQueryMapInfo::of).collect(Collectors.toList())
            );
        }
    }

    public static QueryInfo toStackBean(FastJsonQueryInfo fastJson) {
        if (Objects.isNull(fastJson)) {
            return null;
        } else {
            return new QueryInfo(
                    fastJson.getLookupInfos().stream().map(FastJsonQueryLookupInfo::toStackBean)
                            .collect(Collectors.toList()),
                    fastJson.getMapInfos().stream().map(FastJsonQueryMapInfo::toStackBean).collect(Collectors.toList())
            );
        }
    }

    @JSONField(name = "lookup_infos", ordinal = 1)
    private List<FastJsonQueryLookupInfo> lookupInfos;

    @JSONField(name = "map_infos", ordinal = 2)
    private List<FastJsonQueryMapInfo> mapInfos;

    public FastJsonQueryInfo() {
    }

    public FastJsonQueryInfo(List<FastJsonQueryLookupInfo> lookupInfos, List<FastJsonQueryMapInfo> mapInfos) {
        this.lookupInfos = lookupInfos;
        this.mapInfos = mapInfos;
    }

    @Nonnull
    public List<FastJsonQueryLookupInfo> getLookupInfos() {
        return lookupInfos;
    }

    public void setLookupInfos(List<FastJsonQueryLookupInfo> lookupInfos) {
        this.lookupInfos = lookupInfos;
    }

    @Nonnull
    public List<FastJsonQueryMapInfo> getMapInfos() {
        return mapInfos;
    }

    public void setMapInfos(List<FastJsonQueryMapInfo> mapInfos) {
        this.mapInfos = mapInfos;
    }

    @Override
    public String toString() {
        return "FastJsonQueryInfo{" +
                "lookupInfos=" + lookupInfos +
                ", mapInfos=" + mapInfos +
                '}';
    }

    /**
     * FastJson 查询查看信息。
     *
     * @author DwArFeng
     * @since 2.0.0
     */
    public static class FastJsonQueryLookupInfo implements Dto {

        private static final long serialVersionUID = 3358997246242889769L;

        public static FastJsonQueryLookupInfo of(QueryInfo.QueryLookupInfo queryLookupInfo) {
            if (Objects.isNull(queryLookupInfo)) {
                return null;
            } else {
                return new FastJsonQueryLookupInfo(
                        queryLookupInfo.getPreset(),
                        queryLookupInfo.getParams(),
                        FastJsonLongIdKey.of(queryLookupInfo.getPointKey()),
                        queryLookupInfo.getStartDate(),
                        queryLookupInfo.getEndDate(),
                        queryLookupInfo.isIncludeStartDate(),
                        queryLookupInfo.isIncludeEndDate()
                );
            }
        }

        public static QueryInfo.QueryLookupInfo toStackBean(FastJsonQueryLookupInfo fastJsonQueryLookupInfo) {
            if (Objects.isNull(fastJsonQueryLookupInfo)) {
                return null;
            } else {
                return new QueryInfo.QueryLookupInfo(
                        fastJsonQueryLookupInfo.getPreset(),
                        fastJsonQueryLookupInfo.getParams(),
                        FastJsonLongIdKey.toStackBean(fastJsonQueryLookupInfo.getPointKey()),
                        fastJsonQueryLookupInfo.getStartDate(),
                        fastJsonQueryLookupInfo.getEndDate(),
                        fastJsonQueryLookupInfo.isIncludeStartDate(),
                        fastJsonQueryLookupInfo.isIncludeEndDate()
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

        public FastJsonQueryLookupInfo() {
        }

        public FastJsonQueryLookupInfo(
                String preset, String[] params, FastJsonLongIdKey pointKey, Date startDate, Date endDate,
                boolean includeStartDate, boolean includeEndDate
        ) {
            this.preset = preset;
            this.params = params;
            this.pointKey = pointKey;
            this.startDate = startDate;
            this.endDate = endDate;
            this.includeStartDate = includeStartDate;
            this.includeEndDate = includeEndDate;
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

        @Override
        public String toString() {
            return "FastJsonQueryLookupInfo{" +
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
     * FastJson 映射信息。
     *
     * @author DwArFeng
     * @since 2.0.0
     */
    public static class FastJsonQueryMapInfo implements Dto {

        private static final long serialVersionUID = 3306095557282041507L;

        public static FastJsonQueryMapInfo of(QueryMapInfo queryMapInfo) {
            if (Objects.isNull(queryMapInfo)) {
                return null;
            } else {
                return new FastJsonQueryMapInfo(
                        queryMapInfo.getType(),
                        queryMapInfo.getParam()
                );
            }
        }

        public static QueryMapInfo toStackBean(FastJsonQueryMapInfo fastJsonQueryMapInfo) {
            if (Objects.isNull(fastJsonQueryMapInfo)) {
                return null;
            } else {
                return new QueryMapInfo(
                        fastJsonQueryMapInfo.getType(),
                        fastJsonQueryMapInfo.getParam()
                );
            }
        }

        @JSONField(name = "type", ordinal = 1)
        private String type;

        @JSONField(name = "param", ordinal = 2)
        private String param;

        public FastJsonQueryMapInfo() {
        }

        public FastJsonQueryMapInfo(String type, String param) {
            this.type = type;
            this.param = param;
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
            return "FastJsonQueryMapInfo{" +
                    "type='" + type + '\'' +
                    ", param='" + param + '\'' +
                    '}';
        }
    }
}
