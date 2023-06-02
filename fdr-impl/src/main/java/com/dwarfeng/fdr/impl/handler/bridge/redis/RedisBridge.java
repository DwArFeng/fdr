package com.dwarfeng.fdr.impl.handler.bridge.redis;

import com.dwarfeng.fdr.impl.handler.bridge.AbstractBridge;
import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.fdr.stack.bean.dto.NormalData;
import com.dwarfeng.fdr.stack.bean.dto.TriggeredData;
import org.springframework.stereotype.Component;

/**
 * Redis 桥接器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class RedisBridge extends AbstractBridge {

    public static final String BRIDGE_TYPE = "redis";

    private final RedisBridgeNormalDataKeeper normalDataKeeper;
    private final RedisBridgeFilteredDataKeeper filteredDataKeeper;
    private final RedisBridgeTriggeredDataKeeper triggeredDataKeeper;

    public RedisBridge(
            RedisBridgeNormalDataKeeper normalDataKeeper,
            RedisBridgeFilteredDataKeeper filteredDataKeeper,
            RedisBridgeTriggeredDataKeeper triggeredDataKeeper
    ) {
        super(BRIDGE_TYPE);
        this.normalDataKeeper = normalDataKeeper;
        this.filteredDataKeeper = filteredDataKeeper;
        this.triggeredDataKeeper = triggeredDataKeeper;
    }

    @Override
    protected Keeper<NormalData> getNormalDataKeeper() {
        return normalDataKeeper;
    }

    @Override
    protected Keeper<FilteredData> getFilteredDataKeeper() {
        return filteredDataKeeper;
    }

    @Override
    protected Keeper<TriggeredData> getTriggeredDataKeeper() {
        return triggeredDataKeeper;
    }

    @Override
    public String toString() {
        return "RedisBridge{" +
                "normalDataKeeper=" + normalDataKeeper +
                ", filteredDataKeeper=" + filteredDataKeeper +
                ", triggeredDataKeeper=" + triggeredDataKeeper +
                ", bridgeType='" + bridgeType + '\'' +
                '}';
    }
}
