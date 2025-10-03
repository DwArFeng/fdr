package com.dwarfeng.fdr.impl.handler.bridge.influxdb;

import com.dwarfeng.fdr.sdk.handler.bridge.PersisterOnlyBridge;
import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.fdr.stack.bean.dto.NormalData;
import com.dwarfeng.fdr.stack.bean.dto.TriggeredData;
import org.springframework.stereotype.Component;

/**
 * Influxdb 桥接器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class InfluxdbBridge extends PersisterOnlyBridge {

    public static final String BRIDGE_TYPE = "influxdb";

    private final InfluxdbBridgeNormalDataPersister normalDataPersister;
    private final InfluxdbBridgeFilteredDataPersister filteredDataPersister;
    private final InfluxdbBridgeTriggeredDataPersister triggeredDataPersister;

    public InfluxdbBridge(
            InfluxdbBridgeNormalDataPersister normalDataPersister,
            InfluxdbBridgeFilteredDataPersister filteredDataPersister,
            InfluxdbBridgeTriggeredDataPersister triggeredDataPersister
    ) {
        super(BRIDGE_TYPE);
        this.normalDataPersister = normalDataPersister;
        this.filteredDataPersister = filteredDataPersister;
        this.triggeredDataPersister = triggeredDataPersister;
    }

    @Override
    public Persister<NormalData> getNormalDataPersister() {
        return normalDataPersister;
    }

    @Override
    public Persister<FilteredData> getFilteredDataPersister() {
        return filteredDataPersister;
    }

    @Override
    public Persister<TriggeredData> getTriggeredDataPersister() {
        return triggeredDataPersister;
    }

    @Override
    public String toString() {
        return "InfluxdbBridge{" +
                "normalDataPersister=" + normalDataPersister +
                ", filteredDataPersister=" + filteredDataPersister +
                ", triggeredDataPersister=" + triggeredDataPersister +
                ", bridgeType='" + bridgeType + '\'' +
                '}';
    }
}
