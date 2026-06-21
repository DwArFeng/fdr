package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 抓取器会话持有处理器。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
public interface FetcherSessionHoldHandler extends Handler {

    /**
     * 获取指定的抓取器会话。
     *
     * <p>
     * 如果指定的抓取器会话不存在，则尝试基于抓取器信息进行创建；如果抓取器信息不存在，则抛出异常。
     *
     * @param fetcherInfoKey 指定的抓取器信息主键。
     * @return 指定的抓取器会话。
     * @throws HandlerException 处理器异常。
     */
    FetcherSession get(LongIdKey fetcherInfoKey) throws HandlerException;

    /**
     * 关闭并清除所有的抓取器会话。
     *
     * <p>
     * 该方法会关闭所有当前持有的抓取器会话，并清除所有的抓取器会话。
     *
     * @throws HandlerException 处理器异常。
     */
    void closeAndClear() throws HandlerException;
}
