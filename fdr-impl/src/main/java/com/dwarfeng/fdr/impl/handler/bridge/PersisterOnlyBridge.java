package com.dwarfeng.fdr.impl.handler.bridge;

import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.fdr.stack.bean.dto.NormalData;
import com.dwarfeng.fdr.stack.bean.dto.TriggeredData;
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
    public boolean supportKeeper() {
        return false;
    }

    @Override
    public Keeper<NormalData> getNormalDataKeeper() throws HandlerException {
        try {
            throw new IllegalStateException("不支持的操作");
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public Keeper<FilteredData> getFilteredDataKeeper() throws HandlerException {
        try {
            throw new IllegalStateException("不支持的操作");
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public Keeper<TriggeredData> getTriggeredDataKeeper() throws HandlerException {
        try {
            throw new IllegalStateException("不支持的操作");
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public boolean supportPersister() {
        return true;
    }

    @Override
    public String toString() {
        return "PersisterOnlyBridge{" +
                "bridgeType='" + bridgeType + '\'' +
                '}';
    }
}
