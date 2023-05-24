package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 过滤器处理器。
 *
 * <p>
 * 该处理器用于构造过滤器。
 *
 * <p>
 * 有关过滤的详细信息，请参阅术语。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface FilterHandler extends Handler {

    /**
     * 根据指定的过滤器信息构造一个过滤器。
     *
     * @param type  过滤器类型。
     * @param param 过滤器参数。
     * @return 构造的过滤器。
     * @throws HandlerException 处理器异常。
     */
    Filter make(String type, String param) throws HandlerException;
}
