package com.dwarfeng.fdr.impl.configuration;

import com.dwarfeng.fdr.impl.handler.ConsumeHandlerImpl;
import com.dwarfeng.fdr.impl.handler.Consumer;
import com.dwarfeng.fdr.impl.handler.consumer.*;
import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.fdr.stack.bean.dto.NormalData;
import com.dwarfeng.fdr.stack.bean.dto.TriggeredData;
import com.dwarfeng.fdr.stack.handler.ConsumeHandler;
import com.dwarfeng.fdr.stack.handler.KeepHandler;
import com.dwarfeng.fdr.stack.handler.PersistHandler;
import com.dwarfeng.fdr.stack.handler.PushHandler;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.ArrayList;

@Configuration
public class ConsumeConfiguration {

    private final KeepHandler<NormalData> normalKeepHandler;
    private final PersistHandler<NormalData> normalPersistHandler;
    private final KeepHandler<FilteredData> filteredKeepHandler;
    private final PersistHandler<FilteredData> filteredPersistHandler;
    private final KeepHandler<TriggeredData> triggeredKeepHandler;
    private final PersistHandler<TriggeredData> triggeredPersistHandler;
    private final PushHandler pushHandler;

    private final CuratorFramework curatorFramework;

    private final ThreadPoolTaskExecutor executor;
    private final ThreadPoolTaskScheduler scheduler;

    @Value("${consume.threshold.warn}")
    private double warnThreshold;

    @Value("${consume.normal_keep.consumer_thread}")
    private int normalKeepConsumerThread;
    @Value("${consume.normal_keep.buffer_size}")
    private int normalKeepBufferSize;
    @Value("${consume.normal_keep.batch_size}")
    private int normalKeepBatchSize;
    @Value("${consume.normal_keep.max_idle_time}")
    private long normalKeepMaxIdleTime;

    @Value("${consume.normal_persist.consumer_thread}")
    private int normalPersistConsumerThread;
    @Value("${consume.normal_persist.buffer_size}")
    private int normalPersistBufferSize;
    @Value("${consume.normal_persist.batch_size}")
    private int normalPersistBatchSize;
    @Value("${consume.normal_persist.max_idle_time}")
    private long normalPersistMaxIdleTime;

    @Value("${consume.filtered_keep.consumer_thread}")
    private int filteredKeepConsumerThread;
    @Value("${consume.filtered_keep.buffer_size}")
    private int filteredKeepBufferSize;
    @Value("${consume.filtered_keep.batch_size}")
    private int filteredKeepBatchSize;
    @Value("${consume.filtered_keep.max_idle_time}")
    private long filteredKeepMaxIdleTime;

    @Value("${consume.filtered_persist.consumer_thread}")
    private int filteredPersistConsumerThread;
    @Value("${consume.filtered_persist.buffer_size}")
    private int filteredPersistBufferSize;
    @Value("${consume.filtered_persist.batch_size}")
    private int filteredPersistBatchSize;
    @Value("${consume.filtered_persist.max_idle_time}")
    private long filteredPersistMaxIdleTime;

    @Value("${consume.triggered_keep.consumer_thread}")
    private int triggeredKeepConsumerThread;
    @Value("${consume.triggered_keep.buffer_size}")
    private int triggeredKeepBufferSize;
    @Value("${consume.triggered_keep.batch_size}")
    private int triggeredKeepBatchSize;
    @Value("${consume.triggered_keep.max_idle_time}")
    private long triggeredKeepMaxIdleTime;

    @Value("${consume.triggered_persist.consumer_thread}")
    private int triggeredPersistConsumerThread;
    @Value("${consume.triggered_persist.buffer_size}")
    private int triggeredPersistBufferSize;
    @Value("${consume.triggered_persist.batch_size}")
    private int triggeredPersistBatchSize;
    @Value("${consume.triggered_persist.max_idle_time}")
    private long triggeredPersistMaxIdleTime;

    @Value("${curator.inter_process_mutex.keep_consumer.normal}")
    private String normalKeepConsumerInterProcessMutexPath;
    @Value("${curator.inter_process_mutex.keep_consumer.filtered}")
    private String filteredKeepConsumerInterProcessMutexPath;
    @Value("${curator.inter_process_mutex.keep_consumer.triggered}")
    private String triggeredKeepConsumerInterProcessMutexPath;

    public ConsumeConfiguration(
            KeepHandler<NormalData> normalKeepHandler,
            PersistHandler<NormalData> normalPersistHandler,
            KeepHandler<FilteredData> filteredKeepHandler,
            PersistHandler<FilteredData> filteredPersistHandler,
            KeepHandler<TriggeredData> triggeredKeepHandler,
            PersistHandler<TriggeredData> triggeredPersistHandler,
            PushHandler pushHandler,
            CuratorFramework curatorFramework,
            ThreadPoolTaskExecutor executor,
            ThreadPoolTaskScheduler scheduler
    ) {
        this.normalKeepHandler = normalKeepHandler;
        this.normalPersistHandler = normalPersistHandler;
        this.filteredKeepHandler = filteredKeepHandler;
        this.filteredPersistHandler = filteredPersistHandler;
        this.triggeredKeepHandler = triggeredKeepHandler;
        this.triggeredPersistHandler = triggeredPersistHandler;
        this.pushHandler = pushHandler;
        this.curatorFramework = curatorFramework;
        this.executor = executor;
        this.scheduler = scheduler;
    }

    @Bean("normalKeepConsumer")
    public Consumer<NormalData> normalKeepConsumer() {
        return new NormalKeepConsumer(
                normalKeepHandler,
                pushHandler,
                curatorFramework,
                normalKeepConsumerInterProcessMutexPath
        );
    }

