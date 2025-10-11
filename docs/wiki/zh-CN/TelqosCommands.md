# Telqos Commands - Telqos 命令

## 命令列表

FDR 2.x 版本提供的 Telqos 命令如下所示：

| 命令                       | 说明         | 可用版本    |
|--------------------------|------------|---------|
| [lc](#lc-命令)             | 列出指令       | 2.0.0.a |
| [man](#man-命令)           | 显示指令的详细信息  | 2.0.0.a |
| [memory](#memory-命令)     | 内存监视       | 2.0.0.a |
| [shutdown](#shutdown-命令) | 关闭/重启程序    | 2.0.0.a |
| [quit](#quit-命令)         | 退出         | 2.0.0.a |
| [dubbo](#dubbo-命令)       | 分布式服务上线/下线 | 2.0.0.a |
| [nv](#nv-命令)             | 一般查看指令     | 2.0.0.a |
| [fv](#fv-命令)             | 被过滤查看指令    | 2.0.0.a |
| [tv](#tv-命令)             | 被触发查看指令    | 2.0.0.a |
| [lcsu](#lcsu-命令)         | 逻辑侧消费者操作   | 2.0.0.a |
| [rcsu](#rcsu-命令)         | 记录侧消费者操作   | 2.0.0.a |
| [rlc](#rlc-命令)           | 数据记录本地缓存操作 | 2.0.0.a |
| [mlc](#mlc-命令)           | 映射查询本地缓存操作 | 2.0.0.a |
| [record](#record-命令)     | 记录功能上线/下线  | 2.0.0.a |
| [reset](#reset-命令)       | 重置处理器操作/查看 | 2.0.0.a |
| [source](#source-命令)     | 重置处理器操作/查看 | 2.3.0.a |
| [support](#support-命令)   | 支持操作       | 2.3.0.a |

鉴于所有指令都可以实际操作验证，因此本为对于较长的输出将予以省略，省略的部分将会使用 `etc...` 进行标注。

## lc 命令

列出 Telqos 支持的所有命令。

### 语法

```text
usage: lc [-p prefix|--prefix prefix]
列出指令
 -p,--prefix <prefix>   列出包含指定前缀的命令
```

### 示例

```text
lc -p r
1   rcsu     记录侧消费者操作
2   record   记录功能上线/下线
3   reset    重置处理器操作/查看
4   rlc      数据记录本地缓存操作
-----------------------
共 4 条
OK
```

## man 命令

显示指定命令的详细信息。

### 语法

```text
usage: man [command]
显示指令的详细信息
```

### 示例

```text
man man
usage: man [command]
显示指令的详细信息
```

## memory 命令

内存监视

### 语法

```text
usage: memory [-u unit]
内存监视
 -u <arg>   显示单位
```

### 示例

```text
memory -u mib
JVM 最大内存: 7212.50MiB
JVM 分配内存: 1223.00MiB
JVM 可用内存: 1120.00MiB
OK
```

## shutdown 命令

关闭/重启程序。

在本项目中，重启程序功能不可用，只能使用关闭程序功能。

### 语法

```text
usage: shutdown [-s/-r] [-e exit-code] [-c comment]
关闭/重启程序
 -c <comment>     备注
 -e <exit-code>   退出代码
 -r               重启程序
 -s               退出程序
```

增加 `-c` 参数，可以在关闭程序时添加备注信息，备注信息将会被记录到日志中。

`-r` 参数不可用。

### 示例

```text
shutdown
服务将会关闭，您可能需要登录远程主机才能重新启动该服务，是否继续? Y/N
Y
已确认请求，服务即将关闭...
服务端主动与您中断连接
再见!
```

## quit 命令

退出 Telqos 运维平台。

### 语法

```text
usage: quit
退出
```

### 示例

```text
quit
Bye
服务端主动与您中断连接
再见!


遗失对主机的连接。
```

## dubbo 命令

分布式服务查询/上线/下线。

### 语法

```text
usage: dubbo -online [service-name]
dubbo -offline [service-name]
dubbo -ls
分布式服务上线/下线
 -ls              列出服务
 -offline <arg>   下线服务
 -online <arg>    上线服务
```

`[service-name]` 参数为正则表达式，只有服务的全名称匹配正则表达式时，才会被上线/下线。

如果想要上线/下线名称中包含 包含 `FooBar` 的服务，则可以使用 `.*FooBar.*` 作为正则表达式。

如果不指定 `[service-name]` 参数，则使所有服务上线/下线。

### 示例

#### 列出服务

```text
dubbo -ls
As Provider side:
+---------------------------------------------------------------------------+---+
|                           Provider Service Name                           |PUB|
+---------------------------------------------------------------------------+---+
|       fdr2/com.dwarfeng.fdr.stack.service.FilterInfoMaintainService       | Y |
+---------------------------------------------------------------------------+---+
|             fdr2/com.dwarfeng.fdr.stack.service.RecordService             | Y |
+---------------------------------------------------------------------------+---+
etc...
As Consumer side:
+---------------------------------------------+---+
|            Consumer Service Name            |NUM|
+---------------------------------------------+---+
|com.dwarfeng.sfds.stack.service.LongIdService| 3 |
+---------------------------------------------+---+
OK
```

#### 上线服务

```text
dubbo -online .*ViewService.*
OK
```

随后可以列出服务观察上线效果。

#### 下线服务

```text
dubbo -offline .*ViewService.*
OK
```

随后可以列出服务观察下线效果。

## nv 命令

一般观察指令，用于：

- 查看一般数据的最新值。
- 查看一般数据的历史值。
- 本地查询一般数据的历史值。
- 查询（之后映射处理）一般数据的历史值。

该命令交互性较强，与 `tv`，`fv` 命令同属于观察指令，请参照下文给出的文档，了解该命令的详细使用方法。

- [HowToUseTelqosViewCommand.md](./HowToUseTelqosViewCommand.md) - 如何使用 Telqos 的观察命令。

### 语法

```text
usage: nv -latest [-json json-string] [-jf json-file]
nv -lookup [-json json-string] [-jf json-file]
nv -nquery [-json json-string] [-jf json-file]
nv -query [-json json-string] [-jf json-file]
一般查看指令
 -jf,--json-file <arg>    JSON文件
 -json <arg>              JSON字符串
 -latest                  最新数据指令
 -lookup                  查看指令
 -nquery,--native-query   原生查询指令
 -query                   查询指令
```

## fv 命令

被过滤观察指令，用于：

- 查看被过滤数据的最新值。
- 查看被过滤数据的历史值。
- 本地查询被过滤数据的历史值。
- 查询（之后映射处理）被过滤数据的历史值。

该命令交互性较强，与 `nv`，`tv` 命令同属于观察指令，请参照下文给出的文档，了解该命令的详细使用方法。

- [HowToUseTelqosViewCommand.md](./HowToUseTelqosViewCommand.md) - 如何使用 Telqos 的观察命令。

### 语法

```text
usage: fv -latest [-json json-string] [-jf json-file]
fv -lookup [-json json-string] [-jf json-file]
fv -nquery [-json json-string] [-jf json-file]
fv -query [-json json-string] [-jf json-file]
被过滤查看指令
 -jf,--json-file <arg>    JSON文件
 -json <arg>              JSON字符串
 -latest                  最新数据指令
 -lookup                  查看指令
 -nquery,--native-query   原生查询指令
 -query                   查询指令
```

## tv 命令

被触发观察指令，用于：

- 查看被触发数据的最新值。
- 查看被触发数据的历史值。
- 本地查询被触发数据的历史值。
- 查询（之后映射处理）被触发数据的历史值。

该命令交互性较强，与 `nv`，`fv` 命令同属于观察指令，请参照下文给出的文档，了解该命令的详细使用方法。

- [HowToUseTelqosViewCommand.md](./HowToUseTelqosViewCommand.md) - 如何使用 Telqos 的观察命令。

### 语法

```text
usage: tv -latest [-json json-string] [-jf json-file]
tv -lookup [-json json-string] [-jf json-file]
tv -nquery [-json json-string] [-jf json-file]
tv -query [-json json-string] [-jf json-file]
被触发查看指令
 -jf,--json-file <arg>    JSON文件
 -json <arg>              JSON字符串
 -latest                  最新数据指令
 -lookup                  查看指令
 -nquery,--native-query   原生查询指令
 -query                   查询指令
```

## lcsu 命令

逻辑侧消费者查看与参数调整指令。

### 语法

```text
usage: lcsu -l [-h]
lcsu -s [-b val] [-t val]
逻辑侧消费者操作
 -b <buffer-size>   缓冲器的大小
 -h                 持续输出
 -l                 查看消费者状态
 -s                 设置消费者参数
 -t <thread>        消费者的线程数量
```

输入 `lcsu -l` 指令时，会输出一次当前消费者状态。如果需要持续监视消费者状态，请输入 `lcsu -l -h` 指令。

`-h` 参数可以使消费者状态每秒输出一次，直到用户输入任意键退出。

### 示例

#### 监视消费者状态

```text
lcsu -l -h
输入任意字符停止持续输出
buffered-size:12      buffer-size:1000    thread:1   idle:false
buffered-size:46      buffer-size:1000    thread:1   idle:false
buffered-size:125     buffer-size:1000    thread:1   idle:false
buffered-size:0       buffer-size:1000    thread:1   idle:false
q
OK
```

#### 设置消费者参数

```text
lcsu -s -b 2000 -t 2
设置完成，记录者新的参数为:
buffered-size:0       buffer-size:2000    thread:2   idle:false
OK
```

## rcsu 命令

记录侧消费者查看与参数调整指令。

### 语法

```text
usage: rcsu -l [-c classes] [-n names] [-h]
rcsu -s [-c classes] [-n names] [-b val] [-a val] [-m val] [-t val]
rcsu -lc
rcsu -ln
记录侧消费者操作
 -a <batch-size>      数据的批处理量
 -b <buffer-size>     缓冲器的大小
 -c <classes>         消费者类型
 -h                   持续输出
 -l                   查看消费者状态
 -lc,--list-classes   列出所有消费者类型
 -ln,--list-names     列出所有消费者名称
 -m <max-idle-time>   最大空闲时间
 -n <names>           消费者名称
 -s                   设置消费者参数
 -t <thread>          消费者的线程数量
```

输入 `rcsu -l` 指令时，会输出一次当前消费者状态。如果需要持续监视消费者状态，请输入 `rcsu -l -h` 指令。

`-h` 参数可以使消费者状态每秒输出一次，直到用户输入任意键退出。

### 示例

#### 监视消费者状态

```text
rcsu -c persist -n normal -l -h
输入任意字符停止持续输出
1    persist - normal       buffered-size:12      buffer-size:1000    etc...
1    persist - normal       buffered-size:20      buffer-size:1000    etc...
1    persist - normal       buffered-size:325     buffer-size:1000    etc...
1    persist - normal       buffered-size:0       buffer-size:1000    etc...
q
OK
```

#### 设置消费者参数

```text
rcsu -c persist -n normal -s -b 2000 -a 200 -m 500 -t 8
设置完成，消费者新的参数为:
1    persist - normal       buffered-size:0       buffer-size:2000    etc...
OK
```

## rlc 命令

数据记录本地缓存操作。

### 语法

```text
usage: rlc -l point-id
rlc -c
数据记录本地缓存操作
 -c              清除缓存
 -l <point-id>   查看指定数据点的详细信息，如果本地缓存中不存在，则尝试抓取
```

### 示例

#### 查看记录上下文

```text
rlc -l 1
point: Point{key=LongIdKey{longId=1}, name='测试点位.1', remark='测试点位.1', normalKeepEnabled=false, etc...
filters:
triggers:
OK
```

#### 清除缓存

```text
rlc -c
缓存已清空
OK
```

## mlc 命令

映射查询本地缓存操作。

### 语法

```text
usage: mlc -l mapper-type
mlc -c
映射查询本地缓存操作
 -c                 清除缓存
 -l <mapper-type>   查看指定映射类型的映射器，如果本地缓存中不存在，则尝试抓取
```

### 示例

#### 查看映射器

```text
mlc -l groovy_mapper
mapper: com.dwarfeng.fdr.impl.handler.mapper.GroovyMapperRegistry$GroovyMapper@6d46b319
OK
```

#### 清除缓存

```text
mlc -c
缓存已清空
OK
```

## record 命令

记录功能上线/下线。

### 语法

```text
usage: record -online
record -offline
记录功能上线/下线
 -offline   下线服务
 -online    上线服务
```

### 示例

#### 记录功能上线

```text
record -online
记录功能已上线!
OK
```

#### 记录功能下线

```text
record -offline
记录功能已下线!
OK
```

## reset 命令

重置处理器查看与操作。

用于查看当前生效的重置器，以及基于指令手动重置服务功能。

### 语法

```text
usage: reset -l
reset -start
reset -stop
reset -status
reset --reset-record
reset --reset-map
重置处理器操作/查看
    --l              查看重置处理器
    --reset-map      执行重置映射功能操作
    --reset-record   执行重置记录功能操作
    --start          启动重置处理器
    --status         查看重置处理器状态
    --stop           停止重置处理器
```

`reset --reset-record` 指令重置记录功能，会执行以下操作：

1. 停止记录处理器。
2. 清空记录本地缓存处理器。
3. 启动记录处理器。

此方法会消耗一定的时间，尤其是在逻辑侧消费者或记录侧消费者积压数据较多的情况下。

`reset --reset-map` 指令重置映射功能，会执行以下操作：

1. 清空映射本地缓存处理器。

### 示例

#### 查看当前生效的重置器

```text
reset -l
01. FixedDelayResetter{delay=43200000}
OK
```

#### 查看重置处理器的启停状态

```text
reset -status
started: true
OK
```

#### 启动重置处理器

```text
reset -start
重置处理器已启动!
OK
```

#### 停止重置处理器

```text
reset -stop
重置处理器已停止!
OK
```

#### 重置记录功能

```text
reset --reset-record
重置成功!
OK
```

#### 重置映射功能

```text
reset --reset-map
重置成功!
OK
```

## source 命令

查看数据源。

查看当前生效的数据源。

### 语法

```text
usage: source -l
数据源查看
    --l   查看数据源
```

### 示例

```text
source -l
01. DctiKafkaSource{registry=org.springframework.kafka.config.KafkaListenerEndpointRegistry@etc...
OK
```

## support 命令

支持操作。

重置支持实体。

### 语法

```text
man support
usage: support --reset-filter
support --reset-washer
support --reset-trigger
support --reset-mapper
支持操作
    --reset-filter    重置过滤器支持
    --reset-mapper    重置映射器支持
    --reset-trigger   重置触发器支持
    --reset-washer    重置清洗器支持
```

### 示例

#### 重置过滤器支持

```text
support --reset-filter
重置过滤器支持成功。
OK
```

#### 重置映射器支持

```text
support --reset-mapper
重置映射器成功。
OK
```

#### 重置触发器支持

```text
support --reset-trigger
重置触发器支持成功。
OK
```

#### 重置清洗器支持

```text
support --reset-washer
重置清洗器支持成功。
OK
```
