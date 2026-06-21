package com.dwarfeng.fdr.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 抓取器异常。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
public class FetcherException extends HandlerException {

    private static final long serialVersionUID = 3225825261981879363L;

    public FetcherException() {
    }

    public FetcherException(String message) {
        super(message);
    }

    public FetcherException(String message, Throwable cause) {
        super(message, cause);
    }

    public FetcherException(Throwable cause) {
        super(cause);
    }
}
