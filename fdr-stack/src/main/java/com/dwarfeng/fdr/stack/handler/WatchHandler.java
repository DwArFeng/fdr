package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.fdr.stack.bean.dto.LookupInfo;
import com.dwarfeng.fdr.stack.bean.dto.LookupResult;
import com.dwarfeng.fdr.stack.bean.dto.QueryInfo;
import com.dwarfeng.fdr.stack.bean.dto.QueryResult;
import com.dwarfeng.fdr.stack.struct.Data;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 观察处理器。
 *
 * <p>
 * 该处理器包含了数据的检索、查询和查看方法，同时提供了这些方法的异步版本。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface WatchHandler<D extends Data> extends Handler {

    /**
     * 查询数据。
     *
     * <p>
     * 如果数据点主键对应的数据不存在，则返回的查询结果为 null。
     *
     * @param pointKey 数据点主键。
     * @return 查询结果。
     * @throws HandlerException 处理器异常。
     */
    D inspect(LongIdKey pointKey) throws HandlerException;

    /**
     * 查询数据。
     *
     * <p>
     * 如果数据点主键组成的列表中的某个索引处的数据点主键对应的数据不存在，
     * 则返回的查询结果组成的列表该处索引对应的查询结果为 null。
     *
     * @param pointKeys 数据点主键组成的列表。
     * @return 查询结果。
     * @throws HandlerException 处理器异常。
     */
    List<D> inspect(List<LongIdKey> pointKeys) throws HandlerException;

    /**
     * 查询。
     *
     * @param queryInfo 查询信息。
     * @return 查询结果。
     * @throws HandlerException 处理器异常。
     */
    QueryResult<D> query(QueryInfo queryInfo) throws HandlerException;

    /**
     * 查看。
     *
     * @param lookupInfo 查看信息。
     * @return 查看结果。
     * @throws HandlerException 处理器异常。
     */
    LookupResult lookup(LookupInfo lookupInfo) throws HandlerException;

    /**
     * 异步查询数据。
     *
     * @param pointKey 数据点主键。
     * @return 查询结果。
     * @throws HandlerException 处理器异常。
     */
    CompletableFuture<D> inspectAsync(LongIdKey pointKey) throws HandlerException;

    /**
     * 异步查询数据。
     *
     * @param pointKeys 数据点主键组成的列表。
     * @return 查询结果。
     * @throws HandlerException 处理器异常。
     */
    CompletableFuture<List<D>> inspectAsync(List<LongIdKey> pointKeys) throws HandlerException;

    /**
     * 异步查询。
     *
     * @param queryInfo 查询信息。
     * @return 查询结果。
     * @throws HandlerException 处理器异常。
     */
    CompletableFuture<QueryResult<D>> queryAsync(QueryInfo queryInfo) throws HandlerException;

    /**
     * 异步查看。
     *
     * @param lookupInfo 查看信息。
     * @return 查看结果。
     * @throws HandlerException 处理器异常。
     */
    CompletableFuture<LookupResult> lookupAsync(LookupInfo lookupInfo) throws HandlerException;
}
