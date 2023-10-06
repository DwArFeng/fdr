package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.dto.*;
import com.dwarfeng.fdr.stack.handler.ViewHandler;
import com.dwarfeng.fdr.stack.service.ViewQosService;
import com.dwarfeng.fdr.stack.struct.Data;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;

import java.util.List;

/**
 * 观察服务的抽象实现。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public abstract class AbstractViewQosService<D extends Data> implements ViewQosService<D> {

    protected final ViewHandler<D> viewHandler;

    protected final ServiceExceptionMapper sem;

    protected AbstractViewQosService(
            ViewHandler<D> viewHandler,
            ServiceExceptionMapper sem
    ) {
        this.viewHandler = viewHandler;
        this.sem = sem;
    }

    @Override
    public List<D> latest(List<LongIdKey> pointKeys) throws ServiceException {
        try {
            return viewHandler.latest(pointKeys);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查询数据时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public QueryResult lookup(QueryInfo queryInfo) throws ServiceException {
        try {
            return viewHandler.query(queryInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查看数据时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public QueryResult nativeQuery(NativeQueryInfo queryInfo) throws ServiceException {
        try {
            return viewHandler.nativeQuery(queryInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("原生查询数据时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public LookupResult<D> query(LookupInfo lookupInfo) throws ServiceException {
        try {
            return viewHandler.lookup(lookupInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查询数据时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public String toString() {
        return "AbstractViewQosService{" +
                "viewHandler=" + viewHandler +
                ", sem=" + sem +
                '}';
    }
}
