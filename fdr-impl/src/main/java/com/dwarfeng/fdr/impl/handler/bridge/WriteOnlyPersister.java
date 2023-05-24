package com.dwarfeng.fdr.impl.handler.bridge;

import com.dwarfeng.fdr.stack.bean.dto.QueryInfo;
import com.dwarfeng.fdr.stack.bean.dto.QueryResult;
import com.dwarfeng.fdr.stack.exception.MethodNotSupportedException;
import com.dwarfeng.fdr.stack.structure.Data;

/**
 * 只写持久化器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public abstract class WriteOnlyPersister<D extends Data> extends AbstractPersister<D> {

    public WriteOnlyPersister() {
        super(true);
    }

    @Override
    protected QueryResult<D> doQuery(QueryInfo queryInfo) throws Exception {
        throw new MethodNotSupportedException();
    }
}
