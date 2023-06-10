package com.dwarfeng.fdr.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 持久异常。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class PersistException extends HandlerException {

    private static final long serialVersionUID = 2212780384695660558L;

    public PersistException() {
    }

    public PersistException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "持久异常";
    }
}
