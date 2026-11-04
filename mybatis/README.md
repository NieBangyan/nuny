# 独立 MyBatis 学习模块

在 IDEA Maven 面板点击“重新加载所有 Maven 项目”，然后打开 `src/main/java/com/ruoyi/App.java`，点击 main 方法旁的运行按钮。

模块直接使用 MyBatis 的 SqlSession，不启动 Spring Boot。数据库连接从项目根目录 `.env` 读取，也支持同名环境变量。工作目录可以设为项目根目录或 `mybatis` 目录。配置文件中不保存真实密码。

`model/StudentPracticeRecord.java` 是实体类，`mapper/StudentPracticeRecordsMapper.java` 是查询接口，`resources/mappers/StudentPracticeRecordsMapper.xml` 写查询 SQL，`mybatis-config.xml` 注册连接和 Mapper。

默认只读查询最近 100 条课堂记录，打印到 IDEA 控制台。传入程序参数 `--check-config` 可只校验配置，不连接数据库。

该模块用于独立学习，不被后台应用依赖；原来的 `/api/student/chart` 仍使用 ruoyi-system 中的 Service 和 Mapper。
