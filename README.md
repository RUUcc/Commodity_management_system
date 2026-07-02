# 🛒 商品管理系统（Commodity Management System）

一个基于 **Spring Boot 3 + MyBatis-Plus + Thymeleaf** 的全栈商品管理 Web 应用，适合作为 **Java Web 课程设计**、**毕业设计** 或 **Spring Boot 入门学习** 的参考项目。

---

## 📋 项目简介

本系统实现了一个完整的商品信息管理平台，包含**用户认证**、**商品 CRUD**、**文件管理**三大核心模块。你可以用它来：

- 🏪 **管理商品信息**：添加、编辑、删除、搜索商品，支持商品图片上传与预览
- 👤 **管理用户账号**：添加、编辑、删除系统用户，支持用户头像上传
- 📁 **浏览上传文件**：查看和管理所有上传的文件（图片预览、文件大小、删除）
- 🔐 **安全登录认证**：基于 Session 的登录机制，密码采用 BCrypt 加密存储
- 📊 **数据分页与排序**：商品列表支持按 ID、价格、库存排序，支持多条件模糊搜索
- ⚡ **本地缓存加速**：基于 Caffeine 的商品查询缓存，提升查询性能

---

## 🛠 技术栈

| 层级 | 技术 | 说明 |
|------|------|------|
| **语言** | Java 17 | LTS 长期支持版本 |
| **框架** | Spring Boot 3.0.2 | 企业级快速开发框架 |
| **ORM** | MyBatis-Plus 3.5.9 | 增强版 MyBatis，简化 CRUD |
| **模板引擎** | Thymeleaf | 服务端渲染 HTML 页面 |
| **数据库** | MySQL 8.0 | 关系型数据库 |
| **缓存** | Caffeine | 高性能本地缓存 |
| **安全** | Spring Security Crypto (BCrypt) | 密码加密存储 |
| **校验** | Jakarta Bean Validation | 表单数据校验 |
| **构建** | Maven | 项目构建与依赖管理 |
| **前端** | 原生 HTML5 + CSS3 + JavaScript | 无框架依赖，纯原生实现 |

> 💡 前端不依赖任何第三方 CSS/JS 框架（无 Bootstrap、jQuery），全部使用原生 `fetch()` API 进行 Ajax 交互，非常适合学习前后端分离的基础原理。

---

## 🚀 快速开始

### 前置环境

确保你的开发环境已安装以下软件：

