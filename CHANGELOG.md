# ChangeLog

## Release_3.0.1_20260514_build_A

### 功能构建

- 为部分数据源添加安全轮询机制。
  - com.dwarfeng.fdr.impl.handler.source.mock.historical.HistoricalMockSource。
  - com.dwarfeng.fdr.impl.handler.source.mock.realtime.RealtimeMockSource。

- 依赖升级。
  - 升级 `subgrade` 依赖版本为 `1.8.3.a` 以应用其新功能。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_3.0.0_20260509_build_A

### 功能构建

- 更新 README.md。

- Wiki 更新。
  - docs/wiki/zh-CN/QuickStart.md。
  - docs/wiki/zh-CN/InstallToCentos.md。
  - docs/wiki/zh-CN/Introduction.md。

- 增强数据时间精度支持。
  - 增加 `com.dwarfeng.fdr.stack.struct.RecordMemory.happenedDateNanoOffset` 字段。
  - 增加 `com.dwarfeng.fdr.stack.struct.Data` 中与 `happenedDateNanoOffset` 相关的访问约定。
  - 增加 `com.dwarfeng.fdr.stack.bean.dto.FilteredData.happenedDateNanoOffset` 字段。
  - 增加 `com.dwarfeng.fdr.stack.bean.dto.NormalData.happenedDateNanoOffset` 字段。
  - 增加 `com.dwarfeng.fdr.stack.bean.dto.TriggeredData.happenedDateNanoOffset` 字段。
  - 增加 `com.dwarfeng.fdr.stack.bean.dto.RecordInfo.happenedDateNanoOffset` 字段。
  - 增加 `com.dwarfeng.fdr.stack.bean.dto.QueryResult.Sequence.startDateNanoOffset` 字段。
  - 增加 `com.dwarfeng.fdr.stack.bean.dto.QueryResult.Sequence.endDateNanoOffset` 字段。
  - 增加 `com.dwarfeng.fdr.stack.bean.dto.QueryResult.Item.happenedDateNanoOffset` 字段。
  - 增加 `com.dwarfeng.fdr.sdk.util.DataUtil` 工具类，为 `Data` 提供时间操作工具。
  - 增加 `com.dwarfeng.fdr.sdk.util.NormalDataUtil` 工具类，为 `NormalData` 提供时间操作工具。
  - 增加 `com.dwarfeng.fdr.sdk.util.FilteredDataUtil` 工具类，为 `FilteredData` 提供时间操作工具。
  - 增加 `com.dwarfeng.fdr.sdk.util.TriggeredDataUtil` 工具类，为 `TriggeredData` 提供时间操作工具。
  - 增加 `com.dwarfeng.fdr.sdk.util.RecordInfoUtil` 工具类，为 `RecordInfo` 提供时间操作工具。
  - 增加 `com.dwarfeng.fdr.sdk.util.QueryResultUtil` 工具类，为 `QueryResult` 提供时间操作工具。
  - 增加 `com.dwarfeng.fdr.sdk.util.MapperUtil` 工具类，为 `Mapper` 提供时间操作工具。
  - 调整相关处理器的处理逻辑，以支持纳秒偏移量的传递。
  - 调整相关清洗器的处理逻辑，以支持纳秒偏移量的传递。
  - 调整相关过滤器的处理逻辑，以支持纳秒偏移量的传递。
  - 调整相关触发器的处理逻辑，以支持纳秒偏移量的传递。
  - 调整相关映射器的处理逻辑，以支持纳秒偏移量的传递。
  - 优化 `com.dwarfeng.fdr.impl.service.telqos.ViewCommand` 运维指令的显示逻辑，以显示纳秒偏移量信息。
  - 优化 `com.dwarfeng.fdr.impl.service.telqos.NormalViewCommand` 运维指令的显示逻辑，以显示纳秒偏移量信息。
  - 优化 `com.dwarfeng.fdr.impl.service.telqos.FilteredViewCommand` 运维指令的显示逻辑，以显示纳秒偏移量信息。
  - 优化 `com.dwarfeng.fdr.impl.service.telqos.TriggeredViewCommand` 运维指令的显示逻辑，以显示纳秒偏移量信息。
  - 优化 `com.dwarfeng.fdr.impl.service.telqos.RecordMemoryCommand` 运维指令的显示逻辑，以显示纳秒偏移量信息。

- 依赖升级。
  - 升级 `dcti` 依赖版本为 `2.0.1.a` 并解决兼容性问题，以应用其新功能。
  - 升级 `dwarfeng-dct` 依赖版本为 `2.0.1.a` 并解决兼容性问题，以应用其新功能。
  - 升级 `jedis` 依赖版本为 `3.10.0` 以规避漏洞。
  - 升级 `spring-data-redis` 依赖版本为 `2.7.18` 以规避漏洞。
  - 升级 `kafka` 依赖版本为 `3.9.2` 以规避漏洞。
  - 升级 `dubbo` 依赖版本为 `2.7.23` 以规避漏洞。
  - 升级 `zookeeper` 依赖版本为 `3.9.5` 以规避漏洞。
  - 升级 `log4j2` 依赖版本为 `2.25.4` 以规避漏洞。
  - 升级 `mapstruct` 依赖版本为 `1.5.5.Final` 以规避漏洞。
  - 升级 `dutil` 依赖版本为 `0.4.2.a-beta` 以规避漏洞。
  - 升级 `snowflake` 依赖版本为 `1.9.0.a` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.8.2.a` 以规避漏洞。
  - 升级 `spring-terminator` 依赖版本为 `1.1.0.a` 以规避漏洞。
  - 升级 `spring-telqos` 依赖版本为 `1.2.0.a` 以规避漏洞。
  - 升级 `jackson` 依赖版本为 `2.21.2` 以规避漏洞。
  - 升级 `groovy` 依赖版本为 `4.0.31` 以规避漏洞。

- 优化文件格式。
  - 优化 `application-context-*.xml` 文件的格式。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## 更早的版本

[View all changelogs](./changelogs)
