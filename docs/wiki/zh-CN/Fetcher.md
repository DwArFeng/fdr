# Fetcher - 抓取器

## 说明

抓取器是 FDR 在新版本中用于主动获取数据并写入记录链路的机制。抓取器可以监听外部数据源，也可以由 FDR 内部按照调度规则生成模拟数据。

FDR 支持两种写入记录的方式，一种是由其它系统调用 FDR 的记录服务接口，另一种是由抓取器主动获得数据并调用记录上下文写入 FDR。
对于需要持续监听、持续消费或按频率生成数据的场景，推荐使用抓取器机制。

与旧版本的 `Source` 机制相比，`Fetcher` 的主要优势是配置更灵活。
旧的 `Source` 机制中，一个类型的实现通常对应一个单例数据源实例，这意味着同一种类型很难同时使用两套配置。
例如，如果需要使用 `kafka.dct` 协议同时监听两个 topic，数据源机制需要额外编写新的数据源实现，引入新的抓取器类型，
才能实现多 topic 监听。

新版本将 Source 机制重构为 Fetcher 机制后，抓取器实例由 `FetcherInfo` 描述。
多条 `FetcherInfo` 可以使用相同的 `type`，但配置不同的 `param`，因此同一个抓取器类型可以同时存在多套配置。
例如，可以创建两条 `type` 均为 `kafka.dct` 的抓取器信息，分别配置不同的 `topic` 与 `listener_id`，
从而在同一节点中同时监听两个 topic。

## 重构理由

旧版 `Source` 机制适合配置少量固定数据源，但当同一种采集实现需要同时接入多个目标时，会暴露出配置粒度过粗的问题。
数据源实现与 Spring bean 实例绑定得较紧，一个类型通常只有一个生效实例，配置也更偏向“按类型配置”。
这会限制多 topic、多连接、多采样策略或多模拟点位等场景。

`Fetcher` 机制将“支持哪些抓取器类型”和“启用哪些抓取器实例”拆开：
抓取器注册器声明类型和参数格式，`FetcherInfo` 决定实际启用的实例及其参数。
这样，同一个类型可以被重复实例化为多个抓取器，每个实例拥有独立参数和独立会话资源。

因此，Source 重构为 Fetcher 的主要目标包括：
支持同类型多实例配置；让抓取器参数可以通过维护实体动态管理；让运行资源集中在 `FetcherSession` 中便于启动、停止、关闭和重置；
并为后续新增抓取器类型提供统一的注册、支持信息和示例参数机制。

## 接口

抓取器 `Fetcher` 是一个接口，有如下方法：

| 方法签名                          | 说明      |
|-------------------------------|---------|
| `FetcherSession newSession()` | 新建抓取器会话 |

抓取器会话 `FetcherSession` 是一个接口，有如下方法：

| 方法签名                                | 说明       |
|-------------------------------------|----------|
| `void init(FetcherSession.Context)` | 初始化抓取器会话 |
| `void openSession()`                | 打开抓取器会话  |
| `void startFetch()`                 | 启动抓取     |
| `void stopFetch()`                  | 停止抓取     |
| `void closeSession()`               | 关闭抓取器会话  |

抓取器会话上下文 `FetcherSession.Context` 是一个接口，有如下方法：

| 方法签名                                       | 说明   |
|--------------------------------------------|------|
| `void record(RecordInfo) throws Exception` | 记录数据 |

具体方法的说明请参阅接口的 JavaDoc。

## 抓取器信息

抓取器由 `FetcherInfo` 实体描述，核心字段如下。
每一条 `FetcherInfo` 都代表一个可独立启停的抓取器实例，因此同一个 `type` 可以对应多条不同的 `FetcherInfo`。

| 字段名       | 类型          | 说明                  |
|-----------|-------------|---------------------|
| `key`     | `LongIdKey` | 抓取器信息主键。            |
| `enabled` | `boolean`   | 是否在抓取处理器启动时参与抓取。    |
| `type`    | `String`    | 抓取器类型，用于匹配抓取器注册器。   |
| `param`   | `String`    | 抓取器参数，通常为 JSON 字符串。 |
| `remark`  | `String`    | 备注。                 |

抓取器支持由 `FetcherSupport` 实体描述，核心字段如下：

| 字段名            | 类型            | 说明       |
|----------------|---------------|----------|
| `key`          | `StringIdKey` | 抓取器类型。   |
| `label`        | `String`      | 抓取器显示标签。 |
| `description`  | `String`      | 抓取器说明。   |
| `exampleParam` | `String`      | 抓取器示例参数。 |

程序通过 `FetcherMaker` 根据 `FetcherInfo.type` 和 `FetcherInfo.param` 构造抓取器。
内置抓取器的注册器同时实现 `FetcherMaker` 和 `FetcherSupporter`，因此可以同时提供构造能力和支持信息。

`FetcherSupport` 描述当前的系统支持哪些抓取器类型，以及每种类型的标签、说明和示例参数。

## 抓取器的生命周期

### 构造

当系统需要使用某个抓取器时，会根据 `FetcherInfo` 的主键从本地缓存中获取抓取器。
如果缓存不存在，则读取 `FetcherInfo`，并使用其中的 `type` 和 `param` 构造抓取器。

