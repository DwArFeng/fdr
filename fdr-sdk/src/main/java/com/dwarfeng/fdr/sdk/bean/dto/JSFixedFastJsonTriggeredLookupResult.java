package com.dwarfeng.fdr.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.dto.LookupResult;
import com.dwarfeng.fdr.stack.bean.dto.TriggeredData;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * JSFixed FastJson 被触发查看结果。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class JSFixedFastJsonTriggeredLookupResult implements Dto {

    private static final long serialVersionUID = -3125048770536836571L;

    public static JSFixedFastJsonTriggeredLookupResult of(LookupResult<TriggeredData> lookupResult) {
        if (Objects.isNull(lookupResult)) {
            return null;
        } else {
            return new JSFixedFastJsonTriggeredLookupResult(
                    JSFixedFastJsonLongIdKey.of(lookupResult.getPointKey()),
                    lookupResult.getDatas().stream().map(JSFixedFastJsonTriggeredData::of).collect(Collectors.toList()),
                    lookupResult.isHasMore()
            );
        }
    }

    @JSONField(name = "point_key", ordinal = 1)
    private JSFixedFastJsonLongIdKey pointKey;

    @JSONField(name = "datas", ordinal = 2)
    private List<JSFixedFastJsonTriggeredData> datas;

    @JSONField(name = "has_more", ordinal = 3)
    private boolean hasMore;

    public JSFixedFastJsonTriggeredLookupResult() {
    }

    public JSFixedFastJsonTriggeredLookupResult(
            JSFixedFastJsonLongIdKey pointKey, List<JSFixedFastJsonTriggeredData> datas, boolean hasMore
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
        return "JSFixedFastJsonTriggeredLookupResult{" +
                "pointKey=" + pointKey +
                ", datas=" + datas +
                ", hasMore=" + hasMore +
                '}';
    }
}
