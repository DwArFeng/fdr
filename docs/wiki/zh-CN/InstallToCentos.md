# Install to CentOS - 在 CentOS 上安装

## 确认系统需求

请参考 [SystemRequirements.md](./SystemRequirements.md) 确认系统需求。

## 获取二进制文件

### 从发布包中获取

从 Github 上获取软件包，软件包可以从 Github 的 Release 页面下载。

### 从源码编译

请参考 [CompileBySource.md](./CompileBySource.md) 从源码编译。

## 解压软件包

软件包的名称格式为 `fdr-node-${version}-release.tar.gz`，其中 `${version}` 为软件包的版本号。

使用工具软件，将软件包上传至服务器 `/usr/local` 目录下，解压软件包。

```shell
cd /usr/local
tar -zxvf fdr-node-${version}-release.tar.gz
mv fdr-node-${version} fdr
```

## 更改启停脚本

请参考 [ShellScripts.md](./ShellScripts.md) 或 [Batch Scripts](./BatchScripts.md) 更改启停脚本。

## 更改配置

请参考 [ConfDirectory.md](./ConfDirectory.md) 更改配置。

## 选择可选功能

请参考 [OptDirectory.md](./OptDirectory.md) 选择可选功能。

## 注册服务

运行如下命令，注册服务。

```shell
cat <<EOF | sudo tee /usr/lib/systemd/system/fdr.service
[Unit]
Description=Fdr Service
After=syslog.target network.target

[Service]
User=root
Group=root
Type=forking
ExecStart=/usr/local/fdr/bin/fdr-start.sh
ExecStop=/usr/local/fdr/bin/fdr-stop.sh
PrivateTmp=true

[Install]
WantedBy=multi-user.target

EOF
```

*注意：如果 FDR 安装在其它位置，则需要修改 `ExecStart` 和 `ExecStop` 的路径。*

运行 daemon-reload。

```shell
sudo systemctl daemon-reload
```

设置开机启动。

```shell
sudo systemctl enable fdr
```

## 启动服务

启动服务。

```shell
sudo systemctl start fdr
```

监视日志。

```shell
tail -f /var/log/fdr/info.log
```

*注意：如果 FDR 安装在其它位置，则需要修改监视路径。*

查看服务状态。

```shell
sudo systemctl status fdr
```

## 参阅

- [Shell Scripts](./ShellScripts.md) - 脚本说明，详细说明了本项目的 Shell 脚本，即 `bin/` 目录下 `*.sh` 文件的用法。
- [Batch Scripts](./BatchScripts.md) - 脚本说明，详细说明了本项目的 Batch 脚本，即 `bin/` 目录下 `*.bat` 文件的用法。
- [Conf Directory](./ConfDirectory.md) - 配置目录说明，详细说明如何配置本项目，即 `conf/` 目录下的内容。
- [Opt Directory](./OptDirectory.md) - 可选配置目录说明，详细介绍了本项目的可选配置，即 `opt/` 目录下的内容。
