package com.dwarfeng.fdr.stack.exception;

/**
 * 抓取器会话异常。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
public class FetcherSessionException extends FetcherException {

    private static final long serialVersionUID = -7271425465342284851L;

    public FetcherSessionException() {
    }

    public FetcherSessionException(String message) {
        super(message);
    }

    public FetcherSessionException(String message, Throwable cause) {
        super(message, cause);
    }

    public FetcherSessionException(Throwable cause) {
        super(cause);
    }
}
