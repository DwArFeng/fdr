package com.dwarfeng.fdr.impl.handler.pusher;

import com.dwarfeng.fdr.sdk.handler.pusher.AbstractPusher;
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
    public void normalUpdated(NormalData normalData) {
    }

    @Override
    public void normalUpdated(List<NormalData> normalDatas) {
    }

    @Override
    public void normalRecorded(NormalData normalData) {
    }

    @Override
    public void normalRecorded(List<NormalData> normalDatas) {
    }

    @Override
    public void filteredUpdated(FilteredData filteredData) {
    }

    @Override
    public void filteredUpdated(List<FilteredData> filteredDatas) {
    }

    @Override
    public void filteredRecorded(FilteredData filteredData) {
    }

    @Override
    public void filteredRecorded(List<FilteredData> filteredDatas) {
    }

    @Override
    public void triggeredUpdated(TriggeredData triggeredData) {
    }

    @Override
    public void triggeredUpdated(List<TriggeredData> triggeredDatas) {
    }

    @Override
    public void triggeredRecorded(TriggeredData triggeredData) {
    }

    @Override
    public void triggeredRecorded(List<TriggeredData> triggeredDatas) {
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
