package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.fdr.stack.bean.dto.*;
import com.dwarfeng.fdr.stack.struct.Data;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

import java.util.List;

/**
 * 观察 QOS 服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface ViewQosService<D extends Data> extends Service {

    /**
     * 查询数据点的最新数据。
     *
     * <p>
     * 如果数据点主键组成的列表中的某个索引处的数据点主键对应的数据不存在，
     * 则返回的查询结果组成的列表该处索引对应的查询结果为 null。
     *
     * @param pointKeys 指定的数据点对应的主键组成的列表。
     * @return 指定的数据点的最新数据组成的列表。
     * @throws ServiceException 服务异常。
     */
    List<D> latest(List<LongIdKey> pointKeys) throws ServiceException;

    /**
     * 查看。
     *
     * @param queryInfo 查看信息。
     * @return 查看结果。
     * @throws ServiceException 服务异常。
     */
    QueryResult lookup(QueryInfo queryInfo) throws ServiceException;

    /**
     * 原生查询。
     *
     * @param queryInfo 原生查询信息。
     * @return 查询结果。
     * @throws ServiceException 服务异常。
     */
    QueryResult nativeQuery(NativeQueryInfo queryInfo) throws ServiceException;

    /**
     * 查询。
     *
     * @param lookupInfo 查询信息。
     * @return 查询结果。
     * @throws ServiceException 服务异常。
     */
    LookupResult<D> query(LookupInfo lookupInfo) throws ServiceException;
}
