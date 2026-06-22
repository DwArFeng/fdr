package com.dwarfeng.fdr.impl.handler.fetcher.kafka.dcti;

import com.dwarfeng.fdr.sdk.handler.fetcher.AbstractFetcher;
import com.dwarfeng.fdr.stack.handler.FetcherSession;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 基于 dcti 协议的 Kafka 抓取器。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DctiKafkaFetcher extends AbstractFetcher {

    private final ApplicationContext ctx;

    private final DctiKafkaFetcherConfig config;

    public DctiKafkaFetcher(ApplicationContext ctx, DctiKafkaFetcherConfig config) {
        this.ctx = ctx;
        this.config = config;
    }

    @Override
    protected FetcherSession doNewSession() {
        return ctx.getBean(DctiKafkaFetcherSession.class, ctx, config);
    }

    @Override
    public String toString() {
        return "DctiKafkaFetcher{" +
                "ctx=" + ctx +
                ", config=" + config +
                '}';
    }
}
