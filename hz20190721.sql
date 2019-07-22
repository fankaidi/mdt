/*
SQLyog Professional v12.08 (64 bit)
MySQL - 5.7.25 : Database - hz
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`hz` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `hz`;

/*Table structure for table `base_key` */

DROP TABLE IF EXISTS `base_key`;

CREATE TABLE `base_key` (
  `id` varchar(32) NOT NULL,
  `nowid` varchar(32) NOT NULL,
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `base_key` */

insert  into `base_key`(`id`,`nowid`,`create_date`,`update_date`) values ('lc_daiban','1000','2019-07-20 10:19:14','2019-07-21 21:13:24'),('lc_history','800','2019-07-20 11:58:05','2019-07-21 21:24:08'),('mdt_apply','1800','2019-07-09 17:18:38','2019-07-21 21:12:50'),('mdt_team','2000','2019-07-09 10:52:26','2019-07-21 20:26:21'),('sys_file','300','2019-07-21 18:41:52','2019-07-21 19:08:15');

/*Table structure for table `lc_daiban` */

DROP TABLE IF EXISTS `lc_daiban`;

CREATE TABLE `lc_daiban` (
  `id` bigint(20) NOT NULL COMMENT '流程统一待办',
  `busitype` varchar(64) DEFAULT NULL COMMENT '业务类型，比如是mdt申请的代办',
  `entry_name` varchar(128) DEFAULT NULL COMMENT '节点名称，表示是那个步骤的名称',
  `userid` int(11) DEFAULT NULL COMMENT '操作人',
  `bisiid` int(11) DEFAULT NULL COMMENT '业务id',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `title` varchar(256) DEFAULT NULL COMMENT '内容',
  `apply_person_id` int(11) DEFAULT NULL COMMENT '申请人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `lc_daiban` */

insert  into `lc_daiban`(`id`,`busitype`,`entry_name`,`userid`,`bisiid`,`created_time`,`title`,`apply_person_id`) values (803,'mdt_team','分管院长审核',18,1901,'2019-07-21 20:42:07','22',14);

/*Table structure for table `lc_history` */

DROP TABLE IF EXISTS `lc_history`;

CREATE TABLE `lc_history` (
  `id` bigint(20) NOT NULL COMMENT '处理过的审批痕迹',
  `busitype` varchar(64) DEFAULT NULL COMMENT '业务类型，比如是mdt申请的代办',
  `entry_name` varchar(128) DEFAULT NULL COMMENT '节点名称，表示是那个步骤的名称',
  `userid` int(11) DEFAULT NULL COMMENT '操作人',
  `bisiid` int(11) DEFAULT NULL COMMENT '业务id',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `audit_result` int(11) DEFAULT NULL COMMENT '审核结果，1是通过，-1是不通过',
  `audit_opinion` varchar(512) DEFAULT NULL COMMENT '审核意见',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `lc_history` */

insert  into `lc_history`(`id`,`busitype`,`entry_name`,`userid`,`bisiid`,`created_time`,`audit_result`,`audit_opinion`) values (1,'mdt_apply','科主任审核',3,1009,'2019-07-20 00:00:00',1,'对方的'),(201,'mdt_apply','医务部主任审核',1,1009,'2019-07-20 00:00:00',1,'333啊'),(304,'mdt_team','科主任审核',3,1110,'2019-07-21 00:00:00',1,'的发生'),(305,'mdt_team','医务部主任审核',1,1110,'2019-07-21 00:00:00',1,'房贷首付'),(306,'mdt_team','分管院长审核',18,1110,'2019-07-21 00:00:00',1,'打法'),(406,'mdt_team_objective','医务部主任审核',1,1110,'2019-07-21 00:00:00',1,'的方法'),(502,'mdt_team_assess','医务部主任审核',1,1110,'2019-07-21 00:00:00',1,'订单'),(601,'mdt_team','科主任审核',3,1901,'2019-07-21 00:00:00',1,'的'),(602,'mdt_team','医务部主任审核',1,1901,'2019-07-21 00:00:00',1,'112'),(603,'mdt_team_objective','医务部主任审核',1,1901,'2019-07-21 00:00:00',1,'d '),(605,'mdt_team_assess','医务部主任审核',1,1901,'2019-07-21 00:00:00',1,'的'),(701,'mdt_apply','科主任审核',3,1701,'2019-07-21 00:00:00',1,'阿道夫'),(702,'mdt_apply','医务部主任审核',1,1701,'2019-07-21 00:00:00',1,'打法');

/*Table structure for table `mdt_apply` */

DROP TABLE IF EXISTS `mdt_apply`;

CREATE TABLE `mdt_apply` (
  `id` int(11) NOT NULL COMMENT 'MDT申请表',
  `patient_type` varchar(1) NOT NULL COMMENT '患者类型 1住院 2门诊',
  `name` varchar(255) NOT NULL COMMENT '姓名',
  `gender` varchar(1) NOT NULL COMMENT '性别',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `phone` varchar(255) DEFAULT NULL COMMENT '联系电话',
  `diagnose_date` date DEFAULT NULL COMMENT '入院/首诊时间',
  `number` varchar(100) DEFAULT NULL COMMENT '住院/门诊号',
  `picture` varchar(255) DEFAULT NULL COMMENT '嘉和电子病历用户截图',
  `overview` varchar(2048) DEFAULT NULL COMMENT '病情概述（含主诉、病史、诊断、诊治过程等）',
  `survey_report` varchar(255) DEFAULT NULL COMMENT '检验报告',
  `inspection_report` varchar(255) DEFAULT NULL COMMENT '检查报告',
  `mdt_date` datetime DEFAULT NULL COMMENT 'MDT时间',
  `mdt_location` varchar(255) DEFAULT NULL COMMENT 'MDT地点',
  `disease_name` varchar(255) DEFAULT NULL COMMENT '病种名称',
  `other_disease_name` varchar(255) DEFAULT NULL COMMENT '病种名称其它',
  `mdt_purpose` varchar(255) DEFAULT NULL COMMENT 'MDT目的',
  `other_mdt_purpose` varchar(255) DEFAULT NULL COMMENT 'MDT目的其它',
  `difficulty` varchar(255) DEFAULT NULL COMMENT '诊治难点',
  `is_charge` varchar(255) DEFAULT NULL COMMENT '是否收费 (1:是 0:否)',
  `apply_person` varchar(255) DEFAULT NULL COMMENT '申请人',
  `apply_createtime` datetime DEFAULT NULL COMMENT '申请递交时间',
  `apply_phone` varchar(255) DEFAULT NULL COMMENT '申请人电话',
  `apply_status` varchar(255) DEFAULT NULL COMMENT '申请状态',
  `share` varchar(1) NOT NULL COMMENT '是否分享该病例 1是 0否',
  `summary` varchar(4000) DEFAULT NULL COMMENT '小结',
  `created_userid` int(11) NOT NULL COMMENT '创建人id',
  `created_deptid` varchar(255) NOT NULL COMMENT '创建人科室',
  `is_delete` varchar(1) NOT NULL COMMENT '是否删除 1是 0否',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  `apply_person_id` int(11) DEFAULT NULL COMMENT '申请人id',
  `patient_id` bigint(20) DEFAULT NULL COMMENT '患者id',
  `idcard` varchar(32) DEFAULT NULL COMMENT '身份证',
  `created_orgid` varchar(20) DEFAULT NULL COMMENT '所属机构',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Data for the table `mdt_apply` */

insert  into `mdt_apply`(`id`,`patient_type`,`name`,`gender`,`birthday`,`phone`,`diagnose_date`,`number`,`picture`,`overview`,`survey_report`,`inspection_report`,`mdt_date`,`mdt_location`,`disease_name`,`other_disease_name`,`mdt_purpose`,`other_mdt_purpose`,`difficulty`,`is_charge`,`apply_person`,`apply_createtime`,`apply_phone`,`apply_status`,`share`,`summary`,`created_userid`,`created_deptid`,`is_delete`,`created_time`,`updated_time`,`apply_person_id`,`patient_id`,`idcard`,`created_orgid`) values (201,'1','姓名1','1','2019-07-04','11','2019-07-09','111',NULL,'1111',NULL,NULL,'2019-07-08 05:22:46','1111','病种名称',NULL,'1,5,','其他 ','诊治难点','1','22','2019-07-09 05:23:03','33','3','1','申请小结1',14,'1102','0','2019-07-09 17:23:08','2019-07-12 20:20:33',NULL,NULL,NULL,NULL),(301,'1','姓名','1','2019-07-10','11','2019-07-15','1111',NULL,'1111',NULL,NULL,'2019-07-15 08:38:13','MDT地点','病种名称',NULL,'5,','目的','目的','1','11','2019-07-15 08:38:41','222','1','0',NULL,14,'1102','0','2019-07-15 08:38:49','2019-07-15 11:05:15',NULL,NULL,NULL,NULL),(601,'1','姓名','1','2019-07-04','11','2019-07-03','1223',NULL,'病情概述',NULL,NULL,'2019-07-17 10:25:11','MDT地点','病种名称',NULL,'1,','','诊治难点','1','申请人','2019-07-17 10:25:40','111','3','0',NULL,14,'1102','0','2019-07-17 10:25:50','2019-07-17 10:29:12',NULL,NULL,NULL,NULL),(905,'2','洪小放','男','1962-06-01','','2019-07-19','1',NULL,'病史：病历史\r\n体检：7\r\n处理：1\r\n初步诊断：初步诊断内容',NULL,NULL,'2019-07-19 02:47:32','代付','',NULL,'1,5,','','','2','14','2019-07-19 02:35:56','178282827171','1','0',NULL,14,'1102','0','2019-07-19 14:36:02','2019-07-19 15:01:14',NULL,NULL,NULL,NULL),(1001,'1','洪小放','男','1962-06-01','1344444444','2019-07-20','1',NULL,'病史：病历史\r\n体检：7\r\n处理：1\r\n初步诊断：初步诊断内容',NULL,NULL,'2019-07-20 10:04:31','会议室','',NULL,'1,5,','的地方','大师傅','1','普通用户','2019-07-20 10:04:14','178282827171','1','0',NULL,14,'1102','0','2019-07-20 10:05:04','2019-07-20 10:06:34',NULL,NULL,NULL,NULL),(1002,'1','洪小放','男','1962-06-01','',NULL,'1',NULL,'病史：病历史\r\n体检：7\r\n处理：1\r\n初步诊断：初步诊断内容',NULL,NULL,NULL,'','',NULL,'','','',NULL,'普通用户','2019-07-20 10:08:50','178282827171','0','0',NULL,14,'1102','0','2019-07-20 10:09:09','2019-07-20 10:09:09',NULL,NULL,NULL,NULL),(1003,'1','洪小放','男','1962-06-01','',NULL,'1',NULL,'病史：病历史\r\n体检：7\r\n处理：1\r\n初步诊断：初步诊断内容',NULL,NULL,NULL,'','',NULL,'','','',NULL,'普通用户','2019-07-20 10:09:24','178282827171','0','0',NULL,14,'1102','0','2019-07-20 10:09:30','2019-07-20 10:09:30',NULL,NULL,NULL,NULL),(1005,'1','洪小放','男','1962-06-01','',NULL,'1',NULL,'病史：病历史\r\n体检：7\r\n处理：1\r\n初步诊断：初步诊断内容',NULL,NULL,NULL,'','',NULL,'','','',NULL,'普通用户','2019-07-20 10:13:09','178282827171','0','0',NULL,14,'1102','0','2019-07-20 10:13:13','2019-07-20 10:13:13',NULL,NULL,NULL,NULL),(1006,'1','洪小放','男','1962-06-01','',NULL,'1',NULL,'病史：病历史\r\n体检：7\r\n处理：1\r\n初步诊断：初步诊断内容',NULL,NULL,NULL,'','',NULL,'','','',NULL,'普通用户','2019-07-20 10:13:32','178282827171','0','0',NULL,14,'1102','0','2019-07-20 10:13:36','2019-07-20 10:13:36',NULL,NULL,NULL,'11'),(1009,'1','洪小放','男','1962-06-01','13222223333','2019-07-20','1',NULL,'病史：病历史\r\n体检：7\r\n处理：1\r\n初步诊断：初步诊断内容',NULL,NULL,'2019-07-20 10:18:50','的发生','打法阿道夫',NULL,'1,5,','','的地方','1','普通用户','2019-07-20 10:18:16','178282827171','13','1','专家专家3意见:222\r\n专家专家1意见:33333\r\n11',14,'1102','0','2019-07-20 10:18:21','2019-07-20 21:48:00',14,1,'220101201101010921','11'),(1402,'1','洪小放','男','1962-06-01','134566666','2019-07-20','1',NULL,'病史：病历史\r\n体检：7\r\n处理：1\r\n初步诊断：初步诊断内容',NULL,NULL,'2019-07-20 14:08:12','对方的','为v',NULL,'1,4,5,','','代付','2','普通用户','2019-07-20 14:07:37','178282827171','0','0',NULL,14,'1102','0','2019-07-20 14:08:41','2019-07-20 14:08:41',14,1,'220101201101010921','11'),(1701,'1','洪小放','男','1962-06-01','1244','2019-07-21','1',NULL,'病史：病历史\r\n体检：7\r\n处理：1\r\n初步诊断：初步诊断内容',NULL,NULL,'2019-07-21 21:13:05','11','daf',NULL,'1,2,','','dafd','1','普通用户','2019-07-21 21:12:50','178282827171','13','1','专家普通用户意见:1\r\n专家专家1意见:22222\r\n',14,'1102','0','2019-07-21 21:13:24','2019-07-21 21:29:17',14,1,'220101201101010921','11');

/*Table structure for table `mdt_apply_doctor` */

DROP TABLE IF EXISTS `mdt_apply_doctor`;

CREATE TABLE `mdt_apply_doctor` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'MDT参加专家表',
  `apply_id` int(11) NOT NULL COMMENT 'MDT申请id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `name` varchar(255) NOT NULL COMMENT '专家姓名',
  `department` varchar(255) NOT NULL COMMENT '科室',
  `title` varchar(255) NOT NULL COMMENT '职称',
  `phone` varchar(20) NOT NULL COMMENT '手机号',
  `phone_cornet` varchar(255) NOT NULL COMMENT '手机短号',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Data for the table `mdt_apply_doctor` */

insert  into `mdt_apply_doctor`(`id`,`apply_id`,`user_id`,`name`,`department`,`title`,`phone`,`phone_cornet`,`created_time`,`updated_time`) values (23,2,1,'姓名12','1102','医师','13200000000','1253','2019-06-29 12:02:39','2019-06-29 12:02:39'),(24,2,3,'测试二','1102','医师','13300000000','1241','2019-06-29 12:02:41','2019-06-29 12:02:41'),(25,3,1,'姓名12','1102','医师','13200000000','1253','2019-06-29 12:37:20','2019-06-29 12:37:20'),(26,3,3,'测试二','1102','医师','13300000000','1241','2019-06-29 12:37:22','2019-06-29 12:37:22'),(27,4,15,'专家1','1102','职称','178282827171','81721','2019-07-03 17:49:00','2019-07-03 17:49:00'),(28,4,16,'专家2','1102','职称','178282827171','81721','2019-07-03 17:49:02','2019-07-03 17:49:02'),(29,5,15,'专家1','1102','职称','178282827171','81721','2019-07-05 10:08:27','2019-07-05 10:08:27'),(30,5,1,'姓名12','1102','医师','13200000000','1253','2019-07-05 10:09:27','2019-07-05 10:09:27'),(31,5,3,'测试二','1102','医师','13300000000','1241','2019-07-05 10:09:29','2019-07-05 10:09:29'),(32,201,17,'专家3','1102','职称','178282827171','81721','2019-07-09 17:22:24','2019-07-09 17:22:24'),(33,201,16,'专家2','1102','职称','178282827171','81721','2019-07-09 17:22:31','2019-07-09 17:22:31'),(34,301,17,'专家3','1102','职称','178282827171','81721','2019-07-15 08:38:32','2019-07-15 08:38:32'),(35,301,15,'专家1','1102','职称','178282827171','81721','2019-07-15 08:38:33','2019-07-15 08:38:33'),(37,201,15,'专家1','1102','职称','178282827171','81721','2019-07-16 11:37:44','2019-07-16 11:37:44'),(38,501,17,'专家3','1102','职称','178282827171','81721','2019-07-17 09:42:27','2019-07-17 09:42:27'),(39,601,17,'专家3','1102','职称','178282827171','81721','2019-07-17 10:25:28','2019-07-17 10:25:28'),(40,701,17,'专家3','1102','职称','178282827171','81721','2019-07-19 09:36:58','2019-07-19 09:36:58'),(41,701,15,'专家1','1102','职称','178282827171','81721','2019-07-19 09:37:00','2019-07-19 09:37:00'),(42,717,17,'专家3','1102','职称','178282827171','81721','2019-07-19 10:28:04','2019-07-19 10:28:04'),(43,801,17,'专家3','1102','职称','178282827171','81721','2019-07-19 13:32:25','2019-07-19 13:32:25'),(44,801,15,'专家1','1102','职称','178282827171','81721','2019-07-19 13:32:27','2019-07-19 13:32:27'),(45,802,17,'专家3','110301','职称','178282827171','81721','2019-07-19 13:34:41','2019-07-19 13:34:41'),(46,802,16,'专家2','1102','职称','178282827171','81721','2019-07-19 13:35:38','2019-07-19 13:35:38'),(47,802,1,'医务部主任','1102','医师','13200000000','1253','2019-07-19 13:38:27','2019-07-19 13:38:27'),(48,803,3,'科室主任','1102','医师','13300000000','1241','2019-07-19 13:41:06','2019-07-19 13:41:06'),(49,804,1,'医务部主任','1102','医师','13200000000','1253','2019-07-19 13:43:18','2019-07-19 13:43:18'),(50,804,17,'专家3','1102','职称','178282827171','81721','2019-07-19 13:43:50','2019-07-19 13:43:50'),(51,806,3,'科室主任','1102','医师','13300000000','1241','2019-07-19 13:45:28','2019-07-19 13:45:28'),(52,807,17,'专家3','1102','职称','178282827171','81721','2019-07-19 13:47:14','2019-07-19 13:47:14'),(53,807,14,'普通用户','1102','职称','178282827171','81721','2019-07-19 13:47:21','2019-07-19 13:47:21'),(54,807,3,'科室主任','1102','医师','13300000000','1241','2019-07-19 13:47:31','2019-07-19 13:47:31'),(55,807,18,'分管院长','1102','医师','13300000000','1241','2019-07-19 13:48:53','2019-07-19 13:48:53'),(56,808,17,'专家3','1102','职称','178282827171','81721','2019-07-19 13:49:54','2019-07-19 13:49:54'),(57,808,15,'专家1','1102','职称','178282827171','81721','2019-07-19 13:49:56','2019-07-19 13:49:56'),(58,809,1,'医务部主任','1102','医师','13200000000','1253','2019-07-19 13:54:56','2019-07-19 13:54:56'),(59,809,3,'科室主任','1102','医师','13300000000','1241','2019-07-19 13:55:24','2019-07-19 13:55:24'),(60,810,14,'普通用户','1102','职称','178282827171','81721','2019-07-19 13:57:44','2019-07-19 13:57:44'),(61,817,3,'科室主任','1102','医师','13300000000','1241','2019-07-19 14:04:18','2019-07-19 14:04:18'),(62,820,1,'医务部主任','1102','医师','13200000000','1253','2019-07-19 14:06:21','2019-07-19 14:06:21'),(63,820,16,'专家2','1102','职称','178282827171','81721','2019-07-19 14:06:25','2019-07-19 14:06:25'),(64,820,17,'专家3','1102','职称','178282827171','81721','2019-07-19 14:06:33','2019-07-19 14:06:33'),(65,820,15,'专家1','1102','职称','178282827171','81721','2019-07-19 14:06:35','2019-07-19 14:06:35'),(66,823,3,'科室主任','1102','医师','13300000000','1241','2019-07-19 14:09:17','2019-07-19 14:09:17'),(67,823,17,'专家3','110301','职称','178282827171','81721','2019-07-19 14:09:22','2019-07-19 14:09:22'),(68,824,3,'科室主任','1102','医师','13300000000','1241','2019-07-19 14:18:36','2019-07-19 14:18:36'),(69,901,3,'科室主任','1102','医师','13300000000','1241','2019-07-19 14:20:00','2019-07-19 14:20:00'),(70,905,1,'医务部主任','1102','医师','13200000000','1253','2019-07-19 14:37:25','2019-07-19 14:37:25'),(71,905,16,'专家2','1102','职称','178282827171','81721','2019-07-19 14:37:30','2019-07-19 14:37:30'),(72,1001,3,'科室主任','1102','医师','13300000000','1241','2019-07-20 10:04:40','2019-07-20 10:04:40'),(73,1001,15,'专家1','1102','职称','178282827171','81721','2019-07-20 10:04:45','2019-07-20 10:04:45'),(74,1009,17,'专家3','1102','职称','178282827171','81721','2019-07-20 10:19:02','2019-07-20 10:19:02'),(75,1009,15,'专家1','1102','职称','178282827171','81721','2019-07-20 10:19:03','2019-07-20 10:19:03'),(76,1402,1,'医务部主任','1102','医师','13200000000','1253','2019-07-20 14:08:27','2019-07-20 14:08:27'),(77,1402,14,'普通用户','1102','职称','178282827171','81721','2019-07-20 14:08:30','2019-07-20 14:08:30'),(78,1501,3,'科室主任','1102','医师','13300000000','1241','2019-07-21 21:07:23','2019-07-21 21:07:23'),(79,1501,15,'专家1','1102','职称','178282827171','81721','2019-07-21 21:07:28','2019-07-21 21:07:28'),(80,1601,14,'普通用户','1102','职称','178282827171','81721','2019-07-21 21:10:15','2019-07-21 21:10:15'),(81,1601,15,'专家1','1102','职称','178282827171','81721','2019-07-21 21:10:19','2019-07-21 21:10:19'),(82,1701,14,'普通用户','1102','职称','178282827171','81721','2019-07-21 21:13:13','2019-07-21 21:13:13'),(83,1701,15,'专家1','1102','职称','178282827171','81721','2019-07-21 21:13:15','2019-07-21 21:13:15');

/*Table structure for table `mdt_apply_feedback` */

DROP TABLE IF EXISTS `mdt_apply_feedback`;

CREATE TABLE `mdt_apply_feedback` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'MDT随访反馈表',
  `apply_id` int(11) NOT NULL COMMENT 'MDT申请id',
  `mdt_bedore_diagnosis` varchar(255) NOT NULL COMMENT 'MDT前诊断',
  `mdt_after_diagnosis` varchar(255) NOT NULL COMMENT 'MDT后诊断',
  `outcome` varchar(255) NOT NULL COMMENT '转归',
  `treatment_change` varchar(20) NOT NULL COMMENT 'MDT前后治疗变化',
  `opinion` varchar(255) NOT NULL COMMENT '对MDT中心的工作有何建议和意见',
  `visit_name` varchar(255) NOT NULL COMMENT '随访人姓名',
  `visit_phone` varchar(255) NOT NULL COMMENT '随访人电话',
  `visit_time` datetime NOT NULL COMMENT '随访日期',
  `share` varchar(1) DEFAULT NULL COMMENT '是否分享病例 1是 0否',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Data for the table `mdt_apply_feedback` */

insert  into `mdt_apply_feedback`(`id`,`apply_id`,`mdt_bedore_diagnosis`,`mdt_after_diagnosis`,`outcome`,`treatment_change`,`opinion`,`visit_name`,`visit_phone`,`visit_time`,`share`,`created_time`,`updated_time`) values (33,4,'MDT前诊断','MDT后诊断','2','MDT前后治疗变化','对MDT中心的工作有何建议和意见','随访姓名','患者姓名','2019-07-07 11:00:26',NULL,'2019-07-07 11:00:47','2019-07-07 11:00:47'),(34,201,'MDT前诊断','MDT后诊断','1','MDT前后治疗变化','意见','姓名','111','2019-07-09 09:39:39','1','2019-07-09 21:39:45','2019-07-09 21:39:58'),(35,201,'诊断','诊断','1','1','2','3','1','2019-07-12 20:18:32','1','2019-07-12 20:20:33','2019-07-12 20:20:33'),(36,1009,'打法','111','1','22','11','订单','134567788','2019-07-20 21:47:48','0','2019-07-20 21:48:00','2019-07-20 21:48:00'),(37,1701,'11','22','1','22','22','22','112344','2019-07-21 09:29:08','1','2019-07-21 21:29:17','2019-07-21 21:29:43');

/*Table structure for table `mdt_apply_opinion` */

DROP TABLE IF EXISTS `mdt_apply_opinion`;

CREATE TABLE `mdt_apply_opinion` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'MDT专家意见表',
  `apply_id` int(11) NOT NULL COMMENT 'MDT申请id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `content` varchar(4000) DEFAULT NULL COMMENT '内容',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Data for the table `mdt_apply_opinion` */

insert  into `mdt_apply_opinion`(`id`,`apply_id`,`user_id`,`content`,`created_time`,`updated_time`) values (45,601,17,'11111111111','2019-07-17 23:00:59','2019-07-17 23:00:59'),(46,1009,17,'222','2019-07-20 20:07:31','2019-07-20 20:58:56'),(47,1009,15,'33333','2019-07-20 20:07:31','2019-07-20 20:58:56'),(48,1701,14,'1','2019-07-21 21:28:14','2019-07-21 21:28:14'),(49,1701,15,'22222','2019-07-21 21:28:14','2019-07-21 21:28:14');

/*Table structure for table `mdt_grade_item` */

DROP TABLE IF EXISTS `mdt_grade_item`;

CREATE TABLE `mdt_grade_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评分表',
  `type` varchar(1) NOT NULL COMMENT '1:专家打分 2:组织科室打分',
  `apply_id` int(11) NOT NULL COMMENT 'MDT申请id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `sys_grade_id` int(11) NOT NULL COMMENT '评分维护表id',
  `grade` int(11) NOT NULL COMMENT '打分分数',
  `min_value` int(11) NOT NULL COMMENT '最小分值',
  `max_value` int(11) NOT NULL COMMENT '最大分值',
  `description` varchar(255) NOT NULL COMMENT '用户名',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=155 DEFAULT CHARSET=utf8;

/*Data for the table `mdt_grade_item` */

insert  into `mdt_grade_item`(`id`,`type`,`apply_id`,`user_id`,`sys_grade_id`,`grade`,`min_value`,`max_value`,`description`,`created_time`,`updated_time`) values (26,'1',2,2,1,1,1,5,'申请会诊必要性','2019-07-03 09:18:47','2019-07-03 09:18:47'),(27,'1',2,2,2,2,1,5,'会诊申请书目的明确','2019-07-03 09:18:47','2019-07-03 09:18:47'),(28,'1',2,2,3,3,1,5,'病情现况资料详尽','2019-07-03 09:18:47','2019-07-03 09:18:47'),(29,'1',2,2,4,4,1,5,'会诊书写规范、详细','2019-07-03 09:18:47','2019-07-03 09:18:47'),(30,'1',2,2,5,5,1,5,'会诊意见解决问题','2019-07-03 09:18:48','2019-07-03 09:18:48'),(35,'2',24,3,6,2,1,5,'会诊及时性','2019-07-03 09:26:55','2019-07-03 09:26:55'),(36,'2',24,3,7,3,1,5,'会诊意见可行性','2019-07-03 09:26:55','2019-07-03 09:26:55'),(37,'2',23,1,6,5,1,5,'会诊及时性','2019-07-03 09:27:09','2019-07-03 09:27:09'),(38,'2',23,1,7,5,1,5,'会诊意见可行性','2019-07-03 09:27:09','2019-07-03 09:27:09'),(39,'1',2,3,1,2,1,5,'申请会诊必要性','2019-07-03 11:45:26','2019-07-03 11:45:26'),(40,'1',2,3,2,3,1,5,'会诊申请书目的明确','2019-07-03 11:45:26','2019-07-03 11:45:26'),(41,'1',2,3,3,4,1,5,'病情现况资料详尽','2019-07-03 11:45:26','2019-07-03 11:45:26'),(42,'1',2,3,4,5,1,5,'会诊书写规范、详细','2019-07-03 11:45:26','2019-07-03 11:45:26'),(43,'1',2,3,5,6,1,5,'会诊意见解决问题','2019-07-03 11:45:26','2019-07-03 11:45:26'),(44,'1',201,16,1,1,1,5,'申请会诊必要性','2019-07-11 10:06:35','2019-07-11 10:06:35'),(45,'1',201,16,2,2,1,5,'会诊申请书目的明确','2019-07-11 10:06:35','2019-07-11 10:06:35'),(46,'1',201,16,3,3,1,5,'病情现况资料详尽','2019-07-11 10:06:35','2019-07-11 10:06:35'),(47,'1',201,16,4,4,1,5,'会诊书写规范、详细','2019-07-11 10:06:35','2019-07-11 10:06:35'),(48,'1',201,16,5,5,1,5,'会诊意见解决问题','2019-07-11 10:06:35','2019-07-11 10:06:35'),(51,'2',39,17,6,1,1,5,'会诊及时性','2019-07-17 22:44:35','2019-07-17 22:44:35'),(52,'2',39,17,7,2,1,5,'会诊意见可行性','2019-07-17 22:44:35','2019-07-17 22:44:35'),(57,'2',601,17,6,1,1,5,'会诊及时性','2019-07-17 23:00:59','2019-07-17 23:00:59'),(58,'2',601,17,7,2,1,5,'会诊意见可行性','2019-07-17 23:00:59','2019-07-17 23:00:59'),(87,'2',1009,17,6,2,1,5,'会诊及时性','2019-07-20 20:58:56','2019-07-20 20:58:56'),(88,'2',1009,17,7,5,1,5,'会诊意见可行性','2019-07-20 20:58:56','2019-07-20 20:58:56'),(89,'2',1009,15,6,4,1,5,'会诊及时性','2019-07-20 20:58:56','2019-07-20 20:58:56'),(90,'2',1009,15,7,5,1,5,'会诊意见可行性','2019-07-20 20:58:56','2019-07-20 20:58:56'),(131,'1',1009,17,1,4,1,5,'申请会诊必要性','2019-07-20 21:32:09','2019-07-20 21:32:09'),(132,'1',1009,17,2,5,1,5,'会诊申请书目的明确','2019-07-20 21:32:09','2019-07-20 21:32:09'),(133,'1',1009,17,3,4,1,5,'病情现况资料详尽','2019-07-20 21:32:09','2019-07-20 21:32:09'),(134,'1',1009,17,4,4,1,5,'会诊书写规范、详细','2019-07-20 21:32:09','2019-07-20 21:32:09'),(135,'1',1009,17,5,5,1,5,'会诊意见解决问题','2019-07-20 21:32:09','2019-07-20 21:32:09'),(136,'1',1009,15,1,5,1,5,'申请会诊必要性','2019-07-20 21:32:09','2019-07-20 21:32:09'),(137,'1',1009,15,2,5,1,5,'会诊申请书目的明确','2019-07-20 21:32:09','2019-07-20 21:32:09'),(138,'1',1009,15,3,4,1,5,'病情现况资料详尽','2019-07-20 21:32:09','2019-07-20 21:32:09'),(139,'1',1009,15,4,5,1,5,'会诊书写规范、详细','2019-07-20 21:32:09','2019-07-20 21:32:09'),(140,'1',1009,15,5,5,1,5,'会诊意见解决问题','2019-07-20 21:32:09','2019-07-20 21:32:09'),(141,'2',1701,14,6,3,1,5,'会诊及时性','2019-07-21 21:28:14','2019-07-21 21:28:14'),(142,'2',1701,14,7,5,1,5,'会诊意见可行性','2019-07-21 21:28:14','2019-07-21 21:28:14'),(143,'2',1701,15,6,4,1,5,'会诊及时性','2019-07-21 21:28:14','2019-07-21 21:28:14'),(144,'2',1701,15,7,4,1,5,'会诊意见可行性','2019-07-21 21:28:14','2019-07-21 21:28:14'),(145,'1',1701,14,1,5,1,5,'申请会诊必要性','2019-07-21 21:28:27','2019-07-21 21:28:27'),(146,'1',1701,14,2,5,1,5,'会诊申请书目的明确','2019-07-21 21:28:27','2019-07-21 21:28:27'),(147,'1',1701,14,3,4,1,5,'病情现况资料详尽','2019-07-21 21:28:27','2019-07-21 21:28:27'),(148,'1',1701,14,4,4,1,5,'会诊书写规范、详细','2019-07-21 21:28:27','2019-07-21 21:28:27'),(149,'1',1701,14,5,5,1,5,'会诊意见解决问题','2019-07-21 21:28:27','2019-07-21 21:28:27'),(150,'1',1701,15,1,5,1,5,'申请会诊必要性','2019-07-21 21:28:27','2019-07-21 21:28:27'),(151,'1',1701,15,2,4,1,5,'会诊申请书目的明确','2019-07-21 21:28:27','2019-07-21 21:28:27'),(152,'1',1701,15,3,5,1,5,'病情现况资料详尽','2019-07-21 21:28:27','2019-07-21 21:28:27'),(153,'1',1701,15,4,5,1,5,'会诊书写规范、详细','2019-07-21 21:28:27','2019-07-21 21:28:27'),(154,'1',1701,15,5,5,1,5,'会诊意见解决问题','2019-07-21 21:28:27','2019-07-21 21:28:27');

/*Table structure for table `mdt_team` */

DROP TABLE IF EXISTS `mdt_team`;

CREATE TABLE `mdt_team` (
  `id` int(11) NOT NULL COMMENT 'MDT团队表',
  `proposer` varchar(255) NOT NULL COMMENT '申请人',
  `name` varchar(255) NOT NULL COMMENT 'MDT名称',
  `date` date NOT NULL COMMENT '申请日期',
  `standard` varchar(255) DEFAULT NULL COMMENT 'MDT病种纳入标准和诊疗规范（指南）',
  `annual_status` varchar(1) NOT NULL COMMENT '年度审核状态 1:发起 2:专家填写 3:审核完成',
  `two_year_status` varchar(1) NOT NULL COMMENT '满两年度审核状态 0:未发起 1:发起 2:专家填写 3:审核通过 4:审核不通过',
  `audit_status` varchar(1) DEFAULT NULL COMMENT '审核状态 (0:未提交 1:已提交未审核 2:科主任已审核 3:医务部主任已审核 4:分管院长已审核)',
  `created_userid` int(11) NOT NULL COMMENT '创建用户id',
  `created_deptid` varchar(255) NOT NULL COMMENT '创建科室',
  `is_delete` varchar(1) NOT NULL COMMENT '是否删除  1是 0否',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  `created_orgid` varchar(20) DEFAULT NULL COMMENT '所属机构',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Data for the table `mdt_team` */

insert  into `mdt_team`(`id`,`proposer`,`name`,`date`,`standard`,`annual_status`,`two_year_status`,`audit_status`,`created_userid`,`created_deptid`,`is_delete`,`created_time`,`updated_time`,`created_orgid`) values (7,'王也','病毒性肝炎','2019-06-20','规范指南','1','1','1',1,'1','0','2019-06-28 18:07:04','2019-07-15 10:53:57',NULL),(8,' 申请人','细胞癌','2019-06-28','指南','0','0','0',1,'1','1','2019-06-28 20:58:33','2019-07-03 17:21:19',NULL),(9,' 申请人2','急性肺炎','2019-06-28','1111','1','1','2',1,'1','0','2019-06-28 21:02:44','2019-07-14 22:20:41',NULL),(10,'普通用户','222','2019-07-03','222','1','0','3',14,'1102','0','2019-07-03 17:13:34','2019-07-08 08:26:17',NULL),(11,' 申请人','111','2019-07-05','111','2','0','1',14,'1102','0','2019-07-05 09:54:16','2019-07-15 11:55:52',NULL),(108,'申请人','MDT名称','2019-07-09','指南','1','0','0',11,'1102','0','2019-07-09 11:14:09','2019-07-10 17:18:54','11'),(201,' 申请人','MDT名称','2019-07-09','指南','1','0','0',14,'1102','0','2019-07-09 11:23:39','2019-07-11 15:10:56','11'),(301,' 申请人','MDT名称','2019-07-09','指南','1','0','0',14,'1102','1','2019-07-09 11:30:48','2019-07-21 12:30:27','11'),(401,'申请人2','MDT名称2','2019-07-09','指南334','1','2','4',14,'1102','0','2019-07-09 11:34:02','2019-07-12 20:05:10','11'),(1110,'普通用户','范德萨','2019-07-21','的','3','3','4',14,'1102','0','2019-07-21 10:37:21','2019-07-21 16:09:03','11'),(1901,'普通用户','22','2019-07-21','22','3','3','3',14,'1102','0','2019-07-21 20:27:07','2019-07-21 20:44:03','11');

/*Table structure for table `mdt_team_assess` */

DROP TABLE IF EXISTS `mdt_team_assess`;

CREATE TABLE `mdt_team_assess` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'MDT团队建设期满2年评估表',
  `team_id` int(11) NOT NULL COMMENT '团队id',
  `case_sum` int(11) NOT NULL COMMENT '病例总数',
  `year1` int(11) NOT NULL COMMENT '年度1',
  `case_num1` int(11) NOT NULL COMMENT '年度1病例数',
  `year2` int(11) NOT NULL COMMENT '年度2',
  `case_num2` int(11) NOT NULL COMMENT '年度2病例数',
  `teaching_situation` varchar(255) NOT NULL COMMENT '建期两年教学情况',
  `train_num` int(11) NOT NULL COMMENT '培养研究生数量',
  `hospital_num` int(11) DEFAULT NULL COMMENT '住院医生数量',
  `major_num` int(11) DEFAULT NULL COMMENT '主治医生数量',
  `assistant_major_num` int(11) DEFAULT NULL COMMENT '副主任医生数量',
  `director_num` int(11) DEFAULT NULL COMMENT '主任医生数量',
  `created_userid` int(11) DEFAULT NULL,
  `created_deptid` varchar(255) DEFAULT NULL,
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Data for the table `mdt_team_assess` */

insert  into `mdt_team_assess`(`id`,`team_id`,`case_sum`,`year1`,`case_num1`,`year2`,`case_num2`,`teaching_situation`,`train_num`,`hospital_num`,`major_num`,`assistant_major_num`,`director_num`,`created_userid`,`created_deptid`,`created_time`,`updated_time`) values (24,7,1,2,2,2,2,'建期两年教学情况',1,1,1,1,1,NULL,NULL,'2019-07-08 15:45:41','2019-07-08 17:23:12'),(26,401,22,2019,11,2020,11,'建期两年教学情况',4,1,1,1,1,NULL,NULL,'2019-07-10 18:24:25','2019-07-10 18:24:25'),(27,1110,12,2019,1,2020,11,'22',12,1,2,1,8,14,'1102','2019-07-21 15:52:18','2019-07-21 16:09:03'),(28,1901,11,2019,12,2020,2,'111',1,1,1,1,1,14,'1102','2019-07-21 20:43:42','2019-07-21 20:44:03');

/*Table structure for table `mdt_team_info` */

DROP TABLE IF EXISTS `mdt_team_info`;

CREATE TABLE `mdt_team_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'MDT团队基本信息表（多人明细）',
  `team_id` int(11) NOT NULL COMMENT '团队id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `name` varchar(255) NOT NULL COMMENT '专家姓名',
  `department` varchar(255) NOT NULL COMMENT '科室',
  `title` varchar(255) NOT NULL COMMENT '职称',
  `phone` varchar(20) NOT NULL COMMENT '手机号',
  `phone_cornet` varchar(255) NOT NULL COMMENT '手机短号',
  `specialist_type` varchar(1) NOT NULL COMMENT '专家类型',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Data for the table `mdt_team_info` */

insert  into `mdt_team_info`(`id`,`team_id`,`user_id`,`name`,`department`,`title`,`phone`,`phone_cornet`,`specialist_type`,`created_time`,`updated_time`) values (13,7,1,'姓名12','1102','医师','13200000000','1253','1','2019-06-28 19:03:51','2019-07-04 11:28:08'),(14,7,3,'测试二','1102','医师','13300000000','1241','1','2019-06-28 19:07:01','2019-06-28 19:07:01'),(15,9,3,'测试二','1102','医师','13300000000','1241','1','2019-06-28 21:37:44','2019-06-28 21:37:44'),(16,9,1,'姓名12','1102','医师','13200000000','1253','1','2019-06-28 21:37:57','2019-06-28 21:37:57'),(17,10,15,'专家1','1102','职称','178282827171','81721','1','2019-07-03 17:20:36','2019-07-03 17:20:36'),(18,10,16,'专家2','1102','职称','178282827171','81721','1','2019-07-03 17:20:43','2019-07-03 17:20:43'),(19,7,17,'专家3','1102','职称','178282827171','81721','1','2019-07-05 09:46:32','2019-07-05 09:46:32'),(20,7,16,'专家2','1102','职称','178282827171','81721','1','2019-07-05 09:47:07','2019-07-05 09:47:07'),(21,11,15,'专家1','1102','职称','178282827171','81721','1','2019-07-05 09:54:21','2019-07-05 09:54:21'),(22,11,16,'专家2','1102','职称','178282827171','81721','1','2019-07-05 09:54:24','2019-07-05 09:54:24'),(23,11,18,'分管院长','1102','医师','13300000000','1241','1','2019-07-05 10:49:17','2019-07-05 10:49:17'),(24,102,15,'专家1','1102','职称','178282827171','81721','1','2019-07-09 10:59:18','2019-07-09 10:59:18'),(25,102,16,'专家2','1102','职称','178282827171','81721','1','2019-07-09 10:59:27','2019-07-09 10:59:27'),(27,106,15,'专家1','1102','职称','178282827171','81721','1','2019-07-09 11:03:26','2019-07-09 11:03:26'),(28,106,16,'专家2','1102','职称','178282827171','81721','1','2019-07-09 11:03:30','2019-07-09 11:03:30'),(29,108,15,'专家1','1102','职称','178282827171','81721','1','2019-07-09 11:06:17','2019-07-09 11:06:17'),(30,108,16,'专家2','1102','职称','178282827171','81721','1','2019-07-09 11:06:20','2019-07-09 11:06:20'),(31,201,15,'专家1','1102','职称','178282827171','81721','1','2019-07-09 11:23:23','2019-07-09 11:23:23'),(32,301,15,'专家1','1102','职称','178282827171','81721','1','2019-07-09 11:30:07','2019-07-09 11:30:07'),(33,401,17,'专家3','1102','职称','178282827171','81721','1','2019-07-09 11:33:45','2019-07-09 11:33:45'),(34,401,15,'专家1','1102','职称','178282827171','81721','1','2019-07-09 11:34:57','2019-07-09 11:34:57'),(35,1101,14,'普通用户','1102','职称','178282827171','81721','1','2019-07-21 10:17:12','2019-07-21 10:17:12'),(36,1102,3,'科室主任','1102','医师','13300000000','1241','1','2019-07-21 10:20:17','2019-07-21 10:20:17'),(37,1102,14,'普通用户','1102','职称','178282827171','81721','1','2019-07-21 10:20:24','2019-07-21 10:20:24'),(38,1103,3,'科室主任','1102','医师','13300000000','1241','1','2019-07-21 10:21:27','2019-07-21 10:21:27'),(39,1103,16,'专家2','1102','职称','178282827171','81721','1','2019-07-21 10:21:32','2019-07-21 10:21:32'),(40,1103,18,'分管院长','1102','医师','13300000000','1241','1','2019-07-21 10:21:42','2019-07-21 10:21:42'),(41,1108,3,'科室主任','1102','医师','13300000000','1241','1','2019-07-21 10:28:56','2019-07-21 10:28:56'),(42,1108,14,'普通用户','1102','职称','178282827171','81721','1','2019-07-21 10:28:59','2019-07-21 10:28:59'),(43,1110,14,'普通用户','1102','职称','178282827171','81721','1','2019-07-21 10:30:26','2019-07-21 10:30:26'),(44,1110,1,'医务部主任','1102','医师','13200000000','1253','1','2019-07-21 10:30:30','2019-07-21 10:30:30'),(45,1110,15,'专家1','1102','职称','178282827171','81721','1','2019-07-21 10:30:33','2019-07-21 10:30:33'),(47,1701,1,'医务部主任','1102','医师','13200000000','1253','1','2019-07-21 20:19:26','2019-07-21 20:19:26'),(48,1701,15,'专家1','1102','职称','178282827171','81721','3','2019-07-21 20:19:32','2019-07-21 20:19:32'),(49,1801,3,'科室主任','1102','医师','13300000000','1241','1','2019-07-21 20:21:32','2019-07-21 20:21:32'),(50,1801,14,'普通用户','1102','职称','178282827171','81721','1','2019-07-21 20:21:39','2019-07-21 20:21:39'),(51,1901,14,'普通用户','1102','职称','178282827171','81721','1','2019-07-21 20:26:31','2019-07-21 20:26:31'),(52,1901,16,'专家2','1102','职称','178282827171','81721','3','2019-07-21 20:26:42','2019-07-21 20:26:42');

/*Table structure for table `mdt_team_issue` */

DROP TABLE IF EXISTS `mdt_team_issue`;

CREATE TABLE `mdt_team_issue` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'MDT团队课题表',
  `team_id` int(11) NOT NULL COMMENT '团队id',
  `name` varchar(255) NOT NULL COMMENT '项目名称',
  `research_date` date NOT NULL COMMENT '项目研究时间',
  `project_fund` varchar(255) NOT NULL COMMENT '项目经费',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Data for the table `mdt_team_issue` */

insert  into `mdt_team_issue`(`id`,`team_id`,`name`,`research_date`,`project_fund`,`created_time`,`updated_time`) values (25,7,' 项目名称','2019-07-08','10000','2019-07-08 17:23:05','2019-07-08 17:23:05'),(26,401,'名称','2019-07-10','111','2019-07-10 18:07:29','2019-07-10 18:07:29'),(27,1110,'3v','2019-07-21','12','2019-07-21 15:35:46','2019-07-21 15:35:46'),(28,1901,'3','2019-07-16','33','2019-07-21 20:43:34','2019-07-21 20:43:34');

/*Table structure for table `mdt_team_objective` */

DROP TABLE IF EXISTS `mdt_team_objective`;

CREATE TABLE `mdt_team_objective` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'MDT团队建设责任目标',
  `team_id` int(11) NOT NULL COMMENT '团队id',
  `year` varchar(4) NOT NULL COMMENT '年度',
  `year_sum` int(11) NOT NULL COMMENT '年度目标完成例次',
  `papers_num` int(11) DEFAULT NULL COMMENT 'MDT病种相关研究核心期刊论文发表量',
  `other` varchar(255) DEFAULT NULL COMMENT '其他',
  `flag` varchar(2) NOT NULL COMMENT '1:第一年 2:第二年',
  `summary` varchar(2048) DEFAULT NULL COMMENT '上年年度总结',
  `month1` int(11) NOT NULL COMMENT '月度目标指标1',
  `month2` int(11) NOT NULL COMMENT '月度目标指标2',
  `month3` int(11) NOT NULL COMMENT '月度目标指标3',
  `month4` int(11) NOT NULL COMMENT '月度目标指标4',
  `month5` int(11) NOT NULL COMMENT '月度目标指标5',
  `month6` int(11) NOT NULL COMMENT '月度目标指标6',
  `month7` int(11) NOT NULL COMMENT '月度目标指标7',
  `month8` int(11) NOT NULL COMMENT '月度目标指标8',
  `month9` int(11) NOT NULL COMMENT '月度目标指标9',
  `month10` int(11) NOT NULL COMMENT '月度目标指标10',
  `month11` int(11) NOT NULL COMMENT '月度目标指标11',
  `month12` int(11) NOT NULL COMMENT '月度目标指标12',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  `created_deptid` varchar(20) DEFAULT NULL COMMENT '创建科室',
  `created_userid` int(11) DEFAULT NULL COMMENT '创建用户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Data for the table `mdt_team_objective` */

insert  into `mdt_team_objective`(`id`,`team_id`,`year`,`year_sum`,`papers_num`,`other`,`flag`,`summary`,`month1`,`month2`,`month3`,`month4`,`month5`,`month6`,`month7`,`month8`,`month9`,`month10`,`month11`,`month12`,`created_time`,`updated_time`,`created_deptid`,`created_userid`) values (10,7,'2019',24,12,'无','1',NULL,1,2,3,1,2,3,1,2,3,1,2,3,'2019-06-28 18:07:12','2019-07-15 10:53:57',NULL,NULL),(11,8,'2019',12,11,'11','1',NULL,1,1,1,1,1,1,1,1,1,1,1,1,'2019-06-28 20:58:33','2019-06-28 20:58:33',NULL,NULL),(12,9,'2019',12,1,'1','1',NULL,1,1,1,1,1,1,1,1,1,1,1,1,'2019-06-28 21:02:44','2019-06-28 21:02:44',NULL,NULL),(13,10,'2019',12,1,'1','1',NULL,1,1,1,1,1,1,1,1,1,1,1,1,'2019-07-03 17:13:34','2019-07-03 17:20:50',NULL,NULL),(14,11,'2019',12,1,'2','1',NULL,1,1,1,1,1,1,1,1,1,1,1,1,'2019-07-05 09:54:16','2019-07-05 09:54:26',NULL,NULL),(16,7,'2020',12,NULL,NULL,'2',' 年度总结',1,1,1,1,1,1,1,1,1,1,1,1,'2019-07-07 21:55:52','2019-07-07 23:05:37',NULL,NULL),(17,9,'2020',11,NULL,NULL,'2','年度总结',1,1,1,1,1,1,1,1,1,1,1,1,'2019-07-07 22:03:22','2019-07-07 22:03:22',NULL,NULL),(18,11,'2020',12,NULL,NULL,'2','1212',1,1,1,1,1,1,1,1,1,1,1,1,'2019-07-08 08:41:19','2019-07-08 08:41:19',NULL,NULL),(19,32,'2019',12,1,'1','1',NULL,1,1,1,1,1,1,1,1,1,1,1,1,'2019-07-09 11:30:57','2019-07-09 11:30:57',NULL,NULL),(20,401,'2019',12,3,'3','1',NULL,2,1,1,1,1,1,1,1,1,1,1,1,'2019-07-09 11:34:02','2019-07-09 11:42:34',NULL,NULL),(21,401,'2020',12,NULL,NULL,'2','年度总结2',1,1,1,1,1,1,1,1,1,1,1,1,'2019-07-10 17:39:08','2019-07-12 20:06:13',NULL,NULL),(22,1110,'2019',40,12,'','1',NULL,1,4,3,4,5,4,6,5,6,1,0,1,'2019-07-21 10:37:21','2019-07-21 11:26:53','1102',14),(23,1110,'2020',39,NULL,NULL,'2','dasdf',1,2,3,4,5,5,4,1,2,3,4,5,'2019-07-21 14:05:10','2019-07-21 14:54:21','1102',14),(24,1901,'2019',27,112,'22','1',NULL,1,2,3,4,5,6,1,1,1,1,1,1,'2019-07-21 20:27:07','2019-07-21 20:27:07','1102',14),(25,1901,'2020',12,NULL,NULL,'2','多发点',1,1,1,1,1,1,1,1,1,1,1,1,'2019-07-21 20:42:41','2019-07-21 20:42:56','1102',14);

/*Table structure for table `mdt_team_paper` */

DROP TABLE IF EXISTS `mdt_team_paper`;

CREATE TABLE `mdt_team_paper` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'MDT团队论文表',
  `team_id` int(11) NOT NULL COMMENT '团队id',
  `title` varchar(255) NOT NULL COMMENT '论文题目',
  `serial_number` varchar(255) NOT NULL COMMENT '期刊号',
  `posted_date` date NOT NULL COMMENT '发表时间',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Data for the table `mdt_team_paper` */

insert  into `mdt_team_paper`(`id`,`team_id`,`title`,`serial_number`,`posted_date`,`created_time`,`updated_time`) values (25,7,'论文题目','','2019-07-08','2019-07-08 17:22:54','2019-07-08 17:22:54'),(27,401,'11','11','2019-07-24','2019-07-10 18:08:38','2019-07-10 18:08:38'),(29,401,'dd','dd','2019-07-10','2019-07-10 18:16:38','2019-07-10 18:16:38'),(31,1110,'嗡嗡嗡','嗡嗡嗡','2019-07-22','2019-07-21 15:19:13','2019-07-21 15:19:13'),(32,1901,'dd','11','1970-01-01','2019-07-21 20:43:27','2019-07-21 20:43:27');

/*Table structure for table `sys_code` */

DROP TABLE IF EXISTS `sys_code`;

CREATE TABLE `sys_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '字典维护表',
  `type` varchar(1) NOT NULL COMMENT '类型  1:MDT目的',
  `code` varchar(255) NOT NULL COMMENT 'code',
  `value` varchar(255) NOT NULL COMMENT 'value',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `sys_code` */

insert  into `sys_code`(`id`,`type`,`code`,`value`,`create_time`,`update_time`) values (1,'1','1','明确诊断','2019-07-08 18:42:00','2019-07-08 18:42:02'),(2,'1','2','优化治疗方案','2019-07-08 18:42:00','2019-07-08 18:42:02'),(3,'1','3','手术前风险评估与手术期间管理','2019-07-08 18:42:00','2019-07-08 18:42:02'),(4,'1','4','多科协作手术方案讨论','2019-07-08 18:42:00','2019-07-08 18:42:02'),(5,'1','5','其他','2019-07-08 18:42:00','2019-07-08 18:42:02');

/*Table structure for table `sys_fee` */

DROP TABLE IF EXISTS `sys_fee`;

CREATE TABLE `sys_fee` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'MDT收费情况维护表',
  `min` int(11) NOT NULL,
  `max` int(11) DEFAULT NULL,
  `price` int(11) NOT NULL,
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

/*Data for the table `sys_fee` */

insert  into `sys_fee`(`id`,`min`,`max`,`price`,`create_time`,`update_time`) values (9,1,3,500,'2019-07-04 12:08:09','2019-07-04 12:08:09'),(10,4,5,700,'2019-07-04 12:08:16','2019-07-04 12:08:16'),(11,6,NULL,800,'2019-07-04 12:08:49','2019-07-04 12:08:49');

/*Table structure for table `sys_file` */

DROP TABLE IF EXISTS `sys_file`;

CREATE TABLE `sys_file` (
  `id` int(11) NOT NULL COMMENT '文件表',
  `content` varchar(1024) DEFAULT NULL COMMENT '内容',
  `created_userid` int(11) NOT NULL COMMENT '创建人id',
  `created_deptid` varchar(255) NOT NULL COMMENT '创建人科室',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  `title` varchar(128) NOT NULL COMMENT '标题',
  `created_orgid` varchar(20) DEFAULT NULL COMMENT '所属机构',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_file` */

insert  into `sys_file`(`id`,`content`,`created_userid`,`created_deptid`,`created_time`,`updated_time`,`title`,`created_orgid`) values (101,'3222222222222222222',14,'1102','2019-07-21 19:05:55','2019-07-21 19:05:55','33','11'),(201,'aaa',14,'1102','2019-07-21 19:08:15','2019-07-21 19:08:15','3222222222222222faasf','11');

/*Table structure for table `sys_file_item` */

DROP TABLE IF EXISTS `sys_file_item`;

CREATE TABLE `sys_file_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文件项表',
  `file_id` int(11) NOT NULL COMMENT '主表id',
  `file_name` varchar(255) NOT NULL COMMENT '文件名称',
  `file_path` varchar(255) NOT NULL COMMENT '文件路径',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `sys_file_item` */

insert  into `sys_file_item`(`id`,`file_id`,`file_name`,`file_path`,`created_time`,`updated_time`) values (2,2,'bb-c891113a6c5a4bd48c5c0855174ddd95.png','/mdt/filetemp/20190721/e76a8a74d88d474a8f68308009958552.png','2019-07-21 19:05:46','2019-07-21 19:05:46'),(3,101,'微信图片_20190702153424.png','/mdt/filetemp/20190721/8d4d002707f0484fa2795376fd6228dd.png','2019-07-21 19:05:55','2019-07-21 19:05:55'),(4,201,'微信图片_20190702153424.png','/mdt/filetemp/20190721/c8046377b0eb47028522204325b82210.png','2019-07-21 19:08:15','2019-07-21 19:08:15');

/*Table structure for table `sys_grade` */

DROP TABLE IF EXISTS `sys_grade`;

CREATE TABLE `sys_grade` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评分项维护表',
  `type` varchar(1) NOT NULL COMMENT '类型 1:MDT专家对组织科室评价',
  `description` varchar(255) NOT NULL COMMENT '打分说明',
  `min_value` varchar(255) NOT NULL COMMENT '打分值',
  `max_value` varchar(255) NOT NULL COMMENT '打分值',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `sys_grade` */

insert  into `sys_grade`(`id`,`type`,`description`,`min_value`,`max_value`,`create_time`,`update_time`) values (1,'1','申请会诊必要性','1','5',NULL,'2019-07-04 16:19:33'),(2,'1','会诊申请书目的明确','1','5',NULL,NULL),(3,'1','病情现况资料详尽','1','5',NULL,NULL),(4,'1','会诊书写规范、详细','1','5',NULL,NULL),(5,'1','会诊意见解决问题','1','5',NULL,NULL),(6,'2','会诊及时性','1','5',NULL,'2019-07-04 16:19:37'),(7,'2','会诊意见可行性','1','5',NULL,NULL);

/*Table structure for table `sys_menu` */

DROP TABLE IF EXISTS `sys_menu`;

CREATE TABLE `sys_menu` (
  `id` int(20) NOT NULL COMMENT '菜单表',
  `name` varchar(30) NOT NULL COMMENT '菜单名称',
  `icon` varchar(20) DEFAULT NULL COMMENT '图标',
  `url` varchar(255) DEFAULT NULL COMMENT '链接',
  `pid` int(20) NOT NULL COMMENT '父id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Data for the table `sys_menu` */

insert  into `sys_menu`(`id`,`name`,`icon`,`url`,`pid`) values (0,'系统菜单','icon-sys','-',-1),(100,'MDT管理','icon-sys','-',0),(101,'MDT团队管理','icon-sys','mdtTeam.html',100),(102,'MDT申请','icon-sys','mdtApply.html',100),(103,'MDT团队年度评估','icon-sys','mdtTeamAnnualAssess.html',100),(104,'MDT团队建设期满2年评估','icon-sys','mdtTeamTwoYearAssess.html',100),(105,'MDT分享病例','icon-sys','mdtApplyShare.html',100),(800,'用户管理','icon-sys','-',0),(801,'用户管理','icon-sys','user.html',800),(802,'用户角色设置','icon-sys','userRoleSet.html',800),(803,'角色权限设置','icon-sys','roleMenuSet.html',800),(804,'患者信息管理','icon-sys','patient.html',800),(900,'系统设置','icon-sys','-',0),(901,'组织机构管理','icon-sys','org.html',900),(902,'MDT团队维护','icon-sys','mdtTeam.html?audit=1',900),(903,'收费情况设置','icon-sys','sysFee.html',900),(905,'评分项设置','icon-sys','sysGrade.html',900),(906,'短信模板设置','icon-sys','sysMsgTemp.html',900),(907,'文件上传','icon-sys','sysFile.html',900);

/*Table structure for table `sys_msg_template` */

DROP TABLE IF EXISTS `sys_msg_template`;

CREATE TABLE `sys_msg_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '短信模板表',
  `type` int(11) NOT NULL COMMENT '1:MDT发送短信',
  `content` varchar(255) NOT NULL COMMENT '内容',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `sys_msg_template` */

insert  into `sys_msg_template`(`id`,`type`,`content`,`create_time`,`update_time`) values (1,1,' 尊敬的｛专家名字｝您好，邀请您参加｛MDT名称｝的会诊，时间｛MDT时间｝，地点｛MDT申请地点｝，请准时参加。','2019-07-04 09:50:35','2019-07-04 09:50:37');

/*Table structure for table `sys_org` */

DROP TABLE IF EXISTS `sys_org`;

CREATE TABLE `sys_org` (
  `id` varchar(20) NOT NULL COMMENT '机构表',
  `name` varchar(100) NOT NULL COMMENT '机构名称',
  `pid` varchar(20) NOT NULL COMMENT '父id',
  `area` varchar(255) DEFAULT NULL COMMENT '片区',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `created_orgid` varchar(20) DEFAULT NULL COMMENT '所属机构id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Data for the table `sys_org` */

insert  into `sys_org`(`id`,`name`,`pid`,`area`,`sort`,`created_orgid`) values ('1','恩泽医疗集团','0','',NULL,'1'),('11','台州医院','1','内科',NULL,'11'),('1101','台州医院急诊科','11','外科',NULL,'11'),('1102','台州医院放射科','11','医技',NULL,'11'),('1103','台州医院医务部','11','行政后勤',NULL,'11'),('110301','台州医院医务部XX','1103','',NULL,'11'),('12','恩泽医院','1','',NULL,'12'),('1201','恩泽医院急诊科','12','外科',NULL,'12'),('1202','恩泽医院放射科','12','医技',NULL,'12'),('1203','恩泽医院医务部','12','行政后勤',NULL,'12');

/*Table structure for table `sys_patient` */

DROP TABLE IF EXISTS `sys_patient`;

CREATE TABLE `sys_patient` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '患者信息表',
  `patient_type` varchar(1) NOT NULL COMMENT '患者类型(1:门诊 2:住院)',
  `name` varchar(30) NOT NULL COMMENT '姓名患者名称',
  `gender` varchar(1) DEFAULT NULL COMMENT '性别',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `idcard` varchar(30) DEFAULT NULL COMMENT '身份证号',
  `medical_no` varchar(30) DEFAULT NULL COMMENT '病历号(门诊)',
  `treatment_no` varchar(30) DEFAULT NULL COMMENT '就诊号(门诊)',
  `medical_name` varchar(100) DEFAULT NULL COMMENT '病历名称(门诊)',
  `medical_status` varchar(30) DEFAULT NULL COMMENT '病历状态(门诊)',
  `major_doctor` varchar(30) DEFAULT NULL COMMENT '主治医生(门诊)',
  `medical_history` varchar(30) DEFAULT NULL COMMENT '现在病史(门诊)',
  `medical_exam` varchar(30) DEFAULT NULL COMMENT '体检(门诊)',
  `specialized_medical` varchar(30) DEFAULT NULL COMMENT '专科体检(门诊)',
  `dispose` varchar(30) DEFAULT NULL COMMENT '处理(门诊)',
  `primary_diagnosis` varchar(1000) DEFAULT NULL COMMENT '初步诊断(门诊)',
  `in_hospital_no` varchar(30) DEFAULT NULL COMMENT '住院号(住院)',
  `in_hospital_number` varchar(30) DEFAULT NULL COMMENT '住院次(住院)',
  `fee_type` varchar(30) DEFAULT NULL COMMENT '费别(住院)',
  `in_hospital_date` date DEFAULT NULL COMMENT '入院日期(住院)',
  `cure_doctor` varchar(30) DEFAULT NULL COMMENT '经治医生(住院)',
  `superior_doctor` varchar(30) DEFAULT NULL COMMENT '上级医师(住院)',
  `senior_doctor` varchar(30) DEFAULT NULL COMMENT '主任医师(住院)',
  `diagnosis` varchar(1000) DEFAULT NULL COMMENT '诊断(住院)',
  `in_hospital_days` int(11) DEFAULT NULL COMMENT '出院日(住院)',
  `out_hospital_date` date DEFAULT NULL COMMENT '住院日期(住院)',
  `mdt_before_project` varchar(1000) DEFAULT NULL COMMENT 'MDT前治疗方案',
  `mdt_after_project` varchar(1000) DEFAULT NULL COMMENT 'MDT后治疗方案',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_doctor` varchar(30) DEFAULT NULL COMMENT '修改医生',
  `updated_time` datetime DEFAULT NULL COMMENT '修改时间',
  `created_orgid` varchar(20) DEFAULT NULL COMMENT '所属机构',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `sys_patient` */

insert  into `sys_patient`(`id`,`patient_type`,`name`,`gender`,`birthday`,`age`,`idcard`,`medical_no`,`treatment_no`,`medical_name`,`medical_status`,`major_doctor`,`medical_history`,`medical_exam`,`specialized_medical`,`dispose`,`primary_diagnosis`,`in_hospital_no`,`in_hospital_number`,`fee_type`,`in_hospital_date`,`cure_doctor`,`superior_doctor`,`senior_doctor`,`diagnosis`,`in_hospital_days`,`out_hospital_date`,`mdt_before_project`,`mdt_after_project`,`created_time`,`update_doctor`,`updated_time`,`created_orgid`) values (1,'1','洪小放','男','1962-06-01',45,'220101201101010921','1','2','3','4','王医生','病历史','7','8','1','初步诊断内容','3','5','4','2019-06-21','2','1','3','4',19700101,'1970-01-01','7','8','2019-06-21 10:11:15','李忆深','2019-07-21 19:22:02','11'),(2,'1','222','2','2019-06-21',1,'23','1','2','3','4','5','6','7','8','9','99','','','',NULL,'','','','',NULL,NULL,'','','2019-06-21 11:07:44','999','2019-07-04 11:23:50','11'),(4,'1','11','',NULL,NULL,'','','','','','','','','','','','','','',NULL,'','','','',NULL,NULL,'','',NULL,'','2019-06-21 11:09:18','12'),(5,'2','22','',NULL,NULL,'','','','','','','','','','','','','','',NULL,'','','','',NULL,NULL,'','',NULL,'','2019-06-21 11:09:25','11');

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色表',
  `name` varchar(30) NOT NULL COMMENT '角色名称',
  `level` varchar(1) NOT NULL COMMENT '数据权限级别 1:集团 2:院区 3:部门 4:科室 5:个人',
  `created_orgid` varchar(20) DEFAULT NULL COMMENT '所属机构',
  `code` varchar(32) DEFAULT NULL COMMENT '特殊角色标识',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Data for the table `sys_role` */

insert  into `sys_role`(`id`,`name`,`level`,`created_orgid`,`code`) values (1,'院长','2','11',NULL),(2,'分管院长','2','11','fgyz'),(3,'医务部主任','3','11','ywbzr'),(4,'医务部管理员','3','11',NULL),(5,'科室主任','4','11','kszr'),(6,'专家','5','11','zj'),(7,'普通用户','5','11',NULL),(9,'超级管理员','1',NULL,NULL);

/*Table structure for table `sys_role_menu` */

DROP TABLE IF EXISTS `sys_role_menu`;

CREATE TABLE `sys_role_menu` (
  `role_id` int(11) NOT NULL,
  `menu_id` int(11) NOT NULL,
  PRIMARY KEY (`role_id`,`menu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Data for the table `sys_role_menu` */

insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,100),(1,101),(3,103),(3,104),(3,105),(5,100),(5,101),(5,102),(5,103),(5,104),(5,105),(7,100),(7,101),(7,102),(7,103),(7,104),(7,105),(7,800),(7,801),(7,802),(7,803),(7,804),(7,900),(7,901),(7,902),(7,903),(7,905),(7,906),(7,907),(9,100),(9,101),(9,800),(9,801),(9,802),(9,803),(9,900),(9,901);

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户表',
  `number` varchar(30) NOT NULL COMMENT '工号',
  `name` varchar(100) NOT NULL COMMENT '姓名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `age` int(11) NOT NULL COMMENT '年龄',
  `birthday` date NOT NULL COMMENT '生日',
  `department` varchar(20) NOT NULL COMMENT '所在科室',
  `title` varchar(100) NOT NULL COMMENT '职称',
  `education` varchar(100) NOT NULL COMMENT '学历',
  `phone` varchar(20) NOT NULL COMMENT '手机长号',
  `phone_cornet` varchar(20) DEFAULT NULL COMMENT '手机短号',
  `created_time` datetime NOT NULL,
  `updated_time` datetime NOT NULL,
  `created_orgid` varchar(20) DEFAULT NULL COMMENT '所属机构id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`number`,`name`,`password`,`age`,`birthday`,`department`,`title`,`education`,`phone`,`phone_cornet`,`created_time`,`updated_time`,`created_orgid`) values (1,'001','医务部主任','1',1,'2019-01-16','1102','医师','硕士','13200000000','1253','2019-06-11 09:55:13','2019-07-04 11:29:47','11'),(3,'002','科室主任','1',2,'2019-06-11','1102','医师','硕士','13300000000','1241','2019-06-11 14:11:49','2019-07-03 16:33:06','11'),(14,'003','普通用户','1',1,'2019-07-02','1102','职称','学历','178282827171','81721','2019-07-03 16:32:05','2019-07-03 16:32:56','11'),(15,'004','专家1','1',1,'2019-07-02','1102','职称','学历','178282827171','81721','2019-07-03 16:32:05','2019-07-03 16:37:32','11'),(16,'005','专家2','1',1,'2019-07-02','1102','职称','学历','178282827171','81721','2019-07-03 16:32:05','2019-07-03 16:37:37','11'),(17,'006','专家3','1',1,'2019-07-02','110301','职称','学历','178282827171','81721','2019-07-03 16:32:05','2019-07-15 09:34:39','11'),(18,'002','分管院长','1',2,'2019-06-11','1102','医师','硕士','13300000000','1241','2019-06-11 14:11:49','2019-07-03 16:33:06','11'),(19,'020','恩泽医生','123456',32,'2019-07-21','1201','不知道','研究生','13455555555','3345','2019-07-21 09:40:59','2019-07-21 09:40:59','12');

/*Table structure for table `sys_user_role` */

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Data for the table `sys_user_role` */

insert  into `sys_user_role`(`user_id`,`role_id`) values (1,3),(3,5),(14,7),(15,6),(16,6),(17,6),(18,2);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;