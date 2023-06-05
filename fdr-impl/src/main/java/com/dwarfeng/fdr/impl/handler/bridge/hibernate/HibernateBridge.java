package com.dwarfeng.fdr.impl.handler.bridge.hibernate;

import com.dwarfeng.fdr.impl.handler.bridge.PersisterOnlyBridge;
import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.fdr.stack.bean.dto.NormalData;
import com.dwarfeng.fdr.stack.bean.dto.TriggeredData;
import org.springframework.stereotype.Component;

/**
 * Hibernate 桥接器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class HibernateBridge extends PersisterOnlyBridge {

    public static final String BRIDGE_TYPE = "hibernate";

    private final HibernateBridgeNormalDataPersister normalDataPersister;
    private final HibernateBridgeFilteredDataPersister filteredDataPersister;
    private final HibernateBridgeTriggeredDataPersister triggeredDataPersister;

    public HibernateBridge(
            HibernateBridgeNormalDataPersister normalDataPersister,
            HibernateBridgeFilteredDataPersister filteredDataPersister,
            HibernateBridgeTriggeredDataPersister triggeredDataPersister
    ) {
        super(BRIDGE_TYPE);
        this.normalDataPersister = normalDataPersister;
        this.filteredDataPersister = filteredDataPersister;
        this.triggeredDataPersister = triggeredDataPersister;
    }

    @Override
    protected Persister<NormalData> getNormalDataPersister() {
        return normalDataPersister;
    }

    @Override
    protected Persister<FilteredData> getFilteredDataPersister() {
        return filteredDataPersister;
    }

    @Override
    protected Persister<TriggeredData> getTriggeredDataPersister() {
        return triggeredDataPersister;
    }

    @Override
    public String toString() {
        return "HibernateBridge{" +
                "normalDataPersister=" + normalDataPersister +
                ", filteredDataPersister=" + filteredDataPersister +
                ", triggeredDataPersister=" + triggeredDataPersister +
                ", bridgeType='" + bridgeType + '\'' +
                '}';
    }
}
