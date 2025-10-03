package com.dwarfeng.fdr.sdk.handler.bridge;

import com.dwarfeng.fdr.sdk.handler.Bridge.Persister;
import com.dwarfeng.fdr.stack.struct.Data;

/**
 * 持久器的抽象实现。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
public abstract class AbstractPersister<D extends Data> implements Persister<D> {

    @Override
    public String toString() {
        return "AbstractPersister{}";
    }
}
