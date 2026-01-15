-- ========================================
-- 数据库表结构验证脚本
-- ========================================
-- 用途：验证初始化脚本是否成功执行
-- 使用：mysql -u root -p douyin_live < validate_schema.sql
-- ========================================

SET NAMES utf8mb4;

-- ========================================
-- 1. 验证表是否全部创建
-- ========================================
SELECT '========================================' AS '';
SELECT '1. 表创建验证' AS 'CHECK';
SELECT '========================================' AS '';

SELECT 
    CASE 
        WHEN COUNT(*) = 22 THEN 'PASS'
        ELSE 'FAIL'
    END AS 'RESULT',
    COUNT(*) AS '实际表数量',
    22 AS '期望表数量'
FROM information_schema.TABLES
WHERE TABLE_SCHEMA = DATABASE();

-- 列出所有表
SELECT 
    TABLE_NAME AS '表名',
    TABLE_COMMENT AS '表注释',
    TABLE_ROWS AS '预估行数'
FROM information_schema.TABLES
WHERE TABLE_SCHEMA = DATABASE()
ORDER BY TABLE_NAME;

-- ========================================
-- 2. 验证主键约束
-- ========================================
SELECT '========================================' AS '';
SELECT '2. 主键约束验证' AS 'CHECK';
SELECT '========================================' AS '';

SELECT 
    CASE 
        WHEN COUNT(*) = 22 THEN 'PASS'
        ELSE 'FAIL'
    END AS 'RESULT',
    COUNT(*) AS '实际主键数量',
    22 AS '期望主键数量'
FROM information_schema.TABLE_CONSTRAINTS
WHERE TABLE_SCHEMA = DATABASE()
AND CONSTRAINT_TYPE = 'PRIMARY KEY';

-- 列出所有主键
SELECT 
    TABLE_NAME AS '表名',
    CONSTRAINT_NAME AS '约束名'
FROM information_schema.TABLE_CONSTRAINTS
WHERE TABLE_SCHEMA = DATABASE()
AND CONSTRAINT_TYPE = 'PRIMARY KEY'
ORDER BY TABLE_NAME;

-- ========================================
-- 3. 验证唯一索引
-- ========================================
SELECT '========================================' AS '';
SELECT '3. 唯一索引验证' AS 'CHECK';
SELECT '========================================' AS '';

-- 期望的唯一索引数量：uk_phone, uk_email, uk_auth, uk_user_id(user_profile), 
-- uk_oauth, uk_device, uk_blacklist, uk_follow_pair, uk_post_like, 
-- uk_order_no(gift_order), uk_user_id(wallet_account), uk_order_no(recharge_order), 
-- uk_word
SELECT 
    CASE 
        WHEN COUNT(*) >= 13 THEN 'PASS'
        ELSE 'FAIL'
    END AS 'RESULT',
    COUNT(*) AS '实际唯一索引数量',
    '>=13' AS '期望唯一索引数量'
FROM information_schema.TABLE_CONSTRAINTS
WHERE TABLE_SCHEMA = DATABASE()
AND CONSTRAINT_TYPE = 'UNIQUE';

-- 列出所有唯一索引
SELECT 
    TABLE_NAME AS '表名',
    CONSTRAINT_NAME AS '唯一索引名'
FROM information_schema.TABLE_CONSTRAINTS
WHERE TABLE_SCHEMA = DATABASE()
AND CONSTRAINT_TYPE = 'UNIQUE'
ORDER BY TABLE_NAME, CONSTRAINT_NAME;

-- ========================================
-- 4. 验证普通索引
-- ========================================
SELECT '========================================' AS '';
SELECT '4. 普通索引验证' AS 'CHECK';
SELECT '========================================' AS '';

SELECT 
    COUNT(DISTINCT CONCAT(TABLE_NAME, '.', INDEX_NAME)) AS '普通索引数量'
FROM information_schema.STATISTICS
WHERE TABLE_SCHEMA = DATABASE()
AND INDEX_NAME != 'PRIMARY'
AND NON_UNIQUE = 1;

-- 列出所有普通索引（按表分组）
SELECT 
    TABLE_NAME AS '表名',
    INDEX_NAME AS '索引名',
    GROUP_CONCAT(COLUMN_NAME ORDER BY SEQ_IN_INDEX SEPARATOR ', ') AS '索引列'
