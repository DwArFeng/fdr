# ChangeLog

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

### 更早的版本

[View all changelogs](./changelogs)
