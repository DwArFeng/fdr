package com.dwarfeng.fdr.impl.handler.bridge.mock;

import com.dwarfeng.fdr.impl.handler.bridge.AbstractBridge;
import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.fdr.stack.bean.dto.NormalData;
import com.dwarfeng.fdr.stack.bean.dto.TriggeredData;
import org.springframework.stereotype.Component;

/**
 * 模拟桥接器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class MockBridge extends AbstractBridge {

    public static final String BRIDGE_TYPE = "mock";

    private final MockBridgeNormalDataBridgeKeeper normalDataBridgeKeeper;
    private final MockBridgeFilteredDataBridgeKeeper filteredDataBridgeKeeper;
    private final MockBridgeTriggeredDataBridgeKeeper triggeredDataBridgeKeeper;
    private final MockBridgeNormalDataBridgePersister normalDataBridgePersister;
    private final MockBridgeFilteredDataBridgePersister filteredDataBridgePersister;
    private final MockBridgeTriggeredDataBridgePersister triggeredDataBridgePersister;

    public MockBridge(
            MockBridgeNormalDataBridgeKeeper normalDataBridgeKeeper,
            MockBridgeFilteredDataBridgeKeeper filteredDataBridgeKeeper,
            MockBridgeTriggeredDataBridgeKeeper triggeredDataBridgeKeeper,
            MockBridgeNormalDataBridgePersister normalDataBridgePersister,
            MockBridgeFilteredDataBridgePersister filteredDataBridgePersister,
            MockBridgeTriggeredDataBridgePersister triggeredDataBridgePersister
    ) {
        super(BRIDGE_TYPE);
        this.normalDataBridgeKeeper = normalDataBridgeKeeper;
        this.filteredDataBridgeKeeper = filteredDataBridgeKeeper;
        this.triggeredDataBridgeKeeper = triggeredDataBridgeKeeper;
        this.normalDataBridgePersister = normalDataBridgePersister;
        this.filteredDataBridgePersister = filteredDataBridgePersister;
        this.triggeredDataBridgePersister = triggeredDataBridgePersister;
    }

    @Override
    protected Keeper<NormalData> getNormalDataKeeper() {
        return normalDataBridgeKeeper;
    }

    @Override
    protected Keeper<FilteredData> getFilteredDataKeeper() {
        return filteredDataBridgeKeeper;
    }

    @Override
    protected Keeper<TriggeredData> getTriggeredDataKeeper() {
        return triggeredDataBridgeKeeper;
    }

    @Override
    protected Persister<NormalData> getNormalDataPersister() {
        return normalDataBridgePersister;
    }

    @Override
    protected Persister<FilteredData> getFilteredDataPersister() {
        return filteredDataBridgePersister;
    }

    @Override
    protected Persister<TriggeredData> getTriggeredDataPersister() {
        return triggeredDataBridgePersister;
    }

    @Override
    public String toString() {
        return "MockBridge{" +
                "normalDataBridgeKeeper=" + normalDataBridgeKeeper +
                ", filteredDataBridgeKeeper=" + filteredDataBridgeKeeper +
                ", triggeredDataBridgeKeeper=" + triggeredDataBridgeKeeper +
                ", normalDataBridgePersister=" + normalDataBridgePersister +
                ", filteredDataBridgePersister=" + filteredDataBridgePersister +
                ", triggeredDataBridgePersister=" + triggeredDataBridgePersister +
                '}';
    }
}
