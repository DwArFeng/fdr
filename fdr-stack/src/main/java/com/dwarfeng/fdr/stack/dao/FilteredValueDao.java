package com.dwarfeng.fdr.stack.dao;

import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.BatchWriteDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;
import com.dwarfeng.subgrade.stack.exception.DaoException;

import java.util.Date;

/**
 * 被过滤数据数据访问层。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface FilteredValueDao extends BatchBaseDao<LongIdKey, FilteredValue>, EntireLookupDao<FilteredValue>,
        PresetLookupDao<FilteredValue>, BatchWriteDao<FilteredValue> {

    /**
     * 获取属于指定数据点下的距离指定日期之前最后的被过滤数据。
     * <p>
     * 获取的数据可以是 <code>null</code>。
     *
     * @param pointKey 指定的数据点。
     * @param date     指定的日期
     * @return 属于指定数据点下的距离指定日期之前最后的被过滤数据，可以是 null。
     * @throws DaoException 数据访问层异常。
     * @since 1.9.0
     */
    FilteredValue previous(LongIdKey pointKey, Date date) throws DaoException;

    /**
     * 获取属于指定部件下的距离指定日期之后最早的统计历史。
     * <p>
     * 获取的数据可以是 <code>null</code>。
     *
     * @param pointKey 指定的数据点。
     * @param date     指定的日期
     * @return 属于指定数据点下的距离指定日期之后最早的被过滤数据，可以是 null。
     * @throws DaoException 数据访问层异常。
     * @since 1.9.4
     */
    FilteredValue rear(LongIdKey pointKey, Date date) throws DaoException;
}
