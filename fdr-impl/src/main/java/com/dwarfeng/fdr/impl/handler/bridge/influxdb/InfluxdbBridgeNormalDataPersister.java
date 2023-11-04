package com.dwarfeng.fdr.impl.handler.bridge.influxdb;

import com.dwarfeng.fdr.impl.handler.bridge.influxdb.bean.dto.InfluxdbBridgeLookupResult;
import com.dwarfeng.fdr.impl.handler.bridge.influxdb.handler.InfluxdbBridgeNormalDataHandler;
import com.dwarfeng.fdr.impl.handler.bridge.influxdb.util.Constants;
import com.dwarfeng.fdr.impl.handler.bridge.influxdb.util.DateUtil;
import com.dwarfeng.fdr.stack.bean.dto.NormalData;
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
 * Influxdb 桥接器一般数据持久器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class InfluxdbBridgeNormalDataPersister extends InfluxdbBridgePersister<NormalData> {

    public InfluxdbBridgeNormalDataPersister(
            InfluxdbBridgeNormalDataHandler handler,
            ThreadPoolTaskExecutor executor
    ) {
        super(handler, executor);
    }

    @Override
    protected Point dataToPoint(NormalData data) {
        Point point = new Point(Long.toString(data.getPointKey().getLongId()));
        Map<String, Object> fieldMap = new HashMap<>();
        if (Objects.nonNull(data.getValue())) {
            fieldMap.put(Constants.FIELD_NAME_VALUE, data.getValue());
        }
        point.addFields(fieldMap);
        point.time(DateUtil.date2Instant(data.getHappenedDate()), WritePrecision.MS);
        return point;
    }

    @Override
    protected NormalData itemToData(InfluxdbBridgeLookupResult.Item item) {
        LongIdKey pointKey = new LongIdKey(Long.parseLong(item.getMeasurement()));
        Object value = null;
        Map<String, Object> valueMap = item.getValueMap();
        Object filedValue;
        if (Objects.nonNull((filedValue = valueMap.get(Constants.FIELD_NAME_VALUE)))) {
            value = filedValue;
        }
        Date happenedDate = DateUtil.instant2Date(item.getHappenedInstant());
        return new NormalData(pointKey, value, happenedDate);
    }

    @Override
    public String toString() {
        return "InfluxdbBridgeNormalDataPersister{" +
                "handler=" + handler +
                ", executor=" + executor +
                '}';
    }
}
