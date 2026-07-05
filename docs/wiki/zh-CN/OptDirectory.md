# Opt Directory - 可选配置目录

## 总览

本项目的可选配置位于 `opt/` 目录下，包括：

```text
opt
├─ opt-bridge.xml
├─ opt-fetcher.xml
├─ opt-fetcher-kafka-dct.xml
├─ opt-fetcher-kafka-dcti.xml
├─ opt-filter.xml
├─ opt-mapper.xml
├─ opt-pusher.xml
├─ opt-resetter.xml
├─ opt-trigger.xml
└─ opt-washer.xml
```

所有的可选配置都为每个单独的可选项提供了加载配置，默认是注释的，如果用户需要使用某个可选项，
只需要将其对应的配置项取消注释即可。

此处展示默认的可选配置文件。

## opt-bridge.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- 以下注释用于抑制 idea 中 .md 的警告，实际并无错误，在使用时可以连同本注释一起删除。 -->
<!--suppress SpringXmlModelInspection -->
<!--suppress SpringFacetInspection -->
<!--suppress XmlUnusedNamespaceDeclaration -->
<beans
        xmlns:context="http://www.springframework.org/schema/context"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="http://www.springframework.org/schema/beans"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd"
>

    <!-- 加载 DrainBridge -->
    <!--
    <context:component-scan base-package="com.dwarfeng.fdr.impl.handler.bridge.drain"/>
    -->

    <!-- 加载 MockBridge -->
    <!--
    <context:component-scan base-package="com.dwarfeng.fdr.impl.handler.bridge.mock"/>
    -->

    <!-- 加载 HibernateBridge -->
    <!--
    <context:component-scan base-package="com.dwarfeng.fdr.impl.handler.bridge.hibernate"/>
    -->

    <!-- 加载 RedisBridge -->
    <!--
    <context:component-scan base-package="com.dwarfeng.fdr.impl.handler.bridge.redis"/>
    -->

    <!-- 加载 InfluxdbBridge -->
    <!--
    <context:component-scan base-package="com.dwarfeng.fdr.impl.handler.bridge.influxdb"/>
    -->

    <!-- 加载 MultiBridge -->
    <!--
    <context:component-scan base-package="com.dwarfeng.fdr.impl.handler.bridge.multi"/>
    -->
</beans>
```

## opt-fetcher.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- 以下注释用于抑制 idea 中 .md 的警告，实际并无错误，在使用时可以连同本注释一起删除。 -->
<!--suppress SpringXmlModelInspection -->
<!--suppress SpringFacetInspection -->
<!--suppress XmlUnusedNamespaceDeclaration -->
<beans
        xmlns:context="http://www.springframework.org/schema/context"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="http://www.springframework.org/schema/beans"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd"
>

    <!-- 加载 MockHfFetcher。 -->
    <!--
    <context:component-scan base-package="com.dwarfeng.fdr.impl.handler.fetcher.mock.hf"/>
    -->

    <!-- 加载 MockLfFetcher。 -->
    <!--
    <context:component-scan base-package="com.dwarfeng.fdr.impl.handler.fetcher.mock.lf"/>
    -->

    <!-- 加载 SimulateAwgFetcher。 -->
    <!--
    <context:component-scan base-package="com.dwarfeng.fdr.impl.handler.fetcher.simulate.awg"/>
    -->

    <!-- 加载 SimulateWaveFetcher。 -->
    <!--
    <context:component-scan base-package="com.dwarfeng.fdr.impl.handler.fetcher.simulate.wave"/>
    -->

    <!-- 加载 DctKafkaFetcher。 -->
    <!--
    <context:component-scan base-package="com.dwarfeng.fdr.impl.handler.fetcher.kafka.dct"/>
    -->

    <!-- 加载 DctiKafkaFetcher。 -->
    <!--
    <context:component-scan base-package="com.dwarfeng.fdr.impl.handler.fetcher.kafka.dcti"/>
    -->
</beans>
```

