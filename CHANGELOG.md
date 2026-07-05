# ChangeLog

## Release_4.0.0_20260705_build_A

### 功能构建

- Wiki 更新。
  - docs/wiki/zh-CN/TelqosCommands.md。
  - docs/wiki/zh-CN/QuickStart.md。
  - docs/wiki/zh-CN/OptDirectory.md。
  - docs/wiki/zh-CN/ConfDirectory.md。
  - docs/wiki/zh-CN/CompileBySource.md。

- `fdr-distribute` 模块新增。
  - 新增 `fdr-distribute` 模块，负责各个构型的产物分发。

- `fdr-node` 模块重构。
  - 将 `fdr-node` 调整为聚合模块。
  - 新建 `fdr-node-all-he` 模块，迁移项目原有内容，并形成项目的 `he` 构型。
  - 其它相关文件路径、包路径、配置路径等同步调整。

- 配置命名空间全局唯一化改造。
  - 为 `fdr-impl` 模块配置键统一增加全球唯一前缀，以消除跨微服务同名配置冲突。
  - 为 `fdr-node` 模块配置键统一增加全球唯一前缀，以消除跨微服务同名配置冲突。
  - 处理 `redis/prefix.properties` 缓存前缀配置的实体命名空间。
  - 同步调整 `*.java` 中的配置读取占位符。
  - 同步调整 `*application-context-*.xml` 中的配置读取占位符。

- 部分代理类实现中的字段类型提升为对应的接口，与具体实现解耦。
  - com.dwarfeng.fdr.impl.cache.EnabledFetcherInfoCacheImpl。
  - com.dwarfeng.fdr.impl.cache.EnabledFilterInfoCacheImpl。
  - com.dwarfeng.fdr.impl.cache.EnabledTriggerInfoCacheImpl。
  - com.dwarfeng.fdr.impl.cache.EnabledWasherInfoCacheImpl。
  - com.dwarfeng.fdr.impl.cache.FetcherInfoCacheImpl。
  - com.dwarfeng.fdr.impl.cache.FetcherSupportCacheImpl。
  - com.dwarfeng.fdr.impl.cache.FilterInfoCacheImpl。
  - com.dwarfeng.fdr.impl.cache.FilterSupportCacheImpl。
  - com.dwarfeng.fdr.impl.cache.MapperSupportCacheImpl。
  - com.dwarfeng.fdr.impl.cache.PointCacheImpl。
  - com.dwarfeng.fdr.impl.cache.TriggerInfoCacheImpl。
  - com.dwarfeng.fdr.impl.cache.TriggerSupportCacheImpl。
  - com.dwarfeng.fdr.impl.cache.WasherInfoCacheImpl。
  - com.dwarfeng.fdr.impl.cache.WasherSupportCacheImpl。
  - com.dwarfeng.fdr.impl.dao.FetcherInfoDaoImpl。
  - com.dwarfeng.fdr.impl.dao.FetcherSupportDaoImpl。
  - com.dwarfeng.fdr.impl.dao.FilterInfoDaoImpl。
  - com.dwarfeng.fdr.impl.dao.FilterSupportDaoImpl。
  - com.dwarfeng.fdr.impl.dao.MapperSupportDaoImpl。
  - com.dwarfeng.fdr.impl.dao.PointDaoImpl。
  - com.dwarfeng.fdr.impl.dao.TriggerInfoDaoImpl。
  - com.dwarfeng.fdr.impl.dao.TriggerSupportDaoImpl。
  - com.dwarfeng.fdr.impl.dao.WasherInfoDaoImpl。
  - com.dwarfeng.fdr.impl.dao.WasherSupportDaoImpl。
  - com.dwarfeng.fdr.impl.handler.bridge.hibernate.dao.HibernateBridgeFilteredDataDaoImpl。
  - com.dwarfeng.fdr.impl.handler.bridge.hibernate.dao.HibernateBridgeNormalDataDaoImpl。
  - com.dwarfeng.fdr.impl.handler.bridge.hibernate.dao.HibernateBridgeTriggeredDataDaoImpl。
  - com.dwarfeng.fdr.impl.handler.bridge.hibernate.service.HibernateBridgeFilteredDataMaintainServiceImpl。
  - com.dwarfeng.fdr.impl.handler.bridge.hibernate.service.HibernateBridgeNormalDataMaintainServiceImpl。
  - com.dwarfeng.fdr.impl.handler.bridge.hibernate.service.HibernateBridgeTriggeredDataMaintainServiceImpl。
  - com.dwarfeng.fdr.impl.handler.bridge.redis.dao.RedisBridgeFilteredDataDaoImpl。
  - com.dwarfeng.fdr.impl.handler.bridge.redis.dao.RedisBridgeNormalDataDaoImpl。
  - com.dwarfeng.fdr.impl.handler.bridge.redis.dao.RedisBridgeTriggeredDataDaoImpl。
  - com.dwarfeng.fdr.impl.handler.bridge.redis.service.RedisBridgeFilteredDataMaintainServiceImpl。
  - com.dwarfeng.fdr.impl.handler.bridge.redis.service.RedisBridgeNormalDataMaintainServiceImpl。
  - com.dwarfeng.fdr.impl.handler.bridge.redis.service.RedisBridgeTriggeredDataMaintainServiceImpl。
  - com.dwarfeng.fdr.impl.service.FetcherInfoMaintainServiceImpl。
  - com.dwarfeng.fdr.impl.service.FetcherSupportMaintainServiceImpl。
  - com.dwarfeng.fdr.impl.service.FilterInfoMaintainServiceImpl。
  - com.dwarfeng.fdr.impl.service.FilterSupportMaintainServiceImpl。
  - com.dwarfeng.fdr.impl.service.MapperSupportMaintainServiceImpl。
  - com.dwarfeng.fdr.impl.service.PointMaintainServiceImpl。
  - com.dwarfeng.fdr.impl.service.TriggerInfoMaintainServiceImpl。
  - com.dwarfeng.fdr.impl.service.TriggerSupportMaintainServiceImpl。
  - com.dwarfeng.fdr.impl.service.WasherInfoMaintainServiceImpl。
  - com.dwarfeng.fdr.impl.service.WasherSupportMaintainServiceImpl。

