package com.dwarfeng.fdr.impl.handler.pusher;

import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.bean.entity.RealtimeValue;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

import java.util.List;

/**
 * 推送器适配器。
 *
 * <p>
 * 该类是一个适配器，对所有的事件推送方法均进行空实现。
 *
 * <p>
 * 所有的插件实现推送器时都建议继承该类，这样做的好处是：
 * <ul>
 *     <li>适配器对所有的事件推送方法均进行空实现，因此插件实现推送器时只需要重写需要的方法即可。</li>
 *     <li>当服务版本升级，增加新事件时，旧的推送器实现无须增加代码，因为新的事件推送方法在适配器中已经有了空实现。</li>
 * </ul>
 *
 * @author DwArFeng
 * @since 1.11.5
 */
public abstract class PusherAdapter extends AbstractPusher {

    public PusherAdapter() {
        super();
    }

    public PusherAdapter(String pusherType) {
        super(pusherType);
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    public void dataFiltered(FilteredValue filteredValue) throws HandlerException {
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    public void dataFiltered(List<FilteredValue> filteredValues) throws HandlerException {
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    public void dataTriggered(TriggeredValue triggeredValue) throws HandlerException {
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    public void dataTriggered(List<TriggeredValue> triggeredValues) throws HandlerException {
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    public void realtimeUpdated(RealtimeValue realtimeValue) throws HandlerException {
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    public void realtimeUpdated(List<RealtimeValue> realtimeValues) throws HandlerException {
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    public void persistenceRecorded(PersistenceValue persistenceValue) throws HandlerException {
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    public void persistenceRecorded(List<PersistenceValue> persistenceValues) throws HandlerException {
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    public void recordReset() throws HandlerException {
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    public void mapReset() throws HandlerException {
    }

    @Override
    public String toString() {
        return "PusherAdapter{" +
                "pusherType='" + pusherType + '\'' +
                '}';
    }
}
