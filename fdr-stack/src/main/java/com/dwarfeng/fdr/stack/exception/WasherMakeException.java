package com.dwarfeng.fdr.stack.exception;

/**
 * 清洗器构造异常。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class WasherMakeException extends WasherException {

    private static final long serialVersionUID = -525483606049152586L;

    public WasherMakeException() {
    }

    public WasherMakeException(String message) {
        super(message);
    }

    public WasherMakeException(String message, Throwable cause) {
        super(message, cause);
    }

    public WasherMakeException(Throwable cause) {
        super(cause);
    }
}
