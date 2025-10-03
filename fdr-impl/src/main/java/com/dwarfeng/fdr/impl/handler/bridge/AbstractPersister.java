package com.dwarfeng.fdr.impl.handler.bridge;

import com.dwarfeng.fdr.stack.struct.Data;

/**
 * 持久器的抽象实现。
 *
 * @author DwArFeng
 * @see com.dwarfeng.fdr.sdk.handler.bridge.AbstractPersister
 * @since 2.0.0
 * @deprecated 该类已经被废弃，请使用 sdk 模块下的对应类代替。
 */
@Deprecated
public abstract class AbstractPersister<D extends Data> extends
        com.dwarfeng.fdr.sdk.handler.bridge.AbstractPersister<D> {

    public AbstractPersister() {
    }
}
