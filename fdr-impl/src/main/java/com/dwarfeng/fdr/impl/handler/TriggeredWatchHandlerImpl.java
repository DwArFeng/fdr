package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.bean.dto.TriggeredData;
import com.dwarfeng.fdr.stack.handler.TriggeredKeepHandler;
import com.dwarfeng.fdr.stack.handler.TriggeredOrganizeHandler;
import com.dwarfeng.fdr.stack.handler.TriggeredPersistHandler;
import com.dwarfeng.fdr.stack.handler.TriggeredWatchHandler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class TriggeredWatchHandlerImpl extends AbstractWatchHandler<TriggeredData> implements TriggeredWatchHandler {

    public TriggeredWatchHandlerImpl(
            TriggeredKeepHandler triggeredKeepHandler,
            TriggeredPersistHandler triggeredPersistHandler,
            TriggeredOrganizeHandler triggeredOrganizeHandler,
            ThreadPoolTaskExecutor executor
    ) {
        super(triggeredKeepHandler, triggeredPersistHandler, triggeredOrganizeHandler, executor);
    }

    @Override
    public String toString() {
        return "TriggeredWatchHandlerImpl{" +
                "keepHandler=" + keepHandler +
                ", persistHandler=" + persistHandler +
                ", organizeHandler=" + organizeHandler +
                ", executor=" + executor +
                '}';
    }
}
