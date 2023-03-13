package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.bean.entity.RealtimeValue;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.fdr.stack.handler.ConsumeHandler;
import com.dwarfeng.fdr.stack.handler.RecordHandler;
import com.dwarfeng.fdr.stack.handler.RecordLocalCacheHandler;
import com.dwarfeng.fdr.stack.service.RecordQosService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.Bean;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
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

import static com.dwarfeng.fdr.stack.handler.RecordLocalCacheHandler.RecordContext;

@Service
public class RecordQosServiceImpl implements RecordQosService {

    private final RecordLocalCacheHandler recordLocalCacheHandler;
    private final ConsumeHandler<FilteredValue> filteredEventConsumeHandler;
    private final ConsumeHandler<FilteredValue> filteredValueConsumeHandler;
    private final ConsumeHandler<TriggeredValue> triggeredEventConsumeHandler;
    private final ConsumeHandler<TriggeredValue> triggeredValueConsumeHandler;
    private final ConsumeHandler<RealtimeValue> realtimeEventConsumeHandler;
    private final ConsumeHandler<RealtimeValue> realtimeValueConsumeHandler;
    private final ConsumeHandler<PersistenceValue> persistenceEventConsumeHandler;
    private final ConsumeHandler<PersistenceValue> persistenceValueConsumeHandler;
    private final RecordHandler recordHandler;

    private final ServiceExceptionMapper sem;

    private final Map<ConsumerId, ConsumeHandler<? extends Bean>> consumeHandlerMap = new EnumMap<>(ConsumerId.class);

    public RecordQosServiceImpl(
            RecordLocalCacheHandler recordLocalCacheHandler,
            @Qualifier("filteredEventConsumeHandler")
            ConsumeHandler<FilteredValue> filteredEventConsumeHandler,
            @Qualifier("filteredValueConsumeHandler")
            ConsumeHandler<FilteredValue> filteredValueConsumeHandler,
            @Qualifier("triggeredEventConsumeHandler")
            ConsumeHandler<TriggeredValue> triggeredEventConsumeHandler,
            @Qualifier("triggeredValueConsumeHandler")
            ConsumeHandler<TriggeredValue> triggeredValueConsumeHandler,
            @Qualifier("realtimeEventConsumeHandler")
            ConsumeHandler<RealtimeValue> realtimeEventConsumeHandler,
            @Qualifier("realtimeValueConsumeHandler")
            ConsumeHandler<RealtimeValue> realtimeValueConsumeHandler,
            @Qualifier("persistenceEventConsumeHandler")
            ConsumeHandler<PersistenceValue> persistenceEventConsumeHandler,
            @Qualifier("persistenceValueConsumeHandler")
            ConsumeHandler<PersistenceValue> persistenceValueConsumeHandler,
            RecordHandler recordHandler,
            ServiceExceptionMapper sem
    ) {
        this.recordLocalCacheHandler = recordLocalCacheHandler;
        this.filteredEventConsumeHandler = filteredEventConsumeHandler;
        this.filteredValueConsumeHandler = filteredValueConsumeHandler;
        this.triggeredEventConsumeHandler = triggeredEventConsumeHandler;
        this.triggeredValueConsumeHandler = triggeredValueConsumeHandler;
        this.realtimeEventConsumeHandler = realtimeEventConsumeHandler;
        this.realtimeValueConsumeHandler = realtimeValueConsumeHandler;
        this.persistenceEventConsumeHandler = persistenceEventConsumeHandler;
        this.persistenceValueConsumeHandler = persistenceValueConsumeHandler;
        this.recordHandler = recordHandler;
        this.sem = sem;
    }

    @PostConstruct
    public void init() {
        consumeHandlerMap.put(ConsumerId.EVENT_FILTERED, filteredEventConsumeHandler);
        consumeHandlerMap.put(ConsumerId.VALUE_FILTERED, filteredValueConsumeHandler);
        consumeHandlerMap.put(ConsumerId.EVENT_TRIGGERED, triggeredEventConsumeHandler);
        consumeHandlerMap.put(ConsumerId.VALUE_TRIGGERED, triggeredValueConsumeHandler);
        consumeHandlerMap.put(ConsumerId.EVENT_REALTIME, realtimeEventConsumeHandler);
        consumeHandlerMap.put(ConsumerId.VALUE_REALTIME, realtimeValueConsumeHandler);
        consumeHandlerMap.put(ConsumerId.EVENT_PERSISTENCE, persistenceEventConsumeHandler);
        consumeHandlerMap.put(ConsumerId.VALUE_PERSISTENCE, persistenceValueConsumeHandler);
    }

    @PreDestroy
    public void dispose() throws Exception {
        recordHandler.stop();
    }

    @Override
    @BehaviorAnalyse
    public RecordContext getRecordContext(LongIdKey pointKey) throws ServiceException {
        try {
            return recordLocalCacheHandler.get(pointKey);
        } catch (HandlerException e) {
            throw ServiceExceptionHelper.logAndThrow("从本地缓存中获取记录上下文时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    public void clearLocalCache() throws ServiceException {
        try {
            recordLocalCacheHandler.clear();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("清除本地缓存时发生异常", LogLevel.WARN, sem, e);
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
            throw ServiceExceptionHelper.logAndThrow("获取消费者状态时发生异常", LogLevel.WARN, sem, e);
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
            throw ServiceExceptionHelper.logAndThrow("设置消费者参数时发生异常", LogLevel.WARN, sem, e);
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
            throw ServiceExceptionHelper.logAndThrow("获取记录者状态时发生异常", LogLevel.WARN, sem, e);
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
            throw ServiceExceptionHelper.logAndThrow("设置记录者参数时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    public void startRecord() throws ServiceException {
        try {
            recordHandler.start();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("开启记录服务时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    @BehaviorAnalyse
    public void stopRecord() throws ServiceException {
        try {
            recordHandler.stop();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("关闭记录服务时发生异常", LogLevel.WARN, sem, e);
        }
    }
}
