package com.dwarfeng.fdr.stack.exception;

/**
 * 清洗器执行异常。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class WasherExecutionException extends WasherException {

    private static final long serialVersionUID = 2853062660282403950L;

    public WasherExecutionException() {
    }

    public WasherExecutionException(String message) {
        super(message);
    }

    public WasherExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public WasherExecutionException(Throwable cause) {
        super(cause);
    }
}
