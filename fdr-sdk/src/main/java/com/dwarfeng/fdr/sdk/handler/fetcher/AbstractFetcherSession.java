package com.dwarfeng.fdr.sdk.handler.fetcher;

import com.dwarfeng.fdr.stack.exception.FetcherSessionException;
import com.dwarfeng.fdr.stack.handler.FetcherSession;

/**
 * 抓取器会话的抽象实现。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
public abstract class AbstractFetcherSession implements FetcherSession {

    protected Context context;

    @Override
    public void init(Context context) {
        this.context = context;
    }

    @Override
    public void openSession() throws FetcherSessionException {
        try {
            doOpenSession();
        } catch (Exception e) {
            throw ExceptionHelper.parseFetcherSessionException(e);
        }
    }

    protected abstract void doOpenSession() throws Exception;

    @Override
    public void startFetch() throws FetcherSessionException {
        try {
            doStartFetch();
        } catch (Exception e) {
            throw ExceptionHelper.parseFetcherSessionException(e);
        }
    }

    protected abstract void doStartFetch() throws Exception;

    @Override
    public void stopFetch() throws FetcherSessionException {
        try {
            doStopFetch();
        } catch (Exception e) {
            throw ExceptionHelper.parseFetcherSessionException(e);
        }
    }

    protected abstract void doStopFetch() throws Exception;

    @Override
    public void closeSession() throws FetcherSessionException {
        try {
            doCloseSession();
        } catch (Exception e) {
            throw ExceptionHelper.parseFetcherSessionException(e);
        }
    }

    protected abstract void doCloseSession() throws Exception;
}
