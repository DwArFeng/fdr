# ChangeLog

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
