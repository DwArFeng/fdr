package com.dwarfeng.fdr.impl.handler.pusher;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dwarfeng.fdr.sdk.bean.dto.FastJsonFilteredData;
import com.dwarfeng.fdr.sdk.bean.dto.FastJsonNormalData;
import com.dwarfeng.fdr.sdk.bean.dto.FastJsonTriggeredData;
import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.fdr.stack.bean.dto.NormalData;
import com.dwarfeng.fdr.stack.bean.dto.TriggeredData;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.transaction.KafkaTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 本地 Kafka 推送器。
 *
 * @author DwArFeng
 * @since 1.4.0
 */
@Component
public class NativeKafkaPusher extends AbstractPusher {

    public static final String PUSHER_TYPE = "native.kafka";

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${pusher.native.kafka.topic.normal_updated}")
    private String normalUpdatedTopic;
    @Value("${pusher.native.kafka.topic.normal_recorded}")
    private String normalRecordedTopic;
    @Value("${pusher.native.kafka.topic.filtered_updated}")
    private String filteredUpdatedTopic;
    @Value("${pusher.native.kafka.topic.filtered_recorded}")
    private String filteredRecordedTopic;
    @Value("${pusher.native.kafka.topic.triggered_updated}")
    private String triggeredUpdatedTopic;
    @Value("${pusher.native.kafka.topic.triggered_recorded}")
    private String triggeredRecordedTopic;
    @Value("${pusher.native.kafka.topic.record_reset}")
    private String recordResetTopic;
    @Value("${pusher.native.kafka.topic.map_reset}")
    private String mapResetTopic;

