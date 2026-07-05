# Preset Resetter Implements - 预设重置器实现

## 说明

本文档详细说明了本项目内置的所有重置器。

所有内置重置器的代码均位于 `com.dwarfeng.fdr.impl.handler.resetter` 包中，
您可以通过查看该包下的所有类来了解本项目内置的所有重置器。

项目中所有的内置重置器如下：

| 名称                 | 触发方式 | 说明                       |
|--------------------|------|--------------------------|
| NeverResetter      | 无    | 永远不主动执行重置，通常作为占位实现。      |
| FixedDelayResetter | 被动   | 按固定延迟周期执行记录功能和映射功能重置。    |
| FixedRateResetter  | 被动   | 按固定频率周期执行记录功能和映射功能重置。    |
| CronResetter       | 被动   | 按 CRON 表达式执行记录功能和映射功能重置。 |
| DubboResetter      | 主动   | 通过 Dubbo 远程入口接收外部重置请求。   |

重置器不使用 `FetcherInfo.type`、`MapperInfo.type` 这类业务类型字段。
实际启用哪些重置器，由 `opt/opt-resetter.xml` 中的 Spring `include-filter` 决定。

## NeverResetter

### 介绍

`NeverResetter` 是永远不执行重置的重置器。

该实现的 `start()` 和 `stop()` 均为空操作，不会注册定时任务，也不会暴露远程服务。
当项目不希望通过重置器自动或远程触发重置时，可以使用该实现作为显式占位。

即使只加载 `NeverResetter`，
Telqos 的 `reset --reset-record`、`reset --reset-map`、`reset --reset-fetch` 仍然可以手动执行重置，
因为手动重置直接调用 `ResetQosService`，不依赖重置器自身触发。

### 配置项

`NeverResetter` 不需要进行任何配置。

```properties
###################################################
#                      never                      #
###################################################
# Never 重置器没有任何配置。
```

### 适用场景

- 只希望保留 Telqos 手动重置能力。
- 暂时不希望启用定时重置或远程重置。
- 需要在 `opt-resetter.xml` 中保留一个明确的占位实现。

## FixedDelayResetter

### 介绍

`FixedDelayResetter` 是固定延迟重置器。

该实现启动时会向 `ThreadPoolTaskScheduler` 注册固定延迟任务。
每次任务执行完成后，调度器等待指定的 `delay` 时间，再执行下一次任务。

任务触发后会依次执行记录功能重置和映射功能重置。
如果任一重置动作抛出异常，异常会被记录到日志中，本次调度不会继续扩散异常。

### 配置项

该重置器的配置项如下：

```properties
###################################################
#                   fixed_delay                   #
###################################################
# 重置的间隔。
com.dwarfeng.fdr.resetter.fixed_delay.delay=43200000
```

- `com.dwarfeng.fdr.resetter.fixed_delay.delay`：两次任务之间的固定延迟，单位为毫秒。

### 适用场景

- 希望周期性刷新记录本地缓存和映射本地缓存。
- 希望下一次调度一定发生在上一次任务完成之后。
- 重置动作耗时不稳定，且不希望调度任务堆积。

## FixedRateResetter

### 介绍

`FixedRateResetter` 是固定频率重置器。

该实现启动时会向 `ThreadPoolTaskScheduler` 注册固定频率任务。
调度器按照指定的 `rate` 周期触发任务，不以“上一次任务完成后再等待固定时间”作为下一次调度依据。

任务触发后会依次执行记录功能重置和映射功能重置。该实现适合对重置周期有较强时间节奏要求的场景。

### 配置项

该重置器的配置项如下：

```properties
###################################################
#                   fixed_rate                    #
###################################################
# 重置的间隔。
com.dwarfeng.fdr.resetter.fixed_rate.rate=43200000
```

- `com.dwarfeng.fdr.resetter.fixed_rate.rate`：固定调度周期，单位为毫秒。

