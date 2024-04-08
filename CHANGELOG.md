# ChangeLog

### Release_1.11.5_20240408_build_A

#### 功能构建

- (无)

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.11.4_20230616_build_A

#### 功能构建

- 依赖升级。
  - 升级 `spring` 依赖版本为 `5.3.27` 以规避漏洞。
  - 升级 `dubbo` 依赖版本为 `2.7.22` 以规避漏洞。
  - 升级 `guava` 依赖版本为 `32.0.1-jre` 以规避漏洞。
  - 升级 `snowflake` 依赖版本为 `1.4.11.a` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.3.3.a` 以规避漏洞。
  - 升级 `spring-terminator` 依赖版本为 `1.0.11.a` 以规避漏洞。
  - 升级 `spring-telqos` 依赖版本为 `1.1.6.a` 以规避漏洞。

- 优化了 AbstractResetter 的代码结构，同时对其实现进行了修改。
  - com.dwarfeng.fdr.impl.handler.resetter.CronResetter。
  - com.dwarfeng.fdr.impl.handler.resetter.DubboResetter。
  - com.dwarfeng.fdr.impl.handler.resetter.FixedDelayResetter。
  - com.dwarfeng.fdr.impl.handler.resetter.FixedRateResetter。
  - com.dwarfeng.fdr.impl.handler.resetter.NeverResetter。

#### Bug修复

- 修复 `DubboResetter` 注册微服务时没有指定 `group` 的问题。

#### 功能移除

- (无)

---

### Release_1.11.3_20230404_build_A

#### 功能构建

- 启停脚本优化。
  - 优化 Windows 系统的启动脚本。
  - 优化 Linux 系统的启停脚本。

- 依赖升级。
  - 升级 `snakeyaml` 依赖版本为 `2.0.0` 以规避漏洞。
  - 升级 `netty` 依赖版本为 `4.1.86.Final` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.3.2.a` 以规避漏洞。
  - 升级 `spring` 依赖版本为 `5.3.26` 以规避漏洞。

- 优化变更日志的目录结构。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.11.2_20230316_build_A

#### 功能构建

- 在启动脚本中增加系统参数，以进一步明确程序的运行行为。
  - -Dlog4j.shutdownHookEnabled=false。
  - -Dlog4j2.is.webapp=false。

#### Bug修复

- 修复 `assembly.xml` 中缺失的打包配置。

#### 功能移除

- (无)

---

### Release_1.11.1_20230314_build_A

#### 功能构建

- 优化 `ResetProcessor` `Bean` 注入时不必要的可选配置。

- 依赖升级。
  - 升级 `dubbo` 依赖版本为 `2.7.21` 以规避漏洞。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.11.0_20230313_build_A

#### 功能构建

- 重构 `node` 模块。

- 重构项目的记录机制。

- 增加重置机制，实现记录功能和映射功能的重置。

- `Dubbo` 微服务增加分组配置。

- 优化文件格式。
  - 优化 `opt-*.xml` 文件的格式。
  - 优化 `application-context-*.xml` 文件的格式。
  - 优化 `pom.xml` 文件的格式。

- 使用 `subgrade` 工具库替代本地缓存实现。

- 使用 `MapStruct` 重构 `BeanTransformer`。

- 依赖升级。
  - 升级 `subgrade` 依赖版本为 `1.3.1.a` 以规避漏洞。

#### Bug修复

- (无)

#### 功能移除

- 删除不需要的依赖。
  - 删除 `dozer` 依赖。

---

### Release_1.10.5_20221125_build_A

#### 功能构建

- 优化依赖。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.10.4_20221124_build_A

#### 功能构建

- 优化项目的 `assembly.xml`。

- 优化 QOS 指令的代码结构。

