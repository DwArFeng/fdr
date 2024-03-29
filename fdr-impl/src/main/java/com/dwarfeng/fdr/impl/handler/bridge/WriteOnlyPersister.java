package com.dwarfeng.fdr.impl.handler.bridge;

import com.dwarfeng.fdr.stack.bean.dto.LookupInfo;
import com.dwarfeng.fdr.stack.bean.dto.LookupResult;
import com.dwarfeng.fdr.stack.bean.dto.NativeQueryInfo;
import com.dwarfeng.fdr.stack.bean.dto.QueryResult;
import com.dwarfeng.fdr.stack.exception.LookupNotSupportedException;
import com.dwarfeng.fdr.stack.exception.NativeQueryNotSupportedException;
import com.dwarfeng.fdr.stack.struct.Data;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

import java.util.List;

/**
 * 只写持久器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public abstract class WriteOnlyPersister<D extends Data> extends AbstractPersister<D> {

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
        throw new LookupNotSupportedException();
    }

    @Override
    public List<LookupResult<D>> lookup(List<LookupInfo> lookupInfos) throws HandlerException {
        throw new LookupNotSupportedException();
    }

    @Override
    public QueryResult nativeQuery(NativeQueryInfo queryInfo) throws HandlerException {
        throw new NativeQueryNotSupportedException();
    }

    @Override
    public List<QueryResult> nativeQuery(List<NativeQueryInfo> queryInfos) throws HandlerException {
        throw new NativeQueryNotSupportedException();
    }

    @Override
    public String toString() {
        return "WriteOnlyPersister{}";
    }
}
