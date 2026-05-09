package com.dwarfeng.fdr.impl.handler.bridge.hibernate.dao.nativelookup;

import com.dwarfeng.fdr.impl.handler.bridge.hibernate.bean.HibernateBridgeNormalData;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class HibernateBridgeMysql8NormalDataNativeLookup extends HibernateBridgeNormalDataNativeLookup {

    public static final String SUPPORT_TYPE = "org.hibernate.dialect.MySQL8Dialect";

    public HibernateBridgeMysql8NormalDataNativeLookup() {
        super(SUPPORT_TYPE);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected List<HibernateBridgeNormalData> lookupChildForPointBetweenCloseClose(
            Connection connection, long pointLongId, Date startDate, Date endDate, int offset, int limit
    ) throws SQLException {
        // 构建 SQL。
        StringBuilder sqlBuilder = new StringBuilder();
        Mysql8NativeLookupUtil.selectColumnsFromTable(
                sqlBuilder, "tbl_hibernate_bridge_normal_data", "tbl",
                "id", "point_id", "value", "happened_date", "happened_date_nano_offset"
        );
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date_nano_id");
        Mysql8NativeLookupUtil.where(sqlBuilder);
        Mysql8NativeLookupUtil.pointLongIdEquals(sqlBuilder, "tbl", "point_id");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.happenedMomentBetweenCloseClose(
                sqlBuilder, "tbl", "happened_date", "happened_date_nano_offset"
        );
        Mysql8NativeLookupUtil.orderByHappenedMomentAsc(
                sqlBuilder, "tbl", "happened_date", "happened_date_nano_offset", "id"
        );
        Mysql8NativeLookupUtil.limit(sqlBuilder);

        // 构建 PreparedStatement。
        // SQL 语句是固定值，不存在安全性问题。
        @SuppressWarnings("SqlSourceToSinkFlow")
        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        preparedStatement.setLong(1, pointLongId);
        preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));
        preparedStatement.setTimestamp(4, new Timestamp(endDate.getTime()));
        preparedStatement.setInt(5, 0);
        preparedStatement.setInt(6, offset);
        preparedStatement.setInt(7, limit);

        // 执行查询，构建结果。
        ResultSet resultSet = preparedStatement.executeQuery();
        List<HibernateBridgeNormalData> HibernateBridgeNormalDatas = new ArrayList<>();
        while (resultSet.next()) {
            Timestamp happenedTs = resultSet.getTimestamp(4);
            HibernateBridgeNormalDatas.add(new HibernateBridgeNormalData(
                    new LongIdKey(resultSet.getLong(1)),
                    new LongIdKey(resultSet.getLong(2)),
                    resultSet.getString(3),
                    happenedTs == null ? null : new Date(happenedTs.getTime()),
                    resultSet.getInt(5)
            ));
        }
        return HibernateBridgeNormalDatas;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected List<HibernateBridgeNormalData> lookupChildForPointBetweenCloseOpen(
            Connection connection, long pointLongId, Date startDate, Date endDate, int offset, int limit
    ) throws SQLException {
        // 构建 SQL。
        StringBuilder sqlBuilder = new StringBuilder();
        Mysql8NativeLookupUtil.selectColumnsFromTable(
                sqlBuilder, "tbl_hibernate_bridge_normal_data", "tbl",
                "id", "point_id", "value", "happened_date", "happened_date_nano_offset"
        );
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date_nano_id");
        Mysql8NativeLookupUtil.where(sqlBuilder);
        Mysql8NativeLookupUtil.pointLongIdEquals(sqlBuilder, "tbl", "point_id");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.happenedMomentBetweenCloseOpen(sqlBuilder, "tbl", "happened_date");
        Mysql8NativeLookupUtil.orderByHappenedMomentAsc(
                sqlBuilder, "tbl", "happened_date", "happened_date_nano_offset", "id"
        );
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
        List<HibernateBridgeNormalData> HibernateBridgeNormalDatas = new ArrayList<>();
        while (resultSet.next()) {
            Timestamp happenedTs = resultSet.getTimestamp(4);
            HibernateBridgeNormalDatas.add(new HibernateBridgeNormalData(
                    new LongIdKey(resultSet.getLong(1)),
                    new LongIdKey(resultSet.getLong(2)),
                    resultSet.getString(3),
                    happenedTs == null ? null : new Date(happenedTs.getTime()),
                    resultSet.getInt(5)
            ));
        }
        return HibernateBridgeNormalDatas;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected List<HibernateBridgeNormalData> lookupChildForPointBetweenOpenClose(
            Connection connection, long pointLongId, Date startDate, Date endDate, int offset, int limit
    ) throws SQLException {
        // 构建 SQL。
        StringBuilder sqlBuilder = new StringBuilder();
        Mysql8NativeLookupUtil.selectColumnsFromTable(
                sqlBuilder, "tbl_hibernate_bridge_normal_data", "tbl",
                "id", "point_id", "value", "happened_date", "happened_date_nano_offset"
        );
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date_nano_id");
        Mysql8NativeLookupUtil.where(sqlBuilder);
        Mysql8NativeLookupUtil.pointLongIdEquals(sqlBuilder, "tbl", "point_id");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.happenedMomentBetweenOpenClose(
                sqlBuilder, "tbl", "happened_date", "happened_date_nano_offset"
        );
        Mysql8NativeLookupUtil.orderByHappenedMomentAsc(
                sqlBuilder, "tbl", "happened_date", "happened_date_nano_offset", "id"
        );
        Mysql8NativeLookupUtil.limit(sqlBuilder);

        // 构建 PreparedStatement。
        // SQL 语句是固定值，不存在安全性问题。
        @SuppressWarnings("SqlSourceToSinkFlow")
        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        preparedStatement.setLong(1, pointLongId);
        preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(3, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(4, new Timestamp(endDate.getTime()));
        preparedStatement.setTimestamp(5, new Timestamp(endDate.getTime()));
        preparedStatement.setInt(6, 0);
        preparedStatement.setInt(7, offset);
        preparedStatement.setInt(8, limit);

        // 执行查询，构建结果。
        ResultSet resultSet = preparedStatement.executeQuery();
        List<HibernateBridgeNormalData> HibernateBridgeNormalDatas = new ArrayList<>();
        while (resultSet.next()) {
            Timestamp happenedTs = resultSet.getTimestamp(4);
            HibernateBridgeNormalDatas.add(new HibernateBridgeNormalData(
                    new LongIdKey(resultSet.getLong(1)),
                    new LongIdKey(resultSet.getLong(2)),
                    resultSet.getString(3),
                    happenedTs == null ? null : new Date(happenedTs.getTime()),
                    resultSet.getInt(5)
            ));
        }
        return HibernateBridgeNormalDatas;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected List<HibernateBridgeNormalData> lookupChildForPointBetweenOpenOpen(
            Connection connection, long pointLongId, Date startDate, Date endDate, int offset, int limit
    ) throws SQLException {
        // 构建 SQL。
        StringBuilder sqlBuilder = new StringBuilder();
        Mysql8NativeLookupUtil.selectColumnsFromTable(
                sqlBuilder, "tbl_hibernate_bridge_normal_data", "tbl",
                "id", "point_id", "value", "happened_date", "happened_date_nano_offset"
        );
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date_nano_id");
        Mysql8NativeLookupUtil.where(sqlBuilder);
        Mysql8NativeLookupUtil.pointLongIdEquals(sqlBuilder, "tbl", "point_id");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.happenedMomentBetweenOpenOpen(
                sqlBuilder, "tbl", "happened_date", "happened_date_nano_offset"
        );
        Mysql8NativeLookupUtil.orderByHappenedMomentAsc(
                sqlBuilder, "tbl", "happened_date", "happened_date_nano_offset", "id"
        );
        Mysql8NativeLookupUtil.limit(sqlBuilder);

        // 构建 PreparedStatement。
        // SQL 语句是固定值，不存在安全性问题。
        @SuppressWarnings("SqlSourceToSinkFlow")
        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        preparedStatement.setLong(1, pointLongId);
        preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(3, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(4, new Timestamp(endDate.getTime()));
        preparedStatement.setInt(5, offset);
        preparedStatement.setInt(6, limit);

        // 执行查询，构建结果。
        ResultSet resultSet = preparedStatement.executeQuery();
        List<HibernateBridgeNormalData> HibernateBridgeNormalDatas = new ArrayList<>();
        while (resultSet.next()) {
            Timestamp happenedTs = resultSet.getTimestamp(4);
            HibernateBridgeNormalDatas.add(new HibernateBridgeNormalData(
                    new LongIdKey(resultSet.getLong(1)),
                    new LongIdKey(resultSet.getLong(2)),
                    resultSet.getString(3),
                    happenedTs == null ? null : new Date(happenedTs.getTime()),
                    resultSet.getInt(5)
            ));
        }
        return HibernateBridgeNormalDatas;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected int lookupChildForPointBetweenCloseCloseCount(
            Connection connection, long pointLongId, Date startDate, Date endDate
    ) throws SQLException {
        // 构建 SQL。
        StringBuilder sqlBuilder = new StringBuilder();
        Mysql8NativeLookupUtil.selectCountFromTable(
                sqlBuilder, "tbl_hibernate_bridge_normal_data", "tbl", "id"
        );
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date_nano_id");
        Mysql8NativeLookupUtil.where(sqlBuilder);
        Mysql8NativeLookupUtil.pointLongIdEquals(sqlBuilder, "tbl", "point_id");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.happenedMomentBetweenCloseClose(
                sqlBuilder, "tbl", "happened_date", "happened_date_nano_offset"
        );

        // 构建 PreparedStatement。
        // SQL 语句是固定值，不存在安全性问题。
        @SuppressWarnings("SqlSourceToSinkFlow")
        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        preparedStatement.setLong(1, pointLongId);
        preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));
        preparedStatement.setTimestamp(4, new Timestamp(endDate.getTime()));
        preparedStatement.setInt(5, 0);

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
        // 构建 SQL。
        StringBuilder sqlBuilder = new StringBuilder();
        Mysql8NativeLookupUtil.selectCountFromTable(
                sqlBuilder, "tbl_hibernate_bridge_normal_data", "tbl", "id"
        );
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date_nano_id");
        Mysql8NativeLookupUtil.where(sqlBuilder);
        Mysql8NativeLookupUtil.pointLongIdEquals(sqlBuilder, "tbl", "point_id");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.happenedMomentBetweenCloseOpen(sqlBuilder, "tbl", "happened_date");

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
        // 构建 SQL。
        StringBuilder sqlBuilder = new StringBuilder();
        Mysql8NativeLookupUtil.selectCountFromTable(
                sqlBuilder, "tbl_hibernate_bridge_normal_data", "tbl", "id"
        );
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date_nano_id");
        Mysql8NativeLookupUtil.where(sqlBuilder);
        Mysql8NativeLookupUtil.pointLongIdEquals(sqlBuilder, "tbl", "point_id");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.happenedMomentBetweenOpenClose(
                sqlBuilder, "tbl", "happened_date", "happened_date_nano_offset"
        );

        // 构建 PreparedStatement。
        // SQL 语句是固定值，不存在安全性问题。
        @SuppressWarnings("SqlSourceToSinkFlow")
        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        preparedStatement.setLong(1, pointLongId);
        preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(3, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(4, new Timestamp(endDate.getTime()));
        preparedStatement.setTimestamp(5, new Timestamp(endDate.getTime()));
        preparedStatement.setInt(6, 0);

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
        // 构建 SQL。
        StringBuilder sqlBuilder = new StringBuilder();
        Mysql8NativeLookupUtil.selectCountFromTable(
                sqlBuilder, "tbl_hibernate_bridge_normal_data", "tbl", "id"
        );
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date_nano_id");
        Mysql8NativeLookupUtil.where(sqlBuilder);
        Mysql8NativeLookupUtil.pointLongIdEquals(sqlBuilder, "tbl", "point_id");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.happenedMomentBetweenOpenOpen(
                sqlBuilder, "tbl", "happened_date", "happened_date_nano_offset"
        );

        // 构建 PreparedStatement。
        // SQL 语句是固定值，不存在安全性问题。
        @SuppressWarnings("SqlSourceToSinkFlow")
        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        preparedStatement.setLong(1, pointLongId);
        preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(3, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(4, new Timestamp(endDate.getTime()));

        // 执行查询，返回结果。
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return Long.valueOf(resultSet.getLong(1)).intValue();
    }

    @Override
    public String toString() {
        return "HibernateBridgeMysql8NormalDataNativeLookup{}";
    }
}
