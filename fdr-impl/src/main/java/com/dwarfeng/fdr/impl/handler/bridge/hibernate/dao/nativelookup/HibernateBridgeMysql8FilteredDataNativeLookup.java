package com.dwarfeng.fdr.impl.handler.bridge.hibernate.dao.nativelookup;

import com.dwarfeng.fdr.impl.handler.bridge.hibernate.bean.HibernateBridgeFilteredData;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class HibernateBridgeMysql8FilteredDataNativeLookup extends HibernateBridgeFilteredDataNativeLookup {

    public static final String SUPPORT_TYPE = "org.hibernate.dialect.MySQL8Dialect";

    public HibernateBridgeMysql8FilteredDataNativeLookup() {
        super(SUPPORT_TYPE);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected List<HibernateBridgeFilteredData> lookupChildForPointBetweenCloseClose(
            Connection connection, long pointLongId, Date startDate, Date endDate, int offset, int limit
    ) throws SQLException {
        // 构建SQL。
        StringBuilder sqlBuilder = new StringBuilder();
        Mysql8NativeLookupUtil.selectColumnsFromTable(
                sqlBuilder, "tbl_hibernate_bridge_filtered_data", "tbl",
                "id", "point_id", "filter_id", "value", "message", "happened_date"
        );
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date");
        Mysql8NativeLookupUtil.where(sqlBuilder);
        Mysql8NativeLookupUtil.pointLongIdEquals(sqlBuilder, "tbl", "point_id");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.happenedDateBetweenCloseClose(sqlBuilder, "tbl", "happened_date");
        Mysql8NativeLookupUtil.orderByHappenedDateAsc(sqlBuilder, "tbl", "happened_date");
        Mysql8NativeLookupUtil.limit(sqlBuilder);

        // 构建 PreparedStatement。
        // SQL 语句是固定值，不存在安全性问题。
        @SuppressWarnings("SqlSourceToSinkFlow")
        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        preparedStatement.setLong(1, pointLongId);
        preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));
        preparedStatement.setInt(4, offset);
        preparedStatement.setInt(5, limit);

        // 执行查询，构建结果。
        ResultSet resultSet = preparedStatement.executeQuery();
        List<HibernateBridgeFilteredData> HibernateBridgeFilteredDatas = new ArrayList<>();
        while (resultSet.next()) {
            HibernateBridgeFilteredDatas.add(new HibernateBridgeFilteredData(
                    new LongIdKey(resultSet.getLong(1)),
                    new LongIdKey(resultSet.getLong(2)),
                    new LongIdKey(resultSet.getLong(3)),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    new Date(resultSet.getTimestamp(6).getTime())
            ));
        }
        return HibernateBridgeFilteredDatas;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected List<HibernateBridgeFilteredData> lookupChildForPointBetweenCloseOpen(
            Connection connection, long pointLongId, Date startDate, Date endDate, int offset, int limit
    ) throws SQLException {
        // 构建SQL。
        StringBuilder sqlBuilder = new StringBuilder();
        Mysql8NativeLookupUtil.selectColumnsFromTable(
                sqlBuilder, "tbl_hibernate_bridge_filtered_data", "tbl",
                "id", "point_id", "filter_id", "value", "message", "happened_date"
        );
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date");
        Mysql8NativeLookupUtil.where(sqlBuilder);
        Mysql8NativeLookupUtil.pointLongIdEquals(sqlBuilder, "tbl", "point_id");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.happenedDateBetweenCloseOpen(sqlBuilder, "tbl", "happened_date");
        Mysql8NativeLookupUtil.orderByHappenedDateAsc(sqlBuilder, "tbl", "happened_date");
        Mysql8NativeLookupUtil.limit(sqlBuilder);

        // 构建 PreparedStatement。
        // SQL 语句是固定值，不存在安全性问题。
        @SuppressWarnings("SqlSourceToSinkFlow")
        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        preparedStatement.setLong(1, pointLongId);
        preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));
        preparedStatement.setInt(4, offset);
        preparedStatement.setInt(5, limit);

        // 执行查询，构建结果。
        ResultSet resultSet = preparedStatement.executeQuery();
        List<HibernateBridgeFilteredData> HibernateBridgeFilteredDatas = new ArrayList<>();
        while (resultSet.next()) {
            HibernateBridgeFilteredDatas.add(new HibernateBridgeFilteredData(
                    new LongIdKey(resultSet.getLong(1)),
                    new LongIdKey(resultSet.getLong(2)),
                    new LongIdKey(resultSet.getLong(3)),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    new Date(resultSet.getTimestamp(6).getTime())
            ));
        }
        return HibernateBridgeFilteredDatas;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected List<HibernateBridgeFilteredData> lookupChildForPointBetweenOpenClose(
            Connection connection, long pointLongId, Date startDate, Date endDate, int offset, int limit
    ) throws SQLException {
        // 构建SQL。
        StringBuilder sqlBuilder = new StringBuilder();
        Mysql8NativeLookupUtil.selectColumnsFromTable(
                sqlBuilder, "tbl_hibernate_bridge_filtered_data", "tbl",
                "id", "point_id", "filter_id", "value", "message", "happened_date"
        );
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date");
        Mysql8NativeLookupUtil.where(sqlBuilder);
        Mysql8NativeLookupUtil.pointLongIdEquals(sqlBuilder, "tbl", "point_id");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.happenedDateBetweenOpenClose(sqlBuilder, "tbl", "happened_date");
        Mysql8NativeLookupUtil.orderByHappenedDateAsc(sqlBuilder, "tbl", "happened_date");
        Mysql8NativeLookupUtil.limit(sqlBuilder);

        // 构建 PreparedStatement。
        // SQL 语句是固定值，不存在安全性问题。
        @SuppressWarnings("SqlSourceToSinkFlow")
        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        preparedStatement.setLong(1, pointLongId);
        preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));
        preparedStatement.setInt(4, offset);
        preparedStatement.setInt(5, limit);

        // 执行查询，构建结果。
        ResultSet resultSet = preparedStatement.executeQuery();
        List<HibernateBridgeFilteredData> HibernateBridgeFilteredDatas = new ArrayList<>();
        while (resultSet.next()) {
            HibernateBridgeFilteredDatas.add(new HibernateBridgeFilteredData(
                    new LongIdKey(resultSet.getLong(1)),
                    new LongIdKey(resultSet.getLong(2)),
                    new LongIdKey(resultSet.getLong(3)),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    new Date(resultSet.getTimestamp(6).getTime())
            ));
        }
        return HibernateBridgeFilteredDatas;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected List<HibernateBridgeFilteredData> lookupChildForPointBetweenOpenOpen(
            Connection connection, long pointLongId, Date startDate, Date endDate, int offset, int limit
    ) throws SQLException {
        // 构建SQL。
        StringBuilder sqlBuilder = new StringBuilder();
        Mysql8NativeLookupUtil.selectColumnsFromTable(
                sqlBuilder, "tbl_hibernate_bridge_filtered_data", "tbl",
                "id", "point_id", "filter_id", "value", "message", "happened_date"
        );
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date");
        Mysql8NativeLookupUtil.where(sqlBuilder);
        Mysql8NativeLookupUtil.pointLongIdEquals(sqlBuilder, "tbl", "point_id");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.happenedDateBetweenOpenOpen(sqlBuilder, "tbl", "happened_date");
        Mysql8NativeLookupUtil.orderByHappenedDateAsc(sqlBuilder, "tbl", "happened_date");
        Mysql8NativeLookupUtil.limit(sqlBuilder);

        // 构建 PreparedStatement。
        // SQL 语句是固定值，不存在安全性问题。
        @SuppressWarnings("SqlSourceToSinkFlow")
        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        preparedStatement.setLong(1, pointLongId);
        preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));
        preparedStatement.setInt(4, offset);
        preparedStatement.setInt(5, limit);

        // 执行查询，构建结果。
        ResultSet resultSet = preparedStatement.executeQuery();
        List<HibernateBridgeFilteredData> HibernateBridgeFilteredDatas = new ArrayList<>();
        while (resultSet.next()) {
            HibernateBridgeFilteredDatas.add(new HibernateBridgeFilteredData(
                    new LongIdKey(resultSet.getLong(1)),
                    new LongIdKey(resultSet.getLong(2)),
                    new LongIdKey(resultSet.getLong(3)),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    new Date(resultSet.getTimestamp(6).getTime())
            ));
        }
        return HibernateBridgeFilteredDatas;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected int lookupChildForPointBetweenCloseCloseCount(
            Connection connection, long pointLongId, Date startDate, Date endDate
    ) throws SQLException {
        // 构建SQL。
        StringBuilder sqlBuilder = new StringBuilder();
        Mysql8NativeLookupUtil.selectCountFromTable(
                sqlBuilder, "tbl_hibernate_bridge_filtered_data", "tbl", "id"
        );
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date");
        Mysql8NativeLookupUtil.where(sqlBuilder);
        Mysql8NativeLookupUtil.pointLongIdEquals(sqlBuilder, "tbl", "point_id");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.happenedDateBetweenCloseClose(sqlBuilder, "tbl", "happened_date");

        // 构建 PreparedStatement。
        // SQL 语句是固定值，不存在安全性问题。
        @SuppressWarnings("SqlSourceToSinkFlow")
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
    @Override
    protected int lookupChildForPointBetweenCloseOpenCount(
            Connection connection, long pointLongId, Date startDate, Date endDate
    ) throws SQLException {
        // 构建SQL。
        StringBuilder sqlBuilder = new StringBuilder();
        Mysql8NativeLookupUtil.selectCountFromTable(
                sqlBuilder, "tbl_hibernate_bridge_filtered_data", "tbl", "id"
        );
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date");
        Mysql8NativeLookupUtil.where(sqlBuilder);
        Mysql8NativeLookupUtil.pointLongIdEquals(sqlBuilder, "tbl", "point_id");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.happenedDateBetweenCloseOpen(sqlBuilder, "tbl", "happened_date");

        // 构建 PreparedStatement。
        // SQL 语句是固定值，不存在安全性问题。
        @SuppressWarnings("SqlSourceToSinkFlow")
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
    @Override
    protected int lookupChildForPointBetweenOpenCloseCount(
            Connection connection, long pointLongId, Date startDate, Date endDate
    ) throws SQLException {
        // 构建SQL。
        StringBuilder sqlBuilder = new StringBuilder();
        Mysql8NativeLookupUtil.selectCountFromTable(
                sqlBuilder, "tbl_hibernate_bridge_filtered_data", "tbl", "id"
        );
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date");
        Mysql8NativeLookupUtil.where(sqlBuilder);
        Mysql8NativeLookupUtil.pointLongIdEquals(sqlBuilder, "tbl", "point_id");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.happenedDateBetweenOpenClose(sqlBuilder, "tbl", "happened_date");

        // 构建 PreparedStatement。
        // SQL 语句是固定值，不存在安全性问题。
        @SuppressWarnings("SqlSourceToSinkFlow")
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
    @Override
    protected int lookupChildForPointBetweenOpenOpenCount(
            Connection connection, long pointLongId, Date startDate, Date endDate
    ) throws SQLException {
        // 构建SQL。
        StringBuilder sqlBuilder = new StringBuilder();
        Mysql8NativeLookupUtil.selectCountFromTable(
                sqlBuilder, "tbl_hibernate_bridge_filtered_data", "tbl", "id"
        );
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date");
        Mysql8NativeLookupUtil.where(sqlBuilder);
        Mysql8NativeLookupUtil.pointLongIdEquals(sqlBuilder, "tbl", "point_id");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.happenedDateBetweenOpenOpen(sqlBuilder, "tbl", "happened_date");

        // 构建 PreparedStatement。
        // SQL 语句是固定值，不存在安全性问题。
        @SuppressWarnings("SqlSourceToSinkFlow")
        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        preparedStatement.setLong(1, pointLongId);
        preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));

        // 执行查询，返回结果。
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return Long.valueOf(resultSet.getLong(1)).intValue();
    }

    @Override
    public String toString() {
        return "HibernateBridgeMysql8FilteredDataNativeLookup{}";
    }
}
