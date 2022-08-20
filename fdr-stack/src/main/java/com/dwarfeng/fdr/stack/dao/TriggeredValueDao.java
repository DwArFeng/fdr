package com.dwarfeng.fdr.stack.dao;

import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.fdr.stack.service.TriggeredValueMaintainService;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.BatchWriteDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;
import com.dwarfeng.subgrade.stack.exception.DaoException;

import java.util.Date;

/**
 * 实时数据数据访问层。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface TriggeredValueDao extends BatchBaseDao<LongIdKey, TriggeredValue>, EntireLookupDao<TriggeredValue>,
        PresetLookupDao<TriggeredValue>, BatchWriteDao<TriggeredValue> {

    /**
     * 获取属于指定数据点下的距离指定日期之前最后的被触发数据。
     *
     * <p>
     * 获取的数据可以是 <code>null</code>。
     *
     * <p>
     * 该方法已过时。请使用 {@link TriggeredValueMaintainService#CHILD_FOR_POINT_PREVIOUS} 预设查询代替。<br>
     * 使用 {@link #lookupFirst(String, Object[])} 获得第一个元素。
     *
     * @param pointKey 指定的数据点。
     * @param date     指定的日期
     * @return 属于指定数据点下的距离指定日期之前最后的被触发数据，可以是 null。
     * @throws DaoException 数据访问层异常。
     * @since 1.9.0
     * @deprecated 使用预设查询 {@link TriggeredValueMaintainService#CHILD_FOR_POINT_PREVIOUS} 替代。
     */
    TriggeredValue previous(LongIdKey pointKey, Date date) throws DaoException;

    /**
     * 获取属于指定部件下的距离指定日期之后最早的统计历史。
     * <p>
     * 获取的数据可以是 <code>null</code>。
     *
     * <p>
     * 该方法已过时。请使用 {@link TriggeredValueMaintainService#CHILD_FOR_POINT_REAR} 预设查询代替。<br>
     * 使用 {@link #lookupFirst(String, Object[])} 获得第一个元素。
     *
     * @param pointKey 指定的数据点。
     * @param date     指定的日期
     * @return 属于指定数据点下的距离指定日期之后最早的被触发数据，可以是 null。
     * @throws DaoException 数据访问层异常。
     * @since 1.9.4
     * @deprecated 使用预设查询 {@link TriggeredValueMaintainService#CHILD_FOR_POINT_REAR} 替代。
     */
    TriggeredValue rear(LongIdKey pointKey, Date date) throws DaoException;
}
