package com.dwarfeng.fdr.impl.handler.consumer;

import com.dwarfeng.fdr.stack.bean.dto.TriggeredData;
import com.dwarfeng.fdr.stack.handler.PersistHandler;
import com.dwarfeng.fdr.stack.handler.PushHandler;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

import java.util.List;

public class TriggeredPersistConsumer extends PersistConsumer<TriggeredData> {

    public TriggeredPersistConsumer(PersistHandler<TriggeredData> persistHandler, PushHandler pushHandler) {
        super(persistHandler, pushHandler);
    }

    @BehaviorAnalyse
    @Override
    public void consume(@SkipRecord List<TriggeredData> datas) throws HandlerException {
        super.consume(datas);
    }

    @Override
    protected void doPush(List<TriggeredData> datas) throws HandlerException {
        pushHandler.triggeredRecorded(datas);
    }

    @Override
    protected void doPush(TriggeredData data) throws HandlerException {
        pushHandler.triggeredRecorded(data);
    }

    @Override
    public String toString() {
        return "TriggeredPersistConsumer{" +
                "persistHandler=" + persistHandler +
                ", pushHandler=" + pushHandler +
                '}';
    }
}
