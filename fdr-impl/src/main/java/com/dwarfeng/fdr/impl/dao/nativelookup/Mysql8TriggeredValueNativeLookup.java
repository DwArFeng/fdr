package com.dwarfeng.fdr.impl.dao.nativelookup;

import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.fdr.stack.service.TriggeredValueMaintainService;
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
public class Mysql8TriggeredValueNativeLookup extends AbstractDialectNativeLookup<TriggeredValue> {

    public static final String SUPPORT_TYPE = "org.hibernate.dialect.MySQL8Dialect";

    public Mysql8TriggeredValueNativeLookup() {
        super(SUPPORT_TYPE);
    }

    @Override
    public boolean supportPreset(String preset) {
        switch (preset) {
            case TriggeredValueMaintainService.BETWEEN:
            case TriggeredValueMaintainService.CHILD_FOR_POINT:
            case TriggeredValueMaintainService.CHILD_FOR_TRIGGER:
            case TriggeredValueMaintainService.CHILD_FOR_POINT_BETWEEN:
            case TriggeredValueMaintainService.CHILD_FOR_POINT_BETWEEN_RB_OPEN:
            case TriggeredValueMaintainService.CHILD_FOR_TRIGGER_BETWEEN:
            case TriggeredValueMaintainService.CHILD_FOR_POINT_PREVIOUS:
            case TriggeredValueMaintainService.CHILD_FOR_POINT_REAR:
                return true;
            default:
                return false;
        }
    }

    @Override
    public List<TriggeredValue> lookupEntity(Connection connection, String preset, Object[] args) throws SQLException {
        switch (preset) {
            case TriggeredValueMaintainService.BETWEEN:
                return between(connection, args);
            case TriggeredValueMaintainService.CHILD_FOR_POINT:
                return childForPoint(connection, args);
            case TriggeredValueMaintainService.CHILD_FOR_TRIGGER:
                return childForTrigger(connection, args);
            case TriggeredValueMaintainService.CHILD_FOR_POINT_BETWEEN:
                return childForPointBetween(connection, args);
            case TriggeredValueMaintainService.CHILD_FOR_POINT_BETWEEN_RB_OPEN:
                return childForPointBetweenRbOpen(connection, args);
            case TriggeredValueMaintainService.CHILD_FOR_TRIGGER_BETWEEN:
                return childForTriggerBetween(connection, args);
            case TriggeredValueMaintainService.CHILD_FOR_POINT_PREVIOUS:
                return childForPointPrevious(connection, args);
            case TriggeredValueMaintainService.CHILD_FOR_POINT_REAR:
                return childForPointRear(connection, args);
            default:
                throw new IllegalArgumentException("无法识别的预设: " + preset);
        }
    }