- 优化项目的异常处理机制。
  - `fdr-sdk` 子模块新增 `ServiceExceptionHelper` 工具类，统一维护项目自身的异常映射关系。
  - `fdr-impl` 子模块 `ServiceExceptionMapperConfiguration` 配置类的异常映射处理逻辑优化。
  - `fdr-node` 子模块 `ServiceExceptionMapperConfiguration` 配置类的异常映射处理逻辑优化。

- 依赖升级。
  - 升级 `spring-telqos` 依赖版本为 `2.0.2.a` 并解决兼容性问题，以应用其新功能。
  - 升级 `spring-terminator` 依赖版本为 `2.0.2.a` 并解决兼容性问题，以应用其新功能。
  - 升级 `dwarfeng-datamark` 依赖版本为 `2.2.0.a` 并解决兼容性问题，以应用其新功能。
  - 升级 `dcti` 依赖版本为 `3.0.1.a` 并解决兼容性问题，以应用其新功能。
  - 升级 `dwarfeng-dct` 依赖版本为 `3.0.2.a` 并解决兼容性问题，以应用其新功能。
  - 升级 `snowflake` 依赖版本为 `2.0.2.a` 并解决兼容性问题，以规避漏洞。

- 优化文件格式。
  - 优化 `*.properties` 文件的格式。
  - 优化 `opt-*.xml` 文件的格式。
  - 优化 `application-context-*.xml` 文件的格式。
  - 优化 `pom.xml` 文件的格式。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## 更早的版本

[View all changelogs](./changelogs)
