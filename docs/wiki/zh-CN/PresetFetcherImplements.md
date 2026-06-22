# Preset Fetcher Implements - 预设抓取器实现

## 说明

本文档详细说明了本项目内置的所有抓取器。

所有内置抓取器的代码均位于 `com.dwarfeng.fdr.impl.handler.fetcher` 包的下属包中，
您可以通过查看该包下的所有类来了解本项目内置的所有抓取器。

项目中所有的内置抓取器如下：

| 类型              | 名称                  | 说明                          |
|-----------------|---------------------|-----------------------------|
| `kafka.dcti`    | DctiKafkaFetcher    | 从 Kafka topic 消费 dcti 协议消息。 |
| `kafka.dct`     | DctKafkaFetcher     | 从 Kafka topic 消费 dct 协议消息。  |
| `mock.hf`       | MockHfFetcher       | 使用粗粒度 tick 和批量补样生成高频随机数据。   |
| `mock.lf`       | MockLfFetcher       | 按轮询规则生成低频随机数据。              |
| `simulate.awg`  | SimulateAwgFetcher  | 按任意波形表生成模拟记录。               |
| `simulate.wave` | SimulateWaveFetcher | 按函数波形生成模拟记录。                |

以下各实现的示例参数均可作为 `FetcherInfo.param` 的参考。
实际使用时，需要结合部署环境中的 Spring Bean、Kafka topic、点位主键和采样频率进行调整。

## DctiKafkaFetcher

### 介绍

`DctiKafkaFetcher` 是基于 dcti 协议的 Kafka 抓取器。
该抓取器从指定 Kafka topic 中批量消费 dcti 协议消息，将消息转换为 `RecordInfo` 后写入记录链路。

该抓取器使用 Spring 容器中的 `KafkaListenerContainerFactory` 创建监听容器。
`listener_id` 同时作为 Kafka consumer group id，同一节点内应保持唯一。

### 参数

| 字段名                                          | 类型       | 说明                                                |
|----------------------------------------------|----------|---------------------------------------------------|
| `kafka_listener_container_factory_bean_name` | `String` | `KafkaListenerContainerFactory` 的 Spring Bean 名称。 |
| `topic`                                      | `String` | Kafka 主题名称。                                       |
| `listener_id`                                | `String` | Kafka 监听器 ID，同时作为 consumer group id。              |

### 示例参数

```json
{
  "#kafka_listener_container_factory_bean_name": "KafkaListenerContainerFactory 的 Spring Bean 名称。",
  "kafka_listener_container_factory_bean_name": "dctiKafkaFetcherKafkaListenerContainerFactory",
  "#topic": "Kafka 主题名称。",
  "topic": "fdr.dcti",
  "#listener_id": "Kafka 监听器 ID，同时作为 consumer group id；同一节点内必须唯一。",
  "listener_id": "fdr.fetcher.kafka.dcti"
}
```

## DctKafkaFetcher

### 介绍

`DctKafkaFetcher` 是基于 dwarfeng-dct 协议的 Kafka 抓取器。
该抓取器从指定 Kafka topic 中批量消费 dct 协议消息，通过指定的 `DataCodingHandler` 解码消息，
然后将数据转换为 `RecordInfo` 写入记录链路。

该抓取器使用 Spring 容器中的 `KafkaListenerContainerFactory` 创建监听容器。
`listener_id` 同时作为 Kafka consumer group id，同一节点内应保持唯一。

### 参数

| 字段名                                          | 类型       | 说明                                                  |
|----------------------------------------------|----------|-----------------------------------------------------|
| `kafka_listener_container_factory_bean_name` | `String` | `KafkaListenerContainerFactory` 的 Spring Bean 名称。   |
| `topic`                                      | `String` | Kafka 主题名称。                                         |
| `listener_id`                                | `String` | Kafka 监听器 ID，同时作为 consumer group id。                |
| `data_coding_handler_bean_name`              | `String` | `DataCodingHandler` 的 Spring Bean 名称，用于解码 dct 协议消息。 |

### 示例参数