## opt-fetcher-kafka-dct.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- 以下注释用于抑制 idea 中 .md 的警告，实际并无错误，在使用时可以连同本注释一起删除。 -->
<!--suppress SpringXmlModelInspection -->
<!--suppress SpringFacetInspection -->
<!--suppress XmlUnusedNamespaceDeclaration -->
<beans
        xmlns:dct="http://dwarfeng.com/schema/dwarfeng-dct"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="http://www.springframework.org/schema/beans"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://dwarfeng.com/schema/dwarfeng-dct
        http://dwarfeng.com/schema/dwarfeng-dct/dwarfeng-dct.xsd"
>

    <!--
            本配置文件为 DctKafkaFetcher 提供必要的 ConsumerFactory、KafkaListenerContainerFactory、
            DataCodingHandler 等 bean。
            如果需要使用 DctKafkaFetcher，请取消下方注释，并按照实际情况对下方参数进行配置。

            可以在下方的参数中直接赋值，也可以使用 value placeholder 进行占位，
            并将真正的配置值以 properties 文件的形式放在 confext 目录中。

            如需要连接多个 Kafka 集群，应该将 ConsumerFactory、KafkaListenerContainerFactory 与 DataCodingHandler
            bean 定义复制多份，分配不同的 id，为 ApplicationContext 提供多个 bean。

            对于 Kafka 组件，FetcherInfo.param 中需引用 KafkaListenerContainerFactory 的 bean 名称、topic、
            listener_id 与 data_coding_handler_bean_name；
            ConsumerFactory 仅作为 XML 内部基础设施 bean，不出现在 FetcherInfo.param JSON 中。

            DataCodingHandler 相关 bean 说明：
            DataCodingHandler 用于将 Kafka 消息解码为 dct 协议数据，通过 dwarfeng-dct XSD 命名空间装配。
            默认通过 package-scan 加载 com.dwarfeng.dct.impl.handler.vc 包下的值编解码器，
            支持的值类型包括 Boolean、Byte、Short、Integer、Long、Float、Double、BigDecimal、BigInteger、
            Character、String 等。可以通过调整 value-coding-config 中的 package-scan 来调整支持的值类型。

            ConsumerFactory 参数说明：
            bootstrapServers:
              引导服务器集群。
            sessionTimeoutMs:
              会话的超时限制: 如果 consumer 在这段时间内没有发送心跳信息，一次 rebalance 将会产生。
              该值必须在 [group.min.session.timeout.ms, group.max.session.timeout.ms] 范围内，默认: 10000。
            autoOffsetReset:
              新的 group 加入 topic 时，从什么位置开始消费。
            maxPollRecords:
              监听器的最大拉取数据量。当拉取到的数据量达到这个值时，会立即返回，不会等待 poll_timeout。
            maxPollIntervalMs:
              监听器的最大拉取间隔。如果当前时间距离监听器上一次拉取数据的时间超过了这个值，一次 rebalance 将会产生。

            KafkaListenerContainerFactory 参数说明：
            consumerFactory:
              引用上方定义的 ConsumerFactory bean。
            concurrency:
              监听器启用的消费者的线程数。
              每一个线程都会启动一个 KafkaConsumer，每个 KafkaConsumer 都会占用一个 partition。
              程序分布式部署时，所有节点的线程数之和应该小于等于 topic 的 partition 数。
              该值大于 topic partition 数时可能导致消费者线程空闲或 rebalance 问题。
            pollTimeout:
              监听器调用 KafkaConsumer.poll(Duration) 方法的超时时间，如果超过这个时间还没有拉取到数据，则返回空列表。
    -->
    <!--
    <bean
            id="dctKafkaFetcherConsumerFactory"
            class="com.dwarfeng.fdr.impl.handler.fetcher.kafka.dct.DctKafkaFetcherUtil"
            factory-method="newConsumerFactory"
    >
        <constructor-arg name="bootstrapServers" value="your-ip1:9092,your-ip2:9092,your-ip3:9092"/>
        <constructor-arg name="sessionTimeoutMs" value="10000"/>
        <constructor-arg name="autoOffsetReset" value="latest"/>
        <constructor-arg name="maxPollRecords" value="100"/>
        <constructor-arg name="maxPollIntervalMs" value="300000"/>
    </bean>
    <bean
            id="dctKafkaFetcherKafkaListenerContainerFactory"
            class="com.dwarfeng.fdr.impl.handler.fetcher.kafka.dct.DctKafkaFetcherUtil"
            factory-method="newKafkaListenerContainerFactory"
    >
        <constructor-arg name="consumerFactory" ref="dctKafkaFetcherConsumerFactory"/>
        <constructor-arg name="concurrency" value="2"/>
        <constructor-arg name="pollTimeout" value="3000"/>
    </bean>
    <bean id="dctKafkaFetcherFlatDataCodec" class="com.dwarfeng.dct.impl.handler.fdc.FastJsonFlatDataCodec"/>
    <dct:value-coding-config config-name="dctKafkaFetcherValueCodingConfig">
        <dct:value-codec>
            <dct:value-codec-impl package-scan="com.dwarfeng.dct.impl.handler.vc"/>
        </dct:value-codec>
    </dct:value-coding-config>
    <dct:value-coding-handler
            handler-name="dctKafkaFetcherValueCodingHandler"
            config-ref="dctKafkaFetcherValueCodingConfig"
    />
    <dct:data-coding-config
            config-name="dctKafkaFetcherDataCodingConfig"
            flat-data-codec-ref="dctKafkaFetcherFlatDataCodec"
            value-coding-handler-ref="dctKafkaFetcherValueCodingHandler"
    />
    <dct:data-coding-handler
            handler-name="dctKafkaFetcherDataCodingHandler"
            config-ref="dctKafkaFetcherDataCodingConfig"
    />
    -->
