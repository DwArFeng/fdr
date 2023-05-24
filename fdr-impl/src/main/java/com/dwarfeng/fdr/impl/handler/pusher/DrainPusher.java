package com.dwarfeng.fdr.impl.handler.pusher;

import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.fdr.stack.bean.dto.NormalData;
import com.dwarfeng.fdr.stack.bean.dto.TriggeredData;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 简单的丢弃掉所有信息的推送器。
 *
 * @author DwArFeng
 * @since 1.4.0
 */
@Component
public class DrainPusher extends AbstractPusher {

    public static final String PUSHER_TYPE = "drain";

    public DrainPusher() {
        super(PUSHER_TYPE);
    }

    @Override
    public void normalUpdated(NormalData normalRecord) {
    }

    @Override
    public void normalUpdated(List<NormalData> normalRecords) {
    }

    @Override
    public void normalRecorded(NormalData normalRecord) {
    }

    @Override
    public void normalRecorded(List<NormalData> normalRecords) {
    }

    @Override
    public void filteredUpdated(FilteredData filteredRecord) {
    }

    @Override
    public void filteredUpdated(List<FilteredData> filteredRecords) {
    }

    @Override
    public void filteredRecorded(FilteredData filteredRecord) {
    }

    @Override
    public void filteredRecorded(List<FilteredData> filteredRecords) {
    }

    @Override
    public void triggeredUpdated(TriggeredData triggeredRecord) {
    }

    @Override
    public void triggeredUpdated(List<TriggeredData> triggeredRecords) {
    }

    @Override
    public void triggeredRecorded(TriggeredData triggeredRecord) {
    }

    @Override
    public void triggeredRecorded(List<TriggeredData> triggeredRecords) {
    }

    @Override
    public void recordReset() {
    }

    @Override
    public void mapReset() {
    }

    @Override
    public String toString() {
        return "DrainPusher{" +
                "pusherType='" + pusherType + '\'' +
                '}';
    }
}
