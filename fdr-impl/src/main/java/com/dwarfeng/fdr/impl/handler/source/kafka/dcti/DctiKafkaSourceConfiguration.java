package com.dwarfeng.fdr.impl.handler.source.kafka.dcti;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class DctiKafkaSourceConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(DctiKafkaSourceConfiguration.class);

    @Value("${source.kafka.dcti.bootstrap_servers}")
    private String consumerBootstrapServers;
    @Value("${source.kafka.dcti.session_timeout_ms}")
    private int sessionTimeoutMs;
    @Value("${source.kafka.dcti.auto_offset_reset}")
    private String autoOffsetReset;
    @Value("${source.kafka.dcti.concurrency}")
    private int concurrency;
    @Value("${source.kafka.dcti.poll_timeout}")
    private int pollTimeout;
    @Value("${source.kafka.dcti.max_poll_records}")
    private int maxPollRecords;
    @Value("${source.kafka.dcti.max_poll_interval_ms}")
    private int maxPollIntervalMs;

    @SuppressWarnings("DuplicatedCode")
    @Bean("dctiKafkaSource.consumerProperties")
    public Map<String, Object> consumerProperties() {
        LOGGER.debug("配置Kafka消费者属性...");
        Map<String, Object> props = new HashMap<>();

        // 配置值。
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, consumerBootstrapServers);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeoutMs);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, maxPollIntervalMs);

        // 默认值。
        // 本实例使用ack手动提交，因此禁止自动提交的功能。
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        LOGGER.debug("Kafka消费者属性配置完成...");
        return props;
    }

    @SuppressWarnings("DuplicatedCode")
    @Bean("dctiKafkaSource.consumerFactory")
    public ConsumerFactory<String, String> consumerFactory() {
        LOGGER.debug("配置Kafka消费者工厂...");
        Map<String, Object> properties = consumerProperties();
        DefaultKafkaConsumerFactory<String, String> factory = new DefaultKafkaConsumerFactory<>(properties);
        factory.setKeyDeserializer(new StringDeserializer());
        factory.setValueDeserializer(new StringDeserializer());
        LOGGER.debug("Kafka消费者工厂配置完成");
        return factory;
    }

    @SuppressWarnings("DuplicatedCode")
    @Bean("dctiKafkaSource.kafkaListenerContainerFactory")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>>
    kafkaListenerContainerFactory() {
        LOGGER.info("配置Kafka侦听容器工厂...");
        ConsumerFactory<String, String> consumerFactory = consumerFactory();
        ConcurrentKafkaListenerContainerFactory<String, String> factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setConcurrency(concurrency);
        factory.getContainerProperties().setPollTimeout(pollTimeout);
        // Kafka侦听容器通过框架对开启和关闭进行托管，因此在启动时不自动开启。
        factory.setAutoStartup(false);
        // 监听器启用批量监听模式。
        factory.setBatchListener(true);
        // 配置ACK模式为手动立即提交。
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        LOGGER.info("配置Kafka侦听容器工厂...");
        return factory;
    }
}
