package com.dwarfeng.fdr.impl.handler.bridge.drain;

import com.dwarfeng.fdr.sdk.handler.bridge.FullBridge;
import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.fdr.stack.bean.dto.NormalData;
import com.dwarfeng.fdr.stack.bean.dto.TriggeredData;
import com.dwarfeng.fdr.stack.struct.Data;
import org.springframework.stereotype.Component;

/**
 * Drain 桥接器。
 *
 * <p>
 * 简单地丢弃掉所有的数据的桥接器，一般用于占位。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class DrainBridge extends FullBridge {

    public static final String BRIDGE_TYPE = "drain";

    private final DrainBridgeKeeper<? extends Data> keeper;
    private final DrainBridgePersister<? extends Data> persister;

    public DrainBridge(
            DrainBridgeKeeper<? extends Data> keeper,
            DrainBridgePersister<? extends Data> persister
    ) {
        super(BRIDGE_TYPE);
        this.keeper = keeper;
        this.persister = persister;
    }

    @Override
    public Keeper<NormalData> getNormalDataKeeper() {
        // 此转换类型安全，因为 DrainBridgeKeeper 不对数据进行任何处理。
        @SuppressWarnings("unchecked")
        Keeper<NormalData> dejaVu = (Keeper<NormalData>) keeper;
        return dejaVu;
    }

    @Override
    public Keeper<FilteredData> getFilteredDataKeeper() {
        // 此转换类型安全，因为 DrainBridgeKeeper 不对数据进行任何处理。
        @SuppressWarnings("unchecked")
        Keeper<FilteredData> dejaVu = (Keeper<FilteredData>) keeper;
        return dejaVu;
    }

    @Override
    public Keeper<TriggeredData> getTriggeredDataKeeper() {
        // 此转换类型安全，因为 DrainBridgeKeeper 不对数据进行任何处理。
        @SuppressWarnings("unchecked")
        Keeper<TriggeredData> dejaVu = (Keeper<TriggeredData>) keeper;
        return dejaVu;
    }

    @Override
    public Persister<NormalData> getNormalDataPersister() {
        // 此转换类型安全，因为 DrainBridgePersister 不对数据进行任何处理。
        @SuppressWarnings("unchecked")
        Persister<NormalData> dejaVu = (Persister<NormalData>) persister;
        return dejaVu;
    }

    @Override
    public Persister<FilteredData> getFilteredDataPersister() {
        // 此转换类型安全，因为 DrainBridgePersister 不对数据进行任何处理。
        @SuppressWarnings("unchecked")
        Persister<FilteredData> dejaVu = (Persister<FilteredData>) persister;
        return dejaVu;
    }

    @Override
    public Persister<TriggeredData> getTriggeredDataPersister() {
        // 此转换类型安全，因为 DrainBridgePersister 不对数据进行任何处理。
        @SuppressWarnings("unchecked")
        Persister<TriggeredData> dejaVu = (Persister<TriggeredData>) persister;
        return dejaVu;
    }
}
