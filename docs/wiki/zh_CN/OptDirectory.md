# Opt Directory - 可选配置目录

## 总览

本项目的可选配置位于 `opt/` 目录下，包括：

```text
opt
    opt-bridge.xml
    opt-filter.xml
    opt-mapper.xml
    opt-pusher.xml
    opt-resetter.xml
    opt-source.xml
    opt-trigger.xml
    opt-washer.xml
```

所有的可选配置都为每个单独的可选项提供了加载配置，默认是注释的，如果用户需要使用某个可选项，
只需要将其对应的配置项取消注释即可。

此处展示默认的可选配置文件。

## opt-bridge.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!--suppress SpringFacetInspection, XmlUnusedNamespaceDeclaration -->
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

## opt-filter.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
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

    <!--扫描handler的实现包。 -->
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
    </context:component-scan>
</beans>
```

## opt-mapper.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
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

    <!--扫描handler的实现包。 -->
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
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.mapper.IdentifyMapperRegistry"
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
    </context:component-scan>
</beans>
```

## opt-pusher.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
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

    <!--扫描handler的实现包。 -->
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

## opt-source.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!--suppress SpringFacetInspection, XmlUnusedNamespaceDeclaration -->
<beans
        xmlns:context="http://www.springframework.org/schema/context"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="http://www.springframework.org/schema/beans"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd"
>

    <!-- 加载 RealtimeMockSource -->
    <!--
    <context:component-scan base-package="com.dwarfeng.fdr.impl.handler.source.mock.realtime"/>
    -->

    <!-- 加载 HistoricalMockSource -->
    <!--
    <context:component-scan base-package="com.dwarfeng.fdr.impl.handler.source.mock.historical"/>
    -->

    <!-- 加载 DwarfengDctKafkaSource -->
    <!--
    <context:component-scan base-package="com.dwarfeng.fdr.impl.handler.source.kafka.dwarfengdct"/>
    -->

    <!-- 加载 DctiKafkaSource -->
    <!--
    <context:component-scan base-package="com.dwarfeng.fdr.impl.handler.source.kafka.dcti"/>
    -->
</beans>
```

## opt-trigger.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
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

    <!--扫描handler的实现包。 -->
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

    <!--扫描handler的实现包。 -->
    <context:component-scan base-package="com.dwarfeng.fdr.impl.handler.washer" use-default-filters="false">
        <!-- 加载 GroovyWasher -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.washer.GroovyWasherRegistry"
        />
        -->

        <!-- 加载 IdentifyWasher -->
        <!--
        <context:include-filter
                type="assignable" expression="com.dwarfeng.fdr.impl.handler.washer.IdentifyWasherRegistry"
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
