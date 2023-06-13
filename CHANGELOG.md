# ChangeLog

### Release_2.0.0_20230404_build_A

#### 功能构建

- 依赖升级。
  - 升级 `spring` 依赖版本为 `5.3.27` 以规避漏洞。

- 重写 `README.md`，并增加文档文件夹。

- 实现预设数据源。
  - com.dwarfeng.fdr.impl.handler.source.mock.realtime.RealtimeMockSource。
  - com.dwarfeng.fdr.impl.handler.source.mock.historical.HistoricalMockSource。
  - com.dwarfeng.fdr.impl.handler.source.kafka.dwarfengdct.DwarfengDctKafkaSource。
  - com.dwarfeng.fdr.impl.handler.source.kafka.dcti.DctiKafkaSource。

- 实现预设过滤器。
  - com.dwarfeng.fdr.impl.handler.filter.DataValueIllegalFilterRegistry。

- 新增数据清洗机制，并提供预设清洗器。
  - com.dwarfeng.fdr.impl.handler.washer.GroovyWasherRegistry。
  - com.dwarfeng.fdr.impl.handler.washer.IdentifyWasherRegistry。
  - com.dwarfeng.fdr.impl.handler.washer.ToBooleanWasherRegistry。
  - com.dwarfeng.fdr.impl.handler.washer.ToDoubleWasherRegistry。
  - com.dwarfeng.fdr.impl.handler.washer.ToLongWasherRegistry。

- 优化项目的服务异常映射机制。

- 重构数据记录处理与数据持久化模块。

- 实现预设桥接器。
  - com.dwarfeng.fdr.impl.handler.bridge.mock.MockBridge。
  - com.dwarfeng.fdr.impl.handler.bridge.hibernate.HibernateBridge。
  - com.dwarfeng.fdr.impl.handler.bridge.redis.RedisBridge。
  - com.dwarfeng.fdr.impl.handler.bridge.influxdb.InfluxdbBridge。
  - com.dwarfeng.fdr.impl.handler.bridge.drain.DrainBridge。
  - com.dwarfeng.fdr.impl.handler.bridge.multi.MultiBridge。

#### Bug修复

- 修复 `DubboResetter` 注册微服务时没有指定 `group` 的问题。

#### 功能移除

- (无)

---

### 更早的版本

[View all changelogs](./changelogs)
