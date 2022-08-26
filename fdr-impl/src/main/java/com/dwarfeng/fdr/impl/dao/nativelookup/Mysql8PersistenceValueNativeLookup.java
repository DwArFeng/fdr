package com.dwarfeng.fdr.impl.dao.nativelookup;

import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.service.PersistenceValueMaintainService;
import com.dwarfeng.subgrade.sdk.hibernate.nativelookup.AbstractDialectNativeLookup;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("DuplicatedCode")
@Component
public class Mysql8PersistenceValueNativeLookup extends AbstractDialectNativeLookup<PersistenceValue> {

    public static final String SUPPORT_TYPE = "org.hibernate.dialect.MySQL8Dialect";

    public Mysql8PersistenceValueNativeLookup() {
        super(SUPPORT_TYPE);
    }

    @Override
    public boolean supportPreset(String preset) {
        switch (preset) {
            case PersistenceValueMaintainService.BETWEEN:
            case PersistenceValueMaintainService.CHILD_FOR_POINT:
            case PersistenceValueMaintainService.CHILD_FOR_POINT_BETWEEN:
            case PersistenceValueMaintainService.CHILD_FOR_POINT_BETWEEN_RB_OPEN:
            case PersistenceValueMaintainService.CHILD_FOR_POINT_PREVIOUS:
            case PersistenceValueMaintainService.CHILD_FOR_POINT_REAR:
                return true;
            default:
                return false;
        }
    }

    @Override
    public List<PersistenceValue> lookupEntity(Connection connection, String preset, Object[] args)
            throws SQLException {
        switch (preset) {
            case PersistenceValueMaintainService.BETWEEN:
                return between(connection, args);
            case PersistenceValueMaintainService.CHILD_FOR_POINT:
                return childForPoint(connection, args);
            case PersistenceValueMaintainService.CHILD_FOR_POINT_BETWEEN:
                return childForPointBetween(connection, args);
            case PersistenceValueMaintainService.CHILD_FOR_POINT_BETWEEN_RB_OPEN:
                return childForPointBetweenRbOpen(connection, args);
            case PersistenceValueMaintainService.CHILD_FOR_POINT_PREVIOUS:
                return childForPointPrevious(connection, args);
            case PersistenceValueMaintainService.CHILD_FOR_POINT_REAR:
                return childForPointRear(connection, args);
            default:
                throw new IllegalArgumentException("无法识别的预设: " + preset);
        }
    }

    private List<PersistenceValue> between(Connection connection, Object[] args) throws SQLException {
        Date startDate = (Date) args[0];
        Date endDate = (Date) args[1];

        StringBuilder sqlBuilder = new StringBuilder();
        selectColumn(sqlBuilder, "id", "point_id", "happened_date", "value");
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_happened_date");
        sqlBuilder.append("WHERE tbl.happened_date>=? AND tbl.happened_date<=? ");
        sqlBuilder.append("ORDER BY tbl.happened_date ASC");

        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        preparedStatement.setTimestamp(1, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(2, new Timestamp(endDate.getTime()));

        ResultSet resultSet = preparedStatement.executeQuery();
        List<PersistenceValue> persistenceValues = new ArrayList<>();
        while (resultSet.next()) {
            persistenceValues.add(new PersistenceValue(
                    new LongIdKey(resultSet.getLong(1)),
                    new LongIdKey(resultSet.getLong(2)),
                    new Date(resultSet.getTimestamp(3).getTime()),
                    resultSet.getString(4)
            ));
        }
        return persistenceValues;
    }

    private List<PersistenceValue> childForPoint(Connection connection, Object[] args) throws SQLException {
        LongIdKey pointKey = (LongIdKey) args[0];

        StringBuilder sqlBuilder = new StringBuilder();
        selectColumn(sqlBuilder, "id", "happened_date", "value");
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date");
        sqlBuilder.append("WHERE ");
        if (Objects.isNull(pointKey)) {
            sqlBuilder.append("tbl.point_id IS NULL ");
        } else {
            sqlBuilder.append("tbl.point_id=? ");
        }
        sqlBuilder.append("ORDER BY tbl.happened_date ASC");

        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        if (Objects.nonNull(pointKey)) {
            preparedStatement.setLong(1, pointKey.getLongId());
        }

        ResultSet resultSet = preparedStatement.executeQuery();
        List<PersistenceValue> persistenceValues = new ArrayList<>();
        while (resultSet.next()) {
            persistenceValues.add(new PersistenceValue(
                    new LongIdKey(resultSet.getLong(1)),
                    pointKey,
                    new Date(resultSet.getTimestamp(2).getTime()),
                    resultSet.getString(3)
            ));
        }
        return persistenceValues;
    }

    private List<PersistenceValue> childForPointBetween(Connection connection, Object[] args) throws SQLException {
        LongIdKey pointKey = (LongIdKey) args[0];
        Date startDate = (Date) args[1];
        Date endDate = (Date) args[2];

        StringBuilder sqlBuilder = new StringBuilder();
        selectColumn(sqlBuilder, "id", "happened_date", "value");
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date");
        sqlBuilder.append("WHERE ");
        if (Objects.isNull(pointKey)) {
            sqlBuilder.append("tbl.point_id IS NULL AND ");
        } else {
            sqlBuilder.append("tbl.point_id=? AND ");
        }
        sqlBuilder.append("tbl.happened_date>=? AND tbl.happened_date<=? ");
        sqlBuilder.append("ORDER BY tbl.happened_date ASC");

        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        if (Objects.isNull(pointKey)) {
            preparedStatement.setTimestamp(1, new Timestamp(startDate.getTime()));
            preparedStatement.setTimestamp(2, new Timestamp(endDate.getTime()));
        } else {
            preparedStatement.setLong(1, pointKey.getLongId());
            preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
            preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));
        }

