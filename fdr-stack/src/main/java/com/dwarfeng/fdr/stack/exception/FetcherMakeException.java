package com.dwarfeng.fdr.stack.exception;

/**
 * 抓取器构造异常。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
public class FetcherMakeException extends FetcherException {

    private static final long serialVersionUID = -8495948734375227712L;

    public FetcherMakeException() {
    }

    public FetcherMakeException(String message) {
        super(message);
    }

    public FetcherMakeException(String message, Throwable cause) {
        super(message, cause);
    }

    public FetcherMakeException(Throwable cause) {
        super(cause);
    }
}
