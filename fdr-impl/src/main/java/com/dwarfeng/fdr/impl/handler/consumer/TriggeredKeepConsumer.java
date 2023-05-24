package com.dwarfeng.fdr.impl.handler.consumer;

import com.dwarfeng.fdr.stack.bean.dto.TriggeredData;
import com.dwarfeng.fdr.stack.handler.KeepHandler;
import com.dwarfeng.fdr.stack.handler.PushHandler;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.apache.curator.framework.CuratorFramework;

import java.util.List;

public class TriggeredKeepConsumer extends KeepConsumer<TriggeredData> {

    public TriggeredKeepConsumer(
            KeepHandler<TriggeredData> keepHandler, PushHandler pushHandler, CuratorFramework curatorFramework,
            String latchPath
    ) {
        super(keepHandler, pushHandler, curatorFramework, latchPath);
    }

    @BehaviorAnalyse
    @Override
    public void consume(@SkipRecord List<TriggeredData> records) throws HandlerException {
        super.consume(records);
    }

    @Override
    protected void doPush(List<TriggeredData> records) throws HandlerException {
        pushHandler.triggeredUpdated(records);
    }

    @Override
    protected void doPush(TriggeredData record) throws HandlerException {
        pushHandler.triggeredUpdated(record);
    }
}
