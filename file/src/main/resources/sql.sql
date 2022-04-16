CREATE TABLE `tbl_file`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `identifier`  varchar(64)   NOT NULL DEFAULT '''' COMMENT '' 文件的md5 '',
    `fileName`    varchar(256)  NOT NULL DEFAULT '''' COMMENT '' 文件名 '',
    `file_type`   varchar(32)   NOT NULL DEFAULT '''' COMMENT '' 文件后缀 '',
    `totalSize`   bigint(20) DEFAULT '' 0 '' COMMENT '' 文件大小 '',
    `file_path`   varchar(1024) NOT NULL DEFAULT '''' COMMENT '' 文件存储路径 '',
    `creator`     varchar(64)   NOT NULL DEFAULT '''' COMMENT '' 创建者username '',
    `create_time` datetime               default NOW() COMMENT '' 创建时间 '',
    `updateTime`  datetime               default NOW() on update current_timestamp () COMMENT '' 更新日期 '',
    `deleted`     tinyint(1) NOT NULL DEFAULT '' 0 '' COMMENT '' 状态(可用0/已删除1状态)'',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_file_id` (`id`),
    KEY           `idx_file_hash` (`identifier`),
    KEY           `idx_deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
