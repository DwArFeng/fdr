package com.dwarfeng.fdr.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 方法不支持异常。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class MethodNotSupportedException extends HandlerException {

    private static final long serialVersionUID = 6340110814533782853L;

    public MethodNotSupportedException() {
    }

    public MethodNotSupportedException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "方法不支持";
    }
}
