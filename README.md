# TechHub 博客后端

基于 Spring Boot 3 + MySQL + Spring Security(JWT) 的技术博客社区后端。

## 技术栈

- Java 17
- Spring Boot 3.3.4
- Spring Data JPA
- Spring Security + JWT
- MySQL 8.0
- Lombok

## 快速开始

### 1. 准备数据库

```bash
mysql -u root -p < sql/init.sql
```

或使用 Docker Compose 一键启动 MySQL + 后端：

```bash
docker compose up -d
```

### 2. 修改配置

编辑 `src/main/resources/application.yml`，配置你的数据库账号密码。

### 3. 启动

```bash
mvn spring-boot:run
```

首次启动会自动初始化种子数据（分类、标签、示例文章、测试账号）。

### 测试账号

| 用户名 | 密码 |
|--------|------|
| admin | admin123 |
| xiaoming | 123456 |

## API 接口

### 认证

| 方法 | 路径 | 说明 | 鉴权 |
|------|------|------|------|
| POST | /api/auth/register | 注册 | 否 |
| POST | /api/auth/login | 登录，返回 JWT | 否 |

### 文章

| 方法 | 路径 | 说明 | 鉴权 |
|------|------|------|------|
| GET | /api/articles | 文章列表（支持 categoryId/page/size/sort） | 否 |
| GET | /api/articles/{id} | 文章详情（浏览量+1） | 否 |
| GET | /api/articles/search?keyword= | 搜索 | 否 |
| GET | /api/articles/tag/{tagName} | 按标签查询 | 否 |
| POST | /api/articles | 发布文章 | 是 |
| PUT | /api/articles/{id} | 更新文章 | 是 |
| DELETE | /api/articles/{id} | 删除文章 | 是 |
| POST | /api/articles/{id}/like | 点赞 | 否 |

### 评论

| 方法 | 路径 | 说明 | 鉴权 |
|------|------|------|------|
| GET | /api/comments/article/{articleId} | 文章评论列表 | 否 |
| POST | /api/comments/article/{articleId} | 发表评论 | 是 |
| DELETE | /api/comments/{id} | 删除评论 | 是 |

### 分类 & 标签

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /api/categories | 分类列表 |
| GET | /api/tags | 标签列表 |

## 统一响应格式

```json
{
  "code": 200,
  "message": "success",
  "data": { }
}
```

鉴权方式：请求头携带 `Authorization: Bearer <token>`。
