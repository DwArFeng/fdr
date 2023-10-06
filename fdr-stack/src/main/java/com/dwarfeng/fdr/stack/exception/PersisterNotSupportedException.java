package com.dwarfeng.fdr.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 持久器不支持异常。
 *
 * @author DwArFeng
 * @since 2.1.0
 */
public class PersisterNotSupportedException extends HandlerException {

    private static final long serialVersionUID = 4368070072860682928L;

    public PersisterNotSupportedException() {
    }

    public PersisterNotSupportedException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "持久器不支持";
    }
}
