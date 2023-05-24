package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.dto.LookupInfo;
import com.dwarfeng.fdr.stack.bean.dto.LookupResult;
import com.dwarfeng.fdr.stack.bean.dto.QueryInfo;
import com.dwarfeng.fdr.stack.bean.dto.QueryResult;
import com.dwarfeng.fdr.stack.handler.WatchHandler;
import com.dwarfeng.fdr.stack.service.WatchQosService;
import com.dwarfeng.fdr.stack.structure.Data;
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
public abstract class AbstractWatchQosService<D extends Data> implements WatchQosService<D> {

    protected final WatchHandler<D> watchHandler;

    protected final ServiceExceptionMapper sem;

    protected AbstractWatchQosService(WatchHandler<D> watchHandler, ServiceExceptionMapper sem) {
        this.watchHandler = watchHandler;
        this.sem = sem;
    }

    @Override
    public List<D> inspect(List<LongIdKey> pointKeys) throws ServiceException {
        try {
            return watchHandler.inspect(pointKeys);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询数据时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    public QueryResult<D> query(QueryInfo queryInfo) throws ServiceException {
        try {
            return watchHandler.query(queryInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查询数据时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    public LookupResult lookup(LookupInfo lookupInfo) throws ServiceException {
        try {
            return watchHandler.lookup(lookupInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("查看数据时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    public String toString() {
        return "AbstractWatchQosService{" +
                "watchHandler=" + watchHandler +
                ", sem=" + sem +
                '}';
    }
}
