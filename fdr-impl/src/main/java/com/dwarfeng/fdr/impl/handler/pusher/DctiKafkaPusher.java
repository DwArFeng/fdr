package com.dwarfeng.fdr.impl.handler.pusher;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.dcti.sdk.util.DataInfoUtil;
import com.dwarfeng.dcti.stack.bean.dto.DataInfo;
import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.bean.entity.RealtimeValue;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 标准数据接口 Kafka 推送器。
 *
 * @author DwArFeng
 * @since 1.5.0
 */
@Component
public class DctiKafkaPusher extends AbstractPusher {

    public static final String PUSHER_TYPE = "dcti.kafka";

    @Autowired
    @Qualifier("dctiKafkaPusher.kafkaTemplate")
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${pusher.dcti.kafka.topic.data_filtered}")
    private String dataFilteredTopic;
    @Value("${pusher.dcti.kafka.topic.data_triggered}")
    private String dataTriggeredTopic;
    @Value("${pusher.dcti.kafka.topic.realtime_updated}")
    private String realtimeUpdatedTopic;
    @Value("${pusher.dcti.kafka.topic.persistence_recorded}")
    private String persistenceRecordedTopic;

    public DctiKafkaPusher() {
        super(PUSHER_TYPE);
    }

    @Override
    @Transactional(transactionManager = "dctiKafkaPusher.kafkaTransactionManager")
    public void dataFiltered(FilteredValue filteredValue) {
        MessagedValue messagedValue = new MessagedValue(filteredValue.getValue(), filteredValue.getMessage());
        String value = JSON.toJSONString(messagedValue);
        DataInfo dataInfo = new DataInfo(
                filteredValue.getPointKey().getLongId(), value, filteredValue.getHappenedDate());
        kafkaTemplate.send(dataFilteredTopic, DataInfoUtil.toMessage(dataInfo));
    }

    @Override
    @Transactional(transactionManager = "dctiKafkaPusher.kafkaTransactionManager")
    public void dataFiltered(List<FilteredValue> filteredValues) {
        filteredValues.forEach(this::dataFiltered);
    }

    @Override
    @Transactional(transactionManager = "dctiKafkaPusher.kafkaTransactionManager")
    public void dataTriggered(TriggeredValue triggeredValue) {
        MessagedValue messagedValue = new MessagedValue(triggeredValue.getValue(), triggeredValue.getMessage());
        String value = JSON.toJSONString(messagedValue);
        DataInfo dataInfo = new DataInfo(
                triggeredValue.getPointKey().getLongId(), value, triggeredValue.getHappenedDate());
        kafkaTemplate.send(dataFilteredTopic, DataInfoUtil.toMessage(dataInfo));
    }

    @Override
    @Transactional(transactionManager = "dctiKafkaPusher.kafkaTransactionManager")
    public void dataTriggered(List<TriggeredValue> triggeredValues) {
        triggeredValues.forEach(this::dataTriggered);
    }

    @Override
    @Transactional(transactionManager = "dctiKafkaPusher.kafkaTransactionManager")
    public void realtimeUpdated(RealtimeValue realtimeValue) {
        DataInfo dataInfo = new DataInfo(
                realtimeValue.getKey().getLongId(), realtimeValue.getValue(), realtimeValue.getHappenedDate());
        kafkaTemplate.send(realtimeUpdatedTopic, DataInfoUtil.toMessage(dataInfo));
    }

    @Override
    @Transactional(transactionManager = "dctiKafkaPusher.kafkaTransactionManager")
    public void realtimeUpdated(List<RealtimeValue> realtimeValues) {
        realtimeValues.forEach(this::realtimeUpdated);
    }

    @Override
    @Transactional(transactionManager = "dctiKafkaPusher.kafkaTransactionManager")
    public void persistenceRecorded(PersistenceValue persistenceValue) {
        DataInfo dataInfo = new DataInfo(
                persistenceValue.getKey().getLongId(), persistenceValue.getValue(), persistenceValue.getHappenedDate());
        kafkaTemplate.send(persistenceRecordedTopic, DataInfoUtil.toMessage(dataInfo));
    }

    @Override
    @Transactional(transactionManager = "dctiKafkaPusher.kafkaTransactionManager")
    public void persistenceRecorded(List<PersistenceValue> persistenceValues) {
        persistenceValues.forEach(this::persistenceRecorded);
    }

    @Override
    public void recordReset() {
    }

    @Override
    public void mapReset() {
    }

    @Override
    public String toString() {
        return "DctiKafkaPusher{" +
                "kafkaTemplate=" + kafkaTemplate +
                ", dataFilteredTopic='" + dataFilteredTopic + '\'' +
                ", dataTriggeredTopic='" + dataTriggeredTopic + '\'' +
                ", realtimeUpdatedTopic='" + realtimeUpdatedTopic + '\'' +
                ", persistenceRecordedTopic='" + persistenceRecordedTopic + '\'' +
                ", pusherType='" + pusherType + '\'' +
                '}';
    }

    @Configuration
    public static class KafkaPusherConfiguration {

        private static final Logger LOGGER = LoggerFactory.getLogger(KafkaPusherConfiguration.class);

        @Value("${pusher.dcti.kafka.bootstrap_servers}")
        private String producerBootstrapServers;
        @Value("${pusher.dcti.kafka.retries}")
        private int retries;
        @Value("${pusher.dcti.kafka.linger}")
        private long linger;
        @Value("${pusher.dcti.kafka.buffer_memory}")
        private long bufferMemory;
        @Value("${pusher.dcti.kafka.batch_size}")
        private int batchSize;
        @Value("${pusher.dcti.kafka.acks}")
        private String acks;
        @Value("${pusher.dcti.kafka.transaction_prefix}")
        private String transactionPrefix;

        @SuppressWarnings("DuplicatedCode")
        @Bean("dctiKafkaPusher.producerProperties")
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

        @SuppressWarnings("DuplicatedCode")
        @Bean("dctiKafkaPusher.producerFactory")
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

        @Bean("dctiKafkaPusher.kafkaTemplate")
        public KafkaTemplate<String, String> kafkaTemplate() {
            LOGGER.info("生成KafkaTemplate...");
            ProducerFactory<String, String> producerFactory = producerFactory();
            KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(producerFactory, true);
            LOGGER.debug("KafkaTemplate生成完成...");
            return kafkaTemplate;
        }

        @Bean("dctiKafkaPusher.kafkaTransactionManager")
        public KafkaTransactionManager<String, String> kafkaTransactionManager() {
            LOGGER.info("生成KafkaTransactionManager...");
            ProducerFactory<String, String> producerFactory = producerFactory();
            LOGGER.debug("KafkaTransactionManager生成完成...");
            return new KafkaTransactionManager<>(producerFactory);
        }
    }

    private static class MessagedValue implements Dto {

        private static final long serialVersionUID = -8117329938652795344L;

        @JSONField(name = "value", ordinal = 1)
        private String value;
        @JSONField(name = "massage", ordinal = 2)
        private String message;

        // 该类需要符合 Bean 规范，必须要指定无参数构造函数。
        @SuppressWarnings("unused")
        public MessagedValue() {
        }

        public MessagedValue(String value, String message) {
            this.value = value;
            this.message = message;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return "MessagedValue{" +
                    "value='" + value + '\'' +
                    ", message='" + message + '\'' +
                    '}';
        }
    }
}
