package com.dwarfeng.fdr.stack.exception;

import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 映射查询会话不存在异常。
 *
 * @author DwArFeng
 * @since 1.10.0
 */
public class MappingLookupSessionNotExistsException extends MappingLookupException {

    private static final long serialVersionUID = 2752709858754983032L;

    private final LongIdKey sessionKey;

    public MappingLookupSessionNotExistsException(LongIdKey sessionKey) {
        this.sessionKey = sessionKey;
    }

    public MappingLookupSessionNotExistsException(Throwable cause, LongIdKey sessionKey) {
        super(cause);
        this.sessionKey = sessionKey;
    }

    @Override
    public String getMessage() {
        return "会话 " + sessionKey + " 不存在";
    }
}
