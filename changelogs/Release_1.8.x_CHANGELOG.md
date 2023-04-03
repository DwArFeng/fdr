# ChangeLog

### Release_1.8.7_20201030_build_A

#### 功能构建

- 优化 spring-telqos 的配置。
- 优化 dubbo 的配置。
- 完善 PartialDrainPusher 的文档注释。
- 消除预设配置文件中的真实的 ip 地址。
- 优化 pom.xml 中的不合理配置。
- 优化不合理的 pom.xml。
- 升级 spring-terminator 依赖至 1.0.7.a。
- 优化 impl 模块的日志配置文件，使其支持自定义控制台与文件的输出字符集。
- 优化 ConsumeHandlerImpl 代码的换行。
- 为 RecordQosServiceImpl 中的部分代码加入了行为分析注解。

#### Bug修复

- 添加 LogPusher 缺失的 @Component 注解。

#### 功能移除

- (无)

---

### Release_1.8.6_20201018_build_A

#### 功能构建

- 将 application-context-task.xml 中的全部参数设置为可配置参数。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.8.5_20201015_build_A

#### 功能构建

- 将 RecordHandler.setBufferParameters 更名为 RecordHandler.setBufferSize。

#### Bug修复

- 修正 MapperHandlerImpl 中错误的异常抛出。
- 去除 RecordLocalCacheCommand.handleC 中多余的入口参数。
- 修正错误的文档注释。
- 删除无用字段 RecordCommand.LOGGER。

#### 功能移除

- (无)

---

### Release_1.8.4_20201014_build_A

#### 功能构建

在 banner.txt 中加入节点的描述。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.8.3_20201013_build_A

#### 功能构建

- 调整 application-context-telqos.xml 中 qos 指令的顺序。
- 引入新版本 spring-telqos 中的指令 memory。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.8.2_20201011_build_B

#### 功能构建

- (无)

#### Bug修复

- 修改 fdr-impl test 代码中不合理的配置文件。
- 修改 telqos banner 中错误的版本号。

#### 功能移除

- (无)

---

### Release_1.8.2_20201011_build_A

#### 功能构建

- 优化记录功能开启以及停止时记录者和消费者的日志输出。
- 增加记录者和消费者的缓存监视功能。
- 优化 spring-telqos 框架。
   - 升级 spring-telqos 依赖版本至 1.1.0.a。
   - 改进 spring-telqos 配置文件。

#### Bug修复

- 修正 MockSource 中错误的单词拼写。

#### 功能移除

- (无)

---

### Release_1.8.1_20201007_build_A

#### 功能构建

- 增加 QOS 功能。
   - 添加 dubbo 指令。
   - 添加 rec 指令。
- 更改 record 指令格式，使其与其它指令格式保持一致。
- 更改 RecordHandlerImpl 为多线程消费模式，大大提高单节点数据记录的处理能力。
- 将 application-context-task.xml 中的参数设置为可配置参数。
- 升级 sprint-telqos 依赖至 1.0.3.a。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.8.0_20201004_build_A

#### 功能构建

- 升级 spring-terminator 依赖至1.0.5.a，并使用其新功能简化 Launcher 代码。
- 删除 pom.xml 文件中的 NBSP 空格。
- 增强 RecordQosService。
   - com.dwarfeng.fdr.stack.service.RecordQosService.getRecordContext
   - com.dwarfeng.fdr.stack.service.RecordQosService.getConsumerStatus
   - com.dwarfeng.fdr.stack.service.RecordQosService.setConsumerParameters
- 增强 ConsumeHandler。
   - com.dwarfeng.fdr.stack.handler.ConsumeHandler.bufferedSize
- 增加 QOS 功能。
   - 添加 spring-telqos 依赖。
   - 添加 shutdown 指令。
   - 添加 csu 指令。
   - 添加 record 指令。

#### Bug修复

- (无)

#### 功能移除

- (无)