</beans>
```

## opt-fetcher-kafka-dcti.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- 以下注释用于抑制 idea 中 .md 的警告，实际并无错误，在使用时可以连同本注释一起删除。 -->
<!--suppress SpringXmlModelInspection -->
<!--suppress SpringFacetInspection -->
<!--suppress XmlUnusedNamespaceDeclaration -->
<beans
        xmlns:dcti="http://dwarfeng.com/schema/dcti"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="http://www.springframework.org/schema/beans"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://dwarfeng.com/schema/dcti
        http://dwarfeng.com/schema/dcti/dcti.xsd"
>

    <!--
            本配置文件为 DctiKafkaFetcher 提供必要的 ConsumerFactory、KafkaListenerContainerFactory、
            DctiHandler 等 bean。
            如果需要使用 DctiKafkaFetcher，请取消下方注释，并按照实际情况对下方参数进行配置。

            可以在下方的参数中直接赋值，也可以使用 value placeholder 进行占位，
            并将真正的配置值以 properties 文件的形式放在 confext 目录中。

            如需要连接多个 Kafka 集群，应该将 ConsumerFactory、KafkaListenerContainerFactory 与 DctiHandler
            bean 定义复制多份，分配不同的 id，为 ApplicationContext 提供多个 bean。

            对于 Kafka 组件，FetcherInfo.param 中需引用 KafkaListenerContainerFactory 的 bean 名称、topic、
            listener_id 与 dcti_handler_bean_name；
            ConsumerFactory 仅作为 XML 内部基础设施 bean，不出现在 FetcherInfo.param JSON 中。

            DctiHandler 相关 bean 说明：
            DctiHandler 用于将 Kafka 消息解码为 dcti 协议数据，通过 dcti XSD 命名空间装配。

            ConsumerFactory 参数说明：
            bootstrapServers:
              引导服务器集群。
            sessionTimeoutMs:
              会话的超时限制: 如果 consumer 在这段时间内没有发送心跳信息，一次 rebalance 将会产生。
              该值必须在 [group.min.session.timeout.ms, group.max.session.timeout.ms] 范围内，默认: 10000。
            autoOffsetReset:
              新的 group 加入 topic 时，从什么位置开始消费。
            maxPollRecords:
              监听器的最大拉取数据量。当拉取到的数据量达到这个值时，会立即返回，不会等待 poll_timeout。
            maxPollIntervalMs:
              监听器的最大拉取间隔。如果当前时间距离监听器上一次拉取数据的时间超过了这个值，一次 rebalance 将会产生。

            KafkaListenerContainerFactory 参数说明：
            consumerFactory:
              引用上方定义的 ConsumerFactory bean。
            concurrency:
              监听器启用的消费者的线程数。
              每一个线程都会启动一个 KafkaConsumer，每个 KafkaConsumer 都会占用一个 partition。
              程序分布式部署时，所有节点的线程数之和应该小于等于 topic 的 partition 数。
              该值大于 topic partition 数时可能导致消费者线程空闲或 rebalance 问题。
            pollTimeout:
              监听器调用 KafkaConsumer.poll(Duration) 方法的超时时间，如果超过这个时间还没有拉取到数据，则返回空列表。
    -->
    <!--
    <bean
            id="dctiKafkaFetcherConsumerFactory"
            class="com.dwarfeng.fdr.impl.handler.fetcher.kafka.dcti.DctiKafkaFetcherUtil"
            factory-method="newConsumerFactory"
    >
        <constructor-arg name="bootstrapServers" value="your-ip1:9092,your-ip2:9092,your-ip3:9092"/>
        <constructor-arg name="sessionTimeoutMs" value="10000"/>
        <constructor-arg name="autoOffsetReset" value="latest"/>
        <constructor-arg name="maxPollRecords" value="100"/>
        <constructor-arg name="maxPollIntervalMs" value="300000"/>
    </bean>
    <bean
            id="dctiKafkaFetcherKafkaListenerContainerFactory"
            class="com.dwarfeng.fdr.impl.handler.fetcher.kafka.dcti.DctiKafkaFetcherUtil"
            factory-method="newKafkaListenerContainerFactory"
    >
        <constructor-arg name="consumerFactory" ref="dctiKafkaFetcherConsumerFactory"/>
        <constructor-arg name="concurrency" value="2"/>
        <constructor-arg name="pollTimeout" value="3000"/>
    </bean>
    <dcti:handler handler-name="dctiKafkaFetcherDctiHandler"/>
    -->
</beans>
```

