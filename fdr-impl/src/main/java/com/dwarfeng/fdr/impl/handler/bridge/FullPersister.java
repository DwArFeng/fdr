package com.dwarfeng.fdr.impl.handler.bridge;

import com.dwarfeng.fdr.impl.handler.Bridge.Persister;
import com.dwarfeng.fdr.stack.bean.dto.LookupInfo;
import com.dwarfeng.fdr.stack.bean.dto.LookupResult;
import com.dwarfeng.fdr.stack.struct.Data;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

import java.util.List;

/**
 * 完整持久器。
 *
 * <p>
 * 完整持久器是指同时支持写入和查询的持久器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public abstract class FullPersister<D extends Data> implements Persister<D> {

    @Override
    public boolean writeOnly() {
        return false;
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
    public LookupResult<D> lookup(LookupInfo lookupInfo) throws HandlerException {
        try {
            return doQuery(lookupInfo);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    protected abstract LookupResult<D> doQuery(LookupInfo lookupInfo) throws Exception;

    @Override
    public List<LookupResult<D>> lookup(List<LookupInfo> lookupInfos) throws HandlerException {
        try {
            return doQuery(lookupInfos);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    protected abstract List<LookupResult<D>> doQuery(List<LookupInfo> lookupInfos) throws Exception;

    @Override
    public String toString() {
        return "FullPersister{}";
    }
}
