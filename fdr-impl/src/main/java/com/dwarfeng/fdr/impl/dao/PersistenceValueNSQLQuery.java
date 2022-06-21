package com.dwarfeng.fdr.impl.dao;

import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.DaoException;
import org.springframework.lang.NonNull;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

/**
 * 持久值本地SQL查询。
 *
 * @author DwArFeng
 * @since 1.9.0
 */
public interface PersistenceValueNSQLQuery extends NSQLQuery {

    List<PersistenceValue> lookupPersistence(@NonNull Connection connection, Object[] objs) throws DaoException;

    List<PersistenceValue> lookupPersistence(@NonNull Connection connection, Object[] objs, PagingInfo pagingInfo)
            throws DaoException;

    Integer lookupPersistenceCount(@NonNull Connection connection, Object[] objs) throws DaoException;

    PersistenceValue previous(@NonNull Connection connection, LongIdKey pointKey, Date date) throws DaoException;

    PersistenceValue rear(@NonNull Connection connection, LongIdKey pointKey, Date date) throws DaoException;
}
