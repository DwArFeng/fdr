# ChangeLog

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
