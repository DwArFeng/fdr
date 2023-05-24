package com.dwarfeng.fdr.stack.exception;

import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 数据点不存在异常。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class PointNotExistsException extends HandlerException {

    private static final long serialVersionUID = -5619410067543153933L;

    private final LongIdKey pointKey;

    public PointNotExistsException(LongIdKey pointKey) {
        this.pointKey = pointKey;
    }

    public PointNotExistsException(Throwable cause, LongIdKey pointKey) {
        super(cause);
        this.pointKey = pointKey;
    }

    @Override
    public String getMessage() {
        return "数据点不存在: " + pointKey;
    }
}
