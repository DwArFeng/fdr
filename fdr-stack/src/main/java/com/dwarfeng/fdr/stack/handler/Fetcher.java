package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.fdr.stack.exception.FetcherException;

/**
 * 抓取器。
 *
 * <p>
 * 抓取器用于根据固定配置创建抓取器会话，自身应保持无状态。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
public interface Fetcher {

    /**
     * 新建抓取器会话。
     *
     * @return 新建生成的抓取器会话。
     * @throws FetcherException 抓取器异常。
     */
    FetcherSession newSession() throws FetcherException;
}
