package com.dwarfeng.fdr.stack.exception;

/**
 * 查看不支持异常。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class LookupNotSupportedException extends FunctionNotSupportedException {

    private static final long serialVersionUID = 427531244928731612L;
    
    private static final String FUNCTION_NAME = "lookup";

    public LookupNotSupportedException() {
        super(FUNCTION_NAME);
    }

    public LookupNotSupportedException(Throwable cause) {
        super(cause, FUNCTION_NAME);
    }
}
