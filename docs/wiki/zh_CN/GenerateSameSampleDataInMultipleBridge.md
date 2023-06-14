# Generate Same Sample Data in Multiple Bridge - 在多个桥接器生成相同的样本数据

## 说明

本文档介绍了如何在多个桥接器生成相同的样本数据。这些数据可以用于测试不同的桥接器的查询性能。

按照该文档操作，可以在每个待测试桥接器生成相同的样本数据。数据生成完毕后，
您可以针对每个桥接器进行相同数据点、相同时间段、相同映射信息的查询测试。这样可以保证测试结果的可比性。

## 数据源设置

### 设置可选加载项

修改 `opt/opt-source.xml` 文件，删除模拟数据源 `HistoricalMockSource` 的注释，使其生效。

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

    <!-- 省略上文内容 -->

    <!-- 加载 HistoricalMockSource -->
    <context:component-scan base-package="com.dwarfeng.fdr.impl.handler.source.mock.historical"/>

    <!-- 省略下文内容 -->
</beans>
```

### 配置数据源

修改 `conf/fdr/source.properties` 文件的模拟数据源部分，如下：

```properties
# 省略上文内容。
#
###################################################
#                 mock.historical                 #
###################################################
# Mock 历史数据源的随机种子。
source.mock.historical.random_seed=0
# Mock 历史数据源每个数据点每秒的数据量，设置较低的值可以降低数据处理与记录的压力（会增加生成完整数据所需的时间），
# 不影响数据的完整性。
source.mock.historical.data_size_per_point_per_sec=400
# Mock 历史数据源的起始日期，格式为 yyyy-MM-dd HH:mm:ss.SSS。
source.mock.historical.start_date=1992-12-18 00:00:00.000
# Mock 历史数据源的结束日期，格式为 yyyy-MM-dd HH:mm:ss.SSS。
source.mock.historical.end_date=1992-12-19 00:00:00.000
# Mock 历史数据源的数据点发生时间的增量，单位为毫秒。
source.mock.historical.happened_date_increment=1000
# Mock 历史数据源的数据配置。
#   point_type 的可选值如下:
#     int: 整数。
#     long: 长整数。
#     float: 浮点数。
#     double: 双精度浮点数。
#     gaussian: 标准高斯分布。
#     boolean: 布尔值。
#     string: 字符串。
#     int_string: 整数字符串。
#     long_string: 长整数字符串。
#     float_string: 浮点数字符串。
#     double_string: 双精度浮点数字符串。
#     gaussian_string: 标准高斯分布字符串。
source.mock.historical.data_config=[\
  {"point_id":1,"point_type":"int"},\
  {"point_id":2,"point_type":"int"},\
  ]
#
# 省略下文内容。
```

可以看到，配置项有详细的注释，您可以根据注释进行配置。

本文对以下配置进行额外补充说明：

`source.mock.historical.random_seed` 值为随机种子，除非您希望每此生成的样本数据都完全一样，否则可以不用修改。

`source.mock.historical.data_size_per_point_per_sec` 值为每个数据点每秒的数据量。无论该值为多少，生成的数据都是完整的。
请设置一个合理的值，约为压力测试时数据量的 `0.75-0.85` 倍即可。
过低的值会增加生成完整数据所需的时间；过高的值会造成消费者的数据积压。

## 桥接器设置

### 向预设的桥接器中添加样本数据

#### 设置可选加载项

修改 `opt/opt-bridge.xml` 文件，删除需要测试的桥接器的注释，使其生效，此处以 `InfluxdbBridge` 为例：

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

    <!-- 省略上文内容 -->

    <!-- 加载 DrainBridge -->
    <context:component-scan base-package="com.dwarfeng.fdr.impl.handler.bridge.drain"/>

    <!-- 加载 InfluxdbBridge -->
    <context:component-scan base-package="com.dwarfeng.fdr.impl.handler.bridge.influxdb"/>

    <!-- 加载 MultiBridge -->
    <context:component-scan base-package="com.dwarfeng.fdr.impl.handler.bridge.multi"/>
    
    <!-- 省略下文内容 -->
</beans>
```

#### 配置桥接器

