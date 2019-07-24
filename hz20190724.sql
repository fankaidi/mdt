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

insert  into `base_key`(`id`,`nowid`,`create_date`,`update_date`) values ('lc_daiban','1700','2019-07-20 10:19:14','2019-07-24 16:39:34'),('lc_history','1400','2019-07-20 11:58:05','2019-07-24 16:39:34'),('lc_process_item','700','2019-07-22 20:53:57','2019-07-24 16:40:39'),('mdt_apply','2800','2019-07-09 17:18:38','2019-07-24 16:40:03'),('mdt_team','2700','2019-07-09 10:52:26','2019-07-24 15:19:06'),('sys_file','500','2019-07-21 18:41:52','2019-07-24 16:19:12');

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

insert  into `lc_daiban`(`id`,`busitype`,`entry_name`,`userid`,`bisiid`,`created_time`,`title`,`apply_person_id`) values (1604,'mdt_apply','分管院长审核',18,2602,'2019-07-24 16:41:27','分管院长审核',14),(1605,'mdt_apply','分管院长审核',18,2701,'2019-07-24 16:41:34','分管院长审核',14);

/*Table structure for table `lc_define` */

DROP TABLE IF EXISTS `lc_define`;

CREATE TABLE `lc_define` (
  `id` bigint(20) NOT NULL COMMENT '流程定义表',
  `name` varchar(128) DEFAULT NULL COMMENT '名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `lc_define` */

insert  into `lc_define`(`id`,`name`) values (1,'MDT团队申报'),(2,'住院病人MDT申请'),(3,'MDT团队年度评估'),(4,'MDT团队建设期满2年评估');

/*Table structure for table `lc_define_item` */

DROP TABLE IF EXISTS `lc_define_item`;

CREATE TABLE `lc_define_item` (
  `id` bigint(20) NOT NULL COMMENT '流程节点定义',
  `define_id` bigint(20) NOT NULL COMMENT '流程id',
  `entry_name` varchar(128) DEFAULT NULL COMMENT '节点名称',
  `back_id` bigint(20) DEFAULT NULL COMMENT '上一个节点id,没有就是0',
  `next_id` bigint(20) DEFAULT NULL COMMENT '下一个节点id,没有就是0',
  `type_id` int(11) DEFAULT NULL COMMENT '节点类型，0是开始,1是一般节点，-1是结束节点',
  `role_id` varchar(128) DEFAULT NULL COMMENT '绑定角色的code',
  `back_type` int(11) DEFAULT NULL COMMENT '退回类型，-1是无法退回,1是重新开始申请，2是退回上一步',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `lc_define_item` */

insert  into `lc_define_item`(`id`,`define_id`,`entry_name`,`back_id`,`next_id`,`type_id`,`role_id`,`back_type`) values (1,1,'开始',0,2,0,NULL,-1),(2,1,'申请人申请',1,3,1,NULL,-1),(3,1,'科主任审核',2,4,1,'kszr',1),(4,1,'医务部主任审核',3,5,1,'ywbzr',1),(5,1,'分管院长审核',4,6,1,'fgyz',1),(6,1,'流程结束',5,0,-1,NULL,-1),(7,2,'开始',0,8,0,NULL,-1),(8,2,'申请人申请',7,9,1,NULL,-1),(9,2,'科主任审核',8,10,1,'kszr',1),(10,2,'医务部主任审核',9,11,1,'ywbzr',1),(11,2,'流程结束',10,0,-1,NULL,-1),(21,3,'开始',0,22,0,NULL,-1),(22,3,'医务部发起',21,23,1,NULL,-1),(23,3,'团队首席专家填写',22,24,1,'tdsx',-1),(24,3,'医务部审核',23,25,1,'ywbzr',2),(25,3,'流程结束',24,0,-1,NULL,-1),(31,4,'开始',0,32,0,NULL,-1),(32,4,'医务部发起',31,33,1,NULL,-1),(33,4,'团队首席专家填写',32,34,1,'tdsx',-1),(34,4,'医务部审核',33,35,1,'ywbzr',2),(35,4,'流程结束',34,0,-1,NULL,-1);

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

insert  into `lc_history`(`id`,`busitype`,`entry_name`,`userid`,`bisiid`,`created_time`,`audit_result`,`audit_opinion`) values (1201,'mdt_team','科主任审核',3,2601,'2019-07-24 15:21:12',1,'同意'),(1202,'mdt_team','医务部主任审核',1,2601,'2019-07-24 15:46:41',1,'同意'),(1203,'mdt_team','分管院长审核',18,2601,'2019-07-24 15:47:06',1,'同意'),(1204,'mdt_team','科主任审核',3,2602,'2019-07-24 15:48:22',1,'同意'),(1205,'mdt_team','医务部主任审核',1,2602,'2019-07-24 15:49:06',1,'同意'),(1206,'mdt_team','分管院长审核',18,2602,'2019-07-24 15:49:19',1,'同意'),(1301,'mdt_apply','科主任审核',3,2602,'2019-07-24 16:39:34',1,'同意'),(1302,'mdt_apply','科主任审核',3,2701,'2019-07-24 16:40:57',1,'同意'),(1303,'mdt_apply','医务部主任审核',1,2602,'2019-07-24 16:41:27',1,'同意'),(1304,'mdt_apply','医务部主任审核',1,2701,'2019-07-24 16:41:34',1,'同意');

/*Table structure for table `lc_process` */

DROP TABLE IF EXISTS `lc_process`;

CREATE TABLE `lc_process` (
  `id` bigint(20) NOT NULL COMMENT '流程实例',
  `define_id` bigint(20) DEFAULT NULL COMMENT '流程id',
  `name` varchar(128) DEFAULT NULL COMMENT '名称',
  `type_name` varchar(128) DEFAULT NULL COMMENT '业务类型表名',
  `created_userid` bigint(20) DEFAULT NULL COMMENT '创建用户id',
  `created_orgid` varchar(20) DEFAULT NULL COMMENT '所属机构',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `busiid` bigint(20) DEFAULT NULL COMMENT '业务id',
  `cur_itemid` bigint(20) DEFAULT NULL COMMENT '当前节点id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `lc_process` */

insert  into `lc_process`(`id`,`define_id`,`name`,`type_name`,`created_userid`,`created_orgid`,`created_time`,`updated_time`,`busiid`,`cur_itemid`) values (501,1,'MDT团队申报','mdt_team',14,'11','2019-07-24 15:20:25','2019-07-24 15:47:06',2601,6),(508,1,'MDT团队申报','mdt_team',14,'11','2019-07-24 15:48:08','2019-07-24 15:49:19',2602,6),(515,1,'MDT团队申报','mdt_apply',14,'11','2019-07-24 15:51:10','2019-07-24 16:41:27',2602,5),(601,1,'MDT团队申报','mdt_apply',14,'11','2019-07-24 16:40:39','2019-07-24 16:41:34',2701,5);

/*Table structure for table `lc_process_item` */

DROP TABLE IF EXISTS `lc_process_item`;

CREATE TABLE `lc_process_item` (
  `id` bigint(20) NOT NULL COMMENT '流程实例节点',
  `define_id` bigint(20) NOT NULL COMMENT '流程id',
  `process_id` bigint(20) NOT NULL COMMENT '实例id',
  `entry_name` varchar(128) DEFAULT NULL COMMENT '节点名称',
  `back_id` bigint(20) DEFAULT NULL COMMENT '上一个节点id,没有就是0',
  `next_id` bigint(20) DEFAULT NULL COMMENT '下一个节点id,没有就是0',
  `type_id` int(11) DEFAULT NULL COMMENT '节点类型，0是开始,1是一般节点，-1是结束节点',
  `role_id` varchar(128) DEFAULT NULL COMMENT '绑定角色的code',
  `back_type` int(11) DEFAULT NULL COMMENT '退回类型，1是重新开始申请，2是退回上一步',
  `created_userid` bigint(20) DEFAULT NULL COMMENT '创建用户id',
  `created_orgid` varchar(20) DEFAULT NULL COMMENT '所属机构',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `userid` bigint(20) DEFAULT NULL COMMENT '执行人id',
  `item_id` bigint(20) DEFAULT NULL COMMENT '节点id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `lc_process_item` */

insert  into `lc_process_item`(`id`,`define_id`,`process_id`,`entry_name`,`back_id`,`next_id`,`type_id`,`role_id`,`back_type`,`created_userid`,`created_orgid`,`updated_time`,`created_time`,`userid`,`item_id`) values (502,1,501,'开始',0,2,0,NULL,-1,14,'11','2019-07-24 15:20:25','2019-07-24 15:20:25',14,1),(503,1,501,'申请人申请',1,3,1,NULL,-1,14,'11','2019-07-24 15:20:25','2019-07-24 15:20:25',14,2),(504,1,501,'科主任审核',2,4,1,'kszr',1,14,'11','2019-07-24 15:21:12','2019-07-24 15:20:25',3,3),(505,1,501,'医务部主任审核',3,5,1,'ywbzr',1,14,'11','2019-07-24 15:46:41','2019-07-24 15:20:25',1,4),(506,1,501,'分管院长审核',4,6,1,'fgyz',1,14,'11','2019-07-24 15:47:06','2019-07-24 15:20:25',18,5),(507,1,501,'流程结束',5,0,-1,NULL,-1,14,'11','2019-07-24 15:20:25','2019-07-24 15:20:25',NULL,6),(509,1,508,'开始',0,2,0,NULL,-1,14,'11','2019-07-24 15:48:08','2019-07-24 15:48:08',14,1),(510,1,508,'申请人申请',1,3,1,NULL,-1,14,'11','2019-07-24 15:48:08','2019-07-24 15:48:08',14,2),(511,1,508,'科主任审核',2,4,1,'kszr',1,14,'11','2019-07-24 15:48:22','2019-07-24 15:48:08',3,3),(512,1,508,'医务部主任审核',3,5,1,'ywbzr',1,14,'11','2019-07-24 15:49:06','2019-07-24 15:48:08',1,4),(513,1,508,'分管院长审核',4,6,1,'fgyz',1,14,'11','2019-07-24 15:49:19','2019-07-24 15:48:08',18,5),(514,1,508,'流程结束',5,0,-1,NULL,-1,14,'11','2019-07-24 15:48:08','2019-07-24 15:48:08',NULL,6),(516,1,515,'开始',0,2,0,NULL,-1,14,'11','2019-07-24 15:51:10','2019-07-24 15:51:10',14,1),(517,1,515,'申请人申请',1,3,1,NULL,-1,14,'11','2019-07-24 15:51:10','2019-07-24 15:51:10',14,2),(518,1,515,'科主任审核',2,4,1,'kszr',1,14,'11','2019-07-24 16:39:34','2019-07-24 15:51:10',3,3),(519,1,515,'医务部主任审核',3,5,1,'ywbzr',1,14,'11','2019-07-24 16:41:27','2019-07-24 15:51:10',1,4),(520,1,515,'分管院长审核',4,6,1,'fgyz',1,14,'11','2019-07-24 15:51:10','2019-07-24 15:51:10',NULL,5),(521,1,515,'流程结束',5,0,-1,NULL,-1,14,'11','2019-07-24 15:51:10','2019-07-24 15:51:10',NULL,6),(602,1,601,'开始',0,2,0,NULL,-1,14,'11','2019-07-24 16:40:39','2019-07-24 16:40:39',14,1),(603,1,601,'申请人申请',1,3,1,NULL,-1,14,'11','2019-07-24 16:40:39','2019-07-24 16:40:39',14,2),(604,1,601,'科主任审核',2,4,1,'kszr',1,14,'11','2019-07-24 16:40:57','2019-07-24 16:40:39',3,3),(605,1,601,'医务部主任审核',3,5,1,'ywbzr',1,14,'11','2019-07-24 16:41:34','2019-07-24 16:40:39',1,4),(606,1,601,'分管院长审核',4,6,1,'fgyz',1,14,'11','2019-07-24 16:40:39','2019-07-24 16:40:39',NULL,5),(607,1,601,'流程结束',5,0,-1,NULL,-1,14,'11','2019-07-24 16:40:39','2019-07-24 16:40:39',NULL,6);

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
  `apply_status` int(11) DEFAULT NULL COMMENT '申请状态',
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
  `is_jiaofei` int(11) DEFAULT NULL COMMENT '是否打印缴费通知单，0否，1是',
  `is_zhiqing` int(11) DEFAULT NULL COMMENT '是否打印知情同意书，0否，1是',
  `is_duanxin` int(11) DEFAULT NULL COMMENT '是否短信通知，0否，1是',
  `is_ksdafen` int(11) DEFAULT NULL COMMENT '是否科室打分，0否，1是',
  `is_zjdafen` int(11) DEFAULT NULL COMMENT '是否专家打分，0否，1是',
  `is_xiaojie` int(11) DEFAULT NULL COMMENT '是否小结，0否，1是',
  `is_zuofei` int(11) DEFAULT NULL COMMENT '是否作废，0否，1是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Data for the table `mdt_apply` */

insert  into `mdt_apply`(`id`,`patient_type`,`name`,`gender`,`birthday`,`phone`,`diagnose_date`,`number`,`picture`,`overview`,`survey_report`,`inspection_report`,`mdt_date`,`mdt_location`,`disease_name`,`other_disease_name`,`mdt_purpose`,`other_mdt_purpose`,`difficulty`,`is_charge`,`apply_person`,`apply_createtime`,`apply_phone`,`apply_status`,`share`,`summary`,`created_userid`,`created_deptid`,`is_delete`,`created_time`,`updated_time`,`apply_person_id`,`patient_id`,`idcard`,`created_orgid`,`is_jiaofei`,`is_zhiqing`,`is_duanxin`,`is_ksdafen`,`is_zjdafen`,`is_xiaojie`,`is_zuofei`) values (2601,'2','王小妹','女','2019-06-21','3333333','2019-07-23','1',NULL,'病史：6\r\n体检：7\r\n处理：9\r\n初步诊断：99',NULL,NULL,'2019-07-25 15:49:46','打法',NULL,NULL,'1,2,','','333','1','普通用户','2019-07-24 15:49:32','178282827171',11,'0',NULL,14,'1102','0','2019-07-24 15:50:24','2019-07-24 15:50:24',14,2,'2345678888888','11',0,0,0,0,0,0,0),(2602,'1','洪小放','男','1962-06-01','566899099','2019-06-21','1',NULL,'病史：病历史\r\n体检：7\r\n处理：1\r\n初步诊断：初步诊断内容',NULL,NULL,'2019-07-24 15:50:36','打法',NULL,NULL,'1,2,','','地方撒','0','普通用户','2019-07-24 15:50:25','178282827171',11,'0',NULL,14,'1102','0','2019-07-24 15:51:10','2019-07-24 16:41:27',14,1,'220101201101010921','11',0,0,0,0,0,0,0),(2701,'1','我是谁','男','2019-07-23','13566666666','2019-07-24','22',NULL,'病史：22\r\n体检：222\r\n处理：22\r\n初步诊断：111',NULL,NULL,'2019-07-26 16:40:20','111111',NULL,NULL,'1,','','223','0','普通用户','2019-07-24 16:40:02','178282827171',11,'0',NULL,14,'1102','0','2019-07-24 16:40:39','2019-07-24 16:41:34',14,5,'33302223233333322','11',0,0,0,0,0,0,0);

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
  `team_id` int(11) DEFAULT NULL COMMENT 'mdt团队id，可以为空',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Data for the table `mdt_apply_doctor` */

insert  into `mdt_apply_doctor`(`id`,`apply_id`,`user_id`,`name`,`department`,`title`,`phone`,`phone_cornet`,`created_time`,`updated_time`,`team_id`) values (93,2601,15,'专家1','1102','职称','178282827171','81721','2019-07-24 15:49:57','2019-07-24 15:49:57',2601),(94,2601,3,'科室主任','1102','医师','13300000000','1241','2019-07-24 15:50:06','2019-07-24 15:50:06',2602),(95,2601,17,'专家3','110301','职称','178282827171','81721','2019-07-24 15:50:14','2019-07-24 15:50:14',NULL),(96,2602,15,'专家1','1102','职称','178282827171','81721','2019-07-24 15:50:45','2019-07-24 15:50:45',2601),(97,2602,14,'普通用户','1102','职称','178282827171','81721','2019-07-24 15:50:47','2019-07-24 15:50:47',2601),(98,2602,3,'科室主任','1102','医师','13300000000','1241','2019-07-24 15:50:54','2019-07-24 15:50:54',2602),(99,2602,1,'医务部主任','1102','医师','13200000000','1253','2019-07-24 15:51:00','2019-07-24 15:51:00',NULL),(100,2701,15,'专家1','1102','职称','178282827171','81721','2019-07-24 16:40:29','2019-07-24 16:40:29',2601),(101,2701,3,'科室主任','1102','医师','13300000000','1241','2019-07-24 16:40:34','2019-07-24 16:40:34',NULL);

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
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Data for the table `mdt_apply_feedback` */

/*Table structure for table `mdt_apply_know` */

DROP TABLE IF EXISTS `mdt_apply_know`;

CREATE TABLE `mdt_apply_know` (
  `id` bigint(20) NOT NULL COMMENT '知情同意书',
  `apply_id` bigint(20) DEFAULT NULL COMMENT 'MDT申请id',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `name` varchar(128) DEFAULT NULL COMMENT '患者姓名',
  `gender` varchar(32) DEFAULT NULL COMMENT '性  别',
  `age` varchar(32) DEFAULT NULL COMMENT '年  龄',
  `phone` varchar(32) DEFAULT NULL COMMENT '联系方式',
  `idcard` varchar(32) DEFAULT NULL COMMENT '患者身份证号',
  `medical_no` varchar(64) DEFAULT NULL COMMENT '门诊号',
  `in_hospital_no` varchar(64) DEFAULT NULL COMMENT '住院号',
  `addr` varchar(256) DEFAULT NULL COMMENT '家庭住址',
  `linman` varchar(32) DEFAULT NULL COMMENT '患者联系人',
  `linphone` varchar(32) DEFAULT NULL COMMENT '联系人电话',
  `reason` varchar(256) DEFAULT NULL COMMENT '病因',
  `nameqm` varchar(32) DEFAULT NULL COMMENT '患者签名',
  `qmdate1` date DEFAULT NULL COMMENT '患者签名日期',
  `wtqm` varchar(32) DEFAULT NULL COMMENT '委托人签名',
  `qmdate2` date DEFAULT NULL COMMENT '委托人签名日期',
  `wtgx` varchar(32) DEFAULT NULL COMMENT '委托人与患者的关系',
  `wtphone` varchar(32) DEFAULT NULL COMMENT '委托人联系电话',
  `wtidcard` varchar(32) DEFAULT NULL COMMENT '委托人身份证号',
  `wtaddr` varchar(256) DEFAULT NULL COMMENT '委托人住址',
  `doctorqm` varchar(32) DEFAULT NULL COMMENT '医生签名',
  `qmdate3` date DEFAULT NULL COMMENT '医生签名日期',
  `feiyong` decimal(10,2) DEFAULT NULL COMMENT '费用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `mdt_apply_know` */

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
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Data for the table `mdt_apply_opinion` */

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
) ENGINE=InnoDB AUTO_INCREMENT=212 DEFAULT CHARSET=utf8;

/*Data for the table `mdt_grade_item` */

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

insert  into `mdt_team`(`id`,`proposer`,`name`,`date`,`standard`,`annual_status`,`two_year_status`,`audit_status`,`created_userid`,`created_deptid`,`is_delete`,`created_time`,`updated_time`,`created_orgid`) values (2601,'普通用户','脑卒中专业','2019-07-24','打发打发发发的','0','0','4',14,'1102','0','2019-07-24 15:20:25','2019-07-24 15:47:06','11'),(2602,'普通用户','肺部专家','2019-07-24','大打法','0','0','4',14,'1102','0','2019-07-24 15:48:08','2019-07-24 15:49:19','11');

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
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Data for the table `mdt_team_assess` */

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
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Data for the table `mdt_team_info` */

insert  into `mdt_team_info`(`id`,`team_id`,`user_id`,`name`,`department`,`title`,`phone`,`phone_cornet`,`specialist_type`,`created_time`,`updated_time`) values (61,2601,15,'专家1','1102','职称','178282827171','81721','1','2019-07-24 15:19:41','2019-07-24 15:19:41'),(62,2601,16,'专家2','1102','职称','178282827171','81721','3','2019-07-24 15:19:53','2019-07-24 15:19:53'),(63,2601,14,'普通用户','1102','职称','178282827171','81721','3','2019-07-24 15:20:01','2019-07-24 15:20:01'),(64,2602,14,'普通用户','1102','职称','178282827171','81721','1','2019-07-24 15:47:44','2019-07-24 15:47:44'),(65,2602,3,'科室主任','1102','医师','13300000000','1241','3','2019-07-24 15:47:50','2019-07-24 15:47:56');

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
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Data for the table `mdt_team_issue` */

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
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Data for the table `mdt_team_objective` */

insert  into `mdt_team_objective`(`id`,`team_id`,`year`,`year_sum`,`papers_num`,`other`,`flag`,`summary`,`month1`,`month2`,`month3`,`month4`,`month5`,`month6`,`month7`,`month8`,`month9`,`month10`,`month11`,`month12`,`created_time`,`updated_time`,`created_deptid`,`created_userid`) values (34,2601,'2019',12,10,'22','1',NULL,1,1,1,1,1,1,1,1,1,1,1,1,'2019-07-24 15:20:25','2019-07-24 15:20:25','1102',14),(35,2602,'2016',13,23,'','1',NULL,1,2,1,1,1,1,1,1,1,1,1,1,'2019-07-24 15:48:08','2019-07-24 15:48:08','1102',14);

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
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Data for the table `mdt_team_paper` */

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

insert  into `sys_file`(`id`,`content`,`created_userid`,`created_deptid`,`created_time`,`updated_time`,`title`,`created_orgid`) values (401,'打法',14,'1102','2019-07-24 16:19:12','2019-07-24 16:19:12','测试打发士大夫上','11');

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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `sys_file_item` */

insert  into `sys_file_item`(`id`,`file_id`,`file_name`,`file_path`,`created_time`,`updated_time`) values (6,401,'bb-c891113a6c5a4bd48c5c0855174ddd95.png','/mdt/filetemp/20190724/1b757bb2820b43a4ab3b7ca7334a4055.png','2019-07-24 16:19:12','2019-07-24 16:19:12');

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

insert  into `sys_grade`(`id`,`type`,`description`,`min_value`,`max_value`,`create_time`,`update_time`) values (1,'1','申请会诊必要性','1','5',NULL,'2019-07-04 16:19:33'),(2,'1','会诊申请书目的明确','1','5',NULL,NULL),(3,'1','病情现况资料详尽','1','6',NULL,NULL),(4,'1','会诊书写规范、详细','1','5',NULL,NULL),(5,'1','会诊意见解决问题','1','5',NULL,NULL),(6,'2','会诊及时性','1','5',NULL,'2019-07-04 16:19:37'),(7,'2','会诊意见可行性','1','5',NULL,NULL);

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `sys_msg_template` */

insert  into `sys_msg_template`(`id`,`type`,`content`,`create_time`,`update_time`) values (1,1,' 尊敬的｛专家名字｝您好，邀请您参加｛MDT名称｝的会诊，时间:｛MDT时间｝，地点:｛MDT申请地点｝，请准时参加。','2019-07-04 09:50:35','2019-07-04 09:50:37'),(2,2,' 尊敬的｛专家名字｝您好，原定于时间:｛MDT时间｝，地点:｛MDT申请地点｝，｛MDT名称｝的会诊已经取消，请知悉。','2019-07-24 11:11:49','2019-07-24 11:11:51');

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
  `patient_type` varchar(1) NOT NULL COMMENT '患者类型 1住院 2门诊',
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
  `medical_date` date DEFAULT NULL COMMENT '门诊首诊日期',
  `phone` varchar(32) DEFAULT NULL COMMENT '电话号码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `sys_patient` */

insert  into `sys_patient`(`id`,`patient_type`,`name`,`gender`,`birthday`,`age`,`idcard`,`medical_no`,`treatment_no`,`medical_name`,`medical_status`,`major_doctor`,`medical_history`,`medical_exam`,`specialized_medical`,`dispose`,`primary_diagnosis`,`in_hospital_no`,`in_hospital_number`,`fee_type`,`in_hospital_date`,`cure_doctor`,`superior_doctor`,`senior_doctor`,`diagnosis`,`in_hospital_days`,`out_hospital_date`,`mdt_before_project`,`mdt_after_project`,`created_time`,`update_doctor`,`updated_time`,`created_orgid`,`medical_date`,`phone`) values (1,'1','洪小放','男','1962-06-01',45,'220101201101010921','1','2','3','4','王医生','病历史','7','8','1','初步诊断内容','3','5','4','2019-06-21','2','1','3','4',19700101,'1970-01-01','7','8','2019-06-21 10:11:15','李忆深','2019-07-23 14:36:44','11',NULL,'566899099'),(2,'1','王小妹','女','2019-06-21',1,'2345678888888','1','2','3','4','5','6','7','8','9','99','22sfadsfdff','4','zifei','2019-07-23','','','','',NULL,NULL,'dfdff','dsf ','2019-07-23 14:54:51','999','2019-07-23 14:54:57','11',NULL,'3333333'),(4,'1','11','',NULL,NULL,'','','','','','','','','','','','','','',NULL,'','','','',NULL,NULL,'','',NULL,'','2019-06-21 11:09:18','12',NULL,NULL),(5,'2','我是谁','男','2019-07-23',65,'33302223233333322','22','2','2','22','22','22','222','22','22','111','','','',NULL,'','','','',NULL,NULL,'','',NULL,'','2019-07-23 14:36:02','11','2019-07-23','13566666666');

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色表',
  `name` varchar(30) NOT NULL COMMENT '角色名称',
  `level` int(11) NOT NULL COMMENT '数据权限级别 1:集团 2:院区 3:部门 4:科室 5:个人',
  `created_orgid` varchar(20) DEFAULT NULL COMMENT '所属机构',
  `code` varchar(32) DEFAULT NULL COMMENT '特殊角色标识',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Data for the table `sys_role` */

insert  into `sys_role`(`id`,`name`,`level`,`created_orgid`,`code`) values (1,'院长',2,'11',NULL),(2,'分管院长',2,'11','fgyz'),(3,'医务部主任',2,'11','ywbzr'),(4,'医务部管理员',2,'11',NULL),(5,'科室主任',4,'11','kszr'),(6,'专家',5,'11','zj'),(7,'普通用户',5,'11',NULL),(9,'超级管理员',1,NULL,NULL);

/*Table structure for table `sys_role_menu` */

DROP TABLE IF EXISTS `sys_role_menu`;

CREATE TABLE `sys_role_menu` (
  `role_id` int(11) NOT NULL,
  `menu_id` int(11) NOT NULL,
  PRIMARY KEY (`role_id`,`menu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Data for the table `sys_role_menu` */

insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,100),(1,101),(2,101),(2,102),(3,100),(3,101),(3,102),(3,103),(3,104),(3,105),(5,100),(5,101),(5,102),(5,103),(5,104),(5,105),(7,100),(7,101),(7,102),(7,103),(7,104),(7,105),(7,800),(7,801),(7,802),(7,803),(7,804),(7,900),(7,901),(7,902),(7,903),(7,905),(7,906),(7,907),(9,100),(9,101),(9,800),(9,801),(9,802),(9,803),(9,900),(9,901);

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
