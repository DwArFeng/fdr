package com.dwarfeng.fdr.impl.dao.nsql;

import com.dwarfeng.fdr.impl.dao.PersistenceValueNsqlLookup;
import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
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
public class Mysql8PersistenceValueNsqlLookup extends AbstractNsqlLookup implements PersistenceValueNsqlLookup {

    public static final String SUPPORT_TYPE = "org.hibernate.dialect.MySQL8Dialect";

    public Mysql8PersistenceValueNsqlLookup() {
        super(SUPPORT_TYPE);
    }

    @Override
    public List<PersistenceValue> lookupPersistence(@NonNull Connection connection, Object[] objs) throws DaoException {
        try {
            NsqlLookupUtil.objsValidation(
                    objs, new Class[]{LongIdKey.class, Date.class, Date.class}, new boolean[]{true, false, false}
            );
            LongIdKey pointKey = (LongIdKey) objs[0];
            Date startDate = (Date) objs[1];
            Date endDate = (Date) objs[2];

            StringBuilder sqlBuilder = new StringBuilder();
            selectTable(sqlBuilder);
            Mysql8NsqlLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date");
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
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<PersistenceValue> lookupPersistence(
            @NonNull Connection connection, Object[] objs, PagingInfo pagingInfo
    ) throws DaoException {
        try {
            NsqlLookupUtil.objsValidation(
                    objs, new Class[]{LongIdKey.class, Date.class, Date.class}, new boolean[]{true, false, false}
            );
            LongIdKey pointKey = (LongIdKey) objs[0];
            Date startDate = (Date) objs[1];
            Date endDate = (Date) objs[2];

            StringBuilder sqlBuilder = new StringBuilder();
            selectTable(sqlBuilder);
            Mysql8NsqlLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date");
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
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Integer lookupPersistenceCount(@NonNull Connection connection, Object[] objs) throws DaoException {
        try {
            NsqlLookupUtil.objsValidation(
                    objs, new Class[]{LongIdKey.class, Date.class, Date.class}, new boolean[]{true, false, false}
            );
            LongIdKey pointKey = (LongIdKey) objs[0];
            Date startDate = (Date) objs[1];
            Date endDate = (Date) objs[2];

            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("SELECT ");
            {
                sqlBuilder.append("COUNT(tbl.id) ");
            }
            sqlBuilder.append("FROM ");
            {
                sqlBuilder.append("tbl_persistence_value AS tbl ");
            }
            Mysql8NsqlLookupUtil.forceIndex(sqlBuilder, "idx_point_id");
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
    public PersistenceValue previous(@NonNull Connection connection, LongIdKey pointKey, Date date)
            throws DaoException {
        try {
            StringBuilder sqlBuilder = new StringBuilder();
            selectTable(sqlBuilder);
            Mysql8NsqlLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date_desc");
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
                return new PersistenceValue(
                        new LongIdKey(resultSet.getLong(1)),
                        pointKey,
                        new Date(resultSet.getTimestamp(2).getTime()),
                        resultSet.getString(3)
                );
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public PersistenceValue rear(@NonNull Connection connection, LongIdKey pointKey, Date date) throws DaoException {
        try {
            StringBuilder sqlBuilder = new StringBuilder();
            selectTable(sqlBuilder);
            Mysql8NsqlLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date");
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
                return new PersistenceValue(
                        new LongIdKey(resultSet.getLong(1)),
                        pointKey,
                        new Date(resultSet.getTimestamp(2).getTime()),
                        resultSet.getString(3)
                );
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    private void selectTable(StringBuilder sqlBuilder) {
        sqlBuilder.append("SELECT ");
        {
            sqlBuilder.append("tbl.id,");
            sqlBuilder.append("tbl.happened_date,");
            sqlBuilder.append("tbl.value ");
        }
        sqlBuilder.append("FROM ");
        {
            sqlBuilder.append("tbl_persistence_value AS tbl ");
        }
    }
}
