# ConfDirectory - 配置目录

## 总览

本项目的配置文件位于 `conf/` 目录下，包括：

```text
conf
├─database
│      connection.properties
│      performance.properties
│
├─dubbo
│      connection.properties
│
├─fdr
│      background.properties
│      bridge.properties
│      consume.properties
│      exception.properties
│      launcher.properties
│      push.properties
│      query.properties
│      record.properties
│      reset.properties
│      source.properties
│
├─redis
│      connection.properties
│      prefix.properties
│      timeout.properties
│
└─telqos
        connection.properties
```

鉴于大部分配置文件的配置项中都有详细地注释，此处将展示默认的配置，并重点说明一些必须要修改的配置项。

## database 文件夹

| 文件名                    | 说明        |
|------------------------|-----------|
| connection.properties  | 数据库连接配置文件 |
| performance.properties | 数据库性能配置文件 |

### connection.properties

数据库连接配置文件，除了标准的数据库配置四要素之外，还包括 Hibernate 的方言配置。

```properties
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://your-host-here:3306/fdr?serverTimezone=Asia/Shanghai&autoReconnect=true
jdbc.username=root
jdbc.password=your-password-here
hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

### performance.properties

数据库性能配置文件，使用默认值即可，或按照实际情况进行修改。

```properties
# 数据库的批量写入量，设置激进的值以提高数据库的写入效率。
hibernate.jdbc.batch_size=100
# 数据库的批量抓取量，设置激进的值以提高数据库的读取效率。
hibernate.jdbc.fetch_size=50
# 连接池最大活动连接数量
data_source.max_active=20
# 连接池最小空闲连接数量
data_source.min_idle=0
```

## dubbo 文件夹

| 文件名                   | 说明           |
|-----------------------|--------------|
| connection.properties | Dubbo 连接配置文件 |

### connection.properties

Dubbo 连接配置文件。

```properties
dubbo.registry.zookeeper.address=zookeeper://your-host-here:2181
dubbo.registry.zookeeper.timeout=3000
dubbo.protocol.dubbo.port=20000
dubbo.protocol.dubbo.host=your-host-here
dubbo.provider.group=
dubbo.consumer.snowflake.group=
```

其中，`dubbo.registry.zookeeper.address` 需要配置为 ZooKeeper 的地址，
`dubbo.protocol.dubbo.host` 需要配置为本机的 IP 地址。

如果您需要在本机启动多个 FDR 实例，那么需要为每个实例配置不同的 `dubbo.protocol.dubbo.port`。

如果您在本机上部署了多个项目，每个项目中都使用了 FDR，那么需要为每个项目配置不同的 `dubbo.provider.group`，
以避免微服务错误的调用。

## fdr 文件夹

| 文件名                   | 说明                           |
|-----------------------|------------------------------|
| background.properties | 后台服务配置文件，包括线程池的线程数及其它        |
| bridge.properties     | 桥接服务配置文件                     |
| consume.properties    | 消费服务配置文件                     |
| exception.properties  | ServiceException 的异常代码的偏移量配置 |
| launcher.properties   | 启动器配置文件                      |
| push.properties       | 推送服务配置文件                     |
| query.properties      | 查询服务配置文件                     |
| record.properties     | 记录服务配置文件                     |
| reset.properties      | 重置服务配置文件                     |
| source.properties     | 数据源配置文件                      |

### background.properties

后台服务配置文件，包括线程池的线程数及其它。

```properties
# 任务执行器的线程池数量范围。
executor.pool_size=50-75
# 任务执行器的队列容量。
executor.queue_capacity=100
# 任务执行器的保活时间（秒）。
executor.keep_alive=120
# 计划执行器的线程池数量范围。
scheduler.pool_size=10
```

### bridge.properties

桥接服务配置文件，核心配置之一。

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
keep.normal_data.type=mock
persist.normal_data.type=mock
keep.filtered_data.type=mock
persist.filtered_data.type=mock
keep.triggered_data.type=mock
persist.triggered_data.type=mock
#
###################################################
#                      drain                      #
###################################################
# Drain 桥接器不需要进行任何配置。
#
###################################################
#                      mock                       #
###################################################
# Mock 桥接器的随机种子。
bridge.mock.random_seed=0
#
# 更新模拟: 单条数据更新的延迟时间，单位为毫秒。
bridge.mock.update.delay=0
# 更新模拟: 数据更新前的延迟时间，单位为毫秒。
bridge.mock.update.before_delay=0
# 更新模拟: 数据更新后的延迟时间，单位为毫秒。
bridge.mock.update.after_delay=0
#
# 检查模拟: 单条数据查询最新值的延迟时间，单位为毫秒。
bridge.mock.latest.delay=0
# 检查模拟: 数据查询最新值前的延迟时间，单位为毫秒。
bridge.mock.latest.before_delay=0
# 检查模拟: 数据查询最新值后的延迟时间，单位为毫秒。
bridge.mock.latest.after_delay=0
#
# 记录模拟: 单条数据写入的延迟时间，单位为毫秒。
bridge.mock.record.delay=0
# 记录模拟: 数据写入前的延迟时间，单位为毫秒。
bridge.mock.record.before_delay=0
# 记录模拟: 数据写入后的延迟时间，单位为毫秒。
bridge.mock.record.after_delay=0
#
# 查看模拟: 模拟数据间隔，单位为毫秒。
bridge.mock.lookup.data_interval=1000
# 查看模拟: 单个数据查看的延迟时间，单位为毫秒。
bridge.mock.lookup.delay=0
# 查看模拟: 数据每个偏移量的延迟时间，单位为毫秒。
bridge.mock.lookup.offset_delay=0
# 查看模拟: 数据查看前的延迟时间，单位为毫秒。
bridge.mock.lookup.before_delay=0
# 查看模拟: 数据查看后的延迟时间，单位为毫秒。
bridge.mock.lookup.after_delay=0
#
# 原生查询模拟: 数据原生查询时，查询区间每秒的延迟时间，单位为毫秒。
# 查询的总延迟时间为: (查询区间的总长度 / 1000 + 1) * 每秒的延迟时间。
bridge.mock.native_query.delay_per_second=0
# 原生查询模拟: 数据原生查询前的延迟时间，单位为毫秒。
bridge.mock.native_query.before_delay=0
# 原生查询模拟: 数据原生查询后的延迟时间，单位为毫秒。
bridge.mock.native_query.after_delay=0
#
# Mock 桥接器的数据配置。
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
bridge.mock.data_config=[\
  {"point_id":1,"point_type":"int"},\
  {"point_id":2,"point_type":"int"},\
  ]
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
###################################################
#                      redis                      #
###################################################
# 是否使用项目本身的 redis 配置。
bridge.redis.use_project_config=true
#
# 如果 bridge.redis.use_project_config 为 false，则需要指定以下的配置。
# ------------------------------
# Redis 的主机名。
bridge.redis.host_name=your-host-here
# Redis 的端口号。
bridge.redis.port=6379
# Redis 的密码（如果没有密码则留空）。
bridge.redis.password=your-password-here
# Redis 客户端超时时间单位是毫秒。
bridge.redis.timeout=10000
# 最大空闲数。
bridge.redis.max_idle=300
# 控制一个 pool可分配多少个 jedis 实例, 设为0表示无限制。
bridge.redis.max_total=1000
# 最大建立连接等待时间。如果超过此时间将接到异常。设为 -1 表示无限制。
bridge.redis.max_wait_millis=-1
# 连接的最小空闲时间。
bridge.redis.min_evictable_idle_time_millis=300000
# 每次释放连接的最大数目。
bridge.redis.num_tests_per_eviction_run=1024
# 逐出扫描的时间间隔(毫秒) 如果为负数，则不运行逐出线程。
bridge.redis.time_between_eviction_runs_millis=30000
# 是否在从池中取出连接前进行检验，如果检验失败，则从池中去除连接并尝试取出另一个。
bridge.redis.test_on_borrow=true
# 在空闲时检查有效性。
bridge.redis.test_while_idle=true
# ------------------------------
# 一般数据的数据库键。
bridge.redis.dbkey.normal_data=dbkey.normal_data
# 被过滤数据的数据库键。
bridge.redis.dbkey.filtered_data=dbkey.filtered_data
# 被触发数据的数据库键。
bridge.redis.dbkey.triggered_data=dbkey.triggered_data
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
bridge.multi.delegates.keep.normal_data=xxx1,xxx2,xxx3
# 一般数据持久器的代理列表。
bridge.multi.delegates.persist.normal_data=xxx1,xxx2,xxx3
# 被过滤数据保持器的代理列表。
bridge.multi.delegates.keep.filtered_data=xxx1,xxx2,xxx3
# 被过滤数据持久器的代理列表。
bridge.multi.delegates.persist.filtered_data=xxx1,xxx2,xxx3
# 被触发数据保持器的代理列表。
bridge.multi.delegates.keep.triggered_data=xxx1,xxx2,xxx3
# 被触发数据持久器的代理列表。
bridge.multi.delegates.persist.triggered_data=xxx1,xxx2,xxx3
# ------------------------------
```

