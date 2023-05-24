package com.dwarfeng.fdr.stack.exception;

/**
 * 触发器执行异常。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class TriggerExecutionException extends TriggerException {

    private static final long serialVersionUID = 874696085544456925L;

    public TriggerExecutionException() {
    }

    public TriggerExecutionException(String message) {
        super(message);
    }

    public TriggerExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public TriggerExecutionException(Throwable cause) {
        super(cause);
    }
}
