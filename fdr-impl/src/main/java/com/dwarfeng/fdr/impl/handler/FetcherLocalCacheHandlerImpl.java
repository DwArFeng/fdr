package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.bean.entity.FetcherInfo;
import com.dwarfeng.fdr.stack.handler.Fetcher;
import com.dwarfeng.fdr.stack.handler.FetcherLocalCacheHandler;
import com.dwarfeng.fdr.stack.handler.FetcherMakeHandler;
import com.dwarfeng.fdr.stack.service.FetcherInfoMaintainService;
import com.dwarfeng.subgrade.impl.handler.GeneralLocalCacheHandler;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class FetcherLocalCacheHandlerImpl implements FetcherLocalCacheHandler {

    private final GeneralLocalCacheHandler<LongIdKey, Fetcher> handler;

    public FetcherLocalCacheHandlerImpl(FetcherFetcher fetcherFetcher) {
        handler = new GeneralLocalCacheHandler<>(fetcherFetcher);
    }

    @BehaviorAnalyse
    @Override
    public boolean exists(LongIdKey key) throws HandlerException {
        return handler.exists(key);
    }

    @BehaviorAnalyse
    @Override
    public Fetcher get(LongIdKey key) throws HandlerException {
        return handler.get(key);
    }

    @BehaviorAnalyse
    @Override
    public boolean remove(LongIdKey key) {
        return handler.remove(key);
    }

    @BehaviorAnalyse
    @Override
    public void clear() {
        handler.clear();
    }

    @Component
    public static class FetcherFetcher implements com.dwarfeng.subgrade.impl.handler.Fetcher<LongIdKey, Fetcher> {

        private final FetcherInfoMaintainService fetcherInfoMaintainService;
        private final FetcherMakeHandler fetcherMakeHandler;

        public FetcherFetcher(
                FetcherInfoMaintainService fetcherInfoMaintainService,
                FetcherMakeHandler fetcherMakeHandler
        ) {
            this.fetcherInfoMaintainService = fetcherInfoMaintainService;
            this.fetcherMakeHandler = fetcherMakeHandler;
        }

        @Override
        @BehaviorAnalyse
        @Transactional(
                transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class
        )
        public boolean exists(LongIdKey key) throws Exception {
            return fetcherInfoMaintainService.exists(key);
        }

        @Override
        @BehaviorAnalyse
        @Transactional(
                transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class
        )
        public Fetcher fetch(LongIdKey key) throws Exception {
            FetcherInfo fetcherInfo = fetcherInfoMaintainService.get(key);
            return fetcherMakeHandler.make(fetcherInfo.getType(), fetcherInfo.getParam());
        }
    }
}
