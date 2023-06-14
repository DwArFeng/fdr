# Conduct Stress Test for Record - 进行数据采集压力测试

## 说明

数据采集压力测试，是指在一定的时间内，对数据采集进行压力测试，以验证数据采集的性能。在此期间，使用模拟数据源，
每秒生成指定数量的模拟数据（通常在数百个或更多），以验证数据采集的性能。

在数据写入的过程中，如果逻辑侧消费者和记录侧消费者能够在一段时间内及时稳定地消费数据，
那么可以认为对应数据量的压力测试通过，FDR 的写入性能可以达到这个水平。

压力测试可以在场景下进行：

- 测试 FDR 的极限写入性能。通常情况下，FDR 的极限写入性能与多种因素有关，如 CPU、内存、磁盘、网络等。
  通过压力测试，可以验证 FDR 在当前设备/环境中的极限写入性能。

  在这种场景下，一般需要反复调整消费者的各项参数，才能找到最佳的参数组合，以达到最佳的写入性能。

- 测试 FDR 的性能是否能满足业务需求。如果能估计出业务的数据量，那么可以通过压力测试，验证 FDR 的性能是否能满足业务需求。

  在这种场景下，如果初始的消费者参数能够满足业务需求，那么就不需要调整消费者的各项参数。

## 数据源设置

### 设置可选加载项

修改 `opt/opt-source.xml` 文件，删除模拟数据源 `RealtimeMockSource` 的注释，使其生效。

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

    <!-- 加载 RealtimeMockSource -->
    <context:component-scan base-package="com.dwarfeng.fdr.impl.handler.source.mock.realtime"/>

    <!-- 省略下文内容 -->
</beans>
```

### 配置数据源

修改 `conf/fdr/source.properties` 文件的模拟数据源部分，如下：

```properties
# 省略上文内容。
#
###################################################
#                  mock.realtime                  #
###################################################
# Mock 实时数据源的随机种子。
source.mock.realtime.random_seed=0
# Mock 实时数据源每个数据点每秒的数据量。
source.mock.realtime.data_size_per_point_per_sec=400
# Mock 实时数据源的数据配置。
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
source.mock.realtime.data_config=[\
  {"point_id":1,"point_type":"int"},\
  {"point_id":2,"point_type":"int"},\
  ]
#
# 省略下文内容。
```

`source.mock.realtime.random_seed` 值为随机种子，除非您希望每次压力测试接受完全一样的数据，否则可以不用修改。

`source.mock.realtime.data_size_per_point_per_sec` 值为每个数据点每秒的数据量，该值最好不要超过 `800`，
否则有可能会将两个点的发生时间设置为同一时间，从而导致数据覆写或写入失败。该值是单个数据点的每秒数据生成量，
您可以配置多个数据点，这样每秒数据总量就是 `data_size_per_point_per_sec` 乘以数据点的数量。

如果您正在做极限测试， 总数据量可以设置为 `1200`，可以配置 `6` 个数据点，每个数据点每秒的数据量为 `200`。测试通过后，
再增加数据点的数量， 则每秒数据总量的增量会以 `200` 的速度增加。

如果您正在做业务需求测试，那么可以根据业务需求，设置每秒数据总量的值。该值可以设置为业务峰值的 `1.2-1.5` 倍。
如设置 `10` 个数据点，每个点每秒的数据量为 `45`，则每秒数据总量为 `450`。如果该测试通过，那么理论上，
该系统可以满足 `300` 个点位，每个点位每秒 `1` 个数据的需求。

`source.mock.realtime.data_config` 值为数据配置，该值是一个 JSON 数组，每个元素是一个 JSON 对象。如何配置该值，
在配置注释中有详细的说明。特别的，如果您的业务需求中，需要采集多个类型的数据，那么可以配置多个数据点，
以生成业务需求中所需的全部类型的数据。

## 桥接器设置

### 测试预设的桥接器

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
persist.normal_data.type=influxdb
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
#
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
# 省略下文内容。
```

在上述的配置实例中，我们配置了 `persist.normal_data.type` 的类型为 `influxdb`，其余的类型均为 `drain`。
这意味着我们将针对一般点位的持久值进行测试，如果您在使用时希望测试其它的数据通道，可以修改对应的配置。

influxdb 配置区域中，需要设置 influxdb 的相关连接参数。