    public NativeKafkaPusher(
            @Qualifier("nativeKafkaPusher.kafkaTemplate") KafkaTemplate<String, String> kafkaTemplate
    ) {
        super(PUSHER_TYPE);
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional(transactionManager = "nativeKafkaPusher.kafkaTransactionManager")
    @Override
    public void normalUpdated(NormalData normalRecord) {
        String message = JSON.toJSONString(FastJsonNormalData.of(normalRecord), SerializerFeature.WriteClassName);
        kafkaTemplate.send(normalUpdatedTopic, message);
    }

    @Transactional(transactionManager = "nativeKafkaPusher.kafkaTransactionManager")
    @Override
    public void normalUpdated(List<NormalData> normalRecords) {
        normalRecords.forEach(this::normalUpdated);
    }

    @Transactional(transactionManager = "nativeKafkaPusher.kafkaTransactionManager")
    @Override
    public void normalRecorded(NormalData normalRecord) {
        String message = JSON.toJSONString(FastJsonNormalData.of(normalRecord), SerializerFeature.WriteClassName);
        kafkaTemplate.send(normalRecordedTopic, message);
    }

    @Transactional(transactionManager = "nativeKafkaPusher.kafkaTransactionManager")
    @Override
    public void normalRecorded(List<NormalData> normalRecords) {
        normalRecords.forEach(this::normalRecorded);
    }

    @Transactional(transactionManager = "nativeKafkaPusher.kafkaTransactionManager")
    @Override
    public void filteredUpdated(FilteredData filteredRecord) {
        String message = JSON.toJSONString(FastJsonFilteredData.of(filteredRecord), SerializerFeature.WriteClassName);
        kafkaTemplate.send(filteredUpdatedTopic, message);
    }

    @Transactional(transactionManager = "nativeKafkaPusher.kafkaTransactionManager")
    @Override
    public void filteredUpdated(List<FilteredData> filteredRecords) {
        filteredRecords.forEach(this::filteredUpdated);
    }

    @Transactional(transactionManager = "nativeKafkaPusher.kafkaTransactionManager")
    @Override
    public void filteredRecorded(FilteredData filteredRecord) {
        String message = JSON.toJSONString(FastJsonFilteredData.of(filteredRecord), SerializerFeature.WriteClassName);
        kafkaTemplate.send(filteredRecordedTopic, message);
    }

    @Transactional(transactionManager = "nativeKafkaPusher.kafkaTransactionManager")
    @Override
    public void filteredRecorded(List<FilteredData> filteredRecords) {

    }

    @Transactional(transactionManager = "nativeKafkaPusher.kafkaTransactionManager")
    @Override
    public void triggeredUpdated(TriggeredData triggeredRecord) {
        String message = JSON.toJSONString(FastJsonTriggeredData.of(triggeredRecord), SerializerFeature.WriteClassName);
        kafkaTemplate.send(triggeredUpdatedTopic, message);
    }

    @Transactional(transactionManager = "nativeKafkaPusher.kafkaTransactionManager")
    @Override
    public void triggeredUpdated(List<TriggeredData> triggeredRecords) {
        triggeredRecords.forEach(this::triggeredUpdated);
    }

    @Transactional(transactionManager = "nativeKafkaPusher.kafkaTransactionManager")
    @Override
    public void triggeredRecorded(TriggeredData triggeredRecord) {
        String message = JSON.toJSONString(FastJsonTriggeredData.of(triggeredRecord), SerializerFeature.WriteClassName);
        kafkaTemplate.send(triggeredRecordedTopic, message);
    }

    @Transactional(transactionManager = "nativeKafkaPusher.kafkaTransactionManager")
    @Override
    public void triggeredRecorded(List<TriggeredData> triggeredRecords) {
        triggeredRecords.forEach(this::triggeredRecorded);
    }

    @Transactional(transactionManager = "nativeKafkaPusher.kafkaTransactionManager")
    @Override
    public void recordReset() {
        kafkaTemplate.send(recordResetTopic, StringUtils.EMPTY);
    }

    @Transactional(transactionManager = "nativeKafkaPusher.kafkaTransactionManager")
    @Override
    public void mapReset() {
        kafkaTemplate.send(mapResetTopic, StringUtils.EMPTY);
    }

    @Override
    public String toString() {
        return "NativeKafkaPusher{" +
                "kafkaTemplate=" + kafkaTemplate +
                ", normalUpdatedTopic='" + normalUpdatedTopic + '\'' +
                ", normalRecordedTopic='" + normalRecordedTopic + '\'' +
                ", filteredUpdatedTopic='" + filteredUpdatedTopic + '\'' +
                ", filteredRecordedTopic='" + filteredRecordedTopic + '\'' +
                ", triggeredUpdatedTopic='" + triggeredUpdatedTopic + '\'' +
                ", triggeredRecordedTopic='" + triggeredRecordedTopic + '\'' +
                ", recordResetTopic='" + recordResetTopic + '\'' +
                ", mapResetTopic='" + mapResetTopic + '\'' +
                '}';
    }

    @Configuration
    public static class KafkaPusherConfiguration {

        private static final Logger LOGGER = LoggerFactory.getLogger(KafkaPusherConfiguration.class);

        @Value("${pusher.native.kafka.bootstrap_servers}")
        private String producerBootstrapServers;
        @Value("${pusher.native.kafka.retries}")
        private int retries;
        @Value("${pusher.native.kafka.linger}")
        private long linger;
        @Value("${pusher.native.kafka.buffer_memory}")
        private long bufferMemory;
        @Value("${pusher.native.kafka.batch_size}")
        private int batchSize;
        @Value("${pusher.native.kafka.acks}")
        private String acks;
        @Value("${pusher.native.kafka.transaction_prefix}")
        private String transactionPrefix;

        @Bean("nativeKafkaPusher.producerProperties")
        public Map<String, Object> producerProperties() {
            LOGGER.info("配置Kafka生产者属性...");
            Map<String, Object> props = new HashMap<>();
            props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, producerBootstrapServers);
            props.put(ProducerConfig.RETRIES_CONFIG, retries);
            props.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
            props.put(ProducerConfig.LINGER_MS_CONFIG, linger);
            props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
            props.put(ProducerConfig.ACKS_CONFIG, acks);
            LOGGER.debug("Kafka生产者属性配置完成...");
            return props;
        }

        @Bean("nativeKafkaPusher.producerFactory")
        public ProducerFactory<String, String> producerFactory() {
            LOGGER.info("配置Kafka生产者工厂...");
            Map<String, Object> properties = producerProperties();
            DefaultKafkaProducerFactory<String, String> factory = new DefaultKafkaProducerFactory<>(properties);
            factory.setTransactionIdPrefix(transactionPrefix);
            factory.setKeySerializer(new StringSerializer());
            factory.setValueSerializer(new StringSerializer());
            LOGGER.debug("Kafka生产者工厂配置完成");
            return factory;
        }

        @Bean("nativeKafkaPusher.kafkaTemplate")
        public KafkaTemplate<String, String> kafkaTemplate() {
            LOGGER.info("生成KafkaTemplate...");
            ProducerFactory<String, String> producerFactory = producerFactory();
            KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(producerFactory, true);
            LOGGER.debug("KafkaTemplate生成完成...");
            return kafkaTemplate;
        }

        @Bean("nativeKafkaPusher.kafkaTransactionManager")
        public KafkaTransactionManager<String, String> kafkaTransactionManager() {
            LOGGER.info("生成KafkaTransactionManager...");
            ProducerFactory<String, String> producerFactory = producerFactory();
            LOGGER.debug("KafkaTransactionManager生成完成...");
            return new KafkaTransactionManager<>(producerFactory);
        }
    }
}
