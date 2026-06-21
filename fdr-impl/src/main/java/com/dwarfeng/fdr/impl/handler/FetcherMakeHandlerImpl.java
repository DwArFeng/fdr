package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.sdk.handler.FetcherMaker;
import com.dwarfeng.fdr.stack.exception.FetcherException;
import com.dwarfeng.fdr.stack.exception.UnsupportedFetcherTypeException;
import com.dwarfeng.fdr.stack.handler.Fetcher;
import com.dwarfeng.fdr.stack.handler.FetcherMakeHandler;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class FetcherMakeHandlerImpl implements FetcherMakeHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(FetcherMakeHandlerImpl.class);

    private final List<FetcherMaker> fetcherMakers;

    public FetcherMakeHandlerImpl(List<FetcherMaker> fetcherMakers) {
        this.fetcherMakers = Optional.ofNullable(fetcherMakers).orElse(Collections.emptyList());
    }

    @Override
    public Fetcher make(String type, String param) throws HandlerException {
        try {
            LOGGER.debug("通过抓取器信息构建新的抓取器...");
            FetcherMaker fetcherMaker = fetcherMakers.stream().filter(maker -> maker.supportType(type))
                    .findFirst().orElseThrow(() -> new UnsupportedFetcherTypeException(type));
            Fetcher fetcher = fetcherMaker.makeFetcher(type, param);
            LOGGER.debug("抓取器构建成功!");
            LOGGER.debug("抓取器: {}", fetcher);
            return fetcher;
        } catch (FetcherException e) {
            throw e;
        } catch (Exception e) {
            throw new FetcherException(e);
        }
    }
}
