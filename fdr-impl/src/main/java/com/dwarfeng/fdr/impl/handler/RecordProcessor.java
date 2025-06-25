package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.dutil.develop.backgr.AbstractTask;
import com.dwarfeng.fdr.sdk.util.Constants;
import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.fdr.stack.bean.dto.NormalData;
import com.dwarfeng.fdr.stack.bean.dto.RecordInfo;
import com.dwarfeng.fdr.stack.bean.dto.TriggeredData;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.exception.PointNotExistsException;
import com.dwarfeng.fdr.stack.exception.RecordHandlerStoppedException;
import com.dwarfeng.fdr.stack.handler.*;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.*;
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

    private final SourceHandler sourceHandler;
    private final ConsumeHandler<NormalData> normalKeepConsumeHandler;
    private final ConsumeHandler<NormalData> normalPersistConsumeHandler;
    private final ConsumeHandler<FilteredData> filteredKeepConsumeHandler;
    private final ConsumeHandler<FilteredData> filteredPersistConsumeHandler;
    private final ConsumeHandler<TriggeredData> triggeredKeepConsumeHandler;
    private final ConsumeHandler<TriggeredData> triggeredPersistConsumeHandler;

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
            // 使用懒加载，以避免循环依赖。
            @Lazy SourceHandler sourceHandler,
            @Qualifier("normalKeepConsumeHandler")
            ConsumeHandler<NormalData> normalKeepConsumeHandler,
            @Qualifier("normalPersistConsumeHandler")
            ConsumeHandler<NormalData> normalPersistConsumeHandler,
            @Qualifier("filteredKeepConsumeHandler")
            ConsumeHandler<FilteredData> filteredKeepConsumeHandler,
            @Qualifier("filteredPersistConsumeHandler")
            ConsumeHandler<FilteredData> filteredPersistConsumeHandler,
            @Qualifier("triggeredKeepConsumeHandler")
            ConsumeHandler<TriggeredData> triggeredKeepConsumeHandler,
            @Qualifier("triggeredPersistConsumeHandler")
            ConsumeHandler<TriggeredData> triggeredPersistConsumeHandler,
            ThreadPoolTaskExecutor executor,
            ThreadPoolTaskScheduler scheduler,
            Consumer consumer,
            ConsumeBuffer consumeBuffer
    ) {
        this.sourceHandler = sourceHandler;
        this.normalKeepConsumeHandler = normalKeepConsumeHandler;
        this.normalPersistConsumeHandler = normalPersistConsumeHandler;
        this.filteredKeepConsumeHandler = filteredKeepConsumeHandler;
        this.filteredPersistConsumeHandler = filteredPersistConsumeHandler;
        this.triggeredKeepConsumeHandler = triggeredKeepConsumeHandler;
        this.triggeredPersistConsumeHandler = triggeredPersistConsumeHandler;
        this.executor = executor;
        this.scheduler = scheduler;
        this.consumer = consumer;
        this.consumeBuffer = consumeBuffer;
    }

    /**
     * 优化的记录方法。
     * <p>
     * 该记录方法经过优化，在记录期间，绝大部分数据不需要与缓存和数据访问层进行任何交互。尽一切可能的优化了执行效率。
     * <p>
     * 仅当数据点第一次被调用的时候，该方法才会访问缓存和数据访问层，将元数据取出并缓存在内存后便不再需要继续访问。
     *
     * @param recordInfo 记录信息。
     * @throws HandlerException 处理器异常。
     * @since 1.4.0
     */
    public void record(RecordInfo recordInfo) throws HandlerException {
        lock.lock();
        try {
            internalRecord(recordInfo);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        } finally {
            lock.unlock();
        }
    }

    private void internalRecord(RecordInfo recordInfo) throws Exception {
        // 判断是否允许记录，如果不允许，直接报错。
        if (!startFlag) {
            throw new RecordHandlerStoppedException();
        }
        consumeBuffer.accept(recordInfo);
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

    public void workerWork() throws Exception {
        lock.lock();
        try {
            if (startFlag) {
                return;
            }

            LOGGER.info("记录侧消费处理器启动...");
            normalKeepConsumeHandler.start();
            normalPersistConsumeHandler.start();
            filteredKeepConsumeHandler.start();
            filteredPersistConsumeHandler.start();
            triggeredKeepConsumeHandler.start();
            triggeredPersistConsumeHandler.start();

            LOGGER.info("逻辑侧消费处理器启动...");
            consumeBuffer.block();
            for (int i = 0; i < thread; i++) {
                ConsumeTask consumeTask = new ConsumeTask(consumeBuffer, consumer);
                executor.execute(consumeTask);
                processingConsumeTasks.add(consumeTask);
            }
            capacityCheckFuture = scheduler.scheduleAtFixedRate(() -> {
                double ratio = (double) consumeBuffer.bufferedSize() / (double) consumeBuffer.getBufferSize();
                if (ratio >= warnThreshold) {
                    String message = "逻辑侧的待消费元素占用缓存比例为 {}，超过报警值 {}，请检查";
                    LOGGER.warn(message, ratio, warnThreshold);
                }
            }, Constants.SCHEDULER_CHECK_INTERVAL);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }

            LOGGER.info("数据源上线...");
            List<Source> sources = sourceHandler.all();
            for (Source source : sources) {
                source.online();
            }

            startFlag = true;
        } finally {
            lock.unlock();
        }
    }

    @SuppressWarnings("DuplicatedCode")
    public void workerRest() throws Exception {
        lock.lock();
        try {
            if (!startFlag) {
                return;
            }

            LOGGER.info("数据源下线...");
            List<Source> sources = sourceHandler.all();
            for (Source source : sources) {
                source.offline();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }

            LOGGER.info("逻辑侧消费处理器关闭...");
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
                LOGGER.info("逻辑侧消费处理器中的线程还未完全结束, 等待线程结束...");
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
            LOGGER.info("逻辑侧消费处理器已经妥善处理数据, 消费线程结束");

            LOGGER.info("记录侧消费处理器关闭...");
            normalKeepConsumeHandler.stop();
            normalPersistConsumeHandler.stop();
            filteredKeepConsumeHandler.stop();
            filteredPersistConsumeHandler.stop();
            triggeredKeepConsumeHandler.stop();
            triggeredPersistConsumeHandler.stop();

            startFlag = false;
        } finally {
            lock.unlock();
        }
    }

    private void processRemainingElement() {
        // 如果没有剩余元素，直接跳过。
        if (consumeBuffer.bufferedSize() <= 0) {
            return;
        }

        LOGGER.info("消费逻辑侧消费处理器中剩余的元素 {} 个...", consumeBuffer.bufferedSize());
        LOGGER.info("逻辑侧消费处理器中剩余的元素过多时，需要较长时间消费，请耐心等待...");
        ScheduledFuture<?> scheduledFuture = scheduler.scheduleAtFixedRate(
                () -> {
                    String message = "消费逻辑侧消费处理器中剩余的元素 {} 个，请耐心等待...";
                    LOGGER.info(message, consumeBuffer.bufferedSize());
                },
                new Date(System.currentTimeMillis() + Constants.SCHEDULER_CHECK_INTERVAL),
                Constants.SCHEDULER_CHECK_INTERVAL
        );
        RecordInfo recordInfo2Consume;
        while (Objects.nonNull(recordInfo2Consume = consumeBuffer.poll())) {
            try {
                consumer.consume(recordInfo2Consume);
            } catch (Exception e) {
                LOGGER.warn("逻辑侧消费处理器消费元素时发生异常, 抛弃 RecordInfo: {}", recordInfo2Consume, e);
            }
        }
        scheduledFuture.cancel(true);
    }

    @Component
    public static final class Consumer {

        private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);

        private final RecordLocalCacheHandler recordLocalCacheHandler;

        private final ConsumeHandler<NormalData> normalKeepConsumeHandler;
        private final ConsumeHandler<NormalData> normalPersistConsumeHandler;
        private final ConsumeHandler<FilteredData> filteredKeepConsumeHandler;
        private final ConsumeHandler<FilteredData> filteredPersistConsumeHandler;
        private final ConsumeHandler<TriggeredData> triggeredKeepConsumeHandler;
        private final ConsumeHandler<TriggeredData> triggeredPersistConsumeHandler;

        public Consumer(
                RecordLocalCacheHandler recordLocalCacheHandler,
                @Qualifier("normalKeepConsumeHandler")
                ConsumeHandler<NormalData> normalKeepConsumeHandler,
                @Qualifier("normalPersistConsumeHandler")
                ConsumeHandler<NormalData> normalPersistConsumeHandler,
                @Qualifier("filteredKeepConsumeHandler")
                ConsumeHandler<FilteredData> filteredKeepConsumeHandler,
                @Qualifier("filteredPersistConsumeHandler")
                ConsumeHandler<FilteredData> filteredPersistConsumeHandler,
                @Qualifier("triggeredKeepConsumeHandler")
                ConsumeHandler<TriggeredData> triggeredKeepConsumeHandler,
                @Qualifier("triggeredPersistConsumeHandler")
                ConsumeHandler<TriggeredData> triggeredPersistConsumeHandler
        ) {
            this.recordLocalCacheHandler = recordLocalCacheHandler;
            this.normalKeepConsumeHandler = normalKeepConsumeHandler;
            this.normalPersistConsumeHandler = normalPersistConsumeHandler;
            this.filteredKeepConsumeHandler = filteredKeepConsumeHandler;
            this.filteredPersistConsumeHandler = filteredPersistConsumeHandler;
            this.triggeredKeepConsumeHandler = triggeredKeepConsumeHandler;
            this.triggeredPersistConsumeHandler = triggeredPersistConsumeHandler;
        }

        public void consume(RecordInfo recordInfo) throws HandlerException {
            try {
                // 记录日志，准备工作。
                LOGGER.debug("记录数据信息: {}", recordInfo);
                LongIdKey pointKey = recordInfo.getPointKey();

                // 获取 RecordContext。
                RecordLocalCacheHandler.RecordContext recordContext = recordLocalCacheHandler.get(pointKey);
                if (Objects.isNull(recordContext)) {
                    throw new PointNotExistsException(pointKey);
                }
                Point point = recordContext.getPoint();
                Map<LongIdKey, Washer> preFilterWasherMap = recordContext.getPreFilterWasherMap();
                Map<LongIdKey, Filter> filterMap = recordContext.getFilterMap();
                Map<LongIdKey, Washer> postFilterWasherMap = recordContext.getPostFilterWasherMap();
                Map<LongIdKey, Trigger> triggerMap = recordContext.getTriggerMap();

                // 遍历所有的过滤前清洗器，清洗数据。
                for (Map.Entry<LongIdKey, Washer> entry : preFilterWasherMap.entrySet()) {
                    Washer washer = entry.getValue();

                    Object rawValue = recordInfo.getValue();
                    LOGGER.debug("数据信息经过过滤前清洗, 原始数据点信息: {}", rawValue);
                    Object washedValue = washer.wash(rawValue);
                    LOGGER.debug("数据信息经过过滤前清洗, 清洗数据点信息: {}", washedValue);

                    recordInfo.setValue(washedValue);
                }

                // 遍历所有的过滤器，任意一个过滤器未通过时，根据数据点配置保持或持久被过滤数据，随后终止。
                for (Map.Entry<LongIdKey, Filter> entry : filterMap.entrySet()) {
                    Object value = recordInfo.getValue();
                    Date happenedDate = recordInfo.getHappenedDate();

                    LongIdKey filterKey = entry.getKey();
                    Filter filter = entry.getValue();

                    Filter.TestInfo testInfo = new Filter.TestInfo(pointKey, value, happenedDate);
                    Filter.TestResult testResult = filter.test(testInfo);

                    if (testResult.isFiltered()) {
                        FilteredData filteredRecord = new FilteredData(
                                pointKey, filterKey, value, testResult.getMessage(), happenedDate
                        );
                        LOGGER.debug("数据信息未通过过滤, 过滤数据点信息: {}", filteredRecord);

                        if (point.isFilteredKeepEnabled()) {
                            filteredKeepConsumeHandler.accept(filteredRecord);
                        }
                        if (point.isFilteredPersistEnabled()) {
                            filteredPersistConsumeHandler.accept(filteredRecord);
                        }
                        return;
                    }
                }

                // 遍历所有的过滤后清洗器，清洗数据。
                for (Map.Entry<LongIdKey, Washer> entry : postFilterWasherMap.entrySet()) {
                    Washer washer = entry.getValue();

                    Object rawValue = recordInfo.getValue();
                    LOGGER.debug("数据信息经过过滤后清洗, 原始数据点信息: {}", rawValue);
                    Object washedValue = washer.wash(rawValue);
                    LOGGER.debug("数据信息经过过滤后清洗, 清洗数据点信息: {}", washedValue);

                    recordInfo.setValue(washedValue);
                }

                // 遍历所有的触发器，任意一个触发器触发时，根据数据点配置保持或持久触发数据。
                for (Map.Entry<LongIdKey, Trigger> entry : triggerMap.entrySet()) {
                    Object value = recordInfo.getValue();
                    Date happenedDate = recordInfo.getHappenedDate();

                    LongIdKey triggerKey = entry.getKey();
                    Trigger trigger = entry.getValue();

                    Trigger.TestInfo testInfo = new Trigger.TestInfo(pointKey, value, happenedDate);
                    Trigger.TestResult testResult = trigger.test(testInfo);

                    if (testResult.isTriggered()) {
                        TriggeredData triggeredRecord = new TriggeredData(
                                pointKey, triggerKey, value, testResult.getMessage(), happenedDate
                        );
                        LOGGER.debug("数据信息满足触发条件, 触发数据点信息: {}", triggeredRecord);

                        if (point.isTriggeredKeepEnabled()) {
                            triggeredKeepConsumeHandler.accept(triggeredRecord);
                        }
                        if (point.isTriggeredPersistEnabled()) {
                            triggeredPersistConsumeHandler.accept(triggeredRecord);
                        }
                    }
                }

                // 生成一般数据，根据数据点配置保持或持久一般数据。
                {
                    Object value = recordInfo.getValue();
                    Date happenedDate = recordInfo.getHappenedDate();

                    NormalData normalRecord = new NormalData(pointKey, value, happenedDate);
                    LOGGER.debug("记录一般数据: {}", normalRecord);

                    if (point.isNormalKeepEnabled()) {
                        normalKeepConsumeHandler.accept(normalRecord);
                    }
                    if (point.isNormalPersistEnabled()) {
                        normalPersistConsumeHandler.accept(normalRecord);
                    }
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
        private final List<RecordInfo> buffer = new ArrayList<>();

        private boolean blockEnabled = true;

        public void accept(RecordInfo recordInfo) {
            lock.lock();
            try {
                while (buffer.size() >= bufferSize) {
                    provideCondition.awaitUninterruptibly();
                }

                buffer.add(recordInfo);
                consumeCondition.signalAll();
            } finally {
                lock.unlock();
            }
        }

        public RecordInfo poll() {
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

                // 取出第一个RecordInfo，并判断buffer中为空的情形。
                RecordInfo recordInfo = null;
                if (!buffer.isEmpty()) {
                    recordInfo = buffer.remove(0);
                }

                provideCondition.signalAll();
                return recordInfo;
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

        @Override
        protected void todo() {
            while (runningFlag.get()) {
                RecordInfo recordInfo = null;
                try {
                    recordInfo = consumeBuffer.poll();
                    if (Objects.isNull(recordInfo)) return;
                    consumer.consume(recordInfo);
                } catch (Exception e) {
                    if (Objects.nonNull(recordInfo)) {
                        LOGGER.warn("记录数据信息时发生异常, 抛弃 RecordInfo: {}", recordInfo, e);
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
