package com.dwarfeng.fdr.stack.exception;

/**
 * 查询最新数据不支持异常。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class LatestNotSupportedException extends FunctionNotSupportedException {

    private static final long serialVersionUID = -5231189838883502595L;

    private static final String FUNCTION_NAME = "latest";

    public LatestNotSupportedException() {
        super(FUNCTION_NAME);
    }

    public LatestNotSupportedException(Throwable cause) {
        super(cause, FUNCTION_NAME);
    }
}
