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

    private static final long serialVersionUID = -1601343822986854489L;

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

    public DctiKafkaFetcherConfig() {
    }

    public DctiKafkaFetcherConfig(
            String kafkaListenerContainerFactoryBeanName, String topic, String listenerId
    ) {
        this.kafkaListenerContainerFactoryBeanName = kafkaListenerContainerFactoryBeanName;
        this.topic = topic;
        this.listenerId = listenerId;
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

    @Override
    public String toString() {
        return "DctiKafkaFetcherConfig{" +
                "kafkaListenerContainerFactoryBeanNameRem='" + kafkaListenerContainerFactoryBeanNameRem + '\'' +
                ", kafkaListenerContainerFactoryBeanName='" + kafkaListenerContainerFactoryBeanName + '\'' +
                ", topicRem='" + topicRem + '\'' +
                ", topic='" + topic + '\'' +
                ", listenerIdRem='" + listenerIdRem + '\'' +
                ", listenerId='" + listenerId + '\'' +
                '}';
    }
}
