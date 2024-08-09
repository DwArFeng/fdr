package com.dwarfeng.fdr.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.fdr.stack.bean.dto.LookupResult;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * FastJson 被过滤查看结果。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FastJsonFilteredLookupResult implements Dto {

    private static final long serialVersionUID = -5607497609045875144L;

    public static FastJsonFilteredLookupResult of(LookupResult<FilteredData> lookupResult) {
        if (Objects.isNull(lookupResult)) {
            return null;
        } else {
            return new FastJsonFilteredLookupResult(
                    FastJsonLongIdKey.of(lookupResult.getPointKey()),
                    Optional.ofNullable(lookupResult.getDatas()).map(
                            f -> f.stream().map(FastJsonFilteredData::of).collect(Collectors.toList())
                    ).orElse(null),
                    lookupResult.isHasMore()
            );
        }
    }

    @JSONField(name = "point_key", ordinal = 1)
    private FastJsonLongIdKey pointKey;

    @JSONField(name = "datas", ordinal = 2)
    private List<FastJsonFilteredData> datas;

    @JSONField(name = "has_more", ordinal = 3)
    private boolean hasMore;

    public FastJsonFilteredLookupResult() {
    }

    public FastJsonFilteredLookupResult(
            FastJsonLongIdKey pointKey, List<FastJsonFilteredData> datas, boolean hasMore
    ) {
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
        return "FastJsonFilteredLookupResult{" +
                "pointKey=" + pointKey +
                ", datas=" + datas +
                ", hasMore=" + hasMore +
                '}';
    }
}
