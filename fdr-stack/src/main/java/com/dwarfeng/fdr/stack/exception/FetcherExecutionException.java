package com.dwarfeng.fdr.stack.exception;

/**
 * 抓取器执行异常。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
public class FetcherExecutionException extends FetcherException {

    private static final long serialVersionUID = 8289118489074527957L;

    public FetcherExecutionException() {
    }

    public FetcherExecutionException(String message) {
        super(message);
    }

    public FetcherExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public FetcherExecutionException(Throwable cause) {
        super(cause);
    }
}
