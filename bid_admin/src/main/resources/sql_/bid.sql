/*
Navicat MySQL Data Transfer

Source Server         : 192.168.18.203
Source Server Version : 50714
Source Host           : 192.168.18.203:3306
Source Database       : bid

Target Server Type    : MYSQL
Target Server Version : 50714
File Encoding         : 65001

Date: 2018-06-28 17:31:18
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
  `step` float(11,2) DEFAULT NULL COMMENT '竞价阶梯(元)',
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
) ENGINE=InnoDB AUTO_INCREMENT=849 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=176 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=382 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=169 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(200) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;

-- ----------------------------
-- View structure for bid_details_max_rank_by_uid
-- ----------------------------
DROP VIEW IF EXISTS `bid_details_max_rank_by_uid`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `bid_details_max_rank_by_uid` AS select `bid_details`.`bid_detail_id` AS `bid_detail_id`,`bid_details`.`uid` AS `uid`,`bid_details`.`bid_name` AS `bid_name`,`bid_details`.`product_id` AS `product_id`,max(`bid_details`.`price`) AS `price`,`bid_details`.`create_time` AS `create_time` from `bid_details` group by `bid_details`.`bid_name`,`bid_details`.`product_id`,`bid_details`.`uid` ;

-- ----------------------------
-- View structure for bid_details_min_rank_by_uid
-- ----------------------------
DROP VIEW IF EXISTS `bid_details_min_rank_by_uid`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `bid_details_min_rank_by_uid` AS select `bid_details`.`bid_detail_id` AS `bid_detail_id`,`bid_details`.`uid` AS `uid`,`bid_details`.`bid_name` AS `bid_name`,`bid_details`.`product_id` AS `product_id`,min(`bid_details`.`price`) AS `price`,`bid_details`.`create_time` AS `create_time` from `bid_details` group by `bid_details`.`bid_name`,`bid_details`.`product_id`,`bid_details`.`uid` ;
