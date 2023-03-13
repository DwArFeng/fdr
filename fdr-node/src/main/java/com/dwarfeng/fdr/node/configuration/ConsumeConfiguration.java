package com.dwarfeng.fdr.node.configuration;

import com.dwarfeng.fdr.impl.handler.ConsumeHandlerImpl;
import com.dwarfeng.fdr.impl.handler.consumer.*;
import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.bean.entity.RealtimeValue;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.fdr.stack.handler.ConsumeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.ArrayList;

@Configuration
public class ConsumeConfiguration {

    @Autowired
    private ThreadPoolTaskExecutor executor;
    @Autowired
    private ThreadPoolTaskScheduler scheduler;
    @Value("${consume.threshold.warn}")
    private double warnThreshold;

    @Autowired
    private FilteredEventConsumer filteredEventConsumer;
    @Value("${consume.filtered_event.consumer_thread}")
    private int filteredEventConsumerThread;
    @Value("${consume.filtered_event.buffer_size}")
    private int filteredEventBufferSize;
    @Value("${consume.filtered_event.batch_size}")
    private int filteredEventBatchSize;
    @Value("${consume.filtered_event.max_idle_time}")
    private long filteredEventMaxIdleTime;

    @Bean
    public ConsumeHandler<FilteredValue> filteredEventConsumeHandler() {
        ConsumeHandlerImpl<FilteredValue> consumeHandler = new ConsumeHandlerImpl<>(
                executor,
                scheduler,
                new ArrayList<>(),
                new ArrayList<>(),
                filteredEventConsumer,
                filteredEventConsumerThread,
                warnThreshold
        );
        consumeHandler.setBufferParameters(filteredEventBufferSize, filteredEventBatchSize, filteredEventMaxIdleTime);
        return consumeHandler;
    }

    @Autowired
    private FilteredValueConsumer filteredValueConsumer;
    @Value("${consume.filtered_value.consumer_thread}")
    private int filteredValueConsumerThread;
    @Value("${consume.filtered_value.buffer_size}")
    private int filteredValueBufferSize;
    @Value("${consume.filtered_value.batch_size}")
    private int filteredValueBatchSize;
    @Value("${consume.filtered_value.max_idle_time}")
    private long filteredValueMaxIdleTime;

    @Bean
    public ConsumeHandler<FilteredValue> filteredValueConsumeHandler() {
        ConsumeHandlerImpl<FilteredValue> consumeHandler = new ConsumeHandlerImpl<>(
                executor,
                scheduler,
                new ArrayList<>(),
                new ArrayList<>(),
                filteredValueConsumer,
                filteredValueConsumerThread,
                warnThreshold
        );
        consumeHandler.setBufferParameters(filteredValueBufferSize, filteredValueBatchSize, filteredValueMaxIdleTime);
        return consumeHandler;
    }

    @Autowired
    private TriggeredEventConsumer triggeredEventConsumer;
    @Value("${consume.triggered_event.consumer_thread}")
    private int triggeredEventConsumerThread;
    @Value("${consume.triggered_event.buffer_size}")
    private int triggeredEventBufferSize;
    @Value("${consume.triggered_event.batch_size}")
    private int triggeredEventBatchSize;
    @Value("${consume.triggered_event.max_idle_time}")
    private long triggeredEventMaxIdleTime;

    @Bean
    public ConsumeHandler<TriggeredValue> triggeredEventConsumeHandler() {
        ConsumeHandlerImpl<TriggeredValue> consumeHandler = new ConsumeHandlerImpl<>(
                executor,
                scheduler,
                new ArrayList<>(),
                new ArrayList<>(),
                triggeredEventConsumer,
                triggeredEventConsumerThread,
                warnThreshold
        );
        consumeHandler.setBufferParameters(triggeredEventBufferSize, triggeredEventBatchSize, triggeredEventMaxIdleTime);
        return consumeHandler;
    }

    @Autowired
    private TriggeredValueConsumer triggeredValueConsumer;
    @Value("${consume.triggered_value.consumer_thread}")
    private int triggeredValueConsumerThread;
    @Value("${consume.triggered_value.buffer_size}")
    private int triggeredValueBufferSize;
    @Value("${consume.triggered_value.batch_size}")
    private int triggeredValueBatchSize;
    @Value("${consume.triggered_value.max_idle_time}")
    private long triggeredValueMaxIdleTime;

