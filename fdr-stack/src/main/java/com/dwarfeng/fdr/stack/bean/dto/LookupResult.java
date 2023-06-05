package com.dwarfeng.fdr.stack.bean.dto;

import com.dwarfeng.fdr.stack.struct.Data;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.List;

/**
 * 查看结果。
 *
 * <p>
 * 该实体表示查询结果，包含了查询的数据记录和是否还有更多的数据记录。
 *
 * <p>
 * 有关查询的详细信息，请参阅术语。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class LookupResult<D extends Data> implements Dto {

    private static final long serialVersionUID = 5058287186610195658L;

    private LongIdKey pointKey;
    private List<D> datas;
    private boolean hasMore;

    public LookupResult() {
    }

    public LookupResult(LongIdKey pointKey, List<D> datas, boolean hasMore) {
        this.pointKey = pointKey;
        this.datas = datas;
        this.hasMore = hasMore;
    }

    public LongIdKey getPointKey() {
        return pointKey;
    }

    public void setPointKey(LongIdKey pointKey) {
        this.pointKey = pointKey;
    }

    public List<D> getDatas() {
        return datas;
    }

    public void setDatas(List<D> datas) {
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
        return "LookupResult{" +
                "pointKey=" + pointKey +
                ", datas=" + datas +
                ", hasMore=" + hasMore +
                '}';
    }
}
