package com.dwarfeng.fdr.sdk.handler.washer;

import com.dwarfeng.fdr.stack.exception.WasherException;
import com.dwarfeng.fdr.stack.exception.WasherExecutionException;
import com.dwarfeng.fdr.stack.handler.Washer;

/**
 * 清洗器的抽象实现。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
public abstract class AbstractWasher implements Washer {

    protected Context context;

    @Override
    public void init(Context context) {
        this.context = context;
    }

    @Override
    public WashResult wash(WashInfo washInfo) throws WasherException {
        try {
            return doWash(washInfo);
        } catch (WasherException e) {
            throw e;
        } catch (Exception e) {
            throw new WasherExecutionException(e);
        }
    }

    protected WashResult doWash(WashInfo washInfo) throws Exception {
        return WashResult.of(washInfo.getValue());
    }

    @Deprecated
    @Override
    public Object wash(Object rawValue) throws WasherException {
        try {
            return doWash(rawValue);
        } catch (WasherException e) {
            throw e;
        } catch (Exception e) {
            throw new WasherExecutionException(e);
        }
    }

    @SuppressWarnings("RedundantThrows")
    @Deprecated
    protected Object doWash(Object rawValue) throws Exception {
        return rawValue;
    }
}
