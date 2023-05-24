package com.dwarfeng.fdr.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.fdr.stack.bean.dto.QueryResult;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * JSFixed FastJson 一般查询结果。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class JSFixedFastJsonFilteredQueryResult implements Dto {

    private static final long serialVersionUID = -816908287970516274L;

    public static JSFixedFastJsonFilteredQueryResult of(QueryResult<FilteredData> queryResult) {
        if (Objects.isNull(queryResult)) {
            return null;
        } else {
            return new JSFixedFastJsonFilteredQueryResult(
                    queryResult.getDatas().stream().map(JSFixedFastJsonFilteredData::of).collect(Collectors.toList()),
                    queryResult.isHasMore()
            );
        }
    }

    @JSONField(name = "datas", ordinal = 1)
    private List<JSFixedFastJsonFilteredData> datas;

    @JSONField(name = "has_more", ordinal = 2)
    private boolean hasMore;

    public JSFixedFastJsonFilteredQueryResult() {
    }

    public JSFixedFastJsonFilteredQueryResult(List<JSFixedFastJsonFilteredData> datas, boolean hasMore) {
        this.datas = datas;
        this.hasMore = hasMore;
    }

    public List<JSFixedFastJsonFilteredData> getDatas() {
        return datas;
    }

    public void setDatas(List<JSFixedFastJsonFilteredData> datas) {
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
        return "JSFixedFastJsonFilteredQueryResult{" +
                "datas=" + datas +
                ", hasMore=" + hasMore +
                '}';
    }
}