修改 `conf/fdr/bridge.properties` 文件的 global 部分以及 Influxdb 桥接器部分，如下：

```properties
###################################################
#                     global                      #
###################################################
# 桥接器的类型。
# 目前支持的类型有:
#   drain: 简单地丢弃所有数据，通常用作占位符（如不想保留某类型的数据，则可以在对应类型上配置 drain）。支持保持器和持久器。
#   mock: 仅用于测试，不会真正持久数据，查询时返回随机数据。支持保持器和持久器。
#   hibernate: 使用 hibernate 框架持久数据，可适配多种不同类型的数据库。支持持久器。
#   influxdb: 使用 influxdb 框架持久数据。支持持久器。
#   redis: 使用 redis 维护实时数据。支持保持器。
#   kafka: 使用 kafka 接收消息的推送。支持保持器和持久器。只支持写入，不支持读取。
#   multi: 多重桥接器，将数据写入代理的所有桥接器，并从代理的第一个桥接器读取数据。
#
# 可以为一般数据、被过滤数据、被触发数据分别指定保持器类型。
# 对于一个具体的项目，很可能只需要一种保持器。此时如果希望程序加载时只加载这一种保持器，可以通过编辑
# opt/opt-bridge.xml 文件实现。
keep.normal_data.type=drain
persist.normal_data.type=multi
keep.filtered_data.type=drain
persist.filtered_data.type=drain
keep.triggered_data.type=drain
persist.triggered_data.type=drain
#
###################################################
#                      drain                      #
###################################################
# Drain 桥接器不需要进行任何配置。
#
###################################################
#                    influxdb                     #
###################################################
# influxdb 的 URL。
bridge.influxdb.url=http://your-host-here:8086
# influxdb 的 token。
bridge.influxdb.token=your-token-here
# influxdb 一般数据的桶。
bridge.influxdb.bucket.normal_data=fdr.normal_data
# influxdb 被过滤数据的桶。
bridge.influxdb.bucket.filtered_data=fdr.filtered_data
# influxdb 被触发数据的桶。
bridge.influxdb.bucket.triggered_data=fdr.triggered_data
# influxdb 的组织。
bridge.influxdb.organization=com.dwarfeng
#
###################################################
#                      multi                      #
###################################################
# 多重桥接器本身不直接实现数据的桥接方法，而是通过代理的方式实现。数据写入多重桥接器时，
# 会调用对应的代理列表，依次调用写入方法。数据从多重桥接器中读取时，会使用列表中的第一个代理，作为首选代理，调用读取方法。
# 多重桥接器提供的保持器/持久器是否为只写保持器/持久器取决于首选代理对应的保持器/持久器是否为只写保持器/持久器。
# 以下配置均为代理列表配置，所有的配置说明如下:
#   1. 每个代理类型之间用逗号隔开。如果代理类型文本中包含逗号，则使用反斜杠转义。
#   2. 第一个代理类型为首选代理类型。
# ------------------------------
# 一般数据保持器的代理列表。
bridge.multi.delegates.keep.normal_data=drain
# 一般数据持久器的代理列表。
bridge.multi.delegates.persist.normal_data=influxdb,another-bridge-1,another-bridge-2
# 被过滤数据保持器的代理列表。
bridge.multi.delegates.keep.filtered_data=drain
# 被过滤数据持久器的代理列表。
bridge.multi.delegates.persist.filtered_data=drain
# 被触发数据保持器的代理列表。
bridge.multi.delegates.keep.triggered_data=drain
# 被触发数据持久器的代理列表。
bridge.multi.delegates.persist.triggered_data=drain
# ------------------------------
#
# 省略下文内容。
```

在上述的配置实例中，我们配置了 `persist.normal_data.type` 的类型为 `multi`，其余的类型均为 `drain`。
这意味着我们将针对一般点位的持久值进行测试，如果您在使用时希望测试其它的数据通道，可以修改对应的配置。

同时，我们还配置了 `bridge.multi.delegates.persist.normal_data` 的值为 `influxdb,another-bridge-1,another-bridge-2`，
其余的代理列表均为 `drain`。这意味着我们将使用 `influxdb`，`another-bridge-1`，`another-bridge-2` 作为一般数据持久器的代理，
并且 `influxdb` 作为首选代理。如果您在使用时希望测试其它的数据通道，可以修改对应的配置。