    @Bean
    public ConsumeHandler<TriggeredValue> triggeredValueConsumeHandler() {
        ConsumeHandlerImpl<TriggeredValue> consumeHandler = new ConsumeHandlerImpl<>(
                executor,
                scheduler,
                new ArrayList<>(),
                new ArrayList<>(),
                triggeredValueConsumer,
                triggeredValueConsumerThread,
                warnThreshold
        );
        consumeHandler.setBufferParameters(triggeredValueBufferSize, triggeredValueBatchSize, triggeredValueMaxIdleTime);
        return consumeHandler;
    }

    @Autowired
    private RealtimeEventConsumer realtimeEventConsumer;
    @Value("${consume.realtime_event.consumer_thread}")
    private int realtimeEventConsumerThread;
    @Value("${consume.realtime_event.buffer_size}")
    private int realtimeEventBufferSize;
    @Value("${consume.realtime_event.batch_size}")
    private int realtimeEventBatchSize;
    @Value("${consume.realtime_event.max_idle_time}")
    private long realtimeEventMaxIdleTime;

    @Bean
    public ConsumeHandler<RealtimeValue> realtimeEventConsumeHandler() {
        ConsumeHandlerImpl<RealtimeValue> consumeHandler = new ConsumeHandlerImpl<>(
                executor,
                scheduler,
                new ArrayList<>(),
                new ArrayList<>(),
                realtimeEventConsumer,
                realtimeEventConsumerThread,
                warnThreshold
        );
        consumeHandler.setBufferParameters(realtimeEventBufferSize, realtimeEventBatchSize, realtimeEventMaxIdleTime);
        return consumeHandler;
    }

    @Autowired
    private RealtimeValueConsumer realtimeValueConsumer;
    @Value("${consume.realtime_value.consumer_thread}")
    private int realtimeValueConsumerThread;
    @Value("${consume.realtime_value.buffer_size}")
    private int realtimeValueBufferSize;
    @Value("${consume.realtime_value.batch_size}")
    private int realtimeValueBatchSize;
    @Value("${consume.realtime_value.max_idle_time}")
    private long realtimeValueMaxIdleTime;

    @Bean
    public ConsumeHandler<RealtimeValue> realtimeValueConsumeHandler() {
        ConsumeHandlerImpl<RealtimeValue> consumeHandler = new ConsumeHandlerImpl<>(
                executor,
                scheduler,
                new ArrayList<>(),
                new ArrayList<>(),
                realtimeValueConsumer,
                realtimeValueConsumerThread,
                warnThreshold
        );
        consumeHandler.setBufferParameters(realtimeValueBufferSize, realtimeValueBatchSize, realtimeValueMaxIdleTime);
        return consumeHandler;
    }

    @Autowired
    private PersistenceEventConsumer persistenceEventConsumer;
    @Value("${consume.persistence_event.consumer_thread}")
    private int persistenceEventConsumerThread;
    @Value("${consume.persistence_event.buffer_size}")
    private int persistenceEventBufferSize;
    @Value("${consume.persistence_event.batch_size}")
    private int persistenceEventBatchSize;
    @Value("${consume.persistence_event.max_idle_time}")
    private long persistenceEventMaxIdleTime;

    @Bean
    public ConsumeHandler<PersistenceValue> persistenceEventConsumeHandler() {
        ConsumeHandlerImpl<PersistenceValue> consumeHandler = new ConsumeHandlerImpl<>(
                executor,
                scheduler,
                new ArrayList<>(),
                new ArrayList<>(),
                persistenceEventConsumer,
                persistenceEventConsumerThread,
                warnThreshold
        );
        consumeHandler.setBufferParameters(persistenceEventBufferSize, persistenceEventBatchSize, persistenceEventMaxIdleTime);
        return consumeHandler;
    }

    @Autowired
    private PersistenceValueConsumer persistenceValueConsumer;
    @Value("${consume.persistence_value.consumer_thread}")
    private int persistenceValueConsumerThread;
    @Value("${consume.persistence_value.buffer_size}")
    private int persistenceValueBufferSize;
    @Value("${consume.persistence_value.batch_size}")
    private int persistenceValueBatchSize;
    @Value("${consume.persistence_value.max_idle_time}")
    private long persistenceValueMaxIdleTime;

    @Bean
    public ConsumeHandler<PersistenceValue> persistenceValueConsumeHandler() {
        ConsumeHandlerImpl<PersistenceValue> consumeHandler = new ConsumeHandlerImpl<>(
                executor,
                scheduler,
                new ArrayList<>(),
                new ArrayList<>(),
                persistenceValueConsumer,
                persistenceValueConsumerThread,
                warnThreshold
        );
        consumeHandler.setBufferParameters(persistenceValueBufferSize, persistenceValueBatchSize, persistenceValueMaxIdleTime);
        return consumeHandler;
    }
}
