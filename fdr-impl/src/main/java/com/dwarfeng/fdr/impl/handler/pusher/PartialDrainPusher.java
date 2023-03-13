package com.dwarfeng.fdr.impl.handler.pusher;

import com.dwarfeng.fdr.impl.handler.Pusher;
import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.bean.entity.RealtimeValue;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 丢弃掉部分消息的推送器。
 *
 * @author DwArFeng
 * @since 1.4.0
 */
@Component
public class PartialDrainPusher extends AbstractPusher {

    public static final String PUSHER_TYPE = "partial_drain";

    @Autowired
    private List<Pusher> pushers;

    @Value("${pusher.partial_drain.delegate_type}")
    private String delegateType;
    @Value("${pusher.partial_drain.drain_realtime_value}")
    private boolean drainRealtimeValue;
    @Value("${pusher.partial_drain.drain_persistence_value}")
    private boolean drainPersistenceValue;
    @Value("${pusher.partial_drain.drain_filtered_value}")
    private boolean drainFilteredValue;
    @Value("${pusher.partial_drain.drain_triggered_value}")
    private boolean drainTriggeredValue;
    @Value("${pusher.partial_drain.drain_record_reset}")
    private boolean drainRecordReset;
    @Value("${pusher.partial_drain.drain_map_reset}")
    private boolean drainMapReset;

    private Pusher delegate;

    public PartialDrainPusher() {
        super(PUSHER_TYPE);
    }

    @PostConstruct
    public void init() throws HandlerException {
        this.delegate = pushers.stream().filter(p -> p.supportType(delegateType)).findAny()
                .orElseThrow(() -> new HandlerException("未知的 pusher 类型: " + delegateType));
    }

    @Override
    public void dataFiltered(FilteredValue filteredValue) throws HandlerException {
        if (!drainFilteredValue) {
            delegate.dataFiltered(filteredValue);
        }
    }

    @Override
    public void dataFiltered(List<FilteredValue> filteredValues) throws HandlerException {
        if (!drainFilteredValue) {
            delegate.dataFiltered(filteredValues);
        }
    }

    @Override
    public void dataTriggered(TriggeredValue triggeredValue) throws HandlerException {
        if (!drainTriggeredValue) {
            delegate.dataTriggered(triggeredValue);
        }
    }

    @Override
    public void dataTriggered(List<TriggeredValue> triggeredValues) throws HandlerException {
        if (!drainTriggeredValue) {
            delegate.dataTriggered(triggeredValues);
        }
    }

    @Override
    public void realtimeUpdated(RealtimeValue realtimeValue) throws HandlerException {
        if (!drainRealtimeValue) {
            delegate.realtimeUpdated(realtimeValue);
        }
    }

    @Override
    public void realtimeUpdated(List<RealtimeValue> realtimeValues) throws HandlerException {
        if (!drainRealtimeValue) {
            delegate.realtimeUpdated(realtimeValues);
        }
    }

    @Override
    public void persistenceRecorded(PersistenceValue persistenceValue) throws HandlerException {
        if (!drainPersistenceValue) {
            delegate.persistenceRecorded(persistenceValue);
        }
    }

    @Override
    public void persistenceRecorded(List<PersistenceValue> persistenceValues) throws HandlerException {
        if (!drainPersistenceValue) {
            delegate.persistenceRecorded(persistenceValues);
        }
    }

    @Override
    public void recordReset() throws HandlerException {
        if (!drainRecordReset) {
            delegate.recordReset();
        }
    }

    @Override
    public void mapReset() throws HandlerException {
        if (!drainMapReset) {
            delegate.mapReset();
        }
    }

    public Pusher getDelegate() {
        return delegate;
    }

    public void setDelegate(Pusher delegate) {
        this.delegate = delegate;
    }

    public boolean isDrainRealtimeValue() {
        return drainRealtimeValue;
    }

    public void setDrainRealtimeValue(boolean drainRealtimeValue) {
        this.drainRealtimeValue = drainRealtimeValue;
    }

    public boolean isDrainPersistenceValue() {
        return drainPersistenceValue;
    }

    public void setDrainPersistenceValue(boolean drainPersistenceValue) {
        this.drainPersistenceValue = drainPersistenceValue;
    }

    public boolean isDrainFilteredValue() {
        return drainFilteredValue;
    }

    public void setDrainFilteredValue(boolean drainFilteredValue) {
        this.drainFilteredValue = drainFilteredValue;
    }

    public boolean isDrainTriggeredValue() {
        return drainTriggeredValue;
    }

    public void setDrainTriggeredValue(boolean drainTriggeredValue) {
        this.drainTriggeredValue = drainTriggeredValue;
    }

    public boolean isDrainRecordReset() {
        return drainRecordReset;
    }

    public void setDrainRecordReset(boolean drainRecordReset) {
        this.drainRecordReset = drainRecordReset;
    }

    public boolean isDrainMapReset() {
        return drainMapReset;
    }

    public void setDrainMapReset(boolean drainMapReset) {
        this.drainMapReset = drainMapReset;
    }

    @Override
    public String toString() {
        return "PartialDrainPusher{" +
                "pushers=" + pushers +
                ", delegateType='" + delegateType + '\'' +
                ", drainRealtimeValue=" + drainRealtimeValue +
                ", drainPersistenceValue=" + drainPersistenceValue +
                ", drainFilteredValue=" + drainFilteredValue +
                ", drainTriggeredValue=" + drainTriggeredValue +
                ", pusherType='" + pusherType + '\'' +
                '}';
    }

}
