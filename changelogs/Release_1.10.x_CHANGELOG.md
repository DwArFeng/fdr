# ChangeLog

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
