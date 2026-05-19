# Filter - 过滤器

## 说明

过滤器用于在记录链路中对单条数据的值（`Object`）进行判定，决定该数据是否进入一般数据路径。
典型用途包括空值校验、数值范围校验、类型校验、正则匹配， 以及与过滤前清洗器配合识别不合法数据等。

过滤器是数据处理流程中的一个环节，整个流程如下：

![RecordInfoProcessingFlowChart.png](./images/RecordInfoProcessingFlowChart.png)

任一过滤器判定数据“被过滤”时，记录链路会转入被过滤数据路径，不再执行后续一般数据记录。
全部过滤器均未命中时，数据继续进入过滤后清洗器与触发器，并最终形成一般数据。
过滤器可通过 `Context#lookupRecordMemory` 查询点位最近的记录记忆，详见 [Record Memory](./RecordMemory.md)。

## FilterInfo

`FilterInfo` 描述某个点位上的一条过滤器配置，关键字段如下：

| 字段         | 说明                               |
|------------|----------------------------------|
| `key`      | 过滤器信息主键。                         |
| `pointKey` | 所属点位主键。                          |
| `index`    | 同点位内的执行顺序，数值越小越先执行。              |
| `enabled`  | 是否启用；仅 `enabled=true` 的配置参与记录链路。 |
| `type`     | 过滤器类型标识，由具体实现注册。                 |
| `param`    | 过滤器参数，通常为 JSON 字符串。              |
| `remark`   | 备注。                              |

启用过滤器按 `index` 升序加载，并归入点位的过滤器映射。

## 接口

过滤器 `Filter` 是一个接口，主要方法如下：

| 方法签名                                                        | 说明            |
|-------------------------------------------------------------|---------------|
| `void init(Context context)`                                | 构建后初始化，保存上下文。 |
| `TestResult test(TestInfo testInfo) throws FilterException` | 测试数据是否被过滤。    |

具体方法的说明请参阅接口的 JavaDoc。

### TestInfo

`TestInfo` 封装一次过滤测试所需的输入信息，结构如下：

```java
public final class TestInfo {

    private final LongIdKey pointKey;
    private final Object value;
    private final Date happenedDate;
    private final int happenedDateNanoOffset;

    public TestInfo(
            LongIdKey pointKey, Object value, Date happenedDate, int happenedDateNanoOffset
    ) {
        this.pointKey = pointKey;
        this.value = value;
        this.happenedDate = happenedDate;
        this.happenedDateNanoOffset = happenedDateNanoOffset;
    }

    // getter 省略
}
```

| 字段                       | 说明                                          |
|--------------------------|---------------------------------------------|
| `pointKey`               | 点位主键。                                       |
| `value`                  | 待测试的值。                                      |
| `happenedDate`           | 数据发生时间。                                     |
| `happenedDateNanoOffset` | 毫秒内纳秒偏移（3.0.0 起）；与 `happenedDate` 共同确定发生时刻。 |

### TestResult

`TestResult` 封装过滤测试结果，结构如下：

```java
public final class TestResult {

    public static final TestResult NOT_FILTERED = new TestResult(false, "");

    public static TestResult filtered(String message) {
        return new TestResult(true, message);
    }

    private final boolean filtered;
    private final String message;

    public TestResult(boolean filtered, String message) {
        this.filtered = filtered;
        this.message = message;
    }

    public boolean isFiltered() {
        return filtered;
    }

    public String getMessage() {
        return message;
    }
}
```

当 `isFiltered()` 为 `true` 时，`message` 表示该数据被过滤的原因；
当 `isFiltered()` 为 `false` 时，不应使用 `message` 字段。
未过滤时也可返回 `null` 的 `TestResult`，这主要是考虑兼容性，不推荐新的过滤器这样做。

### Context

`Context` 为过滤器提供运行时上下文，主要方法如下：

| 方法签名                                                        | 说明           |
|-------------------------------------------------------------|--------------|
| `List<RecordMemory> lookupRecordMemory(LongIdKey pointKey)` | 查询指定点位的记录记忆。 |

返回列表按时间从新到旧排列，索引 `0` 对应最新记录。调用者应只读使用返回结果，不应修改列表内容。

## 过滤器的生命周期

### 初始化

当一个 `RecordInfo` 对象被逻辑侧消费者处理时，消费者会根据 `RecordInfo.getPointKey()` 获取点位主键。
点位对应 FDR 配置的数据点信息，一个数据点可关联多条清洗器信息、过滤器信息和触发器信息。

在大部分情况下，一个数据点关联的清洗器、过滤器和触发器会以 `RecordLocalCache` 的形式，缓存在 `RecordLocalCacheHandler` 中。
若缓存不存在，`RecordLocalCacheHandler` 会查询该点位已启用的清洗器、过滤器、触发器信息，并生成对应实例。

