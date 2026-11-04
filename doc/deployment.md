# 一键部署到现有 Linux 服务

在 Windows 项目根目录运行：

```powershell
.\deploy.cmd
```

修改代码后运行该命令，会执行 Maven `package`（包含测试阶段），上传 JAR 和运行所需数据库配置，校验 SHA256，备份旧 JAR，重启已有 systemd 服务，检查服务器本地及公网登录页。默认不会监听代码改动并立即发布，避免保存未完成代码时触发部署。

首次设置本地 `.env` 中的 SSH、数据库、JDK 和 Maven 参数。服务器必须已安装 Java，并有当前项目的 systemd 服务。SSH 主机密钥必须已在本机 known_hosts 中确认；脚本不会自动信任陌生服务器。本项目当前连接已确认。

支持 `SSH_PASSWORD` 或 `SSH_KEY_PATH`。Windows OpenSSH 使用临时 askpass 辅助程序从进程环境读取密码，不把密码放进命令行。设置了私钥路径时优先用私钥；加密私钥建议事先加入 ssh-agent。

仅数据库和 Druid 配置会复制到服务器 `.env`；SSH、面板密码不会随包上传。现有服务器 `application-prod.yml` 仍可覆盖应用配置。脚本不自动执行数据库迁移，不修改安全组，也不修改宝塔管理的其他服务。

备份位于 `${DEPLOY_DIR}/backups/部署编号`，发布文件位于 `releases/部署编号`，权限仅当前部署用户可读。服务重启或本机健康检查失败会恢复旧 JAR 和 `.env` 并再次启动；错误以非零退出码返回。公网检查失败则报告失败，保留已通过本机检查的新版本，便于排查网络问题。

成功后显示网址、版本编号、SHA256 和备份路径，并写入被 Git 忽略的 `ruoyi-admin/target/deploy-result.txt`。本机临时运行配置在退出时删除。历史发布和备份不会自动删除。

如确实要部署已有 JAR 而不构建，可用 `.\deploy.cmd -SkipBuild`。
