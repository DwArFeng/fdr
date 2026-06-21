package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.bean.dto.RecordInfo;
import com.dwarfeng.fdr.stack.exception.FetcherNotExistsException;
import com.dwarfeng.fdr.stack.handler.*;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class FetcherSessionHoldHandlerImpl implements FetcherSessionHoldHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(FetcherSessionHoldHandlerImpl.class);

    private final ApplicationContext ctx;

    private final FetcherLocalCacheHandler fetcherLocalCacheHandler;

    private final RecordHandler recordHandler;

    private final Lock lock = new ReentrantLock();
    private final Map<LongIdKey, FetcherSession> fetcherSessionMap = new HashMap<>();

    public FetcherSessionHoldHandlerImpl(
            ApplicationContext ctx,
            FetcherLocalCacheHandler fetcherLocalCacheHandler,
            RecordHandler recordHandler
    ) {
        this.ctx = ctx;
        this.fetcherLocalCacheHandler = fetcherLocalCacheHandler;
        this.recordHandler = recordHandler;
    }

    @Override
    @BehaviorAnalyse
    public FetcherSession get(LongIdKey fetcherInfoKey) throws HandlerException {
        lock.lock();
        try {
            return get0(fetcherInfoKey);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        } finally {
            lock.unlock();
        }
    }

    private FetcherSession get0(LongIdKey fetcherInfoKey) throws Exception {
        if (fetcherSessionMap.containsKey(fetcherInfoKey)) {
            LOGGER.debug("抓取器会话已经存在, 直接返回该抓取器会话... ");
            return fetcherSessionMap.get(fetcherInfoKey);
        }
        if (!fetcherLocalCacheHandler.exists(fetcherInfoKey)) {
            LOGGER.debug("抓取器不存在, 将抛出异常... ");
            throw new FetcherNotExistsException(fetcherInfoKey);
        }
        LOGGER.info("抓取器存在, 抓取器会话不存在, 新建抓取器会话... ");
        Fetcher fetcher = fetcherLocalCacheHandler.get(fetcherInfoKey);
        LOGGER.info("通过抓取器构建抓取器会话...");
        FetcherSession fetcherSession = fetcher.newSession();
        LOGGER.info("抓取器会话构建成功");
        LOGGER.info("构建抓取器会话上下文...");
        FetcherSession.Context context = ctx.getBean(InternalFetcherSessionContext.class, recordHandler);
        LOGGER.info("抓取器会话上下文构建成功, 抓取器会话上下文: {}", context);
        LOGGER.info("初始化抓取器会话...");
        fetcherSession.init(context);
        LOGGER.info("抓取器会话初始化成功");
        LOGGER.info("抓取器会话: {}", fetcherSession);
        LOGGER.debug("打开抓取器会话...");
        fetcherSession.openSession();
        LOGGER.debug("将构建的抓取器会话放入抓取器会话映射中...");
        fetcherSessionMap.put(fetcherInfoKey, fetcherSession);
        return fetcherSession;
    }

    @Override
    @BehaviorAnalyse
    public void closeAndClear() throws HandlerException {
        lock.lock();
        try {
            closeAndClear0();
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        } finally {
            lock.unlock();
        }
    }

    private void closeAndClear0() {
        for (FetcherSession fetcherSession : fetcherSessionMap.values()) {
            try {
                fetcherSession.closeSession();
            } catch (Exception e) {
                LOGGER.warn("关闭抓取器会话时发生异常, 抓取器会话将不会被关闭, 异常信息如下: ", e);
            }
        }
        fetcherSessionMap.clear();
    }

    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    @Component
    public static class InternalFetcherSessionContext implements FetcherSession.Context {

        private final RecordHandler recordHandler;

        public InternalFetcherSessionContext(RecordHandler recordHandler) {
            this.recordHandler = recordHandler;
        }

        @Override
        public void record(RecordInfo recordInfo) throws Exception {
            recordHandler.record(recordInfo);
        }
    }
}
