package com.dwarfeng.fdr.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 查询信息。
 *
 * <p>
 * 该实体表示查询信息，包含了序列的查看信息和序列的映射信息。
 *
 * <p>
 * 有关查看的详细信息，请参阅术语。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class QueryInfo implements Dto {

    private static final long serialVersionUID = 3748483438711703570L;

    private List<QueryLookupInfo> lookupInfos;
    private List<QueryMapInfo> mapInfos;

    public QueryInfo() {
    }

    public QueryInfo(List<QueryLookupInfo> lookupInfos, List<QueryMapInfo> mapInfos) {
        this.lookupInfos = lookupInfos;
        this.mapInfos = mapInfos;
    }

    @Nonnull
    public List<QueryLookupInfo> getQueryInfos() {
        return lookupInfos;
    }

    public void setQueryInfos(List<QueryLookupInfo> queryLookupInfos) {
        this.lookupInfos = queryLookupInfos;
    }

    @Nonnull
    public List<QueryMapInfo> getMapInfos() {
        return mapInfos;
    }

    public void setMapInfos(List<QueryMapInfo> queryMapInfos) {
        this.mapInfos = queryMapInfos;
    }

    @Override
    public String toString() {
        return "QueryInfo{" +
                "lookupInfos=" + lookupInfos +
                ", mapInfos=" + mapInfos +
                '}';
    }

    /**
     * 查询查看信息。
     *
     * <p>
     * 该实体表示查看信息，包含了查看的预设、参数、开始时间、结束时间、是否包含开始时间、是否包含结束时间、发生时间的排序。
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
    public static class QueryLookupInfo implements Dto {

        private static final long serialVersionUID = 8158742198142751018L;

        private String preset;
        private String[] params;
        private LongIdKey pointKey;
        private Date startDate;
        private Date endDate;
        private boolean includeStartDate;
        private boolean includeEndDate;

        public QueryLookupInfo() {
        }

        public QueryLookupInfo(
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
        public LongIdKey getPointKey() {
            return pointKey;
        }

        public void setPointKey(LongIdKey pointKey) {
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
            return "QueryLookupInfo{" +
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
     * 查询映射信息。
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
    public static class QueryMapInfo implements Dto {

        private static final long serialVersionUID = -850889065992336466L;

        private String type;
        private String param;

        public QueryMapInfo() {
        }

        public QueryMapInfo(String type, String param) {
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
            return "QueryMapInfo{" +
                    "type='" + type + '\'' +
                    ", param='" + param + '\'' +
                    '}';
        }
    }
}
