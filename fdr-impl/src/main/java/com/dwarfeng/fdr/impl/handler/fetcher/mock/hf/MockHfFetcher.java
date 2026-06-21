package com.dwarfeng.fdr.impl.handler.fetcher.mock.hf;

import com.dwarfeng.fdr.sdk.handler.fetcher.AbstractFetcher;
import com.dwarfeng.fdr.stack.handler.FetcherSession;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

/**
 * 高频模拟抓取器。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MockHfFetcher extends AbstractFetcher {

    private final ApplicationContext ctx;

    private final MockHfFetcherConfig config;

    private final ThreadPoolTaskScheduler scheduler;

    public MockHfFetcher(ApplicationContext ctx, MockHfFetcherConfig config, ThreadPoolTaskScheduler scheduler) {
        this.ctx = ctx;
        this.config = config;
        this.scheduler = scheduler;
    }

    @Override
    protected FetcherSession doNewSession() {
        return ctx.getBean(MockHfFetcherSession.class, ctx, config, scheduler);
    }

    @Override
    public String toString() {
        return "MockHfFetcher{" +
                "ctx=" + ctx +
                ", config=" + config +
                ", scheduler=" + scheduler +
                '}';
    }
}