| 信息实体          | 生成的对象     | 生成处理器            |
|---------------|-----------|------------------|
| `WasherInfo`  | `Washer`  | `WasherHandler`  |
| `FilterInfo`  | `Filter`  | `FilterHandler`  |
| `TriggerInfo` | `Trigger` | `TriggerHandler` |

对于过滤器，`FilterHandler` 会根据 `FilterInfo` 的 `type` 和 `param` 字段生成实例。
`FilterHandlerImpl` 在 `make` 成功后会调用 `filter.init(filterContext)`，
注入的上下文可通过 `lookupRecordMemory` 查询记录记忆，详见 [Record Memory](./RecordMemory.md)。

生成的 `RecordLocalCache` 包含 `preFilterWasherMap`、`filterMap`、`postFilterWasherMap` 与 `triggerMap`，
记录链路由 `RecordProcessor` 消费 `RecordInfo` 时按此结构执行。

### 销毁

`RecordLocalCacheHandler` 维护记录本地缓存，其中保存各点位的 `RecordLocalCache`（含清洗器、过滤器、触发器实例）。

在大部分情况下，缓存没有过期时间。
除非调用 `RecordLocalCacheHandler.remove` 或 `RecordLocalCacheHandler.clear()`，否则记录上下文不会被销毁。

记录功能重置（`ResetProcessor.resetRecord`）会调用 `RecordLocalCacheHandler.clear()`，
同时调用 `recordMemoryHandler.clear()` 清空记录记忆。
此时所有 `RecordLocalCache` 被释放，过滤器实例随之销毁。

## 工作机制

### 记录链路顺序

单条 `RecordInfo` 在记录链路中的处理顺序如下：

1. 过滤前清洗器（`preFilter=true`，按 `index` 升序）。
2. 过滤器（任一过滤器判定为被过滤则终止一般数据路径）。
3. 过滤后清洗器（`preFilter=false`，按 `index` 升序）。
4. 触发器。

### 过滤器执行

过滤器在过滤前清洗器之后执行。
FDR 读取 `RecordInfo.getValue()`，按 `index` 升序依次调用各过滤器的 `test(TestInfo)`。
前一个过滤器未命中时，才继续测试下一个过滤器。

调用方式示例如下：

```java
public class FilterUsageExample {

    private boolean isFiltered(
            Filter filter, LongIdKey pointKey, RecordInfo recordInfo
    ) throws FilterException {
        Filter.TestInfo testInfo = new Filter.TestInfo(
                pointKey,
                recordInfo.getValue(),
                recordInfo.getHappenedDate(),
                recordInfo.getHappenedDateNanoOffset()
        );
        Filter.TestResult testResult = filter.test(testInfo);
        return testResult != null && testResult.isFiltered();
    }
}
```

### 被过滤数据

任一过滤器判定数据被过滤时，FDR 会构造 `FilteredData`，其中包含点位主键、命中过滤器主键、
当前值、过滤原因以及发生时间等信息。

随后根据点位配置决定是否保持或持久被过滤数据：

- `Point#isFilteredKeepEnabled()` 为 `true` 时，投递被过滤数据的保持消费者。
- `Point#isFilteredPersistEnabled()` 为 `true` 时，投递被过滤数据的持久消费者。

同时追加一条 `RecordMemory`：`rawValue` 为链路入口时的原始值，`passed=false`，`value` 为 `null`。
追加完成后记录链路直接返回，不再执行过滤后清洗器、触发器与一般数据路径。
记录记忆的写入规则详见 [Record Memory](./RecordMemory.md)。

### 与过滤前清洗器的配合

过滤前清洗器处理的数据可能无效，因此常与过滤器配合使用，推荐流程如下：

1. 过滤前清洗器发现数据不合法时，返回 `null`，或 SDK 提供的特殊值
   `com.dwarfeng.fdr.sdk.util.Constants#DATA_VALUE_ILLEGAL`。
2. 配置 `data_value_illegal_filter` 等过滤器识别上述特殊值并拒绝该数据。

*当过滤器认定数据不合法时，应通过 `TestResult.filtered` 返回可识别的过滤原因，而不应为此抛出异常。*

若因无法调用外部服务等原因无法完成过滤测试，应抛出 `FilterException`，由框架按记录失败流程处理。

### 通过过滤后的处理

全部过滤器均未命中时，记录信息继续进入过滤后清洗器与触发器流程，
并在通过后续处理后形成一般数据。过滤后清洗器的说明请参阅 [Washer](./Washer.md)。

## 参阅

- [Washer](./Washer.md) - 清洗器，详细说明了本项目的清洗器机制。
- [Opt Directory](./OptDirectory.md) - 可选配置目录说明，详细介绍了本项目的可选配置，即 `opt/` 目录下的内容。
- [Record Memory](./RecordMemory.md) - 记录记忆，说明记录记忆的结构、工作机制与运维要点。
- [Telqos Commands](./TelqosCommands.md) - Telqos 命令，详细说明了本项目的 Telqos 命令。