| 软件 | 版本要求 | 说明 |
|------|----------|------|
| JDK | 17+ | [下载地址](https://www.oracle.com/java/technologies/downloads/#java17) |
| MySQL | 8.0+ | [下载地址](https://dev.mysql.com/downloads/mysql/) |
| Maven | 3.6+ | [下载地址](https://maven.apache.org/download.cgi)（或使用 IDE 内置 Maven） |
| IDE | 推荐 IntelliJ IDEA | 也可使用 Eclipse / VS Code |

### 安装步骤

#### 1. 克隆项目

```bash
git clone <仓库地址>
cd Commodity_management_system
```

#### 2. 初始化数据库

执行 `sql/init.sql` 脚本，创建数据库和表结构，并插入初始数据：

```bash
# 方式一：在 MySQL 客户端中执行
mysql -u root -p < sql/init.sql

# 方式二：在 MySQL 命令行中执行
mysql> source sql/init.sql;
```

该脚本会自动：
- 创建 `commodity_db` 数据库（字符集 `utf8mb4`）
- 创建 `tb_user`（用户表）和 `tb_product`（商品表）
- 插入 5 条示例商品数据

#### 3. 配置数据库连接

编辑 `src/main/resources/application.yml`，修改数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/commodity_db?useSSL=false&serverTimezone=Asia/Shanghai&characterEncoding=utf-8&allowPublicKeyRetrieval=true
    username: root      # 改为你的 MySQL 用户名
    password: root      # 改为你的 MySQL 密码
```

#### 4. 配置上传路径

在 `application.yml` 中修改文件上传存储路径（默认为 `D:/spring-boot/期末设计/uploads/`）：

```yaml
app:
  upload-path: D:/your-upload-path/uploads/   # 改为你的实际路径
```

> ⚠️ 请确保该目录已存在，否则上传文件时会报错。

#### 5. 启动项目

```bash
# 在项目根目录执行
mvn spring-boot:run
```

或者在 IDE 中直接运行 `CommodityManagementSystemJzsApplication.java` 主类。

#### 6. 访问系统

打开浏览器访问：**http://localhost:8080**

系统启动时会**自动创建管理员账号**：
- 用户名：`admin`
- 密码：`123456`

> ⚠️ 首次启动后建议立即修改默认密码！

---

## 📖 使用指南

### 登录系统

1. 访问 `http://localhost:8080`，自动跳转到登录页面
2. 输入用户名 `admin` 和密码 `123456`
3. 点击"登录"按钮进入系统主页

### 商品管理

进入"商品管理"页面后，你可以：

- **查看商品**：列表展示所有商品，支持分页浏览
- **搜索商品**：按商品名称（模糊搜索）或类别筛选
- **排序商品**：点击表头的 ID / 价格 / 库存列进行升降序排序
- **添加商品**：点击"添加商品"按钮，填写信息并上传商品图片
- **编辑商品**：点击某行的"编辑"按钮，在弹窗中修改信息
- **删除商品**：点击某行的"删除"按钮确认删除

### 用户管理

进入"用户管理"页面后，你可以：

- **查看用户**：列表展示所有用户，支持分页浏览
- **添加用户**：点击"添加用户"按钮，填写用户名、密码并上传头像
- **编辑用户**：修改用户信息（修改密码时需填写新密码，留空则不修改）
- **删除用户**：删除指定用户（⚠️ 不能删除自己）

### 文件管理

进入"文件管理"页面后，你可以：

- **浏览文件**：查看所有上传的图片文件及其大小和上传时间
- **预览图片**：点击"查看"按钮在新标签页预览原图
- **删除文件**：删除不需要的文件

---

## 📁 项目结构

```
Commodity_management_system/
├── sql/
│   └── init.sql                          # 数据库初始化脚本
├── photo/                                # 静态图标资源
├── src/main/java/org/example/commodity_management_system_jzs/
│   ├── CommodityManagementSystemJzsApplication.java  # 启动入口
│   ├── common/
│   │   ├── Result.java                   # 统一 JSON 响应封装
│   │   └── GlobalExceptionHandler.java   # 全局异常处理器
│   ├── config/
│   │   ├── DataInitializer.java          # 启动时初始化管理员账号
│   │   ├── MyBatisPlusConfig.java        # MyBatis-Plus 分页插件配置
│   │   ├── SecurityConfig.java           # BCrypt 加密器 Bean 注册
│   │   └── WebConfig.java                # 拦截器 + 静态资源映射
│   ├── controller/
│   │   ├── IndexController.java          # 主页控制器
│   │   ├── LoginController.java          # 登录/注销控制器
│   │   ├── ProductController.java        # 商品 CRUD + 图片上传
│   │   ├── UserController.java           # 用户 CRUD + 头像上传
│   │   └── FileController.java           # 文件浏览/删除管理
│   ├── entity/
│   │   ├── Product.java                  # 商品实体类
│   │   └── User.java                     # 用户实体类
│   ├── interceptor/
│   │   └── LoginInterceptor.java         # Session 登录拦截器
│   ├── mapper/
│   │   ├── ProductMapper.java            # 商品 Mapper（BaseMapper + 注解 + XML）
│   │   └── UserMapper.java               # 用户 Mapper
│   └── service/
│       ├── ProductService.java           # 商品 Service 接口
│       ├── UserService.java              # 用户 Service 接口
│       └── impl/
│           ├── ProductServiceImpl.java   # 商品 Service 实现（缓存 + 事务）
│           └── UserServiceImpl.java      # 用户 Service 实现（BCrypt + 事务）
└── src/main/resources/
    ├── application.yml                   # 主配置文件
    ├── mapper/
    │   └── ProductMapper.xml             # MyBatis XML 动态 SQL
    ├── static/                           # 静态资源（背景图等）
    └── templates/
        ├── login.html                    # 登录页面
        ├── index.html                    # 主页/仪表盘
        ├── product-list.html             # 商品管理页面
        ├── user-list.html                # 用户管理页面
        └── file-list.html                # 文件管理页面
```

---

## ✨ 核心特性详解

### 1. 三种 MyBatis 用法示范

本项目有意识地展示了 MyBatis 的三种使用方式，适合学习参考：

| 方式 | 示例 | 适用场景 |
|------|------|----------|
| **MyBatis-Plus BaseMapper** | `UserMapper`、Product 基本 CRUD | 简单的增删改查 |
| **注解方式** | `ProductMapper` 中的 `@Select`、`@Update` | 简单的自定义 SQL |
| **XML 映射** | `ProductMapper.xml` 动态 SQL | 复杂的多条件组合查询 |

### 2. Caffeine 本地缓存

商品查询结果使用 Caffeine 缓存加速：
- 查询操作自动缓存（`@Cacheable`）
- 增删改操作自动清除缓存（`@CacheEvict`）
- 缓存键包含所有查询参数，确保数据一致性

### 3. 密码安全

- 用户密码使用 **BCrypt** 单向加密存储
- 系统启动时自动检测明文密码并升级为 BCrypt 加密
- 修改密码时仅对新密码加密，留空则保持原密码不变

### 4. 表单校验

使用 Jakarta Bean Validation 进行服务端校验：
- 商品名称不能为空
- 商品价格必须大于 0.01
- 商品库存不能为负数
- 用户名和密码不能为空
- 校验失败返回友好的错误提示

### 5. 全局异常处理

统一的异常处理机制，将各类异常转换为标准的 JSON 响应格式，前端统一处理。

---

## 🔧 常见问题

### Q: 启动时报 "Access denied for user" 错误？
A: 检查 `application.yml` 中的数据库用户名和密码是否正确。

### Q: 上传文件时报错？
A: 检查 `app.upload-path` 配置的目录是否存在，以及是否有写入权限。

### Q: 页面样式显示不正常？
A: 确保以管理员身份运行 IDE，或检查 `WebConfig.java` 中的静态资源映射是否正确。

### Q: 如何修改默认管理员密码？
A: 登录后进入"用户管理"页面，找到 admin 用户并编辑密码；或直接修改 `DataInitializer.java` 中的默认密码后重新启动。

---

## 📝 开发说明

- 详细开发文档请参阅 [开发文档.md](./开发文档.md)
- 本项目使用 **JDK 17**，请勿使用更低版本
- 数据库字符集使用 `utf8mb4`，支持 emoji 等特殊字符
- 文件上传大小限制为 10MB（可在 `application.yml` 中修改）

---

## 📄 许可证

本项目仅用于学习和教育目的。

---

## 👨‍💻 作者

**RUUcc**

---

> 💡 **提示**：如果你是 Spring Boot 初学者，建议按照以下顺序阅读源码：
> 1. `application.yml` → 了解项目配置
> 2. `entity/` → 了解数据模型
> 3. `controller/` → 了解 API 接口
> 4. `service/` → 了解业务逻辑
> 5. `config/` → 了解配置和拦截器
> 6. `templates/` → 了解前端页面与 Ajax 交互

