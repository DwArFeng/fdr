# ChangeLog

## Release_2.3.0_20250625_build_A

### 功能构建

- 优化 com.dwarfeng.fdr.stack.service.RecordQosService 服务。
  - 使用 RecordLocalCache 代替 RecordLocalCacheHandler.RecordContext。
  - 修改相关方法的签名。

- 配置文件格式优化。
  - 优化 fdr/record.properties 中的注释格式。
  - 优化 fdr/consume.properties 中的注释格式。

- 代码格式优化。
  - 优化 RecordLocalCacheCommand 的 COMMAND_OPTION_LOOKUP 选项的输出格式。
  - 优化 MapLocalCacheHandler 接口的文档注释。
  - 优化 RecordLocalCacheHandler 接口的文档注释。
  - 优化 RecordHandlerImpl.record 方法的 lock 代码块的格式。
  - 优化 ConsumeHandlerImpl 的日志输出格式。
  - 优化 RecordLocalCacheHandler.RecordContext.toString 方法的输出格式。

- 优化 impl 模块下的 `logging` 目录结构。
  - 将 `logging/settings-windows.xml` 重命名为 `settings-ref-windows.xml`，以消除文件名的歧义。
  - 更新 `logging/README.md` 中的相关说明。

- 优化 node 模块下的 `logging` 目录结构。
  - 将 `logging/settings-linux.xml` 重命名为 `settings-ref-linux.xml`，以消除文件名的歧义。
  - 将 `logging/settings-windows.xml` 重命名为 `settings-ref-windows.xml`，以消除文件名的歧义。
  - 更新 `logging/README.md` 中的相关说明。

- 优化数据源机制。
  - 增加 com.dwarfeng.fdr.stack.service.SourceQosService。

- 优化记录侧消费机制。
  - 优化 `[最大空闲时间] <= 0` 时的行为，使其更加符合直觉。

- 优化部分类中部分方法的行为分析行为。
  - com.dwarfeng.fdr.impl.service.AbstractViewService。

- SPI 目录结构优化。
  - 将桥接机制的 SPI 接口与抽象类提相关代码文件提升至 `sdk` 模块中。
  - 将过滤机制的 SPI 接口与抽象类提相关代码文件提升至 `sdk` 模块中。
  - 将映射机制的 SPI 接口与抽象类提相关代码文件提升至 `sdk` 模块中。
  - 将推送机制的 SPI 抽象类提相关代码文件提升至 `sdk` 模块中。
  - 将重置机制的 SPI 抽象类提相关代码文件提升至 `sdk` 模块中。
  - 将数据源机制的 SPI 抽象类提相关代码文件提升至 `sdk` 模块中。
  - 将触发机制的 SPI 抽象类提相关代码文件提升至 `sdk` 模块中。
  - 将清洗机制的 SPI 抽象类提相关代码文件提升至 `sdk` 模块中。

- 导入运维指令。
  - com.dwarfeng.datamark.service.telqos.*。

- 增加 Hibernate 实体数据标记字段，并应用相关实体侦听器。
  - com.dwarfeng.fdr.impl.bean.entity.HibernateFilterInfo。
  - com.dwarfeng.fdr.impl.bean.entity.HibernatePoint。
  - com.dwarfeng.fdr.impl.bean.entity.HibernateTriggerInfo。
  - com.dwarfeng.fdr.impl.bean.entity.HibernateWasherInfo。

- 增加依赖。
  - 增加依赖 `dwarfeng-datamark` 以应用其新功能，版本为 `1.0.3.a`。

- 优化 `application-context-*.xml` 文件的配置项。
  - application-context-placeholder.xml。

- 优化部分类构造器方法中的参数名。
  - com.dwarfeng.fdr.impl.handler.pusher.AbstractPusher。

- 优化 `changelogs` 目录结构，以适配新的版本规范。

- 优化 `docs/wiki` 目录结构。
  - 将 `docs/wiki/en_US` 目录重命名为 `en-US`，以符合 rfc5646 规范。
  - 将 `docs/wiki/zh_CN` 目录重命名为 `zh-CN`，以符合 rfc5646 规范。
  - 更新 `docs/wiki/README.md` 中的链接指向。
  - 更新 `README.md` 中的链接指向。

