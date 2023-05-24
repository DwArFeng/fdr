package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.fdr.stack.bean.dto.NormalData;
import com.dwarfeng.fdr.stack.bean.dto.RecordInfo;
import com.dwarfeng.fdr.stack.bean.dto.TriggeredData;
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
    public void record(RecordInfo recordInfo) throws HandlerException {
        try {
            lock.lock();
            recordProcessor.record(recordInfo);
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
        private final ConsumeHandler<NormalData> normalKeepConsumeHandler;
        private final ConsumeHandler<NormalData> normalPersistConsumeHandler;
        private final ConsumeHandler<FilteredData> filteredKeepConsumeHandler;
        private final ConsumeHandler<FilteredData> filteredPersistConsumeHandler;
        private final ConsumeHandler<TriggeredData> triggeredKeepConsumeHandler;
        private final ConsumeHandler<TriggeredData> triggeredPersistConsumeHandler;

        private final RecordProcessor recordProcessor;

        public RecordWorker(
                SourceHandler sourceHandler,
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
                RecordProcessor recordProcessor
        ) {
            this.sourceHandler = sourceHandler;
            this.normalKeepConsumeHandler = normalKeepConsumeHandler;
            this.normalPersistConsumeHandler = normalPersistConsumeHandler;
            this.filteredKeepConsumeHandler = filteredKeepConsumeHandler;
            this.filteredPersistConsumeHandler = filteredPersistConsumeHandler;
            this.triggeredKeepConsumeHandler = triggeredKeepConsumeHandler;
            this.triggeredPersistConsumeHandler = triggeredPersistConsumeHandler;
            this.recordProcessor = recordProcessor;
        }

        @Override
        public void work() throws Exception {
            LOGGER.info("记录侧消费处理器启动...");
            normalKeepConsumeHandler.start();
            normalPersistConsumeHandler.start();
            filteredKeepConsumeHandler.start();
            filteredPersistConsumeHandler.start();
            triggeredKeepConsumeHandler.start();
            triggeredPersistConsumeHandler.start();

            LOGGER.info("逻辑侧消费处理器启动...");
            recordProcessor.start();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }

            LOGGER.info("数据源上线...");
            List<Source> sources = sourceHandler.all();
            for (Source source : sources) {
                source.online();
            }
        }

        @Override
        public void rest() throws Exception {
            LOGGER.info("数据源下线...");
            List<Source> sources = sourceHandler.all();
            for (Source source : sources) {
                source.offline();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }

            LOGGER.info("逻辑侧消费处理器关闭...");
            recordProcessor.stop();

            LOGGER.info("记录侧消费处理器关闭...");
            normalKeepConsumeHandler.stop();
            normalPersistConsumeHandler.stop();
            filteredKeepConsumeHandler.stop();
            filteredPersistConsumeHandler.stop();
            triggeredKeepConsumeHandler.stop();
            triggeredPersistConsumeHandler.stop();
        }
    }
}
