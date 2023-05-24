package com.dwarfeng.fdr.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.dto.NormalData;
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
public class JSFixedFastJsonNormalQueryResult implements Dto {

    private static final long serialVersionUID = -5030119194150593310L;

    public static JSFixedFastJsonNormalQueryResult of(QueryResult<NormalData> queryResult) {
        if (Objects.isNull(queryResult)) {
            return null;
        } else {
            return new JSFixedFastJsonNormalQueryResult(
                    queryResult.getDatas().stream().map(JSFixedFastJsonNormalData::of).collect(Collectors.toList()),
                    queryResult.isHasMore()
            );
        }
    }

    @JSONField(name = "datas", ordinal = 1)
    private List<JSFixedFastJsonNormalData> datas;

    @JSONField(name = "has_more", ordinal = 2)
    private boolean hasMore;

    public JSFixedFastJsonNormalQueryResult() {
    }

    public JSFixedFastJsonNormalQueryResult(List<JSFixedFastJsonNormalData> datas, boolean hasMore) {
        this.datas = datas;
        this.hasMore = hasMore;
    }

    public List<JSFixedFastJsonNormalData> getDatas() {
        return datas;
    }

    public void setDatas(List<JSFixedFastJsonNormalData> datas) {
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
        return "JSFixedFastJsonNormalQueryResult{" +
                "datas=" + datas +
                ", hasMore=" + hasMore +
                '}';
    }
}
