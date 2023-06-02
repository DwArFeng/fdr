package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.struct.Data;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

import java.util.List;

/**
 * 消费者。
 *
 * @author DwArFeng
 * @since 1.4.0
 */
public interface Consumer<D extends Data> {

    /**
     * 消费指定的数据。
     *
     * @param datas 指定的数据组成的列表。
     * @throws HandlerException 处理器异常。
     */
    void consume(List<D> datas) throws HandlerException;
}
