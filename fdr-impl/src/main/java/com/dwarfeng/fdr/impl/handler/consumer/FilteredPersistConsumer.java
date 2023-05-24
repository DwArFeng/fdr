package com.dwarfeng.fdr.impl.handler.consumer;

import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.fdr.stack.handler.PersistHandler;
import com.dwarfeng.fdr.stack.handler.PushHandler;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

import java.util.List;

public class FilteredPersistConsumer extends PersistConsumer<FilteredData> {

    public FilteredPersistConsumer(PersistHandler<FilteredData> persistHandler, PushHandler pushHandler) {
        super(persistHandler, pushHandler);
    }

    @BehaviorAnalyse
    @Override
    public void consume(@SkipRecord List<FilteredData> records) throws HandlerException {
        super.consume(records);
    }

    @Override
    protected void doPush(List<FilteredData> records) throws HandlerException {
        pushHandler.filteredRecorded(records);
    }

    @Override
    protected void doPush(FilteredData record) throws HandlerException {
        pushHandler.filteredRecorded(record);
    }
}
