-- ========================================
-- 抖音直播风格App - MySQL数据库初始化脚本 (MVP版)
-- ========================================
-- 数据库版本: MySQL 8.0+
-- 字符集: utf8mb4 (支持emoji)
-- 排序规则: utf8mb4_unicode_ci
-- 存储引擎: InnoDB
-- 创建日期: 2024-01-15
-- ========================================

-- ========================================
-- 1. 用户体系模块（6张表）
-- ========================================

-- 1.1 用户基本表
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号(国际格式)',
  `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
  `password_hash` VARCHAR(255) DEFAULT NULL COMMENT '密码哈希(BCrypt)',
  `nickname` VARCHAR(32) NOT NULL DEFAULT '新用户' COMMENT '昵称',
  `avatar` VARCHAR(500) DEFAULT NULL COMMENT '头像URL',
  `status` TINYINT NOT NULL DEFAULT 0 COMMENT '账号状态:0=正常,1=封禁,2=注销',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '软删除:0=未删,1=已删',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_phone` (`phone`, `deleted`),
  UNIQUE KEY `uk_email` (`email`, `deleted`),
  KEY `idx_status` (`status`, `deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户基本表';

-- 1.2 多登录方式表
DROP TABLE IF EXISTS `user_auth`;
CREATE TABLE `user_auth` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `auth_type` TINYINT NOT NULL COMMENT '认证类型:1=手机验证码,2=邮箱验证码,3=密码,4=第三方',
  `auth_id` VARCHAR(100) NOT NULL COMMENT '认证标识(手机号/邮箱/OAuth ID)',
  `auth_token` VARCHAR(500) DEFAULT NULL COMMENT '认证令牌(OAuth Token)',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '软删除',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_auth` (`auth_type`, `auth_id`, `deleted`),
  KEY `idx_user_id` (`user_id`, `deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='多登录方式表';

-- 1.3 用户资料扩展表
DROP TABLE IF EXISTS `user_profile`;
CREATE TABLE `user_profile` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `gender` TINYINT DEFAULT 0 COMMENT '性别:0=未知,1=男,2=女',
  `birthday` DATE DEFAULT NULL COMMENT '生日',
  `location` VARCHAR(100) DEFAULT NULL COMMENT '地理位置',
  `bio` VARCHAR(255) DEFAULT NULL COMMENT '个人简介',
  `signature` VARCHAR(255) DEFAULT NULL COMMENT '个性签名',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '软删除',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_id` (`user_id`, `deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户资料扩展表';

-- 1.4 第三方绑定表
DROP TABLE IF EXISTS `user_oauth_bind`;
CREATE TABLE `user_oauth_bind` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `oauth_provider` VARCHAR(20) NOT NULL COMMENT 'OAuth提供商:wechat/qq/weibo/apple',
  `oauth_id` VARCHAR(100) NOT NULL COMMENT '第三方用户ID(openid)',
  `unionid` VARCHAR(100) DEFAULT NULL COMMENT '统一用户标识(微信unionid)',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '软删除',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_oauth` (`oauth_provider`, `oauth_id`, `deleted`),
  KEY `idx_user_id` (`user_id`, `deleted`),
  KEY `idx_unionid` (`unionid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='第三方绑定表';

-- 1.5 设备管理表
DROP TABLE IF EXISTS `user_device`;
CREATE TABLE `user_device` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `device_id` VARCHAR(100) NOT NULL COMMENT '设备唯一标识(UDID/IMEI)',
  `device_name` VARCHAR(100) DEFAULT NULL COMMENT '设备名称(iPhone 15 Pro)',
  `os` VARCHAR(20) DEFAULT NULL COMMENT '操作系统:ios/android/web',
  `last_login_time` DATETIME DEFAULT NULL COMMENT '最后登录时间',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '软删除',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_device` (`user_id`, `device_id`, `deleted`),
  KEY `idx_user_id` (`user_id`, `last_login_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='设备管理表';

-- 1.6 黑名单表
DROP TABLE IF EXISTS `user_blacklist`;
CREATE TABLE `user_blacklist` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` BIGINT NOT NULL COMMENT '拉黑者ID',
  `blocked_user_id` BIGINT NOT NULL COMMENT '被拉黑用户ID',
  `reason` VARCHAR(255) DEFAULT NULL COMMENT '拉黑原因',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态:1=生效,0=已移除',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '软删除',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_blacklist` (`user_id`, `blocked_user_id`, `deleted`),
  KEY `idx_blocked_user_id` (`blocked_user_id`, `status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='黑名单表';

-- ========================================
-- 2. 社交关系模块（1张表）
-- ========================================

-- 2.1 关注表
DROP TABLE IF EXISTS `follow_relation`;
CREATE TABLE `follow_relation` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
  `follower_id` BIGINT NOT NULL COMMENT '关注者ID',
  `following_id` BIGINT NOT NULL COMMENT '被关注者ID',
  `mutual` TINYINT NOT NULL DEFAULT 0 COMMENT '是否互关:0=否,1=是',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态:1=正常,0=已取消',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '软删除',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_follow_pair` (`follower_id`, `following_id`, `deleted`),
  KEY `idx_follower_id` (`follower_id`, `status`, `deleted`),
  KEY `idx_following_id` (`following_id`, `status`, `deleted`),
  KEY `idx_mutual` (`following_id`, `mutual`, `status`, `deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='关注表';

-- ========================================
-- 3. 直播模块（3张表）
-- ========================================

-- 3.1 直播间表
DROP TABLE IF EXISTS `live_room`;
CREATE TABLE `live_room` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '直播间ID',
  `creator_id` BIGINT NOT NULL COMMENT '主播用户ID',
  `title` VARCHAR(100) NOT NULL DEFAULT '直播间' COMMENT '直播间标题',
  `description` VARCHAR(500) DEFAULT NULL COMMENT '直播间简介',
  `category` VARCHAR(50) DEFAULT NULL COMMENT '分类:才艺/聊天/游戏/户外...',
  `room_status` TINYINT NOT NULL DEFAULT 0 COMMENT '直播状态:0=待开播,1=直播中,2=已关闭',
  `visibility` TINYINT NOT NULL DEFAULT 0 COMMENT '可见性:0=公开,1=仅自己,2=好友可见,3=指定名单',
  `cover_url` VARCHAR(500) DEFAULT NULL COMMENT '封面图URL',
  `push_url` VARCHAR(500) DEFAULT NULL COMMENT '推流地址(敏感)',
  `play_url` VARCHAR(500) DEFAULT NULL COMMENT '拉流地址(HTTP-FLV/HLS)',
  `stream_key` VARCHAR(100) DEFAULT NULL COMMENT '流密钥',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '软删除',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_creator_id` (`creator_id`, `deleted`),
  KEY `idx_room_status` (`room_status`, `visibility`, `deleted`, `create_time`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='直播间表';

-- 3.2 直播场次表
DROP TABLE IF EXISTS `live_session`;
CREATE TABLE `live_session` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '场次ID',
  `room_id` BIGINT NOT NULL COMMENT '直播间ID',
  `creator_id` BIGINT NOT NULL COMMENT '主播ID',
  `start_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开播时间',
  `end_time` DATETIME DEFAULT NULL COMMENT '下播时间',
  `duration_sec` INT DEFAULT 0 COMMENT '时长(秒)',
  `peak_online` INT NOT NULL DEFAULT 0 COMMENT '峰值在线人数',
  `total_like` BIGINT NOT NULL DEFAULT 0 COMMENT '总点赞数',
  `total_gift_amount` DECIMAL(18,2) NOT NULL DEFAULT 0.00 COMMENT '总礼物金额(金币)',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态:1=直播中,2=已结束,3=异常中断',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '软删除',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_room_id` (`room_id`, `start_time`),
  KEY `idx_creator_id` (`creator_id`, `start_time`),
  KEY `idx_start_time` (`start_time`, `status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='直播场次表';

-- 3.3 弹幕消息表
DROP TABLE IF EXISTS `live_message`;
CREATE TABLE `live_message` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '消息ID',
  `session_id` BIGINT NOT NULL COMMENT '直播场次ID',
  `room_id` BIGINT NOT NULL COMMENT '直播间ID(冗余便于查询)',
  `sender_id` BIGINT NOT NULL COMMENT '发送者ID(系统消息=0)',
  `message_content` VARCHAR(500) NOT NULL COMMENT '消息内容',
  `msg_type` TINYINT NOT NULL DEFAULT 1 COMMENT '消息类型:1=普通,2=系统,3=礼物,4=进房',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态:1=正常,2=已撤回,3=违规删除',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '软删除',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_session_room_time` (`session_id`, `room_id`, `create_time`),
  KEY `idx_sender_id` (`sender_id`, `create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='弹幕消息表';

-- ========================================
-- 4. 内容模块（4张表）
-- ========================================

-- 4.1 内容发布表
DROP TABLE IF EXISTS `post`;
CREATE TABLE `post` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '内容ID',
  `creator_id` BIGINT NOT NULL COMMENT '创建者ID',
  `content_text` TEXT DEFAULT NULL COMMENT '内容文本描述',
  `visibility` TINYINT NOT NULL DEFAULT 0 COMMENT '可见性:0=公开,1=仅自己,2=好友可见,3=私密',
  `allow_comment` TINYINT NOT NULL DEFAULT 1 COMMENT '允许评论:0=否,1=是',
  `allow_download` TINYINT NOT NULL DEFAULT 0 COMMENT '允许下载:0=否,1=是',
  `location` VARCHAR(100) DEFAULT NULL COMMENT '地理位置',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '软删除',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_creator_id` (`creator_id`, `visibility`, `deleted`, `create_time`),
  KEY `idx_visibility_time` (`visibility`, `deleted`, `create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='内容发布表';

-- 4.2 内容媒体表
DROP TABLE IF EXISTS `post_media`;
CREATE TABLE `post_media` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
  `post_id` BIGINT NOT NULL COMMENT '内容ID',
  `media_type` TINYINT NOT NULL COMMENT '媒体类型:1=图片,2=视频',
  `media_url` VARCHAR(500) NOT NULL COMMENT '媒体文件URL(OSS)',
  `duration_sec` INT DEFAULT NULL COMMENT '视频时长(秒)',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '软删除',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_post_id` (`post_id`, `deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='内容媒体表';

-- 4.3 点赞表
DROP TABLE IF EXISTS `post_like`;
CREATE TABLE `post_like` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
  `post_id` BIGINT NOT NULL COMMENT '内容ID',
  `liker_id` BIGINT NOT NULL COMMENT '点赞者ID',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '软删除',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_post_like` (`post_id`, `liker_id`, `deleted`),
  KEY `idx_liker_id` (`liker_id`, `deleted`, `create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='点赞表';

-- 4.4 评论表
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '评论ID',
  `post_id` BIGINT NOT NULL COMMENT '内容ID',
  `commenter_id` BIGINT NOT NULL COMMENT '评论者ID',
  `parent_id` BIGINT DEFAULT 0 COMMENT '父评论ID(0=一级评论)',
  `reply_to_id` BIGINT DEFAULT 0 COMMENT '回复的用户ID',
  `comment_text` VARCHAR(500) NOT NULL COMMENT '评论内容',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '软删除',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_post_id` (`post_id`, `parent_id`, `deleted`, `create_time`),
  KEY `idx_commenter_id` (`commenter_id`, `deleted`, `create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='评论表';

-- ========================================
-- 5. 礼物与钱包模块（4张表）
-- ========================================

-- 5.1 礼物商品表
DROP TABLE IF EXISTS `gift`;
CREATE TABLE `gift` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '礼物ID',
  `gift_name` VARCHAR(50) NOT NULL COMMENT '礼物名称',
  `gift_icon_url` VARCHAR(500) NOT NULL COMMENT '礼物图标URL',
  `coin_price` INT NOT NULL COMMENT '金币价格',
  `combo_multiplier` DECIMAL(3,2) NOT NULL DEFAULT 1.00 COMMENT '连击倍数(1.00-9.99)',
  `special_effect_url` VARCHAR(500) DEFAULT NULL COMMENT '特效动画URL(Lottie/MP4)',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态:1=上架,0=下架',
  `rank` INT NOT NULL DEFAULT 0 COMMENT '排序值(越大越靠前)',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '软删除',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_status_rank` (`status`, `deleted`, `rank`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='礼物商品表';

-- 5.2 礼物订单表
DROP TABLE IF EXISTS `gift_order`;
CREATE TABLE `gift_order` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `session_id` BIGINT NOT NULL COMMENT '直播场次ID',
  `room_id` BIGINT NOT NULL COMMENT '直播间ID(冗余)',
  `sender_id` BIGINT NOT NULL COMMENT '送礼者ID',
  `receiver_id` BIGINT NOT NULL COMMENT '主播ID',
  `gift_id` BIGINT NOT NULL COMMENT '礼物ID',
  `amount` INT NOT NULL COMMENT '金币数量',
  `combo_count` INT NOT NULL DEFAULT 1 COMMENT '连击次数',
  `order_no` VARCHAR(64) NOT NULL COMMENT '订单号(唯一)',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态:0=待确认,1=已确认,2=已取消',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '软删除',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`),
  KEY `idx_session_id` (`session_id`, `create_time`),
  KEY `idx_sender_id` (`sender_id`, `create_time`),
  KEY `idx_receiver_id` (`receiver_id`, `create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='礼物订单表';

-- 5.3 钱包表
DROP TABLE IF EXISTS `wallet_account`;
CREATE TABLE `wallet_account` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `balance` BIGINT NOT NULL DEFAULT 0 COMMENT '可用余额(金币)',
  `frozen_amount` BIGINT NOT NULL DEFAULT 0 COMMENT '冻结金额(金币)',
  `total_recharge` BIGINT NOT NULL DEFAULT 0 COMMENT '累计充值(金币)',
  `total_consume` BIGINT NOT NULL DEFAULT 0 COMMENT '累计消费(金币)',
  `version` INT NOT NULL DEFAULT 0 COMMENT '版本号(乐观锁)',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '软删除',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_id` (`user_id`, `deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='钱包表';

-- 5.4 充值订单表
DROP TABLE IF EXISTS `recharge_order`;
CREATE TABLE `recharge_order` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `amount` DECIMAL(10,2) NOT NULL COMMENT '充值金额(人民币元)',
  `coin_amount` BIGINT NOT NULL COMMENT '赠送金币数量',
  `payment_channel` TINYINT NOT NULL COMMENT '支付渠道:1=支付宝,2=微信,3=苹果IAP',
  `pay_order_id` VARCHAR(64) DEFAULT NULL COMMENT '第三方支付订单号',
  `order_no` VARCHAR(64) NOT NULL COMMENT '订单号(唯一)',
  `status` TINYINT NOT NULL DEFAULT 0 COMMENT '状态:0=待支付,1=已支付,2=已确认,3=已关闭',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '软删除',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`),
  KEY `idx_user_id` (`user_id`, `create_time`),
  KEY `idx_status` (`status`, `create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='充值订单表';

-- ========================================
-- 6. 审核与举报模块（2张表）
-- ========================================

-- 6.1 举报表
DROP TABLE IF EXISTS `report`;
CREATE TABLE `report` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '举报ID',
  `reporter_id` BIGINT NOT NULL COMMENT '举报人ID',
  `report_type` TINYINT NOT NULL COMMENT '举报类型:1=涉黄,2=涉政,3=广告,4=辱骂,5=诈骗,6=侵权,7=其他',
  `target_type` TINYINT NOT NULL COMMENT '目标类型:1=用户,2=直播间,3=内容,4=评论',
  `target_id` BIGINT NOT NULL COMMENT '目标ID',
  `reason` VARCHAR(500) DEFAULT NULL COMMENT '举报原因',
  `evidence_url` VARCHAR(1000) DEFAULT NULL COMMENT '证据截图URL(多个用逗号分隔)',
  `status` TINYINT NOT NULL DEFAULT 0 COMMENT '状态:0=待审核,1=已通过,2=已驳回,3=已处罚',
  `handled_by` BIGINT DEFAULT NULL COMMENT '处理人ID(审核员)',
  `result` VARCHAR(500) DEFAULT NULL COMMENT '处理结果说明',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '软删除',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_reporter_id` (`reporter_id`, `create_time`),
  KEY `idx_report_type` (`report_type`, `status`, `deleted`),
  KEY `idx_target` (`target_type`, `target_id`, `deleted`),
  KEY `idx_status_time` (`status`, `create_time`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='举报表';

-- 6.2 审核工单表
DROP TABLE IF EXISTS `audit_task`;
CREATE TABLE `audit_task` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '工单ID',
  `report_id` BIGINT NOT NULL COMMENT '举报ID',
  `auditor_id` BIGINT NOT NULL COMMENT '审核员ID',
  `audit_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '审核时间',
  `result` TINYINT NOT NULL COMMENT '审核结果:1=通过,2=驳回',
  `reason` VARCHAR(500) DEFAULT NULL COMMENT '审核理由',
  `punishment` TINYINT NOT NULL DEFAULT 0 COMMENT '处罚措施:0=无,1=删除,2=禁言7天,3=封禁账号,4=限流',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '软删除',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_report_id` (`report_id`, `deleted`),
  KEY `idx_auditor_id` (`auditor_id`, `audit_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='审核工单表';

-- ========================================
-- 7. 配置与日志模块（2张表）
-- ========================================

-- 7.1 敏感词表
DROP TABLE IF EXISTS `sensitive_word`;
CREATE TABLE `sensitive_word` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
  `word` VARCHAR(50) NOT NULL COMMENT '敏感词',
  `category` VARCHAR(20) NOT NULL COMMENT '分类:politics/porn/abuse/ad/fraud',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态:1=启用,0=禁用',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '软删除',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_word` (`word`, `deleted`),
  KEY `idx_category` (`category`, `status`, `deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='敏感词表';

-- 7.2 操作日志表
DROP TABLE IF EXISTS `operation_log`;
CREATE TABLE `operation_log` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `operator_id` BIGINT NOT NULL COMMENT '操作人ID',
  `operation_type` VARCHAR(50) NOT NULL COMMENT '操作类型:CREATE/UPDATE/DELETE/LOGIN/EXPORT',
  `table_name` VARCHAR(50) DEFAULT NULL COMMENT '操作表名',
  `target_id` BIGINT DEFAULT NULL COMMENT '目标记录ID',
  `old_data` TEXT DEFAULT NULL COMMENT '修改前数据(JSON)',
  `new_data` TEXT DEFAULT NULL COMMENT '修改后数据(JSON)',
  `ip` VARCHAR(50) DEFAULT NULL COMMENT '操作IP',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '软删除',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_operator_id` (`operator_id`, `create_time`),
  KEY `idx_operation_type` (`operation_type`, `create_time`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='操作日志表';

-- ========================================
-- 8. 初始化数据
-- ========================================

-- 8.1 初始化礼物商品
INSERT INTO `gift` (`gift_name`, `gift_icon_url`, `coin_price`, `combo_multiplier`, `special_effect_url`, `status`, `rank`) VALUES
('玫瑰', 'https://cdn.example.com/gift/rose.png', 1, 1.00, NULL, 1, 100),
('棒棒糖', 'https://cdn.example.com/gift/lollipop.png', 5, 1.20, NULL, 1, 90),
('巧克力', 'https://cdn.example.com/gift/chocolate.png', 10, 1.50, NULL, 1, 80),
('咖啡', 'https://cdn.example.com/gift/coffee.png', 20, 1.80, NULL, 1, 70),
('蛋糕', 'https://cdn.example.com/gift/cake.png', 50, 2.00, NULL, 1, 60),
('香水', 'https://cdn.example.com/gift/perfume.png', 100, 2.50, 'https://cdn.example.com/effect/perfume.json', 1, 50),
('口红', 'https://cdn.example.com/gift/lipstick.png', 200, 3.00, 'https://cdn.example.com/effect/lipstick.json', 1, 40),
('手表', 'https://cdn.example.com/gift/watch.png', 500, 4.00, 'https://cdn.example.com/effect/watch.json', 1, 30),
('手机', 'https://cdn.example.com/gift/phone.png', 1000, 5.00, 'https://cdn.example.com/effect/phone.json', 1, 20),
('跑车', 'https://cdn.example.com/gift/car.png', 5000, 8.00, 'https://cdn.example.com/effect/car.mp4', 1, 10),
('游艇', 'https://cdn.example.com/gift/yacht.png', 10000, 9.99, 'https://cdn.example.com/effect/yacht.mp4', 1, 5),
('城堡', 'https://cdn.example.com/gift/castle.png', 50000, 9.99, 'https://cdn.example.com/effect/castle.mp4', 1, 1);

-- 8.2 初始化敏感词库（示例，实际应导入完整词库）
INSERT INTO `sensitive_word` (`word`, `category`, `status`) VALUES
-- 政治类
('习近平', 'politics', 1),
('江泽民', 'politics', 1),
('胡锦涛', 'politics', 1),
('温家宝', 'politics', 1),
('共产党', 'politics', 1),
('法轮功', 'politics', 1),
('六四', 'politics', 1),
('天安门', 'politics', 1),
('台独', 'politics', 1),
('藏独', 'politics', 1),

-- 色情类
('色情', 'porn', 1),
('黄色', 'porn', 1),
('裸体', 'porn', 1),
('性交', 'porn', 1),
('做爱', 'porn', 1),
('A片', 'porn', 1),
('成人电影', 'porn', 1),
('约炮', 'porn', 1),
('援交', 'porn', 1),
('卖淫', 'porn', 1),

-- 辱骂类
('傻逼', 'abuse', 1),
('傻逼', 'abuse', 1),
('智障', 'abuse', 1),
('脑残', 'abuse', 1),
('煞笔', 'abuse', 1),
('你妈', 'abuse', 1),
('草泥马', 'abuse', 1),
('日你', 'abuse', 1),
('滚蛋', 'abuse', 1),
('去死', 'abuse', 1),

-- 广告类
('微信号', 'ad', 1),
('加微信', 'ad', 1),
('QQ号', 'ad', 1),
('加QQ', 'ad', 1),
('淘宝店', 'ad', 1),
('代购', 'ad', 1),
('刷单', 'ad', 1),
('推广', 'ad', 1),
('引流', 'ad', 1),
('私聊', 'ad', 1),

-- 诈骗类
('贷款', 'fraud', 1),
('无抵押', 'fraud', 1),
('信用卡套现', 'fraud', 1),
('刷信誉', 'fraud', 1),
('兼职', 'fraud', 1),
('返利', 'fraud', 1),
('投资理财', 'fraud', 1),
('高收益', 'fraud', 1),
('稳赚不赔', 'fraud', 1),
('中奖', 'fraud', 1);

-- 8.3 初始化测试用户（密码均为: test123456, BCrypt哈希）
INSERT INTO `user` (`phone`, `email`, `password_hash`, `nickname`, `avatar`, `status`) VALUES
('13800138001', 'admin@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', '系统管理员', 'https://cdn.example.com/avatar/admin.png', 0),
('13800138002', 'user1@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', '测试用户1', 'https://cdn.example.com/avatar/user1.png', 0),
('13800138003', 'user2@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', '测试用户2', 'https://cdn.example.com/avatar/user2.png', 0),
('13800138004', 'streamer@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', '测试主播', 'https://cdn.example.com/avatar/streamer.png', 0);

-- 8.4 为测试用户初始化资料
INSERT INTO `user_profile` (`user_id`, `gender`, `birthday`, `location`, `bio`, `signature`) VALUES
(1, 1, '1990-01-01', '北京市', '系统管理员账号', '让世界变得更美好'),
(2, 1, '1995-05-15', '上海市', '这是测试用户1的个人简介', '做最好的自己'),
(3, 2, '1998-08-20', '深圳市', '这是测试用户2的个人简介', '生活需要仪式感'),
(4, 2, '1996-03-10', '杭州市', '欢迎来到我的直播间', '每天晚上8点开播，不见不散！');

-- 8.5 为测试用户初始化钱包
INSERT INTO `wallet_account` (`user_id`, `balance`, `frozen_amount`, `total_recharge`, `total_consume`) VALUES
(1, 1000000, 0, 1000000, 0),
(2, 10000, 0, 10000, 0),
(3, 5000, 0, 5000, 0),
(4, 100000, 0, 100000, 0);

-- 8.6 初始化测试直播间
INSERT INTO `live_room` (`creator_id`, `title`, `description`, `category`, `room_status`, `visibility`, `cover_url`, `push_url`, `play_url`, `stream_key`) VALUES
(4, '测试主播的直播间', '欢迎来到我的直播间，每天晚上8点开播！', '聊天', 0, 0, 'https://cdn.example.com/cover/room1.jpg', 
'rtmp://push.example.com/live/stream_key_001?auth=xxxx', 
'https://play.example.com/live/stream_key_001.flv', 
'stream_key_001');

-- ========================================
-- 9. 查询验证
-- ========================================

-- 9.1 验证表创建
SELECT 
    TABLE_NAME AS '表名',
    TABLE_COMMENT AS '表注释',
    TABLE_ROWS AS '预估行数',
    ROUND(DATA_LENGTH/1024/1024, 2) AS '数据大小(MB)',
    ROUND(INDEX_LENGTH/1024/1024, 2) AS '索引大小(MB)',
    ROUND((DATA_LENGTH+INDEX_LENGTH)/1024/1024, 2) AS '总大小(MB)'
FROM information_schema.TABLES
WHERE TABLE_SCHEMA = DATABASE()
ORDER BY TABLE_NAME;

-- 9.2 验证索引创建
SELECT 
    TABLE_NAME AS '表名',
    INDEX_NAME AS '索引名',
    COLUMN_NAME AS '列名',
    SEQ_IN_INDEX AS '顺序',
    NON_UNIQUE AS '是否唯一'
FROM information_schema.STATISTICS
WHERE TABLE_SCHEMA = DATABASE()
ORDER BY TABLE_NAME, INDEX_NAME, SEQ_IN_INDEX;

-- 9.3 验证初始化数据
SELECT '礼物商品数量' AS 'ITEM', COUNT(*) AS 'COUNT' FROM gift WHERE deleted = 0
UNION ALL
SELECT '敏感词数量', COUNT(*) FROM sensitive_word WHERE deleted = 0
UNION ALL
SELECT '测试用户数量', COUNT(*) FROM user WHERE deleted = 0
UNION ALL
SELECT '直播间数量', COUNT(*) FROM live_room WHERE deleted = 0;

-- ========================================
-- 执行完成提示
-- ========================================
SELECT '========================================' AS '';
SELECT '数据库初始化完成！' AS 'STATUS';
SELECT '========================================' AS '';
SELECT '表数量' AS 'METRIC', COUNT(*) AS 'VALUE' 
FROM information_schema.TABLES 
WHERE TABLE_SCHEMA = DATABASE()
UNION ALL
SELECT '测试账号', '4个（密码均为test123456）';
SELECT '========================================' AS '';
