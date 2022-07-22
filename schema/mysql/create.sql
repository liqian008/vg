
-- ----------------------------
-- Table structure for tb_datasource
-- ----------------------------
CREATE TABLE `tb_datasource` (
                               `id` int NOT NULL AUTO_INCREMENT,
                               `db_type` smallint NOT NULL DEFAULT '10' COMMENT '数据库类型，0mysql',
                               `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '标题',
                               `description` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '模板描述',
                               `jdbc_url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'jdbc链接',
                               `jdbc_driver` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'jdbc驱动',
                               `username` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '数据库账户名',
                               `password` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '数据库密码',
                               `status` smallint DEFAULT '1' COMMENT '状态',
                               `create_uid` int DEFAULT NULL COMMENT '创建人',
                               `update_uid` int DEFAULT NULL COMMENT '修改人',
                               `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                               `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                               PRIMARY KEY (`id`) USING BTREE,
                               UNIQUE KEY `uk_tk` (`jdbc_url`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=100012 DEFAULT CHARSET=utf8 COMMENT='模板表';

-- ----------------------------
-- Records of tb_datasource
-- ----------------------------
BEGIN;
INSERT INTO `tb_datasource` VALUES (100001, 0, 'Mysql库', '', 'jdbc:mysql://127.0.0.1:3306/test?characterEncoding=utf-8', 'com.mysql.cj.jdbc.Driver', 'root', '', 1, 1, 100001, '2022-07-14 00:21:39', NULL);
COMMIT;

-- ----------------------------
-- Table structure for tb_template
-- ----------------------------
CREATE TABLE `tb_template` (
                             `id` int NOT NULL AUTO_INCREMENT,
                             `source_type` smallint NOT NULL DEFAULT '10' COMMENT '来源类型，0官方，10用户自定义',
                             `template_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '模板key，英文',
                             `version` int NOT NULL DEFAULT '10000' COMMENT '版本号，用于升级检查，必须高于当前版本',
                             `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '模板标题',
                             `description` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '模板描述',
                             `path` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '模板路径，不带后缀名',
                             `config` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '配置信息，json格式',
                             `status` smallint DEFAULT '1' COMMENT '状态',
                             `create_uid` int DEFAULT NULL COMMENT '创建人',
                             `update_uid` int DEFAULT NULL COMMENT '修改人',
                             `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                             `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                             PRIMARY KEY (`id`) USING BTREE,
                             UNIQUE KEY `uk_tk` (`template_key`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=100038 DEFAULT CHARSET=utf8 COMMENT='模板表';

-- ----------------------------
-- Records of tb_template
-- ----------------------------
BEGIN;
INSERT INTO `tb_template` VALUES (100016, 10, 'template_react_project', 10002, 'React工程模板', '内置Axios，Antd模块', 'template_react_project', '{  \"templateKey\": \"template_react_project\",  \"version\": 10002,  \"title\": \"React工程模板\",  \"description\": \"内置Axios，Antd模块\",  \"varDefinitions\": [    {      \"key\": \"projectname\",      \"title\": \"项目名称（英文名）\",      \"description\": \"项目名称（英文名）\",      \"defaultValue\": \"demo-project\",      \"placeholder\": \"\",      \"required\": true    }  ]}', 0, 1, 1, '2022-07-16 23:04:57', '2022-07-17 14:05:13');
INSERT INTO `tb_template` VALUES (100034, 10, 'template_springboot_project2', 10001, 'Springboot工程模板', '内置Mybatis，等模块', 'template_springboot_project2', '{  \"templateKey\": \"template_springboot_project2\",  \"version\": 10001,  \"title\": \"Springboot工程模板\",  \"description\": \"内置Mybatis，等模块\",  \"varDefinitions\": [    {      \"key\": \"project_name\",      \"title\": \"项目名称（英文名）\",      \"description\": \"项目名称（英文名）\",      \"defaultValue\": \"project-springboot\",      \"placeholder\": \"\",      \"required\": true    },    {      \"key\": \"project_title\",      \"title\": \"项目名称（中文名）\",      \"description\": \"项目名称（中文名）\",      \"defaultValue\": \"使用万阵自动生成的Springboot项目\",      \"placeholder\": \"\",      \"required\": true    },    {      \"key\": \"package_name\",      \"title\": \"包名称（英文名）\",      \"description\": \"项目名称（英文名）\",      \"defaultValue\": \"com.jd.cityos.vg\",      \"placeholder\": \"\",      \"required\": true    },    {      \"key\": \"mvn_group_id\",      \"title\": \"Maven GroupId\",      \"description\": \"Maven的groupId\",      \"defaultValue\": \"com.jd.cityos\",      \"placeholder\": \"\",      \"required\": true    },    {      \"key\": \"mvn_artifact_id\",      \"title\": \"Maven ArtifactId\",      \"description\": \"Maven的artifactId\",      \"defaultValue\": \"variable_generator_project\",      \"placeholder\": \"\",      \"required\": true    },    {      \"key\": \"mvn_version\",      \"title\": \"Maven Version\",      \"description\": \"Maven的version\",      \"defaultValue\": \"1.0.0_SNAPSHOT\",      \"placeholder\": \"\",      \"required\": true    },    {      \"key\": \"server_port\",      \"title\": \"启动端口号\",      \"description\": \"启动的端口号\",      \"defaultValue\": \"8080\",      \"placeholder\": \"\",      \"required\": true    },	{      \"key\": \"spring_boot_version\",      \"title\": \"SpringBoot版本\",      \"description\": \"SpringBoot版本\",      \"defaultValue\": \"2.2.4.RELEASE\",      \"placeholder\": \"\",      \"required\": true    }  ]}', 0, 1, NULL, '2022-07-20 01:32:09', NULL);
INSERT INTO `tb_template` VALUES (100037, 10, 'template_springboot_project', 10001, 'Springboot工程模板', '内置Mybatis，等模块', 'template_springboot_project', '{  \"templateKey\": \"template_springboot_project\",  \"version\": 10001,  \"title\": \"Springboot工程模板\",  \"description\": \"内置Mybatis，等模块\",  \"varDefinitions\": [    {      \"key\": \"project_name\",      \"title\": \"项目名称（英文名）\",      \"description\": \"项目名称（英文名）\",      \"defaultValue\": \"project-springboot\",      \"placeholder\": \"\",      \"required\": true    },    {      \"key\": \"project_title\",      \"title\": \"项目名称（中文名）\",      \"description\": \"项目名称（中文名）\",      \"defaultValue\": \"使用万阵自动生成的Springboot项目\",      \"placeholder\": \"\",      \"required\": true    },    {      \"key\": \"package_name\",      \"title\": \"包名称（英文名）\",      \"description\": \"项目名称（英文名）\",      \"defaultValue\": \"com.jd.cityos.vg\",      \"placeholder\": \"\",      \"required\": true    },    {      \"key\": \"mvn_group_id\",      \"title\": \"Maven GroupId\",      \"description\": \"Maven的groupId\",      \"defaultValue\": \"com.jd.cityos\",      \"placeholder\": \"\",      \"required\": true    },    {      \"key\": \"mvn_artifact_id\",      \"title\": \"Maven ArtifactId\",      \"description\": \"Maven的artifactId\",      \"defaultValue\": \"variable_generator_project\",      \"placeholder\": \"\",      \"required\": true    },    {      \"key\": \"mvn_version\",      \"title\": \"Maven Version\",      \"description\": \"Maven的version\",      \"defaultValue\": \"1.0.0_SNAPSHOT\",      \"placeholder\": \"\",      \"required\": true    },    {      \"key\": \"server_port\",      \"title\": \"启动端口号\",      \"description\": \"启动的端口号\",      \"defaultValue\": \"8080\",      \"placeholder\": \"\",      \"required\": true    }  ]}', 0, 1, NULL, '2022-07-21 04:03:20', NULL);
COMMIT;

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '手机号',
                         `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '密码',
                         `nickname` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT '' COMMENT '昵称',
                         `avatar_url` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '头像',
                         `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮箱',
                         `status` smallint DEFAULT '1',
                         `create_time` datetime DEFAULT NULL,
                         `update_time` datetime DEFAULT NULL,
                         PRIMARY KEY (`id`) USING BTREE,
                         UNIQUE KEY `uk_mobile` (`username`) USING BTREE,
                         UNIQUE KEY `uk_nickname` (`nickname`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=100002 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
BEGIN;
INSERT INTO `tb_user` VALUES (1, 'admin', '12', 'admin', NULL, 'liqian008@sina.com', 1, '2022-07-14 07:47:24', NULL);
INSERT INTO `tb_user` VALUES (100001, 'liqian', '12', 'liqian', NULL, 'liqian008@sina.com', 1, '2022-07-14 07:47:24', NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
/*
 Navicat Premium Data Transfer

 Source Server         : huawei2_mysql_docker
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : 121.36.64.208:4306
 Source Schema         : variable_generator

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 23/07/2022 00:41:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_datasource
-- ----------------------------
DROP TABLE IF EXISTS `tb_datasource`;
CREATE TABLE `tb_datasource` (
                               `id` int NOT NULL AUTO_INCREMENT,
                               `db_type` smallint NOT NULL DEFAULT '10' COMMENT '数据库类型，0mysql',
                               `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '标题',
                               `description` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '模板描述',
                               `jdbc_url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'jdbc链接',
                               `jdbc_driver` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'jdbc驱动',
                               `username` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '数据库账户名',
                               `password` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '数据库密码',
                               `status` smallint DEFAULT '1' COMMENT '状态',
                               `create_uid` int DEFAULT NULL COMMENT '创建人',
                               `update_uid` int DEFAULT NULL COMMENT '修改人',
                               `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                               `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                               PRIMARY KEY (`id`) USING BTREE,
                               UNIQUE KEY `uk_tk` (`jdbc_url`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=100012 DEFAULT CHARSET=utf8 COMMENT='模板表';

-- ----------------------------
-- Records of tb_datasource
-- ----------------------------
BEGIN;
INSERT INTO `tb_datasource` VALUES (100001, 0, 'Mysql库', '', 'jdbc:mysql://127.0.0.1:3306/test?characterEncoding=utf-8', 'com.mysql.cj.jdbc.Driver', 'root', '', 1, 1, 100001, '2022-07-14 00:21:39', NULL);
COMMIT;

-- ----------------------------
-- Table structure for tb_template
-- ----------------------------
DROP TABLE IF EXISTS `tb_template`;
CREATE TABLE `tb_template` (
                             `id` int NOT NULL AUTO_INCREMENT,
                             `source_type` smallint NOT NULL DEFAULT '10' COMMENT '来源类型，0官方，10用户自定义',
                             `template_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '模板key，英文',
                             `version` int NOT NULL DEFAULT '10000' COMMENT '版本号，用于升级检查，必须高于当前版本',
                             `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '模板标题',
                             `description` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '模板描述',
                             `path` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '模板路径，不带后缀名',
                             `config` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '配置信息，json格式',
                             `status` smallint DEFAULT '1' COMMENT '状态',
                             `create_uid` int DEFAULT NULL COMMENT '创建人',
                             `update_uid` int DEFAULT NULL COMMENT '修改人',
                             `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                             `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                             PRIMARY KEY (`id`) USING BTREE,
                             UNIQUE KEY `uk_tk` (`template_key`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=100038 DEFAULT CHARSET=utf8 COMMENT='模板表';

-- ----------------------------
-- Records of tb_template
-- ----------------------------
BEGIN;
INSERT INTO `tb_template` VALUES (100016, 10, 'template_react_project', 10002, 'React工程模板', '内置Axios，Antd模块', 'template_react_project', '{  \"templateKey\": \"template_react_project\",  \"version\": 10002,  \"title\": \"React工程模板\",  \"description\": \"内置Axios，Antd模块\",  \"varDefinitions\": [    {      \"key\": \"projectname\",      \"title\": \"项目名称（英文名）\",      \"description\": \"项目名称（英文名）\",      \"defaultValue\": \"demo-project\",      \"placeholder\": \"\",      \"required\": true    }  ]}', 0, 1, 1, '2022-07-16 23:04:57', '2022-07-17 14:05:13');
INSERT INTO `tb_template` VALUES (100034, 10, 'template_springboot_project2', 10001, 'Springboot工程模板', '内置Mybatis，等模块', 'template_springboot_project2', '{  \"templateKey\": \"template_springboot_project2\",  \"version\": 10001,  \"title\": \"Springboot工程模板\",  \"description\": \"内置Mybatis，等模块\",  \"varDefinitions\": [    {      \"key\": \"project_name\",      \"title\": \"项目名称（英文名）\",      \"description\": \"项目名称（英文名）\",      \"defaultValue\": \"project-springboot\",      \"placeholder\": \"\",      \"required\": true    },    {      \"key\": \"project_title\",      \"title\": \"项目名称（中文名）\",      \"description\": \"项目名称（中文名）\",      \"defaultValue\": \"使用万阵自动生成的Springboot项目\",      \"placeholder\": \"\",      \"required\": true    },    {      \"key\": \"package_name\",      \"title\": \"包名称（英文名）\",      \"description\": \"项目名称（英文名）\",      \"defaultValue\": \"com.jd.cityos.vg\",      \"placeholder\": \"\",      \"required\": true    },    {      \"key\": \"mvn_group_id\",      \"title\": \"Maven GroupId\",      \"description\": \"Maven的groupId\",      \"defaultValue\": \"com.jd.cityos\",      \"placeholder\": \"\",      \"required\": true    },    {      \"key\": \"mvn_artifact_id\",      \"title\": \"Maven ArtifactId\",      \"description\": \"Maven的artifactId\",      \"defaultValue\": \"variable_generator_project\",      \"placeholder\": \"\",      \"required\": true    },    {      \"key\": \"mvn_version\",      \"title\": \"Maven Version\",      \"description\": \"Maven的version\",      \"defaultValue\": \"1.0.0_SNAPSHOT\",      \"placeholder\": \"\",      \"required\": true    },    {      \"key\": \"server_port\",      \"title\": \"启动端口号\",      \"description\": \"启动的端口号\",      \"defaultValue\": \"8080\",      \"placeholder\": \"\",      \"required\": true    },	{      \"key\": \"spring_boot_version\",      \"title\": \"SpringBoot版本\",      \"description\": \"SpringBoot版本\",      \"defaultValue\": \"2.2.4.RELEASE\",      \"placeholder\": \"\",      \"required\": true    }  ]}', 0, 1, NULL, '2022-07-20 01:32:09', NULL);
INSERT INTO `tb_template` VALUES (100037, 10, 'template_springboot_project', 10001, 'Springboot工程模板', '内置Mybatis，等模块', 'template_springboot_project', '{  \"templateKey\": \"template_springboot_project\",  \"version\": 10001,  \"title\": \"Springboot工程模板\",  \"description\": \"内置Mybatis，等模块\",  \"varDefinitions\": [    {      \"key\": \"project_name\",      \"title\": \"项目名称（英文名）\",      \"description\": \"项目名称（英文名）\",      \"defaultValue\": \"project-springboot\",      \"placeholder\": \"\",      \"required\": true    },    {      \"key\": \"project_title\",      \"title\": \"项目名称（中文名）\",      \"description\": \"项目名称（中文名）\",      \"defaultValue\": \"使用万阵自动生成的Springboot项目\",      \"placeholder\": \"\",      \"required\": true    },    {      \"key\": \"package_name\",      \"title\": \"包名称（英文名）\",      \"description\": \"项目名称（英文名）\",      \"defaultValue\": \"com.jd.cityos.vg\",      \"placeholder\": \"\",      \"required\": true    },    {      \"key\": \"mvn_group_id\",      \"title\": \"Maven GroupId\",      \"description\": \"Maven的groupId\",      \"defaultValue\": \"com.jd.cityos\",      \"placeholder\": \"\",      \"required\": true    },    {      \"key\": \"mvn_artifact_id\",      \"title\": \"Maven ArtifactId\",      \"description\": \"Maven的artifactId\",      \"defaultValue\": \"variable_generator_project\",      \"placeholder\": \"\",      \"required\": true    },    {      \"key\": \"mvn_version\",      \"title\": \"Maven Version\",      \"description\": \"Maven的version\",      \"defaultValue\": \"1.0.0_SNAPSHOT\",      \"placeholder\": \"\",      \"required\": true    },    {      \"key\": \"server_port\",      \"title\": \"启动端口号\",      \"description\": \"启动的端口号\",      \"defaultValue\": \"8080\",      \"placeholder\": \"\",      \"required\": true    }  ]}', 0, 1, NULL, '2022-07-21 04:03:20', NULL);
COMMIT;

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
CREATE TABLE `tb_user` (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '手机号',
                         `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '密码',
                         `nickname` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT '' COMMENT '昵称',
                         `avatar_url` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '头像',
                         `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮箱',
                         `status` smallint DEFAULT '1',
                         `create_time` datetime DEFAULT NULL,
                         `update_time` datetime DEFAULT NULL,
                         PRIMARY KEY (`id`) USING BTREE,
                         UNIQUE KEY `uk_mobile` (`username`) USING BTREE,
                         UNIQUE KEY `uk_nickname` (`nickname`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=100002 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
BEGIN;
INSERT INTO `tb_user` VALUES (1, 'admin', '12', 'admin', NULL, 'liqian008@sina.com', 1, '2022-07-14 07:47:24', NULL);
INSERT INTO `tb_user` VALUES (100001, 'liqian', '12', 'liqian', NULL, 'liqian008@sina.com', 1, '2022-07-14 07:47:24', NULL);
COMMIT;

