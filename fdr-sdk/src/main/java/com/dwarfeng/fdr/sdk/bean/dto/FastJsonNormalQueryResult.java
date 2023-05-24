package com.dwarfeng.fdr.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.dto.NormalData;
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
public class FastJsonNormalQueryResult implements Dto {

    private static final long serialVersionUID = -8009846086049621877L;

    public static FastJsonNormalQueryResult of(QueryResult<NormalData> queryResult) {
        if (Objects.isNull(queryResult)) {
            return null;
        } else {
            return new FastJsonNormalQueryResult(
                    queryResult.getDatas().stream().map(FastJsonNormalData::of).collect(Collectors.toList()),
                    queryResult.isHasMore()
            );
        }
    }

    @JSONField(name = "datas", ordinal = 1)
    private List<FastJsonNormalData> datas;

    @JSONField(name = "has_more", ordinal = 2)
    private boolean hasMore;

    public FastJsonNormalQueryResult() {
    }

    public FastJsonNormalQueryResult(List<FastJsonNormalData> datas, boolean hasMore) {
        this.datas = datas;
        this.hasMore = hasMore;
    }

    public List<FastJsonNormalData> getDatas() {
        return datas;
    }

    public void setDatas(List<FastJsonNormalData> datas) {
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
        return "FastJsonNormalQueryResult{" +
                "datas=" + datas +
                ", hasMore=" + hasMore +
                '}';
    }
}
