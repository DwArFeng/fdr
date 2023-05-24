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
    public void consume(@SkipRecord List<TriggeredData> records) throws HandlerException {
        super.consume(records);
    }

    @Override
    protected void doPush(List<TriggeredData> records) throws HandlerException {
        pushHandler.triggeredRecorded(records);
    }

    @Override
    protected void doPush(TriggeredData record) throws HandlerException {
        pushHandler.triggeredRecorded(record);
    }
}
