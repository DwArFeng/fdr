package com.dwarfeng.fdr.impl.handler.fetcher.kafka.dct;

import com.dwarfeng.fdr.sdk.handler.fetcher.AbstractFetcher;
import com.dwarfeng.fdr.stack.handler.FetcherSession;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 基于 dwarfeng-dct 协议的 Kafka 抓取器。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DctKafkaFetcher extends AbstractFetcher {

    private final ApplicationContext ctx;

    private final DctKafkaFetcherConfig config;

    public DctKafkaFetcher(ApplicationContext ctx, DctKafkaFetcherConfig config) {
        this.ctx = ctx;
        this.config = config;
    }

    @Override
    protected FetcherSession doNewSession() {
        return ctx.getBean(DctKafkaFetcherSession.class, ctx, config);
    }

    @Override
    public String toString() {
        return "DctKafkaFetcher{" +
                "ctx=" + ctx +
                ", config=" + config +
                '}';
    }
}