您不必对所有的配置项进行配置。

在项目第一次启动之前，您需要修改 `opt/opt-bridge.xml`，决定项目中需要使用哪些桥接器。您只需要修改使用的桥接器的配置。

### consume.properties

消费服务配置文件，核心配置之一。

```properties
#---------------------------------报警配置----------------------------------------
# 当消费者中的待消费元素超过缓存上限指定比例后，向日志中输入警告信息。
consume.threshold.warn=0.8
#---------------------------------配置说明----------------------------------------
# 消费者线程数：线程数越大，处理的能力越强，服务器负荷越重。
# consume.xxx.consumer_thread=1
# 缓存大小：缓存越大抗波动能力越强，数据实时性越低。
# 当缓存被占满时，会导致消费者阻塞。
# 数据占满缓存这一现象是需要尽力避免的，程序将在缓存占用量超过指定值的时候发出警报，并在缓存被占满的时候发出ERROR提示。
# consume.xxx.buffer_size=1000
# 批处理个数：批处理个数越多，平均每个元素消费速度越快，数据实时性越低。
# consume.xxx.batch_size=100
# 最大空闲时间：即使缓存中数据没有达到批处理最小个数，也不能在空闲超过指定的时间，数值越低，数据实时性越高，服务器负荷越重。
# 该值设置为小于0的数意味着禁用最大空闲时间检查，在最坏的情况下，此种设置会导致少于批处理个数的元素无限期的在缓存中等待。
# consume.xxx.max_idle_time=1000
#
#---------------------------------一般数据保持消费者----------------------------------------
consume.normal_keep.consumer_thread=1
consume.normal_keep.buffer_size=5000
consume.normal_keep.batch_size=500
consume.normal_keep.max_idle_time=1000
#
#---------------------------------一般数据持久消费者----------------------------------------
consume.normal_persist.consumer_thread=4
consume.normal_persist.buffer_size=1000
consume.normal_persist.batch_size=100
consume.normal_persist.max_idle_time=1000
#
#---------------------------------被过滤数据保持消费者----------------------------------------
consume.filtered_keep.consumer_thread=1
consume.filtered_keep.buffer_size=5000
consume.filtered_keep.batch_size=500
consume.filtered_keep.max_idle_time=1000
#
#---------------------------------被过滤数据持久消费者----------------------------------------
consume.filtered_persist.consumer_thread=4
consume.filtered_persist.buffer_size=1000
consume.filtered_persist.batch_size=100
consume.filtered_persist.max_idle_time=1000
#
#---------------------------------被触发数据保持消费者----------------------------------------
consume.triggered_keep.consumer_thread=1
consume.triggered_keep.buffer_size=5000
consume.triggered_keep.batch_size=500
consume.triggered_keep.max_idle_time=1000
#
#---------------------------------被触发数据持久消费者----------------------------------------
consume.triggered_persist.consumer_thread=4
consume.triggered_persist.buffer_size=1000
consume.triggered_persist.batch_size=100
consume.triggered_persist.max_idle_time=1000
```

