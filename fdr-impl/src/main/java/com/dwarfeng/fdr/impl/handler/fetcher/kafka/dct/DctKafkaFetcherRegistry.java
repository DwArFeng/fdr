package com.dwarfeng.fdr.impl.handler.fetcher.kafka.dct;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.dct.stack.handler.DataCodingHandler;
import com.dwarfeng.fdr.sdk.handler.fetcher.AbstractFetcherRegistry;
import com.dwarfeng.fdr.stack.exception.FetcherException;
import com.dwarfeng.fdr.stack.exception.FetcherMakeException;
import com.dwarfeng.fdr.stack.handler.Fetcher;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 基于 dwarfeng-dct 协议的 Kafka 抓取器注册。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
@Component
public class DctKafkaFetcherRegistry extends AbstractFetcherRegistry {

    public static final String FETCHER_TYPE = "kafka.dct";

    private final ApplicationContext ctx;

    public DctKafkaFetcherRegistry(ApplicationContext ctx) {
        super(FETCHER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "dct Kafka 抓取器";
    }

    @Override
    public String provideDescription() {
        return "从 Kafka topic 消费 dct 协议消息并写入记录链路。";
    }

    @Override
    public String provideExampleParam() {
        DctKafkaFetcherConfig config = new DctKafkaFetcherConfig(
                "dctKafkaFetcherKafkaListenerContainerFactory", "fdr.dct", "fdr.fetcher.kafka.dct",
                "dctKafkaFetcherDataCodingHandler"
        );
        return JSON.toJSONString(config, true);
    }

    @Override
    public Fetcher makeFetcher(String type, String param) throws FetcherException {
        try {
            DctKafkaFetcherConfig config = JSON.parseObject(param, DctKafkaFetcherConfig.class);
            validateDctKafkaFetcherConfig(config);
            return ctx.getBean(DctKafkaFetcher.class, ctx, config);
        } catch (FetcherException e) {
            throw e;
        } catch (Exception e) {
            throw new FetcherMakeException("构造 dct Kafka 抓取器失败", e);
        }
    }

    private void validateDctKafkaFetcherConfig(DctKafkaFetcherConfig config) throws FetcherMakeException {
        if (Objects.isNull(config)) {
            throw new FetcherMakeException("配置不能为空");
        }
        validateNonEmptyString("kafka_listener_container_factory_bean_name",
                config.getKafkaListenerContainerFactoryBeanName());
        validateNonEmptyString("topic", config.getTopic());
        validateNonEmptyString("listener_id", config.getListenerId());
        validateNonEmptyString("data_coding_handler_bean_name", config.getDataCodingHandlerBeanName());
        validateKafkaListenerContainerFactoryBean(config.getKafkaListenerContainerFactoryBeanName());
        validateDataCodingHandlerBean(config.getDataCodingHandlerBeanName());
    }

    private void validateNonEmptyString(String fieldName, String value) throws FetcherMakeException {
        if (Objects.isNull(value) || value.trim().isEmpty()) {
            throw new FetcherMakeException("字段 " + fieldName + " 不能为空");
        }
    }

    private void validateKafkaListenerContainerFactoryBean(String beanName) throws FetcherMakeException {
        try {
            ctx.getBean(beanName, KafkaListenerContainerFactory.class);
        } catch (NoSuchBeanDefinitionException e) {
            throw new FetcherMakeException(
                    "字段 kafka_listener_container_factory_bean_name 对应的 bean 不存在: " + beanName, e
            );
        } catch (Exception e) {
            throw new FetcherMakeException(
                    "字段 kafka_listener_container_factory_bean_name 对应的 bean 类型不匹配: " + beanName, e
            );
        }
    }

    private void validateDataCodingHandlerBean(String beanName) throws FetcherMakeException {
        try {
            ctx.getBean(beanName, DataCodingHandler.class);
        } catch (NoSuchBeanDefinitionException e) {
            throw new FetcherMakeException(
                    "字段 data_coding_handler_bean_name 对应的 bean 不存在: " + beanName, e
            );
        } catch (Exception e) {
            throw new FetcherMakeException(
                    "字段 data_coding_handler_bean_name 对应的 bean 类型不匹配: " + beanName, e
            );
        }
    }

    @Override
    public String toString() {
        return "DctKafkaFetcherRegistry{" +
                "ctx=" + ctx +
                ", fetcherType='" + fetcherType + '\'' +
                '}';
    }
}