### 测试二次开发的桥接器

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
persist.normal_data.type=your-bridge-type-here
keep.filtered_data.type=drain
persist.filtered_data.type=drain
keep.triggered_data.type=drain
persist.triggered_data.type=drain
#
# 省略下文内容。
```

在上述的配置实例中，我们配置了 `persist.normal_data.type` 的类型为 `your-bridge-type-here`，其余的类型均为 `drain`。
这意味着我们将针对一般点位的持久值进行测试，如果您在使用时希望测试其它的数据通道，可以修改对应的配置。

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

在 telnet 运维平台中，输入以下指令，停止记录服务：

```shell
record -offline
```

FDR 运维平台输出示例如下：

```text
record -online
记录功能已上线!
OK

record -offline
记录功能已下线!
OK
```

## 查看/设置逻辑侧消费者状态

### 查看状态

在 telnet 运维平台中，输入以下指令，查看逻辑侧消费者状态：

```shell
lcsu -l -h
```

其中，`-l` 参数表示查看逻辑侧消费者状态；`-h` 参数表示持续输出，每隔 `1` 秒输出一次当前的状态。

FDR 运维平台输出示例如下：

```text
lcsu -l -h
输入任意字符停止持续输出
buffered-size:12      buffer-size:1000    thread:1   idle:false
buffered-size:46      buffer-size:1000    thread:1   idle:false
buffered-size:125     buffer-size:1000    thread:1   idle:false
buffered-size:0       buffer-size:1000    thread:1   idle:false
q
OK
```

### 设置状态

如果逻辑侧消费者积压，即 `buffered-size / buffer-size` 比例过高，可以通过设置逻辑侧消费者状态来调整积压情况。

在 telnet 运维平台中，输入以下指令，设置逻辑侧消费者状态：

```shell
lcsu -s -b 2000 -t 2
```

其中，`-s` 参数表示设置逻辑侧消费者状态；`-b` 参数表示设置 `buffer-size` 的值；`-t` 参数表示设置 `thread` 的值。

FDR 运维平台输出示例如下：

```text
lcsu -s -b 2000 -t 2
设置完成，记录者新的参数为:
buffered-size:0       buffer-size:2000    thread:2   idle:false
OK
```

## 查看/设置记录侧消费者状态

### 查看状态

在 telnet 运维平台中，输入以下指令，查看记录侧消费者状态：

```shell
rcsu -c persist -n normal -l -h
```

其中，`-c` 参数表示查看特定类型的消费者状态，此处值为 `persist`，代表查看持久值的消费者状态；
`-n` 参数表示查看特定名称的消费者状态，此处值为 `normal`，代表一般数据的消费者状态；
`-l` 参数表示查看记录侧消费者状态；`-h` 参数表示持续输出，每隔 `1` 秒输出一次当前的状态。

FDR 运维平台输出示例如下：

```text
rcsu -c persist -n normal -l -h
输入任意字符停止持续输出
1    persist - normal       buffered-size:12      buffer-size:1000    batch-size:100     max-idle-time:1000       thread:4   idle:false
1    persist - normal       buffered-size:20      buffer-size:1000    batch-size:100     max-idle-time:1000       thread:4   idle:false
1    persist - normal       buffered-size:325     buffer-size:1000    batch-size:100     max-idle-time:1000       thread:4   idle:false
1    persist - normal       buffered-size:0       buffer-size:1000    batch-size:100     max-idle-time:1000       thread:4   idle:false
q
OK
```

### 设置状态

如果记录侧消费者积压，即 `buffered-size / buffer-size` 比例过高，可以通过设置记录侧消费者状态来调整积压情况。

在 telnet 运维平台中，输入以下指令，设置记录侧消费者状态：

```shell
rcsu -c persist -n normal -s -b 2000 -a 200 -m 500 -t 8
```

其中，`-c` 参数表示设置特定类型的消费者状态，此处值为 `persist`，代表设置持久值的消费者状态；
`-n` 参数表示设置特定名称的消费者状态，此处值为 `normal`，代表一般数据的消费者状态；
`-s` 参数表示设置记录侧消费者状态；`-b` 参数表示设置 `buffer-size` 的值；`-a` 参数表示设置 `batch-size` 的值；
`-m` 参数表示设置 `max-idle-time` 的值；`-t` 参数表示设置 `thread` 的值。

FDR 运维平台输出示例如下：

```text
rcsu -c persist -n normal -s -b 2000 -a 200 -m 500 -t 8
设置完成，消费者新的参数为:
1    persist - normal       buffered-size:0       buffer-size:2000    batch-size:200     max-idle-time:500        thread:8   idle:false
OK
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

## 参阅

- [Opt Directory](./OptDirectory.md) - 可选配置目录说明，详细介绍了本项目的可选配置，即 `opt/` 目录下的内容。
- [Conf Directory](./ConfDirectory.md) - 配置目录说明，详细说明如何配置本项目，即 `conf/` 目录下的内容。
