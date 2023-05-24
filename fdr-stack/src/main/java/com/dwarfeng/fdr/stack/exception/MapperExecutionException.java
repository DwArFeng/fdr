package com.dwarfeng.fdr.stack.exception;

/**
 * 映射器执行异常。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class MapperExecutionException extends MapperException {

    private static final long serialVersionUID = -4973345041854946380L;

    public MapperExecutionException() {
    }

    public MapperExecutionException(String message) {
        super(message);
    }

    public MapperExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public MapperExecutionException(Throwable cause) {
        super(cause);
    }
}
