package com.dwarfeng.fdr.impl.handler.bridge.redis;

import com.dwarfeng.dct.handler.ValueCodingHandler;
import com.dwarfeng.fdr.impl.handler.bridge.FulltKeeper;
import com.dwarfeng.fdr.impl.handler.bridge.redis.bean.RedisBridgeNormalData;
import com.dwarfeng.fdr.impl.handler.bridge.redis.service.RedisBridgeNormalDataMaintainService;
import com.dwarfeng.fdr.stack.bean.dto.NormalData;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Redis 桥接一般数据保持器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class RedisBridgeNormalDataKeeper extends FulltKeeper<NormalData> {

    private final RedisBridgeNormalDataMaintainService service;

    private final ValueCodingHandler valueCodingHandler;

    public RedisBridgeNormalDataKeeper(
            RedisBridgeNormalDataMaintainService service,
            @Qualifier("redisBridge.valueCodingHandler") ValueCodingHandler valueCodingHandler
    ) {
        this.service = service;
        this.valueCodingHandler = valueCodingHandler;
    }

    @Override
    protected void doUpdate(NormalData data) throws Exception {
        RedisBridgeNormalData normalData = transformData(data);
        service.insertOrUpdate(normalData);
    }

    @Override
    protected void doUpdate(List<NormalData> datas) throws Exception {
        List<RedisBridgeNormalData> normalDatas = new ArrayList<>();
        for (NormalData data : datas) {
            RedisBridgeNormalData normalData = transformData(data);
            normalDatas.add(normalData);
        }
        service.batchInsertOrUpdate(normalDatas);
    }

    private RedisBridgeNormalData transformData(NormalData data) throws Exception {
        if (Objects.isNull(data)) {
            return null;
        }
        String flatValue = valueCodingHandler.encode(data.getValue());
        return new RedisBridgeNormalData(
                data.getPointKey(),
                flatValue,
                data.getHappenedDate()
        );
    }

    @Override
    protected NormalData doLatest(LongIdKey pointKey) throws Exception {
        RedisBridgeNormalData normalData = service.getIfExists(pointKey);
        return reverseTransform(normalData);
    }

    @Override
    protected List<NormalData> doLatest(List<LongIdKey> pointKeys) throws Exception {
        List<RedisBridgeNormalData> normalDatas = service.batchGetIfExists(pointKeys);
        List<NormalData> datas = new ArrayList<>(normalDatas.size());
        for (RedisBridgeNormalData normalData : normalDatas) {
            datas.add(reverseTransform(normalData));
        }
        return datas;
    }

    private NormalData reverseTransform(RedisBridgeNormalData data) throws Exception {
        if (Objects.isNull(data)) {
            return null;
        }
        Object value = valueCodingHandler.decode(data.getValue());
        return new NormalData(
                data.getKey(),
                value,
                data.getHappenedDate()
        );
    }

    @Override
    public String toString() {
        return "RedisBridgeNormalDataKeeper{" +
                "service=" + service +
                ", valueCodingHandler=" + valueCodingHandler +
                '}';
    }
}