- 实现运维指令。
  - com.dwarfeng.fdr.impl.service.telqos.SourceCommand。
  - com.dwarfeng.fdr.impl.service.telqos.SupportCommand。

- 优化支持实体机制。
  - 新建支持 QoS 服务 com.dwarfeng.fdr.stack.service.SupportQosService。
  - 将支持实体维护服务的重置功能迁移至 QoS 服务。

- 优化实体映射器机制。

- 优化部分异常类的名称。
  - RecordStoppedException -> RecordHandlerStoppedException。

- 优化部分 QOS 服务实现的异常处理逻辑，以规避潜在的异常处理问题。
  - com.dwarfeng.fdr.impl.service.MapQosServiceImpl。
  - com.dwarfeng.fdr.impl.service.RecordQosServiceImpl。

- 依赖升级。
  - 升级 `kafka` 依赖版本为 `3.9.0` 以规避漏洞。
  - 升级 `netty` 依赖版本为 `4.1.119.Final` 以规避漏洞。
  - 升级 `snowflake` 依赖版本为 `1.7.1.a` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.5.11.a` 以规避漏洞。
  - 升级 `spring-telqos` 依赖版本为 `1.1.14.a` 以规避漏洞。
  - 升级 `dcti` 依赖版本为 `1.1.12.a` 以规避漏洞。
  - 升级 `dwarfeng-dct` 依赖版本为 `1.0.3.a` 以规避漏洞。
  - 升级 `jackson` 依赖版本为 `2.18.3` 以规避漏洞。
  - 升级 `groovy` 依赖版本为 `4.0.26` 以规避漏洞。

### Bug 修复

- 修正配置类中的错误。
  - ExceptionCodeOffsetConfiguration。

- 修复运维指令中的 bug。
  - 修复 `RecordConsumerCommand` 中 `lc` 以及 `ln` 选项输出信息错误的问题。

- 修复记录侧消费机制中的 bug。
  - 修复 `[批处理] <= 0` 时潜在的取数逻辑异常行为。
  - 修复 `[批处理] <= 0` 时潜在的阻塞逻辑异常行为。

### 功能移除

- 去除实际未使用的接口。
  - com.dwarfeng.fdr.impl.handler.PersistenceKeeper。
  - com.dwarfeng.fdr.impl.handler.RealtimeKeeper。

- 去除支持实体维护服务的重置功能。
  - com.dwarfeng.fdr.stack.service.FilterSupportMaintainService。
  - com.dwarfeng.fdr.stack.service.MapperSupportMaintainService。
  - com.dwarfeng.fdr.stack.service.TriggerSupportMaintainService。
  - com.dwarfeng.fdr.stack.service.WasherSupportMaintainService。

---

## Release_2.2.3_20250319_build_A

### 功能构建

- 优化项目的启停脚本，以规避潜在的路径问题。
  - binres/fdr-stop.sh。
  - binres/fdr-start.sh。

- 依赖升级。
  - 升级 `spring` 依赖版本为 `5.3.39` 以规避漏洞。
  - 升级 `netty` 依赖版本为 `4.1.115.Final` 以规避漏洞。
  - 升级 `zookeeper` 依赖版本为 `3.9.3` 以规避漏洞。
  - 升级 `snowflake` 依赖版本为 `1.5.3.a` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.5.7.a` 以规避漏洞。
  - 升级 `spring-terminator` 依赖版本为 `1.0.14.a` 以规避漏洞。
  - 升级 `spring-telqos` 依赖版本为 `1.1.11.b` 以规避漏洞。

- 为部分重置器的依赖注入对象增加限定符，以避免潜在的冲突。
  - com.dwarfeng.fdr.impl.handler.resetter.DubboResetter。

- 优化部分类中部分方法的行为分析行为。
  - com.dwarfeng.fdr.impl.service.AbstractViewQosService。
  - com.dwarfeng.fdr.impl.service.AbstractViewService。

- 优化部分配置文件中的注释。
  - 优化 `fdr/query.properties` 配置文件中的注释。