        ResultSet resultSet = preparedStatement.executeQuery();
        List<PersistenceValue> persistenceValues = new ArrayList<>();
        while (resultSet.next()) {
            persistenceValues.add(new PersistenceValue(
                    new LongIdKey(resultSet.getLong(1)),
                    pointKey,
                    new Date(resultSet.getTimestamp(2).getTime()),
                    resultSet.getString(3)
            ));
        }
        return persistenceValues;
    }

    private List<PersistenceValue> childForPointBetweenRbOpen(Connection connection, Object[] args) throws SQLException {
        LongIdKey pointKey = (LongIdKey) args[0];
        Date startDate = (Date) args[1];
        Date endDate = (Date) args[2];

        StringBuilder sqlBuilder = new StringBuilder();
        selectColumn(sqlBuilder, "id", "happened_date", "value");
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date");
        sqlBuilder.append("WHERE ");
        if (Objects.isNull(pointKey)) {
            sqlBuilder.append("tbl.point_id IS NULL AND ");
        } else {
            sqlBuilder.append("tbl.point_id=? AND ");
        }
        sqlBuilder.append("tbl.happened_date>=? AND tbl.happened_date<? ");
        sqlBuilder.append("ORDER BY tbl.happened_date ASC");

        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        if (Objects.isNull(pointKey)) {
            preparedStatement.setTimestamp(1, new Timestamp(startDate.getTime()));
            preparedStatement.setTimestamp(2, new Timestamp(endDate.getTime()));
        } else {
            preparedStatement.setLong(1, pointKey.getLongId());
            preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
            preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));
        }

        ResultSet resultSet = preparedStatement.executeQuery();
        List<PersistenceValue> persistenceValues = new ArrayList<>();
        while (resultSet.next()) {
            persistenceValues.add(new PersistenceValue(
                    new LongIdKey(resultSet.getLong(1)),
                    pointKey,
                    new Date(resultSet.getTimestamp(2).getTime()),
                    resultSet.getString(3)
            ));
        }
        return persistenceValues;
    }

    private List<PersistenceValue> childForPointPrevious(Connection connection, Object[] args) throws SQLException {
        LongIdKey pointKey = (LongIdKey) args[0];
        Date date = (Date) args[1];

        StringBuilder sqlBuilder = new StringBuilder();
        selectColumn(sqlBuilder, "id", "happened_date", "value");
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date_desc");
        sqlBuilder.append("WHERE ");
        if (Objects.isNull(pointKey)) {
            sqlBuilder.append("tbl.point_id IS NULL AND ");
        } else {
            sqlBuilder.append("tbl.point_id=? AND ");
        }
        sqlBuilder.append("tbl.happened_date<? ");
        sqlBuilder.append("ORDER BY tbl.happened_date DESC");

        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        if (Objects.isNull(pointKey)) {
            preparedStatement.setTimestamp(1, new Timestamp(date.getTime()));
        } else {
            preparedStatement.setLong(1, pointKey.getLongId());
            preparedStatement.setTimestamp(2, new Timestamp(date.getTime()));
        }

        ResultSet resultSet = preparedStatement.executeQuery();
        List<PersistenceValue> persistenceValues = new ArrayList<>();
        while (resultSet.next()) {
            persistenceValues.add(new PersistenceValue(
                    new LongIdKey(resultSet.getLong(1)),
                    pointKey,
                    new Date(resultSet.getTimestamp(2).getTime()),
                    resultSet.getString(3)
            ));
        }
        return persistenceValues;
    }

    private List<PersistenceValue> childForPointRear(Connection connection, Object[] args) throws SQLException {
        LongIdKey pointKey = (LongIdKey) args[0];
        Date date = (Date) args[1];

        StringBuilder sqlBuilder = new StringBuilder();
        selectColumn(sqlBuilder, "id", "happened_date", "value");
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date");
        sqlBuilder.append("WHERE ");
        if (Objects.isNull(pointKey)) {
            sqlBuilder.append("tbl.point_id IS NULL AND ");
        } else {
            sqlBuilder.append("tbl.point_id=? AND ");
        }
        sqlBuilder.append("tbl.happened_date>? ");
        sqlBuilder.append("ORDER BY tbl.happened_date ASC");

        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        if (Objects.isNull(pointKey)) {
            preparedStatement.setTimestamp(1, new Timestamp(date.getTime()));
        } else {
            preparedStatement.setLong(1, pointKey.getLongId());
            preparedStatement.setTimestamp(2, new Timestamp(date.getTime()));
        }

        ResultSet resultSet = preparedStatement.executeQuery();
        List<PersistenceValue> persistenceValues = new ArrayList<>();
        while (resultSet.next()) {
            persistenceValues.add(new PersistenceValue(
                    new LongIdKey(resultSet.getLong(1)),
                    pointKey,
                    new Date(resultSet.getTimestamp(2).getTime()),
                    resultSet.getString(3)
            ));
        }
        return persistenceValues;
    }

    @Override
    public List<PersistenceValue> lookupEntity(
            Connection connection, String preset, Object[] args, PagingInfo pagingInfo
    ) throws SQLException {
        switch (preset) {
            case PersistenceValueMaintainService.BETWEEN:
                return between(connection, args, pagingInfo);
            case PersistenceValueMaintainService.CHILD_FOR_POINT:
                return childForPoint(connection, args, pagingInfo);
            case PersistenceValueMaintainService.CHILD_FOR_POINT_BETWEEN:
                return childForPointBetween(connection, args, pagingInfo);
            case PersistenceValueMaintainService.CHILD_FOR_POINT_BETWEEN_RB_OPEN:
                return childForPointBetweenRbOpen(connection, args, pagingInfo);
            case PersistenceValueMaintainService.CHILD_FOR_POINT_PREVIOUS:
                return childForPointPrevious(connection, args, pagingInfo);
            case PersistenceValueMaintainService.CHILD_FOR_POINT_REAR:
                return childForPointRear(connection, args, pagingInfo);
            default:
                throw new IllegalArgumentException("无法识别的预设: " + preset);
        }
    }

    private List<PersistenceValue> between(Connection connection, Object[] args, PagingInfo pagingInfo)
            throws SQLException {
        Date startDate = (Date) args[0];
        Date endDate = (Date) args[1];

        StringBuilder sqlBuilder = new StringBuilder();
        selectColumn(sqlBuilder, "id", "point_id", "happened_date", "value");
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_happened_date");
        sqlBuilder.append("WHERE tbl.happened_date>=? AND tbl.happened_date<=? ");
        sqlBuilder.append("ORDER BY tbl.happened_date ASC ");
        sqlBuilder.append("LIMIT ?, ?");

        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        preparedStatement.setTimestamp(1, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(2, new Timestamp(endDate.getTime()));
        preparedStatement.setInt(3, pagingInfo.getRows() * pagingInfo.getPage());
        preparedStatement.setInt(4, pagingInfo.getRows());

        ResultSet resultSet = preparedStatement.executeQuery();
        List<PersistenceValue> persistenceValues = new ArrayList<>();
        while (resultSet.next()) {
            persistenceValues.add(new PersistenceValue(
                    new LongIdKey(resultSet.getLong(1)),
                    new LongIdKey(resultSet.getLong(2)),
                    new Date(resultSet.getTimestamp(3).getTime()),
                    resultSet.getString(4)
            ));
        }
        return persistenceValues;
    }

    private List<PersistenceValue> childForPoint(Connection connection, Object[] args, PagingInfo pagingInfo)
            throws SQLException {
        LongIdKey pointKey = (LongIdKey) args[0];

        StringBuilder sqlBuilder = new StringBuilder();
        selectColumn(sqlBuilder, "id", "happened_date", "value");
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date");
        sqlBuilder.append("WHERE ");
        if (Objects.isNull(pointKey)) {
            sqlBuilder.append("tbl.point_id IS NULL ");
        } else {
            sqlBuilder.append("tbl.point_id=? ");
        }
        sqlBuilder.append("ORDER BY tbl.happened_date ASC ");
        sqlBuilder.append("LIMIT ?, ?");

        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        if (Objects.isNull(pointKey)) {
            preparedStatement.setInt(1, pagingInfo.getRows() * pagingInfo.getPage());
            preparedStatement.setInt(2, pagingInfo.getRows());
        } else {
            preparedStatement.setLong(1, pointKey.getLongId());
            preparedStatement.setInt(2, pagingInfo.getRows() * pagingInfo.getPage());
            preparedStatement.setInt(3, pagingInfo.getRows());
        }

        ResultSet resultSet = preparedStatement.executeQuery();
        List<PersistenceValue> persistenceValues = new ArrayList<>();
        while (resultSet.next()) {
            persistenceValues.add(new PersistenceValue(
                    new LongIdKey(resultSet.getLong(1)),
                    pointKey,
                    new Date(resultSet.getTimestamp(2).getTime()),
                    resultSet.getString(3)
            ));
        }
        return persistenceValues;
    }

    private List<PersistenceValue> childForPointBetween(Connection connection, Object[] args, PagingInfo pagingInfo)
            throws SQLException {
        LongIdKey pointKey = (LongIdKey) args[0];
        Date startDate = (Date) args[1];
        Date endDate = (Date) args[2];

        StringBuilder sqlBuilder = new StringBuilder();
        selectColumn(sqlBuilder, "id", "happened_date", "value");
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date");
        sqlBuilder.append("WHERE ");
        if (Objects.isNull(pointKey)) {
            sqlBuilder.append("tbl.point_id IS NULL AND ");
        } else {
            sqlBuilder.append("tbl.point_id=? AND ");
        }
        sqlBuilder.append("tbl.happened_date>=? AND tbl.happened_date<=? ");
        sqlBuilder.append("ORDER BY tbl.happened_date ASC ");
        sqlBuilder.append("LIMIT ?, ?");

        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        if (Objects.isNull(pointKey)) {
            preparedStatement.setTimestamp(1, new Timestamp(startDate.getTime()));
            preparedStatement.setTimestamp(2, new Timestamp(endDate.getTime()));
            preparedStatement.setInt(3, pagingInfo.getRows() * pagingInfo.getPage());
            preparedStatement.setInt(4, pagingInfo.getRows());
        } else {
            preparedStatement.setLong(1, pointKey.getLongId());
            preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
            preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));
            preparedStatement.setInt(4, pagingInfo.getRows() * pagingInfo.getPage());
            preparedStatement.setInt(5, pagingInfo.getRows());
        }

        ResultSet resultSet = preparedStatement.executeQuery();
        List<PersistenceValue> persistenceValues = new ArrayList<>();
        while (resultSet.next()) {
            persistenceValues.add(new PersistenceValue(
                    new LongIdKey(resultSet.getLong(1)),
                    pointKey,
                    new Date(resultSet.getTimestamp(2).getTime()),
                    resultSet.getString(3)
            ));
        }
        return persistenceValues;
    }

    private List<PersistenceValue> childForPointBetweenRbOpen(Connection connection, Object[] args, PagingInfo pagingInfo)
            throws SQLException {
        LongIdKey pointKey = (LongIdKey) args[0];
        Date startDate = (Date) args[1];
        Date endDate = (Date) args[2];

        StringBuilder sqlBuilder = new StringBuilder();
        selectColumn(sqlBuilder, "id", "happened_date", "value");
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date");
        sqlBuilder.append("WHERE ");
        if (Objects.isNull(pointKey)) {
            sqlBuilder.append("tbl.point_id IS NULL AND ");
        } else {
            sqlBuilder.append("tbl.point_id=? AND ");
        }
        sqlBuilder.append("tbl.happened_date>=? AND tbl.happened_date<? ");
        sqlBuilder.append("ORDER BY tbl.happened_date ASC ");
        sqlBuilder.append("LIMIT ?, ?");

        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        if (Objects.isNull(pointKey)) {
            preparedStatement.setTimestamp(1, new Timestamp(startDate.getTime()));
            preparedStatement.setTimestamp(2, new Timestamp(endDate.getTime()));
            preparedStatement.setInt(3, pagingInfo.getRows() * pagingInfo.getPage());
            preparedStatement.setInt(4, pagingInfo.getRows());
        } else {
            preparedStatement.setLong(1, pointKey.getLongId());
            preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
            preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));
            preparedStatement.setInt(4, pagingInfo.getRows() * pagingInfo.getPage());
            preparedStatement.setInt(5, pagingInfo.getRows());
        }

        ResultSet resultSet = preparedStatement.executeQuery();
        List<PersistenceValue> persistenceValues = new ArrayList<>();
        while (resultSet.next()) {
            persistenceValues.add(new PersistenceValue(
                    new LongIdKey(resultSet.getLong(1)),
                    pointKey,
                    new Date(resultSet.getTimestamp(2).getTime()),
                    resultSet.getString(3)
            ));
        }
        return persistenceValues;
    }

    private List<PersistenceValue> childForPointPrevious(Connection connection, Object[] args, PagingInfo pagingInfo)
            throws SQLException {
        LongIdKey pointKey = (LongIdKey) args[0];
        Date date = (Date) args[1];

        StringBuilder sqlBuilder = new StringBuilder();
        selectColumn(sqlBuilder, "id", "happened_date", "value");
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date_desc");
        sqlBuilder.append("WHERE ");
        if (Objects.isNull(pointKey)) {
            sqlBuilder.append("tbl.point_id IS NULL AND ");
        } else {
            sqlBuilder.append("tbl.point_id=? AND ");
        }
        sqlBuilder.append("tbl.happened_date<? ");
        sqlBuilder.append("ORDER BY tbl.happened_date DESC ");
        sqlBuilder.append("LIMIT ?, ?");

        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        if (Objects.isNull(pointKey)) {
            preparedStatement.setTimestamp(1, new Timestamp(date.getTime()));
            preparedStatement.setInt(2, pagingInfo.getRows() * pagingInfo.getPage());
            preparedStatement.setInt(3, pagingInfo.getRows());
        } else {
            preparedStatement.setLong(1, pointKey.getLongId());
            preparedStatement.setTimestamp(2, new Timestamp(date.getTime()));
            preparedStatement.setInt(3, pagingInfo.getRows() * pagingInfo.getPage());
            preparedStatement.setInt(4, pagingInfo.getRows());
        }

        ResultSet resultSet = preparedStatement.executeQuery();
        List<PersistenceValue> persistenceValues = new ArrayList<>();
        while (resultSet.next()) {
            persistenceValues.add(new PersistenceValue(
                    new LongIdKey(resultSet.getLong(1)),
                    pointKey,
                    new Date(resultSet.getTimestamp(2).getTime()),
                    resultSet.getString(3)
            ));
        }
        return persistenceValues;
    }

    private List<PersistenceValue> childForPointRear(Connection connection, Object[] args, PagingInfo pagingInfo)
            throws SQLException {
        LongIdKey pointKey = (LongIdKey) args[0];
        Date date = (Date) args[1];

        StringBuilder sqlBuilder = new StringBuilder();
        selectColumn(sqlBuilder, "id", "happened_date", "value");
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date");
        sqlBuilder.append("WHERE ");
        if (Objects.isNull(pointKey)) {
            sqlBuilder.append("tbl.point_id IS NULL AND ");
        } else {
            sqlBuilder.append("tbl.point_id=? AND ");
        }
        sqlBuilder.append("tbl.happened_date>? ");
        sqlBuilder.append("ORDER BY tbl.happened_date ASC ");
        sqlBuilder.append("LIMIT ?, ?");

        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        if (Objects.isNull(pointKey)) {
            preparedStatement.setTimestamp(1, new Timestamp(date.getTime()));
            preparedStatement.setInt(2, pagingInfo.getRows() * pagingInfo.getPage());
            preparedStatement.setInt(3, pagingInfo.getRows());
        } else {
            preparedStatement.setLong(1, pointKey.getLongId());
            preparedStatement.setTimestamp(2, new Timestamp(date.getTime()));
            preparedStatement.setInt(3, pagingInfo.getRows() * pagingInfo.getPage());
            preparedStatement.setInt(4, pagingInfo.getRows());
        }

        ResultSet resultSet = preparedStatement.executeQuery();
        List<PersistenceValue> persistenceValues = new ArrayList<>();
        while (resultSet.next()) {
            persistenceValues.add(new PersistenceValue(
                    new LongIdKey(resultSet.getLong(1)),
                    pointKey,
                    new Date(resultSet.getTimestamp(2).getTime()),
                    resultSet.getString(3)
            ));
        }
        return persistenceValues;
    }

    private void selectColumn(StringBuilder sqlBuilder, String... columns) {
        sqlBuilder.append("SELECT ");
        for (int i = 0; i < columns.length; i++) {
            String column = columns[i];
            sqlBuilder.append("tbl.").append(column);
            if (i < columns.length - 1) {
                sqlBuilder.append(",");
            } else {
                sqlBuilder.append(" ");
            }
        }
        sqlBuilder.append("FROM tbl_persistence_value AS tbl ");
    }

    @Override
    public int lookupCount(Connection connection, String preset, Object[] args) throws SQLException {
        switch (preset) {
            case PersistenceValueMaintainService.BETWEEN:
                return betweenCount(connection, args);
            case PersistenceValueMaintainService.CHILD_FOR_POINT:
                return childForPointCount(connection, args);
            case PersistenceValueMaintainService.CHILD_FOR_POINT_BETWEEN:
                return childForPointBetweenCount(connection, args);
            case PersistenceValueMaintainService.CHILD_FOR_POINT_BETWEEN_RB_OPEN:
                return childForPointBetweenRbOpenCount(connection, args);
            case PersistenceValueMaintainService.CHILD_FOR_POINT_PREVIOUS:
                return childForPointPreviousCount(connection, args);
            case PersistenceValueMaintainService.CHILD_FOR_POINT_REAR:
                return childForPointRearCount(connection, args);
            default:
                throw new IllegalArgumentException("无法识别的预设: " + preset);
        }
    }

    private int betweenCount(Connection connection, Object[] args) throws SQLException {
        Date startDate = (Date) args[0];
        Date endDate = (Date) args[1];

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT COUNT(tbl.id) FROM tbl_persistence_value AS tbl ");
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_happened_date");
        sqlBuilder.append("WHERE tbl.happened_date>=? AND tbl.happened_date<=?");

        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        preparedStatement.setTimestamp(1, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(2, new Timestamp(endDate.getTime()));

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return Long.valueOf(resultSet.getLong(1)).intValue();
    }

    private int childForPointCount(Connection connection, Object[] args) throws SQLException {
        LongIdKey pointKey = (LongIdKey) args[0];

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT COUNT(tbl.id) FROM tbl_persistence_value AS tbl ");
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date");
        sqlBuilder.append("WHERE ");
        if (Objects.isNull(pointKey)) {
            sqlBuilder.append("tbl.point_id IS NULL");
        } else {
            sqlBuilder.append("tbl.point_id=?");
        }

        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        if (Objects.nonNull(pointKey)) {
            preparedStatement.setLong(1, pointKey.getLongId());
        }

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return Long.valueOf(resultSet.getLong(1)).intValue();
    }

    private int childForPointBetweenCount(Connection connection, Object[] args) throws SQLException {
        LongIdKey pointKey = (LongIdKey) args[0];
        Date startDate = (Date) args[1];
        Date endDate = (Date) args[2];

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT COUNT(tbl.id) FROM tbl_persistence_value AS tbl ");
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date");
        sqlBuilder.append("WHERE ");
        if (Objects.isNull(pointKey)) {
            sqlBuilder.append("tbl.point_id IS NULL AND ");
        } else {
            sqlBuilder.append("tbl.point_id=? AND ");
        }
        sqlBuilder.append("tbl.happened_date>=? AND tbl.happened_date<=? ");

        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        if (Objects.isNull(pointKey)) {
            preparedStatement.setTimestamp(1, new Timestamp(startDate.getTime()));
            preparedStatement.setTimestamp(2, new Timestamp(endDate.getTime()));
        } else {
            preparedStatement.setLong(1, pointKey.getLongId());
            preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
            preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));
        }

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return Long.valueOf(resultSet.getLong(1)).intValue();
    }

    private int childForPointBetweenRbOpenCount(Connection connection, Object[] args) throws SQLException {
        LongIdKey pointKey = (LongIdKey) args[0];
        Date startDate = (Date) args[1];
        Date endDate = (Date) args[2];

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT COUNT(tbl.id) FROM tbl_persistence_value AS tbl ");
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date");
        sqlBuilder.append("WHERE ");
        if (Objects.isNull(pointKey)) {
            sqlBuilder.append("tbl.point_id IS NULL AND ");
        } else {
            sqlBuilder.append("tbl.point_id=? AND ");
        }
        sqlBuilder.append("tbl.happened_date>=? AND tbl.happened_date<? ");

        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        if (Objects.isNull(pointKey)) {
            preparedStatement.setTimestamp(1, new Timestamp(startDate.getTime()));
            preparedStatement.setTimestamp(2, new Timestamp(endDate.getTime()));
        } else {
            preparedStatement.setLong(1, pointKey.getLongId());
            preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
            preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));
        }

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return Long.valueOf(resultSet.getLong(1)).intValue();
    }

    private int childForPointPreviousCount(Connection connection, Object[] args) throws SQLException {
        LongIdKey pointKey = (LongIdKey) args[0];
        Date date = (Date) args[1];

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT COUNT(tbl.id) FROM tbl_persistence_value AS tbl ");
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date_desc");
        sqlBuilder.append("WHERE ");
        if (Objects.isNull(pointKey)) {
            sqlBuilder.append("tbl.point_id IS NULL AND ");
        } else {
            sqlBuilder.append("tbl.point_id=? AND ");
        }
        sqlBuilder.append("tbl.happened_date<?");

        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        if (Objects.isNull(pointKey)) {
            preparedStatement.setTimestamp(1, new Timestamp(date.getTime()));
        } else {
            preparedStatement.setLong(1, pointKey.getLongId());
            preparedStatement.setTimestamp(2, new Timestamp(date.getTime()));
        }

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return Long.valueOf(resultSet.getLong(1)).intValue();
    }

    private int childForPointRearCount(Connection connection, Object[] args) throws SQLException {
        LongIdKey pointKey = (LongIdKey) args[0];
        Date date = (Date) args[1];

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT COUNT(tbl.id) FROM tbl_persistence_value AS tbl ");
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date_desc");
        sqlBuilder.append("WHERE ");
        if (Objects.isNull(pointKey)) {
            sqlBuilder.append("tbl.point_id IS NULL AND ");
        } else {
            sqlBuilder.append("tbl.point_id=? AND ");
        }
        sqlBuilder.append("tbl.happened_date<?");

        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        if (Objects.isNull(pointKey)) {
            preparedStatement.setTimestamp(1, new Timestamp(date.getTime()));
        } else {
            preparedStatement.setLong(1, pointKey.getLongId());
            preparedStatement.setTimestamp(2, new Timestamp(date.getTime()));
        }

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return Long.valueOf(resultSet.getLong(1)).intValue();
    }
}
