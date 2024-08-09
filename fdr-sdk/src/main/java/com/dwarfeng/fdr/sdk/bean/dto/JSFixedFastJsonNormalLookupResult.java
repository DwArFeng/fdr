package com.dwarfeng.fdr.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.dto.LookupResult;
import com.dwarfeng.fdr.stack.bean.dto.NormalData;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * JSFixed FastJson 一般查看结果。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class JSFixedFastJsonNormalLookupResult implements Dto {

    private static final long serialVersionUID = 595439563556267095L;

    public static JSFixedFastJsonNormalLookupResult of(LookupResult<NormalData> lookupResult) {
        if (Objects.isNull(lookupResult)) {
            return null;
        } else {
            return new JSFixedFastJsonNormalLookupResult(
                    JSFixedFastJsonLongIdKey.of(lookupResult.getPointKey()),
                    Optional.ofNullable(lookupResult.getDatas()).map(
                            f -> f.stream().map(JSFixedFastJsonNormalData::of).collect(Collectors.toList())
                    ).orElse(null),
                    lookupResult.isHasMore()
            );
        }
    }

    @JSONField(name = "point_key", ordinal = 1)
    private JSFixedFastJsonLongIdKey pointKey;

    @JSONField(name = "datas", ordinal = 2)
    private List<JSFixedFastJsonNormalData> datas;

    @JSONField(name = "has_more", ordinal = 3)
    private boolean hasMore;

    public JSFixedFastJsonNormalLookupResult() {
    }

    public JSFixedFastJsonNormalLookupResult(
            JSFixedFastJsonLongIdKey pointKey, List<JSFixedFastJsonNormalData> datas, boolean hasMore
    ) {
        this.pointKey = pointKey;
        this.datas = datas;
        this.hasMore = hasMore;
    }

    public JSFixedFastJsonLongIdKey getPointKey() {
        return pointKey;
    }

    public void setPointKey(JSFixedFastJsonLongIdKey pointKey) {
        this.pointKey = pointKey;
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
        return "JSFixedFastJsonNormalLookupResult{" +
                "pointKey=" + pointKey +
                ", datas=" + datas +
                ", hasMore=" + hasMore +
                '}';
    }
}