- 增加依赖。
  - 增加依赖 `protobuf` 以规避漏洞，版本为 `3.19.6`。
  - 增加依赖 `guava` 以规避漏洞，版本为 `31.1-jre`。
  - 增加依赖 `gson` 以规避漏洞，版本为 `2.8.9`。
  - 增加依赖 `snakeyaml` 以规避漏洞，版本为 `1.33`。
  - 增加依赖 `jackson` 以规避漏洞，版本为 `2.14.0`。

- 依赖升级。
  - 升级 `kafka` 依赖版本为 `2.6.3` 以规避漏洞。
  - 升级 `spring-kafka` 依赖版本为 `2.8.10` 以规避漏洞。
  - 升级 `mysql` 依赖版本为 `8.0.31` 以规避漏洞。
  - 升级 `jedis` 依赖版本为 `3.8.0` 以规避漏洞。
  - 升级 `spring-data-redis` 依赖版本为 `2.7.5` 以规避漏洞。
  - 升级 `dubbo` 依赖版本为 `2.7.18` 以规避漏洞。
  - 升级 `zookeeper` 依赖版本为 `3.5.7` 以规避漏洞。
  - 升级 `curator` 依赖版本为 `4.3.0` 以规避漏洞。
  - 升级 `hibernate-validator` 依赖版本为 `6.2.5.Final` 以规避漏洞。
  - 升级 `dutil` 依赖版本为 `beta-0.3.2.a` 以规避漏洞。
  - 升级 `snowflake` 依赖版本为 `1.4.10.a` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.2.14.a` 以规避漏洞。
  - 升级 `spring-terminator` 依赖版本为 `1.0.10.a` 以规避漏洞。
  - 升级 `spring-telqos` 依赖版本为 `1.1.5.a` 以规避漏洞。
  - 升级 `dcti` 依赖版本为 `1.1.5.a` 以规避漏洞。
  - 升级 `groovy` 依赖版本为 `4.0.6` 以规避漏洞。

#### Bug修复

- 修正 `pom.xml` 中的依赖坐标错误。

#### 功能移除

- 删除不需要的依赖。
  - 删除 `el` 依赖。
  - 删除 `zkclient` 依赖。
  - 删除 `commons-lang3` 依赖。

---

### Release_1.10.3_20220915_build_A

#### 功能构建

- 依赖升级。
  - 升级 `subgrade` 依赖版本为 `1.2.10.a` 以规避漏洞。
  - 升级 `snowflake` 依赖版本为 `1.4.9.a` 以规避漏洞。
  - 升级 `dcti` 依赖版本为 `1.1.4.a` 以规避漏洞。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.10.2_20220905_build_A

#### 功能构建

- 插件升级。
  - 升级 `maven-deploy-plugin` 插件版本为 `2.8.2`。

- 依赖升级。
  - 升级 `subgrade` 依赖版本为 `1.2.9.a`。
  - 升级 `spring-terminator` 依赖版本为 `1.0.9.a`。
  - 升级 `dutil` 依赖版本为 `beta-0.3.1.a` 以规避漏洞。
  - 升级 `spring-telqos` 依赖版本为 `1.1.4.a` 以规避漏洞。
  - 升级 `snowflake` 依赖版本为 `1.4.8.a`。
  - 升级 `dcti` 依赖版本为 `1.1.3.a` 以规避漏洞。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.10.1_20220826_build_A

#### 功能构建

- 优化 MappingLookupSessionInfo 实体中的字段。

- 修正部分接口实现的日志记录。
  - com.dwarfeng.fdr.impl.handler.FilteredValueMappingLookupHandlerImpl。
  - com.dwarfeng.fdr.impl.handler.PersistenceValueMappingLookupHandlerImpl。
  - com.dwarfeng.fdr.impl.handler.TriggeredValueMappingLookupHandlerImpl。

#### Bug修复

- 修正 application-context-aop.xml 配置文件中存在的错误。

- 解决部分处理器实现发生异常后资源无法释放的问题。
  - com.dwarfeng.fdr.impl.handler.FilteredValueMappingLookupHandlerImpl。
  - com.dwarfeng.fdr.impl.handler.PersistenceValueMappingLookupHandlerImpl。
  - com.dwarfeng.fdr.impl.handler.TriggeredValueMappingLookupHandlerImpl。

- 修正 Mysql8 本地查询中的错误。
  - com.dwarfeng.fdr.impl.dao.nativelookup.Mysql8FilteredValueNativeLookup。
  - com.dwarfeng.fdr.impl.dao.nativelookup.Mysql8PersistenceValueNativeLookup。
  - com.dwarfeng.fdr.impl.dao.nativelookup.Mysql8TriggeredValueNativeLookup。

- 修正映射查询指令错误的显示格式。

- 修正发现的映射查询存在的错误。
  - com.dwarfeng.fdr.impl.handler.FilteredValueMappingLookupHandlerImpl。
  - com.dwarfeng.fdr.impl.handler.PersistenceValueMappingLookupHandlerImpl。
  - com.dwarfeng.fdr.impl.handler.TriggeredValueMappingLookupHandlerImpl。

- 修正部分接口实现中声明周期函数未加注解的错误。
  - com.dwarfeng.fdr.impl.handler.FilteredValueMappingLookupHandlerImpl。
  - com.dwarfeng.fdr.impl.handler.PersistenceValueMappingLookupHandlerImpl。
  - com.dwarfeng.fdr.impl.handler.TriggeredValueMappingLookupHandlerImpl。

#### 功能移除

- (无)

---

### Release_1.10.0_20220825_build_A

#### 功能构建

- 重写映射查询机制，大幅提高映射查询性能。

- 增加 CHILD_FOR_POINT_BETWEEN_RB_OPEN 预设查询。

- 将数据库的连接数量改为可配置项。

- 使用 `subgrade` 的加速查询机制重写部分数据访问层实现。
  - com.dwarfeng.fdr.impl.dao.PersistenceValueDaoImpl。
  - com.dwarfeng.fdr.impl.dao.FilteredValueDaoImpl。
  - com.dwarfeng.fdr.impl.dao.TriggeredValueDaoImpl。

- 依赖升级。
  - 升级 `dutil` 依赖版本为 `beta-0.3.0.a`。
  - 升级 `subgrade` 依赖版本为 `1.2.8.a`。

#### Bug修复

- 修正 BETWEEN 查询预设处理边界时的行为。

#### 功能移除

- (无)

---

### Release_1.9.7_20220625_build_A

#### 功能构建

- 重写维护服务的 `lookupAsList` 方法，以提高查询速度。
  - com.dwarfeng.fdr.impl.service.FilterInfoMaintainServiceImpl。
  - com.dwarfeng.fdr.impl.service.PersistenceValueMaintainServiceImpl。
  - com.dwarfeng.fdr.impl.service.TriggeredValueMaintainServiceImpl。
  - com.dwarfeng.fdr.impl.service.MapperSupportMaintainServiceImpl。
  - com.dwarfeng.fdr.impl.service.RealtimeValueMaintainServiceImpl。
  - com.dwarfeng.fdr.impl.service.FilteredValueMaintainServiceImpl。
  - com.dwarfeng.fdr.impl.service.FilterSupportMaintainServiceImpl。
  - com.dwarfeng.fdr.impl.service.PointMaintainServiceImpl。
  - com.dwarfeng.fdr.impl.service.TriggerInfoMaintainServiceImpl。
  - com.dwarfeng.fdr.impl.service.TriggerSupportMaintainServiceImpl。

#### Bug修复

- 修正 Nsql 中不正确的索引使用。
  - com.dwarfeng.fdr.impl.dao.nsql.Mysql8FilteredValueNsqlLookup。
  - com.dwarfeng.fdr.impl.dao.nsql.Mysql8PersistenceValueNsqlLookup。
  - com.dwarfeng.fdr.impl.dao.nsql.MysqlTriggeredValueNsqlLookup。

#### 功能移除

- (无)

---

### Release_1.9.6_20220624_build_A

#### 功能构建

- 为部分缓存增加批量操作功能。
  - com.dwarfeng.fdr.stack.cache.FilterSupportCache。
  - com.dwarfeng.fdr.stack.cache.MapperSupportCache。
  - com.dwarfeng.fdr.stack.cache.TriggerSupportCache。

- 为部分数据访问层增加批量操作功能。
  - com.dwarfeng.fdr.stack.dao.FilterSupportDao。
  - com.dwarfeng.fdr.stack.dao.MapperSupportDao。
  - com.dwarfeng.fdr.stack.dao.TriggerSupportDao。

- 为部分维护服务增加批量操作功能。
  - com.dwarfeng.fdr.stack.service.FilterSupportMaintainService。
  - com.dwarfeng.fdr.stack.service.MapperSupportMaintainService。
  - com.dwarfeng.fdr.stack.service.RealtimeValueMaintainService。
  - com.dwarfeng.fdr.stack.service.TriggerSupportMaintainService。

- 增加预设查询。
  - com.dwarfeng.fdr.stack.service.PointMaintainService.REMARK_LIKE。
  - com.dwarfeng.fdr.stack.service.PointMaintainService.NAME_LIKE_PERSISTENCE_ENABLED。
  - com.dwarfeng.fdr.stack.service.PointMaintainService.NAME_LIKE_REALTIME_ENABLED。
  - com.dwarfeng.fdr.stack.service.PointMaintainService.NAME_LIKE_PERSISTENCE_DISABLED。
  - com.dwarfeng.fdr.stack.service.PointMaintainService.NAME_LIKE_REALTIME_DISABLED。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.9.5_20220622_build_A

#### 功能构建

- 为 `dubbo` 增加超时时间的配置选项。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.9.4_20220622_build_A

#### 功能构建

- 对持久值、被触发值、被过滤值增加向后查询功能。
  - com.dwarfeng.fdr.stack.service.PersistenceValueMaintainService.rear。
  - com.dwarfeng.fdr.stack.service.TriggeredValueMaintainService.rear。
  - com.dwarfeng.fdr.stack.service.FilteredValueMaintainService.rear。

- 依赖升级。
  - 升级 `junit` 依赖版本为 `4.13.2` 以规避漏洞。
  - 升级 `spring` 依赖版本为 `5.3.20` 以规避漏洞。
  - 升级 `mysql` 依赖版本为 `8.0.28` 以规避漏洞。
  - 升级 `fastjson` 依赖版本为 `1.2.83` 以规避漏洞。
  - 升级 `dubbo` 依赖版本为 `2.7.15` 以规避漏洞。
  - 升级 `netty` 依赖版本为 `4.1.77.Final` 以规避漏洞。
  - 升级 `hibernate` 依赖版本为 `5.4.24.Final` 以规避漏洞。
  - 升级 `hibernate-validator` 依赖版本为 `6.0.21.Final` 以规避漏洞。
  - 升级 `log4j2` 依赖版本为 `2.17.2` 以规避漏洞。
  - 升级 `dutil` 依赖版本为 `beta-0.2.2.a` 以规避漏洞。
  - 升级 `snowflake` 依赖版本为 `1.4.6.a` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.2.7.a` 以规避漏洞。
  - 升级 `spring-terminator` 依赖版本为 `1.0.8.a` 以规避漏洞。
  - 升级 `spring-telqos` 依赖版本为 `1.1.3.a` 以规避漏洞。
  - 升级 `dcti` 依赖版本为 `1.1.2.a` 以规避漏洞。
  - 升级 `groovy` 依赖版本为 `3.0.7` 以规避漏洞。

