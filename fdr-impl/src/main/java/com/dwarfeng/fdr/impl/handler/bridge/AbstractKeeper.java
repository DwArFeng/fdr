package com.dwarfeng.fdr.impl.handler.bridge;

import com.dwarfeng.fdr.impl.handler.Bridge.Keeper;
import com.dwarfeng.fdr.stack.exception.MethodNotSupportedException;
import com.dwarfeng.fdr.stack.struct.Data;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

import java.util.List;

/**
 * 保持器的抽象实现。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public abstract class AbstractKeeper<D extends Data> implements Keeper<D> {

    protected final boolean writeOnly;

    public AbstractKeeper(boolean writeOnly) {
        this.writeOnly = writeOnly;
    }

    @Override
    public boolean writeOnly() {
        return writeOnly;
    }

    @Override
    public void update(D data) throws HandlerException {
        try {
            doUpdate(data);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    protected abstract void doUpdate(D data) throws Exception;

    @Override
    public void update(List<D> datas) throws HandlerException {
        try {
            doUpdate(datas);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    protected abstract void doUpdate(List<D> datas) throws Exception;

    @Override
    public D inspect(LongIdKey pointKey) throws HandlerException {
        if (writeOnly) {
            throw new MethodNotSupportedException();
        }

        try {
            return doInspect(pointKey);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    protected abstract D doInspect(LongIdKey pointKey) throws Exception;

    @Override
    public List<D> inspect(List<LongIdKey> pointKeys) throws HandlerException {
        if (writeOnly) {
            throw new MethodNotSupportedException();
        }

        try {
            return doInspect(pointKeys);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    protected abstract List<D> doInspect(List<LongIdKey> pointKeys) throws Exception;
}
