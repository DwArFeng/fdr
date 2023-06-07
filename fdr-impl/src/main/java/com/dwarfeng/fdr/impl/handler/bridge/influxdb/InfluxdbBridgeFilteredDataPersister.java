package com.dwarfeng.fdr.impl.handler.bridge.influxdb;

import com.dwarfeng.fdr.impl.handler.bridge.influxdb.bean.dto.HibernateBridgeQueryResult;
import com.dwarfeng.fdr.impl.handler.bridge.influxdb.handler.InfluxdbBridgeFilteredDataHandler;
import com.dwarfeng.fdr.impl.handler.bridge.influxdb.util.Constants;
import com.dwarfeng.fdr.impl.handler.bridge.influxdb.util.DateUtil;
import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Influxdb 桥接器被过滤数据持久化器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class InfluxdbBridgeFilteredDataPersister extends InfluxdbBridgeDataPersister<FilteredData> {

    public InfluxdbBridgeFilteredDataPersister(
            InfluxdbBridgeFilteredDataHandler handler
    ) {
        super(handler);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected Point dataToPoint(FilteredData data) {
        Point point = new Point(Long.toString(data.getPointKey().getLongId()));
        Map<String, Object> fieldMap = new HashMap<>();
        if (Objects.nonNull(data.getFilterKey())) {
            fieldMap.put(Constants.FIELD_NAME_FILTER_ID, data.getFilterKey().getLongId());
        }
        if (Objects.nonNull(data.getValue())) {
            fieldMap.put(Constants.FIELD_NAME_VALUE, data.getValue());
        }
        if (Objects.nonNull(data.getMessage())) {
            fieldMap.put(Constants.FILED_NAME_MESSAGE, data.getMessage());
        }
        point.addFields(fieldMap);
        point.time(DateUtil.date2Instant(data.getHappenedDate()), WritePrecision.MS);
        return point;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected FilteredData itemToData(HibernateBridgeQueryResult.Item item) {
        LongIdKey pointKey = new LongIdKey(Long.parseLong(item.getMeasurement()));
        LongIdKey filterKey = null;
        Object value = null;
        String message = null;
        Map<String, Object> valueMap = item.getValueMap();
        Object filedValue;
        if (Objects.nonNull((filedValue = valueMap.get(Constants.FIELD_NAME_FILTER_ID)))) {
            filterKey = new LongIdKey((Long) filedValue);
        }
        if (Objects.nonNull((filedValue = valueMap.get(Constants.FIELD_NAME_VALUE)))) {
            value = filedValue;
        }
        if (Objects.nonNull((filedValue = valueMap.get(Constants.FILED_NAME_MESSAGE)))) {
            message = (String) filedValue;
        }
        Date happenedDate = DateUtil.instant2Date(item.getHappenedInstant());
        return new FilteredData(pointKey, filterKey, value, message, happenedDate);
    }

    @Override
    public String toString() {
        return "InfluxdbBridgeFilteredDataPersister{" +
                "handler=" + handler +
                '}';
    }
}
