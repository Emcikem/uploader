CREATE TABLE `tbl_file`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `identifier`  varchar(64)   NOT NULL DEFAULT '' COMMENT '文件的md5',
    `fileName`    varchar(256)  NOT NULL DEFAULT '' COMMENT '文件名',
    `file_type`   varchar(32)   NOT NULL DEFAULT '' COMMENT '文件后缀',
    `totalSize`   bigint(20) DEFAULT '0'  COMMENT '文件大小',
    `file_path`   varchar(1024) NOT NULL DEFAULT '' COMMENT '文件存储路径',
    `creator`     varchar(64)   NOT NULL DEFAULT '' COMMENT '创建者username',
    `create_time` bigint(20)    COMMENT '创建时间',
    `updateTime`  bigint(20)    COMMENT '更新日期',
    `deleted`     tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态(可用0/已删除1状态)',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_file_id` (`id`),
    KEY           `idx_file_hash` (`identifier`),
    KEY           `idx_deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE `tbl_file` ADD unique(`identifier`)


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
