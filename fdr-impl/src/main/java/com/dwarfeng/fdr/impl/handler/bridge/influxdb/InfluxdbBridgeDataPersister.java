package com.dwarfeng.fdr.impl.handler.bridge.influxdb;

import com.dwarfeng.fdr.impl.handler.bridge.FullPersister;
import com.dwarfeng.fdr.impl.handler.bridge.influxdb.bean.dto.HibernateBridgeQueryInfo;
import com.dwarfeng.fdr.impl.handler.bridge.influxdb.bean.dto.HibernateBridgeQueryResult;
import com.dwarfeng.fdr.impl.handler.bridge.influxdb.handler.InfluxdbBridgeDataHandler;
import com.dwarfeng.fdr.impl.handler.bridge.influxdb.util.DateUtil;
import com.dwarfeng.fdr.sdk.util.WatchUtil;
import com.dwarfeng.fdr.stack.bean.dto.LookupInfo;
import com.dwarfeng.fdr.stack.bean.dto.LookupResult;
import com.dwarfeng.fdr.stack.struct.Data;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.influxdb.client.write.Point;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Influxdb 桥接器一般数据持久化器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public abstract class InfluxdbBridgeDataPersister<D extends Data> extends FullPersister<D> {

    public static final String DEFAULT = "default";

    protected final InfluxdbBridgeDataHandler handler;

    protected InfluxdbBridgeDataPersister(InfluxdbBridgeDataHandler handler) {
        this.handler = handler;
    }

    @Override
    protected void doRecord(D data) throws Exception {
        Point point = dataToPoint(data);
        handler.write(point);
    }

    @Override
    protected void doRecord(List<D> datas) throws Exception {
        List<Point> points = datas.stream().map(this::dataToPoint).collect(Collectors.toList());
        handler.write(points);
    }

    protected abstract Point dataToPoint(D data);

    @Override
    protected LookupResult<D> doQuery(LookupInfo lookupInfo) throws Exception {
        return doSingleQuery(lookupInfo);
    }

    @Override
    protected List<LookupResult<D>> doQuery(List<LookupInfo> lookupInfos) throws Exception {
        List<LookupResult<D>> resultList = new ArrayList<>();
        for (LookupInfo lookupInfo : lookupInfos) {
            resultList.add(doSingleQuery(lookupInfo));
        }
        return resultList;
    }

    @SuppressWarnings("DuplicatedCode")
    private LookupResult<D> doSingleQuery(LookupInfo lookupInfo) throws HandlerException {
        // 展开参数。
        String preset = lookupInfo.getPreset();
        String[] params = lookupInfo.getParams();
        LongIdKey pointKey = lookupInfo.getPointKey();
        Date startDate = WatchUtil.validStartDate(lookupInfo.getStartDate());
        Date endDate = WatchUtil.validEndDate(lookupInfo.getEndDate());
        boolean includeStartDate = lookupInfo.isIncludeStartDate();
        boolean includeEndDate = lookupInfo.isIncludeEndDate();
        int page = WatchUtil.validPage(lookupInfo.getPage());
        int rows = WatchUtil.validRows(lookupInfo.getRows());

        // 构造查询信息。
        String measurement = Long.toString(pointKey.getLongId());
        long startOffset = includeStartDate ? 0 : 1;
        long stopOffset = includeEndDate ? 1 : 0;
        Date rangeStart = DateUtil.offsetDate(startDate, startOffset);
        Date rangeStop = DateUtil.offsetDate(endDate, stopOffset);
        int limitNumber = rows + 1;
        int limitOffset = page * rows;
        HibernateBridgeQueryInfo queryInfo = new HibernateBridgeQueryInfo(
                measurement, rangeStart, rangeStop, limitNumber, limitOffset, params
        );

        // 根据 preset 分类处理。
        HibernateBridgeQueryResult queryResult;
        if (preset.equals(DEFAULT)) {
            queryResult = handler.defaultQuery(queryInfo);
        } else {
            throw new IllegalArgumentException("非法的预设: " + preset);
        }

        // 展开参数。
        List<HibernateBridgeQueryResult.Item> items = queryResult.getItems();

        // 构造查询结果，并返回。
        boolean hasMore = items.size() > rows;
        List<D> datas = new ArrayList<>();
        for (int i = 0; i < rows && i < items.size(); i++) {
            datas.add(itemToData(items.get(i)));
        }
        return new LookupResult<>(pointKey, datas, hasMore);
    }

    protected abstract D itemToData(HibernateBridgeQueryResult.Item item);

    @Override
    public String toString() {
        return "InfluxdbBridgeDataPersister{" +
                "handler=" + handler +
                '}';
    }
}