本配置中的参数直接决定了消费服务的性能，您需要根据您的实际情况进行调整。

为了更加直观的观察调整后的效果，本服务提供了关于消费服务的 telnet 指令，您可以使用指令在 telnet 运维系统中动态修改参数，
并观察修改后的效果。在运维系统中的更改重启后会失效，因此，当您将参数调整到满意的程度后，您需要将参数修改到配置文件中。

### exception.properties

ServiceException 的异常代码的偏移量配置。

```properties
# fdr 工程自身的异常代号偏移量。
fdr.exception_code_offset=5000
# fdr 工程中 subgrade 的异常代号偏移量。
fdr.exception_code_offset.subgrade=0
# fdr 工程中 snowflake 的异常代号偏移量。
fdr.exception_code_offset.snowflake=1500
```

Subgrade 框架中，会将微服务抛出的异常映射为 `ServiceException`，每个 `ServiceException` 都有一个异常代码，
用于标识异常的类型。

如果您的项目中使用了多个基于 Subgrade 框架的微服务，那么，您需要为每个微服务配置一个异常代码偏移量，
以免不同的微服务生成异常代码相同的 `ServiceException`。

### launcher.properties

启动器配置文件，决定了启动时的一些行为，包括是否上线采集服务、重置支持等。可以按需修改。

