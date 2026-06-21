package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.handler.*;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 重置处理器。
 *
 * @author DwArFeng
 * @since 1.11.0
 */
@Component
public class ResetProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResetProcessor.class);

    private final FetchHandler fetchHandler;
    private final RecordHandler recordHandler;
    private final FetcherSessionHoldHandler fetcherSessionHoldHandler;
    private final FetcherLocalCacheHandler fetcherLocalCacheHandler;
    private final RecordLocalCacheHandler recordLocalCacheHandler;
    private final RecordMemoryHandler recordMemoryHandler;

    private final MapLocalCacheHandler mapLocalCacheHandler;

    private final PushHandler pushHandler;

    private final Lock lock = new ReentrantLock();

    public ResetProcessor(
            FetchHandler fetchHandler,
            RecordHandler recordHandler,
            FetcherSessionHoldHandler fetcherSessionHoldHandler,
            FetcherLocalCacheHandler fetcherLocalCacheHandler,
            RecordLocalCacheHandler recordLocalCacheHandler,
            RecordMemoryHandler recordMemoryHandler,
            MapLocalCacheHandler mapLocalCacheHandler,
            PushHandler pushHandler
    ) {
        this.fetchHandler = fetchHandler;
        this.recordHandler = recordHandler;
        this.fetcherSessionHoldHandler = fetcherSessionHoldHandler;
        this.fetcherLocalCacheHandler = fetcherLocalCacheHandler;
        this.recordLocalCacheHandler = recordLocalCacheHandler;
        this.recordMemoryHandler = recordMemoryHandler;
        this.mapLocalCacheHandler = mapLocalCacheHandler;
        this.pushHandler = pushHandler;
    }

    public void resetRecord() throws HandlerException {
        lock.lock();
        try {
            doResetRecord();
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        } finally {
            lock.unlock();
        }
    }

    private void doResetRecord() throws Exception {
        // 获取当前的抓取处理器的状态。
        boolean fetchStarted = fetchHandler.isStarted();
        // 获取当前的记录处理器的状态。
        boolean recordStarted = recordHandler.isStarted();

        // 抓取处理器停止，以防止在重置处理时抓取到新的数据。
        fetchHandler.stop();

        // 记录处理器停止，且清空本地缓存。
        recordHandler.stop();
        recordLocalCacheHandler.clear();
        recordMemoryHandler.clear();

        // 如果记录处理器之前是启动的，则重新启动。
        if (recordStarted) {
            recordHandler.start();
        }

        // 如果抓取处理器之前是启动的，则重新启动。
        if (fetchStarted) {
            fetchHandler.start();
        }

        // 消息推送。
        try {
            pushHandler.recordReset();
        } catch (Exception e) {
            LOGGER.warn("推送处理功能重置消息时发生异常, 本次消息将不会被推送, 异常信息如下: ", e);
        }
    }

    public void resetMap() throws HandlerException {
        lock.lock();
        try {
            doResetMap();
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        } finally {
            lock.unlock();
        }
    }

    private void doResetMap() throws Exception {
        mapLocalCacheHandler.clear();

        try {
            pushHandler.mapReset();
        } catch (Exception e) {
            LOGGER.warn("推送映射功能重置消息时发生异常, 本次消息将不会被推送, 异常信息如下: ", e);
        }
    }

    public void resetFetch() throws HandlerException {
        lock.lock();
        try {
            doResetFetch();
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        } finally {
            lock.unlock();
        }
    }

    private void doResetFetch() throws Exception {
        // 获取当前的抓取处理器的状态。
        boolean started = fetchHandler.isStarted();

        // 抓取处理器停止，且清空本地缓存。
        fetchHandler.stop();
        fetcherSessionHoldHandler.closeAndClear();
        fetcherLocalCacheHandler.clear();

        // 如果抓取处理器之前是启动的，则重新启动。
        if (started) {
            fetchHandler.start();
        }

        try {
            pushHandler.fetchReset();
        } catch (Exception e) {
            LOGGER.warn("推送抓取功能重置消息时发生异常, 本次消息将不会被推送, 异常信息如下: ", e);
        }
    }
}
