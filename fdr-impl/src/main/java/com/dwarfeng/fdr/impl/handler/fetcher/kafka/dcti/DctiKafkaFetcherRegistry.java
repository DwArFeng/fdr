package com.dwarfeng.fdr.impl.handler.fetcher.kafka.dcti;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.dcti.stack.handler.DctiHandler;
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
 * 基于 dcti 协议的 Kafka 抓取器注册。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
@Component
public class DctiKafkaFetcherRegistry extends AbstractFetcherRegistry {

    public static final String FETCHER_TYPE = "kafka.dcti";

    private final ApplicationContext ctx;

    public DctiKafkaFetcherRegistry(ApplicationContext ctx) {
        super(FETCHER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "dcti Kafka 抓取器";
    }

    @Override
    public String provideDescription() {
        return "从 Kafka topic 消费 dcti 协议消息并写入记录链路。";
    }

    @Override
    public String provideExampleParam() {
        DctiKafkaFetcherConfig config = new DctiKafkaFetcherConfig(
                "dctiKafkaFetcherKafkaListenerContainerFactory", "fdr.dcti", "fdr.fetcher.kafka.dcti",
                "dctiKafkaFetcherDctiHandler"
        );
        return JSON.toJSONString(config, true);
    }

    @Override
    public Fetcher makeFetcher(String type, String param) throws FetcherException {
        try {
            DctiKafkaFetcherConfig config = JSON.parseObject(param, DctiKafkaFetcherConfig.class);
            validateDctiKafkaFetcherConfig(config);
            return ctx.getBean(DctiKafkaFetcher.class, ctx, config);
        } catch (FetcherException e) {
            throw e;
        } catch (Exception e) {
            throw new FetcherMakeException("构造 dcti Kafka 抓取器失败", e);
        }
    }

    private void validateDctiKafkaFetcherConfig(DctiKafkaFetcherConfig config) throws FetcherMakeException {
        if (Objects.isNull(config)) {
            throw new FetcherMakeException("配置不能为空");
        }
        validateNonEmptyString("kafka_listener_container_factory_bean_name",
                config.getKafkaListenerContainerFactoryBeanName());
        validateNonEmptyString("topic", config.getTopic());
        validateNonEmptyString("listener_id", config.getListenerId());
        validateNonEmptyString("dcti_handler_bean_name", config.getDctiHandlerBeanName());
        validateKafkaListenerContainerFactoryBean(config.getKafkaListenerContainerFactoryBeanName());
        validateDctiHandlerBean(config.getDctiHandlerBeanName());
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

    private void validateDctiHandlerBean(String beanName) throws FetcherMakeException {
        try {
            ctx.getBean(beanName, DctiHandler.class);
        } catch (NoSuchBeanDefinitionException e) {
            throw new FetcherMakeException(
                    "字段 dcti_handler_bean_name 对应的 bean 不存在: " + beanName, e
            );
        } catch (Exception e) {
            throw new FetcherMakeException(
                    "字段 dcti_handler_bean_name 对应的 bean 类型不匹配: " + beanName, e
            );
        }
    }

    @Override
    public String toString() {
        return "DctiKafkaFetcherRegistry{" +
                "ctx=" + ctx +
                ", fetcherType='" + fetcherType + '\'' +
                '}';
    }
}