### 适用场景

- 希望以固定频率刷新记录本地缓存和映射本地缓存。
- 重置动作通常较快，且不会接近或超过调度周期。
- 运维策略更关注触发频率，而不是任务完成后的空闲时间。

## CronResetter

### 介绍

`CronResetter` 是使用 CRON 表达式定时重置的重置器。

该实现启动时会根据配置的 CRON 表达式注册计划任务。任务触发后会依次执行记录功能重置和映射功能重置。

与固定延迟和固定频率相比，CRON 更适合表达每天固定时间、每周固定日期等运维策略。
例如，可以在业务低峰期定时刷新记录本地缓存和映射本地缓存。

### 配置项

该重置器的配置项如下：

```properties
###################################################
#                      cron                       #
###################################################
# 执行重置的 CRON 表达式。
com.dwarfeng.fdr.resetter.cron.cron=0 0 1 * * *
```

- `com.dwarfeng.fdr.resetter.cron.cron`：执行重置任务的 CRON 表达式。

### 适用场景

- 希望在每天固定时间执行重置。
- 希望避开业务高峰期刷新本地缓存。
- 希望使用 CRON 表达式描述复杂周期。

## DubboResetter

### 介绍

`DubboResetter` 是使用 Dubbo 微服务实现的重置器。

该实现启动时会暴露 `DubboResetService` 服务。外部调用者可以通过远程接口触发记录功能重置或映射功能重置。

在集群部署中，调用者通常会使用 Dubbo 的广播调用能力，例如 `broadcast2`，对集群内所有节点广播重置操作。
每个节点上的 `DubboResetter` 收到请求后，都会通过本节点的 `Context` 执行对应的本地重置动作。

`DubboResetter` 当前暴露的远程重置能力如下：

| 方法              | 说明      |
|-----------------|---------|
| `resetRecord()` | 重置记录功能。 |
| `resetMap()`    | 重置映射功能。 |

当前实现未暴露 `resetFetch()` 远程入口。
如果需要重置抓取功能，可以通过 Telqos 的 `reset --reset-fetch` 在目标节点执行，或根据项目需要扩展远程接口。

### 配置项

`DubboResetter` 没有专用的 `reset.properties` 配置项。

```properties
###################################################
#                      dubbo                      #
###################################################
# Dubbo 重置器没有任何配置。
```

该实现会使用项目中的 Dubbo 注册中心、Dubbo 协议配置，并读取通用的 Dubbo provider group 配置。
因此启用该实现前，需要确认项目的 Dubbo 配置可用。

### 适用场景

- 集群内节点需要同时刷新本地缓存。
- 配置、点位、映射等变更由外部维护服务触发。
- 希望由调用方主动决定何时执行重置。

## 启用方式

重置器通过 `opt/opt-resetter.xml` 启用。以下示例启用 `DubboResetter`：

```xml

<context:component-scan base-package="com.dwarfeng.fdr.impl.handler.resetter" use-default-filters="false">
    <!-- 加载 DubboResetter -->
    <context:include-filter
            type="assignable" expression="com.dwarfeng.fdr.impl.handler.resetter.DubboResetter"
    />
</context:component-scan>
```

如果需要同时加载多个重置器，可以在同一个 `component-scan` 中启用多个 `include-filter`。
需要注意的是，多个重置器可能触发相同的重置动作，运维上应避免无意中形成过高的重置频率。

## 参阅

- [Resetter](./Resetter.md) - 重置器，详细说明了本项目的重置器机制。
- [Conf Directory](./ConfDirectory.md) - 配置目录说明，详细说明如何配置本项目，即 `conf/` 目录下的内容。
- [Opt Directory](./OptDirectory.md) - 可选配置目录说明，详细介绍了本项目的可选配置，即 `opt/` 目录下的内容。
- [Telqos Commands](./TelqosCommands.md) - Telqos 命令，详细说明了本项目的 Telqos 命令。
