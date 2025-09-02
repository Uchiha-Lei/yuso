# 数据库初始化
# @author <a href="https://github.com/liyupi">程序员鱼皮</a>
# @from <a href="https://yupi.icu">编程导航知识星球</a>

-- 创建库
create database if not exists my_db;

-- 切换库
use my_db;

INSERT INTO user (userAccount, userPassword, unionId, mpOpenId, userName, userAvatar, userProfile, userRole) VALUES
                                                                                                                 ('zhangwei', '25d55ad283aa400af464c76d713c07ad', 'wx_union_008', 'mp_open_008', '张伟', 'https://example.com/avatars/zhangwei.jpg', '热爱运动的程序员', 'user'),
                                                                                                                 ('liumei', '25d55ad283aa400af464c76d713c07ad', 'wx_union_009', null, '刘梅', 'https://example.com/avatars/liumei.png', '美食博主', 'admin'),
                                                                                                                 ('wanggang', '25d55ad283aa400af464c76d713c07ad', null, 'mp_open_010', '王刚', null, null, 'user'),
                                                                                                                 ('chenjing', '25d55ad283aa400af464c76d713c07ad', 'wx_union_011', null, '陈静', 'https://example.com/avatars/chenjing.jpg', '旅行摄影师', 'user'),
                                                                                                                 ('litao', '25d55ad283aa400af464c76d713c07ad', null, null, '李涛', 'https://example.com/avatars/litao.png', '前端开发工程师', 'admin'),
                                                                                                                 ('zhaoyan', '25d55ad283aa400af464c76d713c07ad', 'wx_union_012', 'mp_open_012', '赵燕', null, '产品经理', 'user'),
                                                                                                                 ('sunming', '25d55ad283aa400af464c76d713c07ad', null, 'mp_open_013', '孙明', 'https://example.com/avatars/sunming.jpg', null, 'ban'),
                                                                                                                 ('zhouhua', '25d55ad283aa400af464c76d713c07ad', 'wx_union_013', null, '周华', 'https://example.com/avatars/zhouhua.png', '大数据分析师', 'user'),
                                                                                                                 ('wuying', '25d55ad283aa400af464c76d713c07ad', null, null, '吴英', null, 'UI设计师', 'user'),
                                                                                                                 ('zhenglei', '25d55ad283aa400af464c76d713c07ad', 'wx_union_014', 'mp_open_014', '郑雷', 'https://example.com/avatars/zhenglei.jpg', '人工智能研究员', 'user');
-- 用户表
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userAccount  varchar(256)                           not null comment '账号',
    userPassword varchar(512)                           not null comment '密码',
    unionId      varchar(256)                           null comment '微信开放平台id',
    mpOpenId     varchar(256)                           null comment '公众号openId',
    userName     varchar(256)                           null comment '用户昵称',
    userAvatar   varchar(1024)                          null comment '用户头像',
    userProfile  varchar(512)                           null comment '用户简介',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user/admin/ban',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    index idx_unionId (unionId)
) comment '用户' collate = utf8mb4_unicode_ci;

-- 帖子表
create table if not exists post
(
    id         bigint auto_increment comment 'id' primary key,
    title      varchar(512)                       null comment '标题',
    content    text                               null comment '内容',
    tags       varchar(1024)                      null comment '标签列表（json 数组）',
    thumbNum   int      default 0                 not null comment '点赞数',
    favourNum  int      default 0                 not null comment '收藏数',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint  default 0                 not null comment '是否删除',
    index idx_userId (userId)
) comment '帖子' collate = utf8mb4_unicode_ci;

-- 帖子点赞表（硬删除）
create table if not exists post_thumb
(
    id         bigint auto_increment comment 'id' primary key,
    postId     bigint                             not null comment '帖子 id',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    index idx_postId (postId),
    index idx_userId (userId)
) comment '帖子点赞';

-- 帖子收藏表（硬删除）
create table if not exists post_favour
(
    id         bigint auto_increment comment 'id' primary key,
    postId     bigint                             not null comment '帖子 id',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    index idx_postId (postId),
    index idx_userId (userId)
) comment '帖子收藏';
