create database babyboom;

use babyboom;

CREATE TABLE babyboom_users (
                                id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
                                birthday DATE COMMENT '生日',
                                nickname VARCHAR(255) COMMENT '昵称',
                                gender TINYINT COMMENT '性别 (1:男, 2:女)',
                                height DOUBLE COMMENT '身高',
                                weight DOUBLE COMMENT '体重',
                                create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                update_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                                is_deleted TINYINT DEFAULT 1 COMMENT '逻辑删除标志 (1:存在, 0:删除)'
) COMMENT='用户表';

CREATE TABLE babyboom_logs (
                               id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '日志ID',
                               text VARCHAR(255) COMMENT '日志内容',
                               user_id BIGINT COMMENT '关联用户ID',
                               create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                               is_deleted TINYINT DEFAULT 1 COMMENT '逻辑删除标志 (1:存在, 0:删除)'
) COMMENT='日志表';

CREATE TABLE babyboom_logs_image (
                                     id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '日志图片ID',
                                     log_id BIGINT COMMENT '关联日志ID',
                                     user_id BIGINT COMMENT '关联用户ID',
                                     url VARCHAR(255) COMMENT '图片文件的URL',
                                     analysis TEXT COMMENT '图片分析结果',
                                     create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                     update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                     is_deleted TINYINT DEFAULT 1 COMMENT '逻辑删除标志 (1:存在, 0:删除)'
) COMMENT='日志图片表';

CREATE TABLE babyboom_logs_audio (
                                     id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '日志音频ID',
                                     log_id BIGINT COMMENT '关联日志ID',
                                     user_id BIGINT COMMENT '关联用户ID',
                                     url VARCHAR(255) COMMENT '音频文件的URL',
                                     create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                     update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                     is_deleted TINYINT DEFAULT 1 COMMENT '逻辑删除标志 (1:存在, 0:删除)'
) COMMENT='日志音频表';