    private List<TriggeredValue> between(Connection connection, Object[] args) throws SQLException {
        Date startDate = (Date) args[0];
        Date endDate = (Date) args[1];

        StringBuilder sqlBuilder = new StringBuilder();
        selectColumn(sqlBuilder, "id", "point_id", "trigger_id", "happened_date", "value", "message");
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_happened_date");
        sqlBuilder.append("WHERE tbl.happened_date>=? AND tbl.happened_date<=? ");
        sqlBuilder.append("ORDER BY tbl.happened_date ASC");

        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        preparedStatement.setTimestamp(1, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(2, new Timestamp(endDate.getTime()));

        ResultSet resultSet = preparedStatement.executeQuery();
        List<TriggeredValue> TriggeredValues = new ArrayList<>();
        while (resultSet.next()) {
            TriggeredValues.add(new TriggeredValue(
                    new LongIdKey(resultSet.getLong(1)),
                    new LongIdKey(resultSet.getLong(2)),
                    new LongIdKey(resultSet.getLong(3)),
                    new Date(resultSet.getTimestamp(4).getTime()),
                    resultSet.getString(5),
                    resultSet.getString(6)
            ));
        }
        return TriggeredValues;
    }

    private List<TriggeredValue> childForPoint(Connection connection, Object[] args) throws SQLException {
        LongIdKey pointKey = (LongIdKey) args[0];

        StringBuilder sqlBuilder = new StringBuilder();
        selectColumn(sqlBuilder, "id", "trigger_id", "happened_date", "value", "message");
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
        List<TriggeredValue> triggeredValues = new ArrayList<>();
        while (resultSet.next()) {
            triggeredValues.add(new TriggeredValue(
                    new LongIdKey(resultSet.getLong(1)),
                    pointKey,
                    new LongIdKey(resultSet.getLong(2)),
                    new Date(resultSet.getTimestamp(3).getTime()),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return triggeredValues;
    }

    private List<TriggeredValue> childForTrigger(Connection connection, Object[] args) throws SQLException {
        LongIdKey triggerKey = (LongIdKey) args[0];

        StringBuilder sqlBuilder = new StringBuilder();
        selectColumn(sqlBuilder, "id", "point_id", "happened_date", "value", "message");
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_trigger_id_happened_date");
        sqlBuilder.append("WHERE ");
        if (Objects.isNull(triggerKey)) {
            sqlBuilder.append("tbl.trigger_id IS NULL ");
        } else {
            sqlBuilder.append("tbl.trigger_id=? ");
        }
        sqlBuilder.append("ORDER BY tbl.happened_date ASC");

        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        if (Objects.nonNull(triggerKey)) {
            preparedStatement.setLong(1, triggerKey.getLongId());
        }

        ResultSet resultSet = preparedStatement.executeQuery();
        List<TriggeredValue> triggeredValues = new ArrayList<>();
        while (resultSet.next()) {
            triggeredValues.add(new TriggeredValue(
                    new LongIdKey(resultSet.getLong(1)),
                    new LongIdKey(resultSet.getLong(2)),
                    triggerKey,
                    new Date(resultSet.getTimestamp(3).getTime()),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return triggeredValues;
    }

    private List<TriggeredValue> childForPointBetween(Connection connection, Object[] args) throws SQLException {
        LongIdKey pointKey = (LongIdKey) args[0];
        Date startDate = (Date) args[1];
        Date endDate = (Date) args[2];

        StringBuilder sqlBuilder = new StringBuilder();
        selectColumn(sqlBuilder, "id", "trigger_id", "happened_date", "value", "message");
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
        List<TriggeredValue> triggeredValues = new ArrayList<>();
        while (resultSet.next()) {
            triggeredValues.add(new TriggeredValue(
                    new LongIdKey(resultSet.getLong(1)),
                    pointKey,
                    new LongIdKey(resultSet.getLong(2)),
                    new Date(resultSet.getTimestamp(3).getTime()),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return triggeredValues;
    }

    private List<TriggeredValue> childForPointBetweenRbOpen(Connection connection, Object[] args) throws SQLException {
        LongIdKey pointKey = (LongIdKey) args[0];
        Date startDate = (Date) args[1];
        Date endDate = (Date) args[2];

        StringBuilder sqlBuilder = new StringBuilder();
        selectColumn(sqlBuilder, "id", "trigger_id", "happened_date", "value", "message");
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
        List<TriggeredValue> triggeredValues = new ArrayList<>();
        while (resultSet.next()) {
            triggeredValues.add(new TriggeredValue(
                    new LongIdKey(resultSet.getLong(1)),
                    pointKey,
                    new LongIdKey(resultSet.getLong(2)),
                    new Date(resultSet.getTimestamp(3).getTime()),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return triggeredValues;
    }

    private List<TriggeredValue> childForTriggerBetween(Connection connection, Object[] args) throws SQLException {
        LongIdKey triggerKey = (LongIdKey) args[0];
        Date startDate = (Date) args[1];
        Date endDate = (Date) args[2];

        StringBuilder sqlBuilder = new StringBuilder();
        selectColumn(sqlBuilder, "id", "point_id", "happened_date", "value", "message");
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_trigger_id_happened_date");
        sqlBuilder.append("WHERE ");
        if (Objects.isNull(triggerKey)) {
            sqlBuilder.append("tbl.trigger_id IS NULL AND ");
        } else {
            sqlBuilder.append("tbl.trigger_id=? AND ");
        }
        sqlBuilder.append("tbl.happened_date>=? AND tbl.happened_date<=? ");
        sqlBuilder.append("ORDER BY tbl.happened_date ASC");

        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        if (Objects.isNull(triggerKey)) {
            preparedStatement.setTimestamp(1, new Timestamp(startDate.getTime()));
            preparedStatement.setTimestamp(2, new Timestamp(endDate.getTime()));
        } else {
            preparedStatement.setLong(1, triggerKey.getLongId());
            preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
            preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));
        }

        ResultSet resultSet = preparedStatement.executeQuery();
        List<TriggeredValue> triggeredValues = new ArrayList<>();
        while (resultSet.next()) {
            triggeredValues.add(new TriggeredValue(
                    new LongIdKey(resultSet.getLong(1)),
                    new LongIdKey(resultSet.getLong(2)),
                    triggerKey,
                    new Date(resultSet.getTimestamp(3).getTime()),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return triggeredValues;
    }

    private List<TriggeredValue> childForPointPrevious(Connection connection, Object[] args) throws SQLException {
        LongIdKey pointKey = (LongIdKey) args[0];
        Date date = (Date) args[1];

        StringBuilder sqlBuilder = new StringBuilder();
        selectColumn(sqlBuilder, "id", "trigger_id", "happened_date", "value", "message");
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
        List<TriggeredValue> triggeredValues = new ArrayList<>();
        while (resultSet.next()) {
            triggeredValues.add(new TriggeredValue(
                    new LongIdKey(resultSet.getLong(1)),
                    pointKey,
                    new LongIdKey(resultSet.getLong(2)),
                    new Date(resultSet.getTimestamp(3).getTime()),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return triggeredValues;
    }

    private List<TriggeredValue> childForPointRear(Connection connection, Object[] args) throws SQLException {
        LongIdKey pointKey = (LongIdKey) args[0];
        Date date = (Date) args[1];

        StringBuilder sqlBuilder = new StringBuilder();
        selectColumn(sqlBuilder, "id", "trigger_id", "happened_date", "value", "message");
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
        List<TriggeredValue> triggeredValues = new ArrayList<>();
        while (resultSet.next()) {
            triggeredValues.add(new TriggeredValue(
                    new LongIdKey(resultSet.getLong(1)),
                    pointKey,
                    new LongIdKey(resultSet.getLong(2)),
                    new Date(resultSet.getTimestamp(3).getTime()),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return triggeredValues;
    }

    @Override
    public List<TriggeredValue> lookupEntity(Connection connection, String preset, Object[] args, PagingInfo pagingInfo)
            throws SQLException {
        switch (preset) {
            case TriggeredValueMaintainService.BETWEEN:
                return between(connection, args, pagingInfo);
            case TriggeredValueMaintainService.CHILD_FOR_POINT:
                return childForPoint(connection, args, pagingInfo);
            case TriggeredValueMaintainService.CHILD_FOR_TRIGGER:
                return childForTrigger(connection, args, pagingInfo);
            case TriggeredValueMaintainService.CHILD_FOR_POINT_BETWEEN:
                return childForPointBetween(connection, args, pagingInfo);
            case TriggeredValueMaintainService.CHILD_FOR_TRIGGER_BETWEEN:
                return childForTriggerBetween(connection, args, pagingInfo);
            case TriggeredValueMaintainService.CHILD_FOR_POINT_PREVIOUS:
                return childForPointPrevious(connection, args, pagingInfo);
            case TriggeredValueMaintainService.CHILD_FOR_POINT_REAR:
                return childForPointRear(connection, args, pagingInfo);
            default:
                throw new IllegalArgumentException("无法识别的预设: " + preset);
        }
    }

    private List<TriggeredValue> between(Connection connection, Object[] args, PagingInfo pagingInfo)
            throws SQLException {
        Date startDate = (Date) args[0];
        Date endDate = (Date) args[1];

        StringBuilder sqlBuilder = new StringBuilder();
        selectColumn(sqlBuilder, "id", "point_id", "trigger_id", "happened_date", "value", "message");
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_happened_date");
        sqlBuilder.append("WHERE tbl.happened_date>=? AND tbl.happened_date<? ");
        sqlBuilder.append("ORDER BY tbl.happened_date ASC ");
        sqlBuilder.append("LIMIT ?, ?");

        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        preparedStatement.setTimestamp(1, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(2, new Timestamp(endDate.getTime()));
        preparedStatement.setInt(3, pagingInfo.getRows() * pagingInfo.getPage());
        preparedStatement.setInt(4, pagingInfo.getRows());

        ResultSet resultSet = preparedStatement.executeQuery();
        List<TriggeredValue> TriggeredValues = new ArrayList<>();
        while (resultSet.next()) {
            TriggeredValues.add(new TriggeredValue(
                    new LongIdKey(resultSet.getLong(1)),
                    new LongIdKey(resultSet.getLong(2)),
                    new LongIdKey(resultSet.getLong(3)),
                    new Date(resultSet.getTimestamp(4).getTime()),
                    resultSet.getString(5),
                    resultSet.getString(6)
            ));
        }
        return TriggeredValues;
    }

    private List<TriggeredValue> childForPoint(Connection connection, Object[] args, PagingInfo pagingInfo)
            throws SQLException {
        LongIdKey pointKey = (LongIdKey) args[0];

        StringBuilder sqlBuilder = new StringBuilder();
        selectColumn(sqlBuilder, "id", "trigger_id", "happened_date", "value", "message");
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
        List<TriggeredValue> triggeredValues = new ArrayList<>();
        while (resultSet.next()) {
            triggeredValues.add(new TriggeredValue(
                    new LongIdKey(resultSet.getLong(1)),
                    pointKey,
                    new LongIdKey(resultSet.getLong(2)),
                    new Date(resultSet.getTimestamp(3).getTime()),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return triggeredValues;
    }

    private List<TriggeredValue> childForTrigger(Connection connection, Object[] args, PagingInfo pagingInfo)
            throws SQLException {
        LongIdKey triggerKey = (LongIdKey) args[0];

        StringBuilder sqlBuilder = new StringBuilder();
        selectColumn(sqlBuilder, "id", "point_id", "happened_date", "value", "message");
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_trigger_id_happened_date");
        sqlBuilder.append("WHERE ");
        if (Objects.isNull(triggerKey)) {
            sqlBuilder.append("tbl.trigger_id IS NULL ");
        } else {
            sqlBuilder.append("tbl.trigger_id=? ");
        }
        sqlBuilder.append("ORDER BY tbl.happened_date ASC ");
        sqlBuilder.append("LIMIT ?, ?");

        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        if (Objects.isNull(triggerKey)) {
            preparedStatement.setInt(1, pagingInfo.getRows() * pagingInfo.getPage());
            preparedStatement.setInt(2, pagingInfo.getRows());
        } else {
            preparedStatement.setLong(1, triggerKey.getLongId());
            preparedStatement.setInt(2, pagingInfo.getRows() * pagingInfo.getPage());
            preparedStatement.setInt(3, pagingInfo.getRows());
        }

        ResultSet resultSet = preparedStatement.executeQuery();
        List<TriggeredValue> triggeredValues = new ArrayList<>();
        while (resultSet.next()) {
            triggeredValues.add(new TriggeredValue(
                    new LongIdKey(resultSet.getLong(1)),
                    new LongIdKey(resultSet.getLong(2)),
                    triggerKey,
                    new Date(resultSet.getTimestamp(3).getTime()),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return triggeredValues;
    }

    private List<TriggeredValue> childForPointBetween(Connection connection, Object[] args, PagingInfo pagingInfo)
            throws SQLException {
        LongIdKey pointKey = (LongIdKey) args[0];
        Date startDate = (Date) args[1];
        Date endDate = (Date) args[2];

        StringBuilder sqlBuilder = new StringBuilder();
        selectColumn(sqlBuilder, "id", "trigger_id", "happened_date", "value", "message");
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
        List<TriggeredValue> triggeredValues = new ArrayList<>();
        while (resultSet.next()) {
            triggeredValues.add(new TriggeredValue(
                    new LongIdKey(resultSet.getLong(1)),
                    pointKey,
                    new LongIdKey(resultSet.getLong(2)),
                    new Date(resultSet.getTimestamp(3).getTime()),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return triggeredValues;
    }

    private List<TriggeredValue> childForTriggerBetween(Connection connection, Object[] args, PagingInfo pagingInfo)
            throws SQLException {
        LongIdKey triggerKey = (LongIdKey) args[0];
        Date startDate = (Date) args[1];
        Date endDate = (Date) args[2];

        StringBuilder sqlBuilder = new StringBuilder();
        selectColumn(sqlBuilder, "id", "point_id", "happened_date", "value", "message");
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_trigger_id_happened_date");
        sqlBuilder.append("WHERE ");
        if (Objects.isNull(triggerKey)) {
            sqlBuilder.append("tbl.trigger_id IS NULL AND ");
        } else {
            sqlBuilder.append("tbl.trigger_id=? AND ");
        }
        sqlBuilder.append("tbl.happened_date>=? AND tbl.happened_date<? ");
        sqlBuilder.append("ORDER BY tbl.happened_date ASC ");
        sqlBuilder.append("LIMIT ?, ?");

        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        if (Objects.isNull(triggerKey)) {
            preparedStatement.setTimestamp(1, new Timestamp(startDate.getTime()));
            preparedStatement.setTimestamp(2, new Timestamp(endDate.getTime()));
            preparedStatement.setInt(3, pagingInfo.getRows() * pagingInfo.getPage());
            preparedStatement.setInt(4, pagingInfo.getRows());
        } else {
            preparedStatement.setLong(1, triggerKey.getLongId());
            preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
            preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));
            preparedStatement.setInt(4, pagingInfo.getRows() * pagingInfo.getPage());
            preparedStatement.setInt(5, pagingInfo.getRows());
        }

        ResultSet resultSet = preparedStatement.executeQuery();
        List<TriggeredValue> triggeredValues = new ArrayList<>();
        while (resultSet.next()) {
            triggeredValues.add(new TriggeredValue(
                    new LongIdKey(resultSet.getLong(1)),
                    new LongIdKey(resultSet.getLong(2)),
                    triggerKey,
                    new Date(resultSet.getTimestamp(3).getTime()),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return triggeredValues;
    }

    private List<TriggeredValue> childForPointPrevious(Connection connection, Object[] args, PagingInfo pagingInfo)
            throws SQLException {
        LongIdKey pointKey = (LongIdKey) args[0];
        Date date = (Date) args[1];

        StringBuilder sqlBuilder = new StringBuilder();
        selectColumn(sqlBuilder, "id", "trigger_id", "happened_date", "value", "message");
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
        List<TriggeredValue> triggeredValues = new ArrayList<>();
        while (resultSet.next()) {
            triggeredValues.add(new TriggeredValue(
                    new LongIdKey(resultSet.getLong(1)),
                    pointKey,
                    new LongIdKey(resultSet.getLong(2)),
                    new Date(resultSet.getTimestamp(3).getTime()),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return triggeredValues;
    }

    private List<TriggeredValue> childForPointRear(Connection connection, Object[] args, PagingInfo pagingInfo)
            throws SQLException {
        LongIdKey pointKey = (LongIdKey) args[0];
        Date date = (Date) args[1];

        StringBuilder sqlBuilder = new StringBuilder();
        selectColumn(sqlBuilder, "id", "trigger_id", "happened_date", "value", "message");
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
        List<TriggeredValue> triggeredValues = new ArrayList<>();
        while (resultSet.next()) {
            triggeredValues.add(new TriggeredValue(
                    new LongIdKey(resultSet.getLong(1)),
                    pointKey,
                    new LongIdKey(resultSet.getLong(2)),
                    new Date(resultSet.getTimestamp(3).getTime()),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return triggeredValues;
    }

    @Override
    public int lookupCount(Connection connection, String preset, Object[] args) throws SQLException {
        switch (preset) {
            case TriggeredValueMaintainService.BETWEEN:
                return betweenCount(connection, args);
            case TriggeredValueMaintainService.CHILD_FOR_POINT:
                return childForPointCount(connection, args);
            case TriggeredValueMaintainService.CHILD_FOR_TRIGGER:
                return childForTriggerCount(connection, args);
            case TriggeredValueMaintainService.CHILD_FOR_POINT_BETWEEN:
                return childForPointBetweenCount(connection, args);
            case TriggeredValueMaintainService.CHILD_FOR_TRIGGER_BETWEEN:
                return childForTriggerBetweenCount(connection, args);
            case TriggeredValueMaintainService.CHILD_FOR_POINT_PREVIOUS:
                return childForPointPreviousCount(connection, args);
            case TriggeredValueMaintainService.CHILD_FOR_POINT_REAR:
                return childForPointRearCount(connection, args);
            default:
                throw new IllegalArgumentException("无法识别的预设: " + preset);
        }
    }

    private int betweenCount(Connection connection, Object[] args) throws SQLException {
        Date startDate = (Date) args[0];
        Date endDate = (Date) args[1];

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT COUNT(tbl.id) FROM tbl_trigger_value AS tbl ");
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_happened_date");
        sqlBuilder.append("WHERE tbl.happened_date>=? AND tbl.happened_date<?");

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
        sqlBuilder.append("SELECT COUNT(tbl.id) FROM tbl_trigger_value AS tbl ");
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

    private int childForTriggerCount(Connection connection, Object[] args) throws SQLException {
        LongIdKey triggerKey = (LongIdKey) args[0];

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT COUNT(tbl.id) FROM tbl_trigger_value AS tbl ");
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_trigger_id_happened_date");
        sqlBuilder.append("WHERE ");
        if (Objects.isNull(triggerKey)) {
            sqlBuilder.append("tbl.trigger_id IS NULL");
        } else {
            sqlBuilder.append("tbl.trigger_id=?");
        }

        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        if (Objects.nonNull(triggerKey)) {
            preparedStatement.setLong(1, triggerKey.getLongId());
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
        sqlBuilder.append("SELECT COUNT(tbl.id) FROM tbl_trigger_value AS tbl ");
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date");
        sqlBuilder.append("WHERE ");
        if (Objects.isNull(pointKey)) {
            sqlBuilder.append("tbl.point_id IS NULL AND ");
        } else {
            sqlBuilder.append("tbl.point_id=? AND ");
        }
        sqlBuilder.append("tbl.happened_date>=? AND tbl.happened_date<?");

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

    private int childForTriggerBetweenCount(Connection connection, Object[] args) throws SQLException {
        LongIdKey triggerKey = (LongIdKey) args[0];
        Date startDate = (Date) args[1];
        Date endDate = (Date) args[2];

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT COUNT(tbl.id) FROM tbl_trigger_value AS tbl ");
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_trigger_id_happened_date");
        sqlBuilder.append("WHERE ");
        if (Objects.isNull(triggerKey)) {
            sqlBuilder.append("tbl.trigger_id IS NULL AND ");
        } else {
            sqlBuilder.append("tbl.trigger_id=? AND ");
        }
        sqlBuilder.append("tbl.happened_date>=? AND tbl.happened_date<?");

        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        if (Objects.isNull(triggerKey)) {
            preparedStatement.setTimestamp(1, new Timestamp(startDate.getTime()));
            preparedStatement.setTimestamp(2, new Timestamp(endDate.getTime()));
        } else {
            preparedStatement.setLong(1, triggerKey.getLongId());
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
        sqlBuilder.append("SELECT COUNT(tbl.id) FROM tbl_trigger_value AS tbl ");
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
        sqlBuilder.append("SELECT COUNT(tbl.id) FROM tbl_trigger_value AS tbl ");
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date");
        sqlBuilder.append("WHERE ");
        if (Objects.isNull(pointKey)) {
            sqlBuilder.append("tbl.point_id IS NULL AND ");
        } else {
            sqlBuilder.append("tbl.point_id=? AND ");
        }
        sqlBuilder.append("tbl.happened_date>?");

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
        sqlBuilder.append("FROM tbl_triggered_value AS tbl ");
    }
}
