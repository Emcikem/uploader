/*
 Navicat Premium Data Transfer

 Source Server         : 110.40.220.211_3306
 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Host           : 110.40.220.211:3306
 Source Schema         : file_uploader

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 20/04/2022 22:16:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tbl_file
-- ----------------------------
DROP TABLE IF EXISTS `tbl_file`;
CREATE TABLE `tbl_file` (
                            `id` int(11) NOT NULL AUTO_INCREMENT,
                            `file_path` varchar(255) NOT NULL COMMENT '文件存储路径',
                            `file_type` varchar(32) NOT NULL COMMENT '文件类型',
                            `file_name` varchar(255) NOT NULL COMMENT '文件名称',
                            `identifier` varchar(255) NOT NULL COMMENT '文件Md5',
                            `total_size` bigint(20) DEFAULT NULL COMMENT '文件总大小',
                            `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
                            `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
                            `deleted` int(11) NOT NULL DEFAULT 0 COMMENT '0没有删除，1删除了',
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `identifier` (`identifier`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;



CREATE TABLE `tbl_user` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `username` varchar(20) NOT NULL DEFAULT '' COMMENT '用户名',
    `store_type` varchar(10) NOT NULL DEFAULT 'LOCAL' COMMENT '存储方式',
    `upload_folder` varchar(30) NOT NULL DEFAULT 'file' COMMENT '存储地址',
    `should_merge_store` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否合并(合并0/不合并1状态)',
    `create_time` bigint(20)    COMMENT '创建时间',
    `updateTime`  bigint(20)    COMMENT '更新日期',
    `deleted`     tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态(可用0/已删除1状态)',
    PRIMARY KEY (`id`),
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
