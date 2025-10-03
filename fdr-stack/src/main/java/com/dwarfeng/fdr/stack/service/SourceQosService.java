package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.fdr.stack.handler.Source;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

import java.util.List;

/**
 * 数据源 QoS 服务。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
public interface SourceQosService extends Service {

    /**
     * 列出在用的全部数据源。
     *
     * @return 在用的全部数据源组成的列表。
     * @throws ServiceException 服务异常。
     */
    List<Source> all() throws ServiceException;
}
