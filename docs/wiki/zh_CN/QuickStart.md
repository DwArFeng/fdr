# Quick Start - 快速开始

## 确认系统需求

- CPU：2核以上。
- 内存：4G 以上。
- 硬盘：100G 以上。
- CentOS 7。
- JRE 1.8。
- MySQL 8.0.19。
- Redis 5.0.7。
- Zookeeper 3.5.5。
- snowflake-distributed-service 1.4.11。

## 获取软件包

从 Github 上获取软件包，软件包可以从 Github 的 Release 页面下载。

## 解压软件包

解压软件包到 `/usr/local/fdr` 目录下。

## 数据库初始化

连接到 MySQL 数据库，执行如下 SQL 语句：

```sql
CREATE DATABASE fdr;
```

## 最小化配置

下文列出了启动程序需要改动的最少的配置文件，每个配置文件中仅展示需要改动的配置项。

`conf/database/connection.properties` 文件中配置数据库连接信息。

```properties
jdbc.url=jdbc:mysql://your-host-here:3306/fdr?serverTimezone=Asia/Shanghai&autoReconnect=true
jdbc.username=root
jdbc.password=your-password-here
```

`conf/dubbo/connection.properties` 文件中配置 Dubbo 连接信息。

```properties
dubbo.registry.zookeeper.address=zookeeper://your-host-here:2181
```

`conf/fdr/bridge.properties` 文件中配置桥接器信息。

```properties
keep.normal_data.type=redis
persist.normal_data.type=hibernate
keep.filtered_data.type=drain
persist.filtered_data.type=drain
keep.triggered_data.type=drain
persist.triggered_data.type=drain
```

`conf/redis/connection.properties` 文件中配置 Redis 连接信息。

```properties
redis.host=your-host-here
redis.port=6379
redis.password=your-password-here
```

## 修改可选配置

下文列出了启动程序需要改动的可选的配置文件，每个配置文件中仅展示需要改动的配置项。

`opt/opt-bridge.xml` 桥接器可选配置。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!--suppress SpringFacetInspection, XmlUnusedNamespaceDeclaration -->
<beans
        xmlns:context="http://www.springframework.org/schema/context"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="http://www.springframework.org/schema/beans"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd"
>

    <!-- 加载 DrainBridge -->
    <context:component-scan base-package="com.dwarfeng.fdr.impl.handler.bridge.drain"/>

    <!-- 加载 HibernateBridge -->
    <context:component-scan base-package="com.dwarfeng.fdr.impl.handler.bridge.hibernate"/>

    <!-- 加载 RedisBridge -->
    <context:component-scan base-package="com.dwarfeng.fdr.impl.handler.bridge.redis"/>
</beans>
```

`opt/opt-source.xml` 数据源可选配置。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!--suppress SpringFacetInspection, XmlUnusedNamespaceDeclaration -->
<beans
        xmlns:context="http://www.springframework.org/schema/context"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="http://www.springframework.org/schema/beans"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd"
>

    <!-- 加载 RealtimeMockSource -->
    <context:component-scan base-package="com.dwarfeng.fdr.impl.handler.source.mock.realtime"/>
</beans>
```

## 启动程序

在 `/usr/local/fdr` 目录下执行如下命令：

```shell
sh  bin/fdr-start.sh
```

1. 观察数据库，数据库将会自动生成 `tbl_hibernate_bridge_normal_data` 表，表中有历史数据。
2. 观察 Redis，Redis 将会自动生成 `dbkey.normal_data` Hash表，表中有实时数据。

## 停止程序

在 `/usr/local/fdr` 目录下执行如下命令：

```shell
sh  bin/fdr-stop.sh
```
