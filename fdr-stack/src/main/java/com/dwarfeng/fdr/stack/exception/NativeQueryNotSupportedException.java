package com.dwarfeng.fdr.stack.exception;

/**
 * 原生查询不支持异常。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class NativeQueryNotSupportedException extends FunctionNotSupportedException {

    private static final long serialVersionUID = -8563898595112479819L;

    private static final String FUNCTION_NAME = "native_query";

    public NativeQueryNotSupportedException() {
        super(FUNCTION_NAME);
    }

    public NativeQueryNotSupportedException(Throwable cause) {
        super(cause, FUNCTION_NAME);
    }
}
