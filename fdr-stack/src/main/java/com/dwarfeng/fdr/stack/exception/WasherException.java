package com.dwarfeng.fdr.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

public class WasherException extends HandlerException {

    private static final long serialVersionUID = -1070583704828608269L;

    public WasherException() {
    }

    public WasherException(String message) {
        super(message);
    }

    public WasherException(String message, Throwable cause) {
        super(message, cause);
    }

    public WasherException(Throwable cause) {
        super(cause);
    }
}
