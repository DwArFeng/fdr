package com.dwarfeng.fdr.sdk.handler.fetcher;

import com.dwarfeng.fdr.sdk.handler.FetcherMaker;
import com.dwarfeng.fdr.sdk.handler.FetcherSupporter;

import java.util.Objects;

/**
 * 抽象抓取器注册。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
public abstract class AbstractFetcherRegistry implements FetcherMaker, FetcherSupporter {

    protected String fetcherType;

    public AbstractFetcherRegistry() {
    }

    public AbstractFetcherRegistry(String fetcherType) {
        this.fetcherType = fetcherType;
    }

    @Override
    public boolean supportType(String type) {
        return Objects.equals(fetcherType, type);
    }

    @Override
    public String provideType() {
        return fetcherType;
    }

    public String getFetcherType() {
        return fetcherType;
    }

    public void setFetcherType(String fetcherType) {
        this.fetcherType = fetcherType;
    }

    @Override
    public String toString() {
        return "AbstractFetcherRegistry{" +
                "fetcherType='" + fetcherType + '\'' +
                '}';
    }
}