- 优化消费者实现中的部分日志的文案。
  - com.dwarfeng.fdr.impl.handler.consumer.KeepConsumer。
  - com.dwarfeng.fdr.impl.handler.consumer.PersistConsumer。

### Bug 修复

- 修复消费者重复推送消费事件的 bug。
  - com.dwarfeng.fdr.impl.handler.consumer.KeepConsumer。
  - com.dwarfeng.fdr.impl.handler.consumer.PersistConsumer。

### 功能移除

- (无)

---

## Release_2.2.2_20241009_build_A

### 功能构建

- 为观察服务增加行为分析注解。
  - com.dwarfeng.fdr.impl.service.AbstractViewService。
  - com.dwarfeng.fdr.impl.service.AbstractViewQosService。

- 清洗器名称优化。
  - 废弃名称不合理的 `IdentityWasherRegistry`，并使用 `IdentityWasherRegistry` 代替。

- 映射器名称优化。
  - 废弃名称不合理的 `IdentityMapperRegistry`，并使用 `IdentityMapperRegistry` 代替。

- 依赖升级。
  - 升级 `protobuf` 依赖版本为 `3.25.5` 以规避漏洞。
  - 升级 `dcti` 依赖版本为 `1.1.9.a` 以规避漏洞。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_2.2.1_20240816_build_A

### 功能构建

- Wiki 编写。
  - docs/wiki/zh_CN/PresetMapperImplements.md。

- 优化部分类的文档注释。
  - com.dwarfeng.fdr.impl.handler.mapper.HighPassCounterMapperRegistry。
  - com.dwarfeng.fdr.impl.handler.mapper.LowPassCounterMapperRegistry。
  - com.dwarfeng.fdr.impl.handler.mapper.LowPassExistenceMapperRegistry。
  - com.dwarfeng.fdr.impl.handler.mapper.LowPassMapperRegistry。

- 为部分类添加 `toString` 方法。
  - com.dwarfeng.fdr.impl.handler.consumer.FilteredPersistConsumer。
  - com.dwarfeng.fdr.impl.handler.consumer.TriggeredPersistConsumer。
  - com.dwarfeng.fdr.impl.handler.consumer.NormalPersistConsumer。
  - com.dwarfeng.fdr.impl.handler.consumer.NormalKeepConsumer。
  - com.dwarfeng.fdr.impl.handler.consumer.FilteredKeepConsumer。
  - com.dwarfeng.fdr.impl.handler.consumer.TriggeredKeepConsumer。

- Wiki 更新。
  - docs/wiki/zh_CN/PresetBridgeImplements.md。
  - docs/wiki/zh_CN/Contents.md。

- 优化部分类中方法的参数。
  - com.dwarfeng.fdr.impl.handler.consumer.FilteredKeepConsumer。
  - com.dwarfeng.fdr.impl.handler.consumer.FilteredPersistConsumer。
  - com.dwarfeng.fdr.impl.handler.consumer.KeepConsumer。
  - com.dwarfeng.fdr.impl.handler.consumer.NormalKeepConsumer。
  - com.dwarfeng.fdr.impl.handler.consumer.NormalPersistConsumer。
  - com.dwarfeng.fdr.impl.handler.consumer.PersistConsumer。
  - com.dwarfeng.fdr.impl.handler.consumer.TriggeredKeepConsumer。
  - com.dwarfeng.fdr.impl.handler.consumer.TriggeredPersistConsumer。
  - com.dwarfeng.fdr.impl.handler.pusher.DrainPusher。
  - com.dwarfeng.fdr.impl.handler.pusher.LogPusher。
  - com.dwarfeng.fdr.impl.handler.pusher.MultiPusher。
  - com.dwarfeng.fdr.impl.handler.pusher.NativeKafkaPusher。
  - com.dwarfeng.fdr.impl.handler.pusher.PusherAdapter。
  - com.dwarfeng.fdr.impl.handler.Pusher。
  - com.dwarfeng.fdr.impl.handler.PushHandlerImpl。
  - com.dwarfeng.fdr.stack.handler.PushHandler。

### Bug 修复

- Wiki 格式修正。
  - docs/wiki/zh_CN/PresetBridgeImplements.md。

