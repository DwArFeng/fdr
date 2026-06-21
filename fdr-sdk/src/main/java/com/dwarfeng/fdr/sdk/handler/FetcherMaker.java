package com.dwarfeng.fdr.sdk.handler;

import com.dwarfeng.fdr.stack.exception.FetcherException;
import com.dwarfeng.fdr.stack.handler.Fetcher;

/**
 * 抓取器制造器。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
public interface FetcherMaker {

    /**
     * 返回制造器是否支持指定的类型。
     *
     * @param type 指定的类型。
     * @return 制造器是否支持指定的类型。
     */
    boolean supportType(String type);

    /**
     * 根据指定的抓取器信息生成一个抓取器。
     *
     * <p>
     * 可以保证传入的抓取器信息中的类型是支持的。
     *
     * @param type  抓取器类型。
     * @param param 抓取器参数。
     * @return 生成的抓取器。
     * @throws FetcherException 抓取器异常。
     */
    Fetcher makeFetcher(String type, String param) throws FetcherException;
}
