package com.dwarfeng.fdr.impl.handler.bridge.mock;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 桥接器配置。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class MockBridgeConfig {

    @Value("${bridge.mock.update.delay}")
    private long updateDelay;
    @Value("${bridge.mock.update.before_delay}")
    private long updateBeforeDelay;
    @Value("${bridge.mock.update.after_delay}")
    private long updateAfterDelay;

    @Value("${bridge.mock.inspect.delay}")
    private long inspectDelay;
    @Value("${bridge.mock.inspect.before_delay}")
    private long inspectBeforeDelay;
    @Value("${bridge.mock.inspect.after_delay}")
    private long inspectAfterDelay;

    @Value("${bridge.mock.record.delay}")
    private long recordDelay;
    @Value("${bridge.mock.record.before_delay}")
    private long recordBeforeDelay;
    @Value("${bridge.mock.record.after_delay}")
    private long recordAfterDelay;

    @Value("${bridge.mock.query.data_interval}")
    private long queryDataInterval;
    @Value("${bridge.mock.query.delay}")
    private long queryDelay;
    @Value("${bridge.mock.query.offset_delay}")
    private long queryOffsetDelay;
    @Value("${bridge.mock.query.before_delay}")
    private long queryBeforeDelay;
    @Value("${bridge.mock.query.after_delay}")
    private long queryAfterDelay;

    public long getUpdateDelay() {
        return updateDelay;
    }

    public void setUpdateDelay(long updateDelay) {
        this.updateDelay = updateDelay;
    }

    public long getUpdateBeforeDelay() {
        return updateBeforeDelay;
    }

    public void setUpdateBeforeDelay(long updateBeforeDelay) {
        this.updateBeforeDelay = updateBeforeDelay;
    }

    public long getUpdateAfterDelay() {
        return updateAfterDelay;
    }

    public void setUpdateAfterDelay(long updateAfterDelay) {
        this.updateAfterDelay = updateAfterDelay;
    }

    public long getInspectDelay() {
        return inspectDelay;
    }

    public void setInspectDelay(long inspectDelay) {
        this.inspectDelay = inspectDelay;
    }

    public long getInspectBeforeDelay() {
        return inspectBeforeDelay;
    }

    public void setInspectBeforeDelay(long inspectBeforeDelay) {
        this.inspectBeforeDelay = inspectBeforeDelay;
    }

    public long getInspectAfterDelay() {
        return inspectAfterDelay;
    }

    public void setInspectAfterDelay(long inspectAfterDelay) {
        this.inspectAfterDelay = inspectAfterDelay;
    }

    public long getRecordDelay() {
        return recordDelay;
    }

    public void setRecordDelay(long recordDelay) {
        this.recordDelay = recordDelay;
    }

    public long getRecordBeforeDelay() {
        return recordBeforeDelay;
    }

    public void setRecordBeforeDelay(long recordBeforeDelay) {
        this.recordBeforeDelay = recordBeforeDelay;
    }

    public long getRecordAfterDelay() {
        return recordAfterDelay;
    }

    public void setRecordAfterDelay(long recordAfterDelay) {
        this.recordAfterDelay = recordAfterDelay;
    }

    public long getQueryDataInterval() {
        return queryDataInterval;
    }

    public void setQueryDataInterval(long queryDataInterval) {
        this.queryDataInterval = queryDataInterval;
    }

    public long getQueryDelay() {
        return queryDelay;
    }

    public void setQueryDelay(long queryDelay) {
        this.queryDelay = queryDelay;
    }

    public long getQueryOffsetDelay() {
        return queryOffsetDelay;
    }

    public void setQueryOffsetDelay(long queryOffsetDelay) {
        this.queryOffsetDelay = queryOffsetDelay;
    }

    public long getQueryBeforeDelay() {
        return queryBeforeDelay;
    }

    public void setQueryBeforeDelay(long queryBeforeDelay) {
        this.queryBeforeDelay = queryBeforeDelay;
    }

    public long getQueryAfterDelay() {
        return queryAfterDelay;
    }

    public void setQueryAfterDelay(long queryAfterDelay) {
        this.queryAfterDelay = queryAfterDelay;
    }

    @Override
    public String toString() {
        return "MockBridgeConfig{" +
                "updateDelay=" + updateDelay +
                ", updateBeforeDelay=" + updateBeforeDelay +
                ", updateAfterDelay=" + updateAfterDelay +
                ", inspectDelay=" + inspectDelay +
                ", inspectBeforeDelay=" + inspectBeforeDelay +
                ", inspectAfterDelay=" + inspectAfterDelay +
                ", recordDelay=" + recordDelay +
                ", recordBeforeDelay=" + recordBeforeDelay +
                ", recordAfterDelay=" + recordAfterDelay +
                ", queryDataInterval=" + queryDataInterval +
                ", queryDelay=" + queryDelay +
                ", queryOffsetDelay=" + queryOffsetDelay +
                ", queryBeforeDelay=" + queryBeforeDelay +
                ", queryAfterDelay=" + queryAfterDelay +
                '}';
    }
}