```properties
# 程序启动完成后，是否重置过滤器支持。
launcher.reset_filter_support=true
# 程序启动完成后，是否重置触发器支持。
launcher.reset_trigger_support=true
# 程序启动完成后，是否重置映射器支持。
launcher.reset_mapper_support=true
# 程序启动完成后，是否重置清洗器支持。
launcher.reset_washer_support=true
# 程序启动完成后，开启记录的延时时间。
# 有些数据源以及推送器在启动后可能会需要一些时间进行自身的初始化，调整该参数以妥善的处理这些数据源和推送器。
# 该参数等于0，意味着启动后立即开启记录服务。
# 该参数小于0，意味着程序不主动开启记录服务，需要手动开启。
launcher.start_record_delay=3000
# 程序启动完成后，启动重置的延时时间。
# 有些数据仓库以及重置器在启动后可能会需要一些时间进行自身的初始化，调整该参数以妥善的处理这些数据源和推送器。
# 该参数等于0，意味着启动后立即启动重置服务。
# 该参数小于0，意味着程序不主动启动重置服务，需要手动启动。
launcher.start_reset_delay=30000
```

### push.properties

推送服务配置文件。

```properties
###################################################
#                     global                      #
###################################################
# 当前的推送器类型。
# 目前该项目支持的推送器类型有:
#   drain: 简单的丢弃掉所有消息的推送器。
#   multi: 同时将消息推送给所有代理的多重推送器。
#   native.kafka: 使用原生数据的基于Kafka消息队列的推送器。
#   log: 将消息输出到日志中的推送器。
#
# 对于一个具体的项目，很可能只用一个推送器。此时如果希望程序加载时只加载一个推送器，可以通过编辑
# opt/opt-push.xml 文件实现。
# 可以通过编辑 application-context-scan.xml 实现。
pusher.type=drain
#
###################################################
#                      drain                      #
###################################################
# drain推送器没有任何配置。
#
###################################################
#                      multi                      #
###################################################
# 代理的推送器，推送器之间以逗号分隔。
pusher.multi.delegate_types=native.kafka
#
###################################################
#                   native.kafka                  #
###################################################
# broker集群。
pusher.native.kafka.bootstrap_servers=your ip here like ip1:9092,ip2:9092,ip3:9092
# 连接属性。
pusher.native.kafka.acks=all
# 发送失败重试次数。
pusher.native.kafka.retries=3
pusher.native.kafka.linger=10
# 的批处理缓冲区大小。
pusher.native.kafka.buffer_memory=40960
# 批处理条数：当多个记录被发送到同一个分区时，生产者会尝试将记录合并到更少的请求中。这有助于客户端和服务器的性能。
pusher.native.kafka.batch_size=4096
# Kafka事务的前缀。
pusher.native.kafka.transaction_prefix=fdr.pusher.
# 一般数据更新时向 Kafka 发送的主题。
pusher.native.kafka.topic.normal_updated=fdr.pusher.normal_updated
# 一般数据记录时向 Kafka 发送的主题。
pusher.native.kafka.topic.normal_recorded=fdr.pusher.normal_recorded
# 被过滤数据更新时向 Kafka 发送的主题。
pusher.native.kafka.topic.filtered_updated=fdr.pusher.filtered_updated
# 被过滤数据记录时向 Kafka 发送的主题。
pusher.native.kafka.topic.filtered_recorded=fdr.pusher.filtered_recorded
# 被触发数据更新时向 Kafka 发送的主题。
pusher.native.kafka.topic.triggered_updated=fdr.pusher.triggered_updated
# 被触发数据记录时向 Kafka 发送的主题。
pusher.native.kafka.topic.triggered_recorded=fdr.pusher.triggered_recorded
# 记录功能重置时向 Kafka 发送的主题。
pusher.native.kafka.topic.record_reset=fdr.pusher.record_reset
# 映射功能重置时向 Kafka 发送的主题。
pusher.native.kafka.topic.map_reset=fdr.pusher.map_reset
#
###################################################
#                       log                       #
###################################################
# 记录日志的等级，由低到高依次是 TRACE, DEBUG, INFO, WARN, ERROR。
pusher.log.log_level=INFO
```