### 功能移除

- (无)

---

## Release_2.2.0_20240814_build_A

### 功能构建

- Wiki 编写。
  - docs/wiki/zh_CN/Mapper.md。

- Wiki 更新。
  - docs/wiki/zh_CN/HowToUseTelqosViewCommand.md。
  - docs/wiki/zh_CN/Contents.md。

- 优化 HibernateBridge 的本地查询机制。
  - 增加一般数据本地查询的抽象类 `HibernateBridgeNormalDataNativeLookup`。
  - 增加被过滤数据本地查询的抽象类 `HibernateBridgeFilteredDataNativeLookup`。
  - 增加被触发数据本地查询的抽象类 `HibernateBridgeTriggeredDataNativeLookup`。

- 优化部分映射器行为。
  - 为 FirstMapper 增加配置项，可以选择在 Item 列表为空时的行为。
  - 为 LastMapper 增加配置项，可以选择在 Item 列表为空时的行为。

- 优化部分类的文档注释。
  - com.dwarfeng.fdr.impl.handler.mapper.AggregateMapper。

- 优化部分触发器的示例参数。
  - com.dwarfeng.fdr.impl.handler.trigger.LogicAndTriggerRegistry。
  - com.dwarfeng.fdr.impl.handler.trigger.BooleanTriggerRegistry。
  - com.dwarfeng.fdr.impl.handler.trigger.LogicNotTriggerRegistry。
  - com.dwarfeng.fdr.impl.handler.trigger.RegexTriggerRegistry。

- 优化部分过滤器的示例参数。
  - com.dwarfeng.fdr.impl.handler.filter.ValueTypeFilterRegistry。
  - com.dwarfeng.fdr.impl.handler.filter.LogicAndFilterRegistry。
  - com.dwarfeng.fdr.impl.handler.filter.LogicNotFilterRegistry。
  - com.dwarfeng.fdr.impl.handler.filter.RegexFilterRegistry。

- 优化部分映射器的示例参数。
  - com.dwarfeng.fdr.impl.handler.mapper.EnableRatioMapperRegistry。
  - com.dwarfeng.fdr.impl.handler.mapper.HighPassCounterMapperRegistry。
  - com.dwarfeng.fdr.impl.handler.mapper.HighPassExistenceMapperRegistry。
  - com.dwarfeng.fdr.impl.handler.mapper.HighPassMapperRegistry。
  - com.dwarfeng.fdr.impl.handler.mapper.LowPassCounterMapperRegistry。
  - com.dwarfeng.fdr.impl.handler.mapper.LowPassExistenceMapperRegistry。
  - com.dwarfeng.fdr.impl.handler.mapper.LowPassMapperRegistry。
  - com.dwarfeng.fdr.impl.handler.mapper.SortMapperRegistry。
  - com.dwarfeng.fdr.impl.handler.mapper.ToBooleanMapperRegistry。
  - com.dwarfeng.fdr.impl.handler.mapper.ToDoubleMapperRegistry。
  - com.dwarfeng.fdr.impl.handler.mapper.TrimMapperRegistry。
  - com.dwarfeng.fdr.impl.handler.mapper.WindowMapperRegistry。
  - com.dwarfeng.fdr.impl.handler.mapper.GroovyMapperRegistry。

- 优化部分过滤器的调用关系。
  - com.dwarfeng.fdr.impl.handler.mapper.HighPassCounterMapperRegistry。
  - com.dwarfeng.fdr.impl.handler.mapper.HighPassMapperRegistry。
  - com.dwarfeng.fdr.impl.handler.mapper.LowPassCounterMapperRegistry。
  - com.dwarfeng.fdr.impl.handler.mapper.LowPassMapperRegistry。

### Bug 修复

- Wiki 格式修正。
  - docs/wiki/zh_CN/PresetBridgeImplements.md。

