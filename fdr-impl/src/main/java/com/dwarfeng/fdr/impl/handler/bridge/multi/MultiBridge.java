package com.dwarfeng.fdr.impl.handler.bridge.multi;

import com.dwarfeng.fdr.impl.handler.bridge.FullBridge;
import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.fdr.stack.bean.dto.NormalData;
import com.dwarfeng.fdr.stack.bean.dto.TriggeredData;
import org.springframework.stereotype.Component;

/**
 * 多重桥接器。
 *
 * <p>
 * 多重桥接器本身不直接实现数据的桥接方法，而是通过代理的方式实现。数据写入多重桥接器时，
 * 会调用对应的代理列表，依次调用写入方法。<br>
 * 数据从多重桥接器中读取时，会使用列表中的第一个代理，作为首选代理，调用读取方法。<br>
 * 多重桥接器提供的保持器/持久器是否为只写保持器/持久器取决于首选代理对应的保持器/持久器是否为只写保持器/持久器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class MultiBridge extends FullBridge {

    public static final String BRIDGE_TYPE = "multi";

    private final NormalDataMultiBridgeKeeper normalDataKeeper;
    private final FilteredDataMultiBridgeKeeper filteredDataKeeper;
    private final TriggeredDataMultiBridgeKeeper triggeredDataKeeper;
    private final NormalDataMultiBridgePersister normalDataPersister;
    private final FilteredDataMultiBridgePersister filteredDataPersister;
    private final TriggeredDataMultiBridgePersister triggeredDataPersister;

    public MultiBridge(
            NormalDataMultiBridgeKeeper normalDataKeeper,
            FilteredDataMultiBridgeKeeper filteredDataKeeper,
            TriggeredDataMultiBridgeKeeper triggeredDataKeeper,
            NormalDataMultiBridgePersister normalDataPersister,
            FilteredDataMultiBridgePersister filteredDataPersister,
            TriggeredDataMultiBridgePersister triggeredDataPersister
    ) {
        super(BRIDGE_TYPE);
        this.normalDataKeeper = normalDataKeeper;
        this.filteredDataKeeper = filteredDataKeeper;
        this.triggeredDataKeeper = triggeredDataKeeper;
        this.normalDataPersister = normalDataPersister;
        this.filteredDataPersister = filteredDataPersister;
        this.triggeredDataPersister = triggeredDataPersister;
    }

    @Override
    public Keeper<NormalData> getNormalDataKeeper() {
        return normalDataKeeper;
    }

    @Override
    public Keeper<FilteredData> getFilteredDataKeeper() {
        return filteredDataKeeper;
    }

    @Override
    public Keeper<TriggeredData> getTriggeredDataKeeper() {
        return triggeredDataKeeper;
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
        return "MultiBridge{" +
                "normalDataKeeper=" + normalDataKeeper +
                ", filteredDataKeeper=" + filteredDataKeeper +
                ", triggeredDataKeeper=" + triggeredDataKeeper +
                ", normalDataPersister=" + normalDataPersister +
                ", filteredDataPersister=" + filteredDataPersister +
                ", triggeredDataPersister=" + triggeredDataPersister +
                ", bridgeType='" + bridgeType + '\'' +
                '}';
    }
}
