package com.dwarfeng.fdr.impl.handler.bridge.influxdb.handler;

import com.dwarfeng.fdr.impl.handler.bridge.influxdb.bean.dto.HibernateBridgeDefaultQueryInfo;
import com.dwarfeng.fdr.impl.handler.bridge.influxdb.bean.dto.HibernateBridgeLookupInfo;
import com.dwarfeng.fdr.impl.handler.bridge.influxdb.bean.dto.HibernateBridgeLookupResult;
import com.dwarfeng.fdr.impl.handler.bridge.influxdb.bean.dto.HibernateBridgeQueryResult;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApi;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Influxdb 桥接器数据处理器的抽象实现。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public abstract class AbstractInfluxdbBridgeDataHandler implements InfluxdbBridgeDataHandler {

    protected final WriteApi writeApi;
    protected final QueryApi queryApi;

    public AbstractInfluxdbBridgeDataHandler(WriteApi writeApi, QueryApi queryApi) {
        this.writeApi = writeApi;
        this.queryApi = queryApi;
    }

    @Override
    public void write(Point point) throws HandlerException {
        try {
            writeApi.writePoint(getBucket(), getOrganization(), point);
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    public void write(List<Point> points) throws HandlerException {
        try {
            writeApi.writePoints(getBucket(), getOrganization(), points);
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public HibernateBridgeLookupResult lookup(HibernateBridgeLookupInfo lookupInfo) throws HandlerException {
        try {
            // 构造查询语句模板。
            String fluxFormat = "from(bucket: \"%1$s\")\n" +
                    " |> range(start: %2$s, stop: %3$s)\n" +
                    " |> filter(fn: (r) => r[\"_measurement\"] == \"%4$s\")\n" +
                    " |> limit(n: %5$d, offset: %6$d)\n" +
                    " |> pivot(rowKey: [\"_time\"], columnKey: [\"_field\"], valueColumn: \"_value\")";

            // 格式化查询语句。
            @SuppressWarnings("SpellCheckingInspection")
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            String flux = String.format(fluxFormat,
                    getBucket(),
                    simpleDateFormat.format(lookupInfo.getRangeStart()),
                    simpleDateFormat.format(lookupInfo.getRangeStop()),
                    lookupInfo.getMeasurement(),
                    lookupInfo.getLimitNumber(),
                    lookupInfo.getLimitOffset()
            );

            // 查询数据。
            // 由于 InfluxDB 的查询语句中，做了 pivot 操作，因此返回的 fluxTables 不多于 1 个。
            List<FluxTable> fluxTables = queryApi.query(flux, getOrganization());

            // 转换数据并返回。
            List<HibernateBridgeLookupResult.Item> items = new ArrayList<>();
            if (!fluxTables.isEmpty()) {
                for (FluxRecord record : fluxTables.get(0).getRecords()) {
                    items.add(new HibernateBridgeLookupResult.Item(
                            lookupInfo.getMeasurement(),
                            record.getValues(),
                            record.getTime()
                    ));
                }
            }
            return new HibernateBridgeLookupResult(lookupInfo.getMeasurement(), items);
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public HibernateBridgeQueryResult defaultQuery(HibernateBridgeDefaultQueryInfo queryInfo) throws HandlerException {
        try {
            // 构造查询语句模板。
            String fluxFormat = "from(bucket: \"%1$s\")\n" +
                    " |> range(start: %2$s, stop: %3$s)\n" +
                    " |> filter(fn: (r) => %4$s)\n" +
                    " |> filter(fn: (r) => r[\"_field\"] == \"value\")\n" +
                    " |> aggregateWindow(every: %5$dms, offset: %6$dms, fn:%7$s)";

            // 格式化查询语句。
            String measurementPattern = generateMeasurementPattern(queryInfo.getMeasurements());
            @SuppressWarnings("SpellCheckingInspection")
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            String flux = String.format(fluxFormat,
                    getBucket(),
                    simpleDateFormat.format(queryInfo.getRangeStart()),
                    simpleDateFormat.format(queryInfo.getRangeStop()),
                    measurementPattern,
                    queryInfo.getAggregateWindowEvery(),
                    queryInfo.getAggregateWindowOffset(),
                    queryInfo.getAggregateWindowFn()
            );

            // 查询数据。
            List<FluxTable> fluxTables = queryApi.query(flux, getOrganization());

            // 转换数据并返回。
            List<HibernateBridgeQueryResult.HibernateBridgeSequence> sequences = new ArrayList<>(fluxTables.size());
            for (FluxTable fluxTable : fluxTables) {
                List<FluxRecord> records = fluxTable.getRecords();
                List<HibernateBridgeQueryResult.HibernateBridgeItem> items = new ArrayList<>(records.size());

                // 需要保证 fluxTable 中至少有一条记录，否则跳过。
                if (records.isEmpty()) {
                    continue;
                }
                FluxRecord firstRecord = records.get(0);
                String measurement = firstRecord.getMeasurement();
                Instant start = firstRecord.getStart();
                Instant stop = firstRecord.getStop();

                for (FluxRecord record : records) {
                    Object value = record.getValue();
                    items.add(new HibernateBridgeQueryResult.HibernateBridgeItem(
                            record.getMeasurement(), value, record.getTime()
                    ));
                }
                sequences.add(new HibernateBridgeQueryResult.HibernateBridgeSequence(measurement, items, start, stop));
            }
            return new HibernateBridgeQueryResult(sequences);
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    private String generateMeasurementPattern(List<String> measurements) {
        // 特殊情况：如果 measurements 为空，返回预设的查询模式文本，以便于快速返回空结果。
        if (measurements.isEmpty()) {
            return "r[\"_measurement\"] == \"-12450\" and r[\"_measurement\"] == \"-114514\"";
        }

        String singlePatternFormat = "r[\"_measurement\"] == \"%s\"";
        List<String> patterns = new ArrayList<>(measurements.size());
        for (String measurement : measurements) {
            patterns.add(String.format(singlePatternFormat, measurement));
        }
        return String.join(" or ", patterns);
    }

    protected abstract String getBucket();

    protected abstract String getOrganization();

    @Override
    public String toString() {
        return "AbstractInfluxdbBridgeDataHandler{" +
                "writeApi=" + writeApi +
                ", queryApi=" + queryApi +
                '}';
    }
}
