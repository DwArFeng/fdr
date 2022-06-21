package com.dwarfeng.fdr.impl.dao;

import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.DaoException;
import org.springframework.lang.NonNull;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

/**
 * 被触发信息本地SQL查询。
 *
 * @author DwArFeng
 * @since 1.9.0
 */
public interface TriggeredValueNSQLQuery extends NSQLQuery {

    List<TriggeredValue> lookupTriggeredForPoint(@NonNull Connection connection, Object[] objs) throws DaoException;

    List<TriggeredValue> lookupTriggeredForPoint(@NonNull Connection connection, Object[] objs, PagingInfo pagingInfo)
            throws DaoException;

    Integer lookupTriggeredCountForPoint(@NonNull Connection connection, Object[] objs) throws DaoException;

    List<TriggeredValue> lookupTriggeredForTrigger(@NonNull Connection connection, Object[] objs) throws DaoException;

    List<TriggeredValue> lookupTriggeredForTrigger(@NonNull Connection connection, Object[] objs, PagingInfo pagingInfo)
            throws DaoException;

    Integer lookupTriggeredCountForTrigger(@NonNull Connection connection, Object[] objs) throws DaoException;

    TriggeredValue previous(@NonNull Connection connection, LongIdKey pointKey, Date date) throws DaoException;

    TriggeredValue rear(@NonNull Connection connection, LongIdKey pointKey, Date date) throws DaoException;
}
