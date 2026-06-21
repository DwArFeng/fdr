package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 抓取器构造处理器。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
public interface FetcherMakeHandler extends Handler {

    /**
     * 根据指定的类型和参数生成抓取器。
     *
     * @param type  抓取器类型。
     * @param param 抓取器参数。
     * @return 生成的抓取器。
     * @throws HandlerException 处理器异常。
     */
    Fetcher make(String type, String param) throws HandlerException;
}
