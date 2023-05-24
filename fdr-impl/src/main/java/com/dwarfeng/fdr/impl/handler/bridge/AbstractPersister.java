package com.dwarfeng.fdr.impl.handler.bridge;

import com.dwarfeng.fdr.impl.handler.Bridge.Persister;
import com.dwarfeng.fdr.stack.bean.dto.QueryInfo;
import com.dwarfeng.fdr.stack.bean.dto.QueryResult;
import com.dwarfeng.fdr.stack.exception.MethodNotSupportedException;
import com.dwarfeng.fdr.stack.handler.PersistHandler.QueryManual;
import com.dwarfeng.fdr.stack.structure.Data;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

import java.util.Collections;
import java.util.List;

/**
 * 持久器的抽象实现。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public abstract class AbstractPersister<D extends Data> implements Persister<D> {

    protected final boolean writeOnly;
    protected final List<QueryManual> queryManuals;

    protected AbstractPersister(boolean writeOnly) {
        this(writeOnly, Collections.emptyList());
    }

    protected AbstractPersister(boolean writeOnly, List<QueryManual> queryManuals) {
        this.writeOnly = writeOnly;
        this.queryManuals = queryManuals;
    }

    @Override
    public boolean writeOnly() {
        return writeOnly;
    }

    @Override
    public void record(D dataRecord) throws HandlerException {
        try {
            doRecord(dataRecord);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    protected abstract void doRecord(D dataRecord) throws Exception;

    @Override
    public List<QueryManual> getQueryManuals() {
        return queryManuals;
    }

    @Override
    public void record(List<D> dataRecords) throws HandlerException {
        try {
            doRecord(dataRecords);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    protected abstract void doRecord(List<D> dataRecords) throws Exception;

    @Override
    public QueryResult<D> query(QueryInfo queryInfo) throws HandlerException {
        if (writeOnly) {
            throw new MethodNotSupportedException();
        }

        try {
            return doQuery(queryInfo);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    protected abstract QueryResult<D> doQuery(QueryInfo queryInfo) throws Exception;
}
