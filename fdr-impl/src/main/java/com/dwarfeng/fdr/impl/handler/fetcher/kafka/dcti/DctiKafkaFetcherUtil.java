package com.dwarfeng.fdr.impl.handler.fetcher.kafka.dcti;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * 基于 dcti 协议的 Kafka 抓取器工具类。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
public final class DctiKafkaFetcherUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DctiKafkaFetcherUtil.class);

    /**
     * 创建 Kafka 消费者工厂。
     *
     * @param bootstrapServers  引导服务器集群。
     * @param sessionTimeoutMs  会话超时时间，单位为毫秒。
     * @param autoOffsetReset   新 group 加入 topic 时的消费起始位置。
     * @param maxPollRecords    单次 poll 的最大记录数。
     * @param maxPollIntervalMs 两次 poll 之间的最大间隔，单位为毫秒。
     * @return Kafka 消费者工厂。
     */
    @SuppressWarnings("DuplicatedCode")
    public static ConsumerFactory<String, String> newConsumerFactory(
            String bootstrapServers, int sessionTimeoutMs, String autoOffsetReset, int maxPollRecords,
            int maxPollIntervalMs
    ) {
        LOGGER.debug("配置 Kafka 消费者属性...");
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeoutMs);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, maxPollIntervalMs);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        LOGGER.debug("Kafka 消费者属性配置完成");
        LOGGER.debug("配置 Kafka 消费者工厂...");
        DefaultKafkaConsumerFactory<String, String> factory = new DefaultKafkaConsumerFactory<>(props);
        factory.setKeyDeserializer(new StringDeserializer());
        factory.setValueDeserializer(new StringDeserializer());
        LOGGER.debug("Kafka 消费者工厂配置完成");
        return factory;
    }

    /**
     * 创建 Kafka 侦听容器工厂。
     *
     * @param consumerFactory 消费者工厂。
     * @param concurrency     侦听容器启用的消费者线程数。
     * @param pollTimeout     poll 超时时间，单位为毫秒。
     * @return Kafka 侦听容器工厂。
     */
    @SuppressWarnings("DuplicatedCode")
    public static KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>>
    newKafkaListenerContainerFactory(
            ConsumerFactory<String, String> consumerFactory, int concurrency, int pollTimeout
    ) {
        LOGGER.debug("配置 Kafka 侦听容器工厂...");
        ConcurrentKafkaListenerContainerFactory<String, String> factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setConcurrency(concurrency);
        factory.getContainerProperties().setPollTimeout(pollTimeout);
        factory.setAutoStartup(false);
        factory.setBatchListener(true);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        LOGGER.debug("Kafka 侦听容器工厂配置完成");
        return factory;
    }

    private DctiKafkaFetcherUtil() {
        throw new IllegalStateException("禁止实例化");
    }
}
