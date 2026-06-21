package com.dwarfeng.fdr.stack.exception;

/**
 * 不支持的抓取器类型。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
public class UnsupportedFetcherTypeException extends FetcherException {

    private static final long serialVersionUID = -5699436350850286131L;

    private final String type;

    public UnsupportedFetcherTypeException(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String getMessage() {
        return "不支持的抓取器类型: " + type;
    }
}
