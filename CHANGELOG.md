# ChangeLog

### Release_2.1.0_20231006_build_A

#### 功能构建

- 优化数据记录代码结构。
  - 调整 RecordProcessor 结构，增加代理方法，将 RecordWorker 的实现代码移动到 RecordProcessor 中。

- 优化数据处理逻辑。
  - 增加 Source.Context.record 方法调用时，入口参数的限制，并完善接口文档。
  - 简化 KeepConsumer 消费数据的逻辑。

#### Bug修复

- (无)

#### 功能移除

- 去除不需要的接口方法。
  - com.dwarfeng.fdr.stack.handler.KeepHandler.writeOnly。
  - com.dwarfeng.fdr.stack.handler.PersistHandler.writeOnly。
  - com.dwarfeng.fdr.impl.handler.Bridge.supportKeeper。
  - com.dwarfeng.fdr.impl.handler.Bridge.supportPersister。

---

### Release_2.0.3_20231006_build_A

#### 功能构建

- 优化配置文件。
  - 优化 `impl` 模块测试配置 `application-context-database.xml`，使得项目在运行测试时打印生成的 SQL 语句。

- Wiki 更新。
  - 优化 `Contents.md` 中的内容。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_2.0.2_20230619_build_A

#### 功能构建

- Wiki 编写。
  - docs/wiki/zh_CN/Source.md。
  - docs/wiki/zh_CN/Washer.md。

- Wiki 更新。
  - 在 wiki 的根目录以及所有语言目录下添加 `README.md`。

- 实现预设映射器。
  - com.dwarfeng.fdr.impl.handler.mapper.TrimMapperRegistry。
  - com.dwarfeng.fdr.impl.handler.mapper.ToBooleanMapperRegistry。
  - com.dwarfeng.fdr.impl.handler.mapper.EnableRatioMapperRegistry。
  - com.dwarfeng.fdr.impl.handler.mapper.HighPassMapperRegistry。
  - com.dwarfeng.fdr.impl.handler.mapper.HighPassCounterMapperRegistry。
  - com.dwarfeng.fdr.impl.handler.mapper.HighPassExistenceMapperRegistry。
  - com.dwarfeng.fdr.impl.handler.mapper.LowPassMapperRegistry。
  - com.dwarfeng.fdr.impl.handler.mapper.LowPassCounterMapperRegistry。
  - com.dwarfeng.fdr.impl.handler.mapper.LowPassExistenceMapperRegistry。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_2.0.1_20230616_build_A

#### 功能构建

- 依赖升级。
  - 升级 `guava` 依赖版本为 `32.0.1-jre` 以规避漏洞。

- 部分 FastJson 实体补充 toStackBean 方法。
  - com.dwarfeng.fdr.sdk.bean.dto.FastJsonNormalData。
  - com.dwarfeng.fdr.sdk.bean.dto.FastJsonFilteredData。
  - com.dwarfeng.fdr.sdk.bean.dto.FastJsonTriggeredData。

- 优化了 AbstractResetter 的代码结构，同时对其实现进行了修改。
  - com.dwarfeng.fdr.impl.handler.resetter.CronResetter。
  - com.dwarfeng.fdr.impl.handler.resetter.DubboResetter。
  - com.dwarfeng.fdr.impl.handler.resetter.FixedDelayResetter。
  - com.dwarfeng.fdr.impl.handler.resetter.FixedRateResetter。
  - com.dwarfeng.fdr.impl.handler.resetter.NeverResetter。

- 优化部分指令的文案。
  - com.dwarfeng.fdr.impl.service.telqos.LogicConsumerCommand。
  - com.dwarfeng.fdr.impl.service.telqos.RecordConsumerCommand。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_2.0.0_20230614_build_A

#### 功能构建

- 依赖升级。
  - 升级 `spring` 依赖版本为 `5.3.27` 以规避漏洞。
  - 升级 `dubbo` 依赖版本为 `2.7.22` 以规避漏洞。
  - 升级 `snowflake` 依赖版本为 `1.4.11.a` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.3.3.a` 以规避漏洞。
  - 升级 `spring-terminator` 依赖版本为 `1.0.11.a` 以规避漏洞。
  - 升级 `spring-telqos` 依赖版本为 `1.1.6.a` 以规避漏洞。

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
