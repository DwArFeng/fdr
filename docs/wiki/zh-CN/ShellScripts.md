# Shell Scripts - Shell 脚本

## fdr-start.sh

### 可配置变量

#### basedir

basedir 是本项目的安装目录，默认值为：

```bash
basedir="/usr/local/fdr"
```

如果您希望将本项目安装到其它目录，可以修改此变量。

该路径变量支持空格。

#### logdir

logdir 是本项目的日志目录，默认值为：

```bash
logdir="/var/log/fdr"
```

如果您希望将本项目的日志输出到其它目录，可以修改此变量。

该路径变量支持空格。

#### jvm_memory_opts

jvm_memory_opts 是本项目的 JVM 内存选项，默认值为：

```bash
jvm_memory_opts="\
  -Xmx100m \
  -XX:MaxMetaspaceSize=130m \
  -XX:ReservedCodeCacheSize=15m \
  -XX:CompressedClassSpaceSize=15m"
```

如果您希望修改本项目的 JVM 内存选项，可以修改此变量，变量中各选项含义如下：

- Xmx：最大堆内存。
- MaxMetaspaceSize：最大元空间大小。
- ReservedCodeCacheSize：保留代码缓存大小。
- CompressedClassSpaceSize：压缩类空间大小。

#### java_log_encoding_opts

java_log_encoding_opts 是本项目日志编码选项，默认值为：

```bash
java_log_encoding_opts="\
  -Dlog.consoleEncoding=UTF-8 \
  -Dlog.fileEncoding=UTF-8"
```

一般来说，CentOS 系统的默认字符集是 UTF-8，所以您不需要修改此选项。

如果您的 CentOS 系统使用的是其它字符集，可以修改此选项。

#### java_jmxremote_opts

java_jmxremote_opts 是本项目的 JMX 远程选项，默认值为：

```bash
java_jmxremote_opts=""
```

该脚本默认关闭 JMX 远程选项，如果您需要开启 JMX 远程选项，可以修改此选项。

您可以通过该选项注释中的提示，修改该选项的值，以开启 JMX 远程选项。

下面是一种开启 JMX 远程选项的示例：

```bash
java_jmxremote_opts="\
  -Dcom.sun.management.jmxremote.port=23000 \
  -Dcom.sun.management.jmxremote.authenticate=false \
  -Dcom.sun.management.jmxremote.ssl=false"
```

### 调试技巧

在安装与部署本项目时，您可能会遇到各种问题，其中部分问题是因为 JVM 启动失败导致的。

这些问题导致了项目无法启动，也无法通过追踪日志的方式定位问题，这时您可以通过以下方式重定位脚本的输出，以便于您定位问题。

找到脚本中的以下代码：

```bash
eval \
  nohup /bin/java -classpath "lib/*:libext/*" \
  "$jvm_memory_opts" \
  "$java_log_encoding_opts" \
  "$java_jmxremote_opts" \
  "$java_fixed_opts" \
  "com.dwarfeng.fdr.node.launcher.Launcher" \
  >/dev/null 2>&1 "&"
echo $! >"$basedir/fdr.pid"
```

将其修改为：

```bash
eval \
  nohup /bin/java -classpath "lib/*:libext/*" \
  "$jvm_memory_opts" \
  "$java_log_encoding_opts" \
  "$java_jmxremote_opts" \
  "$java_fixed_opts" \
  "com.dwarfeng.fdr.node.launcher.Launcher" \
  >out.log 2>&1 "&"
echo $! >"$basedir/fdr.pid"
```

然后重新启动项目，您将会在项目的安装目录下看到一个名为 out.log 的文件，该文件中记录了脚本的输出。

*排查完问题后，记得将脚本还原，因为 out.log 没有滚动机制，如果不还原，该文件会越来越大。*

## fdr-stop.sh

### 可配置变量

#### basedir

basedir 是本项目的安装目录，默认值为：

```bash
basedir="/usr/local/fdr"
```

如果您希望将本项目安装到其它目录，可以修改此变量。

该路径变量支持空格。

需要注意的是，该变量的值必须与 fdr-start.sh 中的 basedir 变量的值相同。
