package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.fdr.stack.bean.dto.NormalData;
import com.dwarfeng.fdr.stack.bean.dto.TriggeredData;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

import java.util.List;

/**
 * 推送器处理器。
 *
 * @author DwArFeng
 * @since 1.4.0
 */
public interface PushHandler extends Handler {

    /**
     * 一般数据更新时执行的广播操作。
     *
     * @param normalRecord 一般数据记录。
     * @throws HandlerException 处理器异常。
     */
    void normalUpdated(NormalData normalRecord) throws HandlerException;

    /**
     * 一般数据更新时执行的广播操作。
     *
     * @param normalRecords 一般数据记录组成的列表。
     * @throws HandlerException 处理器异常。
     */
    void normalUpdated(List<NormalData> normalRecords) throws HandlerException;

    /**
     * 一般数据记录时执行的广播操作。
     *
     * @param normalRecord 一般数据记录。
     * @throws HandlerException 处理器异常。
     */
    void normalRecorded(NormalData normalRecord) throws HandlerException;

    /**
     * 一般数据记录时执行的广播操作。
     *
     * @param normalRecords 一般数据记录组成的列表。
     * @throws HandlerException 处理器异常。
     */
    void normalRecorded(List<NormalData> normalRecords) throws HandlerException;

    /**
     * 被过滤数据更新时执行的广播操作。
     *
     * @param filteredRecord 被过滤数据记录。
     * @throws HandlerException 处理器异常。
     */
    void filteredUpdated(FilteredData filteredRecord) throws HandlerException;

    /**
     * 被过滤数据更新时执行的广播操作。
     *
     * @param filteredRecords 被过滤数据记录组成的列表。
     * @throws HandlerException 处理器异常。
     */
    void filteredUpdated(List<FilteredData> filteredRecords) throws HandlerException;

    /**
     * 被过滤数据记录时执行的广播操作。
     *
     * @param filteredRecord 被过滤数据记录。
     * @throws HandlerException 处理器异常。
     */
    void filteredRecorded(FilteredData filteredRecord) throws HandlerException;

    /**
     * 被过滤数据记录时执行的广播操作。
     *
     * @param filteredRecords 被过滤数据记录组成的列表。
     * @throws HandlerException 处理器异常。
     */
    void filteredRecorded(List<FilteredData> filteredRecords) throws HandlerException;

    /**
     * 被触发数据更新时执行的广播操作。
     *
     * @param triggeredRecord 被触发数据记录。
     * @throws HandlerException 处理器异常。
     */
    void triggeredUpdated(TriggeredData triggeredRecord) throws HandlerException;

    /**
     * 被触发数据更新时执行的广播操作。
     *
     * @param triggeredRecords 被触发数据记录组成的列表。
     * @throws HandlerException 处理器异常。
     */
    void triggeredUpdated(List<TriggeredData> triggeredRecords) throws HandlerException;

    /**
     * 被触发数据记录时执行的广播操作。
     *
     * @param triggeredRecord 被触发数据记录。
     * @throws HandlerException 处理器异常。
     */
    void triggeredRecorded(TriggeredData triggeredRecord) throws HandlerException;

    /**
     * 被触发数据记录时执行的广播操作。
     *
     * @param triggeredRecords 被触发数据记录组成的列表。
     * @throws HandlerException 处理器异常。
     */
    void triggeredRecorded(List<TriggeredData> triggeredRecords) throws HandlerException;

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
