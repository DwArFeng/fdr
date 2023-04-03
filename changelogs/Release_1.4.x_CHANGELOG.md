# ChangeLog

### Release_1.4.2_20200330_build_D

#### 功能构建

- 使用 @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) 代替 @Scope("prototype")。
- 优化RecordQosServiceImpl的stopRecord方法。
- 调整com.dwarfeng.fdr.node.all.launcher.Launcher部分注释语序。

#### Bug修复

- 修正ConsumeHandlerImpl中错误的日志文本。
- 修正ConsumeHandlerImpl.ConsumeBuffer中的部分get方法未线程同步的bug。
- 修正ConsumeHandlerImpl.ConsumeBuffer.setBufferParameters潜在的取值问题bug。

#### 功能移除

- (无)

---

### Release_1.4.2_20200329_build_C

#### 功能构建

- 优化部分properties文件中的注释。

#### Bug修复

- 修正MultiPusher.SUPPORT_TYPE值不正确的bug。
- 修正KafkaPusher类的不正确的注解。

#### 功能移除

- (无)

---

### Release_1.4.2_20200329_build_B

#### 功能构建

- 优化部分properties文件中的注释。

#### Bug修复

- 修复部分实体的CriteriaMaker中的错误。
- 修复部分模块中pom.xml的错误。

#### 功能移除

- (无)

---

### Release_1.4.2_20200327_build_A

#### 功能构建

- 更新snowflake依赖至1.2.3.b。
- 添加fdr-node-all模块。

#### Bug修复

- 修复fdr-node-record打包文件mainClass不正确的bug。
- 修复部分spring的配置错误。

#### 功能移除

- (无)

---

### Release_1.4.1_20200326_build_B

#### 功能构建

- 更新subgrade依赖至beta-0.3.2.b。
- 更新dcti依赖至1.0.0.c。

#### Bug修复

- 修正node-all模块中pom.xml错误的配置。

#### 功能移除

- (无)

---

### Release_1.4.1_20200326_build_A

#### 功能构建

- Mock数据源实现，将压力测试工具以数据源的形式整合到项目中。
- 数据查询映射接口实现。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.4.0_20200323_build_A

#### 功能构建

- 删除 com.dwarfeng.fdr.stack.handler.Handler，所有的处理器均改为继承 subgrade 的 Handler。
- 更改项目结构，将不同的功能分布在多个节点上。建立node-maintain和node-record两个节点。
- 将kafka数据源整合到项目当中，放弃dubbo数据源。
- 增加多种pusher，事件的推送由之前的仅kafka增加为可自由选择的多种数据源。
- 建立assembly打包文件，项目package后将直接输出可供linux平台运行的.tar.gz文件。

#### Bug修复

- (无)

#### 功能移除

- ~~放弃dubbo数据源。~~
