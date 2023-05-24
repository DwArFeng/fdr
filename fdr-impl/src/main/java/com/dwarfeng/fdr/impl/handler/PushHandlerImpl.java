package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.fdr.stack.bean.dto.NormalData;
import com.dwarfeng.fdr.stack.bean.dto.TriggeredData;
import com.dwarfeng.fdr.stack.handler.PushHandler;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class PushHandlerImpl implements PushHandler {

    private final List<Pusher> pushers;

    @Value("${pusher.type}")
    private String pusherType;

    private Pusher pusher;

    public PushHandlerImpl(List<Pusher> pushers) {
        this.pushers = Optional.ofNullable(pushers).orElse(Collections.emptyList());
    }

    @PostConstruct
    public void init() throws HandlerException {
        this.pusher = pushers.stream().filter(p -> p.supportType(pusherType)).findAny()
                .orElseThrow(() -> new HandlerException("未知的 pusher 类型: " + pusherType));
    }

    @Override
    public void normalUpdated(NormalData normalRecord) throws HandlerException {
        pusher.normalUpdated(normalRecord);
    }

    @Override
    public void normalUpdated(List<NormalData> normalRecords) throws HandlerException {
        pusher.normalUpdated(normalRecords);
    }

    @Override
    public void normalRecorded(NormalData normalRecord) throws HandlerException {
        pusher.normalRecorded(normalRecord);
    }

    @Override
    public void normalRecorded(List<NormalData> normalRecords) throws HandlerException {
        pusher.normalRecorded(normalRecords);
    }

    @Override
    public void filteredUpdated(FilteredData filteredRecord) throws HandlerException {
        pusher.filteredUpdated(filteredRecord);
    }

    @Override
    public void filteredUpdated(List<FilteredData> filteredRecords) throws HandlerException {
        pusher.filteredUpdated(filteredRecords);
    }

    @Override
    public void filteredRecorded(FilteredData filteredRecord) throws HandlerException {
        pusher.filteredRecorded(filteredRecord);
    }

    @Override
    public void filteredRecorded(List<FilteredData> filteredRecords) throws HandlerException {
        pusher.filteredRecorded(filteredRecords);
    }

    @Override
    public void triggeredUpdated(TriggeredData triggeredRecord) throws HandlerException {
        pusher.triggeredUpdated(triggeredRecord);
    }

    @Override
    public void triggeredUpdated(List<TriggeredData> triggeredRecords) throws HandlerException {
        pusher.triggeredUpdated(triggeredRecords);
    }

    @Override
    public void triggeredRecorded(TriggeredData triggeredRecord) throws HandlerException {
        pusher.triggeredRecorded(triggeredRecord);
    }

    @Override
    public void triggeredRecorded(List<TriggeredData> triggeredRecords) throws HandlerException {
        pusher.triggeredRecorded(triggeredRecords);
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
