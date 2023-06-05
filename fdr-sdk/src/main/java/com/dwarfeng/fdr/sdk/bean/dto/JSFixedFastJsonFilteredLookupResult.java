package com.dwarfeng.fdr.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.fdr.stack.bean.dto.LookupResult;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * JSFixed FastJson 被过滤查看结果。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class JSFixedFastJsonFilteredLookupResult implements Dto {

    private static final long serialVersionUID = 8131536470494992838L;

    public static JSFixedFastJsonFilteredLookupResult of(LookupResult<FilteredData> lookupResult) {
        if (Objects.isNull(lookupResult)) {
            return null;
        } else {
            return new JSFixedFastJsonFilteredLookupResult(
                    JSFixedFastJsonLongIdKey.of(lookupResult.getPointKey()),
                    lookupResult.getDatas().stream().map(JSFixedFastJsonFilteredData::of).collect(Collectors.toList()),
                    lookupResult.isHasMore()
            );
        }
    }

    @JSONField(name = "point_key", ordinal = 1)
    private JSFixedFastJsonLongIdKey pointKey;

    @JSONField(name = "datas", ordinal = 2)
    private List<JSFixedFastJsonFilteredData> datas;

    @JSONField(name = "has_more", ordinal = 3)
    private boolean hasMore;

    public JSFixedFastJsonFilteredLookupResult() {
    }

    public JSFixedFastJsonFilteredLookupResult(
            JSFixedFastJsonLongIdKey pointKey, List<JSFixedFastJsonFilteredData> datas, boolean hasMore
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
        return "JSFixedFastJsonFilteredLookupResult{" +
                "pointKey=" + pointKey +
                ", datas=" + datas +
                ", hasMore=" + hasMore +
                '}';
    }
}
