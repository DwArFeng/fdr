package com.dwarfeng.fdr.stack.exception;

/**
 * 不支持的清洗器类型。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class UnsupportedWasherTypeException extends WasherException {

    private static final long serialVersionUID = 3080944834699564429L;

    private final String type;

    public UnsupportedWasherTypeException(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String getMessage() {
        return "不支持的清洗器类型: " + type;
    }
}
