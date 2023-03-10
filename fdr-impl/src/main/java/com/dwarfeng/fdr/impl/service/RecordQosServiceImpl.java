package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.impl.handler.Source;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.dwarfeng.fdr.stack.handler.RecordLocalCacheHandler.RecordContext;

@Service
public class RecordQosServiceImpl implements RecordQosService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecordQosServiceImpl.class);

    @Autowired
    private RecordLocalCacheHandler recordLocalCacheHandler;
    @Autowired(required = false)
    @SuppressWarnings("FieldMayBeFinal")
    private List<Source> sources = Collections.emptyList();
    @Autowired
    private RecordHandler recordHandler;
    @Autowired
    @Qualifier("filteredEventConsumeHandler")
    private ConsumeHandler<FilteredValue> filteredEventConsumeHandler;
    @Autowired
    @Qualifier("filteredValueConsumeHandler")
    private ConsumeHandler<FilteredValue> filteredValueConsumeHandler;
    @Autowired
    @Qualifier("triggeredEventConsumeHandler")
    private ConsumeHandler<TriggeredValue> triggeredEventConsumeHandler;
    @Autowired
    @Qualifier("triggeredValueConsumeHandler")
    private ConsumeHandler<TriggeredValue> triggeredValueConsumeHandler;
    @Autowired
    @Qualifier("realtimeEventConsumeHandler")
    private ConsumeHandler<RealtimeValue> realtimeEventConsumeHandler;
    @Autowired
    @Qualifier("realtimeValueConsumeHandler")
    private ConsumeHandler<RealtimeValue> realtimeValueConsumeHandler;
    @Autowired
    @Qualifier("persistenceEventConsumeHandler")
    private ConsumeHandler<PersistenceValue> persistenceEventConsumeHandler;
    @Autowired
    @Qualifier("persistenceValueConsumeHandler")
    private ConsumeHandler<PersistenceValue> persistenceValueConsumeHandler;

    @Autowired
    private ServiceExceptionMapper sem;

    private final Lock lock = new ReentrantLock();
    private final Map<ConsumerId, ConsumeHandler<? extends Bean>> consumeHandlerMap = new EnumMap<>(ConsumerId.class);

    @PostConstruct
    public void init() {
        lock.lock();
        try {
            consumeHandlerMap.put(ConsumerId.EVENT_FILTERED, filteredEventConsumeHandler);
            consumeHandlerMap.put(ConsumerId.VALUE_FILTERED, filteredValueConsumeHandler);
            consumeHandlerMap.put(ConsumerId.EVENT_TRIGGERED, triggeredEventConsumeHandler);
            consumeHandlerMap.put(ConsumerId.VALUE_TRIGGERED, triggeredValueConsumeHandler);
            consumeHandlerMap.put(ConsumerId.EVENT_REALTIME, realtimeEventConsumeHandler);
            consumeHandlerMap.put(ConsumerId.VALUE_REALTIME, realtimeValueConsumeHandler);
            consumeHandlerMap.put(ConsumerId.EVENT_PERSISTENCE, persistenceEventConsumeHandler);
            consumeHandlerMap.put(ConsumerId.VALUE_PERSISTENCE, persistenceValueConsumeHandler);
        } finally {
            lock.unlock();
        }
    }

    @PreDestroy
    public void dispose() throws Exception {
        lock.lock();
        try {
            internalStopRecord();
        } finally {
            lock.unlock();
        }
    }

    @Override
    @BehaviorAnalyse
    public RecordContext getRecordContext(LongIdKey pointKey) throws ServiceException {
        lock.lock();
        try {
            return recordLocalCacheHandler.get(pointKey);
        } catch (HandlerException e) {
            throw ServiceExceptionHelper.logAndThrow("从本地缓存中获取记录上下文时发生异常",
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
            recordLocalCacheHandler.clear();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("清除本地缓存时发生异常",
                    LogLevel.WARN, sem, e
            );
        } finally {
            lock.unlock();
        }
    }

    @Override
    @BehaviorAnalyse
    public ConsumerStatus getConsumerStatus(ConsumerId consumerId) throws ServiceException {
        lock.lock();
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
            throw ServiceExceptionHelper.logAndThrow("获取消费者状态时发生异常",
                    LogLevel.WARN, sem, e
            );
        } finally {
            lock.unlock();
        }
    }

    @Override
    @BehaviorAnalyse
    public void setConsumerParameters(
            ConsumerId consumerId, Integer bufferSize, Integer batchSize, Long maxIdleTime, Integer thread)
            throws ServiceException {
        lock.lock();
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
            throw ServiceExceptionHelper.logAndThrow("设置消费者参数时发生异常",
                    LogLevel.WARN, sem, e
            );
        } finally {
            lock.unlock();
        }
    }

    @Override
    @BehaviorAnalyse
    public RecorderStatus getRecorderStatus() throws ServiceException {
        lock.lock();
        try {
            return new RecorderStatus(
                    recordHandler.bufferedSize(),
                    recordHandler.getBufferSize(),
                    recordHandler.getThread(),
                    recordHandler.isIdle()
            );
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("获取记录者状态时发生异常",
                    LogLevel.WARN, sem, e
            );
        } finally {
            lock.unlock();
        }
    }

    @Override
    @BehaviorAnalyse
    public void setRecorderParameters(Integer bufferSize, Integer thread) throws ServiceException {
        lock.lock();
        try {
            recordHandler.setBufferSize(
                    Objects.isNull(bufferSize) ? recordHandler.getBufferSize() : bufferSize
            );
            recordHandler.setThread(
                    Objects.isNull(thread) ? recordHandler.getThread() : thread
            );
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("设置记录者参数时发生异常",
                    LogLevel.WARN, sem, e
            );
        } finally {
            lock.unlock();
        }
    }

    @Override
    @BehaviorAnalyse
    public void startRecord() throws ServiceException {
        lock.lock();
        try {
            LOGGER.info("开启记录服务...");
            filteredEventConsumeHandler.start();
            filteredValueConsumeHandler.start();
            triggeredEventConsumeHandler.start();
            triggeredValueConsumeHandler.start();
            realtimeEventConsumeHandler.start();
            realtimeValueConsumeHandler.start();
            persistenceEventConsumeHandler.start();
            persistenceValueConsumeHandler.start();

            recordHandler.start();

            for (Source source : sources) {
                source.online();
            }
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("开启记录服务时发生异常",
                    LogLevel.WARN, sem, e
            );
        } finally {
            lock.unlock();
        }
    }

    @Override
    @BehaviorAnalyse
    public void stopRecord() throws ServiceException {
        lock.lock();
        try {
            internalStopRecord();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("关闭记录服务时发生异常",
                    LogLevel.WARN, sem, e
            );
        } finally {
            lock.unlock();
        }
    }

    private void internalStopRecord() throws Exception {
        LOGGER.info("关闭记录服务...");
        for (Source source : sources) {
            source.offline();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
        recordHandler.stop();

        filteredEventConsumeHandler.stop();
        filteredValueConsumeHandler.stop();
        triggeredEventConsumeHandler.stop();
        triggeredValueConsumeHandler.stop();
        realtimeEventConsumeHandler.stop();
        realtimeValueConsumeHandler.stop();
        persistenceEventConsumeHandler.stop();
        persistenceValueConsumeHandler.stop();
    }
}
