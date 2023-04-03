# ChangeLog

### Release_1.5.3_20200406_build_A

#### 功能构建

- 实现com.dwarfeng.fdr.impl.handler.mapper.MaxMapperMaker。
- 实现com.dwarfeng.fdr.impl.handler.mapper.MinMapperMaker。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.5.2_20200404_build_C

#### 功能构建

- 删除GroovyFilter和GroovyTrigger中无用的字段。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.5.2_20200404_build_B

#### 功能构建

- 优化GroovyFilter和GroovyTrigger工作时的异常抛出方式。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.5.2_20200403_build_A

#### 功能构建

- 实现Groovy过滤器以及Groovy触发器。
   - com.dwarfeng.fdr.impl.handler.filter.GroovyFilterMaker
   - com.dwarfeng.fdr.impl.handler.trigger.GroovyTriggerMaker

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.5.1_20200403_build_A

#### 功能构建

- 新建fdr-api模块。
- 升级dcti依赖至1.1.0.a。
- 删除项目自身TimedValue，使用dcti的TimedValue对象。
- 实现com.dwarfeng.fdr.api.integration.judge.FdrDubboRepository。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.5.0_20200402_build_A

#### 功能构建

- 优化数据查询架构。
- 修改assembly.xml，将LICENSE文件装配至工程目录。
- 更改持久值、被过滤值、被触发值在数据库中的定义为TEXT。
- 实现DctiKafkaPusher，原有的KafkaPusher更名为NativeKafkaPusher。
- 原有的KafkaSource更名为DctiKafkaSource，并优化配置。

#### Bug修复

- 修正启动器对象错误的日志文本。

#### 功能移除

- ~~删除 FilteredLookupService 服务。~~
- ~~删除 TriggeredLookupService 服务。~~
