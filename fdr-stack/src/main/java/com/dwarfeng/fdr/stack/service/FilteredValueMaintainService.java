package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

import java.util.Date;

/**
 * 被过滤数据维护服务。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface FilteredValueMaintainService extends BatchCrudService<LongIdKey, FilteredValue>,
        EntireLookupService<FilteredValue>, PresetLookupService<FilteredValue> {

    String BETWEEN = "between";
    String CHILD_FOR_POINT = "child_for_point";
    String CHILD_FOR_FILTER = "child_for_filter";
    String CHILD_FOR_FILTER_SET = "child_for_filter_set";
    String CHILD_FOR_POINT_BETWEEN = "child_for_point_between";
    String CHILD_FOR_FILTER_BETWEEN = "child_for_filter_between";

    /**
     * 获取属于指定数据点下的距离指定日期之前最后的被过滤数据。
     * <p>
     * 获取的数据可以是 <code>null</code>。
     *
     * @param pointKey 指定的数据点。
     * @param date     指定的日期
     * @return 属于指定数据点下的距离指定日期之前最后的被过滤数据，可以是 null。
     * @throws ServiceException 服务异常。
     * @since 1.9.0
     */
    FilteredValue previous(LongIdKey pointKey, Date date) throws ServiceException;

    /**
     * 获取属于指定部件下的距离指定日期之后最早的统计历史。
     * <p>
     * 获取的数据可以是 <code>null</code>。
     *
     * @param pointKey 指定的数据点。
     * @param date     指定的日期
     * @return 属于指定数据点下的距离指定日期之后最早的被过滤数据，可以是 null。
     * @throws ServiceException 服务异常。
     * @since 1.9.4
     */
    FilteredValue rear(LongIdKey pointKey, Date date) throws ServiceException;
}
