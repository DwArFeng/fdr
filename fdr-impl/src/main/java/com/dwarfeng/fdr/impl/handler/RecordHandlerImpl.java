package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.bean.dto.RecordInfo;
import com.dwarfeng.fdr.stack.handler.RecordHandler;
import com.dwarfeng.subgrade.impl.handler.GeneralStartableHandler;
import com.dwarfeng.subgrade.impl.handler.Worker;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.stereotype.Component;

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

        private final RecordProcessor recordProcessor;

        public RecordWorker(RecordProcessor recordProcessor) {
            this.recordProcessor = recordProcessor;
        }

        @Override
        public void work() throws Exception {
            recordProcessor.workerWork();
        }

        @Override
        public void rest() throws Exception {
            recordProcessor.workerRest();
        }
    }
}
