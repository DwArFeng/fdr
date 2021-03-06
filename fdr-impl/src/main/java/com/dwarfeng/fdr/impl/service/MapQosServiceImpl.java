package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.handler.MapLocalCacheHandler;
import com.dwarfeng.fdr.stack.handler.Mapper;
import com.dwarfeng.fdr.stack.service.MapQosService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class MapQosServiceImpl implements MapQosService {

    @Autowired
    MapLocalCacheHandler mapLocalCacheHandler;

    @Autowired
    private ServiceExceptionMapper sem;

    private final Lock lock = new ReentrantLock();

    @Override
    @BehaviorAnalyse
    public Mapper getMapper(String mapperType) throws ServiceException {
        lock.lock();
        try {
            return mapLocalCacheHandler.getMapper(mapperType);
        } catch (HandlerException e) {
            throw ServiceExceptionHelper.logAndThrow("从本地缓存中获取映射器时发生异常",
                    LogLevel.WARN, sem, e
            );
        } finally {
            lock.unlock();
        }
    }

    @Override
    @BehaviorAnalyse
    public void clearLocalCache() throws ServiceException {
        lock.lock();
        try {
            mapLocalCacheHandler.clear();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("清除本地缓存时发生异常",
                    LogLevel.WARN, sem, e
            );
        } finally {
            lock.unlock();
        }
    }
}
