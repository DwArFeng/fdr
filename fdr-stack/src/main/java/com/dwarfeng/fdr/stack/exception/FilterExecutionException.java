package com.dwarfeng.fdr.stack.exception;

/**
 * 过滤器执行异常。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FilterExecutionException extends FilterException {

    private static final long serialVersionUID = -4363127912509907410L;

    public FilterExecutionException() {
    }

    public FilterExecutionException(String message) {
        super(message);
    }

    public FilterExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public FilterExecutionException(Throwable cause) {
        super(cause);
    }
}
