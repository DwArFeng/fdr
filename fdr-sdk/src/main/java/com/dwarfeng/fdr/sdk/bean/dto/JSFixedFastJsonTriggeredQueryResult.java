package com.dwarfeng.fdr.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.dto.QueryResult;
import com.dwarfeng.fdr.stack.bean.dto.TriggeredData;
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
public class JSFixedFastJsonTriggeredQueryResult implements Dto {

    private static final long serialVersionUID = 4940498769007944070L;

    public static JSFixedFastJsonTriggeredQueryResult of(QueryResult<TriggeredData> queryResult) {
        if (Objects.isNull(queryResult)) {
            return null;
        } else {
            return new JSFixedFastJsonTriggeredQueryResult(
                    queryResult.getDatas().stream().map(JSFixedFastJsonTriggeredData::of).collect(Collectors.toList()),
                    queryResult.isHasMore()
            );
        }
    }

    @JSONField(name = "datas", ordinal = 1)
    private List<JSFixedFastJsonTriggeredData> datas;

    @JSONField(name = "has_more", ordinal = 2)
    private boolean hasMore;

    public JSFixedFastJsonTriggeredQueryResult() {
    }

    public JSFixedFastJsonTriggeredQueryResult(List<JSFixedFastJsonTriggeredData> datas, boolean hasMore) {
        this.datas = datas;
        this.hasMore = hasMore;
    }

    public List<JSFixedFastJsonTriggeredData> getDatas() {
        return datas;
    }

    public void setDatas(List<JSFixedFastJsonTriggeredData> datas) {
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
        return "JSFixedFastJsonTriggeredQueryResult{" +
                "datas=" + datas +
                ", hasMore=" + hasMore +
                '}';
    }
}