influxdb 配置区域中，需要设置 influxdb 的相关连接参数。

### 向二次开发的桥接器中添加样本数据

#### 拷贝文件至指定的目录

将二次开发的桥接器的 jar 包放入 `libext/` 目录下，然后将格式为 `opt*.xml` 的加载项文件放入 `optext/` 目录下，
最后将所需的配置文件放入 `confext/` 目录下，即可测试二次开发的桥接器。

#### 配置桥接器

修改 `conf/fdr/bridge.properties` 文件的 global 部分，如下：

```properties
###################################################
#                     global                      #
###################################################
# 桥接器的类型。
# 目前支持的类型有:
#   drain: 简单地丢弃所有数据，通常用作占位符（如不想保留某类型的数据，则可以在对应类型上配置 drain）。支持保持器和持久器。
#   mock: 仅用于测试，不会真正持久数据，查询时返回随机数据。支持保持器和持久器。
#   hibernate: 使用 hibernate 框架持久数据，可适配多种不同类型的数据库。支持持久器。
#   influxdb: 使用 influxdb 框架持久数据。支持持久器。
#   redis: 使用 redis 维护实时数据。支持保持器。
#   kafka: 使用 kafka 接收消息的推送。支持保持器和持久器。只支持写入，不支持读取。
#   multi: 多重桥接器，将数据写入代理的所有桥接器，并从代理的第一个桥接器读取数据。
#
# 可以为一般数据、被过滤数据、被触发数据分别指定保持器类型。
# 对于一个具体的项目，很可能只需要一种保持器。此时如果希望程序加载时只加载这一种保持器，可以通过编辑
# opt/opt-bridge.xml 文件实现。
keep.normal_data.type=drain
persist.normal_data.type=multi
keep.filtered_data.type=drain
persist.filtered_data.type=drain
keep.triggered_data.type=drain
persist.triggered_data.type=drain
#
###################################################
#                      multi                      #
###################################################
# 多重桥接器本身不直接实现数据的桥接方法，而是通过代理的方式实现。数据写入多重桥接器时，
# 会调用对应的代理列表，依次调用写入方法。数据从多重桥接器中读取时，会使用列表中的第一个代理，作为首选代理，调用读取方法。
# 多重桥接器提供的保持器/持久器是否为只写保持器/持久器取决于首选代理对应的保持器/持久器是否为只写保持器/持久器。
# 以下配置均为代理列表配置，所有的配置说明如下:
#   1. 每个代理类型之间用逗号隔开。如果代理类型文本中包含逗号，则使用反斜杠转义。
#   2. 第一个代理类型为首选代理类型。
# ------------------------------
# 一般数据保持器的代理列表。
bridge.multi.delegates.keep.normal_data=drain
# 一般数据持久器的代理列表。
bridge.multi.delegates.persist.normal_data=your-bridge-type-1,your-bridge-type-2
# 被过滤数据保持器的代理列表。
bridge.multi.delegates.keep.filtered_data=drain
# 被过滤数据持久器的代理列表。
bridge.multi.delegates.persist.filtered_data=drain
# 被触发数据保持器的代理列表。
bridge.multi.delegates.keep.triggered_data=drain
# 被触发数据持久器的代理列表。
bridge.multi.delegates.persist.triggered_data=drain
# ------------------------------
#
# 省略下文内容。
```

在上述的配置实例中，我们配置了 `bridge.multi.delegates.persist.normal_data`
的值为 `your-bridge-type-1,your-bridge-type-2`，其余的代理列表均为 `drain`。这意味着我们将使用 `your-bridge-type-1`，
`your-bridge-type-2` 作为一般数据持久器的代理，并且 `your-bridge-type-1` 作为首选代理。
如果您在使用时希望测试其它的数据通道，可以修改对应的配置。

## 配置启动项

修改 `conf/fdr/launcher.properties` 文件的 `launcher.start_record_delay` 部分，如下：

