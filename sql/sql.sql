
-- ----------------------------
-- Table structure for base_key
-- ----------------------------
DROP TABLE IF EXISTS `base_key`;
CREATE TABLE `base_key`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `nowid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_date` datetime(0) NOT NULL,
  `update_date` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mdt_apply
-- ----------------------------
DROP TABLE IF EXISTS `mdt_apply`;
CREATE TABLE `mdt_apply`  (
  `id` int(11) NOT NULL COMMENT 'MDT申请表',
  `patient_type` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '患者类型 1住院 2门诊',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
  `gender` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '性别',
  `birthday` date NULL DEFAULT NULL COMMENT '出生日期',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `diagnose_date` date NULL DEFAULT NULL COMMENT '入院/首诊时间',
  `number` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '住院/门诊号',
  `picture` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '嘉和电子病历用户截图',
  `overview` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '病情概述（含主诉、病史、诊断、诊治过程等）',
  `survey_report` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检验报告',
  `inspection_report` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检查报告',
  `mdt_date` datetime(0) NULL DEFAULT NULL COMMENT 'MDT时间',
  `mdt_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'MDT地点',
  `disease_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '病种名称',
  `other_disease_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '病种名称其它',
  `mdt_purpose` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'MDT目的',
  `other_mdt_purpose` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'MDT目的其它',
  `difficulty` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '诊治难点',
  `is_charge` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否收费 (1:是 0:否)',
  `apply_person` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '申请人',
  `apply_createtime` datetime(0) NULL DEFAULT NULL COMMENT '申请递交时间',
  `apply_phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '申请人电话',
  `apply_status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '申请状态',
  `audit_result1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审核结果(科主任审核)',
  `audit_opinion1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审核意见(科主任审核)',
  `audit_person1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '科主任名称(科主任审核)',
  `audit_time1` datetime(0) NULL DEFAULT NULL COMMENT '审核时间(科主任审核)',
  `audit_result2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审核结果(医务部主任审核)',
  `audit_opinion2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审核意见(医务部主任审核)',
  `audit_person2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '医务部主任名称(医务部主任审核)',
  `audit_time2` datetime(0) NULL DEFAULT NULL COMMENT '审核时间(医务部主任审核)',
  `share` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否分享该病例 1是 0否',
  `summary` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '小结',
  `create_userid` int(11) NOT NULL COMMENT '创建人id',
  `create_dept` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人科室',
  `is_delete` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否删除 1是 0否',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mdt_apply_doctor
-- ----------------------------
DROP TABLE IF EXISTS `mdt_apply_doctor`;
CREATE TABLE `mdt_apply_doctor`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'MDT参加专家表',
  `apply_id` int(11) NOT NULL COMMENT 'MDT申请id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '专家姓名',
  `department` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '科室',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '职称',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号',
  `phone_cornet` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机短号',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 40 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mdt_apply_feedback
-- ----------------------------
DROP TABLE IF EXISTS `mdt_apply_feedback`;
CREATE TABLE `mdt_apply_feedback`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'MDT随访反馈表',
  `apply_id` int(11) NOT NULL COMMENT 'MDT申请id',
  `mdt_bedore_diagnosis` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'MDT前诊断',
  `mdt_after_diagnosis` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'MDT后诊断',
  `outcome` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '转归',
  `treatment_change` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'MDT前后治疗变化',
  `opinion` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '对MDT中心的工作有何建议和意见',
  `visit_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '随访人姓名',
  `visit_phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '随访人电话',
  `visit_time` datetime(0) NOT NULL COMMENT '随访日期',
  `share` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否分享病例 1是 0否',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mdt_apply_opinion
-- ----------------------------
DROP TABLE IF EXISTS `mdt_apply_opinion`;
CREATE TABLE `mdt_apply_opinion`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'MDT专家意见表',
  `apply_id` int(11) NOT NULL COMMENT 'MDT申请id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `content` varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '内容',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mdt_grade_item
-- ----------------------------
DROP TABLE IF EXISTS `mdt_grade_item`;
CREATE TABLE `mdt_grade_item`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评分表',
  `type` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '1:专家打分 2:组织科室打分',
  `apply_id` int(11) NOT NULL COMMENT 'MDT申请id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `sys_grade_id` int(11) NOT NULL COMMENT '评分维护表id',
  `grade` int(11) NOT NULL COMMENT '打分分数',
  `min_value` int(11) NOT NULL COMMENT '最小分值',
  `max_value` int(11) NOT NULL COMMENT '最大分值',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 49 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mdt_team
-- ----------------------------
DROP TABLE IF EXISTS `mdt_team`;
CREATE TABLE `mdt_team`  (
  `id` int(11) NOT NULL COMMENT 'MDT团队表',
  `proposer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '申请人',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'MDT名称',
  `date` date NOT NULL COMMENT '申请日期',
  `standard` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'MDT病种纳入标准和诊疗规范（指南）',
  `annual_status` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '年度审核状态 1:发起 2:专家填写 3:审核完成',
  `two_year_status` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '满两年度审核状态 0:未发起 1:发起 2:专家填写 3:审核通过 4:审核不通过',
  `audit_status` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审核状态 (0:未提交 1:已提交未审核 2:科主任已审核 3:医务部主任已审核 4:分管院长已审核)',
  `audit_result1` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '科主任审核结果  0:未通过 1:通过',
  `audit_opinion1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '科主任审核意见',
  `audit_result2` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '医务部主任审核结果  0:未通过 1:通过',
  `audit_opinion2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '医务部主任审核意见',
  `audit_result3` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分管院长审核结果  0:未通过 1:通过',
  `audit_opinion3` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分管院长审核意见',
  `create_userid` int(11) NOT NULL COMMENT '创建用户id',
  `create_dept` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建科室',
  `is_delete` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否删除  1是 0否',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mdt_team_assess
-- ----------------------------
DROP TABLE IF EXISTS `mdt_team_assess`;
CREATE TABLE `mdt_team_assess`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'MDT团队建设期满2年评估表',
  `team_id` int(11) NOT NULL COMMENT '团队id',
  `case_sum` int(11) NOT NULL COMMENT '病例总数',
  `year1` int(11) NOT NULL COMMENT '年度1',
  `case_num1` int(11) NOT NULL COMMENT '年度1病例数',
  `year2` int(11) NOT NULL COMMENT '年度2',
  `case_num2` int(11) NOT NULL COMMENT '年度2病例数',
  `teaching_situation` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '建期两年教学情况',
  `train_num` int(11) NOT NULL COMMENT '培养研究生数量',
  `hospital_num` int(11) NULL DEFAULT NULL COMMENT '住院医生数量',
  `major_num` int(11) NULL DEFAULT NULL COMMENT '主治医生数量',
  `assistant_major_num` int(11) NULL DEFAULT NULL COMMENT '副主任医生数量',
  `director_num` int(11) NULL DEFAULT NULL COMMENT '主任医生数量',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mdt_team_info
-- ----------------------------
DROP TABLE IF EXISTS `mdt_team_info`;
CREATE TABLE `mdt_team_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'MDT团队基本信息表（多人明细）',
  `team_id` int(11) NOT NULL COMMENT '团队id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '专家姓名',
  `department` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '科室',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '职称',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号',
  `phone_cornet` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机短号',
  `specialist_type` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '专家类型',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mdt_team_issue
-- ----------------------------
DROP TABLE IF EXISTS `mdt_team_issue`;
CREATE TABLE `mdt_team_issue`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'MDT团队课题表',
  `team_id` int(11) NOT NULL COMMENT '团队id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '项目名称',
  `research_date` date NOT NULL COMMENT '项目研究时间',
  `project_fund` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '项目经费',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mdt_team_objective
-- ----------------------------
DROP TABLE IF EXISTS `mdt_team_objective`;
CREATE TABLE `mdt_team_objective`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'MDT团队建设责任目标',
  `team_id` int(11) NOT NULL COMMENT '团队id',
  `year` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '年度',
  `year_sum` int(11) NOT NULL COMMENT '年度目标完成例次',
  `papers_num` int(11) NULL DEFAULT NULL COMMENT 'MDT病种相关研究核心期刊论文发表量',
  `other` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '其他',
  `flag` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '1:第一年 2:第二年',
  `summary` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上年年度总结',
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
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mdt_team_paper
-- ----------------------------
DROP TABLE IF EXISTS `mdt_team_paper`;
CREATE TABLE `mdt_team_paper`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'MDT团队论文表',
  `team_id` int(11) NOT NULL COMMENT '团队id',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '论文题目',
  `serial_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '期刊号',
  `posted_date` date NOT NULL COMMENT '发表时间',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_code
-- ----------------------------
DROP TABLE IF EXISTS `sys_code`;
CREATE TABLE `sys_code`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '字典维护表',
  `type` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类型  1:MDT目的',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'code',
  `value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'value',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_fee
-- ----------------------------
DROP TABLE IF EXISTS `sys_fee`;
CREATE TABLE `sys_fee`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'MDT收费情况维护表',
  `min` int(11) NOT NULL,
  `max` int(11) NULL DEFAULT NULL,
  `price` int(11) NOT NULL,
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文件表',
  `content` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
  `create_userid` int(11) NOT NULL COMMENT '创建人id',
  `create_dept` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人科室',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_file_item
-- ----------------------------
DROP TABLE IF EXISTS `sys_file_item`;
CREATE TABLE `sys_file_item`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文件项表',
  `file_id` int(11) NOT NULL COMMENT '主表id',
  `file_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件名称',
  `file_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件路径',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_grade
-- ----------------------------
DROP TABLE IF EXISTS `sys_grade`;
CREATE TABLE `sys_grade`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评分项维护表',
  `type` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类型 1:MDT专家对组织科室评价',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '打分说明',
  `min_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '打分值',
  `max_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '打分值',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` int(20) NOT NULL COMMENT '菜单表',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
  `icon` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '链接',
  `pid` int(20) NOT NULL COMMENT '父id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_msg_template
-- ----------------------------
DROP TABLE IF EXISTS `sys_msg_template`;
CREATE TABLE `sys_msg_template`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '短信模板表',
  `type` int(11) NOT NULL COMMENT '1:MDT发送短信',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_org
-- ----------------------------
DROP TABLE IF EXISTS `sys_org`;
CREATE TABLE `sys_org`  (
  `id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '机构表',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '机构名称',
  `pid` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '父id',
  `area` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '片区',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_patient
-- ----------------------------
DROP TABLE IF EXISTS `sys_patient`;
CREATE TABLE `sys_patient`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '患者信息表',
  `patient_type` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '患者类型(1:门诊 2:住院)',
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '姓名患者名称',
  `gender` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `birthday` date NULL DEFAULT NULL COMMENT '出生日期',
  `age` int(11) NULL DEFAULT NULL COMMENT '年龄',
  `idcard` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证号',
  `medical_no` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '病历号(门诊)',
  `treatment_no` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊号(门诊)',
  `medical_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '病历名称(门诊)',
  `medical_status` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '病历状态(门诊)',
  `major_doctor` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主治医生(门诊)',
  `medical_history` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '现在病史(门诊)',
  `medical_exam` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '体检(门诊)',
  `specialized_medical` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '专科体检(门诊)',
  `dispose` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处理(门诊)',
  `primary_diagnosis` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '初步诊断(门诊)',
  `in_hospital_no` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '住院号(住院)',
  `in_hospital_number` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '住院次(住院)',
  `fee_type` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '费别(住院)',
  `in_hospital_date` date NULL DEFAULT NULL COMMENT '入院日期(住院)',
  `cure_doctor` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '经治医生(住院)',
  `superior_doctor` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上级医师(住院)',
  `senior_doctor` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主任医师(住院)',
  `diagnosis` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '诊断(住院)',
  `in_hospital_days` int(11) NULL DEFAULT NULL COMMENT '出院日(住院)',
  `out_hospital_date` date NULL DEFAULT NULL COMMENT '住院日期(住院)',
  `mdt_before_project` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'MDT前治疗方案',
  `mdt_after_project` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'MDT后治疗方案',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_doctor` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改医生',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色表',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `level` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '数据权限级别 1:集团 2:院区 3:部门 4:科室 5:个人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` int(11) NOT NULL,
  `menu_id` int(11) NOT NULL,
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户表',
  `number` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '工号',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `age` int(11) NOT NULL COMMENT '年龄',
  `birthday` date NOT NULL COMMENT '生日',
  `department` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '所在科室',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '职称',
  `education` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '学历',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机长号',
  `phone_cornet` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机短号',
  `create_time` datetime(0) NOT NULL,
  `update_time` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;