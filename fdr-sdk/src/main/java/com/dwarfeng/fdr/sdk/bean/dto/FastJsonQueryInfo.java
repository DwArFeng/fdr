package com.dwarfeng.fdr.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.dto.QueryInfo;
import com.dwarfeng.fdr.stack.bean.dto.QueryInfo.MapInfo;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collectors;

/**
 * FastJson 查询信息。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FastJsonQueryInfo implements Dto {

    private static final long serialVersionUID = 6365426163907554999L;

    public static FastJsonQueryInfo of(QueryInfo queryInfo) {
        if (Objects.isNull(queryInfo)) {
            return null;
        } else {
            return new FastJsonQueryInfo(
                    queryInfo.getPreset(),
                    queryInfo.getParams(),
                    Optional.ofNullable(queryInfo.getPointKeys()).map(
                            f -> f.stream().map(FastJsonLongIdKey::of).collect(Collectors.toList())
                    ).orElse(null),
                    queryInfo.getStartDate(),
                    queryInfo.getEndDate(),
                    queryInfo.isIncludeStartDate(),
                    queryInfo.isIncludeEndDate(),
                    Optional.ofNullable(queryInfo.getMapInfos()).map(
                            f -> f.stream().map(FastJsonMapInfo::of).collect(Collectors.toList())
                    ).orElse(null)
            );
        }
    }

    @SuppressWarnings("DuplicatedCode")
    public static QueryInfo toStackBean(FastJsonQueryInfo fastJson) {
        if (Objects.isNull(fastJson)) {
            return null;
        } else {
            return new QueryInfo(
                    fastJson.getPreset(),
                    fastJson.getParams(),
                    Optional.ofNullable(fastJson.getPointKeys()).map(
                            f -> f.stream().map(FastJsonLongIdKey::toStackBean).collect(Collectors.toList())
                    ).orElse(null),
                    fastJson.getStartDate(),
                    fastJson.getEndDate(),
                    fastJson.isIncludeStartDate(),
                    fastJson.isIncludeEndDate(),
                    Optional.ofNullable(fastJson.getMapInfos()).map(
                            f -> f.stream().map(FastJsonMapInfo::toStackBean).collect(Collectors.toList())
                    ).orElse(null)
            );
        }
    }

    @JSONField(name = "preset", ordinal = 1)
    private String preset;

    @JSONField(name = "params", ordinal = 2)
    private String[] params;

    @JSONField(name = "point_keys", ordinal = 3)
    private List<FastJsonLongIdKey> pointKeys;

    @JSONField(name = "start_date", ordinal = 4)
    private Date startDate;

    @JSONField(name = "end_date", ordinal = 5)
    private Date endDate;

    @JSONField(name = "include_start_date", ordinal = 6)
    private boolean includeStartDate;

    @JSONField(name = "include_end_date", ordinal = 7)
    private boolean includeEndDate;

    @JSONField(name = "map_infos", ordinal = 8)
    private List<FastJsonMapInfo> mapInfos;

    public FastJsonQueryInfo() {
    }

    public FastJsonQueryInfo(
            String preset, String[] params, List<FastJsonLongIdKey> pointKeys, Date startDate, Date endDate,
            boolean includeStartDate, boolean includeEndDate, List<FastJsonMapInfo> mapInfos
    ) {
        this.preset = preset;
        this.params = params;
        this.pointKeys = pointKeys;
        this.startDate = startDate;
        this.endDate = endDate;
        this.includeStartDate = includeStartDate;
        this.includeEndDate = includeEndDate;
        this.mapInfos = mapInfos;
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

    public List<FastJsonLongIdKey> getPointKeys() {
        return pointKeys;
    }

    public void setPointKeys(List<FastJsonLongIdKey> pointKeys) {
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

    public List<FastJsonMapInfo> getMapInfos() {
        return mapInfos;
    }

    public void setMapInfos(List<FastJsonMapInfo> mapInfos) {
        this.mapInfos = mapInfos;
    }

    @Override
    public String toString() {
        return "FastJsonQueryInfo{" +
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
     * FastJson 映射信息。
     *
     * @author DwArFeng
     * @since 2.0.0
     */
    public static class FastJsonMapInfo implements Dto {

        private static final long serialVersionUID = 711135728575826971L;

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
