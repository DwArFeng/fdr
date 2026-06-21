package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.fdr.stack.bean.entity.FetcherInfo;
import com.dwarfeng.fdr.stack.handler.Fetcher;
import com.dwarfeng.fdr.stack.handler.FetcherSession;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 抓取器会话 QoS 服务。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
public interface FetcherSessionQosService extends Service {

    /**
     * 判断指定的抓取器会话是否存在。
     *
     * @param fetcherInfoKey 指定的抓取器信息主键。
     * @return 指定的抓取器会话是否存在。
     * @throws ServiceException 服务异常。
     */
    boolean exists(LongIdKey fetcherInfoKey) throws ServiceException;

    /**
     * 获取指定抓取器的抓取器会话描述。
     *
     * <p>
     * 如果指定的抓取器存在，但抓取器会话不存在，则尝试基于抓取器信息进行创建；
     * 如果抓取器信息不存在，则返回 <code>null</code>。
     *
     * @param fetcherInfoKey 指定的抓取器信息主键。
     * @return 指定的抓取器会话描述。
     * @throws ServiceException 服务异常。
     */
    FetcherSessionDescription get(LongIdKey fetcherInfoKey) throws ServiceException;

    /**
     * 关闭并清除所有的抓取器会话。
     *
     * <p>
     * 该方法会关闭所有当前持有的抓取器会话，并清除所有的抓取器会话。
     *
     * @throws ServiceException 服务异常。
     */
    void closeAndClearHolding() throws ServiceException;

    /**
     * 抓取器会话描述。
     *
     * <p>
     * 该类用于描述一个抓取器会话，包括抓取器信息和抓取器会话本身。
     *
     * @author DwArFeng
     * @since 1.1.0-beta
     */
    final class FetcherSessionDescription {

        private final FetcherInfo fetcherInfo;
        private final Fetcher fetcher;
        private final FetcherSession fetcherSession;

        public FetcherSessionDescription(FetcherInfo fetcherInfo, Fetcher fetcher, FetcherSession fetcherSession) {
            this.fetcherInfo = fetcherInfo;
            this.fetcher = fetcher;
            this.fetcherSession = fetcherSession;
        }

        public FetcherInfo getFetcherInfo() {
            return fetcherInfo;
        }

        public Fetcher getFetcher() {
            return fetcher;
        }

        public FetcherSession getFetcherSession() {
            return fetcherSession;
        }

        @Override
        public String toString() {
            return "FetcherSessionDescription{" +
                    "fetcherInfo=" + fetcherInfo +
                    ", fetcher=" + fetcher +
                    ", fetcherSession=" + fetcherSession +
                    '}';
        }
    }
}
