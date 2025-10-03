package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 支持 QoS 服务。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
public interface SupportQosService extends Service {

    /**
     * 重置过滤器。
     *
     * @throws ServiceException 服务异常。
     */
    void resetFilter() throws ServiceException;

    /**
     * 重置清洗器。
     *
     * @throws ServiceException 服务异常。
     */
    void resetWasher() throws ServiceException;

    /**
     * 重置触发器。
     *
     * @throws ServiceException 服务异常。
     */
    void resetTrigger() throws ServiceException;

    /**
     * 重置映射器。
     *
     * @throws ServiceException 服务异常。
     */
    void resetMapper() throws ServiceException;
}
