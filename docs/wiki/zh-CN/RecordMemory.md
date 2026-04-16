# Record Memory - 记录记忆

## 说明

记录记忆是点位级的轻量内存队列，用于保存记录链路最近处理过的数据样本。

RecordMemory 的主要用途如下：

- 为过滤器、清洗器、触发器等机制提供“最近历史值”上下文。
- 为运维排障提供“该点位最近发生了什么”的快速观察窗口。
- 为 Telqos 指令 `rmem` 提供可查询、可清理的数据基础。

记录记忆属于运行时内存数据，不是持久化存储，重启后会丢失。

## 接口

记录记忆处理器 `RecordMemoryHandler` 提供如下方法：

| 方法签名                                     | 说明                  |
|------------------------------------------|---------------------|
| `void append(RecordMemory, int maxSize)` | 追加记录记忆，按点位维护队列最大长度。 |
| `List<RecordMemory> lookup(LongIdKey)`   | 查询指定点位的记录记忆列表。      |
| `void remove(LongIdKey)`                 | 移除指定点位的全部记录记忆。      |
| `void clear()`                           | 清除全部点位的记录记忆。        |

`lookup` 返回结果按时间从新到旧排列，索引 `0` 对应最近一次入队的记录。

记录记忆结构 `RecordMemory` 的关键字段如下：

| 字段             | 说明                      |
|----------------|-------------------------|
| `pointKey`     | 点位主键。                   |
| `happenedDate` | 数据发生时间。                 |
| `rawValue`     | 原始输入值。                  |
| `passed`       | 是否通过记录机制处理并形成一般数据。      |
| `value`        | 清洗后值；如果未通过，则通常为 `null`。 |

## 工作机制

记录链路处理单条数据时，记录记忆的写入规则如下：

1. 数据未通过过滤器时，追加一条 `passed=false` 的记录，`value` 为 `null`。
2. 数据通过过滤并完成后续清洗后，追加一条 `passed=true` 的记录，`value` 为清洗后值。
3. 追加时会按点位配置的 `recordMemorySize` 截断队列，仅保留最近 N 条数据。

因此，记录记忆可以同时覆盖“通过样本”和“被过滤样本”，便于回溯链路行为。

## 运维与调试

### Telqos 查询与清理

通过 Telqos 指令 `rmem` 可执行以下操作：

- `rmem -l point-id`：查看指定点位的记录记忆（支持分片交互查看）。
- `rmem -r point-id`：移除指定的点位对应的记录记忆。
- `rmem -c`：清除记录记忆。

详细语法与示例请参阅：

- [Telqos Commands](./TelqosCommands.md#rmem-命令) - `rmem` 命令说明。

### 使用建议

- `rmem -l point-id` 指令选项优先使用范围查看 `begin-end`，而不是 `all`，避免刷屏。
- 清理动作会影响依赖历史值的机制判断，建议先观察后清理。
- 如果用于问题复盘，建议先导出关键输出或记录日志，再执行 `-r`/`-c`。

## 参阅

- [Telqos Commands](./TelqosCommands.md) - Telqos 命令总览。
- [Using Telqos](./UsingTelqos.md) - Telqos 使用方法。
