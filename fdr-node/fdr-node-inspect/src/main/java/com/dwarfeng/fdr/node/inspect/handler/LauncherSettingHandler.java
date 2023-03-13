package com.dwarfeng.fdr.node.inspect.handler;

import com.dwarfeng.subgrade.stack.handler.Handler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LauncherSettingHandler implements Handler {

    @Value("${launcher.reset_mapper_support}")
    private boolean resetMapperSupport;
    @Value("${launcher.start_reset_delay}")
    private long startResetDelay;

    public boolean isResetMapperSupport() {
        return resetMapperSupport;
    }

    public long getStartResetDelay() {
        return startResetDelay;
    }
}
