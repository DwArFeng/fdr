package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.bean.dto.NormalData;
import com.dwarfeng.fdr.stack.handler.NormalKeepHandler;
import com.dwarfeng.fdr.stack.handler.NormalOrganizeHandler;
import com.dwarfeng.fdr.stack.handler.NormalPersistHandler;
import com.dwarfeng.fdr.stack.handler.NormalWatchHandler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class NormalWatchHandlerImpl extends AbstractWatchHandler<NormalData> implements NormalWatchHandler {

    public NormalWatchHandlerImpl(
            NormalKeepHandler normalKeepHandler,
            NormalPersistHandler normalPersistHandler,
            NormalOrganizeHandler normalOrganizeHandler,
            ThreadPoolTaskExecutor executor
    ) {
        super(normalKeepHandler, normalPersistHandler, normalOrganizeHandler, executor);
    }

    @Override
    public String toString() {
        return "NormalWatchHandlerImpl{" +
                "keepHandler=" + keepHandler +
                ", persistHandler=" + persistHandler +
                ", organizeHandler=" + organizeHandler +
                ", executor=" + executor +
                '}';
    }
}
