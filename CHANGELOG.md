# ChangeLog

### Release_1.9.4_20210812_build_A

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

---

### Release_1.7.2_20200720_build_A

#### 功能构建

- 新增 MapperRegistry。
  - com.dwarfeng.fdr.impl.handler.mapper.IdentityMapperRegistry
- 优化 Filter, Trigger, Mapper, Pusher 的代码结构。
  - com.dwarfeng.fdr.impl.handler.filter.AbstractFilterRegistry
  - com.dwarfeng.fdr.impl.handler.trigger.AbstractTriggerRegistry
  - com.dwarfeng.fdr.impl.handler.mapper.AbstractMapperRegistry
  - com.dwarfeng.fdr.impl.handler.pusher.AbstractPusher

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.7.1_20200714_build_A

#### 功能构建

- 升级subgrade依赖至1.1.2.a。
- 更改以下异常的继承类。
  - com.dwarfeng.fdr.stack.exception.PersistenceDisabledException
  - com.dwarfeng.fdr.stack.exception.RealtimeDisabledException

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.7.0_20200630_build_B

#### 功能构建

- 优化 node 模块的子模块的 pom.xml。
  - node-all
  - node-inspect
  - node-maintain
  - node-record

#### Bug修复

- 修正spring配置文件的错误。

#### 功能移除

- (无)

---

### Release_1.7.0_20200630_build_A

#### 功能构建

- 升级subgrade依赖至1.1.1.b。
- 使用写入服务优化持久值、被过滤值、被触发值的写入效率。
- 优化MockSource，提供更合理的数据记录以及缓冲容量的观察方式。
- 规范停止脚本 fdr-stop.sh。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.6.10_20200527_build_A

#### 功能构建

- (无)

#### Bug修复

- 修改com.dwarfeng.fdr.impl.handler.source.MockSource下线时造成线程阻塞的bug。

#### 功能移除

- (无)

---

### Release_1.6.9_20200525_build_A

#### 功能构建

- (无)

#### Bug修复

- 修复项目异常偏移设置错误的bug。

#### 功能移除

- (无)

---

### Release_1.6.8_20200511_build_A

#### 功能构建

- 完善@Transactional注解的回滚机制。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.6.7_20200503_build_A

#### 功能构建

- 升级subgrade依赖至1.0.1.a，以避免潜在的RedisDao的分页bug。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.6.6_20200426_build_A

#### 功能构建

- 升级subgrade依赖至1.0.0.a，修复轻微不兼容的错误。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.6.5_20200424_build_A

#### 功能构建

- 将部分实体的Crud服务升级为BatchCrud服务。
  - com.dwarfeng.fdr.stack.service.PointMaintainService
  - com.dwarfeng.fdr.stack.service.FilterInfoMaintainService
  - com.dwarfeng.fdr.stack.service.TriggerInfoMaintainService

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.6.4_20200422_build_A

#### 功能构建

- 优化com.dwarfeng.fdr.impl.handler.consumer.RealtimeEventConsumer的异常处理流程。
- 优化com.dwarfeng.fdr.impl.handler.consumer.RealtimeValueConsumer的异常处理流程。
- 更新README.md说明文件。
- 调整部分实体维护服务的CrudOperation，对齐方法get和batchGet。
  - com.dwarfeng.fdr.impl.service.operation.FilteredValueCrudOperation
  - com.dwarfeng.fdr.impl.service.operation.PersistenceValueCrudOperation
  - com.dwarfeng.fdr.impl.service.operation.TriggeredValueCrudOperation

#### Bug修复

- 修改拼写错误的包名。

#### 功能移除

- (无)

---

### Release_1.6.3_20200417_build_C

#### 功能构建

- (无)

#### Bug修复

- 修复几处pom.xml的问题。

#### 功能移除

- (无)

---

### Release_1.6.3_20200415_build_B

#### 功能构建

- (无)

#### Bug修复

- 修复RegexFilterMaker.RegexFilter过滤器的返回字段错误。

#### 功能移除

- (无)

---

### Release_1.6.3_20200414_build_A

#### 功能构建

