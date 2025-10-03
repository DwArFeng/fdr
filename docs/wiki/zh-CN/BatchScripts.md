# Batch Scripts - 批处理脚本

## fdr-start.bat

### 可配置变量

#### basedir

basedir 是本项目的安装目录，默认值为：

```bat
SET basedir=C:\Program Files\fdr
```

如果您希望将本项目安装到其它目录，可以修改此变量。

该路径变量支持空格。

#### logdir

logdir 是本项目的日志目录，默认值为：

```bat
SET logdir=%basedir%\logs
```

其中，`%basedir%` 是 basedir 变量的值。即默认情况下，日志目录为 `C:\Program Files\fdr\logs`。

如果您希望将本项目的日志输出到其它目录，可以修改此变量。

该路径变量支持空格。

#### jvm_memory_opts

jvm_memory_opts 是本项目的 JVM 内存选项，默认值为：

```bat
SET jvm_memory_opts=^
  -Xmx100m ^
  -XX:MaxMetaspaceSize=130m ^
  -XX:ReservedCodeCacheSize=15m ^
  -XX:CompressedClassSpaceSize=15m
```

如果您希望修改本项目的 JVM 内存选项，可以修改此变量，变量中各选项含义如下：

- Xmx：最大堆内存。
- MaxMetaspaceSize：最大元空间大小。
- ReservedCodeCacheSize：保留代码缓存大小。
- CompressedClassSpaceSize：压缩类空间大小。

#### java_log_encoding_opts

java_log_encoding_opts 是本项目日志编码选项，默认值为：

```bat
SET java_log_encoding_opts=^
  -Dlog.consoleEncoding=GBK ^
  -Dlog.fileEncoding=UTF-8
```

一般来说，Windows 系统的默认字符集是 GBK，如果使用其它字符集，控制台的输出内容可能会乱码。

但是对于文本文件来说，Windows 系统支持 UTF-8 编码，所以您不需要修改此选项。

如果您的 Windows 系统使用的是其它字符集，可以修改此选项。

#### java_jmxremote_opts

java_jmxremote_opts 是本项目的 JMX 远程选项，默认值为：

```bat
SET java_jmxremote_opts=
```

该脚本默认关闭 JMX 远程选项，如果您需要开启 JMX 远程选项，可以修改此选项。

您可以通过该选项注释中的提示，修改该选项的值，以开启 JMX 远程选项。

下面是一种开启 JMX 远程选项的示例：

```bat
SET java_jmxremote_opts=^
  -Dcom.sun.management.jmxremote.port=23000 ^
  -Dcom.sun.management.jmxremote.authenticate=false ^
  -Dcom.sun.management.jmxremote.ssl=false
```
