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
    @Value("${launcher.reset_washer_support}")
    private boolean resetWasherSupport;
    @Value("${launcher.start_record_delay}")
    private long startRecordDelay;
    @Value("${launcher.start_reset_delay}")
    private long startResetDelay;

    public boolean isResetFilterSupport() {
        return resetFilterSupport;
    }

    public void setResetFilterSupport(boolean resetFilterSupport) {
        this.resetFilterSupport = resetFilterSupport;
    }

    public boolean isResetTriggerSupport() {
        return resetTriggerSupport;
    }

    public void setResetTriggerSupport(boolean resetTriggerSupport) {
        this.resetTriggerSupport = resetTriggerSupport;
    }

    public boolean isResetMapperSupport() {
        return resetMapperSupport;
    }

    public void setResetMapperSupport(boolean resetMapperSupport) {
        this.resetMapperSupport = resetMapperSupport;
    }

    public boolean isResetWasherSupport() {
        return resetWasherSupport;
    }

    public void setResetWasherSupport(boolean resetWasherSupport) {
        this.resetWasherSupport = resetWasherSupport;
    }

    public long getStartRecordDelay() {
        return startRecordDelay;
    }

    public void setStartRecordDelay(long startRecordDelay) {
        this.startRecordDelay = startRecordDelay;
    }

    public long getStartResetDelay() {
        return startResetDelay;
    }

    public void setStartResetDelay(long startResetDelay) {
        this.startResetDelay = startResetDelay;
    }

    @Override
    public String toString() {
        return "LauncherSettingHandler{" +
                "resetFilterSupport=" + resetFilterSupport +
                ", resetTriggerSupport=" + resetTriggerSupport +
                ", resetMapperSupport=" + resetMapperSupport +
                ", resetWasherSupport=" + resetWasherSupport +
                ", startRecordDelay=" + startRecordDelay +
                ", startResetDelay=" + startResetDelay +
                '}';
    }
}
