package com.dwarfeng.fdr.sdk.handler.bridge;

import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.fdr.stack.bean.dto.NormalData;
import com.dwarfeng.fdr.stack.bean.dto.TriggeredData;
import com.dwarfeng.fdr.stack.exception.PersisterNotSupportedException;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 仅支持保持器的桥接器。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
public abstract class KeeperOnlyBridge extends AbstractBridge {

    public KeeperOnlyBridge() {
    }

    public KeeperOnlyBridge(String bridgeType) {
        super(bridgeType);
    }

    @Override
    public Persister<NormalData> getNormalDataPersister() throws HandlerException {
        throw new PersisterNotSupportedException();
    }

    @Override
    public Persister<FilteredData> getFilteredDataPersister() throws HandlerException {
        throw new PersisterNotSupportedException();
    }

    @Override
    public Persister<TriggeredData> getTriggeredDataPersister() throws HandlerException {
        throw new PersisterNotSupportedException();
    }

    @Override
    public String toString() {
        return "KeeperOnlyBridge{" +
                "bridgeType='" + bridgeType + '\'' +
                '}';
    }
}
