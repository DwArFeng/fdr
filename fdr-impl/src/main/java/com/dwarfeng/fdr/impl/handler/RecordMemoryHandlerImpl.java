package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.handler.RecordMemoryHandler;
import com.dwarfeng.fdr.stack.struct.RecordMemory;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 记录记忆处理器实现。
 *
 * @author DwArFeng
 * @since 2.5.0
 */
@Component
public class RecordMemoryHandlerImpl implements RecordMemoryHandler {

    private final Lock lock = new ReentrantLock();

    private final Map<LongIdKey, MemoryBucket> memoryMap = new HashMap<>();

    @BehaviorAnalyse
    @Override
    public void append(RecordMemory recordMemory, int maxSize) throws HandlerException {
        try {
            doAppend(recordMemory, maxSize);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private void doAppend(RecordMemory recordMemory, int maxSize) {
        LongIdKey pointKey = recordMemory.getPointKey();

        if (maxSize <= 0) {
            lock.lock();
            try {
                memoryMap.remove(pointKey);
            } finally {
                lock.unlock();
            }
            return;
        }

        MemoryBucket bucket;
        lock.lock();
        try {
            bucket = memoryMap.get(pointKey);
            if (bucket == null) {
                bucket = new MemoryBucket();
                memoryMap.put(pointKey, bucket);
            }
        } finally {
            lock.unlock();
        }

        bucket.getLock().lock();
        try {
            while (bucket.getMemories().size() >= maxSize) {
                bucket.getMemories().removeLast();
            }
            bucket.getMemories().addFirst(recordMemory);
        } finally {
            bucket.getLock().unlock();
        }
    }

    @BehaviorAnalyse
    @SkipRecord
    @Override
    public List<RecordMemory> lookup(LongIdKey pointKey) throws HandlerException {
        try {
            return doLookup(pointKey);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private List<RecordMemory> doLookup(LongIdKey pointKey) {
        MemoryBucket bucket;
        lock.lock();
        try {
            bucket = memoryMap.get(pointKey);
        } finally {
            lock.unlock();
        }
        if (bucket == null) {
            return Collections.emptyList();
        }
        bucket.getLock().lock();
        try {
            if (bucket.getMemories().isEmpty()) {
                return Collections.emptyList();
            }
            return new ArrayList<>(bucket.getMemories());
        } finally {
            bucket.getLock().unlock();
        }
    }

    @BehaviorAnalyse
    @Override
    public void remove(LongIdKey pointKey) throws HandlerException {
        try {
            lock.lock();
            try {
                memoryMap.remove(pointKey);
            } finally {
                lock.unlock();
            }
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    @BehaviorAnalyse
    @Override
    public void clear() throws HandlerException {
        try {
            lock.lock();
            try {
                memoryMap.clear();
            } finally {
                lock.unlock();
            }
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private static final class MemoryBucket {

        private final Lock lock = new ReentrantLock();
        private final LinkedList<RecordMemory> memories = new LinkedList<>();

        public Lock getLock() {
            return lock;
        }

        public LinkedList<RecordMemory> getMemories() {
            return memories;
        }

        @Override
        public String toString() {
            return "MemoryBucket{" +
                    "lock=" + lock +
                    ", memories=" + memories +
                    '}';
        }
    }
}
