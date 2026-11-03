# 本地隐私配置与 GitHub

真实连接信息保存在项目根目录 `.env`，该文件已被 `.gitignore` 排除。只提交 `.env.example`，不要提交 `.env`，也不要使用 `git add -f` 强制添加。

首次使用复制 `.env.example` 为 `.env` 并填写。应用通过 Spring Boot 配置导入读取**启动工作目录**下的 `.env`，无需 dotenv 插件。它采用 Java properties 格式，值不加引号；密码中的反斜杠须写为两个反斜杠。系统环境变量可覆盖对应配置。

从项目根目录启动：

```sh
java -jar ruoyi-admin/target/ruoyi-admin.jar
```

IDE 启动时将 Working directory 设置为项目根目录。部署时将 `.env` 单独放在服务器应用的工作目录中，不打包进 JAR，Linux 下设置 `chmod 600 .env`。SSH、面板及初始登录信息仅作私人备忘，不被应用自动使用。

服务器现有外置 `application-prod.yml` 仍生效，本次整理不会修改线上部署。将来部署新包时，如果想统一使用 `.env`，需同时将服务器外置配置里的数据库配置改为 `${DB_URL}`、`${DB_USERNAME}`、`${DB_PASSWORD}`。

GitHub 上传前检查 `git status --short`，确认没有 `.env`、JAR、数据库备份及登录 Cookie。`target/` 已忽略，其中旧 JAR 可能仍含此前的凭据，不要上传为 Release 附件。

若凭据以前已提交，`.gitignore` 不会清除历史：先轮换相关密码，再清理仓库历史。当前目录未发现 `.git`，因此无法检查任何远端仓库的历史提交。
