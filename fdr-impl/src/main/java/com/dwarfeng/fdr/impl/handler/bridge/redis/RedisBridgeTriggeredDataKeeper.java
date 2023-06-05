package com.dwarfeng.fdr.impl.handler.bridge.redis;

import com.dwarfeng.dct.handler.ValueCodingHandler;
import com.dwarfeng.fdr.impl.handler.bridge.FulltKeeper;
import com.dwarfeng.fdr.impl.handler.bridge.redis.bean.RedisBridgeTriggeredData;
import com.dwarfeng.fdr.impl.handler.bridge.redis.service.RedisBridgeTriggeredDataMaintainService;
import com.dwarfeng.fdr.stack.bean.dto.TriggeredData;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Redis 桥接被触发数据保持器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class RedisBridgeTriggeredDataKeeper extends FulltKeeper<TriggeredData> {

    private final RedisBridgeTriggeredDataMaintainService service;

    private final ValueCodingHandler valueCodingHandler;

    public RedisBridgeTriggeredDataKeeper(
            RedisBridgeTriggeredDataMaintainService service,
            @Qualifier("redisBridge.valueCodingHandler") ValueCodingHandler valueCodingHandler
    ) {
        this.service = service;
        this.valueCodingHandler = valueCodingHandler;
    }

    @Override
    protected void doUpdate(TriggeredData data) throws Exception {
        RedisBridgeTriggeredData triggeredData = transform(data);
        service.insertOrUpdate(triggeredData);
    }

    @Override
    protected void doUpdate(List<TriggeredData> datas) throws Exception {
        List<RedisBridgeTriggeredData> triggeredDatas = new ArrayList<>();
        for (TriggeredData data : datas) {
            RedisBridgeTriggeredData triggeredData = transform(data);
            triggeredDatas.add(triggeredData);
        }
        service.batchInsertOrUpdate(triggeredDatas);
    }

    private RedisBridgeTriggeredData transform(TriggeredData data) throws Exception {
        if (Objects.isNull(data)) {
            return null;
        }
        String flatValue = valueCodingHandler.encode(data.getValue());
        return new RedisBridgeTriggeredData(
                data.getPointKey(),
                data.getTriggerKey(),
                flatValue,
                data.getMessage(),
                data.getHappenedDate()
        );
    }

    @Override
    protected TriggeredData doLatest(LongIdKey pointKey) throws Exception {
        RedisBridgeTriggeredData triggeredData = service.getIfExists(pointKey);
        return reverseTransform(triggeredData);
    }

    @Override
    protected List<TriggeredData> doLatest(List<LongIdKey> pointKeys) throws Exception {
        List<RedisBridgeTriggeredData> triggeredDatas = service.batchGetIfExists(pointKeys);
        List<TriggeredData> datas = new ArrayList<>(triggeredDatas.size());
        for (RedisBridgeTriggeredData triggeredData : triggeredDatas) {
            datas.add(reverseTransform(triggeredData));
        }
        return datas;
    }

    private TriggeredData reverseTransform(RedisBridgeTriggeredData data) throws Exception {
        if (Objects.isNull(data)) {
            return null;
        }
        Object value = valueCodingHandler.decode(data.getValue());
        return new TriggeredData(
                data.getKey(),
                data.getTriggerKey(),
                value,
                data.getMessage(),
                data.getHappenedDate()
        );
    }

    @Override
    public String toString() {
        return "RedisBridgeTriggeredDataKeeper{" +
                "service=" + service +
                ", valueCodingHandler=" + valueCodingHandler +
                '}';
    }
}
