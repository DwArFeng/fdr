package com.dwarfeng.fdr.impl.dao.nsql;

import com.dwarfeng.fdr.impl.dao.FilteredValueNSQLQuery;
import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.DaoException;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("DuplicatedCode")
@Component
public class MySQL8FilteredValueNSQLQuery extends AbstractNSQLQuery implements FilteredValueNSQLQuery {

    public static final String SUPPORT_TYPE = "org.hibernate.dialect.MySQL8Dialect";

    public MySQL8FilteredValueNSQLQuery() {
        super(SUPPORT_TYPE);
    }

    @Override
    public List<FilteredValue> lookupFilteredForPoint(
            @NonNull Connection connection, Object[] objs
    ) throws DaoException {
        try {
            NSQLQueryUtil.objsValidation(
                    objs,
                    new Class[]{LongIdKey.class, Date.class, Date.class},
                    new boolean[]{true, false, false}
            );
            LongIdKey pointKey = (LongIdKey) objs[0];
            Date startDate = (Date) objs[1];
            Date endDate = (Date) objs[2];

            StringBuilder sqlBuilder = new StringBuilder();
            selectTableWithFilterId(sqlBuilder);
            sqlBuilder.append("WHERE ");
            {
                if (Objects.isNull(pointKey)) {
                    sqlBuilder.append("tbl.point_id IS NULL AND ");
                } else {
                    sqlBuilder.append("tbl.point_id=? AND ");
                }
                sqlBuilder.append("tbl.happened_date>=? AND ");
                sqlBuilder.append("tbl.happened_date<? ");
            }
            sqlBuilder.append("ORDER BY ");
            {
                sqlBuilder.append("tbl.happened_date ASC ");
            }

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
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<FilteredValue> lookupFilteredForPoint(
            @NonNull Connection connection, Object[] objs, PagingInfo pagingInfo
    ) throws DaoException {
        try {
            NSQLQueryUtil.objsValidation(
                    objs,
                    new Class[]{LongIdKey.class, Date.class, Date.class},
                    new boolean[]{true, false, false}
            );
            LongIdKey pointKey = (LongIdKey) objs[0];
            Date startDate = (Date) objs[1];
            Date endDate = (Date) objs[2];

            StringBuilder sqlBuilder = new StringBuilder();
            selectTableWithFilterId(sqlBuilder);
            sqlBuilder.append("WHERE ");
            {
                if (Objects.isNull(pointKey)) {
                    sqlBuilder.append("tbl.point_id IS NULL AND ");
                } else {
                    sqlBuilder.append("tbl.point_id=? AND ");
                }
                sqlBuilder.append("tbl.happened_date>=? AND ");
                sqlBuilder.append("tbl.happened_date<? ");
            }
            sqlBuilder.append("ORDER BY ");
            {
                sqlBuilder.append("tbl.happened_date ASC ");
            }
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
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Integer lookupFilteredCountForPoint(
            @NonNull Connection connection, Object[] objs
    ) throws DaoException {
        try {
            NSQLQueryUtil.objsValidation(
                    objs,
                    new Class[]{LongIdKey.class, Date.class, Date.class},
                    new boolean[]{true, false, false}
            );
            LongIdKey pointKey = (LongIdKey) objs[0];
            Date startDate = (Date) objs[1];
            Date endDate = (Date) objs[2];

            StringBuilder sqlBuilder = new StringBuilder();
            selectCount(sqlBuilder);
            sqlBuilder.append("WHERE ");
            {
                if (Objects.isNull(pointKey)) {
                    sqlBuilder.append("tbl.point_id IS NULL AND ");
                } else {
                    sqlBuilder.append("tbl.point_id=? AND ");
                }
                sqlBuilder.append("tbl.happened_date>=? AND ");
                sqlBuilder.append("tbl.happened_date<? ");
            }

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
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<FilteredValue> lookupFilteredForFilter(
            @NonNull Connection connection, Object[] objs
    ) throws DaoException {
        try {
            NSQLQueryUtil.objsValidation(
                    objs,
                    new Class[]{LongIdKey.class, Date.class, Date.class},
                    new boolean[]{true, false, false}
            );
            LongIdKey filterKey = (LongIdKey) objs[0];
            Date startDate = (Date) objs[1];
            Date endDate = (Date) objs[2];

            StringBuilder sqlBuilder = new StringBuilder();
            selectTableWithPointId(sqlBuilder);
            sqlBuilder.append("WHERE ");
            {
                if (Objects.isNull(filterKey)) {
                    sqlBuilder.append("tbl.filter_id IS NULL AND ");
                } else {
                    sqlBuilder.append("tbl.filter_id=? AND ");
                }
                sqlBuilder.append("tbl.happened_date>=? AND ");
                sqlBuilder.append("tbl.happened_date<? ");
            }
            sqlBuilder.append("ORDER BY ");
            {
                sqlBuilder.append("tbl.happened_date ASC ");
            }

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
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<FilteredValue> lookupFilteredForFilter(
            @NonNull Connection connection, Object[] objs, PagingInfo pagingInfo
    ) throws DaoException {
        try {
            NSQLQueryUtil.objsValidation(
                    objs,
                    new Class[]{LongIdKey.class, Date.class, Date.class},
                    new boolean[]{true, false, false}
            );
            LongIdKey filterKey = (LongIdKey) objs[0];
            Date startDate = (Date) objs[1];
            Date endDate = (Date) objs[2];

            StringBuilder sqlBuilder = new StringBuilder();
            selectTableWithPointId(sqlBuilder);
            sqlBuilder.append("WHERE ");
            {
                if (Objects.isNull(filterKey)) {
                    sqlBuilder.append("tbl.filter_id IS NULL AND ");
                } else {
                    sqlBuilder.append("tbl.filter_id=? AND ");
                }
                sqlBuilder.append("tbl.happened_date>=? AND ");
                sqlBuilder.append("tbl.happened_date<? ");
            }
            sqlBuilder.append("ORDER BY ");
            {
                sqlBuilder.append("tbl.happened_date ASC ");
            }
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
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Integer lookupFilteredCountForFilter(
            @NonNull Connection connection, Object[] objs
    ) throws DaoException {
        try {
            NSQLQueryUtil.objsValidation(
                    objs,
                    new Class[]{LongIdKey.class, Date.class, Date.class},
                    new boolean[]{true, false, false}
            );
            LongIdKey filterKey = (LongIdKey) objs[0];
            Date startDate = (Date) objs[1];
            Date endDate = (Date) objs[2];

            StringBuilder sqlBuilder = new StringBuilder();
            selectCount(sqlBuilder);
            sqlBuilder.append("WHERE ");
            {
                if (Objects.isNull(filterKey)) {
                    sqlBuilder.append("tbl.filter_id IS NULL AND ");
                } else {
                    sqlBuilder.append("tbl.filter_id=? AND ");
                }
                sqlBuilder.append("tbl.happened_date>=? AND ");
                sqlBuilder.append("tbl.happened_date<? ");
            }

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
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public FilteredValue previous(
            @NonNull Connection connection, LongIdKey pointKey, Date date
    ) throws DaoException {
        try {
            StringBuilder sqlBuilder = new StringBuilder();
            selectTableWithFilterId(sqlBuilder);
            sqlBuilder.append("WHERE ");
            {
                if (Objects.isNull(pointKey)) {
                    sqlBuilder.append("tbl.point_id IS NULL AND ");
                } else {
                    sqlBuilder.append("tbl.point_id=? AND ");
                }
                sqlBuilder.append("tbl.happened_date<? ");
            }
            sqlBuilder.append("ORDER BY ");
            {
                sqlBuilder.append("tbl.happened_date DESC ");
            }
            sqlBuilder.append("LIMIT 1");

            PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
            if (Objects.isNull(pointKey)) {
                preparedStatement.setTimestamp(1, new Timestamp(date.getTime()));
            } else {
                preparedStatement.setLong(1, pointKey.getLongId());
                preparedStatement.setTimestamp(2, new Timestamp(date.getTime()));
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new FilteredValue(
                        new LongIdKey(resultSet.getLong(1)),
                        pointKey,
                        new LongIdKey(resultSet.getLong(2)),
                        new Date(resultSet.getTimestamp(3).getTime()),
                        resultSet.getString(4),
                        resultSet.getString(5)
                );
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public FilteredValue rear(
            @NonNull Connection connection, LongIdKey pointKey, Date date
    ) throws DaoException {
        try {
            StringBuilder sqlBuilder = new StringBuilder();
            selectTableWithFilterId(sqlBuilder);
            sqlBuilder.append("WHERE ");
            {
                if (Objects.isNull(pointKey)) {
                    sqlBuilder.append("tbl.point_id IS NULL AND ");
                } else {
                    sqlBuilder.append("tbl.point_id=? AND ");
                }
                sqlBuilder.append("tbl.happened_date>? ");
            }
            sqlBuilder.append("ORDER BY ");
            {
                sqlBuilder.append("tbl.happened_date ASC ");
            }
            sqlBuilder.append("LIMIT 1");

            PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
            if (Objects.isNull(pointKey)) {
                preparedStatement.setTimestamp(1, new Timestamp(date.getTime()));
            } else {
                preparedStatement.setLong(1, pointKey.getLongId());
                preparedStatement.setTimestamp(2, new Timestamp(date.getTime()));
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new FilteredValue(
                        new LongIdKey(resultSet.getLong(1)),
                        pointKey,
                        new LongIdKey(resultSet.getLong(2)),
                        new Date(resultSet.getTimestamp(3).getTime()),
                        resultSet.getString(4),
                        resultSet.getString(5)
                );
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    private void selectTableWithFilterId(StringBuilder sqlBuilder) {
        sqlBuilder.append("SELECT ");
        {
            sqlBuilder.append("tbl.id,");
            sqlBuilder.append("tbl.filter_id,");
            sqlBuilder.append("tbl.happened_date, ");
            sqlBuilder.append("tbl.value, ");
            sqlBuilder.append("tbl.message ");
        }
        sqlBuilder.append("FROM ");
        {
            sqlBuilder.append("tbl_filtered_value AS tbl ");
        }
    }

    private void selectTableWithPointId(StringBuilder sqlBuilder) {
        sqlBuilder.append("SELECT ");
        {
            sqlBuilder.append("tbl.id,");
            sqlBuilder.append("tbl.point_id,");
            sqlBuilder.append("tbl.happened_date, ");
            sqlBuilder.append("tbl.value, ");
            sqlBuilder.append("tbl.message ");
        }
        sqlBuilder.append("FROM ");
        {
            sqlBuilder.append("tbl_filtered_value AS tbl ");
        }
    }

    private void selectCount(StringBuilder sqlBuilder) {
        sqlBuilder.append("SELECT ");
        {
            sqlBuilder.append("COUNT(tbl.id) ");
        }
        sqlBuilder.append("FROM ");
        {
            sqlBuilder.append("tbl_filtered_value AS tbl ");
        }
    }
}
