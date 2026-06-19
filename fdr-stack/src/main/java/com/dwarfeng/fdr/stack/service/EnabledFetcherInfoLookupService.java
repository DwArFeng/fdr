package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.fdr.stack.bean.entity.FetcherInfo;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

import java.util.List;

/**
 * 有效的抓取器信息查询服务。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
public interface EnabledFetcherInfoLookupService extends Service {

    /**
     * 获取所有有效的抓取器信息。
     *
     * @return 所有有效的抓取器信息列表。
     * @throws ServiceException 服务异常。
     */
    List<FetcherInfo> getEnabledFetcherInfos() throws ServiceException;
}