- 修复部分映射器配置 Bean 不规范的问题。
  - com.dwarfeng.fdr.impl.handler.mapper.EnableRatioMapperRegistry。
  - com.dwarfeng.fdr.impl.handler.mapper.HighPassMapperRegistry。
  - com.dwarfeng.fdr.impl.handler.mapper.LowPassExistenceMapperRegistry。
  - com.dwarfeng.fdr.impl.handler.mapper.LowPassMapperRegistry。
  - com.dwarfeng.fdr.impl.handler.mapper.ToBooleanMapperRegistry。
  - com.dwarfeng.fdr.impl.handler.mapper.TrimMapperRegistry。

### 功能移除

- (无)

---

## Release_2.1.5_20240813_build_A

### 功能构建

- Wiki 编写。
  - docs/wiki/zh_CN/Bridge.md。
  - docs/wiki/zh_CN/PresetBridgeImplements.md。

- Wiki 更新。
  - docs/wiki/zh_CN/HowToUseTelqosViewCommand.md。
  - docs/wiki/zh_CN/Contents.md。

- 优化重置功能。
  - 为重置处理器的重置方法添加了同步锁。

- 优化文件格式。
  - 优化 `opt-*.xml` 文件的格式。

- 改进运维指令。
  - com.dwarfeng.fdr.impl.service.telqos.RecordCommand。
  - com.dwarfeng.fdr.impl.service.telqos.ViewCommand。

### Bug 修复

- 重置功能 bug 修复。
  - 修复重置器重置记录时，如果之前记录功能未启动，会错误地启动记录功能的 bug。

### 功能移除

- (无)

---

## Release_2.1.4_20240810_build_A

### 功能构建

- 优化部分维护服务实现中的部分方法的性能。
  - com.dwarfeng.fdr.impl.service.FilterInfoMaintainServiceImpl。
  - com.dwarfeng.fdr.impl.service.FilterSupportMaintainServiceImpl。
  - com.dwarfeng.fdr.impl.service.MapperSupportMaintainServiceImpl。
  - com.dwarfeng.fdr.impl.service.PointMaintainServiceImpl。
  - com.dwarfeng.fdr.impl.service.TriggerInfoMaintainServiceImpl。
  - com.dwarfeng.fdr.impl.service.TriggerSupportMaintainServiceImpl。
  - com.dwarfeng.fdr.impl.service.WasherInfoMaintainServiceImpl。
  - com.dwarfeng.fdr.impl.service.WasherSupportMaintainServiceImpl。
  - com.dwarfeng.fdr.impl.handler.bridge.hibernate.service.HibernateBridgeFilteredDataMaintainServiceImpl。
  - com.dwarfeng.fdr.impl.handler.bridge.hibernate.service.HibernateBridgeNormalDataMaintainServiceImpl。
  - com.dwarfeng.fdr.impl.handler.bridge.hibernate.service.HibernateBridgeTriggeredDataMaintainServiceImpl。

- 优化 `node` 模块部分服务启停脚本的注释。
  - binres/fdr-start.bat。
  - binres/fdr-start.sh。

- 部分 dubbo 消费者服务注册配置添加 `check="false"` 属性。
  - snowflakeGenerateService。

- 优化项目启停脚本设置程序的根目录的方式。

- 优化启停脚本的目录结构。

- 为部分工具类中方法的入口参数增加 `@NotNull` 注解。
  - com.dwarfeng.fdr.impl.service.telqos.CommandUtils。

- 启动器优化。
  - 将入口方法中完整独立的功能封装在子方法中，使入口方法代码结构更加清晰。

- 增加预设的运维指令。
  - com.dwarfeng.springtelqos.api.integration.log4j2.Log4j2Command。

- 日志功能优化。
  - 优化默认日志配置，默认配置仅向控制台输出 `INFO` 级别的日志。
  - 优化日志配置结构，提供 `conf/logging/settings.xml` 配置文件及其不同平台的参考配置文件，以供用户自定义日志配置。
  - 优化日志配置结构，提供 `confext/logging-settings.xml` 配置文件，以供外部功能自定义日志配置。
  - 优化启动脚本，使服务支持新的日志配置结构。
  - 优化 `assembly.xml`，使项目打包时输出新的日志配置结构。
  - 优化 `confext/README.md`，添加新的日志配置结构的相关说明。

