package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 清洗器处理器。
 *
 * <p>
 * 该处理器用于构造清洗器。
 *
 * <p>
 * 有关清洗的详细信息，请参阅术语。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface WasherHandler extends Handler {

    /**
     * 根据指定的清洗器信息构造一个清洗器。
     *
     * @param type  清洗器类型。
     * @param param 清洗器参数。
     * @return 构造的清洗器。
     * @throws HandlerException 处理器异常。
     */
    Washer make(String type, String param) throws HandlerException;
}
