package com.dwarfeng.fdr.impl.handler.bridge.influxdb.handler;

import com.dwarfeng.fdr.impl.handler.bridge.influxdb.bean.dto.HibernateBridgeQueryInfo;
import com.dwarfeng.fdr.impl.handler.bridge.influxdb.bean.dto.HibernateBridgeQueryResult;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApi;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;

import java.text.SimpleDateFormat;
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
    public void write(Point point) {
        writeApi.writePoint(getBucket(), getOrganization(), point);
    }

    public void write(List<Point> points) {
        writeApi.writePoints(getBucket(), getOrganization(), points);
    }

    @Override
    public HibernateBridgeQueryResult defaultQuery(HibernateBridgeQueryInfo queryInfo) {
        return singleDefaultQuery(queryInfo);
    }

    @Override
    public List<HibernateBridgeQueryResult> defaultQuery(List<HibernateBridgeQueryInfo> queryInfo) {
        List<HibernateBridgeQueryResult> resultList = new ArrayList<>();
        for (HibernateBridgeQueryInfo hibernateBridgeQueryInfo : queryInfo) {
            resultList.add(singleDefaultQuery(hibernateBridgeQueryInfo));
        }
        return resultList;
    }

    private HibernateBridgeQueryResult singleDefaultQuery(HibernateBridgeQueryInfo hibernateBridgeQueryInfo) {
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
                simpleDateFormat.format(hibernateBridgeQueryInfo.getRangeStart()),
                simpleDateFormat.format(hibernateBridgeQueryInfo.getRangeStop()),
                hibernateBridgeQueryInfo.getMeasurement(),
                hibernateBridgeQueryInfo.getLimitNumber(),
                hibernateBridgeQueryInfo.getLimitOffset()
        );

        // 查询数据。
        // 由于 InfluxDB 的查询语句中，做了 pivot 操作，因此返回的 fluxTables 不多于 1 个。
        List<FluxTable> fluxTables = queryApi.query(flux, getOrganization());

        // 转换数据并返回。
        List<HibernateBridgeQueryResult.Item> items = new ArrayList<>();
        if (!fluxTables.isEmpty()) {
            for (FluxRecord record : fluxTables.get(0).getRecords()) {
                items.add(new HibernateBridgeQueryResult.Item(
                        hibernateBridgeQueryInfo.getMeasurement(),
                        record.getValues(),
                        record.getTime()
                ));
            }
        }
        return new HibernateBridgeQueryResult(hibernateBridgeQueryInfo.getMeasurement(), items);
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
