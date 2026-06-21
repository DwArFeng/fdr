package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.handler.FetchHandler;
import com.dwarfeng.fdr.stack.handler.RecordHandler;
import com.dwarfeng.fdr.stack.handler.ResetHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * 关闭处理器。
 *
 * <p>
 * 该处理器定义 <code>@PreDestroy</code> 注解的方法，用于在应用关闭时统一执行调度工作。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
@Component
public class ShutdownProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShutdownProcessor.class);

    private final FetchHandler fetchHandler;
    private final RecordHandler recordHandler;
    private final ResetHandler resetHandler;

    public ShutdownProcessor(
            FetchHandler fetchHandler,
            RecordHandler recordHandler,
            ResetHandler resetHandler
    ) {
        this.fetchHandler = fetchHandler;
        this.recordHandler = recordHandler;
        this.resetHandler = resetHandler;
    }

    @PreDestroy
    public void dispose() {
        try {
            LOGGER.info("执行关闭调度: 停止抓取功能...");
            fetchHandler.stop();
            LOGGER.info("执行关闭调度: 停止记录功能...");
            recordHandler.stop();
            LOGGER.info("执行关闭调度: 停止重置功能...");
            resetHandler.stop();
        } catch (Exception e) {
            LOGGER.warn("关闭调度工作执行时发生异常, 部分关闭调度工作可能未能执行, 异常信息如下: ", e);
        }
    }
}
