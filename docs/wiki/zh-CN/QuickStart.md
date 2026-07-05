# Quick Start - 快速开始

## 确认系统需求

- CPU：2 核以上。
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

软件包的名称格式为 `fdr-all-he-${version}-release.tar.gz`，其中 `${version}` 为软件包的版本号。

使用工具软件，将软件包上传至服务器 `/usr/local` 目录下，解压软件包。

```shell
cd /usr/local
tar -zxvf fdr-all-he-${version}-release.tar.gz
mv fdr-all-he-${version}-release/fdr-all-he-${version} fdr
```

## 数据库初始化

连接到 MySQL 数据库，执行如下 SQL 语句：

```sql
create database if not exists fdr;

use fdr;

create table if not exists tbl_point
(
    id                        bigint       not null
        primary key,
    filtered_keep_enabled     bit          null,
    filtered_persist_enabled  bit          null,
    name                      varchar(50)  not null,
    normal_keep_enabled       bit          null,
    normal_persist_enabled    bit          null,
    remark                    varchar(100) null,
    triggered_keep_enabled    bit          null,
    triggered_persist_enabled bit          null,
    reserved_string_alpha     text         null,
    reserved_string_bravo     text         null,
    reserved_string_charlie   text         null,
    reserved_string_delta     text         null,
    reserved_long_alpha       bigint       null,
    reserved_long_bravo       bigint       null,
    reserved_integer_alpha    int          null,
    reserved_integer_bravo    int          null,
    reserved_boolean_alpha    bit          null,
    reserved_boolean_bravo    bit          null,
    reserved_date_alpha       datetime(6)  null,
    reserved_date_bravo       datetime(6)  null,
    created_datamark          varchar(100) null,
    modified_datamark         varchar(100) null,
    record_memory_size        int          not null
);

create table if not exists tbl_fetcher_info
(
    id                bigint       not null
        primary key,
    created_datamark  varchar(100) null,
    enabled           bit          not null,
    modified_datamark varchar(100) null,
    param             text         null,
    remark            varchar(100) null,
    type              varchar(50)  null
);

INSERT INTO fdr.tbl_point (id, filtered_keep_enabled, filtered_persist_enabled, name, normal_keep_enabled,
                           normal_persist_enabled, remark, triggered_keep_enabled, triggered_persist_enabled,
                           created_datamark, modified_datamark, record_memory_size)
VALUES (1, false, false, '测试点位.1', true, true, '测试点位.1', false, false,
        'fdr-node', 'fdr-node', 1000);

INSERT INTO fdr.tbl_fetcher_info (id, created_datamark, enabled, modified_datamark, param, remark, type)
VALUES (1, 'fdr-node', true, 'fdr-node',
        '{"point_key":{"long_id":1},"poll_type":"fixed_rate","poll_setting":"1000","generator_type":"double","random_seed":null,"fetch_before_delay":0,"fetch_after_delay":0}',
        'QuickStart MockLF 抓取器', 'mock.lf');
```

## 最小化配置

下文列出了启动程序需要改动的最少的配置文件，每个配置文件中仅展示需要改动的配置项。

`conf/curator/connection.properties` 文件中配置 curator 连接信息。

```properties
com.dwarfeng.fdr.curator.connect.connect_string=your-host-here:2181
```

`conf/database/connection.properties` 文件中配置数据库连接信息。

```properties
com.dwarfeng.fdr.jdbc.url=jdbc:mysql://your-host-here:3306/fdr?serverTimezone=Asia/Shanghai&autoReconnect=true
com.dwarfeng.fdr.jdbc.username=root
com.dwarfeng.fdr.jdbc.password=your-password-here
```

`conf/dubbo/connection.properties` 文件中配置 dubbo 连接信息。

```properties
com.dwarfeng.fdr.dubbo.registry.zookeeper.address=zookeeper://your-host-here:2181
```

`conf/fdr/bridge.properties` 文件中配置桥接器信息。发布包中的默认桥接器配置为 `mock`，
如需按照本文验证 MySQL 历史数据与 Redis 实时数据，需要将相关配置项修改为如下内容。

```properties
com.dwarfeng.fdr.keep.normal_data.type=redis
com.dwarfeng.fdr.persist.normal_data.type=hibernate
com.dwarfeng.fdr.keep.filtered_data.type=drain
com.dwarfeng.fdr.persist.filtered_data.type=drain
com.dwarfeng.fdr.keep.triggered_data.type=drain
com.dwarfeng.fdr.persist.triggered_data.type=drain
```

`conf/redis/connection.properties` 文件中配置 redis 连接信息。

```properties
com.dwarfeng.fdr.redis.hostName=your-host-here
com.dwarfeng.fdr.redis.port=6379
com.dwarfeng.fdr.redis.password=your-password-here
```

## 修改可选配置

下文列出了启动程序需要改动的可选的配置文件，每个配置文件中仅展示需要改动的配置项。

`opt/opt-bridge.xml` 桥接器可选配置。
发布包中的桥接器扩展默认以注释形式提供，如需按照本文配置启用对应桥接器，需要取消下列组件扫描配置的注释。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- 以下注释用于抑制 idea 中 .md 的警告，实际并无错误，在使用时可以连同本注释一起删除。 -->
<!--suppress SpringXmlModelInspection -->
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

`opt/opt-fetcher.xml` 抓取器可选配置。
发布包中的抓取器扩展默认以注释形式提供，如需按照本文使用 MockLF 抓取器生成模拟数据，需要取消下列组件扫描配置的注释。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- 以下注释用于抑制 idea 中 .md 的警告，实际并无错误，在使用时可以连同本注释一起删除。 -->
<!--suppress SpringXmlModelInspection -->
<beans
        xmlns:context="http://www.springframework.org/schema/context"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="http://www.springframework.org/schema/beans"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd"
>

    <!-- 加载 MockLfFetcher。 -->
    <context:component-scan base-package="com.dwarfeng.fdr.impl.handler.fetcher.mock.lf"/>
</beans>
```

## 启动程序

在 `/usr/local/fdr` 目录下执行如下命令：

```shell
sh  bin/fdr-start.sh
```

1. 观察数据库，程序会在 `tbl_hibernate_bridge_normal_data` 表中写入 MockLF 抓取器生成的历史数据。
2. 观察 Redis，程序会在 `dbkey.normal_data` Hash 表中写入 MockLF 抓取器生成的实时数据。

## 停止程序

在 `/usr/local/fdr` 目录下执行如下命令：

```shell
sh  bin/fdr-stop.sh
```
