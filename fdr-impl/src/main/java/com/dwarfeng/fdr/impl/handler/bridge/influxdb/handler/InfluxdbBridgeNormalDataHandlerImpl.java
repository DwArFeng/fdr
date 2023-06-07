package com.dwarfeng.fdr.impl.handler.bridge.influxdb.handler;

import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class InfluxdbBridgeNormalDataHandlerImpl extends AbstractInfluxdbBridgeDataHandler implements
        InfluxdbBridgeNormalDataHandler {

    @Value("${bridge.influxdb.bucket.normal_data}")
    private String bucket;

    @Value("${bridge.influxdb.organization}")
    private String organization;

    public InfluxdbBridgeNormalDataHandlerImpl(
            @Qualifier("influxdbBridge.writeApi") WriteApi writeApi,
            @Qualifier("influxdbBridge.queryApi") QueryApi queryApi
    ) {
        super(writeApi, queryApi);
    }

    @Override
    protected String getBucket() {
        return bucket;
    }

    @Override
    protected String getOrganization() {
        return organization;
    }

    @Override
    public String toString() {
        return "InfluxdbBridgeNormalDataHandlerImpl{" +
                "bucket='" + bucket + '\'' +
                ", organization='" + organization + '\'' +
                ", writeApi=" + writeApi +
                ", queryApi=" + queryApi +
                '}';
    }
}
