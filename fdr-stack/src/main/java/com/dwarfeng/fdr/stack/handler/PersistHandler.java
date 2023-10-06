package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.fdr.stack.bean.dto.LookupInfo;
import com.dwarfeng.fdr.stack.bean.dto.LookupResult;
import com.dwarfeng.fdr.stack.bean.dto.NativeQueryInfo;
import com.dwarfeng.fdr.stack.bean.dto.QueryResult;
import com.dwarfeng.fdr.stack.exception.LookupNotSupportedException;
import com.dwarfeng.fdr.stack.struct.Data;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

import java.util.List;

/**
 * 持久处理器。
 *
 * <p>
 * 该处理器用于持久数据，实现类似于历史数据的查询与更新。
 *
 * <p>
 * 持久处理器为每个数据点维护一系列历史数据，这些数据可以被查询。同时，新的历史数据可以被记录。
 *
 * <p>
 * 部分持久处理器可能只支持写入，不支持查询。<br>
 * 对于只写的持久处理器，其 {@link #lookup(LookupInfo)} 方法以及 {@link #lookup(List)} 方法应该抛出
 * {@link LookupNotSupportedException} 异常。
 *
 * <p>
 * 有关持久的详细信息，请参阅术语。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface PersistHandler<D extends Data> extends Handler {

    /**
     * 记录数据。
     *
     * @param data 数据。
     * @throws HandlerException 处理器异常。
     */
    void record(D data) throws HandlerException;

    /**
     * 记录数据。
     *
     * @param datas 数据组成的列表。
     * @throws HandlerException 处理器异常。
     */
    void record(List<D> datas) throws HandlerException;

    /**
     * 查看。
     *
     * @param lookupInfo 查看信息。
     * @return 查看结果。
     * @throws HandlerException 处理器异常。
     */
    LookupResult<D> lookup(LookupInfo lookupInfo) throws HandlerException;

    /**
     * 查看。
     *
     * @param lookupInfos 查看信息组成的列表。
     * @return 查看结果组成的列表。
     * @throws HandlerException 处理器异常。
     */
    List<LookupResult<D>> lookup(List<LookupInfo> lookupInfos) throws HandlerException;

    /**
     * 原生查询。
     *
     * @param queryInfo 原生查询信息。
     * @return 查询结果。
     * @throws HandlerException 处理器异常。
     */
    QueryResult nativeQuery(NativeQueryInfo queryInfo) throws HandlerException;

    /**
     * 原生查询。
     *
     * @param queryInfos 原生查询信息组成的列表。
     * @return 查询结果组成的列表。
     * @throws HandlerException 处理器异常。
     */
    List<QueryResult> nativeQuery(List<NativeQueryInfo> queryInfos) throws HandlerException;
}
