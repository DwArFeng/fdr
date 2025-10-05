package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.fdr.stack.bean.dto.NormalData;
import com.dwarfeng.fdr.stack.bean.dto.TriggeredData;
import com.dwarfeng.fdr.stack.handler.ConsumeHandler;
import com.dwarfeng.fdr.stack.handler.RecordHandler;
import com.dwarfeng.fdr.stack.handler.RecordLocalCacheHandler;
import com.dwarfeng.fdr.stack.service.RecordQosService;
import com.dwarfeng.fdr.stack.struct.RecordLocalCache;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.Bean;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

@Service
public class RecordQosServiceImpl implements RecordQosService {

    private final RecordLocalCacheHandler recordLocalCacheHandler;
    private final ConsumeHandler<NormalData> normalKeepConsumeHandler;
    private final ConsumeHandler<NormalData> normalPersistConsumeHandler;
    private final ConsumeHandler<FilteredData> filteredKeepConsumeHandler;
    private final ConsumeHandler<FilteredData> filteredPersistConsumeHandler;
    private final ConsumeHandler<TriggeredData> triggeredKeepConsumeHandler;
    private final ConsumeHandler<TriggeredData> triggeredPersistConsumeHandler;
    private final RecordHandler recordHandler;

    private final ServiceExceptionMapper sem;

    private final Map<ConsumerId, ConsumeHandler<? extends Bean>> consumeHandlerMap = new EnumMap<>(ConsumerId.class);

    public RecordQosServiceImpl(
            RecordLocalCacheHandler recordLocalCacheHandler,
            @Qualifier("normalKeepConsumeHandler")
            ConsumeHandler<NormalData> normalKeepConsumeHandler,
            @Qualifier("normalPersistConsumeHandler")
            ConsumeHandler<NormalData> normalPersistConsumeHandler,
            @Qualifier("filteredKeepConsumeHandler")
            ConsumeHandler<FilteredData> filteredKeepConsumeHandler,
            @Qualifier("filteredPersistConsumeHandler")
            ConsumeHandler<FilteredData> filteredPersistConsumeHandler,
            @Qualifier("triggeredKeepConsumeHandler")
            ConsumeHandler<TriggeredData> triggeredKeepConsumeHandler,
            @Qualifier("triggeredPersistConsumeHandler")
            ConsumeHandler<TriggeredData> triggeredPersistConsumeHandler,
            RecordHandler recordHandler,
            ServiceExceptionMapper sem
    ) {
        this.recordLocalCacheHandler = recordLocalCacheHandler;
        this.normalKeepConsumeHandler = normalKeepConsumeHandler;
        this.normalPersistConsumeHandler = normalPersistConsumeHandler;
        this.filteredKeepConsumeHandler = filteredKeepConsumeHandler;
        this.filteredPersistConsumeHandler = filteredPersistConsumeHandler;
        this.triggeredKeepConsumeHandler = triggeredKeepConsumeHandler;
        this.triggeredPersistConsumeHandler = triggeredPersistConsumeHandler;
        this.recordHandler = recordHandler;
        this.sem = sem;
    }

    @PostConstruct
    public void init() {
        consumeHandlerMap.put(ConsumerId.KEEP_NORMAL, normalKeepConsumeHandler);
        consumeHandlerMap.put(ConsumerId.PERSIST_NORMAL, normalPersistConsumeHandler);
        consumeHandlerMap.put(ConsumerId.KEEP_FILTERED, filteredKeepConsumeHandler);
        consumeHandlerMap.put(ConsumerId.PERSIST_FILTERED, filteredPersistConsumeHandler);
        consumeHandlerMap.put(ConsumerId.KEEP_TRIGGERED, triggeredKeepConsumeHandler);
        consumeHandlerMap.put(ConsumerId.PERSIST_TRIGGERED, triggeredPersistConsumeHandler);
    }

    @PreDestroy
    public void dispose() throws Exception {
        recordHandler.stop();
    }

    @Override
    @BehaviorAnalyse
    public RecordLocalCache getRecordLocalCache(LongIdKey pointKey) throws ServiceException {
        try {
            return recordLocalCacheHandler.get(pointKey);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("从本地缓存中获取记录本地缓存时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    @BehaviorAnalyse
    public void clearLocalCache() throws ServiceException {
        try {
            recordLocalCacheHandler.clear();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("清除本地缓存时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    @BehaviorAnalyse
    public ConsumerStatus getConsumerStatus(ConsumerId consumerId) throws ServiceException {
        try {
            ConsumeHandler<? extends Bean> consumeHandler = consumeHandlerMap.get(consumerId);
            return new ConsumerStatus(
                    consumeHandler.bufferedSize(),
                    consumeHandler.getBufferSize(),
                    consumeHandler.getBatchSize(),
                    consumeHandler.getMaxIdleTime(),
                    consumeHandler.getThread(),
                    consumeHandler.isIdle()
            );
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("获取消费者状态时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    @BehaviorAnalyse
    public void setConsumerParameters(
            ConsumerId consumerId, Integer bufferSize, Integer batchSize, Long maxIdleTime, Integer thread
    ) throws ServiceException {
        try {
            ConsumeHandler<? extends Bean> consumeHandler = consumeHandlerMap.get(consumerId);
            consumeHandler.setBufferParameters(
                    Objects.isNull(bufferSize) ? consumeHandler.getBufferSize() : bufferSize,
                    Objects.isNull(batchSize) ? consumeHandler.getBatchSize() : batchSize,
                    Objects.isNull(maxIdleTime) ? consumeHandler.getMaxIdleTime() : maxIdleTime
            );
            consumeHandler.setThread(
                    Objects.isNull(thread) ? consumeHandler.getThread() : thread
            );
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("设置消费者参数时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    @BehaviorAnalyse
    public RecorderStatus getRecorderStatus() throws ServiceException {
        try {
            return new RecorderStatus(
                    recordHandler.bufferedSize(),
                    recordHandler.getBufferSize(),
                    recordHandler.getThread(),
                    recordHandler.isIdle()
            );
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("获取记录者状态时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    @BehaviorAnalyse
    public void setRecorderParameters(Integer bufferSize, Integer thread) throws ServiceException {
        try {
            recordHandler.setBufferSize(
                    Objects.isNull(bufferSize) ? recordHandler.getBufferSize() : bufferSize
            );
            recordHandler.setThread(
                    Objects.isNull(thread) ? recordHandler.getThread() : thread
            );
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("设置记录者参数时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    @BehaviorAnalyse
    public boolean isRecordStarted() throws ServiceException {
        try {
            return recordHandler.isStarted();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("获取记录服务是否已经开始时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    @BehaviorAnalyse
    public void startRecord() throws ServiceException {
        try {
            recordHandler.start();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("开启记录服务时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    @BehaviorAnalyse
    public void stopRecord() throws ServiceException {
        try {
            recordHandler.stop();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("关闭记录服务时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
