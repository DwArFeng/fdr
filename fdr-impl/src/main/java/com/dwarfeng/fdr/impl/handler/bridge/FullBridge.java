package com.dwarfeng.fdr.impl.handler.bridge;

/**
 * 完整桥接器。
 *
 * <p>
 * 完整桥接器是指同时支持保持器和持久器的桥接器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public abstract class FullBridge extends AbstractBridge {

    public FullBridge() {
    }

    public FullBridge(String bridgeType) {
        super(bridgeType);
    }

    @Override
    public boolean supportKeeper() {
        return true;
    }

    @Override
    public boolean supportPersister() {
        return true;
    }

    @Override
    public String toString() {
        return "FullBridge{" +
                "bridgeType='" + bridgeType + '\'' +
                '}';
    }
}
