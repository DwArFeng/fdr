package com.dwarfeng.fdr.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.dto.LookupResult;
import com.dwarfeng.fdr.stack.bean.dto.TriggeredData;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * FastJson 被触发查看结果。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FastJsonTriggeredLookupResult implements Dto {

    private static final long serialVersionUID = 6813789002042369470L;

    public static FastJsonTriggeredLookupResult of(LookupResult<TriggeredData> lookupResult) {
        if (Objects.isNull(lookupResult)) {
            return null;
        } else {
            return new FastJsonTriggeredLookupResult(
                    FastJsonLongIdKey.of(lookupResult.getPointKey()),
                    Optional.ofNullable(lookupResult.getDatas()).map(
                            f -> f.stream().map(FastJsonTriggeredData::of).collect(Collectors.toList())
                    ).orElse(null),
                    lookupResult.isHasMore()
            );
        }
    }

    @JSONField(name = "point_key", ordinal = 1)
    private FastJsonLongIdKey pointKey;

    @JSONField(name = "datas", ordinal = 2)
    private List<FastJsonTriggeredData> datas;

    @JSONField(name = "has_more", ordinal = 3)
    private boolean hasMore;

    public FastJsonTriggeredLookupResult() {
    }

    public FastJsonTriggeredLookupResult(
            FastJsonLongIdKey pointKey, List<FastJsonTriggeredData> datas, boolean hasMore
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

    public List<FastJsonTriggeredData> getDatas() {
        return datas;
    }

    public void setDatas(List<FastJsonTriggeredData> datas) {
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
        return "FastJsonTriggeredLookupResult{" +
                "pointKey=" + pointKey +
                ", datas=" + datas +
                ", hasMore=" + hasMore +
                '}';
    }
}