您不必对所有的配置项进行配置。

在项目第一次启动之前，您需要修改 `opt/opt-pusher.xml`，决定项目中需要使用哪些推送器。您只需要修改使用的推送器的配置。

### query.properties

查询服务配置文件。

```properties
#--------------------------------------------查询参数配置说明--------------------------------------------
# 查询流程示意图。
# |startDate                     endDate'|startDate'                    endDate'|startDate'      endDate|
# |----------------period----------------|----------------period----------------|--------period---------|
# |-----page-----|-----page-----|--page--|-----page-----|-----page-----|--page--|-----page-----|--page--|
# 其中，period 使用不同的 startDate 和 endDate 对数据库做 between ，这样可以避免 offset 过大导致的性能问题。
# 对于每一个 period，使用分页控制数据的最大量，避免一个查看方法执行时间过长。
#
# 查询时每一个 period 的最大跨度。
# query.xxx.max_period_span=86400000
# 查询是每一页的最大数据量。
# query.xxx.max_page_size=1000
#
#-------------------------------------------一般查询参数配置说明-------------------------------------------
query.normal.max_period_span=86400000
query.normal.max_page_size=1000
#
#------------------------------------------被过滤查询参数配置说明-------------------------------------------
query.filtered.max_period_span=86400000
query.filtered.max_page_size=1000
#
#------------------------------------------被触发查询参数配置说明-------------------------------------------
query.triggered.max_period_span=86400000
query.triggered.max_page_size=1000
```

本配置中的参数可以优化查询时的性能，避免服务请求持久器时一次性请求过多数据导致的性能问题。
您可以根据自身服务器性能调节该参数。

如果您的服务只使用了持久器的本地查询功能，您可以不必修改该配置。

### record.properties

消费服务配置文件，核心配置之一。