- 增加 `PusherAdapter`。
  - 建议任何插件的推送器实现都继承自该适配器。
  - 适配器对所有的事件推送方法都进行了空实现。
  - 解决了增加了新的事件时，旧的推送器实现必须实现新的方法的问题。
  - 从此以后，推送器增加新的事件，将被视作兼容性更新。

- 升级 spring-telqos 并应用其新功能。
  - 使用包扫描的方式注册指令。
  - 优化 `telqos/connection.properties` 中配置的键名。

- 优化文件格式。
  - 优化 `application-context-*.xml` 文件的格式。

- 依赖升级。
  - 升级 `subgrade` 依赖版本为 `1.5.6.a` 并解决兼容性问题，以规避漏洞。
  - 升级 `spring` 依赖版本为 `5.3.37` 以规避漏洞。
  - 升级 `kafka` 依赖版本为 `3.6.1` 以规避漏洞。
  - 升级 `mysql` 依赖版本为 `8.2.0` 以规避漏洞。
  - 升级 `netty` 依赖版本为 `4.1.108.Final` 以规避漏洞。
  - 升级 `slf4j` 依赖版本为 `1.7.36` 以规避漏洞。
  - 升级 `snowflake` 依赖版本为 `1.5.2.a` 以规避漏洞。
  - 升级 `spring-terminator` 依赖版本为 `1.0.13.a` 以规避漏洞。
  - 升级 `spring-telqos` 依赖版本为 `1.1.10.a` 以规避漏洞。
  - 升级 `influxdb` 依赖版本为 `6.12.0` 以规避漏洞。

### Bug 修复

- 修复部分 CrudOperation 中部分存在性判断方法行为异常的 bug。
  - com.dwarfeng.fdr.impl.service.operation.FilterInfoCrudOperation。
  - com.dwarfeng.fdr.impl.service.operation.TriggerInfoCrudOperation。
  - com.dwarfeng.fdr.impl.service.operation.WasherInfoCrudOperation。

- 去除 `pom.xml` 中重复的依赖版本属性。
  - kafka.version。
  - spring-kafka.version。

- 修复部分功能性实体集合类型的字段在映射时有可能产生空指针异常的问题。
  - com.dwarfeng.fdr.sdk.bean.dto.FastJsonFilteredLookupResult。
  - com.dwarfeng.fdr.sdk.bean.dto.FastJsonNativeQueryInfo。
  - com.dwarfeng.fdr.sdk.bean.dto.FastJsonNormalLookupResult。
  - com.dwarfeng.fdr.sdk.bean.dto.FastJsonQueryInfo。
  - com.dwarfeng.fdr.sdk.bean.dto.FastJsonQueryResult。
  - com.dwarfeng.fdr.sdk.bean.dto.FastJsonTriggeredLookupResult。
  - com.dwarfeng.fdr.sdk.bean.dto.JSFixedFastJsonFilteredLookupResult。
  - com.dwarfeng.fdr.sdk.bean.dto.JSFixedFastJsonNormalLookupResult。
  - com.dwarfeng.fdr.sdk.bean.dto.JSFixedFastJsonQueryResult。
  - com.dwarfeng.fdr.sdk.bean.dto.JSFixedFastJsonTriggeredLookupResult。
  - com.dwarfeng.fdr.sdk.bean.dto.WebInputNativeQueryInfo。
  - com.dwarfeng.fdr.sdk.bean.dto.WebInputQueryInfo。

- 修复 telqos 工具类中部分注解不正确的 bug。

### 功能移除

- (无)

---

## Release_2.1.3_20231226_build_A

### 功能构建

- (无)

### Bug 修复

- 增加部分映射器缺失的 `@Component` 注解。
  - com.dwarfeng.fdr.impl.handler.mapper.HighPassCounterMapperRegistry。
  - com.dwarfeng.fdr.impl.handler.mapper.HighPassExistenceMapperRegistry。
  - com.dwarfeng.fdr.impl.handler.mapper.HighPassMapperRegistry。
  - com.dwarfeng.fdr.impl.handler.mapper.LowPassCounterMapperRegistry。
  - com.dwarfeng.fdr.impl.handler.mapper.LowPassExistenceMapperRegistry。
  - com.dwarfeng.fdr.impl.handler.mapper.LowPassMapperRegistry。

