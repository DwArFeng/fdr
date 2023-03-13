package com.dwarfeng.fdr.impl.handler.source;

import com.dwarfeng.dcti.stack.bean.dto.DataInfo;
import com.dwarfeng.fdr.stack.exception.RecordStoppedException;
import com.dwarfeng.fdr.stack.handler.Source;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class MockSource extends AbstractSource {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockSource.class);

    private final ThreadPoolTaskScheduler scheduler;

    private final MockBuffer mockBuffer;

    @Value("${source.mock.data_size_per_sec}")
    private int dataSizePerSec;
    @Value("${source.mock.data_record_max_coefficient}")
    private double dataRecordMaxCoefficient;
    @Value("${source.mock.point_id}")
    private long pointId;

    private final Lock lock = new ReentrantLock();
    private boolean startFlag = false;
    private MockRecordPlan mockRecordPlan = null;
    private ScheduledFuture<?> mockRecordPlanFuture = null;
    private MockMonitorPlan mockMonitorPlan = null;
    private ScheduledFuture<?> mockMonitorPlanFuture = null;
    private MockProvidePlan mockProvidePlan = null;
    private ScheduledFuture<?> mockProvidePlanFuture = null;

    public MockSource(
            ThreadPoolTaskScheduler scheduler,
            @Qualifier("mockSource.mockBuffer") MockBuffer mockBuffer
    ) {
        this.scheduler = scheduler;
        this.mockBuffer = mockBuffer;
    }

    @Override
    public boolean isOnline() {
        lock.lock();
        try {
            return startFlag;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void online() throws HandlerException {
        lock.lock();
        try {
            if (!startFlag) {
                LOGGER.info("Mock source 上线...");
                mockBuffer.block();
                mockRecordPlan = new MockRecordPlan(context, mockBuffer, dataSizePerSec, dataRecordMaxCoefficient);
                mockMonitorPlan = new MockMonitorPlan(mockBuffer);
                mockProvidePlan = new MockProvidePlan(mockBuffer, pointId, dataSizePerSec);
                mockRecordPlanFuture = scheduler.scheduleAtFixedRate(mockRecordPlan, 1000);
                mockMonitorPlanFuture = scheduler.scheduleAtFixedRate(mockMonitorPlan, 1000);
                mockProvidePlanFuture = scheduler.scheduleAtFixedRate(mockProvidePlan, 1000);
                startFlag = true;
            }
        } catch (Exception e) {
            throw new HandlerException(e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void offline() throws HandlerException {
        lock.lock();
        try {
            if (startFlag) {
                LOGGER.info("Mock source 下线...");
                mockBuffer.unblock();
                mockRecordPlan.shutdown();
                mockRecordPlanFuture.cancel(true);
                mockMonitorPlan.shutdown();
                mockMonitorPlanFuture.cancel(true);
                mockProvidePlan.shutdown();
                mockProvidePlanFuture.cancel(true);
                mockRecordPlan = null;
                mockRecordPlanFuture = null;
                mockMonitorPlan = null;
                mockMonitorPlanFuture = null;
                mockProvidePlan = null;
                mockProvidePlanFuture = null;
                startFlag = false;
            }
        } catch (Exception e) {
            throw new HandlerException(e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        return "MockSource{" +
                "dataSizePerSec=" + dataSizePerSec +
                ", dataRecordMaxCoefficient=" + dataRecordMaxCoefficient +
                ", pointId=" + pointId +
                ", startFlag=" + startFlag +
                '}';
    }

    @Configuration
    public static class MockSourceConfiguration {

        @Value("${source.mock.buffer_size}")
        private int bufferSize;

        @Bean("mockSource.mockBuffer")
        public MockBuffer mockBuffer() {
            return new MockBuffer(bufferSize);
        }
    }

    private static class MockBuffer {

        private final Lock lock = new ReentrantLock();
        private final Condition provideCondition = lock.newCondition();
        private final Condition consumeCondition = lock.newCondition();
        private final List<DataInfo> buffer = new ArrayList<>();

        private final int bufferSize;

        private boolean blockEnabled = true;

        private MockBuffer(int bufferSize) {
            this.bufferSize = bufferSize;
        }

        public void accept(DataInfo dataInfo) {
            lock.lock();
            try {
                while (buffer.size() >= bufferSize && blockEnabled) {
                    try {
                        provideCondition.await();
                    } catch (InterruptedException ignored) {
                    }
                }

                if (!blockEnabled) {
                    return;
                }

                buffer.add(dataInfo);
                consumeCondition.signalAll();
            } finally {
                lock.unlock();
            }
        }

        public List<DataInfo> poll(int size) {
            lock.lock();
            try {
                while (buffer.size() < size && blockEnabled) {
                    try {
                        consumeCondition.await();
                    } catch (InterruptedException ignored) {
                    }
                }

                if (!blockEnabled) {
                    return Collections.emptyList();
                }

                List<DataInfo> subList = buffer.subList(0, size);
                List<DataInfo> elements2Return = new ArrayList<>(subList);
                subList.clear();

                provideCondition.signalAll();
                return elements2Return;
            } finally {
                lock.unlock();
            }
        }

        public int bufferedSize() {
            lock.lock();
            try {
                return buffer.size();
            } finally {
                lock.unlock();
            }
        }

        public void block() {
            lock.lock();
            try {
                this.blockEnabled = true;
                this.provideCondition.signalAll();
                this.consumeCondition.signalAll();
            } finally {
                lock.unlock();
            }
        }

        public void unblock() {
            lock.lock();
            try {
                this.blockEnabled = false;
                this.provideCondition.signalAll();
                this.consumeCondition.signalAll();
            } finally {
                lock.unlock();
            }
        }
    }

    private static class MockRecordPlan implements Runnable {

        private final Source.Context context;
        private final MockBuffer mockBuffer;
        private final int size;
        private final double dataRecordMaxCoefficient;

        private final Lock lock = new ReentrantLock();
        private boolean runningFlag = true;

        public MockRecordPlan(
                Source.Context context, MockBuffer mockBuffer, int size, double dataRecordMaxCoefficient
        ) {
            this.context = context;
            this.mockBuffer = mockBuffer;
            this.size = size;
            this.dataRecordMaxCoefficient = dataRecordMaxCoefficient;
        }

        @Override
        public void run() {
            lock.lock();
            try {
                if (!runningFlag) {
                    return;
                }

                for (DataInfo dataInfo : mockBuffer.poll(Math.min((int) (size * dataRecordMaxCoefficient),
                        mockBuffer.bufferedSize()))) {
                    try {
                        context.record(dataInfo);
                    } catch (RecordStoppedException e) {
                        LOGGER.warn("记录处理器被禁用， 消息 " + dataInfo + " 将会被忽略", e);
                    } catch (Exception e) {
                        LOGGER.warn("记录处理器无法处理, 消息 " + dataInfo + " 将会被忽略", e);
                    }
                }
            } finally {
                lock.unlock();
            }
        }

        public void shutdown() {
            lock.lock();
            try {
                runningFlag = false;
            } finally {
                lock.unlock();
            }
        }
    }

    private static class MockProvidePlan implements Runnable {

        private final MockBuffer mockBuffer;
        private final long pointId;
        private final int size;

        private final Lock lock = new ReentrantLock();
        private final Random random = new Random();
        private boolean runningFlag = true;

        public MockProvidePlan(MockBuffer mockBuffer, long pointId, int size) {
            this.mockBuffer = mockBuffer;
            this.pointId = pointId;
            this.size = size;
        }

        @Override
        public void run() {
            lock.lock();
            try {
                if (!runningFlag) {
                    return;
                }

                for (int i = 0; i < size; i++) {
                    mockBuffer.accept(new DataInfo(pointId, Long.toString(random.nextLong()), new Date()));
                }
            } finally {
                lock.unlock();
            }
        }

        public void shutdown() {
            lock.lock();
            try {
                runningFlag = false;
            } finally {
                lock.unlock();
            }
        }
    }

    private static class MockMonitorPlan implements Runnable {

        private static final Logger LOGGER = LoggerFactory.getLogger(MockMonitorPlan.class);

        private final MockBuffer mockBuffer;

        private final Lock lock = new ReentrantLock();
        private boolean runningFlag = true;

        public MockMonitorPlan(MockBuffer mockBuffer) {
            this.mockBuffer = mockBuffer;
        }

        @Override
        public void run() {
            lock.lock();
            try {
                if (!runningFlag) {
                    return;
                }
                LOGGER.info("缓冲容量: " + mockBuffer.bufferedSize() + "/" + mockBuffer.bufferSize);
            } finally {
                lock.unlock();
            }
        }

        public void shutdown() {
            lock.lock();
            try {
                runningFlag = false;
            } finally {
                lock.unlock();
            }
        }
    }
}
