package com.dwarfeng.fdr.impl.handler.bridge;

import com.dwarfeng.fdr.stack.struct.Data;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

import java.util.List;

/**
 * 完整保持器。
 *
 * <p>
 * 完整保持器是指同时支持写入和查询的保持器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public abstract class FullKeeper<D extends Data> extends AbstractKeeper<D> {

    @Override
    public boolean writeOnly() {
        return false;
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
    public D latest(LongIdKey pointKey) throws HandlerException {
        try {
            return doLatest(pointKey);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    protected abstract D doLatest(LongIdKey pointKey) throws Exception;

    @Override
    public List<D> latest(List<LongIdKey> pointKeys) throws HandlerException {
        try {
            return doLatest(pointKeys);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    protected abstract List<D> doLatest(List<LongIdKey> pointKeys) throws Exception;

    @Override
    public String toString() {
        return "FulltKeeper{}";
    }
}
