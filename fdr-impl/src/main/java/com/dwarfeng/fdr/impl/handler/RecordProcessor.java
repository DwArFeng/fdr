package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.dcti.sdk.util.DataInfoUtil;
import com.dwarfeng.dcti.stack.bean.dto.DataInfo;
import com.dwarfeng.dutil.develop.backgr.AbstractTask;
import com.dwarfeng.fdr.sdk.util.Constants;
import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.bean.entity.RealtimeValue;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.fdr.stack.exception.PointNotExistsException;
import com.dwarfeng.fdr.stack.exception.RecordStoppedException;
import com.dwarfeng.fdr.stack.handler.ConsumeHandler;
import com.dwarfeng.fdr.stack.handler.Filter;
import com.dwarfeng.fdr.stack.handler.RecordLocalCacheHandler;
import com.dwarfeng.fdr.stack.handler.Trigger;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.generation.KeyGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 记录处理器。
 *
 * @author DwArFeng
 * @since 1.11.0
 */
@Component
public class RecordProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecordProcessor.class);

    private final ThreadPoolTaskExecutor executor;
    private final ThreadPoolTaskScheduler scheduler;

    private final Consumer consumer;
    private final ConsumeBuffer consumeBuffer;

    @Value("${record.consumer_thread}")
    private int thread;
    @Value("${record.threshold.warn}")
    private double warnThreshold;

    private final Lock lock = new ReentrantLock();
    private final List<ConsumeTask> processingConsumeTasks = new ArrayList<>();
    private final List<ConsumeTask> endingConsumeTasks = new ArrayList<>();

    private boolean startFlag = false;
    ScheduledFuture<?> capacityCheckFuture = null;

    public RecordProcessor(
            ThreadPoolTaskExecutor executor,
            ThreadPoolTaskScheduler scheduler,
            Consumer consumer,
            ConsumeBuffer consumeBuffer
    ) {
        this.executor = executor;
        this.scheduler = scheduler;
        this.consumer = consumer;
        this.consumeBuffer = consumeBuffer;
    }

    public void start() {
        lock.lock();
        try {
            if (!startFlag) {
                LOGGER.info("启用 record handler...");
                consumeBuffer.block();
                for (int i = 0; i < thread; i++) {
                    ConsumeTask consumeTask = new ConsumeTask(consumeBuffer, consumer);
                    executor.execute(consumeTask);
                    processingConsumeTasks.add(consumeTask);
                }
                capacityCheckFuture = scheduler.scheduleAtFixedRate(() -> {
                    double ratio = (double) consumeBuffer.bufferedSize() / (double) consumeBuffer.getBufferSize();
                    if (ratio >= warnThreshold) {
                        LOGGER.warn("记录者的待消费元素占用缓存比例为 {}，超过报警值 {}，请检查",
                                ratio,
                                warnThreshold
                        );
                    }
                }, Constants.SCHEDULER_CHECK_INTERVAL);
                startFlag = true;
            }
        } finally {
            lock.unlock();
        }
    }

    @SuppressWarnings("DuplicatedCode")
    public void stop() {
        lock.lock();
        try {
            if (startFlag) {
                LOGGER.info("禁用 record handler...");
                if (Objects.nonNull(capacityCheckFuture)) {
                    capacityCheckFuture.cancel(true);
                    capacityCheckFuture = null;
                }
                processingConsumeTasks.forEach(ConsumeTask::shutdown);
                endingConsumeTasks.addAll(processingConsumeTasks);
                processingConsumeTasks.clear();
                consumeBuffer.unblock();
                processRemainingElement();
                endingConsumeTasks.removeIf(AbstractTask::isFinished);
                if (!endingConsumeTasks.isEmpty()) {
                    LOGGER.info("Record handler 中的线程还未完全结束, 等待线程结束...");
                    endingConsumeTasks.forEach(
                            task -> {
                                try {
                                    task.awaitFinish();
                                } catch (InterruptedException ignored) {
                                }
                            }
                    );
                }
                processingConsumeTasks.clear();
                endingConsumeTasks.clear();
                LOGGER.info("Record handler 已经妥善处理数据, 消费线程结束");
                startFlag = false;
            }
        } finally {
            lock.unlock();
        }
    }

    private void processRemainingElement() {
        // 如果没有剩余元素，直接跳过。
        if (consumeBuffer.bufferedSize() <= 0) return;
        LOGGER.info("消费 record handler 中剩余的元素 {} 个...", consumeBuffer.bufferedSize());
        LOGGER.info("Record handler 中剩余的元素过多时，需要较长时间消费，请耐心等待...");
        ScheduledFuture<?> scheduledFuture = scheduler.scheduleAtFixedRate(() ->
                        LOGGER.info("消费 consume handler 中剩余的元素 {} 个，请耐心等待...", consumeBuffer.bufferedSize()),
                new Date(System.currentTimeMillis() + Constants.SCHEDULER_CHECK_INTERVAL), Constants.SCHEDULER_CHECK_INTERVAL);
        DataInfo dataInfo2Consume;
        while (Objects.nonNull(dataInfo2Consume = consumeBuffer.poll())) {
            try {
                consumer.consume(dataInfo2Consume);
            } catch (Exception e) {
                LOGGER.warn("消费元素时发生异常, 抛弃 DataInfo: " + dataInfo2Consume, e);
            }
        }
        scheduledFuture.cancel(true);
    }

    public void record(String message) throws HandlerException {
        lock.lock();
        try {
            internalRecord(DataInfoUtil.fromMessage(message));
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 优化的记录方法。
     * <p>该记录方法经过优化，在记录期间，绝大部分数据不需要与缓存和数据访问层进行任何交互。尽一切可能的优化了执行效率。</p>
     * <p>仅当数据点第一次被调用的时候，该方法才会访问缓存和数据访问层，将元数据取出并缓存在内存后便不再需要继续访问。</p>
     *
     * @param dataInfo 指定的数据信息。
     * @throws HandlerException 处理器异常。
     * @since 1.4.0
     */
    public void record(DataInfo dataInfo) throws HandlerException {
        lock.lock();
        try {
            internalRecord(dataInfo);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        } finally {
            lock.unlock();
        }
    }

    private void internalRecord(DataInfo dataInfo) throws Exception {
        // 判断是否允许记录，如果不允许，直接报错。
        if (!startFlag) {
            throw new RecordStoppedException();
        }
        consumeBuffer.accept(dataInfo);
    }

    public int bufferedSize() {
        lock.lock();
        try {
            return consumeBuffer.bufferedSize();
        } finally {
            lock.unlock();
        }
    }

    public int getBufferSize() {
        lock.lock();
        try {
            return consumeBuffer.getBufferSize();
        } finally {
            lock.unlock();
        }
    }

    public void setBufferSize(int bufferSize) {
        lock.lock();
        try {
            consumeBuffer.setBufferParameters(bufferSize);
        } finally {
            lock.unlock();
        }
    }

    public int getThread() {
        lock.lock();
        try {
            return thread;
        } finally {
            lock.unlock();
        }
    }

    public void setThread(int thread) {
        lock.lock();
        try {
            thread = Math.max(thread, 1);
            int delta = thread - this.thread;
            this.thread = thread;
            if (startFlag) {
                if (delta > 0) {
                    for (int i = 0; i < delta; i++) {
                        ConsumeTask consumeTask = new ConsumeTask(consumeBuffer, consumer);
                        executor.execute(consumeTask);
                        processingConsumeTasks.add(consumeTask);
                    }
                } else if (delta < 0) {
                    endingConsumeTasks.removeIf(AbstractTask::isFinished);
                    for (int i = 0; i < -delta; i++) {
                        ConsumeTask consumeTask = processingConsumeTasks.remove(0);
                        consumeTask.shutdown();
                        endingConsumeTasks.add(consumeTask);
                    }
                }
            }
        } finally {
            lock.unlock();
        }
    }

    @SuppressWarnings("DuplicatedCode")
    public boolean isIdle() {
        lock.lock();
        try {
            if (consumeBuffer.bufferedSize() > 0) {
                return false;
            }
            if (!processingConsumeTasks.isEmpty()) {
                return false;
            }
            endingConsumeTasks.removeIf(AbstractTask::isFinished);
            return endingConsumeTasks.isEmpty();
        } finally {
            lock.unlock();
        }
    }

    @Component
    public static final class Consumer {

        private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);

        @Autowired
        private KeyGenerator<LongIdKey> keyGenerator;
        @Autowired
        private RecordLocalCacheHandler recordLocalCacheHandler;

        @Autowired
        @Qualifier("filteredEventConsumeHandler")
        private ConsumeHandler<FilteredValue> filteredEventConsumeHandler;
        @Autowired
        @Qualifier("filteredValueConsumeHandler")
        private ConsumeHandler<FilteredValue> filteredValueConsumeHandler;
        @Autowired
        @Qualifier("triggeredEventConsumeHandler")
        private ConsumeHandler<TriggeredValue> triggeredEventConsumeHandler;
        @Autowired
        @Qualifier("triggeredValueConsumeHandler")
        private ConsumeHandler<TriggeredValue> triggeredValueConsumeHandler;
        @Autowired
        @Qualifier("realtimeEventConsumeHandler")
        private ConsumeHandler<RealtimeValue> realtimeEventConsumeHandler;
        @Autowired
        @Qualifier("realtimeValueConsumeHandler")
        private ConsumeHandler<RealtimeValue> realtimeValueConsumeHandler;
        @Autowired
        @Qualifier("persistenceEventConsumeHandler")
        private ConsumeHandler<PersistenceValue> persistenceEventConsumeHandler;
        @Autowired
        @Qualifier("persistenceValueConsumeHandler")
        private ConsumeHandler<PersistenceValue> persistenceValueConsumeHandler;

        public void consume(DataInfo dataInfo) throws HandlerException {
            try {
                // 0. 记录日志，准备工作。
                LOGGER.debug("记录数据信息: " + dataInfo);
                LongIdKey pointKey = new LongIdKey(dataInfo.getPointLongId());
                // 1. 获取 RecordContext。
                RecordLocalCacheHandler.RecordContext recordContext = recordLocalCacheHandler.get(pointKey);
                if (Objects.isNull(recordContext)) {
                    throw new PointNotExistsException(pointKey);
                }
                // 1. 判断数据点是否通过所有的过滤器，任意一个过滤器未通过时，记录并广播过滤点信息并中止整个记录过程。
                for (Filter filter : recordContext.getFilters()) {
                    FilteredValue filteredValue = filter.test(dataInfo);
                    if (Objects.nonNull(filteredValue)) {
                        filteredValue.setKey(keyGenerator.generate());
                        LOGGER.debug("数据信息未通过过滤, 过滤数据点信息: " + filteredValue);
                        filteredEventConsumeHandler.accept(filteredValue);
                        filteredValueConsumeHandler.accept(filteredValue);
                        return;
                    }
                }
                // 3. 遍历所有触发器，获取所有的触发数据点。记录并广播触发信息。
                for (Trigger trigger : recordContext.getTriggers()) {
                    TriggeredValue triggeredValue = trigger.test(dataInfo);
                    if (Objects.nonNull(triggeredValue)) {
                        triggeredValue.setKey(keyGenerator.generate());
                        LOGGER.debug("数据信息满足触发条件, 触发数据点信息: " + triggeredValue);
                        triggeredEventConsumeHandler.accept(triggeredValue);
                        triggeredValueConsumeHandler.accept(triggeredValue);
                    }
                }
                // 4. 如果数据点的实时数据使能且数据的发生时间晚于之前的实时数据发生时间，记录实时数据并广播。
                if (recordContext.getPoint().isRealtimeEnabled()) {
                    RealtimeValue realtimeValue = new RealtimeValue(
                            recordContext.getPoint().getKey(),
                            dataInfo.getHappenedDate(),
                            dataInfo.getValue()
                    );
                    LOGGER.debug("数据点实时数据记录使能, 实时数据信息: " + realtimeValue);
                    realtimeEventConsumeHandler.accept(realtimeValue);
                    realtimeValueConsumeHandler.accept(realtimeValue);
                }
                // 5. 如果数据点的持久数据使能，记录持久数据并广播。
                if (recordContext.getPoint().isPersistenceEnabled()) {
                    PersistenceValue persistenceValue = new PersistenceValue(
                            keyGenerator.generate(),
                            recordContext.getPoint().getKey(),
                            dataInfo.getHappenedDate(),
                            dataInfo.getValue()
                    );
                    LOGGER.debug("数据点持久数据记录使能, 持久数据信息: " + persistenceValue);
                    persistenceEventConsumeHandler.accept(persistenceValue);
                    persistenceValueConsumeHandler.accept(persistenceValue);
                }
            } catch (Exception e) {
                throw HandlerExceptionHelper.parse(e);
            }
        }
    }

    @Component
    public static class ConsumeBuffer {

        @Value("${record.buffer_size}")
        private int bufferSize;

        private final Lock lock = new ReentrantLock();
        private final Condition provideCondition = lock.newCondition();
        private final Condition consumeCondition = lock.newCondition();
        private final List<DataInfo> buffer = new ArrayList<>();

        private boolean blockEnabled = true;

        public void accept(DataInfo dataInfo) {
            lock.lock();
            try {
                while (buffer.size() >= bufferSize) {
                    provideCondition.awaitUninterruptibly();
                }

                buffer.add(dataInfo);
                consumeCondition.signalAll();
            } finally {
                lock.unlock();
            }
        }

        public DataInfo poll() {
            lock.lock();
            try {
                /*
                 * 线程阻塞的逻辑。
                 *   [buffer]当中没有任何元素，便阻塞，否则不会阻塞。
                 *   以上条件发生的前提是 [runningFlag] 必须为 true，一旦 [runningFlag] 为 false，则其余参数为任何值都
                 *   不能够阻塞。
                 */
                while ((buffer.isEmpty() && blockEnabled)) {
                    consumeCondition.awaitUninterruptibly();
                }

                // 取出第一个DataInfo，并判断buffer中为空的情形。
                DataInfo dataInfo = null;
                if (!buffer.isEmpty()) {
                    dataInfo = buffer.remove(0);
                }

                provideCondition.signalAll();
                return dataInfo;
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

        public int getBufferSize() {
            lock.lock();
            try {
                return bufferSize;
            } finally {
                lock.unlock();
            }
        }

        public void setBufferParameters(int bufferSize) {
            lock.lock();
            try {
                this.bufferSize = Math.max(bufferSize, 1);

                provideCondition.signalAll();
                consumeCondition.signalAll();
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

    private static final class ConsumeTask extends AbstractTask {

        private static final Logger LOGGER = LoggerFactory.getLogger(ConsumeTask.class);

        private final ConsumeBuffer consumeBuffer;
        private final Consumer consumer;

        private final AtomicBoolean runningFlag = new AtomicBoolean(true);

        private ConsumeTask(ConsumeBuffer consumeBuffer, Consumer consumer) {
            this.consumeBuffer = consumeBuffer;
            this.consumer = consumer;
        }

        protected void todo() {
            while (runningFlag.get()) {
                DataInfo dataInfo = null;
                try {
                    dataInfo = consumeBuffer.poll();
                    if (Objects.isNull(dataInfo)) return;
                    consumer.consume(dataInfo);
                } catch (Exception e) {
                    if (Objects.nonNull(dataInfo)) {
                        LOGGER.warn("记录数据信息时发生异常, 抛弃 DataInfo: " + dataInfo, e);
                    }
                }
            }
            LOGGER.info("记录线程退出...");
        }

        public void shutdown() {
            runningFlag.set(false);
        }
    }
}
