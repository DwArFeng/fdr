package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.fdr.stack.struct.Data;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 消费处理器。
 *
 * @author DwArFeng
 * @since 1.2.0
 */
public interface ConsumeHandler<D extends Data> extends Handler {

    /**
     * 消费处理器是否启动。
     *
     * @return 消费处理器是否启动。
     * @throws HandlerException 处理器异常。
     */
    boolean isStarted() throws HandlerException;

    /**
     * 开启消费处理器。
     *
     * @throws HandlerException 处理器异常。
     */
    void start() throws HandlerException;

    /**
     * 关闭消费处理器。
     *
     * @throws HandlerException 处理器异常。
     */
    void stop() throws HandlerException;

    /**
     * 接受一条数据。
     *
     * @param data 指定的数据。
     * @throws HandlerException 处理器异常。
     */
    void accept(D data) throws HandlerException;

    /**
     * 获取缓冲器已经缓冲的容量。
     *
     * @return 缓冲器已经缓冲的容量。
     * @throws HandlerException 处理器异常。
     */
    int bufferedSize() throws HandlerException;

    /**
     * 获取缓冲器的容量。
     *
     * @return 缓冲器的容量。
     * @throws HandlerException 处理器异常。
     */
    int getBufferSize() throws HandlerException;

    /**
     * 获取数据的批处理量。
     *
     * @return 数据的批处理量。
     * @throws HandlerException 处理器异常。
     */
    int getBatchSize() throws HandlerException;

    /**
     * 获取最大空闲时间。
     *
     * @return 最大空闲时间。
     * @throws HandlerException 处理器异常。
     */
    long getMaxIdleTime() throws HandlerException;

    /**
     * 设置缓冲器的参数。
     *
     * @param bufferSize  缓冲器的大小。
     * @param batchSize   数据的批处理量。
     * @param maxIdleTime 最大空闲时间。
     * @throws HandlerException 处理器异常。
     */
    void setBufferParameters(int bufferSize, int batchSize, long maxIdleTime) throws HandlerException;

    /**
     * 获取消费者的线程数量。
     *
     * @return 消费者的线程数量。
     * @throws HandlerException 处理器异常。
     */
    int getThread() throws HandlerException;

    /**
     * 设置消费者的线程数量。
     *
     * @param thread 消费者的线程数量。
     * @throws HandlerException 处理器异常。
     */
    void setThread(int thread) throws HandlerException;

    /**
     * 获取消费者是否空闲。
     *
     * @return 消费者是否空闲。
     * @throws HandlerException 处理器异常。
     */
    boolean isIdle() throws HandlerException;
}
