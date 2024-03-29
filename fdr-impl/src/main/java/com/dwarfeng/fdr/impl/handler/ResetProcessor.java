package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.handler.MapLocalCacheHandler;
import com.dwarfeng.fdr.stack.handler.PushHandler;
import com.dwarfeng.fdr.stack.handler.RecordHandler;
import com.dwarfeng.fdr.stack.handler.RecordLocalCacheHandler;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 重置处理器。
 *
 * @author DwArFeng
 * @since 1.11.0
 */
@Component
public class ResetProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResetProcessor.class);

    private final RecordHandler recordHandler;
    private final RecordLocalCacheHandler recordLocalCacheHandler;

    private final MapLocalCacheHandler mapLocalCacheHandler;

    private final PushHandler pushHandler;

    public ResetProcessor(
            RecordHandler recordHandler,
            RecordLocalCacheHandler recordLocalCacheHandler,
            MapLocalCacheHandler mapLocalCacheHandler,
            PushHandler pushHandler
    ) {
        this.recordHandler = recordHandler;
        this.recordLocalCacheHandler = recordLocalCacheHandler;
        this.mapLocalCacheHandler = mapLocalCacheHandler;
        this.pushHandler = pushHandler;
    }

    public void resetRecord() throws HandlerException {
        recordHandler.stop();
        recordLocalCacheHandler.clear();
        recordHandler.start();

        try {
            pushHandler.recordReset();
        } catch (Exception e) {
            LOGGER.warn("推送记录功能重置消息时发生异常, 本次消息将不会被推送, 异常信息如下: ", e);
        }
    }

    public void resetMap() throws HandlerException {
        mapLocalCacheHandler.clear();

        try {
            pushHandler.mapReset();
        } catch (Exception e) {
            LOGGER.warn("推送映射功能重置消息时发生异常, 本次消息将不会被推送, 异常信息如下: ", e);
        }
    }
}
