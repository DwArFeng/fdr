package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.bean.entity.RealtimeValue;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

import java.util.List;

/**
 * 事件推送器。
 *
 * @author DwArFeng
 * @since 1.4.0
 */
public interface Pusher {

    /**
     * 返回制造器是否支持指定的类型。
     *
     * @param type 指定的类型。
     * @return 制造器是否支持指定的类型。
     */
    boolean supportType(String type);

    /**
     * 数据被过滤时执行的广播操作。
     *
     * @param filteredValue 指定的被过滤数据值。
     * @throws HandlerException 处理器异常。
     */
    void dataFiltered(FilteredValue filteredValue) throws HandlerException;

    /**
     * 数据被过滤时执行的广播操作。
     *
     * @param filteredValues 指定的被过滤数据值组成的列表。
     * @throws HandlerException 处理器异常。
     */
    void dataFiltered(List<FilteredValue> filteredValues) throws HandlerException;

    /**
     * 数据被触发时执行的广播操作。
     *
     * @param triggeredValue 指定的数据被触发数据点。
     * @throws HandlerException 处理器异常。
     */
    void dataTriggered(TriggeredValue triggeredValue) throws HandlerException;

    /**
     * 数据被触发时执行的广播操作。
     *
     * @param triggeredValues 指定的数据被触发数据点组成的列表。
     * @throws HandlerException 处理器异常。
     */
    void dataTriggered(List<TriggeredValue> triggeredValues) throws HandlerException;

    /**
     * 实时数据更新时执行的广播操作。
     *
     * @param realtimeValue 指定的实时数据值。
     * @throws HandlerException 处理器异常。
     */
    void realtimeUpdated(RealtimeValue realtimeValue) throws HandlerException;

    /**
     * 实时数据更新时执行的广播操作。
     *
     * @param realtimeValues 指定的实时数据值组成的列表。
     * @throws HandlerException 处理器异常。
     */
    void realtimeUpdated(List<RealtimeValue> realtimeValues) throws HandlerException;

    /**
     * 持久数据记录时执行的广播操作。
     *
     * @param persistenceValue 指定的持久数据值。
     * @throws HandlerException 处理器异常。
     */
    void persistenceRecorded(PersistenceValue persistenceValue) throws HandlerException;

    /**
     * 持久数据记录时执行的广播操作。
     *
     * @param persistenceValues 指定的持久数据值组成的列表。
     * @throws HandlerException 处理器异常。
     */
    void persistenceRecorded(List<PersistenceValue> persistenceValues) throws HandlerException;

    /**
     * 记录功能重置时执行的广播操作。
     *
     * @throws HandlerException 处理器异常。
     */
    void recordReset() throws HandlerException;

    /**
     * 映射功能重置时执行的广播操作。
     *
     * @throws HandlerException 处理器异常。
     */
    void mapReset() throws HandlerException;
}
