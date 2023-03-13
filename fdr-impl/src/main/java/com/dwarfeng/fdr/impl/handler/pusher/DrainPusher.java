package com.dwarfeng.fdr.impl.handler.pusher;

import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.bean.entity.RealtimeValue;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
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
    public void dataFiltered(FilteredValue filteredValue) {
    }

    @Override
    public void dataFiltered(List<FilteredValue> filteredValues) {
    }

    @Override
    public void dataTriggered(TriggeredValue triggeredValue) {
    }

    @Override
    public void dataTriggered(List<TriggeredValue> triggeredValues) {
    }

    @Override
    public void realtimeUpdated(RealtimeValue realtimeValue) {
    }

    @Override
    public void realtimeUpdated(List<RealtimeValue> realtimeValues) {
    }

    @Override
    public void persistenceRecorded(PersistenceValue persistenceValue) {
    }

    @Override
    public void persistenceRecorded(List<PersistenceValue> persistenceValues) {
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
