package com.dwarfeng.fdr.stack.exception;

/**
 * 映射查询会话取消异常。
 *
 * @author DwArFeng
 * @since 1.10.0
 */
public class MappingLookupSessionCanceledException extends MappingLookupException {

    private static final long serialVersionUID = -245435058508655717L;

    public MappingLookupSessionCanceledException() {
    }

    public MappingLookupSessionCanceledException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "会话已经被取消";
    }
}
