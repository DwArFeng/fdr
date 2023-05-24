package com.dwarfeng.fdr.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.fdr.stack.bean.dto.QueryResult;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * FastJson 一般查询结果。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FastJsonFilteredQueryResult implements Dto {

    private static final long serialVersionUID = -3203699128622932226L;

    public static FastJsonFilteredQueryResult of(QueryResult<FilteredData> queryResult) {
        if (Objects.isNull(queryResult)) {
            return null;
        } else {
            return new FastJsonFilteredQueryResult(
                    queryResult.getDatas().stream().map(FastJsonFilteredData::of).collect(Collectors.toList()),
                    queryResult.isHasMore()
            );
        }
    }

    @JSONField(name = "datas", ordinal = 1)
    private List<FastJsonFilteredData> datas;

    @JSONField(name = "has_more", ordinal = 2)
    private boolean hasMore;

    public FastJsonFilteredQueryResult() {
    }

    public FastJsonFilteredQueryResult(List<FastJsonFilteredData> datas, boolean hasMore) {
        this.datas = datas;
        this.hasMore = hasMore;
    }

    public List<FastJsonFilteredData> getDatas() {
        return datas;
    }

    public void setDatas(List<FastJsonFilteredData> datas) {
        this.datas = datas;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    @Override
    public String toString() {
        return "FastJsonFilteredQueryResult{" +
                "datas=" + datas +
                ", hasMore=" + hasMore +
                '}';
    }
}
