package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.bean.dto.TriggeredData;
import com.dwarfeng.fdr.stack.handler.TriggeredKeepHandler;
import com.dwarfeng.fdr.stack.handler.TriggeredPersistHandler;
import com.dwarfeng.fdr.stack.handler.TriggeredQueryHandler;
import com.dwarfeng.fdr.stack.handler.TriggeredViewHandler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class TriggeredViewHandlerImpl extends AbstractViewHandler<TriggeredData> implements TriggeredViewHandler {

    public TriggeredViewHandlerImpl(
            TriggeredKeepHandler triggeredKeepHandler,
            TriggeredPersistHandler triggeredPersistHandler,
            TriggeredQueryHandler triggeredOrganizeHandler,
            ThreadPoolTaskExecutor executor
    ) {
        super(triggeredKeepHandler, triggeredPersistHandler, triggeredOrganizeHandler, executor);
    }

    @Override
    public String toString() {
        return "TriggeredViewHandlerImpl{" +
                "keepHandler=" + keepHandler +
                ", persistHandler=" + persistHandler +
                ", queryHandler=" + queryHandler +
                ", executor=" + executor +
                '}';
    }
}
