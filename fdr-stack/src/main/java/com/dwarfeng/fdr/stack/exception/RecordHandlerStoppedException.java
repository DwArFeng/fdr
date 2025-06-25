package com.dwarfeng.fdr.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 记录处理器停止异常。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
public class RecordHandlerStoppedException extends HandlerException {

    private static final long serialVersionUID = -3042839646744800153L;

    public RecordHandlerStoppedException() {
    }

    public RecordHandlerStoppedException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "记录处理器处于停止状态";
    }
}
