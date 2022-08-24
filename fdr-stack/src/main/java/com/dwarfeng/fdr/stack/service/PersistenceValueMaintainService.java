package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

import java.util.Date;

/**
 * 持久化数据维护服务。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface PersistenceValueMaintainService extends BatchCrudService<LongIdKey, PersistenceValue>,
        EntireLookupService<PersistenceValue>, PresetLookupService<PersistenceValue> {

    /**
     * Between 包含左边界和右边界。
     */
    String BETWEEN = "between";
    String CHILD_FOR_POINT = "child_for_point";
    /**
     * Between 包含左边界和右边界。
     */
    String CHILD_FOR_POINT_BETWEEN = "child_for_point_between";
    /**
     * Between 包含左边界，不包含右边界。
     *
     * @since 1.10.0
     */
    String CHILD_FOR_POINT_BETWEEN_RB_OPEN = "child_for_point_between_rb_open";
    /**
     * @since 1.10.0
     */
    String CHILD_FOR_POINT_PREVIOUS = "child_for_point_previous";
    /**
     * @since 1.10.0
     */
    String CHILD_FOR_POINT_REAR = "child_for_point_rear";

    /**
     * 获取属于指定数据点下的距离指定日期之前最后的被持久化数据。
     *
     * <p>
     * 获取的数据可以是 <code>null</code>。
     *
     * <p>
     * 该方法已过时。请使用 {@link #CHILD_FOR_POINT_PREVIOUS} 预设查询代替。<br>
     * 使用 {@link #lookupFirst(String, Object[])} 获得第一个元素。
     *
     * @param pointKey 指定的数据点。
     * @param date     指定的日期
     * @return 属于指定数据点下的距离指定日期之前最后的被持久化数据，可以是 null。
     * @throws ServiceException 服务异常。
     * @since 1.9.0
     * @deprecated 使用预设查询 {@link #CHILD_FOR_POINT_PREVIOUS} 替代。
     */
    PersistenceValue previous(LongIdKey pointKey, Date date) throws ServiceException;

    /**
     * 获取属于指定部件下的距离指定日期之后最早的被持久化数据。
     *
     * <p>
     * 获取的数据可以是 <code>null</code>。
     *
     * <p>
     * 该方法已过时。请使用 {@link #CHILD_FOR_POINT_REAR} 预设查询代替。<br>
     * 使用 {@link #lookupFirst(String, Object[])} 获得第一个元素。
     *
     * @param pointKey 指定的数据点。
     * @param date     指定的日期
     * @return 属于指定数据点下的距离指定日期之后最早的被持久化数据，可以是 null。
     * @throws ServiceException 服务异常。
     * @since 1.9.4
     * @deprecated 使用预设查询 {@link #CHILD_FOR_POINT_REAR} 替代。
     */
    PersistenceValue rear(LongIdKey pointKey, Date date) throws ServiceException;
}