## opt-filter.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- 以下注释用于抑制 idea 中 .md 的警告，实际并无错误，在使用时可以连同本注释一起删除。 -->
<!--suppress SpringXmlModelInspection -->
<!--suppress SpringFacetInspection -->
<!--suppress XmlUnusedNamespaceDeclaration -->
<beans
        xmlns:context="http://www.springframework.org/schema/context"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="http://www.springframework.org/schema/beans"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd"
>

    <!-- 扫描 handler 的实现包。 -->
    <context:component-scan base-package="com.dwarfeng.fdr.impl.handler.filter" use-default-filters="false">
        <!-- 加载 GroovyFilter -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.filter.GroovyFilterRegistry"
        />
        -->

        <!-- 加载 LogicAndFilter -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.filter.LogicAndFilterRegistry"
        />
        -->

        <!-- 加载 LogicNotFilter -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.filter.LogicNotFilterRegistry"
        />
        -->

        <!-- 加载 NonNullFilter -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.filter.NonNullFilterRegistry"
        />
        -->

        <!-- 加载 NumberStringFilter -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.filter.NumberStringFilterRegistry"
        />
        -->

        <!-- 加载 RangedNumberFilter -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.filter.RangedNumberFilterRegistry"
        />
        -->

        <!-- 加载 RegexFilter -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.filter.RegexFilterRegistry"
        />
        -->

        <!-- 加载 ValueTypeFilter -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.filter.ValueTypeFilterRegistry"
        />
        -->

        <!-- 加载 DeadbandFilter -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.filter.DeadbandFilterRegistry"
        />
        -->
    </context:component-scan>
