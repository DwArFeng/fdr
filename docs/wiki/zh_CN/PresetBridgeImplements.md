# Preset Bridge Implements - 预设桥接器实现

## 说明

本文档详细说明了本项目内置的所有桥接器。

所有内置数据源的代码均位于 `com.dwarfeng.fdr.impl.handler.bridge` 包的下属包中，
您可以通过查看该包下的所有类以及其子包下的所有类来了解本项目内置的所有桥接器。

项目中所有的内置桥接器如下：

| 名称                                  | 说明                                     |
|-------------------------------------|----------------------------------------|
| [DrainBridge](#DrainBridge)         | 简单地丢弃掉所有的数据的桥接器，一般用于占位。                |
| [HibernateBridge](#HibernateBridge) | 使用 Hibernate 与关系型数据库交互的桥接器，支持多种不同的数据库。 |
| [InfluxdbBridge](#InfluxdbBridge)   | 使用 Influxdb 时序数据库的桥接器。                 |
| MockBridge                          | 模拟桥接器。                                 |
| MultiBridge                         | 多重桥接器，代理多个桥接器。                         |
| RedisBridge                         | 使用 Redis 与实时数据交互的桥接器。                  |

## DrainBridge

### 介绍

DrainBridge 是一个简单地丢弃掉所有的数据的桥接器，一般用于占位。

对于一些不需要记录的数据主题，可以使用该桥接器丢弃掉所有的数据。

DrainBridge 的写入操作为空实现，不会对数据进行任何操作；DrainBridge 的读取操作会抛出异常，不支持读取操作。

### 配置项

该桥接器的配置项如下：

```properties
###################################################
#                      drain                      #
###################################################
# Drain 桥接器不需要进行任何配置。
```

可以看到，DrainBridge 桥接器不需要进行任何配置。

## 保持器

DrainBridge 的保持器中的 `update` 方法为空实现，不会对数据进行任何操作；
`latest` 方法会抛出异常，不支持读取操作。

## 持久器

DrainBridge 的持久器中的 `record` 方法为空实现，不会对数据进行任何操作；
`lookup` 以及 `nativeQuery` 方法会抛出异常，不支持读取操作。

## HibernateBridge

### 介绍

HibernateBridge 是一个使用 Hibernate 与关系型数据库交互的桥接器，支持多种不同的数据库。

### 配置项

该桥接器的配置项如下：

```properties
###################################################
#                    hibernate                    #
###################################################
# 是否使用项目本身的 hibernate 配置。
bridge.hibernate.use_project_config=true
#
# 如果 bridge.hibernate.use_project_config 为 false，则需要指定以下的配置。
# ------------------------------
bridge.hibernate.jdbc.driver=com.mysql.cj.jdbc.Driver
bridge.hibernate.jdbc.url=jdbc:mysql://your-host-here:3306/fdr?serverTimezone=Asia/Shanghai&autoReconnect=true
bridge.hibernate.jdbc.username=root
bridge.hibernate.jdbc.password=your-password-here
bridge.hibernate.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
# 数据库的批量写入量，设置激进的值以提高数据库的写入效率。
bridge.hibernate.hibernate.jdbc.batch_size=100
# 数据库的批量抓取量，设置激进的值以提高数据库的读取效率。
bridge.hibernate.hibernate.jdbc.fetch_size=50
# 连接池最大活动连接数量
bridge.hibernate.data_source.max_active=20
# 连接池最小空闲连接数量
bridge.hibernate.data_source.min_idle=0
# ------------------------------
#
# 是否启用 NativeLookup 机制加速部分查询。
# 启用加速机制后，持久数据点、被过滤数据点、被触发数据点的大部分预设查询将通过 JDBC 直接实现（前提是必须使用指定的方言）。
# 如果原生数据库SQL是不支持的，该程序将会继续使用基于 hibernate criteria 机制的查询。
#
# 支持的方言:
#  1. org.hibernate.dialect.MySQL8Dialect。
#
# 欢迎大家持续贡献其它种类的数据库支持。
bridge.hibernate.hibernate.accelerate_enabled=true
```

可以看到，配置项有详细的注释，您可以根据注释进行配置。

本文对以下配置进行额外补充说明：

#### bridge.hibernate.use_project_config

该配置项用于指定是否使用项目本身的 hibernate 配置，这是因为除了桥接器之外，项目本身也使用了 Hibernate 存储数据点等其它数据，
因此桥接器的 Hibernate 配置可以使用项目本身的配置。

如果使用项目本身的配置，那么桥接器的部分配置将会被忽略，受影响的配置在配置文件中以备注的形式进行了标注，配置项清单如下：

- bridge.hibernate.jdbc.driver
- bridge.hibernate.jdbc.url
- bridge.hibernate.jdbc.username
- bridge.hibernate.jdbc.password
- bridge.hibernate.hibernate.dialect
- bridge.hibernate.hibernate.jdbc.batch_size
- bridge.hibernate.hibernate.jdbc.fetch_size
- bridge.hibernate.data_source.max_active
- bridge.hibernate.data_source.min_idle

需要注意的是，如果使用项目本身的配置，那么就意味着历史点位表将会位于项目本身的数据库中，这可能会对项目本身的数据库造成一定的压力。

# bridge.hibernate.hibernate.accelerate_enabled

该配置项用于指定是否启用 NativeLookup 机制加速部分查询。

本地加速机制源自 FDR 1.x，它通过使用 JDBC 直接执行原生 SQL 语句来加速部分查询，
这样可以避免 ORM 框架的性能损耗，最大限度地提高查询效率。

由于直接使用原生 SQL 语句，因此需要对每个不同的数据库定制不同的 SQL 语句，对于不支持的数据库，
程序将会使用基于 hibernate criteria 机制的查询。

本地加速机制会获取桥接器的 hibernate 的方言，如果 `bridge.hibernate.use_project_config` 为 `true`，则判断项目本身的方言；
如果 `bridge.hibernate.use_project_config` 为 `false`，则判断 `bridge.hibernate.hibernate.dialect`。

如果原生查询支持桥接器的 hibernate 的方言，则会启用加速机制，否则不会启用加速机制。

目前支持的方言有：

| 方言                                  | 数据库     |
|-------------------------------------|---------|
| org.hibernate.dialect.MySQL8Dialect | MySQL 8 |

## 保持器

HibernateBridge 不支持保持器。

## 持久器

HibernateBridge 的持久器中的 `record` 方法会将数据点存储到数据库中；`lookup` 方法会从数据库中查询数据点；
`nativeQuery` 暂不支持。

### 查看

#### 预设清单

HibernateBridge 的查看预设清单如下：

| 预设名                 | 说明   | 一般数据支持 | 被过滤数据支持 | 被触发数据支持 |
|---------------------|------|-------:|--------:|--------:|
| [default](#default) | 默认预设 |     支持 |      支持 |      支持 |

#### default

`default` 预设是查看方法支持的唯一预设，它支持一般数据、被过滤数据、被触发数据。

`default` 不需要任何查询参数。

### 原生查询

HibernateBridge 暂不支持原生查询。

## InfluxdbBridge

### 介绍

InfluxdbBridge 是一个使用 Influxdb 时序数据库的桥接器。

### 配置项

该桥接器的配置项如下：

```properties
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
```

可以看到，配置项有详细的注释，您可以根据注释进行配置。

## 保持器

HibernateBridge 不支持保持器。

## 持久器

InfluxdbBridge 的持久器中的 `record` 方法会将数据点存储到 Influxdb 中；`lookup` 方法会从 Influxdb 中查询数据点的原始值；
`nativeQuery` 方法会根据预设查询从 Influxdb 中查询数据点的开窗值或自定义的查询值。

### 查看

#### 预设清单

InfluxdbBridge 的查看预设清单如下：

| 预设名                   | 说明   | 一般数据支持 | 被过滤数据支持 | 被触发数据支持 |
|-----------------------|------|-------:|--------:|--------:|
| [default](#default-1) | 默认预设 |     支持 |      支持 |      支持 |

#### default

`default` 预设是查看方法支持的唯一预设，它支持一般数据、被过滤数据、被触发数据。

`default` 不需要任何查询参数。

### 原生查询

#### 预设清单

InfluxdbBridge 的原生查询预设清单如下：

| 预设名                                   | 说明   | 一般数据支持 | 被过滤数据支持 | 被触发数据支持 |
|---------------------------------------|------|-------:|--------:|--------:|
| [default](#default-2)                 | 默认预设 |     支持 |      支持 |      支持 |
| [aggregate_window](#aggregate_window) | 默认预设 |     支持 |      支持 |      支持 |
| [custom](#custom)                     | 默认预设 |     支持 |      支持 |      支持 |

#### default

`default` 预设是原生查询方法的默认预设，它支持一般数据、被过滤数据、被触发数据。

`default` 预设的行为与 `aggregate_window` 预设的行为完全一致，您可以参考 `aggregate_window` 预设的说明。

#### aggregate_window

`aggregate_window` 预设用于对数据点进行聚合开窗操作，该预设需要三个参数，如下：

| 索引 | 参数类型               | 说明                                                                        |
|---:|--------------------|---------------------------------------------------------------------------|
|  0 | `long`             | aggregateWindowEvery，即聚合窗口的大小，单位为毫秒。                                      |
|  1 | `long`             | aggregateWindowOffset，即聚合窗口的偏移量，单位为毫秒。                                    |
|  2 | `java.lang.String` | aggregateWindowFunction，即聚合窗口的函数，支持的函数有 `mean`、`sum`、`count`、`min`、`max`。 |

`aggregate_window` 预设会生成如下的 flux 语句：

```flux
from(bucket: "${xxx_bucket}")
 |> range(start: ${start_date}, stop: ${end_date})
 |> filter(fn: (r) => r["_measurement"] == "#{point_id_1}" or r["_measurement"] == "#{point_id_2}" ...)
 |> filter(fn: (r) => r["_field"] == "value")
 |> aggregateWindow(every: ${params[0]}ms, offset: ${params[1]}ms, fn:${params[2]})
```

#### custom

`custom` 预设用于对数据点进行自定义查询操作，该预设需要一个参数作为用户自定义的 flux 语句片段，
拼接在查询语句模板后面。

该预设需要一个参数，如下：

| 索引 | 参数类型               | 说明                              |
|---:|--------------------|---------------------------------|
|  0 | `java.lang.String` | fluxFragment，即用户自定义的 flux 语句片段。 |

`custom` 预设会生成如下的 flux 语句：

```flux
from(bucket: "${xxx_bucket}")
 |> range(start: ${start_date}, stop: ${end_date})
 |> filter(fn: (r) => r["_measurement"] == "#{point_id_1}" or r["_measurement"] == "#{point_id_2}" ...)
 |> filter(fn: (r) => r["_field"] == "value")
${params[0]}
```

需要注意的是，`custom` 预设的参数是一个 flux 语句片段，因此需要符合 flux 语法规范。
同时， **一定不要** 将用户输入的内容直接作为 fluxFragment 输入到参数数组中，这样会导致 flux 注入等安全问题。
