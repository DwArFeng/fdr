package com.dwarfeng.fdr.impl.handler.bridge;

import com.dwarfeng.fdr.stack.struct.Data;

/**
 * 只写保持器。
 *
 * @author DwArFeng
 * @see com.dwarfeng.fdr.sdk.handler.bridge.WriteOnlyKeeper
 * @since 2.0.0
 * @deprecated 该对象已经被废弃，请使用 sdk 模块下的对应对象代替。
 */
@Deprecated
public abstract class WriteOnlyKeeper<D extends Data> extends com.dwarfeng.fdr.sdk.handler.bridge.WriteOnlyKeeper<D> {

    public WriteOnlyKeeper() {
    }
}
