package com.dwarfeng.fdr.impl.handler.fetcher.kafka.dcti;

import com.dwarfeng.dcti.sdk.util.DataInfoUtil;
import com.dwarfeng.dcti.stack.bean.dto.DataInfo;
import com.dwarfeng.fdr.sdk.handler.fetcher.AbstractFetcherSession;
import com.dwarfeng.fdr.stack.bean.dto.RecordInfo;
import com.dwarfeng.fdr.stack.exception.RecordHandlerStoppedException;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.listener.BatchAcknowledgingConsumerAwareMessageListener;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 基于 dcti 协议的 Kafka 抓取器会话。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DctiKafkaFetcherSession extends AbstractFetcherSession {

    private static final Logger LOGGER = LoggerFactory.getLogger(DctiKafkaFetcherSession.class);

    private final ApplicationContext ctx;

    private final DctiKafkaFetcherConfig config;

    private ConcurrentMessageListenerContainer<String, String> listenerContainer;

    public DctiKafkaFetcherSession(ApplicationContext ctx, DctiKafkaFetcherConfig config) {
        this.ctx = ctx;
        this.config = config;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected void doOpenSession() {
        LOGGER.info("dcti kafka 抓取器会话打开, listenerId={}, topic={}...", config.getListenerId(), config.getTopic());
        @SuppressWarnings("unchecked")
        KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> containerFactory =
                ctx.getBean(config.getKafkaListenerContainerFactoryBeanName(), KafkaListenerContainerFactory.class);
        listenerContainer = containerFactory.createContainer(config.getTopic());
        listenerContainer.getContainerProperties().setGroupId(config.getListenerId());
        listenerContainer.setBeanName(config.getListenerId());
        listenerContainer.setupMessageListener(
                (BatchAcknowledgingConsumerAwareMessageListener<String, String>) this::handleConsumerRecordsPolled
        );
        listenerContainer.setAutoStartup(false);
    }

    @Override
    protected void doStartFetch() {
        LOGGER.info("dcti kafka 抓取器开始抓取, listenerId={}, topic={}...", config.getListenerId(), config.getTopic());
        if (!listenerContainer.isRunning()) {
            listenerContainer.start();
        }
        if (listenerContainer.isPauseRequested()) {
            listenerContainer.resume();
        }
    }

    @Override
    protected void doStopFetch() {
        LOGGER.info("dcti kafka 抓取器停止抓取, listenerId={}, topic={}...", config.getListenerId(), config.getTopic());
        listenerContainer.stop();
    }

    @Override
    protected void doCloseSession() {
        LOGGER.info("dcti kafka 抓取器会话关闭, listenerId={}, topic={}...", config.getListenerId(), config.getTopic());
        listenerContainer.stop();
        listenerContainer = null;
    }

    private void handleConsumerRecordsPolled(
            List<ConsumerRecord<String, String>> consumerRecords, Acknowledgment ack, Consumer<?, ?> consumer
    ) {
        @SuppressWarnings("unchecked")
        Consumer<String, String> typedConsumer = (Consumer<String, String>) consumer;
        for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
            String message = consumerRecord.value();
            try {
                DataInfo dataInfo = DataInfoUtil.fromMessage(message);
                RecordInfo recordInfo = new RecordInfo(
                        new LongIdKey(dataInfo.getPointLongId()),
                        dataInfo.getValue(),
                        dataInfo.getHappenedDate(),
                        dataInfo.getHappenedDateNanoOffset()
                );
                context.record(recordInfo);
            } catch (RecordHandlerStoppedException e) {
                LOGGER.warn("记录处理器被禁用, 消息 {} 以及其后同一批次的消息均不会被提交", message, e);
                typedConsumer.seek(
                        new TopicPartition(consumerRecord.topic(), consumerRecord.partition()),
                        consumerRecord.offset()
                );
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
        return "DctiKafkaFetcherSession{" +
                "ctx=" + ctx +
                ", config=" + config +
                ", listenerContainer=" + listenerContainer +
                ", context=" + context +
                '}';
    }
}