    @Bean("normalPersistConsumer")
    public Consumer<NormalData> normalPersistConsumer() {
        return new NormalPersistConsumer(
                normalPersistHandler,
                pushHandler
        );
    }

    @Bean("filteredKeepConsumer")
    public Consumer<FilteredData> filteredKeepConsumer() {
        return new FilteredKeepConsumer(
                filteredKeepHandler,
                pushHandler,
                curatorFramework,
                filteredKeepConsumerInterProcessMutexPath
        );
    }

    @Bean("filteredPersistConsumer")
    public Consumer<FilteredData> filteredPersistConsumer() {
        return new FilteredPersistConsumer(
                filteredPersistHandler,
                pushHandler
        );
    }

    @Bean("triggeredKeepConsumer")
    public Consumer<TriggeredData> triggeredKeepConsumer() {
        return new TriggeredKeepConsumer(
                triggeredKeepHandler,
                pushHandler,
                curatorFramework,
                triggeredKeepConsumerInterProcessMutexPath
        );
    }

    @Bean("triggeredPersistConsumer")
    public Consumer<TriggeredData> triggeredPersistConsumer() {
        return new TriggeredPersistConsumer(
                triggeredPersistHandler,
                pushHandler
        );
    }

    @Bean("normalKeepConsumeHandler")
    public ConsumeHandler<NormalData> normalKeepConsumeHandler(
            @Qualifier("normalKeepConsumer") Consumer<NormalData> normalKeepConsumer
    ) {
        ConsumeHandlerImpl<NormalData> consumeHandler = new ConsumeHandlerImpl<>(
                executor,
                scheduler,
                new ArrayList<>(),
                new ArrayList<>(),
                normalKeepConsumer,
                normalKeepConsumerThread,
                warnThreshold
        );
        consumeHandler.setBufferParameters(normalKeepBufferSize, normalKeepBatchSize, normalKeepMaxIdleTime);
        return consumeHandler;
    }

    @Bean("normalPersistConsumeHandler")
    public ConsumeHandler<NormalData> normalPersistConsumeHandler(
            @Qualifier("normalPersistConsumer") Consumer<NormalData> normalPersistConsumer
    ) {
        ConsumeHandlerImpl<NormalData> consumeHandler = new ConsumeHandlerImpl<>(
                executor,
                scheduler,
                new ArrayList<>(),
                new ArrayList<>(),
                normalPersistConsumer,
                normalPersistConsumerThread,
                warnThreshold
        );
        consumeHandler.setBufferParameters(normalPersistBufferSize, normalPersistBatchSize, normalPersistMaxIdleTime);
        return consumeHandler;
    }

    @Bean("filteredKeepConsumeHandler")
    public ConsumeHandler<FilteredData> filteredKeepConsumeHandler(
            @Qualifier("filteredKeepConsumer") Consumer<FilteredData> filteredKeepConsumer
    ) {
        ConsumeHandlerImpl<FilteredData> consumeHandler = new ConsumeHandlerImpl<>(
                executor,
                scheduler,
                new ArrayList<>(),
                new ArrayList<>(),
                filteredKeepConsumer,
                filteredKeepConsumerThread,
                warnThreshold
        );
        consumeHandler.setBufferParameters(filteredKeepBufferSize, filteredKeepBatchSize, filteredKeepMaxIdleTime);
        return consumeHandler;
    }

    @Bean("filteredPersistConsumeHandler")
    public ConsumeHandler<FilteredData> filteredPersistConsumeHandler(
            @Qualifier("filteredPersistConsumer") Consumer<FilteredData> filteredPersistConsumer
    ) {
        ConsumeHandlerImpl<FilteredData> consumeHandler = new ConsumeHandlerImpl<>(
                executor,
                scheduler,
                new ArrayList<>(),
                new ArrayList<>(),
                filteredPersistConsumer,
                filteredPersistConsumerThread,
                warnThreshold
        );
        consumeHandler.setBufferParameters(
                filteredPersistBufferSize, filteredPersistBatchSize, filteredPersistMaxIdleTime
        );
        return consumeHandler;
    }

    @Bean("triggeredKeepConsumeHandler")
    public ConsumeHandler<TriggeredData> triggeredKeepConsumeHandler(
            @Qualifier("triggeredKeepConsumer") Consumer<TriggeredData> triggeredKeepConsumer
    ) {
        ConsumeHandlerImpl<TriggeredData> consumeHandler = new ConsumeHandlerImpl<>(
                executor,
                scheduler,
                new ArrayList<>(),
                new ArrayList<>(),
                triggeredKeepConsumer,
                triggeredKeepConsumerThread,
                warnThreshold
        );
        consumeHandler.setBufferParameters(triggeredKeepBufferSize, triggeredKeepBatchSize, triggeredKeepMaxIdleTime);
        return consumeHandler;
    }

    @Bean("triggeredPersistConsumeHandler")
    public ConsumeHandler<TriggeredData> triggeredPersistConsumeHandler(
            @Qualifier("triggeredPersistConsumer") Consumer<TriggeredData> triggeredPersistConsumer
    ) {
        ConsumeHandlerImpl<TriggeredData> consumeHandler = new ConsumeHandlerImpl<>(
                executor,
                scheduler,
                new ArrayList<>(),
                new ArrayList<>(),
                triggeredPersistConsumer,
                triggeredPersistConsumerThread,
                warnThreshold
        );
        consumeHandler.setBufferParameters(
                triggeredPersistBufferSize, triggeredPersistBatchSize, triggeredPersistMaxIdleTime
        );
        return consumeHandler;
    }
}
