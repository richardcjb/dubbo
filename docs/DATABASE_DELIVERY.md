# 数据库设计交付文档

## 📦 交付物清单

本次任务已完成抖音直播风格App的完整MySQL数据库表结构设计，共交付以下文件：

### 1. 核心文档
- ✅ `docs/数据库表设计文档.md` - 详细设计文档（46KB，14个章节）
- ✅ `db/初始化脚本.sql` - 可执行SQL脚本（30KB，22张表）
- ✅ `db/README.md` - 快速上手指南
- ✅ `db/validate_schema.sql` - 表结构验证脚本

### 2. 文档内容覆盖

#### 数据库表设计文档.md 包含：
1. ✅ 总体架构和技术选型
2. ✅ 表关系图（Mermaid格式）
3. ✅ 22张表的详细设计（6大模块）
   - 用户体系模块（6张表）
   - 社交关系模块（1张表）
   - 直播模块（3张表）
   - 内容模块（4张表）
   - 礼物与钱包模块（4张表）
   - 审核与举报模块（2张表）
   - 配置与日志模块（2张表）
4. ✅ 每张表包含：
   - 表名、用途、字段清单（类型/长度/约束/注释）
   - 关键索引与联合索引说明
   - 软删除、状态字段、可见性设计理由
   - 数据量估算与分区建议
5. ✅ 通用设计规范（命名、类型、索引、查询规范）
6. ✅ 性能优化建议（分表分区、Redis缓存、慢查询优化）
7. ✅ 数据安全与隐私（脱敏、权限、GDPR）
8. ✅ 后续扩展预留（待设计表、分库分表、归档策略）
9. ✅ 验证清单（全部通过✓）
10. ✅ 交付物使用指南（部署、集成、TypeScript接口）
11. ✅ 附录（表清单速查、性能指标SLA）

#### 初始化脚本.sql 包含：
1. ✅ 22张表的完整CREATE TABLE语句
2. ✅ 所有主键、唯一索引、普通索引
3. ✅ 所有约束、DEFAULT值、ON UPDATE触发器
4. ✅ 初始化数据：
   - 12个礼物商品（1-50000金币）
   - 50个敏感词（5个分类）
   - 4个测试账号（含资料和钱包）
   - 1个测试直播间
5. ✅ 验证查询（表数量、索引、数据统计）
6. ✅ MySQL 8.0+兼容，支持直接执行

---

## 📊 表结构概览

### 模块1：用户体系（6张表）
| 表名 | 用途 | 关键字段 | 预估数据量 |
|------|------|---------|-----------|
| user | 用户基本信息 | phone/email/password_hash/nickname | 1000万 |
| user_auth | 多登录方式 | auth_type/auth_id | 2000万 |
| user_profile | 用户资料扩展 | gender/birthday/bio | 1000万 |
| user_oauth_bind | 第三方绑定 | oauth_provider/oauth_id | 1500万 |
| user_device | 设备管理 | device_id/last_login_time | 3000万 |
| user_blacklist | 黑名单 | blocked_user_id/status | 100万 |

### 模块2：社交关系（1张表）
| 表名 | 用途 | 关键字段 | 预估数据量 |
|------|------|---------|-----------|
| follow_relation | 关注关系 | follower_id/following_id/mutual | 5000万 |

### 模块3：直播（3张表）
| 表名 | 用途 | 关键字段 | 预估数据量 |
|------|------|---------|-----------|
| live_room | 直播间配置 | creator_id/room_status/push_url | 50万 |
| live_session | 直播场次 | room_id/start_time/peak_online | 1800万/年 |
| live_message | 弹幕消息 | session_id/message_content | 50亿/年 |

### 模块4：内容（4张表）
| 表名 | 用途 | 关键字段 | 预估数据量 |
|------|------|---------|-----------|
| post | 内容发布 | creator_id/content_text/visibility | 3650万/年 |
| post_media | 内容媒体 | post_id/media_type/media_url | 5000万/年 |
| post_like | 点赞 | post_id/liker_id | 365亿/年 |
| comment | 评论 | post_id/comment_text | 18亿/年 |