- 数据值实体的JPA注解增加索引，自动建表时会自动添加索引。
  - com.dwarfeng.fdr.impl.bean.entity.HibernateFilteredValue。
  - com.dwarfeng.fdr.impl.bean.entity.HibernatePersistenceValue。
  - com.dwarfeng.fdr.impl.bean.entity.HibernateTriggeredValue。

- 增加方法 `NativeSqlLookup.init`， 允许本地 SQL 查询在程序启动时进行初始化操作，如检查数据库结构等。

- 优化 Mysql8NativeSqlLookup 性能。
  - 为全部查询添加 `FORCE INDEX` 强制索引，强制 MySQL 选择最优索引进行查询。

- 规范化命名。
  - `NSQLQuery` 规范化命名为 `NsqlLookup`。
  - `MySQL8XXX` 规范化命名为 `Mysql8XXX`。

- 优化 `NativeSqlLookup` 子接口中的方法名称。
  - com.dwarfeng.fdr.impl.dao.FilteredValueNsqlLookup。
  - com.dwarfeng.fdr.impl.dao.PersistenceValueNsqlLookup。
  - com.dwarfeng.fdr.impl.dao.TriggeredValueNsqlLookup。

#### Bug修复

- 修正 PointCrudOperation 中错误的代码。

#### 功能移除

