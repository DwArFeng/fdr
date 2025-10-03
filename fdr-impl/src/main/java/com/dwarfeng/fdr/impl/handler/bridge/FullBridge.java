package com.dwarfeng.fdr.impl.handler.bridge;

/**
 * 完整桥接器。
 *
 * <p>
 * 完整桥接器是指同时支持保持器和持久器的桥接器。
 *
 * @author DwArFeng
 * @see com.dwarfeng.fdr.sdk.handler.bridge.FullBridge
 * @since 2.0.0
 * @deprecated 该对象已经被废弃，请使用 sdk 模块下的对应对象代替。
 */
@Deprecated
public abstract class FullBridge extends com.dwarfeng.fdr.sdk.handler.bridge.FullBridge {

    public FullBridge() {
    }

    public FullBridge(String bridgeType) {
        super(bridgeType);
    }
}
