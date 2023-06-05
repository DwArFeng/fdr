package com.dwarfeng.fdr.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.dto.LookupResult;
import com.dwarfeng.fdr.stack.bean.dto.NormalData;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * FastJson 一般查看结果。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FastJsonNormalLookupResult implements Dto {

    private static final long serialVersionUID = 3474711709250794195L;

    public static FastJsonNormalLookupResult of(LookupResult<NormalData> lookupResult) {
        if (Objects.isNull(lookupResult)) {
            return null;
        } else {
            return new FastJsonNormalLookupResult(
                    FastJsonLongIdKey.of(lookupResult.getPointKey()),
                    lookupResult.getDatas().stream().map(FastJsonNormalData::of).collect(Collectors.toList()),
                    lookupResult.isHasMore()
            );
        }
    }

    @JSONField(name = "point_key", ordinal = 1)
    private FastJsonLongIdKey pointKey;

    @JSONField(name = "datas", ordinal = 2)
    private List<FastJsonNormalData> datas;

    @JSONField(name = "has_more", ordinal = 3)
    private boolean hasMore;

    public FastJsonNormalLookupResult() {
    }

    public FastJsonNormalLookupResult(FastJsonLongIdKey pointKey, List<FastJsonNormalData> datas, boolean hasMore) {
        this.pointKey = pointKey;
        this.datas = datas;
        this.hasMore = hasMore;
    }

    public FastJsonLongIdKey getPointKey() {
        return pointKey;
    }

    public void setPointKey(FastJsonLongIdKey pointKey) {
        this.pointKey = pointKey;
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
        return "FastJsonNormalLookupResult{" +
                "pointKey=" + pointKey +
                ", datas=" + datas +
                ", hasMore=" + hasMore +
                '}';
    }
}
