package com.dwarfeng.fdr.impl.handler.bridge;

import com.dwarfeng.fdr.stack.exception.MethodNotSupportedException;
import com.dwarfeng.fdr.stack.structure.Data;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.List;

/**
 * 只写保持器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public abstract class WriteOnlyKeeper<D extends Data> extends AbstractKeeper<D> {

    public WriteOnlyKeeper() {
        super(true);
    }

    @Override
    protected D doInspect(LongIdKey pointKey) throws Exception {
        throw new MethodNotSupportedException();
    }

    @Override
    protected List<D> doInspect(List<LongIdKey> pointKeys) throws Exception {
        throw new MethodNotSupportedException();
    }
}