</beans>
```

## opt-mapper.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- 以下注释用于抑制 idea 中 .md 的警告，实际并无错误，在使用时可以连同本注释一起删除。 -->
<!--suppress SpringXmlModelInspection -->
<!--suppress SpringFacetInspection -->
<beans
        xmlns:context="http://www.springframework.org/schema/context"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="http://www.springframework.org/schema/beans"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd"
>

    <!-- 扫描 handler 的实现包。 -->
    <context:component-scan base-package="com.dwarfeng.fdr.impl.handler.mapper" use-default-filters="false">
        <!-- 加载 AlignMapper -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.mapper.AlignMapperRegistry"
        />
        -->

        <!-- 加载 AvgMapper -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.mapper.AvgMapperRegistry"
        />
        -->

        <!-- 加载 CountMapper -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.mapper.CountMapperRegistry"
        />
        -->

        <!-- 加载 FirstMapper -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.mapper.FirstMapperRegistry"
        />
        -->

        <!-- 加载 GroovyMapper -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.mapper.GroovyMapperRegistry"
        />
        -->

        <!-- 加载 IdentifyMapper -->
        <!-- 该映射器由于命名规范性问题，已经被废弃，请使用下方 IdentityMapper 代替 -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.mapper.IdentifyMapperRegistry"
        />
        -->

        <!-- 加载 IdentityMapper -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.mapper.IdentityMapperRegistry"
        />
        -->

        <!-- 加载 LastMapper -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.mapper.LastMapperRegistry"
        />
        -->

        <!-- 加载 SortMapper -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.mapper.SortMapperRegistry"
        />
        -->

        <!-- 加载 TimeWeightedAgvMapper -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.mapper.TimeWeightedAgvMapperRegistry"
        />
        -->

        <!-- 加载 ToDoubleMapper -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.mapper.ToDoubleMapperRegistry"
        />
        -->

        <!-- 加载 WindowMapper -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.mapper.WindowMapperRegistry"
        />
        -->

        <!-- 加载 MergeMapper -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.mapper.MergeMapperRegistry"
        />
        -->

        <!-- 加载 TrimMapper -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.mapper.TrimMapperRegistry"
        />
        -->

        <!-- 加载 ToBooleanMapper -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.mapper.ToBooleanMapperRegistry"
        />
        -->

        <!-- 加载 EnableRatioMapper -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.mapper.EnableRatioMapperRegistry"
        />
        -->

        <!-- 加载 HighPassMapper -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.mapper.HighPassMapperRegistry"
        />
        -->

        <!-- 加载 LowPassMapper -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.mapper.LowPassMapperRegistry"
        />
        -->

        <!-- 加载 HighPassCounterMapper -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.mapper.HighPassCounterMapperRegistry"
        />
        -->

        <!-- 加载 LowPassCounterMapper -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.mapper.LowPassCounterMapperRegistry"
        />
        -->

        <!-- 加载 HighPassExistenceMapper -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.mapper.HighPassExistenceMapperRegistry"
        />
        -->

        <!-- 加载 LowPassExistenceMapper -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.mapper.LowPassExistenceMapperRegistry"
        />
        -->
    </context:component-scan>
</beans>
```

## opt-pusher.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- 以下注释用于抑制 idea 中 .md 的警告，实际并无错误，在使用时可以连同本注释一起删除。 -->
<!--suppress SpringXmlModelInspection -->
<!--suppress SpringFacetInspection -->
<beans
        xmlns:context="http://www.springframework.org/schema/context"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="http://www.springframework.org/schema/beans"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd"
>

    <!-- 扫描 handler 的实现包。 -->
    <context:component-scan base-package="com.dwarfeng.fdr.impl.handler.pusher" use-default-filters="false">
        <!-- 加载 DctiKafkaPusher -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.pusher.DctiKafkaPusher"
        />
        -->

        <!-- 加载 DrainPusher -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.pusher.DrainPusher"
        />
        -->

        <!-- 加载 LogPusher -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.pusher.LogPusher"
        />
        -->

        <!-- 加载 MultiPusher -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.pusher.MultiPusher"
        />
        -->

        <!-- 加载 NativeKafkaPusher -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.pusher.NativeKafkaPusher"
        />
        -->

        <!-- 加载 PartialDrainPusher -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.pusher.PartialDrainPusher"
        />
        -->
    </context:component-scan>