- 修复 com.dwarfeng.fdr.impl.handler.AbstractQueryHandler 中的 bug。
  - 修复 querySingle 方法对序列重复处理的 bug。

### 功能移除

- (无)

---

## Release_2.1.2_20231107_build_A

### 功能构建

- Wiki 编写。
  - docs/wiki/zh_CN/VersionBlacklist.md

### Bug 修复

- 修复关键 bug。
  - 修复记录启动方法被调用后，记录功能无法正常启动的 bug。

### 功能移除

- (无)

---

## Release_2.1.1_20231104_build_A

### 功能构建

- 优化 `push.properties` 配置文件有关 Kafka 部分的注释。

- changelogs 文件夹结构优化。

- 优化 `pom.xml` 文件的格式，修复其中的错误配置。

- 依赖升级。
  - 升级 `zookeeper` 依赖版本为 `3.7.2` 以规避漏洞。

### Bug 修复

- 修复 influxdb 桥接器中部分类与变量命名不规范的问题。

### 功能移除

- (无)

---

## Release_2.1.0_20231006_build_A

### 功能构建

- 类名、配置项、文档注释重命名。
  - dwarfengDct -> dct。

- 增加 RedisBridge 的数据处理策略。
  - 增加一般数据中更早的数据能否覆盖更晚的数据的配置项。
  - 增加被过滤数据中更早的数据能否覆盖更晚的数据的配置项。
  - 增加被触发数据中更早的数据能否覆盖更晚的数据的配置项。

- 依赖升级。
  - 升级 `spring-kafka` 依赖版本为 `2.9.11` 以规避漏洞。
  - 升级 `snowflake` 依赖版本为 `1.5.0.a` 并解决兼容性问题，以应用其新功能。
  - 升级 `subgrade` 依赖版本为 `1.4.5.a` 并解决兼容性问题，以应用其新功能。

- 优化数据记录代码结构。
  - 调整 RecordProcessor 结构，增加代理方法，将 RecordWorker 的实现代码移动到 RecordProcessor 中。

- 优化数据处理逻辑。
  - 修改 KeepConsumer 消费数据的逻辑。
  - 更新 Source.Context.record 方法的文档注释。
  - 更新 KeepHandler 中更新数据方法的文档注释。

### Bug 修复

- (无)

### 功能移除

- 去除不需要的接口方法。
  - com.dwarfeng.fdr.stack.handler.KeepHandler.writeOnly。
  - com.dwarfeng.fdr.stack.handler.PersistHandler.writeOnly。
  - com.dwarfeng.fdr.impl.handler.Bridge.supportKeeper。
  - com.dwarfeng.fdr.impl.handler.Bridge.supportPersister。

---

## Release_2.0.3_20231006_build_A

### 功能构建

- 优化配置文件。
  - 优化 `impl` 模块测试配置 `application-context-database.xml`，使得项目在运行测试时打印生成的 SQL 语句。

- Wiki 更新。
  - 优化 `Contents.md` 中的内容。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_2.0.2_20230619_build_A

### 功能构建

- Wiki 编写。
  - docs/wiki/zh_CN/Source.md。
  - docs/wiki/zh_CN/Washer.md。

- Wiki 更新。
  - 在 wiki 的根目录以及所有语言目录下添加 `README.md`。

- 实现预设映射器。
  - com.dwarfeng.fdr.impl.handler.mapper.TrimMapperRegistry。
  - com.dwarfeng.fdr.impl.handler.mapper.ToBooleanMapperRegistry。
  - com.dwarfeng.fdr.impl.handler.mapper.EnableRatioMapperRegistry。
  - com.dwarfeng.fdr.impl.handler.mapper.HighPassMapperRegistry。
  - com.dwarfeng.fdr.impl.handler.mapper.HighPassCounterMapperRegistry。
  - com.dwarfeng.fdr.impl.handler.mapper.HighPassExistenceMapperRegistry。
  - com.dwarfeng.fdr.impl.handler.mapper.LowPassMapperRegistry。
  - com.dwarfeng.fdr.impl.handler.mapper.LowPassCounterMapperRegistry。
  - com.dwarfeng.fdr.impl.handler.mapper.LowPassExistenceMapperRegistry。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_2.0.1_20230616_build_A

