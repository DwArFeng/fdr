package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

import java.util.Date;

/**
 * 被触发数据维护服务。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface TriggeredValueMaintainService extends BatchCrudService<LongIdKey, TriggeredValue>,
        EntireLookupService<TriggeredValue>, PresetLookupService<TriggeredValue> {

    String BETWEEN = "between";
    String CHILD_FOR_POINT = "child_for_point";
    String CHILD_FOR_TRIGGER = "child_for_trigger";
    String CHILD_FOR_TRIGGER_SET = "child_for_trigger_set";
    String CHILD_FOR_POINT_BETWEEN = "child_for_point_between";
    String CHILD_FOR_TRIGGER_BETWEEN = "child_for_trigger_between";

    /**
     * 获取属于指定数据点下的距离指定日期之前最后的被触发数据。
     * <p>
     * 获取的数据可以是 <code>null</code>。
     *
     * @param pointKey 指定的数据点。
     * @param date     指定的日期
     * @return 属于指定数据点下的距离指定日期之前最后的被触发数据，可以是 null。
     * @throws ServiceException 服务异常。
     * @since 1.9.0
     */
    TriggeredValue previous(LongIdKey pointKey, Date date) throws ServiceException;
}
