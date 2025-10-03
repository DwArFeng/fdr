package com.dwarfeng.fdr.sdk.handler;

import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.fdr.stack.bean.dto.NormalData;
import com.dwarfeng.fdr.stack.bean.dto.TriggeredData;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

import java.util.List;

/**
 * 事件推送器。
 *
 * @author DwArFeng
 * @since 2.3.0
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
     * 一般数据更新时执行的广播操作。
     *
     * @param normalData 一般数据。
     * @throws HandlerException 处理器异常。
     */
    void normalUpdated(NormalData normalData) throws HandlerException;

    /**
     * 一般数据更新时执行的广播操作。
     *
     * @param normalDatas 一般数据组成的列表。
     * @throws HandlerException 处理器异常。
     */
    void normalUpdated(List<NormalData> normalDatas) throws HandlerException;

    /**
     * 一般数据记录时执行的广播操作。
     *
     * @param normalData 一般数据。
     * @throws HandlerException 处理器异常。
     */
    void normalRecorded(NormalData normalData) throws HandlerException;

    /**
     * 一般数据记录时执行的广播操作。
     *
     * @param normalDatas 一般数据组成的列表。
     * @throws HandlerException 处理器异常。
     */
    void normalRecorded(List<NormalData> normalDatas) throws HandlerException;

    /**
     * 被过滤数据更新时执行的广播操作。
     *
     * @param filteredData 被过滤数据。
     * @throws HandlerException 处理器异常。
     */
    void filteredUpdated(FilteredData filteredData) throws HandlerException;

    /**
     * 被过滤数据更新时执行的广播操作。
     *
     * @param filteredDatas 被过滤数据组成的列表。
     * @throws HandlerException 处理器异常。
     */
    void filteredUpdated(List<FilteredData> filteredDatas) throws HandlerException;

    /**
     * 被过滤数据记录时执行的广播操作。
     *
     * @param filteredData 被过滤数据。
     * @throws HandlerException 处理器异常。
     */
    void filteredRecorded(FilteredData filteredData) throws HandlerException;

    /**
     * 被过滤数据记录时执行的广播操作。
     *
     * @param filteredDatas 被过滤数据组成的列表。
     * @throws HandlerException 处理器异常。
     */
    void filteredRecorded(List<FilteredData> filteredDatas) throws HandlerException;

    /**
     * 被触发数据更新时执行的广播操作。
     *
     * @param triggeredData 被触发数据。
     * @throws HandlerException 处理器异常。
     */
    void triggeredUpdated(TriggeredData triggeredData) throws HandlerException;

    /**
     * 被触发数据更新时执行的广播操作。
     *
     * @param triggeredDatas 被触发数据组成的列表。
     * @throws HandlerException 处理器异常。
     */
    void triggeredUpdated(List<TriggeredData> triggeredDatas) throws HandlerException;

    /**
     * 被触发数据记录时执行的广播操作。
     *
     * @param triggeredData 被触发数据。
     * @throws HandlerException 处理器异常。
     */
    void triggeredRecorded(TriggeredData triggeredData) throws HandlerException;

    /**
     * 被触发数据记录时执行的广播操作。
     *
     * @param triggeredDatas 被触发数据组成的列表。
     * @throws HandlerException 处理器异常。
     */
    void triggeredRecorded(List<TriggeredData> triggeredDatas) throws HandlerException;

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
