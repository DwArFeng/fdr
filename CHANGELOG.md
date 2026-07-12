# ChangeLog

## Release_3.1.1_20260712_build_A

### 功能构建

- Wiki 更新。
  - docs/wiki/zh-CN/Filter.md。
  - docs/wiki/zh-CN/Fetcher.md。
  - docs/wiki/zh-CN/PresetFetcherImplements.md。

- `fdr-impl` 子模块类优化注释、文档注释格式、代码换行格式。
  - com.dwarfeng.fdr.impl.handler.fetcher.mock.hf.MockHfFetcherSession。

- 优化文件格式。
  - 优化 `*.properties` 文件的格式。
  - 优化 `opt-*.xml` 文件的格式。
  - 优化 `application-context-*.xml` 文件的格式。
  - 优化 `pom.xml` 文件的格式。

### Bug 修复

- 修正部分过滤器实现中的逻辑错误。
  - com.dwarfeng.fdr.impl.handler.trigger.BooleanTriggerRegistry。

- 修正部分配置文件。
  - 修正 `spring/application-context-scan.xml` 中缺失的配置内容。

### 功能移除

- (无)

---

## Release_3.1.0_20260623_build_A

### 功能构建

- Wiki 编写。
  - docs/wiki/zh-CN/Fetcher.md。
  - docs/wiki/zh-CN/PresetFetcherImplements.md。

- Wiki 更新。
  - docs/wiki/zh-CN/OptDirectory.md。
  - docs/wiki/zh-CN/ConfDirectory.md。
  - docs/wiki/zh-CN/TelqosCommands.md。

- 实现预设抓取器。
  - com.dwarfeng.fdr.impl.handler.fetcher.kafka.dct.DctKafkaFetcherRegistry。
  - com.dwarfeng.fdr.impl.handler.fetcher.kafka.dcti.DctiKafkaFetcherRegistry。
  - com.dwarfeng.fdr.impl.handler.fetcher.simulate.awg.SimulateAwgFetcherRegistry。
  - com.dwarfeng.fdr.impl.handler.fetcher.simulate.wave.SimulateWaveFetcherRegistry。
  - com.dwarfeng.fdr.impl.handler.fetcher.mock.hf.MockHfFetcherRegistry。
  - com.dwarfeng.fdr.impl.handler.fetcher.mock.lf.MockLfFetcherRegistry。

- 实现核心机制。
  - 抓取机制。

- 新增使能实体查询服务。
  - com.dwarfeng.fdr.stack.service.EnabledFetcherInfoLookupService。

- 建立实体以及维护服务，并通过单元测试。
  - com.dwarfeng.fdr.stack.bean.entity.FetcherInfo。
  - com.dwarfeng.fdr.stack.bean.entity.FetcherSupport。

- 优化项目的关闭流程。
  - 引入 ShutdownProcessor，集中处理关闭调度。

- 优化部分 `properties` 文件中的注释。
  - src/main/resources/fdr/launcher.properties。

- 优化部分指令的文案。
  - com.dwarfeng.fdr.impl.service.telqos.SupportCommand。

- `fdr-impl` 子模块添加单元测试。
  - com.dwarfeng.fdr.impl.service.EnabledWasherInfoLookupServiceImplTest。

- `fdr-impl` 子模块类优化注释、文档注释格式、代码换行格式。
  - com.dwarfeng.fdr.impl.cache.EnabledFilterInfoCacheImpl。
  - com.dwarfeng.fdr.impl.cache.EnabledTriggerInfoCacheImpl。
  - com.dwarfeng.fdr.impl.cache.EnabledWasherInfoCacheImpl。
  - com.dwarfeng.fdr.impl.handler.bridge.hibernate.configuration.HibernateBridgeDaoConfiguration。
  - com.dwarfeng.fdr.impl.service.AbstractViewService。
  - com.dwarfeng.fdr.impl.service.FilterSupportMaintainServiceImpl。
  - com.dwarfeng.fdr.impl.service.MapperSupportMaintainServiceImpl。
  - com.dwarfeng.fdr.impl.service.TriggerSupportMaintainServiceImpl。
  - com.dwarfeng.fdr.impl.service.WasherSupportMaintainServiceImpl。
  - com.dwarfeng.fdr.impl.service.telqos.FilteredViewCommand。
  - com.dwarfeng.fdr.impl.service.telqos.LogicConsumerCommand。
  - com.dwarfeng.fdr.impl.service.telqos.MapLocalCacheCommand。
  - com.dwarfeng.fdr.impl.service.telqos.NormalViewCommand。
  - com.dwarfeng.fdr.impl.service.telqos.RecordCommand。
  - com.dwarfeng.fdr.impl.service.telqos.RecordConsumerCommand。
  - com.dwarfeng.fdr.impl.service.telqos.RecordLocalCacheCommand。
  - com.dwarfeng.fdr.impl.service.telqos.RecordMemoryCommand。
  - com.dwarfeng.fdr.impl.service.telqos.ResetCommand。
  - com.dwarfeng.fdr.impl.service.telqos.SourceCommand。
  - com.dwarfeng.fdr.impl.service.telqos.SupportCommand。
  - com.dwarfeng.fdr.impl.service.telqos.TriggeredViewCommand。
  - com.dwarfeng.fdr.impl.service.telqos.ViewCommand。

- 优化文件格式。
  - 优化 `assembly.xml` 文件的格式。

### Bug 修复

- `fdr-impl` 子模块修改错误的单元测试类名。
  - com.dwarfeng.fdr.impl.service.EnabledFilterInfoLookupServiceImplTest。

### 功能移除

- Wiki 移除。
  - docs/wiki/zh-CN/PresetSourceImplements.md。
  - docs/wiki/zh-CN/Source.md。

- 移除核心机制。
  - 数据源机制。

---

## Release_3.0.1_20260519_build_A

### 功能构建

- Wiki 编写。
  - docs/wiki/zh-CN/Filter.md。

- Wiki 更新。
  - docs/wiki/zh-CN/ConfDirectory.md。
  - docs/wiki/zh-CN/Washer.md。

- 优化 `influxdb` 桥接器。
  - 增加 `InfluxdbBridgeBaseConfiguration` 中的配置项，使得更多的配置项可通过配置文件进行调整。

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