FROM information_schema.STATISTICS
WHERE TABLE_SCHEMA = DATABASE()
AND INDEX_NAME != 'PRIMARY'
AND NON_UNIQUE = 1
GROUP BY TABLE_NAME, INDEX_NAME
ORDER BY TABLE_NAME, INDEX_NAME;

-- ========================================
-- 5. 验证字段类型
-- ========================================
SELECT '========================================' AS '';
SELECT '5. 关键字段类型验证' AS 'CHECK';
SELECT '========================================' AS '';

-- 验证id字段统一使用BIGINT
SELECT 
    CASE 
        WHEN COUNT(*) = 0 THEN 'PASS'
        ELSE 'FAIL'
    END AS 'RESULT',
    '所有表的id字段必须是BIGINT类型' AS '检查项',
    COUNT(*) AS '不符合数量'
FROM information_schema.COLUMNS
WHERE TABLE_SCHEMA = DATABASE()
AND COLUMN_NAME = 'id'
AND DATA_TYPE != 'bigint';

-- 验证deleted字段统一使用TINYINT
SELECT 
    CASE 
        WHEN COUNT(*) = 0 THEN 'PASS'
        ELSE 'FAIL'
    END AS 'RESULT',
    '所有表的deleted字段必须是TINYINT类型' AS '检查项',
    COUNT(*) AS '不符合数量'
FROM information_schema.COLUMNS
WHERE TABLE_SCHEMA = DATABASE()
AND COLUMN_NAME = 'deleted'
AND DATA_TYPE != 'tinyint';

-- 验证create_time字段统一使用DATETIME
SELECT 
    CASE 
        WHEN COUNT(*) = 0 THEN 'PASS'
        ELSE 'FAIL'
    END AS 'RESULT',
    '所有表的create_time字段必须是DATETIME类型' AS '检查项',
    COUNT(*) AS '不符合数量'
FROM information_schema.COLUMNS
WHERE TABLE_SCHEMA = DATABASE()
AND COLUMN_NAME = 'create_time'
AND DATA_TYPE != 'datetime';

-- ========================================
-- 6. 验证必需字段
-- ========================================
SELECT '========================================' AS '';
SELECT '6. 必需字段验证' AS 'CHECK';
SELECT '========================================' AS '';

-- 所有表必须有：id, deleted, create_time
SELECT 
    TABLE_NAME AS '表名',
    CASE 
        WHEN SUM(CASE WHEN COLUMN_NAME = 'id' THEN 1 ELSE 0 END) = 0 THEN '缺少id'
        WHEN SUM(CASE WHEN COLUMN_NAME = 'deleted' THEN 1 ELSE 0 END) = 0 THEN '缺少deleted'
        WHEN SUM(CASE WHEN COLUMN_NAME = 'create_time' THEN 1 ELSE 0 END) = 0 THEN '缺少create_time'
        ELSE 'PASS'
    END AS 'RESULT'
FROM information_schema.COLUMNS
WHERE TABLE_SCHEMA = DATABASE()
GROUP BY TABLE_NAME
HAVING RESULT != 'PASS'
UNION ALL
SELECT 
    CASE 
        WHEN COUNT(DISTINCT TABLE_NAME) = 22 THEN '所有表'
        ELSE CONCAT(COUNT(DISTINCT TABLE_NAME), '张表')
    END,
    'PASS'
FROM information_schema.COLUMNS
WHERE TABLE_SCHEMA = DATABASE()
AND TABLE_NAME IN (
    SELECT TABLE_NAME 
    FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
    GROUP BY TABLE_NAME
    HAVING SUM(CASE WHEN COLUMN_NAME = 'id' THEN 1 ELSE 0 END) > 0
    AND SUM(CASE WHEN COLUMN_NAME = 'deleted' THEN 1 ELSE 0 END) > 0
    AND SUM(CASE WHEN COLUMN_NAME = 'create_time' THEN 1 ELSE 0 END) > 0
);

-- ========================================
-- 7. 验证初始化数据
-- ========================================
SELECT '========================================' AS '';
SELECT '7. 初始化数据验证' AS 'CHECK';
SELECT '========================================' AS '';

SELECT 
    '礼物商品' AS '数据类型',
    COUNT(*) AS '数量',
    CASE 
        WHEN COUNT(*) >= 10 THEN 'PASS'
        ELSE 'FAIL'
    END AS 'RESULT'
