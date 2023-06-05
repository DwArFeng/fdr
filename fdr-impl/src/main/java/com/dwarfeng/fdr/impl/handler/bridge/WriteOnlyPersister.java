package com.dwarfeng.fdr.impl.handler.bridge;

import com.dwarfeng.fdr.impl.handler.Bridge.Persister;
import com.dwarfeng.fdr.stack.bean.dto.LookupInfo;
import com.dwarfeng.fdr.stack.bean.dto.LookupResult;
import com.dwarfeng.fdr.stack.exception.LookupNotSupportedException;
import com.dwarfeng.fdr.stack.handler.PersistHandler;
import com.dwarfeng.fdr.stack.struct.Data;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

import java.util.Collections;
import java.util.List;

/**
 * 只写持久化器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public abstract class WriteOnlyPersister<D extends Data> implements Persister<D> {

    @Override
    public boolean writeOnly() {
        return true;
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
    public List<PersistHandler.LookupGuide> lookupGuides() {
        return Collections.emptyList();
    }

    @Override
    public LookupResult<D> lookup(LookupInfo lookupInfo) throws HandlerException {
        throw new LookupNotSupportedException();
    }

    @Override
    public List<LookupResult<D>> lookup(List<LookupInfo> lookupInfos) throws HandlerException {
        throw new LookupNotSupportedException();
    }
}
