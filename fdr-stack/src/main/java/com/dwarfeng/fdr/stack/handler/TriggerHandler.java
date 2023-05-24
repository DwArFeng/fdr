package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 触发器处理器。
 *
 * <p>
 * 该处理器用于构造触发器。
 *
 * <p>
 * 有关触发器的详细信息，请参阅术语。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface TriggerHandler extends Handler {

    /**
     * 根据指定的触发器信息构造一个触发器。
     *
     * @param type  触发器类型。
     * @param param 触发器参数。
     * @return 构造的触发器。
     * @throws HandlerException 处理器异常。
     */
    Trigger make(String type, String param) throws HandlerException;
}
