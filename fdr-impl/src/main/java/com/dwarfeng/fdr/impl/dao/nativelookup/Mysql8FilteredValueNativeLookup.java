package com.dwarfeng.fdr.impl.dao.nativelookup;

import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.service.FilteredValueMaintainService;
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
public class Mysql8FilteredValueNativeLookup extends AbstractDialectNativeLookup<FilteredValue> {

    public static final String SUPPORT_TYPE = "org.hibernate.dialect.MySQL8Dialect";

    public Mysql8FilteredValueNativeLookup() {
        super(SUPPORT_TYPE);
    }

    @Override
    public boolean supportPreset(String preset) {
        switch (preset) {
            case FilteredValueMaintainService.BETWEEN:
            case FilteredValueMaintainService.CHILD_FOR_POINT:
            case FilteredValueMaintainService.CHILD_FOR_FILTER:
            case FilteredValueMaintainService.CHILD_FOR_POINT_BETWEEN:
            case FilteredValueMaintainService.CHILD_FOR_POINT_BETWEEN_RB_OPEN:
            case FilteredValueMaintainService.CHILD_FOR_FILTER_BETWEEN:
            case FilteredValueMaintainService.CHILD_FOR_POINT_PREVIOUS:
            case FilteredValueMaintainService.CHILD_FOR_POINT_REAR:
                return true;
            default:
                return false;
        }
    }

    @Override
    public List<FilteredValue> lookupEntity(Connection connection, String preset, Object[] args) throws SQLException {
        switch (preset) {
            case FilteredValueMaintainService.BETWEEN:
                return between(connection, args);
            case FilteredValueMaintainService.CHILD_FOR_POINT:
                return childForPoint(connection, args);
            case FilteredValueMaintainService.CHILD_FOR_FILTER:
                return childForFilter(connection, args);
            case FilteredValueMaintainService.CHILD_FOR_POINT_BETWEEN:
                return childForPointBetween(connection, args);
            case FilteredValueMaintainService.CHILD_FOR_POINT_BETWEEN_RB_OPEN:
                return childForPointBetweenRbOpen(connection, args);
            case FilteredValueMaintainService.CHILD_FOR_FILTER_BETWEEN:
                return childForFilterBetween(connection, args);
            case FilteredValueMaintainService.CHILD_FOR_POINT_PREVIOUS:
                return childForPointPrevious(connection, args);
            case FilteredValueMaintainService.CHILD_FOR_POINT_REAR:
                return childForPointRear(connection, args);
            default:
                throw new IllegalArgumentException("无法识别的预设: " + preset);
        }
    }

