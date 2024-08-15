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
    public void consume(@SkipRecord List<NormalData> datas) throws HandlerException {
        super.consume(datas);
    }

    @Override
    protected void doPush(List<NormalData> datas) throws HandlerException {
        pushHandler.normalUpdated(datas);
    }

    @Override
    protected void doPush(NormalData data) throws HandlerException {
        pushHandler.normalUpdated(data);
    }

    @Override
    public String toString() {
        return "NormalKeepConsumer{" +
                "keepHandler=" + keepHandler +
                ", pushHandler=" + pushHandler +
                ", curatorFramework=" + curatorFramework +
                ", latchPath='" + latchPath + '\'' +
                '}';
    }
}