```json
{
  "#kafka_listener_container_factory_bean_name": "KafkaListenerContainerFactory 的 Spring Bean 名称。",
  "kafka_listener_container_factory_bean_name": "dctKafkaFetcherKafkaListenerContainerFactory",
  "#topic": "Kafka 主题名称。",
  "topic": "fdr.dct",
  "#listener_id": "Kafka 监听器 ID，同时作为 consumer group id；同一节点内必须唯一。",
  "listener_id": "fdr.fetcher.kafka.dct",
  "#data_coding_handler_bean_name": "DataCodingHandler 的 Spring Bean 名称，用于解码 dct 协议消息。",
  "data_coding_handler_bean_name": "dctKafkaFetcherDataCodingHandler"
}
```

## MockHfFetcher

### 介绍

`MockHfFetcher` 是高频模拟抓取器。该抓取器使用粗粒度 tick 调度，在每次 tick 中按目标频率批量补样，生成连续的随机记录。

该实现适合在开发、验证或压力测试中模拟高频点位数据。为避免调度延迟导致一次性补发过多样本，
可通过 `max_samples_per_tick`、`misfire_threshold` 和 `misfire_postpone` 控制补样与误触发处理。

### 参数

| 字段名                    | 类型          | 说明                                         |
|------------------------|-------------|--------------------------------------------|
| `point_key`            | `LongIdKey` | 抓取数据写入的点位主键。                               |
| `tick_period`          | `long`      | 高频补样任务 tick 周期，单位毫秒，必须大于 0。                |
| `frequency`            | `long`      | 目标采样频率，单位 Hz，必须大于 0。                       |
| `generator_type`       | `String`    | 数据生成类型。                                    |
| `random_seed`          | `Long`      | 随机种子；为 `null` 时使用随机种子，填写 long 值时生成可复现随机序列。 |
| `max_samples_per_tick` | `int`       | 单次 tick 允许补发的最大样本数，必须大于 0。                 |
| `misfire_threshold`    | `long`      | 调度延迟自愈阈值，单位毫秒，必须大于 0。                      |
| `misfire_postpone`     | `long`      | 触发 misfire 后基于当前时间向后推迟的毫秒数，必须大于 0。         |

`generator_type` 的合法值为：`int`、`long`、`float`、`double`、`gaussian`、`boolean`、`string`、`int_string`、`long_string`、
`float_string`、`double_string`、`gaussian_string`、`boolean_string`。

### 示例参数

```json
{
  "#point_key": "抓取数据写入的点位主键。",
  "point_key": {
    "long_id": 12450
  },
  "#tick_period": "高频补样任务 tick 周期，单位毫秒，必须大于 0。",
  "tick_period": 1000,
  "#frequency": "目标采样频率，单位 Hz，必须大于 0。",
  "frequency": 100,
  "#generator_type": "数据生成类型，合法值为 int、long、float、double、gaussian、boolean、string、int_string、long_string、float_string、double_string、gaussian_string、boolean_string。",
  "generator_type": "double",
  "#random_seed": "随机种子。为 null 时使用随机种子；填写 long 值时生成可复现随机序列。",
  "random_seed": null,
  "#max_samples_per_tick": "单次 tick 允许补发的最大样本数，必须大于 0；超过时丢弃本 tick。",
  "max_samples_per_tick": 200,
  "#misfire_threshold": "调度延迟自愈阈值，单位毫秒；实际延迟超过该值时重置调度基线。",
  "misfire_threshold": 5000,
  "#misfire_postpone": "触发 misfire 后基于当前时间向后推迟的毫秒数。",
  "misfire_postpone": 1000
}
```

## MockLfFetcher

### 介绍

`MockLfFetcher` 是低频模拟抓取器。该抓取器按照固定频率、固定延迟或 cron 表达式触发，每次触发生成一条随机记录并写入记录链路。

该实现适合模拟低频点位数据，也可以通过 `fetch_before_delay` 和 `fetch_after_delay` 模拟抓取前后的耗时。

### 参数

| 字段名                  | 类型          | 说明                                     |
|----------------------|-------------|----------------------------------------|
| `point_key`          | `LongIdKey` | 抓取数据写入的点位主键。                           |
| `poll_type`          | `String`    | 轮询类型。                                  |
| `poll_setting`       | `String`    | 轮询参数。                                  |
| `generator_type`     | `String`    | 数据生成类型。                                |
| `random_seed`        | `Long`      | 随机种子；为空字符串时使用随机种子，填写 long 值时生成可复现随机序列。 |
| `fetch_before_delay` | `long`      | 每次生成记录前的延迟毫秒数，必须大于等于 0，0 表示不延迟。        |
| `fetch_after_delay`  | `long`      | 每次生成记录后的延迟毫秒数，必须大于等于 0，0 表示不延迟。        |

