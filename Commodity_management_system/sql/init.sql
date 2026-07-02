-- ========================================
-- 商品管理系统 - 数据库初始化脚本
-- ========================================

-- 创建数据库（如果不存在）
CREATE DATABASE IF NOT EXISTS commodity_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE commodity_db;

-- ========================================
-- 1. 用户表（登录用）
-- ========================================
DROP TABLE IF EXISTS tb_user;
CREATE TABLE tb_user (
    id          INT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    username    VARCHAR(50)  NOT NULL UNIQUE   COMMENT '用户名',
    password    VARCHAR(100) NOT NULL           COMMENT '密码',
    avatar      VARCHAR(200) DEFAULT NULL       COMMENT '头像路径',
    create_time DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 插入默认管理员账号（密码: 123456）
INSERT INTO tb_user (username, password) VALUES ('admin', '123456');

-- ========================================
-- 2. 商品表（核心业务表）
-- ========================================
DROP TABLE IF EXISTS tb_product;
CREATE TABLE tb_product (
    id          INT PRIMARY KEY AUTO_INCREMENT COMMENT '商品ID',
    name        VARCHAR(100)   NOT NULL         COMMENT '商品名称',
    category    VARCHAR(50)    DEFAULT NULL     COMMENT '商品分类',
    price       DECIMAL(10,2)  NOT NULL         COMMENT '价格',
    stock       INT            DEFAULT 0        COMMENT '库存数量',
    image       VARCHAR(200)   DEFAULT NULL     COMMENT '商品图片路径',
    description TEXT           DEFAULT NULL     COMMENT '商品描述',
    create_time DATETIME       DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

-- 插入测试商品数据
INSERT INTO tb_product (name, category, price, stock, description) VALUES
('iPhone 15',      '电子产品', 5999.00,  100, '苹果最新款手机'),
('MacBook Pro',    '电子产品', 12999.00,  50, '苹果笔记本电脑'),
('Nike 运动鞋',    '服装鞋帽', 699.00,   200, '春季新款跑步鞋'),
('华为 MatePad',   '电子产品', 3499.00,   80, '华为平板电脑'),
('三只松鼠坚果礼盒','食品饮料', 99.00,    500, '零食大礼包');
