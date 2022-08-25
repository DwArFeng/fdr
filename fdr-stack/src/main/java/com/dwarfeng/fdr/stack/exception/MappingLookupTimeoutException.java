package com.dwarfeng.fdr.stack.exception;

/**
 * 映射查询超时异常。
 *
 * @author DwArFeng
 * @since 1.10.0
 */
public class MappingLookupTimeoutException extends MappingLookupException {

    private static final long serialVersionUID = -1296758288474768286L;

    private final long timeout;

    public MappingLookupTimeoutException(long timeout) {
        this.timeout = timeout;
    }

    public MappingLookupTimeoutException(Throwable cause, long timeout) {
        super(cause);
        this.timeout = timeout;
    }

    @Override
    public String getMessage() {
        return "方法未响应时间超过 " + timeout + " 毫秒";
    }
}
