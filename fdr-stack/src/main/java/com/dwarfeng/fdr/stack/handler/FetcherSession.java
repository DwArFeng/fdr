package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.fdr.stack.bean.dto.RecordInfo;
import com.dwarfeng.fdr.stack.exception.FetcherSessionException;

/**
 * 抓取器会话。
 *
 * <p>
 * 抓取器会话用于持有抓取过程中的运行资源，如监听容器、调度任务、订阅关系和临时状态。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
public interface FetcherSession {

    /**
     * 初始化抓取器会话。
     *
     * @param context 抓取器会话上下文。
     */
    void init(Context context);

    /**
     * 打开抓取器会话。
     *
     * @throws FetcherSessionException 抓取器会话异常。
     */
    void openSession() throws FetcherSessionException;

    /**
     * 启动抓取。
     *
     * @throws FetcherSessionException 抓取器会话异常。
     */
    void startFetch() throws FetcherSessionException;

    /**
     * 停止抓取。
     *
     * @throws FetcherSessionException 抓取器会话异常。
     */
    void stopFetch() throws FetcherSessionException;

    /**
     * 关闭抓取器会话。
     *
     * <p>
     * 该方法可能会在抓取器启动失败时调用，请妥善处理重复关闭和半初始化资源。
     *
     * @throws FetcherSessionException 抓取器会话异常。
     */
    void closeSession() throws FetcherSessionException;

    /**
     * 抓取器会话上下文。
     *
     * @author DwArFeng
     * @since 3.1.0
     */
    interface Context {

        /**
         * 向程序中记录数据。
         *
         * <p>
         * 该方法只有在数据源上线的情况下能够被调用。
         *
         * <p>
         * 从数据源的业务逻辑上来说，数据源接收数据的过程应该是时间递增的，即接收到的数据永远是最新的。<br>
         * 因此调用该方法时，应尽量保证 {@link RecordInfo#getPointKey()} 相同的记录信息的
         * {@link RecordInfo#getHappenedDate()} 是递增的。<br>
         * 例如基于 kafka 的数据源，{@link RecordInfo#getPointKey()} 相同的记录信息应该交由同一个 partition 来处理。
         * 否则可能会出现旧的数据先于新的数据被记录的情况。同理，其它的数据源实现也应该避免类似的情况。<br>
         * 在特殊情况下，数据源可能会接收到时间不递增的数据，如数据源所在的服务器系统时间回拨。
         * 在这种情况下，数据源可以接受时间不递增的数据。对于时间不递增的数据，不同的保持器的处理方式不同，
         * 可能会选择覆盖最新数据，或者旧的数据，或者抛出异常。
         *
         * @param recordInfo 指定的记录信息。
         * @throws Exception 记录数据的过程中出现的任何异常。
         */
        void record(RecordInfo recordInfo) throws Exception;
    }
}