### 功能构建

- 依赖升级。
  - 升级 `guava` 依赖版本为 `32.0.1-jre` 以规避漏洞。

- 部分 FastJson 实体补充 toStackBean 方法。
  - com.dwarfeng.fdr.sdk.bean.dto.FastJsonNormalData。
  - com.dwarfeng.fdr.sdk.bean.dto.FastJsonFilteredData。
  - com.dwarfeng.fdr.sdk.bean.dto.FastJsonTriggeredData。

- 优化了 AbstractResetter 的代码结构，同时对其实现进行了修改。
  - com.dwarfeng.fdr.impl.handler.resetter.CronResetter。
  - com.dwarfeng.fdr.impl.handler.resetter.DubboResetter。
  - com.dwarfeng.fdr.impl.handler.resetter.FixedDelayResetter。
  - com.dwarfeng.fdr.impl.handler.resetter.FixedRateResetter。
  - com.dwarfeng.fdr.impl.handler.resetter.NeverResetter。

- 优化部分指令的文案。
  - com.dwarfeng.fdr.impl.service.telqos.LogicConsumerCommand。
  - com.dwarfeng.fdr.impl.service.telqos.RecordConsumerCommand。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Release_2.0.0_20230614_build_A

### 功能构建

- 依赖升级。
  - 升级 `spring` 依赖版本为 `5.3.27` 以规避漏洞。
  - 升级 `dubbo` 依赖版本为 `2.7.22` 以规避漏洞。
  - 升级 `snowflake` 依赖版本为 `1.4.11.a` 以规避漏洞。
  - 升级 `subgrade` 依赖版本为 `1.3.3.a` 以规避漏洞。
  - 升级 `spring-terminator` 依赖版本为 `1.0.11.a` 以规避漏洞。
  - 升级 `spring-telqos` 依赖版本为 `1.1.6.a` 以规避漏洞。

- 重写 `README.md`，并增加文档文件夹。

- 实现预设数据源。
  - com.dwarfeng.fdr.impl.handler.source.mock.realtime.RealtimeMockSource。
  - com.dwarfeng.fdr.impl.handler.source.mock.historical.HistoricalMockSource。
  - com.dwarfeng.fdr.impl.handler.source.kafka.dwarfengdct.DwarfengDctKafkaSource。
  - com.dwarfeng.fdr.impl.handler.source.kafka.dcti.DctiKafkaSource。

- 实现预设过滤器。
  - com.dwarfeng.fdr.impl.handler.filter.DataValueIllegalFilterRegistry。

- 新增数据清洗机制，并提供预设清洗器。
  - com.dwarfeng.fdr.impl.handler.washer.GroovyWasherRegistry。
  - com.dwarfeng.fdr.impl.handler.washer.IdentifyWasherRegistry。
  - com.dwarfeng.fdr.impl.handler.washer.ToBooleanWasherRegistry。
  - com.dwarfeng.fdr.impl.handler.washer.ToDoubleWasherRegistry。
  - com.dwarfeng.fdr.impl.handler.washer.ToLongWasherRegistry。

- 优化项目的服务异常映射机制。

- 重构数据记录处理与数据持久化模块。

- 实现预设桥接器。
  - com.dwarfeng.fdr.impl.handler.bridge.mock.MockBridge。
  - com.dwarfeng.fdr.impl.handler.bridge.hibernate.HibernateBridge。
  - com.dwarfeng.fdr.impl.handler.bridge.redis.RedisBridge。
  - com.dwarfeng.fdr.impl.handler.bridge.influxdb.InfluxdbBridge。
  - com.dwarfeng.fdr.impl.handler.bridge.drain.DrainBridge。
  - com.dwarfeng.fdr.impl.handler.bridge.multi.MultiBridge。

### Bug 修复

- 修复 `DubboResetter` 注册微服务时没有指定 `group` 的问题。

### 功能移除

- (无)

---

## 更早的版本

[View all changelogs](./changelogs)
