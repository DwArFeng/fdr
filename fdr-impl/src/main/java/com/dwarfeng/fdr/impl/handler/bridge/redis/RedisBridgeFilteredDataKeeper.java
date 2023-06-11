package com.dwarfeng.fdr.impl.handler.bridge.redis;

import com.dwarfeng.dct.handler.ValueCodingHandler;
import com.dwarfeng.fdr.impl.handler.bridge.FullKeeper;
import com.dwarfeng.fdr.impl.handler.bridge.redis.bean.RedisBridgeFilteredData;
import com.dwarfeng.fdr.impl.handler.bridge.redis.service.RedisBridgeFilteredDataMaintainService;
import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Redis 桥接被过滤数据保持器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class RedisBridgeFilteredDataKeeper extends FullKeeper<FilteredData> {

    private final RedisBridgeFilteredDataMaintainService service;

    private final ValueCodingHandler valueCodingHandler;

    public RedisBridgeFilteredDataKeeper(
            RedisBridgeFilteredDataMaintainService service,
            @Qualifier("redisBridge.valueCodingHandler") ValueCodingHandler valueCodingHandler
    ) {
        this.service = service;
        this.valueCodingHandler = valueCodingHandler;
    }

    @Override
    protected void doUpdate(FilteredData data) throws Exception {
        RedisBridgeFilteredData filteredData = transform(data);
        service.insertOrUpdate(filteredData);
    }

    @Override
    protected void doUpdate(List<FilteredData> datas) throws Exception {
        List<RedisBridgeFilteredData> filteredDatas = new ArrayList<>();
        for (FilteredData data : datas) {
            RedisBridgeFilteredData filteredData = transform(data);
            filteredDatas.add(filteredData);
        }
        service.batchInsertOrUpdate(filteredDatas);
    }

    private RedisBridgeFilteredData transform(FilteredData data) throws Exception {
        if (Objects.isNull(data)) {
            return null;
        }
        String flatValue = valueCodingHandler.encode(data.getValue());
        return new RedisBridgeFilteredData(
                data.getPointKey(),
                data.getFilterKey(),
                flatValue,
                data.getMessage(),
                data.getHappenedDate()
        );
    }

    @Override
    protected FilteredData doLatest(LongIdKey pointKey) throws Exception {
        RedisBridgeFilteredData filteredData = service.getIfExists(pointKey);
        return reverseTransform(filteredData);
    }

    @Override
    protected List<FilteredData> doLatest(List<LongIdKey> pointKeys) throws Exception {
        List<RedisBridgeFilteredData> filteredDatas = service.batchGetIfExists(pointKeys);
        List<FilteredData> datas = new ArrayList<>(filteredDatas.size());
        for (RedisBridgeFilteredData filteredData : filteredDatas) {
            datas.add(reverseTransform(filteredData));
        }
        return datas;
    }

    private FilteredData reverseTransform(RedisBridgeFilteredData data) throws Exception {
        if (Objects.isNull(data)) {
            return null;
        }
        Object value = valueCodingHandler.decode(data.getValue());
        return new FilteredData(
                data.getKey(),
                data.getFilterKey(),
                value,
                data.getMessage(),
                data.getHappenedDate()
        );
    }

    @Override
    public String toString() {
        return "RedisBridgeFilteredDataKeeper{" +
                "service=" + service +
                ", valueCodingHandler=" + valueCodingHandler +
                '}';
    }
}
