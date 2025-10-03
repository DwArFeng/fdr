package com.dwarfeng.fdr.sdk.handler.bridge;

import com.dwarfeng.fdr.sdk.handler.Bridge.Keeper;
import com.dwarfeng.fdr.stack.struct.Data;

/**
 * 保持器的抽象实现。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
public abstract class AbstractKeeper<D extends Data> implements Keeper<D> {

    @Override
    public String toString() {
        return "AbstractKeeper{}";
    }
}
