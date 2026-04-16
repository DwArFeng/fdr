package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.fdr.stack.struct.RecordMemory;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

import java.util.List;

/**
 * 记录记忆处理器。
 *
 * <p>
 * 该处理器用于管理点位级记录记忆队列，提供追加、查询、清理能力。
 *
 * @author DwArFeng
 * @since 2.5.0
 */
public interface RecordMemoryHandler extends Handler {

    /**
     * 追加记录记忆。
     *
     * <p>
     * 记录记忆按时间从新到旧维护。追加后，最新的记录记忆位于索引 <code>0</code>。
     *
     * @param recordMemory 记录记忆。
     * @param maxSize      队列最大长度。
     * @throws HandlerException 处理器异常。
     */
    void append(RecordMemory recordMemory, int maxSize) throws HandlerException;

    /**
     * 查询指定点位的记录记忆。
     *
     * <p>
     * 返回列表中的记录记忆按时间从新到旧排列，索引 <code>0</code> 对应最新的记录记忆。
     *
     * <p>
     * 调用者有义务仅对返回结果进行查看操作，不应对其进行任何修改。
     *
     * @param pointKey 点位主键。
     * @return 指定点位的记录记忆。
     * @throws HandlerException 处理器异常。
     */
    List<RecordMemory> lookup(LongIdKey pointKey) throws HandlerException;

    /**
     * 移除指定的点位对应的记录记忆。
     *
     * @param pointKey 点位主键。
     * @throws HandlerException 处理器异常。
     */
    void remove(LongIdKey pointKey) throws HandlerException;

    /**
     * 清除记录记忆。
     *
     * @throws HandlerException 处理器异常。
     */
    void clear() throws HandlerException;
}