```properties
#---------------------------------报警配置----------------------------------------
# 当记录者中的待消费元素超过缓存上限指定比例后，向日志中输入警告信息。
record.threshold.warn=0.8
#---------------------------------配置说明----------------------------------------
# 记录者线程数：线程数越大，处理的能力越强，服务器负荷越重。
# record.consumer_thread=1
# 缓存大小：缓存越大抗波动能力越强，数据实时性越低。
# 当缓存被占满时，会导致记录者阻塞。
# 数据占满缓存这一现象是需要尽力避免的，程序将在缓存占用量超过指定值的时候发出警报，并在缓存被占满的时候发出ERROR提示。
# record.buffer_size=1000
#
record.consumer_thread=1
record.buffer_size=1000
```

本配置中的参数直接决定了消费服务的性能，您需要根据您的实际情况进行调整。

为了更加直观的观察调整后的效果，本服务提供了关于消费服务的 telnet 指令，您可以使用指令在 telnet 运维系统中动态修改参数，
并观察修改后的效果。在运维系统中的更改重启后会失效，因此，当您将参数调整到满意的程度后，您需要将参数修改到配置文件中。

### reset.properties

重置服务配置文件。

```properties
###################################################
#                      never                      #
###################################################
# Never 推送器没有任何配置。
#
###################################################
#                   fixed_delay                   #
###################################################
# 重置的间隔。
resetter.fixed_delay.delay=43200000
#
###################################################
#                   fixed_rate                    #
###################################################
# 重置的间隔。
resetter.fixed_rate.rate=43200000
#
###################################################
#                      cron                       #
###################################################
# 执行重置的 CRON 表达式。
resetter.cron.cron=0 0 1 * * *
#
###################################################
#                      dubbo                      #
###################################################
# Dubbo 推送器没有任何配置。
```

您不必对所有的配置项进行配置。

在项目第一次启动之前，您需要修改 `opt/opt-resetter.xml`，决定项目中需要使用哪些重置器。您只需要修改使用的重置器的配置。

### source.properties

数据源配置文件，核心配置之一。

```properties
###################################################
#                kafka.dwarfeng_dct               #
###################################################
# 引导服务器集群。
source.kafka.dwarfeng_dct.bootstrap_servers=your-ip1:9092,your-ip2:9092,your-ip3:9092
# 会话的超时限制: 如果consumer在这段时间内没有发送心跳信息，一次 rebalance 将会产生。
# 该值必须在[group.min.session.timeout.ms, group.max.session.timeout.ms]范围内，默认: 10000。
source.kafka.dwarfeng_dct.session_timeout_ms=10000
# 新的 group 加入 topic 时，从什么位置开始消费。
source.kafka.dwarfeng_dct.auto_offset_reset=latest
# 监听器启用的消费者的线程数。
# 每一个线程都会启动一个 KafkaConsumer，每个 KafkaConsumer 都会占用一个 partition。
# 程序分布式部署时，所有节点的线程数之和应该小于等于 topic 的 partition 数。
source.kafka.dwarfeng_dct.concurrency=2
# 监听器调用 KafkaConsumer.poll(Duration) 方法的超时时间，如果超过这个时间还没有拉取到数据，则返回空列表。
source.kafka.dwarfeng_dct.poll_timeout=3000
# 监听器的 id，每一个节点的监听器 id 都应与该节点的其它 kafka 监听器的 id 不同。
# 该设置会覆盖 kafka 的 group.id 设置，因此无需设置 group.id。
source.kafka.dwarfeng_dct.listener_id=fdr.source.dwarfeng_dct
# 监听器的目标 topic。
source.kafka.dwarfeng_dct.listener_topic=dwarfeng_dct.data_info
# 监听器的最大拉取数据量。当拉取到的数据量达到这个值时，会立即返回，不会等待 poll_timeout。
source.kafka.dwarfeng_dct.max_poll_records=100
# 监听器的最大拉取间隔。如果当前时间距离监听器上一次拉取数据的时间超过了这个值，一次 rebalance 将会产生。
source.kafka.dwarfeng_dct.max_poll_interval_ms=300000
#
###################################################
#                    kafka.dcti                   #
###################################################
# 引导服务器集群。
source.kafka.dcti.bootstrap_servers=your-ip1:9092,your-ip2:9092,your-ip3:9092
# 会话的超时限制: 如果consumer在这段时间内没有发送心跳信息，一次 rebalance 将会产生。
# 该值必须在[group.min.session.timeout.ms, group.max.session.timeout.ms]范围内，默认: 10000。
source.kafka.dcti.session_timeout_ms=10000
# 新的 group 加入 topic 时，从什么位置开始消费。
source.kafka.dcti.auto_offset_reset=latest
# 监听器启用的消费者的线程数。
# 每一个线程都会启动一个 KafkaConsumer，每个 KafkaConsumer 都会占用一个 partition。
# 程序分布式部署时，所有节点的线程数之和应该小于等于 topic 的 partition 数。
source.kafka.dcti.concurrency=2
# 监听器调用 KafkaConsumer.poll(Duration) 方法的超时时间，如果超过这个时间还没有拉取到数据，则返回空列表。
source.kafka.dcti.poll_timeout=3000
# 监听器的 id，每一个节点的监听器 id 都应与该节点的其它 kafka 监听器的 id 不同。
# 该设置会覆盖 kafka 的 group.id 设置，因此无需设置 group.id。
source.kafka.dcti.listener_id=fdr.source.dcti
# 监听器的目标 topic。
source.kafka.dcti.listener_topic=dcti.data_info
# 监听器的最大拉取数据量。当拉取到的数据量达到这个值时，会立即返回，不会等待 poll_timeout。
source.kafka.dcti.max_poll_records=100
# 监听器的最大拉取间隔。如果当前时间距离监听器上一次拉取数据的时间超过了这个值，一次 rebalance 将会产生。
source.kafka.dcti.max_poll_interval_ms=300000
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
```

