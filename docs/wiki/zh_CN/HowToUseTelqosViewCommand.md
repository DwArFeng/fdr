# How to User Telqos View Command - 如何使用 Telqos 观察命令

## 说明

本文档详细说明了如何使用 Telqos 的观察命令，在 Telqos 运维平台中观察数据。

观察数据是 FDR 的核心功能之一，观察服务包括：

- 查看一般数据的最新值。
- 查看一般数据的历史值。
- 本地查询一般数据的历史值。
- 查询（之后映射处理）一般数据的历史值。

*对于部分桥接器，有可能不支持本地查询。*

数据的观察分为三个通道，分别是：

- 一般数据通道，对应 `NormalData`。
- 被过滤数据通道，对应 `FilteredData`。
- 被触发数据通道，对应 `TriggeredData`。

每个通道都有一个观察命令，分别是：

| 通道      | 命令   | 实体              |
|---------|------|-----------------|
| 一般数据通道  | `nv` | `NormalData`    |
| 被过滤数据通道 | `fv` | `FilteredData`  |
| 被触发数据通道 | `tv` | `TriggeredData` |

上述每个指令的用法完全一致，本文档以 `nv` 为例进行说明。

### 语法

```text
usage: nv -latest [-json json-string] [-jf json-file]
nv -lookup [-json json-string] [-jf json-file]
nv -nquery [-json json-string] [-jf json-file]
nv -query [-json json-string] [-jf json-file]
一般查看指令
 -jf,--json-file <arg>    json文件
 -json <arg>              json字符串
 -latest                  最新数据指令
 -lookup                  查看指令
 -nquery,--native-query   原生查询指令
 -query                   查询指令
```

在计划的功能设计中，`nv -latest`，`nv -lookup`，`nv -nquery`，`nv -query` 四条指令是可以不需要参数的，当不带参数时，
该命令行会交互式的询问用户输入参数。

但是在当前版本中，该功能由于开发周期等原因，暂时没有实现，所以在当前版本中，这四条指令都需要带参数。

### 参数

#### -json json-string

该参数用于指定 json 字符串，该字符串是一个 json 对象。

在指令解析时，不同的查询指令会将该 json 对象解析为不同的实体，具体如下：

| 指令           | 功能   | 实体类                       |
|--------------|------|---------------------------|
| `nv -latest` | 最新数据 | `List<FastJsonLongIdKey>` |
| `nv -lookup` | 查看   | `FastJsonLookupInfo`      |
| `nv -nquery` | 原生查询 | `FastJsonNativeQueryInfo` |
| `nv -query`  | 查询   | `FastJsonQueryInfo`       |

因此，您需要正确的指定 json 字符串，否则会导致指令解析失败。

请注意，使用该参数时，提供的 json 字符串不应过长；json 字符串中间不得包含空格。
对于过长的 json 字符串，建议将字符串保存到文件中，然后使用 `-jf json-file` 参数。

`-json json-string` 参数可以与 `-jf json-file` 参数共存，两者存在时，以 `-json json-string` 提供的 json 字符串为准。

#### -jf json-file

该参数用于指定 json 文件，该文件是一个文本文件，文件内容是一个 json 对象。

在指令解析时，该指令会尝试读取文件内容，然后将其解析为一个 json 对象。
不同的查询指令会将该 json 对象解析为不同的实体，具体如下：

| 指令           | 功能   | 实体类                       |
|--------------|------|---------------------------|
| `nv -latest` | 最新数据 | `List<FastJsonLongIdKey>` |
| `nv -lookup` | 查看   | `FastJsonLookupInfo`      |
| `nv -nquery` | 原生查询 | `FastJsonNativeQueryInfo` |
| `nv -query`  | 查询   | `FastJsonQueryInfo`       |

因此，您需要正确的指定 json 字符串，否则会导致指令解析失败。

文件的路径可以是相对路径，也可以是绝对路径。

相对路径以当前工作目录为基准，即 FDR 项目的根目录。

`-json json-string` 参数可以与 `-jf json-file` 参数共存，两者存在时，以 `-json json-string` 提供的 json 字符串为准。

### 结果查看

`nv -latest`，`nv -lookup`，`nv -nquery`，`nv -query` 四条指令的结果查看方式均为交互式。

其中 `nv -latest`，`nv -lookup` 两条指令会返回结果列表，当程序查询结束后，会将执行时间、数据量、交互参数输出至控制台：

