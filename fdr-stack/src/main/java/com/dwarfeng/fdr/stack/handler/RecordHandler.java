package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.dcti.stack.bean.dto.DataInfo;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 记录处理器。
 *
 * @author DwArFeng
 * @since 1.4.0
 */
public interface RecordHandler extends Handler {

    /**
     * 记录处理器是否启动。
     *
     * @return 记录处理器是否被启用。
     * @throws HandlerException 处理器异常。
     */
    boolean isStarted() throws HandlerException;

    /**
     * 开启记录处理器。
     *
     * @throws HandlerException 处理器异常。
     */
    void start() throws HandlerException;

    /**
     * 关闭记录处理器。
     *
     * @throws HandlerException 处理器异常。
     */
    void stop() throws HandlerException;

    /**
     * 向程序中记录数据。
     *
     * @param message 文本形式的数据信息。
     * @throws HandlerException 处理器异常。
     */
    void record(String message) throws HandlerException;

    /**
     * 向程序中记录数据。
     *
     * @param dataInfo 指定的数据信息。
     * @throws HandlerException 处理器异常。
     */
    void record(DataInfo dataInfo) throws HandlerException;

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
     * 设置缓冲器的容量。
     *
     * @param bufferSize 缓冲器的容量。
     * @throws HandlerException 处理器异常。
     */
    void setBufferSize(int bufferSize) throws HandlerException;

    /**
     * 获取记录者的线程数量。
     *
     * @return 记录者的线程数量。
     * @throws HandlerException 处理器异常。
     */
    int getThread() throws HandlerException;

    /**
     * 设置记录者的线程数量。
     *
     * @param thread 记录者的线程数量。
     * @throws HandlerException 处理器异常。
     */
    void setThread(int thread) throws HandlerException;

    /**
     * 获取记录者是否空闲。
     *
     * @return 记录者是否空闲。
     * @throws HandlerException 处理器异常。
     */
    boolean isIdle() throws HandlerException;
}
