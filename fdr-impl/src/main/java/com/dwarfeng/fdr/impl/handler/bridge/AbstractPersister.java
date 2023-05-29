package com.dwarfeng.fdr.impl.handler.bridge;

import com.dwarfeng.fdr.impl.handler.Bridge.Persister;
import com.dwarfeng.fdr.stack.bean.dto.QueryInfo;
import com.dwarfeng.fdr.stack.bean.dto.QueryResult;
import com.dwarfeng.fdr.stack.exception.MethodNotSupportedException;
import com.dwarfeng.fdr.stack.handler.PersistHandler;
import com.dwarfeng.fdr.stack.handler.PersistHandler.QueryGuide;
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
    protected final List<PersistHandler.QueryGuide> queryGuides;

    protected AbstractPersister(boolean writeOnly) {
        this(writeOnly, Collections.emptyList());
    }

    protected AbstractPersister(boolean writeOnly, List<PersistHandler.QueryGuide> queryGuides) {
        this.writeOnly = writeOnly;
        this.queryGuides = queryGuides;
    }

    @Override
    public boolean writeOnly() {
        return writeOnly;
    }

    @Override
    public void record(D data) throws HandlerException {
        try {
            doRecord(data);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    protected abstract void doRecord(D data) throws Exception;

    @Override
    public List<QueryGuide> queryGuides() {
        return queryGuides;
    }

    @Override
    public void record(List<D> datas) throws HandlerException {
        try {
            doRecord(datas);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    protected abstract void doRecord(List<D> datas) throws Exception;

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
