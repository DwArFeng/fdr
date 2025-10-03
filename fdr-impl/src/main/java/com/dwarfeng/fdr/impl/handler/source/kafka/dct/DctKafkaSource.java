package com.dwarfeng.fdr.impl.handler.source.kafka.dct;

import com.dwarfeng.dct.handler.DataCodingHandler;
import com.dwarfeng.fdr.sdk.handler.source.AbstractSource;
import com.dwarfeng.fdr.stack.bean.dto.RecordInfo;
import com.dwarfeng.fdr.stack.exception.RecordHandlerStoppedException;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * 基于 dct 框架的 kafka 数据源。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class DctKafkaSource extends AbstractSource {

    private static final Logger LOGGER = LoggerFactory.getLogger(DctKafkaSource.class);

    private final DataCodingHandler dataCodingHandler;

    private final KafkaListenerEndpointRegistry registry;

    @Value("${source.kafka.dct.listener_id}")
    private String listenerId;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public DctKafkaSource(
            @Qualifier("dctKafkaSource.dataCodingHandler")
            DataCodingHandler dataCodingHandler,
            KafkaListenerEndpointRegistry registry
    ) {
        this.dataCodingHandler = dataCodingHandler;
        this.registry = registry;
    }

    @Override
    protected void doOnline() throws Exception {
        LOGGER.info("dct kafka source 上线...");
        MessageListenerContainer listenerContainer = registry.getListenerContainer(listenerId);
        if (Objects.isNull(listenerContainer)) {
            throw new HandlerException("找不到 kafka listener container " + listenerId);
        }
        //判断监听容器是否启动，未启动则将其启动
        if (!listenerContainer.isRunning()) {
            listenerContainer.start();
        }
        listenerContainer.resume();
    }

    @Override
    protected void doOffline() throws Exception {
        LOGGER.info("dct kafka source 下线...");
        MessageListenerContainer listenerContainer = registry.getListenerContainer(listenerId);
        if (Objects.isNull(listenerContainer)) {
            throw new HandlerException("找不到 kafka listener container " + listenerId);
        }
        listenerContainer.pause();
    }

    @KafkaListener(
            id = "${source.kafka.dct.listener_id}",
            containerFactory = "dctKafkaSource.kafkaListenerContainerFactory",
            topics = "${source.kafka.dct.listener_topic}"
    )
    public void handleConsumerRecordsPolled(
            List<ConsumerRecord<String, String>> consumerRecords, Consumer<String, String> consumer, Acknowledgment ack
    ) {
        for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
            String message = consumerRecord.value();
            try {
                com.dwarfeng.dct.struct.Data dctData = dataCodingHandler.decode(message);
                RecordInfo recordInfo = new RecordInfo(
                        dctData.getPointKey(),
                        dctData.getValue(),
                        dctData.getHappenedDate()
                );
                context.record(recordInfo);
            } catch (RecordHandlerStoppedException e) {
                LOGGER.warn("记录处理器被禁用， 消息 {} 以及其后同一批次的消息均不会被提交", message, e);
                // 如果记录处理器被禁用，则放弃其后同一批次的消息记录，并且妥善处理offset的提交。
                // Offset 精确设置到没有提交成功的最后一条信息上。
                consumer.seek(new TopicPartition(consumerRecord.topic(), consumerRecord.partition()),
                        consumerRecord.offset());
                ack.acknowledge();
                return;
            } catch (Exception e) {
                LOGGER.warn("记录处理器无法处理, 消息 {} 将会被忽略", message, e);
            }
        }
        ack.acknowledge();
    }

    @Override
    public String toString() {
        return "DctKafkaSource{" +
                "dataCodingHandler=" + dataCodingHandler +
                ", registry=" + registry +
                ", listenerId='" + listenerId + '\'' +
                ", context=" + context +
                ", onlineFlag=" + onlineFlag +
                '}';
    }
}
