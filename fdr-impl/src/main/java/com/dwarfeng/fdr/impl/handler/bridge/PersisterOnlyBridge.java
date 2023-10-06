package com.dwarfeng.fdr.impl.handler.bridge;

import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.fdr.stack.bean.dto.NormalData;
import com.dwarfeng.fdr.stack.bean.dto.TriggeredData;
import com.dwarfeng.fdr.stack.exception.KeeperNotSupportedException;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 仅支持持久器的桥接器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public abstract class PersisterOnlyBridge extends AbstractBridge {

    public PersisterOnlyBridge() {
    }

    public PersisterOnlyBridge(String bridgeType) {
        super(bridgeType);
    }

    @Override
    public Keeper<NormalData> getNormalDataKeeper() throws HandlerException {
        throw new KeeperNotSupportedException();
    }

    @Override
    public Keeper<FilteredData> getFilteredDataKeeper() throws HandlerException {
        throw new KeeperNotSupportedException();
    }

    @Override
    public Keeper<TriggeredData> getTriggeredDataKeeper() throws HandlerException {
        throw new KeeperNotSupportedException();
    }

    @Override
    public String toString() {
        return "PersisterOnlyBridge{" +
                "bridgeType='" + bridgeType + '\'' +
                '}';
    }
}
