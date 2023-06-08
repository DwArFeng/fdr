package com.dwarfeng.fdr.impl.handler.bridge.influxdb.handler;

import com.dwarfeng.fdr.impl.handler.bridge.influxdb.bean.dto.HibernateBridgeDefaultQueryInfo;
import com.dwarfeng.fdr.impl.handler.bridge.influxdb.bean.dto.HibernateBridgeLookupInfo;
import com.dwarfeng.fdr.impl.handler.bridge.influxdb.bean.dto.HibernateBridgeLookupResult;
import com.dwarfeng.fdr.impl.handler.bridge.influxdb.bean.dto.HibernateBridgeQueryResult;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;
import com.influxdb.client.write.Point;

import java.util.List;

/**
 * Influxdb 桥接器数据处理器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface InfluxdbBridgeDataHandler extends Handler {

    /**
     * 写入。
     *
     * @param point 指定的数据点。
     * @throws HandlerException 处理器异常。
     */
    void write(Point point) throws HandlerException;

    /**
     * 写入。
     *
     * @param points 指定的数据点组成的列表。
     * @throws HandlerException 处理器异常。
     */
    void write(List<Point> points) throws HandlerException;

    /**
     * 默认查看。
     *
     * @param lookupInfo 指定的查看信息。
     * @return 查看结果。
     * @throws HandlerException 处理器异常。
     */
    HibernateBridgeLookupResult lookup(HibernateBridgeLookupInfo lookupInfo) throws HandlerException;

    /**
     * 默认查询。
     *
     * <p>
     * 默认查询使用 InfluxDB 的 aggregateWindow 函数进行开窗聚合。
     *
     * @param queryInfo 指定的查询信息。
     * @return 查询结果。
     * @throws HandlerException 处理器异常。
     */
    HibernateBridgeQueryResult defaultQuery(HibernateBridgeDefaultQueryInfo queryInfo) throws HandlerException;
}