### 模块5：礼物与钱包（4张表）
| 表名 | 用途 | 关键字段 | 预估数据量 |
|------|------|---------|-----------|
| gift | 礼物商品 | coin_price/combo_multiplier | 100 |
| gift_order | 礼物订单 | sender_id/receiver_id/order_no | 9亿/年 |
| wallet_account | 钱包 | balance/version | 1000万 |
| recharge_order | 充值订单 | amount/payment_channel/order_no | 1000万/年 |

### 模块6：审核与举报（2张表）
| 表名 | 用途 | 关键字段 | 预估数据量 |
|------|------|---------|-----------|
| report | 举报记录 | report_type/target_type/status | 36万/年 |
| audit_task | 审核工单 | report_id/result/punishment | 40万/年 |

### 模块7：配置与日志（2张表）
| 表名 | 用途 | 关键字段 | 预估数据量 |
|------|------|---------|-----------|
| sensitive_word | 敏感词库 | word/category | 1万 |
| operation_log | 操作日志 | operator_id/operation_type | 36万/年 |

**总计**: 22张表，覆盖MVP阶段所有核心功能

---

## 🎯 设计亮点

### 1. 通用规范严格执行
- ✅ 所有字段使用蛇形命名（snake_case）
- ✅ 主键统一BIGINT AUTO_INCREMENT
- ✅ 软删除统一deleted字段（0=未删，1=已删）
- ✅ 时间字段统一DATETIME（create_time/update_time）
- ✅ 状态字段统一TINYINT枚举
- ✅ 字符集统一utf8mb4（支持emoji）

### 2. 索引设计合理
- ✅ 主键索引（22个）
- ✅ 唯一索引（13+个，防重复业务逻辑）
  - order_no唯一（礼物订单、充值订单）
  - 关注对唯一（follower_id, following_id）
  - 点赞唯一（post_id, liker_id）
- ✅ 复合索引（40+个，遵循最左前缀原则）
  - 等值查询字段在前
  - 范围查询字段在中
  - 排序字段在后

### 3. 性能优化前瞻
- ✅ 高并发表分表建议（live_message/post_like/follow_relation）
- ✅ 历史数据分区建议（live_session/gift_order按月）
- ✅ Redis缓存策略（用户信息、统计数据、排行榜）
- ✅ 冗余字段减少JOIN（room_id冗余到live_message）
- ✅ 乐观锁版本号（wallet_account.version）

### 4. 业务幂等保证
- ✅ 订单号唯一约束（uk_order_no）
- ✅ 防重复关注（uk_follow_pair）
- ✅ 防重复点赞（uk_post_like）
- ✅ 防重复拉黑（uk_blacklist）

### 5. 数据安全考虑
- ✅ 敏感字段说明（password_hash/push_url/stream_key）
- ✅ 软删除机制（支持GDPR删除）
- ✅ 操作日志记录（operation_log）
- ✅ 审核工单流程（report -> audit_task）

### 6. 扩展性预留
- ✅ 可见性设计（visibility字段4级）
- ✅ OAuth多平台支持（oauth_provider字符串）
- ✅ 支付多渠道支持（payment_channel枚举）
- ✅ 举报多类型支持（report_type/target_type组合）

---

## ✅ 验证清单（全部通过）

- [x] 所有表字段命名遵循蛇形snake_case
- [x] 所有数值类型选用恰当范围(TINYINT/INT/BIGINT/DECIMAL)
- [x] 时间字段统一使用DATETIME，MySQL8.x默认精度
- [x] 字符串字段VARCHAR长度合理(phone=20, email=100, nickname=32)
- [x] 外键关系清晰(user_id FK to user.id等，MVP不强制外键约束)
- [x] 复合索引顺序符合最左前缀原则
- [x] 软删除查询规范：WHERE deleted = 0 AND ... (文档已说明)
- [x] SQL脚本可直接在MySQL 8.0+执行，无语法错误

---

## 📖 使用指南

### 快速部署（3步）

```bash
# 1. 创建数据库
mysql -u root -p -e "CREATE DATABASE douyin_live CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"

# 2. 执行初始化脚本
mysql -u root -p douyin_live < db/初始化脚本.sql

# 3. 验证表结构
mysql -u root -p douyin_live < db/validate_schema.sql
```

### 后端集成（MyBatis代码生成）

1. 配置 `we-code-generator/generatorConfig.xml`
2. 执行 `mvn mybatis-generator:generate`
3. 生成Entity/Mapper/XML文件到对应模块

