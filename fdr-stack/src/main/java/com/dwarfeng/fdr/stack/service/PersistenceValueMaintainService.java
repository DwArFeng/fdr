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

    String BETWEEN = "between";
    String CHILD_FOR_POINT = "child_for_point";
    String CHILD_FOR_POINT_BETWEEN = "child_for_point_between";

    /**
     * 获取属于指定数据点下的距离指定日期之前最后的被持久化数据。
     * <p>
     * 获取的数据可以是 <code>null</code>。
     *
     * @param pointKey 指定的数据点。
     * @param date     指定的日期
     * @return 属于指定数据点下的距离指定日期之前最后的被持久化数据，可以是 null。
     * @throws ServiceException 服务异常。
     * @since 1.9.0
     */
    PersistenceValue previous(LongIdKey pointKey, Date date) throws ServiceException;

    /**
     * 获取属于指定部件下的距离指定日期之后最早的被持久化数据。
     * <p>
     * 获取的数据可以是 <code>null</code>。
     *
     * @param pointKey 指定的数据点。
     * @param date     指定的日期
     * @return 属于指定数据点下的距离指定日期之后最早的被持久化数据，可以是 null。
     * @throws ServiceException 服务异常。
     * @since 1.9.4
     */
    PersistenceValue rear(LongIdKey pointKey, Date date) throws ServiceException;
}
