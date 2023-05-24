package com.dwarfeng.fdr.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 原生查看信息。
 *
 * <p>
 * 该实体表示查看信息，包含了查询信息和映射信息。
 *
 * <p>
 * 有关查看的详细信息，请参阅术语。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class LookupInfo implements Dto {

    private static final long serialVersionUID = -8379676763879083668L;

    private List<QueryInfo> queryInfos;
    private List<MapInfo> mapInfos;

    public LookupInfo() {
    }

    public LookupInfo(List<QueryInfo> queryInfos, List<MapInfo> mapInfos) {
        this.queryInfos = queryInfos;
        this.mapInfos = mapInfos;
    }

    public List<QueryInfo> getQueryInfos() {
        return queryInfos;
    }

    public void setQueryInfos(List<QueryInfo> queryInfos) {
        this.queryInfos = queryInfos;
    }

    public List<MapInfo> getMapInfos() {
        return mapInfos;
    }

    public void setMapInfos(List<MapInfo> mapInfos) {
        this.mapInfos = mapInfos;
    }

    @Override
    public String toString() {
        return "GeneralLookupInfo{" +
                "queryInfos=" + queryInfos +
                ", mapInfos=" + mapInfos +
                '}';
    }

    /**
     * 查询信息。
     *
     * <p>
     * 该实体表示查询信息，包含了查询的预设、参数、开始时间、结束时间、是否包含开始时间、是否包含结束时间、发生时间的排序。
     *
     * <p>
     * 该实体包含了查看过程中的第一步，也就是查询，所需要的信息。
     *
     * <p>
     * 有关查看的详细信息，请参阅术语。
     *
     * @author DwArFeng
     * @since 2.0.0
     */
    public static class QueryInfo implements Dto {

        private static final long serialVersionUID = 7861310280931051582L;

        private String preset;
        private String[] params;
        private LongIdKey pointKey;
        private Date startDate;
        private Date endDate;
        private boolean includeStartDate;
        private boolean includeEndDate;

        public QueryInfo() {
        }

        public QueryInfo(
                String preset, String[] params, LongIdKey pointKey, Date startDate, Date endDate,
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
                    '}';
        }
    }

    /**
     * 映射信息。
     *
     * <p>
     * 该实体表示映射信息，包含了映射的类型和映射的参数。
     *
     * <p>
     * 该实体包含了查看过程中的第二步，也就是映射，所需要的信息。
     *
     * <p>
     * 有关查看的详细信息，请参阅术语。
     *
     * @author DwArFeng
     * @since 2.0.0
     */
    public static class MapInfo implements Dto {

        private static final long serialVersionUID = -8831662619732433270L;

        private String type;
        private String param;

        public MapInfo() {
        }

        public MapInfo(String type, String param) {
            this.type = type;
            this.param = param;
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
            return "MapInfo{" +
                    "type='" + type + '\'' +
                    ", param='" + param + '\'' +
                    '}';
        }
    }
}
