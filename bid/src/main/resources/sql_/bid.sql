/*
Navicat MySQL Data Transfer

Source Server         : 192.168.18.203
Source Server Version : 50714
Source Host           : 192.168.18.203:3306
Source Database       : bid

Target Server Type    : MYSQL
Target Server Version : 50714
File Encoding         : 65001

Date: 2018-06-22 17:45:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for bidding
-- ----------------------------
DROP TABLE IF EXISTS `bidding`;
CREATE TABLE `bidding` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '标单名称',
  `product_id` int(11) NOT NULL COMMENT '关联的产品id',
  `number` int(11) DEFAULT NULL COMMENT '参与投标产品的数量',
  `start_price` float(13,2) DEFAULT NULL COMMENT '起拍价',
  `step` int(11) DEFAULT NULL COMMENT '竞价阶梯(元)',
  `end_delivery_date` timestamp NULL DEFAULT NULL COMMENT '交货期限',
  `end_pay_date` timestamp NULL DEFAULT NULL COMMENT '付款期限',
  `mark` int(2) DEFAULT '0' COMMENT '1=招标（公司卖）   2=竞标（公司买）',
  `status` int(11) DEFAULT '0' COMMENT '标单状态 0：发布状态   1：正在竞标中   2：结束  3:审核',
  `type` int(11) DEFAULT '0' COMMENT '类别 1:草稿 2:垃圾箱',
  `bid_desc` varchar(255) DEFAULT NULL COMMENT '详细信息',
  `start_time` timestamp NULL DEFAULT NULL COMMENT '启标日期',
  `end_time` timestamp NULL DEFAULT NULL COMMENT '竞标单截止日期',
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `uid` int(11) DEFAULT NULL COMMENT '后台系统用户id',
  `finish` int(3) DEFAULT '0' COMMENT '选择中标完成状态 1:完成',
  `task_name` varchar(255) DEFAULT '',
  `group_id` varchar(50) DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `bid_uid` (`uid`),
  KEY `bid_product_id` (`product_id`),
  KEY `name` (`name`),
  CONSTRAINT `bid_product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`),
  CONSTRAINT `bid_uid` FOREIGN KEY (`uid`) REFERENCES `sys_user` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=806 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bidding_supplier
-- ----------------------------
DROP TABLE IF EXISTS `bidding_supplier`;
CREATE TABLE `bidding_supplier` (
  `bidding_supplier_id` int(11) NOT NULL AUTO_INCREMENT,
  `bid_name` varchar(255) DEFAULT '' COMMENT '竞标单名称',
  `suid` int(11) DEFAULT '0' COMMENT '供应商id',
  PRIMARY KEY (`bidding_supplier_id`),
  KEY `bid_name` (`bid_name`),
  CONSTRAINT `bidding_supplier_ibfk_1` FOREIGN KEY (`bid_name`) REFERENCES `bidding` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bid_details
-- ----------------------------
DROP TABLE IF EXISTS `bid_details`;
CREATE TABLE `bid_details` (
  `bid_detail_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '抢标明细的id',
  `uid` int(11) DEFAULT NULL COMMENT '供应商id',
  `bid_name` varchar(255) NOT NULL DEFAULT '' COMMENT '标单id',
  `product_id` int(11) DEFAULT NULL COMMENT '产品id',
  `price` float(13,2) DEFAULT NULL COMMENT '出的价格',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '抢标时间',
  PRIMARY KEY (`bid_detail_id`),
  KEY `bid_name` (`bid_name`),
  CONSTRAINT `bid_details_ibfk_1` FOREIGN KEY (`bid_name`) REFERENCES `bidding` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=216 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for news
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news` (
  `news_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '新闻消息的id',
  `title` varchar(255) NOT NULL DEFAULT '' COMMENT '新闻标题',
  `content` varchar(255) NOT NULL DEFAULT '' COMMENT '新闻内容',
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `uid` int(11) DEFAULT NULL,
  PRIMARY KEY (`news_id`),
  KEY `news_uid` (`uid`),
  CONSTRAINT `news_uid` FOREIGN KEY (`uid`) REFERENCES `sys_user` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `product_id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL COMMENT '产品编码',
  `name` varchar(255) DEFAULT NULL,
  `product_type_id` int(11) DEFAULT NULL COMMENT '产品类型id',
  `spec` varchar(255) DEFAULT NULL COMMENT '产品规格',
  `unit` varchar(255) DEFAULT NULL COMMENT '产品单位',
  `max_unit_price` float(13,2) DEFAULT NULL COMMENT '最高单价',
  `default_gradient` varchar(255) DEFAULT NULL COMMENT '缺省梯度',
  `product_desc` varchar(255) DEFAULT NULL COMMENT '产品描述',
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `uid` int(11) DEFAULT NULL COMMENT '采购员id',
  PRIMARY KEY (`product_id`),
  KEY `product_uid` (`uid`),
  KEY `product_type_id` (`product_type_id`),
  CONSTRAINT `product_ibfk_1` FOREIGN KEY (`product_type_id`) REFERENCES `product_type` (`product_type_id`),
  CONSTRAINT `product_ibfk_2` FOREIGN KEY (`uid`) REFERENCES `sys_user` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=165 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for product_type
-- ----------------------------
DROP TABLE IF EXISTS `product_type`;
CREATE TABLE `product_type` (
  `product_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '产品类型名称',
  `uid` int(11) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`product_type_id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for supplier
-- ----------------------------
DROP TABLE IF EXISTS `supplier`;
CREATE TABLE `supplier` (
  `supplier_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '供应商id',
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `supplier_type_id` int(11) DEFAULT NULL COMMENT '供应商类型id',
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
  `uid` int(11) DEFAULT NULL,
  PRIMARY KEY (`supplier_id`),
  UNIQUE KEY `username` (`username`),
  KEY `supplier_ibfk_1` (`supplier_type_id`),
  KEY `uid` (`uid`),
  CONSTRAINT `supplier_ibfk_1` FOREIGN KEY (`supplier_type_id`) REFERENCES `supplier_type` (`supplier_type_id`),
  CONSTRAINT `supplier_ibfk_2` FOREIGN KEY (`uid`) REFERENCES `sys_user` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for supplier_type
-- ----------------------------
DROP TABLE IF EXISTS `supplier_type`;
CREATE TABLE `supplier_type` (
  `supplier_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `uid` int(11) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`supplier_type_id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT '',
  `role` varchar(255) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for win_bid
-- ----------------------------
DROP TABLE IF EXISTS `win_bid`;
CREATE TABLE `win_bid` (
  `win_bid_id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT '0' COMMENT '采购员id',
  `bid_detail_id` int(11) DEFAULT '0',
  `bid_name` varchar(255) DEFAULT '' COMMENT '竞标单名称',
  `product_id` int(11) DEFAULT '0' COMMENT '产品id',
  `suid` int(11) DEFAULT '0' COMMENT '供应商id',
  `reason` varchar(255) NOT NULL DEFAULT '' COMMENT '中标理由',
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`win_bid_id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;