您不必对所有的配置项进行配置。

在项目第一次启动之前，您需要修改 `opt/opt-source.xml`，决定项目中需要使用哪些数据源。您只需要修改使用的数据源的配置。

## redis 文件夹

| 文件名                   | 说明   |
|-----------------------|------|
| connection.properties | 连接配置 |
| prefix.properties     | 前缀配置 |
| timeout.properties    | 超时配置 |

### connection.properties

Redis 连接配置文件。

```properties
# ip地址
redis.hostName=your-host-here
# 端口号
redis.port=6379
# 如果有密码
redis.password=your-password-here
# 客户端超时时间单位是毫秒 默认是2000
redis.timeout=10000
# 最大空闲数
redis.maxIdle=300
# 控制一个 pool可分配多少个 jedis 实例, 设为0表示无限制。
redis.maxTotal=1000
# 最大建立连接等待时间。如果超过此时间将接到异常。设为-1表示无限制。
redis.maxWaitMillis=1000
# 连接的最小空闲时间 默认1800000毫秒(30分钟)
redis.minEvictableIdleTimeMillis=300000
# 每次释放连接的最大数目,默认3
redis.numTestsPerEvictionRun=1024
# 逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
redis.timeBetweenEvictionRunsMillis=30000
# 是否在从池中取出连接前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个
redis.testOnBorrow=true
# 在空闲时检查有效性, 默认false
redis.testWhileIdle=true
# redis.sentinel.host1=172.20.1.230
# redis.sentinel.port1=26379
# redis.sentinel.host2=172.20.1.231
# redis.sentinel.port2=26379
# redis.sentinel.host3=172.20.1.232
# redis.sentinel.port3=26379
```

### prefix.properties

Redis 前缀配置文件。

