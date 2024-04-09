package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.bean.entity.RealtimeValue;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.fdr.stack.handler.PushHandler;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

@Component
public class PushHandlerImpl implements PushHandler {

    @Autowired(required = false)
    private List<Pusher> pushers = Collections.emptyList();

    @Value("${pusher.type}")
    private String pusherType;

    private Pusher pusher;

    @PostConstruct
    public void init() throws HandlerException {
        this.pusher = pushers.stream().filter(p -> p.supportType(pusherType)).findAny()
                .orElseThrow(() -> new HandlerException("未知的 pusher 类型: " + pusherType));
    }

    @Override
    public void dataFiltered(FilteredValue filteredValue) throws HandlerException {
        pusher.dataFiltered(filteredValue);
    }

    @Override
    public void dataFiltered(List<FilteredValue> filteredValues) throws HandlerException {
        pusher.dataFiltered(filteredValues);
    }

    @Override
    public void dataTriggered(TriggeredValue triggeredValue) throws HandlerException {
        pusher.dataTriggered(triggeredValue);
    }

    @Override
    public void dataTriggered(List<TriggeredValue> triggeredValues) throws HandlerException {
        pusher.dataTriggered(triggeredValues);
    }

    @Override
    public void realtimeUpdated(RealtimeValue realtimeValue) throws HandlerException {
        pusher.realtimeUpdated(realtimeValue);
    }

    @Override
    public void realtimeUpdated(List<RealtimeValue> realtimeValues) throws HandlerException {
        pusher.realtimeUpdated(realtimeValues);
    }

    @Override
    public void persistenceRecorded(PersistenceValue persistenceValue) throws HandlerException {
        pusher.persistenceRecorded(persistenceValue);
    }

    @Override
    public void persistenceRecorded(List<PersistenceValue> persistenceValues) throws HandlerException {
        pusher.persistenceRecorded(persistenceValues);
    }

    @Override
    public void recordReset() throws HandlerException {
        pusher.recordReset();
    }

    @Override
    public void mapReset() throws HandlerException {
        pusher.mapReset();
    }
}
