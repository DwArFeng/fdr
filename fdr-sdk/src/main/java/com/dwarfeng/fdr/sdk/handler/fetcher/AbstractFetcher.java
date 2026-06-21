package com.dwarfeng.fdr.sdk.handler.fetcher;

import com.dwarfeng.fdr.stack.exception.FetcherException;
import com.dwarfeng.fdr.stack.handler.Fetcher;
import com.dwarfeng.fdr.stack.handler.FetcherSession;

/**
 * 抓取器的抽象实现。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
public abstract class AbstractFetcher implements Fetcher {

    @Override
    public FetcherSession newSession() throws FetcherException {
        try {
            return doNewSession();
        } catch (Exception e) {
            throw ExceptionHelper.parseFetcherExecutionException(e);
        }
    }

    /**
     * 新建抓取器会话。
     *
     * @return 新建生成的抓取器会话。
     * @throws Exception 任何可能的异常。
     * @see #newSession()
     */
    protected abstract FetcherSession doNewSession() throws Exception;

    @Override
    public String toString() {
        return "AbstractFetcher{}";
    }
}
