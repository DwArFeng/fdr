package com.dwarfeng.fdr.impl.handler.consumer;

import com.dwarfeng.fdr.stack.bean.dto.NormalData;
import com.dwarfeng.fdr.stack.handler.KeepHandler;
import com.dwarfeng.fdr.stack.handler.PushHandler;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.apache.curator.framework.CuratorFramework;

import java.util.List;

public class NormalKeepConsumer extends KeepConsumer<NormalData> {

    public NormalKeepConsumer(
            KeepHandler<NormalData> keepHandler, PushHandler pushHandler, CuratorFramework curatorFramework,
            String latchPath
    ) {
        super(keepHandler, pushHandler, curatorFramework, latchPath);
    }

    @BehaviorAnalyse
    @Override
    public void consume(@SkipRecord List<NormalData> records) throws HandlerException {
        super.consume(records);
    }

    @Override
    protected void doPush(List<NormalData> records) throws HandlerException {
        pushHandler.normalUpdated(records);
    }

    @Override
    protected void doPush(NormalData record) throws HandlerException {
        pushHandler.normalUpdated(record);
    }
}