```text
nv -lookup -jf foo/foo1.json

执行时间：1255ms

数据总数: 100

输入 all 查看所有数据
输入 begin-end 查看指定范围的数据
输入 q 退出查询

```

当数据量不大时，可以直接输入 `all` 查看所有数据，当数据量很大时，请慎重使用 `all` 命令，
因为这会导致控制台输出大量数据，消耗大量的时间。

*如果不慎输入的 `all` 命令，控制台持续输出大量数据，您可以按下 `ctrl + ]` 进入 telnet 模式，然后输入 `quit` 退出。*

输入 `begin-end`，可以查看指定范围的数据，数据索引从 `0` 开始，输出的数据包括起始索引，不包括结束索引。

如想查看从索引 `0` 开始的 4 条数据，可以输入 `0-4`，这样，程序会输出索引 `0`，`1`，`2`，`3` 的数据：

```text
0-4

索引: 0/4
  pointId: 1
  valueClass: java.lang.Long
  value: -1155484576
  happenedDate: 2023-06-01 10:00:00.000

索引: 1/4
  pointId: 1
  valueClass: java.lang.Long
  value: -723955400
  happenedDate: 2023-06-01 10:00:00.001

索引: 2/4
  pointId: 1
  valueClass: java.lang.Long
  value: 1033096058
  happenedDate: 2023-06-01 10:00:00.002

索引: 3/4
  pointId: 1
  valueClass: java.lang.Long
  value: -1690734402
  happenedDate: 2023-06-01 10:00:00.003
```

输入 `all` 和 `begin-end` 后，程序会输出查询结果，输出完毕后重复输出数据量和交互参数。

```text
数据总数: 100

输入 all 查看所有数据
输入 begin-end 查看指定范围的数据
输入 q 退出查询
```

此时，可继续输入 `all` 或 `begin-end` 查看数据，也可以输入 `q`，可以退出查询。

`nv -nquery`，`nv -query` 两条指令会返回序列列表。当程序查询结束后，会将执行时间、序列数量、交互参数输出至控制台：

```text
执行时间：773ms

序列总数: 1

输入序列索引
输入 q 退出查询
```

此时应该输入序列索引，查看具体的序列内容。序列索引从 `0` 开始，输入序列索引后，程序会输出序列的内容：

```text
序列信息: 
pointId: 1    startDate: 2023-06-01 10:00:00.000    endDate: 2023-06-01 11:00:00.000
数据总数: 60

输入 all 查看所有数据
输入 begin-end 查看指定范围的数据
输入 q 返回至序列选择
```

序列的信息包含 `pointId`，`startDate`，`endDate`，以及数据列表。前三个参数直接显示在控制台上，数据列表需要交互式查看。
数据列表的交互式查看方式与 `nv -latest`，`nv -lookup` 两条指令的查看方式相同。

此处同样以 `0-4` 为例，输入 `0-4`，程序会输出索引 `0`，`1`，`2`，`3` 的数据：

```text
0-4

索引: 0/4
  pointId: 1
  valueClass: java.lang.Double
  value: -3214878.9728833335
  happenedDate: 2023-06-01 10:01:00.000

索引: 1/4
  pointId: 1
  valueClass: java.lang.Double
  value: 5616899.462233333
  happenedDate: 2023-06-01 10:02:00.000

索引: 2/4
  pointId: 1
  valueClass: java.lang.Double
  value: 5465177.10835
  happenedDate: 2023-06-01 10:03:00.000

索引: 3/4
  pointId: 1
  valueClass: java.lang.Double
  value: 1288366.7757166666
  happenedDate: 2023-06-01 10:04:00.000

数据总数: 60

输入 all 查看所有数据
输入 begin-end 查看指定范围的数据
输入 q 返回至序列选择
```

与 `nv -latest`，`nv -lookup` 两条指令不同的是，此时输入 `q` 会返回至序列选择，而不是退出查询；
在序列选择界面，再次输入 `q` 才会退出查询。

## 亲自试一试

您可以使用现成的数据，对上述指令进行测试，`nv`，`fv`，`tv` 三条指令使用方式完全相同。

如果您没有现成的数据，可以按照如下步骤生成测试数据：

- [Generate Same Sample Data in Multiple Bridge](./GenerateSameSampleDataInMultipleBridge.md) -
  在多个桥接器生成相同的样本数据。