`poll_type` 的合法值为：`fixed_rate`、`fixed_delay`、`cron`、`safe_fixed_rate`、`safe_fixed_delay`、`safe_cron`。

`poll_setting` 的格式与 `poll_type` 有关：

| `poll_type`                            | `poll_setting` 说明                                 |
|----------------------------------------|---------------------------------------------------|
| `fixed_rate` / `fixed_delay`           | 毫秒数，必须大于 0。                                       |
| `cron`                                 | cron 表达式。                                         |
| `safe_fixed_rate` / `safe_fixed_delay` | 三段式参数：`基础毫秒数;misfireThreshold;misfirePostpone`。   |
| `safe_cron`                            | 三段式参数：`cron表达式;misfireThreshold;misfirePostpone`。 |

`generator_type` 的合法值与 `MockHfFetcher` 相同。

### 示例参数

```json
{
  "#point_key": "抓取数据写入的点位主键。",
  "point_key": {
    "long_id": 12450
  },
  "#poll_type": "轮询类型，合法值为 fixed_rate、fixed_delay、cron、safe_fixed_rate、safe_fixed_delay、safe_cron。",
  "poll_type": "fixed_rate",
  "#poll_setting": "轮询参数。fixed_rate/fixed_delay 使用毫秒数；cron 使用 cron 表达式；safe 类型使用三段式参数。",
  "poll_setting": "1000",
  "#generator_type": "数据生成类型，合法值为 int、long、float、double、gaussian、boolean、string、int_string、long_string、float_string、double_string、gaussian_string、boolean_string。",
  "generator_type": "double",
  "#random_seed": "随机种子。为空字符串时使用随机种子；填写 long 值时生成可复现随机序列。",
  "random_seed": null,
  "#fetch_before_delay": "每次生成记录前的延迟毫秒数，0 表示不延迟。",
  "fetch_before_delay": 0,
  "#fetch_after_delay": "每次生成记录后的延迟毫秒数，0 表示不延迟。",
  "fetch_after_delay": 0
}
```

## SimulateAwgFetcher

### 介绍

`SimulateAwgFetcher` 是任意波形模拟抓取器。该抓取器根据配置中的波表、插值方式、幅值、偏置与采样频率生成记录。

该实现通过周期性 tick 批量补样，适合模拟由离散波表描述的周期信号。波表至少需要包含 2 个数值，插值方式可以选择阶梯插值或线性插值。

### 参数

| 字段名                    | 类型          | 说明                         |
|------------------------|-------------|----------------------------|
| `point_key`            | `LongIdKey` | 输出记录所属点位主键。                |
| `tick_period`          | `long`      | 抓取器 tick 调度周期，单位毫秒，必须大于 0。 |
| `sample_frequency`     | `long`      | 输出采样频率，单位 Hz，必须大于 0。       |
| `wave_table_frequency` | `double`    | 波表原始采样频率，单位 Hz，必须大于 0。     |
| `wave_table`           | `double[]`  | 任意波形表，至少包含 2 个 Double 数值。  |
| `interpolation`        | `String`    | 插值方式，合法值为 `step`、`linear`。 |
| `amplitude`            | `double`    | 输出缩放系数，必须大于等于 0。           |
| `offset`               | `double`    | 输出偏置。                      |
| `max_samples_per_tick` | `int`       | 单 tick 最大补样数量，必须大于 0。      |
| `misfire_threshold`    | `long`      | 调度延迟判定阈值，单位毫秒，必须大于等于 0。    |
| `misfire_postpone`     | `long`      | 发生误触发时推迟下次调度的毫秒数，必须大于等于 0。 |

### 示例参数

