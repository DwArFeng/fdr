package com.dwarfeng.fdr.stack.exception;

/**
 * 查询不支持异常。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class QueryNotSupportedException extends FunctionNotSupportedException {

    private static final long serialVersionUID = -2049413140139769833L;

    private static final String FUNCTION_NAME = "query";

    public QueryNotSupportedException() {
        super(FUNCTION_NAME);
    }

    public QueryNotSupportedException(Throwable cause) {
        super(cause, FUNCTION_NAME);
    }
}
