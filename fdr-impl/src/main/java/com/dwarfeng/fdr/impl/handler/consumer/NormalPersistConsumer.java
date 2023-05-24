package com.dwarfeng.fdr.impl.handler.consumer;

import com.dwarfeng.fdr.stack.bean.dto.NormalData;
import com.dwarfeng.fdr.stack.handler.PersistHandler;
import com.dwarfeng.fdr.stack.handler.PushHandler;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

import java.util.List;

public class NormalPersistConsumer extends PersistConsumer<NormalData> {

    public NormalPersistConsumer(PersistHandler<NormalData> persistHandler, PushHandler pushHandler) {
        super(persistHandler, pushHandler);
    }

    @BehaviorAnalyse
    @Override
    public void consume(@SkipRecord List<NormalData> records) throws HandlerException {
        super.consume(records);
    }

    @Override
    protected void doPush(List<NormalData> records) throws HandlerException {
        pushHandler.normalRecorded(records);
    }

    @Override
    protected void doPush(NormalData record) throws HandlerException {
        pushHandler.normalRecorded(record);
    }
}
