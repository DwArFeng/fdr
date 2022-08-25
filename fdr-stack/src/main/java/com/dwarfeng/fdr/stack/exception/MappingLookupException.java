package com.dwarfeng.fdr.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 映射查询异常。
 *
 * @author DwArFeng
 * @since 1.10.0
 */
public class MappingLookupException extends HandlerException {

    private static final long serialVersionUID = 7059172321719025094L;

    public MappingLookupException() {
    }

    public MappingLookupException(String message, Throwable cause) {
        super(message, cause);
    }

    public MappingLookupException(String message) {
        super(message);
    }

    public MappingLookupException(Throwable cause) {
        super(cause);
    }
}
