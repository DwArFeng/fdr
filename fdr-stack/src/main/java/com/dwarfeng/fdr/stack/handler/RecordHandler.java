package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.fdr.stack.bean.dto.RecordInfo;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.StartableHandler;

/**
 * 记录处理器。
 *
 * @author DwArFeng
 * @since 1.4.0
 */
public interface RecordHandler extends StartableHandler {

    /**
     * 记录数据。
     *
     * @param recordInfo 记录信息。
     * @throws HandlerException 处理器异常。
     */
    void record(RecordInfo recordInfo) throws HandlerException;

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