```properties
# 程序启动完成后，开启记录的延时时间。
# 有些数据源以及推送器在启动后可能会需要一些时间进行自身的初始化，调整该参数以妥善的处理这些数据源和推送器。
# 该参数等于0，意味着启动后立即开启记录服务。
# 该参数小于0，意味着程序不主动开启记录服务，需要手动开启。
launcher.start_record_delay=-1
```

在上述的配置实例中，我们配置了 `launcher.start_record_delay` 的值为 `-1`，这意味着程序启动后不会自动开启记录服务，
需要手动开启/关闭记录服务。

## 进入 telnet 运维平台

### 启动服务

在终端中执行以下命令，启动 FDR：

```shell
cd /usr/local/fdr
sh ./bin/fdr-start.sh
```

### 进入 telnet 运维平台

等待程序启动完成后，执行：

```shell
telnet localhost 23
```

FDR 运维平台输出欢迎信息，如下：

```text
------------------------------------------------------------------------------------------------
                   8 8888888888        8 888888888o.           8 888888888o.
                   8 8888              8 8888    `^888.        8 8888    `88.
                   8 8888              8 8888        `88.      8 8888     `88
                   8 8888              8 8888         `88      8 8888     ,88
                   8 888888888888      8 8888          88      8 8888.   ,88'
                   8 8888              8 8888          88      8 888888888P'
                   8 8888              8 8888         ,88      8 8888`8b
                   8 8888              8 8888        ,88'      8 8888 `8b.
                   8 8888              8 8888    ,o88P'        8 8888   `8b.
                   8 8888              8 888888888P'           8 8888     `88.
------------------------------------------------------------------------------------------------
FDR QOS 运维系统                                                                   版本: 2.0.0.a
-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=


欢迎您 [0:0:0:0:0:0:0:1]:56814

```

可以使用三个终端连接 telnet 运维平台：第一个用于启动/停止记录服务，第二个用于查看逻辑侧消费者的状态，
第三个用于查看记录侧消费者的状态。

## 启动记录服务

在 telnet 运维平台中，输入以下指令，启动记录服务：

```shell
record -online
```

FDR 运维平台输出示例如下：

```text
record -online
记录功能已上线!
OK
```

服务启动成功后，FDR 逐步生成样本数据，写入复合桥接其中，复合桥接器时一个代理桥接器，它将数据写入多个代理中。

这个过程需要一定的时间，您可以在日志中抓取关键字，查看数据样本是否已经写入完成，相关方法在下文中有介绍。

## 在日志中抓取关键字

输入以下指令，抓取关键字：

```shell
tail -f /var/log/fdr/info.log | grep 所有数据点已经生成完成，停止生成数据。
```

## 关闭 FDR

测试完成后，可以在 telnet 运维平台中，输入以下指令，关闭 FDR：

```shell
shutdown
```

FDR 运维平台输出示例如下：

```text
shutdown
服务将会关闭，您可能需要登录远程主机才能重新启动该服务，是否继续? Y/N
Y
已确认请求，服务即将关闭...
服务端主动与您中断连接
再见!


遗失对主机的连接。
```

也可以在终端执行以下命令，关闭 FDR：

```shell
cd /usr/local/fdr
sh ./bin/fdr-stop.sh
```

## 取消数据源的可选加载项

历史数据生成完毕后，请关闭 FDR 服务，并立即注释 `HistoricalMockSource` 的可选加载项，
使其失效，如果不这么做，FDR 会在下次启动时，重新生成历史数据，造成数据复写或数据重复。

修改 `opt/opt-source.xml` 文件，增加模拟数据源 `HistoricalMockSource` 的注释，使其失效。

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

    <!-- 省略上文内容 -->

    <!-- 加载 HistoricalMockSource -->
    <!--
    <context:component-scan base-package="com.dwarfeng.fdr.impl.handler.source.mock.historical"/>
    -->

    <!-- 省略下文内容 -->
</beans>
```

## 参阅

- [Opt Directory](./OptDirectory.md) - 可选配置目录说明，详细介绍了本项目的可选配置，即 `opt/` 目录下的内容。
- [Conf Directory](./ConfDirectory.md) - 配置目录说明，详细说明如何配置本项目，即 `conf/` 目录下的内容。
