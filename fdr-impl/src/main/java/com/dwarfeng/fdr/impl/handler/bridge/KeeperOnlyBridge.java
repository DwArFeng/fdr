package com.dwarfeng.fdr.impl.handler.bridge;

import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.fdr.stack.bean.dto.NormalData;
import com.dwarfeng.fdr.stack.bean.dto.TriggeredData;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 仅支持保持器的桥接器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public abstract class KeeperOnlyBridge extends AbstractBridge {

    public KeeperOnlyBridge() {
    }

    public KeeperOnlyBridge(String bridgeType) {
        super(bridgeType);
    }

    @Override
    public boolean supportKeeper() {
        return true;
    }

    @Override
    public boolean supportPersister() {
        return false;
    }

    @Override
    public Persister<NormalData> getNormalDataPersister() throws HandlerException {
        try {
            throw new IllegalStateException("不支持的操作");
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public Persister<FilteredData> getFilteredDataPersister() throws HandlerException {
        try {
            throw new IllegalStateException("不支持的操作");
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public Persister<TriggeredData> getTriggeredDataPersister() throws HandlerException {
        try {
            throw new IllegalStateException("不支持的操作");
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public String toString() {
        return "KeeperOnlyBridge{" +
                "bridgeType='" + bridgeType + '\'' +
                '}';
    }
}