</beans>
```

## opt-resetter.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- 以下注释用于抑制 idea 中 .md 的警告，实际并无错误，在使用时可以连同本注释一起删除。 -->
<!--suppress SpringXmlModelInspection -->
<!--suppress SpringFacetInspection -->
<!--suppress XmlUnusedNamespaceDeclaration -->
<beans
        xmlns:context="http://www.springframework.org/schema/context"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="http://www.springframework.org/schema/beans"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd"
>

    <!-- 扫描 handler 的实现包。 -->
    <context:component-scan base-package="com.dwarfeng.fdr.impl.handler.resetter" use-default-filters="false">
        <!-- 加载 NeverResetter -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.resetter.NeverResetter"
        />
        -->

        <!-- 加载 FixedDelayResetter -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.resetter.FixedDelayResetter"
        />
        -->

        <!-- 加载 FixedRateResetter -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.resetter.FixedRateResetter"
        />
        -->

        <!-- 加载 CronResetter -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.resetter.CronResetter"
        />
        -->

        <!-- 加载 DubboResetter -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.resetter.DubboResetter"
        />
        -->
    </context:component-scan>
</beans>
```

## opt-trigger.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- 以下注释用于抑制 idea 中 .md 的警告，实际并无错误，在使用时可以连同本注释一起删除。 -->
<!--suppress SpringXmlModelInspection -->
<!--suppress SpringFacetInspection -->
<!--suppress XmlUnusedNamespaceDeclaration -->
<beans
        xmlns:context="http://www.springframework.org/schema/context"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="http://www.springframework.org/schema/beans"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd"
>

    <!-- 扫描 handler 的实现包。 -->
    <context:component-scan base-package="com.dwarfeng.fdr.impl.handler.trigger" use-default-filters="false">
        <!-- 加载 BooleanTrigger -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.trigger.BooleanTriggerRegistry"
        />
        -->

        <!-- 加载 GroovyTrigger -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.trigger.GroovyTriggerRegistry"
        />
        -->

        <!-- 加载 LogicAndTrigger -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.trigger.LogicAndTriggerRegistry"
        />
        -->

        <!-- 加载 LogicNotTrigger -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.trigger.LogicNotTriggerRegistry"
        />
        -->

        <!-- 加载 RangedNumberTrigger -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.trigger.RangedNumberTriggerRegistry"
        />
        -->

        <!-- 加载 RegexTrigger -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.trigger.RegexTriggerRegistry"
        />
        -->
    </context:component-scan>
</beans>
```

## opt-washer.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- 以下注释用于抑制 idea 中 .md 的警告，实际并无错误，在使用时可以连同本注释一起删除。 -->
<!--suppress SpringXmlModelInspection -->
<!--suppress SpringFacetInspection -->
<!--suppress XmlUnusedNamespaceDeclaration -->
<beans
        xmlns:context="http://www.springframework.org/schema/context"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="http://www.springframework.org/schema/beans"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd"
>

    <!-- 扫描 handler 的实现包。 -->
    <context:component-scan base-package="com.dwarfeng.fdr.impl.handler.washer" use-default-filters="false">
        <!-- 加载 GroovyWasher -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.washer.GroovyWasherRegistry"
        />
        -->

        <!-- 加载 IdentifyWasher -->
        <!-- 该清洗器由于命名规范性问题，已经被废弃，请使用下方 IdentityMapper 代替 -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.washer.IdentifyWasherRegistry"
        />
        -->

        <!-- 加载 IdentityWasher -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.washer.IdentityWasherRegistry"
        />
        -->

        <!-- 加载 ToBooleanWasher -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.washer.ToBooleanWasherRegistry"
        />
        -->

        <!-- 加载 ToDoubleWasher -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.washer.ToDoubleWasherRegistry"
        />
        -->

        <!-- 加载 ToLongWasher -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.washer.ToLongWasherRegistry"
        />
        -->
    </context:component-scan>
</beans>
```
