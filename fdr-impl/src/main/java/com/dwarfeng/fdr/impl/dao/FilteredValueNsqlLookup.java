package com.dwarfeng.fdr.impl.dao;

import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.DaoException;
import org.springframework.lang.NonNull;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

/**
 * 被过滤值本地SQL查询。
 *
 * @author DwArFeng
 * @since 1.9.0
 */
public interface FilteredValueNsqlLookup extends NsqlLookup {

    List<FilteredValue> filteredForPoint(@NonNull Connection connection, Object[] objs) throws DaoException;

    List<FilteredValue> filteredForPoint(@NonNull Connection connection, Object[] objs, PagingInfo pagingInfo)
            throws DaoException;

    Integer filteredCountForPoint(@NonNull Connection connection, Object[] objs) throws DaoException;

    List<FilteredValue> filteredForFilter(@NonNull Connection connection, Object[] objs) throws DaoException;

    List<FilteredValue> filteredForFilter(@NonNull Connection connection, Object[] objs, PagingInfo pagingInfo)
            throws DaoException;

    Integer filteredCountForFilter(@NonNull Connection connection, Object[] objs) throws DaoException;

    FilteredValue previous(@NonNull Connection connection, LongIdKey pointKey, Date date) throws DaoException;

    FilteredValue rear(@NonNull Connection connection, LongIdKey pointKey, Date date) throws DaoException;
}