FROM gift WHERE deleted = 0
UNION ALL
SELECT 
    '敏感词',
    COUNT(*),
    CASE 
        WHEN COUNT(*) >= 30 THEN 'PASS'
        ELSE 'FAIL'
    END
FROM sensitive_word WHERE deleted = 0
UNION ALL
SELECT 
    '测试用户',
    COUNT(*),
    CASE 
        WHEN COUNT(*) >= 4 THEN 'PASS'
        ELSE 'FAIL'
    END
FROM user WHERE deleted = 0
UNION ALL
SELECT 
    '用户资料',
    COUNT(*),
    CASE 
        WHEN COUNT(*) >= 4 THEN 'PASS'
        ELSE 'FAIL'
    END
FROM user_profile WHERE deleted = 0
UNION ALL
SELECT 
    '钱包账户',
    COUNT(*),
    CASE 
        WHEN COUNT(*) >= 4 THEN 'PASS'
        ELSE 'FAIL'
    END
FROM wallet_account WHERE deleted = 0
UNION ALL
SELECT 
    '直播间',
    COUNT(*),
    CASE 
        WHEN COUNT(*) >= 1 THEN 'PASS'
        ELSE 'FAIL'
    END
FROM live_room WHERE deleted = 0;

-- ========================================
-- 8. 验证字符集和排序规则
-- ========================================
SELECT '========================================' AS '';
SELECT '8. 字符集和排序规则验证' AS 'CHECK';
SELECT '========================================' AS '';

SELECT 
    CASE 
        WHEN COUNT(*) = 0 THEN 'PASS'
        ELSE 'FAIL'
    END AS 'RESULT',
    '所有表必须使用utf8mb4字符集' AS '检查项',
    COUNT(*) AS '不符合数量'
FROM information_schema.TABLES
WHERE TABLE_SCHEMA = DATABASE()
AND TABLE_COLLATION NOT LIKE 'utf8mb4%';

-- 列出字符集不符合的表
SELECT 
    TABLE_NAME AS '表名',
    TABLE_COLLATION AS '排序规则'
FROM information_schema.TABLES
WHERE TABLE_SCHEMA = DATABASE()
AND TABLE_COLLATION NOT LIKE 'utf8mb4%';

-- ========================================
-- 9. 验证存储引擎
-- ========================================
SELECT '========================================' AS '';
SELECT '9. 存储引擎验证' AS 'CHECK';
SELECT '========================================' AS '';

SELECT 
    CASE 
        WHEN COUNT(*) = 0 THEN 'PASS'
        ELSE 'FAIL'
    END AS 'RESULT',
    '所有表必须使用InnoDB引擎' AS '检查项',
    COUNT(*) AS '不符合数量'
FROM information_schema.TABLES
WHERE TABLE_SCHEMA = DATABASE()
AND ENGINE != 'InnoDB';

-- 列出存储引擎不符合的表
SELECT 
    TABLE_NAME AS '表名',
    ENGINE AS '存储引擎'
FROM information_schema.TABLES
WHERE TABLE_SCHEMA = DATABASE()
AND ENGINE != 'InnoDB';

-- ========================================
-- 10. 数据库大小统计
-- ========================================
SELECT '========================================' AS '';
SELECT '10. 数据库大小统计' AS 'CHECK';
SELECT '========================================' AS '';

SELECT 
    ROUND(SUM(DATA_LENGTH)/1024/1024, 2) AS '数据大小(MB)',
    ROUND(SUM(INDEX_LENGTH)/1024/1024, 2) AS '索引大小(MB)',
    ROUND(SUM(DATA_LENGTH+INDEX_LENGTH)/1024/1024, 2) AS '总大小(MB)',
    COUNT(*) AS '表数量'
FROM information_schema.TABLES
WHERE TABLE_SCHEMA = DATABASE();

-- ========================================
-- 验证完成
-- ========================================
SELECT '========================================' AS '';
SELECT '验证完成！' AS 'STATUS';
SELECT '========================================' AS '';
SELECT 
    DATABASE() AS '数据库名',
    VERSION() AS 'MySQL版本',
    @@character_set_database AS '字符集',
    @@collation_database AS '排序规则',
    NOW() AS '验证时间';
SELECT '========================================' AS '';