构造时，`FetcherMakeHandler` 会遍历当前容器中的 `FetcherMaker`，选择支持指定类型的制造器，
并调用制造器的 `makeFetcher(String type, String param)` 方法生成抓取器。如果没有制造器支持该类型，则会抛出不支持抓取器类型的异常。

### 建立会话

抓取器会话由 `FetcherSessionHoldHandler` 持有。
首次获取某个抓取器信息对应的会话时，处理器会调用 `Fetcher.newSession()` 新建会话，并构造 `FetcherSession.Context` 注入到会话中。

会话初始化后，程序会调用 `openSession()`。该阶段通常用于准备长期运行资源，
例如创建 Kafka 监听容器、解析配置、构造值生成器等。会话会被缓存在持有器中，后续再次获取同一抓取器信息时直接复用。

### 启动抓取

当用户执行抓取上线指令，或配置文件中配置的启动抓取延迟时间到达时，程序会启动抓取功能。

抓取处理器启动时，会查询所有已启用的 `FetcherInfo`，逐个获取对应的 `FetcherSession`，然后调用 `startFetch()`。
不同的抓取器会在该方法中启动自己的运行逻辑，例如启动 Kafka 监听容器，或向调度器注册周期性任务。

抓取器获得数据后，会将数据转换为 `RecordInfo` 对象，并调用初始化时保存的 `FetcherSession.Context#record(RecordInfo)`，
将数据写入 FDR 的记录链路。

### 停止抓取

当用户执行抓取下线指令，或程序关闭时，程序会停止抓取功能。

抓取处理器停止时，只会对本轮已经成功启动的会话调用 `stopFetch()`。
不同的抓取器会在该方法中停止自己的运行逻辑，例如停止 Kafka 监听容器，或取消调度任务。

### 关闭会话

抓取停止不等同于会话关闭。抓取器会话可以在停止抓取后继续被持有，以便后续再次启动时复用。

当系统执行抓取器会话清理或重置时，`FetcherSessionHoldHandler` 会对当前持有的会话调用 `closeSession()`，随后清空会话映射。
`closeSession()` 可能在抓取器启动失败或资源半初始化时被调用，因此抓取器实现应能安全处理重复关闭和半初始化资源。

## 工作机制

程序根据 `FetcherInfo.type` 找到支持该类型的抓取器注册器，并将 `FetcherInfo.param` 解析为对应的配置对象。
注册器校验参数后创建 `Fetcher`。

`Fetcher` 不直接持有运行状态，而是通过 `newSession()` 创建 `FetcherSession`。
`FetcherSession` 保存 `FetcherSession.Context`，并在 `openSession()`、`startFetch()`、`stopFetch()`、`closeSession()`
中维护监听容器、调度任务或临时状态。

当抓取功能启动时，启用的抓取器会话开始工作。
外部监听型抓取器通常在消息到达时构造 `RecordInfo`；内部模拟型抓取器通常在定时任务触发时构造 `RecordInfo`。
构造完成后，抓取器会话通过上下文调用记录处理器，后续清洗、过滤、保持、持久化等流程由记录链路继续处理。

## 数据时序约束

从抓取器的业务逻辑上来说，同一点位的数据接收过程应尽量保持时间递增，即相同 `RecordInfo#getPointKey()` 的记录信息，
其 `RecordInfo#getHappenedDate()` 应尽量递增。

例如基于 Kafka 的抓取器，建议将同一点位的数据交由同一个 partition 处理，避免旧数据先于新数据被记录。
其它抓取器实现也应尽量避免同一点位数据乱序。

在特殊情况下，抓取器可能接收到时间不递增的数据，例如抓取器所在服务器系统时间回拨。
此类数据可以进入记录链路，但不同保持器对时间不递增数据的处理方式不同，可能覆盖最新数据、覆盖旧数据或抛出异常。

## 运维与配置

抓取器支持信息可以通过重置支持信息的方式写入系统，重置行为通常在程序系统后执行，以便于将其支持的抓取器类型注册到系统中。
内置抓取器注册器提供类型、标签、说明和示例参数，用于生成 `FetcherSupport`。

实际启用抓取器时，需要维护 `FetcherInfo`：设置唯一主键、启用状态、抓取器类型以及参数。
`type` 必须与某个已加载的抓取器注册器匹配，`param` 必须符合该类型对应的 JSON 配置格式。

如果需要同一种抓取器类型使用多套配置，应创建多条 `FetcherInfo`。
例如，使用 `kafka.dct` 同时监听两个 topic 时，可以创建两条启用状态为 `true` 的抓取器信息，
它们的 `type` 都填写 `kafka.dct`，但 `param` 中的 `topic` 和 `listener_id` 分别填写不同的值。

如果修改了抓取器参数或支持信息，运行中的会话可能仍持有旧资源。
此时应根据运维需要停止抓取、清理抓取器本地缓存或关闭并清除抓取器会话，使新配置重新生效。

## 参阅

- [Preset Fetcher Implements](./PresetFetcherImplements.md) - 预设抓取器实现，详细说明了本项目内置的所有抓取器。
