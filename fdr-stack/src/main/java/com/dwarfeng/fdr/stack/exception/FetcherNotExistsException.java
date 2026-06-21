package com.dwarfeng.fdr.stack.exception;

import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 抓取器不存在异常。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
public class FetcherNotExistsException extends HandlerException {

    private static final long serialVersionUID = 4043203728880546180L;

    private final LongIdKey fetcherKey;

    public FetcherNotExistsException(LongIdKey fetcherKey) {
        this.fetcherKey = fetcherKey;
    }

    public FetcherNotExistsException(Throwable cause, LongIdKey fetcherKey) {
        super(cause);
        this.fetcherKey = fetcherKey;
    }

    @Override
    public String getMessage() {
        return "抓取器 " + fetcherKey + " 不存在";
    }
}
