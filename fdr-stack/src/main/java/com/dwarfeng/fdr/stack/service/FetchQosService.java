package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 抓取 QOS 服务。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
public interface FetchQosService extends Service {

    /**
     * 抓取服务是否启动。
     *
     * @return 抓取服务是否启动。
     * @throws ServiceException 服务异常。
     */
    boolean isStarted() throws ServiceException;

    /**
     * 抓取服务启动。
     *
     * @throws ServiceException 服务异常。
     */
    void start() throws ServiceException;

    /**
     * 抓取服务停止。
     *
     * @throws ServiceException 服务异常。
     */
    void stop() throws ServiceException;
}