### 后台前端集成（TypeScript接口）

根据表设计文档的字段清单定义接口：

```typescript
// src/types/user.ts
export interface User {
  id: number;
  phone?: string;
  email?: string;
  nickname: string;
  avatar?: string;
  status: 0 | 1 | 2; // 0=正常,1=封禁,2=注销
  createTime: string;
  updateTime: string;
}
```

### WebSocket消息协议

参考 `live_message` 表结构定义消息格式：

```json
{
  "sessionId": 12345,
  "roomId": 1001,
  "senderId": 88888,
  "messageContent": "主播你好！",
  "msgType": 1,
  "createTime": "2024-01-15 20:30:00"
}
```

---

## 🔗 后续任务交接

本数据库设计可无缝对接以下任务：

### Task 2: 后端项目依赖
- 使用 `初始化脚本.sql` 创建数据库
- 使用 `we-code-generator` 生成MyBatis代码
- 参考文档的字段注释编写业务逻辑

### Task 4: WebSocket消息协议
- 参考 `live_message` 表的msg_type枚举
- 礼物消息格式：`{"gift_id":1,"count":5}`
- 系统消息格式：进房/关注/分享等

### Task 7: 后台前端表格模型
- 参考文档的字段清单定义TypeScript Interface
- status/visibility等枚举值定义前端字典
- 表格排序字段对应数据库索引字段

### Task 9: 敏感词过滤
- 使用 `sensitive_word` 表加载词库
- 实现Trie树/DFA算法内存检测
- Redis缓存词库，定时同步DB

---

## 📈 性能指标（SLA）

根据文档附录B，关键查询性能目标：

| 查询场景 | 目标耗时 | 实现方式 |
|---------|---------|---------|
| 用户信息查询 | < 10ms | Redis缓存 + 主键索引 |
| 直播广场列表 | < 50ms | 复合索引 + Redis缓存 |
| 弹幕分页查询 | < 100ms | 复合索引 + 分表 + 限制查询范围 |
| 关注列表查询 | < 50ms | 复合索引 + Redis缓存 |
| 礼物排行榜 | < 50ms | Redis ZSet |
| 钱包余额查询 | < 10ms | Redis缓存 |

---

## 🚀 生产环境建议

### 1. 数据库配置优化
- innodb_buffer_pool_size = 服务器内存的70%
- max_connections = 1000
- 开启慢查询日志（long_query_time=2）
- 开启binlog（主从复制）

### 2. 分表分区执行
- `live_message`: 按room_id % 10 立即分表
- `post_like`: 按post_id % 10 立即分表
- `live_session`: 按月分区
- `gift_order`: 按月分区

### 3. Redis缓存部署
- 用户信息缓存（1小时TTL）
- 统计数据缓存（10分钟TTL，定时写回）
- 直播在线人数（实时更新）
- 敏感词库（永久缓存）

### 4. 数据归档策略
- live_message: 保留7天，超过7天归档到OSS
- post_like: 保留30天，历史数据归档
- gift_order: 保留3个月，季度归档
- live_session: 保留6个月，半年归档

### 5. 备份策略
- 每天凌晨2点全量备份
- 每小时增量备份（binlog）
- 备份文件保留30天
- 异地备份（至少2个可用区）

---

## 📝 测试账号

脚本自动创建4个测试账号（密码均为：`test123456`）：

| 手机号 | 邮箱 | 昵称 | 钱包余额 | 用途 |
|--------|------|------|---------|------|
| 13800138001 | admin@example.com | 系统管理员 | 100万金币 | 后台管理 |
| 13800138002 | user1@example.com | 测试用户1 | 1万金币 | 普通用户 |
| 13800138003 | user2@example.com | 测试用户2 | 5千金币 | 普通用户 |
| 13800138004 | streamer@example.com | 测试主播 | 10万金币 | 主播测试 |

---

## 📞 技术支持

如有疑问，请参考：
- 详细设计：`docs/数据库表设计文档.md`
- 快速上手：`db/README.md`
- 验证脚本：`db/validate_schema.sql`

---

**交付时间**: 2024-01-15  
**交付版本**: v1.0.0  
**数据库版本**: MySQL 8.0+  
**表数量**: 22张  
**文档行数**: 1500+行  
**质量状态**: ✅ 全部验证通过，可投入使用
