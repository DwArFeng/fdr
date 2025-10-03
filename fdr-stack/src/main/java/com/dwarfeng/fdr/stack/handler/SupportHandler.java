package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 支持处理器。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
public interface SupportHandler extends Handler {

    /**
     * 重置过滤器。
     *
     * @throws HandlerException 处理器异常。
     */
    void resetFilter() throws HandlerException;

    /**
     * 重置清洗器。
     *
     * @throws HandlerException 处理器异常。
     */
    void resetWasher() throws HandlerException;

    /**
     * 重置触发器。
     *
     * @throws HandlerException 处理器异常。
     */
    void resetTrigger() throws HandlerException;

    /**
     * 重置映射器。
     *
     * @throws HandlerException 处理器异常。
     */
    void resetMapper() throws HandlerException;
}
