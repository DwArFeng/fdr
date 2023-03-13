package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.dcti.stack.bean.dto.DataInfo;
import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.bean.entity.RealtimeValue;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.fdr.stack.handler.ConsumeHandler;
import com.dwarfeng.fdr.stack.handler.RecordHandler;
import com.dwarfeng.fdr.stack.handler.Source;
import com.dwarfeng.fdr.stack.handler.SourceHandler;
import com.dwarfeng.subgrade.impl.handler.GeneralStartableHandler;
import com.dwarfeng.subgrade.impl.handler.Worker;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class RecordHandlerImpl implements RecordHandler {

    private final GeneralStartableHandler startableHandler;

    private final RecordProcessor recordProcessor;

    private final Lock lock = new ReentrantLock();

    public RecordHandlerImpl(RecordWorker recordWorker, RecordProcessor recordProcessor) {
        this.startableHandler = new GeneralStartableHandler(recordWorker);
        this.recordProcessor = recordProcessor;
    }

    @BehaviorAnalyse
    @Override
    public boolean isStarted() {
        lock.lock();
        try {
            return startableHandler.isStarted();
        } finally {
            lock.unlock();
        }
    }

    @BehaviorAnalyse
    @Override
    public void start() throws HandlerException {
        lock.lock();
        try {
            startableHandler.start();
        } finally {
            lock.unlock();
        }
    }

    @BehaviorAnalyse
    @Override
    public void stop() throws HandlerException {
        lock.lock();
        try {
            startableHandler.stop();
        } finally {
            lock.unlock();
        }
    }

    @BehaviorAnalyse
    @Override
    public void record(String message) throws HandlerException {
        lock.lock();
        try {
            recordProcessor.record(message);
        } finally {
            lock.unlock();
        }
    }

    @BehaviorAnalyse
    @Override
    public void record(DataInfo dataInfo) throws HandlerException {
        lock.lock();
        try {
            recordProcessor.record(dataInfo);
        } finally {
            lock.unlock();
        }
    }

    @BehaviorAnalyse
    @Override
    public int bufferedSize() {
        lock.lock();
        try {
            return recordProcessor.bufferedSize();
        } finally {
            lock.unlock();
        }
    }

    @BehaviorAnalyse
    @Override
    public int getBufferSize() {
        lock.lock();
        try {
            return recordProcessor.getBufferSize();
        } finally {
            lock.unlock();
        }
    }

    @BehaviorAnalyse
    @Override
    public void setBufferSize(int bufferSize) {
        lock.lock();
        try {
            recordProcessor.setBufferSize(bufferSize);
        } finally {
            lock.unlock();
        }
    }

    @BehaviorAnalyse
    @Override
    public int getThread() {
        lock.lock();
        try {
            return recordProcessor.getThread();
        } finally {
            lock.unlock();
        }
    }

    @BehaviorAnalyse
    @Override
    public void setThread(int thread) {
        lock.lock();
        try {
            recordProcessor.setThread(thread);
        } finally {
            lock.unlock();
        }
    }

    @BehaviorAnalyse
    @Override
    public boolean isIdle() {
        lock.lock();
        try {
            return recordProcessor.isIdle();
        } finally {
            lock.unlock();
        }
    }

    @Component
    public static class RecordWorker implements Worker {

        private static final Logger LOGGER = LoggerFactory.getLogger(RecordWorker.class);

        private final SourceHandler sourceHandler;

        private final RecordProcessor recordProcessor;

        private final ConsumeHandler<FilteredValue> filteredEventConsumeHandler;
        private final ConsumeHandler<FilteredValue> filteredValueConsumeHandler;
        private final ConsumeHandler<TriggeredValue> triggeredEventConsumeHandler;
        private final ConsumeHandler<TriggeredValue> triggeredValueConsumeHandler;
        private final ConsumeHandler<RealtimeValue> realtimeEventConsumeHandler;
        private final ConsumeHandler<RealtimeValue> realtimeValueConsumeHandler;
        private final ConsumeHandler<PersistenceValue> persistenceEventConsumeHandler;
        private final ConsumeHandler<PersistenceValue> persistenceValueConsumeHandler;

        public RecordWorker(
                SourceHandler sourceHandler,
                RecordProcessor recordProcessor,
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
                ConsumeHandler<PersistenceValue> persistenceValueConsumeHandler
        ) {
            this.sourceHandler = sourceHandler;
            this.recordProcessor = recordProcessor;
            this.filteredEventConsumeHandler = filteredEventConsumeHandler;
            this.filteredValueConsumeHandler = filteredValueConsumeHandler;
            this.triggeredEventConsumeHandler = triggeredEventConsumeHandler;
            this.triggeredValueConsumeHandler = triggeredValueConsumeHandler;
            this.realtimeEventConsumeHandler = realtimeEventConsumeHandler;
            this.realtimeValueConsumeHandler = realtimeValueConsumeHandler;
            this.persistenceEventConsumeHandler = persistenceEventConsumeHandler;
            this.persistenceValueConsumeHandler = persistenceValueConsumeHandler;
        }

        @Override
        public void work() throws Exception {
            LOGGER.info("开启记录服务...");

            filteredEventConsumeHandler.start();
            filteredValueConsumeHandler.start();
            triggeredEventConsumeHandler.start();
            triggeredValueConsumeHandler.start();
            realtimeEventConsumeHandler.start();
            realtimeValueConsumeHandler.start();
            persistenceEventConsumeHandler.start();
            persistenceValueConsumeHandler.start();

            recordProcessor.start();

            List<Source> sources = sourceHandler.all();
            for (Source source : sources) {
                source.online();
            }
        }

        @Override
        public void rest() throws Exception {
            LOGGER.info("关闭记录服务...");

            List<Source> sources = sourceHandler.all();
            for (Source source : sources) {
                source.offline();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
            recordProcessor.stop();

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
}
