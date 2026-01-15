# 数据库初始化说明

## 文件说明

- `初始化脚本.sql`: MySQL 8.0+ 完整建表脚本，包含22张表和初始化数据
- `../docs/数据库表设计文档.md`: 详细的表结构设计文档

## 快速开始

### 1. 创建数据库

```bash
mysql -u root -p -e "CREATE DATABASE douyin_live CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
```

### 2. 执行初始化脚本

```bash
mysql -u root -p douyin_live < 初始化脚本.sql
```

### 3. 验证安装

```bash
mysql -u root -p douyin_live -e "SHOW TABLES;"
```

预期输出22张表：
- user, user_auth, user_profile, user_oauth_bind, user_device, user_blacklist
- follow_relation
- live_room, live_session, live_message
- post, post_media, post_like, comment
- gift, gift_order, wallet_account, recharge_order
- report, audit_task
- sensitive_word, operation_log

## 初始化数据

脚本自动创建以下测试数据：

### 测试账号
| 手机号 | 邮箱 | 密码 | 昵称 |
|--------|------|------|------|
| 13800138001 | admin@example.com | test123456 | 系统管理员 |
| 13800138002 | user1@example.com | test123456 | 测试用户1 |
| 13800138003 | user2@example.com | test123456 | 测试用户2 |
| 13800138004 | streamer@example.com | test123456 | 测试主播 |

### 礼物商品
已初始化12个礼物：玫瑰(1金币)、棒棒糖(5金币)、巧克力(10金币)...城堡(50000金币)

### 敏感词库
已初始化50个常见敏感词（politics/porn/abuse/ad/fraud），生产环境需扩展到完整词库

## 数据库配置建议

### MySQL配置优化（my.cnf）

```ini
[mysqld]
# 字符集
character-set-server=utf8mb4
collation-server=utf8mb4_unicode_ci

# InnoDB缓冲池（根据服务器内存调整）
innodb_buffer_pool_size=2G

# 连接数
max_connections=1000

# 慢查询日志
slow_query_log=1
slow_query_log_file=/var/log/mysql/slow.log
long_query_time=2

# 二进制日志（主从复制）
log_bin=mysql-bin
binlog_format=ROW
expire_logs_days=7
```

## MyBatis代码生成

### 使用 we-code-generator 模块生成代码

1. 修改 `we-code-generator/generatorConfig.xml`：

```xml
<jdbcConnection 
    driverClass="com.mysql.cj.jdbc.Driver"
    connectionURL="jdbc:mysql://localhost:3306/douyin_live?useSSL=false&amp;serverTimezone=Asia/Shanghai"
    userId="root"
    password="your_password">
</jdbcConnection>

<table tableName="user" 
       domainObjectName="User"
       enableCountByExample="false"
       enableUpdateByExample="false"
       enableDeleteByExample="false"
       enableSelectByExample="false"
       selectByExampleQueryId="false">
</table>
```

2. 执行生成命令：

```bash
cd we-code-generator
mvn mybatis-generator:generate
```

## 性能优化建议

### 1. Redis缓存策略

建议缓存以下数据到Redis：

```
user:{user_id}              -> 用户信息 (1小时)
user:{user_id}:stat         -> 关注数/粉丝数 (10分钟)
room:{room_id}:online       -> 直播在线人数 (5分钟)
gift:rank:{session_id}      -> 礼物排行榜 (1小时)
post:{post_id}:like         -> 点赞数 (10分钟)
sensitive:words             -> 敏感词库 (永久)
```

### 2. 分表分区

高并发表建议分表：
- `live_message`: 按 room_id % 10 分表
- `post_like`: 按 post_id % 10 分表
- `follow_relation`: 按 follower_id % 10 分表

历史数据建议分区：
- `live_session`: 按月分区
- `gift_order`: 按月分区

### 3. 索引优化

复合索引遵循最左前缀原则：
- 等值查询字段在前
- 范围查询字段在中
- 排序字段在后

示例：
```sql
-- ✅ 正确
KEY idx_correct (user_id, status, create_time DESC)

-- ❌ 错误
KEY idx_wrong (create_time, status, user_id)
```

## 数据归档策略

| 表名 | 归档周期 | 归档目标 |
|------|---------|---------|
| live_message | 7天 | OSS对象存储 |
| post_like | 30天 | 归档数据库 |
| gift_order | 3个月 | 归档数据库 |
| live_session | 6个月 | 归档数据库 |

## 备份策略

### 全量备份（每天凌晨2点）

```bash
#!/bin/bash
mysqldump -u root -p your_password \
    --single-transaction \
    --routines \
    --triggers \
    --databases douyin_live \
    | gzip > /backup/douyin_live_$(date +%Y%m%d).sql.gz
```

### 增量备份（开启binlog）

```bash
# 每小时备份binlog
mysqlbinlog --read-from-remote-server \
    --host=localhost \
    --user=root \
    --password=your_password \
    --raw \
    mysql-bin.000001 > /backup/binlog/$(date +%Y%m%d_%H).binlog
```

## 常见问题

### Q1: 执行脚本报错 "Unknown collation: utf8mb4_unicode_ci"

**A**: MySQL版本过低，请升级到MySQL 8.0+

### Q2: 如何重置数据库？

**A**: 删除数据库后重新执行初始化脚本

```bash
mysql -u root -p -e "DROP DATABASE IF EXISTS douyin_live;"
mysql -u root -p -e "CREATE DATABASE douyin_live CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
mysql -u root -p douyin_live < 初始化脚本.sql
```

### Q3: 如何修改测试账号密码？

**A**: 使用BCrypt在线工具生成新密码哈希，然后更新user表：

```sql
UPDATE user SET password_hash = '$2a$10$YOUR_NEW_HASH' WHERE id = 1;
```

## 联系方式

如有问题，请参考 `../docs/数据库表设计文档.md` 或联系开发团队。

---

**版本**: v1.0.0  
**最后更新**: 2024-01-15  
**维护**: 开发团队
