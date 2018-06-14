/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50714
Source Host           : localhost:3306
Source Database       : bid

Target Server Type    : MYSQL
Target Server Version : 50714
File Encoding         : 65001

Date: 2018-05-11 11:22:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for supplier
-- ----------------------------
DROP TABLE IF EXISTS `supplier`;
CREATE TABLE `supplier` (
  `uid` int(11) NOT NULL AUTO_INCREMENT COMMENT '供应商id',
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `type` varchar(255) DEFAULT NULL COMMENT '供应商类型',
  `company_name` varchar(255) DEFAULT NULL COMMENT '公司名称',
  `license_number` varchar(255) DEFAULT NULL COMMENT '营业执照号码',
  `legal` varchar(255) DEFAULT NULL COMMENT '法定代表人',
  `name` varchar(255) DEFAULT NULL COMMENT '联系人',
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `fax` varchar(255) DEFAULT NULL,
  `company_address` varchar(255) DEFAULT NULL,
  `zip_code` varchar(255) DEFAULT NULL,
  `company_web` varchar(255) DEFAULT NULL COMMENT '公司网站网址',
  `company_level` varchar(255) DEFAULT NULL COMMENT '公司资质等级',
  `company_desc` varchar(255) DEFAULT NULL COMMENT '公司简介',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of supplier
-- ----------------------------
INSERT INTO `supplier` VALUES ('2', 'a', '123456', null, null, null, null, null, null, null, null, null, null, null, null, null, '2018-05-10 10:32:27', null);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