    private List<FilteredValue> between(Connection connection, Object[] args) throws SQLException {
        Date startDate = (Date) args[0];
        Date endDate = (Date) args[1];

        StringBuilder sqlBuilder = new StringBuilder();
        selectColumn(sqlBuilder, "id", "point_id", "filter_id", "happened_date", "value", "message");
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_happened_date");
        sqlBuilder.append("WHERE tbl.happened_date>=? AND tbl.happened_date<=? ");
        sqlBuilder.append("ORDER BY tbl.happened_date ASC");

        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        preparedStatement.setTimestamp(1, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(2, new Timestamp(endDate.getTime()));

        ResultSet resultSet = preparedStatement.executeQuery();
        List<FilteredValue> FilteredValues = new ArrayList<>();
        while (resultSet.next()) {
            FilteredValues.add(new FilteredValue(
                    new LongIdKey(resultSet.getLong(1)),
                    new LongIdKey(resultSet.getLong(2)),
                    new LongIdKey(resultSet.getLong(3)),
                    new Date(resultSet.getTimestamp(4).getTime()),
                    resultSet.getString(5),
                    resultSet.getString(6)
            ));
        }
        return FilteredValues;
    }

    private List<FilteredValue> childForPoint(Connection connection, Object[] args) throws SQLException {
        LongIdKey pointKey = (LongIdKey) args[0];

        StringBuilder sqlBuilder = new StringBuilder();
        selectColumn(sqlBuilder, "id", "filter_id", "happened_date", "value", "message");
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
        List<FilteredValue> filteredValues = new ArrayList<>();
        while (resultSet.next()) {
            filteredValues.add(new FilteredValue(
                    new LongIdKey(resultSet.getLong(1)),
                    pointKey,
                    new LongIdKey(resultSet.getLong(2)),
                    new Date(resultSet.getTimestamp(3).getTime()),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return filteredValues;
    }

    private List<FilteredValue> childForFilter(Connection connection, Object[] args) throws SQLException {
        LongIdKey filterKey = (LongIdKey) args[0];

        StringBuilder sqlBuilder = new StringBuilder();
        selectColumn(sqlBuilder, "id", "point_id", "happened_date", "value", "message");
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_filter_id_happened_date");
        sqlBuilder.append("WHERE ");
        if (Objects.isNull(filterKey)) {
            sqlBuilder.append("tbl.filter_id IS NULL ");
        } else {
            sqlBuilder.append("tbl.filter_id=? ");
        }
        sqlBuilder.append("ORDER BY tbl.happened_date ASC");

        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        if (Objects.nonNull(filterKey)) {
            preparedStatement.setLong(1, filterKey.getLongId());
        }

        ResultSet resultSet = preparedStatement.executeQuery();
        List<FilteredValue> filteredValues = new ArrayList<>();
        while (resultSet.next()) {
            filteredValues.add(new FilteredValue(
                    new LongIdKey(resultSet.getLong(1)),
                    new LongIdKey(resultSet.getLong(2)),
                    filterKey,
                    new Date(resultSet.getTimestamp(3).getTime()),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return filteredValues;
    }

    private List<FilteredValue> childForPointBetween(Connection connection, Object[] args) throws SQLException {
        LongIdKey pointKey = (LongIdKey) args[0];
        Date startDate = (Date) args[1];
        Date endDate = (Date) args[2];

        StringBuilder sqlBuilder = new StringBuilder();
        selectColumn(sqlBuilder, "id", "filter_id", "happened_date", "value", "message");
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
        List<FilteredValue> filteredValues = new ArrayList<>();
        while (resultSet.next()) {
            filteredValues.add(new FilteredValue(
                    new LongIdKey(resultSet.getLong(1)),
                    pointKey,
                    new LongIdKey(resultSet.getLong(2)),
                    new Date(resultSet.getTimestamp(3).getTime()),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return filteredValues;
    }

    private List<FilteredValue> childForPointBetweenRbOpen(Connection connection, Object[] args) throws SQLException {
        LongIdKey pointKey = (LongIdKey) args[0];
        Date startDate = (Date) args[1];
        Date endDate = (Date) args[2];

        StringBuilder sqlBuilder = new StringBuilder();
        selectColumn(sqlBuilder, "id", "filter_id", "happened_date", "value", "message");
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
        List<FilteredValue> filteredValues = new ArrayList<>();
        while (resultSet.next()) {
            filteredValues.add(new FilteredValue(
                    new LongIdKey(resultSet.getLong(1)),
                    pointKey,
                    new LongIdKey(resultSet.getLong(2)),
                    new Date(resultSet.getTimestamp(3).getTime()),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return filteredValues;
    }

    private List<FilteredValue> childForFilterBetween(Connection connection, Object[] args) throws SQLException {
        LongIdKey filterKey = (LongIdKey) args[0];
        Date startDate = (Date) args[1];
        Date endDate = (Date) args[2];

        StringBuilder sqlBuilder = new StringBuilder();
        selectColumn(sqlBuilder, "id", "point_id", "happened_date", "value", "message");
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_filter_id_happened_date");
        sqlBuilder.append("WHERE ");
        if (Objects.isNull(filterKey)) {
            sqlBuilder.append("tbl.filter_id IS NULL AND ");
        } else {
            sqlBuilder.append("tbl.filter_id=? AND ");
        }
        sqlBuilder.append("tbl.happened_date>=? AND tbl.happened_date<=? ");
        sqlBuilder.append("ORDER BY tbl.happened_date ASC");

        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        if (Objects.isNull(filterKey)) {
            preparedStatement.setTimestamp(1, new Timestamp(startDate.getTime()));
            preparedStatement.setTimestamp(2, new Timestamp(endDate.getTime()));
        } else {
            preparedStatement.setLong(1, filterKey.getLongId());
            preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
            preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));
        }

        ResultSet resultSet = preparedStatement.executeQuery();
        List<FilteredValue> filteredValues = new ArrayList<>();
        while (resultSet.next()) {
            filteredValues.add(new FilteredValue(
                    new LongIdKey(resultSet.getLong(1)),
                    new LongIdKey(resultSet.getLong(2)),
                    filterKey,
                    new Date(resultSet.getTimestamp(3).getTime()),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return filteredValues;
    }

    private List<FilteredValue> childForPointPrevious(Connection connection, Object[] args) throws SQLException {
        LongIdKey pointKey = (LongIdKey) args[0];
        Date date = (Date) args[1];

        StringBuilder sqlBuilder = new StringBuilder();
        selectColumn(sqlBuilder, "id", "filter_id", "happened_date", "value", "message");
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
        List<FilteredValue> filteredValues = new ArrayList<>();
        while (resultSet.next()) {
            filteredValues.add(new FilteredValue(
                    new LongIdKey(resultSet.getLong(1)),
                    pointKey,
                    new LongIdKey(resultSet.getLong(2)),
                    new Date(resultSet.getTimestamp(3).getTime()),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return filteredValues;
    }

    private List<FilteredValue> childForPointRear(Connection connection, Object[] args) throws SQLException {
        LongIdKey pointKey = (LongIdKey) args[0];
        Date date = (Date) args[1];

        StringBuilder sqlBuilder = new StringBuilder();
        selectColumn(sqlBuilder, "id", "filter_id", "happened_date", "value", "message");
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
        List<FilteredValue> filteredValues = new ArrayList<>();
        while (resultSet.next()) {
            filteredValues.add(new FilteredValue(
                    new LongIdKey(resultSet.getLong(1)),
                    pointKey,
                    new LongIdKey(resultSet.getLong(2)),
                    new Date(resultSet.getTimestamp(3).getTime()),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return filteredValues;
    }

    @Override
    public List<FilteredValue> lookupEntity(Connection connection, String preset, Object[] args, PagingInfo pagingInfo)
            throws SQLException {
        switch (preset) {
            case FilteredValueMaintainService.BETWEEN:
                return between(connection, args, pagingInfo);
            case FilteredValueMaintainService.CHILD_FOR_POINT:
                return childForPoint(connection, args, pagingInfo);
            case FilteredValueMaintainService.CHILD_FOR_FILTER:
                return childForFilter(connection, args, pagingInfo);
            case FilteredValueMaintainService.CHILD_FOR_POINT_BETWEEN:
                return childForPointBetween(connection, args, pagingInfo);
            case FilteredValueMaintainService.CHILD_FOR_FILTER_BETWEEN:
                return childForFilterBetween(connection, args, pagingInfo);
            case FilteredValueMaintainService.CHILD_FOR_POINT_PREVIOUS:
                return childForPointPrevious(connection, args, pagingInfo);
            case FilteredValueMaintainService.CHILD_FOR_POINT_REAR:
                return childForPointRear(connection, args, pagingInfo);
            default:
                throw new IllegalArgumentException("无法识别的预设: " + preset);
        }
    }

    private List<FilteredValue> between(Connection connection, Object[] args, PagingInfo pagingInfo)
            throws SQLException {
        Date startDate = (Date) args[0];
        Date endDate = (Date) args[1];

        StringBuilder sqlBuilder = new StringBuilder();
        selectColumn(sqlBuilder, "id", "point_id", "filter_id", "happened_date", "value", "message");
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
        List<FilteredValue> FilteredValues = new ArrayList<>();
        while (resultSet.next()) {
            FilteredValues.add(new FilteredValue(
                    new LongIdKey(resultSet.getLong(1)),
                    new LongIdKey(resultSet.getLong(2)),
                    new LongIdKey(resultSet.getLong(3)),
                    new Date(resultSet.getTimestamp(4).getTime()),
                    resultSet.getString(5),
                    resultSet.getString(6)
            ));
        }
        return FilteredValues;
    }

    private List<FilteredValue> childForPoint(Connection connection, Object[] args, PagingInfo pagingInfo)
            throws SQLException {
        LongIdKey pointKey = (LongIdKey) args[0];

        StringBuilder sqlBuilder = new StringBuilder();
        selectColumn(sqlBuilder, "id", "filter_id", "happened_date", "value", "message");
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
        List<FilteredValue> filteredValues = new ArrayList<>();
        while (resultSet.next()) {
            filteredValues.add(new FilteredValue(
                    new LongIdKey(resultSet.getLong(1)),
                    pointKey,
                    new LongIdKey(resultSet.getLong(2)),
                    new Date(resultSet.getTimestamp(3).getTime()),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return filteredValues;
    }

    private List<FilteredValue> childForFilter(Connection connection, Object[] args, PagingInfo pagingInfo)
            throws SQLException {
        LongIdKey filterKey = (LongIdKey) args[0];

        StringBuilder sqlBuilder = new StringBuilder();
        selectColumn(sqlBuilder, "id", "point_id", "happened_date", "value", "message");
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_filter_id_happened_date");
        sqlBuilder.append("WHERE ");
        if (Objects.isNull(filterKey)) {
            sqlBuilder.append("tbl.filter_id IS NULL ");
        } else {
            sqlBuilder.append("tbl.filter_id=? ");
        }
        sqlBuilder.append("ORDER BY tbl.happened_date ASC ");
        sqlBuilder.append("LIMIT ?, ?");

        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        if (Objects.isNull(filterKey)) {
            preparedStatement.setInt(1, pagingInfo.getRows() * pagingInfo.getPage());
            preparedStatement.setInt(2, pagingInfo.getRows());
        } else {
            preparedStatement.setLong(1, filterKey.getLongId());
            preparedStatement.setInt(2, pagingInfo.getRows() * pagingInfo.getPage());
            preparedStatement.setInt(3, pagingInfo.getRows());
        }

        ResultSet resultSet = preparedStatement.executeQuery();
        List<FilteredValue> filteredValues = new ArrayList<>();
        while (resultSet.next()) {
            filteredValues.add(new FilteredValue(
                    new LongIdKey(resultSet.getLong(1)),
                    new LongIdKey(resultSet.getLong(2)),
                    filterKey,
                    new Date(resultSet.getTimestamp(3).getTime()),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return filteredValues;
    }

    private List<FilteredValue> childForPointBetween(Connection connection, Object[] args, PagingInfo pagingInfo)
            throws SQLException {
        LongIdKey pointKey = (LongIdKey) args[0];
        Date startDate = (Date) args[1];
        Date endDate = (Date) args[2];

        StringBuilder sqlBuilder = new StringBuilder();
        selectColumn(sqlBuilder, "id", "filter_id", "happened_date", "value", "message");
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
        List<FilteredValue> filteredValues = new ArrayList<>();
        while (resultSet.next()) {
            filteredValues.add(new FilteredValue(
                    new LongIdKey(resultSet.getLong(1)),
                    pointKey,
                    new LongIdKey(resultSet.getLong(2)),
                    new Date(resultSet.getTimestamp(3).getTime()),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return filteredValues;
    }

    private List<FilteredValue> childForFilterBetween(Connection connection, Object[] args, PagingInfo pagingInfo)
            throws SQLException {
        LongIdKey filterKey = (LongIdKey) args[0];
        Date startDate = (Date) args[1];
        Date endDate = (Date) args[2];

        StringBuilder sqlBuilder = new StringBuilder();
        selectColumn(sqlBuilder, "id", "point_id", "happened_date", "value", "message");
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_filter_id_happened_date");
        sqlBuilder.append("WHERE ");
        if (Objects.isNull(filterKey)) {
            sqlBuilder.append("tbl.filter_id IS NULL AND ");
        } else {
            sqlBuilder.append("tbl.filter_id=? AND ");
        }
        sqlBuilder.append("tbl.happened_date>=? AND tbl.happened_date<? ");
        sqlBuilder.append("ORDER BY tbl.happened_date ASC ");
        sqlBuilder.append("LIMIT ?, ?");

        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        if (Objects.isNull(filterKey)) {
            preparedStatement.setTimestamp(1, new Timestamp(startDate.getTime()));
            preparedStatement.setTimestamp(2, new Timestamp(endDate.getTime()));
            preparedStatement.setInt(3, pagingInfo.getRows() * pagingInfo.getPage());
            preparedStatement.setInt(4, pagingInfo.getRows());
        } else {
            preparedStatement.setLong(1, filterKey.getLongId());
            preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
            preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));
            preparedStatement.setInt(4, pagingInfo.getRows() * pagingInfo.getPage());
            preparedStatement.setInt(5, pagingInfo.getRows());
        }

        ResultSet resultSet = preparedStatement.executeQuery();
        List<FilteredValue> filteredValues = new ArrayList<>();
        while (resultSet.next()) {
            filteredValues.add(new FilteredValue(
                    new LongIdKey(resultSet.getLong(1)),
                    new LongIdKey(resultSet.getLong(2)),
                    filterKey,
                    new Date(resultSet.getTimestamp(3).getTime()),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return filteredValues;
    }

    private List<FilteredValue> childForPointPrevious(Connection connection, Object[] args, PagingInfo pagingInfo)
            throws SQLException {
        LongIdKey pointKey = (LongIdKey) args[0];
        Date date = (Date) args[1];

        StringBuilder sqlBuilder = new StringBuilder();
        selectColumn(sqlBuilder, "id", "filter_id", "happened_date", "value", "message");
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
        List<FilteredValue> filteredValues = new ArrayList<>();
        while (resultSet.next()) {
            filteredValues.add(new FilteredValue(
                    new LongIdKey(resultSet.getLong(1)),
                    pointKey,
                    new LongIdKey(resultSet.getLong(2)),
                    new Date(resultSet.getTimestamp(3).getTime()),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return filteredValues;
    }

    private List<FilteredValue> childForPointRear(Connection connection, Object[] args, PagingInfo pagingInfo)
            throws SQLException {
        LongIdKey pointKey = (LongIdKey) args[0];
        Date date = (Date) args[1];

        StringBuilder sqlBuilder = new StringBuilder();
        selectColumn(sqlBuilder, "id", "filter_id", "happened_date", "value", "message");
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
        List<FilteredValue> filteredValues = new ArrayList<>();
        while (resultSet.next()) {
            filteredValues.add(new FilteredValue(
                    new LongIdKey(resultSet.getLong(1)),
                    pointKey,
                    new LongIdKey(resultSet.getLong(2)),
                    new Date(resultSet.getTimestamp(3).getTime()),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return filteredValues;
    }

    @Override
    public int lookupCount(Connection connection, String preset, Object[] args) throws SQLException {
        switch (preset) {
            case FilteredValueMaintainService.BETWEEN:
                return betweenCount(connection, args);
            case FilteredValueMaintainService.CHILD_FOR_POINT:
                return childForPointCount(connection, args);
            case FilteredValueMaintainService.CHILD_FOR_FILTER:
                return childForFilterCount(connection, args);
            case FilteredValueMaintainService.CHILD_FOR_POINT_BETWEEN:
                return childForPointBetweenCount(connection, args);
            case FilteredValueMaintainService.CHILD_FOR_FILTER_BETWEEN:
                return childForFilterBetweenCount(connection, args);
            case FilteredValueMaintainService.CHILD_FOR_POINT_PREVIOUS:
                return childForPointPreviousCount(connection, args);
            case FilteredValueMaintainService.CHILD_FOR_POINT_REAR:
                return childForPointRearCount(connection, args);
            default:
                throw new IllegalArgumentException("无法识别的预设: " + preset);
        }
    }

    private int betweenCount(Connection connection, Object[] args) throws SQLException {
        Date startDate = (Date) args[0];
        Date endDate = (Date) args[1];

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT COUNT(tbl.id) FROM tbl_filter_value AS tbl ");
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
        sqlBuilder.append("SELECT COUNT(tbl.id) FROM tbl_filter_value AS tbl ");
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

    private int childForFilterCount(Connection connection, Object[] args) throws SQLException {
        LongIdKey filterKey = (LongIdKey) args[0];

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT COUNT(tbl.id) FROM tbl_filter_value AS tbl ");
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_filter_id_happened_date");
        sqlBuilder.append("WHERE ");
        if (Objects.isNull(filterKey)) {
            sqlBuilder.append("tbl.filter_id IS NULL");
        } else {
            sqlBuilder.append("tbl.filter_id=?");
        }

        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        if (Objects.nonNull(filterKey)) {
            preparedStatement.setLong(1, filterKey.getLongId());
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
        sqlBuilder.append("SELECT COUNT(tbl.id) FROM tbl_filter_value AS tbl ");
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

    private int childForFilterBetweenCount(Connection connection, Object[] args) throws SQLException {
        LongIdKey filterKey = (LongIdKey) args[0];
        Date startDate = (Date) args[1];
        Date endDate = (Date) args[2];

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT COUNT(tbl.id) FROM tbl_filter_value AS tbl ");
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_filter_id_happened_date");
        sqlBuilder.append("WHERE ");
        if (Objects.isNull(filterKey)) {
            sqlBuilder.append("tbl.filter_id IS NULL AND ");
        } else {
            sqlBuilder.append("tbl.filter_id=? AND ");
        }
        sqlBuilder.append("tbl.happened_date>=? AND tbl.happened_date<?");

        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        if (Objects.isNull(filterKey)) {
            preparedStatement.setTimestamp(1, new Timestamp(startDate.getTime()));
            preparedStatement.setTimestamp(2, new Timestamp(endDate.getTime()));
        } else {
            preparedStatement.setLong(1, filterKey.getLongId());
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
        sqlBuilder.append("SELECT COUNT(tbl.id) FROM tbl_filter_value AS tbl ");
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
        sqlBuilder.append("SELECT COUNT(tbl.id) FROM tbl_filter_value AS tbl ");
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
        sqlBuilder.append("FROM tbl_filtered_value AS tbl ");
    }
}
