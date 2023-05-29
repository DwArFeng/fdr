package com.dwarfeng.fdr.impl.handler.bridge.hibernate.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

import javax.annotation.Nonnull;

/**
 * 不支持的类型异常。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class TypeNotSupportException extends HandlerException {

    private static final long serialVersionUID = -8145499844167295888L;

    private final Class<?> type;

    public TypeNotSupportException(@Nonnull Class<?> type) {
        this.type = type;
    }

    public TypeNotSupportException(Throwable cause, @Nonnull Class<?> type) {
        super(cause);
        this.type = type;
    }

    @Override
    public String getMessage() {
        return "不支持的类型: " + type.getCanonicalName();
    }
}
