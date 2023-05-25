package com.dwarfeng.fdr.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.dto.LookupInfo;
import com.dwarfeng.fdr.stack.bean.dto.LookupInfo.MapInfo;
import com.dwarfeng.fdr.stack.bean.dto.LookupInfo.QueryInfo;
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
 * FastJson 原生查看信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FastJsonLookupInfo implements Dto {

    private static final long serialVersionUID = 8340174080290012782L;

    public static FastJsonLookupInfo of(LookupInfo lookupInfo) {
        if (Objects.isNull(lookupInfo)) {
            return null;
        } else {
            return new FastJsonLookupInfo(
                    lookupInfo.getQueryInfos().stream().map(FastJsonQueryInfo::of).collect(Collectors.toList()),
                    lookupInfo.getMapInfos().stream().map(FastJsonMapInfo::of).collect(Collectors.toList())
            );
        }
    }

    public static LookupInfo toStackBean(FastJsonLookupInfo fastJson) {
        if (Objects.isNull(fastJson)) {
            return null;
        } else {
            return new LookupInfo(
                    fastJson.getQueryInfos().stream().map(FastJsonQueryInfo::toStackBean).collect(Collectors.toList()),
                    fastJson.getMapInfos().stream().map(FastJsonMapInfo::toStackBean).collect(Collectors.toList())
            );
        }
    }

    @JSONField(name = "query_infos", ordinal = 1)
    private List<FastJsonQueryInfo> queryInfos;

    @JSONField(name = "map_infos", ordinal = 2)
    private List<FastJsonMapInfo> mapInfos;

    public FastJsonLookupInfo() {
    }

    public FastJsonLookupInfo(List<FastJsonQueryInfo> queryInfos, List<FastJsonMapInfo> mapInfos) {
        this.queryInfos = queryInfos;
        this.mapInfos = mapInfos;
    }

    @Nonnull
    public List<FastJsonQueryInfo> getQueryInfos() {
        return queryInfos;
    }

    public void setQueryInfos(List<FastJsonQueryInfo> queryInfos) {
        this.queryInfos = queryInfos;
    }

    @Nonnull
    public List<FastJsonMapInfo> getMapInfos() {
        return mapInfos;
    }

    public void setMapInfos(List<FastJsonMapInfo> mapInfos) {
        this.mapInfos = mapInfos;
    }

    @Override
    public String toString() {
        return "FastJsonLookupInfo{" +
                "queryInfos=" + queryInfos +
                ", mapInfos=" + mapInfos +
                '}';
    }

    /**
     * FastJson 查询信息。
     *
     * @author DwArFeng
     * @since 2.0.0
     */
    public static class FastJsonQueryInfo implements Dto {

        private static final long serialVersionUID = -4760460581967069933L;

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
                        queryInfo.isIncludeEndDate()
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
                        fastJsonQueryInfo.isIncludeEndDate()
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

        public FastJsonQueryInfo() {
        }

        public FastJsonQueryInfo(
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
            return "FastJsonQueryInfo{" +
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
    public static class FastJsonMapInfo implements Dto {

        private static final long serialVersionUID = -68547825452246632L;

        public static FastJsonMapInfo of(MapInfo mapInfo) {
            if (Objects.isNull(mapInfo)) {
                return null;
            } else {
                return new FastJsonMapInfo(
                        mapInfo.getType(),
                        mapInfo.getParam()
                );
            }
        }

        public static MapInfo toStackBean(FastJsonMapInfo fastJsonMapInfo) {
            if (Objects.isNull(fastJsonMapInfo)) {
                return null;
            } else {
                return new MapInfo(
                        fastJsonMapInfo.getType(),
                        fastJsonMapInfo.getParam()
                );
            }
        }

        @JSONField(name = "type", ordinal = 1)
        private String type;

        @JSONField(name = "param", ordinal = 2)
        private String param;

        public FastJsonMapInfo() {
        }

        public FastJsonMapInfo(String type, String param) {
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
            return "FastJsonMapInfo{" +
                    "type='" + type + '\'' +
                    ", param='" + param + '\'' +
                    '}';
        }
    }
}
