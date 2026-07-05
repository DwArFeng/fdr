package com.dwarfeng.fdr.impl.handler.bridge.influxdb.configuration;

import com.influxdb.client.*;
import io.reactivex.rxjava3.core.BackpressureOverflowStrategy;
import io.reactivex.rxjava3.internal.schedulers.ExecutorScheduler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class InfluxdbBridgeBaseConfiguration {

    private final ThreadPoolTaskExecutor executor;

    @Value("${com.dwarfeng.fdr.bridge.influxdb.url}")
    private String url;
    @Value("${com.dwarfeng.fdr.bridge.influxdb.token}")
    private String token;

    /**
     * 单批写入的数据点条数上限。
     *
     * @since 3.0.1
     */
    @Value("${com.dwarfeng.fdr.bridge.influxdb.write.batch_size}")
    private int writeBatchSize;

    /**
     * 批写入最长等待时间（毫秒）。
     *
     * @since 3.0.1
     */
    @Value("${com.dwarfeng.fdr.bridge.influxdb.write.flush_interval_ms}")
    private int writeFlushIntervalMs;

    /**
     * 刷新间隔抖动（毫秒）。
     *
     * @since 3.0.1
     */
    @Value("${com.dwarfeng.fdr.bridge.influxdb.write.jitter_interval_ms}")
    private int writeJitterIntervalMs;

    /**
     * 写入失败时的重试间隔（毫秒）。
     *
     * @since 3.0.1
     */
    @Value("${com.dwarfeng.fdr.bridge.influxdb.write.retry_interval_ms}")
    private int writeRetryIntervalMs;

    /**
     * 写入失败时的最大重试次数。
     *
     * @since 3.0.1
     */
    @Value("${com.dwarfeng.fdr.bridge.influxdb.write.max_retries}")
    private int writeMaxRetries;

    /**
     * 单次重试之间的最大延迟（毫秒）。
     *
     * @since 3.0.1
     */
    @Value("${com.dwarfeng.fdr.bridge.influxdb.write.max_retry_delay_ms}")
    private int writeMaxRetryDelayMs;

    /**
     * 重试过程的总超时上限（毫秒）。
     *
     * @since 3.0.1
     */
    @Value("${com.dwarfeng.fdr.bridge.influxdb.write.max_retry_time_ms}")
    private int writeMaxRetryTimeMs;

    /**
     * 指数退避的底数。
     *
     * @since 3.0.1
     */
    @Value("${com.dwarfeng.fdr.bridge.influxdb.write.exponential_base}")
    private int writeExponentialBase;

    /**
     * 写入缓冲上限（数据点条数）。
     *
     * @since 3.0.1
     */
    @Value("${com.dwarfeng.fdr.bridge.influxdb.write.buffer_limit}")
    private int writeBufferLimit;

    /**
     * 缓冲溢出时的背压策略，取值见 {@link BackpressureOverflowStrategy} 枚举名。
     *
     * @since 3.0.1
     */
    @Value("${com.dwarfeng.fdr.bridge.influxdb.write.backpressure_overflow_strategy}")
    private String writeBackpressureOverflowStrategy;

    public InfluxdbBridgeBaseConfiguration(ThreadPoolTaskExecutor executor) {
        this.executor = executor;
    }

    @Bean(name = "influxdbBridge.influxDBClient", destroyMethod = "close")
    public InfluxDBClient influxDBClient() {
        return InfluxDBClientFactory.create(url, token.toCharArray());
    }

    @Bean(name = "influxdbBridge.writeApi", destroyMethod = "close")
    public WriteApi writeApi(
            @Qualifier("influxdbBridge.influxDBClient") InfluxDBClient influxDBClient

    ) {
        BackpressureOverflowStrategy backpressureOverflowStrategy = BackpressureOverflowStrategy.valueOf(
                writeBackpressureOverflowStrategy.trim()
        );
        WriteOptions writeOptions = WriteOptions.builder()
                .batchSize(writeBatchSize)
                .flushInterval(writeFlushIntervalMs)
                .jitterInterval(writeJitterIntervalMs)
                .retryInterval(writeRetryIntervalMs)
                .maxRetries(writeMaxRetries)
                .maxRetryDelay(writeMaxRetryDelayMs)
                .maxRetryTime(writeMaxRetryTimeMs)
                .exponentialBase(writeExponentialBase)
                .bufferLimit(writeBufferLimit)
                .backpressureStrategy(backpressureOverflowStrategy)
                .writeScheduler(new ExecutorScheduler(executor, true, true))
                .build();
        return influxDBClient.makeWriteApi(writeOptions);
    }

    @Bean(name = "influxdbBridge.queryApi")
    public QueryApi queryApi(
            @Qualifier("influxdbBridge.influxDBClient") InfluxDBClient influxDBClient

    ) {
        return influxDBClient.getQueryApi();
    }
}