- 添加 PersistenceValueMaintainService.BETWEEN 预设查询。
- 添加 FilteredValueMaintainService.BETWEEN 预设查询。
- 添加 TriggeredValueMaintainService.BETWEEN 预设查询。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.6.2_20200413_build_B

#### 功能构建

- (无)

#### Bug修复

- 修复WebInputPersistenceValue.toStackBean方法不是静态方法的bug。

#### 功能移除

- (无)

---

### Release_1.6.2_20200413_build_A

#### 功能构建

- RealtimeValueMaintainService实现全部实体查询。
- PersistenceValueMaintainService实现全部实体查询。
- FilteredValueMaintainService实现全部实体查询。
- TriggerValueMaintainService实现全部实体查询。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.6.1_20200412_build_A

#### 功能构建

- 优化GroovyFilterSupporter.provideExampleContent方法的实现方式。
- 优化GroovyTriggerSupporter.provideExampleContent方法的实现方式。
- 实现com.dwarfeng.fdr.impl.handler.mapper.GroovyMapperMaker映射器构造器。

#### Bug修复

- 修复部分模块dubbo组件未暴露MapperSupportMaintainService服务的问题。
  - node-all
  - node-maintain

#### 功能移除

- RealtimeValueMaintainService实现全部实体查询。

---

### Release_1.6.0_20200407_build_A

#### 功能构建

- 将可放宽装配的组件列表设置为 @Autowired(required = false)
  - com.dwarfeng.fdr.impl.handler.FilterHandlerImpl
  - com.dwarfeng.fdr.impl.handler.MapperHandlerImpl
  - com.dwarfeng.fdr.impl.handler.PushHandlerImpl
  - com.dwarfeng.fdr.impl.handler.TriggerHandlerImpl
  - com.dwarfeng.fdr.impl.service.FilterSupportMaintainServiceImpl
  - com.dwarfeng.fdr.impl.service.TriggerSupportMaintainServiceImpl
- Filter,Trigger相关结构实现功能分离。
  - com.dwarfeng.fdr.impl.handler.FilterSupporter
  - com.dwarfeng.fdr.impl.handler.TriggerSupporter
- MapperSupport实体及其服务实现。
- 更改优化node模块的程序结构。
  - node-all
  - node-maintain
  - node-record
- 添加node-inspect模块。

#### Bug修复

- (无)

#### 功能移除

- ~~删除api模块以及解除judge依赖~~

---

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

---

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

---

### Release_1.3.1_20200319_build_A

#### 功能构建

- 优化AbstractConsumeHandler启动和停止时的代码。
- 构建 fdr-plugin 模块。
- 新建 kafka source 插件。

#### Bug修复

- 开启 redis 事务。
- 修复AbstractConsumeHandler停止后后台任务可能未执行完的bug。

#### 功能移除

- (无)

---

### Release_1.3.0_20200316_build_A

#### 功能构建

- 建立 RecordControlHandler 并实现记录服务的上下线。
- 将数据记录模块集成至dcti。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Release_1.2.0_20200314_build_A

#### 功能构建

- 优化数据记录的效率。
- 实现 Qos 服务。
- 更改启动器为 spring-terminator 风格。
- 更新依赖 dutil 至版本 beta-0.2.1.a。

#### Bug修复

- (无)

#### 功能移除

- ~~去除数据点与数据值质检的外键约束。~~

---

### Release_1.1.0_20200308_build_A

#### 功能构建

- 工程名称更改为fdr。
- 添加 FastJsonDataInfo 和 JSFixedFastJsonDataInfo。
- 改造项目结构。
- 将RealtimeValue存储方式改为Redis数据库。
- 增加Point实体的预设查询。
- 升级subgrade版本至beta-0.3.1.c。
- 升级subgrade版本至1.2.3.a。
- 完善过滤器和触发器的工作方式。

#### Bug修复

- 解决依赖冲突。
- 修正装配文件错误的位置。
- 将//noinspection替换为@SupressWarning。

#### 功能移除

- ~~删除Category~~

---

### Release_1.0.0_20200213_build_A

#### 功能构建

- 实现了基于数据库的数据维护服务节点。
- 实现了基于数据库的数据记录服务节点。

#### Bug修复

- (无)

#### 功能移除

- (无)