```json
{
  "#point_key": "输出记录所属点位主键。",
  "point_key": {
    "long_id": 1
  },
  "#tick_period": "抓取器 tick 调度周期，单位毫秒，必须大于 0。",
  "tick_period": 10,
  "#sample_frequency": "输出采样频率，单位 Hz，必须大于 0。",
  "sample_frequency": 1000,
  "#wave_table_frequency": "波表原始采样频率，单位 Hz，必须大于 0。",
  "wave_table_frequency": 1000.0,
  "#wave_table": "任意波形表，至少包含 2 个 Double 数值。",
  "wave_table": [
    0.0,
    1.0,
    0.0,
    -1.0
  ],
  "#interpolation": "插值方式，合法值为 step、linear。",
  "interpolation": "linear",
  "#amplitude": "输出缩放系数，必须大于等于 0。",
  "amplitude": 1.0,
  "#offset": "输出偏置。",
  "offset": 0.0,
  "#max_samples_per_tick": "单 tick 最大补样数量，必须大于 0。",
  "max_samples_per_tick": 20000,
  "#misfire_threshold": "调度延迟判定阈值，单位毫秒，必须大于等于 0。",
  "misfire_threshold": 200,
  "#misfire_postpone": "发生误触发时推迟下次调度的毫秒数，必须大于等于 0。",
  "misfire_postpone": 100
}
```

## SimulateWaveFetcher

### 介绍

`SimulateWaveFetcher` 是函数波形模拟抓取器。该抓取器根据配置中的波形类型、周期、幅值、偏置与方波占空比，按采样频率生成记录。

该实现通过周期性 tick 批量补样，适合模拟常见函数波形。
`dc` 类型直接输出偏置值，不需要检查波形周期；`square` 类型会使用 `duty_cycle` 控制方波占空比。

### 参数

| 字段名                    | 类型          | 说明                                     |
|------------------------|-------------|----------------------------------------|
| `point_key`            | `LongIdKey` | 输出记录所属点位主键。                            |
| `tick_period`          | `long`      | 抓取器 tick 调度周期，单位毫秒，必须大于 0。             |
| `sample_frequency`     | `long`      | 输出采样频率，单位 Hz，必须大于 0。                   |
| `wave_type`            | `String`    | 函数波形类型。                                |
| `wave_period`          | `double`    | 波形周期，单位毫秒；除 `dc` 外必须大于 0。              |
| `amplitude`            | `double`    | 波形幅值，必须大于等于 0。                         |
| `offset`               | `double`    | 输出偏置；`dc` 类型直接输出该值。                    |
| `duty_cycle`           | `double`    | 方波占空比，仅 `square` 使用，必须在 0 到 1 之间且不含边界。 |
| `max_samples_per_tick` | `int`       | 单 tick 最大补样数量，必须大于 0。                  |
| `misfire_threshold`    | `long`      | 调度延迟判定阈值，单位毫秒，必须大于等于 0。                |
| `misfire_postpone`     | `long`      | 发生误触发时推迟下次调度的毫秒数，必须大于等于 0。             |

`wave_type` 的合法值为：`sine`、`cosine`、`square`、`triangle`、`sawtooth`、`dc`。

### 示例参数

```json
{
  "#point_key": "输出记录所属点位主键。",
  "point_key": {
    "long_id": 1
  },
  "#tick_period": "抓取器 tick 调度周期，单位毫秒，必须大于 0。",
  "tick_period": 10,
  "#sample_frequency": "输出采样频率，单位 Hz，必须大于 0。",
  "sample_frequency": 1000,
  "#wave_type": "函数波形类型，合法值为 sine、cosine、square、triangle、sawtooth、dc。",
  "wave_type": "sine",
  "#wave_period": "波形周期，单位毫秒。除 dc 外必须大于 0。",
  "wave_period": 1000.0,
  "#amplitude": "波形幅值，必须大于等于 0。",
  "amplitude": 1.0,
  "#offset": "输出偏置；dc 类型直接输出该值。",
  "offset": 0.0,
  "#duty_cycle": "方波占空比，仅 square 使用，必须在 0 到 1 之间且不含边界。",
  "duty_cycle": 0.5,
  "#max_samples_per_tick": "单 tick 最大补样数量，必须大于 0。",
  "max_samples_per_tick": 20000,
  "#misfire_threshold": "调度延迟判定阈值，单位毫秒，必须大于等于 0。",
  "misfire_threshold": 200,
  "#misfire_postpone": "发生误触发时推迟下次调度的毫秒数，必须大于等于 0。",
  "misfire_postpone": 100
}
```

## 参阅

- [Fetcher](./Fetcher.md) - 抓取器，详细说明了本项目的抓取器机制。
