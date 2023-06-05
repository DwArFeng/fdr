package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.bean.dto.NormalData;
import com.dwarfeng.fdr.stack.handler.NormalKeepHandler;
import com.dwarfeng.fdr.stack.handler.NormalPersistHandler;
import com.dwarfeng.fdr.stack.handler.NormalQueryHandler;
import com.dwarfeng.fdr.stack.handler.NormalViewHandler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class NormalViewHandlerImpl extends AbstractViewHandler<NormalData> implements NormalViewHandler {

    public NormalViewHandlerImpl(
            NormalKeepHandler normalKeepHandler,
            NormalPersistHandler normalPersistHandler,
            NormalQueryHandler normalOrganizeHandler,
            ThreadPoolTaskExecutor executor
    ) {
        super(normalKeepHandler, normalPersistHandler, normalOrganizeHandler, executor);
    }

    @Override
    public String toString() {
        return "NormalViewHandlerImpl{" +
                "keepHandler=" + keepHandler +
                ", persistHandler=" + persistHandler +
                ", queryHandler=" + queryHandler +
                ", executor=" + executor +
                '}';
    }
}
