package com.dwarfeng.fdr.node.handler;

import com.dwarfeng.subgrade.stack.handler.Handler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LauncherSettingHandler implements Handler {

    @Value("${launcher.reset_filter_support}")
    private boolean resetFilterSupport;
    @Value("${launcher.reset_trigger_support}")
    private boolean resetTriggerSupport;
    @Value("${launcher.reset_mapper_support}")
    private boolean resetMapperSupport;
    @Value("${launcher.start_record_delay}")
    private long startRecordDelay;
    @Value("${launcher.start_reset_delay}")
    private long startResetDelay;

    public boolean isResetFilterSupport() {
        return resetFilterSupport;
    }

    public boolean isResetTriggerSupport() {
        return resetTriggerSupport;
    }

    public boolean isResetMapperSupport() {
        return resetMapperSupport;
    }

    public long getStartRecordDelay() {
        return startRecordDelay;
    }

    public long getStartResetDelay() {
        return startResetDelay;
    }
}