- 去除无用的项目依赖。
  - spring-web。
  - spring-mvc。

- 删除不需要的依赖。
  - 删除 `joda-time` 依赖。
  - 删除 `commons-io` 依赖。
  - 删除 `commons-net` 依赖。
  - 删除 `http-components` 依赖。
  - 删除 `noggit` 依赖。

---

### Release_1.9.3_20210625_build_A

#### 功能构建

- 优化部分映射器的参数组织格式。
- 优化部分映射器的参数说明。
- 修正 RecordCommand 中的参数细节。

#### Bug修复

- 修复栅格查询功能时指定时间区间之内没有数据时抛出异常的 bug。

#### 功能移除

- (无)

---

### Release_1.9.2_20210203_build_A

#### 功能构建

- 去除 solrj 依赖。
- 去除配置文件 application-context-redis.xml 中多余的注释。
- 去除配置文件中多余的注释。
- 去除无用的依赖版本属性。
- 优化 ServiceExceptionCodes 的内部方法结构。
- 为 PointNotExistsException 添加文档注释。
- 优化 RecordHandler 中的文档注释。

#### Bug修复

- 去除 DctiKafkaSource 中可能引起程序行为异常的配置项。

#### 功能移除

- (无)

---

### Release_1.9.1_20201110_build_B

