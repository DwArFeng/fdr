package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.bean.entity.FetcherInfo;
import com.dwarfeng.fdr.stack.handler.FetchHandler;
import com.dwarfeng.fdr.stack.handler.FetcherSession;
import com.dwarfeng.fdr.stack.handler.FetcherSessionHoldHandler;
import com.dwarfeng.fdr.stack.service.EnabledFetcherInfoLookupService;
import com.dwarfeng.subgrade.impl.handler.GeneralStartableHandler;
import com.dwarfeng.subgrade.impl.handler.Worker;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Component
public class FetchHandlerImpl implements FetchHandler {

    private final GeneralStartableHandler startableHandler;
    private final Lock lock = new ReentrantLock();

    public FetchHandlerImpl(FetchWorker fetchWorker) {
        this.startableHandler = new GeneralStartableHandler(fetchWorker);
    }

    @Override
    @BehaviorAnalyse
    public boolean isStarted() {
        lock.lock();
        try {
            return startableHandler.isStarted();
        } finally {
            lock.unlock();
        }
    }

    @Override
    @BehaviorAnalyse
    public void start() throws HandlerException {
        lock.lock();
        try {
            startableHandler.start();
        } finally {
            lock.unlock();
        }
    }

    @Override
    @BehaviorAnalyse
    public void stop() throws HandlerException {
        lock.lock();
        try {
            startableHandler.stop();
        } finally {
            lock.unlock();
        }
    }

    @Component
    public static class FetchWorker implements Worker {

        private static final Logger LOGGER = LoggerFactory.getLogger(FetchWorker.class);

        private final EnabledFetcherInfoLookupService enabledFetcherInfoLookupService;
        private final FetcherSessionHoldHandler fetcherSessionHoldHandler;

        private final List<FetcherSession> alreadyStartedFetchSessions = new ArrayList<>();

        public FetchWorker(
                EnabledFetcherInfoLookupService enabledFetcherInfoLookupService,
                FetcherSessionHoldHandler fetcherSessionHoldHandler
        ) {
            this.enabledFetcherInfoLookupService = enabledFetcherInfoLookupService;
            this.fetcherSessionHoldHandler = fetcherSessionHoldHandler;
        }

        @Override
        public void work() throws Exception {
            LOGGER.info("抓取处理器开始工作...");
            alreadyStartedFetchSessions.clear();
            List<LongIdKey> fetcherInfoKeys = enabledFetcherInfoLookupService.getEnabledFetcherInfos().stream()
                    .map(FetcherInfo::getKey).collect(Collectors.toList());
            for (LongIdKey fetcherInfoKey : fetcherInfoKeys) {
                startSingleFetcherSession(fetcherInfoKey);
            }
        }

        private void startSingleFetcherSession(LongIdKey fetcherInfoKey) {
            try {
                FetcherSession fetcherSession = fetcherSessionHoldHandler.get(fetcherInfoKey);
                fetcherSession.startFetch();
                alreadyStartedFetchSessions.add(fetcherSession);
            } catch (Exception e) {
                String message = "启动抓取器会话时发生异常, 将放弃 1 个抓取器会话的启动, fetcherInfoKey: " +
                        fetcherInfoKey + ", 异常信息如下: ";
                LOGGER.warn(message, e);
            }
        }

        @Override
        public void rest() {
            LOGGER.info("抓取处理器停止工作...");
            for (FetcherSession fetcherSession : alreadyStartedFetchSessions) {
                stopSingleFetcherSession(fetcherSession);
            }
            alreadyStartedFetchSessions.clear();
        }

        private void stopSingleFetcherSession(FetcherSession fetcherSession) {
            try {
                fetcherSession.stopFetch();
            } catch (Exception e) {
                String message = "停止抓取器会话时发生异常, 将放弃 1 个抓取器会话的停止, " +
                        "fetcherSession: " + fetcherSession + ", 异常信息如下: ";
                LOGGER.warn(message, e);
            }
        }
    }
}
