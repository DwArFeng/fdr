package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.StartableHandler;

/**
 * 重置处理器。
 *
 * @author DwArFeng
 * @since 1.11.0
 */
public interface ResetHandler extends StartableHandler {

    /**
     * 重置记录功能。
     *
     * @throws HandlerException 处理器异常。
     */
    void resetRecord() throws HandlerException;

    /**
     * 重置映射功能。
     *
     * @throws HandlerException 处理器异常。
     */
    void resetMap() throws HandlerException;
}
