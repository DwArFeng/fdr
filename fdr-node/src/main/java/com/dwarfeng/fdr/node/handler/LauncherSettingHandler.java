package com.dwarfeng.fdr.node.handler;

import com.dwarfeng.subgrade.stack.handler.Handler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LauncherSettingHandler implements Handler {

    @Value("${com.dwarfeng.fdr.launcher.reset_filter_support}")
    private boolean resetFilterSupport;
    @Value("${com.dwarfeng.fdr.launcher.reset_trigger_support}")
    private boolean resetTriggerSupport;
    @Value("${com.dwarfeng.fdr.launcher.reset_mapper_support}")
    private boolean resetMapperSupport;
    @Value("${com.dwarfeng.fdr.launcher.reset_washer_support}")
    private boolean resetWasherSupport;
    @Value("${com.dwarfeng.fdr.launcher.reset_fetcher_support}")
    private boolean resetFetcherSupport;
    @Value("${com.dwarfeng.fdr.launcher.start_record_delay}")
    private long startRecordDelay;
    @Value("${com.dwarfeng.fdr.launcher.start_fetch_delay}")
    private long startFetchDelay;
    @Value("${com.dwarfeng.fdr.launcher.start_reset_delay}")
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

    public boolean isResetFetcherSupport() {
        return resetFetcherSupport;
    }

    public void setResetFetcherSupport(boolean resetFetcherSupport) {
        this.resetFetcherSupport = resetFetcherSupport;
    }

    public long getStartRecordDelay() {
        return startRecordDelay;
    }

    public void setStartRecordDelay(long startRecordDelay) {
        this.startRecordDelay = startRecordDelay;
    }

    public long getStartFetchDelay() {
        return startFetchDelay;
    }

    public void setStartFetchDelay(long startFetchDelay) {
        this.startFetchDelay = startFetchDelay;
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
                ", resetFetcherSupport=" + resetFetcherSupport +
                ", startRecordDelay=" + startRecordDelay +
                ", startFetchDelay=" + startFetchDelay +
                ", startResetDelay=" + startResetDelay +
                '}';
    }
}
