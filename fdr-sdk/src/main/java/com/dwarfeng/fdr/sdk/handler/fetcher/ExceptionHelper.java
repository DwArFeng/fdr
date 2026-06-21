package com.dwarfeng.fdr.sdk.handler.fetcher;

import com.dwarfeng.fdr.stack.exception.FetcherException;
import com.dwarfeng.fdr.stack.exception.FetcherExecutionException;
import com.dwarfeng.fdr.stack.exception.FetcherSessionException;

/**
 * 抓取器异常助手。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
final class ExceptionHelper {

    public static FetcherException parseFetcherExecutionException(Exception e) {
        if (e instanceof FetcherException) {
            return (FetcherException) e;
        } else {
            return new FetcherExecutionException(e);
        }
    }

    public static FetcherSessionException parseFetcherSessionException(Exception e) {
        if (e instanceof FetcherSessionException) {
            return (FetcherSessionException) e;
        } else {
            return new FetcherSessionException(e);
        }
    }

    private ExceptionHelper() {
        throw new IllegalStateException("禁止实例化");
    }
}
