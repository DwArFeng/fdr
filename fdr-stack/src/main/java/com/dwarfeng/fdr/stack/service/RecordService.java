package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.fdr.stack.bean.dto.RecordInfo;
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
     * 记录数据。
     *
     * @param recordInfo 记录信息。
     * @throws ServiceException 服务异常。
     */
    void record(RecordInfo recordInfo) throws ServiceException;
}
