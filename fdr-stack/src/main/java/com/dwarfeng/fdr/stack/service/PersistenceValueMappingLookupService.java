package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.dcti.stack.bean.dto.TimedValue;
import com.dwarfeng.fdr.stack.bean.dto.MappingLookupInfo;
import com.dwarfeng.fdr.stack.bean.dto.MappingLookupSessionInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

import java.util.List;

/**
 * 持久值映射查询服务。
 *
 * @author DwArFeng
 * @since 1.10.0
 */
public interface PersistenceValueMappingLookupService extends Service {

    /**
     * 映射查询。
     *
     * @param mappingLookupInfo 映射查询信息。
     * @return 映射查询的结果。
     * @throws ServiceException 服务异常。
     */
    List<TimedValue> mappingLookup(MappingLookupInfo mappingLookupInfo) throws ServiceException;

    /**
     * 异步式执行映射查询。
     *
     * @param mappingLookupInfo 映射查询信息。
     * @return 映射查询的会话主键。
     * @throws ServiceException 服务异常。
     */
    LongIdKey mappingLookupAsync(MappingLookupInfo mappingLookupInfo) throws ServiceException;

    /**
     * 取消异步式会话。
     *
     * @param sessionKey 映射查询的会话主键。
     * @throws ServiceException 服务异常。
     */
    void cancel(LongIdKey sessionKey) throws ServiceException;

    /**
     * 异步式获取查询结果。
     *
     * <p>
     * 在映射查询结束前，该方法会一直处于阻塞状态。
     *
     * @param sessionKey 映射查询的会话主键。
     * @return 映射查询的结果。
     * @throws ServiceException 服务异常。
     */
    List<TimedValue> getResult(LongIdKey sessionKey) throws ServiceException;

    /**
     * 异步式获取查询结果。
     *
     * <p>
     * 在映射查询结束前，该方法会一直处于阻塞状态。
     * <p>
     * 当阻塞时间超过了设定的超时时间后，方法直接抛出服务异常。
     *
     * @param sessionKey 映射查询的会话主键。
     * @param timeout    超时时间。
     * @return 映射查询的结果。
     * @throws ServiceException 服务异常。
     */
    List<TimedValue> getResult(LongIdKey sessionKey, long timeout) throws ServiceException;

    /**
     * 获取指定的主键对应的会话信息。
     *
     * @param sessionKey 映射查询的会话主键。
     * @return 指定的主键对应的会话信息。
     * @throws ServiceException 服务异常。
     */
    MappingLookupSessionInfo getSessionInfo(LongIdKey sessionKey) throws ServiceException;
}
