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
    public void normalUpdated(NormalData normalData) throws HandlerException {
        pusher.normalUpdated(normalData);
    }

    @Override
    public void normalUpdated(List<NormalData> normalDatas) throws HandlerException {
        pusher.normalUpdated(normalDatas);
    }

    @Override
    public void normalRecorded(NormalData normalData) throws HandlerException {
        pusher.normalRecorded(normalData);
    }

    @Override
    public void normalRecorded(List<NormalData> normalDatas) throws HandlerException {
        pusher.normalRecorded(normalDatas);
    }

    @Override
    public void filteredUpdated(FilteredData filteredData) throws HandlerException {
        pusher.filteredUpdated(filteredData);
    }

    @Override
    public void filteredUpdated(List<FilteredData> filteredDatas) throws HandlerException {
        pusher.filteredUpdated(filteredDatas);
    }

    @Override
    public void filteredRecorded(FilteredData filteredData) throws HandlerException {
        pusher.filteredRecorded(filteredData);
    }

    @Override
    public void filteredRecorded(List<FilteredData> filteredDatas) throws HandlerException {
        pusher.filteredRecorded(filteredDatas);
    }

    @Override
    public void triggeredUpdated(TriggeredData triggeredData) throws HandlerException {
        pusher.triggeredUpdated(triggeredData);
    }

    @Override
    public void triggeredUpdated(List<TriggeredData> triggeredDatas) throws HandlerException {
        pusher.triggeredUpdated(triggeredDatas);
    }

    @Override
    public void triggeredRecorded(TriggeredData triggeredData) throws HandlerException {
        pusher.triggeredRecorded(triggeredData);
    }

    @Override
    public void triggeredRecorded(List<TriggeredData> triggeredDatas) throws HandlerException {
        pusher.triggeredRecorded(triggeredDatas);
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
