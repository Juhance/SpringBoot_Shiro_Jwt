/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50087
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50087
File Encoding         : 65001

Date: 2020-02-15 11:35:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_functions
-- ----------------------------
DROP TABLE IF EXISTS `tb_functions`;
CREATE TABLE `tb_functions` (
  `func_id` int(11) NOT NULL auto_increment,
  `func_name` varchar(50) default NULL,
  `func_url` varchar(255) default NULL,
  `func_code` varchar(50) default NULL,
  `parent_id` int(11) default NULL,
  `func_type` int(11) default '1',
  `status` int(11) default '1',
  `sort_num` int(11) default '0',
  `create_time` datetime default NULL,
  `update_time` datetime default NULL,
  PRIMARY KEY  (`func_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_functions
-- ----------------------------
INSERT INTO `tb_functions` VALUES ('1', '系统管理', null, 'system', null, '1', '1', '0', '2017-08-09 21:37:24', null);
INSERT INTO `tb_functions` VALUES ('2', '用户管理', 'userManager.do', 'user:manager', '1', '1', '1', '0', null, '2018-11-20 20:13:07');
INSERT INTO `tb_functions` VALUES ('3', '角色管理', 'roleManager.do', 'role:manager', '1', '1', '1', '0', '2017-08-09 21:38:24', null);
INSERT INTO `tb_functions` VALUES ('4', '权限管理', 'functionManager.do', 'function:manager', '1', '1', '1', '0', '2017-08-09 21:38:50', null);
INSERT INTO `tb_functions` VALUES ('5', '新建', '/system/user/create', 'user:add', '2', '0', '1', '0', '2017-08-09 21:39:29', null);
INSERT INTO `tb_functions` VALUES ('6', '修改', 'update.do', 'user:update', '2', '0', '1', '0', '2017-08-09 21:39:58', null);
INSERT INTO `tb_functions` VALUES ('7', '删除', 'delete.do', 'user:delete', '2', '0', '1', '0', '2017-08-09 21:40:23', null);
INSERT INTO `tb_functions` VALUES ('8', '详情', '/system/user/detail', 'user:detail', '2', '0', '1', '0', '2017-08-09 21:40:48', null);
INSERT INTO `tb_functions` VALUES ('9', '授权', '/system/user/grant', 'system:funcation:*', '2', '0', '1', '0', '2017-08-09 21:41:30', null);
INSERT INTO `tb_functions` VALUES ('10', '字典管理', '/system/dict', 'system:dict', '1', '1', '1', '0', '2018-01-30 14:02:26', null);
INSERT INTO `tb_functions` VALUES ('15', '用户添加按钮', 'addUser.do', '1000090', '2', '0', '1', '10', '2018-11-20 09:51:13', null);
INSERT INTO `tb_functions` VALUES ('17', '员工管理菜单', 'departmentManger.do', '100100', '1', '1', '1', '10', null, '2018-11-20 17:08:10');

-- ----------------------------
-- Table structure for tb_roles
-- ----------------------------
DROP TABLE IF EXISTS `tb_roles`;
CREATE TABLE `tb_roles` (
  `role_id` int(11) NOT NULL auto_increment,
  `role_name` varchar(50) default NULL,
  `note` varchar(255) default NULL,
  `system` bit(1) default b'0',
  `status` int(11) default '1',
  `create_time` datetime default NULL,
  `update_time` datetime default NULL,
  PRIMARY KEY  (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_roles
-- ----------------------------
INSERT INTO `tb_roles` VALUES ('1', 'System_manager', '系统超级管理员角色', '\0', '1', '2017-08-09 21:35:09', null);
INSERT INTO `tb_roles` VALUES ('2', 'Plate_manager', '平台管理员角色，管理平台产品、订单等信息', '\0', '1', '2017-08-09 21:35:41', null);
INSERT INTO `tb_roles` VALUES ('3', 'sudo_user', '初等级会员', '\0', '1', '2017-08-09 21:36:04', null);
INSERT INTO `tb_roles` VALUES ('4', 'su_user', '消费金额在40000以上的会员', '\0', '1', '2017-08-09 21:36:30', null);

-- ----------------------------
-- Table structure for tb_role_function
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_function`;
CREATE TABLE `tb_role_function` (
  `role_id` int(11) NOT NULL,
  `func_id` int(11) NOT NULL,
  `id` int(11) NOT NULL auto_increment,
  PRIMARY KEY  (`id`),
  KEY `func_id` (`func_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_role_function
-- ----------------------------
INSERT INTO `tb_role_function` VALUES ('1', '1', '1');
INSERT INTO `tb_role_function` VALUES ('1', '2', '2');
INSERT INTO `tb_role_function` VALUES ('1', '3', '3');
INSERT INTO `tb_role_function` VALUES ('1', '5', '4');
INSERT INTO `tb_role_function` VALUES ('1', '6', '5');
INSERT INTO `tb_role_function` VALUES ('1', '7', '6');
INSERT INTO `tb_role_function` VALUES ('2', '1', '7');
INSERT INTO `tb_role_function` VALUES ('2', '2', '8');
INSERT INTO `tb_role_function` VALUES ('2', '4', '9');
INSERT INTO `tb_role_function` VALUES ('3', '2', '10');
INSERT INTO `tb_role_function` VALUES ('3', '5', '11');
INSERT INTO `tb_role_function` VALUES ('3', '6', '12');

-- ----------------------------
-- Table structure for tb_users
-- ----------------------------
DROP TABLE IF EXISTS `tb_users`;
CREATE TABLE `tb_users` (
  `user_id` int(11) NOT NULL auto_increment,
  `login_name` varchar(20) default NULL,
  `user_name` varchar(20) default NULL,
  `sex` varchar(4) default NULL,
  `header_url` varchar(255) default NULL,
  `password` varchar(36) default NULL,
  `salt` varchar(36) default NULL,
  `login_method` varchar(50) default NULL,
  `phone` varchar(13) default NULL,
  `email` varchar(100) default NULL,
  `status` int(11) default '1',
  `note` varchar(255) default NULL,
  `create_time` datetime default NULL,
  `update_time` datetime default NULL,
  PRIMARY KEY  (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_users
-- ----------------------------
INSERT INTO `tb_users` VALUES ('1', 'admin123', 'admin', null, null, 'admin', 'abcd', 'admin', '15688125399', 'admin@qq.com', '1', '无', '2017-08-09 21:32:04', null);
INSERT INTO `tb_users` VALUES ('49', 'zhangsan', '张三', '', '', '123456', 'abcd', '张三', '', '', '1', null, '2020-01-03 20:21:54', '2020-01-03 20:21:54');

-- ----------------------------
-- Table structure for tb_user_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_role`;
CREATE TABLE `tb_user_role` (
  `user_id` int(11) NOT NULL default '3',
  `role_id` int(11) NOT NULL,
  `id` int(11) NOT NULL auto_increment,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user_role
-- ----------------------------
INSERT INTO `tb_user_role` VALUES ('1', '1', '1');
INSERT INTO `tb_user_role` VALUES ('1', '2', '2');
INSERT INTO `tb_user_role` VALUES ('3', '3', '4');
