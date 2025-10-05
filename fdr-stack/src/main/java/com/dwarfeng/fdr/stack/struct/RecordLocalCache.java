package com.dwarfeng.fdr.stack.struct;

import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.handler.Filter;
import com.dwarfeng.fdr.stack.handler.Trigger;
import com.dwarfeng.fdr.stack.handler.Washer;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Map;

/**
 * 记录本地缓存。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
public final class RecordLocalCache {

    private final Point point;
    private final Map<LongIdKey, Washer> preFilterWasherMap;
    private final Map<LongIdKey, Filter> filterMap;
    private final Map<LongIdKey, Washer> postFilterWasherMap;
    private final Map<LongIdKey, Trigger> triggerMap;

    public RecordLocalCache(
            Point point, Map<LongIdKey, Washer> preFilterWasherMap,
            Map<LongIdKey, Filter> filterMap,
            Map<LongIdKey, Washer> postFilterWasherMap,
            Map<LongIdKey, Trigger> triggerMap
    ) {
        this.point = point;
        this.preFilterWasherMap = preFilterWasherMap;
        this.filterMap = filterMap;
        this.postFilterWasherMap = postFilterWasherMap;
        this.triggerMap = triggerMap;
    }

    public Point getPoint() {
        return point;
    }

    public Map<LongIdKey, Washer> getPreFilterWasherMap() {
        return preFilterWasherMap;
    }

    public Map<LongIdKey, Filter> getFilterMap() {
        return filterMap;
    }

    public Map<LongIdKey, Washer> getPostFilterWasherMap() {
        return postFilterWasherMap;
    }

    public Map<LongIdKey, Trigger> getTriggerMap() {
        return triggerMap;
    }

    @Override
    public String toString() {
        return "RecordLocalCache{" +
                "point=" + point +
                ", preFilterWasherMap=" + preFilterWasherMap +
                ", filterMap=" + filterMap +
                ", postFilterWasherMap=" + postFilterWasherMap +
                ", triggerMap=" + triggerMap +
                '}';
    }
}
