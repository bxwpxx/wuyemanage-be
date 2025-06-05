CREATE DATABASE PropertyManagement;
USE PropertyManagement;
-- 创建用户表
CREATE TABLE User (
    UserID VARCHAR(20) PRIMARY KEY,  -- 格式为：角色前缀 + 角色ID
    Username VARCHAR(50) NOT NULL,
    HashPassword VARCHAR(100) NOT NULL,  -- 修正字段名称为 HashPassword
    Email VARCHAR(100),
    Role VARCHAR(50) NOT NULL,
    PhoneNumber VARCHAR(20),  -- 添加手机号码字段
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP  -- 添加创建时间字段
);

-- 创建物业表
CREATE TABLE Property (
    PropertyID INT PRIMARY KEY AUTO_INCREMENT,
    UserID VARCHAR(20) NOT NULL  -- 不再是外键
);

-- 创建业主表
CREATE TABLE Owner (
    OwnerID INT PRIMARY KEY AUTO_INCREMENT,
    UserID VARCHAR(20) NOT NULL UNIQUE,  -- 不再是外键
    BuildingNumber INT NOT NULL,         -- 添加楼号字段
    DoorNumber INT NOT NULL              -- 添加门牌号字段
);
-- 插入 ADMIN 用户
INSERT INTO User (UserID, Username, HashPassword, Email, Role, PhoneNumber, CreatedAt)
VALUES (
    '0', 
    'admin', 
    'PBKDF2WithHmacSHA1$65536$PyY7ikkUDQHVPUTrk5Pacw==$OP2e1F9I9mqAfhcbBw1Ngi7BIvwDRVWq5tYe0LE0tWY=', 
    NULL, 
    'admin', 
    NULL, 
    '2025-05-30 05:37:02'
);

-- 插入 PROPERTY 用户
INSERT INTO User (UserID, Username, HashPassword, Email, Role, PhoneNumber, CreatedAt)
VALUES (
    'PRO1', 
    'pro1', 
    'PBKDF2WithHmacSHA1$65536$3EFP033LVMC8/QuIsBG+Vw==$/LOlXg7EZfFcrsjADKVwzYB4KDxyOdcmowoFr3luZ7o=', 
    'pr@', 
    'PROPERTY', 
    '1223165', 
    '2025-05-30 21:11:46'
);

-- 插入 OWNER 用户
INSERT INTO User (UserID, Username, HashPassword, Email, Role, PhoneNumber, CreatedAt)
VALUES (
    'OWN1', 
    'own1', 
    'PBKDF2WithHmacSHA1$65536$3EFP033LVMC8/QuIsBG+Vw==$/LOlXg7EZfFcrsjADKVwzYB4KDxyOdcmowoFr3luZ7o=', 
    'own@', 
    'OWNER', 
    '12231657', 
    '2025-05-30 21:19:53'
);
-- 插入 Property 表数据
INSERT INTO Property (PropertyID, UserID)
VALUES (1, 'PRO1');

-- 插入 Owner 表数据
INSERT INTO Owner (OwnerID, UserID, BuildingNumber, DoorNumber)
VALUES (1, 'OWN1', 2, 503);
