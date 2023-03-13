package com.dwarfeng.fdr.node.record.handler;

import com.dwarfeng.subgrade.stack.handler.Handler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LauncherSettingHandler implements Handler {

    @Value("${launcher.start_record_delay}")
    private long startRecordDelay;
    @Value("${launcher.start_reset_delay}")
    private long startResetDelay;

    public long getStartRecordDelay() {
        return startRecordDelay;
    }

    public long getStartResetDelay() {
        return startResetDelay;
    }
}
