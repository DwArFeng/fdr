package com.dwarfeng.fdr.impl.handler.fetcher.kafka.dcti;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.subgrade.stack.bean.Bean;

/**
 * 基于 dcti 协议的 Kafka 抓取器配置。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
public class DctiKafkaFetcherConfig implements Bean {

    private static final long serialVersionUID = 4438152286556288269L;

    @JSONField(name = "#kafka_listener_container_factory_bean_name", ordinal = 1, deserialize = false)
    private String kafkaListenerContainerFactoryBeanNameRem = "KafkaListenerContainerFactory 的 Spring Bean 名称。";

    @JSONField(name = "kafka_listener_container_factory_bean_name", ordinal = 2)
    private String kafkaListenerContainerFactoryBeanName;

    @JSONField(name = "#topic", ordinal = 3, deserialize = false)
    private String topicRem = "Kafka 主题名称。";

    @JSONField(name = "topic", ordinal = 4)
    private String topic;

    @JSONField(name = "#listener_id", ordinal = 5, deserialize = false)
    private String listenerIdRem = "Kafka 监听器 ID，同时作为 consumer group id；同一节点内必须唯一。";

    @JSONField(name = "listener_id", ordinal = 6)
    private String listenerId;

    @JSONField(name = "#dcti_handler_bean_name", ordinal = 7, deserialize = false)
    private String dctiHandlerBeanNameRem = "DctiHandler 的 Spring Bean 名称，用于解码 dcti 协议消息。";

    @JSONField(name = "dcti_handler_bean_name", ordinal = 8)
    private String dctiHandlerBeanName;

    public DctiKafkaFetcherConfig() {
    }

    public DctiKafkaFetcherConfig(
            String kafkaListenerContainerFactoryBeanName, String topic, String listenerId,
            String dctiHandlerBeanName
    ) {
        this.kafkaListenerContainerFactoryBeanName = kafkaListenerContainerFactoryBeanName;
        this.topic = topic;
        this.listenerId = listenerId;
        this.dctiHandlerBeanName = dctiHandlerBeanName;
    }

    public String getKafkaListenerContainerFactoryBeanNameRem() {
        return kafkaListenerContainerFactoryBeanNameRem;
    }

    public void setKafkaListenerContainerFactoryBeanNameRem(String kafkaListenerContainerFactoryBeanNameRem) {
        this.kafkaListenerContainerFactoryBeanNameRem = kafkaListenerContainerFactoryBeanNameRem;
    }

    public String getKafkaListenerContainerFactoryBeanName() {
        return kafkaListenerContainerFactoryBeanName;
    }

    public void setKafkaListenerContainerFactoryBeanName(String kafkaListenerContainerFactoryBeanName) {
        this.kafkaListenerContainerFactoryBeanName = kafkaListenerContainerFactoryBeanName;
    }

    public String getTopicRem() {
        return topicRem;
    }

    public void setTopicRem(String topicRem) {
        this.topicRem = topicRem;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getListenerIdRem() {
        return listenerIdRem;
    }

    public void setListenerIdRem(String listenerIdRem) {
        this.listenerIdRem = listenerIdRem;
    }

    public String getListenerId() {
        return listenerId;
    }

    public void setListenerId(String listenerId) {
        this.listenerId = listenerId;
    }

    public String getDctiHandlerBeanNameRem() {
        return dctiHandlerBeanNameRem;
    }

    public void setDctiHandlerBeanNameRem(String dctiHandlerBeanNameRem) {
        this.dctiHandlerBeanNameRem = dctiHandlerBeanNameRem;
    }

    public String getDctiHandlerBeanName() {
        return dctiHandlerBeanName;
    }

    public void setDctiHandlerBeanName(String dctiHandlerBeanName) {
        this.dctiHandlerBeanName = dctiHandlerBeanName;
    }

    @Override
    public String toString() {
        return "DctiKafkaFetcherConfig{" +
                "kafkaListenerContainerFactoryBeanNameRem='" + kafkaListenerContainerFactoryBeanNameRem + '\'' +
                ", kafkaListenerContainerFactoryBeanName='" + kafkaListenerContainerFactoryBeanName + '\'' +
                ", topicRem='" + topicRem + '\'' +
                ", topic='" + topic + '\'' +
                ", listenerIdRem='" + listenerIdRem + '\'' +
                ", listenerId='" + listenerId + '\'' +
                ", dctiHandlerBeanNameRem='" + dctiHandlerBeanNameRem + '\'' +
                ", dctiHandlerBeanName='" + dctiHandlerBeanName + '\'' +
                '}';
    }
}
