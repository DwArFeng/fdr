package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.dutil.develop.backgr.AbstractTask;
import com.dwarfeng.fdr.sdk.util.Constants;
import com.dwarfeng.fdr.stack.exception.ConsumeStoppedException;
import com.dwarfeng.fdr.stack.handler.ConsumeHandler;
import com.dwarfeng.fdr.stack.struct.Data;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 消费处理器的抽象实现。
 *
 * @author DwArFeng
 * @since 1.2.0
 */
public class ConsumeHandlerImpl<D extends Data> implements ConsumeHandler<D> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumeHandlerImpl.class);

    private final ThreadPoolTaskExecutor executor;
    private final ThreadPoolTaskScheduler scheduler;
    private final List<ConsumeTask<D>> processingConsumeTasks;
    private final List<ConsumeTask<D>> endingConsumeTasks;
    private final Consumer<D> consumer;
    private final double warnThreshold;
    private int thread;

    private final Lock lock = new ReentrantLock();
    private final ConsumeBuffer<D> consumeBuffer = new ConsumeBuffer<>();
    private boolean startFlag = false;
    ScheduledFuture<?> capacityCheckFuture = null;

    public ConsumeHandlerImpl(
            @NonNull ThreadPoolTaskExecutor executor,
            @NonNull ThreadPoolTaskScheduler scheduler,
            @NonNull List<ConsumeTask<D>> processingConsumeTasks,
            @NonNull List<ConsumeTask<D>> endingConsumeTasks,
            @NonNull Consumer<D> consumer,
            int thread,
            double warnThreshold
    ) {
        this.executor = executor;
        this.scheduler = scheduler;
        this.processingConsumeTasks = processingConsumeTasks;
        this.endingConsumeTasks = endingConsumeTasks;
        this.consumer = consumer;
        this.thread = Math.max(thread, 1);
        this.warnThreshold = warnThreshold;
    }

    @Override
    public boolean isStarted() {
        lock.lock();
        try {
            return startFlag;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void start() {
        lock.lock();
        try {
            if (startFlag) {
                return;
            }

            LOGGER.info("消费者为 {} 的记录侧消费处理器开启消费线程...", consumer.getClass().getSimpleName());
            consumeBuffer.block();
            for (int i = 0; i < thread; i++) {
                ConsumeTask<D> consumeTask = new ConsumeTask<>(consumeBuffer, consumer);
                executor.execute(consumeTask);
                processingConsumeTasks.add(consumeTask);
            }
            capacityCheckFuture = scheduler.scheduleAtFixedRate(() -> {
                double ratio = (double) consumeBuffer.bufferedSize() / (double) consumeBuffer.getBufferSize();
                if (ratio >= warnThreshold) {
                    String message = "消费者为 {} 的记录侧的待消费元素占用缓存比例为 {}，超过报警值 {}，请检查";
                    LOGGER.warn(message, consumer.getClass().getSimpleName(), ratio, warnThreshold);
                }
            }, Constants.SCHEDULER_CHECK_INTERVAL);

            startFlag = true;
        } finally {
            lock.unlock();
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void stop() {
        lock.lock();
        try {
            if (!startFlag) {
                return;
            }

            LOGGER.info("消费者为 {} 的记录侧消费处理器结束消费线程...", consumer.getClass().getSimpleName());
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
                String message = "消费者为 {} 的记录侧消费处理器中的线程还未完全结束, 等待线程结束...";
                LOGGER.info(message, consumer.getClass().getSimpleName());
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
            LOGGER.info(
                    "消费者为 {} 的记录侧消费处理器已经妥善处理数据, 消费线程结束",
                    consumer.getClass().getSimpleName()
            );

            startFlag = false;
        } finally {
            lock.unlock();
        }
    }

    private void processRemainingElement() {
        // 如果没有剩余元素，直接跳过。
        if (consumeBuffer.bufferedSize() <= 0) return;
        LOGGER.info(
                "消费者 {} 消费记录侧消费处理器中剩余的元素 {} 个...",
                consumer.getClass().getSimpleName(),
                consumeBuffer.bufferedSize()
        );
        LOGGER.info(
                "消费者为 {} 的记录侧消费处理器中剩余的元素过多时，需要较长时间消费，请耐心等待...",
                consumer.getClass().getSimpleName()
        );
        ScheduledFuture<?> scheduledFuture = scheduler.scheduleAtFixedRate(
                () -> {
                    String message = "消费者 {} 消费记录侧消费处理器中剩余的元素 {} 个，请耐心等待...";
                    LOGGER.info(message, consumer.getClass().getSimpleName(), consumeBuffer.bufferedSize());
                },
                new Date(System.currentTimeMillis() + Constants.SCHEDULER_CHECK_INTERVAL),
                Constants.SCHEDULER_CHECK_INTERVAL
        );
        List<D> element2Consume;
        while (!(element2Consume = consumeBuffer.poll()).isEmpty()) {
            try {
                consumer.consume(element2Consume);
            } catch (Exception e) {
                LOGGER.warn("记录侧消费处理器消费元素时发生异常, 最多抛弃 {} 个元素", element2Consume.size(), e);
            }
        }
        scheduledFuture.cancel(true);
    }

    @Override
    public void accept(D data) throws HandlerException {
        lock.lock();
        try {
            // 判断是否允许消费，如果不允许，直接报错。
            if (!startFlag) {
                throw new ConsumeStoppedException();
            }
            consumeBuffer.accept(data);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int bufferedSize() {
        lock.lock();
        try {
            return consumeBuffer.bufferedSize();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int getBufferSize() {
        lock.lock();
        try {
            return consumeBuffer.getBufferSize();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int getBatchSize() {
        lock.lock();
        try {
            return consumeBuffer.getBatchSize();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public long getMaxIdleTime() {
        lock.lock();
        try {
            return consumeBuffer.getMaxIdleTime();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void setBufferParameters(int bufferSize, int batchSize, long maxIdleTime) {
        lock.lock();
        try {
            consumeBuffer.setBufferParameters(bufferSize, batchSize, maxIdleTime);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int getThread() {
        lock.lock();
        try {
            return thread;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void setThread(int thread) {
        lock.lock();
        try {
            thread = Math.max(thread, 1);
            int delta = thread - this.thread;
            this.thread = thread;
            if (startFlag) {
                if (delta > 0) {
                    for (int i = 0; i < delta; i++) {
                        ConsumeTask<D> consumeTask = new ConsumeTask<>(consumeBuffer, consumer);
                        executor.execute(consumeTask);
                        processingConsumeTasks.add(consumeTask);
                    }
                } else if (delta < 0) {
                    endingConsumeTasks.removeIf(AbstractTask::isFinished);
                    for (int i = 0; i < -delta; i++) {
                        ConsumeTask<D> consumeTask = processingConsumeTasks.remove(0);
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
    @Override
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

    public static final class ConsumeTask<R extends Data> extends AbstractTask {

        private static final Logger LOGGER = LoggerFactory.getLogger(ConsumeTask.class);

        private final ConsumeBuffer<R> consumeBuffer;
        private final Consumer<R> consumer;
        private final AtomicBoolean runningFlag = new AtomicBoolean(true);

        public ConsumeTask(ConsumeBuffer<R> consumeBuffer, Consumer<R> consumer) {
            this.consumeBuffer = consumeBuffer;
            this.consumer = consumer;
        }

        @Override
        protected void todo() {
            while (runningFlag.get()) {
                List<R> pollList = null;
                try {
                    pollList = consumeBuffer.poll();
                    if (!pollList.isEmpty()) {
                        consumer.consume(pollList);
                    }
                } catch (Exception e) {
                    if (Objects.nonNull(pollList)) {
                        LOGGER.warn("消费元素时发生异常, 最多抛弃 {} 个元素", pollList.size(), e);
                    }
                }
            }
            LOGGER.info("消费线程退出...");
        }

        public void shutdown() {
            runningFlag.set(false);
        }
    }

    public static class ConsumeBuffer<R extends Data> {

        private final Lock lock = new ReentrantLock();
        private final Condition provideCondition = lock.newCondition();
        private final Condition consumeCondition = lock.newCondition();
        private final List<R> buffer = new ArrayList<>();

        private int bufferSize;
        private int batchSize;
        private long maxIdleTime;

        private boolean blockEnabled = true;

        private long lastIdleCheckDate = System.currentTimeMillis();

        public void accept(R data) {
            lock.lock();
            try {
                while (buffer.size() >= bufferSize) {
                    provideCondition.awaitUninterruptibly();
                }

                buffer.add(data);
                consumeCondition.signalAll();
            } finally {
                lock.unlock();
            }
        }

        public List<R> poll() {
            long currentTimeMillis = System.currentTimeMillis();
            long timeOffset = 0;
            lock.lock();
            try {
                /*
                 * 线程阻塞的逻辑。
                 *   最终的效果是达到如下的目的。
                 *     [最大空闲时间] 或者 [批处理] 这两个参数有一个小于等于0，意思是只要 [buffer] 中有至少一个元素，
                 *     就立即处理。
                 *     除此之外([最大空闲时间] 和 [批处理] 两个参数都大于 0)
                 *       当[buffer]的数量小于[批处理] 的数且([当前时间] - [最近检查日期] < [最大空闲时间])时，一直阻塞。
                 *     以上条件发生的前提是 [runningFlag] 必须为 true，一旦 [runningFlag] 为 false，则其余参数为任何值都
                 *     不能够阻塞。
                 * 以下三个条件同时满足时，阻塞线程，否则不阻塞:
                 *   1. [buffer] 中没有任何元素，或者 [批处理] 大于 0，
                 *      或者 ([批处理] 大于 0 并且 [buffer] 中的元素小于 [批处理])。
                 *      解释: [buffer] 中没有任何元素，必须要阻塞。
                 *            ([批处理] 大于 0 并且 [buffer] 中的元素小于 [批处理]) 子句中，[批处理] 大于 0 的意思是
                 *            只有 [批处理] 启用，才考虑后续条件，因为基于 buffer.isEmpty() 为 false，
                 *            此时 [buffer] 中至少有一个元素，[批处理] > 0 意味着 [批处理] 启用，
                 *            只有 [批处理] 启用才需要考虑 [buffer] 中元素的数量与 [批处理] 数量的关系，
                 *            否则不应该考虑 [批处理] 的数量，子句直接返回 false。
                 *            因此 [批处理] > 0 时，子句中的短路与逻辑的结果取决于后续条件，
                 *            [批处理] <= 0，即 [批处理] > 0 为 false 时，子句直接返回 false，即不阻塞，使上述逻辑成立。
                 *            如果 [批处理] 启用，就必须考虑 [buffer] 中的元素数量是否小于 [批处理] 的数量，
                 *            如果 [buffer] 中的元素数量小于 [批处理] 的数量，就必须阻塞，反之必须不阻塞。
                 *            [buffer] 中的元素数量 < [批处理] 时，子句返回 true，即可能阻塞，
                 *            [buffer] 中的元素数量 >= [批处理]，即 [buffer] 中的元素数量 < [批处理] 为 false 时，
                 *            子句返回 false，即不阻塞，使上述逻辑成立。
                 *   2. ([最大空闲时间] <= 0 且 [buffer] 中没有任何元素) 或者 ([时间偏移] > 0)。
                 *      解释: ([最大空闲时间] <= 0 且 [buffer] 中没有任何元素) 子句中，[最大空闲时间] <= 0 的意思是
                 *            [最大空闲时间] 不启用，当 [最大空闲时间] 不启用时，只要 [buffer] 中有任何元素，就不应该阻塞，
                 *            因此 [最大空闲时间] <= 0 时，子句的结果取决于 buffer.isEmpty() 的值，
                 *            buffer.isEmpty() 为 true 时，子句返回 true，即可能阻塞，
                 *            buffer.isEmpty() 为 false 时，子句返回 false，即不阻塞，使上述逻辑成立。
                 *            ([时间偏移] > 0) 子句中，[时间偏移] 的定义如下:
                 *            [时间偏移] = [最大空闲时间] - [当前时间] + [最近检查日期]，
                 *            该值指代当前的时间距离下一个最大等待时间点的时间差，
                 *            [时间偏移] > 0 意味着当前时间还没有达到下一个最大等待时间点，必须阻塞，
                 *            [时间偏移] <= 0 意味着当前时间已经达到了下一个最大等待时间点，不应该阻塞。
                 *            [时间偏移] > 0 时子句返回 true，即可能阻塞，
                 *            [时间偏移] <= 0，即 [时间偏移] > 0 为 false 时，子句返回 false，即不阻塞，使上述逻辑成立。
                 *   3. [blockEnabled] 必须为 true。
                 *      解释: [blockEnabled] 为 true 时，子句返回 true，即可能阻塞，
                 *            [blockEnabled] 为 false 时，子句返回 false，即不阻塞，使上述逻辑成立。
                 */
                while ((buffer.isEmpty() || (batchSize > 0 && buffer.size() < batchSize))
                        // 注意: 以下两行是一句
                        && (maxIdleTime <= 0 && buffer.isEmpty() || maxIdleTime > 0
                        && (timeOffset = maxIdleTime - currentTimeMillis + lastIdleCheckDate) > 0)
                        // 注意: 以上两行是一句
                        && blockEnabled
                ) {
                    try {
                        if (batchSize <= 1 || maxIdleTime <= 0) {
                            consumeCondition.await();
                        } else {
                            // 对阻塞是否到达指定时间的判断在外层 while(...) 中完成，不需要此处的结果参与判断。
                            @SuppressWarnings("unused")
                            boolean timeElapsed = consumeCondition.await(timeOffset, TimeUnit.MILLISECONDS);
                        }
                    } catch (InterruptedException ignored) {
                    }
                    currentTimeMillis = System.currentTimeMillis();
                }

                // 更新最新空闲检查时间。
                lastIdleCheckDate = currentTimeMillis;
                // 取出最多 [批处理] 个数个元素，如果 [buffer] 中的元素没有这么多，则全部取出。
                int processingElementSize = Math.min(batchSize, buffer.size());
                List<R> subList = buffer.subList(0, processingElementSize);
                List<R> elements2Return = new ArrayList<>(subList);
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

        public int getBufferSize() {
            lock.lock();
            try {
                return bufferSize;
            } finally {
                lock.unlock();
            }
        }

        public int getBatchSize() {
            lock.lock();
            try {
                return batchSize;
            } finally {
                lock.unlock();
            }
        }

        public long getMaxIdleTime() {
            lock.lock();
            try {
                return maxIdleTime;
            } finally {
                lock.unlock();
            }
        }

        public void setBufferParameters(int bufferSize, int batchSize, long maxIdleTime) {
            lock.lock();
            try {
                this.bufferSize = Math.max(bufferSize, 1);
                this.batchSize = batchSize;
                this.maxIdleTime = maxIdleTime;

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
}
