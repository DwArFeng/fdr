package com.dwarfeng.fdr.impl.handler.consumer;

import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.fdr.stack.handler.KeepHandler;
import com.dwarfeng.fdr.stack.handler.PushHandler;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.apache.curator.framework.CuratorFramework;

import java.util.List;

public class FilteredKeepConsumer extends KeepConsumer<FilteredData> {

    public FilteredKeepConsumer(
            KeepHandler<FilteredData> keepHandler, PushHandler pushHandler, CuratorFramework curatorFramework,
            String latchPath
    ) {
        super(keepHandler, pushHandler, curatorFramework, latchPath);
    }

    @BehaviorAnalyse
    @Override
    public void consume(@SkipRecord List<FilteredData> datas) throws HandlerException {
        super.consume(datas);
    }

    @Override
    protected void doPush(List<FilteredData> datas) throws HandlerException {
        pushHandler.filteredUpdated(datas);
    }

    @Override
    protected void doPush(FilteredData data) throws HandlerException {
        pushHandler.filteredUpdated(data);
    }

    @Override
    public String toString() {
        return "FilteredKeepConsumer{" +
                "keepHandler=" + keepHandler +
                ", pushHandler=" + pushHandler +
                ", curatorFramework=" + curatorFramework +
                ", latchPath='" + latchPath + '\'' +
                '}';
    }
}
