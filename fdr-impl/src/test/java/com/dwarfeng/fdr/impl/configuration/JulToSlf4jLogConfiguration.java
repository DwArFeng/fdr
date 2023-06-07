package com.dwarfeng.fdr.impl.configuration;

import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.annotation.PostConstruct;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class JulToSlf4jLogConfiguration {

    @PostConstruct
    public void init() {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }
}
