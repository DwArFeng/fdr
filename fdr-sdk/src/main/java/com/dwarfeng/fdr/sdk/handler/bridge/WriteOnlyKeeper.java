package com.dwarfeng.fdr.sdk.handler.bridge;

import com.dwarfeng.fdr.stack.exception.LatestNotSupportedException;
import com.dwarfeng.fdr.stack.struct.Data;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

import java.util.List;

/**
 * 只写保持器。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
public abstract class WriteOnlyKeeper<D extends Data> extends AbstractKeeper<D> {

    @Override
    public void update(D data) throws HandlerException {
        try {
            doUpdate(data);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    protected abstract void doUpdate(D data) throws Exception;

    @Override
    public void update(List<D> datas) throws HandlerException {
        try {
            doUpdate(datas);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    protected abstract void doUpdate(List<D> datas) throws Exception;

    @Override
    public D latest(LongIdKey pointKey) throws HandlerException {
        throw new LatestNotSupportedException();
    }

    @Override
    public List<D> latest(List<LongIdKey> pointKeys) throws HandlerException {
        throw new LatestNotSupportedException();
    }

    @Override
    public String toString() {
        return "WriteOnlyKeeper{}";
    }
}
