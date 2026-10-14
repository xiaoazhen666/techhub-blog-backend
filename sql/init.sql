-- TechHub 博客数据库初始化脚本
-- 使用方式：mysql -u root -p < init.sql

CREATE DATABASE IF NOT EXISTS techhub
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;

USE techhub;

-- 说明：表结构由 JPA 的 ddl-auto=update 自动创建，
-- 也可运行此脚本手动创建数据库后直接启动应用。
-- 默认测试账号：admin / admin123