#### 功能构建

- 升级subgrade依赖至1.2.0.b。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.9.1_20201110_build_A

#### 功能构建

- 更正 application-context-scan.xml 配置文件中错误的格式。
- 升级subgrade依赖至1.2.0.a。
- 优化 BehaviorAnalyse，取消有可能产生大量文本的返回结果以及入口参数的记录。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.9.0_20201106_build_A

#### 功能构建

- 更新 pom.xml 中 commons-io 的 groupId。
- 消除预设配置文件中的真实的 ip 地址。
- 优化本地 SQL 查询接口结构。
- 优化列表类自动装配字段的代码结构。
- 对数据维护服务增加前刻点查询方法。
  - com.dwarfeng.fdr.stack.service.FilteredValueMaintainService.previous
  - com.dwarfeng.fdr.stack.service.PersistenceValueMaintainService.previous
  - com.dwarfeng.fdr.stack.service.TriggeredValueMaintainService.previous
- com.dwarfeng.fdr.stack.service.MappingLookupService 完全重写，功能增强。
- 升级subgrade依赖至1.1.8.a。
- 新增映射 QOS 服务，以及 telqos 指令实现。
  - com.dwarfeng.fdr.stack.service.MapQosService
- 增加映射器。
  - com.dwarfeng.fdr.impl.handler.mapper.GeneralGridMapperRegistry
  - com.dwarfeng.fdr.impl.handler.mapper.NumericGridMapperRegistry

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### 更早的版本

[View all changelogs](./changelogs)
