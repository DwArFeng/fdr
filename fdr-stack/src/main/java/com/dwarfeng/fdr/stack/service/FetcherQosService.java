package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.fdr.stack.bean.entity.FetcherInfo;
import com.dwarfeng.fdr.stack.handler.Fetcher;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 抓取器 QoS 服务。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
public interface FetcherQosService extends Service {

    /**
     * 判断指定的抓取器是否存在。
     *
     * @param fetcherInfoKey 指定的抓取器信息主键。
     * @return 指定的抓取器是否存在。
     * @throws ServiceException 服务异常。
     */
    boolean exists(LongIdKey fetcherInfoKey) throws ServiceException;

    /**
     * 获取指定抓取器的抓取器描述。
     *
     * @param fetcherInfoKey 指定抓取器的主键。
     * @return 指定抓取器的抓取器描述。
     * @throws ServiceException 服务异常。
     */
    FetcherDescription get(LongIdKey fetcherInfoKey) throws ServiceException;

    /**
     * 清除抓取器本地缓存。
     *
     * @throws ServiceException 服务异常。
     */
    void clearLocalCache() throws ServiceException;

    /**
     * 抓取器描述。
     *
     * @author DwArFeng
     * @since 1.1.0-beta
     */
    final class FetcherDescription {

        private final FetcherInfo fetcherInfo;
        private final Fetcher fetcher;

        public FetcherDescription(FetcherInfo fetcherInfo, Fetcher fetcher) {
            this.fetcherInfo = fetcherInfo;
            this.fetcher = fetcher;
        }

        public FetcherInfo getFetcherInfo() {
            return fetcherInfo;
        }

        public Fetcher getFetcher() {
            return fetcher;
        }

        @Override
        public String toString() {
            return "FetcherDescription{" +
                    "fetcherInfo=" + fetcherInfo +
                    ", fetcher=" + fetcher +
                    '}';
        }
    }
}
