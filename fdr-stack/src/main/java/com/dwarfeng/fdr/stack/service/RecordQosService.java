package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.fdr.stack.struct.RecordLocalCache;
import com.dwarfeng.subgrade.stack.bean.Bean;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 服务质量服务。
 *
 * @author DwArFeng
 * @since 1.2.0
 */
public interface RecordQosService extends Service {

    /**
     * 获取指定数据点的记录本地缓存。
     *
     * @param pointKey 指定的数据点。
     * @return 指定数据点的记录本地缓存，或者是null。
     * @throws ServiceException 服务异常。
     */
    RecordLocalCache getRecordLocalCache(LongIdKey pointKey) throws ServiceException;

    /**
     * 清除本地缓存。
     *
     * @throws ServiceException 服务异常。
     */
    void clearLocalCache() throws ServiceException;

    /**
     * 获取指定消费者的消费者状态。
     *
     * @param consumerId 指定的消费者ID。
     * @return 消费者状态。
     * @throws ServiceException 服务异常。
     */
    ConsumerStatus getConsumerStatus(ConsumerId consumerId) throws ServiceException;

    /**
     * 设置指定消费者的参数。
     *
     * @param consumerId  指定的消费者ID。
     * @param bufferSize  缓冲器的大小。
     * @param batchSize   数据的批处理量。
     * @param maxIdleTime 最大空闲时间。
     * @param thread      消费者的线程数量。
     * @throws ServiceException 服务异常。
     */
    void setConsumerParameters(
            ConsumerId consumerId, Integer bufferSize, Integer batchSize, Long maxIdleTime, Integer thread)
            throws ServiceException;

    /**
     * 获取指定记录者的记录者状态。
     *
     * @return 记录者状态。
     * @throws ServiceException 服务异常。
     */
    RecorderStatus getRecorderStatus() throws ServiceException;

    /**
     * 设置指定记录者的参数。
     *
     * @param bufferSize 缓冲器的大小。
     * @param thread     记录者的线程数量。
     * @throws ServiceException 服务异常。
     */
    void setRecorderParameters(Integer bufferSize, Integer thread) throws ServiceException;

    /**
     * 获取记录服务是否已经开始。
     *
     * @return 记录服务是否已经开始。
     * @throws ServiceException 服务异常。
     * @since 2.1.5
     */
    boolean isRecordStarted() throws ServiceException;

    /**
     * 开启记录服务。
     *
     * @throws ServiceException 服务异常。
     */
    void startRecord() throws ServiceException;

    /**
     * 关闭记录服务。
     *
     * @throws ServiceException 服务异常。
     */
    void stopRecord() throws ServiceException;

    /**
     * 消费者ID。
     *
     * @author DwArFeng
     * @since 1.8.0
     */
    enum ConsumerId {

        KEEP_FILTERED("keep", "filtered"),
        PERSIST_FILTERED("persist", "filtered"),
        KEEP_TRIGGERED("keep", "triggered"),
        PERSIST_TRIGGERED("persist", "triggered"),
        KEEP_NORMAL("keep", "normal"),
        PERSIST_NORMAL("persist", "normal"),
        ;

        private final String type;
        private final String label;

        ConsumerId(String type, String label) {
            this.type = type;
            this.label = label;
        }

        public String getType() {
            return type;
        }

        public String getLabel() {
            return label;
        }

        @Override
        public String toString() {
            return "ConsumerId{" +
                    "type='" + type + '\'' +
                    ", label='" + label + '\'' +
                    '}';
        }
    }

    /**
     * 消费者状态。
     *
     * @author DwArFeng
     * @since 1.8.0
     */
    class ConsumerStatus implements Bean {

        private static final long serialVersionUID = 5044732228551678000L;

        private int bufferedSize;
        private int bufferSize;
        private int batchSize;
        private long maxIdleTime;
        private int thread;
        private boolean idle;

        public ConsumerStatus() {
        }

        public ConsumerStatus(
                int bufferedSize, int bufferSize, int batchSize, long maxIdleTime, int thread, boolean idle) {
            this.bufferedSize = bufferedSize;
            this.bufferSize = bufferSize;
            this.batchSize = batchSize;
            this.maxIdleTime = maxIdleTime;
            this.thread = thread;
            this.idle = idle;
        }

        public int getBufferedSize() {
            return bufferedSize;
        }

        public void setBufferedSize(int bufferedSize) {
            this.bufferedSize = bufferedSize;
        }

        public int getBufferSize() {
            return bufferSize;
        }

        public void setBufferSize(int bufferSize) {
            this.bufferSize = bufferSize;
        }

        public int getBatchSize() {
            return batchSize;
        }

        public void setBatchSize(int batchSize) {
            this.batchSize = batchSize;
        }

        public long getMaxIdleTime() {
            return maxIdleTime;
        }

        public void setMaxIdleTime(long maxIdleTime) {
            this.maxIdleTime = maxIdleTime;
        }

        public int getThread() {
            return thread;
        }

        public void setThread(int thread) {
            this.thread = thread;
        }

        public boolean isIdle() {
            return idle;
        }

        public void setIdle(boolean idle) {
            this.idle = idle;
        }

        @Override
        public String toString() {
            return "ConsumerStatus{" +
                    "bufferedSize=" + bufferedSize +
                    ", bufferSize=" + bufferSize +
                    ", batchSize=" + batchSize +
                    ", maxIdleTime=" + maxIdleTime +
                    ", thread=" + thread +
                    ", idle=" + idle +
                    '}';
        }
    }

    /**
     * 记录者状态。
     *
     * @author DwArFeng
     * @since 1.8.1
     */
    class RecorderStatus implements Bean {

        private static final long serialVersionUID = -6568120884699592771L;

        private int bufferedSize;
        private int bufferSize;
        private int thread;
        private boolean idle;

        public RecorderStatus() {
        }

        public RecorderStatus(int bufferedSize, int bufferSize, int thread, boolean idle) {
            this.bufferedSize = bufferedSize;
            this.bufferSize = bufferSize;
            this.thread = thread;
            this.idle = idle;
        }

        public int getBufferedSize() {
            return bufferedSize;
        }

        public void setBufferedSize(int bufferedSize) {
            this.bufferedSize = bufferedSize;
        }

        public int getBufferSize() {
            return bufferSize;
        }

        public void setBufferSize(int bufferSize) {
            this.bufferSize = bufferSize;
        }

        public int getThread() {
            return thread;
        }

        public void setThread(int thread) {
            this.thread = thread;
        }

        public boolean isIdle() {
            return idle;
        }

        public void setIdle(boolean idle) {
            this.idle = idle;
        }

        @Override
        public String toString() {
            return "RecorderStatus{" +
                    "bufferedSize=" + bufferedSize +
                    ", bufferSize=" + bufferSize +
                    ", thread=" + thread +
                    ", idle=" + idle +
                    '}';
        }
    }
}
