# CalendaMe Backend (Spring Boot + SQLite)

✅ Spring Boot + SQLite 项目结构与配置说明（CalendaMe 后端）
这是为了练习 Java 和 Spring Boot 而将原 Python/SQLite3 后端重写的版本。前端使用 React + Vite，后端使用 Spring Boot + SQLite。

🧱 1. 项目结构（关键路径）
bash
Copy
Edit
calendame-frontend/
├── react-vite/                   # 前端 React 项目
├── backend-JAVA/
│   └── backend/
│       ├── src/
│       │   └── main/
│       │       ├── java/
│       │       │   └── com/calendame/backend/
│       │       │       ├── BackendApplication.java
│       │       │       ├── HelloController.java
│       │       │       ├── User.java
│       │       │       ├── UserController.java
│       │       │       ├── UserRepository.java
│       │       │       └── config/
│       │       │           └── SQLiteDialect.java ✅ 自定义 Hibernate 支持 SQLite
│       │       └── resources/
│       │           └── application.properties ✅ 配置数据库连接与 JPA
│       ├── pom.xml ✅ Maven 项目配置文件
🔧 2. 配置说明（application.properties）
properties
Copy
Edit
spring.datasource.url=jdbc:sqlite:calendame.db
spring.datasource.driver-class-name=org.sqlite.JDBC
spring.jpa.database-platform=com.calendame.backend.config.SQLiteDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
使用 SQLite 本地数据库文件 calendame.db

自动建表（ddl-auto=update）

打印 SQL 日志到控制台

🧩 3. SQLiteDialect.java 自定义方言
Hibernate 不原生支持 SQLite，需要自己写方言类来告诉 Hibernate：

各种 Java 类型映射到 SQLite 的数据类型

支持主键自增（@GeneratedValue）

不支持外键约束

SQLiteDialect 放在 com.calendame.backend.config 包中，并在 application.properties 中声明使用。

📁 4. 路径问题踩坑笔记
Java 要求 package com.xxx.yyy 必须对应目录 src/main/java/com/xxx/yyy/

❌ 千万不要出现嵌套的路径：src/main/java/**java/com/...，否则 VS Code 会提示 package 不匹配

正确目录为：src/main/java/com/calendame/backend/...

✅ 当前功能实现
启动成功的 Spring Boot 项目

API 测试成功的 /api/hello

配置了 SQLite 数据库支持（并创建了自定义 Dialect 类）

准备添加实体类 User 并通过 Spring Data JPA 进行读写操作

💡 下一步可选功能
 添加 User.java + UserController + UserRepository 实体与接口

 添加事件表 Event.java（对应预约的日程）

 支持预约时间段 Slot 与客户预约记录 Booking

 提供 JSON API 接口供前端使用

📌 技术栈
Java 17

Spring Boot 3.x

Spring Data JPA

SQLite 3（通过第三方 JDBC 驱动）

VS Code + WSL 开发环境