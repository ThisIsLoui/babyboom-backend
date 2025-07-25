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