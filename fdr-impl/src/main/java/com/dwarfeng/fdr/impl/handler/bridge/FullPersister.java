package com.dwarfeng.fdr.impl.handler.bridge;

import com.dwarfeng.fdr.stack.bean.dto.LookupInfo;
import com.dwarfeng.fdr.stack.bean.dto.LookupResult;
import com.dwarfeng.fdr.stack.bean.dto.NativeQueryInfo;
import com.dwarfeng.fdr.stack.bean.dto.QueryResult;
import com.dwarfeng.fdr.stack.struct.Data;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
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
public abstract class FullPersister<D extends Data> extends AbstractPersister<D> {

    @Override
    public void record(D data) throws HandlerException {
        try {
            doRecord(data);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    protected abstract void doRecord(D data) throws Exception;

    @Override
    public void record(List<D> datas) throws HandlerException {
        try {
            doRecord(datas);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    protected abstract void doRecord(List<D> datas) throws Exception;

    @Override
    public LookupResult<D> lookup(LookupInfo lookupInfo) throws HandlerException {
        try {
            return doLookup(lookupInfo);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    protected abstract LookupResult<D> doLookup(LookupInfo lookupInfo) throws Exception;

    @Override
    public List<LookupResult<D>> lookup(List<LookupInfo> lookupInfos) throws HandlerException {
        try {
            return doLookup(lookupInfos);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    protected abstract List<LookupResult<D>> doLookup(List<LookupInfo> lookupInfos) throws Exception;

    @Override
    public QueryResult nativeQuery(NativeQueryInfo queryInfo) throws HandlerException {
        try {
            return doNativeQuery(queryInfo);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    protected abstract QueryResult doNativeQuery(NativeQueryInfo queryInfo) throws Exception;

    @Override
    public List<QueryResult> nativeQuery(List<NativeQueryInfo> queryInfos) throws HandlerException {
        try {
            return doNativeQuery(queryInfos);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    protected abstract List<QueryResult> doNativeQuery(List<NativeQueryInfo> queryInfos) throws Exception;

    @Override
    public String toString() {
        return "FullPersister{}";
    }
}
