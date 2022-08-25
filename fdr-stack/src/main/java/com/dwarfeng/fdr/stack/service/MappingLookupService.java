package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.dcti.stack.bean.dto.TimedValue;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

import java.util.Date;
import java.util.List;

/**
 * 映射查询服务。
 *
 * @author DwArFeng
 * @since 1.5.0
 * @deprecated 查询功能被分散到 {@link PersistenceValueMappingLookupService},
 * {@link FilteredValueMappingLookupService}, {@link TriggeredValueMappingLookupService} 中。
 */
public interface MappingLookupService extends Service {

    /**
     * 查询并映射指定数据点的持久化数据。
     *
     * @param mapperType 映射器的类型。
     * @param pointKey   指定的数据点。
     * @param startDate  持久数据的开始时间。
     * @param endDate    持久数据的结束时间。
     * @param mapperArgs 映射器的参数。
     * @return 被映射的指定数据的持久化数据。
     * @throws ServiceException 服务异常。
     */
    List<TimedValue> mappingPersistenceValue(
            String mapperType, LongIdKey pointKey, Date startDate, Date endDate, Object[] mapperArgs
    ) throws ServiceException;

    /**
     * 查询并映射指定数据点的持久化数据。
     *
     * @param mapperType 映射器的类型。
     * @param pointKey   指定的数据点。
     * @param startDate  持久数据的开始时间。
     * @param endDate    持久数据的结束时间。
     * @param mapperArgs 映射器的参数。
     * @return 被映射的指定数据的持久化数据。
     * @throws ServiceException 服务异常。
     */
    List<TimedValue> mappingFilteredValue(
            String mapperType, LongIdKey pointKey, Date startDate, Date endDate, Object[] mapperArgs
    ) throws ServiceException;

    /**
     * 查询并映射指定数据点的持久化数据。
     *
     * @param mapperType 映射器的类型。
     * @param pointKey   指定的数据点。
     * @param startDate  持久数据的开始时间。
     * @param endDate    持久数据的结束时间。
     * @param mapperArgs 映射器的参数。
     * @return 被映射的指定数据的持久化数据。
     * @throws ServiceException 服务异常。
     */
    List<TimedValue> mappingTriggeredValue(
            String mapperType, LongIdKey pointKey, Date startDate, Date endDate, Object[] mapperArgs
    ) throws ServiceException;
}
