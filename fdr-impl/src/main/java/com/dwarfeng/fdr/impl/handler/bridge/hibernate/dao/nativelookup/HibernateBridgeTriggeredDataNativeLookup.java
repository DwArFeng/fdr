package com.dwarfeng.fdr.impl.handler.bridge.hibernate.dao.nativelookup;

import com.dwarfeng.fdr.impl.handler.bridge.hibernate.bean.entity.HibernateBridgeTriggeredData;
import com.dwarfeng.fdr.impl.handler.bridge.hibernate.service.HibernateBridgeTriggeredDataMaintainService;
import com.dwarfeng.subgrade.sdk.hibernate.nativelookup.AbstractDialectNativeLookup;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class HibernateBridgeTriggeredDataNativeLookup extends AbstractDialectNativeLookup<HibernateBridgeTriggeredData> {

    public static final String SUPPORT_TYPE = "org.hibernate.dialect.MySQL8Dialect";

    public HibernateBridgeTriggeredDataNativeLookup() {
        super(SUPPORT_TYPE);
    }

    @Override
    public boolean supportPreset(String preset) {
        switch (preset) {
            case HibernateBridgeTriggeredDataMaintainService.CHILD_FOR_POINT_BETWEEN_CLOSE_CLOSE:
            case HibernateBridgeTriggeredDataMaintainService.CHILD_FOR_POINT_BETWEEN_CLOSE_OPEN:
            case HibernateBridgeTriggeredDataMaintainService.CHILD_FOR_POINT_BETWEEN_OPEN_CLOSE:
            case HibernateBridgeTriggeredDataMaintainService.CHILD_FOR_POINT_BETWEEN_OPEN_OPEN:
                return true;
            default:
                return false;
        }
    }

    @Override
    public List<HibernateBridgeTriggeredData> lookupEntity(Connection connection, String preset, Object[] args) {
        throw new IllegalArgumentException("不支持不带分页信息的查询");
    }

    @Override
    public List<HibernateBridgeTriggeredData> lookupEntity(
            Connection connection, String preset, Object[] args, PagingInfo pagingInfo
    ) throws SQLException {
        switch (preset) {
            case HibernateBridgeTriggeredDataMaintainService.CHILD_FOR_POINT_BETWEEN_CLOSE_CLOSE:
                return childForPointBetweenCloseClose(connection, args, pagingInfo);
            case HibernateBridgeTriggeredDataMaintainService.CHILD_FOR_POINT_BETWEEN_CLOSE_OPEN:
                return childForPointBetweenCloseOpen(connection, args, pagingInfo);
            case HibernateBridgeTriggeredDataMaintainService.CHILD_FOR_POINT_BETWEEN_OPEN_CLOSE:
                return childForPointBetweenOpenClose(connection, args, pagingInfo);
            case HibernateBridgeTriggeredDataMaintainService.CHILD_FOR_POINT_BETWEEN_OPEN_OPEN:
                return childForPointBetweenOpenOpen(connection, args, pagingInfo);
            default:
                throw new IllegalArgumentException("无法识别的预设: " + preset);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private List<HibernateBridgeTriggeredData> childForPointBetweenCloseClose(
            Connection connection, Object[] args, PagingInfo pagingInfo
    ) throws SQLException {
        // 展开参数。
        long pointLongId = ((LongIdKey) args[0]).getLongId();
        Date startDate = (Date) args[1];
        Date endDate = (Date) args[2];
        int offset = pagingInfo.getPage() * pagingInfo.getRows();
        int limit = pagingInfo.getRows();

        // 构建SQL。
        StringBuilder sqlBuilder = new StringBuilder();
        Mysql8NativeLookupUtil.selectColumnsFromTable(
                sqlBuilder, "tbl_hibernate_bridge_triggered_data", "tbl",
                "id", "point_id", "trigger_id", "value", "message", "happened_date"
        );
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date");
        Mysql8NativeLookupUtil.where(sqlBuilder);
        Mysql8NativeLookupUtil.pointLongIdEquals(sqlBuilder, "tbl", "point_id");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.happenedDateBetweenCloseClose(sqlBuilder, "tbl", "happened_date");
        Mysql8NativeLookupUtil.orderByHappenedDateAsc(sqlBuilder, "tbl", "happened_date");
        Mysql8NativeLookupUtil.limit(sqlBuilder);

        // 构建 PreparedStatement。
        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        preparedStatement.setLong(1, pointLongId);
        preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));
        preparedStatement.setInt(4, offset);
        preparedStatement.setInt(5, limit);

        // 执行查询，构建结果。
        ResultSet resultSet = preparedStatement.executeQuery();
        List<HibernateBridgeTriggeredData> HibernateBridgeTriggeredDatas = new ArrayList<>();
        while (resultSet.next()) {
            HibernateBridgeTriggeredDatas.add(new HibernateBridgeTriggeredData(
                    new LongIdKey(resultSet.getLong(1)),
                    new LongIdKey(resultSet.getLong(2)),
                    new LongIdKey(resultSet.getLong(3)),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    new Date(resultSet.getTimestamp(6).getTime())
            ));
        }
        return HibernateBridgeTriggeredDatas;
    }

    @SuppressWarnings("DuplicatedCode")
    private List<HibernateBridgeTriggeredData> childForPointBetweenCloseOpen(
            Connection connection, Object[] args, PagingInfo pagingInfo
    ) throws SQLException {
        // 展开参数。
        long pointLongId = ((LongIdKey) args[0]).getLongId();
        Date startDate = (Date) args[1];
        Date endDate = (Date) args[2];
        int offset = pagingInfo.getPage() * pagingInfo.getRows();
        int limit = pagingInfo.getRows();

        // 构建SQL。
        StringBuilder sqlBuilder = new StringBuilder();
        Mysql8NativeLookupUtil.selectColumnsFromTable(
                sqlBuilder, "tbl_hibernate_bridge_triggered_data", "tbl",
                "id", "point_id", "trigger_id", "value", "message", "happened_date"
        );
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date");
        Mysql8NativeLookupUtil.where(sqlBuilder);
        Mysql8NativeLookupUtil.pointLongIdEquals(sqlBuilder, "tbl", "point_id");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.happenedDateBetweenCloseOpen(sqlBuilder, "tbl", "happened_date");
        Mysql8NativeLookupUtil.orderByHappenedDateAsc(sqlBuilder, "tbl", "happened_date");
        Mysql8NativeLookupUtil.limit(sqlBuilder);

        // 构建 PreparedStatement。
        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        preparedStatement.setLong(1, pointLongId);
        preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));
        preparedStatement.setInt(4, offset);
        preparedStatement.setInt(5, limit);

        // 执行查询，构建结果。
        ResultSet resultSet = preparedStatement.executeQuery();
        List<HibernateBridgeTriggeredData> HibernateBridgeTriggeredDatas = new ArrayList<>();
        while (resultSet.next()) {
            HibernateBridgeTriggeredDatas.add(new HibernateBridgeTriggeredData(
                    new LongIdKey(resultSet.getLong(1)),
                    new LongIdKey(resultSet.getLong(2)),
                    new LongIdKey(resultSet.getLong(3)),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    new Date(resultSet.getTimestamp(6).getTime())
            ));
        }
        return HibernateBridgeTriggeredDatas;
    }

    @SuppressWarnings("DuplicatedCode")
    private List<HibernateBridgeTriggeredData> childForPointBetweenOpenClose(
            Connection connection, Object[] args, PagingInfo pagingInfo
    ) throws SQLException {
        // 展开参数。
        long pointLongId = ((LongIdKey) args[0]).getLongId();
        Date startDate = (Date) args[1];
        Date endDate = (Date) args[2];
        int offset = pagingInfo.getPage() * pagingInfo.getRows();
        int limit = pagingInfo.getRows();

        // 构建SQL。
        StringBuilder sqlBuilder = new StringBuilder();
        Mysql8NativeLookupUtil.selectColumnsFromTable(
                sqlBuilder, "tbl_hibernate_bridge_triggered_data", "tbl",
                "id", "point_id", "trigger_id", "value", "message", "happened_date"
        );
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date");
        Mysql8NativeLookupUtil.where(sqlBuilder);
        Mysql8NativeLookupUtil.pointLongIdEquals(sqlBuilder, "tbl", "point_id");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.happenedDateBetweenOpenClose(sqlBuilder, "tbl", "happened_date");
        Mysql8NativeLookupUtil.orderByHappenedDateAsc(sqlBuilder, "tbl", "happened_date");
        Mysql8NativeLookupUtil.limit(sqlBuilder);

        // 构建 PreparedStatement。
        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        preparedStatement.setLong(1, pointLongId);
        preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));
        preparedStatement.setInt(4, offset);
        preparedStatement.setInt(5, limit);

        // 执行查询，构建结果。
        ResultSet resultSet = preparedStatement.executeQuery();
        List<HibernateBridgeTriggeredData> HibernateBridgeTriggeredDatas = new ArrayList<>();
        while (resultSet.next()) {
            HibernateBridgeTriggeredDatas.add(new HibernateBridgeTriggeredData(
                    new LongIdKey(resultSet.getLong(1)),
                    new LongIdKey(resultSet.getLong(2)),
                    new LongIdKey(resultSet.getLong(3)),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    new Date(resultSet.getTimestamp(6).getTime())
            ));
        }
        return HibernateBridgeTriggeredDatas;
    }

    @SuppressWarnings("DuplicatedCode")
    private List<HibernateBridgeTriggeredData> childForPointBetweenOpenOpen(
            Connection connection, Object[] args, PagingInfo pagingInfo
    ) throws SQLException {
        // 展开参数。
        long pointLongId = ((LongIdKey) args[0]).getLongId();
        Date startDate = (Date) args[1];
        Date endDate = (Date) args[2];
        int offset = pagingInfo.getPage() * pagingInfo.getRows();
        int limit = pagingInfo.getRows();

        // 构建SQL。
        StringBuilder sqlBuilder = new StringBuilder();
        Mysql8NativeLookupUtil.selectColumnsFromTable(
                sqlBuilder, "tbl_hibernate_bridge_triggered_data", "tbl",
                "id", "point_id", "trigger_id", "value", "message", "happened_date"
        );
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date");
        Mysql8NativeLookupUtil.where(sqlBuilder);
        Mysql8NativeLookupUtil.pointLongIdEquals(sqlBuilder, "tbl", "point_id");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.happenedDateBetweenOpenOpen(sqlBuilder, "tbl", "happened_date");
        Mysql8NativeLookupUtil.orderByHappenedDateAsc(sqlBuilder, "tbl", "happened_date");
        Mysql8NativeLookupUtil.limit(sqlBuilder);

        // 构建 PreparedStatement。
        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        preparedStatement.setLong(1, pointLongId);
        preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));
        preparedStatement.setInt(4, offset);
        preparedStatement.setInt(5, limit);

        // 执行查询，构建结果。
        ResultSet resultSet = preparedStatement.executeQuery();
        List<HibernateBridgeTriggeredData> HibernateBridgeTriggeredDatas = new ArrayList<>();
        while (resultSet.next()) {
            HibernateBridgeTriggeredDatas.add(new HibernateBridgeTriggeredData(
                    new LongIdKey(resultSet.getLong(1)),
                    new LongIdKey(resultSet.getLong(2)),
                    new LongIdKey(resultSet.getLong(3)),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    new Date(resultSet.getTimestamp(6).getTime())
            ));
        }
        return HibernateBridgeTriggeredDatas;
    }

    @Override
    public int lookupCount(Connection connection, String preset, Object[] args) throws SQLException {
        switch (preset) {
            case HibernateBridgeTriggeredDataMaintainService.CHILD_FOR_POINT_BETWEEN_CLOSE_CLOSE:
                return childForPointBetweenCloseCloseCount(connection, args);
            case HibernateBridgeTriggeredDataMaintainService.CHILD_FOR_POINT_BETWEEN_CLOSE_OPEN:
                return childForPointBetweenCloseOpenCount(connection, args);
            case HibernateBridgeTriggeredDataMaintainService.CHILD_FOR_POINT_BETWEEN_OPEN_CLOSE:
                return childForPointBetweenOpenCloseCount(connection, args);
            case HibernateBridgeTriggeredDataMaintainService.CHILD_FOR_POINT_BETWEEN_OPEN_OPEN:
                return childForPointBetweenOpenOpenCount(connection, args);
            default:
                throw new IllegalArgumentException("无法识别的预设: " + preset);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private int childForPointBetweenCloseCloseCount(
            Connection connection, Object[] args
    ) throws SQLException {
        // 展开参数。
        long pointLongId = ((LongIdKey) args[0]).getLongId();
        Date startDate = (Date) args[1];
        Date endDate = (Date) args[2];

        // 构建SQL。
        StringBuilder sqlBuilder = new StringBuilder();
        Mysql8NativeLookupUtil.selectCountFromTable(
                sqlBuilder, "tbl_hibernate_bridge_triggered_data", "tbl", "id"
        );
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date");
        Mysql8NativeLookupUtil.where(sqlBuilder);
        Mysql8NativeLookupUtil.pointLongIdEquals(sqlBuilder, "tbl", "point_id");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.happenedDateBetweenCloseClose(sqlBuilder, "tbl", "happened_date");

        // 构建 PreparedStatement。
        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        preparedStatement.setLong(1, pointLongId);
        preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));

        // 执行查询，返回结果。
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return Long.valueOf(resultSet.getLong(1)).intValue();
    }

    @SuppressWarnings("DuplicatedCode")
    private int childForPointBetweenCloseOpenCount(
            Connection connection, Object[] args
    ) throws SQLException {
        // 展开参数。
        long pointLongId = ((LongIdKey) args[0]).getLongId();
        Date startDate = (Date) args[1];
        Date endDate = (Date) args[2];

        // 构建SQL。
        StringBuilder sqlBuilder = new StringBuilder();
        Mysql8NativeLookupUtil.selectCountFromTable(
                sqlBuilder, "tbl_hibernate_bridge_triggered_data", "tbl", "id"
        );
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date");
        Mysql8NativeLookupUtil.where(sqlBuilder);
        Mysql8NativeLookupUtil.pointLongIdEquals(sqlBuilder, "tbl", "point_id");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.happenedDateBetweenCloseOpen(sqlBuilder, "tbl", "happened_date");

        // 构建 PreparedStatement。
        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        preparedStatement.setLong(1, pointLongId);
        preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));

        // 执行查询，返回结果。
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return Long.valueOf(resultSet.getLong(1)).intValue();
    }

    @SuppressWarnings("DuplicatedCode")
    private int childForPointBetweenOpenCloseCount(
            Connection connection, Object[] args
    ) throws SQLException {
        // 展开参数。
        long pointLongId = ((LongIdKey) args[0]).getLongId();
        Date startDate = (Date) args[1];
        Date endDate = (Date) args[2];

        // 构建SQL。
        StringBuilder sqlBuilder = new StringBuilder();
        Mysql8NativeLookupUtil.selectCountFromTable(
                sqlBuilder, "tbl_hibernate_bridge_triggered_data", "tbl", "id"
        );
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date");
        Mysql8NativeLookupUtil.where(sqlBuilder);
        Mysql8NativeLookupUtil.pointLongIdEquals(sqlBuilder, "tbl", "point_id");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.happenedDateBetweenOpenClose(sqlBuilder, "tbl", "happened_date");

        // 构建 PreparedStatement。
        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        preparedStatement.setLong(1, pointLongId);
        preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));

        // 执行查询，返回结果。
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return Long.valueOf(resultSet.getLong(1)).intValue();
    }

    @SuppressWarnings("DuplicatedCode")
    private int childForPointBetweenOpenOpenCount(
            Connection connection, Object[] args
    ) throws SQLException {
        // 展开参数。
        long pointLongId = ((LongIdKey) args[0]).getLongId();
        Date startDate = (Date) args[1];
        Date endDate = (Date) args[2];

        // 构建SQL。
        StringBuilder sqlBuilder = new StringBuilder();
        Mysql8NativeLookupUtil.selectCountFromTable(
                sqlBuilder, "tbl_hibernate_bridge_triggered_data", "tbl", "id"
        );
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date");
        Mysql8NativeLookupUtil.where(sqlBuilder);
        Mysql8NativeLookupUtil.pointLongIdEquals(sqlBuilder, "tbl", "point_id");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.happenedDateBetweenOpenOpen(sqlBuilder, "tbl", "happened_date");

        // 构建 PreparedStatement。
        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        preparedStatement.setLong(1, pointLongId);
        preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));

        // 执行查询，返回结果。
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return Long.valueOf(resultSet.getLong(1)).intValue();
    }
}
