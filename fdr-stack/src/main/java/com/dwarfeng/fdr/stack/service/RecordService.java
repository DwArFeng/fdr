package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.dcti.stack.bean.dto.DataInfo;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 数据记录服务。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface RecordService extends Service {

    /**
     * 向程序中记录数据。
     *
     * @param message 文本形式的数据信息。
     * @throws ServiceException 服务异常。
     */
    void record(String message) throws ServiceException;

    /**
     * 向程序中记录数据。
     *
     * @param dataInfo 指定的数据信息。
     * @throws ServiceException 服务异常。
     */
    void record(DataInfo dataInfo) throws ServiceException;
}