```properties
#------------------------------------------------------------------------------------
#  缓存时实体的键的格式
#------------------------------------------------------------------------------------
# 数据点对象的主键格式。
cache.prefix.entity.point=entity.point.
# 过滤器信息对象的主键格式。
cache.prefix.entity.filter_info=entity.filter_info.
# 触发器信息对象的主键格式。
cache.prefix.entity.trigger_info=entity.trigger_info.
# 过滤器支持对象的主键格式。
cache.prefix.entity.filter_support=entity.filter_support.
# 触发器支持对象的主键格式。
cache.prefix.entity.trigger_support=entity.trigger_support.
# 映射器支持对象的主键格式。
cache.prefix.entity.mapper_support=entity.mapper_support.
# 清洗器信息对象的主键格式。
cache.prefix.entity.washer_info=entity.washer_info.
# 清洗器支持对象的主键格式。
cache.prefix.entity.washer_support=entity.washer_support.
#------------------------------------------------------------------------------------
#  缓存时列表的键的格式
#------------------------------------------------------------------------------------
# 数据点对象对应的有效过滤器信息列表的主键格式。
cache.prefix.list.enabled_filter_info=list.enabled_filter_info.
# 数据点对象对应的有效触发器信息列表的主键格式。
cache.prefix.list.enabled_trigger_info=list.enabled_trigger_info.
# 数据点对象对应的有效清洗器信息列表的主键格式。
cache.prefix.list.enabled_washer_info=list.enabled_washer_info.
```

Redis 利用该配置文件，为缓存的主键添加前缀，以示区分。

如果您的项目包含其它使用 Redis 的模块，您可以修改该配置文件，以避免不同项目的同名实体前缀冲突，相互覆盖。

一个典型的前缀更改方式是在前缀的头部添加项目的名称，如：

```properties
# 数据点对象的主键格式。
cache.prefix.entity.point=fdr.entity.point.
# etc...
```

### timeout.properties

Redis 缓存的超时配置文件。

```properties
#------------------------------------------------------------------------------------
#  实体缓存时的超时时间
#------------------------------------------------------------------------------------
# 数据点对象缓存的超时时间。
cache.timeout.entity.point=3600000
# 过滤器信息对象缓存的超时时间。
cache.timeout.entity.filter_info=3600000
# 触发器信息对象缓存的超时时间。
cache.timeout.entity.trigger_info=3600000
# 过滤器支持对象缓存的超时时间。
cache.timeout.entity.filter_support=3600000
# 触发器支持对象缓存的超时时间。
cache.timeout.entity.trigger_support=3600000
# 映射器支持对象缓存的超时时间。
cache.timeout.entity.mapper_support=3600000
# 清洗器信息对象缓存的超时时间。
cache.timeout.entity.washer_info=3600000
# 清洗器支持对象缓存的超时时间。
cache.timeout.entity.washer_support=3600000
#------------------------------------------------------------------------------------
#  键值列表缓存时的超时时间
#------------------------------------------------------------------------------------
# 使能过滤器信息的超时时间。
cache.timeout.key_list.enabled_filter_info=3600000
# 使能触发器信息的超时时间。
cache.timeout.key_list.enabled_trigger_info=3600000
# 使能清洗器信息的超时时间。
cache.timeout.key_list.enabled_washer_info=3600000
```

如果您希望缓存更快或更慢地过期，您可以修改该配置文件。

## telqos 文件夹

| 文件名                   | 说明   |
|-----------------------|------|
| connection.properties | 连接配置 |

### connection.properties

Telqos 连接配置文件。

```properties
#Telnet 端口
telqos.port=23
#字符集
telqos.charset=UTF-8
#白名单表达式
telqos.whitelist_regex=
#黑名单表达式
telqos.blacklist_regex=
```

如果您的项目中有多个包含 Telqos 模块的服务，您应该修改 `telqos.port` 的值，以避免端口冲突。

请根据操作系统的默认字符集，修改 `telqos.charset` 的值，以避免乱码。一般情况下，Windows 系统的默认字符集为 `GBK`，
Linux 系统的默认字符集为 `UTF-8`。

如果您希望限制 Telqos 的使用范围，您可以修改 `telqos.whitelist_regex` 和 `telqos.blacklist_regex` 的值。
