package com.dwarfeng.fdr.impl.handler.bridge.influxdb;

import com.dwarfeng.fdr.impl.handler.bridge.influxdb.bean.dto.HibernateBridgeLookupResult;
import com.dwarfeng.fdr.impl.handler.bridge.influxdb.handler.InfluxdbBridgeTriggeredDataHandler;
import com.dwarfeng.fdr.impl.handler.bridge.influxdb.util.Constants;
import com.dwarfeng.fdr.impl.handler.bridge.influxdb.util.DateUtil;
import com.dwarfeng.fdr.stack.bean.dto.TriggeredData;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Influxdb 桥接器被过滤数据持久器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class InfluxdbBridgeTriggeredDataPersister extends InfluxdbBridgePersister<TriggeredData> {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public InfluxdbBridgeTriggeredDataPersister(
            InfluxdbBridgeTriggeredDataHandler handler,
            ThreadPoolTaskExecutor executor
    ) {
        super(handler, executor);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected Point dataToPoint(TriggeredData data) {
        Point point = new Point(Long.toString(data.getPointKey().getLongId()));
        Map<String, Object> fieldMap = new HashMap<>();
        if (Objects.nonNull(data.getTriggerKey())) {
            fieldMap.put(Constants.FIELD_NAME_TRIGGER_ID, data.getTriggerKey().getLongId());
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
    protected TriggeredData itemToData(HibernateBridgeLookupResult.Item item) {
        LongIdKey pointKey = new LongIdKey(Long.parseLong(item.getMeasurement()));
        LongIdKey triggerKey = null;
        Object value = null;
        String message = null;
        Map<String, Object> valueMap = item.getValueMap();
        Object filedValue;
        if (Objects.nonNull((filedValue = valueMap.get(Constants.FIELD_NAME_TRIGGER_ID)))) {
            triggerKey = new LongIdKey((Long) filedValue);
        }
        if (Objects.nonNull((filedValue = valueMap.get(Constants.FIELD_NAME_VALUE)))) {
            value = filedValue;
        }
        if (Objects.nonNull((filedValue = valueMap.get(Constants.FILED_NAME_MESSAGE)))) {
            message = (String) filedValue;
        }
        Date happenedDate = DateUtil.instant2Date(item.getHappenedInstant());
        return new TriggeredData(pointKey, triggerKey, value, message, happenedDate);
    }

    @Override
    public String toString() {
        return "InfluxdbBridgeTriggeredDataPersister{" +
                "handler=" + handler +
                ", executor=" + executor +
                '}';
    }
}
